package com.atguigu.eduservice.manager.impl;

import com.atguigu.commonutils.manager.BaseManager;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduCourseDescriptionPO;
import com.atguigu.eduservice.entity.po.EduCoursePO;
import com.atguigu.eduservice.entity.vo.CourseInfoVO;
import com.atguigu.eduservice.manager.EduCourseManager;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @Author hgk
 * @Date 2021/8/2 21:02
 * @description
 */
@Component
@Slf4j
public class EduCourseManagerImpl extends BaseManager implements EduCourseManager {

    private static final String TAG = "EduCourseManager";

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    /**
     * 添加课程信息及课程描述信息
     * @param courseInfoVO
     * @return
     */
    @Override
    public R addCourseInfoAndEduCourseDescription(CourseInfoVO courseInfoVO) {
        TransactionTemplate transactionTemplate = getDataSourceTransactionManager();
        return transactionTemplate.execute(transactionStatus -> {
            try {
                EduCoursePO eduCoursePO = new EduCoursePO();
                BeanUtils.copyProperties(courseInfoVO,eduCoursePO);
                boolean flag = eduCourseService.save(eduCoursePO);
                if (!flag) {
                    transactionStatus.setRollbackOnly();
                    return R.error().message("添加失败");
                }
                EduCourseDescriptionPO eduCourseDescriptionPO = new EduCourseDescriptionPO();
                eduCourseDescriptionPO.setDescription(courseInfoVO.getDescription());
                eduCourseDescriptionPO.setId(eduCoursePO.getId());
                boolean flag1 = eduCourseDescriptionService.save(eduCourseDescriptionPO);
                if (!flag1) {
                    transactionStatus.setRollbackOnly();
                    return R.error().message("添加失败");
                }
                System.out.println(R.ok().message("添加成功").data("courseId",eduCoursePO.getId()));
                return R.ok().message("添加成功").data("courseId",eduCoursePO.getId());
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                log.error(TAG, e);
                return R.error().message("添加失败");
            }
        });
    }
}
