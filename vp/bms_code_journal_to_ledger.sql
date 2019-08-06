insert into acc_ledger 
(
acc_period_id,bill_transactor_id,account_code,acc_child_account_id,currency_code,
debit_amount,credit_amount,
debit_amount_lc,credit_amount_lc
)  
select 
ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code,
sum(ac.debit_amount),sum(ac.credit_amount),
sum(ac.debit_amount*ac.xrate),sum(ac.credit_amount*ac.xrate) from acc_journal ac  
group by ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code;


update acc_journal set acc_coa_id=46,account_code='2-00-000-010' 
where acc_journal_id>0 and account_code='2-00-000-040';

insert into acc_ledger 
(
acc_period_id,bill_transactor_id,account_code,acc_child_account_id,currency_code,
debit_amount,credit_amount,
debit_amount_lc,credit_amount_lc
)  
select 
ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code,
sum(ac.debit_amount),sum(ac.credit_amount),
sum(ac.debit_amount*ac.xrate),sum(ac.credit_amount*ac.xrate) from acc_journal ac 
where transaction_id>=43476 
group by ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code;
