package com.atguigu.eduservice.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.eduservice.constant.MessageConstant;
import com.atguigu.eduservice.entity.po.EduTeacherPO;
import com.atguigu.eduservice.entity.vo.TeacherAdd;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.commonutils.response.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        value = "EduTeacherPO-description",
        tags = {"讲师管理"}
)
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    // 注入service
    @Autowired
    private EduTeacherService eduTeacherService;

    // 查询讲师列表所有数据
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
            LambdaQueryWrapper<EduTeacherPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByDesc(EduTeacherPO::getGmtCreate, EduTeacherPO::getGmtModified);
            List<EduTeacherPO> teacherList = eduTeacherService.list(queryWrapper);
            return R.ok().data("items",teacherList);
    }

    // TODO 如果先查询 后删除，删除后要保持查询条件;新增加的在第一行；在第二页修改 一条记录，修改完后依旧跳到第二页
    // 逻辑删除
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id
    ){

        if (StrUtil.isEmpty(id)) {
            return R.error().message(MessageConstant.ID_NOT_EMPUT);
        }

        EduTeacherPO eduTeacherPO = eduTeacherService.getById(id);

        if (ObjectUtil.isEmpty(eduTeacherPO)) {
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
        Page<EduTeacherPO> pageParam = new Page<>(page, limit);
        eduTeacherService.page(pageParam, null);
        List<EduTeacherPO> records = pageParam.getRecords();  // list集合
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

        Page<EduTeacherPO> pageParam = new Page<>(page, limit);

        eduTeacherService.pageQuery(pageParam, teacherQuery);

        List<EduTeacherPO> records = pageParam.getRecords(); // list集合

        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    // 新增讲师
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody @Valid TeacherAdd eduTeacher){

        // TODO 有没有简单的查询方法
        QueryWrapper<EduTeacherPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                //.eq(EduTeacherPO::getName,eduTeacher.getName())
                .eq(EduTeacherPO::getIntro,eduTeacher.getIntro())
                .eq(EduTeacherPO::getCareer,eduTeacher.getCareer())
                .eq(EduTeacherPO::getLevel,eduTeacher.getLevel())
                .eq(EduTeacherPO::getAvatar,eduTeacher.getAvatar())
                .eq(EduTeacherPO::getSort,eduTeacher.getSort());

        EduTeacherPO teacher1 = eduTeacherService.getOne(queryWrapper);

        if (ObjectUtil.isNotEmpty(teacher1)) {
            return R.error().message(MessageConstant.USER_HAVE);
        }

        EduTeacherPO teacher = new EduTeacherPO();
        BeanUtils.copyProperties(eduTeacher,teacher);

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
        EduTeacherPO teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    // 根据ID修改讲师
    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("updateTeacher")
    public R updateById(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacherPO eduTeacherPO){

        boolean flag = eduTeacherService.updateById(eduTeacherPO);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

