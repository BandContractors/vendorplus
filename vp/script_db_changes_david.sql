-- put here changes to script_db_upgrade
ALTER TABLE transaction_package_item_unit CHANGE COLUMN transaction_packacge_item_unit_id transaction_package_item_unit_id bigint;

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
		WHERE tpi.transaction_package_id=in_transaction_package_id ORDER BY tpi.transaction_package_item_id ASC;
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
		WHERE tp.transaction_id= in_transaction_id
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

DROP PROCEDURE IF EXISTS sp_update_transaction_package_item;
DELIMITER //
CREATE PROCEDURE sp_update_transaction_package_item
(
	IN in_transaction_package_item_id bigint,
	IN in_item_qty double,
	IN in_unit_price double,
	IN in_unit_trade_discount double,
	IN in_amount double,
    IN in_unit_vat double
) 
BEGIN 
	UPDATE transaction_package_item SET 
		item_qty=in_item_qty,
		unit_price=in_unit_price,
		unit_trade_discount=in_unit_trade_discount,
		unit_vat=in_unit_vat,
		amount=in_amount
	WHERE transaction_package_item_id=in_transaction_package_item_id; 
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_update_transaction_package;
DELIMITER //
CREATE PROCEDURE sp_update_transaction_package
(
    IN in_transaction_package_id bigint,
	IN in_transaction_id bigint,
	IN in_cash_discount double,
	IN in_total_vat double,
	IN in_edit_user_detail_id int,
	IN in_sub_total double,
	IN in_grand_total double,
	IN in_total_trade_discount double
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
		total_trade_discount=in_total_trade_discount
	WHERE transaction_id=in_transaction_id; 
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_search_transaction_package_draft_by_user_type;
DELIMITER //
CREATE PROCEDURE sp_search_transaction_package_draft_by_user_type
(
	IN in_transaction_type_id int,
    IN in_transaction_reason_id int,
    IN in_store_id int,
	IN in_add_user_detail_id int
) 
BEGIN 
		SET @cur_sys_datetime=null;
		CALL sp_get_current_system_datetime(@cur_sys_datetime);

		SELECT * FROM transaction th 
		WHERE th.transaction_type_id=88 AND th.transaction_reason_id=135 AND status_code='100' AND
		th.store_id=1 AND th.add_user_detail_id=1 AND
		DATE_FORMAT(@cur_sys_datetime,'%Y-%m-%d')=DATE_FORMAT(th.transaction_date,'%Y-%m-%d') 
		ORDER BY transaction_id DESC;
        
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_delete_transaction_package_draft_id;
DELIMITER //
CREATE PROCEDURE sp_delete_transaction_package_draft_id
(
	IN in_transaction_id bigint,
    IN in_transaction_package_item_id bigint
) 
BEGIN 
	DELETE FROM transaction_package_item WHERE transaction_package_item_id=in_transaction_package_item_id;
    DELETE FROM transaction_package WHERE transaction_id=in_transaction_id;
	DELETE FROM transaction WHERE transaction_id=in_transaction_id;
END//
DELIMITER ;

-- put here changes to Views






