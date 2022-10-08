create table item_unit_other (
                        item_unit_other_id bigint(20) primary key auto_increment,
                        item_id bigint(20),
                        base_qty double,
                        other_unit_id int,
                        other_qty double,
                        other_unit_retailsale_price double,
                        other_unit_wholesale_price double,
                        other_default_purchase int,
                        other_default_sale int,
                        is_active int,
                        last_edit_by varchar(255),
                        last_edit_date datetime
                        )ENGINE=InnoDB;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_18',15,Now(),'6.0','');

-- drop table transaction_item_unit;
-- drop table transaction_item_cr_dr_note_unit;
-- drop table transaction_item_hist_unit;
-- drop table trans_production_item_unit;
-- drop table trans_production_unit; 
CREATE TABLE IF NOT EXISTS transaction_item_unit (PRIMARY KEY (transaction_item_id)) AS 
select ti.transaction_item_id,i.unit_id,ti.item_qty as base_unit_qty from transaction_item ti inner join item i on ti.item_id=i.item_id;
CREATE TABLE IF NOT EXISTS transaction_item_cr_dr_note_unit (PRIMARY KEY (transaction_item_id)) AS 
select ti.transaction_item_id,i.unit_id,ti.item_qty as base_unit_qty from transaction_item_cr_dr_note ti inner join item i on ti.item_id=i.item_id;
CREATE TABLE IF NOT EXISTS transaction_item_hist_unit (PRIMARY KEY (transaction_item_hist_id)) AS 
select ti.transaction_item_hist_id,i.unit_id,ti.item_qty as base_unit_qty from transaction_item_hist ti inner join item i on ti.item_id=i.item_id;
CREATE TABLE IF NOT EXISTS trans_production_item_unit (PRIMARY KEY (trans_production_item_id)) AS 
select ti.trans_production_item_id,i.unit_id,ti.input_qty as base_unit_qty from trans_production_item ti inner join item i on ti.input_item_id=i.item_id;
CREATE TABLE IF NOT EXISTS trans_production_unit (PRIMARY KEY (transaction_id)) AS 
select ti.transaction_id,i.unit_id,ti.output_qty as base_unit_qty from trans_production ti inner join item i on ti.output_item_id=i.item_id;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_18',33,Now(),'6.0','');

ALTER TABLE efris_invoice_detail 
ADD COLUMN verification_code VARCHAR(50) NOT NULL DEFAULT '' AFTER process_desc,
ADD COLUMN qr_code VARCHAR(1000) NOT NULL DEFAULT '' AFTER verification_code;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_18',39,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (97, 'GENERAL', 'FORCE_UNIT_SELECTION', '0','0:No and 1:Yes. If Yes, Unit will be selected manually at each transaction. If No, default unit is be selected automatically. Applies to Items with multiple units');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',44,Now(),'6.0','');

-- Replace db_name with database name such as daytoday_sm_branch
CALL sp_alter_stock_ledger_tables_trans_item_id("db_name");
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',48,Now(),'6.0','');

ALTER TABLE item_production_map ADD COLUMN output_unit_id INT NULL DEFAULT '0',ADD COLUMN input_unit_id INT NULL DEFAULT '0';
UPDATE item_production_map m set 
m.output_unit_id=(select i1.unit_id from item i1 where i1.item_id=m.output_item_id),
m.input_unit_id=(select i2.unit_id from item i2 where i2.item_id=m.input_item_id) 
WHERE m.item_production_map_id>0;

DROP TABLE trans_production_item_unit;
DROP TABLE trans_production_unit;
ALTER TABLE trans_production_item ADD COLUMN unit_id INT NULL DEFAULT '0',ADD COLUMN base_unit_qty DOUBLE NULL DEFAULT '0';
ALTER TABLE trans_production ADD COLUMN unit_id INT NULL DEFAULT '0',ADD COLUMN base_unit_qty DOUBLE NULL DEFAULT '0';

UPDATE trans_production_item ti SET ti.base_unit_qty=ti.input_qty,ti.unit_id=(select i.unit_id from item i where i.item_id=ti.input_item_id) 
WHERE ti.trans_production_item_id>0;
UPDATE trans_production t SET t.base_unit_qty=t.output_qty,t.unit_id=(select i.unit_id from item i where i.item_id=t.output_item_id) 
WHERE t.transaction_id>0;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',66,Now(),'6.0','');

INSERT INTO parameter_list (parameter_list_id, context, parameter_name, parameter_value, description) 
VALUES (98, 'COMPANY_SETTING', 'COUNTRY_NAME', 'UGANDA','Country of Registration for the company or Branch; this should be the full name e.g UGANDA');
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) VALUES('scrpt_db_upgrade_18',70,Now(),'6.0','');