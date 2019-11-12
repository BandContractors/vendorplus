UPDATE parameter_list SET description='2(Sales Invoice),11(Sale Order)' WHERE parameter_list_id=45;
alter table transaction add column is_invoiced int(1) default 0;
