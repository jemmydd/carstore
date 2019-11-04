DROP TABLE IF EXISTS `wx_qr`;
CREATE TABLE `wx_qr`
(
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time` TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `md5_param`   VARCHAR(32)  NOT NULL COMMENT 'md5后的参数',
    `url`         VARCHAR(200) NOT NULL COMMENT '地址',
    `type`        VARCHAR(10)  NOT NULL COMMENT 'PG_QR-小程序二维码',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk` (`md5_param`)
)
    COMMENT ='微信二维码表避免多次请求'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;