ALTER TABLE transaction_tax_map ADD COLUMN is_updated INT(1) NULL DEFAULT 0  AFTER add_date ,
ADD COLUMN update_synced INT(1) NULL DEFAULT 0  AFTER is_updated ;

ALTER TABLE stock_ledger 
ADD COLUMN tax_update_id bigint(20) NULL DEFAULT 0,
ADD COLUMN tax_is_updated int(1) NULL DEFAULT 0,
ADD COLUMN tax_update_synced INT(1) NULL DEFAULT 0;

CREATE  TABLE item_unspsc (
  item_unspsc_id BIGINT(20) NOT NULL AUTO_INCREMENT ,
  segment_code VARCHAR(50) NULL ,
  segment_name VARCHAR(255) NULL ,
  family_code VARCHAR(50) NULL ,
  family_name VARCHAR(255) NULL ,
  class_code VARCHAR(50) NULL ,
  class_name VARCHAR(255) NULL ,
  commodity_code VARCHAR(50) NULL ,
  commodity_name VARCHAR(255) NULL ,
  PRIMARY KEY (item_unspsc_id) );

ALTER TABLE item_tax_map ADD COLUMN is_synced INT(1) NULL DEFAULT 0;

UPDATE company_setting SET developer_phone='0785485346/0392000708' WHERE company_setting_id=1;

ALTER TABLE unit ADD COLUMN unit_symbol_tax VARCHAR(50) NULL DEFAULT '';

CREATE  TABLE unit_tax_list (
  unit_tax_list_id INT NOT NULL AUTO_INCREMENT ,
  unit_symbol_tax VARCHAR(50) NULL ,
  unit_name_tax VARCHAR(255) NULL ,
  PRIMARY KEY (unit_tax_list_id) );

ALTER TABLE acc_currency ADD COLUMN currency_code_tax VARCHAR(50) NULL DEFAULT '';

CREATE  TABLE acc_currency_tax_list (
  acc_currency_list_id INT NOT NULL AUTO_INCREMENT ,
  currency_code_tax VARCHAR(50) NULL ,
  currency_name_tax VARCHAR(255) NULL ,
  PRIMARY KEY (acc_currency_list_id) );

INSERT INTO acc_currency_tax_list (currency_code_tax, currency_name_tax) VALUES ('101', 'UGX');
INSERT INTO acc_currency_tax_list (currency_code_tax, currency_name_tax) VALUES ('102', 'USD');
INSERT INTO acc_currency_tax_list (currency_code_tax, currency_name_tax) VALUES ('103', 'GBP');
INSERT INTO acc_currency_tax_list (currency_code_tax, currency_name_tax) VALUES ('104', 'EUR');

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_11',28,Now(),'6.0','');
