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

LOAD DATA INFILE 'C:/item_unspsc.csv' INTO TABLE `item_unspsc` FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

ALTER TABLE item_tax_map ADD COLUMN is_synced INT(1) NULL DEFAULT 0;

UPDATE company_setting SET developer_phone='0785485346/0392000708' WHERE company_setting_id=1;


INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_11',28,Now(),'6.0','');
