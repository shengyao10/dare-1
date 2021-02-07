package com.dare.modules.shiro.authc;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: shengyao
 * @Package: com.lbj.shiro.authc
 * @Date: 2020/11/8 16:52
 * @Description:
 * @version: 1.0
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
