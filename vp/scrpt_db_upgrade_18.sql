create table item_unit_other (
                        item_unit_other bigint(20) primary key auto_increment,
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

CREATE TABLE IF NOT EXISTS transaction_item_unit (PRIMARY KEY (transaction_item_id)) AS 
select ti.transaction_item_id,i.unit_id from transaction_item ti inner join item i on ti.item_id=i.item_id;

CREATE TABLE IF NOT EXISTS transaction_item_cr_dr_note_unit (PRIMARY KEY (transaction_item_id)) AS 
select ti.transaction_item_id,i.unit_id from transaction_item_cr_dr_note ti inner join item i on ti.item_id=i.item_id;

CREATE TABLE IF NOT EXISTS transaction_item_hist_unit (PRIMARY KEY (transaction_item_hist_id)) AS 
select ti.transaction_item_hist_id,i.unit_id from transaction_item_hist ti inner join item i on ti.item_id=i.item_id;

CREATE TABLE IF NOT EXISTS trans_production_item_unit (PRIMARY KEY (trans_production_item_id)) AS 
select ti.trans_production_item_id,i.unit_id from trans_production_item ti inner join item i on ti.input_item_id=i.item_id;

CREATE TABLE IF NOT EXISTS trans_production_unit (PRIMARY KEY (transaction_id)) AS 
select ti.transaction_id,i.unit_id from trans_production ti inner join item i on ti.output_item_id=i.item_id;

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_18',33,Now(),'6.0','');
