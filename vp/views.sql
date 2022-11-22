CREATE OR REPLACE VIEW view_pay AS
	SELECT pa.*,concat(ud.first_name,' ',ud.second_name) AS add_user_names,
	concat(ud2.first_name,' ',ud2.second_name) AS edit_user_names,
	tc.transactor_names AS bill_transactor_names,st.store_name,pm.pay_method_name 
	FROM pay pa 
	INNER JOIN store st ON pa.store_id=st.store_id  
	INNER JOIN user_detail ud ON pa.add_user_detail_id=ud.user_detail_id 
	INNER JOIN pay_method pm ON pa.pay_method_id=pm.pay_method_id 
	LEFT JOIN user_detail ud2 ON pa.edit_user_detail_id=ud2.user_detail_id 
	LEFT JOIN transactor tc ON pa.bill_transactor_id=tc.transactor_id;

CREATE OR REPLACE VIEW view_stock_in AS
	SELECT i.*,s.store_id,s.batchno,s.currentqty,s.item_mnf_date,s.item_exp_date,
	t.store_name,c.category_name,u.unit_name,sc.sub_category_name 
	FROM stock s 
	INNER JOIN item i ON s.item_id=i.item_id 
	INNER JOIN store t ON s.store_id=t.store_id 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id;

CREATE OR REPLACE VIEW view_stock_total AS 
	select t.store_id,i.item_id,i.category_id,i.sub_category_id,i.unit_cost_price,i.unit_retailsale_price,i.unit_wholesale_price,
	IFNULL(sum(s.currentqty),0) as currentqty,
	IFNULL(sum(s.currentqty)*i.unit_cost_price,0) as cost_value,
	IFNULL(sum(s.currentqty)*i.unit_wholesale_price,0) as wholesale_value,
	IFNULL(sum(s.currentqty)*i.unit_retailsale_price,0) as retailsale_value,
	i.reorder_level,
	t.store_name,i.description,u.unit_id,u.unit_name,c.category_name,sc.sub_category_name from item i 
	inner join unit u on i.unit_id=u.unit_id 
	inner join category c on i.category_id=c.category_id 
	left join sub_category sc on i.sub_category_id=sc.sub_category_id 
	left join stock s on i.item_id=s.item_id 
	left join store t on s.store_id=t.store_id 
	group by t.store_id,i.item_id 
	order by t.store_name,i.description;

CREATE OR REPLACE VIEW view_stock_all AS 
	select i.item_id,i.category_id,i.sub_category_id,
	IFNULL(sum(s.currentqty),0) as currentqty,i.reorder_level,
	i.description,u.unit_id,u.unit_name,c.category_name,sc.sub_category_name from item i 
	inner join unit u on i.unit_id=u.unit_id 
	inner join category c on i.category_id=c.category_id 
	left join sub_category sc on i.sub_category_id=sc.sub_category_id 
	left join stock s on i.item_id=s.item_id 
	group by i.item_id 
	order by i.description;

CREATE OR REPLACE VIEW view_item AS
	SELECT i.*,c.category_name,sc.sub_category_name,u.unit_symbol 
	FROM item i 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id;

CREATE OR REPLACE VIEW view_transaction AS 
	SELECT  t.*,s.store_name,s2.store_name AS store_name2,
	concat(ud.first_name,' ',ud.second_name) AS add_user_detail_name,
	concat(ud2.first_name,' ',ud2.second_name) AS edit_user_detail_name,
	concat(ud3.first_name,' ',ud3.second_name) AS transaction_user_detail_name,
	tr.transactor_names,tr2.transactor_names AS bill_transactor_names,
	tr3.transactor_names AS scheme_transactor_names,tt.transaction_type_name,
	tn.transaction_reason_name 
	FROM transaction t 
	INNER JOIN store s ON t.store_id=s.store_id 
	INNER JOIN transaction_type tt ON t.transaction_type_id=tt.transaction_type_id 
	INNER JOIN transaction_reason tn ON t.transaction_reason_id=tn.transaction_reason_id 
	INNER JOIN user_detail ud ON t.add_user_detail_id=ud.user_detail_id 
	LEFT JOIN store s2 ON t.store2_id=s2.store_id 
	LEFT JOIN user_detail ud2 ON t.edit_user_detail_id=ud2.user_detail_id 
	LEFT JOIN user_detail ud3 ON t.transaction_user_detail_id=ud3.user_detail_id
	LEFT JOIN transactor tr ON t.transactor_id=tr.transactor_id 
	LEFT JOIN transactor tr2 ON t.bill_transactor_id=tr2.transactor_id 
	LEFT JOIN transactor tr3 ON t.scheme_transactor_id=tr3.transactor_id;

CREATE OR REPLACE VIEW view_transaction_total_paid AS 
	SELECT  t.*,s.store_name,s2.store_name AS store_name2,
	concat(ud.first_name,' ',ud.second_name) AS add_user_detail_name,
	concat(ud2.first_name,' ',ud2.second_name) AS edit_user_detail_name,
	concat(ud3.first_name,' ',ud3.second_name) AS transaction_user_detail_name,
	tr.transactor_names,tr2.transactor_names AS bill_transactor_names,
	tr3.transactor_names AS scheme_transactor_names,tt.transaction_type_name,
	tn.transaction_reason_name,
	(select sum(trans_paid_amount) from pay_trans pt where pt.transaction_id=t.transaction_id) as total_paid_calc 
	FROM transaction t 
	INNER JOIN store s ON t.store_id=s.store_id 
	INNER JOIN transaction_type tt ON t.transaction_type_id=tt.transaction_type_id 
	INNER JOIN transaction_reason tn ON t.transaction_reason_id=tn.transaction_reason_id 
	INNER JOIN user_detail ud ON t.add_user_detail_id=ud.user_detail_id 
	LEFT JOIN store s2 ON t.store2_id=s2.store_id 
	LEFT JOIN user_detail ud2 ON t.edit_user_detail_id=ud2.user_detail_id 
	LEFT JOIN user_detail ud3 ON t.transaction_user_detail_id=ud3.user_detail_id
	LEFT JOIN transactor tr ON t.transactor_id=tr.transactor_id 
	LEFT JOIN transactor tr2 ON t.bill_transactor_id=tr2.transactor_id 
	LEFT JOIN transactor tr3 ON t.scheme_transactor_id=tr3.transactor_id;

