-- 746

-- payments
delete from pay_trans where pay_trans_id>0 and pay_id IN
(
select p.pay_id from pay p where p.pay_id>0 and p.bill_transactor_id=746
);
delete from pay where pay_id>0 and bill_transactor_id=746;

-- journal and ledgers
delete from acc_journal where acc_journal_id>0 and bill_transactor_id=746;
delete from acc_journal_prepaid where acc_journal_id>0 and  bill_transactor_id=746;
delete from acc_journal_receivable where acc_journal_id>0 and  bill_transactor_id=746;
delete from acc_ledger where acc_ledger_id>0 and  bill_transactor_id=746;
delete from acc_ledger_prepaid where acc_ledger_id>0 and   bill_transactor_id=746;
delete from acc_ledger_receivable where acc_ledger_id>0 and   bill_transactor_id=746;

-- transactions
delete from stock_out where stock_out_id>0 and transactor_id=746;
delete from transaction_item where transaction_item_id>0 and transaction_id IN
(
select t.transaction_id from transaction t where t.transactor_id=746
);
delete from transaction where transaction_id>0 and transactor_id=746;


