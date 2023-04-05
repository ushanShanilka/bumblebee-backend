-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bumblebeedb
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'+94','2023-02-16 14:41:30.000000','ushan@gmail.com','ushan','shanilka','0766773245','2023-02-16 14:41:50.000000',1,1,NULL),(2,'+94','2023-02-16 15:44:45.657000','shanilka@gmail.com','shanilka','ushan','119','2023-02-16 15:44:45.657000',2,1,NULL),(3,'+94','2023-02-16 18:18:01.037000','shanilka1@gmail.com','shanilka','ushan','119','2023-02-16 18:18:01.037000',2,1,NULL);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `admin_login_credential`
--

LOCK TABLES `admin_login_credential` WRITE;
/*!40000 ALTER TABLE `admin_login_credential` DISABLE KEYS */;
INSERT INTO `admin_login_credential` VALUES (1,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2hhbkBnbWFpbC5jb20iLCJ0eXBlIjoiQURNSU4iLCJleHAiOjE2ODA3MTI4NTQsImlhdCI6MTY4MDY3Njg1NH0.qGyT1Rl7IqMLSx-wd3jzPuXz_C4dIpu22f-gHgQLdQM','2023-02-16 14:42:59.000000',1,'2023-04-05 12:10:54.104000','ushan@gmail.com',1,1),(6,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGFuaWxrYUBnbWFpbC5jb20iLCJleHAiOjE2NzY1ODYyODcsImlhdCI6MTY3NjU1MDI4N30.VVZYP1Hy0keinkI8JSiaWTMKRHfgMUhzNZ2E3gpm-xQ','2023-02-16 15:44:45.657000',1,'2023-02-16 17:54:47.863000','shanilka@gmail.com',2,1),(7,NULL,'2023-02-16 18:18:01.037000',NULL,'2023-02-16 18:18:01.037000','shanilka1@gmail.com',3,1);
/*!40000 ALTER TABLE `admin_login_credential` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `admin_password`
--

LOCK TABLES `admin_password` WRITE;
/*!40000 ALTER TABLE `admin_password` DISABLE KEYS */;
INSERT INTO `admin_password` VALUES (1,'2023-02-16 14:43:20.000000','$2a$10$89xFn0JQCXmXtRcG9EnQCugkqN/YexylP2lHj9A33wuD7bXM4Ad8G','2023-02-16 14:43:22.000000',1,1),(6,'2023-02-16 15:44:45.657000','$2a$10$VIZJGL0mh9uhqxcsna0aN.dfXlfgCjVjHO75ZF/36Py/TdCyhGBIy','2023-02-16 15:44:45.657000',6,1),(7,'2023-02-16 18:18:01.037000','$2a$10$0br0F8JThKqXIwF6nv9jo.7ZuWgtssjQzg4hWbPXy6AMuNY7q1Zn.','2023-02-16 18:18:01.037000',7,1);
/*!40000 ALTER TABLE `admin_password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `admin_type`
--

LOCK TABLES `admin_type` WRITE;
/*!40000 ALTER TABLE `admin_type` DISABLE KEYS */;
INSERT INTO `admin_type` VALUES (1,'2023-02-16 14:42:08.000000','Super Admin','2023-02-16 14:42:17.000000',1),(2,'2023-02-16 14:42:08.000000','Admin','2023-02-16 14:42:17.000000',1);
/*!40000 ALTER TABLE `admin_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'INNOVEX','2023-04-05 12:13:01','ushan@gmail.com','2023-04-05 12:13:01',1),(2,'Panasonic','2023-04-05 12:19:21','ushan@gmail.com','2023-04-05 12:19:21',1),(3,'Carvin','2023-04-05 12:23:58','ushan@gmail.com','2023-04-05 12:23:58',1);
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (4,'Iron','2023-04-05 12:15:47','ushan@gmail.com','2023-04-05 12:15:47',1),(5,'Stand Fan','2023-04-05 12:19:33','ushan@gmail.com','2023-04-05 12:19:33',1),(6,'Bedroom Furniture','2023-04-05 12:24:17','ushan@gmail.com','2023-04-05 12:24:17',1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `installment_plan`
--

LOCK TABLES `installment_plan` WRITE;
/*!40000 ALTER TABLE `installment_plan` DISABLE KEYS */;
INSERT INTO `installment_plan` VALUES (1,'2023-04-03 02:42:27','ushan@gmail.com','Initial Plan','2023-04-03 02:42:42',1);
/*!40000 ALTER TABLE `installment_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (3,'2023-04-05 12:17:18','1 Year Warranty.\nWarranty Covers Only Manufacturing Defects.','Innovex Steam & Spray Iron',0,'2023-04-05 12:17:18',1,8590,'ushan@gmail.com',1,4),(4,'2023-04-05 12:21:50','2 Year Warranty.\nWarranty Covers Only Manufacturing Defects.','Panasonic Stand Fan with Remote',0,'2023-04-05 12:21:50',1,41695,'ushan@gmail.com',2,5),(5,'2023-04-05 12:26:01','3 Year Comprehensive Warranty.\nWarranty Covers Only Manufacturing Defects.','Carvin Bedroom Suite',0,'2023-04-05 12:26:01',1,123575,'ushan@gmail.com',3,6);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `product_has_images`
--

LOCK TABLES `product_has_images` WRITE;
/*!40000 ALTER TABLE `product_has_images` DISABLE KEYS */;
INSERT INTO `product_has_images` VALUES (1,'2023-04-05 12:17:18','2023-04-05 12:17:18','https://firebasestorage.googleapis.com/v0/b/bumble-bee-72e1d.appspot.com/o/products%2F4c0c36500a0e41680677164689?alt=media&token=980d3d26-f7da-4ff9-ae1e-d20af0490b09',3,1,'ushan@gmail.com'),(2,'2023-04-05 12:17:18','2023-04-05 12:17:18','https://firebasestorage.googleapis.com/v0/b/bumble-bee-72e1d.appspot.com/o/products%2Fae4cd4ab505b31680677170520?alt=media&token=07726905-4f7c-4503-9588-3e570a1e1a3d',3,1,'ushan@gmail.com'),(3,'2023-04-05 12:17:18','2023-04-05 12:17:18','https://firebasestorage.googleapis.com/v0/b/bumble-bee-72e1d.appspot.com/o/products%2Feabe8cd8b73591680677176004?alt=media&token=2355b4d8-2307-4900-9721-3a6ae129d9c3',3,1,'ushan@gmail.com'),(4,'2023-04-05 12:21:50','2023-04-05 12:21:50','https://firebasestorage.googleapis.com/v0/b/bumble-bee-72e1d.appspot.com/o/products%2Fe12fd13e5c70b1680677494346?alt=media&token=c151009c-eecf-44ad-830e-1e2e040c6a92',4,1,'ushan@gmail.com'),(5,'2023-04-05 12:21:50','2023-04-05 12:21:50','https://firebasestorage.googleapis.com/v0/b/bumble-bee-72e1d.appspot.com/o/products%2Fb4a8ec374b331680677496342?alt=media&token=40e4fc7f-1869-4076-b46d-47c80fbe9fc3',4,1,'ushan@gmail.com'),(6,'2023-04-05 12:21:50','2023-04-05 12:21:50','https://firebasestorage.googleapis.com/v0/b/bumble-bee-72e1d.appspot.com/o/products%2F867965af3e0581680677498165?alt=media&token=e7e58213-de9d-4168-b27a-c4e9ff27a4d8',4,1,'ushan@gmail.com'),(7,'2023-04-05 12:26:01','2023-04-05 12:26:01','https://firebasestorage.googleapis.com/v0/b/bumble-bee-72e1d.appspot.com/o/products%2F93e34f60dcc071680677700326?alt=media&token=2c8f269c-6c67-4567-b83f-3bfddf19718e',5,1,'ushan@gmail.com'),(8,'2023-04-05 12:26:01','2023-04-05 12:26:01','https://firebasestorage.googleapis.com/v0/b/bumble-bee-72e1d.appspot.com/o/products%2Fa422f2e61ce131680677702650?alt=media&token=942161f2-df90-4986-9f1c-732df165848b',5,1,'ushan@gmail.com'),(9,'2023-04-05 12:26:01','2023-04-05 12:26:01','https://firebasestorage.googleapis.com/v0/b/bumble-bee-72e1d.appspot.com/o/products%2F202470583fdcd1680677705841?alt=media&token=720171a5-6678-4898-bfc2-772f91158c9b',5,1,'ushan@gmail.com'),(10,'2023-04-05 12:26:01','2023-04-05 12:26:01','https://firebasestorage.googleapis.com/v0/b/bumble-bee-72e1d.appspot.com/o/products%2Fbed10af8802b71680677708567?alt=media&token=44832f2e-353e-4a59-991a-5020ceb73c71',5,1,'ushan@gmail.com');
/*!40000 ALTER TABLE `product_has_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'2023-04-05 12:10:05.000000','Active','2023-04-05 12:10:09.000000'),(2,'2023-04-05 12:10:24.000000','Delete','2023-04-05 12:10:25.000000'),(3,'2023-04-05 12:10:23.000000','Not Verified','2023-04-05 12:10:26.000000');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES (1,'2023-04-05 12:17:18',20,'2023-04-05 12:17:18',3,1,'ushan@gmail.com'),(2,'2023-04-05 12:21:50',45,'2023-04-05 12:21:50',4,1,'ushan@gmail.com'),(3,'2023-04-05 12:26:01',10,'2023-04-05 12:26:01',5,1,'ushan@gmail.com');
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `temp_otp`
--

LOCK TABLES `temp_otp` WRITE;
/*!40000 ALTER TABLE `temp_otp` DISABLE KEYS */;
/*!40000 ALTER TABLE `temp_otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Matara','94','2023-04-05 12:37:19','ushanshanilka20@gmail.com','20006201663','76699875','2023-04-05 12:37:19',1,'2023-04-03','Sunil Sunil',15000,1,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_login_credential`
--

LOCK TABLES `user_login_credential` WRITE;
/*!40000 ALTER TABLE `user_login_credential` DISABLE KEYS */;
INSERT INTO `user_login_credential` VALUES (1,'','2023-04-05 12:37:19',0,'2023-04-05 12:37:19','ushanshanilka20@gmail.com',1,1);
/*!40000 ALTER TABLE `user_login_credential` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_password`
--

LOCK TABLES `user_password` WRITE;
/*!40000 ALTER TABLE `user_password` DISABLE KEYS */;
INSERT INTO `user_password` VALUES (1,'2023-04-05 12:37:19','$2a$10$VvtsqeG3qhEL6Nl.PaEAten0IJtEfbYMjk4tDA6GfBrEnSZCYPg4C','2023-04-05 12:37:19',1,1);
/*!40000 ALTER TABLE `user_password` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-05 12:54:00
