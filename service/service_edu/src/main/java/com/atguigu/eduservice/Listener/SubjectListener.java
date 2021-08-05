package com.atguigu.eduservice.Listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.commonutils.enums.ResultCodeEnum;
import com.atguigu.eduservice.entity.excel.SubjectExcelData;
import com.atguigu.eduservice.entity.po.EduSubjectPO;
import com.atguigu.eduservice.enums.EduSubjectParentIdEnum;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * @Author hgk
 * @Date 2021/7/31 16:58
 * @description
 */
public class SubjectListener extends AnalysisEventListener<SubjectExcelData> {

    // 因为ExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    // 所以不能实现数据库等操作,所以需要手动new一下
    public EduSubjectService eduSubjectService;
    public SubjectListener () {}
    public SubjectListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    //一行一行去读取excle内容
    @Override
    public void invoke(SubjectExcelData subjectExcelData, AnalysisContext analysisContext) {
        if (ObjectUtil.isNull(subjectExcelData)) {
            throw  new GuliException(ResultCodeEnum.ERROR.getResultCode(), "传入的文件不能为空");
        }

        EduSubjectPO eduSubjectPO = this.existOneEduSubject(eduSubjectService,subjectExcelData.getOneSubjectName());
        if (ObjectUtil.isNull(eduSubjectPO)) {
            eduSubjectPO = new EduSubjectPO();
            eduSubjectPO.setTitle(subjectExcelData.getOneSubjectName());
            eduSubjectPO.setParentId(EduSubjectParentIdEnum.ONE_CLASSIFY.getParentId());
            eduSubjectService.save(eduSubjectPO);
        }

        // 获取一级分类id值
        String pid = eduSubjectPO.getId();
        EduSubjectPO eduSubjectPO1 = this.existTwoEduSubject(eduSubjectService,subjectExcelData.getTwoSubjectName(),pid);
        if (ObjectUtil.isNull(eduSubjectPO1)) {
            eduSubjectPO1 = new EduSubjectPO();
            eduSubjectPO1.setTitle(subjectExcelData.getTwoSubjectName());
            eduSubjectPO1.setParentId(pid);
            eduSubjectService.save(eduSubjectPO1);
        }
    }

    //读取excel表头信息
    // 判断Excel文件是否符合格式
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
//        Set<Integer> keySet = headMap.keySet();
//        if (ObjectUtil.isNotEmpty(headMap)) {
//            if (!headMap.get(0).equals("一级课程名称")) {
//
//            }
//        }
        System.out.println("表头信息："+headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    /**
     * 判断一级分类不能重复添加
     * @param name
     * @return
     */
    private EduSubjectPO existOneEduSubject(EduSubjectService eduSubjectService,String name) {
        LambdaQueryWrapper<EduSubjectPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduSubjectPO::getTitle,name)
                .eq(EduSubjectPO::getParentId, EduSubjectParentIdEnum.ONE_CLASSIFY.getParentId());
        EduSubjectPO eduSubjectPO = eduSubjectService.getOne(wrapper);
        return eduSubjectPO;
    }

    /**
     * 判断二级分类不能重复添加
     * @param name
     * @return
     */
    private EduSubjectPO existTwoEduSubject(EduSubjectService eduSubjectService,String name,String parentId) {
        LambdaQueryWrapper<EduSubjectPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduSubjectPO::getTitle,name)
                .eq(EduSubjectPO::getParentId, parentId);
        EduSubjectPO eduSubjectPO = eduSubjectService.getOne(wrapper);
        return eduSubjectPO;
    }
}
