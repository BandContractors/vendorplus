alter table transaction_hist modify column hist_add_date datetime not null;
alter table transaction_hist modify column add_date datetime not null;
alter table transaction_hist modify column edit_date datetime;
alter table transaction_hist modify column from_date datetime;
alter table transaction_hist modify column to_date datetime;
alter table transaction_hist add column location_id bigint(20);
alter table transaction_hist add column status_code varchar(20);
alter table transaction_hist add column status_date datetime;
alter table transaction_hist add column delivery_mode varchar(20) default 'Take Out';
alter table transaction_hist add column is_processed int(1) default 1;
alter table transaction_hist add column is_paid int(1) default 1;
alter table transaction_hist add column is_cancel int(1) default 0;
alter table transaction_hist add column is_invoiced int(1) default 0;

update parameter_list set parameter_value=0 where context='COMPANY_SETTING' and parameter_name='LIST_ITEMS_APPEND' and parameter_list_id>0;