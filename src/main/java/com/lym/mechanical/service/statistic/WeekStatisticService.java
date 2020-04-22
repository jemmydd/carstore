package com.lym.mechanical.service.statistic;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.common.DefaultHandleConstant;
import com.lym.mechanical.bean.dto.my.CommonDTO;
import com.lym.mechanical.bean.dto.publish.PublishDTO;
import com.lym.mechanical.bean.dto.statistic.*;
import com.lym.mechanical.bean.entity.CarUserActiveDO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.PublishDO;
import com.lym.mechanical.dao.mapper.*;
import com.lym.mechanical.service.publish.PublishService;
import com.lym.mechanical.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname StatisticService
 * @Description
 * @Date 2020/4/22 15:07
 * @Created by jimy
 * good good code, day day up!
 */
@Service
public class WeekStatisticService {

    private static final String[] WEEK_DAYS = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private CarUserActiveDOMapper carUserActiveDOMapper;

    @Autowired
    private PublishDOMapper publishDOMapper;

    @Autowired
    private PublishLookRecordDOMapper publishLookRecordDOMapper;

    @Autowired
    private PublishService publishService;

    @Autowired
    private NameCardLookRecordDOMapper nameCardLookRecordDOMapper;

    public StatisticDTO statisticWeek(Integer userId, String startDate, String endDate) {
        CarUserDO carUserDO = carUserDOMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(carUserDO)) {
            throw new RuntimeException("用户不存在");
        }
        List<PublishDO> publishDOS = publishDOMapper.selectByCarUserIdAndDateBetween(userId, startDate, endDate);
        List<CarUserActiveDO> carUserActiveDOS = carUserActiveDOMapper.selectByUserIdAndDateBetween(userId, startDate, endDate);
        List<CommonDTO> guest = nameCardLookRecordDOMapper.selectDateGuest(userId, startDate, endDate);
        Map<String, List<CommonDTO>> guestMap = ObjectUtils.isEmpty(guest) ? Maps.newHashMap() :
                guest.stream().collect(Collectors.groupingBy(CommonDTO::getDate));
        List<StatisticWeekDTO> weekData = Lists.newArrayList();
        Date begin = DateUtil.stringToDate(startDate, "yyyy-MM-dd");
        Date end = DateUtil.stringToDate(endDate, "yyyy-MM-dd");
        int i = 0;
        Calendar calendar = Calendar.getInstance();
        while (begin.compareTo(end) <= 0) {
            String dateStr = DateUtil.formatDate(begin, "yyyyMMdd");
            List<CommonDTO> guests = guestMap.get(dateStr);
            weekData.add(StatisticWeekDTO.builder()
                    .weekDay(WEEK_DAYS[i])
                    .guestCount(ObjectUtils.isEmpty(guests) ? 0 : guests.size())
                    .build());
            i++;
            calendar.setTime(begin);
            calendar.add(Calendar.DATE, 1);
            begin = calendar.getTime();
        }

        List<PublishLookDTO> publishLooks = publishLookRecordDOMapper.selectLookCountByUserIdAndDateBetween(userId, startDate, endDate);
        Map<Integer, Integer> map = ObjectUtils.isEmpty(publishLooks) ? Maps.newHashMap() :
                publishLooks.stream().collect(Collectors.toMap(PublishLookDTO::getPublishId, row -> row.getCount()));
        List<PublishDO> publishList = ObjectUtils.isEmpty(publishLooks) ? Lists.newArrayList() :
                publishDOMapper.selectBatchByPrimaryKey(publishLooks.stream().map(PublishLookDTO::getPublishId).collect(Collectors.toList()));
        Map<Integer, PublishDO> publishMap = ObjectUtils.isEmpty(publishList) ? Maps.newHashMap() :
                publishList.stream().collect(Collectors.toMap(PublishDO::getId, row -> row));
        List<PublishDTO> hotPublishList = publishLooks.stream().map(row -> {
            PublishDO publishDO = publishMap.get(row.getPublishId());
            Integer lookCount = map.get(row.getPublishId());
            return PublishDTO.builder()
                    .id(row.getPublishId())
                    .mainMedia(Objects.isNull(publishDO) ? "" : publishDO.getMainMedia())
                    .title(Objects.isNull(publishDO) ? "" : publishDO.getTitle())
                    .productiveYear(Objects.isNull(publishDO) || Objects.isNull(publishDO.getProductiveYear()) ? 0 : publishDO.getProductiveYear())
                    .usageHours(Objects.isNull(publishDO) || StringUtils.isEmpty(publishDO.getUsageHours()) ? "" : publishDO.getUsageHours())
                    .location(Objects.isNull(publishDO) ? "" : (publishDO.getProvinceName() + " · " + publishDO.getCityName()))
                    .outPrice(Objects.isNull(publishDO) ? DefaultHandleConstant.PUBLISH_OUT : (publishDO.getOutPrice() == null ? DefaultHandleConstant.PUBLISH_OUT : publishDO.getOutPrice()))
                    .lookCount(Objects.isNull(lookCount) ? 0 : lookCount)
                    .build();
        }).collect(Collectors.toList());
        int index = 0;
        List<Integer> publishIds = hotPublishList.stream().map(PublishDTO::getId).collect(Collectors.toList());
        List<PublishDO> publishs = publishDOMapper.selectByCarUserId(userId);
        while (hotPublishList.size() < 3) {
            if (index < publishs.size()) {
                PublishDO publishDO = publishs.get(index);
                if (!publishIds.contains(publishDO.getId())) {
                    hotPublishList.add(PublishDTO.builder()
                            .id(publishDO.getId())
                            .mainMedia(Objects.isNull(publishDO) ? "" : publishDO.getMainMedia())
                            .title(Objects.isNull(publishDO) ? "" : publishDO.getTitle())
                            .productiveYear(Objects.isNull(publishDO) || Objects.isNull(publishDO.getProductiveYear()) ? 0 : publishDO.getProductiveYear())
                            .usageHours(Objects.isNull(publishDO) || StringUtils.isEmpty(publishDO.getUsageHours()) ? "" : publishDO.getUsageHours())
                            .location(Objects.isNull(publishDO) ? "" : (publishDO.getProvinceName() + " · " + publishDO.getCityName()))
                            .outPrice(Objects.isNull(publishDO) ? DefaultHandleConstant.PUBLISH_OUT : (publishDO.getOutPrice() == null ? DefaultHandleConstant.PUBLISH_OUT : publishDO.getOutPrice()))
                            .lookCount(0)
                            .build());
                }
                index++;
            } else {
                break;
            }
        }
        return StatisticDTO.builder()
                .top(StatisticTopDTO.builder()
                        .activeCount(ObjectUtils.isEmpty(carUserActiveDOS) ? 0 : carUserActiveDOS.size())
                        .avatar(carUserDO.getHeadPortrait())
                        .publishCount(ObjectUtils.isEmpty(publishDOS) ? 0 : publishDOS.size())
                        .totalGuest(ObjectUtils.isEmpty(guest) ? 0 : guest.size())
                        .build())
                .medium(StatisticMediumDTO.builder()
                        .weekData(weekData)
                        .build())
                .bottom(StatisticBottom.builder()
                        .hotPublish(hotPublishList)
                        .build())
                .build();
    }
}
