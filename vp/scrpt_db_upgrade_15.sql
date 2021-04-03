INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (74, 'COMPANY_SETTING', 'PURCHASE_INVOICE_MODE', '0','Enter 0 or 1 (0 Purchase Invoice does NOT affect stock, 1 Purchase Invoice affects stock');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',4,Now(),'6.0','');

-- RUN THESE AFTER RUNNING THE STORED PROCEDURES - Splitting stock ledger table (RUN ONCE)
CALL  sp_create_stock_ledger_monthly_tables();
TRUNCATE TABLE stock_ledger;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',10,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (75, 'COMPANY_SETTING', 'CURRENT_TABLE_NAME_STOCK_LEDGER', 'stock_ledger','Current monthly stock leger table; created at the beginning of each month');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',15,Now(),'6.0','');





