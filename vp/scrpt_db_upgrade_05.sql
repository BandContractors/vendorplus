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






