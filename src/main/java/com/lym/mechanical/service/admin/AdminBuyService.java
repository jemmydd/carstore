package com.lym.mechanical.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.dto.admin.AdminBuyDTO;
import com.lym.mechanical.bean.dto.admin.CarUserDTO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.VipOrderDO;
import com.lym.mechanical.bean.entity.VipRecordDO;
import com.lym.mechanical.bean.param.admin.AdminBuySearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import com.lym.mechanical.dao.mapper.VipRecordDOMapper;
import com.lym.mechanical.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @create 2020-02-24 15:21
 * Good good code, day day up!
 **/
@Service
public class AdminBuyService {

    @Autowired
    private VipRecordDOMapper vipRecordDOMapper;

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    public PageData<AdminBuyDTO> list(AdminBuySearchParam param) {
        PageData.checkPageParam(param.getPageNum(), param.getPageSize());
        PageHelper.startPage(param.getPageNum(), param.getPageSize());

        Page<VipRecordDO> recordDOS = (Page<VipRecordDO>) vipRecordDOMapper.selectForWeb(param.getStartTime(), param.getEndTime());
        List<VipRecordDO> data = recordDOS.getResult();
        if (ObjectUtils.isEmpty(data)) {
            return PageData.noData(param.getPageSize());
        }
        List<CarUserDO> carUserDOS = carUserDOMapper.selectBatchByPrimaryKey(data.stream().map(VipRecordDO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, CarUserDO> userMap = ObjectUtils.isEmpty(carUserDOS) ? Maps.newHashMap() :
                carUserDOS.stream().collect(Collectors.toMap(CarUserDO::getId, row -> row));
        return PageData.data(recordDOS, data.stream().map(row -> {
            CarUserDO carUserDO = userMap.get(row.getUserId());
            return AdminBuyDTO.builder()
                    .buyTime(DateUtil.formatDateDefault(row.getCreateTime()))
                    .buyType(Objects.equals((byte) 0, row.getType()) ? "用户购买" : "后台开通")
                    .days(getDays(row.getDays()))
                    .id(row.getId())
                    .price(Objects.isNull(row.getPrice()) ? "" : row.getPrice().stripTrailingZeros().toPlainString())
                    .user(Objects.isNull(carUserDO) ? null :
                            CarUserDTO.builder()
                                    .mobile(carUserDO.getPhone())
                                    .nickName(carUserDO.getNickName())
                                    .avatar(carUserDO.getHeadPortrait())
                                    .build())
                    .build();
        }).collect(Collectors.toList()));
    }

    private String getDays(String days) {
        if (StringUtils.isEmpty(days)) {
            return "未知";
        }
        String unit;
        if (days.contains("d")) {
            unit = "天";
        } else if (days.contains("m")) {
            unit = "个月";
        } else {
            unit = "年";
        }
        return days.substring(0, days.length() - 1) + unit;
    }
}
