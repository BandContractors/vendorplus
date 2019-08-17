alter table item add column specify_size int(1) default 0;
alter table item add column size_to_specific_name int(1) default 0;
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('38', 'COMPANY_SETTING', 'ORDER_VERSION', '0');

alter table stock add column specific_size double default 1 not null;
alter table transaction_item add column specific_size double default 1;
alter table transaction_item_hist add column specific_size double default 1;
alter table trans_production add column specific_size double default 1 not null;
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('39', 'COMPANY_SETTING', 'PRODUCTION_VERSION', '0');

