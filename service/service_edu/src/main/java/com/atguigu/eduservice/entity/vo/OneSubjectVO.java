package com.atguigu.eduservice.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author hgk
 * @Date 2021/8/1 20:38
 * @description
 */
@Data
public class OneSubjectVO {
    private String id;
    private String title;
    private List<TwoSubjectVO> twoSubjectVOList;
}
