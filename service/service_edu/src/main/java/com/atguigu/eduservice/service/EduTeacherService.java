package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.po.EduTeacherPO;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author hgk
 * @since 2021-06-26
 */
public interface EduTeacherService extends IService<EduTeacherPO> {

    void pageQuery(Page<EduTeacherPO> pageParam, TeacherQuery teacherQuery);
}