CREATE OR REPLACE VIEW view_transaction_item AS 
	SELECT  ti.*,i.description,ut.unit_symbol,c.*,
	t.princ_scheme_member,t.scheme_card_number,t.grand_total,
	t.store_id,store2_id,t.transaction_date,t.add_date,t.edit_date,t.scheme_transactor_id,
	t.transactor_id,t.bill_transactor_id,t.transaction_type_id,t.transaction_reason_id,
	t.add_user_detail_id,t.edit_user_detail_id,t.transaction_user_detail_id,
	s.store_name,s2.store_name AS store_name2,
	concat(ud.first_name,' ',ud.second_name) AS add_user_detail_name,
	concat(ud2.first_name,' ',ud2.second_name) AS edit_user_detail_name,
	concat(ud3.first_name,' ',ud3.second_name) AS transaction_user_detail_name,
	tr.transactor_names,tr2.transactor_names AS bill_transactor_names,
	tr3.transactor_names AS scheme_transactor_names,
	tt.transaction_type_name,tn.transaction_reason_name 
	FROM transaction_item ti 
	INNER JOIN transaction t ON ti.transaction_id=t.transaction_id 
	INNER JOIN store s ON t.store_id=s.store_id 
	INNER JOIN transaction_type tt ON t.transaction_type_id=tt.transaction_type_id 
	INNER JOIN transaction_reason tn ON t.transaction_reason_id=tn.transaction_reason_id 
	INNER JOIN user_detail ud ON t.add_user_detail_id=ud.user_detail_id 
	INNER JOIN item i ON ti.item_id=i.item_id 
	INNER JOIN unit ut ON i.unit_id=ut.unit_id 
	INNER JOIN category c ON i.category_id=c.category_id 
	LEFT JOIN store s2 ON t.store2_id=s2.store_id 
	LEFT JOIN user_detail ud2 ON t.edit_user_detail_id=ud2.user_detail_id 
	LEFT JOIN user_detail ud3 ON t.transaction_user_detail_id=ud3.user_detail_id 
	LEFT JOIN transactor tr ON t.transactor_id=tr.transactor_id 
	LEFT JOIN transactor tr2 ON t.bill_transactor_id=tr2.transactor_id 
	LEFT JOIN transactor tr3 ON t.scheme_transactor_id=tr3.transactor_id;

CREATE OR REPLACE VIEW view_location AS 
	SELECT l.*,s.store_name FROM location l INNER JOIN store s ON l.store_id=s.store_id;

CREATE OR REPLACE VIEW view_item_location AS 
	SELECT il.*,l.store_id,l.location_name,s.store_name,i.description,u.* FROM item_location il 
	INNER JOIN location l ON il.location_id=l.location_id 
	INNER JOIN item i ON il.item_id=i.item_id 
	INNER JOIN store s ON l.store_id=s.store_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id;

CREATE OR REPLACE VIEW view_sub_category AS 
	SELECT s.*,c.category_name FROM sub_category s INNER JOIN category c ON s.category_id=c.category_id;

create or replace view transaction_item_bi as 
select ti.*, 
(
t.cash_discount/
(select count(ti2.transaction_id) from transaction_item ti2 where ti2.transaction_id=ti.transaction_id)
) as unit_cash_discount 
from transaction_item ti 
inner join transaction t on ti.transaction_id=t.transaction_id;


create or replace view view_stock_dif_stg1 as 
select 
s.item_id,
(select i.description from item i where i.item_id=s.item_id) as Description,
(select u.unit_name from item i2,unit u where i2.item_id=s.item_id and i2.unit_id=u.unit_id) as Unit,
s.currentqty as CurrentQty,
(select sum(ti1.item_qty) from view_transaction_item ti1 where 
	ti1.item_id=s.item_id and 
	(
		(ti1.transaction_type_id in (1) and ti1.store_id=1) or 
		(ti1.transaction_type_id in (7) and ti1.stock_effect='C' and ti1.store_id=1) or 
		(ti1.transaction_type_id in (4) and ti1.store2_id=1)
	)  
) as InQty,
(select sum(ti2.item_qty) from view_transaction_item ti2 where 
	ti2.item_id=s.item_id and 
	(
		(ti2.transaction_type_id in (3,2,4) and ti2.store_id=1) or 
		(ti2.transaction_type_id in (7) and ti2.stock_effect='D' and ti2.store_id=1) 
	)  
) as OutQty 
from stock s 
where s.store_id=1 
order by s.item_id DESC;

create or replace view view_stock_dif_stg2 as 
SELECT stg1.*,(InQty-OutQty) as ExpectedCurrentQty FROM view_stock_dif_stg1 stg1;

create or replace view view_stock_dif_stg3 as 
SELECT stg2.*, (CurrentQty-ExpectedCurrentQty) as ExcessQty FROM view_stock_dif_stg2 stg2;

create or replace view view_stock_difference as 
SELECT * FROM view_stock_dif_stg3 sd where ExcessQty!=0;

CREATE OR REPLACE VIEW view_ledger_general AS
select NULL as acc_ledger_id,ac.acc_period_id,NULL AS bill_transactor_id,ac.account_code,NULL as acc_child_account_id,ac.currency_code,
sum(ac.debit_amount) as debit_amount,sum(ac.credit_amount) as credit_amount,0 as debit_amount_lc,0 as credit_amount_lc from acc_ledger ac 
group by ac.acc_period_id,ac.account_code,ac.currency_code;

