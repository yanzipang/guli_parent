package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
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
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.16pic.com%2F00%2F88%2F44%2F16pic_8844212_s.jpg&refer=http%3A%2F%2Fimg.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1627890364&t=cde9c16874f8b42d36374e8b0d86d926");
    }

}
