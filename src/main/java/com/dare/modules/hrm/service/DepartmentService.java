package com.dare.modules.hrm.service;

import com.dare.modules.hrm.entity.Department;

import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.deer.modules.hrm.service
 * @Date: 2020/8/4 9:46
 * @Description:
 * @version: 1.0
 */
public interface DepartmentService {
    List<Department> showDepartment();

    boolean EditDepartment(int deptId, String deptName, String deptTel, String description, String time);
}
