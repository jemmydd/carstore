package com.lym.mechanical.service.my;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.common.Constant;
import com.lym.mechanical.bean.common.DefaultHandleConstant;
import com.lym.mechanical.bean.dto.card.NameCardDTO;
import com.lym.mechanical.bean.dto.location.LocationDetailDTO;
import com.lym.mechanical.bean.dto.my.*;
import com.lym.mechanical.bean.dto.publish.PublishDTO;
import com.lym.mechanical.bean.entity.*;
import com.lym.mechanical.bean.enumBean.PublishCallSceneEnum;
import com.lym.mechanical.bean.param.publish.PublishParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.*;
import com.lym.mechanical.service.publish.PublishService;
import com.lym.mechanical.service.user.UserService;
import com.lym.mechanical.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Classname MyService
 * @Description
 * @Date 2019/11/18 9:03
 * @Created by jimy
 * good good code, day day up!
 */
@Service
public class MyService {

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private NameCardDOMapper nameCardDOMapper;

    @Autowired
    private CarUserApplyDOMapper carUserApplyDOMapper;

    @Autowired
    private MessageDOMapper messageDOMapper;

    @Autowired
    private NameCardLookRecordDOMapper nameCardLookRecordDOMapper;

    @Autowired
    private PublishLookRecordDOMapper publishLookRecordDOMapper;

    @Autowired
    private NameCardFriendDOMapper nameCardFriendDOMapper;

    @Autowired
    private CarUserCollectionDOMapper carUserCollectionDOMapper;

    @Autowired
    private PublishDOMapper publishDOMapper;

    @Autowired
    private PublishService publishService;

    @Autowired
    private IntentionCustomDOMapper intentionCustomDOMapper;

    @Autowired
    private UserService userService;

    public MyIndexDTO myIndex(Integer userId) {
        if (Objects.isNull(userId)) {
            throw new RuntimeException("用户未登录");
        }
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        NameCardDO nameCardDO = nameCardDOMapper.selectByUserId(userId);
        List<CarUserApplyDO> applyDOS = carUserApplyDOMapper.selectByUserId(userId);
        List<MessageDO> messageDOS = messageDOMapper.selectByUserId(userId, null);
        List<CommonDTO> todayGuest = Objects.isNull(nameCardDO) ? Lists.newArrayList() :
                nameCardLookRecordDOMapper.selectByCardIdAndDate(nameCardDO.getId(), DateUtil.formatDate(DateUtil.now(), "yyyy-MM-dd"), null, null, null);
        List<CommonDTO> totalGuest = nameCardLookRecordDOMapper.selectByCardIdAndDate(userId, null, null, null, null);
        List<IntentionCustomDO> intentionCustom = intentionCustomDOMapper.selectByUserId(userId);
        return MyIndexDTO.builder()
                .avatar(StringUtils.isEmpty(carUserDO.getHeadPortrait()) ? "" : carUserDO.getHeadPortrait())
                .hasCard(!Objects.isNull(nameCardDO))
                .cardId(Objects.isNull(nameCardDO) ? null : nameCardDO.getId())
                .isCarStore(!ObjectUtils.isEmpty(applyDOS) &&
                        !ObjectUtils.isEmpty(applyDOS.stream().filter(row -> Objects.equals((byte) 2, row.getStatus()))
                                .collect(Collectors.toList())))
                .isVip(DateUtil.dateValid(carUserDO.getVipStartTime(), carUserDO.getVipEndTime()))
                .name(carUserDO.getNickName())
                .talkCount(ObjectUtils.isEmpty(messageDOS) ? 0 : messageDOS.size())
                .todayGuest(ObjectUtils.isEmpty(todayGuest) ? 0 : todayGuest.stream().map(CommonDTO::getUserId).distinct().collect(Collectors.toList()).size())
                .vipEndTime(Objects.isNull(carUserDO.getVipEndTime()) ? "" : DateUtil.formatDate(carUserDO.getVipEndTime(), "yyyy-MM-dd"))
                .totalGuest(ObjectUtils.isEmpty(totalGuest) ? 0 : totalGuest.stream().map(CommonDTO::getUserId).distinct().collect(Collectors.toList()).size())
                .intentionCount(ObjectUtils.isEmpty(intentionCustom) ? 0 : intentionCustom.size())
                .code(!Objects.isNull(nameCardDO) ? nameCardDO.getCode() : "")
                .mobile(carUserDO.getPhone())
                .build();
    }

