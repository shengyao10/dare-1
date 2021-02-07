package com.dare.modules.system.mapper;


import com.dare.modules.system.vo.RolePermissionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: shengyao
 * @Package: com.lbj.mapper
 * @Date: 2020/11/13 21:28
 * @Description:
 * @version: 1.0
 */
@Mapper
@Component(value = "permissionMapper")
public interface PermissionMapper {
    List<RolePermissionVO> getPermissionByUsername(String username);
}
