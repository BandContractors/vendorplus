ALTER TABLE transaction DROP INDEX transaction_number;
-- Ensure or Mark user on ID:1 ==2
-- Run VP and after some house keeping below
-- Ensure Compay settings so that 1 print version is small and 2 is A4
-- Confirm Acc Date start and End Month
-- select min(transaction_date),max(transaction_date) from transaction;
-- From back end add acc periods equivalane to the years in data
-- Import/run in Workbench bms_IE_BMS_export.sql

-- move pay records to pay_trans
insert into pay_trans(pay_id,transaction_id,trans_paid_amount,transaction_type_id,transaction_reason_id) 
select p.pay_id,p.transaction_id,p.paid_amount,p.transaction_type_id,p.transaction_reason_id 
from pay p where p.transaction_id>0;

update pay_trans pt set pt.transaction_number=
(select t.transaction_number from transaction t where t.transaction_id=pt.transaction_id) 
where pt.pay_trans_id>0;

-- update trans with local currency
update transaction t set t.currency_code='UGX',t.xrate=1 where t.transaction_id>0;

-- update pay
update pay set currency_code='UGX', xrate=1 where pay_id>0;

-- upate item
update item set currency_code='UGX' where item_id>0;
update item set is_asset=0 where item_id>0;
update item set is_general=0 where item_id>0;
update item set is_buy=1,is_sale=1 where item_id>0;
update item set is_track=1 where item_id>0 and item_type='PRODUCT';
update item set is_track=0 where item_id>0 and item_type='SERVICE';

-- update item by adding asset and expense items
-- a) Get (Maximum unit_id)+1=UI; (Maximum category_id)+1=CI;(Maximum item_id)+1=II
-- b) Using UI, CI and II above, edit the red columns in bms_expense_asset.xls file
select max(unit_id) from unit; -- (Maximum unit_id)+1=UI=?17
select max(category_id) from category; -- (Maximum category_id)+1=CI=?137
select max(item_id) from item; -- (Maximum item_id)+1=II=?3150
-- c) Using Navicat, import the 3 sheets into the corresponding talbes but note:
--    i) Put '' as item code; Put add_date and edit_date in this format 2018-02-02 00:00:00
--    ii) After check the dates and in case 00s, run the script below
update item set add_date=Now(),edit_date=Now() where item_id>=3150;

-- update stock
update stock s set 
s.purchase_date=now(),
s.unit_cost=(select i.unit_cost_price from item i where i.item_id=s.item_id) 
where stock_id>0; 

update stock s set 
s.code_specific='',s.desc_specific='' 
where stock_id>0; 

-- update trans reason for transfer changes
update transaction set transaction_reason_id=6 where transaction_reason_id=7 and transaction_id>0;
update transaction set transaction_reason_id=19 where transaction_reason_id=21 and transaction_id>0;
-- update trans for dispose changes
update transaction t set t.transaction_reason_id=3,t.transaction_comment='LOST' where t.transaction_reason_id=4 and t.transaction_id>0;
update transaction t set t.transaction_reason_id=3,t.transaction_comment='DAMAGE' where t.transaction_reason_id=5 and t.transaction_id>0;
update transaction t set t.transaction_reason_id=3,t.transaction_comment='EXPIRED' where t.transaction_reason_id=3 and t.transaction_id>0;

-- Update group right functions
update group_right set function_name='DISPOSE STOCK' where group_right_id>0 and function_name='DISPOSE';
update group_right set function_name='GOOD AND SERVICE' where group_right_id>0 and function_name='PURCHASE INVOICE';
update group_right set function_name='GOOD AND SERVICE PO' where group_right_id>0 and function_name='PURCHASE ORDER';
update group_right set function_name='RETAIL SALE QUOTATION' where group_right_id>0 and function_name='SALE QUOTATION';
update group_right set function_name='EXEMPT SALE INVOICE' where group_right_id>0 and function_name='EXEMPT SALE';
update group_right set function_name='CUSTOMER' where group_right_id>0 and function_name='TRANSACTOR';
-- Delete group_right functions not assigned
DELETE FROM group_right where group_right_id>0 AND function_name NOT IN 
(select transaction_reason_name from transaction_reason);

