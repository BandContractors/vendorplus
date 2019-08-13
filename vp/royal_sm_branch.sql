-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 13, 2019 at 12:04 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `royal_sm_branch`
--

-- --------------------------------------------------------

--
-- Table structure for table `acc_category`
--

CREATE TABLE IF NOT EXISTS `acc_category` (
  `acc_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `acc_group_id` int(11) NOT NULL,
  `category_code` varchar(9) NOT NULL,
  `acc_category_name` varchar(100) NOT NULL,
  `acc_category_desc` varchar(1000) DEFAULT NULL,
  `order_category` int(4) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`acc_category_id`),
  UNIQUE KEY `category_code` (`category_code`),
  UNIQUE KEY `acc_category_name` (`acc_category_name`),
  KEY `FKacc_catego335761` (`acc_group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=57 ;

--
-- Dumping data for table `acc_category`
--

INSERT INTO `acc_category` (`acc_category_id`, `acc_group_id`, `category_code`, `acc_category_name`, `acc_category_desc`, `order_category`, `is_active`, `is_deleted`, `add_date`, `add_by`, `last_edit_date`, `last_edit_by`) VALUES
(1, 1, '1-00-000', 'Cash and Cash Equivalents', '', 1, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(2, 1, '1-00-010', 'Receivables', '', 2, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(3, 1, '1-00-020', 'Inventory', '', 3, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(4, 1, '1-00-030', 'Prepaid Expenses', '', 4, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(5, 1, '1-00-040', 'Other Current Assets', '', 5, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(6, 2, '1-10-000', 'Investments', '', 6, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(7, 3, '1-20-000', 'Property, Plant & Equipment', '', 7, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(8, 3, '1-20-010', 'Accumulated Depreciation', '', 8, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(9, 3, '1-20-020', 'Other Noncurrent Assets', '', 9, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(10, 4, '2-00-000', 'Payables', '', 10, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(11, 4, '2-00-010', 'Accrued Compensation', '', 11, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(12, 4, '2-00-020', 'Accrued Taxes', '', 12, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(13, 4, '2-00-030', 'Other Current Liabilities', '', 13, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(14, 5, '2-10-000', 'Long Term Debt', '', 14, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(15, 5, '2-10-010', 'Deferred Taxes', '', 15, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(16, 5, '2-10-020', 'Other Noncurrent Liabilities', '', 16, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(17, 6, '3-10-000', 'Equity', '', 17, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(18, 7, '4-10-000', 'Sales', '', 18, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(19, 8, '4-20-000', 'Non-Operating Revenue', '', 19, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(20, 9, '5-10-000', 'Cost of Sales(Goods Sold)', '', 20, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(21, 10, '5-20-000', 'Advertisement', '', 21, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(22, 10, '5-20-010', 'Audit Expenses', '', 22, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(23, 10, '5-20-020', 'Bad Debts', '', 23, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(24, 10, '5-20-030', 'Commission', '', 24, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(25, 10, '5-20-040', 'Computer Expenses', '', 25, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(26, 10, '5-20-050', 'Donations', '', 26, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(27, 10, '5-20-060', 'Entertainment', '', 27, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(28, 10, '5-20-070', 'Freight and Transport', '', 28, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(29, 10, '5-20-080', 'Gift Expenses', '', 29, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(30, 10, '5-20-090', 'Hotel, Boarding and Lodging Expenses', '', 30, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(31, 10, '5-20-100', 'Legal expenses', '', 31, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(32, 10, '5-20-110', 'Utility Expenses', '', 32, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(33, 10, '5-20-120', 'Rent', '', 33, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(34, 10, '5-20-130', 'Rates', '', 34, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(35, 10, '5-20-140', 'Repairs & Maintenance', '', 35, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(36, 10, '5-20-150', 'Sales Promotion', '', 36, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(37, 10, '5-20-160', 'Staff Welfare Expenses', '', 37, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(38, 10, '5-20-170', 'Startup cost/ pre- operating expenses', '', 38, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(39, 10, '5-20-180', 'Stationery and printing', '', 39, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(40, 10, '5-20-190', 'Subsistence Allowance', '', 40, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(41, 10, '5-20-200', 'Telephone Expenses', '', 41, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(42, 10, '5-20-210', 'Training Expenditure', '', 42, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(43, 10, '5-20-220', 'Travelling Expenses including foreign travelling', '', 43, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(44, 10, '5-20-230', 'Workshop - Conference Expenses', '', 44, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(45, 10, '5-20-240', 'Internet Expenses', '', 45, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(46, 10, '5-20-250', 'Depriciation', '', 46, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(47, 10, '5-20-260', 'Loss on disposal of assets', '', 47, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(48, 10, '5-20-270', 'Management Fees', '', 48, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(49, 10, '5-20-280', 'Scientific Research Expenses', '', 49, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(50, 10, '5-20-290', 'Employment Expenses', '', 50, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(51, 10, '5-20-300', 'Financial Expenses', '', 51, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(52, 10, '5-20-400', 'Short-Term Insurance Business Expenses', '', 52, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(53, 10, '5-20-410', 'Income Tax Expense', '', 53, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(54, 10, '5-20-420', 'Proposed Dividend', '', 54, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(55, 10, '5-20-430', 'Other Operating Expenses', '', 55, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(56, 11, '5-30-000', 'Non-Operating Expenses', '', 56, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `acc_child_account`
--

CREATE TABLE IF NOT EXISTS `acc_child_account` (
  `acc_child_account_id` int(11) NOT NULL AUTO_INCREMENT,
  `acc_coa_id` int(11) NOT NULL,
  `acc_coa_account_code` varchar(20) NOT NULL,
  `child_account_code` varchar(100) NOT NULL,
  `child_account_name` varchar(50) NOT NULL,
  `child_account_desc` varchar(250) DEFAULT NULL,
  `user_detail_id` int(11) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `currency_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `balance_checker_on` int(1) NOT NULL,
  PRIMARY KEY (`acc_child_account_id`),
  KEY `FKacc_child_932504` (`acc_coa_id`),
  KEY `FKacc_child_595280` (`currency_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `acc_child_account`
--

INSERT INTO `acc_child_account` (`acc_child_account_id`, `acc_coa_id`, `acc_coa_account_code`, `child_account_code`, `child_account_name`, `child_account_desc`, `user_detail_id`, `store_id`, `currency_id`, `currency_code`, `is_active`, `is_deleted`, `add_date`, `add_by`, `last_edit_date`, `last_edit_by`, `balance_checker_on`) VALUES
(11, 52, '2-00-000-070', 'PrepInc', 'Customer Deposit A/C', '', NULL, 1, NULL, NULL, 1, 0, '2018-02-15 19:56:12', 1, NULL, NULL, 0),
(12, 22, '1-00-030-050', 'PrepExp', 'Advance to Supplier A/C', '', NULL, 1, NULL, NULL, 1, 0, '2018-02-15 19:57:32', 1, NULL, NULL, 0),
(13, 167, '1-00-010-080', 'WHT', 'Withholding Tax Paid', NULL, NULL, NULL, NULL, NULL, 1, 0, '0000-00-00 00:00:00', 1, NULL, NULL, 0),
(14, 1, '1-00-000-010', 'CD', 'Cash Drawer', '', NULL, NULL, NULL, NULL, 1, 0, '2018-10-20 17:31:48', 1, '2019-07-26 16:42:19', 5, 1),
(15, 3, '1-00-000-030', 'DUGX', 'DFCU BANK UGX', '', NULL, NULL, 4, 'UGX', 1, 0, '2018-12-08 13:18:08', 3, '2019-07-26 16:42:37', 5, 1),
(16, 3, '1-00-000-030', 'DUSD', 'DFCU BANK USD', '', NULL, NULL, 6, 'USD', 1, 0, '2019-05-28 19:46:11', 1, '2019-07-26 16:42:31', 5, 1),
(17, 160, '1-00-000-050', '0773556871', 'MTN MM', '', NULL, NULL, 4, 'UGX', 1, 0, '2019-05-28 19:51:48', 1, '2019-07-26 16:43:26', 5, 1),
(18, 2, '1-00-000-020', 'P Cash', 'Petty Cash', '', NULL, 1, 4, 'UGX', 1, 0, '2019-06-06 13:00:09', 1, '2019-07-26 16:42:25', 5, 1),
(19, 74, '3-10-000-010', 'Capital ACC', 'Alexandra Capital', '', 5, 1, NULL, NULL, 1, 0, '2019-06-06 13:02:29', 1, '2019-07-26 16:43:05', 5, 1),
(20, 74, '3-10-000-010', '01', 'Summer Capital', '', 5, 1, NULL, NULL, 1, 0, '2019-06-11 13:19:13', 5, '2019-07-26 16:42:55', 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `acc_class`
--

CREATE TABLE IF NOT EXISTS `acc_class` (
  `acc_class_id` int(11) NOT NULL AUTO_INCREMENT,
  `acc_class_name` varchar(50) NOT NULL,
  `acc_class_desc` varchar(1000) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`acc_class_id`),
  UNIQUE KEY `acc_class_name` (`acc_class_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `acc_class`
--

INSERT INTO `acc_class` (`acc_class_id`, `acc_class_name`, `acc_class_desc`, `is_active`, `is_deleted`, `add_date`, `add_by`, `last_edit_date`, `last_edit_by`) VALUES
(1, 'Personal Account', 'Personal accounts can be Natural (related to any individual like David, George, Ram, etc.,) or Artificial (related to any artificial person like M/s ABC Ltd, M/s General Trading, M/s Reliance Industries, etc., or Representative (represents a group of account, If there are a number of accounts of similar nature, it is better to group them like salary payable account, rent payable account, insurance prepaid account, interest receivable account, capital account and drawing account, etc.)', 1, 0, '2016-11-11 07:46:00', 1, NULL, NULL),
(2, 'Real Account', 'Every Business has some assets and every asset has an account. Thus, asset account is called a real account. There are two type of assets: Tangible assets are touchable assets such as plant, machinery, furniture, stock, cash, etc. Intangible assets are non-touchable assets such as goodwill, patent, copyrights, etc. Accounting treatment for both type of assets is same.', 1, 0, '2016-11-11 07:46:00', 1, NULL, NULL),
(3, 'Nominal Account', 'Since this account does not represent any tangible asset, it is called nominal or fictitious account. All kinds of expense account, loss account, gain account or income accounts come under the category of nominal account. For example, rent account, salary account, electricity expenses account, interest income account, etc.', 1, 0, '2016-11-11 07:46:00', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `acc_coa`
--

CREATE TABLE IF NOT EXISTS `acc_coa` (
  `acc_coa_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_code` varchar(20) NOT NULL,
  `account_name` varchar(100) NOT NULL,
  `account_desc` varchar(1000) DEFAULT NULL,
  `acc_class_id` int(11) DEFAULT NULL,
  `acc_type_id` int(11) NOT NULL,
  `acc_group_id` int(11) NOT NULL,
  `acc_category_id` int(11) NOT NULL,
  `order_coa` int(4) DEFAULT NULL,
  `is_active` int(1) DEFAULT NULL,
  `is_deleted` int(1) DEFAULT NULL,
  `is_child` int(1) NOT NULL DEFAULT '1',
  `is_transactor_mandatory` int(1) NOT NULL DEFAULT '0',
  `is_system_account` int(1) NOT NULL,
  `add_date` datetime DEFAULT NULL,
  `add_by` int(11) DEFAULT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`acc_coa_id`),
  UNIQUE KEY `account_code` (`account_code`),
  UNIQUE KEY `account_name` (`account_name`),
  KEY `FKacc_coa603581` (`acc_type_id`),
  KEY `FKacc_coa456341` (`acc_class_id`),
  KEY `FKacc_coa196958` (`acc_group_id`),
  KEY `FKacc_coa949787` (`acc_category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=168 ;

--
-- Dumping data for table `acc_coa`
--

INSERT INTO `acc_coa` (`acc_coa_id`, `account_code`, `account_name`, `account_desc`, `acc_class_id`, `acc_type_id`, `acc_group_id`, `acc_category_id`, `order_coa`, `is_active`, `is_deleted`, `is_child`, `is_transactor_mandatory`, `is_system_account`, `add_date`, `add_by`, `last_edit_date`, `last_edit_by`) VALUES
(1, '1-00-000-010', 'CASH Cash on Hand', '', NULL, 1, 1, 1, 1, 1, 0, 0, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(2, '1-00-000-020', 'CASH Petty Cash', '', NULL, 1, 1, 1, 2, 1, 0, 0, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(3, '1-00-000-030', 'CASH at Bank', '', NULL, 1, 1, 1, 3, 1, 0, 0, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(4, '1-00-000-040', 'CASH Debitor', '', NULL, 1, 1, 1, 4, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(5, '1-00-010-010', 'A/REC Trade', '', NULL, 1, 1, 2, 5, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(6, '1-00-010-020', 'A/REC Trade Notes Receivable', '', NULL, 1, 1, 2, 6, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(7, '1-00-010-030', 'A/REC Installment Receivables', '', NULL, 1, 1, 2, 7, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(8, '1-00-010-040', 'A/REC Retainage Withheld', '', NULL, 1, 1, 2, 8, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(9, '1-00-010-050', 'A/REC Allowance For Bad Debts (Contra Account)', '', NULL, 1, 1, 2, 9, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(10, '1-00-010-060', 'A/REC Sales Tax (VAT)', '', NULL, 1, 1, 2, 10, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(11, '1-00-010-070', 'A/REC Other Receivables', '', NULL, 1, 1, 2, 11, 1, 0, 0, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(12, '1-00-020-010', 'INV Stores', '', NULL, 1, 1, 3, 12, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(13, '1-00-020-020', 'INV Raw Materials', '', NULL, 1, 1, 3, 13, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(14, '1-00-020-030', 'INV Work-in-Progress', '', NULL, 1, 1, 3, 14, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(15, '1-00-020-040', 'INV Finished Goods', '', NULL, 1, 1, 3, 15, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(16, '1-00-020-050', 'INV Reservesd', '', NULL, 1, 1, 3, 16, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(17, '1-00-020-060', 'INV Unbilled Cost & Fees', '', NULL, 1, 1, 3, 17, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(18, '1-00-030-010', 'PREPAID Insurance', '', NULL, 1, 1, 4, 18, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(19, '1-00-030-020', 'PREPAID Rent', '', NULL, 1, 1, 4, 19, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(20, '1-00-030-030', 'PREPAID Advertising', '', NULL, 1, 1, 4, 20, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(21, '1-00-030-040', 'PREPAID Interest', '', NULL, 1, 1, 4, 21, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(22, '1-00-030-050', 'PREPAID Other Prepaid Expenses', '', NULL, 1, 1, 4, 22, 1, 0, 0, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(23, '1-00-040-010', 'OCA Other Current Assets', '', NULL, 1, 1, 5, 23, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(24, '1-10-000-010', 'INVEST Shares', '', NULL, 1, 2, 6, 24, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(25, '1-10-000-020', 'INVEST Debantures', '', NULL, 1, 2, 6, 25, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(26, '1-10-000-030', 'INVEST Fixed Deposits', '', NULL, 1, 2, 6, 26, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(27, '1-10-000-040', 'INVEST Government Securities', '', NULL, 1, 2, 6, 27, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(28, '1-10-000-050', 'INVEST Other Investments', '', NULL, 1, 2, 6, 28, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(29, '1-20-000-010', 'PPE Land', '', NULL, 1, 3, 7, 29, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(30, '1-20-000-020', 'PPE Buildings', '', NULL, 1, 3, 7, 30, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(31, '1-20-000-030', 'PPE Machinery & Equipment', '', NULL, 1, 3, 7, 31, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(32, '1-20-000-040', 'PPE Vehicles', '', NULL, 1, 3, 7, 32, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(33, '1-20-000-050', 'PPE Furniture and Fixtures', '', NULL, 1, 3, 7, 33, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(34, '1-20-000-060', 'PPE Computer Equipment', '', NULL, 1, 3, 7, 34, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(35, '1-20-000-070', 'PPE Other Property, Plant & Equipment', '', NULL, 1, 3, 7, 35, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(36, '1-20-010-010', 'ACCUM Buildings (Contra Acc)', '', NULL, 1, 3, 8, 36, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(37, '1-20-010-020', 'ACCUM Machinery & Equipment (Contra Acc)', '', NULL, 1, 3, 8, 37, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(38, '1-20-010-030', 'ACCUM Vehicles (Contra Acc)', '', NULL, 1, 3, 8, 38, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(39, '1-20-010-040', 'ACCUM Furniture and Fixtures (Contra Acc)', '', NULL, 1, 3, 8, 39, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(40, '1-20-010-050', 'ACCUM Computer Equipment (Contra Acc)', '', NULL, 1, 3, 8, 40, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(41, '1-20-010-060', 'ACCUM Other Property, Plant & Equipment (Contrac Acc)', '', NULL, 1, 3, 8, 41, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(42, '1-20-020-010', 'Intangible Assets', '', NULL, 1, 3, 9, 42, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(43, '1-20-020-020', 'Other Noncurrent Assets', '', NULL, 1, 3, 9, 43, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(44, '1-20-020-030', 'Intangible Assets - Accumulated Amortization (Contra Acc)', '', NULL, 1, 3, 9, 44, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(45, '1-20-020-040', 'Other Noncurrent Assets - Accumulated Amortization (Contra Acc)', '', NULL, 1, 3, 9, 45, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(46, '2-00-000-010', 'Accounts Payable Trade', '', NULL, 2, 4, 10, 46, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(47, '2-00-000-020', 'Accounts Payable Interest', '', NULL, 2, 4, 10, 47, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(48, '2-00-000-030', 'Accounts Payable Retainage Withheld', '', NULL, 2, 4, 10, 48, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(49, '2-00-000-040', 'Accounts Payable ', '', NULL, 2, 4, 10, 49, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(50, '2-00-000-050', 'Bank Loans (Notes Payable) ', 'Bank Loans (Notes Payable) - Current principal portion of Long-Term Notes', NULL, 2, 4, 10, 50, 1, 0, 0, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(51, '2-00-000-060', 'Short Term Notes (Demand Notes)', '', NULL, 2, 4, 10, 51, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(52, '2-00-000-070', 'Customer Advances and Deposits Payable', '', NULL, 2, 4, 10, 52, 1, 0, 0, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(53, '2-00-000-080', 'Unearned Revenues', '', NULL, 2, 4, 10, 53, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(54, '2-00-000-090', 'Accounts Payable Sales Tax (VAT)', '', NULL, 2, 4, 10, 54, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(55, '2-00-000-100', 'Accounts Payable Other', '', NULL, 2, 4, 10, 55, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(56, '2-00-010-010', 'Accrued - Payroll', '', NULL, 2, 4, 11, 56, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(57, '2-00-010-020', 'Accrued - Commissions', '', NULL, 2, 4, 11, 57, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(58, '2-00-010-030', 'Accrued - Employer - Social Security Fund', '', NULL, 2, 4, 11, 58, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(59, '2-00-010-040', 'Accrued - Employer - Medical Benefits', '', NULL, 2, 4, 11, 59, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(60, '2-00-010-050', 'Withheld - Employee - Social Security Fund', '', NULL, 2, 4, 11, 60, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(61, '2-00-010-060', 'Withheld - Employee - Medical Benefits', '', NULL, 2, 4, 11, 61, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(62, '2-00-010-070', 'Withheld - Employee - Local Service Tax', '', NULL, 2, 4, 11, 62, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(63, '2-00-010-080', 'Other Accrued Compensation', '', NULL, 2, 4, 11, 63, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(64, '2-00-020-010', 'Accrued Income Tax', '', NULL, 2, 4, 12, 64, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(65, '2-00-020-020', 'Accrued Value Added Tax', '', NULL, 2, 4, 12, 65, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(66, '2-00-030-010', 'Other Current Liabilities', '', NULL, 2, 4, 13, 66, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(67, '2-10-000-010', 'LTD Bank Loans (Notes Payable)', 'Bank Loans (Notes Payable) - Long Term principal portion', NULL, 2, 5, 14, 67, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(68, '2-10-000-020', 'LTD Notes Payable (other than bank notes)', 'Notes Payable (other than bank notes) - Long Term principal portion', NULL, 2, 5, 14, 68, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(69, '2-10-000-030', 'LTD Inter-Company Payables', '', NULL, 2, 5, 14, 69, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(70, '2-10-000-040', 'LTD Other Long Term Debt', '', NULL, 2, 5, 14, 70, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(71, '2-10-010-010', 'DT Deferred Income Taxe', '', NULL, 2, 5, 15, 71, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(72, '2-10-010-020', 'DT Other Deferred Taxes', '', NULL, 2, 5, 15, 72, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(73, '2-10-020-010', 'Other Noncurrent Liabilities', '', NULL, 2, 5, 16, 73, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(74, '3-10-000-010', 'Paid in Capital', '', NULL, 3, 6, 17, 74, 1, 0, 0, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(75, '3-10-000-020', 'Partners Capital', '', NULL, 3, 6, 17, 75, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(76, '3-10-000-030', 'Common Stock', '', NULL, 3, 6, 17, 76, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(77, '3-10-000-040', 'Preferred Stock', '', NULL, 3, 6, 17, 77, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(78, '3-10-000-050', 'Member Contribution', '', NULL, 3, 6, 17, 78, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(79, '3-10-000-060', 'Retained Earnings', '', NULL, 3, 6, 17, 79, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(80, '4-10-000-010', 'SALES Products', '', NULL, 4, 7, 18, 80, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(81, '4-10-000-020', 'SALES Services', '', NULL, 4, 7, 18, 81, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(82, '4-10-000-030', 'SALES Discounts (Contra-Revenue Acc)', '', NULL, 4, 7, 18, 82, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(83, '4-10-000-040', 'SALES Returns and Allowances (Contra-Revenue Acc)', '', NULL, 4, 7, 18, 83, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(84, '4-20-000-010', 'Interest Income', '', NULL, 4, 8, 19, 84, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(85, '4-20-000-020', 'Dividends', '', NULL, 4, 8, 19, 85, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(86, '4-20-000-030', 'Commissions', '', NULL, 4, 8, 19, 86, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(87, '4-20-000-040', 'Rental Income', '', NULL, 4, 8, 19, 87, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(88, '4-20-000-050', 'Gain On Sale Of Assets', '', NULL, 4, 8, 19, 88, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(89, '4-20-000-060', 'Gift-Related Gain', '', NULL, 4, 8, 19, 89, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(90, '4-20-000-070', 'Exchange-Related Gain', '', NULL, 4, 8, 19, 90, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(91, '4-20-000-080', 'Other Non-Operating Revenue', '', NULL, 4, 8, 19, 91, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(92, '5-10-000-010', 'Cost of Purchase - Products', '', NULL, 5, 9, 20, 92, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(93, '5-10-000-020', 'Cost of Purchase - Services', '', NULL, 5, 9, 20, 93, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(94, '5-10-000-030', 'Cost of Purchase - Freight', '', NULL, 5, 9, 20, 94, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(95, '5-10-000-040', 'Cost of Purchase - Inventory Adjustments', '', NULL, 5, 9, 20, 95, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(96, '5-10-000-050', 'Purchase Returns and Allowances (Contra Account)', '', NULL, 5, 9, 20, 96, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(97, '5-10-000-060', 'Purchase Discounts (Contra Account)', '', NULL, 5, 9, 20, 97, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(98, '5-10-000-070', 'Cost of Goods Manufactured and Sold', '', NULL, 5, 9, 20, 98, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(99, '5-10-000-080', 'Cost of Sales - Loyalties', '', NULL, 5, 9, 20, 99, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(100, '5-20-000-010', 'Advertisement', '', NULL, 5, 10, 21, 100, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(101, '5-20-010-010', 'Audit Expenses', '', NULL, 5, 10, 22, 101, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(102, '5-20-020-010', 'Bad Debts Written Off', '', NULL, 5, 10, 23, 102, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(103, '5-20-020-020', 'Provision for Bad and Doubtful Debts/ Impairment for credit losses ( for banks)', '', NULL, 5, 10, 23, 103, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(104, '5-20-030-010', 'Commission', '', NULL, 5, 10, 24, 104, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(105, '5-20-040-010', 'Computer Expenses', '', NULL, 5, 10, 25, 105, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(106, '5-20-050-010', 'Donations', '', NULL, 5, 10, 26, 106, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(107, '5-20-060-010', 'Entertainment', '', NULL, 5, 10, 27, 107, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(108, '5-20-070-010', 'Freight and Transport', '', NULL, 5, 10, 28, 108, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(109, '5-20-080-010', 'Gift Expenses', '', NULL, 5, 10, 29, 109, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(110, '5-20-090-010', 'Hotel, Boarding and Lodging Expenses', '', NULL, 5, 10, 30, 110, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(111, '5-20-100-010', 'Legal expenses', '', NULL, 5, 10, 31, 111, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(112, '5-20-110-010', 'Power and Fuel', '', NULL, 5, 10, 32, 112, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(113, '5-20-110-020', 'Water', '', NULL, 5, 10, 32, 113, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(114, '5-20-110-030', 'Garbage Collection', '', NULL, 5, 10, 32, 114, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(115, '5-20-110-040', 'Other Utilities', '', NULL, 5, 10, 32, 115, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(116, '5-20-120-010', 'Rent', '', NULL, 5, 10, 33, 116, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(117, '5-20-130-010', 'Rates', '', NULL, 5, 10, 34, 117, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(118, '5-20-140-010', 'Repairs of Building', '', NULL, 5, 10, 35, 118, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(119, '5-20-140-020', 'Repairs of Vehicle', '', NULL, 5, 10, 35, 119, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(120, '5-20-140-030', 'Repairs of Machinery', '', NULL, 5, 10, 35, 120, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(121, '5-20-150-010', 'Sales Promotion including Publicity ( other than advertisement)', '', NULL, 5, 10, 36, 121, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(122, '5-20-160-010', 'Staff Welfare Expenses', '', NULL, 5, 10, 37, 122, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(123, '5-20-170-010', 'Startup cost/ pre- operating expenses', '', NULL, 5, 10, 38, 123, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(124, '5-20-180-010', 'Stationery and printing', '', NULL, 5, 10, 39, 124, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(125, '5-20-190-010', 'Subsistence Allowance', '', NULL, 5, 10, 40, 125, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(126, '5-20-200-010', 'Telephone Expenses', '', NULL, 5, 10, 41, 126, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(127, '5-20-210-010', 'Training Expenditure', '', NULL, 5, 10, 42, 127, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(128, '5-20-220-010', 'Travelling Expenses including foreign travelling', '', NULL, 5, 10, 43, 128, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(129, '5-20-230-010', 'Workshop - Conference Expenses', '', NULL, 5, 10, 44, 129, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(130, '5-20-240-010', 'Internet Expenses', '', NULL, 5, 10, 45, 130, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(131, '5-20-250-010', 'Depriciation', '', NULL, 5, 10, 46, 131, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(132, '5-20-260-010', 'Loss on disposal of assets', '', NULL, 5, 10, 47, 132, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(133, '5-20-270-010', 'Management Fees', '', NULL, 5, 10, 48, 133, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(134, '5-20-280-010', 'Scientific Research Expenses', '', NULL, 5, 10, 49, 134, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(135, '5-20-290-010', 'Salaries and wages', '', NULL, 5, 10, 50, 135, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(136, '5-20-290-020', 'Bonus', '', NULL, 5, 10, 50, 136, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(137, '5-20-290-030', 'Reimbursement of medical expenses', '', NULL, 5, 10, 50, 137, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(138, '5-20-290-040', 'Leave encashment', '', NULL, 5, 10, 50, 138, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(139, '5-20-290-050', 'Leave travel benefits', '', NULL, 5, 10, 50, 139, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(140, '5-20-290-060', 'Housing allowance/ rent', '', NULL, 5, 10, 50, 140, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(141, '5-20-290-070', 'Contribution to retirement fund', '', NULL, 5, 10, 50, 141, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(142, '5-20-290-080', 'Contribution to any other fund', '', NULL, 5, 10, 50, 142, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(143, '5-20-290-090', 'Any other employment costs', '', NULL, 5, 10, 50, 143, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(144, '5-20-300-010', 'Interest Expense', '', NULL, 5, 10, 51, 144, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(145, '5-20-300-020', 'Bank Charges', '', NULL, 5, 10, 51, 145, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(146, '5-20-300-030', 'Commitment fees', '', NULL, 5, 10, 51, 146, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(147, '5-20-300-040', 'Insurance', '', NULL, 5, 10, 51, 147, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(148, '5-20-300-050', 'Realized exchange loss', '', NULL, 5, 10, 51, 148, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(149, '5-20-300-060', 'Unrealized exchange loss', '', NULL, 5, 10, 51, 149, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(150, '5-20-400-010', 'Claims admitted during the year', '', NULL, 5, 10, 52, 150, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(151, '5-20-400-020', 'Premium returned to insured', '', NULL, 5, 10, 52, 151, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(152, '5-20-400-030', 'Reserved for unexpired risk carried forward', '', NULL, 5, 10, 52, 152, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(153, '5-20-400-040', 'Agency Expenses', '', NULL, 5, 10, 52, 153, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(154, '5-20-400-050', 'Other expenses related to short term business', '', NULL, 5, 10, 52, 154, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(155, '5-20-410-010', 'Income Tax Expense', '', NULL, 5, 10, 53, 155, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(156, '5-20-420-010', 'Proposed Dividend', '', NULL, 5, 10, 54, 156, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(157, '5-20-430-010', 'Expenses - Loyalties', '', NULL, 5, 10, 55, 157, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(158, '5-20-430-020', 'Other Operating Expenses', '', NULL, 5, 10, 55, 158, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(159, '5-30-000-010', 'Non-Operating Expenses', '', NULL, 5, 11, 56, 159, 1, 0, 1, 0, 1, '2016-12-13 12:55:00', 1, NULL, NULL),
(160, '1-00-000-050', 'CASH Mobile Money', '', NULL, 1, 1, 1, 4, 1, 0, 0, 0, 1, '2016-12-15 16:22:00', 1, NULL, NULL),
(161, '3-10-000-070', 'Drawing Account (Contra Equity)', '', NULL, 3, 6, 17, 161, 1, 0, 1, 0, 1, '2017-03-02 15:04:00', 1, NULL, NULL),
(162, '5-10-000-090', 'Loss on inventory write off', '', NULL, 5, 9, 20, 162, 1, 0, 1, 0, 1, '2017-03-07 13:32:00', 1, NULL, NULL),
(164, '2-00-000-110', 'Dividends Payable', '', NULL, 2, 4, 10, 164, 1, 0, 1, 0, 1, '2017-04-11 10:05:00', 1, NULL, NULL),
(165, '3-10-000-080', 'Dividends (Contra Equity)', '', NULL, 3, 6, 17, 165, 1, 0, 1, 0, 1, '2017-04-11 10:05:00', 1, NULL, NULL),
(166, '4-10-000-050', 'SALES Hire', '', NULL, 4, 7, 18, 166, 1, 0, 1, 0, 1, '2018-05-30 20:33:24', 1, NULL, NULL),
(167, '1-00-010-080', 'A/REC Withholding Tax', '', NULL, 1, 1, 2, 10, 1, 0, 0, 0, 1, '2018-09-06 12:38:26', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `acc_config`
--

CREATE TABLE IF NOT EXISTS `acc_config` (
  `acc_config_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_detail_id` int(11) DEFAULT NULL,
  `transaction_type_id` int(11) DEFAULT NULL,
  `transaction_reason_id` int(11) DEFAULT NULL,
  `acc_coa_id` int(11) DEFAULT NULL,
  `account_code` varchar(20) DEFAULT NULL,
  `config_function` varchar(50) DEFAULT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`acc_config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_currency`
--

CREATE TABLE IF NOT EXISTS `acc_currency` (
  `acc_currency_id` int(11) NOT NULL AUTO_INCREMENT,
  `currency_name` varchar(100) NOT NULL,
  `currency_code` varchar(10) NOT NULL,
  `currency_no` int(5) NOT NULL,
  `is_local_currency` int(1) NOT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `decimal_places` int(1) DEFAULT '3',
  `rounding_mode` int(1) DEFAULT '4',
  PRIMARY KEY (`acc_currency_id`),
  UNIQUE KEY `currency_code` (`currency_code`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `acc_currency`
--

INSERT INTO `acc_currency` (`acc_currency_id`, `currency_name`, `currency_code`, `currency_no`, `is_local_currency`, `is_active`, `is_deleted`, `add_date`, `add_by`, `last_edit_date`, `last_edit_by`, `decimal_places`, `rounding_mode`) VALUES
(4, 'Uganda Shilling', 'UGX', 800, 1, 1, 0, '2017-08-07 18:37:37', 1, '2017-10-19 19:14:33', 7, 0, 4),
(6, 'US Dollar', 'USD', 840, 0, 1, 0, '2017-08-07 18:53:52', 1, '2019-05-16 12:36:29', 1, 2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `acc_dep_schedule`
--

CREATE TABLE IF NOT EXISTS `acc_dep_schedule` (
  `acc_dep_schedule_id` int(11) NOT NULL AUTO_INCREMENT,
  `stock_id` bigint(20) DEFAULT NULL,
  `dep_for_acc_period_id` int(11) DEFAULT NULL,
  `dep_from_date` date DEFAULT NULL,
  `dep_to_date` date DEFAULT NULL,
  `year_number` int(4) DEFAULT NULL,
  `dep_amount` double DEFAULT NULL,
  PRIMARY KEY (`acc_dep_schedule_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `acc_dep_schedule`
--

INSERT INTO `acc_dep_schedule` (`acc_dep_schedule_id`, `stock_id`, `dep_for_acc_period_id`, `dep_from_date`, `dep_to_date`, `year_number`, `dep_amount`) VALUES
(1, 1, 0, NULL, NULL, 1, 100000);

-- --------------------------------------------------------

--
-- Table structure for table `acc_group`
--

CREATE TABLE IF NOT EXISTS `acc_group` (
  `acc_group_id` int(11) NOT NULL AUTO_INCREMENT,
  `acc_type_id` int(11) NOT NULL,
  `group_code` varchar(4) NOT NULL,
  `acc_group_name` varchar(100) NOT NULL,
  `acc_group_desc` varchar(1000) DEFAULT NULL,
  `order_group` int(4) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`acc_group_id`),
  UNIQUE KEY `group_code` (`group_code`),
  UNIQUE KEY `acc_group_name` (`acc_group_name`),
  KEY `FKacc_group505319` (`acc_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `acc_group`
--

INSERT INTO `acc_group` (`acc_group_id`, `acc_type_id`, `group_code`, `acc_group_name`, `acc_group_desc`, `order_group`, `is_active`, `is_deleted`, `add_date`, `add_by`, `last_edit_date`, `last_edit_by`) VALUES
(1, 1, '1-00', 'Current Assets', 'Current Assets include Cash and Assets that will be converted into cash or consumed in a relatively short period of time, usually within a year or the business''s operating cycle. Prepaid Expenses and Supplies (already paid for or a liability incurred) are included because they will normally be used or consumed within the operating cycle.', 1, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(2, 1, '1-10', 'Investments', 'Investments that are intended to be held and not converted into cash for an extended period of time (longer than the operating cycle). Reported at current market value by using an allowance for unrealized market gains and losses.', 2, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(3, 1, '1-20', 'Fixed Assets', 'Assets of a durable nature that are used to provide current and future economic benefits to the business. These accounts will normally have a sub ledger that contains a record for each parcel of land, building, or piece of machinery and equipment along with depreciation calculations and amounts.', 3, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(4, 2, '2-00', 'Current Liabilities', 'Current liabilities are the portion of obligations (amounts owed) due to be paid within the current operating cycle (normally a year) and that normally require the use of existing current assets to satisfy the debt.', 4, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(5, 2, '2-10', 'Long Term Liabilities', 'Long term liability accounts are the portions of debts with due dates greater than a year or the operating cycle. These are obligations that are not expected to be paid within the current operating cycle.', 5, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(6, 3, '3-10', 'Equity', 'The owner''s rights or claims to the property (assets) of the business. What the business owes the owner(s). The good stuff left for the owner(s) assuming all liabilities (amounts owed) have been paid. Includes: Owner''s Capital Invested and the Accumulated Profits or Losses for the business since it began', 6, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(7, 4, '4-10', 'Operating Revenue', 'Revenues resulting from the normal operations of a business such as the revenues resulting from the sale of products and services to your customers.', 7, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(8, 4, '4-20', 'Non-Operating Revenue', 'Non-operating revenue accounts include all types of income that you receive that are not part of your main line of business. In other words, revenues or gains resulting from something other than from normal business operations.', 8, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(9, 5, '5-10', 'Cost of Sales(Goods Sold)', 'Cost of Sales or Cost of Goods Sold. Cost of Goods Sold - the cost of the products purchased or manufactured and sold by a business.', 9, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(10, 5, '5-20', 'Operating Expenses', 'Operating Expenses - the expenses related to normal daily operations such as wages, rent,advertising, insurance, etc. These expenses are related to the normal operations of the business (primary activities) and are incurred in order to earn normal operating revenues. In other words,amounts spent on products and services related to normal business operations.', 10, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL),
(11, 5, '5-30', 'Non-Operating Expenses', 'Amounts spent on products and services not related to normal business operations (secondary activities). In other words, expenses and losses resulting from something other than from normal business operations.', 11, 1, 0, '2016-12-13 12:33:00', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `acc_journal`
--

CREATE TABLE IF NOT EXISTS `acc_journal` (
  `acc_journal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `journal_date` date NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `transaction_type_id` int(11) DEFAULT NULL,
  `transaction_reason_id` int(11) DEFAULT NULL,
  `pay_id` bigint(20) DEFAULT NULL,
  `pay_type_id` int(11) DEFAULT NULL,
  `pay_reason_id` int(11) DEFAULT NULL,
  `store_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `ledger_folio` varchar(3) DEFAULT NULL,
  `acc_coa_id` int(11) NOT NULL,
  `account_code` varchar(20) NOT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `acc_period_id` int(11) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `job_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`acc_journal_id`),
  KEY `FKacc_journa956479` (`acc_coa_id`),
  KEY `FKacc_journa25711` (`acc_period_id`),
  KEY `FKacc_journa88937` (`acc_child_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_journal_payable`
--

CREATE TABLE IF NOT EXISTS `acc_journal_payable` (
  `acc_journal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `journal_date` date NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `transaction_type_id` int(11) DEFAULT NULL,
  `transaction_reason_id` int(11) DEFAULT NULL,
  `pay_id` bigint(20) DEFAULT NULL,
  `pay_type_id` int(11) DEFAULT NULL,
  `pay_reason_id` int(11) DEFAULT NULL,
  `store_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `ledger_folio` varchar(3) DEFAULT NULL,
  `acc_coa_id` int(11) NOT NULL,
  `account_code` varchar(20) NOT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `acc_period_id` int(11) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `job_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`acc_journal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_journal_prepaid`
--

CREATE TABLE IF NOT EXISTS `acc_journal_prepaid` (
  `acc_journal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `journal_date` date NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `transaction_type_id` int(11) DEFAULT NULL,
  `transaction_reason_id` int(11) DEFAULT NULL,
  `pay_id` bigint(20) DEFAULT NULL,
  `pay_type_id` int(11) DEFAULT NULL,
  `pay_reason_id` int(11) DEFAULT NULL,
  `store_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `ledger_folio` varchar(3) DEFAULT NULL,
  `acc_coa_id` int(11) NOT NULL,
  `account_code` varchar(20) NOT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `acc_period_id` int(11) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `job_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`acc_journal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_journal_receivable`
--

CREATE TABLE IF NOT EXISTS `acc_journal_receivable` (
  `acc_journal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `journal_date` date NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `transaction_type_id` int(11) DEFAULT NULL,
  `transaction_reason_id` int(11) DEFAULT NULL,
  `pay_id` bigint(20) DEFAULT NULL,
  `pay_type_id` int(11) DEFAULT NULL,
  `pay_reason_id` int(11) DEFAULT NULL,
  `store_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `ledger_folio` varchar(3) DEFAULT NULL,
  `acc_coa_id` int(11) NOT NULL,
  `account_code` varchar(20) NOT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `acc_period_id` int(11) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `job_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`acc_journal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_ledger`
--

CREATE TABLE IF NOT EXISTS `acc_ledger` (
  `acc_ledger_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_period_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `account_code` varchar(20) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) NOT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `debit_amount_lc` double DEFAULT NULL,
  `credit_amount_lc` double DEFAULT NULL,
  PRIMARY KEY (`acc_ledger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_ledger_close`
--

CREATE TABLE IF NOT EXISTS `acc_ledger_close` (
  `acc_ledger_close_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_period_id` int(11) NOT NULL,
  `account_code` varchar(20) NOT NULL,
  `currency_code` varchar(10) NOT NULL,
  `debit_bal` double NOT NULL,
  `credit_bal` double NOT NULL,
  `debit_bal_lc` double DEFAULT NULL,
  `credit_bal_lc` double DEFAULT NULL,
  PRIMARY KEY (`acc_ledger_close_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_ledger_open_bal`
--

CREATE TABLE IF NOT EXISTS `acc_ledger_open_bal` (
  `acc_ledger_open_bal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_period_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `account_code` varchar(20) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) NOT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `debit_amount_lc` double DEFAULT NULL,
  `credit_amount_lc` double DEFAULT NULL,
  PRIMARY KEY (`acc_ledger_open_bal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_ledger_payable`
--

CREATE TABLE IF NOT EXISTS `acc_ledger_payable` (
  `acc_ledger_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_period_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `account_code` varchar(20) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) NOT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `debit_amount_lc` double DEFAULT NULL,
  `credit_amount_lc` double DEFAULT NULL,
  PRIMARY KEY (`acc_ledger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_ledger_prepaid`
--

CREATE TABLE IF NOT EXISTS `acc_ledger_prepaid` (
  `acc_ledger_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_period_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `account_code` varchar(20) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) NOT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `debit_amount_lc` double DEFAULT NULL,
  `credit_amount_lc` double DEFAULT NULL,
  PRIMARY KEY (`acc_ledger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_ledger_receivable`
--

CREATE TABLE IF NOT EXISTS `acc_ledger_receivable` (
  `acc_ledger_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_period_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `account_code` varchar(20) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) NOT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `debit_amount_lc` double DEFAULT NULL,
  `credit_amount_lc` double DEFAULT NULL,
  PRIMARY KEY (`acc_ledger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_period`
--

CREATE TABLE IF NOT EXISTS `acc_period` (
  `acc_period_id` int(11) NOT NULL AUTO_INCREMENT,
  `acc_period_name` varchar(50) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `is_current` int(1) NOT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `is_open` int(1) NOT NULL,
  `is_closed` int(1) NOT NULL,
  `order_no` int(2) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`acc_period_id`),
  UNIQUE KEY `acc_period_name` (`acc_period_name`),
  UNIQUE KEY `order_no` (`order_no`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `acc_period`
--

INSERT INTO `acc_period` (`acc_period_id`, `acc_period_name`, `start_date`, `end_date`, `is_current`, `is_active`, `is_deleted`, `is_open`, `is_closed`, `order_no`, `add_date`, `add_by`, `last_edit_date`, `last_edit_by`) VALUES
(3, '2019', '2019-01-01', '2019-12-31', 1, 1, 0, 1, 0, 1, '2019-01-02 13:10:43', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `acc_type`
--

CREATE TABLE IF NOT EXISTS `acc_type` (
  `acc_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_code` varchar(2) NOT NULL,
  `acc_type_name` varchar(100) NOT NULL,
  `acc_type_desc` varchar(1000) DEFAULT NULL,
  `order_type` int(4) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`acc_type_id`),
  UNIQUE KEY `type_code` (`type_code`),
  UNIQUE KEY `acc_type_name` (`acc_type_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `acc_type`
--

INSERT INTO `acc_type` (`acc_type_id`, `type_code`, `acc_type_name`, `acc_type_desc`, `order_type`, `is_active`, `is_deleted`, `add_date`, `add_by`, `last_edit_date`, `last_edit_by`) VALUES
(1, '1', 'Assets', 'The properties used in the operation or investment activities of a business. All the good stuff a business has (anything with value). The goodies. Includes: Cash, Receivables, Investments, Buildings, Land, Equipment, Vehicles, etc.', 1, 1, 0, '2016-11-11 07:33:39', 1, NULL, NULL),
(2, '2', 'Liabilities', 'Claims by creditors to the property (assets) of a business until they are paid. Other''s claims to the business''s stuff. Amounts the business owes to others. Includes: Payables, Notes, Loans, Mortgages, etc.', 2, 1, 0, '2016-11-11 07:33:39', 1, NULL, NULL),
(3, '3', 'Equity', 'The owner''s rights or claims to the property (assets) of the business. What the business owes the owner(s). The good stuff left for the owner(s) assuming all liabilities (amounts owed) have been paid. Includes: Owner''s Capital Invested and the Accumulated Profits or Losses for the business since it began', 3, 1, 0, '2016-11-11 07:33:39', 1, NULL, NULL),
(4, '4', 'Revenue', 'The gross increase in owner''s equity resulting from the operations and other activities of the business. Amounts a business earns by selling services and products and investing. Amounts billed to customers for services and/or products. Includes: Sales of Goods and Services - revenue directly related to daily operations. Other Income - revenue not directly related to daily operations such as Interest and Dividends.', 4, 1, 0, '2016-11-11 07:33:39', 1, NULL, NULL),
(5, '5', 'Expenses', 'Decrease in owner''s equity resulting from the cost of goods, fixed assets, and services and supplies consumed in the operations of a business. The costs of doing business. The stuff we used and had to pay for or charge to run our business. Cost of Goods Sold - the cost of the products being sold by the business. Operating Expenses - the expenses related to daily operations such as rent, advertising, insurance, etc. Other Expenses - the expenses not directly related to daily operations such as Interest and Financing.', 5, 1, 0, '2016-11-11 07:33:39', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `acc_xrate`
--

CREATE TABLE IF NOT EXISTS `acc_xrate` (
  `acc_xrate_id` int(11) NOT NULL AUTO_INCREMENT,
  `local_currency_id` int(11) NOT NULL,
  `foreign_currency_id` int(11) NOT NULL,
  `local_currency_code` varchar(10) NOT NULL,
  `foreign_currency_code` varchar(10) NOT NULL,
  `buying` double NOT NULL DEFAULT '1',
  `selling` double NOT NULL DEFAULT '1',
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`acc_xrate_id`),
  UNIQUE KEY `unique_local_foreign` (`local_currency_code`,`foreign_currency_code`),
  KEY `FKacc_xrate442932` (`local_currency_id`),
  KEY `FKacc_xrate594846` (`foreign_currency_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `acc_xrate`
--

INSERT INTO `acc_xrate` (`acc_xrate_id`, `local_currency_id`, `foreign_currency_id`, `local_currency_code`, `foreign_currency_code`, `buying`, `selling`, `is_active`, `is_deleted`, `add_date`, `add_by`, `last_edit_date`, `last_edit_by`) VALUES
(1, 4, 6, 'UGX', 'USD', 3800, 3800, 1, 0, '2017-10-19 19:14:24', 7, '2019-05-16 12:36:29', 1);

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

-- --------------------------------------------------------

--
-- Table structure for table `asset_status`
--

CREATE TABLE IF NOT EXISTS `asset_status` (
  `asset_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_status_name` varchar(50) NOT NULL,
  PRIMARY KEY (`asset_status_id`),
  UNIQUE KEY `asset_status_name` (`asset_status_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `average_method`
--

CREATE TABLE IF NOT EXISTS `average_method` (
  `average_method_id` int(11) NOT NULL AUTO_INCREMENT,
  `average_method_name` varchar(50) NOT NULL,
  PRIMARY KEY (`average_method_id`),
  UNIQUE KEY `average_method_name` (`average_method_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `average_method`
--

INSERT INTO `average_method` (`average_method_id`, `average_method_name`) VALUES
(1, 'NONE');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(50) NOT NULL,
  `display_quick_order` int(1) DEFAULT '0',
  `list_rank` int(3) DEFAULT '0',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `display_quick_order`, `list_rank`) VALUES
(1, 'Asset-Land', 0, 0),
(2, 'Asset-Building', 0, 0),
(3, 'Asset-Machinery and Equipment', 0, 0),
(4, 'Asset-Vehicle', 0, 0),
(5, 'Asset-Furniture and Fixtures', 0, 0),
(6, 'Asset-Computer Equipment', 0, 0),
(7, 'Asset-Other Fixed Asset', 0, 0),
(8, 'Operating Expenses', 0, 0),
(9, 'Non-Operating Expenses', 0, 0),
(10, 'Box Profile', 0, 1),
(11, 'Accessories', 0, 2),
(12, 'Corrugated Profile', 0, 3),
(13, 'Roman Profile', 0, 4),
(14, 'Brick Tile Profile', 0, 5),
(15, 'Zee Tile Profile', 0, 6),
(16, 'Coils', 0, 7);

-- --------------------------------------------------------

--
-- Table structure for table `company_setting`
--

CREATE TABLE IF NOT EXISTS `company_setting` (
  `company_setting_id` int(11) NOT NULL,
  `company_name` varchar(100) NOT NULL,
  `physical_address` varchar(250) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `fax` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `website` varchar(100) NOT NULL,
  `logo_url` varchar(300) NOT NULL,
  `sloghan` varchar(100) NOT NULL,
  `currency_used` varchar(10) NOT NULL,
  `vat_perc` double NOT NULL,
  `is_allow_discount` varchar(3) NOT NULL,
  `is_allow_debt` varchar(3) NOT NULL,
  `is_customer_mandatory` varchar(3) NOT NULL,
  `is_supplier_mandatory` varchar(3) NOT NULL,
  `is_vat_inclusive` varchar(3) NOT NULL,
  `is_trade_discount_vat_liable` varchar(3) NOT NULL,
  `is_cash_discount_vat_liable` varchar(3) NOT NULL,
  `is_map_items_active` varchar(3) NOT NULL,
  `branch_code` varchar(100) NOT NULL,
  `branch_id` int(11) NOT NULL,
  `award_amount_per_point` double NOT NULL,
  `spend_amount_per_point` double NOT NULL,
  `tax_identity` varchar(50) NOT NULL,
  `sales_receipt_name` varchar(20) NOT NULL,
  `is_show_developer` varchar(3) NOT NULL,
  `developer_email` varchar(100) NOT NULL,
  `developer_phone` varchar(100) NOT NULL,
  `show_logo_invoice` varchar(3) NOT NULL,
  `is_allow_auto_unpack` varchar(3) DEFAULT NULL,
  `time_zone` varchar(20) DEFAULT NULL,
  `date_format` varchar(20) DEFAULT NULL,
  `license_key` varchar(254) NOT NULL,
  `sales_receipt_version` int(11) DEFAULT '1',
  `enforce_trans_user_select` varchar(3) DEFAULT NULL,
  `show_branch_invoice` varchar(3) DEFAULT NULL,
  `show_store_invoice` varchar(3) DEFAULT NULL,
  `show_vat_analysis_invoice` varchar(3) DEFAULT 'Yes',
  `store_equiv_name` varchar(20) DEFAULT 'Store',
  `inventory_cost_mehod` varchar(20) DEFAULT NULL,
  `is_item_cost_mandatory` varchar(3) DEFAULT NULL,
  `is_show_batch_dtl_default` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`company_setting_id`),
  UNIQUE KEY `company_name` (`company_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `company_setting`
--

INSERT INTO `company_setting` (`company_setting_id`, `company_name`, `physical_address`, `phone`, `fax`, `email`, `website`, `logo_url`, `sloghan`, `currency_used`, `vat_perc`, `is_allow_discount`, `is_allow_debt`, `is_customer_mandatory`, `is_supplier_mandatory`, `is_vat_inclusive`, `is_trade_discount_vat_liable`, `is_cash_discount_vat_liable`, `is_map_items_active`, `branch_code`, `branch_id`, `award_amount_per_point`, `spend_amount_per_point`, `tax_identity`, `sales_receipt_name`, `is_show_developer`, `developer_email`, `developer_phone`, `show_logo_invoice`, `is_allow_auto_unpack`, `time_zone`, `date_format`, `license_key`, `sales_receipt_version`, `enforce_trans_user_select`, `show_branch_invoice`, `show_store_invoice`, `show_vat_analysis_invoice`, `store_equiv_name`, `inventory_cost_mehod`, `is_item_cost_mandatory`, `is_show_batch_dtl_default`) VALUES
(1, 'Royal Mabati (U) Ltd', 'Plot 32 Mukabya Rd, Industrial Area, Along Jinja Rd, Opp. City Oil, Kampala', '256702638383', '', 'info@royalmabati.co.ug', 'royalmabati.co.ug', 'logo_royal.png', 'we are the best', 'UGX', 18, 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'No', 'No', 'Yes', 'KAMPALA', 1, 1, 1, '1010608743', 'Tax Invoice', 'Yes', 'info@wingersoft.co.ug', '+256705010850', 'Yes', 'No', '+03:00', 'dd-MM-yyyy', 'A9NAR6adH3s2y3ZtP5LLu0y D4R)u3qUD1a(A5J P2yii9Xtw8qaB5mbA0qaX3HMo2q X1Rlq8oaR2qyR7Tom6RRo1A0o8J2P7k0F0i0i9TEg3DGg4JDe7wET4B8X5w8R1L8F2X8R5g8m7J8g0T8A0D8X6Z9s3X1Z1J0T5s2H5F2o4T1R0A1F0g3w8XBi8D9', 2, 'Yes', 'Yes', 'Yes', 'Yes', 'Store', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `contact_list`
--

CREATE TABLE IF NOT EXISTS `contact_list` (
  `contact_list_id` int(11) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `subcategory` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `second_name` varchar(255) DEFAULT NULL,
  `email1` varchar(255) DEFAULT NULL,
  `email2` varchar(255) DEFAULT NULL,
  `phone1` varchar(255) DEFAULT NULL,
  `phone2` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `loc_level_1` varchar(255) DEFAULT NULL,
  `loc_level_2` varchar(255) DEFAULT NULL,
  `loc_level_3` varchar(255) DEFAULT NULL,
  `loc_level_4` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`contact_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `country`
--

CREATE TABLE IF NOT EXISTS `country` (
  `country_id` int(11) NOT NULL AUTO_INCREMENT,
  `country_name` varchar(100) NOT NULL,
  PRIMARY KEY (`country_id`),
  UNIQUE KEY `country_name` (`country_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `dep_method`
--

CREATE TABLE IF NOT EXISTS `dep_method` (
  `dep_method_id` int(11) NOT NULL AUTO_INCREMENT,
  `dep_method_name` varchar(50) NOT NULL,
  `dep_method_code` varchar(5) DEFAULT NULL,
  `is_active` int(2) DEFAULT NULL,
  PRIMARY KEY (`dep_method_id`),
  UNIQUE KEY `dep_method_name` (`dep_method_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `dep_method`
--

INSERT INTO `dep_method` (`dep_method_id`, `dep_method_name`, `dep_method_code`, `is_active`) VALUES
(1, 'Straight Line Depreciation', 'SLD', 1),
(2, 'Reducing Balance Depreciation', 'RBD', 1),
(3, 'Sum of the Year Digits Depreciation', 'SYD', 1);

-- --------------------------------------------------------

--
-- Table structure for table `discount_package`
--

CREATE TABLE IF NOT EXISTS `discount_package` (
  `discount_package_id` int(11) NOT NULL,
  `package_name` varchar(50) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`discount_package_id`),
  UNIQUE KEY `package_name` (`package_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `discount_package_item`
--

CREATE TABLE IF NOT EXISTS `discount_package_item` (
  `discount_package_item_id` bigint(20) NOT NULL,
  `discount_package_id` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `item_qty` double NOT NULL,
  `wholesale_discount_amt` double NOT NULL,
  `retailsale_discount_amt` double NOT NULL,
  `hire_price_discount_amt` double DEFAULT NULL,
  PRIMARY KEY (`discount_package_item_id`),
  UNIQUE KEY `u_discpackitem_store_item_qty` (`store_id`,`item_id`,`item_qty`),
  KEY `Item_to_PackItem_on_ItemId` (`item_id`),
  KEY `Pack_to_PackItem_on_PackId` (`discount_package_id`),
  KEY `Store_to_DiscPackageItem_on_StoreId` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `district`
--

CREATE TABLE IF NOT EXISTS `district` (
  `district_id` int(11) NOT NULL AUTO_INCREMENT,
  `district_name` varchar(100) NOT NULL,
  `country_id` int(11) NOT NULL,
  `region_id` int(11) NOT NULL,
  PRIMARY KEY (`district_id`),
  UNIQUE KEY `unique_country_district` (`district_id`,`country_id`),
  KEY `ctry_dist` (`country_id`),
  KEY `region_district` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `group_detail`
--

CREATE TABLE IF NOT EXISTS `group_detail` (
  `group_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  `is_active` varchar(3) NOT NULL,
  PRIMARY KEY (`group_detail_id`),
  UNIQUE KEY `group_name` (`group_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `group_detail`
--

INSERT INTO `group_detail` (`group_detail_id`, `group_name`, `is_active`) VALUES
(2, 'Management', 'Yes'),
(3, 'Cashier', 'Yes');

-- --------------------------------------------------------

--
-- Table structure for table `group_right`
--

CREATE TABLE IF NOT EXISTS `group_right` (
  `group_right_id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) NOT NULL,
  `group_detail_id` int(11) NOT NULL,
  `function_name` varchar(50) NOT NULL,
  `allow_view` varchar(3) NOT NULL,
  `allow_add` varchar(3) NOT NULL,
  `allow_edit` varchar(3) NOT NULL,
  `allow_delete` varchar(3) NOT NULL,
  PRIMARY KEY (`group_right_id`),
  UNIQUE KEY `u_groupright_store_group_function` (`store_id`,`group_detail_id`,`function_name`),
  KEY `GroupDetail_to_GroupRight_GroupDetailId` (`group_detail_id`),
  KEY `Store_to_GroupRight_StoreId` (`store_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=104 ;

--
-- Dumping data for table `group_right`
--

INSERT INTO `group_right` (`group_right_id`, `store_id`, `group_detail_id`, `function_name`, `allow_view`, `allow_add`, `allow_edit`, `allow_delete`) VALUES
(1, 1, 3, '50', 'No', 'No', 'No', 'No'),
(2, 1, 3, '53', 'No', 'No', 'No', 'No'),
(3, 1, 3, '51', 'No', 'No', 'No', 'No'),
(4, 1, 3, '52', 'No', 'No', 'No', 'No'),
(5, 1, 3, '56', 'No', 'No', 'No', 'No'),
(6, 1, 3, '47', 'No', 'No', 'No', 'No'),
(7, 1, 3, '55', 'No', 'No', 'No', 'No'),
(8, 1, 3, '46', 'No', 'No', 'No', 'No'),
(9, 1, 3, '49', 'No', 'No', 'No', 'No'),
(10, 1, 3, '26', 'No', 'No', 'No', 'No'),
(11, 1, 3, '25', 'No', 'No', 'No', 'No'),
(12, 1, 3, '105', 'No', 'No', 'No', 'No'),
(13, 1, 3, '33', 'No', 'No', 'No', 'No'),
(14, 1, 3, '34', 'No', 'No', 'No', 'No'),
(15, 1, 3, '91', 'No', 'No', 'No', 'No'),
(16, 1, 3, '84', 'No', 'No', 'No', 'No'),
(17, 1, 3, '23', 'No', 'No', 'No', 'No'),
(18, 1, 3, '21', 'Yes', 'Yes', 'No', 'No'),
(19, 1, 3, '22', 'Yes', 'Yes', 'No', 'No'),
(20, 1, 3, '24', 'No', 'No', 'No', 'No'),
(21, 1, 3, '90', 'Yes', 'Yes', 'No', 'No'),
(22, 1, 3, '83', 'No', 'No', 'No', 'No'),
(23, 1, 3, '41', 'No', 'No', 'No', 'No'),
(24, 1, 3, '42', 'No', 'No', 'No', 'No'),
(25, 1, 3, '85', 'No', 'No', 'No', 'No'),
(26, 1, 3, '68', 'No', 'No', 'No', 'No'),
(27, 1, 3, '86', 'No', 'No', 'No', 'No'),
(28, 1, 3, '3', 'No', 'No', 'No', 'No'),
(29, 1, 3, '82', 'No', 'No', 'No', 'No'),
(30, 1, 3, '43', 'No', 'No', 'No', 'No'),
(31, 1, 3, '18', 'No', 'No', 'No', 'No'),
(32, 1, 3, '95', 'No', 'No', 'No', 'No'),
(33, 1, 3, '94', 'No', 'No', 'No', 'No'),
(34, 1, 3, '93', 'No', 'No', 'No', 'No'),
(35, 1, 3, '92', 'No', 'No', 'No', 'No'),
(36, 1, 3, '102', 'No', 'No', 'No', 'No'),
(37, 1, 3, '99', 'No', 'No', 'No', 'No'),
(38, 1, 3, '98', 'No', 'No', 'No', 'No'),
(39, 1, 3, '101', 'No', 'No', 'No', 'No'),
(40, 1, 3, '104', 'No', 'No', 'No', 'No'),
(41, 1, 3, '97', 'No', 'No', 'No', 'No'),
(42, 1, 3, '96', 'No', 'No', 'No', 'No'),
(43, 1, 3, '48', 'No', 'No', 'No', 'No'),
(44, 1, 3, '87', 'No', 'No', 'No', 'No'),
(45, 1, 3, '62', 'No', 'No', 'No', 'No'),
(46, 1, 3, '63', 'No', 'No', 'No', 'No'),
(47, 1, 3, '61', 'No', 'No', 'No', 'No'),
(48, 1, 3, '8', 'No', 'No', 'No', 'No'),
(49, 1, 3, '28', 'No', 'No', 'No', 'No'),
(50, 1, 3, '32', 'No', 'No', 'No', 'No'),
(51, 1, 3, '13', 'No', 'No', 'No', 'No'),
(52, 1, 3, '66', 'No', 'No', 'No', 'No'),
(53, 1, 3, '65', 'No', 'No', 'No', 'No'),
(54, 1, 3, '67', 'No', 'No', 'No', 'No'),
(55, 1, 3, '64', 'No', 'No', 'No', 'No'),
(56, 1, 3, '35', 'No', 'No', 'No', 'No'),
(57, 1, 3, '44', 'No', 'No', 'No', 'No'),
(58, 1, 3, '59', 'No', 'No', 'No', 'No'),
(59, 1, 3, '60', 'No', 'No', 'No', 'No'),
(60, 1, 3, '54', 'No', 'No', 'No', 'No'),
(61, 1, 3, '107', 'No', 'No', 'No', 'No'),
(62, 1, 3, '71', 'No', 'No', 'No', 'No'),
(63, 1, 3, '29', 'No', 'No', 'No', 'No'),
(64, 1, 3, '27', 'No', 'No', 'No', 'No'),
(65, 1, 3, '1', 'No', 'No', 'No', 'No'),
(66, 1, 3, '77', 'No', 'No', 'No', 'No'),
(67, 1, 3, '79', 'No', 'No', 'No', 'No'),
(68, 1, 3, '31', 'No', 'No', 'No', 'No'),
(69, 1, 3, '30', 'Yes', 'Yes', 'No', 'No'),
(70, 1, 3, '12', 'Yes', 'Yes', 'No', 'No'),
(71, 1, 3, '78', 'No', 'No', 'No', 'No'),
(72, 1, 3, '57', 'No', 'No', 'No', 'No'),
(73, 1, 3, '58', 'No', 'No', 'No', 'No'),
(74, 1, 3, '11', 'No', 'No', 'No', 'No'),
(75, 1, 3, '17', 'No', 'No', 'No', 'No'),
(76, 1, 3, '2', 'Yes', 'Yes', 'No', 'No'),
(77, 1, 3, '103', 'No', 'No', 'No', 'No'),
(78, 1, 3, '10', 'No', 'No', 'No', 'No'),
(79, 1, 3, '16', 'No', 'No', 'No', 'No'),
(80, 1, 3, '14', 'Yes', 'Yes', 'No', 'No'),
(81, 1, 3, '15', 'Yes', 'Yes', 'No', 'No'),
(82, 1, 3, '75', 'No', 'No', 'No', 'No'),
(83, 1, 3, '72', 'No', 'No', 'No', 'No'),
(84, 1, 3, '74', 'No', 'No', 'No', 'No'),
(85, 1, 3, '73', 'No', 'No', 'No', 'No'),
(86, 1, 3, '76', 'No', 'No', 'No', 'No'),
(87, 1, 3, '70', 'No', 'No', 'No', 'No'),
(88, 1, 3, '88', 'No', 'No', 'No', 'No'),
(89, 1, 3, '89', 'No', 'No', 'No', 'No'),
(90, 1, 3, '106', 'No', 'No', 'No', 'No'),
(91, 1, 3, '108', 'No', 'No', 'No', 'No'),
(92, 1, 3, '69', 'No', 'No', 'No', 'No'),
(93, 1, 3, '36', 'Yes', 'Yes', 'No', 'No'),
(94, 1, 3, '40', 'No', 'No', 'No', 'No'),
(95, 1, 3, '39', 'No', 'No', 'No', 'No'),
(96, 1, 3, '38', 'No', 'No', 'No', 'No'),
(97, 1, 3, '37', 'Yes', 'Yes', 'No', 'No'),
(98, 1, 3, '6', 'No', 'No', 'No', 'No'),
(99, 1, 3, '19', 'No', 'No', 'No', 'No'),
(100, 1, 3, '80', 'No', 'No', 'No', 'No'),
(101, 1, 3, '81', 'No', 'No', 'No', 'No'),
(102, 1, 3, '45', 'No', 'No', 'No', 'No'),
(103, 1, 3, '9', 'No', 'No', 'No', 'No');

-- --------------------------------------------------------

--
-- Table structure for table `group_user`
--

CREATE TABLE IF NOT EXISTS `group_user` (
  `group_user_id` int(11) NOT NULL,
  `group_detail_id` int(11) NOT NULL,
  `user_detail_id` int(11) NOT NULL,
  PRIMARY KEY (`group_detail_id`,`user_detail_id`),
  UNIQUE KEY `group_user_id` (`group_user_id`),
  KEY `GroupDetail_to_GroupUser_GroupDetailId` (`group_detail_id`),
  KEY `UserDetail_to_GroupUser_UserDetailId` (`user_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `group_user`
--

INSERT INTO `group_user` (`group_user_id`, `group_detail_id`, `user_detail_id`) VALUES
(1, 3, 4),
(2, 3, 3),
(3, 2, 5);

-- --------------------------------------------------------

--
-- Table structure for table `iso_currency`
--

CREATE TABLE IF NOT EXISTS `iso_currency` (
  `iso_currency_id` int(11) NOT NULL AUTO_INCREMENT,
  `entity` varchar(250) DEFAULT NULL,
  `currency` varchar(100) DEFAULT NULL,
  `alphabetic_code` varchar(10) DEFAULT NULL,
  `numeric_code` int(5) DEFAULT NULL,
  `minor_unit` int(2) DEFAULT NULL,
  PRIMARY KEY (`iso_currency_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=280 ;

--
-- Dumping data for table `iso_currency`
--

INSERT INTO `iso_currency` (`iso_currency_id`, `entity`, `currency`, `alphabetic_code`, `numeric_code`, `minor_unit`) VALUES
(1, 'AFGHANISTAN', 'Afghani', 'AFN', 971, 2),
(2, '??áLAND ISLANDS', 'Euro', 'EUR', 978, 2),
(3, 'ALBANIA', 'Lek', 'ALL', 8, 2),
(4, 'ALGERIA', 'Algerian Dinar', 'DZD', 12, 2),
(5, 'AMERICAN SAMOA', 'US Dollar', 'USD', 840, 2),
(6, 'ANDORRA', 'Euro', 'EUR', 978, 2),
(7, 'ANGOLA', 'Kwanza', 'AOA', 973, 2),
(8, 'ANGUILLA', 'East Caribbean Dollar', 'XCD', 951, 2),
(9, 'ANTARCTICA', 'No universal currency', '', NULL, NULL),
(10, 'ANTIGUA AND BARBUDA', 'East Caribbean Dollar', 'XCD', 951, 2),
(11, 'ARGENTINA', 'Argentine Peso', 'ARS', 32, 2),
(12, 'ARMENIA', 'Armenian Dram', 'AMD', 51, 2),
(13, 'ARUBA', 'Aruban Florin', 'AWG', 533, 2),
(14, 'AUSTRALIA', 'Australian Dollar', 'AUD', 36, 2),
(15, 'AUSTRIA', 'Euro', 'EUR', 978, 2),
(16, 'AZERBAIJAN', 'Azerbaijanian Manat', 'AZN', 944, 2),
(17, 'BAHAMAS (THE)', 'Bahamian Dollar', 'BSD', 44, 2),
(18, 'BAHRAIN', 'Bahraini Dinar', 'BHD', 48, 3),
(19, 'BANGLADESH', 'Taka', 'BDT', 50, 2),
(20, 'BARBADOS', 'Barbados Dollar', 'BBD', 52, 2),
(21, 'BELARUS', 'Belarusian Ruble', 'BYN', 933, 2),
(22, 'BELARUS', 'Belarusian Ruble', 'BYR', 974, 0),
(23, 'BELGIUM', 'Euro', 'EUR', 978, 2),
(24, 'BELIZE', 'Belize Dollar', 'BZD', 84, 2),
(25, 'BENIN', 'CFA Franc BCEAO', 'XOF', 952, 0),
(26, 'BERMUDA', 'Bermudian Dollar', 'BMD', 60, 2),
(27, 'BHUTAN', 'Indian Rupee', 'INR', 356, 2),
(28, 'BHUTAN', 'Ngultrum', 'BTN', 64, 2),
(29, 'BOLIVIA (PLURINATIONAL STATE OF)', 'Boliviano', 'BOB', 68, 2),
(30, 'BOLIVIA (PLURINATIONAL STATE OF)', 'Mvdol', 'BOV', 984, 2),
(31, 'BONAIRE, SINT EUSTATIUS AND SABA', 'US Dollar', 'USD', 840, 2),
(32, 'BOSNIA AND HERZEGOVINA', 'Convertible Mark', 'BAM', 977, 2),
(33, 'BOTSWANA', 'Pula', 'BWP', 72, 2),
(34, 'BOUVET ISLAND', 'Norwegian Krone', 'NOK', 578, 2),
(35, 'BRAZIL', 'Brazilian Real', 'BRL', 986, 2),
(36, 'BRITISH INDIAN OCEAN TERRITORY (THE)', 'US Dollar', 'USD', 840, 2),
(37, 'BRUNEI DARUSSALAM', 'Brunei Dollar', 'BND', 96, 2),
(38, 'BULGARIA', 'Bulgarian Lev', 'BGN', 975, 2),
(39, 'BURKINA FASO', 'CFA Franc BCEAO', 'XOF', 952, 0),
(40, 'BURUNDI', 'Burundi Franc', 'BIF', 108, 0),
(41, 'CABO VERDE', 'Cabo Verde Escudo', 'CVE', 132, 2),
(42, 'CAMBODIA', 'Riel', 'KHR', 116, 2),
(43, 'CAMEROON', 'CFA Franc BEAC', 'XAF', 950, 0),
(44, 'CANADA', 'Canadian Dollar', 'CAD', 124, 2),
(45, 'CAYMAN ISLANDS (THE)', 'Cayman Islands Dollar', 'KYD', 136, 2),
(46, 'CENTRAL AFRICAN REPUBLIC (THE)', 'CFA Franc BEAC', 'XAF', 950, 0),
(47, 'CHAD', 'CFA Franc BEAC', 'XAF', 950, 0),
(48, 'CHILE', 'Chilean Peso', 'CLP', 152, 0),
(49, 'CHILE', 'Unidad de Fomento', 'CLF', 990, 4),
(50, 'CHINA', 'Yuan Renminbi', 'CNY', 156, 2),
(51, 'CHRISTMAS ISLAND', 'Australian Dollar', 'AUD', 36, 2),
(52, 'COCOS (KEELING) ISLANDS (THE)', 'Australian Dollar', 'AUD', 36, 2),
(53, 'COLOMBIA', 'Colombian Peso', 'COP', 170, 2),
(54, 'COLOMBIA', 'Unidad de Valor Real', 'COU', 970, 2),
(55, 'COMOROS (THE)', 'Comoro Franc', 'KMF', 174, 0),
(56, 'CONGO (THE DEMOCRATIC REPUBLIC OF THE)', 'Congolese Franc', 'CDF', 976, 2),
(57, 'CONGO (THE)', 'CFA Franc BEAC', 'XAF', 950, 0),
(58, 'COOK ISLANDS (THE)', 'New Zealand Dollar', 'NZD', 554, 2),
(59, 'COSTA RICA', 'Costa Rican Colon', 'CRC', 188, 2),
(60, 'C??ÂTE D''IVOIRE', 'CFA Franc BCEAO', 'XOF', 952, 0),
(61, 'CROATIA', 'Kuna', 'HRK', 191, 2),
(62, 'CUBA', 'Cuban Peso', 'CUP', 192, 2),
(63, 'CUBA', 'Peso Convertible', 'CUC', 931, 2),
(64, 'CURA??ºAO', 'Netherlands Antillean Guilder', 'ANG', 532, 2),
(65, 'CYPRUS', 'Euro', 'EUR', 978, 2),
(66, 'CZECH REPUBLIC (THE)', 'Czech Koruna', 'CZK', 203, 2),
(67, 'DENMARK', 'Danish Krone', 'DKK', 208, 2),
(68, 'DJIBOUTI', 'Djibouti Franc', 'DJF', 262, 0),
(69, 'DOMINICA', 'East Caribbean Dollar', 'XCD', 951, 2),
(70, 'DOMINICAN REPUBLIC (THE)', 'Dominican Peso', 'DOP', 214, 2),
(71, 'ECUADOR', 'US Dollar', 'USD', 840, 2),
(72, 'EGYPT', 'Egyptian Pound', 'EGP', 818, 2),
(73, 'EL SALVADOR', 'El Salvador Colon', 'SVC', 222, 2),
(74, 'EL SALVADOR', 'US Dollar', 'USD', 840, 2),
(75, 'EQUATORIAL GUINEA', 'CFA Franc BEAC', 'XAF', 950, 0),
(76, 'ERITREA', 'Nakfa', 'ERN', 232, 2),
(77, 'ESTONIA', 'Euro', 'EUR', 978, 2),
(78, 'ETHIOPIA', 'Ethiopian Birr', 'ETB', 230, 2),
(79, 'EUROPEAN UNION', 'Euro', 'EUR', 978, 2),
(80, 'FALKLAND ISLANDS (THE) [MALVINAS]', 'Falkland Islands Pound', 'FKP', 238, 2),
(81, 'FAROE ISLANDS (THE)', 'Danish Krone', 'DKK', 208, 2),
(82, 'FIJI', 'Fiji Dollar', 'FJD', 242, 2),
(83, 'FINLAND', 'Euro', 'EUR', 978, 2),
(84, 'FRANCE', 'Euro', 'EUR', 978, 2),
(85, 'FRENCH GUIANA', 'Euro', 'EUR', 978, 2),
(86, 'FRENCH POLYNESIA', 'CFP Franc', 'XPF', 953, 0),
(87, 'FRENCH SOUTHERN TERRITORIES (THE)', 'Euro', 'EUR', 978, 2),
(88, 'GABON', 'CFA Franc BEAC', 'XAF', 950, 0),
(89, 'GAMBIA (THE)', 'Dalasi', 'GMD', 270, 2),
(90, 'GEORGIA', 'Lari', 'GEL', 981, 2),
(91, 'GERMANY', 'Euro', 'EUR', 978, 2),
(92, 'GHANA', 'Ghana Cedi', 'GHS', 936, 2),
(93, 'GIBRALTAR', 'Gibraltar Pound', 'GIP', 292, 2),
(94, 'GREECE', 'Euro', 'EUR', 978, 2),
(95, 'GREENLAND', 'Danish Krone', 'DKK', 208, 2),
(96, 'GRENADA', 'East Caribbean Dollar', 'XCD', 951, 2),
(97, 'GUADELOUPE', 'Euro', 'EUR', 978, 2),
(98, 'GUAM', 'US Dollar', 'USD', 840, 2),
(99, 'GUATEMALA', 'Quetzal', 'GTQ', 320, 2),
(100, 'GUERNSEY', 'Pound Sterling', 'GBP', 826, 2),
(101, 'GUINEA', 'Guinea Franc', 'GNF', 324, 0),
(102, 'GUINEA-BISSAU', 'CFA Franc BCEAO', 'XOF', 952, 0),
(103, 'GUYANA', 'Guyana Dollar', 'GYD', 328, 2),
(104, 'HAITI', 'Gourde', 'HTG', 332, 2),
(105, 'HAITI', 'US Dollar', 'USD', 840, 2),
(106, 'HEARD ISLAND AND McDONALD ISLANDS', 'Australian Dollar', 'AUD', 36, 2),
(107, 'HOLY SEE (THE)', 'Euro', 'EUR', 978, 2),
(108, 'HONDURAS', 'Lempira', 'HNL', 340, 2),
(109, 'HONG KONG', 'Hong Kong Dollar', 'HKD', 344, 2),
(110, 'HUNGARY', 'Forint', 'HUF', 348, 2),
(111, 'ICELAND', 'Iceland Krona', 'ISK', 352, 0),
(112, 'INDIA', 'Indian Rupee', 'INR', 356, 2),
(113, 'INDONESIA', 'Rupiah', 'IDR', 360, 2),
(114, 'INTERNATIONAL MONETARY FUND (IMF)??í', 'SDR (Special Drawing Right)', 'XDR', 960, NULL),
(115, 'IRAN (ISLAMIC REPUBLIC OF)', 'Iranian Rial', 'IRR', 364, 2),
(116, 'IRAQ', 'Iraqi Dinar', 'IQD', 368, 3),
(117, 'IRELAND', 'Euro', 'EUR', 978, 2),
(118, 'ISLE OF MAN', 'Pound Sterling', 'GBP', 826, 2),
(119, 'ISRAEL', 'New Israeli Sheqel', 'ILS', 376, 2),
(120, 'ITALY', 'Euro', 'EUR', 978, 2),
(121, 'JAMAICA', 'Jamaican Dollar', 'JMD', 388, 2),
(122, 'JAPAN', 'Yen', 'JPY', 392, 0),
(123, 'JERSEY', 'Pound Sterling', 'GBP', 826, 2),
(124, 'JORDAN', 'Jordanian Dinar', 'JOD', 400, 3),
(125, 'KAZAKHSTAN', 'Tenge', 'KZT', 398, 2),
(126, 'KENYA', 'Kenyan Shilling', 'KES', 404, 2),
(127, 'KIRIBATI', 'Australian Dollar', 'AUD', 36, 2),
(128, 'KOREA (THE DEMOCRATIC PEOPLE?ö?ç?ûS REPUBLIC OF)', 'North Korean Won', 'KPW', 408, 2),
(129, 'KOREA (THE REPUBLIC OF)', 'Won', 'KRW', 410, 0),
(130, 'KUWAIT', 'Kuwaiti Dinar', 'KWD', 414, 3),
(131, 'KYRGYZSTAN', 'Som', 'KGS', 417, 2),
(132, 'LAO PEOPLE?ö?ç?ûS DEMOCRATIC REPUBLIC (THE)', 'Kip', 'LAK', 418, 2),
(133, 'LATVIA', 'Euro', 'EUR', 978, 2),
(134, 'LEBANON', 'Lebanese Pound', 'LBP', 422, 2),
(135, 'LESOTHO', 'Loti', 'LSL', 426, 2),
(136, 'LESOTHO', 'Rand', 'ZAR', 710, 2),
(137, 'LIBERIA', 'Liberian Dollar', 'LRD', 430, 2),
(138, 'LIBYA', 'Libyan Dinar', 'LYD', 434, 3),
(139, 'LIECHTENSTEIN', 'Swiss Franc', 'CHF', 756, 2),
(140, 'LITHUANIA', 'Euro', 'EUR', 978, 2),
(141, 'LUXEMBOURG', 'Euro', 'EUR', 978, 2),
(142, 'MACAO', 'Pataca', 'MOP', 446, 2),
(143, 'MACEDONIA (THE FORMER YUGOSLAV REPUBLIC OF)', 'Denar', 'MKD', 807, 2),
(144, 'MADAGASCAR', 'Malagasy Ariary', 'MGA', 969, 2),
(145, 'MALAWI', 'Malawi Kwacha', 'MWK', 454, 2),
(146, 'MALAYSIA', 'Malaysian Ringgit', 'MYR', 458, 2),
(147, 'MALDIVES', 'Rufiyaa', 'MVR', 462, 2),
(148, 'MALI', 'CFA Franc BCEAO', 'XOF', 952, 0),
(149, 'MALTA', 'Euro', 'EUR', 978, 2),
(150, 'MARSHALL ISLANDS (THE)', 'US Dollar', 'USD', 840, 2),
(151, 'MARTINIQUE', 'Euro', 'EUR', 978, 2),
(152, 'MAURITANIA', 'Ouguiya', 'MRO', 478, 2),
(153, 'MAURITIUS', 'Mauritius Rupee', 'MUR', 480, 2),
(154, 'MAYOTTE', 'Euro', 'EUR', 978, 2),
(155, 'MEMBER COUNTRIES OF THE AFRICAN DEVELOPMENT BANK GROUP', 'ADB Unit of Account', 'XUA', 965, NULL),
(156, 'MEXICO', 'Mexican Peso', 'MXN', 484, 2),
(157, 'MEXICO', 'Mexican Unidad de Inversion (UDI)', 'MXV', 979, 2),
(158, 'MICRONESIA (FEDERATED STATES OF)', 'US Dollar', 'USD', 840, 2),
(159, 'MOLDOVA (THE REPUBLIC OF)', 'Moldovan Leu', 'MDL', 498, 2),
(160, 'MONACO', 'Euro', 'EUR', 978, 2),
(161, 'MONGOLIA', 'Tugrik', 'MNT', 496, 2),
(162, 'MONTENEGRO', 'Euro', 'EUR', 978, 2),
(163, 'MONTSERRAT', 'East Caribbean Dollar', 'XCD', 951, 2),
(164, 'MOROCCO', 'Moroccan Dirham', 'MAD', 504, 2),
(165, 'MOZAMBIQUE', 'Mozambique Metical', 'MZN', 943, 2),
(166, 'MYANMAR', 'Kyat', 'MMK', 104, 2),
(167, 'NAMIBIA', 'Namibia Dollar', 'NAD', 516, 2),
(168, 'NAMIBIA', 'Rand', 'ZAR', 710, 2),
(169, 'NAURU', 'Australian Dollar', 'AUD', 36, 2),
(170, 'NEPAL', 'Nepalese Rupee', 'NPR', 524, 2),
(171, 'NETHERLANDS (THE)', 'Euro', 'EUR', 978, 2),
(172, 'NEW CALEDONIA', 'CFP Franc', 'XPF', 953, 0),
(173, 'NEW ZEALAND', 'New Zealand Dollar', 'NZD', 554, 2),
(174, 'NICARAGUA', 'Cordoba Oro', 'NIO', 558, 2),
(175, 'NIGER (THE)', 'CFA Franc BCEAO', 'XOF', 952, 0),
(176, 'NIGERIA', 'Naira', 'NGN', 566, 2),
(177, 'NIUE', 'New Zealand Dollar', 'NZD', 554, 2),
(178, 'NORFOLK ISLAND', 'Australian Dollar', 'AUD', 36, 2),
(179, 'NORTHERN MARIANA ISLANDS (THE)', 'US Dollar', 'USD', 840, 2),
(180, 'NORWAY', 'Norwegian Krone', 'NOK', 578, 2),
(181, 'OMAN', 'Rial Omani', 'OMR', 512, 3),
(182, 'PAKISTAN', 'Pakistan Rupee', 'PKR', 586, 2),
(183, 'PALAU', 'US Dollar', 'USD', 840, 2),
(184, 'PALESTINE, STATE OF', 'No universal currency', '', NULL, NULL),
(185, 'PANAMA', 'Balboa', 'PAB', 590, 2),
(186, 'PANAMA', 'US Dollar', 'USD', 840, 2),
(187, 'PAPUA NEW GUINEA', 'Kina', 'PGK', 598, 2),
(188, 'PARAGUAY', 'Guarani', 'PYG', 600, 0),
(189, 'PERU', 'Sol', 'PEN', 604, 2),
(190, 'PHILIPPINES (THE)', 'Philippine Peso', 'PHP', 608, 2),
(191, 'PITCAIRN', 'New Zealand Dollar', 'NZD', 554, 2),
(192, 'POLAND', 'Zloty', 'PLN', 985, 2),
(193, 'PORTUGAL', 'Euro', 'EUR', 978, 2),
(194, 'PUERTO RICO', 'US Dollar', 'USD', 840, 2),
(195, 'QATAR', 'Qatari Rial', 'QAR', 634, 2),
(196, 'R??½UNION', 'Euro', 'EUR', 978, 2),
(197, 'ROMANIA', 'Romanian Leu', 'RON', 946, 2),
(198, 'RUSSIAN FEDERATION (THE)', 'Russian Ruble', 'RUB', 643, 2),
(199, 'RWANDA', 'Rwanda Franc', 'RWF', 646, 0),
(200, 'SAINT BARTH??½LEMY', 'Euro', 'EUR', 978, 2),
(201, 'SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA', 'Saint Helena Pound', 'SHP', 654, 2),
(202, 'SAINT KITTS AND NEVIS', 'East Caribbean Dollar', 'XCD', 951, 2),
(203, 'SAINT LUCIA', 'East Caribbean Dollar', 'XCD', 951, 2),
(204, 'SAINT MARTIN (FRENCH PART)', 'Euro', 'EUR', 978, 2),
(205, 'SAINT PIERRE AND MIQUELON', 'Euro', 'EUR', 978, 2),
(206, 'SAINT VINCENT AND THE GRENADINES', 'East Caribbean Dollar', 'XCD', 951, 2),
(207, 'SAMOA', 'Tala', 'WST', 882, 2),
(208, 'SAN MARINO', 'Euro', 'EUR', 978, 2),
(209, 'SAO TOME AND PRINCIPE', 'Dobra', 'STD', 678, 2),
(210, 'SAUDI ARABIA', 'Saudi Riyal', 'SAR', 682, 2),
(211, 'SENEGAL', 'CFA Franc BCEAO', 'XOF', 952, 0),
(212, 'SERBIA', 'Serbian Dinar', 'RSD', 941, 2),
(213, 'SEYCHELLES', 'Seychelles Rupee', 'SCR', 690, 2),
(214, 'SIERRA LEONE', 'Leone', 'SLL', 694, 2),
(215, 'SINGAPORE', 'Singapore Dollar', 'SGD', 702, 2),
(216, 'SINT MAARTEN (DUTCH PART)', 'Netherlands Antillean Guilder', 'ANG', 532, 2),
(217, 'SISTEMA UNITARIO DE COMPENSACION REGIONAL DE PAGOS "SUCRE"', 'Sucre', 'XSU', 994, NULL),
(218, 'SLOVAKIA', 'Euro', 'EUR', 978, 2),
(219, 'SLOVENIA', 'Euro', 'EUR', 978, 2),
(220, 'SOLOMON ISLANDS', 'Solomon Islands Dollar', 'SBD', 90, 2),
(221, 'SOMALIA', 'Somali Shilling', 'SOS', 706, 2),
(222, 'SOUTH AFRICA', 'Rand', 'ZAR', 710, 2),
(223, 'SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS', 'No universal currency', '', NULL, NULL),
(224, 'SOUTH SUDAN', 'South Sudanese Pound', 'SSP', 728, 2),
(225, 'SPAIN', 'Euro', 'EUR', 978, 2),
(226, 'SRI LANKA', 'Sri Lanka Rupee', 'LKR', 144, 2),
(227, 'SUDAN (THE)', 'Sudanese Pound', 'SDG', 938, 2),
(228, 'SURINAME', 'Surinam Dollar', 'SRD', 968, 2),
(229, 'SVALBARD AND JAN MAYEN', 'Norwegian Krone', 'NOK', 578, 2),
(230, 'SWAZILAND', 'Lilangeni', 'SZL', 748, 2),
(231, 'SWEDEN', 'Swedish Krona', 'SEK', 752, 2),
(232, 'SWITZERLAND', 'Swiss Franc', 'CHF', 756, 2),
(233, 'SWITZERLAND', 'WIR Euro', 'CHE', 947, 2),
(234, 'SWITZERLAND', 'WIR Franc', 'CHW', 948, 2),
(235, 'SYRIAN ARAB REPUBLIC', 'Syrian Pound', 'SYP', 760, 2),
(236, 'TAIWAN (PROVINCE OF CHINA)', 'New Taiwan Dollar', 'TWD', 901, 2),
(237, 'TAJIKISTAN', 'Somoni', 'TJS', 972, 2),
(238, 'TANZANIA, UNITED REPUBLIC OF', 'Tanzanian Shilling', 'TZS', 834, 2),
(239, 'THAILAND', 'Baht', 'THB', 764, 2),
(240, 'TIMOR-LESTE', 'US Dollar', 'USD', 840, 2),
(241, 'TOGO', 'CFA Franc BCEAO', 'XOF', 952, 0),
(242, 'TOKELAU', 'New Zealand Dollar', 'NZD', 554, 2),
(243, 'TONGA', 'Pa?ö?ç?ûanga', 'TOP', 776, 2),
(244, 'TRINIDAD AND TOBAGO', 'Trinidad and Tobago Dollar', 'TTD', 780, 2),
(245, 'TUNISIA', 'Tunisian Dinar', 'TND', 788, 3),
(246, 'TURKEY', 'Turkish Lira', 'TRY', 949, 2),
(247, 'TURKMENISTAN', 'Turkmenistan New Manat', 'TMT', 934, 2),
(248, 'TURKS AND CAICOS ISLANDS (THE)', 'US Dollar', 'USD', 840, 2),
(249, 'TUVALU', 'Australian Dollar', 'AUD', 36, 2),
(250, 'UGANDA', 'Uganda Shilling', 'UGX', 800, 0),
(251, 'UKRAINE', 'Hryvnia', 'UAH', 980, 2),
(252, 'UNITED ARAB EMIRATES (THE)', 'UAE Dirham', 'AED', 784, 2),
(253, 'UNITED KINGDOM OF GREAT BRITAIN AND NORTHERN IRELAND (THE)', 'Pound Sterling', 'GBP', 826, 2),
(254, 'UNITED STATES MINOR OUTLYING ISLANDS (THE)', 'US Dollar', 'USD', 840, 2),
(255, 'UNITED STATES OF AMERICA (THE)', 'US Dollar', 'USD', 840, 2),
(256, 'UNITED STATES OF AMERICA (THE)', 'US Dollar (Next day)', 'USN', 997, 2),
(257, 'URUGUAY', 'Peso Uruguayo', 'UYU', 858, 2),
(258, 'URUGUAY', 'Uruguay Peso en Unidades Indexadas (URUIURUI)', 'UYI', 940, 0),
(259, 'UZBEKISTAN', 'Uzbekistan Sum', 'UZS', 860, 2),
(260, 'VANUATU', 'Vatu', 'VUV', 548, 0),
(261, 'VENEZUELA (BOLIVARIAN REPUBLIC OF)', 'Bol??ívar', 'VEF', 937, 2),
(262, 'VIET NAM', 'Dong', 'VND', 704, 0),
(263, 'VIRGIN ISLANDS (BRITISH)', 'US Dollar', 'USD', 840, 2),
(264, 'VIRGIN ISLANDS (U.S.)', 'US Dollar', 'USD', 840, 2),
(265, 'WALLIS AND FUTUNA', 'CFP Franc', 'XPF', 953, 0),
(266, 'WESTERN SAHARA', 'Moroccan Dirham', 'MAD', 504, 2),
(267, 'YEMEN', 'Yemeni Rial', 'YER', 886, 2),
(268, 'ZAMBIA', 'Zambian Kwacha', 'ZMW', 967, 2),
(269, 'ZIMBABWE', 'Zimbabwe Dollar', 'ZWL', 932, 2),
(270, 'ZZ01_Bond Markets Unit European_EURCO', 'Bond Markets Unit European Composite Unit (EURCO)', 'XBA', 955, NULL),
(271, 'ZZ02_Bond Markets Unit European_EMU-6', 'Bond Markets Unit European Monetary Unit (E.M.U.-6)', 'XBB', 956, NULL),
(272, 'ZZ03_Bond Markets Unit European_EUA-9', 'Bond Markets Unit European Unit of Account 9 (E.U.A.-9)', 'XBC', 957, NULL),
(273, 'ZZ04_Bond Markets Unit European_EUA-17', 'Bond Markets Unit European Unit of Account 17 (E.U.A.-17)', 'XBD', 958, NULL),
(274, 'ZZ06_Testing_Code', 'Codes specifically reserved for testing purposes', 'XTS', 963, NULL),
(275, 'ZZ07_No_Currency', 'The codes assigned for transactions where no currency is involved', 'XXX', 999, NULL),
(276, 'ZZ08_Gold', 'Gold', 'XAU', 959, NULL),
(277, 'ZZ09_Palladium', 'Palladium', 'XPD', 964, NULL),
(278, 'ZZ10_Platinum', 'Platinum', 'XPT', 962, NULL),
(279, 'ZZ11_Silver', 'Silver', 'XAG', 961, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE IF NOT EXISTS `item` (
  `item_id` bigint(20) NOT NULL,
  `item_code` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  `category_id` int(11) NOT NULL,
  `sub_category_id` int(11) DEFAULT NULL,
  `unit_id` int(11) NOT NULL,
  `reorder_level` int(11) NOT NULL,
  `unit_cost_price` double NOT NULL,
  `unit_retailsale_price` double NOT NULL,
  `unit_wholesale_price` double NOT NULL,
  `is_suspended` varchar(3) NOT NULL,
  `vat_rated` varchar(10) NOT NULL,
  `item_img_url` varchar(100) DEFAULT NULL,
  `add_date` datetime NOT NULL,
  `edit_date` datetime NOT NULL,
  `item_type` varchar(7) NOT NULL DEFAULT 'PRODUCT',
  `currency_code` varchar(10) DEFAULT NULL,
  `is_general` int(1) DEFAULT NULL,
  `asset_type` varchar(50) DEFAULT NULL,
  `is_buy` int(1) DEFAULT '0',
  `is_sale` int(1) DEFAULT '0',
  `is_track` int(1) DEFAULT '0',
  `is_asset` int(1) DEFAULT '0',
  `asset_account_code` varchar(20) DEFAULT NULL,
  `expense_account_code` varchar(20) DEFAULT NULL,
  `is_hire` int(1) DEFAULT NULL,
  `duration_type` varchar(20) DEFAULT NULL,
  `unit_hire_price` double DEFAULT NULL,
  `unit_special_price` double DEFAULT '0',
  `unit_weight` double DEFAULT '0',
  `expense_type` varchar(50) DEFAULT '',
  `alias_name` varchar(50) DEFAULT '',
  `display_alias_name` int(1) DEFAULT '0',
  `is_free` int(1) DEFAULT '0',
  `allow_negative_stock` int(1) DEFAULT '0',
  `specify_size` int(1) DEFAULT '0',
  `size_to_specific_name` int(1) DEFAULT '0',
  PRIMARY KEY (`item_id`),
  KEY `item_code` (`item_code`),
  KEY `is_suspended` (`is_suspended`),
  KEY `item_type` (`item_type`),
  KEY `description` (`description`) USING BTREE,
  KEY `SubCat_to_Item_on_SubCatId` (`sub_category_id`),
  KEY `Cat_to_Item_on_CatId` (`category_id`),
  KEY `Unit_to_Item_on_UnitId2` (`unit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`item_id`, `item_code`, `description`, `category_id`, `sub_category_id`, `unit_id`, `reorder_level`, `unit_cost_price`, `unit_retailsale_price`, `unit_wholesale_price`, `is_suspended`, `vat_rated`, `item_img_url`, `add_date`, `edit_date`, `item_type`, `currency_code`, `is_general`, `asset_type`, `is_buy`, `is_sale`, `is_track`, `is_asset`, `asset_account_code`, `expense_account_code`, `is_hire`, `duration_type`, `unit_hire_price`, `unit_special_price`, `unit_weight`, `expense_type`, `alias_name`, `display_alias_name`, `is_free`, `allow_negative_stock`, `specify_size`, `size_to_specific_name`) VALUES
(241, '', 'PPE Land', 1, NULL, 1, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'PRODUCT', 'UGX', 1, 'LAND', 1, 0, 1, 1, '1-20-000-010', '', 0, NULL, NULL, 0, 0, '', '', 0, 0, 0, 0, 0),
(242, '', 'PPE Buildings', 2, NULL, 1, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'PRODUCT', 'UGX', 1, 'BUILDING', 1, 0, 1, 1, '1-20-000-020', '', 0, NULL, NULL, 0, 0, '', '', 0, 0, 0, 0, 0),
(243, '', 'PPE Machinery & Equipment', 3, NULL, 1, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'PRODUCT', 'UGX', 1, 'MACHINE', 1, 0, 1, 1, '1-20-000-030', '', 0, NULL, NULL, 0, 0, '', '', 0, 0, 0, 0, 0),
(244, '', 'PPE Vehicles', 4, NULL, 1, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'PRODUCT', 'UGX', 1, 'VEHICLE', 1, 0, 1, 1, '1-20-000-040', '', 0, NULL, NULL, 0, 0, '', '', 0, 0, 0, 0, 0),
(245, '', 'PPE Furniture and Fixtures', 5, NULL, 1, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'PRODUCT', 'UGX', 1, 'FURNITURE', 1, 0, 1, 1, '1-20-000-050', '', 0, NULL, NULL, 0, 0, '', '', 0, 0, 0, 0, 0),
(246, '', 'PPE Computer Equipment', 6, NULL, 1, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'PRODUCT', 'UGX', 1, 'COMPUTER', 1, 0, 1, 1, '1-20-000-060', '', 0, NULL, NULL, 0, 0, '', '', 0, 0, 0, 0, 0),
(247, '', 'PPE Other Property, Plant & Equipment', 7, NULL, 1, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'PRODUCT', 'UGX', 1, 'OTHER', 1, 0, 1, 1, '1-20-000-070', '', 0, NULL, NULL, 0, 0, '', '', 0, 0, 0, 0, 0),
(248, '', 'Advertisement', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-000-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(249, '', 'Audit Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-010-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(250, '', 'Bad Debts Written Off', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-020-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(251, '', 'Provision for Bad and Doubtful Debts/ Impairment for credit losses ( for banks)', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-020-020', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(252, '', 'Commission', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-030-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(253, '', 'Computer Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-040-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(254, '', 'Donations', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-050-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(255, '', 'Entertainment', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-060-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(256, '', 'Freight and Transport', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-070-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(257, '', 'Gift Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-080-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(258, '', 'Hotel, Boarding and Lodging Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-090-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(259, '', 'Legal expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-100-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(260, '', 'Power and Fuel', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-110-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(261, '', 'Water', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-110-020', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(262, '', 'Garbage Collection', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-110-030', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(263, '', 'Other Utilities', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-110-040', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(264, '', 'Rent', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-120-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(265, '', 'Rates', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-130-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(266, '', 'Repairs of Building', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-140-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(267, '', 'Repairs of Vehicle', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-140-020', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(268, '', 'Repairs of Machinery', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-140-030', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(269, '', 'Sales Promotion including Publicity ( other than advertisement)', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-150-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(270, '', 'Staff Welfare Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-160-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(271, '', 'Startup cost/ pre- operating expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-170-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(272, '', 'Stationery and printing', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-180-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(273, '', 'Subsistence Allowance', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-190-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(274, '', 'Telephone Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-200-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(275, '', 'Training Expenditure', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-210-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(276, '', 'Travelling Expenses including foreign travelling', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-220-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(277, '', 'Workshop - Conference Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-230-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(278, '', 'Internet Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-240-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(279, '', 'Depriciation', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-250-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(280, '', 'Loss on disposal of assets', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-260-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(281, '', 'Management Fees', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-270-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(282, '', 'Scientific Research Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-280-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(283, '', 'Salaries and wages', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-290-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(284, '', 'Bonus', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-290-020', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(285, '', 'Reimbursement of medical expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-290-030', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(286, '', 'Leave encashment', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-290-040', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(287, '', 'Leave travel benefits', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-290-050', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(288, '', 'Housing allowance/ rent', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-290-060', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(289, '', 'Contribution to retirement fund', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-290-070', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(290, '', 'Contribution to any other fund', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-290-080', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(291, '', 'Any other employment costs', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-290-090', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(292, '', 'Interest Expense', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-300-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(293, '', 'Bank Charges', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-300-020', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(294, '', 'Commitment fees', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-300-030', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(295, '', 'Insurance', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-300-040', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(296, '', 'Realized exchange loss', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-300-050', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(297, '', 'Unrealized exchange loss', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-300-060', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(298, '', 'Claims admitted during the year', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-400-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(299, '', 'Premium returned to insured', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-400-020', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(300, '', 'Reserved for unexpired risk carried forward', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-400-030', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(301, '', 'Agency Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-400-040', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(302, '', 'Other expenses related to short term business', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-400-050', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(303, '', 'Income Tax Expense', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-410-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(304, '', 'Proposed Dividend', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-420-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(305, '', 'Expenses - Loyalties', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-430-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(306, '', 'Other Operating Expenses', 8, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-20-430-020', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(307, '', 'Non-Operating Expenses', 9, NULL, 2, 0, 0, 0, 0, 'No', 'STANDARD', NULL, '2019-02-23 16:35:16', '2019-02-23 16:35:16', 'SERVICE', 'UGX', 1, '', 1, 0, 0, 0, '', '5-30-000-010', 0, NULL, NULL, 0, 0, 'Consumption', '', 0, 0, 0, 0, 0),
(308, '', 'Ridge caps-30', 11, NULL, 3, 0, 0, 16500, 16500, 'No', 'STANDARD', '', '2019-08-12 10:54:52', '2019-08-12 11:32:17', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, NULL, 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(309, '', 'Ridge caps g-28 matt & glossy', 11, NULL, 3, 0, 0, 24000, 24000, 'No', 'STANDARD', '', '2019-08-12 11:01:23', '2019-08-12 11:30:48', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(310, '', 'Roll tops g-28 matt & glossy', 11, NULL, 3, 0, 0, 24000, 24000, 'No', 'STANDARD', '', '2019-08-12 11:31:49', '2019-08-12 11:31:49', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(311, '', 'Roll tops g-30', 11, NULL, 3, 0, 0, 20500, 20500, 'No', 'STANDARD', '', '2019-08-12 11:33:01', '2019-08-12 11:33:01', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(312, '', 'Old valleys g-28 matt', 11, NULL, 3, 0, 0, 20200, 20200, 'No', 'STANDARD', '', '2019-08-12 11:33:50', '2019-08-12 11:33:50', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(313, '', 'Old valleys glossy g-30', 11, NULL, 3, 0, 0, 14000, 14000, 'No', 'STANDARD', '', '2019-08-12 11:34:29', '2019-08-12 11:34:29', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(314, '', 'Gutter valleys glossy g-30', 11, NULL, 3, 0, 0, 19000, 19000, 'No', 'STANDARD', '', '2019-08-12 12:15:53', '2019-08-12 12:15:53', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(315, '', 'Gutter valleys matt g-28', 11, NULL, 3, 0, 0, 28500, 28500, 'No', 'STANDARD', '', '2019-08-12 12:16:51', '2019-08-12 12:16:51', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(316, '', 'Gutter valleys glossy g-28', 11, NULL, 3, 0, 0, 24000, 24000, 'No', 'STANDARD', '', '2019-08-12 12:17:34', '2019-08-12 12:17:34', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(317, '', 'Rubber washers', 11, NULL, 4, 0, 0, 5600, 5600, 'No', 'STANDARD', '', '2019-08-12 12:18:12', '2019-08-12 12:18:12', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(318, '', 'Expanded Mesh-8*2-tiger', 11, NULL, 6, 0, 0, 34000, 34000, 'No', 'STANDARD', '', '2019-08-12 12:19:12', '2019-08-12 12:19:12', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(319, '', 'Roofing nails', 11, NULL, 5, 0, 0, 6500, 6500, 'No', 'STANDARD', '', '2019-08-12 12:20:36', '2019-08-12 12:20:36', 'PRODUCT', 'UGX', NULL, NULL, 1, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(320, '', 'Box profile glossy g-30', 10, NULL, 7, 0, 0, 13500, 13500, 'No', 'STANDARD', '', '2019-08-12 12:40:57', '2019-08-13 12:19:48', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 1, 1),
(321, '', 'Box profile glossy g-28', 10, NULL, 7, 0, 0, 18000, 18000, 'No', 'STANDARD', '', '2019-08-12 12:41:47', '2019-08-12 12:43:14', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(322, '', 'Box profile matt g-28', 10, NULL, 7, 0, 0, 22000, 22000, 'No', 'STANDARD', '', '2019-08-12 12:43:01', '2019-08-12 12:43:01', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(323, '', 'Roman long tile glossy g-30', 13, NULL, 7, 0, 0, 19500, 19500, 'No', 'STANDARD', '', '2019-08-12 12:45:13', '2019-08-12 12:45:13', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(324, '', 'Roman long tile glossy g-28', 13, NULL, 7, 0, 0, 21000, 21000, 'No', 'STANDARD', '', '2019-08-12 12:45:59', '2019-08-12 12:45:59', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(325, '', 'Roman long tile matt g-28', 13, NULL, 7, 0, 0, 23000, 23000, 'No', 'STANDARD', '', '2019-08-12 12:47:04', '2019-08-12 12:47:04', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(326, '', 'Zeetile profile glossy g-28', 15, NULL, 7, 0, 0, 21000, 21000, 'No', 'STANDARD', '', '2019-08-12 13:20:33', '2019-08-12 13:20:33', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(327, '', 'Zeetile profile glossy g-30', 15, NULL, 7, 0, 0, 19500, 15000, 'No', 'STANDARD', '', '2019-08-12 13:24:23', '2019-08-12 13:24:23', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(328, '', 'Zeetile profile matt g-28', 15, NULL, 7, 0, 0, 22333.34, 22333.34, 'No', 'STANDARD', '', '2019-08-12 13:25:34', '2019-08-12 13:25:46', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(329, '', 'Brick tile profile glossy g-28', 14, NULL, 7, 0, 0, 21000, 21000, 'No', 'STANDARD', '', '2019-08-12 13:26:34', '2019-08-12 13:26:34', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(330, '', 'Brick tile profile glossy g-30', 14, NULL, 7, 0, 0, 19500, 19500, 'No', 'STANDARD', '', '2019-08-12 13:27:19', '2019-08-12 13:27:19', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(331, '', 'Brick tile profile matt g-28', 14, NULL, 7, 0, 0, 21966.7, 21966.7, 'No', 'STANDARD', '', '2019-08-12 13:28:10', '2019-08-12 13:28:20', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(332, '', 'Corrugated matt g-28', 12, NULL, 7, 0, 0, 22000, 22000, 'No', 'STANDARD', '', '2019-08-12 13:28:59', '2019-08-12 13:28:59', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(333, '', 'Corrugated glossy g-28', 12, NULL, 7, 0, 0, 18000, 18000, 'No', 'STANDARD', '', '2019-08-12 13:29:49', '2019-08-12 13:29:49', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(334, '', 'Corrugated glossy g-30', 12, NULL, 7, 0, 0, 13500, 13500, 'No', 'STANDARD', '', '2019-08-12 13:30:33', '2019-08-12 13:30:33', 'PRODUCT', 'UGX', 1, NULL, 0, 1, 1, 0, NULL, NULL, 0, '', 0, 0, 0, '', '', 0, 0, 0, 0, 0),
(335, '', '180629A05 Gauge 30 2256 MTRS DARK BLUE GLOSSY 4174 KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 13:42:36', '2019-08-12 14:25:11', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0),
(336, '', '180628B103 Gauge 28 1740 MTRS ORANGE MATTE 4254KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 13:47:40', '2019-08-12 13:48:56', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0),
(337, '', '181114B19 Gauge 28 1768 Mtrs CHARCOAL GREY MATTE 4210 KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 13:51:02', '2019-08-12 13:51:02', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0),
(338, '', '181109B37B Gauge 28 1623 mtrs BRICK RED GLOSSY 3906 KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 13:53:40', '2019-08-12 13:53:40', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0),
(339, '', '5181106B28A Gauge 28 1817 mtrs TILE RED MATTE 4035 KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 13:55:37', '2019-08-12 13:55:37', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0),
(340, '', '171108237 Gauge 28 1500 Mtrs ORANGE GLOSSY 3642 KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 14:11:25', '2019-08-12 14:11:25', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0),
(341, '', '181109A32 Gauge 20 1597 mtrs DARK BLUE GLOSSY 3812 KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 14:18:46', '2019-08-12 14:18:46', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0),
(342, '', '2A190316-59 Gauge 30 2478 Mtrs ORANGE GLOSSY 3880 KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 14:21:56', '2019-08-12 14:21:56', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0),
(343, '', '180629A06 Gauge 30 2203 TILE RED GLOSSY 4058 KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 14:23:15', '2019-08-12 14:23:15', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0),
(344, '', '181109A30 28 1559 MTRS JUNGLE GREEN GLOSSY 3746 KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 14:26:48', '2019-08-12 14:26:48', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0),
(345, '', '180629A09 Gauge 30 2256 mtrs JUNGLE GREEN GLOSSY 4174 KGS', 16, NULL, 7, 0, 0, 0, 0, 'No', 'STANDARD', '', '2019-08-12 14:28:17', '2019-08-12 14:28:17', 'PRODUCT', 'UGX', NULL, NULL, 1, 0, 1, 0, NULL, '5-10-000-070', 0, '', 0, 0, 0, 'Raw Material', '', 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `item_combination`
--

CREATE TABLE IF NOT EXISTS `item_combination` (
  `item_combination_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_item_id` bigint(20) NOT NULL,
  `child_item_id` bigint(20) NOT NULL,
  `child_qty` double NOT NULL,
  PRIMARY KEY (`item_combination_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `item_location`
--

CREATE TABLE IF NOT EXISTS `item_location` (
  `item_location_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `location_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  PRIMARY KEY (`item_location_id`),
  KEY `Location_to_ItemLocation` (`location_id`),
  KEY `Item_to_ItemLocation` (`item_id`),
  KEY `u_itemloc_loc_item` (`location_id`,`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `item_map`
--

CREATE TABLE IF NOT EXISTS `item_map` (
  `item_map_id` bigint(20) NOT NULL,
  `big_item_id` bigint(20) NOT NULL,
  `small_item_id` bigint(20) NOT NULL,
  `fraction_qty` double NOT NULL,
  `position` int(11) NOT NULL,
  `map_group_id` bigint(20) NOT NULL,
  PRIMARY KEY (`item_map_id`),
  UNIQUE KEY `big_item_id` (`big_item_id`),
  UNIQUE KEY `small_item_id` (`small_item_id`),
  KEY `Item_to_ItemMap_on_BigItemId` (`big_item_id`),
  KEY `Item_to_ItemMap_on_SmallItemId` (`small_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `item_production_map`
--

CREATE TABLE IF NOT EXISTS `item_production_map` (
  `item_production_map_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `output_item_id` bigint(20) NOT NULL,
  `input_item_id` bigint(20) NOT NULL,
  `input_qty` double NOT NULL,
  PRIMARY KEY (`item_production_map_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `item_warranty`
--

CREATE TABLE IF NOT EXISTS `item_warranty` (
  `item_warranty_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_warranty_name` varchar(50) NOT NULL,
  `item_warranty_desc` varchar(250) NOT NULL,
  `duration_months` int(3) DEFAULT NULL,
  PRIMARY KEY (`item_warranty_id`),
  UNIQUE KEY `item_warranty_name` (`item_warranty_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `location_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) NOT NULL,
  `location_name` varchar(20) NOT NULL,
  PRIMARY KEY (`location_id`),
  UNIQUE KEY `Unique_store_location` (`store_id`,`location_name`),
  KEY `Store_to_Location` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `login_session`
--

CREATE TABLE IF NOT EXISTS `login_session` (
  `login_session_id` int(11) NOT NULL,
  `user_detail_id` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `session_id` varchar(100) NOT NULL,
  `add_date` datetime NOT NULL,
  `remote_ip` varchar(50) NOT NULL,
  `remote_host` varchar(50) NOT NULL,
  `remote_user` varchar(50) NOT NULL,
  PRIMARY KEY (`login_session_id`),
  KEY `Store_to_LoginSession` (`store_id`),
  KEY `UserDetail_to_LoginSession` (`user_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `parameter_list`
--

CREATE TABLE IF NOT EXISTS `parameter_list` (
  `parameter_list_id` int(11) NOT NULL AUTO_INCREMENT,
  `context` varchar(50) NOT NULL,
  `parameter_name` varchar(50) NOT NULL,
  `parameter_value` varchar(250) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`parameter_list_id`),
  UNIQUE KEY `UniqueContextParameter` (`context`,`parameter_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=39 ;

--
-- Dumping data for table `parameter_list`
--

INSERT INTO `parameter_list` (`parameter_list_id`, `context`, `parameter_name`, `parameter_value`, `description`, `store_id`) VALUES
(1, 'MODULE', 'HIRE_MODULE_ON', '0', NULL, NULL),
(2, 'CURRENCY', 'DEFAULT_CURRENCY_CODE', 'UGX', NULL, NULL),
(3, 'DURATION', 'DEFAULT_DURATION_TYPE', 'Week', NULL, NULL),
(4, 'COMPANY_SETTING', 'BOX_ADDRESS', 'P.O. Box 28537, Kampala', NULL, NULL),
(5, 'COMPANY_SETTING', 'MOBILE_NUMBER', '', NULL, NULL),
(6, 'COMPANY_SETTING', 'VAT_NUMBER', '', NULL, NULL),
(7, 'SYSTEM', 'SYSTEM_NAME', 'WINGERsoft Technologies Ltd', NULL, NULL),
(8, 'SYSTEM', 'SYSTEM_VERSION', '5.0', NULL, NULL),
(9, 'SYSTEM', 'SYSTEM_NAME_CLIENT', 'SalesManager', NULL, NULL),
(10, 'ITEM', 'DEFAULT_MEASURE_UNIT', 'Kg', NULL, NULL),
(11, 'COMPANY_SETTING', 'ACC_DETAIL1', 'DFCU BANK USD 02181021397532 | Capital Shoppers Branch', NULL, NULL),
(12, 'COMPANY_SETTING', 'ACC_DETAIL2', 'DFCU BANK UGX 01181024362184 | Capital Shoppers Branch', NULL, NULL),
(13, 'COMPANY_SETTING', 'PHYSICAL_ADDRESS1', '', '', 0),
(14, 'COMPANY_SETTING', 'PHYSICAL_ADDRESS2', '', NULL, NULL),
(15, 'COMPANY_SETTING', 'PAYEE_NAME', 'ROYAL MABATI UGANDA LTD', '', 0),
(16, 'COMPANY_SETTING', 'SHOW_GEN_ITEM_NAME', '1', '', 0),
(17, 'COMPANY_SETTING', 'LIST_ITEMS_APPEND', '1', NULL, NULL),
(18, 'LOCALE', 'LANGUAGE_CODE', 'en', NULL, NULL),
(19, 'LOCALE', 'COUNTRY_CODE', 'US', NULL, NULL),
(20, 'ORDER', 'DEFAULT_DELIVERY_MODE', 'Take Out', NULL, NULL),
(21, 'ORDER', 'DEFAULT_ORDER_TO_STORE', '1', NULL, NULL),
(22, 'ORDER', 'USER_CODE_NEEDED', '0', NULL, NULL),
(23, 'IMAGE', 'ITEM_IMAGE_BASE_URL', 'http://localhost:9090/images', NULL, NULL),
(24, 'IMAGE', 'ITEM_IMAGE_LOCAL_LOCATION', 'C:\\ApacheSoftwareFoundation\\Tomcat80\\webapps\\images', NULL, NULL),
(25, 'PRODUCTION', 'CALC_OUTPUT_UNIT_COST_FROM_INPUT', '1', NULL, NULL),
(26, 'ORDER', 'LOCATION_NEEDED', '1', NULL, NULL),
(27, 'EMAIL', 'HOST', 'mail.wingersoftweb.com', NULL, NULL),
(28, 'EMAIL', 'PORT', '587', NULL, NULL),
(29, 'EMAIL', 'SSL_FLAG', '0', NULL, NULL),
(30, 'EMAIL', 'USERNAME', 'sales@wingersoftweb.com', NULL, NULL),
(31, 'EMAIL', 'PASSWORD', 'Z4NoD9Txm1y3c8D9N4P_T3BDL2Lnq5qeH4kss6eUZ9qcm3B5', NULL, NULL),
(32, 'EMAIL', 'FROM_ADDRESS', 'Sales<sales@wingersoft.co.ug>', NULL, NULL),
(33, 'EMAIL', 'SEND_BATCH_SIZE', '3', NULL, NULL),
(34, 'GENERAL_NAME', 'NEW_LINE_EACH_NAME', '1', NULL, NULL),
(35, 'GENERAL_NAME', 'LINES_BTN_NAMES', '1', NULL, NULL),
(36, 'CURRENCY', 'NON_CURRENCY_TRANS_TYPES', '4,7,9,12', NULL, NULL),
(37, 'COMPANY_SETTING', 'FOOTER_MESSAGE', '', '', 0),
(38, 'COMPANY_SETTING', 'ORDER_VERSION', '0', '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `pay`
--

CREATE TABLE IF NOT EXISTS `pay` (
  `pay_id` bigint(20) NOT NULL,
  `pay_date` date NOT NULL,
  `paid_amount` double NOT NULL,
  `pay_method_id` int(11) NOT NULL,
  `add_user_detail_id` int(11) NOT NULL,
  `edit_user_detail_id` int(11) DEFAULT NULL,
  `add_date` datetime NOT NULL,
  `edit_date` datetime DEFAULT NULL,
  `points_spent` double NOT NULL,
  `points_spent_amount` double NOT NULL,
  `delete_pay_id` bigint(20) DEFAULT NULL,
  `pay_ref_no` varchar(100) DEFAULT NULL,
  `pay_category` varchar(100) DEFAULT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `acc_child_account_id2` int(11) DEFAULT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `status_desc` varchar(100) DEFAULT NULL,
  `pay_type_id` int(11) DEFAULT NULL,
  `pay_reason_id` int(11) DEFAULT NULL,
  `principal_amount` double DEFAULT NULL,
  `interest_amount` double DEFAULT NULL,
  `pay_number` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pay_id`),
  KEY `PayMeth_to_Pay_on_PayMethId` (`pay_method_id`),
  KEY `UserD_to_Pay_on_AddUserId` (`add_user_detail_id`),
  KEY `UserD_to_Pay_on_EditUserId` (`edit_user_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pay_hist`
--

CREATE TABLE IF NOT EXISTS `pay_hist` (
  `pay_hist_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hist_flag` varchar(10) NOT NULL,
  `hist_add_date` datetime NOT NULL,
  `pay_id` bigint(20) NOT NULL,
  `pay_date` date NOT NULL,
  `paid_amount` double NOT NULL,
  `pay_method_id` int(11) NOT NULL,
  `add_user_detail_id` int(11) NOT NULL,
  `edit_user_detail_id` int(11) DEFAULT NULL,
  `add_date` datetime NOT NULL,
  `edit_date` datetime DEFAULT NULL,
  `points_spent` double NOT NULL,
  `points_spent_amount` double NOT NULL,
  `delete_pay_id` bigint(20) DEFAULT NULL,
  `pay_ref_no` varchar(100) DEFAULT NULL,
  `pay_category` varchar(100) DEFAULT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `acc_child_account_id2` int(11) DEFAULT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `status_desc` varchar(100) DEFAULT NULL,
  `pay_type_id` int(11) DEFAULT NULL,
  `pay_reason_id` int(11) DEFAULT NULL,
  `principal_amount` double DEFAULT NULL,
  `interest_amount` double DEFAULT NULL,
  `pay_number` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pay_hist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `pay_method`
--

CREATE TABLE IF NOT EXISTS `pay_method` (
  `pay_method_id` int(11) NOT NULL,
  `pay_method_name` varchar(50) NOT NULL,
  `display_order` int(1) DEFAULT NULL,
  `is_active` int(1) DEFAULT NULL,
  `is_deleted` int(1) DEFAULT NULL,
  `is_default` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`pay_method_id`),
  UNIQUE KEY `pay_method_name` (`pay_method_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='the different payment methods such as cash, checque, etc';

--
-- Dumping data for table `pay_method`
--

INSERT INTO `pay_method` (`pay_method_id`, `pay_method_name`, `display_order`, `is_active`, `is_deleted`, `is_default`) VALUES
(1, 'CASH', 1, 1, 0, 1),
(2, 'MM', 4, 1, 0, 0),
(3, 'CHEQUE', 3, 1, 0, 0),
(4, 'EFT', 5, 1, 0, 0),
(5, 'BANK', 2, 1, 0, 0),
(6, 'PREPAID INCOME', 6, 1, 0, 0),
(7, 'PREPAID EXPENSE', 7, 1, 0, 0),
(8, 'RECEIVABLE ACC', 8, 1, 0, 0),
(11, 'PETTY CASH', 8, 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `pay_trans`
--

CREATE TABLE IF NOT EXISTS `pay_trans` (
  `pay_trans_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pay_id` bigint(20) NOT NULL,
  `trans_paid_amount` double NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `transaction_number` varchar(50) DEFAULT NULL,
  `transaction_type_id` int(11) DEFAULT NULL,
  `transaction_reason_id` int(11) DEFAULT NULL,
  `account_code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pay_trans_id`),
  KEY `FKpay_trans264324` (`pay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `pay_trans_hist`
--

CREATE TABLE IF NOT EXISTS `pay_trans_hist` (
  `pay_trans_hist_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pay_hist_id` bigint(20) NOT NULL,
  `pay_trans_id` bigint(11) NOT NULL,
  `pay_id` bigint(20) NOT NULL,
  `trans_paid_amount` double NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `transaction_number` varchar(50) DEFAULT NULL,
  `transaction_type_id` int(11) DEFAULT NULL,
  `transaction_reason_id` int(11) DEFAULT NULL,
  `account_code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pay_trans_hist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `region`
--

CREATE TABLE IF NOT EXISTS `region` (
  `region_id` int(11) NOT NULL AUTO_INCREMENT,
  `region_name` varchar(100) DEFAULT NULL,
  `country_id` int(11) NOT NULL,
  PRIMARY KEY (`region_id`),
  UNIQUE KEY `unique_country_region` (`region_id`,`country_id`),
  KEY `FKregion870345` (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `salary_deduction`
--

CREATE TABLE IF NOT EXISTS `salary_deduction` (
  `salary_deduction_id` int(11) NOT NULL AUTO_INCREMENT,
  `transactor_id` bigint(20) NOT NULL,
  `perc` double DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `account_code` varchar(20) DEFAULT NULL,
  `deduction_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`salary_deduction_id`),
  KEY `FKsalary_ded739898` (`transactor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `site`
--

CREATE TABLE IF NOT EXISTS `site` (
  `site_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transactor_id` bigint(20) DEFAULT NULL,
  `site_name` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `district` varchar(100) DEFAULT NULL,
  `village` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `snapshot_stock_value`
--

CREATE TABLE IF NOT EXISTS `snapshot_stock_value` (
  `snapshot_stock_value_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `snapshot_no` int(11) NOT NULL,
  `snapshot_date` datetime NOT NULL,
  `acc_period_id` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `batchno` varchar(100) DEFAULT NULL,
  `code_specific` varchar(50) DEFAULT NULL,
  `desc_specific` varchar(100) DEFAULT NULL,
  `currency_code` varchar(10) NOT NULL,
  `currentqty` double NOT NULL,
  `unit_cost_price` double NOT NULL,
  `cp_value` double NOT NULL,
  `wp_value` double NOT NULL,
  `rp_value` double NOT NULL,
  PRIMARY KEY (`snapshot_stock_value_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `snapshot_xrate`
--

CREATE TABLE IF NOT EXISTS `snapshot_xrate` (
  `snapshot_xrate_id` int(11) NOT NULL AUTO_INCREMENT,
  `snapshot_no` int(11) NOT NULL,
  `snapshot_date` datetime NOT NULL,
  `acc_period_id` int(11) NOT NULL,
  `local_currency_id` int(11) NOT NULL,
  `foreign_currency_id` int(11) NOT NULL,
  `local_currency_code` varchar(10) NOT NULL,
  `foreign_currency_code` varchar(10) NOT NULL,
  `buying` double NOT NULL DEFAULT '1',
  `selling` double NOT NULL DEFAULT '1',
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  PRIMARY KEY (`snapshot_xrate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `stage_points_transaction`
--

CREATE TABLE IF NOT EXISTS `stage_points_transaction` (
  `stage_points_transaction_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `points_card_id` bigint(20) NOT NULL,
  `transaction_date` date NOT NULL,
  `points_awarded` double NOT NULL,
  `points_spent` double NOT NULL,
  `transaction_id` bigint(20) NOT NULL,
  `trans_branch_id` int(11) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_user` varchar(100) NOT NULL,
  `edit_date` datetime NOT NULL,
  `edit_user` varchar(100) NOT NULL,
  `points_spent_amount` double NOT NULL,
  PRIMARY KEY (`stage_points_transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE IF NOT EXISTS `stock` (
  `stock_id` bigint(20) NOT NULL,
  `store_id` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `batchno` varchar(100) NOT NULL,
  `currentqty` double NOT NULL,
  `item_mnf_date` date DEFAULT NULL,
  `item_exp_date` date DEFAULT NULL,
  `unit_cost` double DEFAULT NULL,
  `code_specific` varchar(50) NOT NULL DEFAULT '',
  `desc_specific` varchar(100) NOT NULL DEFAULT '',
  `desc_more` varchar(250) DEFAULT NULL,
  `warranty_desc` varchar(150) DEFAULT NULL,
  `warranty_expiry_date` date DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `dep_start_date` date DEFAULT NULL,
  `dep_method_id` int(11) DEFAULT NULL,
  `dep_rate` double DEFAULT NULL,
  `average_method_id` int(11) DEFAULT NULL,
  `effective_life` int(3) DEFAULT NULL,
  `asset_status_id` int(11) DEFAULT NULL,
  `asset_status_desc` varchar(100) DEFAULT NULL,
  `account_code` varchar(20) DEFAULT NULL,
  `residual_value` double DEFAULT NULL,
  `qty_damage` double DEFAULT '0',
  PRIMARY KEY (`stock_id`),
  UNIQUE KEY `u_stock_store_item_batch_specific` (`store_id`,`item_id`,`batchno`,`code_specific`,`desc_specific`),
  KEY `Store_to_Stock_on_StoreId` (`store_id`),
  KEY `Item_to_Stock_on_ItemId` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stock_id`, `store_id`, `item_id`, `batchno`, `currentqty`, `item_mnf_date`, `item_exp_date`, `unit_cost`, `code_specific`, `desc_specific`, `desc_more`, `warranty_desc`, `warranty_expiry_date`, `purchase_date`, `dep_start_date`, `dep_method_id`, `dep_rate`, `average_method_id`, `effective_life`, `asset_status_id`, `asset_status_desc`, `account_code`, `residual_value`, `qty_damage`) VALUES
(1, 1, 245, '', 1, NULL, NULL, 100000, '', 'Office Chair', '', NULL, NULL, '2019-06-06', '2019-06-06', 1, 0, NULL, 1, 1, '', '1-20-000-050', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `stock_out`
--

CREATE TABLE IF NOT EXISTS `stock_out` (
  `stock_out_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `batchno` varchar(100) NOT NULL,
  `code_specific` varchar(50) NOT NULL DEFAULT '',
  `desc_specific` varchar(100) NOT NULL DEFAULT '',
  `transactor_id` bigint(20) NOT NULL,
  `site_id` bigint(20) NOT NULL,
  `transaction_id` bigint(20) NOT NULL,
  `qty_out` double NOT NULL,
  PRIMARY KEY (`stock_out_id`),
  UNIQUE KEY `UniqueStockOut` (`store_id`,`item_id`,`batchno`,`code_specific`,`desc_specific`,`transactor_id`,`site_id`,`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `store`
--

CREATE TABLE IF NOT EXISTS `store` (
  `store_id` int(11) NOT NULL,
  `store_name` varchar(20) NOT NULL,
  PRIMARY KEY (`store_id`),
  UNIQUE KEY `store_name` (`store_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `store`
--

INSERT INTO `store` (`store_id`, `store_name`) VALUES
(1, 'HEAD OFFICE');

-- --------------------------------------------------------

--
-- Table structure for table `sub_category`
--

CREATE TABLE IF NOT EXISTS `sub_category` (
  `sub_category_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `sub_category_name` varchar(50) NOT NULL,
  PRIMARY KEY (`sub_category_id`),
  UNIQUE KEY `sub_category_name` (`sub_category_name`),
  KEY `Cat_to_SubCat_on_CatId` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `town`
--

CREATE TABLE IF NOT EXISTS `town` (
  `town_id` int(11) NOT NULL AUTO_INCREMENT,
  `town_name` varchar(100) NOT NULL,
  `district_id` int(11) NOT NULL,
  PRIMARY KEY (`town_id`),
  UNIQUE KEY `unique_district_town` (`town_id`,`district_id`),
  KEY `district_town` (`district_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE IF NOT EXISTS `transaction` (
  `transaction_id` bigint(20) NOT NULL,
  `transaction_date` date NOT NULL,
  `store_id` int(11) NOT NULL,
  `store2_id` int(11) DEFAULT NULL,
  `transactor_id` bigint(20) DEFAULT NULL,
  `transaction_type_id` int(11) NOT NULL,
  `transaction_reason_id` int(11) NOT NULL,
  `sub_total` double NOT NULL,
  `total_trade_discount` double NOT NULL,
  `total_vat` double NOT NULL,
  `cash_discount` double DEFAULT NULL,
  `grand_total` double NOT NULL,
  `transaction_ref` varchar(100) DEFAULT NULL,
  `transaction_comment` varchar(255) DEFAULT NULL,
  `add_user_detail_id` int(11) NOT NULL,
  `add_date` datetime NOT NULL,
  `edit_user_detail_id` int(11) DEFAULT NULL,
  `edit_date` datetime DEFAULT NULL,
  `points_awarded` double NOT NULL,
  `card_number` varchar(10) DEFAULT NULL,
  `total_std_vatable_amount` double NOT NULL,
  `total_zero_vatable_amount` double NOT NULL,
  `total_exempt_vatable_amount` double NOT NULL,
  `vat_perc` double NOT NULL,
  `amount_tendered` double DEFAULT NULL,
  `change_amount` double DEFAULT NULL,
  `is_cash_discount_vat_liable` varchar(3) DEFAULT NULL,
  `total_profit_margin` double NOT NULL DEFAULT '0',
  `transaction_user_detail_id` int(11) DEFAULT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `scheme_transactor_id` bigint(20) DEFAULT NULL,
  `princ_scheme_member` varchar(100) DEFAULT NULL,
  `scheme_card_number` varchar(100) DEFAULT NULL,
  `transaction_number` varchar(50) NOT NULL,
  `delivery_date` date DEFAULT NULL,
  `delivery_address` varchar(250) DEFAULT NULL,
  `pay_terms` varchar(250) DEFAULT NULL,
  `terms_conditions` varchar(250) DEFAULT NULL,
  `authorised_by_user_detail_id` int(11) DEFAULT NULL,
  `authorise_date` date DEFAULT NULL,
  `pay_due_date` date DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `duration_type` varchar(20) DEFAULT NULL,
  `duration_value` double DEFAULT NULL,
  `site_id` bigint(20) DEFAULT '0',
  `transactor_rep` varchar(100) DEFAULT NULL,
  `transactor_vehicle` varchar(20) DEFAULT NULL,
  `transactor_driver` varchar(100) DEFAULT NULL,
  `location_id` bigint(20) DEFAULT NULL,
  `status_code` varchar(20) DEFAULT NULL,
  `status_date` datetime DEFAULT NULL,
  `delivery_mode` varchar(20) DEFAULT 'Take Out',
  `is_processed` int(1) DEFAULT '1',
  `is_paid` int(1) DEFAULT '1',
  `is_cancel` int(1) DEFAULT '0',
  `is_invoiced` int(1) DEFAULT '0',
  PRIMARY KEY (`transaction_id`),
  KEY `TransType_to_Trans_on_TransTypeId` (`transaction_type_id`),
  KEY `TransReas_to_Trans_on_TransReasId` (`transaction_reason_id`),
  KEY `Transactor_to_Trans_on_TransactorId` (`transactor_id`),
  KEY `UserD_to_Trans_on_AddUserId` (`add_user_detail_id`),
  KEY `UserD_to_Trans_on_EditUserId` (`edit_user_detail_id`),
  KEY `Store_to_Trans_on_StoreId` (`store_id`),
  KEY `Store_to_Trans_on_Store2Id` (`store2_id`),
  KEY `transactor_trans_bill` (`bill_transactor_id`),
  KEY `tractor_traction_scheme` (`scheme_transactor_id`),
  KEY `user_trans_aauthorisedby` (`authorised_by_user_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_approve`
--

CREATE TABLE IF NOT EXISTS `transaction_approve` (
  `transaction_approve_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transaction_id` bigint(20) NOT NULL,
  `function_name` varchar(20) NOT NULL,
  `user_detail_id` int(11) NOT NULL,
  `approve_date` datetime NOT NULL,
  PRIMARY KEY (`transaction_approve_id`),
  KEY `user_detail_to_transaction_approve_on_userdetailid` (`user_detail_id`),
  KEY `trans_to_transapprove_on_trans_id` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_deleted`
--

CREATE TABLE IF NOT EXISTS `transaction_deleted` (
  `transaction_id` bigint(20) NOT NULL,
  `transaction_date` date NOT NULL,
  `store_id` int(11) NOT NULL,
  `store2_id` int(11) DEFAULT NULL,
  `transactor_id` bigint(20) DEFAULT NULL,
  `transaction_type_id` int(11) NOT NULL,
  `transaction_reason_id` int(11) NOT NULL,
  `sub_total` double NOT NULL,
  `total_trade_discount` double NOT NULL,
  `total_vat` double NOT NULL,
  `cash_discount` double DEFAULT NULL,
  `grand_total` double NOT NULL,
  `transaction_ref` varchar(100) DEFAULT NULL,
  `transaction_comment` varchar(255) DEFAULT NULL,
  `add_user_detail_id` int(11) NOT NULL,
  `add_date` datetime NOT NULL,
  `edit_user_detail_id` int(11) DEFAULT NULL,
  `edit_date` datetime DEFAULT NULL,
  `points_awarded` double NOT NULL,
  `card_number` varchar(10) DEFAULT NULL,
  `total_std_vatable_amount` double NOT NULL,
  `total_zero_vatable_amount` double NOT NULL,
  `total_exempt_vatable_amount` double NOT NULL,
  `vat_perc` double NOT NULL,
  `amount_tendered` double DEFAULT NULL,
  `change_amount` double DEFAULT NULL,
  `is_cash_discount_vat_liable` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_hist`
--

CREATE TABLE IF NOT EXISTS `transaction_hist` (
  `transaction_hist_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hist_flag` varchar(10) NOT NULL,
  `hist_add_date` datetime NOT NULL,
  `transaction_id` bigint(20) NOT NULL,
  `transaction_date` date NOT NULL,
  `store_id` int(11) NOT NULL,
  `store2_id` int(11) DEFAULT NULL,
  `transactor_id` bigint(20) DEFAULT NULL,
  `transaction_type_id` int(11) NOT NULL,
  `transaction_reason_id` int(11) NOT NULL,
  `sub_total` double NOT NULL,
  `total_trade_discount` double NOT NULL,
  `total_vat` double NOT NULL,
  `cash_discount` double DEFAULT NULL,
  `grand_total` double NOT NULL,
  `transaction_ref` varchar(100) DEFAULT NULL,
  `transaction_comment` varchar(255) DEFAULT NULL,
  `add_user_detail_id` int(11) NOT NULL,
  `add_date` datetime NOT NULL,
  `edit_user_detail_id` int(11) DEFAULT NULL,
  `edit_date` datetime DEFAULT NULL,
  `points_awarded` double NOT NULL,
  `card_number` varchar(10) DEFAULT NULL,
  `total_std_vatable_amount` double NOT NULL,
  `total_zero_vatable_amount` double NOT NULL,
  `total_exempt_vatable_amount` double NOT NULL,
  `vat_perc` double NOT NULL,
  `amount_tendered` double DEFAULT NULL,
  `change_amount` double DEFAULT NULL,
  `is_cash_discount_vat_liable` varchar(3) DEFAULT NULL,
  `total_profit_margin` double NOT NULL DEFAULT '0',
  `transaction_user_detail_id` int(11) DEFAULT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `scheme_transactor_id` bigint(20) DEFAULT NULL,
  `princ_scheme_member` varchar(100) DEFAULT NULL,
  `scheme_card_number` varchar(100) DEFAULT NULL,
  `transaction_number` varchar(50) DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `delivery_address` varchar(250) DEFAULT NULL,
  `pay_terms` varchar(250) DEFAULT NULL,
  `terms_conditions` varchar(250) DEFAULT NULL,
  `authorised_by_user_detail_id` int(11) DEFAULT NULL,
  `authorise_date` date DEFAULT NULL,
  `pay_due_date` date DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `duration_type` varchar(20) DEFAULT NULL,
  `site_id` bigint(20) DEFAULT NULL,
  `transactor_rep` varchar(50) DEFAULT NULL,
  `transactor_vehicle` varchar(20) DEFAULT NULL,
  `transactor_driver` varchar(50) DEFAULT NULL,
  `duration_value` double DEFAULT NULL,
  `location_id` bigint(20) DEFAULT NULL,
  `status_code` varchar(20) DEFAULT NULL,
  `status_date` datetime DEFAULT NULL,
  `delivery_mode` varchar(20) DEFAULT 'Take Out',
  `is_processed` int(1) DEFAULT '1',
  `is_paid` int(1) DEFAULT '1',
  `is_cancel` int(1) DEFAULT '0',
  `is_invoiced` int(1) DEFAULT '0',
  PRIMARY KEY (`transaction_hist_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `transaction_hist`
--

INSERT INTO `transaction_hist` (`transaction_hist_id`, `hist_flag`, `hist_add_date`, `transaction_id`, `transaction_date`, `store_id`, `store2_id`, `transactor_id`, `transaction_type_id`, `transaction_reason_id`, `sub_total`, `total_trade_discount`, `total_vat`, `cash_discount`, `grand_total`, `transaction_ref`, `transaction_comment`, `add_user_detail_id`, `add_date`, `edit_user_detail_id`, `edit_date`, `points_awarded`, `card_number`, `total_std_vatable_amount`, `total_zero_vatable_amount`, `total_exempt_vatable_amount`, `vat_perc`, `amount_tendered`, `change_amount`, `is_cash_discount_vat_liable`, `total_profit_margin`, `transaction_user_detail_id`, `bill_transactor_id`, `scheme_transactor_id`, `princ_scheme_member`, `scheme_card_number`, `transaction_number`, `delivery_date`, `delivery_address`, `pay_terms`, `terms_conditions`, `authorised_by_user_detail_id`, `authorise_date`, `pay_due_date`, `expiry_date`, `acc_child_account_id`, `currency_code`, `xrate`, `from_date`, `to_date`, `duration_type`, `site_id`, `transactor_rep`, `transactor_vehicle`, `transactor_driver`, `duration_value`, `location_id`, `status_code`, `status_date`, `delivery_mode`, `is_processed`, `is_paid`, `is_cancel`, `is_invoiced`) VALUES
(1, 'Edit', '2019-06-11 12:14:16', 18, '2019-06-01', 1, NULL, 9, 2, 2, 1012, 0, 0, 0, 1012, '', '', 3, '2019-06-10 11:41:45', NULL, NULL, 0, '', 0, 1012, 0, 0, 0, -1012, 'No', 1012, 3, 9, NULL, '', '', 'SI1906100001', NULL, '', '', '', NULL, NULL, NULL, NULL, 14, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(2, 'Edit', '2019-06-11 12:16:22', 26, '2019-06-11', 1, NULL, 9, 2, 2, 343, 0, 0, 0, 343, '', '', 3, '2019-06-11 11:10:04', NULL, NULL, 0, '', 0, 343, 0, 0, 0, -343, 'No', 343, 3, 9, NULL, '', '', 'SI1906110001', NULL, '', '', '', NULL, NULL, NULL, NULL, 14, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(3, 'Edit', '2019-06-11 12:21:32', 19, '2019-06-10', 1, NULL, 3, 8, 12, 892, 0, 0, 0, 892, '', '', 5, '2019-06-10 12:36:23', NULL, NULL, 0, '', 0, 892, 0, 0, 0, -892, 'No', 0, 5, 3, NULL, '', '', '19', '2019-06-10', '', '', '', 5, '2019-06-10', NULL, NULL, NULL, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(4, 'Edit', '2019-06-17 16:42:49', 50, '2019-06-17', 1, NULL, 3, 2, 2, 434, 0, 0, 0, 434, '', '', 5, '2019-06-17 14:45:32', NULL, NULL, 0, '', 0, 434, 0, 0, 434, 0, 'No', 434, 5, 3, NULL, '', '', 'SI1906170002', NULL, '', '', '', NULL, NULL, NULL, NULL, 14, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(5, 'Edit', '2019-06-17 16:43:11', 49, '2019-06-17', 1, NULL, 3, 2, 2, 635, 0, 0, 0, 635, '', '', 5, '2019-06-17 14:43:35', NULL, NULL, 0, '', 0, 635, 0, 0, 635, 0, 'No', 635, 5, 3, NULL, '', '', 'SI1906170001', NULL, '', '', '', NULL, NULL, NULL, NULL, 14, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(6, 'Edit', '2019-06-17 16:55:03', 55, '2019-06-17', 1, NULL, 3, 1, 1, 1734, 0, 0, 0, 1734, '35', '', 5, '2019-06-17 16:50:43', NULL, NULL, 0, '', 0, 1734, 0, 0, 1300, -1734, 'No', 0, 5, 3, NULL, '', '', 'PI1906170002', NULL, '', '', '', NULL, NULL, NULL, NULL, 14, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(7, 'Edit', '2019-06-18 15:28:31', 62, '2019-06-18', 1, NULL, 3, 8, 12, 769, 0, 0, 0, 0, '', '', 4, '2019-06-18 15:01:05', NULL, NULL, 0, '', 0, 769, 0, 0, 0, 0, 'No', 0, 4, 3, NULL, '', '', '62', '2019-06-18', '', '', '', 3, '2019-06-18', NULL, NULL, NULL, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(8, 'Edit', '2019-07-01 10:04:34', 99, '2019-06-27', 1, NULL, 21, 2, 2, 2050, 0, 0, 0, 2050, '', '', 3, '2019-06-27 15:03:20', NULL, NULL, 0, '', 0, 2050, 0, 0, 0, -2050, 'No', 2050, 3, 21, NULL, '', '', 'SI1906270001', NULL, '', '', '', NULL, NULL, NULL, NULL, 14, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(9, 'Edit', '2019-07-01 10:06:53', 100, '2019-04-28', 1, NULL, 21, 2, 2, 2480, 0, 0, 0, 2480, '', '', 3, '2019-06-27 15:05:49', NULL, NULL, 0, '', 0, 2480, 0, 0, 0, -2480, 'No', 2480, 3, 21, NULL, '', '', 'SI1906270002', NULL, '', '', '', NULL, NULL, NULL, NULL, 14, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(10, 'Edit', '2019-07-01 10:17:06', 14, '2019-05-23', 1, NULL, 8, 2, 2, 1457, 0, 0, 0, 1457, '', '', 4, '2019-06-06 12:20:15', NULL, NULL, 0, '', 0, 1457, 0, 0, 0, -1457, 'No', 1457, 3, 8, NULL, '', '', 'SI1906060002', NULL, '', '', '', NULL, NULL, NULL, NULL, 14, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(11, 'Edit', '2019-07-11 17:43:26', 149, '2019-07-11', 1, NULL, 30, 10, 14, 6839, 0, 0, 0, 6839, '', '', 5, '2019-07-11 15:49:22', NULL, NULL, 0, '', 0, 6839, 0, 0, 0, -6839, 'No', 0, 5, 30, NULL, '', '', 'SQ1907110001', NULL, '', 'Cash Dollars', 'No booking made so prices subject to change', NULL, NULL, NULL, '2019-07-18', NULL, 'UGX', 1, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(12, 'Edit', '2019-08-07 16:24:25', 210, '2019-08-07', 1, 1, 42, 11, 16, 325, 0, 0, 0, 325, '', '', 5, '2019-08-07 16:23:02', NULL, NULL, 0, '', 0, 325, 0, 0, 0, -325, 'No', 0, 5, 42, NULL, '', '', '210', '2019-08-07', '', 'cash', '', NULL, NULL, NULL, NULL, NULL, 'UGX', 1, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0),
(13, 'Edit', '2019-08-08 15:18:28', 197, '2019-08-07', 1, NULL, 40, 2, 2, 1650, 0, 0, 0, 1650, '', '', 3, '2019-08-07 10:23:52', NULL, NULL, 0, '', 0, 1650, 0, 0, 0, -1650, 'No', 1650, 3, 40, NULL, '', '', 'SI1908070001', NULL, '', '', '', NULL, NULL, NULL, NULL, 14, 'USD', 3800, NULL, NULL, 'Week', 0, '', '', '', 0, NULL, NULL, NULL, 'Take Out', 1, 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `transaction_item`
--

CREATE TABLE IF NOT EXISTS `transaction_item` (
  `transaction_item_id` bigint(20) NOT NULL,
  `transaction_id` bigint(20) NOT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `batchno` varchar(100) DEFAULT NULL,
  `item_qty` double NOT NULL,
  `unit_price` double DEFAULT NULL,
  `unit_trade_discount` double DEFAULT NULL,
  `unit_vat` double DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `item_expiry_date` date DEFAULT NULL,
  `item_mnf_date` date DEFAULT NULL,
  `vat_rated` varchar(10) DEFAULT NULL,
  `vat_perc` double DEFAULT NULL,
  `unit_price_inc_vat` double DEFAULT NULL,
  `unit_price_exc_vat` double DEFAULT NULL,
  `amount_inc_vat` double DEFAULT NULL,
  `amount_exc_vat` double DEFAULT NULL,
  `stock_effect` varchar(1) DEFAULT NULL,
  `is_trade_discount_vat_liable` varchar(3) DEFAULT NULL,
  `unit_cost_price` double NOT NULL DEFAULT '0',
  `unit_profit_margin` double NOT NULL DEFAULT '0',
  `earn_perc` double NOT NULL DEFAULT '0',
  `earn_amount` double NOT NULL DEFAULT '0',
  `code_specific` varchar(100) DEFAULT NULL,
  `desc_specific` varchar(150) DEFAULT NULL,
  `desc_more` varchar(350) DEFAULT NULL,
  `warranty_desc` varchar(150) DEFAULT NULL,
  `warranty_expiry_date` date DEFAULT NULL,
  `account_code` varchar(20) DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `dep_start_date` date DEFAULT NULL,
  `dep_method_id` int(11) DEFAULT NULL,
  `dep_rate` double DEFAULT NULL,
  `average_method_id` int(11) DEFAULT NULL,
  `effective_life` int(3) DEFAULT NULL,
  `residual_value` double DEFAULT NULL,
  `narration` varchar(100) DEFAULT NULL,
  `qty_balance` double DEFAULT '0',
  `duration_value` double DEFAULT NULL,
  `qty_damage` double DEFAULT '0',
  `duration_passed` double DEFAULT NULL,
  PRIMARY KEY (`transaction_item_id`),
  KEY `Trans_to_TransItem_on_TransId` (`transaction_id`),
  KEY `Item_to_TransItem_on_ItemId` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Stand-in structure for view `transaction_item_bi`
--
CREATE TABLE IF NOT EXISTS `transaction_item_bi` (
`transaction_item_id` bigint(20)
,`transaction_id` bigint(20)
,`item_id` bigint(20)
,`batchno` varchar(100)
,`item_qty` double
,`unit_price` double
,`unit_trade_discount` double
,`unit_vat` double
,`amount` double
,`item_expiry_date` date
,`item_mnf_date` date
,`vat_rated` varchar(10)
,`vat_perc` double
,`unit_price_inc_vat` double
,`unit_price_exc_vat` double
,`amount_inc_vat` double
,`amount_exc_vat` double
,`stock_effect` varchar(1)
,`is_trade_discount_vat_liable` varchar(3)
,`unit_cost_price` double
,`unit_profit_margin` double
,`earn_perc` double
,`earn_amount` double
,`code_specific` varchar(100)
,`desc_specific` varchar(150)
,`desc_more` varchar(350)
,`warranty_desc` varchar(150)
,`warranty_expiry_date` date
,`account_code` varchar(20)
,`purchase_date` date
,`dep_start_date` date
,`dep_method_id` int(11)
,`dep_rate` double
,`average_method_id` int(11)
,`effective_life` int(3)
,`residual_value` double
,`narration` varchar(100)
,`qty_balance` double
,`duration_value` double
,`qty_damage` double
,`duration_passed` double
,`unit_cash_discount` double
);
-- --------------------------------------------------------

--
-- Table structure for table `transaction_item_deleted`
--

CREATE TABLE IF NOT EXISTS `transaction_item_deleted` (
  `transaction_item_id` bigint(20) NOT NULL,
  `transaction_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `batchno` varchar(100) NOT NULL,
  `item_qty` double NOT NULL,
  `unit_price` double DEFAULT NULL,
  `unit_trade_discount` double DEFAULT NULL,
  `unit_vat` double DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `item_expiry_date` date DEFAULT NULL,
  `item_mnf_date` date DEFAULT NULL,
  `vat_rated` varchar(10) DEFAULT NULL,
  `vat_perc` double DEFAULT NULL,
  `unit_price_inc_vat` double DEFAULT NULL,
  `unit_price_exc_vat` double DEFAULT NULL,
  `amount_inc_vat` double DEFAULT NULL,
  `amount_exc_vat` double DEFAULT NULL,
  `stock_effect` varchar(1) DEFAULT NULL,
  `is_trade_discount_vat_liable` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`transaction_item_id`),
  UNIQUE KEY `u_transitemdel_trans_item_batch` (`transaction_id`,`item_id`,`batchno`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_item_hist`
--

CREATE TABLE IF NOT EXISTS `transaction_item_hist` (
  `transaction_item_hist_id` bigint(20) NOT NULL,
  `transaction_hist_id` bigint(20) NOT NULL,
  `transaction_item_id` bigint(20) NOT NULL,
  `transaction_id` bigint(20) NOT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `batchno` varchar(100) DEFAULT NULL,
  `item_qty` double NOT NULL,
  `unit_price` double DEFAULT NULL,
  `unit_trade_discount` double DEFAULT NULL,
  `unit_vat` double DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `item_expiry_date` date DEFAULT NULL,
  `item_mnf_date` date DEFAULT NULL,
  `vat_rated` varchar(10) DEFAULT NULL,
  `vat_perc` double DEFAULT NULL,
  `unit_price_inc_vat` double DEFAULT NULL,
  `unit_price_exc_vat` double DEFAULT NULL,
  `amount_inc_vat` double DEFAULT NULL,
  `amount_exc_vat` double DEFAULT NULL,
  `stock_effect` varchar(1) DEFAULT NULL,
  `is_trade_discount_vat_liable` varchar(3) DEFAULT NULL,
  `unit_cost_price` double NOT NULL DEFAULT '0',
  `unit_profit_margin` double NOT NULL DEFAULT '0',
  `earn_perc` double NOT NULL DEFAULT '0',
  `earn_amount` double NOT NULL DEFAULT '0',
  `code_specific` varchar(100) DEFAULT NULL,
  `desc_specific` varchar(150) DEFAULT NULL,
  `desc_more` varchar(350) DEFAULT NULL,
  `warranty_desc` varchar(150) DEFAULT NULL,
  `warranty_expiry_date` date DEFAULT NULL,
  `account_code` varchar(20) DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `dep_start_date` date DEFAULT NULL,
  `dep_method_id` int(11) DEFAULT NULL,
  `dep_rate` double DEFAULT NULL,
  `average_method_id` int(11) DEFAULT NULL,
  `effective_life` int(3) DEFAULT NULL,
  `residual_value` double DEFAULT NULL,
  `narration` varchar(100) DEFAULT NULL,
  `qty_balance` double DEFAULT NULL,
  `duration_value` double DEFAULT NULL,
  `qty_damage` double DEFAULT NULL,
  `duration_passed` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_reason`
--

CREATE TABLE IF NOT EXISTS `transaction_reason` (
  `transaction_reason_id` int(11) NOT NULL,
  `transaction_reason_name` varchar(50) NOT NULL,
  `transaction_type_id` int(11) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`transaction_reason_id`),
  UNIQUE KEY `transaction_reason_name` (`transaction_reason_name`),
  KEY `TransType_to_TransReas_on_TransReasId` (`transaction_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction_reason`
--

INSERT INTO `transaction_reason` (`transaction_reason_id`, `transaction_reason_name`, `transaction_type_id`, `description`) VALUES
(1, 'GOOD AND SERVICE', 1, NULL),
(2, 'RETAIL SALE INVOICE', 2, NULL),
(3, 'DISPOSE STOCK', 3, NULL),
(6, 'TRANSFER', 4, NULL),
(8, 'ITEM', 5, NULL),
(9, 'UNPACK', 7, NULL),
(10, 'WHOLE SALE INVOICE', 2, NULL),
(11, 'COST-PRICE SALE INVOICE', 2, NULL),
(12, 'GOOD AND SERVICE PO', 8, NULL),
(13, 'GOODS RECEIVED', 9, NULL),
(14, 'RETAIL SALE QUOTATION', 10, NULL),
(15, 'WHOLE SALE QUOTATION', 10, NULL),
(16, 'RETAIL SALE ORDER', 11, NULL),
(17, 'EXEMPT SALE INVOICE', 2, NULL),
(18, 'GOODS DELIVERY', 12, NULL),
(19, 'TRANSFER REQUEST', 13, NULL),
(21, 'CASH SALE', 14, NULL),
(22, 'CREDIT SALE', 14, NULL),
(23, 'CAPITAL', 14, NULL),
(24, 'LOAN', 14, NULL),
(25, 'CREDIT PURCHASE', 15, NULL),
(26, 'CASH PURCHASE', 15, NULL),
(27, 'EXPENSE', 1, NULL),
(28, 'ASSET RECEIVED', 9, NULL),
(29, 'ASSET', 1, NULL),
(30, 'EXPENSE PO', 8, NULL),
(31, 'ASSET PO', 8, NULL),
(32, 'EXPENSE RECEIVED', 9, NULL),
(33, 'LOAN INSTALLMENT', 15, NULL),
(34, 'OWNER CASH DRAWING', 15, NULL),
(35, 'JOURNAL ENTRY', 16, NULL),
(36, 'CUSTOMER', 17, NULL),
(37, 'SUPPLIER', 17, NULL),
(38, 'SCHEME', 17, NULL),
(39, 'PROVIDER', 17, NULL),
(40, 'EMPLOYEE', 17, NULL),
(41, 'CASH TRANSFER', 18, NULL),
(42, 'CHEQUE TRANSFER', 18, NULL),
(43, 'EXPENSE ENTRY', 19, NULL),
(44, 'JOURNAL ENTRY RPT', 20, NULL),
(45, 'TRIAL BALANCE', 21, NULL),
(46, 'CASH FLOW BY USER RPT', 22, NULL),
(47, 'BALANCE SHEET', 23, NULL),
(48, 'INCOME STATEMENT', 24, NULL),
(49, 'CASH FLOW STATEMENT', 25, NULL),
(50, 'ACCOUNT PERIOD CLOSE', 26, NULL),
(51, 'ACCOUNT PERIOD OPEN', 26, NULL),
(52, 'ACCOUNT PERIOD RE-OPEN', 26, NULL),
(53, 'ACCOUNT PERIOD DETAIL', 26, NULL),
(54, 'POST-CLOSING TRIAL BALANCE', 27, NULL),
(55, 'CASH ACCOUNT BALANCE', 28, NULL),
(56, 'ACCOUNT STATEMENT', 29, NULL),
(57, 'RECEIVABLE DETAIL', 30, NULL),
(58, 'RECEIVABLE SUMMARY', 31, NULL),
(59, 'PAYABLE DETAIL', 32, NULL),
(60, 'PAYABLE SUMMARY', 33, NULL),
(61, 'INVENTORY-STOCK RPT', 34, NULL),
(62, 'INVENTORY-ASSET RPT', 35, NULL),
(63, 'INVENTORY-EXPENSE RPT', 36, NULL),
(64, 'ITEM-DETAIL-STOCK RPT', 37, NULL),
(65, 'ITEM-DETAIL-EXPENSE RPT', 38, NULL),
(66, 'ITEM-DETAIL-ASSET RPT', 39, NULL),
(67, 'ITEM-DETAIL-LOCATION RPT', 40, NULL),
(68, 'CUSTOMER LIST RPT', 41, NULL),
(69, 'SUPPLIER LIST RPT', 42, NULL),
(70, 'SCHEME LIST RPT', 43, NULL),
(71, 'PROVIDER LIST RPT', 44, NULL),
(72, 'SALES INVOICE DETAIL RPT', 45, NULL),
(73, 'SALES QUOTATION DETAIL RPT', 46, NULL),
(74, 'SALES ORDER DETAIL RPT', 47, NULL),
(75, 'SALES DELIVERY DETAIL RPT', 48, NULL),
(76, 'SALES USER EARN RPT', 49, NULL),
(77, 'PURCHASE INVOICE DETAIL RPT', 50, NULL),
(78, 'PURCHASE ORDER DETAIL RPT', 51, NULL),
(79, 'PURCHASE ITEM RECEIVED DETAIL RPT', 52, NULL),
(80, 'ITEM TRANSFER REQUEST DETAIL RPT', 53, NULL),
(81, 'ITEM TRANSFER DETAIL RPT', 54, NULL),
(82, 'DISPOSE STOCK DETAIL RPT', 55, NULL),
(83, 'CASH RECEIPT DETAIL RPT', 56, NULL),
(84, 'CASH PAYMENT DETAIL RPT', 57, NULL),
(85, 'CHART OF ACCOUNTS DTL', 58, NULL),
(86, 'DISCOUNT', 59, NULL),
(87, 'INTER BRANCH', 60, NULL),
(88, 'SETTING', 61, NULL),
(89, 'SPEND POINT', 62, NULL),
(90, 'PREPAID INCOME', 14, 'Prepaid income is revenue received in advance but which is not yet earned.'),
(91, 'PREPAID EXPENSE', 15, 'Prepaid expense is expense paid in advance but which has not yet been incurred.'),
(92, 'HIRE QUOTATION', 63, NULL),
(93, 'HIRE ORDER', 64, NULL),
(94, 'HIRE INVOICE', 65, NULL),
(95, 'HIRE DELIVERY NOTE', 66, NULL),
(96, 'HIRE RETURN NOTE', 67, NULL),
(97, 'HIRE RETURN INVOICE', 68, NULL),
(98, 'HIRE QUOTATION DETAIL RPT', 69, NULL),
(99, 'HIRE INVOICE DETAIL RPT', 69, NULL),
(101, 'HIRE RETURN DETAIL RPT', 69, NULL),
(102, 'HIRE DELIVERY DETAIL RPT', 69, NULL),
(103, 'SPECIAL SALE INVOICE', 2, NULL),
(104, 'HIRE UNRETURNED ITEMS DETAIL RPT', 69, NULL),
(105, 'LIABILITY PAYMENT', 15, NULL),
(106, 'STOCK ADJUSTMENT', 71, NULL),
(107, 'PRODUCTION', 70, NULL),
(108, 'STOCK CONSUMPTION', 72, NULL),
(109, 'WHOLE SALE ORDER', 11, NULL),
(110, 'SPECIAL SALE ORDER', 11, NULL),
(111, 'SPECIAL SALE QUOTATION', 10, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `transaction_repeat`
--

CREATE TABLE IF NOT EXISTS `transaction_repeat` (
  `transaction_repeat_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transaction_id` bigint(20) NOT NULL,
  `repeat_start_date` datetime NOT NULL,
  `repeart_interval_mode` varchar(20) NOT NULL,
  `repeart_interval_value` int(11) NOT NULL,
  `repeat_end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`transaction_repeat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_type`
--

CREATE TABLE IF NOT EXISTS `transaction_type` (
  `transaction_type_id` int(11) NOT NULL,
  `transaction_type_name` varchar(50) NOT NULL,
  `transactor_label` varchar(50) NOT NULL DEFAULT ' ',
  `transaction_number_label` varchar(50) NOT NULL DEFAULT ' ',
  `transaction_output_label` varchar(50) NOT NULL DEFAULT ' ',
  `bill_transactor_label` varchar(50) NOT NULL DEFAULT ' ',
  `transaction_ref_label` varchar(50) NOT NULL DEFAULT ' ',
  `transaction_date_label` varchar(50) NOT NULL DEFAULT ' ',
  `transaction_user_label` varchar(50) NOT NULL DEFAULT ' ',
  `is_transactor_mandatory` varchar(3) NOT NULL DEFAULT 'No',
  `is_transaction_user_mandatory` varchar(3) NOT NULL DEFAULT 'No',
  `is_transaction_ref_mandatory` varchar(3) NOT NULL DEFAULT 'No',
  `is_authorise_user_mandatory` varchar(3) NOT NULL DEFAULT 'No',
  `is_authorise_date_mandatory` varchar(3) NOT NULL DEFAULT 'No',
  `is_delivery_address_mandatory` varchar(3) NOT NULL DEFAULT 'No',
  `is_delivery_date_mandatory` varchar(3) NOT NULL DEFAULT 'No',
  `is_pay_due_date_mandatory` varchar(3) NOT NULL DEFAULT 'No',
  `is_expiry_date_mandatory` varchar(3) NOT NULL DEFAULT 'No',
  `description` varchar(100) DEFAULT NULL,
  `group_name` varchar(50) DEFAULT NULL,
  `print_file_name1` varchar(50) DEFAULT NULL,
  `print_file_name2` varchar(50) DEFAULT NULL,
  `default_print_file` int(1) DEFAULT NULL,
  `transaction_type_code` varchar(4) DEFAULT NULL,
  `default_currency_code` varchar(10) DEFAULT NULL,
  `trans_number_format` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`transaction_type_id`),
  UNIQUE KEY `transaction_type_name` (`transaction_type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction_type`
--

INSERT INTO `transaction_type` (`transaction_type_id`, `transaction_type_name`, `transactor_label`, `transaction_number_label`, `transaction_output_label`, `bill_transactor_label`, `transaction_ref_label`, `transaction_date_label`, `transaction_user_label`, `is_transactor_mandatory`, `is_transaction_user_mandatory`, `is_transaction_ref_mandatory`, `is_authorise_user_mandatory`, `is_authorise_date_mandatory`, `is_delivery_address_mandatory`, `is_delivery_date_mandatory`, `is_pay_due_date_mandatory`, `is_expiry_date_mandatory`, `description`, `group_name`, `print_file_name1`, `print_file_name2`, `default_print_file`, `transaction_type_code`, `default_currency_code`, `trans_number_format`) VALUES
(1, 'PURCHASE INVOICE', 'Supplier', 'Invoice No', 'SUPPLIER INVOICE', 'Supplier', 'LPO No', 'Invoice Date', 'Staff', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'Invoice from Supplier for the Purchased Goods/Services', 'PURCHASE AND EXPENSE', 'OutputPI_General', 'OutputPI_General', 1, 'PI', 'USD', 'CYMDX'),
(2, 'SALE INVOICE', 'Customer', 'Sales Invoice No', 'SALES INVOICE', 'Bill Customer', 'Purchase Order Ref', 'Invoice Date', 'Served By', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'SALES INVOICE', 'SALES', 'OutputSI_Size_Small', 'OutputSI_WTL', 2, 'SI', 'USD', 'CYMDX'),
(3, 'DISPOSE STOCK', '', 'Dispose Stock No', 'DISPOSE STOCK', '', '', 'Dispose Date', 'Disposed By', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, 'outputDS_General', 'outputDS_General', 1, NULL, NULL, NULL),
(4, 'TRANSFER', '', 'Item Transfer No', 'ITEM TRANSFER', '', 'Item Transfer Request Ref', 'Transfer Date', 'Transfered By', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', '', 'OutputST_General', 'OutputST_General', 1, '', '', ''),
(5, 'ITEM', '', '', '', '', '', '', '', '', '', '', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(6, 'PAYMENT', '', '', '', '', '', 'Pay Date', '', '', '', '', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(7, 'UNPACK', '', 'Unpack No', '', '', '', 'Unpack Date', 'Unpacked By', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', '', '', '', 0, 'U', 'UGX', 'CYMDX'),
(8, 'PURCHASE ORDER', 'Supplier', 'LPO No', 'PURCHASE ORDER', 'Bill Supplier', 'Transaction Ref', 'Purchase Order Date', 'Ordered By', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', '', 'OutputPO_WTL', 'OutputPO_General', 1, '', 'USD', ''),
(9, 'ITEM RECEIVED', 'Supplier', 'Goods Receive No', 'GOODS RECEIVED NOTE', 'Bill Supplier', 'Purchase Order Ref', 'Receive Date', 'Received By', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, 'OutputGRN_General', 'OutputGRN_General', 1, NULL, NULL, NULL),
(10, 'SALE QUOTATION', 'Customer', 'Sales Quote No', 'SALE QUOTATION', 'Bill Customer', 'Transaction Ref', 'Quote Date', 'Quoted By', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', '', 'OutputSQ_WTL', 'OutputSQ_General', 1, 'SQ', 'UGX', 'CYMDX'),
(11, 'SALE ORDER', 'Customer', 'Sales Order No', 'SALES ORDER', 'Bill Customer', 'Purchase Order Ref', 'Order Date', 'Received By', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, 'OutputSO_General', 'OutputSO_General', 1, NULL, NULL, NULL),
(12, 'GOODS DELIVERY', 'Customer', 'Goods Delivery No', 'GOODS DELIVERY NOTE', 'Bill Customer', 'Sales Invoice Ref', 'Delivery Date', 'Delivered By', 'Yes', 'No', 'No', 'No', 'No', 'Yes', 'No', 'No', 'No', '', '', 'OutputGDN_General', 'OutputGDN_General', 1, '', '', ''),
(13, 'TRANSFER REQUEST', '', 'Item Transfer Request No', 'ITEM TRANSFER REQUEST', '', 'Request Ref', 'Request Date', 'Request By', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, 'OutputSTR_General', 'OutputSTR_General', 1, NULL, NULL, NULL),
(14, 'CASH RECEIPT', ' Supplier', 'Receipt No', 'RECEIPT', 'Supplier', 'Purchase Ref', 'Purchase Date', ' Staff', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', '', 'OutputCR_WTL', 'OutputCR_WTL', 1, 'CR', 'USD', 'CYMDX'),
(15, 'CASH PAYMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, 'OutputCP_General', 'OutputCP_General', 1, 'CP', NULL, 'CYMDX'),
(16, 'JOURNAL ENTRY', 'Customer', 'Journal No', 'Journal Entry', 'Customer', 'Reference No', 'Journal Date', 'Staff', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(17, 'TRANSACTOR', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(18, 'CASH TRANSFER', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(19, 'EXPENSE ENTRY', 'Supplier', 'Expense No', 'EXPENSE NOTE', 'Supplier', '', 'Expense Date', '', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', 'PURCHASE AND EXPENSE', 'OutputPI_General', 'OutputPI_General', 1, 'EX', 'UGX', 'CYDMX'),
(20, 'JOURNAL RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS', NULL, NULL, NULL, NULL, NULL, NULL),
(21, 'TRIAL BALANCE', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS', NULL, NULL, NULL, NULL, NULL, NULL),
(22, 'CASH FLOW RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS', NULL, NULL, NULL, NULL, NULL, NULL),
(23, 'BALANCE SHEET', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'Statement of Financial Position', 'REPORTS - FINANCIAL ACCOUNTING', NULL, NULL, NULL, NULL, NULL, NULL),
(24, 'INCOME STATEMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'Profit and Loss Statement', 'REPORTS - FINANCIAL ACCOUNTING', NULL, NULL, NULL, NULL, NULL, NULL),
(25, 'CASH FLOW STATEMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - FINANCIAL ACCOUNTING', NULL, NULL, NULL, NULL, NULL, NULL),
(26, 'ACCOUNT PERIOD', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ACCOUNT PERIOD', NULL, NULL, NULL, NULL, NULL, NULL),
(27, 'POST-CLOSING TRIAL BALANCE', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - FINANCIAL ACCOUNTING', NULL, NULL, NULL, NULL, NULL, NULL),
(28, 'CASH ACCOUNT RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL, NULL, NULL, NULL, NULL, NULL),
(29, 'ACCOUNT STATEMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL, NULL, NULL, NULL, NULL, NULL),
(30, 'RECEIVABLE DETAIL', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL, NULL, NULL, NULL, NULL, NULL),
(31, 'RECEIVABLE SUMMARY', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL, NULL, NULL, NULL, NULL, NULL),
(32, 'PAYABLE DETAIL', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL, NULL, NULL, NULL, NULL, NULL),
(33, 'PAYABLE SUMMARY', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL, NULL, NULL, NULL, NULL, NULL),
(34, 'INVENTORY-STOCK RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - INVENTORY', NULL, NULL, NULL, NULL, NULL, NULL),
(35, 'INVENTORY-ASSET RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - INVENTORY', NULL, NULL, NULL, NULL, NULL, NULL),
(36, 'INVENTORY-EXPENSE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - INVENTORY', NULL, NULL, NULL, NULL, NULL, NULL),
(37, 'ITEM-DETAIL-STOCK RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL', NULL, NULL, NULL, NULL, NULL, NULL),
(38, 'ITEM-DETAIL-EXPENSE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL', NULL, NULL, NULL, NULL, NULL, NULL),
(39, 'ITEM-DETAIL-ASSET RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL', NULL, NULL, NULL, NULL, NULL, NULL),
(40, 'ITEM-DETAIL-LOCATION RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL', NULL, NULL, NULL, NULL, NULL, NULL),
(41, 'CUSTOMER LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST', NULL, NULL, NULL, NULL, NULL, NULL),
(42, 'SUPPLIER LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST', NULL, NULL, NULL, NULL, NULL, NULL),
(43, 'SCHEME LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST', NULL, NULL, NULL, NULL, NULL, NULL),
(44, 'PROVIDER LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST', NULL, NULL, NULL, NULL, NULL, NULL),
(45, 'SALES INVOICE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES', NULL, NULL, NULL, NULL, NULL, NULL),
(46, 'SALES QUOTATION RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES', NULL, NULL, NULL, NULL, NULL, NULL),
(47, 'SALES ORDER RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES', NULL, NULL, NULL, NULL, NULL, NULL),
(48, 'SALES DELIVERY RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES', NULL, NULL, NULL, NULL, NULL, NULL),
(49, 'SALES USER EARN RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES', NULL, NULL, NULL, NULL, NULL, NULL),
(50, 'PURCHASE INVOICE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PURCHASES', NULL, NULL, NULL, NULL, NULL, NULL),
(51, 'PURCHASE ORDER RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PURCHASES', NULL, NULL, NULL, NULL, NULL, NULL),
(52, 'PURCHASE ITEM RECEIVED RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PURCHASES', NULL, NULL, NULL, NULL, NULL, NULL),
(53, 'TRANSFER REQUEST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT ITEM TRANSFERS', NULL, NULL, NULL, NULL, NULL, NULL),
(54, 'TRANSFER RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT ITEM TRANSFERS', NULL, NULL, NULL, NULL, NULL, NULL),
(55, 'DISPOSE STOCK RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT DISPOSE STOCK', NULL, NULL, NULL, NULL, NULL, NULL),
(56, 'CASH RECEIPT RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS CASH', NULL, NULL, NULL, NULL, NULL, NULL),
(57, 'CASH PAYMENT RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS CASH', NULL, NULL, NULL, NULL, NULL, NULL),
(58, 'CHART OF ACCOUNTS', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'ACCOUNTS', NULL, NULL, NULL, NULL, NULL, NULL),
(59, 'DISCOUNT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'SALES', NULL, NULL, NULL, NULL, NULL, NULL),
(60, 'INTER BRANCH', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'INTER BRANCH', NULL, NULL, NULL, NULL, NULL, NULL),
(61, 'SETTING', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'SETTING', NULL, NULL, NULL, NULL, NULL, NULL),
(62, 'SPEND POINT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'SALES', NULL, NULL, NULL, NULL, NULL, NULL),
(63, 'HIRE QUOTATION', 'Customer', 'Quote No', 'HIRE QUOTATION', 'Bill Customer', 'Quote Ref', 'Quote Date', 'Company Rep', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHQ_Sheaf', NULL, 1, NULL, NULL, NULL),
(64, 'HIRE ORDER', 'Customer', 'Order No', 'HIRE ORDER', 'Bill Customer', 'Order Ref', 'Order Date', 'Company Rep', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'HIRE', NULL, NULL, NULL, NULL, NULL, NULL),
(65, 'HIRE INVOICE', 'Customer', 'Hire Number', 'HIRE INVOICE', 'Bill Customer', 'Order Ref', 'Hire Date', 'Company Rep', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHI_Sheaf', 'OutputHI_Sheaf', 1, 'HI', 'USD', 'CYMDX'),
(66, 'HIRE DELIVERY NOTE', 'Customer', 'Delivery No', 'HIRE DELIVERY NOTE', 'Bill Customer', 'Delivery Ref', 'Delivery Date', 'Company Rep', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHD_Sheaf', NULL, 1, NULL, NULL, NULL),
(67, 'HIRE RETURN NOTE', 'Customer', 'Return Number ', 'HIRE RETURN NOTE', 'Bill Customer', 'Return Number', 'Return Date', ' Company Rep', 'Yes', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHR_Sheaf', 'OutputHR_Sheaf', 1, NULL, NULL, NULL),
(68, 'HIRE RETURN INVOICE', 'Customer', 'Invoice Number', 'HIRE RETURN INVOICE', 'Bill Customer', 'Return Number', 'Invoice Date', ' Company Rep', 'Yes', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'HIRE', 'OutputHRI_Sheaf', NULL, 1, NULL, NULL, NULL),
(69, 'HIRE REPORTS', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'HIRE REPORTS', NULL, NULL, NULL, NULL, NULL, NULL),
(70, 'PRODUCTION', ' ', 'Production No', 'PRODUCTION', ' ', ' ', 'Production Date', 'Production By', 'No', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'PRODUCTION', NULL, NULL, NULL, NULL, 'UGX', NULL),
(71, 'STOCK ADJUSTMENT', ' ', 'Stock Adjust No', 'STOCK ADJUSTMENT', ' ', ' ', 'Adjust Date', 'Adjusted By', 'No', 'No', 'No', 'Yes', 'Yes', 'No', 'No', 'No', 'No', NULL, 'STOCK MANAGEMENT', 'OutputAS_General', NULL, 1, 'SA', NULL, 'CYMDX'),
(72, 'STOCK CONSUMPTION', ' ', 'Stock Consume No', 'STOCK CONSUMPTION', ' ', ' ', 'Consume Date', 'Recorded By', 'No', 'No', 'No', 'Yes', 'Yes', 'No', 'No', 'No', 'No', NULL, 'STOCK MANAGEMENT', 'OutputSC_General', NULL, 1, 'SC', NULL, 'CYMDX');

-- --------------------------------------------------------

--
-- Table structure for table `transactor`
--

CREATE TABLE IF NOT EXISTS `transactor` (
  `transactor_id` bigint(20) NOT NULL,
  `transactor_type` varchar(20) NOT NULL,
  `transactor_names` varchar(100) NOT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `website` varchar(100) DEFAULT NULL,
  `cpname` varchar(100) DEFAULT NULL,
  `cptitle` varchar(100) DEFAULT NULL,
  `cpphone` varchar(100) DEFAULT NULL,
  `cpemail` varchar(100) DEFAULT NULL,
  `physical_address` varchar(255) DEFAULT NULL,
  `tax_identity` varchar(100) DEFAULT NULL,
  `account_details` varchar(255) DEFAULT NULL,
  `card_number` varchar(10) DEFAULT NULL,
  `add_date` datetime NOT NULL,
  `edit_date` datetime NOT NULL,
  `dob` date DEFAULT NULL,
  `is_suspended` varchar(3) DEFAULT 'No',
  `suspended_reason` varchar(50) DEFAULT NULL,
  `category` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `occupation` varchar(50) DEFAULT NULL,
  `loc_country_id` int(11) DEFAULT NULL,
  `loc_district_id` int(11) DEFAULT NULL,
  `loc_town_id` int(11) DEFAULT NULL,
  `loc_country` varchar(100) DEFAULT NULL,
  `loc_district` varchar(100) DEFAULT NULL,
  `loc_town` varchar(100) DEFAULT NULL,
  `first_date` date DEFAULT NULL,
  `file_reference` varchar(100) DEFAULT NULL,
  `id_type` varchar(50) DEFAULT NULL,
  `id_number` varchar(50) DEFAULT NULL,
  `id_expiry_date` date DEFAULT NULL,
  `transactor_ref` varchar(20) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `month_gross_pay` double DEFAULT NULL,
  `month_net_pay` double DEFAULT NULL,
  PRIMARY KEY (`transactor_id`),
  KEY `transactor_type` (`transactor_type`),
  KEY `district_transactor` (`loc_district_id`),
  KEY `country_transactor` (`loc_country_id`),
  KEY `town_transactor` (`loc_town_id`),
  KEY `transactor_names` (`transactor_names`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transactor_ledger`
--

CREATE TABLE IF NOT EXISTS `transactor_ledger` (
  `transactor_ledger_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) NOT NULL,
  `store_name` varchar(20) NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `pay_id` bigint(20) NOT NULL,
  `transaction_type_name` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL,
  `transaction_date` date NOT NULL,
  `add_date` datetime NOT NULL,
  `transactor_id` bigint(20) DEFAULT NULL,
  `transactor_names` varchar(100) NOT NULL,
  `ledger_entry_type` varchar(2) NOT NULL,
  `amount_debit` double NOT NULL,
  `amount_credit` double NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `bill_transactor_names` varchar(100) DEFAULT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  PRIMARY KEY (`transactor_ledger_id`),
  KEY `Transactor_TransactorLedger` (`transactor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `trans_number_control`
--

CREATE TABLE IF NOT EXISTS `trans_number_control` (
  `trans_number_control_id` int(11) NOT NULL AUTO_INCREMENT,
  `trans_type_id` int(11) DEFAULT NULL,
  `year_num` int(11) DEFAULT NULL,
  `month_num` int(11) DEFAULT NULL,
  `day_num` int(11) DEFAULT NULL,
  `day_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`trans_number_control_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=179 ;

--
-- Dumping data for table `trans_number_control`
--

INSERT INTO `trans_number_control` (`trans_number_control_id`, `trans_type_id`, `year_num`, `month_num`, `day_num`, `day_count`) VALUES
(1, 2, 2019, 6, 5, 5),
(2, 14, 2019, 6, 5, 6),
(3, 19, 2019, 6, 5, 1),
(4, 15, 2019, 6, 5, 3),
(5, 8, 2019, 6, 5, 2),
(6, 1, 2019, 6, 5, 2),
(7, 18, 2019, 6, 5, 0),
(8, 2, 2019, 6, 6, 5),
(9, 14, 2019, 6, 6, 5),
(10, 2, 2019, 6, 10, 2),
(11, 14, 2019, 6, 10, 5),
(12, 8, 2019, 6, 10, 2),
(13, 1, 2019, 6, 10, 2),
(14, 15, 2019, 6, 10, 4),
(15, 19, 2019, 6, 10, 2),
(16, 2, 2019, 6, 11, 3),
(17, 14, 2019, 6, 11, 5),
(18, 8, 2019, 6, 11, 2),
(19, 1, 2019, 6, 11, 4),
(20, 15, 2019, 6, 11, 6),
(21, 18, 2019, 6, 11, 0),
(22, 19, 2019, 6, 11, 2),
(23, 2, 2019, 6, 13, 1),
(24, 14, 2019, 6, 13, 1),
(25, 19, 2019, 6, 13, 2),
(26, 15, 2019, 6, 13, 2),
(27, 14, 2019, 6, 14, 3),
(28, 15, 2019, 6, 14, 1),
(29, 8, 2019, 6, 14, 2),
(30, 2, 2019, 6, 14, 2),
(31, 14, 2019, 6, 17, 5),
(32, 8, 2019, 6, 17, 1),
(33, 2, 2019, 6, 17, 3),
(34, 19, 2019, 6, 17, 2),
(35, 15, 2019, 6, 17, 4),
(36, 18, 2019, 6, 17, 0),
(37, 1, 2019, 6, 17, 2),
(38, 19, 2019, 6, 18, 2),
(39, 15, 2019, 6, 18, 5),
(40, 8, 2019, 6, 18, 4),
(41, 1, 2019, 6, 18, 3),
(42, 18, 2019, 6, 18, 0),
(43, 2, 2019, 6, 18, 2),
(44, 14, 2019, 6, 18, 4),
(45, 19, 2019, 6, 19, 2),
(46, 15, 2019, 6, 19, 3),
(47, 2, 2019, 6, 20, 2),
(48, 14, 2019, 6, 20, 4),
(49, 8, 2019, 6, 20, 1),
(50, 1, 2019, 6, 20, 1),
(51, 15, 2019, 6, 20, 4),
(52, 19, 2019, 6, 20, 3),
(53, 18, 2019, 6, 20, 0),
(54, 2, 2019, 6, 24, 6),
(55, 14, 2019, 6, 24, 8),
(56, 19, 2019, 6, 24, 2),
(57, 15, 2019, 6, 24, 4),
(58, 8, 2019, 6, 24, 2),
(59, 1, 2019, 6, 24, 2),
(60, 8, 2019, 6, 25, 1),
(61, 2, 2019, 6, 25, 1),
(62, 14, 2019, 6, 25, 1),
(63, 1, 2019, 6, 25, 1),
(64, 15, 2019, 6, 25, 3),
(65, 19, 2019, 6, 25, 2),
(66, 18, 2019, 6, 25, 0),
(67, 8, 2019, 6, 26, 1),
(68, 1, 2019, 6, 26, 1),
(69, 15, 2019, 6, 26, 1),
(70, 2, 2019, 6, 27, 2),
(71, 14, 2019, 6, 27, 4),
(72, 19, 2019, 6, 27, 1),
(73, 15, 2019, 6, 27, 1),
(74, 2, 2019, 6, 28, 1),
(75, 14, 2019, 6, 28, 2),
(76, 19, 2019, 7, 1, 1),
(77, 15, 2019, 7, 1, 1),
(78, 19, 2019, 7, 2, 3),
(79, 15, 2019, 7, 2, 3),
(80, 2, 2019, 7, 2, 1),
(81, 14, 2019, 7, 2, 2),
(82, 8, 2019, 7, 3, 2),
(83, 2, 2019, 7, 3, 1),
(84, 14, 2019, 7, 3, 2),
(85, 1, 2019, 7, 3, 2),
(86, 15, 2019, 7, 3, 3),
(87, 19, 2019, 7, 3, 1),
(88, 2, 2019, 7, 4, 1),
(89, 14, 2019, 7, 4, 2),
(90, 8, 2019, 7, 4, 1),
(91, 1, 2019, 7, 4, 1),
(92, 15, 2019, 7, 4, 5),
(93, 19, 2019, 7, 4, 4),
(94, 18, 2019, 7, 4, 0),
(95, 2, 2019, 7, 5, 1),
(96, 14, 2019, 7, 5, 1),
(97, 2, 2019, 7, 6, 1),
(98, 14, 2019, 7, 6, 2),
(99, 8, 2019, 7, 8, 1),
(100, 1, 2019, 7, 8, 1),
(101, 15, 2019, 7, 8, 1),
(102, 2, 2019, 7, 9, 3),
(103, 14, 2019, 7, 9, 4),
(104, 19, 2019, 7, 9, 1),
(105, 15, 2019, 7, 9, 2),
(106, 18, 2019, 7, 9, 0),
(107, 8, 2019, 7, 9, 1),
(108, 1, 2019, 7, 9, 1),
(109, 2, 2019, 7, 10, 3),
(110, 14, 2019, 7, 10, 7),
(111, 8, 2019, 7, 10, 2),
(112, 18, 2019, 7, 10, 0),
(113, 1, 2019, 7, 10, 2),
(114, 15, 2019, 7, 10, 2),
(115, 14, 2019, 7, 11, 1),
(116, 18, 2019, 7, 11, 0),
(117, 10, 2019, 7, 11, 2),
(118, 2, 2019, 7, 12, 2),
(119, 14, 2019, 7, 12, 3),
(120, 8, 2019, 7, 15, 1),
(121, 2, 2019, 7, 15, 1),
(122, 14, 2019, 7, 15, 1),
(123, 14, 2019, 7, 16, 1),
(124, 8, 2019, 7, 16, 1),
(125, 1, 2019, 7, 18, 1),
(126, 15, 2019, 7, 18, 3),
(127, 19, 2019, 7, 18, 2),
(128, 18, 2019, 7, 18, 0),
(129, 2, 2019, 7, 18, 1),
(130, 14, 2019, 7, 18, 1),
(131, 2, 2019, 7, 22, 1),
(132, 14, 2019, 7, 22, 1),
(133, 19, 2019, 7, 23, 3),
(134, 15, 2019, 7, 23, 3),
(135, 2, 2019, 7, 25, 1),
(136, 14, 2019, 7, 25, 2),
(137, 8, 2019, 7, 25, 1),
(138, 1, 2019, 7, 26, 1),
(139, 15, 2019, 7, 26, 1),
(140, 14, 2019, 7, 26, 1),
(141, 2, 2019, 7, 27, 1),
(142, 14, 2019, 7, 27, 1),
(143, 14, 2019, 7, 30, 2),
(144, 19, 2019, 7, 30, 1),
(145, 15, 2019, 7, 30, 1),
(146, 8, 2019, 7, 31, 1),
(147, 14, 2019, 7, 31, 3),
(148, 2, 2019, 7, 31, 1),
(149, 1, 2019, 7, 31, 1),
(150, 15, 2019, 7, 31, 3),
(151, 19, 2019, 7, 31, 2),
(152, 2, 2019, 8, 2, 3),
(153, 14, 2019, 8, 2, 4),
(154, 8, 2019, 8, 2, 3),
(155, 19, 2019, 8, 2, 2),
(156, 15, 2019, 8, 2, 3),
(157, 1, 2019, 8, 2, 1),
(158, 2, 2019, 8, 3, 1),
(159, 14, 2019, 8, 3, 1),
(160, 2, 2019, 8, 6, 4),
(161, 14, 2019, 8, 6, 11),
(162, 8, 2019, 8, 6, 3),
(163, 19, 2019, 8, 6, 3),
(164, 15, 2019, 8, 6, 3),
(165, 18, 2019, 8, 6, 0),
(166, 10, 2019, 8, 6, 1),
(167, 2, 2019, 8, 7, 4),
(168, 14, 2019, 8, 7, 8),
(169, 8, 2019, 8, 7, 5),
(170, 1, 2019, 8, 7, 8),
(171, 15, 2019, 8, 7, 8),
(172, 11, 2019, 8, 7, 0),
(173, 14, 2019, 8, 8, 4),
(174, 8, 2019, 8, 8, 1),
(175, 2, 2019, 8, 8, 3),
(176, 18, 2019, 8, 8, 0),
(177, 14, 2019, 8, 9, 2),
(178, 8, 2019, 8, 9, 1);

-- --------------------------------------------------------

--
-- Table structure for table `trans_production`
--

CREATE TABLE IF NOT EXISTS `trans_production` (
  `transaction_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transaction_date` date NOT NULL,
  `store_id` int(11) NOT NULL,
  `store2_id` int(11) DEFAULT NULL,
  `transaction_type_id` int(11) NOT NULL,
  `transaction_reason_id` int(11) NOT NULL,
  `transaction_ref` varchar(100) DEFAULT NULL,
  `output_item_id` bigint(20) NOT NULL,
  `output_qty` double NOT NULL,
  `output_unit_cost` double NOT NULL,
  `output_total_cost` double NOT NULL,
  `batchno` varchar(100) NOT NULL,
  `item_expiry_date` date DEFAULT NULL,
  `item_mnf_date` date DEFAULT NULL,
  `code_specific` varchar(50) NOT NULL,
  `desc_specific` varchar(100) NOT NULL,
  `desc_more` varchar(250) NOT NULL,
  `transaction_comment` varchar(255) DEFAULT NULL,
  `add_user_detail_id` int(11) NOT NULL,
  `add_date` datetime NOT NULL,
  `edit_user_detail_id` int(11) DEFAULT NULL,
  `edit_date` datetime DEFAULT NULL,
  `transaction_user_detail_id` int(11) NOT NULL,
  `authorised_by_user_detail_id` int(11) DEFAULT NULL,
  `authorise_date` date DEFAULT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `account_code` varchar(20) DEFAULT NULL,
  `transactor_id` bigint(20) DEFAULT NULL,
  `transaction_number` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `trans_production_item`
--

CREATE TABLE IF NOT EXISTS `trans_production_item` (
  `trans_production_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transaction_id` bigint(20) NOT NULL,
  `input_item_id` bigint(20) NOT NULL,
  `input_qty` double NOT NULL,
  `input_unit_cost` double NOT NULL,
  `batchno` varchar(100) NOT NULL,
  `code_specific` varchar(50) NOT NULL,
  `desc_specific` varchar(100) NOT NULL,
  `desc_more` varchar(250) NOT NULL,
  PRIMARY KEY (`trans_production_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `unit`
--

CREATE TABLE IF NOT EXISTS `unit` (
  `unit_id` int(11) NOT NULL,
  `unit_name` varchar(50) NOT NULL,
  `unit_symbol` varchar(5) NOT NULL,
  PRIMARY KEY (`unit_id`),
  UNIQUE KEY `unit_name` (`unit_name`),
  UNIQUE KEY `unit_symbol` (`unit_symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `unit`
--

INSERT INTO `unit` (`unit_id`, `unit_name`, `unit_symbol`) VALUES
(1, 'Asset', 'Asset'),
(2, 'Expense', 'Exp'),
(3, 'pc', 'Piece'),
(4, 'Packets', 'Pkt'),
(5, 'Kilograms', 'KGS'),
(6, 'Bundle/Roll', 'Rl'),
(7, 'Metres', 'Mtr');

-- --------------------------------------------------------

--
-- Table structure for table `user_category`
--

CREATE TABLE IF NOT EXISTS `user_category` (
  `user_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_category_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user_category`
--

INSERT INTO `user_category` (`user_category_id`, `user_category_name`) VALUES
(1, 'Administration'),
(2, 'Staff');

-- --------------------------------------------------------

--
-- Table structure for table `user_detail`
--

CREATE TABLE IF NOT EXISTS `user_detail` (
  `user_detail_id` int(11) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `second_name` varchar(100) NOT NULL,
  `third_name` varchar(100) NOT NULL,
  `user_img_url` varchar(255) DEFAULT NULL,
  `is_user_locked` varchar(3) NOT NULL,
  `is_user_gen_admin` varchar(3) NOT NULL,
  `add_date` datetime NOT NULL,
  `edit_date` datetime NOT NULL,
  `user_category_id` int(11) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `email_address` varchar(100) DEFAULT NULL,
  `phone_no` varchar(50) DEFAULT NULL,
  `trans_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_detail_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `trans_code` (`trans_code`),
  UNIQUE KEY `trans_code_2` (`trans_code`),
  KEY `ucategory_user` (`user_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_detail`
--

INSERT INTO `user_detail` (`user_detail_id`, `user_name`, `user_password`, `first_name`, `second_name`, `third_name`, `user_img_url`, `is_user_locked`, `is_user_gen_admin`, `add_date`, `edit_date`, `user_category_id`, `employee_id`, `email_address`, `phone_no`, `trans_code`) VALUES
(1, 'Admin', 'H8iTT7k4k2s2L3s3F9B2X3y1V4cnJ1AiL6wmJ1odg8caw9iLJ3J0', 'Admin', 'Aser', '', '', 'No', 'Yes', '2014-09-24 23:45:09', '2019-05-10 15:34:16', 2, NULL, '', '', 'V8HHe5H4L1T3a3V3A4m2m5P1i7PZV0a5'),
(2, 'system', 'T7aga5X3u0w3s8g2w5c1o5DmX1seo9yts7qsc0Dyu6osZ0mcJ6R9', 'System', 'ASER', '', NULL, 'No', 'Yes', '2014-12-29 18:26:42', '2014-12-29 18:26:42', NULL, NULL, NULL, NULL, NULL),
(3, 'Joseph', 'y6BRT8s3T7a7u8k2T5u1o5uhH7qpc0ces9AsX6wos2XjN9VPe8J6', 'Joseph', 'Wasswa', '', '', 'No', 'No', '2019-05-14 18:24:56', '2019-06-05 14:31:32', 2, NULL, '', '', 'X1Acq2q9y0s8e3R9a2P9e2o9L7J9i7icy2k6'),
(4, 'Irene', 'T5sNX8a3N8s1a1H2R7g1J2XeR9Nns8sew1Nrw5Riq9oRq7L1', 'Irene', 'Luyiga', '', '', 'No', 'No', '2019-05-14 18:25:57', '2019-06-05 14:31:55', 2, NULL, '', '', 'D9NNL7N8H4H4V9P8w1B8e1A8R1e8o2kmB3g3'),
(5, 'Alexandra', 'N2qgy1a3q4s7L7y2s6g1y5enq5miA6kmH4DdZ0iaF5XVe0a1', 'Alexandra', 'Karemire', '', '', 'No', 'Yes', '2019-05-28 19:35:45', '2019-06-05 14:32:48', 1, NULL, '', '', 'Z4aTq8s7H2F8i0J7N9T7a8L7Z2y7F1NZi0i4');

-- --------------------------------------------------------

--
-- Table structure for table `user_item_earn`
--

CREATE TABLE IF NOT EXISTS `user_item_earn` (
  `user_item_earn_id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_type_id` int(11) NOT NULL,
  `transaction_reason_id` int(11) NOT NULL,
  `user_category_id` int(11) NOT NULL,
  `item_category_id` int(11) NOT NULL,
  `item_sub_category_id` int(11) DEFAULT NULL,
  `earn_perc` double NOT NULL,
  PRIMARY KEY (`user_item_earn_id`),
  UNIQUE KEY `earn_constraint` (`transaction_type_id`,`user_category_id`,`item_category_id`,`item_sub_category_id`,`transaction_reason_id`),
  KEY `usercat_userearn` (`user_category_id`),
  KEY `cat_userearn` (`item_category_id`),
  KEY `transtype_userearn` (`transaction_type_id`),
  KEY `transreas_userearn` (`transaction_reason_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_right`
--

CREATE TABLE IF NOT EXISTS `user_right` (
  `user_right_id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) NOT NULL,
  `user_detail_id` int(11) NOT NULL,
  `function_name` varchar(20) NOT NULL,
  `allow_view` varchar(3) NOT NULL,
  `allow_add` varchar(3) NOT NULL,
  `allow_edit` varchar(3) NOT NULL,
  `allow_delete` varchar(3) NOT NULL,
  PRIMARY KEY (`user_right_id`),
  UNIQUE KEY `u_userright_store_user_function` (`store_id`,`user_detail_id`,`function_name`),
  KEY `Store_to_UserRight_on_StoreId` (`store_id`),
  KEY `UserD_to_UserRight_on_UserDId` (`user_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `view_fact_expenses`
--
CREATE TABLE IF NOT EXISTS `view_fact_expenses` (
`transaction_type_id` int(11)
,`transaction_reason_id` int(11)
,`transaction_id` bigint(20)
,`transaction_date` date
,`y` int(4)
,`m` int(2)
,`d` int(2)
,`amount` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_fact_expenses_items`
--
CREATE TABLE IF NOT EXISTS `view_fact_expenses_items` (
`transaction_type_id` int(11)
,`transaction_reason_id` int(11)
,`transaction_id` bigint(20)
,`item_id` bigint(20)
,`item_qty` double
,`amount` double
,`transaction_date` date
,`y` int(4)
,`m` int(2)
,`d` int(2)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_fact_sales`
--
CREATE TABLE IF NOT EXISTS `view_fact_sales` (
`transaction_id` bigint(20)
,`transaction_date` date
,`y` int(4)
,`m` int(2)
,`d` int(2)
,`amount` double
,`c` bigint(21)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_fact_sales_items`
--
CREATE TABLE IF NOT EXISTS `view_fact_sales_items` (
`transaction_id` bigint(20)
,`item_id` bigint(20)
,`item_qty` double
,`amount` double
,`transaction_date` date
,`y` int(4)
,`m` int(2)
,`d` int(2)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_fact_sales_no_items`
--
CREATE TABLE IF NOT EXISTS `view_fact_sales_no_items` (
`transaction_id` bigint(20)
,`transaction_date` date
,`y` int(4)
,`m` int(2)
,`d` int(2)
,`amount` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_fact_sales_no_view`
--
CREATE TABLE IF NOT EXISTS `view_fact_sales_no_view` (
`transaction_id` bigint(20)
,`transaction_date` date
,`y` int(4)
,`m` int(2)
,`d` int(2)
,`amount` double
,`c` bigint(21)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_fact_trans_avg_items`
--
CREATE TABLE IF NOT EXISTS `view_fact_trans_avg_items` (
`tid` bigint(20)
,`ic` bigint(21)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_inventory_asset`
--
CREATE TABLE IF NOT EXISTS `view_inventory_asset` (
`stock_id` bigint(20)
,`store_id` int(11)
,`item_id` bigint(20)
,`batchno` varchar(100)
,`currentqty` double
,`item_mnf_date` date
,`item_exp_date` date
,`unit_cost` double
,`code_specific` varchar(50)
,`desc_specific` varchar(100)
,`desc_more` varchar(250)
,`warranty_desc` varchar(150)
,`warranty_expiry_date` date
,`purchase_date` date
,`dep_start_date` date
,`dep_method_id` int(11)
,`dep_rate` double
,`average_method_id` int(11)
,`effective_life` int(3)
,`asset_status_id` int(11)
,`asset_status_desc` varchar(100)
,`account_code` varchar(20)
,`residual_value` double
,`qty_damage` double
,`item_code` varchar(50)
,`description` varchar(100)
,`currency_code` varchar(10)
,`reorder_level` int(11)
,`category_id` int(11)
,`sub_category_id` int(11)
,`unit_cost_price` double
,`unit_retailsale_price` double
,`unit_wholesale_price` double
,`asset_type` varchar(50)
,`store_name` varchar(20)
,`category_name` varchar(50)
,`unit_name` varchar(50)
,`unit_symbol` varchar(5)
,`sub_category_name` varchar(50)
,`cost_value` int(1)
,`retailsale_value` int(1)
,`wholesale_value` int(1)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_inventory_expense`
--
CREATE TABLE IF NOT EXISTS `view_inventory_expense` (
`stock_id` bigint(20)
,`store_id` int(11)
,`item_id` bigint(20)
,`batchno` varchar(100)
,`currentqty` double
,`item_mnf_date` date
,`item_exp_date` date
,`unit_cost` double
,`code_specific` varchar(50)
,`desc_specific` varchar(100)
,`desc_more` varchar(250)
,`warranty_desc` varchar(150)
,`warranty_expiry_date` date
,`purchase_date` date
,`dep_start_date` date
,`dep_method_id` int(11)
,`dep_rate` double
,`average_method_id` int(11)
,`effective_life` int(3)
,`asset_status_id` int(11)
,`asset_status_desc` varchar(100)
,`account_code` varchar(20)
,`residual_value` double
,`qty_damage` double
,`item_code` varchar(50)
,`description` varchar(100)
,`currency_code` varchar(10)
,`reorder_level` int(11)
,`category_id` int(11)
,`sub_category_id` int(11)
,`unit_cost_price` double
,`unit_retailsale_price` double
,`unit_wholesale_price` double
,`store_name` varchar(20)
,`category_name` varchar(50)
,`unit_name` varchar(50)
,`unit_symbol` varchar(5)
,`sub_category_name` varchar(50)
,`cost_value` int(1)
,`retailsale_value` int(1)
,`wholesale_value` int(1)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_inventory_stock`
--
CREATE TABLE IF NOT EXISTS `view_inventory_stock` (
`stock_id` bigint(20)
,`store_id` int(11)
,`item_id` bigint(20)
,`batchno` varchar(100)
,`currentqty` double
,`item_mnf_date` date
,`item_exp_date` date
,`unit_cost` double
,`code_specific` varchar(50)
,`desc_specific` varchar(100)
,`desc_more` varchar(250)
,`warranty_desc` varchar(150)
,`warranty_expiry_date` date
,`purchase_date` date
,`dep_start_date` date
,`dep_method_id` int(11)
,`dep_rate` double
,`average_method_id` int(11)
,`effective_life` int(3)
,`asset_status_id` int(11)
,`asset_status_desc` varchar(100)
,`account_code` varchar(20)
,`residual_value` double
,`qty_damage` double
,`item_code` varchar(50)
,`description` varchar(100)
,`currency_code` varchar(10)
,`reorder_level` int(11)
,`category_id` int(11)
,`sub_category_id` int(11)
,`unit_cost_price` double
,`unit_retailsale_price` double
,`unit_wholesale_price` double
,`store_name` varchar(20)
,`category_name` varchar(50)
,`unit_name` varchar(50)
,`unit_symbol` varchar(5)
,`sub_category_name` varchar(50)
,`cost_value` int(1)
,`retailsale_value` int(1)
,`wholesale_value` int(1)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_item`
--
CREATE TABLE IF NOT EXISTS `view_item` (
`item_id` bigint(20)
,`item_code` varchar(50)
,`description` varchar(100)
,`category_id` int(11)
,`sub_category_id` int(11)
,`unit_id` int(11)
,`reorder_level` int(11)
,`unit_cost_price` double
,`unit_retailsale_price` double
,`unit_wholesale_price` double
,`is_suspended` varchar(3)
,`vat_rated` varchar(10)
,`item_img_url` varchar(100)
,`add_date` datetime
,`edit_date` datetime
,`item_type` varchar(7)
,`currency_code` varchar(10)
,`is_general` int(1)
,`asset_type` varchar(50)
,`is_buy` int(1)
,`is_sale` int(1)
,`is_track` int(1)
,`is_asset` int(1)
,`asset_account_code` varchar(20)
,`expense_account_code` varchar(20)
,`is_hire` int(1)
,`duration_type` varchar(20)
,`unit_hire_price` double
,`unit_special_price` double
,`unit_weight` double
,`expense_type` varchar(50)
,`alias_name` varchar(50)
,`display_alias_name` int(1)
,`is_free` int(1)
,`allow_negative_stock` int(1)
,`specify_size` int(1)
,`size_to_specific_name` int(1)
,`category_name` varchar(50)
,`sub_category_name` varchar(50)
,`unit_symbol` varchar(5)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_item_detail_asset`
--
CREATE TABLE IF NOT EXISTS `view_item_detail_asset` (
`item_id` bigint(20)
,`item_code` varchar(50)
,`description` varchar(100)
,`category_id` int(11)
,`sub_category_id` int(11)
,`unit_id` int(11)
,`reorder_level` int(11)
,`unit_cost_price` double
,`unit_retailsale_price` double
,`unit_wholesale_price` double
,`is_suspended` varchar(3)
,`vat_rated` varchar(10)
,`item_img_url` varchar(100)
,`add_date` datetime
,`edit_date` datetime
,`item_type` varchar(7)
,`currency_code` varchar(10)
,`is_general` int(1)
,`asset_type` varchar(50)
,`is_buy` int(1)
,`is_sale` int(1)
,`is_track` int(1)
,`is_asset` int(1)
,`asset_account_code` varchar(20)
,`expense_account_code` varchar(20)
,`is_hire` int(1)
,`duration_type` varchar(20)
,`unit_hire_price` double
,`unit_special_price` double
,`unit_weight` double
,`expense_type` varchar(50)
,`alias_name` varchar(50)
,`display_alias_name` int(1)
,`is_free` int(1)
,`allow_negative_stock` int(1)
,`specify_size` int(1)
,`size_to_specific_name` int(1)
,`category_name` varchar(50)
,`sub_category_name` varchar(50)
,`unit_symbol` varchar(5)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_item_detail_expense`
--
CREATE TABLE IF NOT EXISTS `view_item_detail_expense` (
`item_id` bigint(20)
,`item_code` varchar(50)
,`description` varchar(100)
,`category_id` int(11)
,`sub_category_id` int(11)
,`unit_id` int(11)
,`reorder_level` int(11)
,`unit_cost_price` double
,`unit_retailsale_price` double
,`unit_wholesale_price` double
,`is_suspended` varchar(3)
,`vat_rated` varchar(10)
,`item_img_url` varchar(100)
,`add_date` datetime
,`edit_date` datetime
,`item_type` varchar(7)
,`currency_code` varchar(10)
,`is_general` int(1)
,`asset_type` varchar(50)
,`is_buy` int(1)
,`is_sale` int(1)
,`is_track` int(1)
,`is_asset` int(1)
,`asset_account_code` varchar(20)
,`expense_account_code` varchar(20)
,`is_hire` int(1)
,`duration_type` varchar(20)
,`unit_hire_price` double
,`unit_special_price` double
,`unit_weight` double
,`expense_type` varchar(50)
,`alias_name` varchar(50)
,`display_alias_name` int(1)
,`is_free` int(1)
,`allow_negative_stock` int(1)
,`specify_size` int(1)
,`size_to_specific_name` int(1)
,`category_name` varchar(50)
,`sub_category_name` varchar(50)
,`unit_symbol` varchar(5)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_item_detail_stock`
--
CREATE TABLE IF NOT EXISTS `view_item_detail_stock` (
`item_id` bigint(20)
,`item_code` varchar(50)
,`description` varchar(100)
,`category_id` int(11)
,`sub_category_id` int(11)
,`unit_id` int(11)
,`reorder_level` int(11)
,`unit_cost_price` double
,`unit_retailsale_price` double
,`unit_wholesale_price` double
,`is_suspended` varchar(3)
,`vat_rated` varchar(10)
,`item_img_url` varchar(100)
,`add_date` datetime
,`edit_date` datetime
,`item_type` varchar(7)
,`currency_code` varchar(10)
,`is_general` int(1)
,`asset_type` varchar(50)
,`is_buy` int(1)
,`is_sale` int(1)
,`is_track` int(1)
,`is_asset` int(1)
,`asset_account_code` varchar(20)
,`expense_account_code` varchar(20)
,`is_hire` int(1)
,`duration_type` varchar(20)
,`unit_hire_price` double
,`unit_special_price` double
,`unit_weight` double
,`expense_type` varchar(50)
,`alias_name` varchar(50)
,`display_alias_name` int(1)
,`is_free` int(1)
,`allow_negative_stock` int(1)
,`specify_size` int(1)
,`size_to_specific_name` int(1)
,`category_name` varchar(50)
,`sub_category_name` varchar(50)
,`unit_symbol` varchar(5)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_item_location`
--
CREATE TABLE IF NOT EXISTS `view_item_location` (
`item_location_id` bigint(20)
,`location_id` bigint(20)
,`item_id` bigint(20)
,`store_id` int(11)
,`location_name` varchar(20)
,`store_name` varchar(20)
,`description` varchar(100)
,`unit_id` int(11)
,`unit_name` varchar(50)
,`unit_symbol` varchar(5)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_acc_pay_balances`
--
CREATE TABLE IF NOT EXISTS `view_ledger_acc_pay_balances` (
`account_code` varchar(20)
,`bill_transactor_id` bigint(20)
,`currency_code` varchar(10)
,`debit_amount` double
,`credit_amount` double
,`debit_amount_lc` double
,`credit_amount_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_acc_pay_balances_old`
--
CREATE TABLE IF NOT EXISTS `view_ledger_acc_pay_balances_old` (
`account_code` varchar(20)
,`bill_transactor_id` bigint(20)
,`currency_code` varchar(10)
,`debit_amount` double
,`credit_amount` double
,`debit_amount_lc` double
,`credit_amount_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_acc_prepaid_expense_balances`
--
CREATE TABLE IF NOT EXISTS `view_ledger_acc_prepaid_expense_balances` (
`account_code` varchar(20)
,`bill_transactor_id` bigint(20)
,`currency_code` varchar(10)
,`debit_amount` double
,`credit_amount` double
,`debit_amount_lc` double
,`credit_amount_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_acc_prepaid_income_balances`
--
CREATE TABLE IF NOT EXISTS `view_ledger_acc_prepaid_income_balances` (
`account_code` varchar(20)
,`bill_transactor_id` bigint(20)
,`currency_code` varchar(10)
,`debit_amount` double
,`credit_amount` double
,`debit_amount_lc` double
,`credit_amount_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_acc_rec_balances`
--
CREATE TABLE IF NOT EXISTS `view_ledger_acc_rec_balances` (
`account_code` varchar(20)
,`bill_transactor_id` bigint(20)
,`currency_code` varchar(10)
,`debit_amount` double
,`credit_amount` double
,`debit_amount_lc` double
,`credit_amount_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_general`
--
CREATE TABLE IF NOT EXISTS `view_ledger_general` (
`acc_ledger_id` binary(0)
,`acc_period_id` int(11)
,`bill_transactor_id` binary(0)
,`account_code` varchar(20)
,`acc_child_account_id` binary(0)
,`currency_code` varchar(10)
,`debit_amount` double
,`credit_amount` double
,`debit_amount_lc` int(1)
,`credit_amount_lc` int(1)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_general_lc`
--
CREATE TABLE IF NOT EXISTS `view_ledger_general_lc` (
`acc_ledger_id` binary(0)
,`acc_period_id` int(11)
,`bill_transactor_id` binary(0)
,`account_code` varchar(20)
,`acc_child_account_id` binary(0)
,`currency_code` binary(0)
,`debit_amount` int(1)
,`credit_amount` int(1)
,`debit_amount_lc` double
,`credit_amount_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_general_lc_union_close`
--
CREATE TABLE IF NOT EXISTS `view_ledger_general_lc_union_close` (
`acc_ledger_id` binary(0)
,`acc_period_id` int(11)
,`bill_transactor_id` binary(0)
,`account_code` varchar(20)
,`acc_child_account_id` binary(0)
,`currency_code` binary(0)
,`debit_amount` bigint(20)
,`credit_amount` bigint(20)
,`debit_amount_lc` double
,`credit_amount_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_general_union_close`
--
CREATE TABLE IF NOT EXISTS `view_ledger_general_union_close` (
`acc_ledger_id` binary(0)
,`acc_period_id` int(11)
,`bill_transactor_id` binary(0)
,`account_code` varchar(20)
,`acc_child_account_id` binary(0)
,`currency_code` varchar(10)
,`debit_amount` double
,`credit_amount` double
,`debit_amount_lc` bigint(20)
,`credit_amount_lc` bigint(20)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_open_bal`
--
CREATE TABLE IF NOT EXISTS `view_ledger_open_bal` (
`acc_ledger_id` bigint(20)
,`acc_period_id` int(11)
,`bill_transactor_id` bigint(20)
,`account_code` varchar(20)
,`acc_child_account_id` int(11)
,`currency_code` varchar(10)
,`debit_amount` double
,`credit_amount` double
,`debit_amount_lc` double
,`credit_amount_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_temp_acc_balances`
--
CREATE TABLE IF NOT EXISTS `view_ledger_temp_acc_balances` (
`acc_period_id` int(11)
,`account_code` varchar(20)
,`currency_code` varchar(10)
,`debit_bal` double
,`credit_bal` double
,`debit_bal_lc` double
,`credit_bal_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_union_close_balances`
--
CREATE TABLE IF NOT EXISTS `view_ledger_union_close_balances` (
`acc_period_id` int(11)
,`bill_transactor_id` bigint(20)
,`account_code` varchar(20)
,`acc_child_account_id` int(11)
,`currency_code` varchar(10)
,`debit_bal` double
,`credit_bal` double
,`debit_bal_lc` double
,`credit_bal_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_union_close_detail`
--
CREATE TABLE IF NOT EXISTS `view_ledger_union_close_detail` (
`acc_ledger_id` bigint(20)
,`acc_period_id` int(11)
,`bill_transactor_id` bigint(20)
,`account_code` varchar(20)
,`acc_child_account_id` int(11)
,`currency_code` varchar(10)
,`debit_amount` double
,`credit_amount` double
,`debit_amount_lc` double
,`credit_amount_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_ledger_union_open_balances`
--
CREATE TABLE IF NOT EXISTS `view_ledger_union_open_balances` (
`acc_ledger_id` bigint(20)
,`acc_period_id` int(11)
,`bill_transactor_id` bigint(20)
,`account_code` varchar(20)
,`acc_child_account_id` int(11)
,`currency_code` varchar(10)
,`debit_amount` double
,`credit_amount` double
,`debit_amount_lc` double
,`credit_amount_lc` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_location`
--
CREATE TABLE IF NOT EXISTS `view_location` (
`location_id` bigint(20)
,`store_id` int(11)
,`location_name` varchar(20)
,`store_name` varchar(20)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_pay`
--
CREATE TABLE IF NOT EXISTS `view_pay` (
`pay_id` bigint(20)
,`pay_date` date
,`paid_amount` double
,`pay_method_id` int(11)
,`add_user_detail_id` int(11)
,`edit_user_detail_id` int(11)
,`add_date` datetime
,`edit_date` datetime
,`points_spent` double
,`points_spent_amount` double
,`delete_pay_id` bigint(20)
,`pay_ref_no` varchar(100)
,`pay_category` varchar(100)
,`bill_transactor_id` bigint(20)
,`store_id` int(11)
,`acc_child_account_id` int(11)
,`acc_child_account_id2` int(11)
,`currency_code` varchar(10)
,`xrate` double
,`status` int(1)
,`status_desc` varchar(100)
,`pay_type_id` int(11)
,`pay_reason_id` int(11)
,`principal_amount` double
,`interest_amount` double
,`pay_number` varchar(50)
,`add_user_names` varchar(201)
,`edit_user_names` varchar(201)
,`bill_transactor_names` varchar(100)
,`store_name` varchar(20)
,`pay_method_name` varchar(50)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_stock_all`
--
CREATE TABLE IF NOT EXISTS `view_stock_all` (
`item_id` bigint(20)
,`category_id` int(11)
,`sub_category_id` int(11)
,`currentqty` double
,`reorder_level` int(11)
,`description` varchar(100)
,`unit_id` int(11)
,`unit_name` varchar(50)
,`category_name` varchar(50)
,`sub_category_name` varchar(50)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_stock_difference`
--
CREATE TABLE IF NOT EXISTS `view_stock_difference` (
`item_id` bigint(20)
,`Description` varchar(100)
,`Unit` varchar(50)
,`CurrentQty` double
,`InQty` double
,`OutQty` double
,`ExpectedCurrentQty` double
,`ExcessQty` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_stock_dif_stg1`
--
CREATE TABLE IF NOT EXISTS `view_stock_dif_stg1` (
`item_id` bigint(20)
,`Description` varchar(100)
,`Unit` varchar(50)
,`CurrentQty` double
,`InQty` double
,`OutQty` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_stock_dif_stg2`
--
CREATE TABLE IF NOT EXISTS `view_stock_dif_stg2` (
`item_id` bigint(20)
,`Description` varchar(100)
,`Unit` varchar(50)
,`CurrentQty` double
,`InQty` double
,`OutQty` double
,`ExpectedCurrentQty` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_stock_dif_stg3`
--
CREATE TABLE IF NOT EXISTS `view_stock_dif_stg3` (
`item_id` bigint(20)
,`Description` varchar(100)
,`Unit` varchar(50)
,`CurrentQty` double
,`InQty` double
,`OutQty` double
,`ExpectedCurrentQty` double
,`ExcessQty` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_stock_in`
--
CREATE TABLE IF NOT EXISTS `view_stock_in` (
`item_id` bigint(20)
,`item_code` varchar(50)
,`description` varchar(100)
,`category_id` int(11)
,`sub_category_id` int(11)
,`unit_id` int(11)
,`reorder_level` int(11)
,`unit_cost_price` double
,`unit_retailsale_price` double
,`unit_wholesale_price` double
,`is_suspended` varchar(3)
,`vat_rated` varchar(10)
,`item_img_url` varchar(100)
,`add_date` datetime
,`edit_date` datetime
,`item_type` varchar(7)
,`currency_code` varchar(10)
,`is_general` int(1)
,`asset_type` varchar(50)
,`is_buy` int(1)
,`is_sale` int(1)
,`is_track` int(1)
,`is_asset` int(1)
,`asset_account_code` varchar(20)
,`expense_account_code` varchar(20)
,`is_hire` int(1)
,`duration_type` varchar(20)
,`unit_hire_price` double
,`unit_special_price` double
,`unit_weight` double
,`expense_type` varchar(50)
,`alias_name` varchar(50)
,`display_alias_name` int(1)
,`is_free` int(1)
,`allow_negative_stock` int(1)
,`specify_size` int(1)
,`size_to_specific_name` int(1)
,`store_id` int(11)
,`batchno` varchar(100)
,`currentqty` double
,`item_mnf_date` date
,`item_exp_date` date
,`store_name` varchar(20)
,`category_name` varchar(50)
,`unit_name` varchar(50)
,`sub_category_name` varchar(50)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_stock_total`
--
CREATE TABLE IF NOT EXISTS `view_stock_total` (
`store_id` int(11)
,`item_id` bigint(20)
,`category_id` int(11)
,`sub_category_id` int(11)
,`unit_cost_price` double
,`unit_retailsale_price` double
,`unit_wholesale_price` double
,`currentqty` double
,`cost_value` double
,`wholesale_value` double
,`retailsale_value` double
,`reorder_level` int(11)
,`store_name` varchar(20)
,`description` varchar(100)
,`unit_id` int(11)
,`unit_name` varchar(50)
,`category_name` varchar(50)
,`sub_category_name` varchar(50)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_stock_value`
--
CREATE TABLE IF NOT EXISTS `view_stock_value` (
`item_id` bigint(20)
,`batchno` varchar(100)
,`code_specific` varchar(50)
,`desc_specific` varchar(100)
,`currency_code` varchar(10)
,`currentqty` double
,`unit_cost_price` double
,`cp_value` double
,`wp_value` double
,`rp_value` double
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_sub_category`
--
CREATE TABLE IF NOT EXISTS `view_sub_category` (
`sub_category_id` int(11)
,`category_id` int(11)
,`sub_category_name` varchar(50)
,`category_name` varchar(50)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_transaction`
--
CREATE TABLE IF NOT EXISTS `view_transaction` (
`transaction_id` bigint(20)
,`transaction_date` date
,`store_id` int(11)
,`store2_id` int(11)
,`transactor_id` bigint(20)
,`transaction_type_id` int(11)
,`transaction_reason_id` int(11)
,`sub_total` double
,`total_trade_discount` double
,`total_vat` double
,`cash_discount` double
,`grand_total` double
,`transaction_ref` varchar(100)
,`transaction_comment` varchar(255)
,`add_user_detail_id` int(11)
,`add_date` datetime
,`edit_user_detail_id` int(11)
,`edit_date` datetime
,`points_awarded` double
,`card_number` varchar(10)
,`total_std_vatable_amount` double
,`total_zero_vatable_amount` double
,`total_exempt_vatable_amount` double
,`vat_perc` double
,`amount_tendered` double
,`change_amount` double
,`is_cash_discount_vat_liable` varchar(3)
,`total_profit_margin` double
,`transaction_user_detail_id` int(11)
,`bill_transactor_id` bigint(20)
,`scheme_transactor_id` bigint(20)
,`princ_scheme_member` varchar(100)
,`scheme_card_number` varchar(100)
,`transaction_number` varchar(50)
,`delivery_date` date
,`delivery_address` varchar(250)
,`pay_terms` varchar(250)
,`terms_conditions` varchar(250)
,`authorised_by_user_detail_id` int(11)
,`authorise_date` date
,`pay_due_date` date
,`expiry_date` date
,`acc_child_account_id` int(11)
,`currency_code` varchar(10)
,`xrate` double
,`from_date` datetime
,`to_date` datetime
,`duration_type` varchar(20)
,`duration_value` double
,`site_id` bigint(20)
,`transactor_rep` varchar(100)
,`transactor_vehicle` varchar(20)
,`transactor_driver` varchar(100)
,`location_id` bigint(20)
,`status_code` varchar(20)
,`status_date` datetime
,`delivery_mode` varchar(20)
,`is_processed` int(1)
,`is_paid` int(1)
,`is_cancel` int(1)
,`is_invoiced` int(1)
,`store_name` varchar(20)
,`store_name2` varchar(20)
,`add_user_detail_name` varchar(201)
,`edit_user_detail_name` varchar(201)
,`transaction_user_detail_name` varchar(201)
,`transactor_names` varchar(100)
,`bill_transactor_names` varchar(100)
,`scheme_transactor_names` varchar(100)
,`transaction_type_name` varchar(50)
,`transaction_reason_name` varchar(50)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_transaction_item`
--
CREATE TABLE IF NOT EXISTS `view_transaction_item` (
`transaction_item_id` bigint(20)
,`transaction_id` bigint(20)
,`item_id` bigint(20)
,`batchno` varchar(100)
,`item_qty` double
,`unit_price` double
,`unit_trade_discount` double
,`unit_vat` double
,`amount` double
,`item_expiry_date` date
,`item_mnf_date` date
,`vat_rated` varchar(10)
,`vat_perc` double
,`unit_price_inc_vat` double
,`unit_price_exc_vat` double
,`amount_inc_vat` double
,`amount_exc_vat` double
,`stock_effect` varchar(1)
,`is_trade_discount_vat_liable` varchar(3)
,`unit_cost_price` double
,`unit_profit_margin` double
,`earn_perc` double
,`earn_amount` double
,`code_specific` varchar(100)
,`desc_specific` varchar(150)
,`desc_more` varchar(350)
,`warranty_desc` varchar(150)
,`warranty_expiry_date` date
,`account_code` varchar(20)
,`purchase_date` date
,`dep_start_date` date
,`dep_method_id` int(11)
,`dep_rate` double
,`average_method_id` int(11)
,`effective_life` int(3)
,`residual_value` double
,`narration` varchar(100)
,`qty_balance` double
,`duration_value` double
,`qty_damage` double
,`duration_passed` double
,`description` varchar(100)
,`unit_symbol` varchar(5)
,`category_id` int(11)
,`category_name` varchar(50)
,`display_quick_order` int(1)
,`list_rank` int(3)
,`princ_scheme_member` varchar(100)
,`scheme_card_number` varchar(100)
,`grand_total` double
,`store_id` int(11)
,`store2_id` int(11)
,`transaction_date` date
,`add_date` datetime
,`edit_date` datetime
,`scheme_transactor_id` bigint(20)
,`transactor_id` bigint(20)
,`bill_transactor_id` bigint(20)
,`transaction_type_id` int(11)
,`transaction_reason_id` int(11)
,`add_user_detail_id` int(11)
,`edit_user_detail_id` int(11)
,`transaction_user_detail_id` int(11)
,`store_name` varchar(20)
,`store_name2` varchar(20)
,`add_user_detail_name` varchar(201)
,`edit_user_detail_name` varchar(201)
,`transaction_user_detail_name` varchar(201)
,`transactor_names` varchar(100)
,`bill_transactor_names` varchar(100)
,`scheme_transactor_names` varchar(100)
,`transaction_type_name` varchar(50)
,`transaction_reason_name` varchar(50)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_transaction_total_paid`
--
CREATE TABLE IF NOT EXISTS `view_transaction_total_paid` (
`transaction_id` bigint(20)
,`transaction_date` date
,`store_id` int(11)
,`store2_id` int(11)
,`transactor_id` bigint(20)
,`transaction_type_id` int(11)
,`transaction_reason_id` int(11)
,`sub_total` double
,`total_trade_discount` double
,`total_vat` double
,`cash_discount` double
,`grand_total` double
,`transaction_ref` varchar(100)
,`transaction_comment` varchar(255)
,`add_user_detail_id` int(11)
,`add_date` datetime
,`edit_user_detail_id` int(11)
,`edit_date` datetime
,`points_awarded` double
,`card_number` varchar(10)
,`total_std_vatable_amount` double
,`total_zero_vatable_amount` double
,`total_exempt_vatable_amount` double
,`vat_perc` double
,`amount_tendered` double
,`change_amount` double
,`is_cash_discount_vat_liable` varchar(3)
,`total_profit_margin` double
,`transaction_user_detail_id` int(11)
,`bill_transactor_id` bigint(20)
,`scheme_transactor_id` bigint(20)
,`princ_scheme_member` varchar(100)
,`scheme_card_number` varchar(100)
,`transaction_number` varchar(50)
,`delivery_date` date
,`delivery_address` varchar(250)
,`pay_terms` varchar(250)
,`terms_conditions` varchar(250)
,`authorised_by_user_detail_id` int(11)
,`authorise_date` date
,`pay_due_date` date
,`expiry_date` date
,`acc_child_account_id` int(11)
,`currency_code` varchar(10)
,`xrate` double
,`from_date` datetime
,`to_date` datetime
,`duration_type` varchar(20)
,`duration_value` double
,`site_id` bigint(20)
,`transactor_rep` varchar(100)
,`transactor_vehicle` varchar(20)
,`transactor_driver` varchar(100)
,`location_id` bigint(20)
,`status_code` varchar(20)
,`status_date` datetime
,`delivery_mode` varchar(20)
,`is_processed` int(1)
,`is_paid` int(1)
,`is_cancel` int(1)
,`is_invoiced` int(1)
,`store_name` varchar(20)
,`store_name2` varchar(20)
,`add_user_detail_name` varchar(201)
,`edit_user_detail_name` varchar(201)
,`transaction_user_detail_name` varchar(201)
,`transactor_names` varchar(100)
,`bill_transactor_names` varchar(100)
,`scheme_transactor_names` varchar(100)
,`transaction_type_name` varchar(50)
,`transaction_reason_name` varchar(50)
,`total_paid` double
);
-- --------------------------------------------------------

--
-- Structure for view `transaction_item_bi`
--
DROP TABLE IF EXISTS `transaction_item_bi`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `transaction_item_bi` AS select `ti`.`transaction_item_id` AS `transaction_item_id`,`ti`.`transaction_id` AS `transaction_id`,`ti`.`item_id` AS `item_id`,`ti`.`batchno` AS `batchno`,`ti`.`item_qty` AS `item_qty`,`ti`.`unit_price` AS `unit_price`,`ti`.`unit_trade_discount` AS `unit_trade_discount`,`ti`.`unit_vat` AS `unit_vat`,`ti`.`amount` AS `amount`,`ti`.`item_expiry_date` AS `item_expiry_date`,`ti`.`item_mnf_date` AS `item_mnf_date`,`ti`.`vat_rated` AS `vat_rated`,`ti`.`vat_perc` AS `vat_perc`,`ti`.`unit_price_inc_vat` AS `unit_price_inc_vat`,`ti`.`unit_price_exc_vat` AS `unit_price_exc_vat`,`ti`.`amount_inc_vat` AS `amount_inc_vat`,`ti`.`amount_exc_vat` AS `amount_exc_vat`,`ti`.`stock_effect` AS `stock_effect`,`ti`.`is_trade_discount_vat_liable` AS `is_trade_discount_vat_liable`,`ti`.`unit_cost_price` AS `unit_cost_price`,`ti`.`unit_profit_margin` AS `unit_profit_margin`,`ti`.`earn_perc` AS `earn_perc`,`ti`.`earn_amount` AS `earn_amount`,`ti`.`code_specific` AS `code_specific`,`ti`.`desc_specific` AS `desc_specific`,`ti`.`desc_more` AS `desc_more`,`ti`.`warranty_desc` AS `warranty_desc`,`ti`.`warranty_expiry_date` AS `warranty_expiry_date`,`ti`.`account_code` AS `account_code`,`ti`.`purchase_date` AS `purchase_date`,`ti`.`dep_start_date` AS `dep_start_date`,`ti`.`dep_method_id` AS `dep_method_id`,`ti`.`dep_rate` AS `dep_rate`,`ti`.`average_method_id` AS `average_method_id`,`ti`.`effective_life` AS `effective_life`,`ti`.`residual_value` AS `residual_value`,`ti`.`narration` AS `narration`,`ti`.`qty_balance` AS `qty_balance`,`ti`.`duration_value` AS `duration_value`,`ti`.`qty_damage` AS `qty_damage`,`ti`.`duration_passed` AS `duration_passed`,(`t`.`cash_discount` / (select count(`ti2`.`transaction_id`) from `transaction_item` `ti2` where (`ti2`.`transaction_id` = `ti`.`transaction_id`))) AS `unit_cash_discount` from (`transaction_item` `ti` join `transaction` `t` on((`ti`.`transaction_id` = `t`.`transaction_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_fact_expenses`
--
DROP TABLE IF EXISTS `view_fact_expenses`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_fact_expenses` AS select `transaction`.`transaction_type_id` AS `transaction_type_id`,`transaction`.`transaction_reason_id` AS `transaction_reason_id`,`transaction`.`transaction_id` AS `transaction_id`,`transaction`.`transaction_date` AS `transaction_date`,year(`transaction`.`transaction_date`) AS `y`,month(`transaction`.`transaction_date`) AS `m`,dayofmonth(`transaction`.`transaction_date`) AS `d`,(`transaction`.`grand_total` * `transaction`.`xrate`) AS `amount` from `transaction` where ((`transaction`.`transaction_type_id` in (1,19)) and (`transaction`.`grand_total` > 0));

-- --------------------------------------------------------

--
-- Structure for view `view_fact_expenses_items`
--
DROP TABLE IF EXISTS `view_fact_expenses_items`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_fact_expenses_items` AS select `t`.`transaction_type_id` AS `transaction_type_id`,`t`.`transaction_reason_id` AS `transaction_reason_id`,`t`.`transaction_id` AS `transaction_id`,`ti`.`item_id` AS `item_id`,`ti`.`item_qty` AS `item_qty`,(`ti`.`amount_inc_vat` * `t`.`xrate`) AS `amount`,`t`.`transaction_date` AS `transaction_date`,year(`t`.`transaction_date`) AS `y`,month(`t`.`transaction_date`) AS `m`,dayofmonth(`t`.`transaction_date`) AS `d` from (`transaction_item` `ti` join `transaction` `t` on(((`ti`.`transaction_id` = `t`.`transaction_id`) and (`t`.`transaction_type_id` in (1,19)) and (`t`.`grand_total` > 0))));

-- --------------------------------------------------------

--
-- Structure for view `view_fact_sales`
--
DROP TABLE IF EXISTS `view_fact_sales`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_fact_sales` AS select `t`.`transaction_id` AS `transaction_id`,`t`.`transaction_date` AS `transaction_date`,year(`t`.`transaction_date`) AS `y`,month(`t`.`transaction_date`) AS `m`,dayofmonth(`t`.`transaction_date`) AS `d`,(`t`.`grand_total` * `t`.`xrate`) AS `amount`,`v`.`ic` AS `c` from (`transaction` `t` join `view_fact_trans_avg_items` `v` on((`t`.`transaction_id` = `v`.`tid`))) where ((`t`.`transaction_type_id` in (2,65,68)) and (`t`.`grand_total` > 0));

-- --------------------------------------------------------

--
-- Structure for view `view_fact_sales_items`
--
DROP TABLE IF EXISTS `view_fact_sales_items`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_fact_sales_items` AS select `t`.`transaction_id` AS `transaction_id`,`ti`.`item_id` AS `item_id`,`ti`.`item_qty` AS `item_qty`,(`ti`.`amount_inc_vat` * `t`.`xrate`) AS `amount`,`t`.`transaction_date` AS `transaction_date`,year(`t`.`transaction_date`) AS `y`,month(`t`.`transaction_date`) AS `m`,dayofmonth(`t`.`transaction_date`) AS `d` from (`transaction_item` `ti` join `transaction` `t` on(((`ti`.`transaction_id` = `t`.`transaction_id`) and (`t`.`transaction_type_id` in (2,65,68)) and (`t`.`grand_total` > 0))));

-- --------------------------------------------------------

--
-- Structure for view `view_fact_sales_no_items`
--
DROP TABLE IF EXISTS `view_fact_sales_no_items`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_fact_sales_no_items` AS select `transaction`.`transaction_id` AS `transaction_id`,`transaction`.`transaction_date` AS `transaction_date`,year(`transaction`.`transaction_date`) AS `y`,month(`transaction`.`transaction_date`) AS `m`,dayofmonth(`transaction`.`transaction_date`) AS `d`,(`transaction`.`grand_total` * `transaction`.`xrate`) AS `amount` from `transaction` where ((`transaction`.`transaction_type_id` in (2,65,68)) and (`transaction`.`grand_total` > 0));

-- --------------------------------------------------------

--
-- Structure for view `view_fact_sales_no_view`
--
DROP TABLE IF EXISTS `view_fact_sales_no_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_fact_sales_no_view` AS select `t`.`transaction_id` AS `transaction_id`,`t`.`transaction_date` AS `transaction_date`,year(`t`.`transaction_date`) AS `y`,month(`t`.`transaction_date`) AS `m`,dayofmonth(`t`.`transaction_date`) AS `d`,(`t`.`grand_total` * `t`.`xrate`) AS `amount`,(select count(0) from `transaction_item` `ti` where (`ti`.`transaction_id` = `t`.`transaction_id`)) AS `c` from `transaction` `t` where ((`t`.`transaction_type_id` in (2,65,68)) and (`t`.`grand_total` > 0));

-- --------------------------------------------------------

--
-- Structure for view `view_fact_trans_avg_items`
--
DROP TABLE IF EXISTS `view_fact_trans_avg_items`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_fact_trans_avg_items` AS select `transaction_item`.`transaction_id` AS `tid`,count(`transaction_item`.`transaction_id`) AS `ic` from `transaction_item` group by `transaction_item`.`transaction_id`;

-- --------------------------------------------------------

--
-- Structure for view `view_inventory_asset`
--
DROP TABLE IF EXISTS `view_inventory_asset`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_inventory_asset` AS select `s`.`stock_id` AS `stock_id`,`s`.`store_id` AS `store_id`,`s`.`item_id` AS `item_id`,`s`.`batchno` AS `batchno`,`s`.`currentqty` AS `currentqty`,`s`.`item_mnf_date` AS `item_mnf_date`,`s`.`item_exp_date` AS `item_exp_date`,`s`.`unit_cost` AS `unit_cost`,`s`.`code_specific` AS `code_specific`,`s`.`desc_specific` AS `desc_specific`,`s`.`desc_more` AS `desc_more`,`s`.`warranty_desc` AS `warranty_desc`,`s`.`warranty_expiry_date` AS `warranty_expiry_date`,`s`.`purchase_date` AS `purchase_date`,`s`.`dep_start_date` AS `dep_start_date`,`s`.`dep_method_id` AS `dep_method_id`,`s`.`dep_rate` AS `dep_rate`,`s`.`average_method_id` AS `average_method_id`,`s`.`effective_life` AS `effective_life`,`s`.`asset_status_id` AS `asset_status_id`,`s`.`asset_status_desc` AS `asset_status_desc`,`s`.`account_code` AS `account_code`,`s`.`residual_value` AS `residual_value`,`s`.`qty_damage` AS `qty_damage`,`i`.`item_code` AS `item_code`,`i`.`description` AS `description`,`i`.`currency_code` AS `currency_code`,`i`.`reorder_level` AS `reorder_level`,`i`.`category_id` AS `category_id`,`i`.`sub_category_id` AS `sub_category_id`,`s`.`unit_cost` AS `unit_cost_price`,`i`.`unit_retailsale_price` AS `unit_retailsale_price`,`i`.`unit_wholesale_price` AS `unit_wholesale_price`,`i`.`asset_type` AS `asset_type`,`t`.`store_name` AS `store_name`,`c`.`category_name` AS `category_name`,`u`.`unit_name` AS `unit_name`,`u`.`unit_symbol` AS `unit_symbol`,`sc`.`sub_category_name` AS `sub_category_name`,0 AS `cost_value`,0 AS `retailsale_value`,0 AS `wholesale_value` from (((((`stock` `s` join `item` `i` on(((`s`.`item_id` = `i`.`item_id`) and (`i`.`is_sale` = 0) and (`i`.`is_asset` = 1) and (`i`.`is_track` = 1)))) join `store` `t` on((`s`.`store_id` = `t`.`store_id`))) join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`))) left join `sub_category` `sc` on((`i`.`sub_category_id` = `sc`.`sub_category_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_inventory_expense`
--
DROP TABLE IF EXISTS `view_inventory_expense`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_inventory_expense` AS select `s`.`stock_id` AS `stock_id`,`s`.`store_id` AS `store_id`,`s`.`item_id` AS `item_id`,`s`.`batchno` AS `batchno`,`s`.`currentqty` AS `currentqty`,`s`.`item_mnf_date` AS `item_mnf_date`,`s`.`item_exp_date` AS `item_exp_date`,`s`.`unit_cost` AS `unit_cost`,`s`.`code_specific` AS `code_specific`,`s`.`desc_specific` AS `desc_specific`,`s`.`desc_more` AS `desc_more`,`s`.`warranty_desc` AS `warranty_desc`,`s`.`warranty_expiry_date` AS `warranty_expiry_date`,`s`.`purchase_date` AS `purchase_date`,`s`.`dep_start_date` AS `dep_start_date`,`s`.`dep_method_id` AS `dep_method_id`,`s`.`dep_rate` AS `dep_rate`,`s`.`average_method_id` AS `average_method_id`,`s`.`effective_life` AS `effective_life`,`s`.`asset_status_id` AS `asset_status_id`,`s`.`asset_status_desc` AS `asset_status_desc`,`s`.`account_code` AS `account_code`,`s`.`residual_value` AS `residual_value`,`s`.`qty_damage` AS `qty_damage`,`i`.`item_code` AS `item_code`,`i`.`description` AS `description`,`i`.`currency_code` AS `currency_code`,`i`.`reorder_level` AS `reorder_level`,`i`.`category_id` AS `category_id`,`i`.`sub_category_id` AS `sub_category_id`,`s`.`unit_cost` AS `unit_cost_price`,`i`.`unit_retailsale_price` AS `unit_retailsale_price`,`i`.`unit_wholesale_price` AS `unit_wholesale_price`,`t`.`store_name` AS `store_name`,`c`.`category_name` AS `category_name`,`u`.`unit_name` AS `unit_name`,`u`.`unit_symbol` AS `unit_symbol`,`sc`.`sub_category_name` AS `sub_category_name`,0 AS `cost_value`,0 AS `retailsale_value`,0 AS `wholesale_value` from (((((`stock` `s` join `item` `i` on(((`s`.`item_id` = `i`.`item_id`) and (`i`.`is_sale` = 0) and (`i`.`is_asset` = 0) and (`i`.`is_track` = 1) and (`i`.`is_buy` = 1)))) join `store` `t` on((`s`.`store_id` = `t`.`store_id`))) join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`))) left join `sub_category` `sc` on((`i`.`sub_category_id` = `sc`.`sub_category_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_inventory_stock`
--
DROP TABLE IF EXISTS `view_inventory_stock`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_inventory_stock` AS select `s`.`stock_id` AS `stock_id`,`s`.`store_id` AS `store_id`,`s`.`item_id` AS `item_id`,`s`.`batchno` AS `batchno`,`s`.`currentqty` AS `currentqty`,`s`.`item_mnf_date` AS `item_mnf_date`,`s`.`item_exp_date` AS `item_exp_date`,`s`.`unit_cost` AS `unit_cost`,`s`.`code_specific` AS `code_specific`,`s`.`desc_specific` AS `desc_specific`,`s`.`desc_more` AS `desc_more`,`s`.`warranty_desc` AS `warranty_desc`,`s`.`warranty_expiry_date` AS `warranty_expiry_date`,`s`.`purchase_date` AS `purchase_date`,`s`.`dep_start_date` AS `dep_start_date`,`s`.`dep_method_id` AS `dep_method_id`,`s`.`dep_rate` AS `dep_rate`,`s`.`average_method_id` AS `average_method_id`,`s`.`effective_life` AS `effective_life`,`s`.`asset_status_id` AS `asset_status_id`,`s`.`asset_status_desc` AS `asset_status_desc`,`s`.`account_code` AS `account_code`,`s`.`residual_value` AS `residual_value`,`s`.`qty_damage` AS `qty_damage`,`i`.`item_code` AS `item_code`,`i`.`description` AS `description`,`i`.`currency_code` AS `currency_code`,`i`.`reorder_level` AS `reorder_level`,`i`.`category_id` AS `category_id`,`i`.`sub_category_id` AS `sub_category_id`,`s`.`unit_cost` AS `unit_cost_price`,`i`.`unit_retailsale_price` AS `unit_retailsale_price`,`i`.`unit_wholesale_price` AS `unit_wholesale_price`,`t`.`store_name` AS `store_name`,`c`.`category_name` AS `category_name`,`u`.`unit_name` AS `unit_name`,`u`.`unit_symbol` AS `unit_symbol`,`sc`.`sub_category_name` AS `sub_category_name`,0 AS `cost_value`,0 AS `retailsale_value`,0 AS `wholesale_value` from (((((`stock` `s` join `item` `i` on(((`s`.`item_id` = `i`.`item_id`) and (`i`.`is_sale` = 1) and (`i`.`is_asset` = 0) and (`i`.`is_track` = 1)))) join `store` `t` on((`s`.`store_id` = `t`.`store_id`))) join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`))) left join `sub_category` `sc` on((`i`.`sub_category_id` = `sc`.`sub_category_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_item`
--
DROP TABLE IF EXISTS `view_item`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_item` AS select `i`.`item_id` AS `item_id`,`i`.`item_code` AS `item_code`,`i`.`description` AS `description`,`i`.`category_id` AS `category_id`,`i`.`sub_category_id` AS `sub_category_id`,`i`.`unit_id` AS `unit_id`,`i`.`reorder_level` AS `reorder_level`,`i`.`unit_cost_price` AS `unit_cost_price`,`i`.`unit_retailsale_price` AS `unit_retailsale_price`,`i`.`unit_wholesale_price` AS `unit_wholesale_price`,`i`.`is_suspended` AS `is_suspended`,`i`.`vat_rated` AS `vat_rated`,`i`.`item_img_url` AS `item_img_url`,`i`.`add_date` AS `add_date`,`i`.`edit_date` AS `edit_date`,`i`.`item_type` AS `item_type`,`i`.`currency_code` AS `currency_code`,`i`.`is_general` AS `is_general`,`i`.`asset_type` AS `asset_type`,`i`.`is_buy` AS `is_buy`,`i`.`is_sale` AS `is_sale`,`i`.`is_track` AS `is_track`,`i`.`is_asset` AS `is_asset`,`i`.`asset_account_code` AS `asset_account_code`,`i`.`expense_account_code` AS `expense_account_code`,`i`.`is_hire` AS `is_hire`,`i`.`duration_type` AS `duration_type`,`i`.`unit_hire_price` AS `unit_hire_price`,`i`.`unit_special_price` AS `unit_special_price`,`i`.`unit_weight` AS `unit_weight`,`i`.`expense_type` AS `expense_type`,`i`.`alias_name` AS `alias_name`,`i`.`display_alias_name` AS `display_alias_name`,`i`.`is_free` AS `is_free`,`i`.`allow_negative_stock` AS `allow_negative_stock`,`i`.`specify_size` AS `specify_size`,`i`.`size_to_specific_name` AS `size_to_specific_name`,`c`.`category_name` AS `category_name`,`sc`.`sub_category_name` AS `sub_category_name`,`u`.`unit_symbol` AS `unit_symbol` from (((`item` `i` join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`))) left join `sub_category` `sc` on((`i`.`sub_category_id` = `sc`.`sub_category_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_item_detail_asset`
--
DROP TABLE IF EXISTS `view_item_detail_asset`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_item_detail_asset` AS select `i`.`item_id` AS `item_id`,`i`.`item_code` AS `item_code`,`i`.`description` AS `description`,`i`.`category_id` AS `category_id`,`i`.`sub_category_id` AS `sub_category_id`,`i`.`unit_id` AS `unit_id`,`i`.`reorder_level` AS `reorder_level`,`i`.`unit_cost_price` AS `unit_cost_price`,`i`.`unit_retailsale_price` AS `unit_retailsale_price`,`i`.`unit_wholesale_price` AS `unit_wholesale_price`,`i`.`is_suspended` AS `is_suspended`,`i`.`vat_rated` AS `vat_rated`,`i`.`item_img_url` AS `item_img_url`,`i`.`add_date` AS `add_date`,`i`.`edit_date` AS `edit_date`,`i`.`item_type` AS `item_type`,`i`.`currency_code` AS `currency_code`,`i`.`is_general` AS `is_general`,`i`.`asset_type` AS `asset_type`,`i`.`is_buy` AS `is_buy`,`i`.`is_sale` AS `is_sale`,`i`.`is_track` AS `is_track`,`i`.`is_asset` AS `is_asset`,`i`.`asset_account_code` AS `asset_account_code`,`i`.`expense_account_code` AS `expense_account_code`,`i`.`is_hire` AS `is_hire`,`i`.`duration_type` AS `duration_type`,`i`.`unit_hire_price` AS `unit_hire_price`,`i`.`unit_special_price` AS `unit_special_price`,`i`.`unit_weight` AS `unit_weight`,`i`.`expense_type` AS `expense_type`,`i`.`alias_name` AS `alias_name`,`i`.`display_alias_name` AS `display_alias_name`,`i`.`is_free` AS `is_free`,`i`.`allow_negative_stock` AS `allow_negative_stock`,`i`.`specify_size` AS `specify_size`,`i`.`size_to_specific_name` AS `size_to_specific_name`,`c`.`category_name` AS `category_name`,`sc`.`sub_category_name` AS `sub_category_name`,`u`.`unit_symbol` AS `unit_symbol` from (((`item` `i` join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`))) left join `sub_category` `sc` on((`i`.`sub_category_id` = `sc`.`sub_category_id`))) where ((`i`.`is_sale` = 0) and (`i`.`is_asset` = 1));

-- --------------------------------------------------------

--
-- Structure for view `view_item_detail_expense`
--
DROP TABLE IF EXISTS `view_item_detail_expense`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_item_detail_expense` AS select `i`.`item_id` AS `item_id`,`i`.`item_code` AS `item_code`,`i`.`description` AS `description`,`i`.`category_id` AS `category_id`,`i`.`sub_category_id` AS `sub_category_id`,`i`.`unit_id` AS `unit_id`,`i`.`reorder_level` AS `reorder_level`,`i`.`unit_cost_price` AS `unit_cost_price`,`i`.`unit_retailsale_price` AS `unit_retailsale_price`,`i`.`unit_wholesale_price` AS `unit_wholesale_price`,`i`.`is_suspended` AS `is_suspended`,`i`.`vat_rated` AS `vat_rated`,`i`.`item_img_url` AS `item_img_url`,`i`.`add_date` AS `add_date`,`i`.`edit_date` AS `edit_date`,`i`.`item_type` AS `item_type`,`i`.`currency_code` AS `currency_code`,`i`.`is_general` AS `is_general`,`i`.`asset_type` AS `asset_type`,`i`.`is_buy` AS `is_buy`,`i`.`is_sale` AS `is_sale`,`i`.`is_track` AS `is_track`,`i`.`is_asset` AS `is_asset`,`i`.`asset_account_code` AS `asset_account_code`,`i`.`expense_account_code` AS `expense_account_code`,`i`.`is_hire` AS `is_hire`,`i`.`duration_type` AS `duration_type`,`i`.`unit_hire_price` AS `unit_hire_price`,`i`.`unit_special_price` AS `unit_special_price`,`i`.`unit_weight` AS `unit_weight`,`i`.`expense_type` AS `expense_type`,`i`.`alias_name` AS `alias_name`,`i`.`display_alias_name` AS `display_alias_name`,`i`.`is_free` AS `is_free`,`i`.`allow_negative_stock` AS `allow_negative_stock`,`i`.`specify_size` AS `specify_size`,`i`.`size_to_specific_name` AS `size_to_specific_name`,`c`.`category_name` AS `category_name`,`sc`.`sub_category_name` AS `sub_category_name`,`u`.`unit_symbol` AS `unit_symbol` from (((`item` `i` join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`))) left join `sub_category` `sc` on((`i`.`sub_category_id` = `sc`.`sub_category_id`))) where ((`i`.`is_sale` = 0) and (`i`.`is_asset` = 0));

-- --------------------------------------------------------

--
-- Structure for view `view_item_detail_stock`
--
DROP TABLE IF EXISTS `view_item_detail_stock`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_item_detail_stock` AS select `i`.`item_id` AS `item_id`,`i`.`item_code` AS `item_code`,`i`.`description` AS `description`,`i`.`category_id` AS `category_id`,`i`.`sub_category_id` AS `sub_category_id`,`i`.`unit_id` AS `unit_id`,`i`.`reorder_level` AS `reorder_level`,`i`.`unit_cost_price` AS `unit_cost_price`,`i`.`unit_retailsale_price` AS `unit_retailsale_price`,`i`.`unit_wholesale_price` AS `unit_wholesale_price`,`i`.`is_suspended` AS `is_suspended`,`i`.`vat_rated` AS `vat_rated`,`i`.`item_img_url` AS `item_img_url`,`i`.`add_date` AS `add_date`,`i`.`edit_date` AS `edit_date`,`i`.`item_type` AS `item_type`,`i`.`currency_code` AS `currency_code`,`i`.`is_general` AS `is_general`,`i`.`asset_type` AS `asset_type`,`i`.`is_buy` AS `is_buy`,`i`.`is_sale` AS `is_sale`,`i`.`is_track` AS `is_track`,`i`.`is_asset` AS `is_asset`,`i`.`asset_account_code` AS `asset_account_code`,`i`.`expense_account_code` AS `expense_account_code`,`i`.`is_hire` AS `is_hire`,`i`.`duration_type` AS `duration_type`,`i`.`unit_hire_price` AS `unit_hire_price`,`i`.`unit_special_price` AS `unit_special_price`,`i`.`unit_weight` AS `unit_weight`,`i`.`expense_type` AS `expense_type`,`i`.`alias_name` AS `alias_name`,`i`.`display_alias_name` AS `display_alias_name`,`i`.`is_free` AS `is_free`,`i`.`allow_negative_stock` AS `allow_negative_stock`,`i`.`specify_size` AS `specify_size`,`i`.`size_to_specific_name` AS `size_to_specific_name`,`c`.`category_name` AS `category_name`,`sc`.`sub_category_name` AS `sub_category_name`,`u`.`unit_symbol` AS `unit_symbol` from (((`item` `i` join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`))) left join `sub_category` `sc` on((`i`.`sub_category_id` = `sc`.`sub_category_id`))) where ((`i`.`is_sale` = 1) and (`i`.`is_asset` = 0));

-- --------------------------------------------------------

--
-- Structure for view `view_item_location`
--
DROP TABLE IF EXISTS `view_item_location`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_item_location` AS select `il`.`item_location_id` AS `item_location_id`,`il`.`location_id` AS `location_id`,`il`.`item_id` AS `item_id`,`l`.`store_id` AS `store_id`,`l`.`location_name` AS `location_name`,`s`.`store_name` AS `store_name`,`i`.`description` AS `description`,`u`.`unit_id` AS `unit_id`,`u`.`unit_name` AS `unit_name`,`u`.`unit_symbol` AS `unit_symbol` from ((((`item_location` `il` join `location` `l` on((`il`.`location_id` = `l`.`location_id`))) join `item` `i` on((`il`.`item_id` = `i`.`item_id`))) join `store` `s` on((`l`.`store_id` = `s`.`store_id`))) join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_acc_pay_balances`
--
DROP TABLE IF EXISTS `view_ledger_acc_pay_balances`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_acc_pay_balances` AS select `al`.`account_code` AS `account_code`,`al`.`bill_transactor_id` AS `bill_transactor_id`,`al`.`currency_code` AS `currency_code`,sum(`al`.`debit_amount`) AS `debit_amount`,sum(`al`.`credit_amount`) AS `credit_amount`,sum(`al`.`debit_amount_lc`) AS `debit_amount_lc`,sum(`al`.`credit_amount_lc`) AS `credit_amount_lc` from (`acc_ledger` `al` join `acc_coa` `ac` on((`al`.`account_code` = `ac`.`account_code`))) where (`al`.`account_code` like '2-00-000%') group by `al`.`account_code`,`al`.`bill_transactor_id`,`al`.`currency_code` having ((sum(`al`.`credit_amount`) - sum(`al`.`debit_amount`)) <> 0);

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_acc_pay_balances_old`
--
DROP TABLE IF EXISTS `view_ledger_acc_pay_balances_old`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_acc_pay_balances_old` AS select `al`.`account_code` AS `account_code`,`al`.`bill_transactor_id` AS `bill_transactor_id`,`al`.`currency_code` AS `currency_code`,sum(`al`.`debit_amount`) AS `debit_amount`,sum(`al`.`credit_amount`) AS `credit_amount`,sum(`al`.`debit_amount_lc`) AS `debit_amount_lc`,sum(`al`.`credit_amount_lc`) AS `credit_amount_lc` from (`view_ledger_union_open_balances` `al` join `acc_coa` `ac` on((`al`.`account_code` = `ac`.`account_code`))) where (`al`.`account_code` like '2-00-000%') group by `al`.`account_code`,`al`.`bill_transactor_id`,`al`.`currency_code` having ((sum(`al`.`credit_amount`) - sum(`al`.`debit_amount`)) <> 0);

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_acc_prepaid_expense_balances`
--
DROP TABLE IF EXISTS `view_ledger_acc_prepaid_expense_balances`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_acc_prepaid_expense_balances` AS select `al`.`account_code` AS `account_code`,`al`.`bill_transactor_id` AS `bill_transactor_id`,`al`.`currency_code` AS `currency_code`,sum(`al`.`debit_amount`) AS `debit_amount`,sum(`al`.`credit_amount`) AS `credit_amount`,sum(`al`.`debit_amount_lc`) AS `debit_amount_lc`,sum(`al`.`credit_amount_lc`) AS `credit_amount_lc` from (`acc_ledger` `al` join `acc_coa` `ac` on((`al`.`account_code` = `ac`.`account_code`))) where (`al`.`account_code` like '1-00-030%') group by `al`.`account_code`,`al`.`bill_transactor_id`,`al`.`currency_code` having ((sum(`al`.`debit_amount`) - sum(`al`.`credit_amount`)) <> 0);

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_acc_prepaid_income_balances`
--
DROP TABLE IF EXISTS `view_ledger_acc_prepaid_income_balances`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_acc_prepaid_income_balances` AS select `al`.`account_code` AS `account_code`,`al`.`bill_transactor_id` AS `bill_transactor_id`,`al`.`currency_code` AS `currency_code`,sum(`al`.`debit_amount`) AS `debit_amount`,sum(`al`.`credit_amount`) AS `credit_amount`,sum(`al`.`debit_amount_lc`) AS `debit_amount_lc`,sum(`al`.`credit_amount_lc`) AS `credit_amount_lc` from (`view_ledger_union_open_balances` `al` join `acc_coa` `ac` on((`al`.`account_code` = `ac`.`account_code`))) where (`al`.`account_code` = '2-00-000-070') group by `al`.`account_code`,`al`.`bill_transactor_id`,`al`.`currency_code` having ((sum(`al`.`credit_amount`) - sum(`al`.`debit_amount`)) <> 0);

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_acc_rec_balances`
--
DROP TABLE IF EXISTS `view_ledger_acc_rec_balances`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_acc_rec_balances` AS select `al`.`account_code` AS `account_code`,`al`.`bill_transactor_id` AS `bill_transactor_id`,`al`.`currency_code` AS `currency_code`,sum(`al`.`debit_amount`) AS `debit_amount`,sum(`al`.`credit_amount`) AS `credit_amount`,sum(`al`.`debit_amount_lc`) AS `debit_amount_lc`,sum(`al`.`credit_amount_lc`) AS `credit_amount_lc` from (`acc_ledger` `al` join `acc_coa` `ac` on((`al`.`account_code` = `ac`.`account_code`))) where (`al`.`account_code` like '1-00-010%') group by `al`.`account_code`,`al`.`bill_transactor_id`,`al`.`currency_code` having ((sum(`al`.`debit_amount`) - sum(`al`.`credit_amount`)) <> 0);

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_general`
--
DROP TABLE IF EXISTS `view_ledger_general`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_general` AS select NULL AS `acc_ledger_id`,`ac`.`acc_period_id` AS `acc_period_id`,NULL AS `bill_transactor_id`,`ac`.`account_code` AS `account_code`,NULL AS `acc_child_account_id`,`ac`.`currency_code` AS `currency_code`,sum(`ac`.`debit_amount`) AS `debit_amount`,sum(`ac`.`credit_amount`) AS `credit_amount`,0 AS `debit_amount_lc`,0 AS `credit_amount_lc` from `acc_ledger` `ac` group by `ac`.`acc_period_id`,`ac`.`account_code`,`ac`.`currency_code`;

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_general_lc`
--
DROP TABLE IF EXISTS `view_ledger_general_lc`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_general_lc` AS select NULL AS `acc_ledger_id`,`ac`.`acc_period_id` AS `acc_period_id`,NULL AS `bill_transactor_id`,`ac`.`account_code` AS `account_code`,NULL AS `acc_child_account_id`,NULL AS `currency_code`,0 AS `debit_amount`,0 AS `credit_amount`,sum(`ac`.`debit_amount_lc`) AS `debit_amount_lc`,sum(`ac`.`credit_amount_lc`) AS `credit_amount_lc` from `acc_ledger` `ac` group by `ac`.`acc_period_id`,`ac`.`account_code`;

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_general_lc_union_close`
--
DROP TABLE IF EXISTS `view_ledger_general_lc_union_close`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_general_lc_union_close` AS select NULL AS `acc_ledger_id`,`ac`.`acc_period_id` AS `acc_period_id`,NULL AS `bill_transactor_id`,`ac`.`account_code` AS `account_code`,NULL AS `acc_child_account_id`,NULL AS `currency_code`,0 AS `debit_amount`,0 AS `credit_amount`,sum(`ac`.`debit_amount_lc`) AS `debit_amount_lc`,sum(`ac`.`credit_amount_lc`) AS `credit_amount_lc` from `acc_ledger` `ac` group by `ac`.`acc_period_id`,`ac`.`account_code` union select NULL AS `acc_ledger_id`,`ac2`.`acc_period_id` AS `acc_period_id`,NULL AS `bill_transactor_id`,`ac2`.`account_code` AS `account_code`,NULL AS `acc_child_account_id`,NULL AS `currency_code`,0 AS `debit_amount`,0 AS `credit_amount`,sum(`ac2`.`debit_bal_lc`) AS `debit_amount_lc`,sum(`ac2`.`credit_bal_lc`) AS `credit_amount_lc` from `acc_ledger_close` `ac2` group by `ac2`.`acc_period_id`,`ac2`.`account_code`;

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_general_union_close`
--
DROP TABLE IF EXISTS `view_ledger_general_union_close`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_general_union_close` AS select NULL AS `acc_ledger_id`,`ac`.`acc_period_id` AS `acc_period_id`,NULL AS `bill_transactor_id`,`ac`.`account_code` AS `account_code`,NULL AS `acc_child_account_id`,`ac`.`currency_code` AS `currency_code`,sum(`ac`.`debit_amount`) AS `debit_amount`,sum(`ac`.`credit_amount`) AS `credit_amount`,0 AS `debit_amount_lc`,0 AS `credit_amount_lc` from `acc_ledger` `ac` group by `ac`.`acc_period_id`,`ac`.`account_code`,`ac`.`currency_code` union select NULL AS `acc_ledger_id`,`ac2`.`acc_period_id` AS `acc_period_id`,NULL AS `bill_transactor_id`,`ac2`.`account_code` AS `account_code`,NULL AS `acc_child_account_id`,`ac2`.`currency_code` AS `currency_code`,sum(`ac2`.`debit_bal`) AS `debit_amount`,sum(`ac2`.`credit_bal`) AS `credit_amount`,0 AS `debit_amount_lc`,0 AS `credit_amount_lc` from `acc_ledger_close` `ac2` group by `ac2`.`acc_period_id`,`ac2`.`account_code`,`ac2`.`currency_code`;

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_open_bal`
--
DROP TABLE IF EXISTS `view_ledger_open_bal`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_open_bal` AS select `acc_ledger_open_bal`.`acc_ledger_open_bal_id` AS `acc_ledger_id`,`acc_ledger_open_bal`.`acc_period_id` AS `acc_period_id`,`acc_ledger_open_bal`.`bill_transactor_id` AS `bill_transactor_id`,`acc_ledger_open_bal`.`account_code` AS `account_code`,`acc_ledger_open_bal`.`acc_child_account_id` AS `acc_child_account_id`,`acc_ledger_open_bal`.`currency_code` AS `currency_code`,`acc_ledger_open_bal`.`debit_amount` AS `debit_amount`,`acc_ledger_open_bal`.`credit_amount` AS `credit_amount`,`acc_ledger_open_bal`.`debit_amount_lc` AS `debit_amount_lc`,`acc_ledger_open_bal`.`credit_amount_lc` AS `credit_amount_lc` from `acc_ledger_open_bal`;

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_temp_acc_balances`
--
DROP TABLE IF EXISTS `view_ledger_temp_acc_balances`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_temp_acc_balances` AS select `acc_ledger`.`acc_period_id` AS `acc_period_id`,`acc_ledger`.`account_code` AS `account_code`,`acc_ledger`.`currency_code` AS `currency_code`,(sum(`acc_ledger`.`debit_amount`) - sum(`acc_ledger`.`credit_amount`)) AS `debit_bal`,(sum(`acc_ledger`.`credit_amount`) - sum(`acc_ledger`.`debit_amount`)) AS `credit_bal`,(sum(`acc_ledger`.`debit_amount_lc`) - sum(`acc_ledger`.`credit_amount_lc`)) AS `debit_bal_lc`,(sum(`acc_ledger`.`credit_amount_lc`) - sum(`acc_ledger`.`debit_amount_lc`)) AS `credit_bal_lc` from `acc_ledger` where ((`acc_ledger`.`account_code` like '4%') or (`acc_ledger`.`account_code` like '5%') or (`acc_ledger`.`account_code` = '3-10-000-070') or (`acc_ledger`.`account_code` = '3-10-000-080')) group by `acc_ledger`.`acc_period_id`,`acc_ledger`.`account_code`,`acc_ledger`.`currency_code`;

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_union_close_balances`
--
DROP TABLE IF EXISTS `view_ledger_union_close_balances`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_union_close_balances` AS select `view_ledger_union_close_detail`.`acc_period_id` AS `acc_period_id`,`view_ledger_union_close_detail`.`bill_transactor_id` AS `bill_transactor_id`,`view_ledger_union_close_detail`.`account_code` AS `account_code`,`view_ledger_union_close_detail`.`acc_child_account_id` AS `acc_child_account_id`,`view_ledger_union_close_detail`.`currency_code` AS `currency_code`,(sum(`view_ledger_union_close_detail`.`debit_amount`) - sum(`view_ledger_union_close_detail`.`credit_amount`)) AS `debit_bal`,(sum(`view_ledger_union_close_detail`.`credit_amount`) - sum(`view_ledger_union_close_detail`.`debit_amount`)) AS `credit_bal`,(sum(`view_ledger_union_close_detail`.`debit_amount_lc`) - sum(`view_ledger_union_close_detail`.`credit_amount_lc`)) AS `debit_bal_lc`,(sum(`view_ledger_union_close_detail`.`credit_amount_lc`) - sum(`view_ledger_union_close_detail`.`debit_amount_lc`)) AS `credit_bal_lc` from `view_ledger_union_close_detail` group by `view_ledger_union_close_detail`.`acc_period_id`,`view_ledger_union_close_detail`.`bill_transactor_id`,`view_ledger_union_close_detail`.`account_code`,`view_ledger_union_close_detail`.`acc_child_account_id`,`view_ledger_union_close_detail`.`currency_code`;

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_union_close_detail`
--
DROP TABLE IF EXISTS `view_ledger_union_close_detail`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_union_close_detail` AS select `ac`.`acc_ledger_id` AS `acc_ledger_id`,`ac`.`acc_period_id` AS `acc_period_id`,`ac`.`bill_transactor_id` AS `bill_transactor_id`,`ac`.`account_code` AS `account_code`,`ac`.`acc_child_account_id` AS `acc_child_account_id`,`ac`.`currency_code` AS `currency_code`,`ac`.`debit_amount` AS `debit_amount`,`ac`.`credit_amount` AS `credit_amount`,`ac`.`debit_amount_lc` AS `debit_amount_lc`,`ac`.`credit_amount_lc` AS `credit_amount_lc` from `acc_ledger` `ac` union select `ac2`.`acc_ledger_close_id` AS `acc_ledger_id`,`ac2`.`acc_period_id` AS `acc_period_id`,NULL AS `bill_transactor_id`,`ac2`.`account_code` AS `account_code`,NULL AS `acc_child_account_id`,`ac2`.`currency_code` AS `currency_code`,`ac2`.`debit_bal` AS `debit_amount`,`ac2`.`credit_bal` AS `credit_amount`,`ac2`.`debit_bal_lc` AS `debit_amount_lc`,`ac2`.`credit_bal_lc` AS `credit_amount_lc` from `acc_ledger_close` `ac2`;

-- --------------------------------------------------------

--
-- Structure for view `view_ledger_union_open_balances`
--
DROP TABLE IF EXISTS `view_ledger_union_open_balances`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_ledger_union_open_balances` AS select `acc_ledger`.`acc_ledger_id` AS `acc_ledger_id`,`acc_ledger`.`acc_period_id` AS `acc_period_id`,`acc_ledger`.`bill_transactor_id` AS `bill_transactor_id`,`acc_ledger`.`account_code` AS `account_code`,`acc_ledger`.`acc_child_account_id` AS `acc_child_account_id`,`acc_ledger`.`currency_code` AS `currency_code`,`acc_ledger`.`debit_amount` AS `debit_amount`,`acc_ledger`.`credit_amount` AS `credit_amount`,`acc_ledger`.`debit_amount_lc` AS `debit_amount_lc`,`acc_ledger`.`credit_amount_lc` AS `credit_amount_lc` from `acc_ledger` union select `acc_ledger_open_bal`.`acc_ledger_open_bal_id` AS `acc_ledger_id`,`acc_ledger_open_bal`.`acc_period_id` AS `acc_period_id`,`acc_ledger_open_bal`.`bill_transactor_id` AS `bill_transactor_id`,`acc_ledger_open_bal`.`account_code` AS `account_code`,`acc_ledger_open_bal`.`acc_child_account_id` AS `acc_child_account_id`,`acc_ledger_open_bal`.`currency_code` AS `currency_code`,`acc_ledger_open_bal`.`debit_amount` AS `debit_amount`,`acc_ledger_open_bal`.`credit_amount` AS `credit_amount`,`acc_ledger_open_bal`.`debit_amount_lc` AS `debit_amount_lc`,`acc_ledger_open_bal`.`credit_amount_lc` AS `credit_amount_lc` from `acc_ledger_open_bal`;

-- --------------------------------------------------------

--
-- Structure for view `view_location`
--
DROP TABLE IF EXISTS `view_location`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_location` AS select `l`.`location_id` AS `location_id`,`l`.`store_id` AS `store_id`,`l`.`location_name` AS `location_name`,`s`.`store_name` AS `store_name` from (`location` `l` join `store` `s` on((`l`.`store_id` = `s`.`store_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_pay`
--
DROP TABLE IF EXISTS `view_pay`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_pay` AS select `pa`.`pay_id` AS `pay_id`,`pa`.`pay_date` AS `pay_date`,`pa`.`paid_amount` AS `paid_amount`,`pa`.`pay_method_id` AS `pay_method_id`,`pa`.`add_user_detail_id` AS `add_user_detail_id`,`pa`.`edit_user_detail_id` AS `edit_user_detail_id`,`pa`.`add_date` AS `add_date`,`pa`.`edit_date` AS `edit_date`,`pa`.`points_spent` AS `points_spent`,`pa`.`points_spent_amount` AS `points_spent_amount`,`pa`.`delete_pay_id` AS `delete_pay_id`,`pa`.`pay_ref_no` AS `pay_ref_no`,`pa`.`pay_category` AS `pay_category`,`pa`.`bill_transactor_id` AS `bill_transactor_id`,`pa`.`store_id` AS `store_id`,`pa`.`acc_child_account_id` AS `acc_child_account_id`,`pa`.`acc_child_account_id2` AS `acc_child_account_id2`,`pa`.`currency_code` AS `currency_code`,`pa`.`xrate` AS `xrate`,`pa`.`status` AS `status`,`pa`.`status_desc` AS `status_desc`,`pa`.`pay_type_id` AS `pay_type_id`,`pa`.`pay_reason_id` AS `pay_reason_id`,`pa`.`principal_amount` AS `principal_amount`,`pa`.`interest_amount` AS `interest_amount`,`pa`.`pay_number` AS `pay_number`,concat(`ud`.`first_name`,' ',`ud`.`second_name`) AS `add_user_names`,concat(`ud2`.`first_name`,' ',`ud2`.`second_name`) AS `edit_user_names`,`tc`.`transactor_names` AS `bill_transactor_names`,`st`.`store_name` AS `store_name`,`pm`.`pay_method_name` AS `pay_method_name` from (((((`pay` `pa` join `store` `st` on((`pa`.`store_id` = `st`.`store_id`))) join `user_detail` `ud` on((`pa`.`add_user_detail_id` = `ud`.`user_detail_id`))) join `pay_method` `pm` on((`pa`.`pay_method_id` = `pm`.`pay_method_id`))) left join `user_detail` `ud2` on((`pa`.`edit_user_detail_id` = `ud2`.`user_detail_id`))) left join `transactor` `tc` on((`pa`.`bill_transactor_id` = `tc`.`transactor_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_stock_all`
--
DROP TABLE IF EXISTS `view_stock_all`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_stock_all` AS select `i`.`item_id` AS `item_id`,`i`.`category_id` AS `category_id`,`i`.`sub_category_id` AS `sub_category_id`,ifnull(sum(`s`.`currentqty`),0) AS `currentqty`,`i`.`reorder_level` AS `reorder_level`,`i`.`description` AS `description`,`u`.`unit_id` AS `unit_id`,`u`.`unit_name` AS `unit_name`,`c`.`category_name` AS `category_name`,`sc`.`sub_category_name` AS `sub_category_name` from ((((`item` `i` join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`))) join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) left join `sub_category` `sc` on((`i`.`sub_category_id` = `sc`.`sub_category_id`))) left join `stock` `s` on((`i`.`item_id` = `s`.`item_id`))) group by `i`.`item_id` order by `i`.`description`;

-- --------------------------------------------------------

--
-- Structure for view `view_stock_difference`
--
DROP TABLE IF EXISTS `view_stock_difference`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_stock_difference` AS select `sd`.`item_id` AS `item_id`,`sd`.`Description` AS `Description`,`sd`.`Unit` AS `Unit`,`sd`.`CurrentQty` AS `CurrentQty`,`sd`.`InQty` AS `InQty`,`sd`.`OutQty` AS `OutQty`,`sd`.`ExpectedCurrentQty` AS `ExpectedCurrentQty`,`sd`.`ExcessQty` AS `ExcessQty` from `view_stock_dif_stg3` `sd` where (`sd`.`ExcessQty` <> 0);

-- --------------------------------------------------------

--
-- Structure for view `view_stock_dif_stg1`
--
DROP TABLE IF EXISTS `view_stock_dif_stg1`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_stock_dif_stg1` AS select `s`.`item_id` AS `item_id`,(select `i`.`description` from `item` `i` where (`i`.`item_id` = `s`.`item_id`)) AS `Description`,(select `u`.`unit_name` from (`item` `i2` join `unit` `u`) where ((`i2`.`item_id` = `s`.`item_id`) and (`i2`.`unit_id` = `u`.`unit_id`))) AS `Unit`,`s`.`currentqty` AS `CurrentQty`,(select sum(`ti1`.`item_qty`) from `view_transaction_item` `ti1` where ((`ti1`.`item_id` = `s`.`item_id`) and (((`ti1`.`transaction_type_id` = 1) and (`ti1`.`store_id` = 1)) or ((`ti1`.`transaction_type_id` = 7) and (`ti1`.`stock_effect` = 'C') and (`ti1`.`store_id` = 1)) or ((`ti1`.`transaction_type_id` = 4) and (`ti1`.`store2_id` = 1))))) AS `InQty`,(select sum(`ti2`.`item_qty`) from `view_transaction_item` `ti2` where ((`ti2`.`item_id` = `s`.`item_id`) and (((`ti2`.`transaction_type_id` in (3,2,4)) and (`ti2`.`store_id` = 1)) or ((`ti2`.`transaction_type_id` = 7) and (`ti2`.`stock_effect` = 'D') and (`ti2`.`store_id` = 1))))) AS `OutQty` from `stock` `s` where (`s`.`store_id` = 1) order by `s`.`item_id` desc;

-- --------------------------------------------------------

--
-- Structure for view `view_stock_dif_stg2`
--
DROP TABLE IF EXISTS `view_stock_dif_stg2`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_stock_dif_stg2` AS select `stg1`.`item_id` AS `item_id`,`stg1`.`Description` AS `Description`,`stg1`.`Unit` AS `Unit`,`stg1`.`CurrentQty` AS `CurrentQty`,`stg1`.`InQty` AS `InQty`,`stg1`.`OutQty` AS `OutQty`,(`stg1`.`InQty` - `stg1`.`OutQty`) AS `ExpectedCurrentQty` from `view_stock_dif_stg1` `stg1`;

-- --------------------------------------------------------

--
-- Structure for view `view_stock_dif_stg3`
--
DROP TABLE IF EXISTS `view_stock_dif_stg3`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_stock_dif_stg3` AS select `stg2`.`item_id` AS `item_id`,`stg2`.`Description` AS `Description`,`stg2`.`Unit` AS `Unit`,`stg2`.`CurrentQty` AS `CurrentQty`,`stg2`.`InQty` AS `InQty`,`stg2`.`OutQty` AS `OutQty`,`stg2`.`ExpectedCurrentQty` AS `ExpectedCurrentQty`,(`stg2`.`CurrentQty` - `stg2`.`ExpectedCurrentQty`) AS `ExcessQty` from `view_stock_dif_stg2` `stg2`;

-- --------------------------------------------------------

--
-- Structure for view `view_stock_in`
--
DROP TABLE IF EXISTS `view_stock_in`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_stock_in` AS select `i`.`item_id` AS `item_id`,`i`.`item_code` AS `item_code`,`i`.`description` AS `description`,`i`.`category_id` AS `category_id`,`i`.`sub_category_id` AS `sub_category_id`,`i`.`unit_id` AS `unit_id`,`i`.`reorder_level` AS `reorder_level`,`i`.`unit_cost_price` AS `unit_cost_price`,`i`.`unit_retailsale_price` AS `unit_retailsale_price`,`i`.`unit_wholesale_price` AS `unit_wholesale_price`,`i`.`is_suspended` AS `is_suspended`,`i`.`vat_rated` AS `vat_rated`,`i`.`item_img_url` AS `item_img_url`,`i`.`add_date` AS `add_date`,`i`.`edit_date` AS `edit_date`,`i`.`item_type` AS `item_type`,`i`.`currency_code` AS `currency_code`,`i`.`is_general` AS `is_general`,`i`.`asset_type` AS `asset_type`,`i`.`is_buy` AS `is_buy`,`i`.`is_sale` AS `is_sale`,`i`.`is_track` AS `is_track`,`i`.`is_asset` AS `is_asset`,`i`.`asset_account_code` AS `asset_account_code`,`i`.`expense_account_code` AS `expense_account_code`,`i`.`is_hire` AS `is_hire`,`i`.`duration_type` AS `duration_type`,`i`.`unit_hire_price` AS `unit_hire_price`,`i`.`unit_special_price` AS `unit_special_price`,`i`.`unit_weight` AS `unit_weight`,`i`.`expense_type` AS `expense_type`,`i`.`alias_name` AS `alias_name`,`i`.`display_alias_name` AS `display_alias_name`,`i`.`is_free` AS `is_free`,`i`.`allow_negative_stock` AS `allow_negative_stock`,`i`.`specify_size` AS `specify_size`,`i`.`size_to_specific_name` AS `size_to_specific_name`,`s`.`store_id` AS `store_id`,`s`.`batchno` AS `batchno`,`s`.`currentqty` AS `currentqty`,`s`.`item_mnf_date` AS `item_mnf_date`,`s`.`item_exp_date` AS `item_exp_date`,`t`.`store_name` AS `store_name`,`c`.`category_name` AS `category_name`,`u`.`unit_name` AS `unit_name`,`sc`.`sub_category_name` AS `sub_category_name` from (((((`stock` `s` join `item` `i` on((`s`.`item_id` = `i`.`item_id`))) join `store` `t` on((`s`.`store_id` = `t`.`store_id`))) join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`))) left join `sub_category` `sc` on((`i`.`sub_category_id` = `sc`.`sub_category_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_stock_total`
--
DROP TABLE IF EXISTS `view_stock_total`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_stock_total` AS select `t`.`store_id` AS `store_id`,`i`.`item_id` AS `item_id`,`i`.`category_id` AS `category_id`,`i`.`sub_category_id` AS `sub_category_id`,`i`.`unit_cost_price` AS `unit_cost_price`,`i`.`unit_retailsale_price` AS `unit_retailsale_price`,`i`.`unit_wholesale_price` AS `unit_wholesale_price`,ifnull(sum(`s`.`currentqty`),0) AS `currentqty`,ifnull((sum(`s`.`currentqty`) * `i`.`unit_cost_price`),0) AS `cost_value`,ifnull((sum(`s`.`currentqty`) * `i`.`unit_wholesale_price`),0) AS `wholesale_value`,ifnull((sum(`s`.`currentqty`) * `i`.`unit_retailsale_price`),0) AS `retailsale_value`,`i`.`reorder_level` AS `reorder_level`,`t`.`store_name` AS `store_name`,`i`.`description` AS `description`,`u`.`unit_id` AS `unit_id`,`u`.`unit_name` AS `unit_name`,`c`.`category_name` AS `category_name`,`sc`.`sub_category_name` AS `sub_category_name` from (((((`item` `i` join `unit` `u` on((`i`.`unit_id` = `u`.`unit_id`))) join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) left join `sub_category` `sc` on((`i`.`sub_category_id` = `sc`.`sub_category_id`))) left join `stock` `s` on((`i`.`item_id` = `s`.`item_id`))) left join `store` `t` on((`s`.`store_id` = `t`.`store_id`))) group by `t`.`store_id`,`i`.`item_id` order by `t`.`store_name`,`i`.`description`;

-- --------------------------------------------------------

--
-- Structure for view `view_stock_value`
--
DROP TABLE IF EXISTS `view_stock_value`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_stock_value` AS select `s`.`item_id` AS `item_id`,`s`.`batchno` AS `batchno`,`s`.`code_specific` AS `code_specific`,`s`.`desc_specific` AS `desc_specific`,`i`.`currency_code` AS `currency_code`,`s`.`currentqty` AS `currentqty`,`s`.`unit_cost` AS `unit_cost_price`,(`s`.`currentqty` * `s`.`unit_cost`) AS `cp_value`,(`s`.`currentqty` * `i`.`unit_wholesale_price`) AS `wp_value`,(`s`.`currentqty` * `i`.`unit_retailsale_price`) AS `rp_value` from (`stock` `s` join `item` `i` on((`s`.`item_id` = `i`.`item_id`))) where ((`i`.`is_suspended` <> 'Yes') and (`i`.`is_track` = 1) and (`i`.`is_sale` = 1) and (`i`.`is_asset` = 0));

-- --------------------------------------------------------

--
-- Structure for view `view_sub_category`
--
DROP TABLE IF EXISTS `view_sub_category`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_sub_category` AS select `s`.`sub_category_id` AS `sub_category_id`,`s`.`category_id` AS `category_id`,`s`.`sub_category_name` AS `sub_category_name`,`c`.`category_name` AS `category_name` from (`sub_category` `s` join `category` `c` on((`s`.`category_id` = `c`.`category_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_transaction`
--
DROP TABLE IF EXISTS `view_transaction`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_transaction` AS select `t`.`transaction_id` AS `transaction_id`,`t`.`transaction_date` AS `transaction_date`,`t`.`store_id` AS `store_id`,`t`.`store2_id` AS `store2_id`,`t`.`transactor_id` AS `transactor_id`,`t`.`transaction_type_id` AS `transaction_type_id`,`t`.`transaction_reason_id` AS `transaction_reason_id`,`t`.`sub_total` AS `sub_total`,`t`.`total_trade_discount` AS `total_trade_discount`,`t`.`total_vat` AS `total_vat`,`t`.`cash_discount` AS `cash_discount`,`t`.`grand_total` AS `grand_total`,`t`.`transaction_ref` AS `transaction_ref`,`t`.`transaction_comment` AS `transaction_comment`,`t`.`add_user_detail_id` AS `add_user_detail_id`,`t`.`add_date` AS `add_date`,`t`.`edit_user_detail_id` AS `edit_user_detail_id`,`t`.`edit_date` AS `edit_date`,`t`.`points_awarded` AS `points_awarded`,`t`.`card_number` AS `card_number`,`t`.`total_std_vatable_amount` AS `total_std_vatable_amount`,`t`.`total_zero_vatable_amount` AS `total_zero_vatable_amount`,`t`.`total_exempt_vatable_amount` AS `total_exempt_vatable_amount`,`t`.`vat_perc` AS `vat_perc`,`t`.`amount_tendered` AS `amount_tendered`,`t`.`change_amount` AS `change_amount`,`t`.`is_cash_discount_vat_liable` AS `is_cash_discount_vat_liable`,`t`.`total_profit_margin` AS `total_profit_margin`,`t`.`transaction_user_detail_id` AS `transaction_user_detail_id`,`t`.`bill_transactor_id` AS `bill_transactor_id`,`t`.`scheme_transactor_id` AS `scheme_transactor_id`,`t`.`princ_scheme_member` AS `princ_scheme_member`,`t`.`scheme_card_number` AS `scheme_card_number`,`t`.`transaction_number` AS `transaction_number`,`t`.`delivery_date` AS `delivery_date`,`t`.`delivery_address` AS `delivery_address`,`t`.`pay_terms` AS `pay_terms`,`t`.`terms_conditions` AS `terms_conditions`,`t`.`authorised_by_user_detail_id` AS `authorised_by_user_detail_id`,`t`.`authorise_date` AS `authorise_date`,`t`.`pay_due_date` AS `pay_due_date`,`t`.`expiry_date` AS `expiry_date`,`t`.`acc_child_account_id` AS `acc_child_account_id`,`t`.`currency_code` AS `currency_code`,`t`.`xrate` AS `xrate`,`t`.`from_date` AS `from_date`,`t`.`to_date` AS `to_date`,`t`.`duration_type` AS `duration_type`,`t`.`duration_value` AS `duration_value`,`t`.`site_id` AS `site_id`,`t`.`transactor_rep` AS `transactor_rep`,`t`.`transactor_vehicle` AS `transactor_vehicle`,`t`.`transactor_driver` AS `transactor_driver`,`t`.`location_id` AS `location_id`,`t`.`status_code` AS `status_code`,`t`.`status_date` AS `status_date`,`t`.`delivery_mode` AS `delivery_mode`,`t`.`is_processed` AS `is_processed`,`t`.`is_paid` AS `is_paid`,`t`.`is_cancel` AS `is_cancel`,`t`.`is_invoiced` AS `is_invoiced`,`s`.`store_name` AS `store_name`,`s2`.`store_name` AS `store_name2`,concat(`ud`.`first_name`,' ',`ud`.`second_name`) AS `add_user_detail_name`,concat(`ud2`.`first_name`,' ',`ud2`.`second_name`) AS `edit_user_detail_name`,concat(`ud3`.`first_name`,' ',`ud3`.`second_name`) AS `transaction_user_detail_name`,`tr`.`transactor_names` AS `transactor_names`,`tr2`.`transactor_names` AS `bill_transactor_names`,`tr3`.`transactor_names` AS `scheme_transactor_names`,`tt`.`transaction_type_name` AS `transaction_type_name`,`tn`.`transaction_reason_name` AS `transaction_reason_name` from ((((((((((`transaction` `t` join `store` `s` on((`t`.`store_id` = `s`.`store_id`))) join `transaction_type` `tt` on((`t`.`transaction_type_id` = `tt`.`transaction_type_id`))) join `transaction_reason` `tn` on((`t`.`transaction_reason_id` = `tn`.`transaction_reason_id`))) join `user_detail` `ud` on((`t`.`add_user_detail_id` = `ud`.`user_detail_id`))) left join `store` `s2` on((`t`.`store2_id` = `s2`.`store_id`))) left join `user_detail` `ud2` on((`t`.`edit_user_detail_id` = `ud2`.`user_detail_id`))) left join `user_detail` `ud3` on((`t`.`transaction_user_detail_id` = `ud3`.`user_detail_id`))) left join `transactor` `tr` on((`t`.`transactor_id` = `tr`.`transactor_id`))) left join `transactor` `tr2` on((`t`.`bill_transactor_id` = `tr2`.`transactor_id`))) left join `transactor` `tr3` on((`t`.`scheme_transactor_id` = `tr3`.`transactor_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_transaction_item`
--
DROP TABLE IF EXISTS `view_transaction_item`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_transaction_item` AS select `ti`.`transaction_item_id` AS `transaction_item_id`,`ti`.`transaction_id` AS `transaction_id`,`ti`.`item_id` AS `item_id`,`ti`.`batchno` AS `batchno`,`ti`.`item_qty` AS `item_qty`,`ti`.`unit_price` AS `unit_price`,`ti`.`unit_trade_discount` AS `unit_trade_discount`,`ti`.`unit_vat` AS `unit_vat`,`ti`.`amount` AS `amount`,`ti`.`item_expiry_date` AS `item_expiry_date`,`ti`.`item_mnf_date` AS `item_mnf_date`,`ti`.`vat_rated` AS `vat_rated`,`ti`.`vat_perc` AS `vat_perc`,`ti`.`unit_price_inc_vat` AS `unit_price_inc_vat`,`ti`.`unit_price_exc_vat` AS `unit_price_exc_vat`,`ti`.`amount_inc_vat` AS `amount_inc_vat`,`ti`.`amount_exc_vat` AS `amount_exc_vat`,`ti`.`stock_effect` AS `stock_effect`,`ti`.`is_trade_discount_vat_liable` AS `is_trade_discount_vat_liable`,`ti`.`unit_cost_price` AS `unit_cost_price`,`ti`.`unit_profit_margin` AS `unit_profit_margin`,`ti`.`earn_perc` AS `earn_perc`,`ti`.`earn_amount` AS `earn_amount`,`ti`.`code_specific` AS `code_specific`,`ti`.`desc_specific` AS `desc_specific`,`ti`.`desc_more` AS `desc_more`,`ti`.`warranty_desc` AS `warranty_desc`,`ti`.`warranty_expiry_date` AS `warranty_expiry_date`,`ti`.`account_code` AS `account_code`,`ti`.`purchase_date` AS `purchase_date`,`ti`.`dep_start_date` AS `dep_start_date`,`ti`.`dep_method_id` AS `dep_method_id`,`ti`.`dep_rate` AS `dep_rate`,`ti`.`average_method_id` AS `average_method_id`,`ti`.`effective_life` AS `effective_life`,`ti`.`residual_value` AS `residual_value`,`ti`.`narration` AS `narration`,`ti`.`qty_balance` AS `qty_balance`,`ti`.`duration_value` AS `duration_value`,`ti`.`qty_damage` AS `qty_damage`,`ti`.`duration_passed` AS `duration_passed`,`i`.`description` AS `description`,`ut`.`unit_symbol` AS `unit_symbol`,`c`.`category_id` AS `category_id`,`c`.`category_name` AS `category_name`,`c`.`display_quick_order` AS `display_quick_order`,`c`.`list_rank` AS `list_rank`,`t`.`princ_scheme_member` AS `princ_scheme_member`,`t`.`scheme_card_number` AS `scheme_card_number`,`t`.`grand_total` AS `grand_total`,`t`.`store_id` AS `store_id`,`t`.`store2_id` AS `store2_id`,`t`.`transaction_date` AS `transaction_date`,`t`.`add_date` AS `add_date`,`t`.`edit_date` AS `edit_date`,`t`.`scheme_transactor_id` AS `scheme_transactor_id`,`t`.`transactor_id` AS `transactor_id`,`t`.`bill_transactor_id` AS `bill_transactor_id`,`t`.`transaction_type_id` AS `transaction_type_id`,`t`.`transaction_reason_id` AS `transaction_reason_id`,`t`.`add_user_detail_id` AS `add_user_detail_id`,`t`.`edit_user_detail_id` AS `edit_user_detail_id`,`t`.`transaction_user_detail_id` AS `transaction_user_detail_id`,`s`.`store_name` AS `store_name`,`s2`.`store_name` AS `store_name2`,concat(`ud`.`first_name`,' ',`ud`.`second_name`) AS `add_user_detail_name`,concat(`ud2`.`first_name`,' ',`ud2`.`second_name`) AS `edit_user_detail_name`,concat(`ud3`.`first_name`,' ',`ud3`.`second_name`) AS `transaction_user_detail_name`,`tr`.`transactor_names` AS `transactor_names`,`tr2`.`transactor_names` AS `bill_transactor_names`,`tr3`.`transactor_names` AS `scheme_transactor_names`,`tt`.`transaction_type_name` AS `transaction_type_name`,`tn`.`transaction_reason_name` AS `transaction_reason_name` from ((((((((((((((`transaction_item` `ti` join `transaction` `t` on((`ti`.`transaction_id` = `t`.`transaction_id`))) join `store` `s` on((`t`.`store_id` = `s`.`store_id`))) join `transaction_type` `tt` on((`t`.`transaction_type_id` = `tt`.`transaction_type_id`))) join `transaction_reason` `tn` on((`t`.`transaction_reason_id` = `tn`.`transaction_reason_id`))) join `user_detail` `ud` on((`t`.`add_user_detail_id` = `ud`.`user_detail_id`))) join `item` `i` on((`ti`.`item_id` = `i`.`item_id`))) join `unit` `ut` on((`i`.`unit_id` = `ut`.`unit_id`))) join `category` `c` on((`i`.`category_id` = `c`.`category_id`))) left join `store` `s2` on((`t`.`store2_id` = `s2`.`store_id`))) left join `user_detail` `ud2` on((`t`.`edit_user_detail_id` = `ud2`.`user_detail_id`))) left join `user_detail` `ud3` on((`t`.`transaction_user_detail_id` = `ud3`.`user_detail_id`))) left join `transactor` `tr` on((`t`.`transactor_id` = `tr`.`transactor_id`))) left join `transactor` `tr2` on((`t`.`bill_transactor_id` = `tr2`.`transactor_id`))) left join `transactor` `tr3` on((`t`.`scheme_transactor_id` = `tr3`.`transactor_id`)));

-- --------------------------------------------------------

--
-- Structure for view `view_transaction_total_paid`
--
DROP TABLE IF EXISTS `view_transaction_total_paid`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_transaction_total_paid` AS select `t`.`transaction_id` AS `transaction_id`,`t`.`transaction_date` AS `transaction_date`,`t`.`store_id` AS `store_id`,`t`.`store2_id` AS `store2_id`,`t`.`transactor_id` AS `transactor_id`,`t`.`transaction_type_id` AS `transaction_type_id`,`t`.`transaction_reason_id` AS `transaction_reason_id`,`t`.`sub_total` AS `sub_total`,`t`.`total_trade_discount` AS `total_trade_discount`,`t`.`total_vat` AS `total_vat`,`t`.`cash_discount` AS `cash_discount`,`t`.`grand_total` AS `grand_total`,`t`.`transaction_ref` AS `transaction_ref`,`t`.`transaction_comment` AS `transaction_comment`,`t`.`add_user_detail_id` AS `add_user_detail_id`,`t`.`add_date` AS `add_date`,`t`.`edit_user_detail_id` AS `edit_user_detail_id`,`t`.`edit_date` AS `edit_date`,`t`.`points_awarded` AS `points_awarded`,`t`.`card_number` AS `card_number`,`t`.`total_std_vatable_amount` AS `total_std_vatable_amount`,`t`.`total_zero_vatable_amount` AS `total_zero_vatable_amount`,`t`.`total_exempt_vatable_amount` AS `total_exempt_vatable_amount`,`t`.`vat_perc` AS `vat_perc`,`t`.`amount_tendered` AS `amount_tendered`,`t`.`change_amount` AS `change_amount`,`t`.`is_cash_discount_vat_liable` AS `is_cash_discount_vat_liable`,`t`.`total_profit_margin` AS `total_profit_margin`,`t`.`transaction_user_detail_id` AS `transaction_user_detail_id`,`t`.`bill_transactor_id` AS `bill_transactor_id`,`t`.`scheme_transactor_id` AS `scheme_transactor_id`,`t`.`princ_scheme_member` AS `princ_scheme_member`,`t`.`scheme_card_number` AS `scheme_card_number`,`t`.`transaction_number` AS `transaction_number`,`t`.`delivery_date` AS `delivery_date`,`t`.`delivery_address` AS `delivery_address`,`t`.`pay_terms` AS `pay_terms`,`t`.`terms_conditions` AS `terms_conditions`,`t`.`authorised_by_user_detail_id` AS `authorised_by_user_detail_id`,`t`.`authorise_date` AS `authorise_date`,`t`.`pay_due_date` AS `pay_due_date`,`t`.`expiry_date` AS `expiry_date`,`t`.`acc_child_account_id` AS `acc_child_account_id`,`t`.`currency_code` AS `currency_code`,`t`.`xrate` AS `xrate`,`t`.`from_date` AS `from_date`,`t`.`to_date` AS `to_date`,`t`.`duration_type` AS `duration_type`,`t`.`duration_value` AS `duration_value`,`t`.`site_id` AS `site_id`,`t`.`transactor_rep` AS `transactor_rep`,`t`.`transactor_vehicle` AS `transactor_vehicle`,`t`.`transactor_driver` AS `transactor_driver`,`t`.`location_id` AS `location_id`,`t`.`status_code` AS `status_code`,`t`.`status_date` AS `status_date`,`t`.`delivery_mode` AS `delivery_mode`,`t`.`is_processed` AS `is_processed`,`t`.`is_paid` AS `is_paid`,`t`.`is_cancel` AS `is_cancel`,`t`.`is_invoiced` AS `is_invoiced`,`s`.`store_name` AS `store_name`,`s2`.`store_name` AS `store_name2`,concat(`ud`.`first_name`,' ',`ud`.`second_name`) AS `add_user_detail_name`,concat(`ud2`.`first_name`,' ',`ud2`.`second_name`) AS `edit_user_detail_name`,concat(`ud3`.`first_name`,' ',`ud3`.`second_name`) AS `transaction_user_detail_name`,`tr`.`transactor_names` AS `transactor_names`,`tr2`.`transactor_names` AS `bill_transactor_names`,`tr3`.`transactor_names` AS `scheme_transactor_names`,`tt`.`transaction_type_name` AS `transaction_type_name`,`tn`.`transaction_reason_name` AS `transaction_reason_name`,(select sum(`pt`.`trans_paid_amount`) from `pay_trans` `pt` where (`pt`.`transaction_id` = `t`.`transaction_id`)) AS `total_paid` from ((((((((((`transaction` `t` join `store` `s` on((`t`.`store_id` = `s`.`store_id`))) join `transaction_type` `tt` on((`t`.`transaction_type_id` = `tt`.`transaction_type_id`))) join `transaction_reason` `tn` on((`t`.`transaction_reason_id` = `tn`.`transaction_reason_id`))) join `user_detail` `ud` on((`t`.`add_user_detail_id` = `ud`.`user_detail_id`))) left join `store` `s2` on((`t`.`store2_id` = `s2`.`store_id`))) left join `user_detail` `ud2` on((`t`.`edit_user_detail_id` = `ud2`.`user_detail_id`))) left join `user_detail` `ud3` on((`t`.`transaction_user_detail_id` = `ud3`.`user_detail_id`))) left join `transactor` `tr` on((`t`.`transactor_id` = `tr`.`transactor_id`))) left join `transactor` `tr2` on((`t`.`bill_transactor_id` = `tr2`.`transactor_id`))) left join `transactor` `tr3` on((`t`.`scheme_transactor_id` = `tr3`.`transactor_id`)));

--
-- Constraints for dumped tables
--

--
-- Constraints for table `acc_category`
--
ALTER TABLE `acc_category`
  ADD CONSTRAINT `FKacc_catego335761` FOREIGN KEY (`acc_group_id`) REFERENCES `acc_group` (`acc_group_id`);

--
-- Constraints for table `acc_child_account`
--
ALTER TABLE `acc_child_account`
  ADD CONSTRAINT `FKacc_child_595280` FOREIGN KEY (`currency_id`) REFERENCES `acc_currency` (`acc_currency_id`),
  ADD CONSTRAINT `FKacc_child_932504` FOREIGN KEY (`acc_coa_id`) REFERENCES `acc_coa` (`acc_coa_id`);

--
-- Constraints for table `acc_coa`
--
ALTER TABLE `acc_coa`
  ADD CONSTRAINT `FKacc_coa196958` FOREIGN KEY (`acc_group_id`) REFERENCES `acc_group` (`acc_group_id`),
  ADD CONSTRAINT `FKacc_coa456341` FOREIGN KEY (`acc_class_id`) REFERENCES `acc_class` (`acc_class_id`),
  ADD CONSTRAINT `FKacc_coa603581` FOREIGN KEY (`acc_type_id`) REFERENCES `acc_type` (`acc_type_id`),
  ADD CONSTRAINT `FKacc_coa949787` FOREIGN KEY (`acc_category_id`) REFERENCES `acc_category` (`acc_category_id`);

--
-- Constraints for table `acc_group`
--
ALTER TABLE `acc_group`
  ADD CONSTRAINT `FKacc_group505319` FOREIGN KEY (`acc_type_id`) REFERENCES `acc_type` (`acc_type_id`);

--
-- Constraints for table `acc_journal`
--
ALTER TABLE `acc_journal`
  ADD CONSTRAINT `FKacc_journa25711` FOREIGN KEY (`acc_period_id`) REFERENCES `acc_period` (`acc_period_id`),
  ADD CONSTRAINT `FKacc_journa88937` FOREIGN KEY (`acc_child_account_id`) REFERENCES `acc_child_account` (`acc_child_account_id`),
  ADD CONSTRAINT `FKacc_journa956479` FOREIGN KEY (`acc_coa_id`) REFERENCES `acc_coa` (`acc_coa_id`);

--
-- Constraints for table `acc_xrate`
--
ALTER TABLE `acc_xrate`
  ADD CONSTRAINT `FKacc_xrate442932` FOREIGN KEY (`local_currency_id`) REFERENCES `acc_currency` (`acc_currency_id`),
  ADD CONSTRAINT `FKacc_xrate594846` FOREIGN KEY (`foreign_currency_id`) REFERENCES `acc_currency` (`acc_currency_id`);

--
-- Constraints for table `discount_package_item`
--
ALTER TABLE `discount_package_item`
  ADD CONSTRAINT `Item_to_PackItem_on_ItemId` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`),
  ADD CONSTRAINT `Pack_to_PackItem_on_PackId` FOREIGN KEY (`discount_package_id`) REFERENCES `discount_package` (`discount_package_id`),
  ADD CONSTRAINT `Store_to_DiscPackageItem_on_StoreId` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`);

--
-- Constraints for table `district`
--
ALTER TABLE `district`
  ADD CONSTRAINT `ctry_dist` FOREIGN KEY (`country_id`) REFERENCES `country` (`country_id`),
  ADD CONSTRAINT `region_district` FOREIGN KEY (`region_id`) REFERENCES `region` (`region_id`);

--
-- Constraints for table `group_right`
--
ALTER TABLE `group_right`
  ADD CONSTRAINT `GroupDetail_to_GroupRight_GroupDetailId` FOREIGN KEY (`group_detail_id`) REFERENCES `group_detail` (`group_detail_id`),
  ADD CONSTRAINT `Store_to_GroupRight_StoreId` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`);

--
-- Constraints for table `group_user`
--
ALTER TABLE `group_user`
  ADD CONSTRAINT `GroupDetail_to_GroupUser_GroupDetailId` FOREIGN KEY (`group_detail_id`) REFERENCES `group_detail` (`group_detail_id`),
  ADD CONSTRAINT `UserDetail_to_GroupUser_UserDetailId` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`user_detail_id`);

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `Unit_to_Item_on_UnitId2` FOREIGN KEY (`unit_id`) REFERENCES `unit` (`unit_id`),
  ADD CONSTRAINT `Cat_to_Item_on_CatId` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  ADD CONSTRAINT `SubCat_to_Item_on_SubCatId` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`sub_category_id`);

--
-- Constraints for table `item_location`
--
ALTER TABLE `item_location`
  ADD CONSTRAINT `Item_to_ItemLocation` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`),
  ADD CONSTRAINT `Location_to_ItemLocation` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`);

--
-- Constraints for table `item_map`
--
ALTER TABLE `item_map`
  ADD CONSTRAINT `Item_to_ItemMap_on_BigItemId` FOREIGN KEY (`big_item_id`) REFERENCES `item` (`item_id`),
  ADD CONSTRAINT `Item_to_ItemMap_on_SmallItemId` FOREIGN KEY (`small_item_id`) REFERENCES `item` (`item_id`);

--
-- Constraints for table `location`
--
ALTER TABLE `location`
  ADD CONSTRAINT `Store_to_Location` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`);

--
-- Constraints for table `login_session`
--
ALTER TABLE `login_session`
  ADD CONSTRAINT `Store_to_LoginSession` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`),
  ADD CONSTRAINT `UserDetail_to_LoginSession` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`user_detail_id`);

--
-- Constraints for table `pay`
--
ALTER TABLE `pay`
  ADD CONSTRAINT `PayMeth_to_Pay_on_PayMethId` FOREIGN KEY (`pay_method_id`) REFERENCES `pay_method` (`pay_method_id`),
  ADD CONSTRAINT `UserD_to_Pay_on_AddUserId` FOREIGN KEY (`add_user_detail_id`) REFERENCES `user_detail` (`user_detail_id`),
  ADD CONSTRAINT `UserD_to_Pay_on_EditUserId` FOREIGN KEY (`edit_user_detail_id`) REFERENCES `user_detail` (`user_detail_id`);

--
-- Constraints for table `pay_trans`
--
ALTER TABLE `pay_trans`
  ADD CONSTRAINT `FKpay_trans264324` FOREIGN KEY (`pay_id`) REFERENCES `pay` (`pay_id`);

--
-- Constraints for table `region`
--
ALTER TABLE `region`
  ADD CONSTRAINT `FKregion870345` FOREIGN KEY (`country_id`) REFERENCES `country` (`country_id`);

--
-- Constraints for table `salary_deduction`
--
ALTER TABLE `salary_deduction`
  ADD CONSTRAINT `FKsalary_ded739898` FOREIGN KEY (`transactor_id`) REFERENCES `transactor` (`transactor_id`);

--
-- Constraints for table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `Item_to_Stock_on_ItemId` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`),
  ADD CONSTRAINT `Store_to_Stock_on_StoreId` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`);

--
-- Constraints for table `sub_category`
--
ALTER TABLE `sub_category`
  ADD CONSTRAINT `Cat_to_SubCat_on_CatId` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`);

--
-- Constraints for table `town`
--
ALTER TABLE `town`
  ADD CONSTRAINT `district_town` FOREIGN KEY (`district_id`) REFERENCES `district` (`district_id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `Store_to_Trans_on_Store2Id` FOREIGN KEY (`store2_id`) REFERENCES `store` (`store_id`),
  ADD CONSTRAINT `Store_to_Trans_on_StoreId` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`),
  ADD CONSTRAINT `tractor_traction_scheme` FOREIGN KEY (`scheme_transactor_id`) REFERENCES `transactor` (`transactor_id`),
  ADD CONSTRAINT `Transactor_to_Trans_on_TransactorId` FOREIGN KEY (`transactor_id`) REFERENCES `transactor` (`transactor_id`),
  ADD CONSTRAINT `transactor_trans_bill` FOREIGN KEY (`bill_transactor_id`) REFERENCES `transactor` (`transactor_id`),
  ADD CONSTRAINT `TransReas_to_Trans_on_TransReasId` FOREIGN KEY (`transaction_reason_id`) REFERENCES `transaction_reason` (`transaction_reason_id`),
  ADD CONSTRAINT `TransType_to_Trans_on_TransTypeId` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_type` (`transaction_type_id`),
  ADD CONSTRAINT `UserD_to_Trans_on_AddUserId` FOREIGN KEY (`add_user_detail_id`) REFERENCES `user_detail` (`user_detail_id`),
  ADD CONSTRAINT `UserD_to_Trans_on_EditUserId` FOREIGN KEY (`edit_user_detail_id`) REFERENCES `user_detail` (`user_detail_id`),
  ADD CONSTRAINT `user_trans_aauthorisedby` FOREIGN KEY (`authorised_by_user_detail_id`) REFERENCES `user_detail` (`user_detail_id`);

--
-- Constraints for table `transaction_approve`
--
ALTER TABLE `transaction_approve`
  ADD CONSTRAINT `trans_to_transapprove_on_trans_id` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`),
  ADD CONSTRAINT `user_detail_to_transaction_approve_on_userdetailid` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`user_detail_id`);

--
-- Constraints for table `transaction_item`
--
ALTER TABLE `transaction_item`
  ADD CONSTRAINT `Item_to_TransItem_on_ItemId` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`),
  ADD CONSTRAINT `Trans_to_TransItem_on_TransId` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`);

--
-- Constraints for table `transaction_reason`
--
ALTER TABLE `transaction_reason`
  ADD CONSTRAINT `TransType_to_TransReas_on_TransReasId` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_type` (`transaction_type_id`);

--
-- Constraints for table `transactor`
--
ALTER TABLE `transactor`
  ADD CONSTRAINT `country_transactor` FOREIGN KEY (`loc_country_id`) REFERENCES `country` (`country_id`),
  ADD CONSTRAINT `district_transactor` FOREIGN KEY (`loc_district_id`) REFERENCES `district` (`district_id`),
  ADD CONSTRAINT `town_transactor` FOREIGN KEY (`loc_town_id`) REFERENCES `town` (`town_id`);

--
-- Constraints for table `transactor_ledger`
--
ALTER TABLE `transactor_ledger`
  ADD CONSTRAINT `Transactor_TransactorLedger` FOREIGN KEY (`transactor_id`) REFERENCES `transactor` (`transactor_id`);

--
-- Constraints for table `user_detail`
--
ALTER TABLE `user_detail`
  ADD CONSTRAINT `ucategory_user` FOREIGN KEY (`user_category_id`) REFERENCES `user_category` (`user_category_id`);

--
-- Constraints for table `user_item_earn`
--
ALTER TABLE `user_item_earn`
  ADD CONSTRAINT `cat_userearn` FOREIGN KEY (`item_category_id`) REFERENCES `category` (`category_id`),
  ADD CONSTRAINT `transreas_userearn` FOREIGN KEY (`transaction_reason_id`) REFERENCES `transaction_reason` (`transaction_reason_id`),
  ADD CONSTRAINT `transtype_userearn` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_type` (`transaction_type_id`),
  ADD CONSTRAINT `usercat_userearn` FOREIGN KEY (`user_category_id`) REFERENCES `user_category` (`user_category_id`);

--
-- Constraints for table `user_right`
--
ALTER TABLE `user_right`
  ADD CONSTRAINT `Store_to_UserRight_on_StoreId` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`),
  ADD CONSTRAINT `UserD_to_UserRight_on_UserDId` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`user_detail_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
