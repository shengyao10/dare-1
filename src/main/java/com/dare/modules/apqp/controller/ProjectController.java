//package com.dare.modules.apqp.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.dare.common.exception.DareException;
//import com.dare.common.vo.PageResult;
//import com.dare.common.vo.Result;
//import com.dare.modules.apqp.service.APQPService;
//import com.dare.modules.apqp.service.ProjectService;
//import com.dare.modules.project.vo.ProjectVO;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
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
// * @Package: com.dare.modules.apqp.controller
// * @Date: 2020/11/3 10:17
// * @Description: 项目控制类
// * @version: 1.0
// */
//@Slf4j
//@RestController
//@RequestMapping("/apqp/project")
//@Api(tags = "项目信息")
//public class ProjectController {
//
//    @Autowired
//    private ProjectService projectService;
//    @Autowired
//    private APQPService apqpService;
//
//
//    /**
//     * @param jsonObject
//     * @Author: shengyao
//     * @Description: 添加项目
//     * @Date: 2020/11/3 15:55
//     * @Return com.dare.common.vo.Result<com.dare.modules.apqp.entity.Project>
//     */
//    @ApiOperation("添加项目")
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public Result<?> add(@RequestBody JSONObject jsonObject) {
//        System.out.println(jsonObject.toString());
//        Result result = new Result();
//        try {
//            projectService.create(jsonObject);
//
//            result.success("添加成功！");
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.setSuccess(false);
//            result.setMessage("添加失败！");
//        }
//        return result;
//    }
//
////    @ApiOperation("添加项目")
////    @RequestMapping(value = "/add", method = RequestMethod.POST)
////    public Result<Project> add(@RequestParam(name = "project_name", required = true) String project_name,
////                               @RequestParam(name = "created_by", required = true) String created_by,
////                               @RequestParam(name = "plan_time", required = true) String plan_time,
////                               @RequestBody ProjectStepVO projectStepVO) {
////        Project project = new Project();
////        Result<Project> result = new Result<>();
////        project.setProject_name(project_name);
////        project.setCreated_by(created_by);
////        project.setProject_state(CommonConstant.PROJECT_STATE_0);
////        project.setProject_no(TimeUtil.getCurrentTimeToNo());
////        project.setGmt_create(new Date());
////        try {
////            projectService.save(project);
////            result.success("添加成功！");
////        } catch (Exception e) {
////            e.printStackTrace();
////            result.error500("添加失败！");
////        }
////        return result;
////    }
//
//    /**
//     * @param pageNo
//     * @param pageSize
//     * @Author: shengyao
//     * @Description: 分页查询项目信息
//     * @Date: 2020/11/3 15:31
//     * @Return com.dare.common.vo.PageResult<java.util.List < com.dare.modules.apqp.entity.Project>>
//     */
//    @ApiOperation("分页查询项目信息")
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public PageResult<List<ProjectVO>> getList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
//                                               @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
//        PageResult<List<ProjectVO>> result = new PageResult<>();
//        PageHelper.startPage(pageNo, pageSize);
//
//        try {
//            List<ProjectVO> projectList = projectService.findAll();
//            PageInfo<ProjectVO> pageInfo = new PageInfo<>(projectList);
//            log.info(String.valueOf(projectList));
//            result.setTotal(pageInfo.getTotal());
//            result.setPages(pageInfo.getPages());
//            result.setCurrent(pageInfo.getPageNum());
//            result.setSize(pageInfo.getPageSize());
//            result.setResult(projectList);
//            result.success("查询成功！");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            result.setSuccess(false);
//            result.setMessage("操作失败！");
//        } finally {
//            PageHelper.clearPage();
//        }
//        return result;
//    }
//
//
//    /**
//     * @param projectNo 项目编号
//     * @Author: shengyao
//     * @Description: 根据编号查询项目信息
//     * @Date: 2020/11/4 19:35
//     * @Return com.dare.common.vo.Result<com.dare.modules.project.vo.ProjectVO>
//     */
//    @ApiOperation("根据编号查询项目信息")
//    @RequestMapping(value = "/queryByProNo", method = RequestMethod.GET)
//    public Result<ProjectVO> queryByProNo(@RequestParam(name = "projectNo", required = true) String projectNo) {
//        if (StringUtils.isEmpty(projectNo)) {
//            System.out.println("参数错误！");
//            throw new DareException("参数错误！");
//        }
//        Result<ProjectVO> result = new Result<>();
//        try {
//            ProjectVO projectVO = projectService.queryByProNo(projectNo);
//            result.setResult(projectVO);
//            result.success("查询成功！");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            result.setSuccess(false);
//            result.setMessage("操作失败！");
//        }
//
//
//        return result;
//    }
//
//    @ApiOperation("根据项目立项状态查询项目信息")
//    @RequestMapping(value = "/queryByProState", method = RequestMethod.GET)
//    public PageResult<List<ProjectVO>> queryByProState(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
//                                                       @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize,
//                                                       @RequestParam(name = "projectState") Integer projectState) {
//        if (StringUtils.isEmpty(projectState)) {
//            throw new DareException("参数错误！");
//        }
//        PageResult<List<ProjectVO>> result = new PageResult<>();
//        try {
//            result = projectService.queryByProState(pageNo, pageSize, projectState);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            result.setSuccess(false);
//            result.setMessage("操作失败！");
//        }
//
//
//        return result;
//    }
//
//
//
//    //    @ResponseBody
//    @RequestMapping(method = RequestMethod.GET, value = "/test")
//    public JSONObject test(@RequestBody JSONObject jsonObject) {
////        System.out.println(jsonObject);
////        System.out.println(jsonObject.toString());
//
////        String proName = jsonObject.getString("proName");
////        System.out.println("proName: " + proName);
//
//        List list = jsonObject.getJSONArray("createStep");
//        System.out.println(list);
////        for (Object l : list
////        ) {
////            System.out.println(l);
////        }
////        APQPCreatedVO apqpCreatedVO = (APQPCreatedVO) list.get(0);
//        Map<String, Object> map = (Map<String, Object>) list.get(0);
//
////        System.out.println("map: " + map);
////        System.out.println("id: " + map.get("id"));
////        System.out.println("department: " + map.get("department"));
////        System.out.println("pid: " + map.get("pid"));
////        System.out.println("staff: " + map.get("staff"));
////        System.out.println("taskDateRange: " + map.get("taskDateRange"));
////        List list1 = (List) map.get("taskDateRange");
////        System.out.println(list1.get(0));
//
//        List zeroDateRange = jsonObject.getJSONArray("zeroDateRange");
//        System.out.println(zeroDateRange.get(0));
//        System.out.println(zeroDateRange.get(1));
//
//        return jsonObject;
//    }
//
//
//    public static void main(String[] args) throws ParseException {
//        Map map = new HashMap();
//        map.put("start", "2020-12-31 00:00:00");
//        map.put("end", "2021-1-10 23:59:59");
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Date date1 = sdf.parse(sdf.format(map.get("start")));
//        Date date1 = sdf.parse((String) map.get("start"));
//        Date date2 = sdf.parse((String) map.get("end"));
////        Date date2 = sdf.parse(sdf.format(map.get("end")));
//
////        double day = (double)(date2.getTime()-date1.getTime())/(24*60*60*1000);
//        long day = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000L) + 1;
//        System.out.println(day);
//
//
//        System.out.println(date1);
//        System.out.println(date2);
//    }
//
//
//}
