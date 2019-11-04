package com.luoyanjie.mechanical.service.purchaseInformation;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.luoyanjie.mechanical.bean.common.Constant;
import com.luoyanjie.mechanical.bean.dto.OperateDTO;
import com.luoyanjie.mechanical.bean.dto.SubmitDTO;
import com.luoyanjie.mechanical.bean.dto.location.LocationDetailDTO;
import com.luoyanjie.mechanical.bean.dto.purchaseInformation.PurchaseInformationDTO;
import com.luoyanjie.mechanical.bean.entity.BrandDO;
import com.luoyanjie.mechanical.bean.entity.PurchaseInformationDO;
import com.luoyanjie.mechanical.bean.entity.UserDO;
import com.luoyanjie.mechanical.bean.enumBean.OperateTypeEnum;
import com.luoyanjie.mechanical.bean.param.purchaseInformation.AddPurchaseInformationParam;
import com.luoyanjie.mechanical.bean.param.purchaseInformation.ModifyPurchaseInformationParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.dao.mapper.BrandDOMapper;
import com.luoyanjie.mechanical.dao.mapper.PurchaseInformationDOMapper;
import com.luoyanjie.mechanical.dao.mapper.UserDOMapper;
import com.luoyanjie.mechanical.service.category.CategoryService;
import com.luoyanjie.mechanical.service.user.UserService;
import com.luoyanjie.mechanical.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-08-24 20:24
 * Coding happily every day!
 */
@Slf4j
@Service
public class PurchaseInformationServiceImpl implements PurchaseInformationService {
    @Autowired
    private PurchaseInformationDOMapper purchaseInformationDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandDOMapper brandDOMapper;

    @Override
    public SubmitDTO add(AddPurchaseInformationParam param) {
        /*purchaseInformationDOMapper.insertSelective(
                PurchaseInformationDO.builder()
                        .areaCode(param.getAreaCode())
                        .areaName(param.getAreaName())
                        .categoryFirstId(param.getFirstCategoryId())
                        .categorySecondId(param.getSecondCategoryId())
                        .cityCode(param.getCityCode())
                        .cityName(param.getCityName())
                        .contactMobile(param.getContactPhone())
                        .content(param.getContent())
                        .creator(param.getCreator())
                        .newOldLevel(param.getNewOldLevel())
                        .provinceCode(param.getProvinceCode())
                        .provinceName(param.getProvinceName())
                        .shelfStatus(param.getShelfStatus() == null ? (byte) 1 : param.getShelfStatus())
                        .title(param.getTitle())
                        .viewCount(0)
                        .build()
        );*/

        PurchaseInformationDO purchaseInformationDO = PurchaseInformationDO.builder()
                .areaCode(param.getAreaCode())
                .areaName(param.getAreaName())
                .categoryFirstId(param.getFirstCategoryId())
                .categorySecondId(param.getSecondCategoryId())
                .cityCode(param.getCityCode())
                .cityName(param.getCityName())
                .contactMobile(param.getContactPhone())
                .content(param.getContent())
                .creator(param.getCreator())
                .newOldLevel(param.getNewOldLevel())
                .provinceCode(param.getProvinceCode())
                .provinceName(param.getProvinceName())
                .shelfStatus(param.getShelfStatus() == null ? (byte) 1 : param.getShelfStatus())
                .title(param.getTitle())
                .viewCount(0)

                .expectedPrice(param.getExpectedPrice())
                .usageHours(param.getUsageHours())
                .yearLimitNum(param.getYearLimitNum())
                .hasInvoice(param.getHasInvoice())
                .hasCertificate(param.getHasCertificate())
                .contact(param.getContact())
                .remark(param.getRemark())

                .brandId(param.getBrandId())

                .build();

        purchaseInformationDOMapper.insertSelective(purchaseInformationDO);

        userDOMapper.addPurchaseInformationCount(param.getCreator());

        return SubmitDTO.builder()
                .id(purchaseInformationDO.getId())
                .succeed(true)
                .build();
    }


    @Override
    public Boolean delete(Integer purchaseInformationId) {
        if (purchaseInformationId == null) throw new RuntimeException("求购信息ID不能为空");

        purchaseInformationDOMapper.deleteByPrimaryKey(purchaseInformationId);

        return true;
    }

    @Override
    public Boolean deleteBatch(List<Integer> purchaseInformationIds) {
        if (ObjectUtils.isEmpty(purchaseInformationIds)) throw new RuntimeException("求购信息IDs不能为空");

        for (Integer purchaseInformationId : purchaseInformationIds) {
            delete(purchaseInformationId);
        }

        return true;
    }

