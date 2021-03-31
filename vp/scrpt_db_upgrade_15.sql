INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (74, 'COMPANY_SETTING', 'PURCHASE_INVOICE_MODE', '0','Enter 0 or 1 (0 Purchase Invoice does NOT affect stock, 1 Purchase Invoice affects stock');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',4,Now(),'6.0','');



