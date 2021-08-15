package com.atguigu.vod.controller;

import com.atguigu.commonutils.response.R;
import com.atguigu.vod.api.VodApi;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author hgk
 * @Date 2021/8/13 20:32
 * @description
 */
@RestController
@CrossOrigin
public class VodController implements VodApi {

    @Autowired
    private VodService vodService;

    public R uploadAliyun(MultipartFile file) {
        // 返回视频id
        R r = vodService.uploadVideo(file);
        return r;
    }

    @Override
    public R removeVideo(String id) {
        R r = vodService.removeVideo(id);
        return r;
    }

    @Override
    public R removeBatchVideo(List<String> videoIdList) {
        R r = vodService.removeBatchVideo(videoIdList);
        return r;
    }

}
