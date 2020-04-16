/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : lanjerry

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 02/03/2020 01:49:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `user_account` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户帐号',
  `ip_address` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `ajax_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否异步请求 0.否 1.是',
  `request_uri` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求uri',
  `request_params` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `request_method` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `class_method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作方法',
  `action_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动作名称',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 1.成功 2.失败',
  `execution_time` decimal(10, 2) NULL DEFAULT NULL COMMENT '执行时间（秒）',
  `exception_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常信息',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 358025 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `parent_id` int(11) NOT NULL COMMENT '父类id，当值等于0时，代表的是一级的菜单',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '类型 1.菜单 2.按钮',
  `path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `permission` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '图标',
  `sort` int(3) NOT NULL DEFAULT 99 COMMENT '排序',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 1.启用 2.停用',
  `frame_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否外链 0.否 1.是',
  `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0.否 1.是',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '系统管理', 0, 1, 'sys', NULL, 'sys', 'system', 20, 1, 0, 0, 1, '管理员', '2019-09-05 17:34:29');
INSERT INTO `sys_permission` VALUES (2, '用户管理', 1, 1, 'user', 'sys/user', 'sys:user', 'user', 10, 1, 0, 0, 1, '管理员', '2019-09-05 17:38:57');
INSERT INTO `sys_permission` VALUES (3, '分页查询系统用户列表', 2, 2, NULL, NULL, 'sys:user:page', '#', 10, 2, 0, 0, 1, '管理员', '2019-09-05 17:40:20');
INSERT INTO `sys_permission` VALUES (4, '新增系统用户', 2, 2, NULL, NULL, 'sys:user:save', '#', 20, 1, 0, 0, 1, '管理员', '2019-09-05 17:42:16');
INSERT INTO `sys_permission` VALUES (5, '更新系统用户', 2, 2, NULL, NULL, 'sys:user:update', '#', 30, 1, 0, 0, 1, '管理员', '2019-09-05 17:42:55');
INSERT INTO `sys_permission` VALUES (6, '删除系统用户', 2, 2, NULL, NULL, 'sys:user:remove', '#', 40, 1, 0, 0, 1, '管理员', '2019-09-05 17:43:52');
INSERT INTO `sys_permission` VALUES (7, '启用系统用户', 2, 2, NULL, NULL, 'sys:user:enable', '#', 50, 1, 0, 0, 1, '管理员', '2019-09-05 17:45:15');
INSERT INTO `sys_permission` VALUES (8, '停用系统用户', 2, 2, NULL, NULL, 'sys:user:disable', '#', 60, 1, 0, 0, 1, '管理员', '2019-09-05 17:45:48');
INSERT INTO `sys_permission` VALUES (9, '重置系统用户密码', 2, 2, NULL, NULL, 'sys:user:resetPassword', '#', 70, 1, 0, 0, 1, '管理员', '2019-09-05 17:46:31');
INSERT INTO `sys_permission` VALUES (10, '角色管理', 1, 1, 'role', 'sys/role', 'sys:role', 'peoples', 20, 1, 0, 0, 1, '管理员', '2019-09-05 17:48:11');
INSERT INTO `sys_permission` VALUES (11, '分页查询系统角色列表', 10, 2, NULL, NULL, 'sys:role:page', '#', 10, 1, 0, 0, 1, '管理员', '2019-09-05 17:49:29');
INSERT INTO `sys_permission` VALUES (12, '新增系统角色', 10, 2, NULL, NULL, 'sys:role:save', '#', 20, 1, 0, 0, 1, '管理员', '2019-09-05 17:49:29');
INSERT INTO `sys_permission` VALUES (13, '更新系统角色', 10, 2, NULL, NULL, 'sys:role:update', '#', 30, 1, 0, 0, 1, '管理员', '2019-09-05 17:49:29');
INSERT INTO `sys_permission` VALUES (14, '删除系统角色', 10, 2, NULL, NULL, 'sys:role:remove', '#', 40, 1, 0, 0, 1, '管理员', '2019-09-05 17:49:29');
INSERT INTO `sys_permission` VALUES (15, '权限管理', 1, 1, 'permission', 'sys/permission', 'sys:permission', 'tree-table', 30, 1, 0, 0, 1, '管理员', '2019-09-05 17:51:11');
INSERT INTO `sys_permission` VALUES (16, '查询系统权限列表', 15, 2, NULL, NULL, 'sys:permission:list', '#', 10, 1, 0, 0, 1, '管理员', '2019-09-05 17:51:41');
INSERT INTO `sys_permission` VALUES (17, '新增系统权限', 15, 2, NULL, NULL, 'sys:permission:save', '#', 20, 1, 0, 0, 1, '管理员', '2019-09-05 17:51:41');
INSERT INTO `sys_permission` VALUES (18, '更新系统权限', 15, 2, NULL, NULL, 'sys:permission:update', '#', 30, 1, 0, 0, 1, '管理员', '2019-09-05 17:51:41');
INSERT INTO `sys_permission` VALUES (19, '删除系统权限', 15, 2, NULL, NULL, 'sys:permission:remove', '#', 40, 1, 0, 0, 1, '管理员', '2019-09-05 17:51:41');
INSERT INTO `sys_permission` VALUES (20, '启用系统权限', 15, 2, NULL, NULL, 'sys:permission:enable', '#', 50, 1, 0, 0, 1, '管理员', '2019-09-05 17:51:41');
INSERT INTO `sys_permission` VALUES (21, '停用系统权限', 15, 2, NULL, NULL, 'sys:permission:disable', '#', 60, 1, 0, 0, 1, '管理员', '2019-09-05 17:51:41');
INSERT INTO `sys_permission` VALUES (22, '日志管理', 1, 1, 'log', 'sys/log', 'sys:log', 'log', 40, 1, 0, 0, 1, '管理员', '2019-09-05 18:01:30');
INSERT INTO `sys_permission` VALUES (23, '分页查询系统日志列表', 22, 2, NULL, NULL, 'sys:log:page', '#', 10, 1, 0, 0, 1, '管理员', '2019-09-05 18:01:42');
INSERT INTO `sys_permission` VALUES (24, '删除系统日志', 22, 2, NULL, NULL, 'sys:log:remove', '#', 20, 1, 0, 0, 1, '管理员', '2019-12-27 11:26:40');
INSERT INTO `sys_permission` VALUES (34, '系统工具', 0, 1, 'tool', '', 'tool', 'tool', 99, 1, 0, 0, 1, '管理员', '2020-02-11 16:18:11');
INSERT INTO `sys_permission` VALUES (35, '系统接口', 34, 1, 'swagger', 'tool/swagger', '', 'swagger', 99, 1, 0, 0, 1, '管理员', '2020-02-11 16:20:05');
INSERT INTO `sys_permission` VALUES (36, '代码生成', 34, 1, 'gen', 'tool/gen', '', 'code', 10, 1, 0, 0, 1, '管理员', '2020-02-11 16:54:24');
INSERT INTO `sys_permission` VALUES (37, '表单构建', 34, 1, 'build', 'tool/build', '', 'build', 99, 1, 0, 0, 1, '管理员', '2020-02-11 16:56:56');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `permission_tag` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限标识',
  `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0.否 1.是',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 223 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', 0, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色权限表' ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `account` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帐号',
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别 1.男 2.女 3.未知',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 1.启用 2.停用',
  `login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最后登陆ip',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0.否 1.是',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'aaa42296669b958c3cee6c0475c8093e', '管理员', 1, '123456@qq.com', '15017919916', '', 1, '127.0.0.1', '2020-03-02 01:48:54', 0, 1, '管理员', '2019-04-11 16:01:33');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (48, 1, 1, 1, '管理员', '2020-02-15 01:23:05');

-- ----------------------------
-- Table structure for tool_gen
-- ----------------------------
DROP TABLE IF EXISTS `tool_gen`;
CREATE TABLE `tool_gen`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表编号',
  `table_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表名称',
  `table_comment` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表描述',
  `class_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '实体类',
  `tpl_function` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板功能',
  `package_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '生成功能作者',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tool_gen_detail
-- ----------------------------
DROP TABLE IF EXISTS `tool_gen_detail`;
CREATE TABLE `tool_gen_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字段编号',
  `table_id` int(11) NOT NULL COMMENT '表编号',
  `column_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `pk_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否主键 0.否 1.是',
  `increment_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否自增 0.否 1.是',
  `required_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否必填 0.否 1.是',
  `only_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否唯一 0.否 1.是',
  `form_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否表单字段 0.否 1.是',
  `list_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否列表字段 0.否 1.是',
  `query_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否查询字段 0.否 1.是',
  `query_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询方式',
  `html_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询显示类型',
  `export_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否导出字段 0.否 1.是',
  `creator_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 541 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成业务明细表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
