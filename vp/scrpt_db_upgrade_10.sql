UPDATE acc_journal SET currency_code='UGX' WHERE acc_journal_id>0 and ifnull(currency_code,'')='';

UPDATE acc_ledger SET currency_code='UGX' WHERE acc_ledger_id>0 and ifnull(currency_code,'')='';


INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_09',51,Now(),'6.0','');