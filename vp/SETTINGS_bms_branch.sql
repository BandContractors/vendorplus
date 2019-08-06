-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 06, 2018 at 09:16 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `d2d_bms_branch`
--

-- --------------------------------------------------------

--
-- Table structure for table `parameter_list`
--

CREATE TABLE IF NOT EXISTS `parameter_list` (
  `parameter_list_id` int(11) NOT NULL AUTO_INCREMENT,
  `context` varchar(50) NOT NULL,
  `parameter_name` varchar(50) NOT NULL,
  `parameter_value` varchar(50) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`parameter_list_id`),
  UNIQUE KEY `UniqueContextParameter` (`context`,`parameter_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `parameter_list`
--

INSERT INTO `parameter_list` (`parameter_list_id`, `context`, `parameter_name`, `parameter_value`, `description`, `store_id`) VALUES
(1, 'MODULE', 'HIRE_MODULE_ON', '1', NULL, NULL),
(2, 'CURRENCY', 'DEFAULT_CURRENCY_CODE', 'USD', NULL, NULL),
(3, 'DURATION', 'DEFAULT_DURATION_TYPE', 'Week', NULL, NULL),
(4, 'COMPANY_SETTING', 'BOX_ADDRESS', 'PO BOX  7OO91 KAMPALA', NULL, NULL),
(5, 'COMPANY_SETTING', 'MOBILE_NUMBER', '0785485346', NULL, NULL),
(6, 'COMPANY_SETTING', 'VAT_NUMBER', '29082-1', NULL, NULL);

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
(16, 'SALE ORDER', 11, NULL),
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
(103, 'SPECIAL SALE INVOICE', 2, NULL);

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
  `print_file_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`transaction_type_id`),
  UNIQUE KEY `transaction_type_name` (`transaction_type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction_type`
--

INSERT INTO `transaction_type` (`transaction_type_id`, `transaction_type_name`, `transactor_label`, `transaction_number_label`, `transaction_output_label`, `bill_transactor_label`, `transaction_ref_label`, `transaction_date_label`, `transaction_user_label`, `is_transactor_mandatory`, `is_transaction_user_mandatory`, `is_transaction_ref_mandatory`, `is_authorise_user_mandatory`, `is_authorise_date_mandatory`, `is_delivery_address_mandatory`, `is_delivery_date_mandatory`, `is_pay_due_date_mandatory`, `is_expiry_date_mandatory`, `description`, `group_name`, `print_file_name`) VALUES
(1, 'PURCHASE INVOICE', 'Supplier', '', '', 'Supplier', '', 'Date', 'Authorised By', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', '', NULL),
(2, 'SALE INVOICE', 'Customer', 'Sales Invoice No', 'SALES INVOICE', 'Bill Customer', 'Purchase Order Ref', 'Invoice Date', 'Served By', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(3, 'DISPOSE STOCK', '', 'Dispose Stock No', 'DISPOSE STOCK', '', '', 'Dispose Date', 'Disposed By', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(4, 'TRANSFER', '', 'Stock Transfer No', 'STOCK TRANSFER', '', 'Stock Transfer Request Ref', 'Transfer Date', 'Transfered By', '', 'No', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(5, 'ITEM', '', '', '', '', '', '', '', '', '', '', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(6, 'PAYMENT', '', '', '', '', '', 'Pay Date', '', '', '', '', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(7, 'UNPACK', '', 'Unpack No', '', '', '', 'Unpack Date', 'Unpacked By', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(8, 'PURCHASE ORDER', '', '', '', '', '', '', '', 'No', 'No', 'No', 'No', 'No', 'Yes', 'No', 'Yes', 'No', '', '', NULL),
(9, 'ITEM RECEIVED', 'Supplier', 'Goods Receive No', 'GOODS RECEIVED NOTE', 'Bill Supplier', 'Purchase Order Ref', 'Receive Date', 'Received By', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(10, 'SALE QUOTATION', 'Customer', 'Sales Quote No', 'SALES QUOTATION', 'Bill Customer', 'Transaction Ref', 'Quote Date', 'Quoted By', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(11, 'SALE ORDER', 'Customer', 'Sales Order No', 'SALES ORDER', 'Bill Customer', 'Purchase Order Ref', 'Order Date', 'Received By', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(12, 'GOODS DELIVERY', 'Customer', 'Goods Delivery No', 'GOODS DELIVERY NOTE', 'Bill Customer', 'Sales Invoice Ref', 'Delivery Date', 'Delivered By', 'Yes', 'Yes', 'Yes', 'No', 'No', 'Yes', 'Yes', 'No', 'No', NULL, NULL, NULL),
(13, 'TRANSFER REQUEST', '', 'Stock Transfer Request No', 'STOCK TRANSFER REQUEST', '', 'Request Ref', 'Request Date', '', '', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(14, 'CASH RECEIPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(15, 'CASH PAYMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(16, 'JOURNAL ENTRY', 'Customer', 'Journal No', 'Journal Entry', 'Customer', 'Reference No', 'Journal Date', 'Staff', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(17, 'TRANSACTOR', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(18, 'CASH TRANSFER', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL, NULL),
(19, 'EXPENSE ENTRY', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'PURCHASE AND EXPENSE', NULL),
(20, 'JOURNAL RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS', NULL),
(21, 'TRIAL BALANCE', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS', NULL),
(22, 'CASH FLOW RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS', NULL),
(23, 'BALANCE SHEET', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'Statement of Financial Position', 'REPORTS - FINANCIAL ACCOUNTING', NULL),
(24, 'INCOME STATEMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'Profit and Loss Statement', 'REPORTS - FINANCIAL ACCOUNTING', NULL),
(25, 'CASH FLOW STATEMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - FINANCIAL ACCOUNTING', NULL),
(26, 'ACCOUNT PERIOD', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ACCOUNT PERIOD', NULL),
(27, 'POST-CLOSING TRIAL BALANCE', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - FINANCIAL ACCOUNTING', NULL),
(28, 'CASH ACCOUNT RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL),
(29, 'ACCOUNT STATEMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL),
(30, 'RECEIVABLE DETAIL', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL),
(31, 'RECEIVABLE SUMMARY', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL),
(32, 'PAYABLE DETAIL', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL),
(33, 'PAYABLE SUMMARY', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH', NULL),
(34, 'INVENTORY-STOCK RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - INVENTORY', NULL),
(35, 'INVENTORY-ASSET RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - INVENTORY', NULL),
(36, 'INVENTORY-EXPENSE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - INVENTORY', NULL),
(37, 'ITEM-DETAIL-STOCK RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL', NULL),
(38, 'ITEM-DETAIL-EXPENSE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL', NULL),
(39, 'ITEM-DETAIL-ASSET RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL', NULL),
(40, 'ITEM-DETAIL-LOCATION RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL', NULL),
(41, 'CUSTOMER LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST', NULL),
(42, 'SUPPLIER LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST', NULL),
(43, 'SCHEME LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST', NULL),
(44, 'PROVIDER LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST', NULL),
(45, 'SALES INVOICE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES', NULL),
(46, 'SALES QUOTATION RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES', NULL),
(47, 'SALES ORDER RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES', NULL),
(48, 'SALES DELIVERY RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES', NULL),
(49, 'SALES USER EARN RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES', NULL),
(50, 'PURCHASE INVOICE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PURCHASES', NULL),
(51, 'PURCHASE ORDER RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PURCHASES', NULL),
(52, 'PURCHASE ITEM RECEIVED RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PURCHASES', NULL),
(53, 'TRANSFER REQUEST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT ITEM TRANSFERS', NULL),
(54, 'TRANSFER RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT ITEM TRANSFERS', NULL),
(55, 'DISPOSE STOCK RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT DISPOSE STOCK', NULL),
(56, 'CASH RECEIPT RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS CASH', NULL),
(57, 'CASH PAYMENT RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS CASH', NULL),
(58, 'CHART OF ACCOUNTS', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'ACCOUNTS', NULL),
(59, 'DISCOUNT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'SALES', NULL),
(60, 'INTER BRANCH', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'INTER BRANCH', NULL),
(61, 'SETTING', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'SETTING', NULL),
(62, 'SPEND POINT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'SALES', NULL),
(63, 'HIRE QUOTATION', 'Customer', 'Quote No', 'HIRE QUOTATION', 'Bill Customer', 'Quote Ref', 'Quote Date', 'Company Rep', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHQ_Sheaf'),
(64, 'HIRE ORDER', 'Customer', 'Order No', 'HIRE ORDER', 'Bill Customer', 'Order Ref', 'Order Date', 'Company Rep', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'HIRE', NULL),
(65, 'HIRE INVOICE', 'Customer', 'Hire Number', 'HIRE INVOICE', 'Bill Customer', 'Order Ref', 'Hire Date', 'Company Rep', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHI_Sheaf'),
(66, 'HIRE DELIVERY NOTE', 'Customer', 'Delivery No', 'HIRE DELIVERY NOTE', 'Bill Customer', 'Delivery Ref', 'Delivery Date', 'Company Rep', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHD_Sheaf'),
(67, 'HIRE RETURN NOTE', 'Customer', 'Return Number ', 'HIRE RETURN NOTE', 'Bill Customer', 'Return Number', 'Return Date', ' Company Rep', 'Yes', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHR_Sheaf'),
(68, 'HIRE RETURN INVOICE', 'Customer', 'Invoice Number', 'HIRE RETURN INVOICE', 'Bill Customer', 'Return Number', 'Invoice Date', ' Company Rep', 'Yes', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'HIRE', 'OutputHRI_Sheaf'),
(69, 'HIRE REPORTS', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'HIRE REPORTS', NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaction_reason`
--
ALTER TABLE `transaction_reason`
  ADD CONSTRAINT `TransType_to_TransReas_on_TransReasId` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_type` (`transaction_type_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
