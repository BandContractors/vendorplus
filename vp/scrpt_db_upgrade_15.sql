INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (74, 'COMPANY_SETTING', 'PURCHASE_INVOICE_MODE', '0','Enter 0 or 1 (0 Purchase Invoice does NOT affect stock, 1 Purchase Invoice affects stock');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',4,Now(),'6.0','');

-- RUN THESE AFTER RUNNING THE STORED PROCEDURES - Splitting stock ledger table (RUN ONCE)
CALL  sp_create_stock_ledger_monthly_tables();
TRUNCATE TABLE stock_ledger;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',10,Now(),'6.0','');

-- Substitute X with current month stock table e.g. 'stock_ledger_2021_04' for April 2021
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (75, 'COMPANY_SETTING', 'CURRENT_TABLE_NAME_STOCK_LEDGER', X,'Current monthly stock leger table; created at the beginning of each month');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',15,Now(),'6.0','');
ALTER TABLE X CHANGE COLUMN stock_ledger_id stock_ledger_id BIGINT(20) NOT NULL AUTO_INCREMENT ;

CREATE TABLE snapshot_stock_value_day_sum AS 
select year(s.snapshot_date) as y,month(s.snapshot_date) as m,day(s.snapshot_date) as d,s.snapshot_no,s.currency_code,
s.store_id,IFNULL(i.expense_type,'') as stock_type,
sum(s.cp_value) as cp_value,sum(s.wp_value) as wp_value,sum(s.rp_value) as rp_value from snapshot_stock_value s
INNER JOIN item i ON s.item_id=i.item_id AND i.is_asset=0 AND i.is_track=1 
group by year(s.snapshot_date),month(s.snapshot_date),day(s.snapshot_date),s.snapshot_no,s.currency_code,
s.store_id,IFNULL(i.expense_type,'');

ALTER TABLE snapshot_stock_value_day_sum 
ADD COLUMN snapshot_stock_value_day_sum_id INT(11) NOT NULL AUTO_INCREMENT FIRST,
ADD PRIMARY KEY (snapshot_stock_value_day_sum_id);

DELETE FROM snapshot_stock_value_day_sum WHERE snapshot_stock_value_day_sum_id>1 and snapshot_no NOT IN (
select sn from (select y,m,d,max(snapshot_no) as sn from snapshot_stock_value_day_sum group by y,m,d) as t2
);

-- DELETE ALL STOCK SNAPSHOT below/older-than LAST MONTH 
-- Substitute 'yyy-mm-dd' with current date, parameter should look like '2021-10-25'
call sp_delete_snapshot_stock_value_below_last_month('yyy-mm-dd');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',33,Now(),'6.0','');







