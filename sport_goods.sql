-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: sport_goods
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES gbk */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `sport_goods`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `sport_goods` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `sport_goods`;

--
-- Table structure for table `carousel`
--

DROP TABLE IF EXISTS `carousel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carousel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gid` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carousel`
--

LOCK TABLES `carousel` WRITE;
/*!40000 ALTER TABLE `carousel` DISABLE KEYS */;
INSERT INTO `carousel` VALUES (14,'2024-03-11/B2B97318C8FE4BA5AAD19770392DDD7B.jpg',51),(15,'2024-03-11/5318EDF56E2944B8B1F2C2FB241EA662.jpg',59);
/*!40000 ALTER TABLE `carousel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gid` int DEFAULT NULL,
  `star` int DEFAULT NULL,
  `content` text COLLATE utf8mb4_unicode_ci,
  `image_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `reply_id` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `account` (`account`),
  KEY `gid` (`gid`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`account`) REFERENCES `user` (`account`),
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`gid`) REFERENCES `goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (3,'normal',57,3,'很好啊',';;2024-03-13/9CB664E33ED34FE4ACEBA029499BEFEF.jpg;2024-03-13/E5B3E1CC06FE4B5DB184B6379A4FD26D.jpg','2024-03-13 03:49:35',0),(4,'normal',57,3,'很好啊',';;2024-03-13/9CB664E33ED34FE4ACEBA029499BEFEF.jpg;2024-03-13/E5B3E1CC06FE4B5DB184B6379A4FD26D.jpg;2024-03-13/8F45E1A1D63E453E91A7BFD693A41AD8.jpg;2024-03-13/D66B46453F8B498CA63C862726289C2E.jpg','2024-03-13 03:49:39',12),(6,'normal',57,1,'测试',';;2024-03-14/11FE5E6E4FB94526AA912CA74F4D65DC.jpeg','2024-03-14 00:41:54',0),(10,'liuying',57,0,'非常抱歉',';','2024-03-14 16:19:39',-1),(11,'liuying',57,0,'非常抱歉',';','2024-03-14 16:31:04',-1),(12,'liuying',57,0,'感谢亲的认可?',';','2024-03-14 16:42:47',-1);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` decimal(10,2) DEFAULT NULL,
  `image_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `merchant` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `available` int DEFAULT NULL,
  `label` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `introduction` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `good_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `image_list` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `merchant` (`merchant`),
  CONSTRAINT `goods_ibfk_1` FOREIGN KEY (`merchant`) REFERENCES `user` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (50,6.60,'2024-03-09/638505BB6E89423F99CB0E7F0A5777C4.jpg','merchant',1,'健身器材 哑铃','蓝色 2kg','小哑铃',498,'2024-03-09/638505BB6E89423F99CB0E7F0A5777C4.jpg'),(51,6.60,'2024-03-09/4C1932C8236543FDA9A8A428DDFC5925.jpg','merchant',1,'健身器材 跳神','黑色','跳绳',490,'2024-03-09/4C1932C8236543FDA9A8A428DDFC5925.jpg'),(52,100.00,'2024-03-09/09CB8CB33C8042D2BE7D1095FE41390C.jpg','merchant',1,'健身器材 跑步机','运动健身','跑步机',500,'2024-03-09/09CB8CB33C8042D2BE7D1095FE41390C.jpg'),(53,6.60,'2024-03-09/896F97F3DA894B1A81CAC0B5000C5A13.jpg','merchant',1,'健身器材','2kg','磅砣',500,'2024-03-09/896F97F3DA894B1A81CAC0B5000C5A13.jpg'),(54,100.00,'2024-03-09/1BB6EB7C52C6424AA0AD572825041E16.jpg','merchant',1,'健身器材 哑铃','拉伸','哑铃',499,'2024-03-09/1BB6EB7C52C6424AA0AD572825041E16.jpg'),(55,100.00,'2024-03-09/36DEEA2FE4EF45E393849A973D87625C.jpg','merchant',1,'健身器材 大哑铃','双层','大哑铃',500,'2024-03-09/36DEEA2FE4EF45E393849A973D87625C.jpg'),(56,100.00,'2024-03-09/E88031AABEC6458A86E1C60A0031B79A.jpg','merchant',1,'健身器材 大哑铃','双层','大哑铃',500,'2024-03-09/E88031AABEC6458A86E1C60A0031B79A.jpg'),(57,100.00,'2024-03-09/3BB793B380BA442FB5B37A5FADDD5103.jpg','merchant',1,'健身器材 大哑铃','双层','大哑铃',499,'2024-03-09/3BB793B380BA442FB5B37A5FADDD5103.jpg'),(58,100.00,'2024-03-09/35F80A1B948D4ACBB4C37C4E67586313.jpg','merchant',1,'健身器材 大哑铃','双层','大哑铃',499,'2024-03-09/35F80A1B948D4ACBB4C37C4E67586313.jpg'),(59,100.00,'2024-03-09/D6A42134AED8447C89B8317C4F9A09C1.jpg','merchant',1,'健身器材 大哑铃','双层','大哑铃',500,'2024-03-09/D6A42134AED8447C89B8317C4F9A09C1.jpg'),(60,6.60,'2024-03-13/9A9E4514E38441C8A5C035BFDF4EC577.jpg','merchant',1,'拉伸 健身','拉伸','拉伸棒',100,'2024-03-13/9A9E4514E38441C8A5C035BFDF4EC577.jpg'),(64,1.00,'2024-03-16/85F091128F504EF991CA29CF77B6CAED.jpg','merchant',1,'测试','测试','测试啊',1,'2024-03-16/85F091128F504EF991CA29CF77B6CAED.jpg'),(65,3.00,'2024-03-16/22303D4C17294309A6B4BCDCA30C59D3.jpg','merchant',1,'测试','测试','测试',3,'2024-03-16/22303D4C17294309A6B4BCDCA30C59D3.jpg'),(66,4.00,'2024-03-16/B5CE2863FCEC4339B92ADD0FFEC0DB85.jpg','merchant',1,'测试','测试','测试',4,'2024-03-16/B5CE2863FCEC4339B92ADD0FFEC0DB85.jpg'),(67,5.00,'2024-03-16/124929F771DE420BA3CD895F8B0EFD95.jpeg','merchant',1,'测试','测试','测试',5,'2024-03-16/124929F771DE420BA3CD895F8B0EFD95.jpeg;2024-03-16/1A0361DFDA6443F0AAD40F172C90D6A0.jpg');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history` (
  `account` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gid` int NOT NULL,
  `view_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `star` int DEFAULT '0',
  `available` int DEFAULT '1',
  PRIMARY KEY (`account`,`gid`),
  KEY `gid` (`gid`),
  CONSTRAINT `history_ibfk_1` FOREIGN KEY (`account`) REFERENCES `user` (`account`),
  CONSTRAINT `history_ibfk_2` FOREIGN KEY (`gid`) REFERENCES `goods` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES ('liuying',54,'2024-03-14 16:50:34',0,1),('liuying',55,'2024-03-14 16:30:52',0,1),('liuying',56,'2024-03-14 16:30:53',0,1),('liuying',58,'2024-03-14 16:30:53',0,1),('merchant',50,'2024-03-14 22:58:38',0,1),('merchant',53,'2024-03-15 20:49:57',0,1),('merchant',64,'2024-03-15 20:58:18',0,1),('merchant',67,'2024-03-15 21:02:25',0,1),('normal',51,'2024-03-14 16:17:57',1,1),('normal',52,'2024-03-14 16:17:57',0,1),('normal',57,'2024-03-14 17:37:09',0,1),('normal',59,'2024-03-13 03:59:19',0,1),('normal',60,'2024-03-14 00:41:31',0,1);
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` varchar(255) DEFAULT NULL,
  `merchant` varchar(255) DEFAULT NULL,
  `customerService` varchar(255) DEFAULT NULL,
  `have_read_user` tinyint DEFAULT '0',
  `have_read_customerService` tinyint DEFAULT '0',
  `image_path` varchar(255) DEFAULT NULL,
  `send_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (9,'测试','这是一条测试数据','2024-03-11 04:54:08','2024-03-11 08:00:00'),(10,'测试','这是一条测试数据','2024-03-13 00:12:50','2024-03-13 08:00:00');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `percode` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `href` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'创建客服权限','merchant:createCustomerService','','','permission'),(2,'客服管理菜单','merchant:客服管理:客服管理:1','','people','menu'),(3,'客服概览','merchant:客服管理:客服概览:2','/sys/customerServiceManagement','person','menu'),(4,'添加客服','merchant:客服管理:添加客服:2','/sys/createCustomerService','person-add','menu'),(5,'删除客服权限','merchant:controlCustomerService','','','permission'),(6,'商品管理菜单','merchant:商品管理:商品管理:1','','box2','menu'),(7,'商品概览','merchant:商品管理:商品概览:2','/sys/goodsManagement','bar-chart','menu'),(8,'商品概览','merchant:商品管理:添加商品:2','/sys/addGoods','plus','menu'),(9,'添加商品权限','merchant:addGoods','','','permission'),(10,'更新商品权限','merchant:updateGoods','','','permission'),(11,'同意退货权限','customerService:agreeRefound','','','permission'),(12,'订单管理菜单','user:订单管理:订单管理:1','','clipboard','menu'),(13,'订单概览','user:订单管理:订单概览:2','/sys/orderManagement','clipboard-data','menu'),(14,'同意退订权限','customerService:agreeRefound','','','permisssion'),(15,'退订管理菜单','customerService:退订管理:退订管理:1','','clipboard','menu'),(16,'退订概览','customerService:退订管理:退订概览:2','/sys/refoundManagement','clipboard-data','menu'),(17,'管理员权限','*:*','','','permission'),(18,'首页管理菜单','*:首页管理:首页管理:1','','house','menu'),(19,'首页管理菜单','*:首页管理:轮播管理:2','/sys/carousel','image','menu'),(20,'首页管理菜单','*:首页管理:公告管理:2','/sys/notice','megaphone','menu'),(21,'审核功能','*:审核功能:审核功能:1','','toggles','menu'),(22,'审核功能','*:审核功能:人员审核:2','/sys/userCheck','person-check','menu'),(23,'审核功能','*:审核功能:商品审核:2','/sys/goodCheck','patch-check','menu'),(24,'删除评论权限','user:deleteComment','','','permission'),(25,'评价管理','user:评价管理:评价管理:1','','chat-left-text','menu'),(26,'评价管理','user:评价管理:删除评价:2','/sys/deleteComments','x','menu');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `available` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'普通用户','普通用户,拥有查看,购买,评论等权限',1),(2,'商家','商家,拥有发布,修改,创建客服账号等权限',1),(3,'客服','仅仅可以由商家在系统创建',0),(4,'管理员','拥有高级权限',0);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `rid` int NOT NULL,
  `pid` int NOT NULL,
  PRIMARY KEY (`rid`,`pid`),
  KEY `pid` (`pid`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `role` (`id`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),(3,11),(1,12),(1,13),(3,14),(3,15),(3,16),(4,17),(4,18),(4,19),(4,20),(4,21),(4,22),(4,23),(1,24),(3,24),(1,25),(1,26);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `account` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nickname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sex` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `merchant` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` int DEFAULT NULL,
  `avatarpath` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `salt` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `available` int DEFAULT NULL,
  `gold` decimal(10,2) DEFAULT '999.00',
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('dereliction',NULL,'7e63e52450ef7a549e2f732c277c3060',NULL,NULL,NULL,2,NULL,'a4e2253194f8783911cf5f8fe740ad16',1,999.00),('liuying','流萤','e51e56a1c8fa2a25a2a42367871dfee5',NULL,NULL,'merchant',3,'2024-03-08/265C747B405F4D0285FCFE1E55E8F338.jpg','43c2be3cab3eddf207c5406e6658ce87',1,1051.80),('merchant','商家','c76d253ae84c39dc233b21bdd3976a4a','',NULL,NULL,2,'2024-03-09/EBFB984496404730A7EBCFF2CD10F6E6.jpg','4f42e857882255b26e94c985dc4b111e',1,999.00),('merchant1',NULL,'1cd262c1a3c3550c1e7a022f6113f673',NULL,NULL,NULL,2,NULL,'e69ee2c8ebe4768bafb8299e5c52419e',1,999.00),('normal','用户','3026532e73d1027ce79b7fd643916aec','无',NULL,NULL,1,'2024-03-05/5BBAFB1C185542948D675DA85DAC5733.jpg','28d3b02fe37d12f6a4396a4f33412d6c',1,422.10),('system','管理员','e51e56a1c8fa2a25a2a42367871dfee5','',NULL,NULL,4,'2024-03-09/7B2B835EFCA04A07B91C51DF0E4E5325.jpg','43c2be3cab3eddf207c5406e6658ce87',1,999.00);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_goods`
--

