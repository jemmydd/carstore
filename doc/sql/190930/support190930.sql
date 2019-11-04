#
ALTER TABLE publish
    ADD brand_id INT NULL COMMENT '品牌ID';
ALTER TABLE publish
    ADD usage_hours VARCHAR(100) NULL COMMENT '使用小时数';
ALTER TABLE publish
    ADD has_invoice TINYINT NULL COMMENT '是否有发票，1有0无';
ALTER TABLE publish
    ADD has_certificate TINYINT NULL COMMENT '是否有合格证，1有0无';
ALTER TABLE publish
    ADD contact VARCHAR(100) NULL COMMENT '联系人';

ALTER TABLE purchase_information
    ADD brand_id INT NULL COMMENT '品牌ID';

