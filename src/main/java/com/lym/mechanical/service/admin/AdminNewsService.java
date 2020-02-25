package com.lym.mechanical.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.lym.mechanical.bean.dto.admin.AdminNewsDTO;
import com.lym.mechanical.bean.dto.admin.AdminNewsTypeDTO;
import com.lym.mechanical.bean.entity.NewsDO;
import com.lym.mechanical.bean.entity.NewsTypeDO;
import com.lym.mechanical.bean.param.admin.AdminNewsParam;
import com.lym.mechanical.bean.param.admin.AdminNewsSearchParam;
import com.lym.mechanical.bean.param.admin.AdminNewsTypeParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.NewsDOMapper;
import com.lym.mechanical.dao.mapper.NewsTypeDOMapper;
import com.lym.mechanical.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @create 2020-02-25 12:18
 * Good good code, day day up!
 **/
@Service
public class AdminNewsService {

    @Autowired
    private NewsDOMapper newsDOMapper;

    @Autowired
    private NewsTypeDOMapper newsTypeDOMapper;

    public PageData<AdminNewsDTO> newsList(AdminNewsSearchParam param) {
        PageData.checkPageParam(param.getPageNum(), param.getPageSize());
        PageHelper.startPage(param.getPageNum(), param.getPageSize());

        Page<NewsDO> newsDOS = (Page<NewsDO>) newsDOMapper.selectForWeb(param.getTypeId(), param.getIsValid());
        if (ObjectUtils.isEmpty(newsDOS)) {
            return PageData.noData(param.getPageSize());
        }

        List<NewsTypeDO> newsTypeDOS = newsTypeDOMapper.selectAll();
        Map<Integer, NewsTypeDO> map = ObjectUtils.isEmpty(newsTypeDOS) ? Maps.newHashMap() :
                newsTypeDOS.stream().collect(Collectors.toMap(NewsTypeDO::getId, row -> row));
        return PageData.data(newsDOS, newsDOS.getResult().stream().map(row -> {
            NewsTypeDO newsTypeDO = map.get(row.getTypeId());
            return AdminNewsDTO.builder()
                    .createTime(DateUtil.formatDateDefault(row.getCreateTime()))
                    .id(row.getId())
                    .image(row.getImage())
                    .isValid(row.getIsValid() ? "1" : "0")
                    .type(Objects.isNull(newsTypeDO) ? "" : newsTypeDO.getName())
                    .typeId(row.getTypeId())
                    .build();
        })
        .collect(Collectors.toList()));
    }

    public Boolean addNews(AdminNewsParam param) {
        newsDOMapper.insertSelective(
                NewsDO.builder()
                    .createTime(DateUtil.now())
                    .updateTime(DateUtil.now())
                    .image(param.getImage())
                    .typeId(param.getTypeId())
                    .isValid(Objects.equals("1", param.getIsValid()))
                    .build()
        );
        return Boolean.TRUE;
    }

    public Boolean modifyNews(AdminNewsParam param) {
        NewsDO newsDO = newsDOMapper.selectByPrimaryKey(param.getId());
        if (Objects.isNull(newsDO)) {
            throw new RuntimeException("海报不存在");
        }
        newsDOMapper.updateByPrimaryKeySelective(NewsDO.builder()
                .id(param.getId())
                .updateTime(DateUtil.now())
                .image(param.getImage())
                .typeId(param.getTypeId())
                .isValid(Objects.equals("1", param.getIsValid()))
                .build());
        return Boolean.TRUE;
    }

    public Boolean deleteNews(Integer id) {
        newsDOMapper.deleteByPrimaryKey(id);
        return Boolean.TRUE;
    }

    public PageData<AdminNewsTypeDTO> typeList(Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        Page<NewsTypeDO> newsTypeDOS = (Page<NewsTypeDO>) newsTypeDOMapper.selectAll();
        if (ObjectUtils.isEmpty(newsTypeDOS)) {
            return PageData.noData(pageSize);
        }

        return PageData.data(newsTypeDOS, newsTypeDOS.getResult().stream().map(row -> AdminNewsTypeDTO.builder()
                    .createTime(DateUtil.formatDateDefault(row.getCreateTime()))
                    .id(row.getId())
                    .name(row.getName())
                    .build())
                .collect(Collectors.toList()));
    }

    public Boolean addType(AdminNewsTypeParam param) {
        newsTypeDOMapper.insertSelective(
                NewsTypeDO.builder()
                        .createTime(DateUtil.now())
                        .updateTime(DateUtil.now())
                        .name(param.getName())
                        .build()
        );
        return Boolean.TRUE;
    }

    public Boolean modifyType(AdminNewsTypeParam param) {
        NewsTypeDO newsTypeDO = newsTypeDOMapper.selectByPrimaryKey(param.getId());
        if (Objects.isNull(newsTypeDO)) {
            throw new RuntimeException("主题不存在");
        }
        newsTypeDOMapper.updateByPrimaryKeySelective(NewsTypeDO.builder()
                .id(param.getId())
                .updateTime(DateUtil.now())
                .name(param.getName())
                .build());
        return Boolean.TRUE;
    }

    public Boolean deleteType(Integer id) {
        newsTypeDOMapper.deleteByPrimaryKey(id);
        return Boolean.TRUE;
    }

    public List<NewsTypeDO> selectTypeList() {
        return newsTypeDOMapper.selectAll();
    }
}