-- update group right function with trans reason IDs
update group_right gr set gr.function_name=
(select tr.transaction_reason_id from transaction_reason tr where tr.transaction_reason_name=gr.function_name) 
where group_right_id>0;

-- update transaction item table nulls to ''
update transaction_item set code_specific='' where transaction_item_id>0 and code_specific is null;
update transaction_item set desc_specific='' where transaction_item_id>0 and desc_specific is null;

-- update transaction item for product and service account codes
update transaction_item ti set ti.account_code='4-10-000-010' where ti.transaction_item_id>0 
and 'PRODUCT'=(select i.item_type from item i where i.item_id=ti.item_id) 
and 2=(select t.transaction_type_id from transaction t where t.transaction_id=ti.transaction_id);
update transaction_item ti set ti.account_code='4-10-000-020' where ti.transaction_item_id>0 
and 'SERVICE'=(select i.item_type from item i where i.item_id=ti.item_id) 
and 2=(select t.transaction_type_id from transaction t where t.transaction_id=ti.transaction_id);

update transaction_item ti set ti.account_code='5-10-000-010' where ti.transaction_item_id>0 
and 'PRODUCT'=(select i.item_type from item i where i.item_id=ti.item_id) 
and 1=(select t.transaction_type_id from transaction t where t.transaction_id=ti.transaction_id);
update transaction_item ti set ti.account_code='5-10-000-020' where ti.transaction_item_id>0 
and 'SERVICE'=(select i.item_type from item i where i.item_id=ti.item_id) 
and 1=(select t.transaction_type_id from transaction t where t.transaction_id=ti.transaction_id);

-- update and insert payment methods
UPDATE pay_method SET pay_method_name='CHEQUE', display_order='3', is_active='1', is_deleted='0' WHERE pay_method_id='3';
UPDATE pay_method SET pay_method_name='CASH', display_order='1', is_active='1', is_deleted='0', is_default='1' WHERE pay_method_id='1';
INSERT INTO pay_method (pay_method_id, pay_method_name, display_order, is_active, is_deleted, is_default) VALUES ('5', 'BANK', '2', '1', '0', '0');
UPDATE pay_method SET display_order='4', is_active='1', is_deleted='0' WHERE pay_method_id='2';
UPDATE pay_method SET display_order='5', is_active='1', is_deleted='0' WHERE pay_method_id='4';

