create table item_unit_other (
                        item_unit_other_id bigint(20) primary key auto_increment,
                        item_id bigint(20),
                        base_qty double,
                        other_unit_id int,
                        other_qty double,
                        other_unit_retailsale_price double,
                        other_unit_wholesale_price double,
                        other_default_purchase int,
                        other_default_sale int,
                        is_active int,
                        last_edit_by varchar(255),
                        last_edit_date datetime
                        )ENGINE=InnoDB;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_18',15,Now(),'6.0','');

-- drop table transaction_item_unit;
-- drop table transaction_item_cr_dr_note_unit;
-- drop table transaction_item_hist_unit;
-- drop table trans_production_item_unit;
-- drop table trans_production_unit; 
CREATE TABLE IF NOT EXISTS transaction_item_unit (PRIMARY KEY (transaction_item_id)) AS 
select ti.transaction_item_id,i.unit_id,ti.item_qty as base_unit_qty from transaction_item ti inner join item i on ti.item_id=i.item_id;
CREATE TABLE IF NOT EXISTS transaction_item_cr_dr_note_unit (PRIMARY KEY (transaction_item_id)) AS 
select ti.transaction_item_id,i.unit_id,ti.item_qty as base_unit_qty from transaction_item_cr_dr_note ti inner join item i on ti.item_id=i.item_id;
CREATE TABLE IF NOT EXISTS transaction_item_hist_unit (PRIMARY KEY (transaction_item_hist_id)) AS 
select ti.transaction_item_hist_id,i.unit_id,ti.item_qty as base_unit_qty from transaction_item_hist ti inner join item i on ti.item_id=i.item_id;
CREATE TABLE IF NOT EXISTS trans_production_item_unit (PRIMARY KEY (trans_production_item_id)) AS 
select ti.trans_production_item_id,i.unit_id,ti.input_qty as base_unit_qty from trans_production_item ti inner join item i on ti.input_item_id=i.item_id;
CREATE TABLE IF NOT EXISTS trans_production_unit (PRIMARY KEY (transaction_id)) AS 
select ti.transaction_id,i.unit_id,ti.output_qty as base_unit_qty from trans_production ti inner join item i on ti.output_item_id=i.item_id;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_18',33,Now(),'6.0','');

ALTER TABLE efris_invoice_detail 
ADD COLUMN verification_code VARCHAR(50) NOT NULL DEFAULT '' AFTER process_desc,
ADD COLUMN qr_code VARCHAR(1000) NOT NULL DEFAULT '' AFTER verification_code;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_18',39,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (97, 'GENERAL', 'FORCE_UNIT_SELECTION', '0','0:No and 1:Yes. If Yes, Unit will be selected manually at each transaction. If No, default unit is be selected automatically. Applies to Items with multiple units');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',44,Now(),'6.0','');

-- Replace db_name with database name such as daytoday_sm_branch
CALL sp_alter_stock_ledger_tables_trans_item_id("db_name");
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',48,Now(),'6.0','');

ALTER TABLE item_production_map ADD COLUMN output_unit_id INT NULL DEFAULT '0',ADD COLUMN input_unit_id INT NULL DEFAULT '0';
UPDATE item_production_map m set 
m.output_unit_id=(select i1.unit_id from item i1 where i1.item_id=m.output_item_id),
m.input_unit_id=(select i2.unit_id from item i2 where i2.item_id=m.input_item_id) 
WHERE m.item_production_map_id>0;

DROP TABLE trans_production_item_unit;
DROP TABLE trans_production_unit;
ALTER TABLE trans_production_item ADD COLUMN unit_id INT NULL DEFAULT '0',ADD COLUMN base_unit_qty DOUBLE NULL DEFAULT '0';
ALTER TABLE trans_production ADD COLUMN unit_id INT NULL DEFAULT '0',ADD COLUMN base_unit_qty DOUBLE NULL DEFAULT '0';

UPDATE trans_production_item ti SET ti.base_unit_qty=ti.input_qty,ti.unit_id=(select i.unit_id from item i where i.item_id=ti.input_item_id) 
WHERE ti.trans_production_item_id>0;
UPDATE trans_production t SET t.base_unit_qty=t.output_qty,t.unit_id=(select i.unit_id from item i where i.item_id=t.output_item_id) 
WHERE t.transaction_id>0;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',66,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (98, 'COMPANY_SETTING', 'COUNTRY_NAME', 'UGANDA','Country of Registration for the company or Branch; this should be the full name e.g UGANDA');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',70,Now(),'6.0','');

