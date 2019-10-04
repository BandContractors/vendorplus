SELECT 
	CASE 
		WHEN i.is_sale=1 THEN 1 
		WHEN i.is_sale=0 AND i.is_buy=1 THEN 2 
		ELSE 0 
	END as stock_type_order,
	CASE 
		WHEN i.is_sale=1 THEN 'Goods for Sale' 
		WHEN i.is_sale=0 AND i.is_buy=1 THEN i.expense_type 
		ELSE '' 
	END as stock_type
	,i.*,
	c.category_name,u.unit_symbol,sc.sub_category_name,
	(select sum(qty_added) from stock_ledger sl where sl.item_id=i.item_id and sl.add_date between '2019-08-01' and '2019-10-02') as qty_added,
	(select sum(qty_subtracted) from stock_ledger sl where sl.item_id=i.item_id and sl.add_date between '2019-08-01' and '2019-10-02') as qty_subtracted,
	(select sum(sv.currentqty) from 
		(
		select sv2.item_id,max(sv2.snapshot_no) as max_snapshot_no from snapshot_stock_value sv2 where sv2.item_id=i.item_id and sv2.snapshot_date<='2019-08-01' group by sv2.item_id
		) as sv where sv.snapshot_no=max(sv.snapshot_no)
	) as qty_open, 
	(select sum(currentqty) from snapshot_stock_value sv where sv.item_id=i.item_id and sv.snapshot_date>='2019-08-01' and sv.snapshot_no=min(sv.snapshot_no)) as qty_close,
	(select sum(currentqty) from stock s where s.item_id=i.item_id) as qty_current 
FROM item i  
INNER JOIN category c ON i.category_id=c.category_id 
INNER JOIN unit u ON i.unit_id=u.unit_id 
LEFT JOIN sub_category sc ON i.sub_category_id=sc.sub_category_id 
WHERE i.is_asset=0 AND i.is_track=1

select * from stock_ledger where add_date between '2019-10-01' and '2019-10-02';
select * from snapshot_stock_value;