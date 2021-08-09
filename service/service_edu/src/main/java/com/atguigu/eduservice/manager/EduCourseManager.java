package com.atguigu.eduservice.manager;

import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVO;

/**
 * @Author hgk
 * @Date 2021/8/2 21:01
 * @description
 */
public interface EduCourseManager {

    /**
     * 添加课程信息及课程描述信息
     * @param courseInfoVO
     * @return
     */
    R addCourseInfoAndEduCourseDescription(CourseInfoVO courseInfoVO);

    /**
     * 删除课程
     * @param courseId
     * @return
     */
    R removeCourse(String courseId);
}
