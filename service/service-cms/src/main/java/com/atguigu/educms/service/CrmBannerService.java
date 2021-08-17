package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author hgk
 * @since 2021-08-15
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectIndexList();

    public List<CrmBanner> selectIndexListNew();
}
