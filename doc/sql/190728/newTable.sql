DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`                         INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time`                TIMESTAMP             DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`                TIMESTAMP             DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `is_deleted`                 TINYINT      NOT NULL DEFAULT 0 COMMENT '是否被删除：1已被删除、0未',

    `background_image`           VARCHAR(200) NOT NULL DEFAULT '' COMMENT '主页背景',
    `head_portrait`              VARCHAR(200) NULL     DEFAULT '' COMMENT '头像',

    `name`                       VARCHAR(50)  NULL COMMENT '姓名',
    `nick_name`                  VARCHAR(50)  NULL COMMENT '昵称',
    `sex`                        CHAR(1)      NULL COMMENT '性别：M-男性、F-女性',
    `phone`                      VARCHAR(20)  NULL COMMENT '手机号',
    `user_identity`              VARCHAR(50)  NOT NULL COMMENT '用户身份',

    `province_code`              VARCHAR(10)  NOT NULL DEFAULT '' COMMENT '省份code',
    `province_name`              VARCHAR(20)  NOT NULL DEFAULT '' COMMENT '省份名称',
    `city_code`                  VARCHAR(10)  NOT NULL DEFAULT '' COMMENT '城市code',
    `city_name`                  VARCHAR(20)  NOT NULL DEFAULT '' COMMENT '城市名称',
    `area_code`                  VARCHAR(10)  NOT NULL DEFAULT '' COMMENT '城市code',
    `area_name`                  VARCHAR(20)  NOT NULL DEFAULT '' COMMENT '城市名称',

    `publish_count`              INT          NOT NULL DEFAULT 0 COMMENT '发布次数',
    `view_phone_count`           INT          NOT NULL DEFAULT 0 COMMENT '查看电话次数',
    `fans_count`                 INT          NOT NULL DEFAULT 0 COMMENT '粉丝数',
    `follow_count`               INT          NOT NULL DEFAULT 0 COMMENT '关注数',
    `collection_count`           INT          NOT NULL DEFAULT 0 COMMENT '收藏数',
    `purchase_information_count` INT          NOT NULL DEFAULT 0 COMMENT '发布的求购信息数量',
    `invite_count`               INT          NOT NULL DEFAULT 0 COMMENT '邀请数量',

    `signature`                  VARCHAR(300) NOT NULL DEFAULT '' COMMENT '个性签名',

    `account`                    VARCHAR(100) NOT NULL DEFAULT '' COMMENT '账户名',
    `password`                   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '密码',
    `token`                      VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'token',

    `openid`                     VARCHAR(200) NULL COMMENT '微信小程序授权，openid',
    `new_bind`                   TINYINT      NULL COMMENT '是否是新关联的微信用户',

    PRIMARY KEY (`id`)
)
    COMMENT ='用户表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`
(
    `id`          INT NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time` TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `from_id`     INT NOT NULL COMMENT '关注者ID',
    `to_id`       INT NOT NULL COMMENT '被关注者ID',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk` (`from_id`, `to_id`),
    INDEX `idx_from_id` (`from_id`),
    INDEX `idx_to_id` (`to_id`)
)
    COMMENT ='关注表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id`             INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time`    TIMESTAMP             DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    TIMESTAMP             DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `is_deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '是否被删除：1已被删除、0未',

    `category_level` INT          NOT NULL COMMENT '级别',
    `parent_id`      INT          NOT NULL COMMENT '父ID',
    `name`           VARCHAR(100) NOT NULL COMMENT '类目名称',
    `sorted_num`     INT          NOT NULL COMMENT '排序码',
    `icon`           VARCHAR(200) NOT NULL DEFAULT '' COMMENT '类目图标，格式：/mechanical/categoryFirst/icon/57cd812b-d7ce-4654-9f15-c07d16c2a863.jpg',

    `is_show_index`  TINYINT      NOT NULL DEFAULT 1 COMMENT '是否在首页展示：1-在首页展示、0不在首页展示',

    PRIMARY KEY (`id`)
)
    COMMENT ='类目表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

