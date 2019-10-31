-- Update wholesale price basing on formula
update item i set i.unit_wholesale_price=
(
(0.4*i.unit_retailsale_price) + 0.6*
	(
	IFNULL((select s.unit_cost from stock s where s.item_id=i.item_id),i.unit_retailsale_price)
	)
) 
where i.item_id>0;