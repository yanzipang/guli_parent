package com.atguigu.oss.service;

import com.atguigu.commonutils.response.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author HanGuangKai
 * @Date 2021/7/28 23:22
 * @description
 */
public interface OssService {

    /**
     * 上传讲师头像
     * @param file 头像
     * @return
     */
    R uploadFileAvatar(MultipartFile file);

}
