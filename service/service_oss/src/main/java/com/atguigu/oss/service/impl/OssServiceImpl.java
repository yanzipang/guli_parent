package com.atguigu.oss.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.commonutils.response.R;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author HanGuangKai
 * @Date 2021/7/28 23:22
 * @description
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {

    private static final String TAG = "OssService";

    @Override
    public R uploadFileAvatar(MultipartFile file) {

        /**
         * 通过工具类获取值
         */
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = null;

        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = null;

        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件的输入流
            inputStream = file.getInputStream();

            // 获取文件名称
            String filename = file.getOriginalFilename();

            // 在文件名中添加随机的唯一值（防止重名覆盖）
            // 随机生成的uuid里可能会有-,使用replaceAll()将-替换成空字符串
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid + filename;

            // 把文件按照日期分类
            // 获取当前的日期,如：2021/07/29
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            filename = dataPath + "/" +filename;

            /**
             * 第一个参数：Bucket名称
             * 第二个参数：上川到OSS文件路径和文件名称
             * 第三个参数：上传文件输入流
             */
            // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
            ossClient.putObject(bucketName, filename, inputStream);

            // 把上传之后的文件路径返回
            // 需要把上传到阿里云oss路径手动拼接出来
            // https://hgk-guli.oss-cn-qingdao.aliyuncs.com/b.jpg
            String url = "http://" + bucketName + "." + endpoint + "/" + filename;

            return R.ok().data("url",url);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("错误接口：{}，错误信息：{}",TAG,e.getMessage());
        } finally {
            if (ObjectUtil.isNotNull(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ObjectUtil.isNotNull(ossClient)) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
        return R.error();
    }

}