CREATE OR REPLACE VIEW view_ledger_general_lc AS
select NULL as acc_ledger_id,ac.acc_period_id,NULL AS bill_transactor_id,ac.account_code,NULL as acc_child_account_id,NULL as currency_code,
0 as debit_amount,0 as credit_amount,sum(ac.debit_amount_lc) as debit_amount_lc,sum(ac.credit_amount_lc) as credit_amount_lc from acc_ledger ac 
group by ac.acc_period_id,ac.account_code;

CREATE OR REPLACE VIEW view_stock_value AS 
select s.item_id,s.batchno,s.code_specific,s.desc_specific,i.currency_code,s.currentqty,s.unit_cost as unit_cost_price,
(s.currentqty*s.unit_cost) as cp_value,(s.currentqty*i.unit_wholesale_price) as wp_value,(s.currentqty*i.unit_retailsale_price) as rp_value 
from stock s inner join item i on s.item_id=i.item_id 
where i.is_suspended!='Yes' and i.is_track=1 and i.is_sale=1 and i.is_asset=0;

CREATE OR REPLACE VIEW view_ledger_temp_acc_balances AS 
select acc_period_id,account_code,currency_code,
(sum(debit_amount)-sum(credit_amount)) as debit_bal,(sum(credit_amount)-sum(debit_amount)) as credit_bal,
(sum(debit_amount_lc)-sum(credit_amount_lc)) as debit_bal_lc,(sum(credit_amount_lc)-sum(debit_amount_lc)) as credit_bal_lc  
from acc_ledger where (account_code like '4%' or account_code like '5%' or account_code='3-10-000-070' or account_code='3-10-000-080') 
group by acc_period_id,account_code,currency_code;

CREATE OR REPLACE VIEW view_ledger_general_union_close AS
select NULL as acc_ledger_id,ac.acc_period_id,NULL AS bill_transactor_id,ac.account_code,NULL as acc_child_account_id,ac.currency_code,
sum(ac.debit_amount) as debit_amount,sum(ac.credit_amount) as credit_amount,0 as debit_amount_lc,0 as credit_amount_lc from acc_ledger ac 
group by ac.acc_period_id,ac.account_code,ac.currency_code 
UNION 
select NULL as acc_ledger_id,ac2.acc_period_id,NULL AS bill_transactor_id,ac2.account_code,NULL as acc_child_account_id,ac2.currency_code,
sum(ac2.debit_bal) as debit_amount,sum(ac2.credit_bal) as credit_amount,0 as debit_amount_lc,0 as credit_amount_lc from acc_ledger_close ac2 
group by ac2.acc_period_id,ac2.account_code,ac2.currency_code;

CREATE OR REPLACE VIEW view_ledger_general_lc_union_close AS
select NULL as acc_ledger_id,ac.acc_period_id,NULL AS bill_transactor_id,ac.account_code,NULL as acc_child_account_id,NULL as currency_code,
0 as debit_amount,0 as credit_amount,sum(ac.debit_amount_lc) as debit_amount_lc,sum(ac.credit_amount_lc) as credit_amount_lc from acc_ledger ac 
group by ac.acc_period_id,ac.account_code 
UNION 
select NULL as acc_ledger_id,ac2.acc_period_id,NULL AS bill_transactor_id,ac2.account_code,NULL as acc_child_account_id,NULL as currency_code,
0 as debit_amount,0 as credit_amount,sum(ac2.debit_bal_lc) as debit_amount_lc,sum(ac2.credit_bal_lc) as credit_amount_lc from acc_ledger_close ac2 
group by ac2.acc_period_id,ac2.account_code;

CREATE OR REPLACE VIEW view_ledger_union_close_detail AS
select ac.acc_ledger_id,ac.acc_period_id,ac.bill_transactor_id,ac.account_code,ac.acc_child_account_id,ac.currency_code,
ac.debit_amount,ac.credit_amount,ac.debit_amount_lc,ac.credit_amount_lc from acc_ledger ac 
UNION 
select ac2.acc_ledger_close_id as acc_ledger_id,ac2.acc_period_id,NULL AS bill_transactor_id,ac2.account_code,NULL as acc_child_account_id,ac2.currency_code,
ac2.debit_bal as debit_amount,ac2.credit_bal as credit_amount,ac2.debit_bal_lc as debit_amount_lc,ac2.credit_bal_lc as credit_amount_lc 
from acc_ledger_close ac2;

CREATE OR REPLACE VIEW view_ledger_union_close_balances AS 
select acc_period_id,bill_transactor_id,account_code,acc_child_account_id,currency_code,
(sum(debit_amount)-sum(credit_amount)) as debit_bal,(sum(credit_amount)-sum(debit_amount)) as credit_bal,
(sum(debit_amount_lc)-sum(credit_amount_lc)) as debit_bal_lc,(sum(credit_amount_lc)-sum(debit_amount_lc)) as credit_bal_lc  
from view_ledger_union_close_detail group by acc_period_id,bill_transactor_id,account_code,acc_child_account_id,currency_code;

CREATE OR REPLACE VIEW view_ledger_union_open_balances AS 
select acc_ledger_id,acc_period_id,bill_transactor_id,account_code,acc_child_account_id,currency_code,
debit_amount,credit_amount,debit_amount_lc,credit_amount_lc from acc_ledger 
UNION 
select acc_ledger_open_bal_id as acc_ledger_id,acc_period_id,bill_transactor_id,account_code,acc_child_account_id,currency_code,
debit_amount,credit_amount,debit_amount_lc,credit_amount_lc from acc_ledger_open_bal;

CREATE OR REPLACE VIEW view_ledger_open_bal AS 
select acc_ledger_open_bal_id as acc_ledger_id,acc_period_id,bill_transactor_id,account_code,acc_child_account_id,currency_code,
debit_amount,credit_amount,debit_amount_lc,credit_amount_lc from acc_ledger_open_bal;

