package com.dare.modules.hrm.service.impl;

import com.dare.modules.hrm.mapper.EmployeeMapper;
import com.dare.modules.hrm.entity.Employee;
import com.dare.modules.hrm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.deer.modules.hrm.service.impl
 * @Date: 2020/8/3 16:51
 * @Description:
 * @version: 1.0
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

//    @Override
//    public Page<Employee> getList(Page<Employee> page) {
//        return page.setRecords(this.baseMapper.getList(page));
//    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employeeList = employeeMapper.findAll();
//        System.out.println(employeeList);
        return employeeList;
    }

    @Override
    public void deleteByEmpNo(String empNo) {
        employeeMapper.deleteByEmpNo(empNo);
    }

    @Override
    public void save(Employee employee) {
        employeeMapper.save(employee);
//        return false;
    }


    @Override
    public void edit(Employee employee) {
        employeeMapper.edit(employee);
    }

    @Override
    public Employee queryByEmpNo(String empNo) {
        Employee employee = employeeMapper.queryByEmpNo(empNo);
        return employee;
    }

    @Override
    public void deleteBatch(String empNos) {

    }

    @Override
    public List<Employee> queryByDeptNo(String deptNo) {
        List<Employee> employeeList = employeeMapper.queryByDeptNo(deptNo);
        return employeeList;
    }


}
