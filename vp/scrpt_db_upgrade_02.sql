alter table item add column specify_size int(1) default 0;
alter table item add column size_to_specific_name int(1) default 0;
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('38', 'COMPANY_SETTING', 'ORDER_VERSION', '0');
