-- put here changes to script_db_upgrade
ALTER TABLE transaction_package MODIFY transaction_date datetime;
ALTER TABLE transaction_package ADD COLUMN transaction_id bigint(20);






-- ************************************************************************
-- put here changes to SPs
DROP PROCEDURE IF EXISTS sp_search_transaction_item_by_transaction_id;
DELIMITER //
CREATE PROCEDURE sp_search_transaction_item_by_transaction_id
(
	IN in_transaction_id bigint 
) 
BEGIN 
		SELECT ti.*,tiu.unit_id,tiu.base_unit_qty FROM transaction_item ti 
        INNER JOIN transaction_item_unit tiu ON ti.transaction_item_id=tiu.transaction_item_id 
		WHERE ti.transaction_id=in_transaction_id ORDER BY ti.transaction_item_id ASC;
END//
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_search_transaction_package_item_by_transaction_package_id;
DELIMITER //
CREATE PROCEDURE sp_search_transaction_package_item_by_transaction_package_id
(
	IN in_transaction_package_id bigint 
) 
BEGIN 
		SELECT tpi.*,tiu.unit_id,tiu.base_unit_qty FROM transaction_package_item tpi 
        INNER JOIN transaction_package_item_unit tiu ON tpi.transaction_package_item_id=tiu.transaction_package_item_unit_id 
		WHERE tpi.transaction_package_id=56 ORDER BY tpi.transaction_package_item_id ASC;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_search_transaction_package_item_by_transaction_id;
DELIMITER //
CREATE PROCEDURE sp_search_transaction_package_item_by_transaction_id
(
	IN in_transaction_id bigint 
) 
BEGIN 
		SELECT tpi.*,i.description as item_description,u.unit_symbol,i.item_code,tu.unit_id,tu.base_unit_qty FROM transaction_package_item tpi 
        INNER JOIN item i ON tpi.item_id=i.item_id 
        INNER JOIN transaction_package_item_unit tu ON tpi.transaction_package_item_id=tu.transaction_package_item_unit_id 
        INNER JOIN unit u ON tu.unit_id=u.unit_id 
		INNER JOIN transaction_package tp ON tpi.transaction_package_id = tp.transaction_package_id
		WHERE tp.transaction_id=in_transaction_id 
		ORDER BY tpi.transaction_package_item_id ASC;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_search_transaction_package_transaction_by_id;
DELIMITER //
CREATE PROCEDURE sp_search_transaction_package_transaction_by_id
(
	IN in_transaction_id bigint 
) 
BEGIN 
		SELECT * FROM transaction_package
		WHERE transaction_id=in_transaction_id;
END//
DELIMITER ;




DROP PROCEDURE IF EXISTS sp_update_transaction2;
DELIMITER //
CREATE PROCEDURE sp_update_transaction2
(
	IN in_transaction_id bigint,
	IN in_cash_discount double,
	IN in_total_vat double,
	IN in_edit_user_detail_id int,
	IN in_sub_total double,
	IN in_grand_total double,
	IN in_total_trade_discount double,
	IN in_points_awarded double,
	IN in_card_number varchar(10),
	IN in_total_std_vatable_amount double,
	IN in_total_zero_vatable_amount double,
	IN in_total_exempt_vatable_amount double,
	IN in_amount_tendered double,
	IN in_change_amount double,
	IN in_total_profit_margin double,
    IN in_spent_points_amount double
) 
BEGIN 

	SET @cur_sys_datetime=null;
	CALL sp_get_current_system_datetime(@cur_sys_datetime);

	UPDATE transaction SET 
		cash_discount=in_cash_discount,
		total_vat=in_total_vat,
		edit_user_detail_id=in_edit_user_detail_id,
		edit_date=@cur_sys_datetime,
		sub_total=in_sub_total,
		grand_total=in_grand_total,
		total_trade_discount=in_total_trade_discount,
		points_awarded=in_points_awarded,
		card_number=in_card_number,
		total_std_vatable_amount=in_total_std_vatable_amount,
		total_zero_vatable_amount=in_total_zero_vatable_amount,
		total_exempt_vatable_amount=in_total_exempt_vatable_amount,
		amount_tendered=in_amount_tendered,
		change_amount=in_change_amount,
		total_profit_margin=in_total_profit_margin,
        spent_points_amount=in_spent_points_amount 
	WHERE transaction_id=in_transaction_id; 
END//
DELIMITER ;


