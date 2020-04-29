package com.lym.mechanical.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lym.mechanical.bean.dto.admin.AdminCarUserDTO;
import com.lym.mechanical.bean.dto.admin.AdminFriendDTO;
import com.lym.mechanical.bean.entity.*;
import com.lym.mechanical.bean.param.admin.AdminCarStoreSearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.*;
import com.lym.mechanical.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @create 2020-02-21 15:58
 * Good good code, day day up!
 **/
@Service
public class AdminCarStoreService {

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private NameCardDOMapper nameCardDOMapper;

    @Autowired
    private NameCardLookRecordDOMapper nameCardLookRecordDOMapper;

    @Autowired
    private IntentionCustomDOMapper intentionCustomDOMapper;

    @Autowired
    private PublishDOMapper publishDOMapper;

    @Autowired
    private NameCardFriendDOMapper nameCardFriendDOMapper;

    @Autowired
    private PublishLookRecordDOMapper publishLookRecordDOMapper;

    public PageData<AdminCarUserDTO> list(AdminCarStoreSearchParam param) {
        PageData.checkPageParam(param.getPageNum(), param.getPageSize());
        PageHelper.startPage(param.getPageNum(), param.getPageSize());

        Page<CarUserDO> carUserDOS = (Page<CarUserDO>) carUserDOMapper.selectForWeb(param.getNickName(), param.getMobile(), param.getUserId(), param.getIsVip(), "1");
        List<CarUserDO> data = carUserDOS.getResult();
        if (ObjectUtils.isEmpty(data)) {
            return PageData.noData(param.getPageSize());
        }
        Date now = DateUtil.now();
        List<Integer> userIds = data.stream().map(CarUserDO::getId).distinct().collect(Collectors.toList());
        List<NameCardDO> nameCardDOS = nameCardDOMapper.selectBatchByUserId(userIds);
        Map<Integer, NameCardDO> nameCardMap = ObjectUtils.isEmpty(nameCardDOS) ? Maps.newHashMap() :
                nameCardDOS.stream().collect(Collectors.toMap(NameCardDO::getUserId, row -> row));
        List<NameCardLookRecordDO> recordDOS = nameCardLookRecordDOMapper.selectBatchByNameCardId(ObjectUtils.isEmpty(nameCardDOS) ? Lists.newArrayList() :
                nameCardDOS.stream().map(NameCardDO::getId).collect(Collectors.toList()));
        Map<Integer, List<NameCardLookRecordDO>> recordMap = ObjectUtils.isEmpty(recordDOS) ? Maps.newHashMap() :
                recordDOS.stream().collect(Collectors.groupingBy(NameCardLookRecordDO::getCardId));
        List<IntentionCustomDO> intentionCustomDOS = intentionCustomDOMapper.selectBatchByUserId(userIds);
        Map<Integer, List<IntentionCustomDO>> intentionMap = ObjectUtils.isEmpty(intentionCustomDOS) ? Maps.newHashMap() :
                intentionCustomDOS.stream().collect(Collectors.groupingBy(IntentionCustomDO::getUserId));
        List<PublishDO> publishDOS = publishDOMapper.selectBatchByCarUserId(userIds);
        Map<Integer, List<PublishDO>> publishMap = ObjectUtils.isEmpty(publishDOS) ? Maps.newHashMap() :
                publishDOS.stream().collect(Collectors.groupingBy(PublishDO::getCarUserId));
        List<PublishLookRecordDO> publishLookRecordDOS = publishLookRecordDOMapper.selectBatchByCarUserId(userIds);
        Map<Integer, List<PublishLookRecordDO>> publishLookMap = ObjectUtils.isEmpty(publishLookRecordDOS) ? Maps.newHashMap() :
                publishLookRecordDOS.stream().collect(Collectors.groupingBy(PublishLookRecordDO::getCarUserId));
        List<NameCardFriendDO> nameCardFriendDOS = nameCardFriendDOMapper.selectBatchByUserId(userIds);
        Map<Integer, List<NameCardFriendDO>> friendMap = ObjectUtils.isEmpty(nameCardFriendDOS) ? Maps.newHashMap() :
                nameCardFriendDOS.stream().collect(Collectors.groupingBy(NameCardFriendDO::getUserId));
        return PageData.data(carUserDOS, data.stream().map(row -> {
            Set<Integer> todayGuestIds = Sets.newHashSet();
            Set<Integer> totalGuestIds = Sets.newHashSet();
            NameCardDO nameCardDO = nameCardMap.get(row.getId());
            Boolean isVip = !Objects.isNull(row.getVipStartTime()) && !Objects.isNull(row.getVipEndTime()) && row.getVipStartTime().compareTo(now) <= 0 && row.getVipEndTime().compareTo(now) >= 0;
            List<NameCardLookRecordDO> recordList = Objects.isNull(nameCardDO) ? Lists.newArrayList() :
                    recordMap.get(nameCardDO.getId());
            List<IntentionCustomDO> intentionList = intentionMap.get(row.getId());
            List<PublishDO> publishList = publishMap.get(row.getId());
            List<NameCardFriendDO> friendList = friendMap.get(row.getId());
            List<PublishLookRecordDO> publishLookRecords = publishLookMap.get(row.getId());
            if (!ObjectUtils.isEmpty(recordList)) {
                todayGuestIds.addAll(recordList.stream().filter(r -> Objects.equals(DateUtil.formatDate(r.getCreateTime(), "yyyyMMdd"), DateUtil.formatDate(now, "yyyyMMdd")))
                        .map(NameCardLookRecordDO::getUserId).distinct().collect(Collectors.toList()));
                totalGuestIds.addAll(recordList.stream().map(NameCardLookRecordDO::getUserId).distinct().collect(Collectors.toList()));
            }
            if (!ObjectUtils.isEmpty(publishLookRecords)) {
                todayGuestIds.addAll(publishLookRecords.stream().filter(r -> Objects.equals(DateUtil.formatDate(r.getCreateTime(), "yyyyMMdd"), DateUtil.formatDate(now, "yyyyMMdd")))
                        .map(PublishLookRecordDO::getUserId).distinct().collect(Collectors.toList()));
                totalGuestIds.addAll(publishLookRecords.stream().map(PublishLookRecordDO::getUserId).distinct().collect(Collectors.toList()));
            }
            return AdminCarUserDTO.builder()
                    .userId(row.getId())
                    .avatar(row.getHeadPortrait())
                    .nickName(row.getNickName())
                    .mobile(row.getPhone())
                    .companyAddress(Objects.isNull(nameCardDO) ? "" : nameCardDO.getCompanyAddress())
                    .companyName(Objects.isNull(nameCardDO) ? "" : nameCardDO.getCompanyName())
                    .jobTitle(Objects.isNull(nameCardDO) ? "" : nameCardDO.getJobTitle())
                    .isVip(isVip ? "是" : "否")
                    .vipEndTime(!Objects.isNull(row.getVipEndTime()) ? DateUtil.formatDateDefault(row.getVipEndTime()) : "")
                    .buyTime(isVip ? DateUtil.formatDateDefault(row.getVipStartTime()) : "")
                    .todayGuest(todayGuestIds.size())
                    .totalGuest(totalGuestIds.size())
                    .talkGuest(ObjectUtils.isEmpty(recordList) ? 0 : recordList.stream().filter(r -> r.getHasDial()).map(NameCardLookRecordDO::getUserId).distinct().collect(Collectors.toList()).size())
                    .intentionGuest(ObjectUtils.isEmpty(intentionList) ? 0 : intentionList.stream().map(IntentionCustomDO::getIntentionCustomUserId).distinct().collect(Collectors.toList()).size())
                    .publishCount(ObjectUtils.isEmpty(publishList) ? 0 : publishList.size())
                    .friendCount(ObjectUtils.isEmpty(friendList) ? 0 : friendList.size())
                    .name(Objects.isNull(nameCardDO) ? "" : nameCardDO.getName())
                    .code(Objects.isNull(nameCardDO) ? "" : nameCardDO.getCode())
                    .isShow(row.getIsShow())
                    .cardCreateTime(Objects.isNull(nameCardDO) ? "" : DateUtil.formatDate(nameCardDO.getCreateTime(), "yyyy-MM-dd"))
                    .build();
        }).collect(Collectors.toList()));
    }

