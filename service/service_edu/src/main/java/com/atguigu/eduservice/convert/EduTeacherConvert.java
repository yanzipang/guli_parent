package com.atguigu.eduservice.convert;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherAdd;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @Author hgk
 * @Date 2021/7/29 15:08
 * @description
 */
@Mapper(componentModel = "spring")
public interface EduTeacherConvert {

    EduTeacher toEduTeacher(TeacherAdd teacherAdd);
}
