package com.dare.modules.system.service.impl;


import com.dare.modules.system.mapper.RoleMapper;
import com.dare.modules.system.service.RoleService;
import com.dare.modules.system.vo.UserRoleVO;
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
 * @Date: 2020/11/13 20:55
 * @Description:
 * @version: 1.0
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Set<String> getUserRolesSet(String username) {
        Set<String> roleSet = new HashSet<>();
        List<UserRoleVO> roleList = roleMapper.getRoleByUsername(username);
        for (UserRoleVO role : roleList
        ) {
            if (ConvertUtil.isNotEmpty(role.getRoleName())) {
                roleSet.add(role.getRoleName());
            }
        }
        return roleSet;
    }
}
