package com.dare.modules.sign.control;


import com.dare.common.vo.Result;
import com.dare.modules.project.entity.Project;
import com.dare.modules.shiro.vo.DefConstants;
import com.dare.modules.sign.entity.Sign;
import com.dare.modules.sign.entity.SignPerson;
import com.dare.modules.sign.service.SignService;
import com.dare.modules.sign.vo.ApqpStateVO;
import com.dare.modules.sign.vo.RejectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dare.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/sign")
@Api(tags = "签名")
public class SignController {
    @Autowired
    private SignService signService;

    @ApiOperation("审核")
    @GetMapping(value = "/examine")
    public Result<?> examine(HttpServletRequest request,
                             @RequestParam(name = "projectNo") String projectNo,
                             @RequestParam(name = "apqpNo") String apqpNo)
    {
        Result<?> result = new Result();
        try {
            String token = request.getHeader(DefConstants.X_TOKEN);
            if (token == null) {
                result.error500("token为空！");
                return result;
            }
            String username = JwtUtil.getUsername(token);
            String empNo = signService.getEmpNo(username);
            result.setMessage("未上传当前流程文件！");
            int times=signService.findExamined(projectNo, apqpNo);
            String templatePath=signService.findFile(projectNo, apqpNo);
            result.setMessage("未发现当前用户签名样图");
            String sign=signService.findSign(empNo);
            String newPDFPath="/mnt/minio/data/template/countersign/会签模板.pdf";
            Map<Object, Object> treeDto = new HashMap<>();
            Date date = new Date();
            SignPerson signPerson=new SignPerson();
            signPerson.setProjectNo(projectNo);
            signPerson.setApqpNo(apqpNo);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            String time=formatter.format(date);
            result.setMessage("当前工作流程文件的基本信息不存在，未录入需要审核的次数");
            int  autograph=signService.findExamine(apqpNo);
            if( autograph>times)
            {
                if (times == 0) {
                    /*treeDto.put("name2", sign);
                    treeDto.put("time2", time);
                    PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
                    PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);*/
                    signPerson.setExamine1(empNo);
                    signPerson.setExamineTime1(time);
                }
                else if(times==1)
                {
                    /*treeDto.put("name3", sign);
                    treeDto.put("time3", time);
                    PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
                    PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);*/
                    signPerson.setExamine2(empNo);
                    signPerson.setExamineTime2(time);
                }
                else if(times==2)
                {
                    /*treeDto.put("name4", sign);
                    treeDto.put("time4", time);
                    PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
                    PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);*/
                    signPerson.setExamine3(empNo);
                    signPerson.setExamineTime3(time);
                }
                times++;
                Sign  sign1=new Sign();
                sign1.setApqpNo(apqpNo);
                sign1.setExamined(times);
                sign1.setProjectNo(projectNo);
                result.setMessage("更新审核记录失败");
                signService.updateExamined(sign1);
                result.setMessage("未发现当前流程签名记录或更新签名记录失败");
                signService.updateSignPerson(signPerson);

                SignPerson person=signService.selectSignPerson(projectNo,apqpNo);
                treeDto.put("name2",signService.findSign(person.getExamine1()));
                treeDto.put("name3",signService.findSign(person.getExamine2()));
                treeDto.put("name4",signService.findSign(person.getExamine3()));
                treeDto.put("name5",signService.findSign(person.getJointlySign1()));
                treeDto.put("name6",signService.findSign(person.getJointlySign2()));
                treeDto.put("name7",signService.findSign(person.getJointlySign3()));
                treeDto.put("name8",signService.findSign(person.getJointlySign4()));
                treeDto.put("name9",signService.findSign(person.getJointlySign5()));
                treeDto.put("name10",signService.findSign(person.getJointlySign6()));
                treeDto.put("name11",signService.findSign(person.getApprove()));
                treeDto.put("dept2",signService.findDept(person.getExamine1()));
                treeDto.put("dept3",signService.findDept(person.getExamine2()));
                treeDto.put("dept4",signService.findDept(person.getExamine3()));
                treeDto.put("dept5",signService.findDept(person.getJointlySign1()));
                treeDto.put("dept6",signService.findDept(person.getJointlySign2()));
                treeDto.put("dept7",signService.findDept(person.getJointlySign3()));
                treeDto.put("dept8",signService.findDept(person.getJointlySign4()));
                treeDto.put("dept9",signService.findDept(person.getJointlySign5()));
                treeDto.put("dept10",signService.findDept(person.getJointlySign6()));
                treeDto.put("time2",person.getExamineTime1());
                treeDto.put("time3",person.getExamineTime2());
                treeDto.put("time4",person.getExamineTime3());
                treeDto.put("time5",person.getJointlySignTime1());
                treeDto.put("time6",person.getJointlySignTime2());
                treeDto.put("time7",person.getJointlySignTime3());
                treeDto.put("time8",person.getJointlySignTime4());
                treeDto.put("time9",person.getJointlySignTime5());
                treeDto.put("time10",person.getJointlySignTime6());
                result.setMessage("生成签名文件失败");
                PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);
                result.setMessage("审核成功！");

                result.success("审核成功！");
            }
            else
            {
                result.setSuccess(false);
                result.setMessage("审核达到上限！");
            }


        }catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            //result.setMessage("审核失败！");
        }
        return result;
    }

    @ApiOperation("会签")
    @GetMapping(value = "/jointlySign")
    public Result<?> jointlySign(HttpServletRequest request,
                                 @RequestParam(name = "projectNo") String projectNo,
                                 @RequestParam(name = "apqpNo") String apqpNo)
    {
        Result<?> result = new Result();
        try {
            String token = request.getHeader(DefConstants.X_TOKEN);
            if (token == null) {
                result.error500("token为空！");
                return result;
            }
            String username = JwtUtil.getUsername(token);
            String empNo = signService.getEmpNo(username);
            result.setMessage("未上传当前流程文件！");
            int examined=signService.findExamined(projectNo, apqpNo);
            String templatePath=signService.findFile(projectNo, apqpNo);
            result.setMessage("当前工作流程文件的基本信息不存在，未录入需要审核的次数");
            int examine=signService.findExamine(apqpNo);
            if(examined==examine)
            {
                int times=signService.findJointlySigned(projectNo, apqpNo);
                result.setMessage("未发现当前用户签名样图");
                String sign=signService.findSign(empNo);

                String newPDFPath="/mnt/minio/data/template/countersign/会签模板.pdf";
                Map<Object, Object> treeDto = new HashMap<>();
                Date date = new Date();
                SignPerson signPerson=new SignPerson();
                signPerson.setProjectNo(projectNo);
                signPerson.setApqpNo(apqpNo);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
                String time=formatter.format(date);
                result.setMessage("当前工作流程文件的基本信息不存在，未录入需要会签的次数");
                int  autograph=signService.findJointlySign(apqpNo);
                if( autograph>times)
                {
                    if(times==0)
                    {
                        /*treeDto.put("name5", sign);
                        treeDto.put("time5", time);
                        PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
                        PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);*/
                        signPerson.setJointlySign1(empNo);
                        signPerson.setJointlySignTime1(time);
                    }
                    else if(times==1)
                    {
                        /*treeDto.put("name6", sign);
                        treeDto.put("time6", time);
                        PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
                        PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);*/
                        signPerson.setJointlySign2(empNo);
                        signPerson.setJointlySignTime2(time);
                    }
                    else if(times==2)
                    {
                        /*treeDto.put("name7", sign);
                        treeDto.put("time7", time);
                        PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
                        PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);*/
                        signPerson.setJointlySign3(empNo);
                        signPerson.setJointlySignTime3(time);
                    }
                    else if(times==3)
                    {
                        /*treeDto.put("name8", sign);
                        treeDto.put("time8", time);
                        PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
                        PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);*/
                        signPerson.setJointlySign4(empNo);
                        signPerson.setJointlySignTime4(time);
                    }
                    else if(times==4)
                    {
                        /*treeDto.put("name9", sign);
                        treeDto.put("time9", time);
                        PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
                        PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);*/
                        signPerson.setJointlySign5(empNo);
                        signPerson.setJointlySignTime5(time);
                    }
                    else if(times==5)
                    {
                        /*treeDto.put("name10", sign);
                        treeDto.put("time10", time);
                        PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
                        PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);*/
                        signPerson.setJointlySign6(empNo);
                        signPerson.setJointlySignTime6(time);
                    }
                    times++;
                    Sign  sign1=new Sign();
                    sign1.setApqpNo(apqpNo);
                    sign1.setJointlySigned(times);
                    sign1.setProjectNo(projectNo);
                    result.setMessage("更新审核记录失败");
                    signService.updateJointlySigned(sign1);
                    if( autograph==times)
                    {
                        sign1.setRate(80);
                        signService.updateRate(sign1);
                    }
                    result.setMessage("未发现当前流程签名记录或更新签名记录失败");
                    signService.updateSignPerson(signPerson);

                    SignPerson person=signService.selectSignPerson(projectNo,apqpNo);
                    treeDto.put("name2",signService.findSign(person.getExamine1()));
                    treeDto.put("name3",signService.findSign(person.getExamine2()));
                    treeDto.put("name4",signService.findSign(person.getExamine3()));
                    treeDto.put("name5",signService.findSign(person.getJointlySign1()));
                    treeDto.put("name6",signService.findSign(person.getJointlySign2()));
                    treeDto.put("name7",signService.findSign(person.getJointlySign3()));
                    treeDto.put("name8",signService.findSign(person.getJointlySign4()));
                    treeDto.put("name9",signService.findSign(person.getJointlySign5()));
                    treeDto.put("name10",signService.findSign(person.getJointlySign6()));
                    treeDto.put("name11",signService.findSign(person.getApprove()));
                    treeDto.put("dept2",signService.findDept(person.getExamine1()));
                    treeDto.put("dept3",signService.findDept(person.getExamine2()));
                    treeDto.put("dept4",signService.findDept(person.getExamine3()));
                    treeDto.put("dept5",signService.findDept(person.getJointlySign1()));
                    treeDto.put("dept6",signService.findDept(person.getJointlySign2()));
                    treeDto.put("dept7",signService.findDept(person.getJointlySign3()));
                    treeDto.put("dept8",signService.findDept(person.getJointlySign4()));
                    treeDto.put("dept9",signService.findDept(person.getJointlySign5()));
                    treeDto.put("dept10",signService.findDept(person.getJointlySign6()));
                    treeDto.put("time2",person.getExamineTime1());
                    treeDto.put("time3",person.getExamineTime2());
                    treeDto.put("time4",person.getExamineTime3());
                    treeDto.put("time5",person.getJointlySignTime1());
                    treeDto.put("time6",person.getJointlySignTime2());
                    treeDto.put("time7",person.getJointlySignTime3());
                    treeDto.put("time8",person.getJointlySignTime4());
                    treeDto.put("time9",person.getJointlySignTime5());
                    treeDto.put("time10",person.getJointlySignTime6());
                    result.setMessage("生成签名文件失败");
                    PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);
                    result.setMessage("会签成功！");

                    result.success("会签成功！");
                }
                else
                {
                    result.setSuccess(false);
                    result.setMessage("会签达到上限！");
                }
            }
            else
            {
                result.setSuccess(false);
                result.setMessage("审核未全部完成！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            //result.setMessage("会签失败！");
        }
        return result;
    }

    @ApiOperation("批准")
    @GetMapping(value = "/approval")
    public Result<?> approval(HttpServletRequest request,
                                 @RequestParam(name = "projectNo") String projectNo,
                                 @RequestParam(name = "apqpNo") String apqpNo) {
        Result<?> result = new Result();
        try
        {
            String token = request.getHeader(DefConstants.X_TOKEN);
            if (token == null) {
                result.error500("token为空！");
                return result;
            }
            String username = JwtUtil.getUsername(token);
            String empNo = signService.getEmpNo(username);
            result.setMessage("当前工作流程文件的基本信息不存在，未录入需要会签的次数");
            int autograph=signService.findJointlySign(apqpNo);
            result.setMessage("未上传当前流程文件！");
            int times=signService.findJointlySigned(projectNo,apqpNo);
            String templatePath=signService.findFile(projectNo,apqpNo);
            result.setMessage("未发现当前用户签名样图");
            String sign=signService.findSign(empNo);
            if(times==autograph)
            {

                String newPDFPath="/mnt/minio/data/template/countersign/会签模板.pdf";
                Sign  sign1=new Sign();
                sign1.setApqpNo(apqpNo);
                sign1.setProjectNo(projectNo);
                result.setMessage("更新批准记录失败");
                sign1.setApprovaled(1);
                signService.updateapprovaled(sign1);
                sign1.setRate(100);
                signService.updateRate(sign1);
                /*Map<Object, Object> treeDto = new HashMap<>();
                treeDto.put("name11", sign);
                PdfSignUtil.fillPdf(templatePath, newPDFPath, treeDto);
                PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);*/
                SignPerson signPerson=new SignPerson();
                signPerson.setProjectNo(projectNo);
                signPerson.setApqpNo(apqpNo);
                signPerson.setApprove(empNo);
                result.setMessage("未发现当前流程签名记录或更新签名记录失败");
                signService.updateSignPerson(signPerson);

                Map<Object, Object> treeDto = new HashMap<>();

                SignPerson person=signService.selectSignPerson(projectNo,apqpNo);
                treeDto.put("name2",signService.findSign(person.getExamine1()));
                treeDto.put("name3",signService.findSign(person.getExamine2()));
                treeDto.put("name4",signService.findSign(person.getExamine3()));
                treeDto.put("name5",signService.findSign(person.getJointlySign1()));
                treeDto.put("name6",signService.findSign(person.getJointlySign2()));
                treeDto.put("name7",signService.findSign(person.getJointlySign3()));
                treeDto.put("name8",signService.findSign(person.getJointlySign4()));
                treeDto.put("name9",signService.findSign(person.getJointlySign5()));
                treeDto.put("name10",signService.findSign(person.getJointlySign6()));
                treeDto.put("name11",signService.findSign(person.getApprove()));
                treeDto.put("dept2",signService.findDept(person.getExamine1()));
                treeDto.put("dept3",signService.findDept(person.getExamine2()));
                treeDto.put("dept4",signService.findDept(person.getExamine3()));
                treeDto.put("dept5",signService.findDept(person.getJointlySign1()));
                treeDto.put("dept6",signService.findDept(person.getJointlySign2()));
                treeDto.put("dept7",signService.findDept(person.getJointlySign3()));
                treeDto.put("dept8",signService.findDept(person.getJointlySign4()));
                treeDto.put("dept9",signService.findDept(person.getJointlySign5()));
                treeDto.put("dept10",signService.findDept(person.getJointlySign6()));
                treeDto.put("time2",person.getExamineTime1());
                treeDto.put("time3",person.getExamineTime2());
                treeDto.put("time4",person.getExamineTime3());
                treeDto.put("time5",person.getJointlySignTime1());
                treeDto.put("time6",person.getJointlySignTime2());
                treeDto.put("time7",person.getJointlySignTime3());
                treeDto.put("time8",person.getJointlySignTime4());
                treeDto.put("time9",person.getJointlySignTime5());
                treeDto.put("time10",person.getJointlySignTime6());
                result.setMessage("生成签名文件失败");
                PdfSignUtil.fillPdf(newPDFPath, templatePath, treeDto);
                result.setMessage("项目完成状态更新失败");
                ApqpStateVO apqpStateVO=new ApqpStateVO();
                apqpStateVO.setApqpNo(apqpNo);
                apqpStateVO.setProjectNo(projectNo);
                apqpStateVO.setState(1);
                signService.updateState(apqpStateVO);
                Project project;
                project=signService.getProjrct(projectNo);
                project.setCompleteTask(project.getCompleteTask()+1);
                if(project.getCompleteTask()==project.getProjectTask())
                {
                    project.setProCompleteState(1);
                    project.setProjectState(2);
                }
                signService.updateProject(project);
                result.setMessage("批准成功！");

                result.success("批准成功！");
            }
            else
            {
                result.setSuccess(false);
                result.setMessage("会签未全部完成！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            //result.setMessage("批准失败！");
        }

        return result;
    }

    @ApiOperation("驳回")
    @GetMapping(value = "/reject")
    public Result<?> reject(HttpServletRequest request,
                              @RequestParam(name = "projectNo") String projectNo,
                              @RequestParam(name = "apqpNo") String apqpNo,
                              @RequestParam(name = "rejectReason") String rejectReason) {
        Result<?> result = new Result();
        try {
            result.setMessage("未上传当前流程文件，无需驳回！");
            String address1=signService.getFile(projectNo,apqpNo);
            String address2=signService.findFile(projectNo,apqpNo);
            //PdfSignUtil.deleteFile(address1);//删除已经上传的文件
            //PdfSignUtil.deleteFile(address2);

            result.setMessage("不存在当前流程工作文件记录或文件签名记录！");
            //signService.deleteFile(projectNo,apqpNo);
            //signService.deleteSign(projectNo,apqpNo);
            Date date=new Date();
            SignPerson signPerson=new SignPerson();
            signPerson.setProjectNo(projectNo);
            signPerson.setApqpNo(apqpNo);
            signPerson.setReject(1);
            signPerson.setRejectReason(rejectReason);
            signPerson.setRejectTime(date);
            Sign sign=new Sign();
            sign.setProjectNo(projectNo);
            sign.setApqpNo(apqpNo);
            sign.setReject(1);
            signService.rejectSignPerson(signPerson);
            signService.rejectFile(sign);
            ApqpStateVO apqpStateVO=new ApqpStateVO();
            apqpStateVO.setApqpNo(apqpNo);
            apqpStateVO.setProjectNo(projectNo);
            apqpStateVO.setFileState(0);
            signService.updateFileState(apqpStateVO);
            result.success("驳回成功！");

        }catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            //result.setMessage("驳回失败！");
        }
        return result;
    }
    @ApiOperation("查看驳回原因")
    @GetMapping(value = "/rejectReason")
    public Result<List<RejectVO>> showExamine(@RequestParam(name = "projectNo") String projectNo,
                                              @RequestParam(name = "apqpNo") String apqpNo) {
        Result<List<RejectVO>> result = new Result<>();
        List<RejectVO> rejectVOList=signService.getRejectInformation(projectNo, apqpNo);
        result.setResult(rejectVOList);
        return result;
    }

}
