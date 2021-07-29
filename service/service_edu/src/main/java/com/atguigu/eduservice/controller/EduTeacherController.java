package com.atguigu.eduservice.controller;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.eduservice.constant.MessageConstant;
import com.atguigu.eduservice.convert.EduTeacherConvert;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherAdd;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.commonutils.response.R;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hgk
 * @since 2021-06-26
 */
@Api(
        value = "EduTeacher-description",
        tags = {"讲师管理"}
)
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    // 注入service
    @Autowired
    private EduTeacherService eduTeacherService;

    @Resource
    private EduTeacherConvert eduTeacherConvert;

    // 查询讲师列表所有数据
    // @ApiOperation(value = "查询所有讲师列表")
    @ApiOperation(
            value = "查询讲师列表所有数据",
            notes = "查询讲师列表所有数据",
            nickname = "findAllTeacher"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 20000, message = "成功", response = R.class),
            @ApiResponse(code = 20001, message = "失败", response = R.class),
    })
    @GetMapping("findAll")
    public R findAllTeacher(){
            List<EduTeacher> teacherList = eduTeacherService.list(null);
            return R.ok().data("items",teacherList);
    }

    // 逻辑删除
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id
    ){

        if (StringUtils.isEmpty(id)) {
            return R.error().message(MessageConstant.ID_NOT_EMPUT);
        }

        EduTeacher eduTeacher = eduTeacherService.getById(id);

        if (ObjectUtil.isEmpty(eduTeacher)) {
            return R.error().message(MessageConstant.DELETE_OBJ_NOT_HAS);
        }

        boolean flag = eduTeacherService.removeById(id);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 分页查询
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        eduTeacherService.page(pageParam, null);
        List<EduTeacher> records = pageParam.getRecords();  // list集合
        long total = pageParam.getTotal();  //  总记录数
        return R.ok().data("total", total).data("rows", records);   //
    }

    /**
     * 分页查询带查询条件
     * @param page
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "分页讲师列表带查询条件")
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody(required = false) TeacherQuery teacherQuery  // @RequestBody取不到get请求方式提交的数据
    ){

        Page<EduTeacher> pageParam = new Page<>(page, limit);

        eduTeacherService.pageQuery(pageParam, teacherQuery);

        List<EduTeacher> records = pageParam.getRecords(); // list集合

        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    // 新增讲师
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody TeacherAdd eduTeacher){

        // TODO 有没有简单的查询方法
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(EduTeacher::getName,eduTeacher.getName())
                .eq(EduTeacher::getIntro,eduTeacher.getIntro())
                .eq(EduTeacher::getCareer,eduTeacher.getCareer())
                .eq(EduTeacher::getLevel,eduTeacher.getLevel())
                .eq(EduTeacher::getAvatar,eduTeacher.getAvatar())
                .eq(EduTeacher::getSort,eduTeacher.getSort());

        EduTeacher teacher1 = eduTeacherService.getOne(queryWrapper);

        if (ObjectUtil.isNotEmpty(teacher1)) {
            return R.error().message(MessageConstant.USER_HAVE);
        }

        EduTeacher teacher = eduTeacherConvert.toEduTeacher(eduTeacher);

        boolean flag = eduTeacherService.save(teacher);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 根据ID查询讲师,做数据回显
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        if (StrUtil.isBlank(id)) {
            return R.error().message(MessageConstant.ID_NOT_EMPUT);
        }
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    // 根据ID修改讲师
    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("updateTeacher")
    public R updateById(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher){

        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

