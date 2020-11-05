CREATE TABLE snapshot_cash_balance (
  snapshot_cash_balance_id bigint(20) NOT NULL AUTO_INCREMENT,
  snapshot_no int(11) NOT NULL,
  snapshot_date datetime NOT NULL,
  acc_period_id int(11) NOT NULL,
  cdc_id varchar(20) DEFAULT NULL,
  account_code varchar(20) NOT NULL,
  acc_child_account_id int(11) DEFAULT NULL,
  currency_code varchar(10) NOT NULL,
  debit_amount double NOT NULL,
  credit_amount double NOT NULL,
  debit_amount_lc double DEFAULT NULL,
  credit_amount_lc double DEFAULT NULL,
  debit_balance double DEFAULT NULL,
  debit_balance_lc double DEFAULT NULL, 
  PRIMARY KEY (snapshot_cash_balance_id)
);

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',19,Now(),'6.0','');