package com.dare.modules.hrm.mapper;


import com.dare.modules.hrm.entity.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "positionMapper")
public interface PositionMapper {
//    @Modifying
//    @Update(value =" update hrm_position set position_name=#{positionName},description=#{description},gmt_modified=#{time},dept_id=#{deptId} where position_id=#{positionId}")
    void UpdatePosition(int positionId, String positionName, String description, String time, int deptId);
//    @Select(value="select * from hrm_position where dept_id=#{deptId}")
    List<Position> SelectPosition(Integer deptId);

}
