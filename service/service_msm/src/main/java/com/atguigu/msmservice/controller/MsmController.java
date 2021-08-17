package com.atguigu.msmservice.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.commonutils.response.R;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author hgk
 * @Date 2021/8/16 21:48
 * @description
 */
@RestController
@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @GetMapping(value = "/send/{phone}")
    public R sendPhone(@PathVariable String phone) {
        R r = msmService.sendPhone(phone);
        return r;
    }

}
