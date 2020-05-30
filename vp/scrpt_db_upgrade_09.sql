ALTER TABLE acc_period ADD COLUMN is_reopen INT(1) NULL DEFAULT 0  AFTER is_closed;

-- tax related modifications
ALTER TABLE item ADD COLUMN tax_item_code VARCHAR(50) NULL DEFAULT '';

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (57, 'COMPANY_SETTING', 'TAX_BRANCH_NO', '','Number issued by the Tax Entity to identify company/branch.');

CREATE  TABLE transaction_tax (
  transaction_tax_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  transaction_id BIGINT(20) NOT NULL,
  transaction_type_id INT(11) NOT NULL,
  transaction_reason_id INT(11) NOT NULL,
  transaction_number VARCHAR(50) NULL, 
  tax_trans_number VARCHAR(50) NULL,
  add_date TIMESTAMP NULL, 
  PRIMARY KEY (transaction_tax_id) );

-- run this; modified at the end of each change batch change
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_09',20,Now(),'6.0','');
