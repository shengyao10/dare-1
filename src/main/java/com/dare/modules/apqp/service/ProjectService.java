//package com.dare.modules.apqp.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.dare.common.vo.PageResult;
//import com.dare.modules.apqp.entity.Project;
//import com.dare.modules.project.vo.ProjectListVO;
//import com.dare.modules.project.vo.ProjectVO;
//
//import java.text.ParseException;
//import java.util.List;
//
///**
// * @Author: shengyao
// * @Package: com.dare.modules.apqp.service
// * @Date: 2020/11/3 10:20
// * @Description:
// * @version: 1.0
// */
//public interface ProjectService {
//    List<ProjectVO> findAll();
//
//    void save(Project project);
//
//    ProjectVO queryByProNo(String projectNo);
//
//    PageResult<List<ProjectVO>> queryByProState(Integer pageNo, Integer pageSize, Integer projectState);
//
//    Integer getProCount();
//
//    void create(JSONObject jsonObject) throws ParseException;
//
////    PageResult<List<ProjectListVO>> queryByCompleteState(Integer pageNo, Integer pageSize, Integer proCompleteState);
////
////    PageResult<List<ProjectListVO>> queryByProjectName(Integer pageNo, Integer pageSize, String projectName);
//}
