package com.atguigu.demo;

import org.junit.Test;
import org.springframework.util.StringUtils;

/**
 * @Author hgk
 * @Date 2021/7/29 15:31
 * @description
 */
public class StringTest {

    @Test
    public void test() {
        String s = new String("''");
        System.out.println(s.length());
        System.out.println(StringUtils.isEmpty(""));
    }
}
