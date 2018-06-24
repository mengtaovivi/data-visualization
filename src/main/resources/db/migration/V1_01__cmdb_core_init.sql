/*
Navicat MySQL Data Transfer

Source Server         : 10.130.210.116
Source Server Version : 50720
Source Host           : 10.130.210.116:3306
Source Database       : mntdev

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-05-29 10:34:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tk_alarm_coverage
-- ----------------------------
DROP TABLE IF EXISTS `tk_alarm_coverage`;
CREATE TABLE `tk_alarm_coverage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operation_id` varchar(32) DEFAULT NULL COMMENT '操作外键',
  `operation_os` varchar(120) DEFAULT NULL COMMENT '操作系统',
  `all_operation_os` varchar(120) DEFAULT NULL COMMENT '所有操作系统数量',
  `used_operation_os` varchar(120) DEFAULT NULL COMMENT '已用数量',
  `zone` varchar(64) DEFAULT '' COMMENT '区域',
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `tk_alarm_coverage_id` (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=519 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tk_cloud_equipment
-- ----------------------------
DROP TABLE IF EXISTS `tk_cloud_equipment`;
CREATE TABLE `tk_cloud_equipment` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `department` varchar(64) COLLATE utf8_bin DEFAULT '',
  `in_val` varchar(32) COLLATE utf8_bin DEFAULT '',
  `out_val` varchar(32) COLLATE utf8_bin DEFAULT '',
  `replace_val` varchar(32) COLLATE utf8_bin DEFAULT '',
  `create_time` varchar(32) COLLATE utf8_bin DEFAULT '',
  `update_time` varchar(32) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tk_computer_room
-- ----------------------------
DROP TABLE IF EXISTS `tk_computer_room`;
CREATE TABLE `tk_computer_room` (
  `id` varchar(120) NOT NULL DEFAULT '' COMMENT 'id',
  `frame_used` varchar(120) DEFAULT NULL COMMENT '机架已用容量',
  `frame_all` varchar(120) DEFAULT NULL COMMENT '机架总容量',
  `computer_room` varchar(120) DEFAULT NULL COMMENT '机房PUE',
  `power_used` varchar(120) DEFAULT NULL COMMENT '电力已用容量',
  `power_all` varchar(120) DEFAULT NULL COMMENT '电力总容量',
  `computer_room_name` varchar(120) DEFAULT NULL COMMENT '机房名称',
  `create_time` varchar(120) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(120) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tk_computer_room_air_temperature
-- ----------------------------
DROP TABLE IF EXISTS `tk_computer_room_air_temperature`;
CREATE TABLE `tk_computer_room_air_temperature` (
  `id` varchar(120) NOT NULL DEFAULT '',
  `air_suply` varchar(120) DEFAULT NULL COMMENT '风送温度',
  `return_air` varchar(120) DEFAULT NULL COMMENT '回风温度',
  `create_time` varchar(120) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(120) DEFAULT NULL COMMENT '更新时间',
  `computer_room_name` varchar(120) DEFAULT NULL COMMENT '房机信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tk_computer_room_temperature
-- ----------------------------
DROP TABLE IF EXISTS `tk_computer_room_temperature`;
CREATE TABLE `tk_computer_room_temperature` (
  `id` varchar(80) COLLATE utf8_bin NOT NULL,
  `types` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `cold` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '冷制机组',
  `precise` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '密精空调',
  `create_time` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `computer_room_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '机房名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tk_coverage_percent
-- ----------------------------
DROP TABLE IF EXISTS `tk_coverage_percent`;
CREATE TABLE `tk_coverage_percent` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `compare_type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '比较的集合来源类型，比如从cmdb来的就是cmdb',
  `compare_count` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '比较的集合数据总数量',
  `base_type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '被比较的集合来源类型，比如从zabbix来的就是zabbix',
  `base_count` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '被比较的集合数据总数量',
  `intersection_count` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '交集的数量',
  `percent` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '交集的数量/被比较的集合数据总数量',
  `create_date` datetime DEFAULT NULL COMMENT '数据计算时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tk_dict
-- ----------------------------
DROP TABLE IF EXISTS `tk_dict`;
CREATE TABLE `tk_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dict_code` varchar(100) DEFAULT NULL,
  `dict_value` varchar(1000) DEFAULT NULL,
  `dict_name` varchar(150) DEFAULT NULL,
  `enable_flag` varchar(10) DEFAULT 'Enabled' COMMENT '删除标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tk_electricity_count
-- ----------------------------
DROP TABLE IF EXISTS `tk_electricity_count`;
CREATE TABLE `tk_electricity_count` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '年度',
  `month` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '月份',
  `type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `value` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '电力值',
  `created_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `computer_room_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '机房名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tk_host
-- ----------------------------
DROP TABLE IF EXISTS `tk_host`;
CREATE TABLE `tk_host` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'uuid',
  `hostname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '主机名称',
  `display_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '显示名字',
  `ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'ip',
  `status` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '状态',
  `device_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '设备ID',
  `mem_size` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '内存大小（KB）',
  `disk_size` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '磁盘大小（KB）',
  `cpus` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'CPU总数',
  `os_release` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '操作系统内核发行版本',
  `os_system` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '操作系统类型',
  `id_in_cloud` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '云平台 id',
  `cloud_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '云平台类型',
  `zone_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'zone名称',
  `object_version` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '对象版本',
  `creator` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `ctime` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '本条数据插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tk_ip_address
-- ----------------------------
DROP TABLE IF EXISTS `tk_ip_address`;
CREATE TABLE `tk_ip_address` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'uuid',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `qingcloud_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '青云ID',
  `address` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'IP地址',
  `type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'EIP; 互联网IP; 带外管理IP; 私有IP; 内网IP',
  `id_in_cloud` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '云平台 id',
  `cloud_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '云平台类型',
  `instance_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '主机id',
  `object_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '对象id',
  `object_version` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '对象版本',
  `creator` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `ctime` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '本条数据插入时间',
  `os_system` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '操作系统类型',
  `status` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tk_ip_itemscount
-- ----------------------------
DROP TABLE IF EXISTS `tk_ip_itemscount`;
CREATE TABLE `tk_ip_itemscount` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'interface中，获取的IP值',
  `items_count` bigint(20) NOT NULL COMMENT 'ip对应指标数数量',
  `hostid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'ip对应的Hostid',
  `create_date` datetime DEFAULT NULL COMMENT '数据计算时间',
  `enable_flag` varchar(10) COLLATE utf8_bin DEFAULT 'Enabled' COMMENT '删除标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134682 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tk_operation_information
-- ----------------------------
DROP TABLE IF EXISTS `tk_operation_information`;
CREATE TABLE `tk_operation_information` (
  `id` varchar(32) NOT NULL,
  `operation_ip` varchar(120) DEFAULT NULL COMMENT '操作ip',
  `operation_type` varchar(64) DEFAULT NULL,
  `operation_date` varchar(120) DEFAULT NULL COMMENT '操作日期',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tk_rdb
-- ----------------------------
DROP TABLE IF EXISTS `tk_rdb`;
CREATE TABLE `tk_rdb` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `rdb_id` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `rdb_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `status` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `master_ip` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `rvip` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `wvip` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `cloud_type` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `rdb_engine` varchar(32) COLLATE utf8_bin DEFAULT '',
  `zone_name` varchar(32) COLLATE utf8_bin DEFAULT '',
  `refresh_time` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '采集时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tk_screen_navigation
-- ----------------------------
DROP TABLE IF EXISTS `tk_screen_navigation`;
CREATE TABLE `tk_screen_navigation` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `url` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '图片地址',
  `image` longtext COLLATE utf8_bin COMMENT '图片信息',
  `operator` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '操作者',
  `operate_time` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tk_temperature_alarm
-- ----------------------------
DROP TABLE IF EXISTS `tk_temperature_alarm`;
CREATE TABLE `tk_temperature_alarm` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `temperature_val` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '温度',
  `humidity` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '湿度',
  `alarm_all` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '告警总数',
  `alarm_alert` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '警报',
  `alarm_normal` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '正常',
  `alarm_early_warning` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '预警',
  `computer_room_name` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '机房信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for tk_weather
-- ----------------------------
DROP TABLE IF EXISTS `tk_weather`;
CREATE TABLE `tk_weather` (
  `id` varchar(120) COLLATE utf8_bin NOT NULL,
  `city` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `weather_json` text COLLATE utf8_bin COMMENT '气天的json数据',
  `create_time` varchar(120) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
