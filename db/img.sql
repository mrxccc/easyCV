/*
 Navicat Premium Data Transfer

 Source Server         : 10.199.1.210
 Source Server Type    : MariaDB
 Source Server Version : 100322
 Source Host           : 10.199.1.210:3306
 Source Schema         : easycv

 Target Server Type    : MariaDB
 Target Server Version : 100322
 File Encoding         : 65001

 Date: 17/12/2020 15:20:32
*/
CREATE DATABASE IF NOT EXISTS easycv;
use easycv;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for img
-- ----------------------------
DROP TABLE IF EXISTS `img`;
CREATE TABLE `img`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `img_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片名称',
  `img_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片路径',
  `create_time` datetime(0) NOT NULL COMMENT '创建日期',
  `update_time` datetime(0) NOT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for img_record_task
-- ----------------------------
DROP TABLE IF EXISTS `img_record_task`;
CREATE TABLE `img_record_task`  (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `image_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片id',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `play_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '播放地址',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '任务结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
