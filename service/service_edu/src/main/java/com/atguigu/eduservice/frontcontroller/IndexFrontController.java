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

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

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

    @Resource(name = "myExecutor")
    private Executor myExecutor;

    /**
     * 查询前8条课程记录及前四条老师记录
     * @return
     */
    @GetMapping("index")
    public R index() {
//        List<EduTeacherPO> list1 = eduTeacherService.selectTeacharIndexFour();
//        List<EduCoursePO> list2 = eduCourseService.selectCourseIndexEnght();
        // 查询老师
        CompletableFuture<List<EduTeacherPO>> listCompletableFuture = CompletableFuture.supplyAsync(() -> {
            List<EduTeacherPO> list1 = eduTeacherService.selectTeacharIndexFour();
            return list1;
        }, myExecutor);
        // 查询课程
        CompletableFuture<List<EduCoursePO>> listCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            List<EduCoursePO> list2 = eduCourseService.selectCourseIndexEnght();
            return list2;
        }, myExecutor);
        
        try {
            // 等待
            CompletableFuture.allOf(listCompletableFuture,listCompletableFuture1).get();
            // 获取
            List<EduTeacherPO> eduTeacherPOS = listCompletableFuture.get();
            List<EduCoursePO> eduCoursePOS = listCompletableFuture1.get();
            // 返回
            return R.ok().data("teacherList",eduTeacherPOS).data("eduList",eduCoursePOS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return R.error().message("程序错误");
    }

}