    @Override
    public SubmitDTO modify(ModifyPurchaseInformationParam param) {
        if (param.getId() == null) throw new RuntimeException("ID不能为空");

        PurchaseInformationDO old = purchaseInformationDOMapper.selectByPrimaryKey(param.getId());
        if (old != null) {
            old.setAreaCode(StringUtils.isEmpty(param.getAreaCode()) ? null : param.getAreaCode());
            old.setAreaName(StringUtils.isEmpty(param.getAreaName()) ? null : param.getAreaName());
            old.setCategoryFirstId(param.getFirstCategoryId());
            old.setCategorySecondId(param.getSecondCategoryId());
            old.setCityCode(StringUtils.isEmpty(param.getCityCode()) ? null : param.getCityCode());
            old.setCityName(StringUtils.isEmpty(param.getCityName()) ? null : param.getCityName());
            old.setContact(StringUtils.isEmpty(param.getContact()) ? null : param.getContact());
            old.setContactMobile(StringUtils.isEmpty(param.getContactPhone()) ? null : param.getContactPhone());
            old.setContent(StringUtils.isEmpty(param.getContent()) ? null : param.getContent());
            old.setCreateTime(old.getCreateTime());
            old.setCreator(old.getCreator());
            old.setExpectedPrice(StringUtils.isEmpty(param.getExpectedPrice()) ? null : param.getExpectedPrice());
            old.setHasCertificate(param.getHasCertificate());
            old.setHasInvoice(param.getHasInvoice());
            old.setId(old.getId());
            old.setNewOldLevel(StringUtils.isEmpty(param.getNewOldLevel()) ? null : param.getNewOldLevel());
            old.setProvinceCode(StringUtils.isEmpty(param.getProvinceCode()) ? null : param.getProvinceCode());
            old.setProvinceName(StringUtils.isEmpty(param.getProvinceName()) ? null : param.getProvinceName());
            old.setRemark(StringUtils.isEmpty(param.getRemark()) ? null : param.getRemark());
            old.setShelfStatus(param.getShelfStatus() == null ? old.getShelfStatus() : param.getShelfStatus());
            old.setTitle(StringUtils.isEmpty(param.getTitle()) ? null : param.getTitle());
            old.setUpdateTime(old.getUpdateTime());
            old.setUsageHours(StringUtils.isEmpty(param.getUsageHours()) ? null : param.getUsageHours());
            old.setViewCount(old.getViewCount());
            old.setYearLimitNum(StringUtils.isEmpty(param.getYearLimitNum()) ? null : param.getYearLimitNum());
            old.setBrandId(param.getBrandId());
            purchaseInformationDOMapper.updateByPrimaryKey(old);
        }

        return SubmitDTO.builder()
                .id(param.getId())
                .succeed(true)
                .build();
    }

