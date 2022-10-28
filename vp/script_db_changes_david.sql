-- put here changes to script_db_upgrade
ALTER TABLE transaction_package MODIFY transaction_date datetime;







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
		WHERE tp.transaction_id=413400 
		ORDER BY tpi.transaction_package_item_id ASC;
END//
DELIMITER ;



-- put here changes to Views



