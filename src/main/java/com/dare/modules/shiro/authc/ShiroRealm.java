package com.dare.modules.shiro.authc;

import com.dare.common.constant.CommonConstant;
import com.dare.modules.system.entity.User;
import com.dare.modules.system.service.PermissionService;
import com.dare.modules.system.service.RoleService;
import com.dare.modules.system.service.UserService;
import com.dare.utils.ConvertUtil;
import com.dare.utils.JwtUtil;
import com.dare.utils.RedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author: shengyao
 * @Package: com.dare.modules.shiro.authc
 * @Date: 2020/11/10 15:48
 * @Description:
 * @version: 1.0
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Autowired
    @Lazy
    private PermissionService permissionService;

    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        System.out.println("----------身份认证开始----------");
        String token = (String) auth.getCredentials();
        System.out.println("token: " + token);
        if (token == null) {
            System.out.println("————————身份认证失败——————————token为空!");
            throw new AuthenticationException("token为空!");
        }
        // 校验token有效性
        User user = this.checkUserTokenIsEffect(token);
        return new SimpleAuthenticationInfo(user, token, getName());
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("===============Shiro权限认证开始============ [ roles、permissions]==========");
        String username = null;
//        User user = new User();
        if (principals != null) {
            User user = (User) principals.getPrimaryPrincipal();
            username = user.getUsername();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 设置用户拥有的角色集合，比如“admin,test”
        Set<String> roleSet = roleService.getUserRolesSet(username);
        info.setRoles(roleSet);
        System.out.println(info.getRoles());

        // 设置用户拥有的权限集合，比如“sys:role:add,sys:user:add”
        Set<String> permissionSet = permissionService.getUserPermissionsSet(username);
        info.addStringPermissions(permissionSet);
        System.out.println(info.getStringPermissions());
        System.out.println("===============Shiro权限认证成功==============");
        return info;
    }

    /**
     * @Author: shengyao
     * @Description: TODO
     * @Date: 2020/11/10 16:07
     * @param token
     * @Return com.dare.modules.system.entity.User
     */
    public User checkUserTokenIsEffect(String token) {
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token非法无效!");
        }
        // 查询用户信息
        System.out.println("———校验token是否有效————checkUserTokenIsEffect——————— " + token);
        User user = userService.getUserByName(username);
        System.out.println(user);
//        System.out.println(user.getUsername());
        if (user == null) {
            throw new AuthenticationException("用户不存在!");
        }
        // 判断用户状态
//        if (user.getStatus() != 1) {
//            throw new AuthenticationException("账号已被锁定,请联系管理员!");
//        }
        // 校验token是否超时失效 & 或者账号密码是否错误
        if (!jwtTokenRefresh(token, username, user.getPassword())) {
            throw new AuthenticationException("Token失效，请重新登录!");
        }

        return user;
    }


    /**
     * @Author: shengyao
     * @Description: TODO
     * @Date: 2020/11/10 16:08
     * @param token
     * @param username
     * @param password
     * @Return boolean
     */
    private boolean jwtTokenRefresh(String token, String username, String password) {

        String cacheToken = String.valueOf(redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + token));
        if (ConvertUtil.isNotEmpty(cacheToken)) {
            // 校验token有效性
            if (!JwtUtil.verify(cacheToken, username, password)) {
                String newAuthorization = JwtUtil.sign(username, password);
                // 设置超时时间
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, newAuthorization);
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
                System.out.println("——————————用户在线操作，更新token保证不掉线—————————jwtTokenRefresh——————— " + token);
            }
            return true;
        }
        return false;
    }

    /**
     * 清除当前用户的权限认证缓存
     *
     * @param principals 权限信息
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
}
