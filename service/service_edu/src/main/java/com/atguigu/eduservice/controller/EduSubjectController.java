package com.atguigu.eduservice.controller;


import cn.hutool.core.util.ObjectUtil;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author hgk
 * @since 2021-07-31
 */
@Api(
        value = "EduSubjectPO-description",
        tags = {"课程管理"}
)
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    // 查询讲师列表所有数据
    @ApiOperation(
            value = "添加课程",
            notes = "添加课程",
            nickname = "addSubject"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 20000, message = "成功", response = R.class),
            @ApiResponse(code = 20001, message = "失败", response = R.class),
    })
    @PostMapping
    public R addSubject( @ApiParam(name = "file", value = "上传的Excel文件", required = true) MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            return R.error().message("上传的文件不能为空！");
        }
        // 保存
        R r = eduSubjectService.saveSubject(file,eduSubjectService);

        return r;
    }

}