DROP TABLE IF EXISTS `publish`;
CREATE TABLE `publish`
(
    `id`                   INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time`          TIMESTAMP             DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`          TIMESTAMP             DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `is_deleted`           TINYINT      NOT NULL DEFAULT 0 COMMENT '是否被删除：1已被删除、0未',

    `user_id`              INT          NOT NULL COMMENT '用户ID，发布者',

    `category_first_id`    INT          NULL COMMENT '一级类目ID',
    `category_second_id`   INT          NULL COMMENT '二级类目ID',
    `phone`                VARCHAR(20)  NULL COMMENT '手机号',

    `title`                VARCHAR(200) NULL COMMENT '标题',
    `text_introduce`       VARCHAR(200) NULL COMMENT '文本介绍',
    `voice_introduce`      VARCHAR(200) NULL COMMENT '语音介绍，格式：/mechanical/publish/voiceIntroduce/57cd812b-d7ce-4654-9f15-c07d16c2a863.mp3',
    `voice_introduce_time` INT          NULL COMMENT '语音秒数',
    `main_media`           VARCHAR(200) NULL COMMENT '主图/视频首帧，格式：/mechanical/publish/mainMedia/57cd812b-d7ce-4654-9f15-c07d16c2a863.jpg',
    `contact_phone`        VARCHAR(20)  NULL COMMENT '联系电话',

    `province_code`        VARCHAR(10)  NULL     DEFAULT '' COMMENT '省份code',
    `province_name`        VARCHAR(20)  NULL     DEFAULT '' COMMENT '省份名称',
    `city_code`            VARCHAR(10)  NULL     DEFAULT '' COMMENT '城市code',
    `city_name`            VARCHAR(20)  NULL     DEFAULT '' COMMENT '城市名称',
    `area_code`            VARCHAR(10)  NULL     DEFAULT '' COMMENT '城市code',
    `area_name`            VARCHAR(20)  NULL     DEFAULT '' COMMENT '城市名称',

    `in_price`             VARCHAR(100) NULL COMMENT '入手价格，前端传啥存啥',
    `out_price`            VARCHAR(100) NULL COMMENT '转让价格，前端传啥存啥',
    `productive_year`      INT          NULL COMMENT '年份',

    `like_count`           INT          NOT NULL DEFAULT 0 COMMENT '点赞数',
    `view_count`           INT          NOT NULL DEFAULT 0 COMMENT '浏览数',
    `discuss_count`        INT          NOT NULL DEFAULT 0 COMMENT '留言数',
    `collection_count`     INT          NOT NULL DEFAULT 0 COMMENT '抽藏数',
    `view_phone_count`     INT          NOT NULL DEFAULT 0 COMMENT '查看电话次数',

    `shelf_status`         TINYINT      NOT NULL DEFAULT 1 COMMENT '上架状态：1出售中、0已下架',

    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_category_second_id` (`category_second_id`)
)
    COMMENT ='发布表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

DROP TABLE IF EXISTS `publish_image_video`;
CREATE TABLE `publish_image_video`
(
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time` TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `publish_id`  INT          NOT NULL COMMENT '发布ID',
    `file`        VARCHAR(200) NOT NULL COMMENT '格式：/mechanical/publishImageVideo/file/57cd812b-d7ce-4654-9f15-c07d16c2a863.mp3',
    `type`        CHAR(5)      NOT NULL COMMENT '类型：IMAGE、VIDEO',
    `first_pic`   VARCHAR(200) NOT NULL COMMENT '如果是视频，则存首帧图：/mechanical/publish/voiceIntroduce/57cd812b-d7ce-4654-9f15-c07d16c2a863.mp3',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk` (`publish_id`, `file`),
    INDEX `idx_publish_id` (`publish_id`)
)
    COMMENT ='发布带有的图片视频表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`
(
    `id`          INT NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time` TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `user_id`     INT NOT NULL COMMENT '收藏者ID',
    `publish_id`  INT NOT NULL COMMENT '发布ID',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk` (`publish_id`, `user_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_publish_id` (`publish_id`)
)
    COMMENT ='收藏表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

DROP TABLE IF EXISTS `discuss`;
CREATE TABLE `discuss`
(
    `id`                             INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time`                    TIMESTAMP             DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`                    TIMESTAMP             DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `user_id`                        INT          NOT NULL COMMENT '评论者ID',
    `content`                        VARCHAR(300) NOT NULL DEFAULT '' COMMENT '本次评论的内容',

    `publish_id`                     INT          NOT NULL COMMENT '发布ID',
    `publish_user_id`                INT          NOT NULL COMMENT '发布这个发布的用户ID',
    `publish_user_read_status`       TINYINT      NOT NULL DEFAULT 0 COMMENT '发布这个发布的用户读取状态，1读取、0未读',

    `floor_discuss_id`               INT          NULL COMMENT '主楼ID',
    `floor_discuss_user_id`          INT          NULL COMMENT '主楼的发布者ID',

    `be_reply_discuss_id`            INT          NULL COMMENT '被回复的评论的ID',
    `be_reply_reply_discuss_user_id` INT          NULL COMMENT '被回复的评论的评论者ID',

    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_publish_id` (`publish_id`),
    INDEX `idx_publish_user_id` (`publish_user_id`)
)
    COMMENT ='评论动态表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

DROP TABLE IF EXISTS `publish_like`;
CREATE TABLE `publish_like`
(
    `id`          INT NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time` TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `user_id`     INT NOT NULL COMMENT '点赞者ID',
    `publish_id`  INT NOT NULL COMMENT '发布ID',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk` (`publish_id`, `user_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_publish_id` (`publish_id`)
)
    COMMENT ='点赞表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`
(
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time` TIMESTAMP             DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP             DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `bind_id`     INT                   DEFAULT NULL COMMENT '绑定的用户，小程序跳转到这个的主页',
    `image`       VARCHAR(200) NOT NULL COMMENT '轮播图片URL',
    `is_valid`    TINYINT      NOT NULL DEFAULT 1 COMMENT '是否有效：1有效、0无效',
    `user_id`     INT          NOT NULL COMMENT '创建者ID',

    PRIMARY KEY (`id`)
)
    COMMENT ='banner表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

# START 190817
DROP TABLE IF EXISTS `cooperation_company`;
CREATE TABLE `cooperation_company`
(
    `id`            INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time`   TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`   TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `image`         VARCHAR(200) NOT NULL COMMENT '公司的图片',
    `name`          VARCHAR(50)  NOT NULL COMMENT '公司名称',
    `biz_introduce` VARCHAR(200) NOT NULL COMMENT '业务介绍',
    `address`       VARCHAR(200) NOT NULL COMMENT '地址',
    `contact_phone` VARCHAR(20)  NOT NULL COMMENT '联系电话',

    `creator`       INT          NOT NULL COMMENT '创建者ID',

    PRIMARY KEY (`id`)
)
    COMMENT ='合作公司表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

# START 190824
DROP TABLE IF EXISTS `purchase_information`;
CREATE TABLE `purchase_information`
(
    `id`                 INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time`        TIMESTAMP             DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`        TIMESTAMP             DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `category_first_id`  INT          NULL COMMENT '一级类目ID',
    `category_second_id` INT          NULL COMMENT '二级类目ID',

    `province_code`      VARCHAR(10)  NULL     DEFAULT '' COMMENT '省份code',
    `province_name`      VARCHAR(20)  NULL     DEFAULT '' COMMENT '省份名称',
    `city_code`          VARCHAR(10)  NULL     DEFAULT '' COMMENT '城市code',
    `city_name`          VARCHAR(20)  NULL     DEFAULT '' COMMENT '城市名称',
    `area_code`          VARCHAR(10)  NULL     DEFAULT '' COMMENT '城市code',
    `area_name`          VARCHAR(20)  NULL     DEFAULT '' COMMENT '城市名称',

    `new_old_level`      VARCHAR(200) NULL     DEFAULT '' COMMENT '新旧程度',
    `title`              VARCHAR(200) NULL COMMENT '标题',
    `content`            VARCHAR(300) NULL COMMENT '内容',
    `contact_mobile`     VARCHAR(300) NULL COMMENT '联系电话',

    `view_count`         INT          NOT NULL DEFAULT 0 COMMENT '浏览数',

    `shelf_status`       TINYINT      NOT NULL DEFAULT 1 COMMENT '上架状态：1出售中、0已下架',

    `creator`            INT          NOT NULL COMMENT '创建者ID',

    PRIMARY KEY (`id`)
)
    COMMENT ='求购信息'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

DROP TABLE IF EXISTS `invite`;
CREATE TABLE `invite`
(
    `id`          INT NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time` TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `from_id`     INT NOT NULL COMMENT '邀请者ID',
    `to_id`       INT NOT NULL COMMENT '被邀请者ID',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk` (`from_id`, `to_id`),
    INDEX `idx_from_id` (`from_id`),
    INDEX `idx_to_id` (`to_id`)
)
    COMMENT ='邀请表'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;

DROP TABLE IF EXISTS `wx_access_token`;
CREATE TABLE `wx_access_token`
(
    `id`           INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time`  TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`  TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `access_token` VARCHAR(600) NULL COMMENT '微信接口返回的accessToken',
    `app_id`       VARCHAR(50)  NOT NULL COMMENT 'permission表的ID集合',
    `secret`       VARCHAR(50)  NOT NULL COMMENT '排序字段',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_id_secret` (`app_id`, `secret`)
)
    COMMENT ='wx的accessToken'
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;

