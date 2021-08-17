package com.atguigu.commonutils.util;

/**
 * @Author hgk
 * @Date 2021/8/17 8:49
 * @description 拼接rides-key的工具类
 */
public class RedisKeyConstantUtil {

    /**
     * 拼接手机验证码的redis-key
     * @param phone 手机号
     * @return
     */
    public static String getVerificationCodeKey(String phone) {
        String  verificationCodeKey = RedisKeyConstant.VERIFICATION_CODE_KEY + phone;
        return verificationCodeKey;
    }

    /**
     * 前台首页redis-key
     * @return
     */
    public static String getBannerKey() {
        String  bannerKey = RedisKeyConstant.BANNER;
        return bannerKey;
    }

}
