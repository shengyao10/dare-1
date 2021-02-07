package com.dare.modules.hrm.mapper;


import com.dare.modules.hrm.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.deer.modules.hrm.mapper
 * @Date: 2020/8/3 16:54
 * @Description:
 * @version: 1.0
 */
@Mapper
@Component(value = "employeeMapper")
public interface EmployeeMapper {
//    @Select(value = "select e.id, e.emp_name, d.dept_name, p.position_name from hrm_employee e " +
//                        " left join hrm_dept d on e.dept_id=d.dept_id " +
//                        " left join hrm_position p on e.position_id=p.position_id ")
    List<Employee> findAll();

    void deleteByEmpNo(String empNo);

    void save(Employee employee);

    void edit(Employee employee);

    Employee queryByEmpNo(String empNo);

    List<Employee> queryByDeptNo(String deptNo);

//    List<Employee> getList(Page page);
}
