package com.dare.modules.apqp.service.impl;

import com.dare.modules.apqp.mapper.APQPMapper;
import com.dare.modules.apqp.service.APQPService;
import com.dare.modules.apqp.vo.APQPStepVO;
import com.dare.modules.project.vo.ProjectDetailVO;
import com.dare.modules.project.vo.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.apqp.service.impl
 * @Date: 2020/11/19 21:10
 * @Description:
 * @version: 1.0
 */
@Service
@Transactional
public class APQPServiceImpl implements APQPService {

    @Autowired
    private APQPMapper apqpMapper;

    @Override
    public List<APQPStepVO> getStep0() {
        List<APQPStepVO> step = apqpMapper.getStep0();
//        for (APQPStep0VO a : step
//             ) {
//            a.setId(a.getId().replace("0","-1").replace("9","0"));
//            a.setPid(a.getPid().replace("0","-1").replace("9","0"));
//        }

        List<APQPStepVO> tree = new ArrayList<>();
        for (APQPStepVO e : step
        ) {
            tree.add(new APQPStepVO(e.getId(), e.getPid(), e.getLabel()));
        }
        List<APQPStepVO> list = APQPStepVO.getTreeResult(tree, "0");
        return list;

    }

    @Override
    public List<APQPStepVO> getAllStep() {
        List<APQPStepVO> step = apqpMapper.getAllStep();
        List<APQPStepVO> tree = new ArrayList<>();
        for (APQPStepVO e : step
        ) {
            tree.add(new APQPStepVO(e.getId(), e.getPid(), e.getLabel()));
        }
        List<APQPStepVO> list = APQPStepVO.getTreeResult(tree, "0");
        return list;
    }

    @Override
    public List<APQPStepVO> test() {
        List<APQPStepVO> step = apqpMapper.test();
        for (APQPStepVO a : step
        ) {

        }
        List<APQPStepVO> tree = new ArrayList<>();
        for (APQPStepVO e : step
        ) {
            tree.add(new APQPStepVO(e.getId(), e.getPid(), e.getLabel()));
        }
        List<APQPStepVO> list = APQPStepVO.getTreeResult(tree, "0");
        return list;
    }

    @Override
    public void saveProjectAPQPStep(List<Map<String, Object>> mapList) {
        apqpMapper.saveProjectAPQPStep(mapList);
    }

    @Override
    public List<ProjectDetailVO> queryProjectDetail(String projectNo) {
//        List<ProjectDetailVO> list = new LinkedList<>();
        List<ProjectDetailVO> list = apqpMapper.queryProjectDetail(projectNo);
        List<ProjectDetailVO> list2 = apqpMapper.queryProjectParentDetail(projectNo);
        list.addAll(list2);

        return list;
    }

    @Override
    public List<ScheduleVO> getScheduleBeforeStep(String projectNo, String apqpNo) {
        List<ScheduleVO> scheduleVOList = apqpMapper.getScheduleBeforeStep(projectNo, apqpNo);
        return scheduleVOList;
    }

    @Override
    public Integer findSchedule(String projectNo, String apqpNo) {
        Integer scheduleExist = apqpMapper.findSchedule(projectNo, apqpNo);
        return scheduleExist;
    }

}
