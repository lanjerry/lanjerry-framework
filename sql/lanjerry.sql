/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : 127.0.0.1:3306
 Source Schema         : lanjerry

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 25/09/2019 15:22:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `ip_address` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `ajax_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否异步请求 0.否 1.是',
  `request_uri` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求uri',
  `request_params` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `http_method` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'http方法',
  `class_method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作方法',
  `action_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动作名称',
  `execution_time` decimal(10, 2) NULL DEFAULT NULL COMMENT '执行时间',
  `exception_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常信息',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 357729 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `parent_id` int(11) NOT NULL COMMENT '父类id，当值等于0时，代表的是一级的菜单',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '类型 1.菜单 2.按钮',
  `view_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视图地址',
  `permission` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `sort` int(3) NOT NULL DEFAULT 99 COMMENT '排序',
  `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0.否 1.是',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '首页', 0, 1, 'index', NULL, 'icon-home', 10, 0, 1, '管理员', '2019-09-05 17:32:50');
INSERT INTO `sys_permission` VALUES (2, '系统管理', 0, 1, 'layout', 'sys', 'icon-sys', 20, 0, 1, '管理员', '2019-09-05 17:34:29');
INSERT INTO `sys_permission` VALUES (3, '用户管理', 2, 1, 'sys/user', 'sys:user', 'icon-sys-user', 10, 0, 1, '管理员', '2019-09-05 17:38:57');
INSERT INTO `sys_permission` VALUES (4, '分页', 3, 2, NULL, 'sys:user:page', 'icon-search', 10, 0, 1, '管理员', '2019-09-05 17:40:20');
INSERT INTO `sys_permission` VALUES (5, '新增', 3, 2, NULL, 'sys:user:save', 'icon-save', 20, 0, 1, '管理员', '2019-09-05 17:42:16');
INSERT INTO `sys_permission` VALUES (6, '修改', 3, 2, NULL, 'sys:user:update', 'icon-update', 30, 0, 1, '管理员', '2019-09-05 17:42:55');
INSERT INTO `sys_permission` VALUES (7, '删除', 3, 2, NULL, 'sys:user:remove', 'icon-remove', 40, 0, 1, '管理员', '2019-09-05 17:43:52');
INSERT INTO `sys_permission` VALUES (8, '锁定', 3, 2, NULL, 'sys:user:lock', 'icon-lock', 50, 0, 1, '管理员', '2019-09-05 17:45:15');
INSERT INTO `sys_permission` VALUES (9, '解锁', 3, 2, NULL, 'sys:user:unlock', 'icon-unlock', 60, 0, 1, '管理员', '2019-09-05 17:45:48');
INSERT INTO `sys_permission` VALUES (10, '重置密码', 3, 2, NULL, 'sys:user:resetPassword', 'icon-reset', 70, 0, 1, '管理员', '2019-09-05 17:46:31');
INSERT INTO `sys_permission` VALUES (11, '角色管理', 2, 1, 'sys/role', 'sys:role', 'icon-sys-role', 20, 0, 1, '管理员', '2019-09-05 17:48:11');
INSERT INTO `sys_permission` VALUES (12, '分页', 11, 2, NULL, 'sys:role:page', 'icon-search', 10, 0, 1, '管理员', '2019-09-05 17:49:29');
INSERT INTO `sys_permission` VALUES (13, '新增', 11, 2, NULL, 'sys:role:save', 'icon-save', 20, 0, 1, '管理员', '2019-09-05 17:49:29');
INSERT INTO `sys_permission` VALUES (14, '修改', 11, 2, NULL, 'sys:role:update', 'icon-update', 30, 0, 1, '管理员', '2019-09-05 17:49:29');
INSERT INTO `sys_permission` VALUES (15, '删除', 11, 2, NULL, 'sys:role:remove', 'icon-remove', 40, 0, 1, '管理员', '2019-09-05 17:49:29');
INSERT INTO `sys_permission` VALUES (16, '权限管理', 2, 1, 'sys/permission', 'sys:permission', 'icon-sys-permission', 30, 0, 1, '管理员', '2019-09-05 17:51:11');
INSERT INTO `sys_permission` VALUES (17, '列表', 16, 2, NULL, 'sys:permission:list', 'icon-search', 10, 0, 1, '管理员', '2019-09-05 17:51:41');
INSERT INTO `sys_permission` VALUES (18, '新增', 16, 2, NULL, 'sys:permission:save', 'icon-save', 20, 0, 1, '管理员', '2019-09-05 17:51:41');
INSERT INTO `sys_permission` VALUES (19, '修改', 16, 2, NULL, 'sys:permission:update', 'icon-update', 30, 0, 1, '管理员', '2019-09-05 17:51:41');
INSERT INTO `sys_permission` VALUES (20, '删除', 16, 2, NULL, 'sys:permission:remove', 'icon-remove', 40, 0, 1, '管理员', '2019-09-05 17:51:41');
INSERT INTO `sys_permission` VALUES (21, '日志管理', 2, 1, 'sys/log', 'sys:log', 'icon-sys-log', 40, 0, 1, '管理员', '2019-09-05 18:01:30');
INSERT INTO `sys_permission` VALUES (22, '分页', 21, 2, NULL, 'sys:log:page', 'icon-search', 10, 0, 1, '管理员', '2019-09-05 18:01:42');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0.否 1.是',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 0, 1, '管理员', '2019-09-25 15:21:03');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1, 1, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (2, 1, 2, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (3, 1, 3, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (4, 1, 4, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (5, 1, 5, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (6, 1, 6, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (7, 1, 7, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (8, 1, 8, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (9, 1, 9, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (10, 1, 10, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (11, 1, 11, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (12, 1, 12, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (13, 1, 13, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (14, 1, 14, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (15, 1, 15, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (16, 1, 16, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (17, 1, 17, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (18, 1, 18, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (19, 1, 19, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (20, 1, 20, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (21, 1, 21, 1, '管理员', '2019-09-25 15:21:21');
INSERT INTO `sys_role_permission` VALUES (22, 1, 22, 1, '管理员', '2019-09-25 15:22:36');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统用户id',
  `account` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帐号',
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 1.正常 2.禁用',
  `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0.否 1.是',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'aaa42296669b958c3cee6c0475c8093e', '管理员', 1, 0, 1, '管理员', '2019-04-11 16:01:33');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, 1, '管理员', '2019-04-16 19:22:39');

SET FOREIGN_KEY_CHECKS = 1;
