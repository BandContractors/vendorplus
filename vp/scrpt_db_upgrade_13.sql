INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (64, 'COMPANY_SETTING', 'DEFAULT_FOCUS_CONTROL_ID', 'itxtItemCode','Deafult item control to focus after adding sales. Options are itxtItemCode for BarCode and autcItem for  Description');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_13',4,Now(),'6.0','');

