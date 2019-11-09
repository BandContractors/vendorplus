-- adding capability to receive cash paid through WHT
UPDATE acc_coa SET is_child=0 WHERE acc_coa_id=11;

INSERT INTO acc_coa VALUES (167, '1-00-010-080', 'A/REC Withholding Tax', '', NULL, 1, 1, 2, 10, 1, 0, 0, 0, 1, '2018-9-6 12:38:26', 1, NULL, NULL);

INSERT INTO acc_child_account 
(acc_coa_id, acc_coa_account_code, child_account_code, child_account_name, is_active, is_deleted, add_date, add_by) 
VALUES 
('167', '1-00-010-080', 'WHT', 'Withholding Tax Paid', '1', '0', '2018-09-106 08:00:00', '1');

INSERT INTO pay_method (pay_method_id, pay_method_name, display_order, is_active, is_deleted, is_default) 
VALUES ('8', 'RECEIVABLE ACC', '8', '1', '0', '0');

DROP PROCEDURE IF EXISTS sp_search_pay_method_active_in;
DELIMITER //
CREATE PROCEDURE sp_search_pay_method_active_in
(
	IN in_pay_method_IDs varchar(100)
) 
BEGIN 
	SET @sql_text=concat('SELECT * FROM pay_method WHERE is_active=1 AND is_deleted=0 AND pay_method_id IN(',in_pay_method_IDs,') ORDER BY display_order ASC,pay_method_name ASC');
	PREPARE stmt FROM @sql_text;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
END//
DELIMITER ;





alter table pay add column pay_number varchar(50);
update pay set pay_number=pay_id where pay_id>0;

DROP PROCEDURE IF EXISTS sp_insert_pay;
DELIMITER //
CREATE PROCEDURE sp_insert_pay
(
	IN in_pay_date date,
	IN in_paid_amount double,
	IN in_pay_method_id int,
	IN in_add_user_detail_id int,
	IN in_edit_user_detail_id int,
	IN in_add_date datetime,
	IN in_edit_date datetime,
	IN in_points_spent double,
	IN in_points_spent_amount double,
	IN in_delete_pay_id bigint,
	IN in_pay_ref_no varchar(100),
	IN in_pay_category varchar(10),
	IN in_bill_transactor_id bigint,
	IN in_pay_type_id int,
	IN in_pay_reason_id int,
	IN in_store_id int,
	IN in_acc_child_account_id int,
	IN in_acc_child_account_id2 int,
	IN in_currency_code varchar(10),
	IN in_xrate double,
	IN in_status int,
	IN in_status_desc varchar(100),
	IN in_principal_amount double,
	IN in_interest_amount double,
	OUT out_pay_id bigint,
	IN in_pay_number varchar(50)
) 
BEGIN 
	SET @new_id=0;
	CALL sp_get_new_id("pay","pay_id",@new_id);

	SET @cur_sys_datetime=null;
	CALL sp_get_current_system_datetime(@cur_sys_datetime);

	SET @edit_datetime=null;
	SET @add_user_detail_id=null;
	SET @edit_user_detail_id=null;

	SET @bill_transactor_id=null;
	if (in_bill_transactor_id!=0) then 
		SET @bill_transactor_id=in_bill_transactor_id;
	end if;
	SET @delete_pay_id=null;
	if (in_delete_pay_id!=0) then 
		SET @delete_pay_id=in_delete_pay_id;
	end if;
	SET @acc_child_account_id=null;
	if (in_acc_child_account_id!=0) then 
		SET @acc_child_account_id=in_acc_child_account_id;
	end if;
	SET @acc_child_account_id2=null;
	if (in_acc_child_account_id2!=0) then 
		SET @acc_child_account_id2=in_acc_child_account_id2;
	end if;
	if (in_add_user_detail_id!=0) then 
		SET @add_user_detail_id=in_add_user_detail_id;
	end if;

	INSERT INTO pay
	(
		pay_id,
		pay_date,
		paid_amount,
		pay_method_id,
		add_user_detail_id,
		edit_user_detail_id,
		add_date,
		edit_date,
		points_spent,
		points_spent_amount,
		delete_pay_id,
		pay_ref_no,
		pay_category,
		bill_transactor_id,
		pay_type_id,
		pay_reason_id,
		store_id,
		acc_child_account_id,
		acc_child_account_id2,
		currency_code,
		xrate,
		status,
		status_desc,
		principal_amount,
		interest_amount,
		pay_number
	) 
    VALUES
	(
		@new_id,
		in_pay_date,
		in_paid_amount,
		in_pay_method_id,
		@add_user_detail_id,
		@edit_user_detail_id,
		@cur_sys_datetime,
		@edit_datetime,
		in_points_spent,
		in_points_spent_amount,
		@delete_pay_id,
		in_pay_ref_no,
		in_pay_category,
		@bill_transactor_id,
		in_pay_type_id,
		in_pay_reason_id,
		in_store_id,
		@acc_child_account_id,
		@acc_child_account_id2,
		in_currency_code,
		in_xrate,
		in_status,
		in_status_desc,
		in_principal_amount,
		in_interest_amount,
		in_pay_number
	); 