CREATE OR REPLACE VIEW view_inventory_stock AS
	SELECT s.*,i.item_code,i.description,i.currency_code,i.reorder_level,i.category_id,i.sub_category_id,
	s.unit_cost as unit_cost_price,i.unit_retailsale_price,i.unit_wholesale_price,
	t.store_name,c.category_name,u.unit_name,u.unit_symbol,sc.sub_category_name,
	0 as cost_value,0 as retailsale_value,0 as wholesale_value 
	FROM stock s 
	INNER JOIN item i ON s.item_id=i.item_id AND i.is_sale=1 AND i.is_asset=0 AND i.is_track=1 
	INNER JOIN store t ON s.store_id=t.store_id 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id;

CREATE OR REPLACE VIEW view_inventory_expense AS
	SELECT s.*,i.item_code,i.description,i.currency_code,i.reorder_level,i.category_id,i.sub_category_id,
	s.unit_cost as unit_cost_price,i.unit_retailsale_price,i.unit_wholesale_price,
	t.store_name,c.category_name,u.unit_name,u.unit_symbol,sc.sub_category_name,
	0 as cost_value,0 as retailsale_value,0 as wholesale_value 
	FROM stock s 
	INNER JOIN item i ON s.item_id=i.item_id AND i.is_sale=0 AND i.is_asset=0 AND i.is_track=1 AND i.is_buy=1 
	INNER JOIN store t ON s.store_id=t.store_id 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id;

CREATE OR REPLACE VIEW view_inventory_asset AS
	SELECT s.*,i.item_code,i.description,i.currency_code,i.reorder_level,i.category_id,i.sub_category_id,
	s.unit_cost as unit_cost_price,i.unit_retailsale_price,i.unit_wholesale_price,i.asset_type,
	t.store_name,c.category_name,u.unit_name,u.unit_symbol,sc.sub_category_name,
	0 as cost_value,0 as retailsale_value,0 as wholesale_value 
	FROM stock s 
	INNER JOIN item i ON s.item_id=i.item_id AND i.is_sale=0 AND i.is_asset=1 AND i.is_track=1 
	INNER JOIN store t ON s.store_id=t.store_id 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id;

CREATE OR REPLACE VIEW view_inventory_in AS
	SELECT (CASE WHEN i.is_sale=1 THEN 1 ELSE 2 END) as stock_type_order,i.expense_type as stock_type,
	s.*,i.item_code,i.description,i.currency_code,i.reorder_level,i.category_id,i.sub_category_id,
	s.unit_cost as unit_cost_price,i.unit_retailsale_price,i.unit_wholesale_price,
	t.store_name,c.category_name,u.unit_name,u.unit_symbol,sc.sub_category_name,
	0 as cost_value,0 as retailsale_value,0 as wholesale_value 
	FROM stock s 
	INNER JOIN item i ON s.item_id=i.item_id AND i.is_asset=0 AND i.is_track=1 
	INNER JOIN store t ON s.store_id=t.store_id 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id;

CREATE OR REPLACE VIEW view_inventory_low_out AS
	SELECT (CASE WHEN i.is_sale=1 THEN 1 ELSE 2 END) as stock_type_order,i.expense_type as stock_type,i.*,
	(select ifnull(sum(s.currentqty),0) from stock s where s.item_id=i.item_id) as qty_total,
	c.category_name,u.unit_symbol,sc.sub_category_name 
	FROM item i  
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id 
	WHERE i.is_asset=0 AND i.is_track=1;

CREATE OR REPLACE VIEW view_inventory_low_out_vw AS 
	SELECT v.*,
	CASE
		WHEN v.reorder_level=0 THEN 'No Reorder Level' 
		WHEN v.reorder_level>0 and v.qty_total<=0 THEN 'Out of Stock' 
		WHEN v.reorder_level>0 and v.qty_total<=v.reorder_level THEN 'Low Stock'  
		WHEN v.reorder_level>0 and v.qty_total>v.reorder_level THEN 'Stocked'  
		ELSE ''
	END as stock_status 
FROM view_inventory_low_out v;

CREATE OR REPLACE VIEW view_inventory_low_out_per_store AS 
	SELECT (CASE WHEN i.is_sale=1 THEN 1 ELSE 2 END) as stock_type_order,i.expense_type as stock_type,i.*,
	(select ifnull(sum(s.currentqty),0) from stock s where s.item_id=i.item_id and s.store_id=ro.store_id) as qty_total,
	c.category_name,u.unit_symbol,sc.sub_category_name,ro.reorder_level as reorder_level_ro,ro.store_id as store_id_ro  
	FROM item i  
    INNER JOIN item_store_reorder ro ON i.item_id=ro.item_id 
    INNER JOIN store st on ro.store_id=st.store_id 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id 
	WHERE i.is_asset=0 AND i.is_track=1 AND ro.reorder_level>0;

CREATE OR REPLACE VIEW view_inventory_low_out_per_store_vw AS 
	SELECT v.*,
	CASE
		WHEN v.reorder_level_ro=0 THEN 'No Reorder Level' 
		WHEN v.reorder_level_ro>0 and v.qty_total<=0 THEN 'Out of Stock' 
		WHEN v.reorder_level_ro>0 and v.qty_total<=v.reorder_level_ro THEN 'Low Stock'  
		WHEN v.reorder_level_ro>0 and v.qty_total>v.reorder_level_ro THEN 'Stocked'  
		ELSE ''
	END as stock_status 
	FROM view_inventory_low_out_per_store v;

CREATE OR REPLACE VIEW view_item_detail AS
	SELECT i.*,c.category_name,sc.sub_category_name,u.unit_symbol,
	CASE
		WHEN i.is_sale=1 and i.is_asset=0 THEN 'Sale' 
		WHEN i.is_sale=0 and i.is_asset=0 THEN 'Expenditure' 
		WHEN i.is_asset=1 THEN 'Asset' 
		ELSE ''
	END as purpose,ifnull(im.item_code_tax,'') as item_code_tax,ifnull(im.is_synced,0) as is_synced 
	FROM item i 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id 
	LEFT JOIN item_tax_map im ON i.item_id=im.item_id;

