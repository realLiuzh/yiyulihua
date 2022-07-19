package com.yiyulihua.gateway.util;

import com.auth0.jwt.JWT;
import com.alibaba.fastjson.JSON;
import com.yiyulihua.gateway.entity.UserJwtVo;
import org.springframework.security.jwt.JwtHelper;

import java.util.Map;

public class JwtUtils {
    //解析jwt令牌
    public static Map parsingJwt(String token) {
        //解析jwt
//        Jwt decode = JwtHelper.decode(token);
        //得到 jwt中的用户信息
//        String claims = decode.getClaims();
        //将jwt转为Map
//        Map map = JSON.parseObject(claims, Map.class);
        return null;

    }

    /**
     * 获取jwt令牌用户信息
     *
     * @param token
     * @return
     */
    public static UserJwtVo getUserInfoByToken(String token) {
        int id = JWT.decode(token).getClaim("id").asInt();
        String username = JWT.decode(token).getClaim("username").asString();
        return new UserJwtVo(id, username);
    }

}
