package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hgk
 * @since 2021-06-26
 */
@Api(value = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    // 注入service
    @Autowired
    private EduTeacherService eduTeacherService;

    // 查询讲师列表所有数据
    @ApiOperation(value = "查询所有讲师列表")
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
            @RequestBody EduTeacher eduTeacher){

        boolean flag = eduTeacherService.save(eduTeacher);

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
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("item", teacher);
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

