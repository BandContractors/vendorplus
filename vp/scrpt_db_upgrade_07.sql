CREATE  TABLE upgrade_control (
  upgrade_control_id INT NOT NULL AUTO_INCREMENT ,
  script_name VARCHAR(50) NULL ,
  line_no INT NULL ,
  upgrade_date TIMESTAMP NULL ,
  version_no VARCHAR(10) NULL ,
  upgrade_detail VARCHAR(250) NULL ,
  PRIMARY KEY (upgrade_control_id) );

UPDATE acc_coa SET account_name='INV Merchandise' WHERE acc_coa_id>0 AND account_code='1-00-020-010';
INSERT INTO acc_coa (acc_coa_id, account_code, account_name, acc_type_id, acc_group_id, acc_category_id, order_coa, is_active, is_deleted, is_child, is_transactor_mandatory, is_system_account, add_date, add_by) 
VALUES ('168', '1-00-020-070', 'INV Consumables', '1', '1', '3', '16', '1', '0', '1', '0', '1', '2020-03-20 12:38:26', '1');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (55, 'ITEMS_RECEIVED', 'AUTO_APPEND_NEW_COST_BATCH', '0','Receipt of item with new cost price, append cost price as batch');
UPDATE item set expense_type='Services',expense_account_code='5-10-000-020' 
WHERE is_asset=0 and is_sale=1 and item_type='SERVICE' and is_track=0 and is_hire=0;

UPDATE item set expense_type='Services',expense_account_code='5-10-000-010' 
WHERE is_asset=0 and is_sale=1 and item_type='PRODUCT' and is_track=0 and is_hire=0;

UPDATE item set expense_type='Merchandise',expense_account_code='1-00-020-010' 
WHERE is_asset=0 and is_sale=1 and is_buy=1 and item_type='PRODUCT' and is_track=1 and is_hire=0;

UPDATE item set expense_type='Finished Goods',expense_account_code='1-00-020-040' 
WHERE is_asset=0 and is_sale=1 and is_buy=0 and item_type='PRODUCT' and is_track=1 and is_hire=0;

UPDATE item set expense_type='Raw Material',expense_account_code='1-00-020-020' 
WHERE is_asset=0 and is_sale=0 and is_buy=1 and item_type='PRODUCT' and is_track=1 and is_hire=0;
-- OR
UPDATE item set expense_type='Consumption',expense_account_code='1-00-020-070' 
WHERE is_asset=0 and is_sale=0 and is_buy=1 and item_type='PRODUCT' and is_track=1 and is_hire=0;

-- select * from item where is_asset=0 and (ifnull(expense_type,'')='' or ifnull(expense_account_code,'')='');

UPDATE item set expense_type='Merchandise',expense_account_code='1-00-020-010' 
WHERE is_asset=0 and is_sale=1 and is_buy=1 and item_type='SERVICE' and is_track=1 and is_hire=0;

-- select * from item where is_asset=0 and (ifnull(expense_type,'')='' or ifnull(expense_account_code,'')='');

-- run this; modified at the end of each change batch
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_07',42,Now(),'6.0','');


