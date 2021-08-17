package com.atguigu.educms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.util.RedisKeyConstantUtil;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.junit.rules.Timeout;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author hgk
 * @since 2021-08-15
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 查询所有幻灯片
    /**
     * @Cacheable 注解
     * 根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据返回；如果缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上。
     * 属性/方法名:
     *      1:value---缓存名，必填，它指定了你的缓存存放在哪块命名空间
     *      2:cacheNames---与 value 差不多，二选一即可
     *      3:key---可选属性，可以使用 SpEL 标签自定义缓存的key
     */
    @Cacheable(key = "'selectIndexList'",value = "banner")
    @Override
    public List<CrmBanner> selectIndexList() {
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }

    /**
     * 查询首页轮播图，redis存在从redis取，不存在则查询数据库并存入redis
     * @return
     */
    public List<CrmBanner> selectIndexListNew() {
        // 查询redis,有则返回
        String banner = redisTemplate.opsForValue().get(RedisKeyConstantUtil.getBannerKey());
        if (StrUtil.isBlank(banner)) {
            List<CrmBanner> banners = JSONArray.parseArray(banner, CrmBanner.class);
            return banners;
        }
        // 没有查询数据库
        List<CrmBanner> list = baseMapper.selectList(null);
        if (CollUtil.isNotEmpty(list)) {
            // 设置到redis中，设置过期时间---1天
            String bannerStr = JSON.toJSONString(list);
            redisTemplate.opsForValue().setIfAbsent(RedisKeyConstantUtil.getBannerKey(),bannerStr,1, TimeUnit.DAYS);
        }
        // 返回
        return list;
    }

}
