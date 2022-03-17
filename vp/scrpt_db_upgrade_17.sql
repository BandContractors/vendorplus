UPDATE item SET add_date=Now() WHERE item_id>0 AND YEAR(add_date)=0;
UPDATE item SET edit_date=Now() WHERE item_id>0 AND YEAR(edit_date)=0;
CREATE TABLE item_back AS SELECT * FROM item;
ALTER TABLE item CHANGE COLUMN reorder_level reorder_level DOUBLE NOT NULL ;
-- A) Check if tables has been altered successfully
-- SELECT COUNT(*) FROM item;
-- B) Delete backup tables if tables has been altered successfully
-- DROP TABLE IF EXISTS item_back;
-- C) Restore tables if alter statement deleted the tables AND MANUALLY SET THE DOUBLE as the datatype for reorder_level IN WORKBENCH.
-- CREATE TABLE item AS SELECT * FROM item_back;

create table item_store_reorder 
(item_store_reorder_id BIGINT(20) not null auto_increment,item_id bigint(20),store_id INT(11),reorder_level DOUBLE, primary key (item_store_reorder_id));

INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',15,Now(),'6.0','');

ALTER TABLE transaction_approval
ADD COLUMN status_comment varchar(150) NULL after status_desc;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',20,Now(),'6.0','');

create table shift (shift_id int(11) not null auto_increment, shift_name varchar(50) not null, description varchar(150) not null, start_time time not null, end_time time not null, primary key (shift_id));
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',24,Now(),'6.0','');

create table transaction_shift (transaction_shift_id bigint(20) not null auto_increment, transaction_id bigint(20) not null, shift_id int(11) not null, primary key (transaction_shift_id));
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',28,Now(),'6.0','');