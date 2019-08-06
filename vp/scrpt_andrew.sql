update acc_journal set currency_code='UGX' where acc_journal_id>0;
truncate acc_ledger;
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