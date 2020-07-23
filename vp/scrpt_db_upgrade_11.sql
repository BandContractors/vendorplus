ALTER TABLE transaction_tax_map ADD COLUMN is_updated INT(1) NULL DEFAULT 0  AFTER add_date ,
ADD COLUMN update_synced INT(1) NULL DEFAULT 0  AFTER is_updated ;

ALTER TABLE stock_ledger 
ADD COLUMN tax_update_id bigint(20) NULL DEFAULT 0,
ADD COLUMN tax_is_updated int(1) NULL DEFAULT 0,
ADD COLUMN tax_update_synced INT(1) NULL DEFAULT 0;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_11',7,Now(),'6.0','');
