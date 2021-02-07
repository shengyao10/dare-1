package com.dare.modules.system.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: shengyao
 * @Package: com.lbj.controller
 * @Date: 2020/11/9 21:37
 * @Description:
 * @version: 1.0
 */
@RestController
@RequestMapping("/sys")
public class HelloWorldController {


    @RequestMapping("/hello")
    @RequiresRoles("user")
//    @RequiresPermissions({"sys:add"})
    public String hello() {

//        Subject subject = SecurityUtils.getSubject();
//        if (subject.isPermitted("sys:add")) {
//            return "hello world!";
//        } else {
//            return "无权限";
//        }
        return "hello world!";

    }
}
