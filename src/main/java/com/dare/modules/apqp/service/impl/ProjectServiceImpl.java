//package com.dare.modules.apqp.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.dare.common.constant.CommonConstant;
//import com.dare.common.vo.PageResult;
//import com.dare.modules.apqp.entity.Project;
//import com.dare.modules.apqp.mapper.APQPMapper;
//import com.dare.modules.apqp.mapper.ProjectMapper;
//import com.dare.modules.apqp.service.ProjectService;
//import com.dare.modules.project.vo.ProjectListVO;
//import com.dare.modules.project.vo.ProjectVO;
//import com.dare.utils.NumberGenerateUtil;
//import com.dare.utils.TimeUtil;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: shengyao
// * @Package: com.dare.modules.apqp.service.impl
// * @Date: 2020/11/3 14:45
// * @Description: ProjectService实现类
// * @version: 1.0
// */
//@Service
//@Transactional
//public class ProjectServiceImpl implements ProjectService {
//
//    @Autowired
//    private ProjectMapper projectMapper;
//    @Autowired
//    private APQPMapper apqpMapper;
//
//    @Override
//    public List<ProjectVO> findAll() {
//        List<ProjectVO> projectList = projectMapper.findAll();
//        return projectList;
//    }
//
//    @Override
//    public void save(Project project) {
//        projectMapper.save(project);
//    }
//
//    @Override
//    public ProjectVO queryByProNo(String projectNo) {
//        ProjectVO projectVO = projectMapper.queryByProNo(projectNo);
//        return projectVO;
//    }
//
//    @Override
//    public PageResult<List<ProjectVO>> queryByProState(Integer pageNo, Integer pageSize, Integer projectState) {
//
//        PageResult<List<ProjectVO>> result = new PageResult<>();
//
//        try {
//            PageHelper.startPage(pageNo, pageSize);
//            List<ProjectVO> projectVOList = projectMapper.queryByProState(projectState);
//            PageInfo<ProjectVO> pageInfo = new PageInfo<>(projectVOList);
//            result.setTotal(pageInfo.getTotal());
//            result.setPages(pageInfo.getPages());
//            result.setCurrent(pageInfo.getPageNum());
//            result.setSize(pageInfo.getPageSize());
//            result.setResult(projectVOList);
//            result.success("查询成功！");
//        } finally {
//            PageHelper.clearPage();
//        }
//
//        return result;
//    }
//
//    @Override
//    public Integer getProCount() {
//        Integer count = projectMapper.getProCount();
//        return count;
//    }
//
//    @Override
//    public void create(JSONObject jsonObject) throws ParseException {
//        String proName = jsonObject.getString("proName");
//        List zeroDate = jsonObject.getJSONArray("zeroDateRange");
//
//        Integer count = projectMapper.getProCount();
//        String projectNo = NumberGenerateUtil.ProjectNoGenerate(count + 1);
//        Project project = new Project();
//        project.setProjectName(proName);
//        project.setProjectNo(projectNo);
//        project.setCreatedBy("李雷");
//        project.setProjectState(CommonConstant.PROJECT_STATE_0);
//        project.setGmtCreated(TimeUtil.getCurrentTime());
//
//        projectMapper.save(project);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List list = jsonObject.getJSONArray("createStep");
//
//        for (int i = 0; i < list.size(); i++) {
//            Map<String, Object> map = (Map<String, Object>) list.get(i);
//            List timeList = (List) map.get("taskDateRange");
//            map.put("planStart", timeList.get(0));
//            map.put("planEnd", timeList.get(1));
//            Date planStart = sdf.parse((String) map.get("planStart"));
//            Date planEnd = sdf.parse((String) map.get("planEnd"));
//            long time = (planEnd.getTime() - planStart.getTime()) / (24 * 60 * 60 * 1000L) + 1;
//            int planPeriod = (int) time;
//            map.put("planPeriod", planPeriod);
//            map.remove("taskDateRange");
//            map.put("projectNo", projectNo);
//            map.put("gmtCreated", TimeUtil.getCurrentTime());
//        }
//        Map<String, Object> map0 = new HashMap<>();
//        map0.put("id", "9");
//        map0.put("projectNo", projectNo);
//        map0.put("planStart", zeroDate.get(0));
//        map0.put("planEnd", zeroDate.get(1));
//        Date planStart = sdf.parse((String) map0.get("planStart"));
//        Date planEnd = sdf.parse((String) map0.get("planEnd"));
//        long time = (planEnd.getTime() - planStart.getTime()) / (24 * 60 * 60 * 1000L) + 1;
//        int planPeriod = (int) time;
//        map0.put("planPeriod", planPeriod);
//        map0.put("gmtCreated", TimeUtil.getCurrentTime());
//        list.add(map0);
//        apqpMapper.saveProAPQPStepDetail(list, projectNo);
//    }
//
//
//}
