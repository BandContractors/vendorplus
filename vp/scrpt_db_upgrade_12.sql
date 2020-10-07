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

CREATE  TABLE id_type (
  id_type_id INT NOT NULL AUTO_INCREMENT ,
  id_type_code VARCHAR(20) NOT NULL ,
  id_type_name VARCHAR(100) NOT NULL ,
  applies_to_individual INT NOT NULL ,
  applies_to_company INT NOT NULL ,
  is_active INT NOT NULL ,
  PRIMARY KEY (id_type_id) );
INSERT INTO id_type (id_type_code, id_type_name, applies_to_individual, applies_to_company, is_active) VALUES ('BRN', 'Business Registration Number', '0', '1', '1');
INSERT INTO id_type (id_type_code, id_type_name, applies_to_individual, applies_to_company, is_active) VALUES ('NIN', 'National Identifcation Number', '1', '0', '1');
INSERT INTO id_type (id_type_code, id_type_name, applies_to_individual, applies_to_company, is_active) VALUES ('PASSPORT', 'Passport', '1', '0', '1');
INSERT INTO id_type (id_type_code, id_type_name, applies_to_individual, applies_to_company, is_active) VALUES ('VISA', 'VISA', '1', '0', '1');
INSERT INTO id_type (id_type_code, id_type_name, applies_to_individual, applies_to_company, is_active) VALUES ('REFUGE', 'Refuge ID', '1', '0', '1');

-- Run command below and add/update to the id type manually in the back end
-- SELECT distinct id_type FROM transactor;

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (61, 'LOCATION', 'LEVEL_2_3_LABEL', 'District,Town/Village','Level1 is Country, so specify level 2 and 3 seperated by commas such as District,Town');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (62, 'COMPANY_SETTING', 'COUNTRY_CODE', 'UGA','Country of Registration for the company or Branch; this should be the ISO 3 digit country code');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (63, 'COMPANY_SETTING', 'IS_HQ', '1','Is Headquarter: 1 for Yes and 0 for No');

CREATE  TABLE iso_country_code (
  iso_country_code_id INT NOT NULL AUTO_INCREMENT ,
  country_short_name VARCHAR(100) NOT NULL ,
  code_2 VARCHAR(10) NOT NULL ,
  code_3 VARCHAR(10) NOT NULL ,
  numeric_code VARCHAR(10) NOT NULL ,
  PRIMARY KEY (iso_country_code_id) );

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_12',54,Now(),'6.0','');

ALTER TABLE item CHANGE COLUMN vat_rated vat_rated VARCHAR(50) NOT NULL  ;
ALTER TABLE transaction_item CHANGE COLUMN vat_rated vat_rated VARCHAR(50) NULL DEFAULT NULL  ;
ALTER TABLE transaction_item_hist CHANGE COLUMN vat_rated vat_rated VARCHAR(50) NULL DEFAULT NULL  ;

ALTER TABLE item_unspsc ADD COLUMN excise_duty_product_type VARCHAR(255) NULL  AFTER commodity_name ,
ADD COLUMN vat_rate VARCHAR(50) NULL  AFTER excise_duty_product_type ,
ADD COLUMN service_mark VARCHAR(45) NULL  AFTER vat_rate ,
ADD COLUMN zero_rate VARCHAR(45) NULL  AFTER service_mark ,
ADD COLUMN exempt_rate VARCHAR(45) NULL  AFTER zero_rate ;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_12',65,Now(),'6.0','');

ALTER TABLE transaction ADD COLUMN total_paid DOUBLE NULL DEFAULT '0'  AFTER source_code;

UPDATE transactor SET category='Consumer' WHERE transactor_id>0 and category='Individual';
UPDATE transactor SET category='Business' WHERE transactor_id>0 and category='Company';

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_12',72,Now(),'6.0','');

INSERT INTO transaction_type (transaction_type_id, transaction_type_name) VALUES (77, 'RISK REPORTS');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (121, 'RISK REPORTS', 77);

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_12',77,Now(),'6.0','');
