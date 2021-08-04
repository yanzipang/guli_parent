package com.atguigu.eduservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduCoursePO;
import com.atguigu.eduservice.entity.vo.CourseInfoVO;
import com.atguigu.eduservice.manager.EduCourseManager;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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

    @Autowired
    private RedisTemplate redisTemplate;

    private final String key = "course";

    @Override
    public R addCourseInfo(CourseInfoVO courseInfoVO) {
        if (ObjectUtil.isNull(courseInfoVO)) {
            return R.error().message("数据不能为空");
        }

        //R r = eduCourseManager.addCourseInfoAndEduCourseDescription(courseInfoVO);

        redisTemplate.opsForHash().put(key,"value",courseInfoVO.getPrice());

        return R.ok();
    }
}
