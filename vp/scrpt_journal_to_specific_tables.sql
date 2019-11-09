-- 0.0 : START

-- 1.0 : CREATE STRUCTURE (IF NOT CREATED ALREADY)
-- 1.1 : acc_journal_payable
CREATE TABLE IF NOT EXISTS `acc_journal_payable` (
  `acc_journal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `journal_date` date NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `transaction_type_id` int(11) DEFAULT NULL,
  `transaction_reason_id` int(11) DEFAULT NULL,
  `pay_id` bigint(20) DEFAULT NULL,
  `pay_type_id` int(11) DEFAULT NULL,
  `pay_reason_id` int(11) DEFAULT NULL,
  `store_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `ledger_folio` varchar(3) DEFAULT NULL,
  `acc_coa_id` int(11) NOT NULL,
  `account_code` varchar(20) NOT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `acc_period_id` int(11) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `job_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`acc_journal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
-- 1.2 : acc_journal_prepaid
CREATE TABLE IF NOT EXISTS `acc_journal_prepaid` (
  `acc_journal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `journal_date` date NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `transaction_type_id` int(11) DEFAULT NULL,
  `transaction_reason_id` int(11) DEFAULT NULL,
  `pay_id` bigint(20) DEFAULT NULL,
  `pay_type_id` int(11) DEFAULT NULL,
  `pay_reason_id` int(11) DEFAULT NULL,
  `store_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `ledger_folio` varchar(3) DEFAULT NULL,
  `acc_coa_id` int(11) NOT NULL,
  `account_code` varchar(20) NOT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `acc_period_id` int(11) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `job_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`acc_journal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- 1.0 : TRUNCATE DATA IF STRUCTURE AVAILABLE
TRUNCATE TABLE acc_journal_receivable;
TRUNCATE TABLE acc_journal_payable;
TRUNCATE TABLE acc_journal_prepaid;
TRUNCATE TABLE acc_ledger_receivable;
TRUNCATE TABLE acc_ledger_payable;
TRUNCATE TABLE acc_ledger_prepaid;

-- 1.3 : acc_journal_receivable
CREATE TABLE IF NOT EXISTS `acc_journal_receivable` (
  `acc_journal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `journal_date` date NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `transaction_type_id` int(11) DEFAULT NULL,
  `transaction_reason_id` int(11) DEFAULT NULL,
  `pay_id` bigint(20) DEFAULT NULL,
  `pay_type_id` int(11) DEFAULT NULL,
  `pay_reason_id` int(11) DEFAULT NULL,
  `store_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `ledger_folio` varchar(3) DEFAULT NULL,
  `acc_coa_id` int(11) NOT NULL,
  `account_code` varchar(20) NOT NULL,
  `currency_code` varchar(10) DEFAULT NULL,
  `xrate` double DEFAULT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `narration` varchar(200) DEFAULT NULL,
  `acc_period_id` int(11) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `is_active` int(1) NOT NULL,
  `is_deleted` int(1) NOT NULL,
  `add_date` datetime NOT NULL,
  `add_by` int(11) NOT NULL,
  `last_edit_date` datetime DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `job_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`acc_journal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
-- 1.4 : acc_ledger_payable
CREATE TABLE IF NOT EXISTS `acc_ledger_payable` (
  `acc_ledger_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_period_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `account_code` varchar(20) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) NOT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `debit_amount_lc` double DEFAULT NULL,
  `credit_amount_lc` double DEFAULT NULL,
  PRIMARY KEY (`acc_ledger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
-- 1.5 : acc_ledger_prepaid
CREATE TABLE IF NOT EXISTS `acc_ledger_prepaid` (
  `acc_ledger_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_period_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `account_code` varchar(20) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) NOT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `debit_amount_lc` double DEFAULT NULL,
  `credit_amount_lc` double DEFAULT NULL,
  PRIMARY KEY (`acc_ledger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
-- 1.6 : acc_ledger_receivable
CREATE TABLE IF NOT EXISTS `acc_ledger_receivable` (
  `acc_ledger_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_period_id` int(11) NOT NULL,
  `bill_transactor_id` bigint(20) DEFAULT NULL,
  `account_code` varchar(20) NOT NULL,
  `acc_child_account_id` int(11) DEFAULT NULL,
  `currency_code` varchar(10) NOT NULL,
  `debit_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `debit_amount_lc` double DEFAULT NULL,
  `credit_amount_lc` double DEFAULT NULL,
  PRIMARY KEY (`acc_ledger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- 2.0 : COPY JOURNAL RECORDS TO SPECIFIC JOURNAL TABLES
-- 2.1 : AR Trade
insert into acc_journal_receivable 
select * from acc_journal where account_code='1-00-010-010';
-- 2.2 : AP Trade
insert into acc_journal_payable 
select * from acc_journal where account_code='2-00-000-010';
-- 2.3 : Prepaid
insert into acc_journal_prepaid 
select * from acc_journal where (account_code='2-00-000-070' or account_code='1-00-030-050');

-- 3 : COPY JOURNAL RECORDS TO SPECIFIC LEDGER TABLES
-- 3.1 : Acc Rec Trade
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
-- 3.2 : Acc Pay Trade
insert into acc_ledger_payable 
(
acc_period_id,bill_transactor_id,account_code,acc_child_account_id,currency_code,
debit_amount,credit_amount,
debit_amount_lc,credit_amount_lc
)  
select 
ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code,
sum(ac.debit_amount),sum(ac.credit_amount),
sum(ac.debit_amount*ac.xrate),sum(ac.credit_amount*ac.xrate) from acc_journal ac 
where ac.account_code='2-00-000-010' 
group by ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code;
-- 3.3 : Prepaid (Income and Expense)
insert into acc_ledger_prepaid 
(
acc_period_id,bill_transactor_id,account_code,acc_child_account_id,currency_code,
debit_amount,credit_amount,
debit_amount_lc,credit_amount_lc
)  
select 
ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code,
sum(ac.debit_amount),sum(ac.credit_amount),
sum(ac.debit_amount*ac.xrate),sum(ac.credit_amount*ac.xrate) from acc_journal ac 
where (ac.account_code='2-00-000-070' or ac.account_code='1-00-030-050') 
group by ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code;

-- 0.0 : END