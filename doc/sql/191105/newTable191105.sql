DROP TABLE IF EXISTS `car_user`;
CREATE TABLE `car_user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`is_deleted` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否被删除：1已被删除、0未',
	`head_portrait` VARCHAR(200) NULL DEFAULT '' COMMENT '头像',
	`name` VARCHAR(50) NULL DEFAULT NULL COMMENT '姓名',
	`nick_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '昵称',
	`sex` TINYINT(2) NULL DEFAULT NULL COMMENT '性别',
	`phone` VARCHAR(20) NULL DEFAULT NULL COMMENT '手机号',
	`openid` VARCHAR(200) NULL DEFAULT NULL COMMENT '微信小程序授权，openid',
	`session_key` VARCHAR(50) NULL DEFAULT NULL COMMENT 'session_key',
	`vip_start_time` DATETIME NULL DEFAULT NULL COMMENT '会员有效开始时间',
	`vip_end_time` DATETIME NULL DEFAULT NULL COMMENT '会员有效截止时间',
	PRIMARY KEY (`id`)
)
COMMENT='车商用户'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

DROP TABLE IF EXISTS `car_user_apply`;
CREATE TABLE `car_user_apply` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`user_id` INT NOT NULL COMMENT '用户id',
	`name` VARCHAR(50) NULL DEFAULT NULL COMMENT '姓名',
	`mobile` VARCHAR(11) NULL DEFAULT NULL COMMENT '联系电话',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	`status` TINYINT(2) NOT NULL COMMENT '申请状态，0-申请中，1-拒绝，2-通过',
	PRIMARY KEY (`id`)
)
COMMENT='车商申请记录表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

DROP TABLE IF EXISTS `name_card`;
CREATE TABLE `name_card` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`user_id` INT NOT NULL COMMENT '用户id',
	`name` VARCHAR(50) NULL DEFAULT NULL COMMENT '姓名',
	`mobile` VARCHAR(11) NULL DEFAULT NULL COMMENT '联系电话',
	`wechat_no` VARCHAR(100) NULL DEFAULT NULL COMMENT '微信号',
	`company_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '',
	`job_title` VARCHAR(100) NULL DEFAULT NULL COMMENT '',
	`company_address` VARCHAR(100) NULL DEFAULT NULL COMMENT '公司地址',
	`location` VARCHAR(100) NULL DEFAULT NULL COMMENT '',
	`introduce` VARCHAR(200) NULL COMMENT '',
	`voice_introduce` VARCHAR(500) NULL DEFAULT NULL COMMENT '语音介绍',
	`voice_introduce_time` INT(11) NULL DEFAULT NULL COMMENT '语音秒数',
	PRIMARY KEY (`id`)
)
COMMENT='名片表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

DROP TABLE IF EXISTS `name_card_image_video`;
CREATE TABLE `name_card_image_video` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`card_id` INT NOT NULL COMMENT '名片id',
	`file` VARCHAR(500) NOT NULL,
	`type` CHAR(5) NOT NULL COMMENT '类型：IMAGE、VIDEO',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `uk` (`card_id`, `file`),
	INDEX `idx_card_id` (`card_id`)
)
COMMENT='名片-图片视频关系表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

DROP TABLE IF EXISTS `name_card_friend`;
CREATE TABLE `name_card_friend` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`user_id` INT NOT NULL COMMENT '用户id',
	`card_id` INT NOT NULL COMMENT '友情合作名片id',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `uk` (`user_id`, `card_id`),
	INDEX `idx_card_id` (`card_id`)
)
COMMENT='友情合作表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`from_car_user_id` INT NOT NULL COMMENT '发送者用户id',
	`to_car_user_id` INT NOT NULL COMMENT '接收者用户id',
	`content` VARCHAR(500) NOT NULL COMMENT '内容，可能是图片、视频、文字、设备id',
	`type` VARCHAR(10) NOT NULL COMMENT '类型，TEXT-文本，IMAGE-图片，VIDEO-视频，PUBLISH-设备',
	`user_group` VARCHAR(30) NOT NULL COMMENT '分组标识',
	PRIMARY KEY (`id`),
	INDEX `idx_from_car_user_id` (`from_car_user_id`),
	INDEX `idx_to_car_user_id` (`to_car_user_id`)
)
COMMENT='消息表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

DROP TABLE IF EXISTS `name_card_look_record`;
CREATE TABLE `name_card_look_record` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`user_id` INT NOT NULL COMMENT '用户id',
	`card_id` INT NOT NULL COMMENT '看过的名片id',
	PRIMARY KEY (`id`),
	INDEX `idx_user_id` (`user_id`)
)
COMMENT='名片浏览记录表（看自己的不算）'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

DROP TABLE IF EXISTS `friend_card_record`;
CREATE TABLE `friend_card_record` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`user_id` INT NOT NULL COMMENT '用户id',
	`card_id` INT NOT NULL COMMENT '添加过的名片id',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `uk` (`user_id`, `card_id`),
	INDEX `idx_user_id` (`user_id`)
)
COMMENT='友情合作添加记录'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

DROP TABLE IF EXISTS `car_user_collection`;
CREATE TABLE `car_user_collection` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`car_user_id` INT(11) NOT NULL COMMENT '收藏者ID',
	`publish_id` INT(11) NOT NULL COMMENT '发布ID',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `uk` (`publish_id`, `car_user_id`),
	INDEX `idx_car_user_id` (`car_user_id`),
	INDEX `idx_publish_id` (`publish_id`)
)
COMMENT='车商收藏表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

DROP TABLE IF EXISTS `publish_look_record`;
CREATE TABLE `publish_look_record` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`user_id` INT(11) NOT NULL COMMENT '用户id',
	`publish_id` INT(11) NOT NULL COMMENT '看过的设备id',
	PRIMARY KEY (`id`),
	INDEX `idx_user_id` (`user_id`)
)
COMMENT='设备浏览记录表（看自己的不算）'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

