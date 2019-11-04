SELECT @@sql_mode;
SET @@sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

#增加长度
ALTER TABLE `user`
    CHANGE COLUMN signature `signature` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '个性签名';
ALTER TABLE `user`
    CHANGE COLUMN background_image `background_image` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '主页背景';
ALTER TABLE `category`
    CHANGE COLUMN icon `icon` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '类目图标';
ALTER TABLE `publish`
    CHANGE COLUMN voice_introduce `voice_introduce` VARCHAR(500) NULL COMMENT '语音介绍';
ALTER TABLE `publish_image_video`
    CHANGE COLUMN file `file` VARCHAR(500) NOT NULL COMMENT '';
ALTER TABLE `publish_image_video`
    CHANGE COLUMN first_pic `first_pic` VARCHAR(500) NOT NULL COMMENT '如果是视频，则存首帧图';
ALTER TABLE `banner`
    CHANGE COLUMN image `image` VARCHAR(500) NOT NULL COMMENT '轮播图片URL';
ALTER TABLE `cooperation_company`
    CHANGE COLUMN image `image` VARCHAR(500) NOT NULL COMMENT '公司的图片';
ALTER TABLE publish
    CHANGE COLUMN main_media `main_media` VARCHAR(500) NULL COMMENT '主图/视频首帧';

#添加首帧图配置
UPDATE publish
SET main_media = concat(main_media, '?x-oss-PROCESS=video/SNAPSHOT,t_1000,f_jpg,w_0,h_0,m_fast')
WHERE main_media LIKE '%.mp4';