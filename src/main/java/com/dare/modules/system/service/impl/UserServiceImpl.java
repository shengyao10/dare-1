package com.dare.modules.system.service.impl;


import com.dare.modules.system.entity.User;
import com.dare.modules.system.mapper.UserMapper;
import com.dare.modules.system.service.UserService;
import com.dare.modules.system.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: shengyao
 * @Package: com.lbj.service.impl
 * @Date: 2020/11/9 16:12
 * @Description:
 * @version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String username) {
        User user = userMapper.getUserByName(username);
        return user;
    }

    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public UserInfoVO getUserInfoByUserName(String username) {
        UserInfoVO userInfoVO = userMapper.getUserInfoByUserName(username);
        return userInfoVO;
    }

    @Override
    public String getEmpNameByUsername(String username) {
        String empName = userMapper.getEmpNameByUsername(username);
        return empName;
    }
    @Override
    public void updatePassword(User user)
    {
        userMapper.updatePassword(user);
    }

//    /**
//     * 通过用户名获取用户角色集合
//     * @param username 用户名
//     * @return 角色集合
//     */
//    @Override
//    public Set<String> getUserRolesSet(String username) {
//        // 查询用户拥有的角色集合
//        List<String> roles = roleMapper.getRoleByUsername(username);
//        System.out.println("-------通过数据库读取用户拥有的角色Rules------username： " + username + ",Roles size: " + (roles == null ? 0 : roles.size()));
//        return new HashSet<>(roles);
//    }
//
//
//    /**
//     * 通过用户名获取用户权限集合
//     *
//     * @param username 用户名
//     * @return 权限集合
//     */
//    @Override
//    public Set<String> getUserPermissionsSet(String username) {
//        Set<String> permissionSet = new HashSet<>();
//        List<SysPermission> permissionList = sysPermissionMapper.queryByUser(username);
//        for (SysPermission po : permissionList) {
//            if (oConvertUtils.isNotEmpty(po.getPerms())) {
//                permissionSet.add(po.getPerms());
//            }
//        }
//        log.info("-------通过数据库读取用户拥有的权限Perms------username： "+ username+",Perms size: "+ (permissionSet==null?0:permissionSet.size()) );
//        return permissionSet;
//    }
}
