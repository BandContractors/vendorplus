ALTER TABLE transaction_tax_map ADD COLUMN verification_code_tax VARCHAR(50) NULL DEFAULT ''  AFTER transaction_number_tax ,
ADD COLUMN qr_code_tax VARCHAR(1000) NULL DEFAULT ''  AFTER verification_code_tax ;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_12',4,Now(),'6.0','');