    public PageData<AdminFriendDTO> friendList(Integer userId, Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        Page<NameCardFriendDO> nameCardFriendDOS = (Page<NameCardFriendDO>) nameCardFriendDOMapper.selectByUserId(userId);
        List<NameCardFriendDO> data = nameCardFriendDOS.getResult();
        if (ObjectUtils.isEmpty(data)) {
            return PageData.noData(pageSize);
        }
        List<NameCardDO> nameCardDOS = nameCardDOMapper.selectBatchByPrimaryKey(data.stream().map(NameCardFriendDO::getCardId).distinct().collect(Collectors.toList()));
        Map<Integer, NameCardDO> nameCardMap = ObjectUtils.isEmpty(nameCardDOS) ? Maps.newHashMap() :
                nameCardDOS.stream().collect(Collectors.toMap(NameCardDO::getId, row -> row));
        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(ObjectUtils.isEmpty(nameCardDOS) ? Lists.newArrayList() :
                nameCardDOS.stream().map(NameCardDO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        List<NameCardLookRecordDO> recordDOS = nameCardLookRecordDOMapper.selectBatchByNameCardId(ObjectUtils.isEmpty(nameCardDOS) ? Lists.newArrayList() :
                nameCardDOS.stream().map(NameCardDO::getId).collect(Collectors.toList()));
        Map<Integer, List<NameCardLookRecordDO>> recordMap = ObjectUtils.isEmpty(recordDOS) ? Maps.newHashMap() :
                recordDOS.stream().collect(Collectors.groupingBy(NameCardLookRecordDO::getCardId));
        return PageData.data(nameCardFriendDOS, data.stream().map(row -> {
            NameCardDO nameCardDO = nameCardMap.get(row.getCardId());
            CarUserDO carUserDO = Objects.isNull(nameCardDO) ? null : userMap.get(nameCardDO.getUserId());
            List<NameCardLookRecordDO> recordList = Objects.isNull(nameCardDO) ? Lists.newArrayList() :
                    recordMap.get(nameCardDO.getId());
            return AdminFriendDTO.builder()
                    .avatar(Objects.isNull(carUserDO) ? "" : carUserDO.getHeadPortrait())
                    .nickName(Objects.isNull(carUserDO) ? "" : carUserDO.getNickName())
                    .mobile(Objects.isNull(carUserDO) ? "" : carUserDO.getPhone())
                    .companyName(Objects.isNull(nameCardDO) ? "" : nameCardDO.getCompanyName())
                    .jobTitle(Objects.isNull(nameCardDO) ? "" : nameCardDO.getJobTitle())
                    .totalGuest(ObjectUtils.isEmpty(recordList) ? 0 : recordList.stream().map(NameCardLookRecordDO::getUserId).distinct().collect(Collectors.toList()).size())
                    .build();
        }).collect(Collectors.toList()));
    }

    public Boolean switchShow(Integer userId) {
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        carUserDOMapper.updateByPrimaryKeySelective(CarUserDO.builder()
                .id(userId)
                .isShow(!carUserDO.getIsShow())
                .build());
        return Boolean.TRUE;
    }
}
