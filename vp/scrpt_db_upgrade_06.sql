-- 7th Feb 2020
alter table category add column store_quick_order int(1) default 0;
-- 10th Feb 2020
alter table item add column override_gen_name int(1) default 0; -- 0 DoNotOverride, 1 OverrideShowYes, 2 OverrideShowNo
INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) VALUES (53, 'COMPANY_SETTING', 'OUTPUT_SHOW_CO_NAME', '1','');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (115, 'OTHER REVENUE', 14);
INSERT INTO transaction_type (transaction_type_id, transaction_type_name) VALUES (75, 'CASH ADJUSTMENT');
INSERT INTO transaction_reason (transaction_reason_id, transaction_reason_name, transaction_type_id) VALUES (116, 'CASH ADJUSTMENT', 75);


-- DO NOT RUN  THE ONES BELO---

-- 17th Feb 2020
alter table discount_package add column store_scope varchar(500) default '';
alter table discount_package add column transactor_scope varchar(500) default ''; 

alter table discount_package_item add column category_scope varchar(500) default '';
alter table discount_package_item add column sub_category_scope varchar(500) default '';
alter table discount_package_item add column item_scope varchar(500) default '';

select concat("'",replace("1,2,3",",","','"),"'");