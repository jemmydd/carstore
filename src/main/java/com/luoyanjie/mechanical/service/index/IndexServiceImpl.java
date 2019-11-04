package com.luoyanjie.mechanical.service.index;

import com.google.common.collect.Lists;
import com.luoyanjie.mechanical.bean.dto.index.AdminIndexDataDTO;
import com.luoyanjie.mechanical.bean.dto.index.ThisMonthDataIncreaseDTO;
import com.luoyanjie.mechanical.bean.dto.index.ThisYearDataDistributionDTO;
import com.luoyanjie.mechanical.bean.entity.PublishDO;
import com.luoyanjie.mechanical.bean.entity.UserDO;
import com.luoyanjie.mechanical.dao.mapper.PublishDOMapper;
import com.luoyanjie.mechanical.dao.mapper.UserDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-08-15 10:40
 * Coding happily every day!
 */
@Slf4j
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private PublishDOMapper publishDOMapper;

    private static final List<String> MONTH = Lists.newArrayList(
            "01",
            "02",
            "03",
            "04",
            "05",
            "06",
            "07",
            "08",
            "09",
            "10",
            "11",
            "12"
    );

    private static final List<String> DAY = Lists.newArrayList(
            "01",
            "02",
            "03",
            "04",
            "05",
            "06",
            "07",
            "08",
            "09",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25",
            "26",
            "27",
            "28",
            "29",
            "30",
            "31"
    );

    @Override
    public AdminIndexDataDTO getIndexData() {
        return AdminIndexDataDTO.builder()
                .publishCount(publishDOMapper.selectCount())
                .userCount(userDOMapper.selectCount())
                .build();
    }

    @Override
    public List<ThisYearDataDistributionDTO> getThisYearDataDistribution() {
        List<ThisYearDataDistributionDTO> result = Lists.newArrayList();

        String year = DateFormatUtils.format(new Date(), "yyyy");

        List<PublishDO> publishDOS = publishDOMapper.selectByYear(year);
        Map<String, List<PublishDO>> pMap = publishDOS.stream().collect(Collectors.groupingBy(row -> DateFormatUtils.format(row.getCreateTime(), "MM")));

        List<UserDO> userDOS = userDOMapper.selectByYear(year);
        Map<String, List<UserDO>> uMap = userDOS.stream().collect(Collectors.groupingBy(row -> DateFormatUtils.format(row.getCreateTime(), "MM")));

        int yearMonth = Integer.parseInt(DateFormatUtils.format(new Date(), "MM"));
        for (String month : MONTH) {
            ThisYearDataDistributionDTO thisYearDataDistributionDTO = ThisYearDataDistributionDTO.builder()
                    .monthValue(month)
                    .overOrIn(false)
                    .publishCount(pMap.get(month) == null ? 0 : pMap.get(month).size())
                    .userCount(uMap.get(month) == null ? 0 : uMap.get(month).size())
                    .build();

            if (Integer.parseInt(month) <= yearMonth) thisYearDataDistributionDTO.setOverOrIn(true);

            result.add(thisYearDataDistributionDTO);
        }

        return result;
    }

    @Override
    public List<ThisMonthDataIncreaseDTO> getThisMonthDataIncrease() {
        List<ThisMonthDataIncreaseDTO> result = Lists.newArrayList();

        String yearMonth = DateFormatUtils.format(new Date(), "yyyy-MM");

        List<PublishDO> publishDOS = publishDOMapper.selectByYearMonth(yearMonth);
        Map<String, List<PublishDO>> pMap = publishDOS.stream().collect(Collectors.groupingBy(row -> DateFormatUtils.format(row.getCreateTime(), "dd")));

        List<UserDO> userDOS = userDOMapper.selectByYearMonth(yearMonth);
        Map<String, List<UserDO>> uMap = userDOS.stream().collect(Collectors.groupingBy(row -> DateFormatUtils.format(row.getCreateTime(), "dd")));

        int yearMonthDay = Integer.parseInt(DateFormatUtils.format(new Date(), "dd"));
        String monthStr = yearMonth.substring(5, 6) + "-";
        for (String day : DAY) {
            ThisMonthDataIncreaseDTO thisMonthDataIncreaseDTO = ThisMonthDataIncreaseDTO.builder()
                    .day(monthStr + day)
                    .overOrIn(false)
                    .publishIncreaseCount(pMap.get(day) == null ? 0 : pMap.get(day).size())
                    .userIncreaseCount(uMap.get(day) == null ? 0 : uMap.get(day).size())
                    .build();

            if (Integer.parseInt(day) <= yearMonthDay) thisMonthDataIncreaseDTO.setOverOrIn(true);

            result.add(thisMonthDataIncreaseDTO);
        }

        return result;
    }
}
