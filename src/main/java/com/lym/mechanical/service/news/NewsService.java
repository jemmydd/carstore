package com.lym.mechanical.service.news;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.lym.mechanical.bean.entity.NewsDO;
import com.lym.mechanical.bean.entity.NewsTypeDO;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.NewsDOMapper;
import com.lym.mechanical.dao.mapper.NewsTypeDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @create 2020-02-25 12:37
 * Good good code, day day up!
 **/
@Service
public class NewsService {

    @Autowired
    private NewsTypeDOMapper newsTypeDOMapper;

    @Autowired
    private NewsDOMapper newsDOMapper;

    public List<NewsTypeDO> typeList() {
        List<NewsTypeDO> result = Lists.newArrayList(
                NewsTypeDO.builder().id(0).name("所有海报").build()
        );
        List<NewsTypeDO> newsTypeDOS = newsTypeDOMapper.selectAll();
        if (!ObjectUtils.isEmpty(newsTypeDOS)) {
            result.addAll(newsTypeDOS);
        }
        return result;
    }

    public PageData<String> newsList(Integer pageNum, Integer pageSize, Integer typeId) {
        typeId = Objects.equals(0, typeId) ? null : typeId;
        PageData.checkPageParam(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        Page<NewsDO> newsDOS = (Page<NewsDO>) newsDOMapper.selectForWeb(typeId, "1");
        if (ObjectUtils.isEmpty(newsDOS)) {
            return PageData.noData(pageSize);
        }
        return PageData.data(newsDOS, newsDOS.getResult().stream().map(NewsDO::getImage).collect(Collectors.toList()));
    }
}
