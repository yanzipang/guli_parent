package com.atguigu.eduservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.entity.po.EduChapterPO;
import com.atguigu.eduservice.entity.po.EduCoursePO;
import com.atguigu.eduservice.entity.po.EduVideoPO;
import com.atguigu.eduservice.entity.vo.ChapterVO;
import com.atguigu.eduservice.entity.vo.VideoVO;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hgk
 * @since 2021-08-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapterPO> implements EduChapterService {

    @Resource
    private EduChapterMapper eduChapterMapper;

    @Resource
    private EduVideoMapper eduVideoMapper;

    @Resource
    private EduCourseMapper eduCourseMapper;

    @Override
    public R getAllChapterVideo(String courseId) {
        // 先查询有无此课程
//        EduCoursePO eduCoursePO = eduCourseMapper.selectById(courseId);
//        if (ObjectUtil.isNull(eduCoursePO)) {
//            return R.error().message("无此课程");
//        }
        // 查询章节
        LambdaQueryWrapper<EduChapterPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EduChapterPO::getCourseId,courseId);
        List<EduChapterPO> eduChapterPOS = eduChapterMapper.selectList(lambdaQueryWrapper);
//        if (ObjectUtil.isEmpty(eduChapterPOS)) {
//            return R.error().message("章节为空");
//        }
        // 查询小节
        LambdaQueryWrapper<EduVideoPO> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(EduVideoPO::getCourseId,courseId);
        List<EduVideoPO> eduVideoPOS = eduVideoMapper.selectList(lambdaQueryWrapper1);
        List<ChapterVO> chapterVOList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(eduVideoPOS)) {
            // 将小节按照章节分组
            Map<String, List<EduVideoPO>> collect = eduVideoPOS.stream().collect(Collectors.groupingBy(EduVideoPO::getChapterId));
            eduChapterPOS.forEach(k -> {
                ChapterVO chapterVO = new ChapterVO();
                chapterVO.setId(k.getId());
                chapterVO.setTitle(k.getTitle());
                List<EduVideoPO> eduVideoPOS1 = collect.get(k.getId());
                if (ObjectUtil.isNotEmpty(eduVideoPOS1)) {
                    List<VideoVO> videoVOS = new ArrayList<>();
                    eduVideoPOS1.forEach(k1 -> {
                        VideoVO videoVO = new VideoVO();
                        BeanUtils.copyProperties(k1,videoVO);
                        videoVOS.add(videoVO);
                    });
                    chapterVO.setChildren(videoVOS);
                }
                chapterVOList.add(chapterVO);
            });
        } else {
            eduChapterPOS.forEach(k -> {
                ChapterVO chapterVO = new ChapterVO();
                chapterVO.setId(k.getId());
                chapterVO.setTitle(k.getTitle());
                chapterVOList.add(chapterVO);
            });
        }

        return R.ok().data("allChapterVideo",chapterVOList);
    }
}
