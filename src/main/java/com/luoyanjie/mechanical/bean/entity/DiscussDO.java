package com.luoyanjie.mechanical.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer userId;

    private String content;

    private Integer publishId;

    private Integer publishUserId;

    private Byte publishUserReadStatus;

    private Integer floorDiscussId;

    private Integer floorDiscussUserId;

    private Integer beReplyDiscussId;

    private Integer beReplyReplyDiscussUserId;
}