SET out_pay_id=@new_id;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_update_pay;
DELIMITER //
CREATE PROCEDURE sp_update_pay
(
	IN in_pay_id bigint,
	IN in_pay_date date,
	IN in_paid_amount double,
	IN in_pay_method_id int,
	IN in_edit_user_detail_id int,
	IN in_edit_date datetime,
	IN in_points_spent double,
	IN in_points_spent_amount double,
	IN in_delete_pay_id bigint,
	IN in_pay_ref_no varchar(100),
	IN in_pay_category varchar(10),
	IN in_bill_transactor_id bigint,
	IN in_pay_type_id int,
	IN in_pay_reason_id int,
	IN in_store_id int,
	IN in_acc_child_account_id int,
	IN in_acc_child_account_id2 int,
	IN in_currency_code varchar(10),
	IN in_xrate double,
	IN in_status int,
	IN in_status_desc varchar(100),
	IN in_principal_amount double,
	IN in_interest_amount double,
	IN in_pay_number varchar(50)
) 
BEGIN 
	SET @cur_sys_datetime=null;
	CALL sp_get_current_system_datetime(@cur_sys_datetime);

	SET @delete_pay_id=null;
	if (in_delete_pay_id!=0) then 
		SET @delete_pay_id=in_delete_pay_id;
	end if;
	SET @bill_transactor_id=null;
	if (in_bill_transactor_id!=0) then 
		SET @bill_transactor_id=in_bill_transactor_id;
	end if;
	SET @acc_child_account_id=null;
	if (in_acc_child_account_id!=0) then 
		SET @acc_child_account_id=in_acc_child_account_id;
	end if;
	SET @acc_child_account_id2=null;
	if (in_acc_child_account_id2!=0) then 
		SET @acc_child_account_id2=in_acc_child_account_id2;
	end if;

	UPDATE pay SET 
		pay_date=in_pay_date,
		paid_amount=in_paid_amount,
		pay_method_id=in_pay_method_id,
		edit_user_detail_id=in_edit_user_detail_id,
		edit_date=@cur_sys_datetime,
		points_spent=in_points_spent,
		points_spent_amount=in_points_spent_amount,
		delete_pay_id=@delete_pay_id,
		pay_ref_no=in_pay_ref_no,
		pay_category=in_pay_category,
		bill_transactor_id=@bill_transactor_id,
		pay_type_id=in_pay_type_id,
		pay_reason_id=in_pay_reason_id,
		store_id=in_store_id,
		acc_child_account_id=@acc_child_account_id,
		acc_child_account_id2=@acc_child_account_id2,
		currency_code=in_currency_code,
		xrate=in_xrate,
		status=in_status,
		status_desc=in_status_desc,
		principal_amount=in_principal_amount,
		interest_amount=in_interest_amount,
		pay_number=in_pay_number 
	WHERE pay_id=in_pay_id; 
END//
DELIMITER ;


-- 8th Sep 2018
INSERT INTO parameter_list (context, parameter_name, parameter_value) VALUES ('COMPANY_SETTING', 'SHOW_GEN_ITEM_NAME', '0');
INSERT INTO parameter_list (context, parameter_name, parameter_value) VALUES ('COMPANY_SETTING', 'LIST_ITEMS_APPEND', '0');



