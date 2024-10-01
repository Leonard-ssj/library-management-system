-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: biblioteca_proyect
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `available_quantity` int NOT NULL,
  `category` enum('ART','BIOGRAPHY','FICTION','HISTORY','LITERATURE','SCIENCE','SPORTS','TECHNOLOGY') NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) NOT NULL,
  `publication_date` date DEFAULT NULL,
  `quantity` int NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKkibbepcitr0a3cpk3rfr7nihn` (`isbn`),
  CONSTRAINT `books_chk_1` CHECK ((`quantity` >= 1))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'F. Scott Fitzgerald',5,'FICTION','/book-covers/elgrangatsby.jpeg','9876543219874','1925-04-10',5,'El Gran Gatsby'),(2,'Gabriel García Márquez',11,'LITERATURE','/book-covers/cienañosdesoledad.jpeg','9780307474728','1967-06-05',11,'Cien años de soledad'),(3,'Antoine de Saint-Exupéry',15,'LITERATURE','/book-covers/elprincipito.png','9780156012195','1943-04-06',15,'El Principito123'),(4,'Stephen Hawking',11,'SCIENCE','/book-covers/Breves respuestas a las grandes preguntas.jpeg','9781984819192','2018-10-16',11,'Breves respuestas a las grandes preguntas'),(5,'Yuval Noah Harari',12,'HISTORY','/book-covers/De animales a dioses.jpeg','9780062316097','2011-02-04',12,'De animales a dioses'),(6,'Andrew Hunt, David Thomas',6,'TECHNOLOGY','/book-covers/The Pragmatic Programmer.jpeg','9789564762154','1999-10-30',6,'The Pragmatic Programmer'),(7,'Jostein Gaarde',22,'HISTORY','/book-covers/el mundo de sofia.jpeg','9568754312546','1991-12-05',22,'El mundo de Sofía');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loans`
--

DROP TABLE IF EXISTS `loans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loans` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `duration` enum('ONE_MONTH','TWO_MONTHS','TWO_WEEKS') DEFAULT NULL,
  `loan_date` date NOT NULL,
  `request_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `status` enum('ACCEPTED','OVERDUE','PENDING','REJECTED','RETURNED') NOT NULL,
  `book_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKokwvlrv6o4i4h3le3bwhe6kie` (`book_id`),
  KEY `FK6xxlcjc0rqtn5nq28vjnx5t9d` (`user_id`),
  CONSTRAINT `FK6xxlcjc0rqtn5nq28vjnx5t9d` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKokwvlrv6o4i4h3le3bwhe6kie` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loans`
--

LOCK TABLES `loans` WRITE;
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
INSERT INTO `loans` VALUES (1,'TWO_WEEKS','2024-10-03','2024-09-19','2024-09-21','RETURNED',1,9),(2,'ONE_MONTH','2024-10-19','2024-09-19','2024-09-24','RETURNED',6,9),(3,'TWO_WEEKS','2024-10-03','2024-09-19','2024-09-24','RETURNED',1,9),(4,'ONE_MONTH','2024-10-19','2024-09-19',NULL,'REJECTED',3,9),(5,'TWO_MONTHS','2024-11-18','2024-09-19','2024-09-24','RETURNED',4,7),(6,'TWO_WEEKS','2024-10-03','2024-09-19',NULL,'REJECTED',4,7),(7,'ONE_MONTH','2024-10-19','2024-09-19','2024-09-24','RETURNED',2,11),(8,'TWO_MONTHS','2024-11-19','2024-09-20','2024-09-24','RETURNED',5,11),(9,'ONE_MONTH','2024-10-20','2024-09-20','2024-09-24','RETURNED',3,11),(10,'TWO_MONTHS','2024-11-20','2024-09-21','2024-09-21','RETURNED',2,12),(11,'TWO_MONTHS','2024-11-20','2024-09-21','2024-09-24','RETURNED',3,12),(12,'ONE_MONTH','2024-10-25','2024-09-25','2024-09-25','RETURNED',3,14),(13,'TWO_MONTHS','2024-11-24','2024-09-25',NULL,'REJECTED',5,14),(14,'TWO_MONTHS','2024-11-24','2024-09-25','2024-09-25','RETURNED',7,14);
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` enum('ADMIN','LIBRARIAN','USER') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'LIBRARIAN'),(3,'USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (13,1),(10,2),(5,3),(6,3),(7,3),(8,3),(9,3),(11,3),(12,3),(14,3);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(80) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'usuario1@example.com','Pérez','Juan','password123','usuario1'),(2,'eduardo12@example.com','Lopez','Eduardo','password123','eduardo14'),(3,'maria@example.com','Fernandez','Maria','password123','maria123'),(4,'juanito@example.com','Pérez','Juan','password123','juanito13'),(5,'pardo0435@gmail.com','Alonso','Leonardo','eeeeeeeeeeeee','leonard'),(6,'genardo88@gmail.com','Cipres','Gerardo','$2a$10$5hwRslTkvMihbaXUN2v7OubWiSTi1lMEsYmrchUVBP8CBmsIYGWpm','gerardo88'),(7,'carlos14@gmail.com','ortega','carlos','$2a$10$MsR9tS1tHVR1YtJevQ/QVuyx42vwjIhJBt/Nt6kUImbb497NoFlCa','carlos14'),(8,'ramon@mail','sisa','ramon','$2a$10$xhtbefjt2f9JCD4yZ6/7ouUDegYmTVbxfrgFyZS8aT9ulYggt4zsq','ramonn'),(9,'gilberto09@gmail.com','morales','gilberto','$2a$10$F0BcuIZsAaGW8aSqiXZB1eNcgkrFFzEXes9xpgX.Nx6OJVVe14K7y','gilber123'),(10,'librarian@example.com','LastName','Librarian','$2a$10$iNr1BPwPrJi78Pc1m8qz3.e3AUPhxs1d9OQSF8IspDsVOeJxNOFsm','librarianUser'),(11,'jesus33@mail.com','Arteaga','Jesus','$2a$10$QAAhyPHWkhqtVF2Hi.IhQe009aLc6s260vTj2sOjvnPotz5.h61zu','jesus33'),(12,'gilberto123@mailcom','Towers','Gilberto','$2a$10$FuxtkUE/bq1w3I0Ogk2KretYX258gJLwnGVtARrWaFrVby9sApbju','gilberto31'),(13,'admin@example.com','Adminis','Admin','$2a$10$WyDIa/dLekDDnfWVr6DVuO6Z0b6j/zm0EqbH7JSBPcyz2ao1gUUE6','admin123'),(14,'ramon@mail.com','anta','ramon','$2a$10$jHDzfFzjvosdQFI4graq8ug8c.TTU6838yST6jy5JQYm1tctpMC9O','ramon123');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-26 23:09:55