CREATE OR REPLACE VIEW view_item_detail_stock AS
	SELECT i.*,c.category_name,sc.sub_category_name,u.unit_symbol 
	FROM item i 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id 
	WHERE  i.is_sale=1 AND i.is_asset=0;

CREATE OR REPLACE VIEW view_item_detail_expense AS
	SELECT i.*,c.category_name,sc.sub_category_name,u.unit_symbol 
	FROM item i 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id 
	WHERE  i.is_sale=0 AND i.is_asset=0;

CREATE OR REPLACE VIEW view_item_detail_asset AS
	SELECT i.*,c.category_name,sc.sub_category_name,u.unit_symbol 
	FROM item i 
	INNER JOIN category c ON i.category_id=c.category_id 
	INNER JOIN unit u ON i.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id 
	WHERE  i.is_sale=0 AND i.is_asset=1;

CREATE OR REPLACE VIEW view_ledger_acc_rec_balances AS 
	SELECT al.account_code,al.bill_transactor_id,al.currency_code,
	sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,
	sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc 
	FROM acc_ledger al INNER JOIN acc_coa ac ON al.account_code=ac.account_code 
	WHERE al.account_code LIKE '1-00-010%' 
	GROUP BY al.account_code,al.bill_transactor_id,al.currency_code 
	HAVING (sum(al.debit_amount)-sum(al.credit_amount))!=0;

CREATE OR REPLACE VIEW view_ledger_acc_pay_balances AS 
	SELECT al.account_code,al.bill_transactor_id,al.currency_code,
	sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,
	sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc 
	FROM acc_ledger al INNER JOIN acc_coa ac ON 
	al.account_code=ac.account_code WHERE al.account_code LIKE '2-00-000%' 
	GROUP BY al.account_code,al.bill_transactor_id,al.currency_code 
	HAVING (sum(al.credit_amount)-sum(al.debit_amount))!=0;

CREATE OR REPLACE VIEW view_ledger_acc_pay_balances_OLD AS 
	SELECT al.account_code,al.bill_transactor_id,al.currency_code,
	sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,
	sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc 
	FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON 
	al.account_code=ac.account_code WHERE al.account_code LIKE '2-00-000%' 
	GROUP BY al.account_code,al.bill_transactor_id,al.currency_code 
	HAVING (sum(al.credit_amount)-sum(al.debit_amount))!=0;

CREATE OR REPLACE VIEW view_ledger_acc_prepaid_income_balances AS 
	SELECT al.account_code,al.bill_transactor_id,al.currency_code,
	sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,
	sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc 
	FROM view_ledger_union_open_balances al INNER JOIN acc_coa ac ON 
	al.account_code=ac.account_code WHERE al.account_code='2-00-000-070' 
	GROUP BY al.account_code,al.bill_transactor_id,al.currency_code 
	HAVING (sum(al.credit_amount)-sum(al.debit_amount))!=0;

CREATE OR REPLACE VIEW view_ledger_acc_prepaid_expense_balances AS 
	SELECT al.account_code,al.bill_transactor_id,al.currency_code,
	sum(al.debit_amount) as debit_amount,sum(al.credit_amount) as credit_amount,
	sum(al.debit_amount_lc) as debit_amount_lc,sum(al.credit_amount_lc) as credit_amount_lc 
	FROM acc_ledger al INNER JOIN acc_coa ac ON al.account_code=ac.account_code 
	WHERE al.account_code LIKE '1-00-030%' 
	GROUP BY al.account_code,al.bill_transactor_id,al.currency_code 
	HAVING (sum(al.debit_amount)-sum(al.credit_amount))!=0;

CREATE OR REPLACE VIEW view_fact_sales_no_items AS 
	select transaction_id,transaction_date,YEAR(transaction_date) as y,MONTH(transaction_date) as m,DAY(transaction_date) as d,
	grand_total*xrate as amount from transaction where transaction_type_id IN(2,65,68) and grand_total>0;

CREATE OR REPLACE VIEW view_fact_sales_no_view AS 
select transaction_id,transaction_date,YEAR(transaction_date) as y,MONTH(transaction_date) as m,DAY(transaction_date) as d,
	grand_total*xrate as amount,(select count(*) from transaction_item ti where ti.transaction_id=t.transaction_id) as c from transaction t where transaction_type_id IN(2,65,68) and grand_total>0;

CREATE OR REPLACE VIEW view_fact_trans_avg_items AS 
select transaction_id as tid,count(transaction_id) as ic from transaction_item group by transaction_id;

CREATE OR REPLACE VIEW view_fact_sales AS 
select transaction_id,transaction_date,YEAR(transaction_date) as y,MONTH(transaction_date) as m,DAY(transaction_date) as d,
	grand_total*xrate as amount,v.ic as c from transaction t 
	inner join view_fact_trans_avg_items v on t.transaction_id=v.tid where transaction_type_id IN(2,65,68) and grand_total>0;


CREATE OR REPLACE VIEW view_fact_sales_items AS 
	select t.transaction_id,ti.item_id,ti.item_qty,ti.amount_inc_vat*t.xrate as amount,
	t.transaction_date,YEAR(t.transaction_date) as y,MONTH(t.transaction_date) as m,DAY(t.transaction_date) as d 
	from transaction_item ti inner join transaction t on 
	ti.transaction_id=t.transaction_id and t.transaction_type_id IN(2,65,68) and t.grand_total>0;

CREATE OR REPLACE VIEW view_fact_expenses AS 
	select transaction_type_id,transaction_reason_id,transaction_id,transaction_date,YEAR(transaction_date) as y,MONTH(transaction_date) as m,DAY(transaction_date) as d,
	grand_total*xrate as amount from transaction where transaction_type_id IN(1,19) and grand_total>0;

