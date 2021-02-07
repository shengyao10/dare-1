package com.dare.modules.hrm.controller;


import com.dare.common.vo.Result;
import com.dare.modules.hrm.entity.Department;
import com.dare.modules.hrm.service.DepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/hrm/dept")
@Api(tags = "部门信息")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("分页查询部门信息")
    @GetMapping(value = "/list")
    public Result<List<Department>> show(@RequestParam("pageNo") String pageNo, @RequestParam("pageSize") String pageSize) {
        //request.getParameter;
        Result<List<Department>> result = new Result<>();
        int pagenum = 1, pagesize = 5;
        if (pageNo == null) pagenum = 1;
        else pagenum = Integer.parseInt(pageNo);
        if (pageSize == null) pagesize = 5;
        else pagesize = Integer.parseInt(pageSize);
        PageHelper.startPage(pagenum, pagesize);
        List<Department> dept = departmentService.showDepartment();
        result.setResult(dept);
        PageInfo<Department> pageInfo = new PageInfo<Department>(dept);
        System.out.println(pageInfo.getTotal());  //输出数据总数
        System.out.println(pageSize);             //输出每页显示条数
        System.out.println(pageNo);                //输出当前页
        System.out.println(pageInfo.getTotal() / pagesize);//输出总页数
        return result;
    }

    @ApiOperation("修改部门信息")
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Result<Department> edit(@RequestParam("deptId") int deptId, @RequestParam("deptName") String deptName,
                        @RequestParam("deptTel") String deptTel, @RequestParam("description") String description) {
        Result<Department> result = new Result<>();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            departmentService.EditDepartment(deptId, deptName, deptTel, description, time.format(new Date()));
            result.success("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.error500("修改失败！");
        }
        return result;

    }

    @ApiOperation("不分页查询部门信息")
    @GetMapping(value = "/listAll")
    public Result<List<Department>> showDept(){
        //request.getParameter;
        Result<List<Department>> result = new Result<>();
        List<Department> dept=departmentService.showDepartment();
        result.setResult(dept);
        return result;
    }
}
