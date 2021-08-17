package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduTeacherPO;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author hgk
 * @since 2021-06-26
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacherPO> implements EduTeacherService {




    @Override
    public void pageQuery(Page<EduTeacherPO> pageParam, TeacherQuery teacherQuery) {

        // 构建条件
        QueryWrapper<EduTeacherPO> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByAsc("sort");
        if (teacherQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // 判断条件值是否为空，不过不为空则拼接条件
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 查四个老师
     * @return
     */
    @Cacheable(key = "'teacherList'",value = "records")
    @Override
    public List<EduTeacherPO>  selectTeacharIndexFour() {
        LambdaQueryWrapper<EduTeacherPO> queryWrapper = new LambdaQueryWrapper<>();
        Page<EduTeacherPO> page = new Page<EduTeacherPO>(1, 4);
        queryWrapper.orderByDesc(EduTeacherPO::getGmtCreate);
        IPage<EduTeacherPO> eduTeacherPOIPage = baseMapper.selectPage(page, queryWrapper);
        List<EduTeacherPO> records = eduTeacherPOIPage.getRecords();
        return records;
    }
}
