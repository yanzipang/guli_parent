package com.atguigu.eduservice.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author hgk
 * @Date 2021/8/7 11:35
 * @description
 */
@Data
public class CoursePublishVO {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    // 课程封面
    private String cover;
    // 课时数
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String description;
    private BigDecimal price;//只用于显示
}
