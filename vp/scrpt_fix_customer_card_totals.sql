DROP PROCEDURE IF EXISTS sp_report_customer_card_totals;
DELIMITER //
CREATE PROCEDURE sp_report_customer_card_totals
(
	IN in_transaction_date_from date,
	IN in_transaction_date_to date,
	IN in_store_id int,
	IN in_bill_transactor_id bigint 
) 
BEGIN 
	SET @TransDate='';
	SET @FromStore='';
	SET @BillTransactor='';

	if ((in_transaction_date_from is not null) and (in_transaction_date_to is not null)) then 
		SET @TransDate=concat(" AND (t.transaction_date BETWEEN '",in_transaction_date_from,"' AND '",in_transaction_date_to,"')");
	end if;
	if (in_store_id!=0) then 
		SET @FromStore=concat(' AND t.store_id=',in_store_id);
	end if;
	if (in_bill_transactor_id!=0) then 
		SET @BillTransactor=concat(' AND t.bill_transactor_id=',in_bill_transactor_id);
	end if;
	
	SET @sql_inner=concat('SELECT t.*,ud.user_name,tr.transactor_names,
		(select sum(pt.trans_paid_amount) from pay_trans pt where pt.transaction_id=t.transaction_id) as total_paid2 FROM transaction t  
		INNER JOIN user_detail ud ON t.add_user_detail_id=ud.user_detail_id 
		INNER JOIN transactor tr ON t.transactor_id=tr.transactor_id WHERE (t.transaction_type_id=2 OR t.transaction_reason_id IN(117)) ',@TransDate,@FromStore,@BillTransactor,' ORDER BY t.transaction_id DESC');
	SET @sql_text=concat('select CC.currency_code,sum(CC.grand_total) as grand_total,sum(CC.total_paid2) as total_paid2 from (',
		@sql_inner,') as CC group by CC.currency_code');
	PREPARE stmt FROM @sql_text;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_report_supplier_card_totals;
DELIMITER //
CREATE PROCEDURE sp_report_supplier_card_totals
(
	IN in_transaction_date_from date,
	IN in_transaction_date_to date,
	IN in_store_id int,
	IN in_bill_transactor_id bigint 
) 
BEGIN 
	SET @TransDate='';
	SET @FromStore='';
	SET @BillTransactor='';

	if ((in_transaction_date_from is not null) and (in_transaction_date_to is not null)) then 
		SET @TransDate=concat(" AND (t.transaction_date BETWEEN '",in_transaction_date_from,"' AND '",in_transaction_date_to,"')");
	end if;
	if (in_store_id!=0) then 
		SET @FromStore=concat(' AND t.store_id=',in_store_id);
	end if;
	if (in_bill_transactor_id!=0) then 
		SET @BillTransactor=concat(' AND t.bill_transactor_id=',in_bill_transactor_id);
	end if;
	
	SET @sql_inner=concat('SELECT t.*,ud.user_name,tr.transactor_names,
		(select sum(pt.trans_paid_amount) from pay_trans pt where pt.transaction_id=t.transaction_id) as total_paid2 FROM transaction t  
		INNER JOIN user_detail ud ON t.add_user_detail_id=ud.user_detail_id 
		INNER JOIN transactor tr ON t.transactor_id=tr.transactor_id WHERE (t.transaction_type_id=1 OR t.transaction_reason_id IN(118)) ',@TransDate,@FromStore,@BillTransactor,' ORDER BY t.transaction_id DESC');
	SET @sql_text=concat('select CC.currency_code,sum(CC.grand_total) as grand_total,sum(CC.total_paid2) as total_paid2 from (',
		@sql_inner,') as CC group by CC.currency_code');
	PREPARE stmt FROM @sql_text;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
END//
DELIMITER ;

