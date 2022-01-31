INSERT INTO transaction_type (transaction_type_id, transaction_type_name,transaction_output_label,transaction_number_label,transaction_type_code,trans_number_format,
transaction_date_label,transaction_ref_label,print_file_name1,print_file_name2,default_print_file) 
VALUES (85, 'SUBSCRIPTION','SUBSCRIPTION','Subscription No','SUBS','CYMDX','Subscription Date','','','',1);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (129, 'SUBSCRIPTION', 85);

create table subscription (subscription_id int(11) not null auto_increment, transactor_id bigint(20) not null, subscription_category_id int(11) not null, item_id bigint(20) not null, description varchar(150) not null, amount double not null, is_recurring varchar(10) not null, current_status varchar(20) not null, frequency varchar(20) not null, subscription_date datetime   not null, renewal_date datetime   not null, add_date datetime not null, added_by varchar(50) not null, last_edit_date datetime null, last_edited_by varchar(50), primary key (subscription_id));
create table subscription_category (subscription_category_id int(11) not null auto_increment, category_code varchar(50) not null, category_name varchar(50) not null, primary key (subscription_category_id));
create table subscription_log (subscription_log_id bigint(20) not null auto_increment, add_date datetime not null, added_by varchar(50) not null, subscription_id int(11) not null, action varchar(20) default '1' not null, primary key (subscription_log_id));

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',10,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (88, 'INVOICE', 'SHOW_BATCH_ON_PRINTOUT', '1','1:Shows Batch Number (BN) and Expiry Date (ED) for the item on the sales invoice print out, 0:Do not show');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',15,Now(),'6.0','');

ALTER TABLE subscription 
MODIFY renewal_date datetime null, MODIFY frequency varchar(20) null, ADD qty double not null, ADD unit_price double not null, ADD agent varchar(50) null;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',20,Now(),'6.0','');

ALTER TABLE subscription ADD expiry_date datetime null;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',24,Now(),'6.0','');

ALTER TABLE subscription ADD account_manager varchar(50) null;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',28,Now(),'6.0','');

create table business_category (business_category_id int(11) not null auto_increment, category_name varchar(50) not null, primary key (business_category_id));
ALTER TABLE subscription ADD business_category_id int(11) not null;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',33,Now(),'6.0','');