CREATE OR REPLACE VIEW view_fact_expenses_items AS 
	select t.transaction_type_id,t.transaction_reason_id,t.transaction_id,ti.item_id,ti.item_qty,
	ti.amount_inc_vat*t.xrate as amount,
	t.transaction_date,YEAR(t.transaction_date) as y,MONTH(t.transaction_date) as m,DAY(t.transaction_date) as d 
	from transaction_item ti inner join transaction t on 
	ti.transaction_id=t.transaction_id and t.transaction_type_id IN(1,19) and t.grand_total>0;

CREATE OR REPLACE VIEW view_stock_expiry_status AS 
	SELECT 
	s.*,i.unit_id,i.category_id,i.sub_category_id,i.is_sale,i.is_asset,i.is_track,i.is_buy,i.expense_type,i.description,i.item_type,
	ifnull(i.is_general,0) as is_general,i.item_code,i.currency_code,
	DATEDIFF(s.item_exp_date,now()) as days_to_expiry,
	CASE 
	WHEN DATEDIFF(s.item_exp_date,now())<=0 THEN 'Expired' 
	WHEN CAST(SUBSTRING(i.expiry_band,1,4) AS UNSIGNED)>0 AND DATEDIFF(s.item_exp_date,now())<=CAST(SUBSTRING(i.expiry_band,1,4) AS UNSIGNED) THEN 'Unusable' 
	WHEN CAST(SUBSTRING(i.expiry_band,6,4) AS UNSIGNED)>0 AND DATEDIFF(s.item_exp_date,now())<=CAST(SUBSTRING(i.expiry_band,6,4) AS UNSIGNED) THEN 'High' 
	WHEN CAST(SUBSTRING(i.expiry_band,11,4) AS UNSIGNED)>0 AND DATEDIFF(s.item_exp_date,now())<=CAST(SUBSTRING(i.expiry_band,11,4) AS UNSIGNED) THEN 'Medium' 
	WHEN CAST(SUBSTRING(i.expiry_band,16,4) AS UNSIGNED)>0 AND DATEDIFF(s.item_exp_date,now())<=CAST(SUBSTRING(i.expiry_band,16,4) AS UNSIGNED) THEN 'Low' 
	WHEN CAST(SUBSTRING(i.expiry_band,16,4) AS UNSIGNED)>0 AND DATEDIFF(s.item_exp_date,now())>CAST(SUBSTRING(i.expiry_band,16,4) AS UNSIGNED) THEN 'NoRisk' 
	ELSE 'N/A' 
	END as expiry_status 
	FROM stock s 
	INNER JOIN item i ON s.item_id=i.item_id;

CREATE OR REPLACE VIEW view_stock_expiry_status_vw AS 
SELECT sv.*,
	IFNULL(sv.expense_type,'') as stock_type,
	CASE 
	WHEN sv.is_sale=1 AND sv.is_asset=0 AND sv.is_track=1 THEN 1 
	WHEN sv.is_sale=0 AND sv.is_asset=0 AND sv.is_track=1 THEN 2 
	ELSE 3 
	END as stock_type_order,
	c.category_name,u.unit_symbol,sc.sub_category_name 
	FROM view_stock_expiry_status as sv 
	INNER JOIN category c ON sv.category_id=c.category_id 
	INNER JOIN unit u ON sv.unit_id=u.unit_id 
	LEFT JOIN sub_category sc ON sv.sub_category_id=sc.sub_category_id;

CREATE OR REPLACE VIEW view_snapshot_stock_value AS 
SELECT 
	s.*,
	IFNULL(i.expense_type,'') as stock_type 
	FROM snapshot_stock_value s INNER JOIN item i ON s.item_id=i.item_id 
	WHERE i.is_asset=0 AND i.is_track=1;

CREATE OR REPLACE VIEW view_opening_balance_manual AS 
SELECT 
	'Manual' as entry_type,t.transaction_id,t.transaction_number,t.transaction_type_id,t.transaction_reason_id,
	t.transactor_id,t.transaction_date,t.add_date,t.transaction_ref,t.add_user_detail_id,t.grand_total,
	ti.account_code,ti.code_specific as child_account_code,ti.amount_exc_vat,ti.amount_inc_vat,ti.amount,t.currency_code,
	ifnull((select p.acc_period_id from acc_period p where t.transaction_date between p.start_date and p.end_date),0) as acc_period_id 
	FROM transaction t INNER JOIN transaction_item ti ON t.transaction_id=ti.transaction_id 
	WHERE t.transaction_type_id=76;

CREATE OR REPLACE VIEW view_pay_trans_with_no_trans_record AS 
select pt.pay_trans_id from pay_trans pt where pt.transaction_type_id=2 and pt.transaction_number NOT IN (select t.transaction_number from transaction t);

CREATE OR REPLACE VIEW view_transaction_tax_map AS 
SELECT  
		t.*,0 as mode_code,
		ifnull(t2.reference_number_tax,'') as reference_number_tax,ifnull(t2.transaction_number_tax,'') as transaction_number_tax,
		ifnull(t2.verification_code_tax,'') as verification_code_tax,ifnull(t2.qr_code_tax,'') as qr_code_tax,
		case when ifnull(t2.transaction_tax_map_id,0)>0 then 1 else 0 end as tax_synced,
		case when ifnull(t2.transaction_tax_map_id,0)>0 then 'Synced' else 'Not Synced' end as sync_flag,
		t2.is_updated_more_than_once,t2.more_than_once_update_reconsiled, 
		case 
			when t2.is_updated_more_than_once=1 and t2.more_than_once_update_reconsiled=1 then 'Reconsiled' 
			when t2.is_updated_more_than_once=1 and t2.more_than_once_update_reconsiled=0 then 'Not Reconsiled' 
			else '' 
		end as reconsile_flag 
		FROM transaction t 
		left join transaction_tax_map t2 on t.transaction_id=t2.transaction_id and t.transaction_type_id=t2.transaction_type_id 
		WHERE t.transaction_type_id IN(2,65,68) 
