package com.lym.mechanical.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.dto.admin.AdminLookRecordDTO;
import com.lym.mechanical.bean.dto.admin.AdminPublishRecordDTO;
import com.lym.mechanical.bean.dto.admin.AdminUserDTO;
import com.lym.mechanical.bean.dto.admin.CarUserDTO;
import com.lym.mechanical.bean.dto.admin.UserLookRecordDTO;
import com.lym.mechanical.bean.entity.CarUserApplyDO;
import com.lym.mechanical.bean.entity.CarUserCollectionDO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.NameCardDO;
import com.lym.mechanical.bean.entity.NameCardLookRecordDO;
import com.lym.mechanical.bean.entity.PublishDO;
import com.lym.mechanical.bean.entity.VipRecordDO;
import com.lym.mechanical.bean.param.admin.AdminUserSearchParam;
import com.lym.mechanical.bean.param.admin.AdminVipParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.CarUserApplyDOMapper;
import com.lym.mechanical.dao.mapper.CarUserCollectionDOMapper;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import com.lym.mechanical.dao.mapper.NameCardDOMapper;
import com.lym.mechanical.dao.mapper.NameCardLookRecordDOMapper;
import com.lym.mechanical.dao.mapper.PublishDOMapper;
import com.lym.mechanical.dao.mapper.VipRecordDOMapper;
import com.lym.mechanical.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @create 2020-02-20 13:24
 * Good good code, day day up!
 **/
@Service
public class AdminUserService {

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private CarUserCollectionDOMapper carUserCollectionDOMapper;

    @Autowired
    private NameCardLookRecordDOMapper nameCardLookRecordDOMapper;

    @Autowired
    private VipRecordDOMapper vipRecordDOMapper;

    @Autowired
    private PublishDOMapper publishDOMapper;

    @Autowired
    private NameCardDOMapper nameCardDOMapper;

    @Autowired
    private CarUserApplyDOMapper carUserApplyDOMapper;

    public PageData<AdminUserDTO> list(AdminUserSearchParam param) {
        PageData.checkPageParam(param.getPageNum(), param.getPageSize());
        PageHelper.startPage(param.getPageNum(), param.getPageSize());

        Page<CarUserDO> carUserDOS = (Page<CarUserDO>) carUserDOMapper.selectForWeb(param.getNickName(), param.getMobile(), param.getUserId(), null, "0");
        List<CarUserDO> data = carUserDOS.getResult();
        if (ObjectUtils.isEmpty(data)) {
            return PageData.noData(param.getPageSize());
        }
        List<Integer> userIds = data.stream().map(CarUserDO::getId).collect(Collectors.toList());
        List<CarUserCollectionDO> collectionDOS = carUserCollectionDOMapper.selectBatchByUserId(userIds);
        Map<Integer, List<CarUserCollectionDO>> collectMap = ObjectUtils.isEmpty(collectionDOS) ? Maps.newHashMap() :
                collectionDOS.stream().collect(Collectors.groupingBy(CarUserCollectionDO::getCarUserId));
        List<NameCardLookRecordDO> nameCardLookRecordDOS = nameCardLookRecordDOMapper.selectBatchByUserId(userIds);
        Map<Integer, List<NameCardLookRecordDO>> recordMap = ObjectUtils.isEmpty(nameCardLookRecordDOS) ? Maps.newHashMap() :
                nameCardLookRecordDOS.stream().collect(Collectors.groupingBy(NameCardLookRecordDO::getUserId));
        return PageData.data(carUserDOS, data.stream().map(row -> {
            List<CarUserCollectionDO> carUserCollectionDOS = collectMap.get(row.getId());
            List<NameCardLookRecordDO> recordDOS = recordMap.get(row.getId());
            recordDOS = ObjectUtils.isEmpty(recordDOS) ? Lists.newArrayList() :
                    recordDOS.stream().sorted((o1, o2) -> -o1.getCreateTime().compareTo(o2.getCreateTime())).collect(Collectors.toList());
            return AdminUserDTO.builder()
                    .id(row.getId())
                    .avatar(row.getHeadPortrait())
                    .lastTime(ObjectUtils.isEmpty(recordDOS) ? "" : DateUtil.formatDateDefault(recordDOS.get(0).getCreateTime()))
                    .collectCount(ObjectUtils.isEmpty(carUserCollectionDOS) ? 0 : carUserCollectionDOS.stream().map(CarUserCollectionDO::getPublishId).distinct().collect(Collectors.toList()).size())
                    .lookCount(ObjectUtils.isEmpty(recordDOS) ? 0 : recordDOS.stream().map(NameCardLookRecordDO::getCardId).distinct().collect(Collectors.toList()).size())
                    .mobile(row.getPhone())
                    .nickName(row.getNickName())
                    .registerTime(DateUtil.formatDateDefault(row.getCreateTime()))
                    .build();
        }).collect(Collectors.toList()));
    }

