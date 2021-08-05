package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author hgk
 * @Date 2021/8/2 20:36
 * @description
 */
@Data
public class CourseInfoVO {

    /**
     * 要根据此id进行修改
     */
    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    @NotBlank(message = "讲师不能为空")
    private String teacherId;

    @ApiModelProperty(value = "课程专业ID")
    @NotBlank(message = "课程专业不能为空")
    private String subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    @NotBlank(message = "课程标题不能为空")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    @NotNull(message = "课程销售价格不能为空")
    @DecimalMin(value = "0",message = "课程售价不能小于0")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    @Min(value = 0,message = "课时不能小于0")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;

}
