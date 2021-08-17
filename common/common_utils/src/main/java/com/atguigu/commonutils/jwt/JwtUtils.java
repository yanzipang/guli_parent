package com.atguigu.commonutils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author hgk
 * @Date 2021/8/16 21:26
 * @description JWT工具类---token
 */
public class JwtUtils {

    // 常量
    public static final long EXPIRE = 1000 * 60 * 60 * 24;  // token过期时间---1天
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";   // 秘钥，随机填写

    /**
     * 生成token字符串
     * @param id 用户id
     * @param nickname 用户名称
     * @return 可以传多个参数
     */
    public static String getJwtToken(String id, String nickname){

        String JwtToken = Jwts.builder()    // 构建JWT字符串
                // 设置JWT的头信息(固定的)
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // 自定义设置---设置JWT分类
                .setSubject("guli-user")
                // 设置JWT过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                // 设置token的主体部分，存储用户信息
                .claim("id", id)
                .claim("nickname", nickname)
                // 签名哈希
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            // 验证token是否有效
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            // 验证token是否有效
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        // 得到token字符串主体部分
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }

}
