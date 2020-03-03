package com.lym.mechanical.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lym.mechanical.bean.common.Constant;
import com.lym.mechanical.bean.dto.admin.AdminMessageDTO;
import com.lym.mechanical.bean.dto.admin.AdminVipDTO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.MessageDO;
import com.lym.mechanical.bean.entity.MessageRecordDO;
import com.lym.mechanical.bean.entity.VipConfigDO;
import com.lym.mechanical.bean.param.admin.AdminMessageParam;
import com.lym.mechanical.bean.param.admin.AdminMessageSearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import com.lym.mechanical.dao.mapper.MessageDOMapper;
import com.lym.mechanical.dao.mapper.MessageRecordDOMapper;
import com.lym.mechanical.dao.mapper.VipConfigDOMapper;
import com.lym.mechanical.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @create 2020-02-24 16:27
 * Good good code, day day up!
 **/
@Service
public class AdminSettingService {

    @Autowired
    private VipConfigDOMapper vipConfigDOMapper;

    @Autowired
    private MessageDOMapper messageDOMapper;

    @Autowired
    private MessageRecordDOMapper messageRecordDOMapper;

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    public PageData<AdminVipDTO> vipList(Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        Page<VipConfigDO> configDOS = (Page<VipConfigDO>) vipConfigDOMapper.selectAll();
        if (ObjectUtils.isEmpty(configDOS)) {
            return PageData.noData(pageSize);
        }
        return PageData.data(configDOS, configDOS.stream().map(row -> AdminVipDTO.builder()
                .createTime(DateUtil.formatDateDefault(row.getCreateTime()))
                .days(days(row.getDays()))
                .desc(row.getDescription())
                .isLimit(row.getIsLimit() ? "是" : "否")
                .price(row.getPrice().stripTrailingZeros().toPlainString())
                .id(row.getId())
                .build())
        .collect(Collectors.toList()));
    }

    private String days(String days) {
        if (StringUtils.isEmpty(days)) {
            return "未知";
        }
        String unit;
        if (days.contains("d")) {
            unit = "天";
        } else if (days.contains("m")) {
            unit = "月";
        } else {
            unit = "年";
        }
        return days.substring(0, days.length() - 1) + unit;
    }

    public Boolean addVip(AdminVipDTO param) {
        vipConfigDOMapper.insertSelective(VipConfigDO.builder()
                .createTime(DateUtil.now())
                .updateTime(DateUtil.now())
                .days(getDays(param.getDays()))
                .description(param.getDesc())
                .isLimit(Objects.equals("1", param.getIsLimit()))
                .price(new BigDecimal(param.getPrice()))
                .build());
        return Boolean.TRUE;
    }

    private String getDays(String days) {
        days = days.replaceAll("年", "y").replaceAll("月", "m").replaceAll("天", "d");
        return days.substring(0, days.length() - 1) + days.substring(days.length() - 1, days.length());
    }

    public Boolean modifyVip(AdminVipDTO param) {
        VipConfigDO vipConfigDO = vipConfigDOMapper.selectByPrimaryKey(param.getId());
        if (!Objects.isNull(vipConfigDO)) {
            vipConfigDOMapper.insertSelective(VipConfigDO.builder()
                    .id(param.getId())
                    .updateTime(DateUtil.now())
                    .days(getDays(param.getDays()))
                    .description(param.getDesc())
                    .isLimit(Objects.equals("1", param.getIsLimit()))
                    .price(new BigDecimal(param.getPrice()))
                    .build());
        }
        return Boolean.TRUE;
    }

    public Boolean deleteVip(Integer id) {
        vipConfigDOMapper.deleteByPrimaryKey(id);
        return Boolean.TRUE;
    }

    public PageData<AdminMessageDTO> messageList(AdminMessageSearchParam param) {
        PageData.checkPageParam(param.getPageNum(), param.getPageSize());
        PageHelper.startPage(param.getPageNum(), param.getPageSize());

        Page<MessageRecordDO> recordDOS = (Page<MessageRecordDO>) messageRecordDOMapper.selectForWeb(param.getStartTime(), param.getEndTime());
        if (ObjectUtils.isEmpty(recordDOS)) {
            return PageData.noData(param.getPageSize());
        }
        Set<Integer> userIds = Sets.newHashSet();
        recordDOS.forEach(row -> {
            if (!StringUtils.isEmpty(row.getUserIds())) {
                userIds.addAll(stringToIntList(row.getUserIds()));
            }
        });
        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(Lists.newArrayList(userIds));
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        return PageData.data(recordDOS, recordDOS.stream().map(row -> {
            String targetNames = "";
            if (!StringUtils.isEmpty(row.getUserIds())) {
                List<String> list = Lists.newArrayList();
                for (String s : row.getUserIds().split(",")) {
                    CarUserDO carUserDO = userMap.get(Integer.parseInt(s));
                    if (!Objects.isNull(carUserDO)) {
                        list.add(carUserDO.getNickName());
                    }
                }
                if (!ObjectUtils.isEmpty(list)) {
                    targetNames = StringUtils.join(list, ",");
                }
            } else {
                targetNames = "全部用户";
            }
            return AdminMessageDTO.builder()
                    .content(row.getContent())
                    .createTime(DateUtil.formatDateDefault(row.getCreateTime()))
                    .target(StringUtils.isEmpty(row.getUserIds()) ? "全部用户" : row.getUserIds())
                    .targetNames(targetNames)
                    .build();
        }).collect(Collectors.toList()));
    }

    public Boolean send(AdminMessageParam param) {
        Date now = DateUtil.now();
        messageRecordDOMapper.insertSelective(
                MessageRecordDO.builder()
                        .createTime(now)
                        .updateTime(now)
                        .content(param.getContent())
                        .userIds(StringUtils.isEmpty(param.getUserIds()) ? null : param.getUserIds())
                        .build()
        );
        List<Integer> userIds;
        if (!StringUtils.isEmpty(param.getUserIds())) {
            userIds = stringToIntList(param.getUserIds());
        } else {
            List<CarUserDO> carUserDOS = carUserDOMapper.selectAllNotAdmin();
            userIds = ObjectUtils.isEmpty(carUserDOS) ? Lists.newArrayList() :
                    carUserDOS.stream().map(CarUserDO::getId).distinct().collect(Collectors.toList());
        }
        if (!ObjectUtils.isEmpty(userIds)) {
            List<MessageDO> messageDOS = userIds.stream().map(row -> MessageDO.builder()
                    .createTime(now)
                    .updateTime(now)
                    .fromCarUserId(0)
                    .toCarUserId(row)
                    .content(param.getContent())
                    .type("TEXT")
                    .userGroup("0-" + row)
                    .isRead(Boolean.FALSE)
                    .build())
                    .collect(Collectors.toList());
            if (!ObjectUtils.isEmpty(messageDOS)) {
                messageDOMapper.insertBatchSelective(messageDOS);
            }
        }
        return Boolean.TRUE;
    }

    private List<Integer> stringToIntList(String str) {
        if (StringUtils.isEmpty(str)) {
            return Lists.newArrayList();
        }
        return Arrays.stream(str.split(",")).map(row -> Integer.parseInt(row)).collect(Collectors.toList());
    }
}
