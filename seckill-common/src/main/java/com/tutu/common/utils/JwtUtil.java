package com.tutu.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * TOKEN 处理
 *
 * @Author
 * @Date 2021/12/30 16:33
 */
@Component
public class JwtUtil {
    /**
     * JWT 密匙
     */
    private static String secret = "abcdefg1234567";
    /**
     * JWT 超时时间
     */
    private static long expire = 1000;
    /**
     * Token 名称
     */
    private static String name = "TOKEN";

    public static void main(String[] args) {
        JwtUtil jwtConfig = new JwtUtil();
        String token = jwtConfig.createToken("17872373058");
        System.out.println(token);
        DecodedJWT decode = JWT.decode(token);
        System.out.println(decode.getExpiresAt().getTime());
        Claim claim = decode.getClaim(name);
        System.out.println(claim.asBoolean());
        System.out.println(jwtConfig.getToken(token));
        System.out.println(jwtConfig.getTokenExpiresAt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxNzg3MjM3MzA1OCIsImV4cCI6MTY0MDkzNDMzNiwiVE9LRU4iOnRydWV9.DPkLT2HdqNeAi6fvI1KQNdtePkNm80gqRrKH9TAGFYg"));
    }

    /**
     * 生成Token
     *
     * @param subject 这里我使用的是用户的电话号码，但是也可以更换
     * @return
     */
    public String createToken(String subject) {
        Date date = new Date();
        // 过期时间
        Date expireTime = new Date(date.getTime() + expire * 1000);
        // 创建一个新的JWT，并使用给定的算法对其进行签名
        return JWT.create()
                .withAudience(subject)
                .withExpiresAt(expireTime)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 获取Token信息
     *
     * @param token
     * @return
     */
    public String getToken(String token) {
        try {
            return getJWT(token).getAudience().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据token拿到过期时间
     *
     * @param token
     * @return
     */
    public Long getTokenExpiresAt(String token) {
        try {
            return getJWT(token).getExpiresAt().getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param token
     * @return
     */
    private DecodedJWT getJWT(String token) {
        try {
            return JWT.decode(token);
        } catch (Exception e) {
            return null;
        }

    }

}
