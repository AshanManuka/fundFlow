-- MySQL dump 10.13  Distrib 8.0.41, for Linux (x86_64)
--
-- Host: localhost    Database: fund_flow_db
-- ------------------------------------------------------
-- Server version	8.0.41-0ubuntu0.24.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `credit_score` double NOT NULL,
  `email` varchar(255) NOT NULL,
  `income` double NOT NULL,
  `name` varchar(255) NOT NULL,
  `nic` varchar(255) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdwk6cx0afu8bs9o4t536v1j5v` (`email`),
  UNIQUE KEY `UK9st6x9trhf0s27g0vgpcaeu3m` (`nic`),
  UNIQUE KEY `UKj7ja2xvrxudhvssosd4nu1o92` (`user_id`),
  CONSTRAINT `FKj8dlm21j202cadsbfkoem0s58` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'2025-05-08 23:12:01.628000',750,'ashen@mail.com',160000,'Ashen Manuka','200010600600','2025-05-10 10:30:27.542000',3),(2,'2025-05-10 09:11:07.825000',750,'avishka@mail.com',140000,'avishka senadeera','200010933890','2025-05-10 11:00:58.661000',4),(3,'2025-05-10 09:11:47.303000',700,'adamz@mail.com',0,'adamz hashni','20001093384','2025-05-10 19:57:29.441000',5),(4,'2025-05-10 09:12:26.463000',350,'gayan@mail.com',0,'gayan jayasekara','20001055384','2025-05-10 19:57:09.465000',6),(5,'2025-05-10 09:13:20.205000',650,'kusal@mail.com',0,'kusal mendis','20401055384','2025-05-10 19:56:01.010000',7),(6,'2025-05-10 10:47:13.988000',600,'pathum@mail.com',0,'pathum konara','20401665384','2025-05-10 19:56:46.997000',8),(7,'2025-05-10 13:38:35.358000',750,'jayani@mail.com',180000,'jayani bandara','20001654888','2025-05-10 13:38:35.358000',9),(9,'2025-05-10 21:35:00.933000',700,'samod@mail.com',200000,'samod','1999564566','2025-05-10 21:35:00.933000',11);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `installment` int NOT NULL,
  `loan_amount` double NOT NULL,
  `reason` varchar(255) NOT NULL,
  `requested_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcwv05agfqwg5ndy6adbefsx7d` (`customer_id`),
  CONSTRAINT `FKcwv05agfqwg5ndy6adbefsx7d` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES (1,10,50000,'personal loan','2025-05-10 13:42:04.661000','ON GOING','2025-05-10 18:32:16.013000',6),(2,10,200000,'personal loan','2025-05-10 13:42:45.797000','ELIGIBLE','2025-05-10 13:42:45.797000',6),(3,10,500000,'business loan','2025-05-10 13:59:50.963000','ELIGIBLE','2025-05-10 13:59:50.963000',7),(4,50,600000,'vehicle loan','2025-05-10 14:00:58.648000','ON GOING','2025-05-10 20:28:43.910000',7),(5,50,600000,'vehicle loan','2025-05-10 14:03:42.062000','NOT ELIGIBLE','2025-05-10 14:03:42.062000',4),(6,50,60000,'vehicle loan','2025-05-10 14:03:51.294000','ON GOING','2025-05-10 20:28:47.761000',4),(7,10,50000,'personal loan','2025-05-10 14:06:40.084000','NOT ELIGIBLE','2025-05-10 14:06:40.084000',4),(8,25,50000,'personal loan','2025-05-10 14:06:49.921000','NOT ELIGIBLE','2025-05-10 14:06:49.921000',4),(9,25,10000,'personal loan','2025-05-10 14:07:47.748000','NOT ELIGIBLE','2025-05-10 14:07:47.748000',4),(10,25,10000,'personal loan','2025-05-10 14:09:06.406000','ELIGIBLE','2025-05-10 14:09:06.406000',4),(11,25,5000,'easy loan','2025-05-10 14:09:23.506000','NOT ELIGIBLE','2025-05-10 14:09:23.506000',4),(12,15,150000,'sample purpose','2025-05-10 21:48:09.480000','NOT ELIGIBLE','2025-05-10 21:48:09.480000',6),(13,20,50000,'sample one','2025-05-10 21:49:54.973000','NOT ELIGIBLE','2025-05-10 21:49:54.973000',6);
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'$2a$10$Je4suG6fnIAd419U3pV7tOSjLugwyHtI8y3ZRwk0s0v/5e//AoP/y','ADMIN','adminOne'),(2,'$2a$12$EzyuEf.2wlFPJgVj6iI6Ee1rDe0luxfmHOM8TMuzVO0XLtJ05ZOFa','ADMIN','adminTwo'),(3,'$2a$10$xif2Xs2H.b79ZbFUDkIZg.tON.x5OfAu4zRqVjBpeeULbpznq.2ou','CUSTOMER','ashan@mail.com'),(4,'$2a$10$UAyg0rkyb.YTXvQsNEZElOVxDan4wpwsNIA/TozMPoAzWulrg0To2','CUSTOMER','avishka@mail.com'),(5,'$2a$10$1lsY7RZXX1/qQIYLU/OW3elIqh.Ku.fKuv56ZIDO8gL323tk2iuS.','CUSTOMER','adamz@mail.com'),(6,'$2a$10$c6PPZMxgKWW2yt4rDKRNCO4ScMLi48WSrSYLbqniCetzM0MfvZZW.','CUSTOMER','gayan@mail.com'),(7,'$2a$10$b0BO7fM3IRsgDEDPX2MTAOX4Qf6DpYnL8csIjCMyXfR0nlncpgHuq','CUSTOMER','kusal@mail.com'),(8,'$2a$10$mddOb9EYNy29Ei8krSnNcu9VezGn0CVyr4UcApaKbgl4t/vbvupTe','CUSTOMER','pathum@mail.com'),(9,'$2a$10$FhHYyW.h2cnX2.OMwpuNQedFp8IbrZ1.s/S6h.kHU4/.SHEOS916.','CUSTOMER','jayani@mail.com'),(10,'$2a$10$2iSIx196eVfyLl7vMPq5mug/s0A4cwGb5FKrS4SJmfEeYPdSEF1AG','CUSTOMER','disara@mail.com'),(11,'$2a$10$fl54EliZTNwqSYaEgARUp.hl6GAVyo76mm4LeEF2JWXF57fOsCueC','CUSTOMER','samod@mail.com'),(12,'$2a$10$1ICClfEYLcmTWizerPlsfub3.pDpgQKxT8aGfCeBGtQUHbnV3E22W','CUSTOMER','raam@gmai.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-10 23:20:07
