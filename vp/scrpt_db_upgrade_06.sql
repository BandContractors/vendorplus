-- 7th Feb 2020
alter table category add column store_quick_order int(1) default 0;
-- 10th Feb 2020
alter table item add column override_gen_name int(1) default 0; -- 0 DoNotOverride, 1 OverrideShowYes, 2 OverrideShowNo