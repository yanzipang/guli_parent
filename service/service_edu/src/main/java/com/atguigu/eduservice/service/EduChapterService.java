package com.atguigu.eduservice.service;

import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduChapterPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hgk
 * @since 2021-08-02
 */
public interface EduChapterService extends IService<EduChapterPO> {

    R getAllChapterVideo(String courseId);

    R deleteChapterByChapterId(String chapterId);
}
