-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 05, 2017 at 01:06 PM
-- Server version: 5.1.36
-- PHP Version: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `wingerso_bms_inter_branch`
--

-- --------------------------------------------------------

--
-- Table structure for table `actionlog`
--

CREATE TABLE IF NOT EXISTS `actionlog` (
  `LogNo` int(11) NOT NULL AUTO_INCREMENT,
  `LogDay` int(11) NOT NULL,
  `LogMonth` int(11) NOT NULL,
  `LogYear` int(11) NOT NULL,
  `UserNo` int(11) NOT NULL,
  `UserName` varchar(255) DEFAULT NULL,
  `PermissionNo` int(11) NOT NULL,
  `PermissionName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`LogNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `actionlog`
--


-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE IF NOT EXISTS `branch` (
  `branch_id` int(11) NOT NULL AUTO_INCREMENT,
  `branch_code` varchar(100) NOT NULL,
  `branch_name` varchar(100) NOT NULL,
  `add_date` datetime NOT NULL,
  `edit_date` datetime NOT NULL,
  PRIMARY KEY (`branch_id`),
  UNIQUE KEY `branch_code` (`branch_code`),
  UNIQUE KEY `branch_name` (`branch_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`branch_id`, `branch_code`, `branch_name`, `add_date`, `edit_date`) VALUES
(1, 'KAMPALA', 'KAMPALA', '2014-09-25 00:01:13', '2016-07-15 14:25:17'),
(2, 'MBARARA', 'MBARARA', '2015-03-30 15:26:57', '2016-07-15 14:25:28');

-- --------------------------------------------------------

--
-- Table structure for table `group_right`
--

CREATE TABLE IF NOT EXISTS `group_right` (
  `group_right_id` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `group_detail_id` int(11) NOT NULL,
  `function_name` varchar(20) NOT NULL,
  `allow_view` varchar(3) NOT NULL,
  `allow_add` varchar(3) NOT NULL,
  `allow_edit` varchar(3) NOT NULL,
  `allow_delete` varchar(3) NOT NULL,
  PRIMARY KEY (`store_id`,`group_detail_id`,`function_name`),
  UNIQUE KEY `group_right_id` (`group_right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `group_right`
--


-- --------------------------------------------------------

--
-- Table structure for table `points_card`
--

CREATE TABLE IF NOT EXISTS `points_card` (
  `points_card_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `card_number` varchar(10) NOT NULL,
  `card_holder` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `physical_address` varchar(250) NOT NULL,
  `tax_identity` varchar(100) NOT NULL,
  `account_details` varchar(255) NOT NULL,
  `website` varchar(100) DEFAULT NULL,
  `cpname` varchar(100) DEFAULT NULL,
  `cptitle` varchar(100) DEFAULT NULL,
  `cpphone` varchar(100) DEFAULT NULL,
  `cpemail` varchar(100) DEFAULT NULL,
  `reg_branch_id` int(11) NOT NULL,
  `points_balance` float NOT NULL,
  `add_date` datetime NOT NULL,
  `add_user` varchar(100) NOT NULL,
  `edit_date` datetime NOT NULL,
  `edit_user` varchar(100) NOT NULL,
  PRIMARY KEY (`points_card_id`),
  UNIQUE KEY `card_number` (`card_number`),
  KEY `branch_to_points_card_branch_id` (`reg_branch_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `points_card`
--

INSERT INTO `points_card` (`points_card_id`, `card_number`, `card_holder`, `email`, `phone`, `physical_address`, `tax_identity`, `account_details`, `website`, `cpname`, `cptitle`, `cpphone`, `cpemail`, `reg_branch_id`, `points_balance`, `add_date`, `add_user`, `edit_date`, `edit_user`) VALUES
(1, '4406893724', 'SSERWANGA ANDREW', 'dubaigroup@ymail.com', '0774060222', 'mbarara', '1002345413', 'ANDREW', '', 'DAYTODAY', '', '0704500416', '', 1, 630.6, '2015-04-26 14:24:26', 'Andrew Sserwanga ', '2015-04-26 14:24:26', 'Andrew Sserwanga ');

-- --------------------------------------------------------

--
-- Table structure for table `points_transaction`
--

CREATE TABLE IF NOT EXISTS `points_transaction` (
  `points_transaction_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `points_card_id` bigint(20) NOT NULL,
  `transaction_date` date NOT NULL,
  `points_awarded` float NOT NULL,
  `points_spent` float NOT NULL,
  `transaction_id` bigint(20) NOT NULL,
  `trans_branch_id` int(11) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_user` varchar(100) NOT NULL,
  `edit_date` datetime NOT NULL,
  `edit_user` varchar(100) NOT NULL,
  `points_spent_amount` float DEFAULT NULL,
  PRIMARY KEY (`points_transaction_id`),
  KEY `PointsCard_to_PointsTransaction_PointsCardId` (`points_card_id`),
  KEY `branch_to_points_transaction_branch_id` (`trans_branch_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=56 ;

--
-- Dumping data for table `points_transaction`
--

INSERT INTO `points_transaction` (`points_transaction_id`, `points_card_id`, `transaction_date`, `points_awarded`, `points_spent`, `transaction_id`, `trans_branch_id`, `add_date`, `add_user`, `edit_date`, `edit_user`, `points_spent_amount`) VALUES
(1, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-07-07 19:15:40', 'Admin User ', '2016-07-07 19:15:40', 'Admin User ', 0),
(2, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-07-07 19:17:44', 'Admin User ', '2016-07-07 19:17:44', 'Admin User ', 0),
(3, 1, '2016-07-07', 15, 10, 6703, 1, '2016-07-07 19:17:44', 'Admin User ', '2016-07-07 19:17:44', 'Admin User ', 1000),
(4, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-07-07 19:24:21', 'Admin User ', '2016-07-07 19:24:21', 'Admin User ', 0),
(5, 1, '2016-07-07', 15, 10, 6703, 1, '2016-07-07 19:24:21', 'Admin User ', '2016-07-07 19:24:21', 'Admin User ', 1000),
(6, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-08-30 17:10:22', 'Admin ASER ', '2016-08-30 17:10:22', 'Admin ASER ', 0),
(7, 1, '2016-07-07', 15, 10, 6703, 1, '2016-08-30 17:10:22', 'Admin ASER ', '2016-08-30 17:10:22', 'Admin ASER ', 1000),
(8, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-08-30 17:11:49', 'Admin ASER ', '2016-08-30 17:11:49', 'Admin ASER ', 0),
(9, 1, '2016-07-07', 15, 10, 6703, 1, '2016-08-30 17:11:49', 'Admin ASER ', '2016-08-30 17:11:49', 'Admin ASER ', 1000),
(10, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-08-30 17:18:33', 'Admin ASER ', '2016-08-30 17:18:33', 'Admin ASER ', 0),
(11, 1, '2016-07-07', 15, 10, 6703, 1, '2016-08-30 17:18:33', 'Admin ASER ', '2016-08-30 17:18:33', 'Admin ASER ', 1000),
(12, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-08-30 17:28:38', 'Admin ASER ', '2016-08-30 17:28:38', 'Admin ASER ', 0),
(13, 1, '2016-07-07', 15, 10, 6703, 1, '2016-08-30 17:28:38', 'Admin ASER ', '2016-08-30 17:28:38', 'Admin ASER ', 1000),
(14, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-09-03 16:29:23', 'Admin ASER ', '2016-09-03 16:29:23', 'Admin ASER ', 0),
(15, 1, '2016-07-07', 15, 10, 6703, 1, '2016-09-03 16:29:23', 'Admin ASER ', '2016-09-03 16:29:23', 'Admin ASER ', 1000),
(16, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-09-03 16:59:36', 'Admin ASER ', '2016-09-03 16:59:36', 'Admin ASER ', 0),
(17, 1, '2016-07-07', 15, 10, 6703, 1, '2016-09-03 16:59:37', 'Admin ASER ', '2016-09-03 16:59:37', 'Admin ASER ', 1000),
(18, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-09-06 20:38:25', 'Admin ASER ', '2016-09-06 20:38:25', 'Admin ASER ', 0),
(19, 1, '2016-07-07', 15, 10, 6703, 1, '2016-09-06 20:38:25', 'Admin ASER ', '2016-09-06 20:38:25', 'Admin ASER ', 1000),
(20, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-09-06 20:42:39', 'Admin ASER ', '2016-09-06 20:42:39', 'Admin ASER ', 0),
(21, 1, '2016-07-07', 15, 10, 6703, 1, '2016-09-06 20:42:39', 'Admin ASER ', '2016-09-06 20:42:39', 'Admin ASER ', 1000),
(22, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-09-06 20:56:52', 'Admin ASER ', '2016-09-06 20:56:52', 'Admin ASER ', 0),
(23, 1, '2016-07-07', 15, 10, 6703, 1, '2016-09-06 20:56:52', 'Admin ASER ', '2016-09-06 20:56:52', 'Admin ASER ', 1000),
(24, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-12 12:50:27', 'Admin ASER ', '2016-11-12 12:50:27', 'Admin ASER ', 0),
(25, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-12 12:50:27', 'Admin ASER ', '2016-11-12 12:50:27', 'Admin ASER ', 1000),
(26, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-12 12:54:17', 'Admin ASER ', '2016-11-12 12:54:17', 'Admin ASER ', 0),
(27, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-12 12:54:17', 'Admin ASER ', '2016-11-12 12:54:17', 'Admin ASER ', 1000),
(28, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-12 12:57:35', 'Admin ASER ', '2016-11-12 12:57:35', 'Admin ASER ', 0),
(29, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-12 12:57:35', 'Admin ASER ', '2016-11-12 12:57:35', 'Admin ASER ', 1000),
(30, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-12 13:00:43', 'Admin ASER ', '2016-11-12 13:00:43', 'Admin ASER ', 0),
(31, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-12 13:00:43', 'Admin ASER ', '2016-11-12 13:00:43', 'Admin ASER ', 1000),
(32, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-12 13:02:34', 'Admin ASER ', '2016-11-12 13:02:34', 'Admin ASER ', 0),
(33, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-12 13:02:34', 'Admin ASER ', '2016-11-12 13:02:34', 'Admin ASER ', 1000),
(34, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-16 11:35:12', 'Admin ASER ', '2016-11-16 11:35:12', 'Admin ASER ', 0),
(35, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-16 11:35:12', 'Admin ASER ', '2016-11-16 11:35:12', 'Admin ASER ', 1000),
(36, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-16 11:37:28', 'Admin ASER ', '2016-11-16 11:37:28', 'Admin ASER ', 0),
(37, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-16 11:37:28', 'Admin ASER ', '2016-11-16 11:37:28', 'Admin ASER ', 1000),
(38, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-16 11:48:52', 'Admin ASER ', '2016-11-16 11:48:52', 'Admin ASER ', 0),
(39, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-16 11:48:52', 'Admin ASER ', '2016-11-16 11:48:52', 'Admin ASER ', 1000),
(40, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-16 11:54:38', 'Admin ASER ', '2016-11-16 11:54:38', 'Admin ASER ', 0),
(41, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-16 11:54:38', 'Admin ASER ', '2016-11-16 11:54:38', 'Admin ASER ', 1000),
(42, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-16 11:57:25', 'Admin ASER ', '2016-11-16 11:57:25', 'Admin ASER ', 0),
(43, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-16 11:57:25', 'Admin ASER ', '2016-11-16 11:57:25', 'Admin ASER ', 1000),
(44, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-16 12:02:32', 'Admin ASER ', '2016-11-16 12:02:32', 'Admin ASER ', 0),
(45, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-16 12:02:32', 'Admin ASER ', '2016-11-16 12:02:32', 'Admin ASER ', 1000),
(46, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-16 12:13:02', 'Admin ASER ', '2016-11-16 12:13:02', 'Admin ASER ', 0),
(47, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-16 12:13:02', 'Admin ASER ', '2016-11-16 12:13:02', 'Admin ASER ', 1000),
(48, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-16 12:21:15', 'Admin ASER ', '2016-11-16 12:21:15', 'Admin ASER ', 0),
(49, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-16 12:21:16', 'Admin ASER ', '2016-11-16 12:21:16', 'Admin ASER ', 1000),
(50, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-16 12:23:55', 'Admin ASER ', '2016-11-16 12:23:55', 'Admin ASER ', 0),
(51, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-16 12:23:55', 'Admin ASER ', '2016-11-16 12:23:55', 'Admin ASER ', 1000),
(52, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-22 20:19:14', 'Admin ASER ', '2016-11-22 20:19:14', 'Admin ASER ', 0),
(53, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-22 20:19:15', 'Admin ASER ', '2016-11-22 20:19:15', 'Admin ASER ', 1000),
(54, 1, '2016-07-07', 17.7, 0, 6702, 1, '2016-11-22 20:32:24', 'Admin ASER ', '2016-11-22 20:32:24', 'Admin ASER ', 0),
(55, 1, '2016-07-07', 15, 10, 6703, 1, '2016-11-22 20:32:24', 'Admin ASER ', '2016-11-22 20:32:24', 'Admin ASER ', 1000);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `points_card`
--
ALTER TABLE `points_card`
  ADD CONSTRAINT `branch_to_points_card_branch_id` FOREIGN KEY (`reg_branch_id`) REFERENCES `branch` (`branch_id`);

--
-- Constraints for table `points_transaction`
--
ALTER TABLE `points_transaction`
  ADD CONSTRAINT `branch_to_points_transaction_branch_id` FOREIGN KEY (`trans_branch_id`) REFERENCES `branch` (`branch_id`),
  ADD CONSTRAINT `PointsCard_to_PointsTransaction_PointsCardId` FOREIGN KEY (`points_card_id`) REFERENCES `points_card` (`points_card_id`);
