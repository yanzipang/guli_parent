package com.atguigu.vod.service;

import com.atguigu.commonutils.response.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author hgk
 * @Date 2021/8/13 20:35
 * @description
 */
@RequestMapping("/eduvod/video")
public interface VodService {
    R uploadVideo(MultipartFile file);

    R removeVideo(String id);

    R removeBatchVideo(List<String> videoIdList);
}
