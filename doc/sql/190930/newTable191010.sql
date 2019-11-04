DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`
(
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `create_time` TIMESTAMP DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    `name`        VARCHAR(200) NULL COMMENT '名称',
    `capital`     VARCHAR(200) NULL COMMENT '首字母',
    `is_hot`      TINYINT      NULL COMMENT '是否为热门',

    PRIMARY KEY (`id`)
)
    COMMENT ='品牌'
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB;