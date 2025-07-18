CREATE DATABASE IF NOT EXISTS couponcenter_coupon_db;

-- 创建 coupon_template 数据表
DROP TABLE IF EXISTS `couponcenter_coupon_db`.`coupon_template`;

CREATE TABLE IF NOT EXISTS `couponcenter_coupon_db`.`coupon_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `available` boolean NOT NULL DEFAULT false COMMENT '优惠券可用状态',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '优惠券名称',
  `description` varchar(256) NOT NULL DEFAULT '' COMMENT '优惠券详细信息',
  `type` varchar(10) NOT NULL DEFAULT '' COMMENT '优惠券类型，比如满减、随机立减、晚间双倍等等',
  `shop_id` bigint(20) COMMENT '优惠券适用的门店，如果是空则代表全场适用',
  `created_time` datetime NOT NULL DEFAULT '2021-12-13 00:00:00' COMMENT '创建时间',
  `rule` varchar(2000) NOT NULL DEFAULT '' COMMENT '详细的使用规则',
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='优惠券模板';

DROP TABLE if exists  `couponcenter_coupon_db`.`coupon` ;
CREATE TABLE IF NOT EXISTS `couponcenter_coupon_db`.`coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `template_id` int(20) NOT NULL DEFAULT '0' COMMENT '主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '拥有这张券的用户ID',
  `created_time` datetime NOT NULL DEFAULT '2021-12-13 00:00:00' COMMENT '领券时间',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '优惠券的状态，比如未用，已用',
  `shop_id` bigint(20) COMMENT '冗余字段，方便查找',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_template_id` (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='领到手的优惠券';

-- 插入 coupon_template 测试数据
INSERT INTO `couponcenter_coupon_db`.`coupon_template` (`available`, `name`, `description`, `type`, `shop_id`, `created_time`, `rule`) VALUES
(true, '满减优惠券', '满100减10元', '满减', NULL, '2023-01-01 10:00:00', '{"discount": {"quota": 10, "base": 100}}'),
(true, '折扣优惠券', '全场8折', '折扣', 1, '2023-01-02 11:00:00', '{"discount": {"quota": 20, "base": 100}}'),
(true, '随机立减券', '随机立减1-5元', '立减', 2, '2023-01-03 12:00:00', '{"discount": {"quota": 5, "base": 1}}');

-- 插入 coupon 测试数据
INSERT INTO `couponcenter_coupon_db`.`coupon` (`template_id`, `user_id`, `created_time`, `status`, `shop_id`) VALUES
(1, 10001, '2023-01-05 09:00:00', 0, NULL),
(2, 10002, '2023-01-06 10:00:00', 1, 1),
(3, 10003, '2023-01-07 11:00:00', 0, 2);