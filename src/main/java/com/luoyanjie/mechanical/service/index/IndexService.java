package com.luoyanjie.mechanical.service.index;

import com.luoyanjie.mechanical.bean.dto.index.AdminIndexDataDTO;
import com.luoyanjie.mechanical.bean.dto.index.ThisMonthDataIncreaseDTO;
import com.luoyanjie.mechanical.bean.dto.index.ThisYearDataDistributionDTO;

import java.util.List;

public interface IndexService {
    AdminIndexDataDTO getIndexData();

    List<ThisYearDataDistributionDTO> getThisYearDataDistribution();

    List<ThisMonthDataIncreaseDTO> getThisMonthDataIncrease();
}
