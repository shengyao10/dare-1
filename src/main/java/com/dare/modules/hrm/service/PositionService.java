package com.dare.modules.hrm.service;

import com.dare.modules.hrm.entity.Position;

import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.deer.modules.hrm.service
 * @Date: 2020/8/4 9:43
 * @Description:
 * @version: 1.0
 */
public interface PositionService {
    List<Position> ShowPosition(Integer deptId);
    boolean EditPosition(int positionId, String positionName, String description, String time,int deptId);
}
