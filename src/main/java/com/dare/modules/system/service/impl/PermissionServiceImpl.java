package com.dare.modules.system.service.impl;


import com.dare.modules.system.mapper.PermissionMapper;
import com.dare.modules.system.service.PermissionService;
import com.dare.modules.system.vo.RolePermissionVO;
import com.dare.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: shengyao
 * @Package: com.lbj.service.impl
 * @Date: 2020/11/13 20:57
 * @Description:
 * @version: 1.0
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<String> getUserPermissionsSet(String username) {
        Set<String> permissionSet = new HashSet<>();
        List<RolePermissionVO> permissionList = permissionMapper.getPermissionByUsername(username);
        for (RolePermissionVO per : permissionList
             ) {
            if ((ConvertUtil.isNotEmpty(per.getPermissionName()))){
                permissionSet.add(per.getPermissionName());
            }
            
        }

        return permissionSet;
    }
}
