package com.atguigu.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author hgk
 * @Date 2021/8/13 20:49
 * @description
 *  使用@Value读取application.properties里的配置内容
 *  用spring的 InitializingBean 的 afterPropertiesSet() 来初始化配置信息，这个方法将在所有的属性被初始化后调用。
 */
@Component
//@PropertySource("classpath:application.properties")
public class ConstantVodUtil implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
