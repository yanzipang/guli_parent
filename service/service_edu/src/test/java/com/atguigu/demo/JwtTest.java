package com.atguigu.demo;

import com.atguigu.commonutils.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.Test;

import static com.atguigu.commonutils.jwt.JwtUtils.APP_SECRET;

/**
 * @Author hgk
 * @Date 2021/8/17 8:31
 * @description
 */
public class JwtTest {

    @Test
    public void jwt() {
        String token = JwtUtils.getJwtToken("123446", "周杰伦");
        System.out.println(token);
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpLXVzZXIiLCJpYXQiOjE2MjkxNjAzMzQsImV4cCI6MTYyOTI0NjczNCwiaWQiOiIxMjM0NDYiLCJuaWNrbmFtZSI6IumfqeW5v-WHryJ9.jeqUHxD4eO4LkIt_ZmC225FoC1z588q1jFR3aJa4IIk
    }

    @Test
    public void checkToken() {
        String token = JwtUtils.getJwtToken("123446", "周杰伦");
        boolean flag = JwtUtils.checkToken(token);
        System.out.println(flag);
    }

    @Test
    public void getMemberIdByJwtToken() {
        String token = JwtUtils.getJwtToken("123446", "周杰伦");
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        // 得到token字符串主体部分
        Claims claims = claimsJws.getBody();
        String id = (String)claims.get("id");
        System.out.println(id);
        String nickname = (String)claims.get("nickname");
        System.out.println(nickname);
    }
}
