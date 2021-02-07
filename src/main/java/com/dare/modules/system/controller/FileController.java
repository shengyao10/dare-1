package com.dare.modules.system.controller;

import com.dare.common.vo.Result;
import com.dare.modules.apqp.service.APQPService;
import com.dare.modules.project.service.ProjectService;
import com.dare.modules.sign.entity.Sign;
import com.dare.modules.sign.entity.SignPerson;
import com.dare.modules.sign.mapper.SignMapper;
import com.dare.modules.sign.service.SignService;
import com.dare.modules.system.entity.File;
import com.dare.modules.system.service.FileService;
import com.dare.utils.MinIoUtil;
import com.dare.utils.PdfSignUtil;
import com.dare.utils.TimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.system.controller
 * @Date: 2020/12/10 18:29
 * @Description: 文件控制类
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/sys/file")
@Api(tags = "文件操作")
public class FileController {
    @Autowired
    private MinIoUtil minIoUtil;
    @Autowired
    private FileService fileService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private SignService signService;
    @Autowired
    private APQPService apqpService;


    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result<List<String>> fileUpload(@RequestParam("file") MultipartFile[] files, @RequestParam("projectNo") String projectNo,
                                           @RequestParam("apqpNo") String apqpNo) {
        Result<List<String>> result = new Result();
        Integer scheduleExist = apqpService.findSchedule(projectNo, apqpNo);
        if (scheduleExist == 0) {
            result.error500("项目没有此流程，请选择正确的流程！");
            return result;
        }
        Integer isUpload = fileService.isUpload(projectNo, apqpNo);
        System.out.println(isUpload);
//        if (isUpload != 0) {
//            result.error500("上传失败！该步骤文件已存在！");
//            return result;
//        }
        if (isUpload == 1) {
            Integer isExamined = fileService.isExamined(projectNo, apqpNo);
            if (isExamined >= 1) {
                result.error500("上传失败！该步骤文件正在审批中！");
                return result;
            }
        }
        try {
            for (MultipartFile file : files
            ) {
                String orgName = null;
                String fileName = null;
                if (file != null) {
                    orgName = file.getOriginalFilename();
                    if (orgName != null) {
                        fileName = apqpNo + "/" + orgName;
                    }
                }
                String bucketName = projectNo;
                bucketName = bucketName.toLowerCase();
                boolean isBucket = minIoUtil.bucketExists(bucketName);
                if (!isBucket) {
                    minIoUtil.makeBucket(bucketName);
                }
                boolean fileExisted = minIoUtil.isFileExisted(fileName, bucketName);
                if (fileExisted) {
                    log.error("文件" + bucketName + "   " + fileName + "已存在！");
                }
                minIoUtil.putObject(bucketName, fileName, file.getInputStream(), file.getContentType());
                boolean fileExisted2 = minIoUtil.isFileExisted(fileName, bucketName);
                if (fileExisted2) {
                    File file1 = new File();
                    file1.setFileName(fileName);
                    file1.setProjectNo(projectNo);
                    file1.setApqpNo(apqpNo);
                    String filePath = "/mnt/minio/data/" + bucketName + "/" + fileName;
                    file1.setFilePath(filePath);
                    file1.setExamined(0);
                    file1.setJointlySigned(0);
                    file1.setApproved(0);
                    file1.setEdition("1");
                    file1.setSignFile("");
                    file1.setProgressRate(50);
                    file1.setReject(0);
                    System.out.println(file1.getReject());
                    fileService.save(file1, projectNo, apqpNo, isUpload);
                    List<String> list = new LinkedList<>();
                    list.add(orgName + "上传成功。");
                }
            }
            result.success("上传成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.error500("上传失败！");
        }

        return result;
    }

    @ApiOperation("下载文件")
    @GetMapping("/downloadFile")
    public Result download(@RequestParam(name = "projectNo") String projectNo, @RequestParam(name = "apqpNo") String apqpNo, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = fileService.getFileName(projectNo, apqpNo);
//            String[] str = fileName.split("/");
//            String objectName = str[str.length - 1];
            String bucketName = projectNo.toLowerCase();
            minIoUtil.downloadFile(bucketName, fileName, response);
            result.success("下载成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.error500("下载失败！");
        }
        return result;
    }

    @ApiOperation("下载会签表")
    @GetMapping("/downloadSign")
    public Result downloadSign(@RequestParam(name = "projectNo") String projectNo, @RequestParam(name = "apqpNo") String apqpNo, HttpServletResponse response) {
        Result result = new Result();
        try {
            String signPath = fileService.getSignFilePath(projectNo, apqpNo);
            String[] str = signPath.split("/");
            Integer len = str.length;
            String signFileName = str[len - 2] + "/" + str[len - 1];
            String bucketName = projectNo.toLowerCase();
            minIoUtil.downloadFile(bucketName, signFileName, response);
            result.success("下载成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.error500("下载失败！");
        }
        return result;
    }


    public static void main(String[] args) throws ParseException {
//        String fileName = "2.1.1/abc.doc";
//        String[] str = fileName.split("/");
//        String objectName = str[str.length - 1];
//        System.out.println(objectName);
        String projectNo = "123";
        String apqpNo = "456";
        File file = new File();
        StringBuffer sb = new StringBuffer();
        sb.append("/mnt/minio/data/");
        sb.append(projectNo.toLowerCase());
        sb.append("/");
        sb.append(apqpNo);
        sb.append("/会签表.pdf");
        file.setSignFile(sb.toString());
        file.setGmtCreated(TimeUtil.getCurrentTime());
        System.out.println(file.getGmtCreated());
        System.out.println(file.getSignFile());
    }

}
