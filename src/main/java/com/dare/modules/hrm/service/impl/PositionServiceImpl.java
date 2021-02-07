package com.dare.modules.hrm.service.impl;

import com.dare.modules.hrm.mapper.PositionMapper;
import com.dare.modules.hrm.entity.Position;
import com.dare.modules.hrm.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<Position> ShowPosition(Integer deptId) {
        return positionMapper.SelectPosition(deptId);
    }

    @Override
    public boolean EditPosition(int positionId, String positionName, String description, String time, int deptId) {
        positionMapper.UpdatePosition( positionId,  positionName, description, time,deptId);
        return true;
    }
}
