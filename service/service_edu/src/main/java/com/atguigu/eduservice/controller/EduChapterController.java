package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduChapterPO;
import com.atguigu.eduservice.entity.vo.ChapterVO;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hgk
 * @since 2021-08-02
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 根据课程id查询其下的所有章节和小节
     * @param courseId
     * @return
     */
    @GetMapping("getAllChapterVideo/{courseId}")
    public R getAllChapterVideo(@PathVariable("courseId") String courseId) {
        R r  = eduChapterService.getAllChapterVideo(courseId);
        return r;
    }

    /**
     * 添加章节
     * @param eduChapterPO
     * @return
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapterPO eduChapterPO) {
        boolean flag = eduChapterService.save(eduChapterPO);
        if (flag) {
            return R.ok().message("添加成功");
        }
        return R.error().message("添加失败");
    }

    // 根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable("chapterId") String id) {
        EduChapterPO eduChapterPO = eduChapterService.getById(id);
        return R.ok().data("chapter",eduChapterPO);
    }

    // 修改章节
    // TODO 乐观锁
    @PutMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapterPO eduChapterPO) {
        EduChapterPO eduChapterPO1 = eduChapterService.getById(eduChapterPO.getId());
        if (eduChapterPO.equals(eduChapterPO1)) {
            return R.error().message("未作修改");
        }
        boolean b = eduChapterService.updateById(eduChapterPO);
        if (b) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("修改失败");
        }
    }

    /**
     * 删除章节，如果章节下面还有小节就不让删除，若没有才让删除
     * @param chapterId
     * @return
     */
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId) {
        R r = eduChapterService.deleteChapterByChapterId(chapterId);
        return r;
    }
}