UNION 
SELECT  
	t.*,
	ifnull(t2.reference_number_tax,'') as reference_number_tax,ifnull(t2.transaction_number_tax,'') as transaction_number_tax,
    ifnull(t2.verification_code_tax,'') as verification_code_tax,ifnull(t2.qr_code_tax,'') as qr_code_tax,
	case when ifnull(t2.transaction_tax_map_id,0)>0 then 1 else 0 end as tax_synced,
    case when ifnull(t2.transaction_tax_map_id,0)>0 then 'Synced' else 'Not Synced' end as sync_flag,
	t2.is_updated_more_than_once,t2.more_than_once_update_reconsiled, 
	case 
		when t2.is_updated_more_than_once=1 and t2.more_than_once_update_reconsiled=1 then 'Reconsiled' 
		when t2.is_updated_more_than_once=1 and t2.more_than_once_update_reconsiled=0 then 'Not Reconsiled' 
		else '' 
	end as reconsile_flag 
	FROM transaction_cr_dr_note t 
	left join transaction_tax_map t2 on t.transaction_id=t2.transaction_id and t.transaction_type_id=t2.transaction_type_id 
	WHERE t.transaction_type_id IN(82,83);

CREATE OR REPLACE VIEW view_cash_receipt_detail AS 
  SELECT 
	CASE 
		WHEN p.pay_reason_id=21 THEN 1 
		WHEN p.pay_reason_id=22 THEN 2 
		WHEN p.pay_reason_id=90 THEN 3 
		WHEN p.pay_reason_id=23 THEN 4 
		WHEN p.pay_reason_id=24 THEN 5 
		ELSE 7 
	END as display_order,
	CASE 
		WHEN p.pay_reason_id=21 THEN 'Cash Sales' 
		WHEN p.pay_reason_id=22 THEN 'Credit Sales' 
		WHEN p.pay_reason_id=23 THEN 'Capital Injections' 
		WHEN p.pay_reason_id=24 THEN 'Loan Injections'
		WHEN p.pay_reason_id=90 THEN 'Deposits from Customers' 
		ELSE 'UnKnown' 
	END as cash_category,
  p.pay_date,YEAR(p.pay_date) as year_cal,MONTH(p.pay_date) as month_cal,(p.xrate*p.paid_amount) as paid_amount 
  FROM pay p  
  WHERE p.pay_type_id=14 AND p.pay_method_id!=6 AND p.pay_reason_id!=115  
UNION 
  SELECT 
	CASE 
		WHEN p.pay_reason_id=115 THEN 6 
		ELSE 7 
	END as display_order,
	CASE 
		WHEN p.pay_reason_id=115 THEN IFNULL(ac.account_name,'Other Cash Receipts') 
		ELSE 'UnKnown' 
	END as cash_category,
  p.pay_date,YEAR(p.pay_date) as year_cal,MONTH(p.pay_date) as month_cal,(p.xrate*p.paid_amount) as paid_amount 
  FROM pay p 
  INNER JOIN pay_trans pt ON p.pay_id=pt.pay_id 
  INNER JOIN acc_coa ac ON pt.account_code=ac.account_code 
  WHERE p.pay_type_id=14 AND p.pay_method_id!=6 AND p.pay_reason_id=115;

CREATE OR REPLACE VIEW view_cash_paid_detail AS 
	SELECT 
		CASE 
			WHEN p.pay_reason_id=91 THEN 2 
			WHEN p.pay_reason_id=34 THEN 3 
			WHEN p.pay_reason_id=33 THEN 4 
			ELSE 9 
		END as display_order,
		CASE 
			WHEN p.pay_reason_id=34 THEN 'Owner Cash Drawings' 
			WHEN p.pay_reason_id=33 THEN 'Loan Repayments'
			WHEN p.pay_reason_id=91 THEN 'Deposits to Suppliers' 
			ELSE 'NotKnown'  
		END as cash_category,
		p.pay_date,YEAR(p.pay_date) as year_cal,MONTH(p.pay_date) as month_cal,(p.xrate*p.paid_amount) as paid_amount 
	FROM pay p  
	WHERE p.pay_type_id=15 AND p.pay_method_id!=7 AND p.pay_reason_id IN(34,33,91) 
UNION 
	SELECT 
		CASE 
			WHEN pt.transaction_reason_id=1 THEN 1 
			WHEN pt.transaction_reason_id=29 THEN 6 
			WHEN pt.transaction_reason_id=27 OR pt.transaction_reason_id=43 THEN 7  
			ELSE 9 
		END as display_order,
		CASE 
			WHEN pt.transaction_reason_id=1 THEN 'Purcahse of Goods and Services' 
			WHEN pt.transaction_reason_id=29 THEN 'Purcahse of Assets' 
			WHEN pt.transaction_reason_id=27 OR pt.transaction_reason_id=43 THEN 'Operational Expenses' 
			ELSE 'UnKnown'  
		END as cash_category,
		pa.pay_date,YEAR(pa.pay_date) as year_cal,MONTH(pa.pay_date) as month_cal,(pa.xrate*pt.trans_paid_amount) as paid_amount
	FROM pay_trans pt INNER JOIN pay pa ON pt.pay_id=pa.pay_id 
	WHERE pa.pay_type_id=15 AND pa.pay_method_id!=7 AND pa.pay_reason_id IN(25,26) 
UNION 
	SELECT 
		CASE 
			WHEN p.pay_reason_id=105 THEN 8 
			ELSE 9 
		END as display_order,
		CASE 
			WHEN p.pay_reason_id=105 THEN IFNULL(ac.account_name,'Other Cash Payments') 
			ELSE 'NotKnown'  
		END as cash_category,
		p.pay_date,YEAR(p.pay_date) as year_cal,MONTH(p.pay_date) as month_cal,(p.xrate*p.paid_amount) as paid_amount 
	FROM pay p 
	INNER JOIN pay_trans pt ON p.pay_id=pt.pay_id 
	INNER JOIN acc_coa ac ON pt.account_code=ac.account_code 
	WHERE p.pay_type_id=15 AND p.pay_method_id!=7 AND p.pay_reason_id IN(105) ;
    
