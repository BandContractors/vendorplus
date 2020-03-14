-- 7th Feb 2020
alter table category add column store_quick_order int(1) default 0;
-- 10th Feb 2020
alter table item add column override_gen_name int(1) default 0; -- 0 DoNotOverride, 1 OverrideShowYes, 2 OverrideShowNo
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES (53, 'COMPANY_SETTING', 'OUTPUT_SHOW_CO_NAME', '1','');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (115, 'OTHER REVENUE', 14);
INSERT INTO transaction_type (transaction_type_id, transaction_type_name) VALUES (75, 'CASH ADJUSTMENT');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (116, 'CASH ADJUSTMENT', 75);

ALTER TABLE acc_currency ADD COLUMN currency_unit VARCHAR(50) NULL  AFTER rounding_mode,ADD COLUMN decimal_unit VARCHAR(50) NULL  AFTER currency_unit;
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES (54, 'COMPANY_SETTING', 'OUTPUT_SHOW_AMOUNT_IN_WORDS', '0','');

ALTER TABLE transaction_item CHANGE COLUMN code_specific code_specific VARCHAR(250) NULL DEFAULT NULL  ,
CHANGE COLUMN desc_specific desc_specific VARCHAR(250) NULL DEFAULT NULL,
CHANGE COLUMN desc_more desc_more VARCHAR(250) NULL DEFAULT NULL  ;

ALTER TABLE transaction_item_hist CHANGE COLUMN code_specific code_specific VARCHAR(250) NULL DEFAULT NULL,
CHANGE COLUMN desc_specific desc_specific VARCHAR(250) NULL DEFAULT NULL  ,
CHANGE COLUMN desc_more desc_more VARCHAR(250) NULL DEFAULT NULL  ;

ALTER TABLE stock CHANGE COLUMN code_specific code_specific VARCHAR(250) NULL DEFAULT NULL,
CHANGE COLUMN desc_specific desc_specific VARCHAR(250) NULL DEFAULT NULL  ,
CHANGE COLUMN desc_more desc_more VARCHAR(250) NULL DEFAULT NULL  ;

ALTER TABLE stock_ledger CHANGE COLUMN code_specific code_specific VARCHAR(250) NULL DEFAULT NULL,
CHANGE COLUMN desc_specific desc_specific VARCHAR(250) NULL DEFAULT NULL;

ALTER TABLE stock_out CHANGE COLUMN code_specific code_specific VARCHAR(250) NULL DEFAULT NULL,
CHANGE COLUMN desc_specific desc_specific VARCHAR(250) NULL DEFAULT NULL;

-- 17th Feb 2020
ALTER TABLE discount_package_item DROP FOREIGN KEY Item_to_PackItem_on_ItemId ;
ALTER TABLE discount_package_item CHANGE COLUMN item_id item_id BIGINT(20) NULL,
  ADD CONSTRAINT Item_to_PackItem_on_ItemId
  FOREIGN KEY (item_id)
  REFERENCES item (item_id);

ALTER TABLE discount_package_item DROP FOREIGN KEY Store_to_DiscPackageItem_on_StoreId ;
ALTER TABLE discount_package_item CHANGE COLUMN store_id store_id INT(11) NULL,
  ADD CONSTRAINT Store_to_DiscPackageItem_on_StoreId
  FOREIGN KEY (store_id )
  REFERENCES store (store_id );

alter table discount_package add column store_scope varchar(500) default '';
alter table discount_package add column transactor_scope varchar(500) default ''; 

alter table discount_package_item add column category_scope varchar(500) default '';
alter table discount_package_item add column sub_category_scope varchar(500) default '';
alter table discount_package_item add column item_scope varchar(500) default '';

INSERT INTO transaction_type (transaction_type_id, transaction_type_name) VALUES (76, 'OPENING BANANCE');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (117, 'CUSTOMER OPENING BALANCE', 76);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (118, 'SUPPLIER OPENING BALANCE', 76);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (119, 'CASH ACCOUNT OPENING BALANCE', 76);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (120, 'OTHER ACCOUNT OPENING BALANCE', 76);

CREATE TABLE transactor_segment (
  transactor_segment_id int(11) NOT NULL AUTO_INCREMENT,
  segment_name varchar(50) NOT NULL,
  PRIMARY KEY (transactor_segment_id),
  UNIQUE KEY segment_name (segment_name)
);
ALTER TABLE transactor ADD COLUMN transactor_segment_id INT NULL;


alter table discount_package add column segment_scope varchar(500) default '';
alter table discount_package add column day_scope varchar(20) default '';
alter table discount_package add column time_scope_from varchar(10) default '';
alter table discount_package add column time_scope_to varchar(10) default '';

