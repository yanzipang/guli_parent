package com.atguigu.oss.controller;

import cn.hutool.core.util.ObjectUtil;
import com.atguigu.commonutils.response.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author HanGuangKai
 * @Date 2021/7/28 23:23
 * @description
 */
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    // 上传头像的方法
    @PostMapping("upload")
    public R uploadOssFile(MultipartFile file) {

        if (ObjectUtil.isEmpty(file)) {
            return R.error().message("上传文件不能为空");
        }
        // 获取上传的文件 MultipartFile即上传的文件

        return R.ok();
    }
}
