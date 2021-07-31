package com.atguigu.eduservice.convert;

import com.atguigu.eduservice.entity.po.EduTeacherPO;
import com.atguigu.eduservice.entity.vo.TeacherAdd;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author hgk
 * @Date 2021/7/29 15:08
 * @description
 */
@Mapper(componentModel = "spring")
public interface EduTeacherConvert {
    EduTeacherConvert INSTANCE = Mappers.getMapper(EduTeacherConvert.class);

    EduTeacherPO toEduTeacher(TeacherAdd teacherAdd);

}
