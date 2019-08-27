alter table item add column specify_size int(1) default 0;
alter table item add column size_to_specific_name int(1) default 0;
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('38', 'COMPANY_SETTING', 'ORDER_VERSION', '0');

alter table stock add column specific_size double default 1 not null;
alter table transaction_item add column specific_size double default 1;
alter table transaction_item_hist add column specific_size double default 1;
alter table trans_production add column specific_size double default 1 not null;
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('39', 'COMPANY_SETTING', 'PRODUCTION_VERSION', '0');

create table cdc_general (cdc_general_id bigint(20) not null auto_increment, cdc_function varchar(20), cdc_id varchar(20), cdc_date datetime null, cdc_start_time datetime null, cdc_end_time datetime null, is_passed int(1), records_affected double, add_date datetime null, add_by int(11), last_update_date datetime null, last_update_by int(11), primary key (cdc_general_id));
alter table snapshot_stock_value add column specific_size double default 1;
alter table snapshot_stock_value add column qty_damage double default 0;
alter table snapshot_stock_value add column cdc_id varchar(20);
alter table cdc_general add column snapshot_no int(11);
alter table cdc_general add column acc_period_id int(11);
alter table snapshot_stock_value add column store_id int(11);

create table stock_ledger (stock_ledger_id bigint(20) not null auto_increment, store_id int(11), item_id bigint(20), batchno varchar(100), code_specific varchar(50), desc_specific varchar(100), specific_size double default 1, qty_added double, qty_subtracted double, transaction_type_id int(11), action_type varchar(20), transaction_id bigint(20), user_detail_id int(11), add_date datetime null, primary key (stock_ledger_id));
alter table stock_ledger add column qty_bal double;




