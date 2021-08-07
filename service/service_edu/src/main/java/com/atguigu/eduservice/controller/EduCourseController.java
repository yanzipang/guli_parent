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

    /**
     * 查询课程基本信息，做数据回显
     * @param id
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String id) {
        R r = eduCourseService.getCourseInfo(id);
        return r;
    }

    /**
     * 修改课程信息
     * @param courseInfoVO
     * @return
     */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo( @RequestBody CourseInfoVO courseInfoVO) {
        R r = eduCourseService.updateCourseInfo(courseInfoVO);
        return r;
    }

    /**
     * 根据课程id查询课程信息--用于课程最终发布
     * @param courseId
     * @return
     */
    @GetMapping("getPublishAllCourse/{id}")
    public R getAllCourse(@PathVariable("id") String courseId) {
        R r = eduCourseService.getPublishAllCourse(courseId);
        System.out.println(r.getData());
        return r;
    }

    /**
     * 课程最终发布，修改发布状态
     * @param id
     * @return
     */
    @GetMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable("id") String id) {
        R r = eduCourseService.publishCourseInfo(id);
        return r;
    }
}

