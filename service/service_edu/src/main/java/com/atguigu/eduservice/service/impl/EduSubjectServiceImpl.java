package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.Listener.SubjectListener;
import com.atguigu.eduservice.entity.excel.SubjectExcelData;
import com.atguigu.eduservice.entity.po.EduSubjectPO;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.Scope;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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

    @Override
    public R saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {

        try {
            // 获取文件输入流
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectExcelData.class,new SubjectListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok().message("添加成功");
    }
}
