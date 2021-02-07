package com.dare.modules.project.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dare.common.constant.CommonConstant;
import com.dare.common.vo.PageResult;
import com.dare.modules.apqp.mapper.APQPMapper;
import com.dare.modules.project.vo.*;
import com.dare.modules.project.entity.Project;
import com.dare.modules.project.mapper.ProjectMapper;
import com.dare.modules.project.service.ProjectService;
import com.dare.modules.shiro.vo.DefConstants;
import com.dare.modules.sign.mapper.SignMapper;
import com.dare.modules.sign.vo.SignPersonDetailVO;
import com.dare.modules.system.entity.File;
import com.dare.modules.system.mapper.FileMapper;
import com.dare.utils.JwtUtil;
import com.dare.utils.NumberGenerateUtil;
import com.dare.utils.TimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.project.service.impl
 * @Date: 2020/12/29 10:38
 * @Description:
 * @version: 1.0
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private APQPMapper apqpMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private SignMapper signMapper;

    @Override
    public List<ProjectVO> findAll() {
        List<ProjectVO> projectList = projectMapper.findAll();
        return projectList;
    }

    @Override
    public void save(Project project) {
        projectMapper.save(project);
    }

    @Override
    public ProjectVO queryByProNo(String projectNo) {
        ProjectVO projectVO = projectMapper.queryByProNo(projectNo);
        return projectVO;
    }

    @Override
    public PageResult<List<ProjectVO>> queryByProState(Integer pageNo, Integer pageSize, Integer projectState) {

        PageResult<List<ProjectVO>> result = new PageResult<>();

        try {
            PageHelper.startPage(pageNo, pageSize);
            List<ProjectVO> projectVOList = projectMapper.queryByProState(projectState);
            PageInfo<ProjectVO> pageInfo = new PageInfo<>(projectVOList);
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
            result.setCurrent(pageInfo.getPageNum());
            result.setSize(pageInfo.getPageSize());
            result.setResult(projectVOList);
            result.success("查询成功！");
        } finally {
            PageHelper.clearPage();
        }

        return result;
    }

    @Override
    public Integer getProCount() {
        Integer count = projectMapper.getProCount();
        return count;
    }

    @Override
    public void create(JSONObject jsonObject) throws ParseException {
        String proName = jsonObject.getString("proName");
        List zeroDate = jsonObject.getJSONArray("zeroDateRange");

        Integer count = projectMapper.getProCount();
        String projectNo = NumberGenerateUtil.ProjectNoGenerate(count + 1);
        Project project = new Project();
        project.setProjectName(proName);
        project.setProjectNo(projectNo);
        project.setCreatedBy("李雷");
        project.setProjectState(CommonConstant.PROJECT_STATE_0);
        project.setGmtCreated(TimeUtil.getCurrentTime());

        projectMapper.save(project);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List list = jsonObject.getJSONArray("createStep");

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) list.get(i);
            List timeList = (List) map.get("taskDateRange");
            map.put("planStart", timeList.get(0));
            map.put("planEnd", timeList.get(1));
            Date planStart = sdf.parse((String) map.get("planStart"));
            Date planEnd = sdf.parse((String) map.get("planEnd"));
            long time = (planEnd.getTime() - planStart.getTime()) / (24 * 60 * 60 * 1000L) + 1;
            int planPeriod = (int) time;
            map.put("planPeriod", planPeriod);
            map.remove("taskDateRange");
            map.put("projectNo", projectNo);
            map.put("gmtCreated", TimeUtil.getCurrentTime());
        }
        Map<String, Object> map0 = new HashMap<>();
        map0.put("id", "9");
        map0.put("projectNo", projectNo);
        map0.put("planStart", zeroDate.get(0));
        map0.put("planEnd", zeroDate.get(1));
        Date planStart = sdf.parse((String) map0.get("planStart"));
        Date planEnd = sdf.parse((String) map0.get("planEnd"));
        long time = (planEnd.getTime() - planStart.getTime()) / (24 * 60 * 60 * 1000L) + 1;
        int planPeriod = (int) time;
        map0.put("planPeriod", planPeriod);
        map0.put("gmtCreated", TimeUtil.getCurrentTime());
        list.add(map0);
        apqpMapper.saveProAPQPStepDetail(list, projectNo);
    }

    @Override
    public PageResult<List<ProjectListVO>> queryByCompleteState(Integer pageNo, Integer pageSize, Integer proCompleteState) {
        PageResult<List<ProjectListVO>> result = new PageResult<>();

        try {
            PageHelper.startPage(pageNo, pageSize);
            List<ProjectListVO> projectVOList = projectMapper.queryByCompleteState(proCompleteState);
            PageInfo<ProjectListVO> pageInfo = new PageInfo<>(projectVOList);
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
            result.setCurrent(pageInfo.getPageNum());
            result.setSize(pageInfo.getPageSize());
            result.setResult(projectVOList);
            result.success("查询成功！");
        } finally {
            PageHelper.clearPage();
        }

        return result;
    }

    @Override
    public PageResult<List<ProjectListVO>> queryByProjectName(Integer pageNo, Integer pageSize, String projectName) {
        PageResult<List<ProjectListVO>> result = new PageResult<>();
        try {
            PageHelper.startPage(pageNo, pageSize);
            List<ProjectListVO> projectVOList = projectMapper.queryByProjectName(projectName);
            PageInfo<ProjectListVO> pageInfo = new PageInfo<>(projectVOList);
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
            result.setCurrent(pageInfo.getPageNum());
            result.setSize(pageInfo.getPageSize());
            result.setResult(projectVOList);
            result.success("查询成功！");
        } finally {
            PageHelper.clearPage();
        }

        return result;
    }

    @Override
    public List<ProjectListVO> getProjectList() {
        List<ProjectListVO> projectList = projectMapper.getProjectList();
        return projectList;
    }

    @Override
    public void addProject(Project project, List list) {
        projectMapper.save(project);
        apqpMapper.saveProjectAPQPStep(list);
    }


    @Override
    public List getTime(String projectNo) {
        List<Map<String, Object>> list = new LinkedList();
        ProjectVisualizationVO pro = projectMapper.getTime(projectNo);
//        list.add((Map<String, Object>) list1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>();
        Date startTime = pro.getStartTime();
        Date endTime = pro.getEndTime();
        long time = (endTime.getTime() - startTime.getTime()) / (24 * 60 * 60 * 1000L) + 1;
        int planPeriod = (int) time;
        map.put("duration", planPeriod);
        long ts = startTime.getTime();
        String start = String.valueOf(ts);
        double per = 100 * pro.getCompleteTask() / pro.getProjectTask();
        map.put("id", (int) 0);
        map.put("projectNo", pro.getProjectNo());
        map.put("projectName", pro.getProjectName());
        map.put("label", pro.getProjectName());
        map.put("user", pro.getCreatedBy());
        map.put("start", Long.parseLong(start));
        map.put("end", (int) 0);
        map.put("type", "project");
        map.put("percent", per);
        Map colorMap = new HashMap();
        colorMap.put("fill", "#FF6666");
        colorMap.put("stroke", "#000000");
        Map base = new HashMap();
        base.put("base", colorMap);
        map.put("style", base);
        list.add(map);
        List<ProjectDetailVO> list1 = apqpMapper.visualization(projectNo);
        System.out.println(list1);
        int id = 1;
        for (int i = 0; i < list1.size(); i++) {
            Map<String, Object> map1 = new HashMap<>();
            ProjectDetailVO projectDetailVO = list1.get(i);
            map1.put("id", id++);
            map1.put("parentId", 0);
            map1.put("label", projectDetailVO.getLabel());
            map1.put("user", projectDetailVO.getEmpName());
            start = String.valueOf(projectDetailVO.getPlanStart().getTime());
            map1.put("start", Long.parseLong(start));
            map1.put("end", 0);
            map1.put("duration", projectDetailVO.getPlanPeriod());
            map1.put("type", "milestone");
            if (projectDetailVO.getProgressRate() == null) {
                map1.put("percent", 0);
            } else {
                map1.put("percent", projectDetailVO.getProgressRate());
            }
            Map colorMap1 = new HashMap();
            colorMap1.put("fill", "#CCFFFF");
            colorMap1.put("stroke", "#0287D0");
            Map base1 = new HashMap();
            base1.put("base", colorMap1);
            map1.put("style", base1);
            list.add(map1);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public List<ProjectStateVO> getProjectState() {
        return projectMapper.getProjectState();
    }

    @Override
    public Project queryDetailByProNo(String projectNo) {
        Project project = projectMapper.queryDetailByProNo(projectNo);
        return project;
    }

    @Override
    public Integer getCompleteTaskNoBeforeStep(String projectNo, String apqpNo) {
        Integer completeTask = projectMapper.getCompleteTaskNoBeforeStep(projectNo, apqpNo);
        return completeTask;
    }

    @Override
    public void projectChange(Project project, List<ScheduleVO> scheduleVOList, List list, List<File> fileList, List<SignPersonDetailVO> signPersonDetailVOList) {
        projectMapper.save(project);
        apqpMapper.saveList(scheduleVOList);
        apqpMapper.saveProjectAPQPStep(list);
//        apqpMapper.saveList(scheduleVOList);
        fileMapper.saveList(fileList);
        signMapper.saveList(signPersonDetailVOList);
    }

    @Override
    public Integer getTaskNoBeforeStep(String projectNo, String apqpNo) {
        Integer projectTask = projectMapper.getTaskNoBeforeStep(projectNo, apqpNo);
        return projectTask;
    }

    @Override
    public String findCustomerNo(String projectNo) {
        String customerNo = projectMapper.findCustomerNo(projectNo);
        return customerNo;
    }

    @Override
    public void submitCustomer(String projectNo, String customerNo, Date time) {
        projectMapper.submitCustomer(projectNo, customerNo,time);
    }


}
