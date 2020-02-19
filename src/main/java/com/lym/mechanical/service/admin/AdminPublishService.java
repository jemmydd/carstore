package com.lym.mechanical.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.dto.admin.AdminPublishDTO;
import com.lym.mechanical.bean.dto.admin.AdminPublishRecordDTO;
import com.lym.mechanical.bean.dto.admin.CarUserDTO;
import com.lym.mechanical.bean.dto.admin.UserLookRecordDTO;
import com.lym.mechanical.bean.entity.CarUserCollectionDO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.PublishDO;
import com.lym.mechanical.bean.entity.PublishLookRecordDO;
import com.lym.mechanical.bean.param.admin.AdminPublishSearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.CarUserCollectionDOMapper;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import com.lym.mechanical.dao.mapper.PublishDOMapper;
import com.lym.mechanical.dao.mapper.PublishLookRecordDOMapper;
import com.lym.mechanical.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @create 2020-02-19 16:34
 * Good good code, day day up!
 **/
@Service
public class AdminPublishService {

    @Autowired
    private PublishDOMapper publishDOMapper;

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private CarUserCollectionDOMapper carUserCollectionDOMapper;

    @Autowired
    private PublishLookRecordDOMapper publishLookRecordDOMapper;

    public PageData<AdminPublishDTO> list(AdminPublishSearchParam param) {
        PageData.checkPageParam(param.getPageNum(), param.getPageSize());
        PageHelper.startPage(param.getPageNum(), param.getPageSize());

        Page<PublishDO> publishDOS = (Page<PublishDO>) publishDOMapper.selectForWeb(param.getNickName(), param.getMobile(), param.getUserId());
        List<PublishDO> data = publishDOS.getResult();
        if (ObjectUtils.isEmpty(data)) {
            return PageData.noData(param.getPageSize());
        }

        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(data.stream().map(PublishDO::getCarUserId).distinct().collect(Collectors.toList()));
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        List<Integer> publishIds = data.stream().map(PublishDO::getId).collect(Collectors.toList());
        List<CarUserCollectionDO> collectionDOS = carUserCollectionDOMapper.selectBatchByPublishId(publishIds);
        Map<Integer, List<CarUserCollectionDO>> collectMap = ObjectUtils.isEmpty(collectionDOS) ? Maps.newHashMap() :
                collectionDOS.stream().collect(Collectors.groupingBy(CarUserCollectionDO::getPublishId));
        List<PublishLookRecordDO> recordDOS = publishLookRecordDOMapper.selectBatchByPublishId(publishIds);
        Map<Integer, List<PublishLookRecordDO>> lookMap = ObjectUtils.isEmpty(recordDOS) ? Maps.newHashMap() :
                recordDOS.stream().collect(Collectors.groupingBy(PublishLookRecordDO::getPublishId));
        return PageData.data(publishDOS, data.stream().map(row -> {
            CarUserDO carUserDO = userMap.get(row.getCarUserId());
            List<CarUserCollectionDO> collectList = collectMap.get(row.getId());
            List<PublishLookRecordDO> lookList = lookMap.get(row.getId());
            return AdminPublishDTO.builder()
                    .publishId(row.getId())
                    .askCount(ObjectUtils.isEmpty(lookList) ? 0 : lookList.stream().filter(l -> l.getHasDial()).map(PublishLookRecordDO::getUserId).distinct().collect(Collectors.toList()).size())
                    .collectCount(ObjectUtils.isEmpty(collectList) ? 0 : collectList.stream().map(CarUserCollectionDO::getCarUserId).distinct().collect(Collectors.toList()).size())
                    .createTime(DateUtil.formatDateDefault(row.getCreateTime()))
                    .hasCertificate(Objects.isNull(row.getHasCertificate()) || Objects.equals((byte) 0, row.getHasCertificate()) ? "无" : "有")
                    .hasInvoice(Objects.isNull(row.getHasInvoice()) || Objects.equals((byte) 0, row.getHasInvoice()) ? "无" : "有")
                    .image(row.getMainMedia())
                    .introduce(row.getTextIntroduce())
                    .location((StringUtils.isEmpty(row.getProvinceName()) ? "" : row.getProvinceName()) + (StringUtils.isEmpty(row.getCityName()) ? "" : row.getCityName()))
                    .lookCount((ObjectUtils.isEmpty(lookList) ? 0 : lookList.stream().map(PublishLookRecordDO::getUserId).distinct().collect(Collectors.toList()).size()) + "人")
                    .lookTimes((ObjectUtils.isEmpty(lookList) ? 0 : lookList.size()) + "次")
                    .price(StringUtils.isEmpty(row.getOutPrice()) ? "未知" : (row.getOutPrice() + "万"))
                    .productiveYear(row.getProductiveYear().toString())
                    .title(row.getTitle())
                    .type(row.getType())
                    .usageHours(StringUtils.isEmpty(row.getUsageHours()) ? "" : (row.getUsageHours() + "小时"))
                    .user(Objects.isNull(carUserDO) ? null : CarUserDTO.builder()
                            .avatar(carUserDO.getHeadPortrait())
                            .mobile(carUserDO.getPhone())
                            .nickName(carUserDO.getNickName())
                            .build())
                    .build();
        }).collect(Collectors.toList()));
    }

