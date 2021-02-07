package com.dare.modules.sign.mapper;


import com.dare.modules.project.entity.Project;
import com.dare.modules.sign.entity.Sign;
import com.dare.modules.sign.entity.SignPerson;
import com.dare.modules.sign.vo.ApqpStateVO;
import com.dare.modules.sign.vo.RejectVO;
import com.dare.modules.sign.vo.SignPersonDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "signMapper")
public interface  SignMapper {
    String getEmpNo(String username);
    String findSign(String emp_no);
    String findDept(String emp_no);
    int findJointlySigned(String project, String apqpNo);
    int findExamined(String project, String apqpNo);
    String findFile(String project, String apqpNo);
    String getFile(String project, String apqpNo);
    int findJointlySign(String apqpNo);
    int findExamine(String apqpNo);
    void updateJointlySigned(Sign sign);
    void updateExamined(Sign sign);
    void updateapprovaled(Sign sign);
    void updateSignFile(Sign sign);
    void insertSignPerson(SignPerson signPerson);
    void updateSignPerson(SignPerson signPerson);
    void rejectSignPerson(SignPerson signPerson);
    void rejectFile(Sign sign);
    void updateRate(Sign sign);
    void deleteFile(String project, String apqpNo);
    void deleteSign(String project, String apqpNo);
    SignPerson selectSignPerson(String project, String apqpNo);
    void updateState(ApqpStateVO apqpStateVO);
    void updateFileState(ApqpStateVO apqpStateVO);
    Project getProjrct(String projectNo);
    void updateProject(Project project);
    List<RejectVO> getRejectInformation(String projectNo, String apqpNo);


    List<SignPersonDetailVO> getSignListBeforeStep(String projectNo, String apqpNo);

    void saveList(@Param("signs") List<SignPersonDetailVO> signPersonDetailVOList);

    void deleteSignPerson(String projectNo, String apqpNo);
}
