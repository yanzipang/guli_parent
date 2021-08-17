package com.atguigu.eduservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduCourseDescriptionPO;
import com.atguigu.eduservice.entity.po.EduCoursePO;
import com.atguigu.eduservice.entity.po.EduSubjectPO;
import com.atguigu.eduservice.entity.po.EduTeacherPO;
import com.atguigu.eduservice.entity.vo.CourseInfoVO;
import com.atguigu.eduservice.entity.vo.CoursePublishVO;
import com.atguigu.eduservice.enums.CourseStatusEnum;
import com.atguigu.eduservice.manager.EduCourseManager;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private EduTeacherMapper eduTeacherMapper;

    @Resource
    private EduSubjectMapper eduSubjectMapper;


    @Override
    public R addCourseInfo(CourseInfoVO courseInfoVO) {
        if (ObjectUtil.isNull(courseInfoVO)) {
            return R.error().message("数据不能为空");
        }

        R r = eduCourseManager.addCourseInfoAndEduCourseDescription(courseInfoVO);

        return r;
    }

    /**
     * 获取课程信息
     * @param id
     * @return
     */
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
        //R r = eduCourseManager.updateCourseInfo(eduCoursePO,eduCourseDescriptionPO,courseInfoVO.getId())
        return R.ok().message("修改成功");
    }

    @Override
    public R getPublishAllCourse(String courseId) {
        CoursePublishVO coursePublishVO = new CoursePublishVO();
        // 查询课程表记录
        EduCoursePO eduCoursePO = eduCourseMapper.selectById(courseId);
        if (ObjectUtil.isNull(eduCoursePO)) {
            return R.error().message("课程不存在");
        }
        coursePublishVO.setId(eduCoursePO.getId());
        coursePublishVO.setLessonNum(eduCoursePO.getLessonNum());
        coursePublishVO.setPrice(eduCoursePO.getPrice());
        coursePublishVO.setTitle(eduCoursePO.getTitle());
        coursePublishVO.setCover(eduCoursePO.getCover());
        // 查询课程描述表信息
        EduCourseDescriptionPO eduCourseDescriptionPO = eduCourseDescriptionMapper.selectById(courseId);
        if (ObjectUtil.isNotNull(eduCourseDescriptionPO)) {
            coursePublishVO.setDescription(eduCourseDescriptionPO.getDescription());
        }
        // 查询讲师表信息
        EduTeacherPO eduTeacherPO = eduTeacherMapper.selectById(eduCoursePO.getTeacherId());
        if (ObjectUtil.isNotNull(eduTeacherPO)) {
            coursePublishVO.setTeacherName(eduTeacherPO.getName());
        }
        // 查询课程父类分类表信息
        EduSubjectPO eduSubjectPO = eduSubjectMapper.selectById(eduCoursePO.getSubjectParentId());
        if (ObjectUtil.isNotNull(eduSubjectPO)) {
            coursePublishVO.setSubjectLevelOne(eduSubjectPO.getTitle());
        }
        // 查询课程子类分类表信息
        EduSubjectPO eduSubjectPO1 = eduSubjectMapper.selectById(eduCoursePO.getSubjectId());
        if (ObjectUtil.isNotNull(eduSubjectPO1)) {
            coursePublishVO.setSubjectLevelTwo(eduSubjectPO1.getTitle());
        }
        return R.ok().data("coursePublishVO",coursePublishVO);
    }

    @Override
    public R publishCourseInfo(String id) {
        EduCoursePO eduCoursePO = new EduCoursePO();
        eduCoursePO.setId(id).setStatus(CourseStatusEnum.NORMAL.getStatus());
        int i = eduCourseMapper.updateById(eduCoursePO);
        if (i < 1) {
            return R.error().message("发布失败");
        }
        return R.ok().message("发布成功");
    }

    @Override
    public R removeByCourseId(String courseId) {
        R r = eduCourseManager.removeCourse(courseId);
        return r;
    }

    /**
     * 查八个课程
     * @return
     */
    @Cacheable(key = "'courseList'",value = "recordsList")
    @Override
    public List<EduCoursePO> selectCourseIndexEnght() {
        LambdaQueryWrapper<EduCoursePO> queryWrapper = new LambdaQueryWrapper<>();
        Page<EduCoursePO> page = new Page<EduCoursePO>(1, 8);
        queryWrapper.orderByDesc(EduCoursePO::getViewCount);
        IPage<EduCoursePO> eduEduCoursePOIPage = baseMapper.selectPage(page, queryWrapper);
        List<EduCoursePO> records = eduEduCoursePOIPage.getRecords();
        return records;
    }
}