    public PageData<AdminPublishRecordDTO> recordList(Integer publishId, String type, Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        if (Objects.equals("0", type)) {
            // 浏览的访客
            Page<UserLookRecordDTO> userLookRecordDTOS = (Page<UserLookRecordDTO>) publishLookRecordDOMapper.selectUserLookByPublishId(publishId);
            List<UserLookRecordDTO> data = userLookRecordDTOS.getResult();
            if (ObjectUtils.isEmpty(data)) {
                return PageData.noData(pageSize);
            }
            PublishDO publishDO = publishDOMapper.selectByPrimaryKey(publishId);
            List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(data.stream().map(UserLookRecordDTO::getUserId).distinct().collect(Collectors.toList()));
            Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                    carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
            return PageData.data(userLookRecordDTOS, data.stream().map(row -> {
                CarUserDO carUserDO = userMap.get(row.getUserId());
                return AdminPublishRecordDTO.builder()
                        .image(Objects.isNull(publishDO) ? "" : publishDO.getMainMedia())
                        .time(DateUtil.formatDateDefault(row.getLastTime()))
                        .title(Objects.isNull(publishDO) ? "" : publishDO.getTitle())
                        .type(Objects.isNull(publishDO) ? "" : publishDO.getType())
                        .user(Objects.isNull(carUserDO) ? null : CarUserDTO.builder()
                                .avatar(carUserDO.getHeadPortrait())
                                .mobile(carUserDO.getPhone())
                                .nickName(carUserDO.getNickName())
                                .build())
                        .build();
            }).collect(Collectors.toList()));
        } else {
            // 收藏的访客
            Page<UserLookRecordDTO> userLookRecordDTOS = (Page<UserLookRecordDTO>) carUserCollectionDOMapper.selectUserCollectByPublishId(publishId);
            List<UserLookRecordDTO> data = userLookRecordDTOS.getResult();
            if (ObjectUtils.isEmpty(data)) {
                return PageData.noData(pageSize);
            }
            PublishDO publishDO = publishDOMapper.selectByPrimaryKey(publishId);
            List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(data.stream().map(UserLookRecordDTO::getUserId).distinct().collect(Collectors.toList()));
            Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                    carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
            return PageData.data(userLookRecordDTOS, data.stream().map(row -> {
                CarUserDO carUserDO = userMap.get(row.getUserId());
                return AdminPublishRecordDTO.builder()
                        .image(Objects.isNull(publishDO) ? "" : publishDO.getMainMedia())
                        .time(DateUtil.formatDateDefault(row.getLastTime()))
                        .title(Objects.isNull(publishDO) ? "" : publishDO.getTitle())
                        .type(Objects.isNull(publishDO) ? "" : publishDO.getType())
                        .user(Objects.isNull(carUserDO) ? null : CarUserDTO.builder()
                                .avatar(carUserDO.getHeadPortrait())
                                .mobile(carUserDO.getPhone())
                                .nickName(carUserDO.getNickName())
                                .build())
                        .build();
            }).collect(Collectors.toList()));
        }
    }
}
