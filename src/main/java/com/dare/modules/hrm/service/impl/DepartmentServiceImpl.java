package com.dare.modules.hrm.service.impl;

import com.dare.modules.hrm.mapper.DepartmentMapper;
import com.dare.modules.hrm.entity.Department;
import com.dare.modules.hrm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> showDepartment() {
        return departmentMapper.SelectAllDepartment();
    }

    @Override
    public boolean EditDepartment(int deptId, String deptName, String deptTel, String description, String time) {
        departmentMapper.UpdateDepartment(deptId,deptName,deptTel,description,time);
        return true;
    }
}