DROP TABLE IF EXISTS `user_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gid` int DEFAULT NULL,
  `num` int DEFAULT NULL,
  `finishTime` timestamp NULL DEFAULT NULL,
  `status` int DEFAULT NULL COMMENT '0鏈畬鎴?涔熷氨鏄湪璐墿杞﹂噷. 1瀹屾垚浜ゆ槗, -1閫€璐х敵璇? 2閫€璐ф垚鍔?-2閫€璐уけ璐?,
  `cost` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `account` (`account`),
  KEY `gid` (`gid`),
  CONSTRAINT `user_goods_ibfk_1` FOREIGN KEY (`account`) REFERENCES `user` (`account`),
  CONSTRAINT `user_goods_ibfk_2` FOREIGN KEY (`gid`) REFERENCES `goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


--
-- Dumping data for table `user_goods`
--

LOCK TABLES `user_goods` WRITE;
/*!40000 ALTER TABLE `user_goods` DISABLE KEYS */;
INSERT INTO `user_goods` VALUES (55,'normal',55,2,'2024-03-12 00:50:34',0,NULL),(56,'normal',59,2,'2024-03-12 00:50:57',0,NULL),(57,'normal',57,2,'2024-03-12 00:50:58',0,NULL),(58,'normal',50,1,'2024-03-12 00:50:54',0,NULL),(59,'normal',58,1,'2024-03-12 00:50:55',0,NULL),(60,'normal',50,1,'2024-03-12 00:51:16',1,6.60),(61,'normal',51,1,'2024-03-13 00:11:53',2,6.60),(62,'normal',51,1,'2024-03-13 00:10:44',1,6.60),(63,'normal',57,1,'2024-03-14 16:19:17',-2,100.00);
/*!40000 ALTER TABLE `user_goods` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-23 22:14:35
