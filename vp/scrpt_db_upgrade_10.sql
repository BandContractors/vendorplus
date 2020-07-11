UPDATE acc_journal SET currency_code=(select c.currency_code from acc_currency c where c.is_local_currency=1) 
WHERE acc_journal_id>0 and ifnull(currency_code,'')='';
UPDATE acc_journal_payable SET currency_code=(select c.currency_code from acc_currency c where c.is_local_currency=1) 
WHERE acc_journal_id>0 and ifnull(currency_code,'')='';
UPDATE acc_journal_prepaid SET currency_code=(select c.currency_code from acc_currency c where c.is_local_currency=1) 
WHERE acc_journal_id>0 and ifnull(currency_code,'')='';
UPDATE acc_journal_receivable SET currency_code=(select c.currency_code from acc_currency c where c.is_local_currency=1) 
WHERE acc_journal_id>0 and ifnull(currency_code,'')='';

UPDATE acc_ledger SET currency_code=(select c.currency_code from acc_currency c where c.is_local_currency=1) 
WHERE acc_ledger_id>0 and ifnull(currency_code,'')='';
UPDATE acc_ledger_close SET currency_code=(select c.currency_code from acc_currency c where c.is_local_currency=1) 
WHERE acc_ledger_close_id>0 and ifnull(currency_code,'')='';
UPDATE acc_ledger_open_bal SET currency_code=(select c.currency_code from acc_currency c where c.is_local_currency=1) 
WHERE acc_ledger_open_bal_id>0 and ifnull(currency_code,'')='';
UPDATE acc_ledger_payable SET currency_code=(select c.currency_code from acc_currency c where c.is_local_currency=1) 
WHERE acc_ledger_id>0 and ifnull(currency_code,'')='';
UPDATE acc_ledger_prepaid SET currency_code=(select c.currency_code from acc_currency c where c.is_local_currency=1) 
WHERE acc_ledger_id>0 and ifnull(currency_code,'')='';
UPDATE acc_ledger_receivable SET currency_code=(select c.currency_code from acc_currency c where c.is_local_currency=1) 
WHERE acc_ledger_id>0 and ifnull(currency_code,'')='';

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_10',23,Now(),'6.0','');
