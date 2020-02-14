package com.lym.mechanical.service.admin;

import com.google.common.collect.Lists;
import com.lym.mechanical.bean.dto.admin.AdminStatisticDTO;
import com.lym.mechanical.bean.dto.admin.AdminStatisticMonthDTO;
import com.lym.mechanical.bean.dto.admin.AdminStatisticYearDTO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.entity.PublishDO;
import com.lym.mechanical.dao.mapper.CarUserDOMapper;
import com.lym.mechanical.dao.mapper.PublishDOMapper;
import com.lym.mechanical.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @create 2020-02-14 16:58
 * Good good code, day day up!
 **/
@Service
public class StatisticService {

    @Autowired
    private CarUserDOMapper carUserDOMapper;

    @Autowired
    private PublishDOMapper publishDOMapper;

    public AdminStatisticDTO statistic() {
        List<CarUserDO> carUserDOS = carUserDOMapper.selectAllNotAdmin();
        List<PublishDO> publishDOS = publishDOMapper.selectCarUserPublish();
        List<AdminStatisticYearDTO> adminStatisticYear = Lists.newArrayList();
        List<AdminStatisticMonthDTO> adminStatisticMonth = Lists.newArrayList();
        Date now = DateUtil.now();
        Integer maxDays = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= 12; i++) {
            Integer index = i;
            List<CarUserDO> userList = ObjectUtils.isEmpty(carUserDOS) ? Lists.newArrayList() :
                    carUserDOS.stream().filter(row -> Objects.equals((byte) 0, row.getIsDeleted()) &&
                            Objects.equals(DateUtil.formatDate(row.getCreateTime(), "yyyyM"), DateUtil.formatDate(now, "yyyy") + index))
                            .collect(Collectors.toList());
            List<PublishDO> publishList = ObjectUtils.isEmpty(publishDOS) ? Lists.newArrayList() :
                    publishDOS.stream().filter(row -> Objects.equals((byte) 0, row.getIsDeleted()) &&
                            Objects.equals(DateUtil.formatDate(row.getCreateTime(), "yyyyM"), DateUtil.formatDate(now, "yyyy") + index))
                            .collect(Collectors.toList());
            adminStatisticYear.add(AdminStatisticYearDTO.builder()
                    .month(index + "月")
                    .userCount(ObjectUtils.isEmpty(userList) ? 0 : userList.size())
                    .publishCount(ObjectUtils.isEmpty(publishList) ? 0 : publishList.size())
                    .build());
        }
        for (int i = 1; i <= maxDays; i++) {
            Integer index = i;
            List<CarUserDO> userList = ObjectUtils.isEmpty(carUserDOS) ? Lists.newArrayList() :
                    carUserDOS.stream().filter(row -> Objects.equals((byte) 0, row.getIsDeleted()) &&
                            Objects.equals(DateUtil.formatDate(row.getCreateTime(), "yyyyMMd"), DateUtil.formatDate(now, "yyyyMM") + index))
                            .collect(Collectors.toList());
            List<PublishDO> publishList = ObjectUtils.isEmpty(publishDOS) ? Lists.newArrayList() :
                    publishDOS.stream().filter(row -> Objects.equals((byte) 0, row.getIsDeleted()) &&
                            Objects.equals(DateUtil.formatDate(row.getCreateTime(), "yyyyMMd"), DateUtil.formatDate(now, "yyyyMM") + index))
                            .collect(Collectors.toList());
            adminStatisticMonth.add(AdminStatisticMonthDTO.builder()
                    .day(DateUtil.formatDate(now, "M.") + index)
                    .userCount(ObjectUtils.isEmpty(userList) ? 0 : userList.size())
                    .publishCount(ObjectUtils.isEmpty(publishList) ? 0 : publishList.size())
                    .build());
        }
        return AdminStatisticDTO.builder()
                .totalCarUserCount(ObjectUtils.isEmpty(carUserDOS) ? 0 : carUserDOS.stream().filter(
                        row -> Objects.equals((byte) 0, row.getIsDeleted())).collect(Collectors.toList()).size())
                .totalPublishCount(ObjectUtils.isEmpty(publishDOS) ? 0 : publishDOS.stream().filter(
                        row -> Objects.equals((byte) 0, row.getIsDeleted())).collect(Collectors.toList()).size())
                .adminStatisticYear(adminStatisticYear)
                .adminStatisticMonth(adminStatisticMonth)
                .build();
    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse("2000-02-21"));
        System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    }
}