-- update and insert transaction types
UPDATE transaction_type SET transaction_type_name='DISPOSE STOCK' WHERE transaction_type_id='3';
UPDATE transaction_type SET transaction_type_name='ITEM RECEIVED' WHERE transaction_type_id='9';
INSERT INTO `transaction_type` (`transaction_type_id`, `transaction_type_name`, `transactor_label`, `transaction_number_label`, `transaction_output_label`, `bill_transactor_label`, `transaction_ref_label`, `transaction_date_label`, `transaction_user_label`, `is_transactor_mandatory`, `is_transaction_user_mandatory`, `is_transaction_ref_mandatory`, `is_authorise_user_mandatory`, `is_authorise_date_mandatory`, `is_delivery_address_mandatory`, `is_delivery_date_mandatory`, `is_pay_due_date_mandatory`, `is_expiry_date_mandatory`, `description`, `group_name`) VALUES
(14, 'CASH RECEIPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL),
(15, 'CASH PAYMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL),
(16, 'JOURNAL ENTRY', 'Customer', 'Journal No', 'Journal Entry', 'Customer', 'Reference No', 'Journal Date', 'Staff', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL),
(17, 'TRANSACTOR', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL),
(18, 'CASH TRANSFER', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, NULL),
(19, 'EXPENSE ENTRY', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'PURCHASE AND EXPENSE'),
(20, 'JOURNAL RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS'),
(21, 'TRIAL BALANCE', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS'),
(22, 'CASH FLOW RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS'),
(23, 'BALANCE SHEET', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'Statement of Financial Position', 'REPORTS - FINANCIAL ACCOUNTING'),
(24, 'INCOME STATEMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'Profit and Loss Statement', 'REPORTS - FINANCIAL ACCOUNTING'),
(25, 'CASH FLOW STATEMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - FINANCIAL ACCOUNTING'),
(26, 'ACCOUNT PERIOD', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ACCOUNT PERIOD'),
(27, 'POST-CLOSING TRIAL BALANCE', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - FINANCIAL ACCOUNTING'),
(28, 'CASH ACCOUNT RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH'),
(29, 'ACCOUNT STATEMENT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH'),
(30, 'RECEIVABLE DETAIL', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH'),
(31, 'RECEIVABLE SUMMARY', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH'),
(32, 'PAYABLE DETAIL', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH'),
(33, 'PAYABLE SUMMARY', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - CASH'),
(34, 'INVENTORY-STOCK RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - INVENTORY'),
(35, 'INVENTORY-ASSET RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - INVENTORY'),
(36, 'INVENTORY-EXPENSE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - INVENTORY'),
(37, 'ITEM-DETAIL-STOCK RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL'),
(38, 'ITEM-DETAIL-EXPENSE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL'),
(39, 'ITEM-DETAIL-ASSET RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL'),
(40, 'ITEM-DETAIL-LOCATION RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS - ITEM DETAIL'),
(41, 'CUSTOMER LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST'),
(42, 'SUPPLIER LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST'),
(43, 'SCHEME LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST'),
(44, 'PROVIDER LIST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PARTNER LIST'),
(45, 'SALES INVOICE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES'),
(46, 'SALES QUOTATION RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES'),
(47, 'SALES ORDER RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES'),
(48, 'SALES DELIVERY RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES'),
(49, 'SALES USER EARN RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT SALES'),
(50, 'PURCHASE INVOICE RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PURCHASES'),
(51, 'PURCHASE ORDER RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PURCHASES'),
(52, 'PURCHASE ITEM RECEIVED RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT PURCHASES'),
(53, 'TRANSFER REQUEST RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT ITEM TRANSFERS'),
(54, 'TRANSFER RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT ITEM TRANSFERS'),
(55, 'DISPOSE STOCK RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORT DISPOSE STOCK'),
(56, 'CASH RECEIPT RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS CASH'),
(57, 'CASH PAYMENT RPT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'REPORTS CASH'),
(58, 'CHART OF ACCOUNTS', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'ACCOUNTS'),
(59, 'DISCOUNT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'SALES'),
(60, 'INTER BRANCH', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'INTER BRANCH'),
(61, 'SETTING', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'SETTING'),
(62, 'SPEND POINT', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', 'No', NULL, 'SALES');

-- update and insert transaction reasons
UPDATE transaction_reason SET transaction_reason_name='GOOD AND SERVICE' WHERE transaction_reason_id='1';
UPDATE transaction_reason SET transaction_reason_name='DISPOSE STOCK' WHERE transaction_reason_id='3';
UPDATE transaction_reason SET transaction_reason_name='TRANSFER' WHERE transaction_reason_id='6';
UPDATE transaction_reason SET transaction_reason_name='GOOD AND SERVICE PO' WHERE transaction_reason_id='12';
UPDATE transaction_reason SET transaction_reason_name='TRANSFER REQUEST' WHERE transaction_reason_id='19';
DELETE FROM transaction_reason WHERE transaction_reason_id='4';
DELETE FROM transaction_reason WHERE transaction_reason_id='5';
DELETE FROM transaction_reason WHERE transaction_reason_id='7';
DELETE FROM transaction_reason WHERE transaction_reason_id='20';
INSERT INTO `transaction_reason` (`transaction_reason_id`, `transaction_reason_name`, `transaction_type_id`, `description`) VALUES
(21, 'CASH SALE', 14, NULL),
(22, 'CREDIT SALE', 14, NULL),
(23, 'CAPITAL', 14, NULL),
(24, 'LOAN', 14, NULL),
(25, 'CREDIT PURCHASE', 15, NULL),
(26, 'CASH PURCHASE', 15, NULL),
(27, 'EXPENSE', 1, NULL),
(28, 'ASSET RECEIVED', 9, NULL),
(29, 'ASSET', 1, NULL),
(30, 'EXPENSE PO', 8, NULL),
(31, 'ASSET PO', 8, NULL),
(32, 'EXPENSE RECEIVED', 9, NULL),
(33, 'LOAN INSTALLMENT', 15, NULL),
(34, 'OWNER CASH DRAWING', 15, NULL),
(35, 'JOURNAL ENTRY', 16, NULL),
(36, 'CUSTOMER', 17, NULL),
(37, 'SUPPLIER', 17, NULL),
(38, 'SCHEME', 17, NULL),
(39, 'PROVIDER', 17, NULL),
(40, 'EMPLOYEE', 17, NULL),
(41, 'CASH TRANSFER', 18, NULL),
(42, 'CHEQUE TRANSFER', 18, NULL),
(43, 'EXPENSE ENTRY', 19, NULL),
(44, 'JOURNAL ENTRY RPT', 20, NULL),
(45, 'TRIAL BALANCE', 21, NULL),
(46, 'CASH FLOW BY USER RPT', 22, NULL),
(47, 'BALANCE SHEET', 23, NULL),
(48, 'INCOME STATEMENT', 24, NULL),
(49, 'CASH FLOW STATEMENT', 25, NULL),
(50, 'ACCOUNT PERIOD CLOSE', 26, NULL),
(51, 'ACCOUNT PERIOD OPEN', 26, NULL),
(52, 'ACCOUNT PERIOD RE-OPEN', 26, NULL),
(53, 'ACCOUNT PERIOD DETAIL', 26, NULL),
(54, 'POST-CLOSING TRIAL BALANCE', 27, NULL),
(55, 'CASH ACCOUNT BALANCE', 28, NULL),
(56, 'ACCOUNT STATEMENT', 29, NULL),
(57, 'RECEIVABLE DETAIL', 30, NULL),
(58, 'RECEIVABLE SUMMARY', 31, NULL),
(59, 'PAYABLE DETAIL', 32, NULL),
(60, 'PAYABLE SUMMARY', 33, NULL),
(61, 'INVENTORY-STOCK RPT', 34, NULL),
(62, 'INVENTORY-ASSET RPT', 35, NULL),
(63, 'INVENTORY-EXPENSE RPT', 36, NULL),
(64, 'ITEM-DETAIL-STOCK RPT', 37, NULL),
(65, 'ITEM-DETAIL-EXPENSE RPT', 38, NULL),
(66, 'ITEM-DETAIL-ASSET RPT', 39, NULL),
(67, 'ITEM-DETAIL-LOCATION RPT', 40, NULL),
(68, 'CUSTOMER LIST RPT', 41, NULL),
(69, 'SUPPLIER LIST RPT', 42, NULL),
(70, 'SCHEME LIST RPT', 43, NULL),
(71, 'PROVIDER LIST RPT', 44, NULL),
(72, 'SALES INVOICE DETAIL RPT', 45, NULL),
(73, 'SALES QUOTATION DETAIL RPT', 46, NULL),
(74, 'SALES ORDER DETAIL RPT', 47, NULL),
(75, 'SALES DELIVERY DETAIL RPT', 48, NULL),
(76, 'SALES USER EARN RPT', 49, NULL),
(77, 'PURCHASE INVOICE DETAIL RPT', 50, NULL),
(78, 'PURCHASE ORDER DETAIL RPT', 51, NULL),
(79, 'PURCHASE ITEM RECEIVED DETAIL RPT', 52, NULL),
(80, 'ITEM TRANSFER REQUEST DETAIL RPT', 53, NULL),
(81, 'ITEM TRANSFER DETAIL RPT', 54, NULL),
(82, 'DISPOSE STOCK DETAIL RPT', 55, NULL),
(83, 'CASH RECEIPT DETAIL RPT', 56, NULL),
(84, 'CASH PAYMENT DETAIL RPT', 57, NULL),
(85, 'CHART OF ACCOUNTS DTL', 58, NULL),
(86, 'DISCOUNT', 59, NULL),
(87, 'INTER BRANCH', 60, NULL),
(88, 'SETTING', 61, NULL),
(89, 'SPEND POINT', 62, NULL);

-- Currency
INSERT INTO acc_currency (acc_currency_id, currency_name, currency_code, currency_no, is_local_currency, is_active, is_deleted, add_date, add_by, last_edit_date, last_edit_by) VALUES
(1, 'Uganda Shilling', 'UGX', 800, 1, 1, 0, '2018-01-01 00:00:00', 1, NULL, NULL);

-- Child accounts
INSERT INTO acc_child_account (acc_coa_id, acc_coa_account_code, 
child_account_code, child_account_name, child_account_desc, user_detail_id, store_id, currency_id, currency_code, 
is_active, is_deleted, add_date, add_by, last_edit_date, last_edit_by) 
SELECT 1, '1-00-000-010',s.store_name,s.store_name,s.store_name, NULL, s.store_id, 1, 'UGX', 1, 0, '2018-1-1 00:00:00', 1,NULL, 0 FROM store s;

-- Update child accounts in transaction
update transaction t set 
t.acc_child_account_id=(select c.acc_child_account_id from acc_child_account c where c.store_id=t.store_id) 
where t.transaction_id>0;

-- Update child accounts in payments
update pay p set 
p.acc_child_account_id=(select c.acc_child_account_id from acc_child_account c where c.store_id=p.store_id) 
where p.pay_id>0;

-- Pay Type and Pay Reas IDs
update pay set pay_type_id=14,pay_reason_id=22 where pay_id>0 and pay_category='IN';
update pay set pay_type_id=15,pay_reason_id=25 where pay_id>0 and pay_category='OUT';

-- Make all sales to No Client as 'Walk-in Customer'
SELECT max(transactor_id) FROM transactor; -- 932
INSERT INTO transactor (transactor_id, transactor_type, transactor_names, add_date, is_suspended, category) VALUES ('937', 'CUSTOMER', 'Walk-in Customer', '2018-02-02 00:00:00', 'No', 'Individual');
INSERT INTO transactor (transactor_id, transactor_type, transactor_names, add_date, is_suspended, category) VALUES ('938', 'SUPPLIER', 'Walk-in Supplier', '2018-02-02 00:00:00', 'No', 'Individual');

-- Make all sales to No Client as Walk-in Customer 17795
update transaction set bill_transactor_id=937,transactor_id=937 
where transaction_id>0 and transaction_type_id=2 and bill_transactor_id is null 
and transactor_id is null;

-- Update all purchase to No Client as Walk-in Supplier 17796
update transaction set bill_transactor_id=938,transactor_id=938 
where transaction_id>0 and transaction_type_id=1 and bill_transactor_id is null 
and transactor_id is null;

-- Re-map bill transactor payments to now new transactors
update pay p set p.bill_transactor_id=
(select t.bill_transactor_id from transaction t where t.transaction_id=p.transaction_id) 
where p.pay_id>0 and (p.bill_transactor_id=0 or p.bill_transactor_id is null);

-- Update pay status to 1
update pay set status=1 where pay_id>0;

-- Import VIEWS and SPs

-- convertTransToJournalAll()
create table trans_convert as select * from transaction where transaction_type_id IN(1,2,3);
ALTER TABLE trans_convert ADD COLUMN convert_status INT(1) NULL DEFAULT 0  AFTER xrate ;

-- convertTransToJournalAll()
create table pay_convert as select * from pay;
ALTER TABLE pay_convert ADD COLUMN convert_status INT(1) NULL DEFAULT 0  AFTER interest_amount ;

-- Run trans to journal and then code below - BMS
create table acc_journal_bk as select * from acc_journal;
create table acc_ledger_bk as select * from acc_ledger;
-- Run pay to journal - BMS
-- Delete ALL THE CONVERT TABLES!
-- Lastly please update with the VP model
-- Test access rights