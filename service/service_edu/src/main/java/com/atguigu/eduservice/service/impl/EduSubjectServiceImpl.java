package com.atguigu.eduservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.Listener.SubjectListener;
import com.atguigu.eduservice.entity.excel.SubjectExcelData;
import com.atguigu.eduservice.entity.po.EduSubjectPO;
import com.atguigu.eduservice.entity.vo.OneSubjectVO;
import com.atguigu.eduservice.entity.vo.TwoSubjectVO;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author hgk
 * @since 2021-07-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubjectPO> implements EduSubjectService {

    @Resource
    private EduSubjectMapper eduSubjectMapper;

    @Override
    public R saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        // TODO 判断上传的Excel是否符合模板标准
        try {
            // 获取文件输入流
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectExcelData.class,new SubjectListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok().message("添加成功");
    }

    @Override
    public R getAllSubject() {
        // 查询所有一级分类
        LambdaQueryWrapper<EduSubjectPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubjectPO::getParentId,0);
        List<EduSubjectPO> oneEduSubjectPOS = eduSubjectMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(oneEduSubjectPOS)) {
            return R.error().message("无数据");
        }

        // 查询所有二级分类
        LambdaQueryWrapper<EduSubjectPO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.ne(EduSubjectPO::getParentId,0);
        List<EduSubjectPO> twoEduSubjectPOS = eduSubjectMapper.selectList(queryWrapper1);

        // 组装数据
        // 按照parentId分组
        Map<String, List<EduSubjectPO>> collect = twoEduSubjectPOS.stream().collect(Collectors.groupingBy(EduSubjectPO::getParentId));
        List<OneSubjectVO> oneSubjectVOList = new ArrayList<>();
        oneEduSubjectPOS.forEach(k -> {
            OneSubjectVO oneSubjectVO = new OneSubjectVO();
            oneSubjectVO.setId(k.getId());
            oneSubjectVO.setTitle(k.getTitle());
            List<EduSubjectPO> eduSubjectPOS = collect.get(k.getId());
            List<TwoSubjectVO> twoSubjectVOList = new ArrayList<>();
            eduSubjectPOS.forEach(k1 -> {
                TwoSubjectVO twoSubjectVO = new TwoSubjectVO();
                twoSubjectVO.setId(k1.getId());
                twoSubjectVO.setTitle(k1.getTitle());
                twoSubjectVOList.add(twoSubjectVO);
            });
            oneSubjectVO.setTwoSubjectVOList(twoSubjectVOList);
            oneSubjectVOList.add(oneSubjectVO);
        });

        return R.ok().data("list",oneSubjectVOList);
    }
}