create table efris_excise_duty_list (
	efris_excise_duty_list_id bigint primary key auto_increment,
	id varchar(20) not null,
	exciseDutyCode varchar(20) not null,
    goodService varchar(500),
    parentCode varchar(20),
    rateText varchar(50) not null,
    isLeafNode varchar(1),
    effectiveDate varchar(100),
    unit varchar(3),
    currency varchar(100) not null,
    rate_perc varchar(500),
    rate_value varchar(500),
    add_date datetime not null
)ENGINE=InnoDB;

create table item_excise_duty_map (
	item_excise_duty_map_id bigint primary key auto_increment,
	item_id bigint(20) not null,
	efris_excise_duty_list_id bigint not null
)ENGINE=InnoDB;

create table transaction_item_excise(
                        transaction_item_excise_id bigint(20) primary key auto_increment,
                        transaction_item_id bigint(20),
                        rate_name varchar(250),
                        rate_perc double,
                        rate_value double,
                        excise_tax double,
                        currency_code_tax varchar(50),
                        unit_code_tax varchar(50)
                        )ENGINE=InnoDB;

create table transaction_tax(
                        transaction_tax_id bigint(20) primary key auto_increment,
                        transaction_id bigint(20),
                        tax_category varchar(50),
                        tax_rate_name varchar(50),
                        tax_rate double,
                        taxable_amount double,
                        tax_amount double
                        )ENGINE=InnoDB;

CREATE TABLE transaction_package (
  transaction_package_id bigint(20) NOT NULL AUTO_INCREMENT,
  transaction_number varchar(50) NOT NULL,
  transaction_date date NOT NULL,
  store_id int(11) NOT NULL,
  store2_id int(11) DEFAULT NULL,
  transactor_id bigint(20) DEFAULT NULL,
  transaction_type_id int(11) NOT NULL,
  transaction_reason_id int(11) NOT NULL,
  sub_total double NOT NULL,
  total_trade_discount double NOT NULL,
  total_tax double NOT NULL,
  cash_discount double DEFAULT NULL,
  grand_total double NOT NULL,
  transaction_ref varchar(100) DEFAULT NULL,
  transaction_comment varchar(255) DEFAULT NULL,
  add_user_detail_id int(11) NOT NULL,
  add_date datetime NOT NULL,
  edit_user_detail_id int(11) DEFAULT NULL,
  edit_date datetime DEFAULT NULL,
  transaction_user_detail_id int(11) DEFAULT NULL,
  currency_code varchar(10) DEFAULT NULL,
  location_id bigint(20) DEFAULT NULL,
  status_code varchar(20) DEFAULT NULL,
  status_date datetime DEFAULT NULL,
  PRIMARY KEY (transaction_package_id),
  KEY idp_trans_number (transaction_number)
) ENGINE=InnoDB;

create table transaction_package_tax(
                        transaction_package_tax_id bigint(20) primary key auto_increment,
                        transaction_package_id bigint(20),
                        tax_category varchar(50),
                        tax_rate_name varchar(50),
                        tax_rate double,
                        taxable_amount double,
                        tax_amount double
                        )ENGINE=InnoDB;

CREATE TABLE transaction_package_item (
  transaction_package_item_id bigint(20) NOT NULL AUTO_INCREMENT,
  transaction_package_id bigint(20) NOT NULL,
  item_id bigint(20) DEFAULT NULL,
  unit_id int,
  batchno varchar(100) DEFAULT NULL,
  code_specific varchar(250) DEFAULT NULL,
  desc_specific varchar(250) DEFAULT NULL,
  desc_more varchar(250) DEFAULT NULL,
  item_qty double NOT NULL,
  base_unit_qty double NOT NULL,
  unit_price double DEFAULT NULL,
  unit_trade_discount double DEFAULT NULL,
  unit_vat double DEFAULT NULL,
  excise_rate_name varchar(250),
  excise_rate_perc double,
  excise_rate_value double,
  excise_tax double,
  excise_currency_code_tax varchar(50),
  excise_unit_code_tax varchar(50),
  amount double DEFAULT NULL,
  vat_rated varchar(50) DEFAULT NULL,
  vat_perc double DEFAULT NULL,
  narration varchar(100) DEFAULT NULL,
  PRIMARY KEY (transaction_package_item_id)
) ENGINE=InnoDB;

