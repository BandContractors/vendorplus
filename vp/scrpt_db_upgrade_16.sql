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

SET FOREIGN_KEY_CHECKS=0;
DROP INDEX  Trans_to_TransItem_on_TransId ON transaction_item;
ALTER TABLE transaction CHANGE COLUMN transaction_id transaction_id BIGINT(20) NOT NULL AUTO_INCREMENT ;
ALTER TABLE transaction_item CHANGE COLUMN transaction_item_id transaction_item_id BIGINT(20) NOT NULL AUTO_INCREMENT ;
SET FOREIGN_KEY_CHECKS=1;
UPDATE transaction_type SET trans_number_format='IYMW' WHERE transaction_type_id>0 AND length(ifnull(trans_number_format,''))=0;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',37,Now(),'6.0','');

create table business_category (business_category_id int(11) not null auto_increment, category_name varchar(50) not null, primary key (business_category_id));
ALTER TABLE subscription ADD business_category_id int(11) not null;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',42,Now(),'6.0','');

ALTER TABLE subscription_log ADD transactor_id bigint(20) not null, ADD subscription_category_id int(11) not null, ADD item_id bigint(20) not null, ADD description varchar(150) not null, ADD amount double not null, ADD  is_recurring varchar(10) not null, ADD current_status varchar(20) not null, ADD frequency varchar(20) null, ADD subscription_date datetime not null, ADD renewal_date datetime null,  ADD qty double not null, ADD unit_price double not null, ADD agent varchar(50) null, ADD expiry_date datetime null, ADD account_manager varchar(50) null, ADD business_category_id int(11) not null;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',46,Now(),'6.0','');

SET FOREIGN_KEY_CHECKS=0;
DROP INDEX TransType_to_Trans_on_TransTypeId  ON transaction;
DROP INDEX TransReas_to_Trans_on_TransReasId  ON transaction;
DROP INDEX Transactor_to_Trans_on_TransactorId  ON transaction;
DROP INDEX UserD_to_Trans_on_AddUserId  ON transaction;
DROP INDEX UserD_to_Trans_on_EditUserId  ON transaction;
DROP INDEX Store_to_Trans_on_StoreId  ON transaction;
DROP INDEX Store_to_Trans_on_Store2Id  ON transaction;
DROP INDEX transactor_trans_bill  ON transaction;
DROP INDEX tractor_traction_scheme  ON transaction;
DROP INDEX user_trans_aauthorisedby  ON transaction;
DROP INDEX Item_to_TransItem_on_ItemId  ON transaction_item;
DROP INDEX PayMeth_to_Pay_on_PayMethId ON pay;
DROP INDEX UserD_to_Pay_on_AddUserId ON pay;
DROP INDEX UserD_to_Pay_on_EditUserId ON pay;
DROP INDEX FKpay_trans264324 ON pay_trans;
CREATE INDEX idx_trans_number ON transaction(transaction_number);
SET FOREIGN_KEY_CHECKS=1;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',67,Now(),'6.0','');

SET FOREIGN_KEY_CHECKS=0;
CREATE INDEX idx_trans_id ON transaction_item(transaction_id);
SET FOREIGN_KEY_CHECKS=1;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',73,Now(),'6.0','');

UPDATE transaction_type SET print_file_name1='OutputSTR_Size_A4' WHERE transaction_type_id>0 AND print_file_name1='OutputSTR_General';
UPDATE transaction_type SET print_file_name2='OutputSTR_Size_A4' WHERE transaction_type_id>0 AND print_file_name2='OutputSTR_General';
UPDATE transaction_type SET print_file_name1='OutputST_Size_A4' WHERE transaction_type_id>0 AND print_file_name1='OutputST_General';
UPDATE transaction_type SET print_file_name2='OutputST_Size_A4' WHERE transaction_type_id>0 AND print_file_name2='OutputST_General';
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',81,Now(),'6.0','');

ALTER TABLE subscription ADD COLUMN free_at_reg INT(1) NOT NULL DEFAULT 0;
ALTER TABLE subscription_log ADD COLUMN free_at_reg INT(1) NOT NULL DEFAULT 0;
CREATE TABLE transaction_approval (
  transaction_approval_id bigint(20) NOT NULL AUTO_INCREMENT,
  transaction_hist_id bigint(20) NOT NULL,
  transaction_type_id int(11) NOT NULL,
  transaction_reason_id int(11) NOT NULL,
  request_date timestamp NOT NULL,
  request_by_id int(11) NOT NULL,
  approval_status int(1) DEFAULT '0',
  status_date timestamp NULL DEFAULT NULL,
  status_desc varchar(100) DEFAULT NULL,
  status_by_id int(11) NULL DEFAULT NULL,
  PRIMARY KEY (transaction_approval_id)
);
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',98,Now(),'6.0','');

ALTER TABLE transaction_approval 
ADD COLUMN transactor_id bigint(20) NULL,
ADD COLUMN store_id int(11) NULL,
ADD COLUMN amount_tendered double NULL,
ADD COLUMN transaction_id bigint(20) NULL,
ADD COLUMN grand_total double NULL;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',107,Now(),'6.0','');

INSERT INTO transaction_type (transaction_type_id, transaction_type_name,transaction_output_label,transaction_number_label,transaction_type_code,trans_number_format,
transaction_date_label,transaction_ref_label,print_file_name1,print_file_name2,default_print_file) 
VALUES (86, 'APPROVAL','APPROVAL','Approval No','APR','CYMDX','Approval Date','','','',1);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (130, 'SALE INVOICE APPROVAL', 86);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (131, 'PURCHASE INVOICE APPROVAL', 86);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (132, 'CASH PAYMENT APPROVAL', 86);
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (133, 'CASH RECEIPT APPROVAL', 86);

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (89, 'GENERAL', 'TRANSACTIONS_FOR_APPROVAL', '0','Seperate with comma. 0:None 1:Credit Sale 2:Cash Sale 3:Credit Purchase 4:Cash Purchase 5:Cash Receipt 6:Cash Payement');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_16',120,Now(),'6.0','');

