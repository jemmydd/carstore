package com.luoyanjie.mechanical.service.banner;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.luoyanjie.mechanical.bean.dto.banner.BannerAdminDTO;
import com.luoyanjie.mechanical.bean.dto.banner.BannerDTO;
import com.luoyanjie.mechanical.bean.entity.BannerDO;
import com.luoyanjie.mechanical.bean.entity.UserDO;
import com.luoyanjie.mechanical.bean.param.banner.AddBannerParam;
import com.luoyanjie.mechanical.bean.param.banner.ModifyBannerParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.dao.mapper.BannerDOMapper;
import com.luoyanjie.mechanical.dao.mapper.UserDOMapper;
import com.luoyanjie.mechanical.service.user.UserService;
import com.luoyanjie.mechanical.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-08-10 18:58
 * Coding happily every day!
 */
@Slf4j
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDOMapper bannerDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserService userService;

    private static final Integer DEFAULT_COUNT = 4;

    @Override
    public List<BannerDTO> list(Integer count) {
        if (count == null || count <= 0) count = DEFAULT_COUNT;

        List<BannerDO> bannerDOS = bannerDOMapper.selectLimit(count);

        List<BannerDTO> result = Lists.newArrayList();

        if (!ObjectUtils.isEmpty(bannerDOS)) {
            result.addAll(
                    bannerDOS.stream()
                            .map(row -> BannerDTO.builder()
                                    .bindId(row.getBindId())
                                    .canJump(row.getBindId() != null)
                                    .id(row.getId())
                                    .image(row.getImage())
                                    .url(row.getUrl())
                                    .build())
                            .collect(Collectors.toList())
            );
        }

        return result;
    }

    @Override
    public Boolean addBanner(AddBannerParam param) {
        if (param.getUserId() == null) throw new RuntimeException("登录用户ID不能为空");
        if (StringUtils.isEmpty(param.getImage())) throw new RuntimeException("轮播图不能为空");

        bannerDOMapper.insertSelective(
                BannerDO.builder()
                        .bindId(param.getBindId())
                        .image(param.getImage())
                        .isValid(formatIsValid(param.getIsValid()))
                        .userId(param.getUserId())
                        .url(param.getUrl())
                        .build()
        );

        return true;
    }

    @Override
    public Boolean modifyBanner(ModifyBannerParam param) {
        if (param.getId() == null) throw new RuntimeException("轮播ID不能为空");
        if (param.getUserId() == null) throw new RuntimeException("登录用户ID不能为空");
        if (StringUtils.isEmpty(param.getImage())) throw new RuntimeException("轮播图不能为空");
        if (StringUtils.isEmpty(param.getUrl())) param.setUrl("");

        bannerDOMapper.update(param.getId(), formatIsValid(param.getIsValid()), param.getBindId(), param.getImage(), param.getUrl());

        return true;
    }

    @Override
    public Boolean validBanner(Integer bannerId) {
        if (bannerId == null) throw new RuntimeException("轮播ID不能为空");

        bannerDOMapper.updateByPrimaryKeySelective(
                BannerDO.builder()
                        .id(bannerId)
                        .isValid((byte) 1)
                        .build()
        );

        return true;
    }

    @Override
    public Boolean unValidBanner(Integer bannerId) {
        if (bannerId == null) throw new RuntimeException("轮播ID不能为空");

        bannerDOMapper.updateByPrimaryKeySelective(
                BannerDO.builder()
                        .id(bannerId)
                        .isValid((byte) 0)
                        .build()
        );

        return true;
    }

    @Override
    public Boolean deleteBanner(Integer bannerId) {
        if (bannerId == null) throw new RuntimeException("轮播ID不能为空");

        bannerDOMapper.deleteByPrimaryKey(bannerId);

        return true;
    }

    @Override
    public Boolean deleteBatchBanner(List<Integer> bannerIds) {
        if (ObjectUtils.isEmpty(bannerIds)) throw new RuntimeException("轮播ID不能为空");

        for (Integer bannerId : bannerIds) {
            deleteBanner(bannerId);
        }

        return true;
    }

    @Override
    public BannerAdminDTO getOne(Integer bannerId) {
        if (bannerId == null) throw new RuntimeException("轮播ID不能为空");

        BannerDO bannerDO = bannerDOMapper.selectByPrimaryKey(bannerId);
        if (bannerDO == null) throw new RuntimeException("轮播不存在");

        return cav(bannerDO, userDOMapper.selectByPrimaryKey(bannerDO.getBindId()));
    }

    @Override
    public PageData<BannerAdminDTO> getPageData(Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<BannerDO> pd = (Page<BannerDO>) bannerDOMapper.select();

        List<BannerDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        Map<Integer, UserDO> userDOMap = Maps.newHashMap();
        List<Integer> bindIds = data.stream().filter(row -> row.getBindId() != null).map(BannerDO::getBindId).distinct().collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(bindIds)) {
            userDOMap.putAll(userService.getUserMap(bindIds));
        }

        return PageData.data(pd, data.stream().map(row -> cav(row, userDOMap.get(row.getBindId()))).collect(Collectors.toList()));
    }

    private BannerAdminDTO cav(BannerDO row, UserDO bind) {
        return BannerAdminDTO.builder()
                .bind(bind == null ? null : userService.getUser(bind))
                .bindId(row.getBindId())
                .createTime(DateUtil.formatDateDefault(row.getCreateTime()))
                .id(row.getId())
                .image(row.getImage())
                .isValid(row.getIsValid())
                .updateTime(DateUtil.formatDateDefault(row.getUpdateTime()))
                .url(row.getUrl())
                .build();
    }

    private Byte formatIsValid(Boolean isValid) {
        return (isValid != null && isValid) ? (byte) 1 : (byte) 0;
    }
}