    public PageData<MyGuestDTO> myGuest(Integer userId, String type, String hasManyLook, String hasDial, String hasMobile, Integer pageNum, Integer pageSize) {
        NameCardDO nameCardDO = nameCardDOMapper.selectByUserId(userId);
        if (Objects.isNull(nameCardDO)) {
            return PageData.noData(pageSize);
        }
        if (Objects.equals("0", type) || Objects.equals("1", type)) {
            PageData.checkPageParam(pageNum, pageSize);
            PageHelper.startPage(pageNum, pageSize);
            Page<CommonDTO> lookRecordDOS = (Page<CommonDTO>) nameCardLookRecordDOMapper.selectByCardIdAndDate(nameCardDO.getId(),
                    Objects.equals("0", type) ? DateUtil.formatDate(DateUtil.now(), "yyyy-MM-dd") : null, hasManyLook, hasDial, hasMobile);
            List<CommonDTO> data = lookRecordDOS.getResult();
            if (ObjectUtils.isEmpty(data)) {
                return PageData.noData(pageSize);
            }
            List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(
                    lookRecordDOS.stream().map(CommonDTO::getUserId).distinct().collect(Collectors.toList()));
            Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                    carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
            return PageData.data(lookRecordDOS, data.stream().map(row -> {
                CarUserDO carUserDO = userMap.get(row.getUserId());
                return MyGuestDTO.builder()
                        .avatar(Objects.isNull(carUserDO) ? "" : carUserDO.getHeadPortrait())
                        .guestId(row.getUserId())
                        .name(Objects.isNull(carUserDO) ? "" : carUserDO.getNickName())
                        .recentTime(DateUtil.getDateStr(row.getCreateTime()))
                        .build();
            }).collect(Collectors.toList()));
        } else if (Objects.equals("2", type)) {
            List<CommonDTO> lookRecordDOS = nameCardLookRecordDOMapper.selectByCardIdAndDate(nameCardDO.getId(), null, hasManyLook, hasDial, hasMobile);
            if (ObjectUtils.isEmpty(lookRecordDOS)) {
                return PageData.noData(pageSize);
            }
            List<Integer> userIds = lookRecordDOS.stream().map(CommonDTO::getUserId).collect(Collectors.toList());
            PageData.checkPageParam(pageNum, pageSize);
            PageHelper.startPage(pageNum, pageSize);
            Page<CommonDTO> messageDOS = (Page<CommonDTO>) messageDOMapper.selectByUserIdAndOtherUserIds(userId, userIds);
            List<CommonDTO> data = messageDOS.getResult();
            if (ObjectUtils.isEmpty(data)) {
                return PageData.noData(pageSize);
            }
            List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(
                    lookRecordDOS.stream().map(CommonDTO::getUserId).distinct().collect(Collectors.toList()));
            Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                    carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
            return PageData.data(messageDOS, data.stream().map(row -> {
                CarUserDO carUserDO = userMap.get(row.getUserId());
                return MyGuestDTO.builder()
                        .avatar(Objects.isNull(carUserDO) ? "" : carUserDO.getHeadPortrait())
                        .guestId(row.getUserId())
                        .name(Objects.isNull(carUserDO) ? "" : carUserDO.getNickName())
                        .recentTime(DateUtil.getDateStr(row.getCreateTime()))
                        .build();
            }).collect(Collectors.toList()));
        } else {
            PageData.checkPageParam(pageNum, pageSize);
            PageHelper.startPage(pageNum, pageSize);
            Page<CommonDTO> intentionCustom = (Page<CommonDTO>) intentionCustomDOMapper.selectByCardIdAndDate(nameCardDO.getId(), null, hasManyLook, hasDial, hasMobile);
            List<CommonDTO> data = intentionCustom.getResult();
            if (ObjectUtils.isEmpty(data)) {
                return PageData.noData(pageSize);
            }
            List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(
                    intentionCustom.stream().map(CommonDTO::getUserId).distinct().collect(Collectors.toList()));
            Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                    carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
            return PageData.data(intentionCustom, data.stream().map(row -> {
                CarUserDO carUserDO = userMap.get(row.getUserId());
                return MyGuestDTO.builder()
                        .avatar(Objects.isNull(carUserDO) ? "" : carUserDO.getHeadPortrait())
                        .guestId(row.getUserId())
                        .name(Objects.isNull(carUserDO) ? "" : carUserDO.getNickName())
                        .recentTime(DateUtil.getDateStr(row.getCreateTime()))
                        .build();
            }).collect(Collectors.toList()));
        }
    }

