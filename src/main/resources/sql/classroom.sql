/*
 Navicat Premium Data Transfer

 Source Server         : database
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : classroom

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 03/06/2021 09:30:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
BEGIN;
INSERT INTO `t_admin` VALUES (1, 'admin', 'admin');
INSERT INTO `t_admin` VALUES (2, 'root', 'root');
COMMIT;

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `teachername` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `coursename` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `room` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `starttime` datetime NOT NULL,
  `endtime` datetime NOT NULL,
  `announcement` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_course
-- ----------------------------
BEGIN;
INSERT INTO `t_course` VALUES (4, 'jason', '高等数学', 'qddx12', '2021-05-29 08:00:00', '2021-05-29 10:00:00', '加油');
INSERT INTO `t_course` VALUES (5, 'jason', '高等数学', 'qddx12', '2021-05-29 11:00:00', '2021-05-29 12:00:00', '加油');
INSERT INTO `t_course` VALUES (6, 'jason', '离散数学', 'qddx6', '2021-05-31 09:00:00', '2021-05-31 21:39:00', '请准时上课');
COMMIT;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `num` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `school` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `room` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tablenum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `starttime` datetime NOT NULL,
  `endtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_order
-- ----------------------------
BEGIN;
INSERT INTO `t_order` VALUES (1, '131231', 'jason', '青岛大学', 'qdu01', 't1', '2021-05-25 05:11:20', '2021-05-25 08:11:41');
INSERT INTO `t_order` VALUES (2, '1216', 'admin', '青岛大学', 'qddx12', '1排2列', '2021-05-30 08:00:00', '2021-05-30 10:00:00');
COMMIT;

-- ----------------------------
-- Table structure for t_room
-- ----------------------------
DROP TABLE IF EXISTS `t_room`;
CREATE TABLE `t_room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `num` varchar(50) NOT NULL,
  `capacity` int NOT NULL,
  `status` tinyint NOT NULL,
  `school` varchar(255) NOT NULL,
  `starttime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_room
-- ----------------------------
BEGIN;
INSERT INTO `t_room` VALUES (1, 'qdu01', 30, 2, '青岛大学', '2021-05-04 14:52:48', '2021-05-04 15:52:48');
INSERT INTO `t_room` VALUES (3, 'hydx4', 46, 1, '海洋大学', NULL, NULL);
INSERT INTO `t_room` VALUES (4, 'qddx4', 12, 1, '青岛大学', NULL, NULL);
INSERT INTO `t_room` VALUES (5, 'qddx5', 16, 1, '青岛大学', NULL, NULL);
INSERT INTO `t_room` VALUES (6, 'qddx6', 55, 1, '青岛大学', '2021-05-31 09:00:00', '2021-05-31 21:39:00');
INSERT INTO `t_room` VALUES (7, 'hydx7', 80, 1, '海洋大学', NULL, NULL);
INSERT INTO `t_room` VALUES (9, 'qddx8', 40, 1, '青岛大学', NULL, NULL);
INSERT INTO `t_room` VALUES (11, 'qdlgdx10', 31, 1, '青岛理工大学', NULL, NULL);
INSERT INTO `t_room` VALUES (12, 'qddx12', 34, 1, '青岛大学', '2021-05-29 11:00:00', '2021-05-29 12:00:00');
INSERT INTO `t_room` VALUES (13, 'qddx13', 1, 1, '青岛大学', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sign
-- ----------------------------
DROP TABLE IF EXISTS `t_sign`;
CREATE TABLE `t_sign` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `signcount` int DEFAULT NULL,
  `signed` smallint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_sign
-- ----------------------------
BEGIN;
INSERT INTO `t_sign` VALUES (5, 'jason', 2, 1);
INSERT INTO `t_sign` VALUES (6, 'admin', 1, 1);
INSERT INTO `t_sign` VALUES (7, '王君帅', 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_table
-- ----------------------------
DROP TABLE IF EXISTS `t_table`;
CREATE TABLE `t_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room` varchar(255) NOT NULL,
  `x` int NOT NULL,
  `y` int NOT NULL,
  `status` tinyint NOT NULL,
  `starttime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_table
-- ----------------------------
BEGIN;
INSERT INTO `t_table` VALUES (1, 'qdu01', 1, 1, 2, '2021-05-04 14:52:48', '2021-05-04 15:52:48');
INSERT INTO `t_table` VALUES (2, 'qdu01', 1, 2, 1, '2021-05-04 13:02:52', '2021-05-04 13:03:59');
COMMIT;

-- ----------------------------
-- Table structure for t_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  `school` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
BEGIN;
INSERT INTO `t_teacher` VALUES (1, 'jason', '123', '青岛大学', '18766657057', 's947517134@163.com');
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` int NOT NULL,
  `creat_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (4, 'admin', '21232f297a57a5a743894a0e4a801fc3', NULL, NULL, '青岛大学', 1, NULL);
INSERT INTO `t_user` VALUES (5, '123', '81dc9bdb52d04dc20036dbd8313ed055', '', '', '青岛大学', 1, NULL);
INSERT INTO `t_user` VALUES (7, 'jason', 'd9b1d7db4cd6e70935368a1efb10e377', '', '', '青岛大学', 1, NULL);
INSERT INTO `t_user` VALUES (15, '王君帅', '202cb962ac59075b964b07152d234b70', '18766657057', 's947517134@163.com', '', 1, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
