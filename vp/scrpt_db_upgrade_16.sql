INSERT INTO transaction_type (transaction_type_id, transaction_type_name,transaction_output_label,transaction_number_label,transaction_type_code,trans_number_format,
transaction_date_label,transaction_ref_label,print_file_name1,print_file_name2,default_print_file) 
VALUES (85, 'SUBSCRIPTION','SUBSCRIPTION','Subscription No','SUBS','CYMDX','Subscription Date','','','',1);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (129, 'SUBSCRIPTION', 85);

create table subscription (subscription_id int(11) not null auto_increment, transactor_id bigint(20) not null, subscription_category_id int(11) not null, item_id bigint(20) not null, description varchar(150) not null, amount double not null, is_recurring varchar(10) not null, current_status varchar(20) not null, frequency varchar(20) not null, subscription_date datetime   not null, renewal_date datetime   not null, add_date datetime not null, added_by varchar(50) not null, last_edit_date datetime null, last_edited_by varchar(50), primary key (subscription_id));
create table subscription_category (subscription_category_id int(11) not null auto_increment, category_code varchar(50) not null, category_name varchar(50) not null, primary key (subscription_category_id));
create table subscription_log (subscription_log_id bigint(20) not null auto_increment, add_date datetime not null, added_by varchar(50) not null, subscription_id int(11) not null, action varchar(20) default '1' not null, primary key (subscription_log_id));

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',10,Now(),'6.0','');
