package com.atguigu.eduservice.convert;

import com.atguigu.eduservice.entity.po.EduTeacherPO;
import com.atguigu.eduservice.entity.vo.TeacherAdd;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @Author hgk
 * @Date 2021/8/3 10:01
 * @description
 */
@Mapper
public interface EduTeacherConvert {

    EduTeacherConvert INSTANCE = Mappers.getMapper(EduTeacherConvert.class);


    @Mapping(target = "name", ignore = true)
    EduTeacherPO toEduTeacherPO(TeacherAdd teacher);
}
