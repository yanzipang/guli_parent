package com.atguigu.eduservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.commonutils.enums.ResultCodeEnum;
import com.atguigu.commonutils.response.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.po.EduVideoPO;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author hgk
 * @since 2021-08-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideoPO> implements EduVideoService {

    // 注入VodClient（远程服务）
    @Autowired
    private VodClient vodClient;

    @Resource
    private EduVideoMapper eduVideoMapper;

    @Resource(name = "myExecutor")
    private Executor myExecutor;

    /**
     * 删除小节删除视频
     * @param id
     * @return
     */
    @Override
    public R removeVideoById(String id) {
        EduVideoPO eduVideoPO = eduVideoMapper.selectById(id);
        if (BeanUtil.isEmpty(eduVideoPO)) {
            return R.error().message("要删除的小节不存在");
        }
        int i = eduVideoMapper.deleteById(id);
        if (i < 0) {
            return R.error().message("删除小节失败");
        }
        // 判断是否有小节视频id
//        if (StrUtil.isNotBlank(eduVideoPO.getVideoSourceId())) {
//            // 异步删除阿里云视频
//            CompletableFuture.runAsync(() -> {
//                vodClient.removeVideo(eduVideoPO.getVideoSourceId());
//            },myExecutor);
//        }
        if (StrUtil.isNotBlank(eduVideoPO.getVideoSourceId())) {
            R r = vodClient.removeVideo(eduVideoPO.getVideoSourceId());
            if (!r.getCode().equals(ResultCodeEnum.SUCCESS.getResultCode())) {
                throw new GuliException(r.getCode(),"删除视频失败，熔断器...");
            }
        }

        return R.ok().message("删除小节成功");
    }
}
