-- 1. 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS coupon_center_db
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- 2. 使用该数据库
USE coupon_center_db;

-- 3. 创建优惠券模板表
CREATE TABLE IF NOT EXISTS `coupon_template` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `available` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可用',
    `name` VARCHAR(64) NOT NULL COMMENT '模板名称',
    `shop_id` BIGINT DEFAULT NULL COMMENT '适用门店 ID，NULL 表示全店',
    `description` VARCHAR(255) NOT NULL COMMENT '描述信息',
    `type` VARCHAR(32) NOT NULL COMMENT '优惠券类型（枚举编码）',
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `rule` TEXT NOT NULL COMMENT '优惠券使用规则（JSON 格式）',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券模板表';
