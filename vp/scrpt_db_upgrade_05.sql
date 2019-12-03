UPDATE parameter_list SET description='2(Sales Invoice),11(Sale Order)' WHERE parameter_list_id=45;
alter table transaction add column is_invoiced int(1) default 0;

-- run these please
alter table transaction_hist add column is_invoiced int(1) default 0;
alter table transaction_hist add column location_id bigint(20);
alter table transaction_hist add column status_code varchar(20);
alter table transaction_hist add column status_date datetime;
alter table transaction_hist add column delivery_mode varchar(50) default 'Take Out';
alter table transaction_hist add column is_processed int(1) default 0;
alter table transaction_hist add column is_paid int(1) default 0;
alter table transaction_hist add column is_cancel int(1) default 0;

UPDATE acc_currency SET decimal_places=0 WHERE acc_currency_id>0 and currency_code='UGX';

alter table trans_production_item add column input_unit_qty double default 0 not null;
alter table trans_production_item add column input_qty_bfr_prod double default 0 not null;
alter table trans_production_item add column input_qty_afr_prod double default 0 not null;

INSERT INTO transaction_type (transaction_type_id, transaction_type_name) VALUES ('74', 'BACKDATING');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES ('113', 'BACKDATING', '74');

alter table store add column store_code varchar(10);

alter table trans_number_control modify column trans_number_control_id bigint(20) not null;
alter table trans_number_control drop primary key;
alter table trans_number_control modify column trans_number_control_id bigint(20) not null;
alter table trans_number_control modify column day_count bigint(20);
alter table trans_number_control add column month_count bigint(20);
alter table trans_number_control add column year_count bigint(20);
alter table trans_number_control add primary key(trans_number_control_id);
alter table trans_number_control modify column trans_number_control_id bigint(20) not null auto_increment;

alter table transactor add column store_id int(11);

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('48', 'ORDER', 'DELIVERY_MODES', 'Sit In,Take Out,Delivery','E.g. Sit In,Take Out,Delivery'); 

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('49', 'COMPANY_SETTING', 'CUSTOMER_NAME', 'Customer','E.g. Customer,Client,Patient,etc.'); 
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('50', 'COMPANY_SETTING', 'SUPPLIER_NAME', 'Supplier','E.g. Supplier,Agent, etc.');
UPDATE transaction_type SET transactor_label='' AND bill_transactor_label='' WHERE transaction_type_id>0;

alter table item add column expiry_band varchar(50);
update item set expiry_band='0030,0060,0120,0180' WHERE item_id>0 and is_track=1 and is_asset=0;

INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES ('114', 'EXPIRY ALERTS', '73');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('51', 'ALERTS', 'EXPIRY_ALERTS_MODE', '0','0(None),1(Expired,Unusable),2(Expired,Unusable,High),3(Expired,Unusable,High,Medium)');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('52', 'ALERTS', 'EXPIRY_ALERTS_EMAIL', '0','0(No),1(Yes-Expired,Unusable),2(Yes-Expired,Unusable,High),3(Yes-Expired,Unusable,High,Medium)');

alter table alert_general add column alert_category varchar(50);
UPDATE alert_general SET alert_category='Stock Alert' WHERE alert_general_id>0 AND (alert_type LIKE '%Stock%' OR alert_type LIKE '%No Reorder%');









