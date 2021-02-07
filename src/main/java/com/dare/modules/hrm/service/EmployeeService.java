package com.dare.modules.hrm.service;

import com.dare.modules.hrm.entity.Employee;

import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.deer.modules.hrm.service
 * @Date: 2020/8/3 16:51
 * @Description:
 * @version: 1.0
 */
public interface EmployeeService {

//    Page<Employee> getList(Page<Employee> page);

    List<Employee> findAll();

    void deleteByEmpNo(String empNo);

    void save(Employee employee);

    void edit(Employee employee);

    Employee queryByEmpNo(String empNo);

    void deleteBatch(String empNos);


    List<Employee> queryByDeptNo(String deptNo);
}
