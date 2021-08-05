package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.vo.ChapterVO;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hgk
 * @since 2021-08-02
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("getAllChapterVideo/{courseId}")
    public R getAllChapterVideo(@PathVariable("courseId") String courseId) {
        R r  = eduChapterService.getAllChapterVideo(courseId);
        return r;
    }
}

