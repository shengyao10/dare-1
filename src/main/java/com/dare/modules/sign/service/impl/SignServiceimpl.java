package com.dare.modules.sign.service.impl;


import com.dare.modules.project.entity.Project;
import com.dare.modules.sign.entity.Sign;
import com.dare.modules.sign.entity.SignPerson;
import com.dare.modules.sign.mapper.SignMapper;
import com.dare.modules.sign.service.SignService;
import com.dare.modules.sign.vo.ApqpStateVO;
import com.dare.modules.sign.vo.RejectVO;
import com.dare.modules.sign.vo.SignPersonDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SignServiceimpl implements SignService {
    @Autowired
    private SignMapper signMapper;

    @Override
    public String getEmpNo(String username) {
        String empNo = signMapper.getEmpNo(username);
        return empNo;
    }

    public String findSign(String emp_no) {
        String sign = signMapper.findSign(emp_no);

        return sign;
    }

    public String findDept(String emp_no) {
        String dept = signMapper.findDept(emp_no);
        return dept;
    }

    public void updateJointlySigned(Sign sign) {
        signMapper.updateJointlySigned(sign);
    }

    public String findFile(String project, String apqpNo) {
        String file_path = signMapper.findFile(project, apqpNo);
        return file_path;
    }

    public String getFile(String project, String apqpNo) {
        String file_path = signMapper.getFile(project, apqpNo);
        return file_path;
    }

    public int findJointlySigned(String project, String apqpNo) {
        int jointly_signed = signMapper.findJointlySigned(project, apqpNo);
        return jointly_signed;
    }

    public int findJointlySign(String apqpNo) {
        int jointly_sign = signMapper.findJointlySign(apqpNo);
        return jointly_sign;
    }

    public int findExamined(String project, String apqpNo) {
        int examined = signMapper.findExamined(project, apqpNo);
        return examined;
    }

    public int findExamine(String apqpNo) {
        int examine = signMapper.findExamine(apqpNo);
        return examine;
    }

    public void updateSignPerson(SignPerson signPerson) {
        signMapper.updateSignPerson(signPerson);
    }

    public void insertSignPerson(SignPerson signPerson) {
        signMapper.insertSignPerson(signPerson);
    }
    public void rejectSignPerson(SignPerson signPerson){signMapper.rejectSignPerson(signPerson);}
    public void rejectFile(Sign sign){signMapper.rejectFile(sign);}

    public void updateapprovaled(Sign sign) {
        signMapper.updateapprovaled(sign);
    }

    public void updateExamined(Sign sign) {
        signMapper.updateExamined(sign);
    }

    public void updateRate(Sign sign) {
        signMapper.updateRate(sign);
    }

    public void updateSignFile(Sign sign) {
        signMapper.updateSignFile(sign);
    }

    public void deleteFile(String project, String apqpNo) {
        signMapper.deleteFile(project, apqpNo);
    }

    public void deleteSign(String project, String apqpNo) {
        signMapper.deleteSign(project, apqpNo);
    }
    public void updateState(ApqpStateVO apqpStateVO) {
        signMapper.updateState(apqpStateVO);
    }
    public void updateFileState(ApqpStateVO apqpStateVO)
    {
        signMapper.updateFileState(apqpStateVO);
    }
    public Project getProjrct(String projectNo){
        Project project=new Project();
        project=signMapper.getProjrct(projectNo);
        return project;
    }
    public void updateProject(Project project){signMapper.updateProject(project);}
    public List<RejectVO> getRejectInformation(String projectNo, String apqpNo)
    {
        List<RejectVO> list=signMapper.getRejectInformation(projectNo, apqpNo);
        return list;
    }

    @Override
    public List<SignPersonDetailVO> getSignListBeforeStep(String projectNo, String apqpNo) {
        List<SignPersonDetailVO> list = signMapper.getSignListBeforeStep(projectNo, apqpNo);
        return list;
    }

    @Override
    public SignPerson selectSignPerson(String projectNo, String apqpNo) {
        SignPerson person=signMapper.selectSignPerson(projectNo,apqpNo);
        return  person;
    }
}
