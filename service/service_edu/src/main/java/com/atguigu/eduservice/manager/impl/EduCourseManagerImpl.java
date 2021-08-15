package com.atguigu.eduservice.manager.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.commonutils.manager.BaseManager;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.po.EduChapterPO;
import com.atguigu.eduservice.entity.po.EduCourseDescriptionPO;
import com.atguigu.eduservice.entity.po.EduCoursePO;
import com.atguigu.eduservice.entity.po.EduVideoPO;
import com.atguigu.eduservice.entity.vo.CourseInfoVO;
import com.atguigu.eduservice.manager.EduCourseManager;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

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

    @Resource
    private EduChapterMapper eduChapterMapper;

    @Resource
    private EduVideoMapper eduVideoMapper;

    @Resource
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;

    @Resource
    private EduCourseMapper eduCourseMapper;

    @Resource( name = "myExecutor")
    private Executor myExecutor;

    @Autowired
    private VodClient vodClient;

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

    @Override
    public R removeCourse(String courseId) {
        TransactionTemplate transactionTemplate = getDataSourceTransactionManager();
        return transactionTemplate.execute(transactionStatus -> {
            try {
                // 小节
                LambdaQueryWrapper<EduVideoPO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(EduVideoPO::getCourseId,courseId);
                List<EduVideoPO> eduVideoPOS = eduVideoMapper.selectList(queryWrapper);
                if (ObjectUtil.isNotEmpty(eduVideoPOS)) {
                    int i = eduVideoMapper.delete(queryWrapper);
                    if (i < 1) {
                        transactionStatus.setRollbackOnly();
                        return R.error().message("删除失败");
                    }
                }

                // 章节
                LambdaQueryWrapper<EduChapterPO> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(EduChapterPO::getCourseId,courseId);
                List<EduChapterPO> eduChapterPOS = eduChapterMapper.selectList(queryWrapper1);
                if (ObjectUtil.isNotEmpty(eduChapterPOS)) {
                    int i = eduChapterMapper.delete(queryWrapper1);
                    if (i < 1) {
                        transactionStatus.setRollbackOnly();
                        return R.error().message("删除失败");
                    }
                }

                // 课程描述
                LambdaQueryWrapper<EduCourseDescriptionPO> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(EduCourseDescriptionPO::getId,courseId);
                EduCourseDescriptionPO eduCourseDescriptionPO = eduCourseDescriptionMapper.selectOne(queryWrapper2);
                if (ObjectUtil.isNotNull(eduCourseDescriptionPO)) {
                    int i = eduCourseDescriptionMapper.delete(queryWrapper2);
                    if (i < 1) {
                        transactionStatus.setRollbackOnly();
                        return R.error().message("删除失败");
                    }
                }

                // 课程
                LambdaQueryWrapper<EduCoursePO> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(EduCoursePO::getId,courseId);
                EduCoursePO eduCoursePO = eduCourseMapper.selectOne(queryWrapper3);
                if (ObjectUtil.isNotNull(eduCoursePO)) {
                    int i = eduCourseMapper.delete(queryWrapper3);
                    if (i < 1) {
                        transactionStatus.setRollbackOnly();
                        return R.error().message("删除失败");
                    }
                }

                // 异步删除小节视频
                CompletableFuture.runAsync(() -> {
                    // 获取视频id的集合
                    List<String> videoIdList = eduVideoPOS.stream().filter(k -> StrUtil.isNotBlank(k.getVideoSourceId())).map(EduVideoPO::getVideoSourceId).collect(Collectors.toList());
                    // 视频id列表不为空
                    if (CollUtil.isNotEmpty(videoIdList)) {
                        // 批量删除视频
                        vodClient.removeBatchVideo(videoIdList);
                    }
                },myExecutor);

                return R.ok().message("删除成功");

            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                log.error(TAG, e);
                return R.error().message("删除失败");
            }
        });
    }
}
