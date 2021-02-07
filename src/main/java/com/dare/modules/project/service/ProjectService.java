package com.dare.modules.project.service;

import com.alibaba.fastjson.JSONObject;
import com.dare.common.vo.PageResult;
import com.dare.modules.project.vo.ProjectStateVO;
import com.dare.modules.project.vo.ProjectVO;
import com.dare.modules.project.entity.Project;
import com.dare.modules.project.vo.ProjectListVO;
import com.dare.modules.project.vo.ScheduleVO;
import com.dare.modules.sign.vo.SignPersonDetailVO;
import com.dare.modules.system.entity.File;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.project.service.impl
 * @Date: 2020/12/29 10:37
 * @Description:
 * @version: 1.0
 */
public interface ProjectService {
    List<ProjectVO> findAll();

    void save(Project project);

    ProjectVO queryByProNo(String projectNo);

    PageResult<List<ProjectVO>> queryByProState(Integer pageNo, Integer pageSize, Integer projectState);

    Integer getProCount();

    void create(JSONObject jsonObject) throws ParseException;

    PageResult<List<ProjectListVO>> queryByCompleteState(Integer pageNo, Integer pageSize, Integer proCompleteState);

    PageResult<List<ProjectListVO>> queryByProjectName(Integer pageNo, Integer pageSize, String projectName);

    List<ProjectListVO> getProjectList();

    void addProject(Project project, List list);


    List getTime(String projectNo) throws ParseException;

    List<ProjectStateVO> getProjectState();

    Project queryDetailByProNo(String projectNo);

    Integer getCompleteTaskNoBeforeStep(String projectNo, String apqpNo);

    void projectChange(Project project, List<ScheduleVO> scheduleVOList, List list, List<File> fileList, List<SignPersonDetailVO> signPersonDetailVOList);

    Integer getTaskNoBeforeStep(String projectNo, String apqpNo);

    String findCustomerNo(String projectNo);

    void submitCustomer(String projectNo, String customerNo, Date time);
}
