package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.enums.ResultCodeEnum;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.po.EduVideoPO;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author hgk
 * @since 2021-08-02
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {

    @Resource
    private EduVideoService eduVideoService;

    /**
     * 添加小节
     * @param eduVideoPO
     * @return
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideoPO eduVideoPO) {
        boolean flag = eduVideoService.save(eduVideoPO);
        if (!flag) {
            throw new GuliException(ResultCodeEnum.ERROR.getResultCode(), "添加小节失败");
        }
        return R.ok().message("添加小节成功");
    }

    /**
     * 删除小节
     * @param id
     * @return
     */
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable("id") String id) {
        R r = eduVideoService.removeVideoById(id);
        return r;
    }

    @PutMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideoPO eduVideoPO) {
        boolean flag = eduVideoService.updateById(eduVideoPO);
        if (!flag) {
            throw new GuliException(ResultCodeEnum.ERROR.getResultCode(), "修改小节失败");
        }
        return R.ok().message("修改小节成功");
    }

    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable("id") String id) {
        EduVideoPO eduVideoPO = eduVideoService.getById(id);
        return R.ok().data("video",eduVideoPO);
    }

}

