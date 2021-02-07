package com.dare.modules.sign.service;

import com.dare.modules.project.entity.Project;
import com.dare.modules.sign.entity.Sign;
import com.dare.modules.sign.entity.SignPerson;
import com.dare.modules.sign.vo.ApqpStateVO;
import com.dare.modules.sign.vo.RejectVO;
import com.dare.modules.sign.vo.SignPersonDetailVO;

import java.util.List;

public interface SignService {
    String getEmpNo(String username);
    String findSign(String emp_no);
    String findDept(String emp_no);
    int findJointlySigned(String project,String apqpNo);
    int findJointlySign(String apqpNo);
    String findFile(String project,String apqpNo);
    String getFile(String project,String apqpNo);
    void updateJointlySigned(Sign sign);
    void updateapprovaled(Sign sign);
    void updateExamined(Sign sign);
    void updateSignPerson(SignPerson signPerson);
    void updateSignFile(Sign sign);
    void insertSignPerson(SignPerson signPerson);
    int findExamined(String project, String apqpNo);
    int findExamine(String apqpNo);
    void updateRate(Sign sign);
    void deleteFile(String project, String apqpNo);
    void deleteSign(String project, String apqpNo);
    void updateState(ApqpStateVO apqpStateVO);
    void updateFileState(ApqpStateVO apqpStateVO);
    void rejectSignPerson(SignPerson signPerson);
    void rejectFile(Sign sign);
    Project getProjrct(String projectNo);
    void updateProject(Project project);
    List<RejectVO> getRejectInformation(String projectNo, String apqpNo);

    List<SignPersonDetailVO> getSignListBeforeStep(String projectNo, String apqpNo);

    SignPerson selectSignPerson(String projectNo, String apqpNo);
}
