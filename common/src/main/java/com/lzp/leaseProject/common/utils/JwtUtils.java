package com.lzp.leaseProject.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author 李志平
 * @version 1.0
 * @data 2025/5/29 15:58
 * @desc
 */
public class JwtUtils {

    //规定token的过期时间，一小时
    private static long tokenExpiration = 60 * 60 * 1000L;
    private static SecretKey tokenSignKey = Keys.hmacShaKeyFor("M0PKKI6pYGVWWfDZw90a0lTpGYX1d4AQ".getBytes());

    public static String createToken(Long userId, String username) {
        String token = Jwts.builder().
                setSubject("USER_INFO").        //设置令牌主题，用于标识令牌用途
                setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)). //过期时间
                claim("userId", userId).
                claim("username", username).
                signWith(tokenSignKey).
                compact();
        return token;
    }
}
