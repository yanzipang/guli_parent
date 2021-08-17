package com.atguigu.msmservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author hgk
 * @Date 2021/8/17 9:29
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@PropertySource("classpath:application.properties")
@Component
public class ShortMessageProperties {
    @Value("${short.message.host}")
    private String host;
    @Value("${short.message.path}")
    private String path;
    @Value("${short.message.appCode}")
    private String appCode;
    @Value("${short.message.sign}")
    private String sign;
    @Value("${short.message.skin}")
    private String skin;
}
