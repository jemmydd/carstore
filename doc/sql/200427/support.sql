ALTER TABLE `car_user`
	ADD COLUMN `is_show` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否同步二手平台' AFTER `sig_create_time`;