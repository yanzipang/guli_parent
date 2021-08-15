package com.atguigu.eduservice.client;

import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author hgk
 * @Date 2021/8/15 10:09
 * @description 调用vod服务--实现删除小节同时删除视频功能
 */
// fallback = VodFileDegradeFeignClient.class  兜底方法，VodClient的实现类
@FeignClient(name = "service-vod" , fallback = VodFileDegradeFeignClient.class) // @FeignClient注解用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致。
@Component // @Component注解防止，在其他位置注入VodClient时idea报错
public interface VodClient {

    /**
     * 远程接口声明式调用
     * @PathVariable注解一定要指定参数名称，否则出错
     * @param id
     * @return
     */
    @DeleteMapping(value = "/eduvod/video/remove/{id}")
    public R removeVideo(@PathVariable("id") String id);

    /**
     * 同时删除多个视频
     * @param videoIdList 视频id
     * @return
     */
    @DeleteMapping("/eduvod/video/remove-batch")
    public R removeBatchVideo(@RequestParam("videoIdList") List<String> videoIdList);

}
