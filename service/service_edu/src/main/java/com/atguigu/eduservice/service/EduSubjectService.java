package com.atguigu.eduservice.service;

import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduSubjectPO;
import com.atguigu.eduservice.entity.vo.OneSubjectVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author hgk
 * @since 2021-07-31
 */
public interface EduSubjectService extends IService<EduSubjectPO> {

    R saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    R getAllSubject();
}