CREATE OR REPLACE VIEW view_transaction_cr_dr AS 
SELECT t.*, ifnull((select group_concat(concat(t2.transaction_id,',',t2.transaction_comment) separator ':') from transaction_cr_dr_note t2 where t2.transaction_ref=t.transaction_number),'') as cr_dr_flag 
FROM transaction t 
WHERE t.transaction_type_id IN(2,65,68);

CREATE OR REPLACE VIEW view_snapshot_stock_value_max_month AS 
select year(cdc_date) as y,month(cdc_date) as m,max(snapshot_no) as snapshot_no from cdc_general 
	where cdc_function='STOCK' and is_passed=1  
	group by year(cdc_date),month(cdc_date) 
	order by year(cdc_date),month(cdc_date);
    
CREATE OR REPLACE VIEW view_snapshot_stock_value_max_day AS 
select year(cdc_date) as y,month(cdc_date) as m,day(cdc_date) as d,max(snapshot_no) as snapshot_no from cdc_general 
	where cdc_function='STOCK' and is_passed=1  
	group by year(cdc_date),month(cdc_date),day(cdc_date) 
	order by year(cdc_date),month(cdc_date),day(cdc_date);
    
CREATE OR REPLACE VIEW view_sales_invoice_age AS 
select 
	t1.transaction_id,t1.transaction_number,ifnull(t2.transactor_id,0) as transactor_id,ifnull(t2.transactor_names,'') as transactor_names,t1.currency_code,t1.transaction_date,
    DATEDIFF(now(),t1.transaction_date) as age_days,t1.grand_total,t1.total_paid,(t1.grand_total-t1.total_paid) as balance,
    CASE 
		WHEN CAST(SUBSTRING(p.parameter_value,1,4) AS UNSIGNED)>0 AND DATEDIFF(now(),transaction_date)<=CAST(SUBSTRING(p.parameter_value,1,4) AS UNSIGNED) THEN 'A'
		WHEN CAST(SUBSTRING(p.parameter_value,6,4) AS UNSIGNED)>0 AND DATEDIFF(now(),transaction_date)<=CAST(SUBSTRING(p.parameter_value,6,4) AS UNSIGNED) THEN 'B' 
		WHEN CAST(SUBSTRING(p.parameter_value,6,4) AS UNSIGNED)>0 AND DATEDIFF(now(),transaction_date)>CAST(SUBSTRING(p.parameter_value,6,4) AS UNSIGNED) THEN 'C' 
		ELSE 'D' 
	END as days_category 
	from transaction t1 inner join transactor t2 on t1.bill_transactor_id=t2.transactor_id inner join parameter_list p on p.parameter_list_id=79 
	where (t1.transaction_type_id IN (2,65,68) or t1.transaction_reason_id=117) and t1.grand_total>t1.total_paid;

CREATE OR REPLACE VIEW view_supplier_invoice_age AS 
select 
	t1.transaction_id,t1.transaction_number,ifnull(t2.transactor_id,0) as transactor_id,ifnull(t2.transactor_names,'') as transactor_names,t1.currency_code,t1.transaction_date,
    DATEDIFF(now(),t1.transaction_date) as age_days,t1.grand_total,t1.total_paid,(t1.grand_total-t1.total_paid) as balance,
    CASE 
		WHEN CAST(SUBSTRING(p.parameter_value,1,4) AS UNSIGNED)>0 AND DATEDIFF(now(),transaction_date)<=CAST(SUBSTRING(p.parameter_value,1,4) AS UNSIGNED) THEN 'A'
		WHEN CAST(SUBSTRING(p.parameter_value,6,4) AS UNSIGNED)>0 AND DATEDIFF(now(),transaction_date)<=CAST(SUBSTRING(p.parameter_value,6,4) AS UNSIGNED) THEN 'B' 
		WHEN CAST(SUBSTRING(p.parameter_value,6,4) AS UNSIGNED)>0 AND DATEDIFF(now(),transaction_date)>CAST(SUBSTRING(p.parameter_value,6,4) AS UNSIGNED) THEN 'C' 
		ELSE 'D' 
	END as days_category 
	from transaction t1 inner join transactor t2 on t1.bill_transactor_id=t2.transactor_id inner join parameter_list p on p.parameter_list_id=81 
	where (t1.transaction_type_id=1 or t1.transaction_reason_id=118) and t1.grand_total>t1.total_paid;
    
CREATE OR REPLACE VIEW view_api_smbi AS 
select m.*,tt.transaction_type_name from transaction_smbi_map m inner join transaction_type tt on m.transaction_type_id=tt.transaction_type_id 
UNION 
SELECT loyalty_transaction_id as transaction_smbi_map_id, loyalty_transaction_id as transaction_id,1010 as transaction_type_id,1010 as transaction_reason_id, 
card_number as transaction_number,add_date,status_sync,status_date,status_desc, 'LOYALTY' as transaction_type_name 
FROM loyalty_transaction;

CREATE OR REPLACE VIEW view_sales_invoice_detail AS 
SELECT t.*,ifnull(ted.tax_amount,0) as TotalExciseDutyTaxAmount 
FROM transaction t LEFT JOIN 
(
	SELECT tt.transaction_id,SUM(tt.taxable_amount) as taxable_amount,SUM(tt.tax_amount) as tax_amount FROM transaction_tax tt WHERE  tt.tax_category='Excise Duty' GROUP BY tt.transaction_id
) ted ON t.transaction_id=ted.transaction_id 
WHERE t.transaction_type_id IN(2,65,68);