/*
 Navicat Premium Data Transfer

 Source Server         : wjh
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : oj-article

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 04/02/2024 12:23:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint(20) NOT NULL COMMENT '文章的id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',
  `title_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题名称',
  `tags` json NULL COMMENT '标签列表',
  `thumb_num` int(11) NULL DEFAULT NULL COMMENT '点赞个数',
  `favour_num` int(11) NULL DEFAULT NULL COMMENT '收藏个数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(11) NULL DEFAULT NULL COMMENT '是否删除',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文章内容',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文章描述（预览), 长度必须大于 等于 10 个字小于100个字',
  `article_type` int(11) NULL DEFAULT NULL COMMENT '文章类别(0 表示 算法文章， 1表示题解文章，2 表示经验分享文章，3表示杂谈文章， 4表示竞赛文章，5表示算法板子文章)',
  `article_reads` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '文章的阅读个数',
  `article_id` bigint(20) NOT NULL COMMENT '文章来源id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL COMMENT '种类主键',
  `category` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
