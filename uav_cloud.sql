/*
 Navicat Premium Data Transfer

 Source Server         : 天翼云
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 183.56.219.211:33066
 Source Schema         : uav_cloud

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 11/08/2020 23:58:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for airspace
-- ----------------------------
DROP TABLE IF EXISTS `airspace`;
CREATE TABLE `airspace`  (
  `id` bigint(0) NOT NULL,
  `airspace_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '空域名称',
  `status` int(0) NOT NULL COMMENT '空域审批状态',
  `airspace_point` linestring NOT NULL COMMENT '具体空域范围坐标',
  `start_time` datetime(0) NOT NULL COMMENT '空域开始使用时间',
  `end_time` datetime(0) NOT NULL COMMENT '空域结束使用时间',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` int(0) NULL DEFAULT 1 COMMENT '逻辑删除 0:删除 1:未删除',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '空域信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for altitudes
-- ----------------------------
DROP TABLE IF EXISTS `altitudes`;
CREATE TABLE `altitudes`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `altitude` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for plan_data
-- ----------------------------
DROP TABLE IF EXISTS `plan_data`;
CREATE TABLE `plan_data`  (
  `id` bigint(0) NOT NULL,
  `uav_id` bigint(0) NOT NULL COMMENT '对应飞行器的id',
  `speed` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一组飞行速度',
  `altitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '一组海拔高度',
  `start` datetime(0) NOT NULL COMMENT '飞行开始记录时间',
  `end` datetime(0) NOT NULL COMMENT '飞行结束记录时间',
  `distance` double NOT NULL COMMENT '飞行总距离',
  `coordinate` linestring NOT NULL COMMENT '一组坐标详情',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '记录飞行器的飞行数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT '创建者Id',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单位名称',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` int(0) NULL DEFAULT 1 COMMENT '状态 0：禁用 1：正常',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT '创建者Id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `role_id` int(0) NOT NULL COMMENT '关联的角色id',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证号或社会信用代码',
  `address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '家庭住址或公司地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` bigint(0) NOT NULL,
  `task_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '飞行计划名字',
  `plan_start_time` datetime(0) NOT NULL COMMENT '计划飞行开始时间',
  `plan_end_time` datetime(0) NOT NULL COMMENT '计划飞行结束时间',
  `height` int(0) NOT NULL COMMENT '离地高度',
  `within_line_of_sight` int(0) NOT NULL COMMENT '是否在视距内飞行 0：不在 1：在',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '飞机计划说明',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `uav_id` bigint(0) NOT NULL COMMENT '对应执行计划的无人机',
  `task_nature_id` int(0) NOT NULL COMMENT '对应计划性质id',
  `status` int(0) NULL DEFAULT NULL COMMENT '飞行计划的批准情况 0:禁止 1:准飞',
  `deleted` int(0) NULL DEFAULT 1 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '飞行计划' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_airspace
-- ----------------------------
DROP TABLE IF EXISTS `task_airspace`;
CREATE TABLE `task_airspace`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(0) NULL DEFAULT NULL COMMENT '计划id',
  `airspace_id` bigint(0) NULL DEFAULT NULL COMMENT '空域id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '飞行计划与空域关系列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_natrue
-- ----------------------------
DROP TABLE IF EXISTS `task_natrue`;
CREATE TABLE `task_natrue`  (
  `task_natrue_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '飞机计划性质id',
  `task_natrue_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '飞行计划性质名称',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '具体描述',
  PRIMARY KEY (`task_natrue_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '飞行计划' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for uav
-- ----------------------------
DROP TABLE IF EXISTS `uav`;
CREATE TABLE `uav`  (
  `id` bigint(0) NOT NULL COMMENT '无人机编号',
  `nickname` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '无人机称呼，方便运营商辨认',
  `Manufacturer_name` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '无人机厂商',
  `uav_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '无人机型号',
  `weight` double NOT NULL COMMENT '空重',
  `speed_max` double NOT NULL COMMENT '最大飞行速度',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` int(0) NULL DEFAULT 1 COMMENT '逻辑删除 0:删除 1:没删',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '无人机设备信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_airspace
-- ----------------------------
DROP TABLE IF EXISTS `user_airspace`;
CREATE TABLE `user_airspace`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `airspace_id` bigint(0) NOT NULL COMMENT '空域id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户(单位)与空域关系列表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_uav
-- ----------------------------
DROP TABLE IF EXISTS `user_uav`;
CREATE TABLE `user_uav`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL,
  `uav_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与无人机关系' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
