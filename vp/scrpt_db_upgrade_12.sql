ALTER TABLE transaction_tax_map ADD COLUMN verification_code_tax VARCHAR(50) NULL DEFAULT ''  AFTER transaction_number_tax ,
ADD COLUMN qr_code_tax VARCHAR(1000) NULL DEFAULT ''  AFTER verification_code_tax ;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_12',4,Now(),'6.0','');

ALTER TABLE transaction_tax_map 
ADD COLUMN update_type VARCHAR(20) NULL DEFAULT ''  AFTER update_synced ,
ADD COLUMN transaction_number_tax_update VARCHAR(50) NULL DEFAULT ''  AFTER update_type ,
ADD COLUMN verification_code_tax_update VARCHAR(50) NULL DEFAULT ''  AFTER transaction_number_tax_update ,
ADD COLUMN qr_code_tax_update VARCHAR(1000) NULL DEFAULT ''  AFTER verification_code_tax_update ,
ADD COLUMN is_updated_more_than_once INT(1) NULL DEFAULT '0'  AFTER qr_code_tax_update ,
ADD COLUMN more_than_once_update_reconsiled INT(1) NULL DEFAULT '0'  AFTER is_updated_more_than_once ;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_12',14,Now(),'6.0','');