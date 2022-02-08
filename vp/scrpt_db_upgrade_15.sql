INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (74, 'COMPANY_SETTING', 'PURCHASE_INVOICE_MODE', '0','Enter 0 or 1 (0 Purchase Invoice does NOT affect stock, 1 Purchase Invoice affects stock');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',4,Now(),'6.0','');

-- RUN THESE AFTER RUNNING THE STORED PROCEDURES - Splitting stock ledger table (RUN ONCE)
CALL  sp_create_stock_ledger_monthly_tables();
TRUNCATE TABLE stock_ledger;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',10,Now(),'6.0','');

-- Substitute X with current month stock table e.g. stock_ledger_2021_04 for April 2021
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (75, 'COMPANY_SETTING', 'CURRENT_TABLE_NAME_STOCK_LEDGER', 'X','Current monthly stock leger table; created at the beginning of each month');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',15,Now(),'6.0','');
ALTER TABLE X CHANGE COLUMN stock_ledger_id stock_ledger_id BIGINT(20) NOT NULL AUTO_INCREMENT ;

CREATE TABLE snapshot_stock_value_day_sum AS 
select year(s.snapshot_date) as y,month(s.snapshot_date) as m,day(s.snapshot_date) as d,s.snapshot_no,s.currency_code,
s.store_id,IFNULL(i.expense_type,'') as stock_type,
sum(s.cp_value) as cp_value,sum(s.wp_value) as wp_value,sum(s.rp_value) as rp_value from snapshot_stock_value s
INNER JOIN item i ON s.item_id=i.item_id AND i.is_asset=0 AND i.is_track=1 
group by year(s.snapshot_date),month(s.snapshot_date),day(s.snapshot_date),s.snapshot_no,s.currency_code,
s.store_id,IFNULL(i.expense_type,'');

ALTER TABLE snapshot_stock_value_day_sum 
ADD COLUMN snapshot_stock_value_day_sum_id INT(11) NOT NULL AUTO_INCREMENT FIRST,
ADD PRIMARY KEY (snapshot_stock_value_day_sum_id);

DELETE FROM snapshot_stock_value_day_sum WHERE snapshot_stock_value_day_sum_id>1 and snapshot_no NOT IN (
select sn from (select y,m,d,max(snapshot_no) as sn from snapshot_stock_value_day_sum group by y,m,d) as t2
);

-- DELETE ALL STOCK SNAPSHOT below/older-than LAST MONTH 
-- Substitute 'yyy-mm-dd' with current date, parameter should look like '2021-10-25'
call sp_delete_snapshot_stock_value_below_last_month('yyy-mm-dd');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',33,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (76, 'API', 'API_SMBI_URL','','API URL for SMbi (SalesManager BI), leave field empty if not sending data throught that API');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (77, 'API', 'API_SMBI_GROUP_CODE','','API GROUP CODE for SMbi (SalesManager BI), leave field empty if not sending data throught that API');

CREATE TABLE transaction_smbi_map (
  transaction_smbi_map_id bigint(20) NOT NULL AUTO_INCREMENT,
  transaction_id bigint(20) NOT NULL,
  transaction_type_id int(11) NOT NULL,
  transaction_reason_id int(11) NOT NULL,
  transaction_number varchar(50) DEFAULT NULL,
  add_date timestamp NULL DEFAULT NULL,
  status_sync int(1) DEFAULT '0',
  status_date timestamp NULL DEFAULT NULL,
  status_desc varchar(250) NOT NULL,
  PRIMARY KEY (transaction_smbi_map_id)
);
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',58,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (78, 'API', 'API_SMBI_SYNC_JOB_REPEAT_AFTER','10','Time in Minutes the Sync Job to SMbi should take before repeating');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',66,Now(),'6.0','');

UPDATE parameter_list SET description = 'Currency formatting Language [Locale Code]  e.g en for Englidh, fr for French' WHERE (parameter_list_id = '18');
UPDATE parameter_list SET description = 'Currency formatting [Country Code]  e.g US for USA' WHERE (parameter_list_id = '19');

ALTER TABLE user_detail 
ADD COLUMN language_system VARCHAR(50) NOT NULL DEFAULT 'ENGLISH',
ADD COLUMN language_output VARCHAR(50) NOT NULL DEFAULT 'ENGLISH';

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',76,Now(),'6.0','');

UPDATE parameter_list SET parameter_value = 'District,Town' WHERE parameter_list_id = 61;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',79,Now(),'6.0','');

create table loyalty_transaction (
loyalty_transaction_id bigint(20) not null auto_increment, 
store_id int(11) NOT NULL,
card_number varchar(20) not null, 
invoice_number varchar(50) not null, 
transaction_date date not null, 
points_awarded double not null, 
amount_awarded double not null, 
points_spent double not null, 
amount_spent double not null, 
currency_code varchar(10) not null, 
add_date datetime not null, 
staff_code varchar(50) not null,
status_sync int(1) DEFAULT '0',
status_date timestamp NULL DEFAULT NULL,
status_desc varchar(250) NOT NULL,
primary key (loyalty_transaction_id)
);
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',98,Now(),'6.0','');

