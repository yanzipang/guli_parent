package com.atguigu.eduservice.service;

import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduCoursePO;
import com.atguigu.eduservice.entity.vo.CourseInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hgk
 * @since 2021-08-02
 */
public interface EduCourseService extends IService<EduCoursePO> {

    R addCourseInfo(CourseInfoVO courseInfoVO);

    R getCourseInfo(String id);

    R updateCourseInfo(CourseInfoVO courseInfoVO);

    R getPublishAllCourse(String courseId);

    R publishCourseInfo(String id);
}
