package com.atguigu.eduservice.client.impl;

import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author hgk
 * @Date 2021/8/15 17:44
 * @description VodClient实现类，兜底方法，当被调用服务超时或不可用是调用此类中方法
 */
// 出错之后才会执行
@Component
public class VodFileDegradeFeignClient implements VodClient {

    @Override
    public R removeVideo(String id) {
        return R.error().message("time out");
    }

    @Override
    public R removeBatchVideo(List<String> videoIdList) {
        return R.error().message("time out");
    }
}
