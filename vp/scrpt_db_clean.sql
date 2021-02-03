select count(*) from stock_ledger; -- 247,502
select count(*) from snapshot_stock_value; -- 2,405,991
select count(*) from transaction_item; -- 248,057
select * from cdc_general;

-- delete all snapshots and leave the first one per month
delete from snapshot_stock_value WHERE snapshot_stock_value_id>0 AND snapshot_no NOT IN 
SELECT k.MN FROM 
(
select YEAR(cdc_date) as Y,MONTH(cdc_date) as M,MIN(snapshot_no) as MN from cdc_general 
where cdc_function='STOCK' and YEAR(cdc_date)=2020 
group by YEAR(cdc_date),MONTH(cdc_date)
) as k;