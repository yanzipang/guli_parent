package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.response.R;
import org.springframework.web.bind.annotation.*;

/**
 * @Author HanGuangKai
 * @Date 2021/6/30 18:08
 * @description
 */

@RestController
@RequestMapping(value ="/eduservice/user")
@CrossOrigin    // 解决跨域问题
public class EduLoginController {

    @PostMapping("/login")
    public R login() {
        return R.ok().data("token","admin");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://hgk-guli.oss-cn-qingdao.aliyuncs.com/2021/07/29/ima3.jpg4fbf24e6a205478e9af9f3b8cd954985");
    }

}
