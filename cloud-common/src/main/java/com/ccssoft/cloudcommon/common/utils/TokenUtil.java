package com.ccssoft.cloudcommon.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class TokenUtil {
    public static long getUserId (String token) {
        Claims claims = Jwts.parser().setSigningKey("jwtsecretdemo").parseClaimsJws(token).getBody();
        //解析自定义的claim中的内容
        long userId = (long) claims.get("userId");
        return userId;
    }
}
