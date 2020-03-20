CALL sp_insert_upgrade_control("scrpt_db_upgrade_07",1,Now(),"6.0","");
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

