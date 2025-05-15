-- MySQL dump 10.13  Distrib 5.7.19, for Win32 (AMD64)
--
-- Host: localhost    Database: oj-article
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
-- Current Database: `oj-article`
--

/*!40000 DROP DATABASE IF EXISTS `oj-article`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `oj-article` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `oj-article`;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL COMMENT '文章的id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  `title_name` varchar(16) DEFAULT NULL COMMENT '标题名称',
  `tags` json DEFAULT NULL COMMENT '标签列表',
  `thumb_num` int(11) DEFAULT NULL COMMENT '点赞个数',
  `favour_num` int(11) DEFAULT NULL COMMENT '收藏个数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(11) DEFAULT NULL COMMENT '是否删除',
  `content` text COMMENT '文章内容',
  `description` text COMMENT '文章描述（预览), 长度必须大于 等于 10 个字小于100个字',
  `article_type` int(11) DEFAULT NULL COMMENT '文章类别(0 表示 算法文章， 1表示题解文章，2 表示经验分享文章，3表示杂谈文章， 4表示竞赛文章，5表示算法板子文章)',
  `article_reads` int(11) unsigned DEFAULT NULL COMMENT '文章的阅读个数',
  `article_id` bigint(20) NOT NULL COMMENT '文章来源id',
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (2342342342342,2,'测试题解','[\"动态规划\", \"贪心\", \"并查集\"]',0,0,'2024-01-29 19:21:12','2024-01-29 19:21:12',0,'这是一个测试的用例呀','废弃字段',1,0,1743601251803406337),(2342342342443,3,'测试题解','[\"动态规划\", \"贪心\", \"并查集\"]',0,0,'2024-01-29 19:21:12','2024-01-29 19:21:12',0,'这是一个测试的用例呀','这是一个测试的用例呀',1,0,1743601251803406337),(1753019758114697218,1749071803607371777,'暴力操作','[\"模拟\"]',0,0,'2024-02-01 19:37:35','2024-02-01 19:37:35',0,'```cpp\n#include<iostream>\n#include<string>\n#include<vector>\n#include<algorithm>\nusing namespace std;\n\nvoid solve() {\n    string s;\n    cin>>s;\n     string ans = \"\";\n        int lsize = 1;\n        int start = 0;\n        int n = s.length();\n        vector<vector<bool> >tmp (s.length(),vector<bool>(s.length(),false));\n        for(int i = 0; i < n;++i){\n            tmp[i][i] = true;\n            if(i>0&&s[i-1]==s[i]){\n                tmp[i-1][i] = true;\n                lsize = 2;\n                start = i-1;\n            }\n        }\n        for(int len = 3; len <= s.length();++len){\n            for(int i = 0; i < n;++i){\n                int j = i+len-1;\n                if(j>=n)break;\n                if(tmp[i+1][j-1]&&s[i]==s[j]){\n                     tmp[i][j] = true;\n                     lsize = len;\n                     start = i;\n                }\n            }\n        }\n       cout<<s.substr(start, lsize);\n}\n\nint main() {\n    int t = 1;\n    // cin>>t;\n    while(t--){\n        solve();\n    }\n    return 0;\n}\n```',NULL,1,0,1752967907570847745);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL COMMENT '种类主键',
  `category` varchar(16) NOT NULL COMMENT '类别名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (0,'算法'),(1,'题解'),(2,'经验'),(3,'杂谈'),(4,'竞赛'),(5,'算法板子');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;
