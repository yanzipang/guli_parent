package com.atguigu.eduservice.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author hgk
 * @Date 2021/8/4 20:06
 * @description
 */
@Data
public class ChapterVO {
    private String id;
    private String title;
    // 小节列表
    private List<VideoVO> children;
}
