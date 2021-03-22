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

CREATE TABLE stock_take (
  stock_take_id bigint(20) NOT NULL AUTO_INCREMENT,
  snapshot_no int(11) NOT NULL,
  snapshot_date datetime NOT NULL,
  cdc_id varchar(20) DEFAULT NULL,
  store_id int(11) DEFAULT NULL,
  acc_period_id int(11) NOT NULL,
  item_id bigint(20) NOT NULL,
  batchno varchar(100) DEFAULT NULL,
  code_specific varchar(50) DEFAULT NULL,
  desc_specific varchar(100) DEFAULT NULL,
  specific_size double DEFAULT '1',
  qty_system double NOT NULL,
  qty_physical double NOT NULL,
  qty_short double NOT NULL,
  qty_over double NOT NULL,
  PRIMARY KEY (stock_take_id)
);

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',73,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (65, 'COMPANY_SETTING', 'HOTEL_MODULE_ON', '0','0 is OFF and 1 is ON');

INSERT INTO transaction_type (transaction_type_id, transaction_type_name) VALUES (79, 'HOTEL RESERVATION');
INSERT INTO transaction_type (transaction_type_id, transaction_type_name) VALUES (80, 'HOTEL CHECK IN');
INSERT INTO transaction_type (transaction_type_id, transaction_type_name) VALUES (81, 'HOTEL CHECK OUT');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (123, 'HOTEL RESERVATION', 79);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (124, 'HOTEL CHECK IN', 80);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (125, 'HOTEL CHECK OUT', 81);

ALTER TABLE item_unspsc ADD COLUMN add_date datetime NOT NULL AFTER exempt_rate;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',87,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (66, 'API', 'API_TAX_MODE', 'OFFLINE','OFFLINE or ONLINE');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (67, 'API', 'API_TAX_KEYSTORE_FILE', '','The full file location for the keystore file including the extension such as d:/key_file_name.p12.p12, /home/key_file_name.p12');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (68, 'API', 'API_TAX_KEYSTORE_PASSWORD', '','(Encrypted password): to change, enter password and click Encrypt before Save');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (69, 'API', 'API_TAX_KEYSTORE_ALIAS', '','Alias name for the Keystore');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',99,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (70, 'API', 'API_TAX_AES_PUBLIC_KEY', '','Stores AESpublickeystring variable for others to use, is picked once every day');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',105,Now(),'6.0','');

INSERT INTO transaction_type (transaction_type_id, transaction_type_name,transaction_output_label,transaction_number_label,transaction_type_code,trans_number_format,
transaction_date_label,transaction_ref_label,print_file_name1,print_file_name2,default_print_file) 
VALUES (82, 'CREDIT NOTE','CREDIT NOTE','Credit Trans No','CN','CYMDX','Credit Date','Orig. Invoice No','OutputCNDN_Size_Small','OutputCNDN_Size_A4',1);
INSERT INTO transaction_type (transaction_type_id, transaction_type_name,transaction_output_label,transaction_number_label,transaction_type_code,trans_number_format,
transaction_date_label,transaction_ref_label,print_file_name1,print_file_name2,default_print_file) 
VALUES (83, 'DEBIT NOTE','DEBIT NOTE','Debit Trans No','DN','CYMDX','Debit Date','Orig. Invoice No','OutputCNDN_Size_Small','OutputCNDN_Size_A4',1);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (126, 'CREDIT NOTE', 82);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (127, 'DEBIT NOTE', 83);

create table transaction_cr_dr_note as 
select * from transaction limit 0;
ALTER TABLE transaction_cr_dr_note ADD PRIMARY KEY (`transaction_id`);

create table transaction_item_cr_dr_note as 
select * from transaction_item limit 0;
ALTER TABLE transaction_item_cr_dr_note ADD PRIMARY KEY (`transaction_item_id`);

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',121,Now(),'6.0','');

ALTER TABLE transaction_tax_map 
DROP COLUMN qr_code_tax_update,
DROP COLUMN verification_code_tax_update,
DROP COLUMN transaction_number_tax_update,
DROP COLUMN update_type,
DROP COLUMN update_synced,
DROP COLUMN is_updated;

ALTER TABLE transaction_tax_map 
ADD COLUMN reference_number_tax VARCHAR(50) NULL DEFAULT '';

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',139,Now(),'6.0','');

ALTER TABLE transaction_tax_map ADD COLUMN fdn_ref VARCHAR(50) NULL DEFAULT '';
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',143,Now(),'6.0','');

UPDATE parameter_list SET parameter_value='6.0' WHERE parameter_list_id=8;
ALTER TABLE transaction_tax_map ADD COLUMN fdn_ref VARCHAR(50) NULL DEFAULT '';
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',147,Now(),'6.0','');

ALTER TABLE item_tax_map 
CHANGE COLUMN item_id_tax item_id_tax VARCHAR(50) NULL DEFAULT NULL ;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',153,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (71, 'COMPANY_SETTING', 'ENABLE_AUTO_COMPLETE_ITEM_SEARCH', '1','possible values: 1 or 0: Enables(1) or disables(0) auto complete on items search where manual search is available.');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',158,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (72, 'CUSTOMER_DISPLAY', 'COM_PORT_NAME', '','Port name for the customer display in form of COM8,etc.');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',162,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (73, 'CUSTOMER_DISPLAY', 'MAX_CHARACTERS_PER_LINE', '','The maximum number of characters per line supported by the display device.');

DROP INDEX u_transitem_trans_item_batch1 ON transaction_item_hist;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',171,Now(),'6.0','');

UPDATE parameter_list SET parameter_value='' WHERE parameter_list_id IN(32,27,31,30);
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_14',175,Now(),'6.0','');



