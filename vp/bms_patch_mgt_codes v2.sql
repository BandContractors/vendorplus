-- Journal to Ledger
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

-- Acc-Payable to Acc-Payable-Trade
update acc_journal set acc_coa_id=46,account_code='2-00-000-010' 
where acc_journal_id>0 and account_code='2-00-000-040';



-- Add Transaction Type for Prepayments
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id, description) VALUES ('90', 'PREPAID INCOME', '14', 'Prepaid income is revenue received in advance but which is not yet earned.');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id, description) VALUES ('91', 'PREPAID EXPENSE', '15', 'Prepaid expense is expense paid in advance but which has not yet been incurred.');

-- Add Pay Method for prepayments
INSERT INTO pay_method (pay_method_id, pay_method_name, display_order, is_active, is_deleted, is_default) VALUES ('6', 'PREPAID INCOME', '6', '1', '0', '0');
INSERT INTO pay_method (pay_method_id, pay_method_name, display_order, is_active, is_deleted, is_default) VALUES ('7', 'PREPAID EXPENSE', '7', '1', '0', '0');

-- Make PREPAID ACCOUNTS CHILD
UPDATE acc_coa SET is_child=0 WHERE account_code IN ('2-00-000-070','1-00-030-050');

-- Add Child Accounts for PrePayments
INSERT INTO acc_child_account 
(acc_coa_id, acc_coa_account_code, child_account_code, child_account_name, child_account_desc, user_detail_id, store_id, currency_id, currency_code, is_active, is_deleted, add_date, add_by, last_edit_date, last_edit_by) 
VALUES
(52, '2-00-000-070', 'PrepInc', 'Customer Deposit A/C', '', NULL, 1, NULL, NULL, 1, 0, '2018-02-15 19:56:12', 1, NULL, NULL),
(22, '1-00-030-050', 'PrepExp', 'Advance to Supplier A/C', '', NULL, 1, NULL, NULL, 1, 0, '2018-02-15 19:57:32', 1, NULL, NULL);


-- Over Paid
-- first back up
-- for D2D only
delete from acc_ledger;
update acc_journal set currency_code='UGX' where (currency_code='' or currency_code is null) and acc_journal_id>0;
-- run the first query above
-- some mgt
ALTER TABLE pay_trans ADD COLUMN grand_total FLOAT NULL  AFTER transaction_reason_id;
update pay_trans PT SET PT.grand_total=
(
select T.grand_total from transaction T where T.transaction_id=PT.transaction_id
) 
where PT.pay_trans_id>0;
select * from transaction where transaction_id=(
select max(pt.transaction_id) from pay_trans pt where pt.trans_paid_amount>pt.grand_total
);
delete from pay_trans where pay_trans_id>0 and trans_paid_amount>grand_total;
ALTER TABLE pay_trans DROP COLUMN grand_total;

-- real start
create table aa_trans_12 as (select * from transaction where transaction_type_id in(1,2));
ALTER TABLE aa_trans_12 ADD PRIMARY KEY (transaction_id);

create table aa_trans_total_paid as (
select transaction_id,IFNULL(sum(trans_paid_amount),0) as trans_paid_amount from pay_trans group by transaction_id
);
ALTER TABLE aa_trans_total_paid CHANGE COLUMN transaction_id transaction_id BIGINT(20) NOT NULL,
ADD PRIMARY KEY (transaction_id);

create table aa_trans_12_total_paid as 
(
select t.*,pt.trans_paid_amount from aa_trans_12 t left join aa_trans_total_paid pt on t.transaction_id=pt.transaction_id
);
ALTER TABLE aa_trans_12_total_paid ADD PRIMARY KEY (transaction_id);

update aa_trans_12_total_paid set trans_paid_amount=0 where transaction_id>0 and trans_paid_amount is null;

create table aa_trans_not_paid as 
(
select t.*,0 as convert_status from aa_trans_12_total_paid t where t.grand_total>t.trans_paid_amount 
order by t.transaction_id asc
);
ALTER TABLE aa_trans_not_paid ADD PRIMARY KEY (transaction_id);

-- run the UI on admin

-- delete the objects after testing
DROP table aa_trans_not_paid;
DROP table aa_trans_12;
DROP table aa_trans_total_paid;
DROP table aa_trans_12_total_paid;