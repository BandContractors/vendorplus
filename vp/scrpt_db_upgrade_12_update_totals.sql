-- start- update total paid
CREATE TABLE temp_trans_total_paid AS 
select transaction_id,sum(trans_paid_amount) as total_paid_calc from pay_trans group by transaction_id;

ALTER TABLE temp_trans_total_paid ADD PRIMARY KEY (transaction_id) ;

DROP PROCEDURE IF EXISTS sp_update_total_paid_for_all;
DELIMITER //
CREATE PROCEDURE sp_update_total_paid_for_all() 
BEGIN 
	DECLARE finished INTEGER DEFAULT 0;
	DECLARE transId bigint DEFAULT 0;

	-- declare cursor for sales and purchase transactions
	DEClARE curTransactions 
		CURSOR FOR 
			SELECT t.transaction_id FROM transaction t INNER JOIN temp_trans_total_paid ttp ON t.transaction_id=ttp.transaction_id WHERE t.transaction_type_id IN(2,1) AND t.total_paid=0 AND ttp.total_paid_calc>0;
	-- declare NOT FOUND handler
	DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET finished = 1;

	OPEN curTransactions;

	getTrans: LOOP
		FETCH curTransactions INTO transId;
		IF finished = 1 THEN 
			LEAVE getTrans;
		END IF;
		-- act on the selected id
	UPDATE transaction SET total_paid=ifnull((select total_paid_calc from temp_trans_total_paid tp where tp.transaction_id=transId),0) WHERE transaction_id>0 AND transaction_id=transId;
	END LOOP getTrans;
	CLOSE curTransactions;
END//
DELIMITER ;

CALL sp_update_total_paid_for_all();

DROP TABLE temp_trans_total_paid;

-- end- update total paid