-- MySQL dump 10.13  Distrib 5.7.19, for Win32 (AMD64)
--
-- Host: localhost    Database: oj-user
-- ------------------------------------------------------
-- Server version	5.7.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `oj-user`
--

/*!40000 DROP DATABASE IF EXISTS `oj-user`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `oj-user` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `oj-user`;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `content` varchar(1000) NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '评论创造时间',
  `update_time` datetime NOT NULL COMMENT '评论更新时间',
  `is_delete` bit(1) NOT NULL COMMENT '是否删除',
  `article_id` bigint(20) NOT NULL COMMENT ' 文章id',
  `comment_like_count` int(11) NOT NULL COMMENT '评论点赞个数',
  `root_comment_id` bigint(20) DEFAULT NULL COMMENT '顶级评论， 如果是null，代表该评论就是顶级评论',
  `to_comment_id` bigint(20) DEFAULT NULL COMMENT '回复评论id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `article_type` int(1) NOT NULL COMMENT '文章类别(0 表示 算法文章， 1表示题解文章，2 表示经验分享文章，3表示杂谈文章， 4表示竞赛文章，5表示算法板子文章)',
  PRIMARY KEY (`id`,`article_type`) USING BTREE,
  KEY `articleIndex` (`article_id`) USING BTREE COMMENT '文章id索引'
) ENGINE=InnoDB AUTO_INCREMENT=1753086995664166914 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (2,'here is comment ','2024-01-06 23:02:35','2024-01-06 23:02:42','\0',23,2,NULL,NULL,2,0),(3,'we like this','2024-01-06 23:04:03','2024-01-06 23:04:06','\0',23,5,NULL,NULL,3,0),(4,'bra af','2024-01-06 23:04:32','2024-01-06 23:04:35','\0',23,0,NULL,NULL,4,0),(5,'aef','2024-01-06 23:04:52','2024-01-06 23:04:55','\0',23,3,0,0,5,0),(6,'gragrg','2024-01-06 23:05:42','2024-01-06 23:05:45','\0',23,2,0,3,2,0),(7,'gragrgthyjy','2024-01-06 23:06:11','2024-01-06 23:06:13','\0',23,3,0,4,8,0),(8,'agrgrg','2024-01-06 23:06:37','2024-01-06 23:06:40','\0',23,0,0,4,2,0),(1745057043601633282,'2423affe','2024-01-10 20:16:35','2024-01-10 20:16:35','\0',23,0,NULL,NULL,8,0),(1745057418358484993,'2423affe','2024-01-10 20:18:05','2024-01-10 20:18:05','',23,0,0,4,8,0),(1752309078453583873,'# 标题\n这是一条测试程序','2024-01-30 20:33:35','2024-01-30 20:33:35','\0',2342342342342,0,NULL,NULL,1749071803607371777,1),(1752315050173558786,'# 你好世界\n```c\n#include<stdio.h>\nvoid solve() {\n    int a;\n    scanf(\"%d\", &a);\n    printf(\"%d\\n\", a + 2);\n}\nint main() {\n    int t ;\n    scanf(\"%d\", &t);\n    while(t--) solve();\n    return 0;\n}\n    \n```','2024-01-30 20:57:19','2024-01-30 20:57:19','\0',2342342342342,0,NULL,NULL,1749071803607371777,1),(1752315579821961217,'# 你好sij','2024-01-30 20:59:25','2024-01-30 20:59:25','\0',2342342342342,0,NULL,NULL,1749071803607371777,1),(1752541188581928962,'阿飞','2024-01-31 11:55:55','2024-01-31 11:55:55','\0',2342342342342,0,NULL,NULL,1749071803607371777,1),(1752674376490864642,'```\n@Eren_Yeagerssk\n# 标题\n这是一条测...\n```\n你好世界二\n','2024-01-31 20:45:09','2024-01-31 20:45:09','\0',2342342342342,0,1752309078453584000,1752309078453584000,1749071803607371777,1),(1752702768992935937,'```\n@Eren_Yeagerssk\n# 标题\n这是一条测...\n```\n你好使劲儿','2024-01-31 22:37:58','2024-01-31 22:37:58','\0',2342342342342,0,1752309078453584000,1752309078453584000,1749071803607371777,1),(1752704816480182274,'```\n@Eren_Yeagerssk\n# 标题\n这是一条测...\n```\nafef','2024-01-31 22:46:07','2024-01-31 22:46:07','\0',2342342342342,0,1752309078453584000,1752309078453584000,1749071803607371777,1),(1752708089266233345,'```\n@Eren_Yeagerssk\n```\n@Eren_...\n```\n','2024-01-31 22:59:07','2024-01-31 22:59:07','\0',2342342342342,0,1752309078453584000,1752702768992936000,1749071803607371777,1),(1752709089423187970,'```\n@Eren_Yeagerssk\n# 你好sij\n```\ntest2\n','2024-01-31 23:03:05','2024-01-31 23:03:05','\0',2342342342342,0,1752315579821961200,1752315579821961200,1749071803607371777,1),(1752711943819026433,'```\n@Eren_Yeagerssk\n# 标题\n这是一条测...\n```\nnihao1','2024-01-31 23:14:26','2024-01-31 23:14:26','\0',2342342342342,0,1752309078453584000,1752309078453584000,1749071803607371777,1),(1752712708998488065,'```\n@Eren_Yeagerssk\n# 你好sij\n```\n你好','2024-01-31 23:17:28','2024-01-31 23:17:28','\0',2342342342342,0,1752315579821961200,1752315579821961200,1749071803607371777,1),(1752716427467087873,'```\n@Eren_Yeagerssk\n# 标题\n这是一条测...\n```\nfae','2024-01-31 23:32:15','2024-01-31 23:32:15','\0',2342342342342,0,1752309078453584000,1752309078453584000,1749071803607371777,1),(1752724226142035969,'```\n@Eren_Yeagerssk\n# 标题\n这是一条测...\n```\n你好','2024-02-01 00:03:14','2024-02-01 00:03:14','\0',2342342342342,0,1752309078453584000,1752309078453584000,1749071803607371777,1),(1752724483034767362,'```\n@Eren_Yeagerssk\n# 你好世界\n```...\n```\n你好','2024-02-01 00:04:15','2024-02-01 00:04:15','\0',2342342342342,0,1752315050173558800,1752315050173558800,1749071803607371777,1),(1752724770432671745,'```\n@Eren_Yeagerssk\n# 你好世界\n```...\n```\nafaef','2024-02-01 00:05:24','2024-02-01 00:05:24','\0',2342342342342,0,1752315050173558800,1752315050173558800,1749071803607371777,1),(1752725343739502594,'```\n@Eren_Yeagerssk\n# 你好世界\n```...\n```\nfaef','2024-02-01 00:07:41','2024-02-01 00:07:41','\0',2342342342342,0,1752315050173558800,1752315050173558800,1749071803607371777,1),(1752726446120034306,'```\n@null\n```\n@Eren_...\n```\nafef','2024-02-01 00:12:04','2024-02-01 00:12:04','\0',2342342342342,0,1752315050173558800,NULL,1749071803607371777,1),(1752728091528716289,'```\n@Eren_Yeagerssk\n# 你好世界\n```...\n```\n你好世界','2024-02-01 00:18:36','2024-02-01 00:18:36','\0',2342342342342,0,1752315050173558800,1752315050173558800,1749071803607371777,1),(1752728157333151745,'```\n@undefined\n```\n@Eren_...\n```\n我就是世界','2024-02-01 00:18:51','2024-02-01 00:18:51','\0',2342342342342,0,1752315050173558800,NULL,1749071803607371777,1),(1752728794741530626,'```\n@Eren_Yeagerssk\n# 你好世界\n```...\n```\nfaef','2024-02-01 00:21:23','2024-02-01 00:21:23','\0',2342342342342,0,1752315050173558800,1752315050173558800,1749071803607371777,1),(1752728845404528642,'```\n@null\n```\n@Eren_...\n```\n你好世界','2024-02-01 00:21:36','2024-02-01 00:21:36','\0',2342342342342,0,1752315050173558800,NULL,1749071803607371777,1),(1752729479272914946,'```\n@null\n```\n@null\n...\n```\n测试程序哈','2024-02-01 00:24:07','2024-02-01 00:24:07','\0',2342342342342,0,1752315050173558800,1752728845404528600,1749071803607371777,1),(1752729721636577281,'```\n@null\n```\n@null\n...\n```\nfaefaefaefaef','2024-02-01 00:25:04','2024-02-01 00:25:04','\0',2342342342342,0,1752315050173558800,1752729479272915000,1749071803607371777,1),(1752729948766527489,'```\n@null\n```\n@null\n...\n```\nafefaef','2024-02-01 00:25:59','2024-02-01 00:25:59','\0',2342342342342,0,1752315050173558800,1752729721636577300,1749071803607371777,1),(1752729988532723713,'```\n@null\n```\n@null\n...\n```\nafefaefaefefefa','2024-02-01 00:26:08','2024-02-01 00:26:08','\0',2342342342342,0,1752315050173558800,1752729721636577300,1749071803607371777,1),(1752730036318429185,'```\n@null\n```\n@null\n...\n```\nafefaefaefefefa反反复复烦烦烦烦烦烦烦烦烦','2024-02-01 00:26:19','2024-02-01 00:26:19','\0',2342342342342,0,1752315050173558800,1752729721636577300,1749071803607371777,1),(1752730225879998466,'```\n@null\n```\n@null\n...\n```\n学啊学','2024-02-01 00:27:05','2024-02-01 00:27:05','\0',2342342342342,0,1752315050173558800,1752729721636577300,1749071803607371777,1),(1752903570709766146,'这个一个好的题解','2024-02-01 11:55:53','2024-02-01 11:55:53','\0',2342342342342,0,NULL,NULL,1749071803607371777,1),(1752961210903126018,'这是我的解决方法\n```c\n#include<stdio.h>\nvoid solve() {\n    int a;\n    scanf(\"%d\", &a);\n    \n    printf(\"%d\\n\", a + 2);\n}\nint main() {\n    int t ;\n    scanf(\"%d\", &t);\n    while(t--) solve();\n    return 0;\n}\n```','2024-02-01 15:44:56','2024-02-01 15:44:56','\0',2342342342342,0,NULL,NULL,1752959445814173697,1),(1752961370831937538,'留个评论','2024-02-01 15:45:34','2024-02-01 15:45:34','\0',2342342342443,0,NULL,NULL,1752959445814173697,1),(1753020367282855938,'写的很好','2024-02-01 19:40:00','2024-02-01 19:40:00','\0',1753019758114697218,0,NULL,NULL,1749071803607371777,1),(1753086995664166913,'```\n@Eren_Yeagerssk\n写的很好\n```\n你说的都对','2024-02-02 00:04:45','2024-02-02 00:04:45','\0',1753019758114697218,0,1753020367282856000,1753020367282856000,1749071803607371777,1);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '菜单名',
  `path` varchar(200) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT '0' COMMENT '是否删除（0未删除 1已删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'管理','dept','system/dept/index','0','0','manager','#',NULL,NULL,NULL,NULL,0,NULL),(2,'会员用户','test','system/test/index','0','0','vip','#',NULL,NULL,NULL,NULL,0,NULL),(3,'出题权限','make','system/make/index','0','0','pmk','#',NULL,NULL,NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `role_key` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
  `status` char(1) DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `del_flag` int(1) DEFAULT '0' COMMENT 'del_flag',
  `create_by` bigint(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(200) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'manager','ceo','0',0,NULL,NULL,NULL,NULL,NULL),(2,'vip','coder','0',0,NULL,NULL,NULL,NULL,NULL),(3,'problemmake','maker','0',0,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(200) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `menu_id` bigint(200) NOT NULL DEFAULT '0' COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1,1),(1,2),(1,3),(2,2),(3,2),(3,3);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` char(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(1024) DEFAULT NULL COMMENT '头像',
  `user_type` char(1) NOT NULL DEFAULT '1' COMMENT '用户类型（0管理员，1普通用户）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(11) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  `default_language` varchar(16) DEFAULT NULL COMMENT '默认选择语言',
  `thumb_num` int(11) DEFAULT NULL COMMENT '该用户被点赞次数',
  `favour_num` int(11) DEFAULT NULL COMMENT '该用户被收藏次数',
  `description` varchar(255) DEFAULT NULL COMMENT '用户个人简介（不得超过25字， 默认为该用户啥也没有写）',
  `following` int(11) DEFAULT NULL COMMENT '用户关注人数',
  `followers` int(11) DEFAULT NULL COMMENT '用户被多少人关注',
  `attend_cop_times` int(11) unsigned zerofill NOT NULL COMMENT '参加比赛次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1766681823324880898 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (2,'sg','三更','$2a$10$z8ZrqoQ38.N.flqHDjT7vuULL1XrKCiNX2pr0wkVa9.TpaJ3x..Py','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-09 19:19:16',NULL,'2024-01-10 12:30:27',0,NULL,0,1,'我是测试sg',0,0,00000000000),(3,'我是不白吃','fafeafaef','$2a$10$RbKlAiCCtimF3or6E9vo9O04kzSdthZHqBZXEPQcLzoawzxGl2rT2','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-09 19:19:16',NULL,'2023-12-09 19:19:16',0,NULL,0,0,NULL,0,0,00000000000),(4,'mikasa','afafaefaef','$2a$10$XE/dwCh4wB1hUy7WBR0oluwsPRVzYVxlZESMyMcQxzlPDRZZ7GZBu','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-09 19:19:16',NULL,'2023-12-09 19:19:16',0,NULL,0,0,NULL,0,0,00000000000),(5,'alanyaeer','我是艾伦','$2a$10$0k.7RvNWm7H4bIRZ4.vtyeipiVWCZAhwaNcU1hyGGyc6LyjfOA1y6','0','111@qq.com',NULL,'1','https://picsum.photos/60/60','1',NULL,'2023-12-09 19:19:16',NULL,'2023-12-09 19:19:16',0,NULL,0,0,NULL,0,0,00000000000),(6,'alansyaeer','我是艾伦他爹','$2a$10$CCvdxUw818hi4R82CWBWEO5QyNaJBrcmH9ANgKwZ/f2dpXam4WwZm','0','114514@qq.com',NULL,'1','https://picsum.photos/60/60','1',NULL,'2023-12-09 17:56:25',NULL,'2023-12-09 17:58:16',0,NULL,0,0,NULL,0,0,00000000000),(8,'alanyaeer123','我是测试三哥','$2a$10$cbQV2NY/my914iBEI9xc.e9ksIG1gciNUMoE04rihqsv4XTsb6uii','0',NULL,NULL,'0','http://118.25.143.28:9000/oj-question-file/Flat Design Illustrations Vector Hd Images, On The Night Of Ramadan Vector Illustration Flat Design, Arab, Background, Belief PNG Image For Free Download_1709906070365.jpg','1',NULL,'2023-12-17 17:38:11',NULL,'2024-03-08 21:54:55',0,NULL,4,3,'听我说谢谢你',0,0,00000000003),(9,'alanyaee23','NULL','$2a$10$qtW.VH1mzycPHrTlksewouX0WAigpdyNzD0fuC8gpulpZjfIQ/9BW','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-18 18:48:26',NULL,'2023-12-18 18:48:26',0,NULL,0,0,NULL,0,0,00000000000),(10,'alanyaeeff23','NULL','$2a$10$JB.ACX/NZVNC1QYshO5SD.eZuTy8UStXfGqKXHnZpfDydHCxfWC4i','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-18 18:48:39',NULL,'2023-12-18 18:48:39',0,NULL,0,0,NULL,0,0,00000000000),(11,'alanyaefaf','NULL','$2a$10$UNqtw5IKieYDTgpZGa4rDOzbCUwaEDpzF7BPObSUcsGcJkIm6LRke','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-18 18:51:34',NULL,'2023-12-18 18:51:34',1,NULL,0,0,NULL,0,0,00000000000),(12,'alanyfa','NULL','$2a$10$wZWi2ukCiV9XBCjNZzvct.RwDlRbnCFShQon3by8BQqZ4v50F6XLy','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-18 19:12:34',NULL,'2023-12-18 19:12:34',0,NULL,0,0,NULL,0,0,00000000000),(13,'alangg','NULL','$2a$10$2412F4zEpd3II11YY/xvHec2wWXPQgvk3fDD5m1vniwD1DXA2qfmK','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-18 19:12:37',NULL,'2023-12-18 19:12:37',0,NULL,0,0,NULL,0,0,00000000000),(14,'alaggga','NULL','$2a$10$R4yrxk2lnxyrp5sXG0JvSO5Adr3KBRdlS2//3e8SBHnldjsU0ohxC','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-18 19:12:39',NULL,'2023-12-18 19:12:39',0,NULL,0,0,NULL,0,0,00000000000),(15,'alaggggggg','NULL','$2a$10$PoxN.BIVxf/YROPcWaafCeyMu4QENbjPIiGUpGAMBVWaQI8TGP4o2','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-18 19:12:42',NULL,'2023-12-18 19:12:42',0,NULL,0,0,NULL,0,0,00000000000),(16,'alaggagggg','NULL','$2a$10$pwvusD1I8I9BdpwLQoAoluk/iS8e6aFPCmtOxcb5SHv3haK2/inh.','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-18 19:12:44',NULL,'2023-12-18 19:12:44',0,NULL,0,0,NULL,0,0,00000000000),(17,'alaggagbg','NULL','$2a$10$4SiK254JWE96fTdpe3O1/.yBLxSQKgEmQft4XBxL7.n49AwoHErmu','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2023-12-18 19:12:46',NULL,'2023-12-18 19:12:46',0,NULL,0,0,NULL,0,0,00000000000),(333,'testtesttest','测试','{noop}114514114514','0',NULL,NULL,NULL,NULL,'1',NULL,'2024-03-06 21:15:55',NULL,'2024-03-06 21:15:57',0,NULL,0,0,NULL,0,0,00000000000),(1749071803607371777,'alanyaeertest','Eren_Yeagerssk','$2a$10$EkfftOuicfg7SVY8bV5ob.7XlUVeJYmMXv707m.qmNzShjj5y0p/S','0',NULL,NULL,'1','http://118.25.143.28:9000/oj-question-file/OIP_1706413214693.jpg','1',NULL,'2024-01-21 22:09:49',NULL,'2024-01-29 13:49:47',0,NULL,0,0,'意大利面要拌42号混凝土啊',0,0,00000000000),(1751526270822961154,'alanyeeeer','welearnAny','$2a$10$2PwhhSjbXugBDM7wo5wwoerqnI.ihYMVC2K6fPBI5awcxh13zJQLS','0',NULL,NULL,'0','https://picsum.photos/60/60','1',NULL,'2024-01-28 16:42:59',NULL,'2024-01-28 16:43:23',0,NULL,0,0,'我是第二个测试者',0,0,00000000000),(1752959445814173697,'alanyaeettest','TestRobot','$2a$10$cDTnKgZEF6CKMdZQDotsZuGizzZGoQRKPxMhKtTl2ywM4VY4YNxL.','0',NULL,NULL,'0','http://118.25.143.28:9000/oj-question-file/Flat Design Illustrations Vector Hd Images, On The Night Of Ramadan Vector Illustration Flat Design, Arab, Background, Belief PNG Image For Free Download_1706773398785.jpg','1',NULL,'2024-02-01 15:37:55',NULL,'2024-02-01 15:43:23',0,NULL,0,0,'这是一个测试账号',0,0,00000000000),(1753246509457694722,'alanyaeettt','测试第三个','$2a$10$qWSYbLqpQYxmEC6zOK3YS.zJ4IPLzLkojBeYvCLrq60ZbfxrQZJ5W','0',NULL,NULL,'0','http://118.25.143.28:9000/oj-question-file/Flat Design Illustrations Vector Hd Images, On The Night Of Ramadan Vector Illustration Flat Design, Arab, Background, Belief PNG Image For Free Download_1706841583495.jpg','1',NULL,'2024-02-02 10:38:36',NULL,'2024-02-02 10:41:21',0,NULL,0,0,'我是第三个测试人员',0,0,00000000000),(1753254705681358849,'alanyaeerttt','Genshinafs','$2a$10$02CHhpre2c7JjjkS/1j7JuTJ3fhZd/KxwGjKXymgHKcpYhxtdxE.O','0',NULL,NULL,'0','http://118.25.143.28:9000/oj-question-file/咖波手機桌布圖_1706843480329.jpg','1',NULL,'2024-02-02 11:11:10',NULL,'2024-02-02 11:11:38',0,NULL,0,0,'你好呀刷题者',0,0,00000000000),(1765359119137185794,'alanyaeers','测试','$2a$10$TsnPJR.RZuVGk6R/t2kpnukXeiEEIg4By74vSxLG60Z6EChNXVGS2','0',NULL,NULL,'0','https://picsum.photos/60/60','1',NULL,'2024-03-06 20:49:47',NULL,'2024-03-06 20:50:07',0,NULL,0,0,'我爱学算法',0,0,00000000000),(1766681823324880897,'blanblantest','Genshin','$2a$10$JPS.I9yxKS2nZkDCTGCNE.x1.TtBThsRVe3EfZ29Uc9SaC8Ccwv5y','0',NULL,NULL,NULL,'https://picsum.photos/60/60','1',NULL,'2024-03-10 12:25:45',NULL,'2024-03-10 12:25:45',0,NULL,0,0,'',0,0,00000000000);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(200) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `role_id` bigint(200) NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1766681823324880898 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,1),(4,2),(5,3),(6,2),(7,2),(8,1),(9,2),(10,2),(11,2),(12,2),(13,2),(14,2),(15,2),(16,2),(17,2),(333,1),(1749071803607371777,2),(1751526270822961154,2),(1752959445814173697,2),(1753246509457694722,2),(1753254705681358849,2),(1765359119137185794,2),(1766681286097440769,1),(1766681582584401922,1),(1766681823324880897,1);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thumb_and_favour`
--

DROP TABLE IF EXISTS `thumb_and_favour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thumb_and_favour` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `source_id` bigint(20) NOT NULL COMMENT '来源id',
  `type` int(11) NOT NULL COMMENT '来源类型(0 表示文章  1 表示 题目）',
  `is_thumb` bit(1) NOT NULL COMMENT '是否点赞',
  `is_favour` bit(1) NOT NULL COMMENT '是否收藏',
  PRIMARY KEY (`user_id`,`source_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thumb_and_favour`
--

LOCK TABLES `thumb_and_favour` WRITE;
/*!40000 ALTER TABLE `thumb_and_favour` DISABLE KEYS */;
INSERT INTO `thumb_and_favour` VALUES (2,1743600401257275393,1,'','\0'),(2,1743601251803406337,1,'',''),(2,1743828341450539009,0,'\0','\0'),(8,1743600401257275393,1,'',''),(8,1743828341450239009,0,'\0',''),(1749071803607371777,1743601251803406337,1,'','');
/*!40000 ALTER TABLE `thumb_and_favour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_connection`
--

DROP TABLE IF EXISTS `user_connection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_connection` (
  `id` bigint(20) NOT NULL COMMENT '用户id1',
  `fid` bigint(20) NOT NULL COMMENT '用户id2',
  `is_delete` bit(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`,`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_connection`
--

LOCK TABLES `user_connection` WRITE;
/*!40000 ALTER TABLE `user_connection` DISABLE KEYS */;
INSERT INTO `user_connection` VALUES (1,2,'\0'),(1,8,''),(2,1,'\0'),(2,8,'\0'),(5,8,'\0'),(8,2,''),(8,3,'\0'),(8,5,'\0'),(8,8,''),(1749071803607371777,2,'\0'),(1749071803607371777,3,'\0'),(1749071803607371777,8,'\0'),(1749071803607371777,1749071803607371777,'\0'),(1749071803607371777,1751526270822961154,'\0'),(1752959445814173697,1749071803607371777,'\0');
/*!40000 ALTER TABLE `user_connection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_daily`
--

DROP TABLE IF EXISTS `user_daily`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_daily` (
  `id` bigint(20) NOT NULL COMMENT '用户id',
  `create_time` date NOT NULL COMMENT '用户提交时间',
  `times` int(11) NOT NULL COMMENT '用户当天提交次数',
  `update_time` date DEFAULT NULL COMMENT '用户更新时间',
  KEY `datetime` (`create_time`) COMMENT '用户创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_daily`
--

LOCK TABLES `user_daily` WRITE;
/*!40000 ALTER TABLE `user_daily` DISABLE KEYS */;
INSERT INTO `user_daily` VALUES (1749071803607371777,'2024-01-27',8,'2024-01-27'),(1749071803607371777,'2024-01-26',2,'2024-01-26'),(1749071803607371777,'2024-01-28',1,'2024-01-28'),(1751526270822961154,'2024-01-28',1,'2024-01-28'),(1749071803607371777,'2024-01-30',10,'2024-01-30'),(1749071803607371777,'2024-01-31',15,'2024-01-31'),(1752959445814173697,'2024-02-01',1,'2024-02-01'),(1749071803607371777,'2024-02-01',11,'2024-02-01'),(1753246509457694722,'2024-02-02',7,'2024-02-02'),(1753254705681358849,'2024-02-02',4,'2024-02-02'),(1765359119137185794,'2024-03-06',6,'2024-03-06'),(8,'2024-03-09',10,'2024-03-09'),(1765359119137185794,'2024-03-09',33,'2024-03-09'),(1765359119137185794,'2024-03-10',66,'2024-03-10');
/*!40000 ALTER TABLE `user_daily` ENABLE KEYS */;
UNLOCK TABLES;
