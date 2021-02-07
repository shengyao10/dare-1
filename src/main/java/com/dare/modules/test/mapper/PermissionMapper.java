package com.dare.modules.test.mapper;
import com.dare.modules.test.entity.PermissionTest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "PermissionMapperTest")
public interface PermissionMapper {
    void updateExamine(PermissionTest permission);
    void updateJointlySign(PermissionTest permission);
    List<PermissionTest> SelectAllExamine();
    List<PermissionTest> SelectAllJointlySign();
}
