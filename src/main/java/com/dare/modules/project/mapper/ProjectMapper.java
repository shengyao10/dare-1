package com.dare.modules.project.mapper;

import com.dare.modules.project.vo.*;
import com.dare.modules.project.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.project.mapper
 * @Date: 2020/12/29 10:42
 * @Description:
 * @version: 1.0
 */
@Mapper
@Component(value = "projectMapper")
public interface ProjectMapper {
    List<ProjectVO> findAll();

    void save(Project project);

    ProjectVO queryByProNo(String project_no);

    List<ProjectVO> queryByProState(Integer projectState);

    Integer getProCount();

    List<ProjectListVO> queryByCompleteState(Integer proCompleteState);

    List<ProjectListVO> queryByProjectName(String projectName);

    List<ProjectListVO> getProjectList();

    ProjectVisualizationVO getTime(String projectNo);

    List<ProjectStateVO> getProjectState();

    Project queryDetailByProNo(String projectNo);

    Integer getCompleteTaskNoBeforeStep(String projectNo, String apqpNo);

    List<ScheduleVO> getScheduleBeforeStep(String projectNo, String apqpNo);

    Integer getTaskNoBeforeStep(String projectNo, String apqpNo);

    String findCustomerNo(String projectNo);

    void submitCustomer(String projectNo, String customerNo, Date time);
}