INSERT INTO transaction_type (transaction_type_id, transaction_type_name, transactor_label, transaction_number_label, transaction_output_label, transaction_date_label, transaction_user_label, is_transactor_mandatory, is_transaction_user_mandatory, is_transaction_ref_mandatory, is_authorise_user_mandatory, is_authorise_date_mandatory, is_delivery_address_mandatory, is_delivery_date_mandatory, is_pay_due_date_mandatory, is_expiry_date_mandatory, description, group_name, print_file_name1, print_file_name2, default_print_file, transaction_type_code, default_currency_code, trans_number_format) 
VALUES ('88', 'PACKAGING', '', 'Packaging No', 'PACKAGING', 'Packaging Date', 'Packed By', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'Packaging', 'OutputPCG_General', 'OutputPCG_General', '1', 'PCG', 'UGX', 'CYMDX');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id, description) 
VALUES ('135', 'SALES PACKAGING', '88', 'Pack items to be sold');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',186,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (99, 'ROUNDING_DECIMAL_PLACES', 'ROUND_DECIMAL_PLACES_ITEM', '0','Rounding off decimal places for (transaction item amounts). Put 0 for currency decimal places to take  or specify the number of decimal places');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (100, 'ROUNDING_DECIMAL_PLACES', 'ROUND_DECIMAL_PLACES_TOTAL', '0','Rounding off decimal places for (transaction grand total). Put 0 for currency decimal places to take precedence or specify the number of decimal places');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (101, 'ROUNDING_DECIMAL_PLACES', 'ROUND_DECIMAL_PLACES_TOTAL_OTHER', '0','Rounding off decimal places for (transaction totals excluding grand total). Put 0 for currency decimal places to take precedence or specify the number of decimal places');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',194,Now(),'6.0','');

ALTER TABLE item_excise_duty_map CHANGE COLUMN efris_excise_duty_list_id excise_duty_code VARCHAR(50) NOT NULL ;
create table efris_goods_commodity (
	efris_goods_commodity_id bigint primary key auto_increment,
    commodityCategoryCode varchar(18) not null default '',
    parentCode varchar(18) not null default '',
    commodityCategoryName varchar(200) not null default '',
    commodityCategoryLevel varchar(1) not null default '',
    rate varchar(4) not null default '',
    isLeafNode varchar(3) not null default '',
    serviceMark varchar(3) not null default '',
    isZeroRate varchar(3) not null default '',
    zeroRateStartDate varchar(100) not null default '',
    zeroRateEndDate varchar(100) not null default '',
    isExempt varchar(3) not null default '',
    exemptRateStartDate varchar(100) not null default '',
    exemptRateEndDate varchar(100) not null default '',
    enableStatusCode varchar(1) not null default '',
    exclusion varchar(1) not null default '',
    add_date datetime not null
)ENGINE=InnoDB;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',217,Now(),'6.0','');

create table download_status (
	download_status_id int primary key auto_increment,
    download_name varchar(50) not null unique,
	download_status int not null,
	download_status_msg varchar(50) not null,
	total_amount int not null,
	total_downloaded int not null,
    add_date datetime not null
)ENGINE=InnoDB;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',228,Now(),'6.0','');

ALTER TABLE transaction_item_excise ADD COLUMN excise_duty_code VARCHAR(50) NULL AFTER transaction_item_id;
INSERT INTO download_status (download_name, download_status, download_status_msg, total_amount, total_downloaded, add_date) VALUES ('GOODS COMMODITY', '3', 'NEW', '0','0',Now());

CREATE TABLE transaction_packacge_item_unit (
  transaction_packacge_item_unit_id bigint(20) NOT NULL DEFAULT '0',
  unit_id int(11) NOT NULL,
  base_unit_qty double NOT NULL,
  PRIMARY KEY (transaction_packacge_item_unit_id)
) ENGINE=InnoDB;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',240,Now(),'6.0','');

ALTER TABLE transaction_item_excise 
CHANGE COLUMN excise_tax calc_excise_tax_amount DOUBLE NULL DEFAULT NULL AFTER rate_unit_code_tax,
CHANGE COLUMN currency_code_tax rate_currency_code_tax VARCHAR(50) NULL DEFAULT NULL ,
CHANGE COLUMN unit_code_tax rate_unit_code_tax VARCHAR(50) NULL DEFAULT NULL ;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',247,Now(),'6.0','');

ALTER TABLE efris_excise_duty_list 
CHANGE COLUMN rate_value rate_qty VARCHAR(500) NULL DEFAULT NULL,
ADD COLUMN rateText_perc VARCHAR(500) NULL AFTER rateText,
ADD COLUMN rateText_qty VARCHAR(500) NULL AFTER rateText_perc;

ALTER TABLE transaction_item_excise 
DROP COLUMN rate_perc,
ADD COLUMN rate_text VARCHAR(100) NULL AFTER excise_duty_code,
ADD COLUMN rate_name_type VARCHAR(10) NULL AFTER rate_name;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',259,Now(),'6.0','');