ALTER TABLE transactor 
ADD COLUMN is_credit_limit int(1) default 0 not null AFTER transactor_segment_id;
ALTER TABLE transactor 
ADD COLUMN credit_limit double default 0 not null AFTER is_credit_limit;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',108,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (79, 'COMPANY_SETTING', 'SALES_INVOICE_AGING_BAND', '0030,0060','E.g. 0030,0060 for 0-30days, 31-60 days and 61+days');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',114,Now(),'6.0','');

ALTER TABLE loyalty_transaction 
ADD COLUMN credit_note_number VARCHAR(50) default '' NOT NULL AFTER invoice_number,
ADD COLUMN debit_note_number VARCHAR(50) default '' NOT NULL AFTER credit_note_number;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',121,Now(),'6.0','');

ALTER TABLE transaction 
ADD COLUMN spent_points_amount double default 0 NOT NULL AFTER total_paid;

ALTER TABLE transaction_cr_dr_note 
ADD COLUMN spent_points_amount double default 0 NOT NULL AFTER total_paid;

ALTER TABLE transaction_hist 
ADD COLUMN total_paid double default 0 NOT NULL AFTER source_code;

ALTER TABLE transaction_hist 
ADD COLUMN spent_points_amount double default 0 NOT NULL AFTER total_paid;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',136,Now(),'6.0','');

UPDATE item SET vat_rated='ZERO,EXEMPT,STANDARD' WHERE item_id>0 AND vat_rated='STANDARD,ZERO,EXEMPT';
UPDATE item SET vat_rated='ZERO,STANDARD' WHERE item_id>0 AND vat_rated='STANDARD,ZERO';
UPDATE item SET vat_rated='EXEMPT,STANDARD' WHERE item_id>0 AND vat_rated='STANDARD,EXEMPT';

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (80, 'COMPANY_SETTING', 'SHOW_DETAIL_BY_DEFAULT', '1','1 for Yes and 0 for No. Details include Customer, Dates, Currency');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',146,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (81, 'COMPANY_SETTING', 'SUPPLIER_INVOICE_AGING_BAND', '0030,0060','E.g. 0030,0060 for 0-30days, 31-60 days and 61+days');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',152,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (82, 'API', 'API_SMBI_SCOPE', '','Leave empty for all otherwise state comma separted transaction types such as SALES,EXPENSES,INVENTORY,LOYALTY');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',158,Now(),'6.0','');

CREATE TABLE item_code_other (
  item_code_other_id bigint(20) NOT NULL AUTO_INCREMENT,
  item_id bigint(20) NOT NULL,
  item_code varchar(50) NOT NULL,
  PRIMARY KEY (item_code_other_id)
);
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',166,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (83, 'GENERAL', 'TAX_VAT_RATE_ORDER', 'ZERO,EXEMPT,STANDARD','Order of priority for an item with more that 1 VAT Tax Rate. If not provided, default is ZERO,EXEMPT,STANDARD');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',171,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (84, 'GENERAL', 'ITEM_FULL_SEARCH_ON', '0','0 for OFF, 1 for ON. If ON, words are searched without following any order of apperance in the item description');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (85, 'GENERAL', 'ITEM_CODE_ERROR_ON', '0','0 for OFF, 1 for ON. If ON, the first 1-to-3 characters of the item code are ignored; this applies to certain barcode raeders');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',179,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (86, 'GENERAL', 'SEARCH_ITEMS_LIST_LIMIT', '10','Maximum number of search items that will be displayed');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',174,Now(),'6.0','');

DROP TABLE IF EXISTS stock_take;

create table stock_take_session (
	stock_take_session_id bigint(20) not null auto_increment,store_id int(11) not null,acc_period_id int(11) not null,notes varchar(250),
    start_time datetime not null, end_time datetime null, is_closed int(1) DEFAULT '0', stock_items_available double,stock_items_counted double, 
    add_date datetime not null, add_by varchar(20) not null,last_update_date datetime null, last_update_by varchar(20), primary key (stock_take_session_id)
);

CREATE TABLE stock_take_session_item (
  stock_take_session_item_id bigint(20) NOT NULL AUTO_INCREMENT,
  stock_take_session_id bigint(20) NOT NULL,
  add_date datetime NOT NULL,
  add_by varchar(20) not null,
  item_id bigint(20) NOT NULL,
  batchno varchar(100) DEFAULT '',
  code_specific varchar(50) DEFAULT '',
  desc_specific varchar(100) DEFAULT '',
  specific_size double DEFAULT '1',
  qty_system double NOT NULL,
  qty_physical double NOT NULL,
  qty_short double NOT NULL,
  qty_over double NOT NULL,
  unit_cost double NOT NULL,
  qty_diff_adjusted int(1) DEFAULT '0',
  notes varchar(250),
  PRIMARY KEY (stock_take_session_item_id)
);

INSERT INTO transaction_type (transaction_type_id, transaction_type_name,transaction_output_label,transaction_number_label,transaction_type_code,trans_number_format,
transaction_date_label,transaction_ref_label,print_file_name1,print_file_name2,default_print_file) 
VALUES (84, 'STOCK TAKE','STOCK TAKE','Stock Take No','STTK','CYMDX','Stock Take Date','','','',1);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (128, 'STOCK TAKE', 84);
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',219,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (87, 'STOCKTAKE', 'STOCKTAKE_ACTION', '1','1:Save and Adjust, 2:Save Only');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_15',223,Now(),'6.0','');