    @Override
    public PageData<PurchaseInformationDTO> search(
            Integer pageNum, Integer pageSize,
            Integer categoryFirstId, Integer categorySecondId,
            String provinceCode, String cityCode, String areaCode,
            String keyword, String contactMobile, Byte shelfStatus, Integer creator,
            Integer brandId
    ) {
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<PurchaseInformationDO> pd = (Page<PurchaseInformationDO>) purchaseInformationDOMapper.search(
                categoryFirstId, categorySecondId,
                provinceCode, cityCode, areaCode,
                keyword, contactMobile, shelfStatus, creator, brandId
        );

        List<PurchaseInformationDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        Map<Integer, UserDO> userDOMap = userService.getUserMap(data.stream().map(PurchaseInformationDO::getCreator).distinct().collect(Collectors.toList()));

        Map<Integer, String> cm = categoryService.allMap();

        return PageData.data(pd,
                data.stream()
                        .map(row -> cov(row, null, userDOMap.get(row.getCreator()), cm))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public PurchaseInformationDTO getOne(Integer purchaseInformationId) {
        if (purchaseInformationId == null) throw new RuntimeException("采购信息ID不能为空");

        PurchaseInformationDO purchaseInformationDO = purchaseInformationDOMapper.searchById(purchaseInformationId);
        if (purchaseInformationDO == null) throw new RuntimeException("采购信息不存在");

        purchaseInformationDOMapper.addViewCountCount(purchaseInformationId);//增加浏览量

        UserDO userDO = userDOMapper.selectByPrimaryKey(purchaseInformationDO.getCreator());

        Map<Integer, String> cm = categoryService.allMap();

        return cov(purchaseInformationDO, null, userDO, cm);
    }

    @Override
    public Boolean down(Integer userId, Integer purchaseInformationId) {
        if (userId == null) throw new RuntimeException("用户ID不能为空");
        if (purchaseInformationId == null) throw new RuntimeException("采购信息ID不能为空");

        purchaseInformationDOMapper.updateByPrimaryKeySelective(
                PurchaseInformationDO.builder()
                        .id(purchaseInformationId)
                        .shelfStatus((byte) 0)
                        .build()
        );

        return true;
    }

    private void checkInParam(AddPurchaseInformationParam param) {
        if (param.getCreator() == null) throw new RuntimeException("发布求购信息的人不能为空");
        if (StringUtils.isEmpty(param.getAreaCode())) throw new RuntimeException("区编码不能为空");
        if (StringUtils.isEmpty(param.getAreaName())) throw new RuntimeException("区名称不能为空");
        if (StringUtils.isEmpty(param.getCityCode())) throw new RuntimeException("城市编码不能为空");
        if (StringUtils.isEmpty(param.getCityName())) throw new RuntimeException("城市名称不能为空");
        if (StringUtils.isEmpty(param.getProvinceCode())) throw new RuntimeException("省份编码不能为空");
        if (StringUtils.isEmpty(param.getProvinceName())) throw new RuntimeException("省份名称不能为空");
        if (param.getFirstCategoryId() == null) throw new RuntimeException("一级分类不能为空");
        if (param.getSecondCategoryId() == null) throw new RuntimeException("二级分类不能为空");
        if (StringUtils.isEmpty(param.getTitle())) throw new RuntimeException("标题不能为空");
    }

    private PurchaseInformationDTO cov(PurchaseInformationDO row, Integer requestUserId, UserDO creator, Map<Integer, String> cm) {
        BrandDO brandDO = row.getBrandId() == null ? null : brandDOMapper.selectByPrimaryKey(row.getBrandId());

        return PurchaseInformationDTO.builder()
                .categoryFirstId(row.getCategoryFirstId())
                .categoryFirstName(cm.get(row.getCategoryFirstId()))
                .categorySecondId(row.getCategorySecondId())
                .categorySecondName(cm.get(row.getCategorySecondId()))
                .contactMobile(StringUtils.isEmpty(row.getContactMobile()) ? "" : row.getContactMobile())
                .content(StringUtils.isEmpty(row.getContent()) ? "" : row.getContent())
                .createTime(DateFormatUtils.format(row.getCreateTime(), Constant.DATE_FORMAT))
                .createTimeFriendly(DateUtil.formatDate(row.getCreateTime()))
                .creator(row.getCreator())
                .creatorInfo(userService.getUser(creator))
                .id(row.getId())
                .locationDetail(getLocationDetail(row))
                .newOldLevel(StringUtils.isEmpty(row.getNewOldLevel()) ? "" : row.getNewOldLevel())
                .operates(getOperates(row, requestUserId))
                .shelfStatus(row.getShelfStatus())
                .title(StringUtils.isEmpty(row.getTitle()) ? "" : row.getTitle())
                .viewCount(row.getViewCount())

                .expectedPrice(row.getExpectedPrice())
                .usageHours(row.getUsageHours())
                .yearLimitNum(row.getYearLimitNum())
                .hasInvoice(row.getHasInvoice())
                .hasCertificate(row.getHasCertificate())
                .contact(row.getContact())
                .remark(row.getRemark())

                .brandId(row.getBrandId())
                .brandName(brandDO == null ? "" : brandDO.getName())

                .build();
    }

    private LocationDetailDTO getLocationDetail(PurchaseInformationDO row) {
        return LocationDetailDTO.builder()
                .areaCode(row.getAreaCode())
                .areaName(row.getAreaName())
                .cityCode(row.getCityCode())
                .cityName(row.getCityName())
                .location(userService.getUserLocation(row.getProvinceName(), row.getCityName(), row.getAreaName()))
                .provinceCode(row.getProvinceCode())
                .provinceName(row.getProvinceName())
                .build();
    }

    private List<OperateDTO> getOperates(PurchaseInformationDO row, Integer requestUserId) {
        List<OperateDTO> data = Lists.newArrayList();

        if (Objects.equals(row.getShelfStatus(), (byte) 1)) {
            Lists.newArrayList(
                    OperateDTO.getOperate(OperateTypeEnum.PI_OFF)
            );
        } else {
            Lists.newArrayList(
                    OperateDTO.getOperate(OperateTypeEnum.PI_MODIFY),
                    OperateDTO.getOperate(OperateTypeEnum.PI_ON)
            );
        }

        return data;
    }
}
