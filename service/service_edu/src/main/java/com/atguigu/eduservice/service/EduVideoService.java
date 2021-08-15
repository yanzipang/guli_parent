package com.atguigu.eduservice.service;

import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduVideoPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author hgk
 * @since 2021-08-02
 */
public interface EduVideoService extends IService<EduVideoPO> {

    R removeVideoById(String id);
}
