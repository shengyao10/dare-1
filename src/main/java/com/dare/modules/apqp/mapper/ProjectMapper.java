//package com.dare.modules.apqp.mapper;
//
//import com.dare.modules.apqp.entity.Project;
//import com.dare.modules.project.vo.ProjectVO;
//import org.apache.ibatis.annotations.Mapper;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @Author: shengyao
// * @Package: com.dare.modules.apqp.mapper
// * @Date: 2020/11/3 14:48
// * @Description:
// * @version: 1.0
// */
//@Mapper
//@Component(value = "projectMapper")
//public interface ProjectMapper {
//    List<ProjectVO> findAll();
//
//    void save(Project project);
//
//    ProjectVO queryByProNo(String project_no);
//
//    List<ProjectVO> queryByProState(Integer projectState);
//
//    Integer getProCount();
//
////    List<ProjectListVO> queryByCompleteState(Integer proCompleteState);
////
////    List<ProjectListVO> queryByProjectName(String projectName);
//}
