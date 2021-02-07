package com.dare.modules.apqp.mapper;

import com.dare.modules.apqp.vo.APQPStepVO;
import com.dare.modules.project.vo.ProjectDetailVO;
import com.dare.modules.project.vo.ScheduleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.apqp.mapper
 * @Date: 2020/11/19 21:12
 * @Description:
 * @version: 1.0
 */
@Mapper
@Component(value = "apqpMapper")
public interface APQPMapper {
    List<APQPStepVO> getStep0();

    List<APQPStepVO> getAllStep();

    List<APQPStepVO> test();

    void saveProAPQPStepDetail(List list, String projectNo);

    void saveProjectAPQPStep(List<Map<String, Object>> mapList);

    List<ProjectDetailVO> queryProjectDetail(String projectNo);

    List<ProjectDetailVO> queryProjectParentDetail(String projectNo);

    List<ScheduleVO> getSchedule(String projectNo, String apqpNo, Integer id);

    Integer getScheduleId(String projectNo, String apqpNo);

    List<ProjectDetailVO> visualization(String projectNo);

    List<ScheduleVO> getScheduleBeforeStep(String projectNo, String apqpNo);

    void saveList(@Param("schedules") List<ScheduleVO> scheduleVOList);

    Integer findSchedule(String projectNo, String apqpNo);
}
