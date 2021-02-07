package com.dare.modules.system.controller;

import com.dare.common.constant.CommonConstant;
import com.dare.common.vo.Result;
import com.dare.modules.shiro.vo.DefConstants;
import com.dare.modules.system.entity.User;
import com.dare.modules.system.service.PermissionService;
import com.dare.modules.system.service.RoleService;
import com.dare.modules.system.service.UserService;
import com.dare.modules.system.vo.UserInfoVO;
import com.dare.modules.system.vo.UserLoginVO;
import com.dare.utils.ConvertUtil;
import com.dare.utils.JwtUtil;
import com.dare.utils.PasswordUtil;
import com.dare.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: shengyao
 * @Package: com.lbj.controller
 * @Date: 2020/11/9 15:18
 * @Description: 用户控制类
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/sys")
@Api(tags = "登录信息")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("用户注册")
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public String register(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password) {

        User user = new User();
        user.setUsername(username);
        String salt = ConvertUtil.randomGen(8);
        user.setSalt(salt);
        String passwordEncode = PasswordUtil.encrypt(username, password, salt);
        user.setPassword(passwordEncode);
        userService.save(user);

        return "success";
    }

    @ApiOperation("用户登录")
    @RequestMapping(method = RequestMethod.POST, value = "/login")
//    public Result<Map<String, String>> login(@RequestParam(value = "username") String username,
//                                             @RequestParam(value = "password") String password) {
    public Result<Map<String, String>> login(@RequestBody UserLoginVO userLoginVO) {
        String username = userLoginVO.getUsername();
        String password = userLoginVO.getPassword();
        Result<Map<String, String>> result = new Result<>();
        Map<String, String> map = new HashMap<>();
        User user = userService.getUserByName(username);
        if (user == null) {
            result.setResult(null);
            result.setSuccess(false);
            result.setMessage("用户不存在！");
            return result;
        }
        String userPassword = PasswordUtil.encrypt(username, password, user.getSalt());
        String password1 = user.getPassword();
        System.out.println("userPassword: " + userPassword);
        System.out.println("password1: " + password1);
        if (!password1.equals(userPassword)) {
            result.setResult(null);
            result.setSuccess(false);
            result.setMessage("密码错误！");
            return result;
        }
        String token = userToken(user);
        map.put("token", token);
        result.setResult(map);
        result.success("登录成功！");
        return result;
    }

    @ApiOperation("用户注销")
    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    public Result<?> logout(HttpServletRequest request) {
        String token = request.getHeader(DefConstants.X_TOKEN);
        Result result = new Result();
        if (ConvertUtil.isEmpty(token)) {
//            Map map = new HashMap();
//            map.put()
            result.error500("退出登录失败！");
            return result;
        }
        String username = JwtUtil.getUsername(token);
        User user = userService.getUserByName(username);
        if (user != null) {
            //清空用户登录Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户登录Shiro权限缓存
//            redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + user.getId());

            SecurityUtils.getSubject().logout();
            System.out.println("user: " + user);
            result.success("退出成功！");
            return result;
        } else {
            result.error500("token无效！");
            return result;
        }
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/userInfo")
    public Result<UserInfoVO> userInfo(HttpServletRequest request) {
        Result<UserInfoVO> result = new Result<>();
        String token = request.getHeader(DefConstants.X_TOKEN);
        String username = JwtUtil.getUsername(token);
        UserInfoVO userInfoVO = userService.getUserInfoByUserName(username);
        result.setResult(userInfoVO);
        result.success("查询成功");

        return result;
    }
    @ApiOperation("修改密码信息")
    @GetMapping("/updatePassword")
    public Result<?> updatePassword(HttpServletRequest request,@RequestParam(name = "password") String passwordNew)
    {
        Result<?> result = new Result();
        try {
            String token = request.getHeader(DefConstants.X_TOKEN);
            if (token == null) {
                result.error500("token为空！");
                return result;
            }
            String username = JwtUtil.getUsername(token);
            User user=new User();
            String salt = ConvertUtil.randomGen(8);
            user.setSalt(salt);
            String passwordEncode = PasswordUtil.encrypt(username, passwordNew, salt);
            user.setPassword(passwordEncode);
            user.setUsername(username);
            userService.updatePassword(user);
            result.success("修改密码成功！");
        }catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("修改密码失败！");
        }
        return result;
    }


    private String userToken(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        // 生成token
        String token = JwtUtil.sign(username, password);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        System.out.println(redisUtil.getExpire(CommonConstant.PREFIX_USER_TOKEN + token) + "s");
        System.out.println("token: " + token);
        return token;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/role")
    public String roles(@RequestParam("username") String username) {
        Set<String> roleSet = roleService.getUserRolesSet(username);
        System.out.println(roleSet);
        return "roleSet";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/permission")
    public String permissions(@RequestParam("username") String username) {
        Set<String> permissionSet = permissionService.getUserPermissionsSet(username);
        System.out.println(permissionSet);
        return "permissionSet";
    }


}
