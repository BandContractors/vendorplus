INSERT INTO `acc_coa` (`acc_coa_id`, `account_code`, `account_name`, `account_desc`, `acc_class_id`, `acc_type_id`, `acc_group_id`, `acc_category_id`, `order_coa`, `is_active`, `is_deleted`, `is_child`, `is_transactor_mandatory`, `is_system_account`, `add_date`, `add_by`, `last_edit_date`, `last_edit_by`) VALUES
(166, '4-10-000-050', 'SALES Hire', '', NULL, 4, 7, 18, 166, 1, 0, 1, 0, 1, '2018-05-30 20:33:24', 1, NULL, NULL);


INSERT INTO `parameter_list` (`parameter_list_id`, `context`, `parameter_name`, `parameter_value`, `description`, `store_id`) VALUES
(1, 'MODULE', 'HIRE_MODULE_ON', '0', NULL, NULL),
(2, 'CURRENCY', 'DEFAULT_CURRENCY_CODE', 'UGX', NULL, NULL),
(3, 'DURATION', 'DEFAULT_DURATION_TYPE', 'Week', NULL, NULL),
(4, 'COMPANY_SETTING', 'BOX_ADDRESS', '', NULL, NULL),
(5, 'COMPANY_SETTING', 'MOBILE_NUMBER', '', NULL, NULL),
(6, 'COMPANY_SETTING', 'VAT_NUMBER', '', NULL, NULL),
(7, 'SYSTEM', 'SYSTEM_NAME', 'WINGERsoft BMS', NULL, NULL),
(8, 'SYSTEM', 'SYSTEM_VERSION', '2.0', NULL, NULL),
(9, 'SYSTEM', 'SYSTEM_NAME_CLIENT', 'WINGERsoft BMS', NULL, NULL);

INSERT INTO `transaction_type` (`transaction_type_id`, `transaction_type_name`, `transactor_label`, `transaction_number_label`, `transaction_output_label`, `bill_transactor_label`, `transaction_ref_label`, `transaction_date_label`, `transaction_user_label`, `is_transactor_mandatory`, `is_transaction_user_mandatory`, `is_transaction_ref_mandatory`, `is_authorise_user_mandatory`, `is_authorise_date_mandatory`, `is_delivery_address_mandatory`, `is_delivery_date_mandatory`, `is_pay_due_date_mandatory`, `is_expiry_date_mandatory`, `description`, `group_name`, `print_file_name1`, `print_file_name2`, `default_print_file`, `transaction_type_code`) VALUES
(63, 'HIRE QUOTATION', 'Customer', 'Quote No', 'HIRE QUOTATION', 'Bill Customer', 'Quote Ref', 'Quote Date', 'Company Rep', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHQ_Sheaf', NULL, 1, NULL),
(64, 'HIRE ORDER', 'Customer', 'Order No', 'HIRE ORDER', 'Bill Customer', 'Order Ref', 'Order Date', 'Company Rep', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'HIRE', NULL, NULL, NULL, NULL),
(65, 'HIRE INVOICE', 'Customer', 'Hire Number', 'HIRE INVOICE', 'Bill Customer', 'Order Ref', 'Hire Date', 'Company Rep', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHI_Sheaf', '', 1, NULL),
(66, 'HIRE DELIVERY NOTE', 'Customer', 'Delivery No', 'HIRE DELIVERY NOTE', 'Bill Customer', 'Delivery Ref', 'Delivery Date', 'Company Rep', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHD_Sheaf', NULL, 1, NULL),
(67, 'HIRE RETURN NOTE', 'Customer', 'Return Number ', 'HIRE RETURN NOTE', 'Bill Customer', 'Return Number', 'Return Date', ' Company Rep', 'Yes', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', '', 'HIRE', 'OutputHR_Sheaf', NULL, 1, NULL),
(68, 'HIRE RETURN INVOICE', 'Customer', 'Invoice Number', 'HIRE RETURN INVOICE', 'Bill Customer', 'Return Number', 'Invoice Date', ' Company Rep', 'Yes', 'Yes', 'Yes', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'HIRE', 'OutputHRI_Sheaf', NULL, 1, NULL),
(69, 'HIRE REPORTS', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'HIRE REPORTS', NULL, NULL, NULL, NULL);

INSERT INTO `transaction_reason` (`transaction_reason_id`, `transaction_reason_name`, `transaction_type_id`, `description`) VALUES
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
(104, 'HIRE UNRETURNED ITEMS DETAIL RPT', 69, NULL);

UPDATE transaction SET duration_value=ceil((DATEDIFF(to_date,from_date)+1)/7) WHERE transaction_type_id=65 AND transaction_id>0 AND (duration_value=0 OR duration_value is null);