    @Transactional
    public Boolean vip(AdminVipParam param) {
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(param.getUserId());
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        Date now = DateUtil.now();
        CarUserDO updateUser = CarUserDO.builder().id(carUserDO.getId()).updateTime(DateUtil.now())
                .vipStartTime(DateUtil.now())
                .build();
        Calendar calendar = Calendar.getInstance();
        if (!Objects.isNull(carUserDO.getVipStartTime()) && carUserDO.getVipStartTime().compareTo(now) <= 0 &&
                !Objects.isNull(carUserDO.getVipEndTime()) && carUserDO.getVipEndTime().compareTo(now) >= 0) {
            calendar.setTime(carUserDO.getVipEndTime());
            calendar.add(Calendar.DATE, param.getDays());
        } else {
            updateUser.setVipStartTime(DateUtil.now());
            calendar.add(Calendar.DATE, param.getDays());
        }
        updateUser.setVipEndTime(calendar.getTime());
        carUserDOMapper.updateByPrimaryKeySelective(updateUser);
        vipRecordDOMapper.insertSelective(VipRecordDO.builder()
                .createTime(DateUtil.now())
                .updateTime(DateUtil.now())
                .userId(param.getUserId())
                .days(param.getDays() + "d")
                .type((byte) 1)
                .build());
        List<CarUserApplyDO> carUserApplyDOS = carUserApplyDOMapper.selectByUserId(param.getUserId());
        if (ObjectUtils.isEmpty(carUserApplyDOS) || ObjectUtils.isEmpty(carUserApplyDOS.stream().filter(row -> Objects.equals((byte) 2, row.getStatus())).collect(Collectors.toList()))) {
            carUserApplyDOMapper.insertSelective(CarUserApplyDO.builder()
                    .createTime(DateUtil.now())
                    .updateTime(DateUtil.now())
                    .userId(param.getUserId())
                    .name(carUserDO.getNickName())
                    .mobile(carUserDO.getPhone())
                    .status((byte) 2)
                    .build());
        }
        return Boolean.TRUE;
    }

    public PageData<AdminPublishRecordDTO> collectRecord(Integer userId, Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        Page<CarUserCollectionDO> carUserCollectionDOS = (Page<CarUserCollectionDO>) carUserCollectionDOMapper.selectByUserId(userId);
        List<CarUserCollectionDO> data = carUserCollectionDOS.getResult();
        if (ObjectUtils.isEmpty(data)) {
            return PageData.noData(pageSize);
        }
        List<PublishDO> publishDOS = publishDOMapper.selectBatchByPrimaryKey(data.stream().map(CarUserCollectionDO::getPublishId).distinct().collect(Collectors.toList()));
        Map<Integer, PublishDO> publishMap = ObjectUtils.isEmpty(publishDOS) ? Maps.newHashMap() :
                publishDOS.stream().collect(Collectors.toMap(PublishDO::getId, row -> row));
        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(ObjectUtils.isEmpty(publishDOS) ? Lists.newArrayList() : publishDOS.stream().map(PublishDO::getCarUserId).collect(Collectors.toList()));
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        return PageData.data(carUserCollectionDOS, data.stream().map(row -> {
            PublishDO publishDO = publishMap.get(row.getPublishId());
            CarUserDO carUserDO = Objects.isNull(publishDO) ? null : userMap.get(publishDO.getCarUserId());
            return AdminPublishRecordDTO.builder()
                    .image(Objects.isNull(publishDO) ? "" : publishDO.getMainMedia())
                    .time(DateUtil.formatDateDefault(row.getCreateTime()))
                    .title(Objects.isNull(publishDO) ? "" : publishDO.getTitle())
                    .type(Objects.isNull(publishDO) ? "" : publishDO.getType())
                    .user(Objects.isNull(carUserDO) ? null : CarUserDTO.builder()
                            .avatar(carUserDO.getHeadPortrait())
                            .nickName(carUserDO.getNickName())
                            .mobile(carUserDO.getPhone())
                            .build())
                    .build();
        }).collect(Collectors.toList()));
    }

    public PageData<AdminLookRecordDTO> nameCardRecord(Integer userId, Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        Page<UserLookRecordDTO> userLookRecordDTOS = (Page<UserLookRecordDTO>) nameCardLookRecordDOMapper.selectLastByUserId(userId);
        List<UserLookRecordDTO> data = userLookRecordDTOS.getResult();
        if (ObjectUtils.isEmpty(data)) {
            return PageData.noData(pageSize);
        }
        List<NameCardDO> nameCardDOS = nameCardDOMapper.selectBatchByPrimaryKey(data.stream().map(UserLookRecordDTO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, NameCardDO> nameCardMap = ObjectUtils.isEmpty(nameCardDOS) ? Maps.newHashMap() :
                nameCardDOS.stream().collect(Collectors.toMap(NameCardDO::getId, row -> row));
        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(ObjectUtils.isEmpty(nameCardDOS) ? Lists.newArrayList() : nameCardDOS.stream().map(NameCardDO::getUserId).collect(Collectors.toList()));
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        return PageData.data(userLookRecordDTOS, data.stream().map(row -> {
            NameCardDO nameCardDO = nameCardMap.get(row.getUserId());
            CarUserDO carUserDO = Objects.isNull(nameCardDO) ? null : userMap.get(nameCardDO.getUserId());
            return AdminLookRecordDTO.builder()
                    .laseTime(DateUtil.formatDateDefault(row.getLastTime()))
                    .user(Objects.isNull(carUserDO) ? null : CarUserDTO.builder()
                            .avatar(carUserDO.getHeadPortrait())
                            .nickName(carUserDO.getNickName())
                            .mobile(carUserDO.getPhone())
                            .build())
                    .build();
        }).collect(Collectors.toList()));
    }
}
