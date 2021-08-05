package com.atguigu.eduservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduCourseDescriptionPO;
import com.atguigu.eduservice.entity.po.EduCoursePO;
import com.atguigu.eduservice.entity.vo.CourseInfoVO;
import com.atguigu.eduservice.manager.EduCourseManager;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hgk
 * @since 2021-08-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCoursePO> implements EduCourseService {

    @Autowired
    private EduCourseManager eduCourseManager;

    @Resource
    private EduCourseMapper eduCourseMapper;

    @Resource
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;


    @Override
    public R addCourseInfo(CourseInfoVO courseInfoVO) {
        if (ObjectUtil.isNull(courseInfoVO)) {
            return R.error().message("数据不能为空");
        }

        R r = eduCourseManager.addCourseInfoAndEduCourseDescription(courseInfoVO);

        return r;
    }

    @Override
    public R getCourseInfo(String id) {
        EduCoursePO eduCoursePO = eduCourseMapper.selectById(id);
        EduCourseDescriptionPO eduCourseDescriptionPO = eduCourseDescriptionMapper.selectById(id);
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        BeanUtils.copyProperties(eduCoursePO,courseInfoVO);
        BeanUtils.copyProperties(eduCourseDescriptionPO,courseInfoVO);
        return R.ok().data("courseInfo",courseInfoVO);
    }

    // TODO 加乐观锁，加事务
    @Override
    @Transactional
    public R updateCourseInfo(CourseInfoVO courseInfoVO) {
        EduCoursePO eduCoursePO = new EduCoursePO();
        BeanUtils.copyProperties(courseInfoVO,eduCoursePO);
        int i = eduCourseMapper.updateById(eduCoursePO);
        EduCourseDescriptionPO eduCourseDescriptionPO = new EduCourseDescriptionPO();
        BeanUtils.copyProperties(courseInfoVO,eduCourseDescriptionPO);
        int i1 = eduCourseDescriptionMapper.updateById(eduCourseDescriptionPO);
        return R.ok().message("修改成功");
    }
}
