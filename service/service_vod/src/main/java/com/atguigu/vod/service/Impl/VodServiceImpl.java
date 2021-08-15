package com.atguigu.vod.service.Impl;

import cn.hutool.core.collection.CollUtil;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.commonutils.response.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtil;
import com.atguigu.vod.utils.InitObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @Author hgk
 * @Date 2021/8/13 20:36
 * @description
 */
@Service
@Slf4j
public class VodServiceImpl implements VodService {

    private final String TAG = "VodService";

    @Override
    public R uploadVideo(MultipartFile file) {

        try {

            // accessKeyId
            String accessKeyId = ConstantVodUtil.ACCESS_KEY_ID;
            // accessKeySecret
            String accessKeySecret = ConstantVodUtil.ACCESS_KEY_SECRET;
            // fileName---上传文件的原始名称
            String fileName = file.getOriginalFilename();
            // title---上传后控制台显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            // inputStream---上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret,title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return R.ok().data("videoId",videoId);

        } catch (Exception e) {
            log.error("{},异常信息：{}",TAG,e.getMessage());
            return R.error().message("上传视频失败");
        }

    }

    /**
     * 删除单个视频
     * @param id
     * @return
     */
    @Override
    public R removeVideo(String id) {
        try{
            // 初始化对象
            DefaultAcsClient client = InitObject.initVodClient(
                    ConstantVodUtil.ACCESS_KEY_ID,
                    ConstantVodUtil.ACCESS_KEY_SECRET);
            // 创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 想request对象中设置id
            request.setVideoIds(id);
            DeleteVideoResponse response = client.getAcsResponse(request);
            return R.ok().message("删除成功");
        }catch (Exception e){
            log.error("{},{}",TAG,e.getMessage());
            throw new GuliException(20001, "视频删除失败");
        }
    }

    /**
     * 同时删除多个视频
     * @param videoIdList
     * @return
     */
    @Override
    public R removeBatchVideo(List<String> videoIdList) {
        if (CollUtil.isEmpty(videoIdList)) {
            return R.error().message("要删除的视频id列表不能为空");
        }
        try{
            // 初始化对象
            DefaultAcsClient client = InitObject.initVodClient(
                    ConstantVodUtil.ACCESS_KEY_ID,
                    ConstantVodUtil.ACCESS_KEY_SECRET);
            // 创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // videoIdList拼接成符合入参要求的格式
            String idStr = StringUtils.join(videoIdList.toArray(), ",");
            // 想request对象中设置id
            request.setVideoIds(idStr);
            DeleteVideoResponse response = client.getAcsResponse(request);
            return R.ok().message("删除成功");
        }catch (Exception e){
            log.error("{},{}",TAG,e.getMessage());
            throw new GuliException(20001, "视频删除失败");
        }
    }

}
