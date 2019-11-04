#
ALTER TABLE purchase_information
    ADD expected_price VARCHAR(100) NULL COMMENT '期望价格';
ALTER TABLE purchase_information
    ADD usage_hours VARCHAR(100) NULL COMMENT '使用小时数';
ALTER TABLE purchase_information
    ADD year_limit_num VARCHAR(100) NULL COMMENT '年限';
ALTER TABLE purchase_information
    ADD has_invoice TINYINT NULL COMMENT '其他要求';
ALTER TABLE purchase_information
    ADD has_certificate TINYINT NULL COMMENT '其他要求';
ALTER TABLE purchase_information
    ADD contact VARCHAR(100) NULL COMMENT '联系人';
ALTER TABLE purchase_information
    ADD remark VARCHAR(100) NULL COMMENT '备注';