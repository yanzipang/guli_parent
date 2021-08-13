package com.atguigu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;


import java.util.List;

/**
 * @Author hgk
 * @Date 2021/8/11 22:30
 * @description 根据视频id获取视频播放地址
 */
public class TestVod {
    public static void main(String[] args) {
        // 本地文件 上传
        //账号AK信息请填写(必选)
        final String accessKeyId = "LTAI5tRYv6xR6o3AzjUZexyx";
        //账号AK信息请填写(必选)
        final String accessKeySecret = "LyAaEFQixP8jvKLaMoT2dn7iA7lMqU";
        //视频标题(必选)
        String title = "tup";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "F:\\\\6 - What If I Want to Move Faster.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

//    public static void main(String[] args) throws ClientException {
//
//        // 本地文件 上传
//        //账号AK信息请填写(必选)
//        final String accessKeyId = "LTAI5tRYv6xR6o3AzjUZexyx";
//        //账号AK信息请填写(必选)
//        final String accessKeySecret = "LyAaEFQixP8jvKLaMoT2dn7iA7lMqU";
//        //视频标题(必选)
//        String title = "3 - How Does Project Submission Work - upload by sdk";
//        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
//        //文件名必须包含扩展名
//        String fileName = "F:\\6 - What If I Want to Move Faster.mp4";
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
//        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
//        request.setPartSize(2 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setTaskNum(1);
//
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        } else {
//            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//            System.out.print("ErrorCode=" + response.getCode() + "\n");
//            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//        }
//
//    }


    // 根据id获取视频播放凭证
    public static void getVideoPlay() throws ClientException {
        // 根据视频id获取视频播放地址
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tRYv6xR6o3AzjUZexyx", "LyAaEFQixP8jvKLaMoT2dn7iA7lMqU");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("546746b3c7934efd9f918c1d0a9641a8");
        return client.getAcsResponse(request);
    }

    //        DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI5tRYv6xR6o3AzjUZexyx", "LyAaEFQixP8jvKLaMoT2dn7iA7lMqU");
//        // 创建获取视频地址request和response
//        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
//        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
//
//        // 向request对象里面设置视频id
//        request.setVideoId("546746b3c7934efd9f918c1d0a9641a8");
//        // 调用初始化对象里面的方法传递request，获取数据
//        response = defaultAcsClient.getAcsResponse(request);
//
//        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
//        //播放地址
//        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
//            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
//        }

}
