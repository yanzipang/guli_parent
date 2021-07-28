package com.atguigu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author HanGuangKai
 * @Date 2021/7/28 22:53
 * @description
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // 让其不去加载数据源的配置，以防启动失败
@ComponentScan(basePackages = {"com.atguigu"}) // 目的是为了扫描swagger配置类
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}