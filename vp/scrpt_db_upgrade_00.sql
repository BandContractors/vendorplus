ALTER TABLE acc_child_account ADD COLUMN balance_checker_on INT(1) NULL  AFTER last_edit_by;
ALTER TABLE transaction DROP INDEX transaction_number;
ALTER TABLE transaction_item DROP INDEX u_transitem_trans_item_batch;
ALTER TABLE transaction_item DROP INDEX u_transitem_trans_item_batch1;
ALTER TABLE transaction_item_hist DROP INDEX u_transitem_trans_item_batch;
ALTER TABLE transaction_item_hist DROP INDEX u_transitem_trans_item_batch1;
ALTER TABLE stock DROP INDEX u_stock_store_item_batch;
ALTER TABLE location DROP INDEX location_name;
alter table location add constraint Unique_store_location unique (store_id, location_name);
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('26', 'ORDER', 'LOCATION_NEEDED', '1');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('27', 'EMAIL', 'HOST', 'mail.wingersoft.co.ug');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('28', 'EMAIL', 'PORT', '587');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('29', 'EMAIL', 'SSL_FLAG', '0');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('30', 'EMAIL', 'USERNAME', 'sales@wingersoft.co.ug');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('31', 'EMAIL', 'PASSWORD', 'A8mRi4F3g4D8w4N2y4N1o2N_L5asX6mew2Xlg4Xac6Asq9LZL6T9');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('32', 'EMAIL', 'FROM_ADDRESS', 'Sales<sales@wingersoft.co.ug>');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('33', 'EMAIL', 'SEND_BATCH_SIZE', '20');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('34', 'GENERAL_NAME', 'NEW_LINE_EACH_NAME', '0');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('35', 'GENERAL_NAME', 'LINES_BTN_NAMES', '1');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('36', 'CURRENCY', 'NON_CURRENCY_TRANS_TYPES', '4,7,9,12');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value) VALUES ('37', 'COMPANY_SETTING', 'FOOTER_MESSAGE', 'Thank you for trusting us with your business. May God bless your endeavors!');

ALTER TABLE transaction_item CHANGE COLUMN code_specific code_specific VARCHAR(100) NULL DEFAULT NULL  ,
CHANGE COLUMN desc_specific desc_specific VARCHAR(150) NULL DEFAULT NULL  ,
CHANGE COLUMN desc_more desc_more VARCHAR(350) NULL DEFAULT NULL  ;

ALTER TABLE transaction_item_hist CHANGE COLUMN code_specific code_specific VARCHAR(100) NULL DEFAULT NULL  ,
CHANGE COLUMN desc_specific desc_specific VARCHAR(150) NULL DEFAULT NULL  ,
CHANGE COLUMN desc_more desc_more VARCHAR(350) NULL DEFAULT NULL  ;

INSERT INTO pay_method (pay_method_id, pay_method_name, display_order, is_active, is_deleted, is_default) VALUES ('11', 'PETTY CASH', '8', '1', '0', '0');
