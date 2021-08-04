package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVO;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * @param courseInfo
     * @return
     */
    // TODO 这里有三步保存操作，现在是每一步都保存到数据后，后期优化成：每一步都先缓存到redis，最后在保存的时候统一保存到数据库中
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody @Valid CourseInfoVO courseInfo) {
        R r = eduCourseService.addCourseInfo(courseInfo);
        return r;
    }
}

