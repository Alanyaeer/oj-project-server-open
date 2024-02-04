/*
 Navicat Premium Data Transfer

 Source Server         : wjh
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : oj-question

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 04/02/2024 12:23:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alg_tem_list
-- ----------------------------
DROP TABLE IF EXISTS `alg_tem_list`;
CREATE TABLE `alg_tem_list`  (
  `id` bigint(20) NOT NULL COMMENT '算法模板名称id',
  `algTemName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '算法模板名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for algo
-- ----------------------------
DROP TABLE IF EXISTS `algo`;
CREATE TABLE `algo`  (
  `id` bigint(20) NOT NULL COMMENT '算法模板id',
  `create_by` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '算法模板更新时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '算法模板创建时间',
  `alg_location` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '算法模板存入地址',
  `alg_tem` bigint(20) NULL DEFAULT NULL COMMENT '算法名称关联id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `alg_tem`(`alg_tem`) USING BTREE,
  CONSTRAINT `algo_ibfk_1` FOREIGN KEY (`alg_tem`) REFERENCES `alg_tem_list` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` bigint(20) NOT NULL COMMENT '题目id',
  `title_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目名称',
  `create_by` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '题目更新时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '题目创建时间',
  `question_location` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '题目文件创建地址',
  `question_type` int(11) NULL DEFAULT NULL COMMENT '(-1 表示比赛状态题目,0表示正常状态， 1表示请求删除，2表示已被删除，3表示请求审核题目 4表示用户保存状态 )',
  `likes` int(11) NULL DEFAULT NULL COMMENT '点赞个数',
  `pass_person` int(11) NULL DEFAULT NULL COMMENT '通过人数',
  `score` int(4) NULL DEFAULT NULL COMMENT '题目难度',
  `file_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `title_id` int(6) NOT NULL COMMENT '题目编号',
  `language` json NULL COMMENT '使用语言',
  `judge_case` json NULL COMMENT '判断题目的测试用例',
  `favour_num` int(11) NULL DEFAULT NULL COMMENT '收藏个数',
  `submit_num` int(11) NULL DEFAULT NULL COMMENT '提交人数',
  `ans` json NULL COMMENT '题目答案',
  `tags` json NULL COMMENT '标签列表',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '题目内容',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建者的userid',
  `judge_config` json NULL COMMENT '题目的限制（耗时限制， 内存限制， 堆栈限制）',
  `try_person` int(11) NULL DEFAULT NULL COMMENT '尝试的人数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `add_for_name`(`title_name`) USING BTREE COMMENT '提供方法名字索引',
  INDEX `user_id_index`(`create_user_id`) USING BTREE COMMENT '提供用户id检索查询',
  INDEX `query_for_next`(`title_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for submit_records
-- ----------------------------
DROP TABLE IF EXISTS `submit_records`;
CREATE TABLE `submit_records`  (
  `id` bigint(20) NOT NULL COMMENT '题目提交编号',
  `pid` bigint(20) NOT NULL COMMENT '题目提交用户名',
  `status` int(11) NOT NULL COMMENT '题目提交通过所有案例（0表示待判题， 1表示判题中， 2成功， 3失败）',
  `update_time` datetime(0) NOT NULL COMMENT '题目提交更新时间',
  `create_time` datetime(0) NOT NULL COMMENT '题目提交创建时间',
  `last_case` int(11) NULL DEFAULT NULL COMMENT '题目最后一个错误的案例数',
  `last_reason` bigint(20) NULL DEFAULT NULL COMMENT '题目最后一个错误的原因',
  `judge_info` json NOT NULL COMMENT '判题信息(JSON对象）',
  `question_id` bigint(20) NULL DEFAULT NULL COMMENT '题目id',
  `language` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语言类型',
  `code` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '语言代码',
  `error_message` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '代码编译器错误',
  `annotation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提交记录注释',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `last_reason`(`last_reason`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tag_list
-- ----------------------------
DROP TABLE IF EXISTS `tag_list`;
CREATE TABLE `tag_list`  (
  `id` bigint(20) NOT NULL COMMENT '标签id',
  `tag_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` bigint(20) NOT NULL COMMENT '关联id',
  `id_tag` bigint(20) NULL DEFAULT NULL COMMENT '关联的标签id',
  `type` int(11) NULL DEFAULT NULL COMMENT '关联类型 0 代表关联题目， 1 代表关联算法模板',
  `source_id` bigint(20) NULL DEFAULT NULL COMMENT '关联来源的id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_tag`(`id_tag`) USING BTREE,
  INDEX `source_id`(`source_id`) USING BTREE,
  CONSTRAINT `tags_ibfk_1` FOREIGN KEY (`id_tag`) REFERENCES `tag_list` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for testcase
-- ----------------------------
DROP TABLE IF EXISTS `testcase`;
CREATE TABLE `testcase`  (
  `id` bigint(20) NULL DEFAULT NULL COMMENT '题目测试用例编号',
  `update_time` datetime(0) NOT NULL COMMENT '题目测试用例更新时间',
  `create_time` datetime(0) NOT NULL COMMENT '题目测试机用例创建时间',
  `type` int(11) NOT NULL COMMENT '题目测试用例类型（0代表展示用例， 1代表测试用例）',
  `input_text` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目测试用例输入',
  `qid` bigint(20) NOT NULL COMMENT '题目对应的id',
  `output_text` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目测试用例输出'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for testcase_reason
-- ----------------------------
DROP TABLE IF EXISTS `testcase_reason`;
CREATE TABLE `testcase_reason`  (
  `id` bigint(20) NOT NULL COMMENT '题目提交编号',
  `reason` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目错误原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_question
-- ----------------------------
DROP TABLE IF EXISTS `user_question`;
CREATE TABLE `user_question`  (
  `id` bigint(20) NOT NULL COMMENT '用户id',
  `solve_easy` int(11) NULL DEFAULT NULL COMMENT '解决简单问题个数',
  `solve_middle` int(11) NULL DEFAULT NULL COMMENT '解决中等问题个数',
  `solve_hard` int(11) NULL DEFAULT NULL COMMENT '解决难题问题个数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_solve
-- ----------------------------
DROP TABLE IF EXISTS `user_solve`;
CREATE TABLE `user_solve`  (
  `id` bigint(20) NOT NULL COMMENT '用户id',
  `update_time` datetime(0) NOT NULL COMMENT '题目解决时间',
  `create_time` datetime(0) NOT NULL COMMENT '题目解决创建时间',
  `titleId` bigint(20) NOT NULL COMMENT '题目id',
  PRIMARY KEY (`id`, `titleId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
