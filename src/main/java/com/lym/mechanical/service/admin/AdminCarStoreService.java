package com.lym.mechanical.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.dto.admin.AdminCarUserDTO;
import com.lym.mechanical.bean.dto.admin.AdminFriendDTO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.IntentionCustomDO;
import com.lym.mechanical.bean.entity.NameCardDO;
import com.lym.mechanical.bean.entity.NameCardFriendDO;
import com.lym.mechanical.bean.entity.NameCardLookRecordDO;
import com.lym.mechanical.bean.entity.PublishDO;
import com.lym.mechanical.bean.param.admin.AdminCarStoreSearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import com.lym.mechanical.dao.mapper.IntentionCustomDOMapper;
import com.lym.mechanical.dao.mapper.NameCardDOMapper;
import com.lym.mechanical.dao.mapper.NameCardFriendDOMapper;
import com.lym.mechanical.dao.mapper.NameCardLookRecordDOMapper;
import com.lym.mechanical.dao.mapper.PublishDOMapper;
import com.lym.mechanical.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        List<NameCardFriendDO> nameCardFriendDOS = nameCardFriendDOMapper.selectBatchByUserId(userIds);
        Map<Integer, List<NameCardFriendDO>> friendMap = ObjectUtils.isEmpty(nameCardFriendDOS) ? Maps.newHashMap() :
                nameCardFriendDOS.stream().collect(Collectors.groupingBy(NameCardFriendDO::getUserId));
        return PageData.data(carUserDOS, data.stream().map(row -> {
            NameCardDO nameCardDO = nameCardMap.get(row.getId());
            Boolean isVip = !Objects.isNull(row.getVipStartTime()) && !Objects.isNull(row.getVipEndTime()) && row.getVipStartTime().compareTo(now) <= 0 && row.getVipEndTime().compareTo(now) >= 0;
            List<NameCardLookRecordDO> recordList = Objects.isNull(nameCardDO) ? Lists.newArrayList() :
                    recordMap.get(nameCardDO.getId());
            List<IntentionCustomDO> intentionList = intentionMap.get(row.getId());
            List<PublishDO> publishList = publishMap.get(row.getId());
            List<NameCardFriendDO> friendList = friendMap.get(row.getId());
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
                    .todayGuest(ObjectUtils.isEmpty(recordList) ? 0 : recordList.stream().filter(r -> Objects.equals(DateUtil.formatDate(r.getCreateTime(), "yyyyMMdd"), DateUtil.formatDate(now, "yyyyMMdd")))
                            .map(NameCardLookRecordDO::getUserId).distinct().collect(Collectors.toList()).size())
                    .totalGuest(ObjectUtils.isEmpty(recordList) ? 0 : recordList.stream().map(NameCardLookRecordDO::getUserId).distinct().collect(Collectors.toList()).size())
                    .talkGuest(ObjectUtils.isEmpty(recordList) ? 0 : recordList.stream().filter(r -> r.getHasDial()).map(NameCardLookRecordDO::getUserId).distinct().collect(Collectors.toList()).size())
                    .intentionGuest(ObjectUtils.isEmpty(intentionList) ? 0 : intentionList.stream().map(IntentionCustomDO::getIntentionCustomUserId).distinct().collect(Collectors.toList()).size())
                    .publishCount(ObjectUtils.isEmpty(publishList) ? 0 : publishList.size())
                    .friendCount(ObjectUtils.isEmpty(friendList) ? 0 : friendList.size())
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
}
