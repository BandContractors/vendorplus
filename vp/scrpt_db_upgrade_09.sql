ALTER TABLE acc_period ADD COLUMN is_reopen INT(1) NULL DEFAULT 0  AFTER is_closed;

-- tax related modifications
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (57, 'COMPANY_SETTING', 'TAX_BRANCH_NO', '','Number issued by the Tax Entity to identify company/branch.');

CREATE  TABLE item_tax_map (
  item_tax_map_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  item_id BIGINT(20) NOT NULL,
  item_id_tax BIGINT(20) NULL,
  item_code_tax VARCHAR(50) NULL,
  add_date TIMESTAMP NULL, 
  PRIMARY KEY (item_tax_map_id) );

CREATE  TABLE transaction_tax_map (
  transaction_tax_map_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  transaction_id BIGINT(20) NOT NULL,
  transaction_type_id INT(11) NOT NULL,
  transaction_reason_id INT(11) NOT NULL,
  transaction_number VARCHAR(50) NULL, 
  transaction_number_tax VARCHAR(50) NULL,
  add_date TIMESTAMP NULL, 
  PRIMARY KEY (transaction_tax_map_id) );

-- run this; modified at the end of each change batch change
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_09',20,Now(),'6.0','');

ALTER TABLE acc_dep_schedule ADD COLUMN post_status INT(1) NULL DEFAULT 0;
UPDATE acc_dep_schedule SET post_status=1 WHERE acc_dep_schedule_id>0 AND year_number=1 AND (post_status=0 OR dep_for_acc_period_id is null OR dep_for_acc_period_id=0);
