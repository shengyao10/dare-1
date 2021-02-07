package com.dare.modules.hrm.mapper;

import com.dare.modules.hrm.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component(value = "departmentMapper")
public interface DepartmentMapper {
//    @Modifying
//    @Update(value =" update hrm_dept set dept_name=#{deptName},dept_tel=#{deptTel},description=#{description},gmt_modified=#{time} where dept_id=#{deptId}")
    void UpdateDepartment(int deptId, String deptName, String deptTel, String description, String time);
//    @Select("select * from hrm_dept ")
    List<Department> SelectAllDepartment();
}
