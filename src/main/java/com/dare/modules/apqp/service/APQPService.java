package com.dare.modules.apqp.service;

import com.dare.modules.apqp.vo.APQPStepVO;
import com.dare.modules.project.vo.ProjectDetailVO;
import com.dare.modules.project.vo.ScheduleVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.apqp.service
 * @Date: 2020/11/19 20:49
 * @Description:
 * @version: 1.0
 */
public interface APQPService {
    List<APQPStepVO> getStep0();

    List<APQPStepVO> getAllStep();

    List<APQPStepVO> test();

    void saveProjectAPQPStep(List<Map<String, Object>> mapList);

    List<ProjectDetailVO> queryProjectDetail(String projectNo);

    List<ScheduleVO> getScheduleBeforeStep(String projectNo, String apqpNo);

    Integer findSchedule(String projectNo, String apqpNo);
}
