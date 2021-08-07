package com.atguigu.eduservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author hgk
 * @Date 2021/8/7 14:16
 * @description 课程发布状态
 */
@Getter
@AllArgsConstructor
public enum CourseStatusEnum {
    DRAFT("Draft","未发布"),
    NORMAL("Normal","已发布");
    private final String status;
    private final String statusMessage;
}
