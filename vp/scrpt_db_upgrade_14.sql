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

CREATE TABLE cash_balancing_daily (
  cash_balancing_daily_id bigint(20) NOT NULL AUTO_INCREMENT,
  balancing_date date NOT NULL,
  acc_child_account_id int(11) DEFAULT NULL,
  currency_code varchar(10) NOT NULL,
  cash_begin double NOT NULL,
  cash_transfer_in double NOT NULL,
  cash_adjustment_pos double NOT NULL,
  cash_receipts double DEFAULT NULL,
  cash_transfer_out double NOT NULL,
  cash_adjustment_neg double NOT NULL,
  cash_payments double DEFAULT NULL,
  cash_balance double DEFAULT NULL,
  actual_cash_count double DEFAULT NULL,
  cash_over double DEFAULT NULL, 
  cash_short double DEFAULT NULL,
  add_user_detail_id int(11) NOT NULL,
  edit_user_detail_id int(11) DEFAULT NULL,
  add_date datetime NOT NULL,
  edit_date datetime DEFAULT NULL,
  PRIMARY KEY (cash_balancing_daily_id)
);

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',47,Now(),'6.0','');

INSERT INTO transaction_type (transaction_type_id, transaction_type_name) VALUES (78, 'CASH BALANCING');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (122, 'CASH BALANCING DAILY', 78);

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',53,Now(),'6.0','');