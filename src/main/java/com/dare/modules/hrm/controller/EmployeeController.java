package com.dare.modules.hrm.controller;


import com.dare.common.constant.CacheConstant;
import com.dare.common.vo.PageResult;
import com.dare.common.vo.Result;
import com.dare.modules.hrm.entity.Employee;
import com.dare.modules.hrm.service.EmployeeService;
import com.dare.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.deer.modules.hrm.controller
 * @Date: 2020/8/3 16:50
 * @Description: 员工信息controller
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/hrm/employee")
@Api(tags = "员工信息")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * @param employee
     * @Author: shengyao
     * @Description: 添加员工
     * @Date: 2020/8/3 14:32
     * @Return
     */
    @ApiOperation("添加员工")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Employee> add(@RequestBody Employee employee) {

        Integer deptNo = employee.getDeptId();
        if (redisUtil.exists(CacheConstant.SYS_EMPLOYEES_DEPT_CACHE + deptNo)) {
            log.info("删除缓存中的员工信息：" + employee);
            redisUtil.del(CacheConstant.SYS_EMPLOYEES_DEPT_CACHE + deptNo);
        }

        Result<Employee> result = new Result<>();
        employee.setGmtCreate(new Date());
        try {
            employeeService.save(employee);
            result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.error500("添加失败！");
        }
        return result;
    }


    /**
     * @param empNo
     * @Author: shengyao
     * @Description: 删除员工信息
     * @Date: 2020/8/3 10:42
     * @Return
     */
    @ApiOperation("通过员工号删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result<?> deleteByEmpNo(@RequestParam(name = "empNo", required = true) String empNo) {
        Result<?> result = new Result();
        try {
            Employee employee = employeeService.queryByEmpNo(empNo);
            Integer deptNo = employee.getDeptId();
            employeeService.deleteByEmpNo(empNo);
            if (redisUtil.exists(CacheConstant.SYS_EMPLOYEES_DEPT_CACHE + deptNo)) {
                log.info("删除缓存中的员工信息：" + employee);
                redisUtil.del(CacheConstant.SYS_EMPLOYEES_DEPT_CACHE + deptNo);
            }
            result.success("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }


    @ApiOperation("批量删除员工信息")
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
    public Result<?> deleteBatch(@RequestParam(name = "empNos", required = true) String empNos) {
        employeeService.deleteBatch(empNos);
        return Result.ok("删除成功！");
    }


    /**
     * @param employee
     * @Author: shengyao
     * @Description: 修改员工信息
     * @Date: 2020/8/3 15:51
     * @Return [employee]
     */
    @ApiOperation("修改员工信息")
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Result<Employee> edit(@RequestBody Employee employee) {
        Employee employee1 = employeeService.queryByEmpNo(employee.getEmpNo());
        Integer deptNo = employee1.getDeptId();
        if (redisUtil.exists(CacheConstant.SYS_EMPLOYEES_DEPT_CACHE + deptNo)) {
            log.info("删除缓存中的员工信息：" + employee1);
            redisUtil.del(CacheConstant.SYS_EMPLOYEES_DEPT_CACHE + deptNo);
        }
        Result<Employee> result = new Result<>();
        employee.setGmtModified(new Date());
        try {
            employeeService.edit(employee);
            result.success("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.error500("修改失败！");
        }
        return result;
    }

    /**
     * @param pageNo
     * @param pageSize
     * @Author: shengyao
     * @Description: 分页查询员工信息
     * @Date: 2020/8/2 19:36
     * @Return
     */
    @ApiOperation("分页查询员工信息")
    @GetMapping("/list")
//    @RequiresRoles("root")
    public PageResult<List<Employee>> getList(@RequestParam(required = true, defaultValue = "1", value = "pageNo") Integer pageNo,
                                              @RequestParam(required = true, defaultValue = "10", value = "pageSize") Integer pageSize) {
        PageResult<List<Employee>> result = new PageResult<>();
        PageHelper.startPage(pageNo, pageSize);


        try {
            List<Employee> employeeList = employeeService.findAll();
            PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
//            System.out.println(employeeList);
            log.info(String.valueOf(employeeList));
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
            result.setCurrent(pageInfo.getPageNum());
            result.setSize(pageInfo.getPageSize());
            result.setResult(employeeList);
            result.success("查询成功！");

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        } finally {
            PageHelper.clearPage();
        }
//        Page<Employee> employeePage = employeeService.getList(new Page<>(pageNo, pageSize));
//        System.out.println(employeePage);
//        result.setResult(employeePage);

        return result;
    }

    /**
     * @param empNo
     * @Author: shengyao
     * @Description: 根据员工号查询员工信息
     * @Date: 2020/8/6 16:31
     * @Return
     */
    @ApiOperation("根据员工号查询员工信息")
    @RequestMapping(value = "/queryByEmpNo", method = RequestMethod.GET)
    public Result<Employee> queryByEmpNo(@RequestParam(name = "empNo", required = true) String empNo) {

        Result<Employee> result = new Result<>();
        try {
            Employee employee = employeeService.queryByEmpNo(empNo);
            result.setResult(employee);
            result.success("查询成功！");

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }


        return result;

    }

    @ApiOperation("根据部门查询员工信息")
    @RequestMapping(value = "/queryByDeptNo", method = RequestMethod.GET)
    public Result<List<Employee>> queryByDeptNo(@RequestParam(name = "deptNo", required = true) String deptNo) {

        if (redisUtil.exists(CacheConstant.SYS_EMPLOYEES_DEPT_CACHE + deptNo)) {
            log.info("通过缓存读取员工信息：" + redisUtil.get(CacheConstant.SYS_EMPLOYEES_DEPT_CACHE + deptNo));
            return (Result<List<Employee>>) redisUtil.get(CacheConstant.SYS_EMPLOYEES_DEPT_CACHE + deptNo);
        }
        Result<List<Employee>> result = new Result<>();
        try {
            List<Employee> employeeList = employeeService.queryByDeptNo(deptNo);
            result.setResult(employeeList);
            result.success("查询成功");
            log.info("将员工信息存入缓存：" + result);
            redisUtil.set(CacheConstant.SYS_EMPLOYEES_DEPT_CACHE + deptNo, result);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }


        return result;

    }


}