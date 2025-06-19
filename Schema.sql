-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 19, 2025 at 01:56 PM
-- Server version: 9.1.0
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `trackngo`
--

-- --------------------------------------------------------

--
-- Table structure for table `deliverypersonnel`
--

DROP TABLE IF EXISTS `deliverypersonnel`;
CREATE TABLE IF NOT EXISTS `deliverypersonnel` (
  `personnelID` int NOT NULL,
  `personnelName` varchar(50) NOT NULL,
  `personnelContact` varchar(15) NOT NULL,
  `schedule` varchar(100) NOT NULL,
  `assignedRoute` varchar(100) NOT NULL,
  `deliveryHistory` text,
  `createdOn` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `availability` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'available',
  PRIMARY KEY (`personnelID`),
  KEY `DeliveryPersonnel_ibfk_1` (`personnelID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `deliverypersonnel`
--

INSERT INTO `deliverypersonnel` (`personnelID`, `personnelName`, `personnelContact`, `schedule`, `assignedRoute`, `deliveryHistory`, `createdOn`, `lastUpdated`, `availability`) VALUES
(3, 'Driver', '0779841651', '8AM - 10AM', 'Galle - Colombo', 'N/A', '2025-06-18 13:17:27', '2025-06-19 04:31:03', 'Available');

-- --------------------------------------------------------

--
-- Table structure for table `deliveryschedule`
--

DROP TABLE IF EXISTS `deliveryschedule`;
CREATE TABLE IF NOT EXISTS `deliveryschedule` (
  `scheduleID` int NOT NULL AUTO_INCREMENT,
  `shipmentID` int NOT NULL,
  `deliveryDate` date NOT NULL,
  `timeSlot` varchar(50) NOT NULL,
  PRIMARY KEY (`scheduleID`),
  KEY `DeliverySchedule_ibfk_1` (`shipmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `drivernotifications`
--

DROP TABLE IF EXISTS `drivernotifications`;
CREATE TABLE IF NOT EXISTS `drivernotifications` (
  `notificationID` int NOT NULL AUTO_INCREMENT,
  `personnelID` int NOT NULL,
  `message` text NOT NULL,
  `sentOn` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notificationID`),
  KEY `personnelIDfk` (`personnelID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
CREATE TABLE IF NOT EXISTS `notifications` (
  `notificationID` int NOT NULL AUTO_INCREMENT,
  `recipientID` int NOT NULL,
  `message` text NOT NULL,
  `createdOn` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notificationID`),
  KEY `fk_notifications_recipientID` (`recipientID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`notificationID`, `recipientID`, `message`, `createdOn`) VALUES
(19, 1, 'Status has been changed to: Delivered', '2025-06-18 16:04:29'),
(24, 2, 'Estimated arrival time changed to: 2025-01-01 00:00:00', '2025-06-19 04:34:38'),
(25, 2, 'Status has been changed to: In Transit', '2025-06-19 04:34:38'),
(27, 2, 'Status has been changed to: Delayed', '2025-06-19 04:34:53'),
(28, 2, 'Status has been changed to: Delivered', '2025-06-19 04:54:08');

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
CREATE TABLE IF NOT EXISTS `reports` (
  `reportID` int NOT NULL AUTO_INCREMENT,
  `reportType` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `generatedOn` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reportID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `shipments`
--

DROP TABLE IF EXISTS `shipments`;
CREATE TABLE IF NOT EXISTS `shipments` (
  `shipmentID` int NOT NULL AUTO_INCREMENT,
  `receiverName` varchar(100) NOT NULL,
  `shipmentStatus` varchar(50) DEFAULT 'Pending',
  `assignedDriverID` int DEFAULT NULL,
  `createdOn` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` int DEFAULT NULL,
  `urgent` tinyint(1) NOT NULL DEFAULT '0',
  `currentLocation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'Warehouse',
  `estimatedDeliveryTime` datetime DEFAULT NULL,
  `delay` int DEFAULT '0',
  PRIMARY KEY (`shipmentID`),
  KEY `fk_shipments_userid` (`userid`),
  KEY `Shipments_ibfk_1` (`assignedDriverID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `shipments`
--

INSERT INTO `shipments` (`shipmentID`, `receiverName`, `shipmentStatus`, `assignedDriverID`, `createdOn`, `userid`, `urgent`, `currentLocation`, `estimatedDeliveryTime`, `delay`) VALUES
(4, 'Matheesha', 'Delivered', 3, '2025-06-18 14:14:33', 2, 0, '773/A, Panagodad, Homagama', '2025-01-01 00:00:00', 0),
(5, 'Sasvin', 'Delayed', 3, '2025-06-18 14:14:51', 2, 0, '771/5, Panagoda, homagama', '2025-11-28 00:00:00', 10),
(6, 'Buddhima', 'Delivered', 3, '2025-06-18 14:15:08', 2, 0, 'Thalawathugoda', '2025-12-07 00:00:00', 0),
(7, 'Themiya', 'Delivered', 3, '2025-06-18 15:58:29', 1, 0, 'Jaela', '2025-01-01 00:00:00', 10),
(8, 'Chamara', 'Pending', 3, '2025-06-18 16:09:10', 2, 0, 'Gampaha', '2028-01-01 12:00:00', 0),
(9, 'Tharuka', 'In Transit', 3, '2025-06-18 16:17:19', 2, 0, 'Ambalangoda', '2027-04-01 00:00:00', 0),
(10, 'Henuka', 'In Transit', 3, '2025-06-18 16:23:28', 1, 0, NULL, '2025-01-01 08:00:00', 0),
(11, 'Test', 'Delayed', 3, '2025-06-19 04:31:11', 2, 0, 'Galle', '2025-01-01 00:00:00', 5),
(12, 'new', 'Pending', 3, '2025-06-19 04:43:44', 2, 0, 'Colombo', '2025-08-15 12:00:00', 0),
(13, 'henuka', 'In Transit', 3, '2025-06-19 04:54:37', 1, 0, NULL, '2025-01-01 08:00:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `trackshipmentprogress`
--

DROP TABLE IF EXISTS `trackshipmentprogress`;
CREATE TABLE IF NOT EXISTS `trackshipmentprogress` (
  `trackingID` int NOT NULL AUTO_INCREMENT,
  `shipmentID` int NOT NULL,
  `currentLocation` varchar(100) DEFAULT NULL,
  `estimatedDeliveryTime` datetime DEFAULT NULL,
  `delay` int DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `userid` int DEFAULT NULL,
  PRIMARY KEY (`trackingID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `email` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'user',
  `userid` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`email`),
  UNIQUE KEY `userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`email`, `username`, `password`, `role`, `userid`) VALUES
('admin@admin.com', 'Admin', 'admin', 'admin', 1),
('driver@driver.com', 'Driver', 'driver', 'driver', 3),
('newdriver@driver.com', 'newdriver', 'driver', 'driver', 4),
('user@user.com', 'User', 'user', 'user', 2);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `deliverypersonnel`
--
ALTER TABLE `deliverypersonnel`
  ADD CONSTRAINT `DeliveryPersonnel_ibfk_1` FOREIGN KEY (`personnelID`) REFERENCES `users` (`userid`) ON DELETE CASCADE;

--
-- Constraints for table `deliveryschedule`
--
ALTER TABLE `deliveryschedule`
  ADD CONSTRAINT `DeliverySchedule_ibfk_1` FOREIGN KEY (`shipmentID`) REFERENCES `shipments` (`shipmentID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
