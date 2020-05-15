-- correct store journal error
UPDATE acc_journal j SET j.store_id=(select t.store_id from transaction t where t.transaction_id=j.transaction_id) WHERE j.store_id=12 and acc_journal_id>0;
UPDATE acc_journal_payable j SET j.store_id=(select t.store_id from transaction t where t.transaction_id=j.transaction_id) WHERE j.store_id=12 and acc_journal_id>0;
UPDATE acc_journal_prepaid j SET j.store_id=(select t.store_id from transaction t where t.transaction_id=j.transaction_id) WHERE j.store_id=12 and acc_journal_id>0;
UPDATE acc_journal_receivable j SET j.store_id=(select t.store_id from transaction t where t.transaction_id=j.transaction_id) WHERE j.store_id=12 and acc_journal_id>0;

ALTER TABLE item ADD COLUMN hide_unit_price_invoice INT(1) NULL DEFAULT '0';

-- run this; modified at the end of each change batch change
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_08',10,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (56, 'COMPANY_SETTING', 'ACCOUNTING_PERIOD_END_DATE', '30/06','Day and Month that ends eaach Accouting Period in the format of dd/mm such as 30/06 for 01/07-30/06 period, 31/12 for 01/01-31/12 period.');

-- run this; modified at the end of each change batch change
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_08',16,Now(),'6.0','');
