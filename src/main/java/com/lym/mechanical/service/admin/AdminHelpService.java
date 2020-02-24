package com.lym.mechanical.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.lym.mechanical.bean.dto.admin.AdminFeedbackDTO;
import com.lym.mechanical.bean.dto.admin.AdminQuestionDTO;
import com.lym.mechanical.bean.entity.FeedbackDO;
import com.lym.mechanical.bean.entity.MessageDO;
import com.lym.mechanical.bean.entity.QuestionAnswerDO;
import com.lym.mechanical.bean.param.admin.AdminFeedbackDealParam;
import com.lym.mechanical.bean.param.admin.AdminFeedbackSearchParam;
import com.lym.mechanical.bean.param.admin.AdminQuestionParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.FeedbackDOMapper;
import com.lym.mechanical.dao.mapper.MessageDOMapper;
import com.lym.mechanical.dao.mapper.QuestionAnswerDOMapper;
import com.lym.mechanical.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @create 2020-02-24 15:36
 * Good good code, day day up!
 **/
@Service
public class AdminHelpService {

    @Autowired
    private QuestionAnswerDOMapper questionAnswerDOMapper;

    @Autowired
    private FeedbackDOMapper feedbackDOMapper;

    @Autowired
    private MessageDOMapper messageDOMapper;

    public PageData<AdminQuestionDTO> questionList(Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        Page<QuestionAnswerDO> questionAnswerDOS = (Page<QuestionAnswerDO>) questionAnswerDOMapper.selectAll();
        if (ObjectUtils.isEmpty(questionAnswerDOS)) {
            return PageData.noData(pageSize);
        }
        return PageData.data(questionAnswerDOS, questionAnswerDOS.getResult().stream().map(row -> AdminQuestionDTO.builder()
                .answer(row.getAnswer())
                .createTime(DateUtil.formatDateDefault(row.getCreateTime()))
                .id(row.getId())
                .question(row.getQuestion())
                .build())
        .collect(Collectors.toList()));
    }

    public Boolean addQuestion(AdminQuestionParam param) {
        questionAnswerDOMapper.insertSelective(
                QuestionAnswerDO.builder()
                        .createTime(DateUtil.now())
                        .updateTime(DateUtil.now())
                        .question(param.getQuestion())
                        .answer(param.getAnswer())
                        .build()
        );
        return Boolean.TRUE;
    }

    public Boolean modifyQuestion(AdminQuestionParam param) {
        QuestionAnswerDO questionAnswerDO = questionAnswerDOMapper.selectByPrimaryKey(param.getId());
        if (!Objects.isNull(questionAnswerDO)) {
            QuestionAnswerDO.builder()
                    .id(questionAnswerDO.getId())
                    .updateTime(DateUtil.now())
                    .question(param.getQuestion())
                    .answer(param.getAnswer())
                    .build();
        }
        return Boolean.TRUE;
    }

    public Boolean deleteQuestion(Integer id) {
        questionAnswerDOMapper.deleteByPrimaryKey(id);
        return Boolean.TRUE;
    }

    public PageData<AdminFeedbackDTO> feedbackList(AdminFeedbackSearchParam param) {
        PageData.checkPageParam(param.getPageNum(), param.getPageSize());
        PageHelper.startPage(param.getPageNum(), param.getPageSize());

        Page<FeedbackDO> feedbackDOS = (Page<FeedbackDO>) feedbackDOMapper.selectForWeb(param.getStartTime(), param.getEndTime(), param.getIsDeal());
        if (ObjectUtils.isEmpty(feedbackDOS)) {
            return PageData.noData(param.getPageSize());
        }
        return PageData.data(feedbackDOS, feedbackDOS.getResult().stream().map(row -> AdminFeedbackDTO.builder()
                .content(row.getContent())
                .createTime(DateUtil.formatDateDefault(row.getCreateTime()))
                .dealMessage(StringUtils.isEmpty(row.getContent()) ? "" : row.getDealMessage())
                .dealTime(Objects.isNull(row.getDealTime()) ? "" : DateUtil.formatDateDefault(row.getDealTime()))
                .id(row.getId())
                .images(StringUtils.isEmpty(row.getImages()) ? Lists.newArrayList() : Lists.newArrayList(row.getImages().split(",")))
                .build())
                .collect(Collectors.toList()));
    }

    public Boolean dealFeedback(AdminFeedbackDealParam param) {
        FeedbackDO feedbackDO = feedbackDOMapper.selectByPrimaryKey(param.getId());
        if (!Objects.isNull(feedbackDO)) {
            feedbackDOMapper.updateByPrimaryKeySelective(FeedbackDO.builder()
                    .id(param.getId())
                    .updateTime(DateUtil.now())
                    .isDeal(Boolean.TRUE)
                    .dealTime(DateUtil.now())
                    .dealMessage(param.getMessage())
                    .build());
            if (!StringUtils.isEmpty(param.getMessage())) {
                messageDOMapper.insertSelective(
                        MessageDO.builder()
                                .createTime(DateUtil.now())
                                .updateTime(DateUtil.now())
                                .fromCarUserId(0)
                                .toCarUserId(feedbackDO.getUserId())
                                .content(param.getMessage())
                                .type("TEXT")
                                .userGroup("0-" + feedbackDO.getUserId())
                                .isRead(Boolean.FALSE)
                                .build()
                );
            }
        }
        return Boolean.TRUE;
    }
}
