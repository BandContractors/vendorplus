INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('42', 'INVOICE', 'SHOW_PAYMENT_SUMMARY', '0','');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('43', 'PAY_METHOD', 'FORCE_SELECTION', '0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('44', 'COMPANY_SETTING', 'DEPLETE_SOLD_STOCK_UPON', '0','0(Sales Invoice),1(Goods Delivery)');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('45', 'GOODS_DELIVERY', 'TRANSACTION_REF', '0','2(Sales Invoice),11(Sale Order),0(Any)');

create table alert_general 
(alert_general_id bigint(20) not null auto_increment,alert_date date null, alert_type varchar(50), subject varchar(150), message varchar(500), 
alert_users varchar(250), read_by varchar(250), alert_items varchar(1000), add_date datetime null, add_by int(11), last_update_date datetime null, last_update_by int(11),status_code varchar(20), primary key (alert_general_id));

INSERT INTO transaction_type (transaction_type_id, transaction_type_name) VALUES ('73', 'ALERTS');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES ('112', 'STOCK ALERTS', '73');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('46', 'ALERTS', 'STOCK_ALERTS_MODE', '0','0(None),1(Out of stock),2(Low stock),3(Both Out and Low)');
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES ('47', 'ALERTS', 'STOCK_ALERTS_EMAIL', '0','0(No),1(Yes-Out of stock),2(Yes-Low stock),3(Yes-Both Out and Low)');
