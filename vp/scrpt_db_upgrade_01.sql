alter table acc_child_account add column balance_checker_on int(1) not null;
alter table trans_production add column transactor_id bigint(20);
alter table item add column is_free int(1) default 0;
alter table trans_production add column transaction_number varchar(50);
update trans_production set transaction_number=transaction_id where transaction_id>0;
UPDATE pay SET pay_number=pay_id WHERE pay_id>0 and (pay_number='' or pay_number is null);

UPDATE transaction_reason SET transaction_reason_name='RETAIL SALE ORDER' WHERE transaction_reason_id='16';
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES ('109', 'WHOLE SALE ORDER', '11');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES ('110', 'SPECIAL SALE ORDER', '11');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES ('111', 'SPECIAL SALE QUOTATION', '10');
