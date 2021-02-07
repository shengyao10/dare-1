package com.dare.modules.project.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dare.common.constant.CommonConstant;
import com.dare.common.exception.DareException;
import com.dare.common.vo.PageResult;
import com.dare.common.vo.Result;
import com.dare.modules.apqp.service.APQPService;
import com.dare.modules.apqp.vo.APQPStepVO;
import com.dare.modules.project.entity.Project;
import com.dare.modules.project.vo.*;
import com.dare.modules.project.service.ProjectService;
import com.dare.modules.shiro.vo.DefConstants;
import com.dare.modules.sign.entity.Sign;
import com.dare.modules.sign.entity.SignPerson;
import com.dare.modules.sign.service.SignService;
import com.dare.modules.sign.vo.SignPersonDetailVO;
import com.dare.modules.system.entity.File;
import com.dare.modules.system.entity.User;
import com.dare.modules.system.mapper.FileMapper;
import com.dare.modules.system.service.FileService;
import com.dare.modules.system.service.UserService;
import com.dare.utils.JwtUtil;
import com.dare.utils.MinIoUtil;
import com.dare.utils.NumberGenerateUtil;
import com.dare.utils.TimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.project.controller
 * @Date: 2020/12/29 10:37
 * @Description:
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/project")
@Api(tags = "项目信息")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private APQPService apqpService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MinIoUtil minIoUtil;
    @Autowired
    private SignService signService;
    @Autowired
    private FileMapper fileMapper;


    /**
     * @param jsonObject
     * @Author: shengyao
     * @Description: 添加项目
     * @Date: 2020/11/3 15:55
     * @Return com.dare.common.vo.Result<com.dare.modules.apqp.entity.Project>
     */
    @ApiOperation("添加项目")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(@RequestBody JSONObject jsonObject) {
        System.out.println(jsonObject.toString());
        Result result = new Result();
        try {
            projectService.create(jsonObject);

            result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("添加失败！");
        }
        return result;
    }

    /**
     * @param jsonObject
     * @param request
     * @Author: shengyao
     * @Description: 项目管理添加项目
     * @Date: 2021/1/4 9:46
     * @Return com.dare.common.vo.Result<?>
     */
    @ApiOperation("项目管理添加项目")
    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    public Result<?> addProject(@RequestBody JSONObject jsonObject, HttpServletRequest request) throws ParseException {
//        projectService.addProject(jsonObject);

        log.info(jsonObject.toString());
        System.out.println(jsonObject.toString());
        Result result = new Result();

        String token = request.getHeader(DefConstants.X_TOKEN);
        if (token == null) {
            result.error500("token为空！");
            return result;
        }
        String username = JwtUtil.getUsername(token);
        String empName = userService.getEmpNameByUsername(username);
        String proName = jsonObject.getString("proName");
        if (proName.length() == 0) {
            result.error500("未填写项目名！");
            return result;
        }
        List dateRange = jsonObject.getJSONArray("dateRange");

        if (dateRange == null) {
            result.error500("未填写项目计划时间！");
            return result;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Integer count = projectService.getProCount();
        String projectNo = NumberGenerateUtil.ProjectNoGenerate(count + 1);
        Project project = new Project();
        project.setProjectName(proName);
        project.setProjectNo(projectNo);
        project.setCreatedBy(empName);
        project.setStartTime(sdf.parse((String) dateRange.get(0)));
        project.setEndTime(sdf.parse((String) dateRange.get(1)));
        project.setProjectState(CommonConstant.PROJECT_STATE_1);
        project.setProCompleteState(CommonConstant.PROJECT_COMPLETE_STATE_0);
        project.setGmtCreated(TimeUtil.getCurrentTime());
        project.setCompleteTask(0);

        List list = new LinkedList();
        Integer projectTask = 0;

        for (int i = 0; i <= 5; i++) {
            String str = "createStep";
            str = str + i;
            JSONArray stepList = jsonObject.getJSONArray(str);
            if (stepList == null) {
                continue;
            }
            for (int j = 0; j < stepList.size(); j++) {
                Map<String, Object> map = (Map<String, Object>) stepList.get(j);
                List timeList = (List) map.get("taskDateRange");
                if (timeList.size() == 2) {
                    projectTask++;
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
                    map.put("state", CommonConstant.APQP_STATE_0);
                    map.put("fileState", CommonConstant.FILE_STATE_0);
                    list.add(map);
                }
            }
        }
        System.out.println("projectTask: " + projectTask);
        if (projectTask == 0) {
            result.error500("未选择步骤时间！");
            return result;
        }
        project.setProjectTask(projectTask);
        try {
            projectService.addProject(project, list);
            result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("添加失败！");
        }
        return result;
    }

    @ApiOperation("项目可视化")
    @GetMapping("/visualization")
    public Result visualization(@RequestParam(name = "projectNo") String projectNo) throws ParseException {
        Result result = new Result();
        try {
            List list = projectService.getTime(projectNo);
            result.setResult(list);
            result.success("操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }

    /**
     * @param jsonObject
     * @param request
     * @Author: shengyao
     * @Description: 项目变更
     * @Date: 2021/1/20 22:51
     * @Return com.dare.common.vo.Result
     */
    @ApiOperation("项目变更")
    @PostMapping("/projectChange")
    public Result projectChange(@RequestBody JSONObject jsonObject, HttpServletRequest request) throws ParseException, IOException {
        log.info(jsonObject.toString());

        Result result = new Result<>();
        String token = request.getHeader(DefConstants.X_TOKEN);
        if (token == null) {
            result.error500("token为空！");
            return result;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 获取项目计划总时间
            List dateRange = jsonObject.getJSONArray("dateRange");

            String username = JwtUtil.getUsername(token);
            String empName = userService.getEmpNameByUsername(username);
            // 获取项目编号
            String projectNo = jsonObject.getString("projectNo");
            // 获取apqp编号，即从该步骤开始变更
            String apqpNo = jsonObject.getString("apqpNo");
            // 无需变更部分已完成的项目数
            Integer completeTask = projectService.getCompleteTaskNoBeforeStep(projectNo, apqpNo);
            // 无需变更部分总的项目数
            Integer projectTask = projectService.getTaskNoBeforeStep(projectNo, apqpNo);

            Project project = projectService.queryDetailByProNo(projectNo);
            project.setId(null);
            Integer count = projectService.getProCount();
            // 新项目编号
            String newProjectNo = NumberGenerateUtil.ProjectNoGenerate(count + 1);
            project.setProjectNo(newProjectNo);
            project.setCreatedBy(empName);
            project.setStartTime(sdf.parse((String) dateRange.get(0)));
            project.setEndTime(sdf.parse((String) dateRange.get(1)));
            project.setProjectState(CommonConstant.PROJECT_STATE_1);
            project.setProCompleteState(CommonConstant.PROJECT_COMPLETE_STATE_0);
            project.setCompleteTask(completeTask);
            project.setGmtCreated(TimeUtil.getCurrentTime());
            // 旧桶名
            String oldBucketName = projectNo.toLowerCase();
            // 新桶名
            String newBucketName = newProjectNo.toLowerCase();
            // 判断桶是否存在，不存在则新建桶
            boolean isBucket = minIoUtil.bucketExists(newBucketName);
            if (!isBucket) {
                minIoUtil.makeBucket(newBucketName);
            }
            // 无需变更部分文件信息列表
            List<File> fileList = fileService.getFileListBeforeStep(projectNo, apqpNo);
            for (File file : fileList
            ) {
                // 复制无需变更部分文件
//                java.io.File apqpFile = new java.io.File(file.getFilePath());
//                InputStream inputStream = new FileInputStream(apqpFile);
//                MultipartFile multipartFile = new MockMultipartFile(apqpFile.getName(), inputStream);
//                minIoUtil.putObject(newBucketName, file.getFileName(), multipartFile.getInputStream(), multipartFile.getContentType());
                String apqpFilePath = file.getFilePath();
                String[] str = apqpFilePath.split("/");
                String apqpFileName = str[str.length - 2] + "/" + str[str.length - 1];
                minIoUtil.uploadObject(newBucketName, apqpFileName, apqpFilePath);
                // 获取会签表文件名
                String signPath = file.getSignFile();
                String[] strings = signPath.split("/");
                int len = strings.length;
                String signFileName = strings[len - 2] + "/" + strings[len - 1];
                // 无需变更部分会签表
//                java.io.File signFile = new java.io.File(file.getSignFile());
//                InputStream inputStream2 = new FileInputStream(signFile);
//                MultipartFile multipartFile2 = new MockMultipartFile(signFile.getName(), inputStream2);
//                minIoUtil.putObject(newBucketName, signFileName, multipartFile2.getInputStream(), multipartFile2.getContentType());
                minIoUtil.uploadObject(newBucketName, signFileName, signPath);

                // 文件对应项目号更新
                file.setProjectNo(newProjectNo);
                String filePath = file.getFilePath().replace(oldBucketName, newBucketName);
                // 文件、会签表路径更新
                file.setFilePath(filePath);
                String signFilePath = file.getSignFile().replace(oldBucketName, newBucketName);
                file.setSignFile(signFilePath);
            }

            // 无需变更部分会签信息列表
            List<SignPersonDetailVO> signPersonDetailVOList = signService.getSignListBeforeStep(projectNo, apqpNo);
            for (SignPersonDetailVO a : signPersonDetailVOList
            ) {
                a.setProjectNo(newProjectNo);
            }

            // 无需变更部分apqp流程信息列表
            List<ScheduleVO> scheduleVOList = apqpService.getScheduleBeforeStep(projectNo, apqpNo);
            for (ScheduleVO a : scheduleVOList
            ) {
                a.setProjectNo(newProjectNo);
            }

            // one：第几阶段
            String[] strings = apqpNo.split("\\.");
            Integer one = Integer.parseInt(strings[0]);
            if (one == 9) {
                one = 0;
            }
//            System.out.println("one: " + one);
            // 判断该阶段是否需要变更，
            // 0：无需变更，进入下一阶段。
            // 1：需要变更
            Integer stepStart = 0;

            // 变更部分的apqp流程内容
            List<Map<String, Object>> list = new LinkedList<>();

            // 从第one阶段开始更新
            for (int i = one; i <= 5; i++) {
                String str = "createStep";
                str = str + i;
                JSONArray stepList = jsonObject.getJSONArray(str);
                if (stepList == null) {
                    continue;
                }
                for (int j = 0; j < stepList.size(); j++) {
                    Map<String, Object> map = (Map<String, Object>) stepList.get(j);
                    // 进入需要变更的某一步骤，stepStart = 1
                    if (map.get("id").equals(apqpNo)) {
                        stepStart = 1;
                    }
                    // stepStart=1：需要变更
                    if (stepStart == 1) {
                        List timeList = (List) map.get("taskDateRange");
                        // 判断计划时间是否为空
                        // 若无计划时间，则证明没有该步骤，跳过
                        if (timeList.size() == 2) {
                            // 总任务数+1
                            projectTask++;
                            map.put("planStart", timeList.get(0));
                            map.put("planEnd", timeList.get(1));
                            Date planStart = sdf.parse((String) map.get("planStart"));
                            Date planEnd = sdf.parse((String) map.get("planEnd"));
                            // 计算计划工期
                            long time = (planEnd.getTime() - planStart.getTime()) / (24 * 60 * 60 * 1000L) + 1;
                            int planPeriod = (int) time;
                            map.put("planPeriod", planPeriod);
                            map.remove("taskDateRange");
                            map.put("projectNo", newProjectNo);
                            map.put("gmtCreated", TimeUtil.getCurrentTime());
                            map.put("state", CommonConstant.APQP_STATE_0);
                            map.put("fileState", CommonConstant.FILE_STATE_0);
                            list.add(map);
                        }
                    }
                }
            }
            // 存储项目总任务数
            project.setProjectTask(projectTask);
            // 存储项目内容，变更部分信息，无需变更部分信息，已上传文件信息，会签信息
            projectService.projectChange(project, scheduleVOList, list, fileList, signPersonDetailVOList);
            result.success("操作成功！");
//            result.setResult(signPersonDetailVOList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.error500("操作失败！");
        }
        return result;
    }


    /**
     * @param pageNo
     * @param pageSize
     * @Author: shengyao
     * @Description: 分页查询项目信息
     * @Date: 2020/11/3 15:31
     * @Return com.dare.common.vo.PageResult<java.util.List < com.dare.modules.apqp.entity.Project>>
     */
    @ApiOperation("分页查询项目信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageResult<List<ProjectVO>> getList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
                                               @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        PageResult<List<ProjectVO>> result = new PageResult<>();
        PageHelper.startPage(pageNo, pageSize);

        try {
            List<ProjectVO> projectList = projectService.findAll();
            PageInfo<ProjectVO> pageInfo = new PageInfo<>(projectList);
            log.info(String.valueOf(projectList));
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
            result.setCurrent(pageInfo.getPageNum());
            result.setSize(pageInfo.getPageSize());
            result.setResult(projectList);
            result.success("查询成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        } finally {
            PageHelper.clearPage();
        }
        return result;
    }

    /**
     * @param pageNo
     * @param pageSize
     * @Author: shengyao
     * @Description: 项目管理分页查询项目信息
     * @Date: 2021/1/4 9:45
     * @Return com.dare.common.vo.PageResult<java.util.List < com.dare.modules.project.vo.ProjectListVO>>
     */
    @ApiOperation("项目管理分页查询项目信息")
    @RequestMapping(value = "/proList", method = RequestMethod.GET)
    public PageResult<List<ProjectListVO>> getProjectList(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
                                                          @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        PageResult<List<ProjectListVO>> result = new PageResult<>();
        PageHelper.startPage(pageNo, pageSize);

        try {
            List<ProjectListVO> projectList = projectService.getProjectList();
            PageInfo<ProjectListVO> pageInfo = new PageInfo<>(projectList);
            log.info(String.valueOf(projectList));
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
            result.setCurrent(pageInfo.getPageNum());
            result.setSize(pageInfo.getPageSize());
            result.setResult(projectList);
            result.success("查询成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        } finally {
            PageHelper.clearPage();
        }
        return result;
    }

    /**
     * @param projectNo
     * @Author: shengyao
     * @Description: 获取项目详细信息
     * @Date: 2021/1/4 16:08
     * @Return com.dare.common.vo.Result
     */
    @ApiOperation("获取项目详细信息")
    @GetMapping("/queryProjectDetail")
    public Result<List<ProjectDetailVO>> queryProjectDetail(@RequestParam(name = "projectNo") String projectNo) {

        Result result = new Result();
        try {
            List<ProjectDetailVO> proList = apqpService.queryProjectDetail(projectNo);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < proList.size(); i++) {
                List tList = new LinkedList();
                if (proList.get(i).getPlanStart() != null && proList.get(i).getPlanEnd() != null) {
                    tList.add(sdf.format(proList.get(i).getPlanStart()));
                    tList.add(sdf.format(proList.get(i).getPlanEnd()));
                    proList.get(i).setTimeList(tList);
                }

            }
            System.out.println(proList);
            List<ProjectDetailVO> list = ProjectDetailVO.getTreeResult(proList, "0");
            System.out.println(list);
            log.info("list: " + list);
            result.setResult(list);
            result.success("操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.error500("操作失败！");
        }

        return result;
    }


    /**
     * @param projectNo 项目编号
     * @Author: shengyao
     * @Description: 根据编号查询项目信息
     * @Date: 2020/11/4 19:35
     * @Return com.dare.common.vo.Result<com.dare.modules.project.vo.ProjectVO>
     */
    @ApiOperation("根据编号查询项目信息")
    @RequestMapping(value = "/queryByProNo", method = RequestMethod.GET)
    public Result<ProjectVO> queryByProNo(@RequestParam(name = "projectNo", required = true) String projectNo) {
        if (StringUtils.isEmpty(projectNo)) {
            System.out.println("参数错误！");
            throw new DareException("参数错误！");
        }
        Result<ProjectVO> result = new Result<>();
        try {
            ProjectVO projectVO = projectService.queryByProNo(projectNo);
            result.setResult(projectVO);
            result.success("查询成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }


        return result;
    }

    @ApiOperation("根据项目立项状态查询项目信息")
    @RequestMapping(value = "/queryByProState", method = RequestMethod.GET)
    public PageResult<List<ProjectVO>> queryByProState(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
                                                       @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize,
                                                       @RequestParam(name = "projectState") Integer projectState) {
        if (StringUtils.isEmpty(projectState)) {
            throw new DareException("参数错误！");
        }
        PageResult<List<ProjectVO>> result = new PageResult<>();
        try {
            result = projectService.queryByProState(pageNo, pageSize, projectState);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }


        return result;
    }


    @ApiOperation("根据项目完成状态查询项目信息")
    @RequestMapping(value = "/queryByProCompleteState", method = RequestMethod.GET)
    public PageResult<List<ProjectListVO>> queryByCompleteState(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
                                                                @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize,
                                                                @RequestParam(name = "proCompleteState") Integer proCompleteState) {
        if (StringUtils.isEmpty(proCompleteState)) {
            throw new DareException("参数错误！");
        }
        PageResult<List<ProjectListVO>> result = new PageResult<>();
        try {
            result = projectService.queryByCompleteState(pageNo, pageSize, proCompleteState);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }

    @ApiOperation("根据项目名查询项目信息")
    @RequestMapping(value = "/queryByProjectName", method = RequestMethod.GET)
    public PageResult<List<ProjectListVO>> queryByProjectName(@RequestParam(defaultValue = "1", value = "pageNo") Integer pageNo,
                                                              @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize,
                                                              @RequestParam(name = "projectName") String projectName) {
        if (StringUtils.isEmpty(projectName)) {
            throw new DareException("参数错误！");
        }
        PageResult<List<ProjectListVO>> result = new PageResult<>();
        try {
            projectName = "%" + projectName + "%";
//            System.out.println(projectName);
            result = projectService.queryByProjectName(pageNo, pageSize, projectName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("操作失败！");
        }
        return result;
    }


    /**
     * @return 返回所有项目的状态
     * @Author Yinlei
     */
    @ApiOperation("返回所有的项目状态")
    @GetMapping("/projectstate")
    public Result<List<ProjectStateVO>> getProjectState() {
        List<ProjectStateVO> projectStateVOList = projectService.getProjectState();
        Result result = new Result();
        try {
            result.setResult(projectStateVOList);
            result.setCode(CommonConstant.SC_OK_200);
            result.setSuccess(true);
            result.setMessage("查找成功!");
        } catch (Exception e) {
            result.setCode(400);
            result.setMessage("查找失败!");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/test")
    public String test() throws IOException {
//        java.io.File apqpFile = new java.io.File("D:/1德尔/任务书.docx");
//        InputStream inputStream = new FileInputStream(apqpFile);
//        MultipartFile multipartFile = new MockMultipartFile(apqpFile.getName(), inputStream);
//        minIoUtil.putObject("test", "任务书.docx", multipartFile.getInputStream(), multipartFile.getContentType());
        minIoUtil.uploadObject("test", "任务书.docx", "D:/1德尔/任务书.docx");
        return "success";
    }


    public static void main(String[] args) throws ParseException, IOException {
//        Map map = new HashMap();
//        map.put("start", "2020-12-31 00:00:00");
//        map.put("end", "2021-1-10 23:59:59");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Date date1 = sdf.parse(sdf.format(map.get("start")));
//        Date date1 = sdf.parse((String) map.get("start"));
//        Date date2 = sdf.parse((String) map.get("end"));
////        Date date2 = sdf.parse(sdf.format(map.get("end")));
////        double day = (double)(date2.getTime()-date1.getTime())/(24*60*60*1000);
//        long day = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000L) + 1;
//        System.out.println(day);
//        System.out.println(date1);
//        System.out.println(date2);


    }
}
