package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author hgk
 * @Date 2021/7/29 15:08
 * @description
 */
@Data
public class TeacherAdd implements Serializable {

    @ApiModelProperty(value = "讲师姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(value = "讲师简介")
    @NotBlank(message = "讲师简介不能为空")
    private String intro;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String career;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    @NotNull(message = "头衔不能为空")
    private Integer level;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;

    @ApiModelProperty(value = "排序")
    @NotNull(message = "排序不能为空")
    @Min(value = 0,message = "不能小于0")
    private Integer sort;

}
