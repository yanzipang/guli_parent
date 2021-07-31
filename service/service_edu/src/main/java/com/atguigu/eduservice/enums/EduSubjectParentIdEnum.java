package com.atguigu.eduservice.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author hgk
 * @Date 2021/7/31 17:30
 * @description
 */
@Getter
@AllArgsConstructor
public enum EduSubjectParentIdEnum {
    ONE_CLASSIFY("0","一级分类");

    private final String parentId;
    private final String parentIdMess;
}
