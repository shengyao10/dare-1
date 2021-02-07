package com.dare.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dare.common.exception.DareException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author: shengyao
 * @Package: com.lbj.utils
 * @Date: 2020/11/9 10:17
 * @Description:
 * @version: 1.0
 */
public class JwtUtil {

    // Token过期时间30分钟（用户登录过期时间是此时间的两倍，以token在reids缓存时间为准）
    public static final long EXPIRE_TIME = 30 * 60 * 1000;
//    public static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }


    /**
     * 生成签名,5min后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);

    }

    /**
     * 根据request中的token获取用户账号
     *
     * @param request
     * @return
     */
    public static String getUserNameByToken(HttpServletRequest request) throws DareException {
        String accessToken = request.getHeader("X-TOKEN");
        String username = getUsername(accessToken);
        if (ConvertUtil.isEmpty(username)) {
            throw new DareException("未获取到用户");
        }
        return username;
    }



    public static void main(String[] args) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDIzMzU3NDYsInVzZXJuYW1lIjoicm9vdCJ9.FTE2r8A4JrYPVFD-nMZFg7NqXH1qlVBmYBcD4uQxEbc";
        System.out.println(JwtUtil.getUsername(token));

    }
}
