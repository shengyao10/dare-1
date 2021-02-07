package com.dare.modules.apqp.controller;

import com.dare.common.exception.DareException;
import com.dare.common.vo.Result;
import com.dare.modules.apqp.entity.Customer;
import com.dare.modules.apqp.mapper.CustomerMapper;
import com.dare.modules.apqp.service.CustomerService;
import com.dare.modules.apqp.vo.CustomerVO;
import com.dare.modules.project.service.ProjectService;
import com.dare.modules.system.service.FileService;
import com.dare.utils.TimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/apqp/customer")
@Api(tags = "顾客信息")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ProjectService projectService;

    @ApiOperation("添加顾客")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Customer> add(@RequestBody CustomerVO customerVO) {
        if (customerVO.equals(null)) {
            throw new DareException();
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerVO, customer);
        Result<Customer> result = new Result<>();
//        customer.setCustomerName(customerName);
//        customer.setContactPerson(contactPerson);
//        customer.setTel(tel);
        customer.setCustomerNo(TimeUtil.getCurrentTimeToNo());
        customer.setGmtCreate(new Date());
        try {
            customerService.save(customer);
            result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.error500("添加失败！");
        }
        return result;
    }


    @ApiOperation("提交顾客信息")
    @PutMapping("/submitCustomer")
    public Result submitCustomer(@RequestParam(value = "projectNo") String projectNo,
                                 @RequestParam(value = "customerNo") String customerNo) {
        Result result = new Result();
        String no = projectService.findCustomerNo(projectNo);
        System.out.println(no);
//        if (no != null || !no.equals("")) {
        if (no != null && no.length() > 0) {
            System.out.println("error");
            result.error500("顾客已设置，不能重复设置！");
            return result;
        }
        Integer file_0_1_1 = fileService.isUpload(projectNo, "9.1.1");
        System.out.println("9.1.1");
        Integer file_0_1_2 = fileService.isUpload(projectNo, "9.1.2");
        Integer file_0_1_3 = fileService.isUpload(projectNo, "9.1.3");
        StringBuffer sb = new StringBuffer();
        if (file_0_1_1 == 0) {
            sb.append("0.0.1文件——市场调研报告未上传！  ");
        }
        if (file_0_1_2 == 0) {
            sb.append("0.0.2文件——同行竞争产品调查报告未上传！  ");
        }
        if (file_0_1_3 == 0) {
            sb.append("0.0.3文件——产品建议书未上传！");
        }
        if (!sb.toString().equals("")) {
            result.error500(sb.toString());
            return result;
        }
        try {
            Date time = TimeUtil.getCurrentTime();
            projectService.submitCustomer(projectNo, customerNo, time);
            result.success("提交成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.error500("提交失败！");
            return result;
        }

        return result;
    }

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        sb.append("123");
        if (!sb.toString().equals("")) {
            sb.append("hello world");
        }
        System.out.println(sb);
    }

    @ApiOperation("查询顾客信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Customer>> getList() {
        Result<List<Customer>> result = new Result<>();
        try {
            List<Customer> customerList = customerService.findAll();
            result.setResult(customerList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.error500("查询失败！");
        }
        return result;
    }


}
