create table transactor_fix as
select 
tr.transactor_id,
tr.transactor_names,
(select sum(ifnull(grand_total,0)) from transaction t1 where t1.transaction_type_id=2 and t1.transactor_id=tr.transactor_id) as TTotal,
(select sum(ifnull(p.paid_amount,0)) from pay p where p.bill_transactor_id=tr.transactor_id and p.pay_type_id=14) as TPaid,
(select sum(ifnull(j1.debit_amount,0)) from acc_journal_receivable j1 where j1.bill_transactor_id=tr.transactor_id) as LDebit,
(select sum(ifnull(j2.credit_amount,0)) from acc_journal_receivable j2 where j2.bill_transactor_id=tr.transactor_id) as LCredit,
0 as flag 
from transactor tr order by tr.transactor_id;

ALTER TABLE transactor_fix ADD PRIMARY KEY (transactor_id) ;

update transactor_fix set flag=1 where transactor_id>0 and (TTotal-TPaid)!=(LDebit-LCredit);

delete from transactor_fix where transactor_id>0 and flag=0;

create table trans_total_paid as 
select transaction_id,sum(ifnull(trans_paid_amount,0)) as total_paid from pay_trans group by transaction_id;

create table trans_sales_paid_bal as 
select t.transactor_id,t.transaction_id,t.grand_total,ifnull(tp.total_paid,0) as total_paid,
(t.grand_total-ifnull(tp.total_paid,0)) as tbal 
from transaction t 
inner join transactor_fix tf on t.transactor_id=tf.transactor_id 
left join trans_total_paid tp on t.transaction_id=tp.transaction_id 
where t.transaction_type_id=2 ;

-- step 1
delete from acc_journal where acc_journal_id>0 and account_code='1-00-010-010' and bill_transactor_id 
IN(select tx.transactor_id from transactor_fix tx);

SELECT max(job_id)+1 FROM acc_journal; -- 56812

INSERT INTO acc_journal
(journal_date,transaction_id,transaction_type_id,transaction_reason_id,
pay_id,pay_type_id,pay_reason_id,
store_id,bill_transactor_id,ledger_folio,acc_coa_id,account_code,currency_code,xrate,
debit_amount,credit_amount,narration,acc_period_id,
acc_child_account_id,is_active,is_deleted,add_date,add_by,last_edit_date,last_edit_by,job_id) 
SELECT t.transaction_date,t.transaction_id,t.transaction_type_id,t.transaction_reason_id,
NULL,NULL,NULL,
t.store_id,t.transactor_id,'',5,'1-00-010-010','UGX',1,
sb.tbal,0,'CREDIT RECEIVABLE',(select ap.acc_period_id from acc_period ap where t.transaction_date between ap.start_date and ap.end_date),
NULL,1,0,Now(),1,NULL,NULL,56812 
from trans_sales_paid_bal sb inner join transaction t on t.transaction_id=sb.transaction_id where sb.tbal>0;

TRUNCATE TABLE acc_journal_receivable;

TRUNCATE TABLE acc_ledger_receivable;

insert into acc_journal_receivable 
select * from acc_journal where account_code='1-00-010-010';

insert into acc_ledger_receivable 
(
acc_period_id,bill_transactor_id,account_code,acc_child_account_id,currency_code,
debit_amount,credit_amount,
debit_amount_lc,credit_amount_lc
)  
select 
ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code,
sum(ac.debit_amount),sum(ac.credit_amount),
sum(ac.debit_amount*ac.xrate),sum(ac.credit_amount*ac.xrate) from acc_journal ac 
where ac.account_code='1-00-010-010' 
group by ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code;

TRUNCATE TABLE acc_ledger;

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
