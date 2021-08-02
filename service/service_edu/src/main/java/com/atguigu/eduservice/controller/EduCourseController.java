package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVO;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hgk
 * @since 2021-08-02
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 添加课程基本信息
     * @param courseInfoVO
     * @return
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVO courseInfoVO) {
        R r = eduCourseService.addCourseInfo(courseInfoVO);
        return r;
    }
}

