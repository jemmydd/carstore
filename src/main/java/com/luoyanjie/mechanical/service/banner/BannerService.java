package com.luoyanjie.mechanical.service.banner;

import com.luoyanjie.mechanical.bean.dto.banner.BannerAdminDTO;
import com.luoyanjie.mechanical.bean.dto.banner.BannerDTO;
import com.luoyanjie.mechanical.bean.param.banner.AddBannerParam;
import com.luoyanjie.mechanical.bean.param.banner.ModifyBannerParam;
import com.luoyanjie.mechanical.component.result.PageData;

import java.util.List;

public interface BannerService {
    List<BannerDTO> list(Integer count);

    Boolean addBanner(AddBannerParam param);

    Boolean modifyBanner(ModifyBannerParam param);

    Boolean validBanner(Integer bannerId);

    Boolean unValidBanner(Integer bannerId);

    Boolean deleteBanner(Integer bannerId);

    Boolean deleteBatchBanner(List<Integer> bannerIds);

    BannerAdminDTO getOne(Integer bannerId);

    PageData<BannerAdminDTO> getPageData(Integer pageNum, Integer pageSize);
}
