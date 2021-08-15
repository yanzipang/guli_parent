package com.atguigu.eduservice.frontcontroller;

import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduCoursePO;
import com.atguigu.eduservice.entity.po.EduTeacherPO;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author hgk
 * @Date 2021/8/15 21:27
 * @description 前台显示
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 查询前8条课程记录及前四条老师记录
     * @return
     */
    // TODO 异步查询-等待-返回
    @GetMapping("index")
    public R index() {
        List<EduTeacherPO> list1 = eduTeacherService.selectTeacharIndexFour();
        List<EduCoursePO> list2 = eduCourseService.selectCourseIndexEnght();
        return R.ok().data("teacherList",list1).data("eduList",list2);
    }

}
