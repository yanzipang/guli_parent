package com.atguigu.vod.api;

import com.atguigu.commonutils.response.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author hgk
 * @Date 2021/8/13 21:52
 * @description
 */
@RequestMapping("/eduvod/video")
public interface VodApi {

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @PostMapping("uploadAliyun/video")
    public R uploadAliyun(MultipartFile file);

    /**
     * 根据视频id删除阿里云上的视频
     * @param id
     * @return
     */
    @DeleteMapping("remove/{id}")
    public R removeVideo(@PathVariable("id") String id);

    /**
     * 同时删除多个视频
     * @param videoIdList 视频id
     * @return
     */
    @DeleteMapping("remove-batch")
    public R removeBatchVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
