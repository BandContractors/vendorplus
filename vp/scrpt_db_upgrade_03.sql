alter table transaction_type add column output_footer_message varchar(250);
alter table transaction_type add column default_term_condition varchar(250);
alter table company_setting modify column license_key varchar(400) not null;
alter table transaction add column is_delivered int(1) default 0;
alter table transaction add column source_code varchar(20);
alter table transaction_hist add column is_delivered int(1) default 0;
alter table transaction_hist add column source_code varchar(20);