    public List<NameCardDTO> myFriendCards(Integer userId) {
        List<NameCardFriendDO> nameCardFriendDOS = nameCardFriendDOMapper.selectByUserId(userId);
        if (ObjectUtils.isEmpty(nameCardFriendDOS)) {
            return Lists.newArrayList();
        }
        List<NameCardDO> nameCardDOS = nameCardDOMapper.selectBatchByPrimaryKey(nameCardFriendDOS.stream()
                .map(NameCardFriendDO::getCardId).distinct().collect(Collectors.toList()));
        Map<Integer, NameCardDO> map = ObjectUtils.isEmpty(nameCardDOS) ? Maps.newHashMap() :
                nameCardDOS.stream().collect(Collectors.toMap(NameCardDO::getId, row -> row));
        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(ObjectUtils.isEmpty(nameCardDOS) ? Lists.newArrayList() :
                nameCardDOS.stream().map(NameCardDO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        return nameCardFriendDOS.stream().map(row -> {
            NameCardDO nameCardDO = map.get(row.getCardId());
            CarUserDO userDO = Objects.isNull(nameCardDO) ? null : userMap.get(nameCardDO.getId());
            return NameCardDTO.builder()
                    .name(Objects.isNull(nameCardDO) ? "" : nameCardDO.getName())
                    .jobTitle(Objects.isNull(nameCardDO) ? "" : nameCardDO.getJobTitle())
                    .companyName(Objects.isNull(nameCardDO) ? "" : nameCardDO.getCompanyName())
                    .companyAddress(Objects.isNull(nameCardDO) ? "" : nameCardDO.getCompanyAddress())
                    .avatar(Objects.isNull(userDO) ? "" : userDO.getHeadPortrait())
                    .id(row.getCardId())
                    .code(Objects.isNull(nameCardDO) ? "" : nameCardDO.getCode())
                    .mobile(Objects.isNull(nameCardDO) ? "" : nameCardDO.getMobile())
                    .userId(Objects.isNull(nameCardDO) ? null : nameCardDO.getUserId())
                    .location(Objects.isNull(nameCardDO) ? "" : nameCardDO.getLocation())
                    .isVip(Objects.isNull(userDO) ? Boolean.FALSE :
                            DateUtil.dateValid(userDO.getVipStartTime(), userDO.getVipEndTime()))
                    .build();
        }).collect(Collectors.toList());

    }

    public Boolean removeFriendCards(Integer userId, Integer cardId) {
        nameCardFriendDOMapper.deleteByUserIdAndCardId(userId, cardId);
        return Boolean.TRUE;
    }

    public PageData<PublishDTO> getMyCollectionPageData(Integer pageNum, Integer pageSize, Integer userId) {
        PageHelper.startPage(pageNum, pageSize);
        Page<CarUserCollectionDO> pd = (Page<CarUserCollectionDO>) carUserCollectionDOMapper.search(userId);

        List<CarUserCollectionDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        return PageData.data(
                pd,
                publishService.getPublishByIds(data.stream().map(CarUserCollectionDO::getPublishId).distinct().collect(Collectors.toList()), userId, PublishCallSceneEnum.MY_COLLECTION)
        );
    }

    public PageData<PublishDTO> getMyPublishPageData(Integer pageNum, Integer pageSize, Integer userId, Byte downShelf) {
        return publishService.getPageData(PublishParam.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .publishCallSceneEnum(PublishCallSceneEnum.MY_PUBLISH)
                .publishId(userId)
                .isDownShelf(downShelf)
                .build());
    }

    @Transactional
    public Boolean removeCollect(Integer userId, Integer publishId) {
        carUserCollectionDOMapper.deleteByUserIdAndPublishId(userId, publishId);
        publishLookRecordDOMapper.updateCollectByPublishIdAndUserId(userId, publishId, "0");
        return Boolean.TRUE;
    }

    public PublishStatisticDTO publishStatistic(Integer userId) {
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        Boolean isVip = DateUtil.dateValid(carUserDO.getVipStartTime(), carUserDO.getVipEndTime());
        List<PublishLookRecordDO> recordDOS = publishLookRecordDOMapper.selectByUserId(userId);
        if (ObjectUtils.isEmpty(recordDOS)) {
            return PublishStatisticDTO.builder()
                    .totalGuest(0)
                    .todayGuest(0)
                    .isVip(isVip)
                    .lookRecords(Lists.newArrayList())
                    .build();
        }
        List<PublishDO> publishDOS = publishDOMapper.searchByIds(recordDOS.stream().map(PublishLookRecordDO::getPublishId).distinct().collect(Collectors.toList()));
        Map<Integer, PublishDO> publishMap = ObjectUtils.isEmpty(publishDOS) ? Maps.newHashMap() :
                publishDOS.stream().collect(Collectors.toMap(PublishDO::getId, row -> row));
        Map<Integer, List<PublishLookRecordDO>> recordMap = recordDOS.stream().collect(Collectors.groupingBy(PublishLookRecordDO::getPublishId));
        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(recordDOS.stream().map(PublishLookRecordDO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        return PublishStatisticDTO.builder()
                .todayGuest(recordDOS.stream().filter(row -> Objects.equals(DateUtil.formatDate(row.getCreateTime(), "yyyyMMdd"), DateUtil.formatDate(DateUtil.now(), "yyyyMMdd")))
                        .map(PublishLookRecordDO::getUserId).distinct().collect(Collectors.toList()).size())
                .totalGuest(recordDOS.stream().map(PublishLookRecordDO::getUserId).distinct().collect(Collectors.toList()).size())
                .isVip(isVip)
                .lookRecords(publishDOS.stream().sorted((o1, o2) -> -o1.getCreateTime().compareTo(o2.getCreateTime())).map(row -> {
                    PublishDO publishDO = publishMap.get(row.getId());
                    List<PublishLookRecordDO> records = recordMap.get(row.getId());
                    List<Integer> userIds = ObjectUtils.isEmpty(records) ? Lists.newArrayList() :
                            records.stream().map(PublishLookRecordDO::getUserId).distinct().collect(Collectors.toList());
                    return PublishLookRecordDTO.builder()
                            .publishId(row.getId())
                            .title(Objects.isNull(publishDO) ? "" : publishDO.getTitle())
                            .collectCount(ObjectUtils.isEmpty(records) ? 0 : records.stream().filter(r -> r.getHasCollect()).map(PublishLookRecordDO::getUserId).distinct().count())
                            .guests(ObjectUtils.isEmpty(userIds) ? Lists.newArrayList() :
                                    userIds.stream().map(r -> {
                                        CarUserDO userDO = userMap.get(r);
                                        return Objects.isNull(userDO) ? "" : userDO.getHeadPortrait();
                                    }).collect(Collectors.toList()))
                            .image(Objects.isNull(publishDO) ? "" : publishDO.getMainMedia())
                            .lookCount(ObjectUtils.isEmpty(records) ? 0 : records.stream().map(PublishLookRecordDO::getUserId).distinct().count())
                            .productiveYear(publishDO.getProductiveYear())
                            .usageHours(publishDO.getUsageHours())
                            .locationDetail(getLocationDetail(publishDO))
                            .location(userService.getUserLocation(publishDO.getProvinceName(), publishDO.getCityName(), publishDO.getAreaName()))
                            .createTime(DateFormatUtils.format(row.getCreateTime(), Constant.DATE_FORMAT))
                            .inPrice(publishDO.getInPrice() == null ? DefaultHandleConstant.PUBLISH_IN : String.valueOf(publishDO.getInPrice()))
                            .outPrice(publishDO.getOutPrice() == null ? DefaultHandleConstant.PUBLISH_OUT : String.valueOf(publishDO.getOutPrice()))
                            .build();
                }).sorted((o1, o2) -> -o1.getLookCount().compareTo(o2.getLookCount())).collect(Collectors.toList()))
                .build();
    }

    private LocationDetailDTO getLocationDetail(PublishDO row) {
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

    public LatentUserPublishDTO latentUserPublishInfo(Integer publishId) {
        PublishDO publishDO = publishDOMapper.selectByPrimaryKey(publishId);
        if (Objects.isNull(publishDO)) {
            throw new RuntimeException("设备不存在");
        }
        return LatentUserPublishDTO.builder()
                .publishId(publishId)
                .date(DateUtil.formatDate(publishDO.getCreateTime(), "yyyy-MM-dd") + "发布")
                .desc((Objects.isNull(publishDO.getProductiveYear()) ? "" : (publishDO.getProductiveYear() + "年|")) +
                        (StringUtils.isEmpty(publishDO.getUsageHours()) ? "" : (publishDO.getUsageHours() + "小时|")) +
                        (StringUtils.isEmpty(publishDO.getCityName()) ? "" : publishDO.getCityName()))
                .image(StringUtils.isEmpty(publishDO.getMainMedia()) ? "" : publishDO.getMainMedia())
                .price(publishDO.getOutPrice() == null ? DefaultHandleConstant.PUBLISH_OUT : publishDO.getOutPrice())
                .title(publishDO.getTitle())
                .build();
    }

//    public LookUserDTO latentUserTop(Integer userId, Integer publishId, String type) {
//        PublishLookRecordDO top = null;
//        String mostLookTime = "";
//        String totalLookTimes = "";
//        if (Objects.equals("0", type)) {
//            // 访问最多
//            Integer topUserId = publishLookRecordDOMapper.selectLookTimeTopByPublishId(publishId);
//            if (!Objects.isNull(topUserId)) {
//                List<PublishLookRecordDO> recordDOS = publishLookRecordDOMapper.selectByUserIdAndPublishIdOrderByLookTime(topUserId, publishId);
//                top = recordDOS.get(0);
//                mostLookTime = DateUtil.getTime(top.getLookTime().longValue());
//                totalLookTimes = recordDOS.size() + "次";
//            }
//        } else {
//            // 时间最长
//            List<PublishLookRecordDO> recordDOS = publishLookRecordDOMapper.selectByUserIdAndPublishIdOrderByLookTime(userId, publishId);
//            if (!ObjectUtils.isEmpty(recordDOS)) {
//                top = recordDOS.get(0);
//                mostLookTime = DateUtil.getTime(top.getLookTime().longValue());
//                List<PublishLookRecordDO> records = publishLookRecordDOMapper.selectByUserIdAndPublishIdOrderByLookTime(top.getUserId(), publishId);
//                totalLookTimes = records.size() + "次";
//            }
//        }
//        if (Objects.isNull(top)) {
//            return LookUserDTO.builder().build();
//        }
//        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(top.getUserId());
//        List<MessageDO> messageDOS = messageDOMapper.selectByUserGroup(userId < top.getUserId() ? (userId + "-" + top.getUserId()) : (top.getUserId() + "-" + userId));
//        return LookUserDTO.builder()
//                .avatar(Objects.isNull(carUserDO) ? "" : carUserDO.getHeadPortrait())
//                .hasCollect(top.getHasCollect() ? "有" : "无")
//                .hasTakeMobile(top.getHasDial() ? "有" : "无")
//                .hasTalk(!ObjectUtils.isEmpty(messageDOS))
//                .mobile(Objects.isNull(carUserDO) ? "" : carUserDO.getPhone())
//                .mostLookTime(mostLookTime)
//                .name(Objects.isNull(carUserDO) ? "" : carUserDO.getNickName())
//                .totalLookTimes(totalLookTimes)
//                .userId(top.getUserId())
//                .build();
//    }

    public LatentDTO latent(Integer userId, Integer publishId, String hasDial, String hasCollect, String hasManyLook, String hasMobile, String sortBy) {
        PublishDO publishDO = publishDOMapper.selectByPrimaryKey(publishId);
        if (Objects.isNull(publishDO)) {
            throw new RuntimeException("设备不存在");
        }
        LatentUserPublishDTO publish = LatentUserPublishDTO.builder()
                .publishId(publishId)
                .date(DateUtil.formatDate(publishDO.getCreateTime(), "yyyy-MM-dd") + "发布")
                .desc((Objects.isNull(publishDO.getProductiveYear()) ? "" : (publishDO.getProductiveYear() + "年 | ")) +
                        (StringUtils.isEmpty(publishDO.getUsageHours()) ? "" : (publishDO.getUsageHours() + "小时 | ")) +
                        (StringUtils.isEmpty(publishDO.getCityName()) ? "" : publishDO.getCityName()))
                .image(StringUtils.isEmpty(publishDO.getMainMedia()) ? "" : publishDO.getMainMedia())
                .price(publishDO.getOutPrice() == null ? DefaultHandleConstant.PUBLISH_OUT : publishDO.getOutPrice())
                .title(publishDO.getTitle())
                .build();
        List<LookUserDTO> result = Lists.newArrayList();
        List<PublishLookRecordDO> recordDOS = publishLookRecordDOMapper.selectHistory(publishId, hasDial, hasCollect);
        if (!ObjectUtils.isEmpty(recordDOS)) {
            List<Integer> userIds = Lists.newArrayList();
            Map<Integer, List<PublishLookRecordDO>> recordMap = recordDOS.stream().collect(Collectors.groupingBy(PublishLookRecordDO::getUserId));
            List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(recordDOS.stream().map(PublishLookRecordDO::getUserId).distinct().collect(Collectors.toList()));
            Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                    carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
            List<String> userGroups = Lists.newArrayList();
            recordDOS.forEach(row -> {
                String userGroup = userId < row.getUserId() ? (userId + "-" + row.getUserId()) : (row.getUserId() + "-" + userId);
                if (!userGroups.contains(userGroup)) {
                    userGroups.add(userGroup);
                }
            });
            List<MessageDO> messageDOS = messageDOMapper.selectBatchByUserGroup(userGroups);
            Map<String, List<MessageDO>> messageMap = ObjectUtils.isEmpty(messageDOS) ? Maps.newHashMap() :
                    messageDOS.stream().collect(Collectors.groupingBy(MessageDO::getUserGroup));
            for (PublishLookRecordDO recordDO : recordDOS) {
                if (!userIds.contains(recordDO.getUserId())) {
                    List<PublishLookRecordDO> recordList = recordMap.get(recordDO.getUserId());
                    recordList = recordList.stream().sorted((o1, o2) -> -o1.getLookTime().compareTo(o2.getLookTime())).collect(Collectors.toList());
                    CarUserDO carUserDO = userMap.get(recordDO.getUserId());
                    String userGroup = userId < recordDO.getUserId() ? (userId + "-" + recordDO.getUserId()) : (recordDO.getUserId() + "-" + userId);
                    List<MessageDO> messageList = messageMap.get(userGroup);
                    Boolean flag = Boolean.TRUE;
                    if (Objects.equals("1", hasManyLook) && recordList.size() <= 1) {
                        flag = Boolean.FALSE;
                    }
                    if (Objects.equals("1", hasMobile) && Objects.isNull(carUserDO) && !StringUtils.isEmpty(carUserDO.getPhone())) {
                        flag = Boolean.FALSE;
                    }
                    if (flag) {
                        result.add(LookUserDTO.builder()
                                .totalLookTimes(recordList.size())
                                .userId(recordDO.getUserId())
                                .name(Objects.isNull(carUserDO) ? "" : carUserDO.getNickName())
                                .mostLookTime(DateUtil.getTime(recordList.get(0).getLookTime().longValue()))
                                .time(recordList.get(0).getLookTime().longValue())
                                .mobile(Objects.isNull(carUserDO) ? "" : carUserDO.getPhone())
                                .hasTalk(!ObjectUtils.isEmpty(messageList))
                                .hasTakeMobile(recordDO.getHasDial() ? "有" : "无")
                                .hasCollect(recordDO.getHasCollect() ? "有" : "无")
                                .avatar(Objects.isNull(carUserDO) ? "" : carUserDO.getHeadPortrait())
                                .score(getScore(recordList))
                                .build());
                    }
                    userIds.add(recordDO.getUserId());
                }
            }
        }
        if (!ObjectUtils.isEmpty(result)) {
            if (Objects.equals("0", sortBy)) {
                result = result.stream().sorted((o1, o2) -> -o1.getScore().compareTo(o2.getScore())).collect(Collectors.toList());
            } else if (Objects.equals("1", sortBy)) {
                result = result.stream().sorted((o1, o2) -> -o1.getTotalLookTimes().compareTo(o2.getTotalLookTimes())).collect(Collectors.toList());
            } else {
                result = result.stream().sorted((o1, o2) -> -o1.getTime().compareTo(o2.getTime())).collect(Collectors.toList());
            }
        }
        return LatentDTO.builder().publish(publish).users(result).build();
    }

    private Integer getScore(List<PublishLookRecordDO> recordList) {
        // 计算综合评分
        Integer score = 0;
        if (!ObjectUtils.isEmpty(recordList)) {
            if (recordList.size() == 1) {
                score += 1;
            } else {
                score += 2;
            }
            if (recordList.get(0).getLookTime() >= 20) {
                score += 1;
            }
            if (!ObjectUtils.isEmpty(recordList.stream().filter(row -> row.getHasCollect()).collect(Collectors.toList()))) {
                score += 1;
            }
            if (!ObjectUtils.isEmpty(recordList.stream().filter(row -> row.getHasDial()).collect(Collectors.toList()))) {
                score += 1;
            }
        }
        return score;
    }

    public LatentUserDTO latentUser(Integer userId, Integer latentUserId) {
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(latentUserId);
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        List<MessageDO> messageDOS = messageDOMapper.selectByUserGroup(userId < latentUserId ? (userId + "-" + latentUserId) :
                (latentUserId + "-" + userId));
        List<PublishLookRecordDO> publishLookRecordDOS = publishLookRecordDOMapper.selectByUserIdAndPublishIdOrderByLookTime(latentUserId, null);
        return LatentUserDTO.builder()
                .avatar(Objects.isNull(carUserDO) ? "" : carUserDO.getHeadPortrait())
                .collectCount(ObjectUtils.isEmpty(publishLookRecordDOS) ? "0个" : (publishLookRecordDOS.stream().filter(row -> row.getHasCollect()).map(PublishLookRecordDO::getPublishId).distinct().count() + "个"))
                .hasTalk(!ObjectUtils.isEmpty(messageDOS))
                .lookCount(ObjectUtils.isEmpty(publishLookRecordDOS) ? "0个" : (publishLookRecordDOS.stream().map(PublishLookRecordDO::getPublishId).distinct().count() + "个"))
                .lookTimes(ObjectUtils.isEmpty(publishLookRecordDOS) ? "0次" : (publishLookRecordDOS.size() + "次"))
                .mobile(Objects.isNull(carUserDO) ? "" : carUserDO.getPhone())
                .name(Objects.isNull(carUserDO) ? "" : carUserDO.getNickName())
                .mostLookTime(ObjectUtils.isEmpty(publishLookRecordDOS) ? "0秒" : DateUtil.getTime(publishLookRecordDOS.get(0).getLookTime().longValue()))
                .userId(latentUserId)
                .build();
    }

//    public LatentPublishStatisticDTO latentPublishTop(Integer latentUserId, String type) {
//        PublishLookRecordDO top = null;
//        String mostLookTime = "";
//        String totalLookTimes = "";
//        String recentTime = "";
//        if (Objects.equals("0", type)) {
//            // 访问最多
//            Integer topPublishId = publishLookRecordDOMapper.selectLookTimeTopByUserId(latentUserId);
//            if (!Objects.isNull(topPublishId)) {
//                List<PublishLookRecordDO> recordDOS = publishLookRecordDOMapper.selectByUserIdAndPublishIdOrderByLookTime(latentUserId, topPublishId);
//                top = recordDOS.get(0);
//                mostLookTime = DateUtil.getTime(top.getLookTime().longValue());
//                totalLookTimes = recordDOS.size() + "次";
//                recordDOS = recordDOS.stream().sorted((o1, o2) -> -o1.getCreateTime().compareTo(o2.getCreateTime())).collect(Collectors.toList());
//                recentTime = DateUtil.getDateStr(recordDOS.get(0).getCreateTime());
//            }
//        } else {
//            // 时间最长
//            List<PublishLookRecordDO> recordDOS = publishLookRecordDOMapper.selectByUserIdAndPublishIdOrderByLookTime(latentUserId, null);
//            if (!ObjectUtils.isEmpty(recordDOS)) {
//                top = recordDOS.get(0);
//                mostLookTime = DateUtil.getTime(top.getLookTime().longValue());
//                List<PublishLookRecordDO> records = publishLookRecordDOMapper.selectByUserIdAndPublishIdOrderByLookTime(latentUserId, top.getPublishId());
//                totalLookTimes = records.size() + "次";
//                records = records.stream().sorted((o1, o2) -> -o1.getCreateTime().compareTo(o2.getCreateTime())).collect(Collectors.toList());
//                recentTime = DateUtil.getDateStr(records.get(0).getCreateTime());
//            }
//        }
//        if (Objects.isNull(top)) {
//            return LatentPublishStatisticDTO.builder().build();
//        }
//        PublishDO publishDO = publishDOMapper.selectByPrimaryKey(top.getPublishId());
//        if (Objects.isNull(publishDO)) {
//            throw new RuntimeException("设备不存在");
//        }
//        return LatentPublishStatisticDTO.builder()
//                .publishId(top.getPublishId())
//                .date(DateUtil.formatDate(publishDO.getCreateTime(), "yyyy-MM-dd") + "发布")
//                .desc((Objects.isNull(publishDO.getProductiveYear()) ? "" : (publishDO.getProductiveYear() + "年|")) +
//                        (StringUtils.isEmpty(publishDO.getUsageHours()) ? "" : (publishDO.getUsageHours() + "小时|")) +
//                        (StringUtils.isEmpty(publishDO.getCityName()) ? "" : publishDO.getCityName()))
//                .hasCollect(top.getHasCollect() ? "有" : "无")
//                .hasTakeMobile(top.getHasDial() ? "有" : "无")
//                .image(publishDO.getMainMedia())
//                .lookTimes(totalLookTimes)
//                .mostLookTime(mostLookTime)
//                .price(publishDO.getOutPrice() == null ? DefaultHandleConstant.PUBLISH_OUT : publishDO.getOutPrice())
//                .recentTime(recentTime)
//                .title(publishDO.getTitle())
//                .build();
//    }

    public UserLatentDTO latentPublishList(Integer userId, Integer latentUserId, String hasDial, String hasCollect, String hasManyLook, String sortBy) {
        LatentUserDTO user = latentUser(userId, latentUserId);
        List<LatentPublishStatisticDTO> result = Lists.newArrayList();
        List<PublishLookRecordDO> recordDOS = publishLookRecordDOMapper.selectHistoryByUserId(latentUserId, hasDial, hasCollect);
        if (!ObjectUtils.isEmpty(recordDOS)) {
            List<Integer> publishIds = Lists.newArrayList();
            Map<Integer, List<PublishLookRecordDO>> recordMap = recordDOS.stream().collect(Collectors.groupingBy(PublishLookRecordDO::getPublishId));
            List<PublishDO> publishDOS = publishDOMapper.searchByIds(recordDOS.stream().map(PublishLookRecordDO::getPublishId).distinct().collect(Collectors.toList()));
            Map<Integer, PublishDO> map = ObjectUtils.isEmpty(publishDOS) ? Maps.newHashMap() :
                    publishDOS.stream().collect(Collectors.toMap(PublishDO::getId, row -> row));
            for (PublishLookRecordDO recordDO : recordDOS) {
                if (!publishIds.contains(recordDO.getPublishId())) {
                    List<PublishLookRecordDO> recordList = recordMap.get(recordDO.getPublishId());
                    String recentTime;
                    recordList = recordList.stream().sorted((o1, o2) -> -o1.getCreateTime().compareTo(o2.getCreateTime())).collect(Collectors.toList());
                    recentTime = DateUtil.getDateStr(recordList.get(0).getCreateTime());
                    recordList = recordList.stream().sorted((o1, o2) -> -o1.getLookTime().compareTo(o2.getLookTime())).collect(Collectors.toList());
                    PublishDO publishDO = map.get(recordDO.getPublishId());
                    if (Objects.equals("1", hasManyLook) && recordList.size() <= 1) {
                        continue;
                    } else {
                        result.add(LatentPublishStatisticDTO.builder()
                                .publishId(recordDO.getPublishId())
                                .date(DateUtil.formatDate(publishDO.getCreateTime(), "yyyy-MM-dd") + "发布")
                                .desc((Objects.isNull(publishDO.getProductiveYear()) ? "" : (publishDO.getProductiveYear() + "年 | ")) +
                                        (StringUtils.isEmpty(publishDO.getUsageHours()) ? "" : (publishDO.getUsageHours() + "小时 | ")) +
                                        (StringUtils.isEmpty(publishDO.getCityName()) ? "" : publishDO.getCityName()))
                                .hasCollect(recordDO.getHasCollect() ? "有" : "无")
                                .hasTakeMobile(recordDO.getHasDial() ? "有" : "无")
                                .image(publishDO.getMainMedia())
                                .lookTimes(recordList.size())
                                .mostLookTime(DateUtil.getTime(recordList.get(0).getLookTime().longValue()))
                                .time(recordList.get(0).getLookTime().longValue())
                                .price(publishDO.getOutPrice() == null ? DefaultHandleConstant.PUBLISH_OUT : publishDO.getOutPrice())
                                .recentTime(recentTime)
                                .title(publishDO.getTitle())
                                .score(getScore(recordList))
                                .build());
                    }
                    publishIds.add(recordDO.getPublishId());
                }
            }
        }
        if (!ObjectUtils.isEmpty(result)) {
            if (Objects.equals("0", sortBy)) {
                result = result.stream().sorted((o1, o2) -> -o1.getScore().compareTo(o2.getScore())).collect(Collectors.toList());
            } else if (Objects.equals("1", sortBy)) {
                result = result.stream().sorted((o1, o2) -> -o1.getLookTimes().compareTo(o2.getLookTimes())).collect(Collectors.toList());
            } else {
                result = result.stream().sorted((o1, o2) -> -o1.getTime().compareTo(o2.getTime())).collect(Collectors.toList());
            }
        }
        return UserLatentDTO.builder().user(user).publishs(result).build();
    }
}
