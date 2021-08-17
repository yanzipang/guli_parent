package com.atguigu.msmservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.commonutils.response.R;
import com.atguigu.commonutils.util.RandomUtil;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author hgk
 * @Date 2021/8/16 21:49
 * @description
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public R sendPhone(String phone) {
        if (StrUtil.isBlank(phone)) {
            return R.error().message("手机号不能为空");
        }

        // 生成四位验证码
        String code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);

        // 调用方法，发送短信验证码
        boolean isSend = this.send(phone, param);
        if(isSend) {
            // 发送成功 TODO 完善key规则
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return R.ok().message("短信发送成功");
        } else {
            return R.error().message("发送短信失败");
        }
    }

    /**
     * 调阿里云接口，发送短信
     * @param phone 手机号
     * @param param 验证码
     * @return
     */
    private boolean send(String phone, Map<String, Object> param) {

        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI5tRYv6xR6o3AzjUZexyx", "LyAaEFQixP8jvKLaMoT2dn7iA7lMqU");
        IAcsClient client = new DefaultAcsClient(profile);

        // 设置相关参数
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);   // 手机号
        request.putQueryParameter("SignName", "韩广凯谷粒在线教育平台");   // 签名名称
        request.putQueryParameter("TemplateCode", "templateCode");    // 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); // 验证码

        try {
            // 发送
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

}
