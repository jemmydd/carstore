DROP TABLE IF EXISTS `car_user_active`;
CREATE TABLE `car_user_active` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`user_id` INT NOT NULL COMMENT '用户id',
	`active_date` DATE NOT NULL COMMENT '活跃日期',
	PRIMARY KEY (`id`),
	INDEX `user_id`(user_id)
)
COMMENT='车商用户活跃表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;