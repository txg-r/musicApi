package com.tyfff.musicapi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtUtil {
    //过期时间
    private static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 密钥
    private static final String APP_SECRET = "A123456b";

    /**
     * 根据用户id签发token
     * @param id    user_id
     * @return      token
     */
    public static String getJwtToken(Integer id){
        return Jwts.builder()
                //设置token头
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //设置token主题
                .setSubject("login")
                //设置token创建时间
                .setIssuedAt(new Date())
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //设置载荷
                .claim("user_id", id)
                //设置签名，密钥
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    /**
     * 验证jwt是否有效
     * @param token     token
     * @return          是否有效
     */
    public static boolean checkToken(String token){
        if (!StringUtils.hasText(token)){
            return false;
        }

        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static Integer getUserIdByToken(String token){
        if (!checkToken(token)){
            return null;
        }

        Jws<Claims> claimsJws =
                Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (Integer) claims.get("user_id");
    }



}
