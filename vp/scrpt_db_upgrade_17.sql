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

create table mode_activity (mode_activity_id int primary key auto_increment,mode_name varchar(25) not null unique  )ENGINE=InnoDB;
create table staff (staff_id int primary key auto_increment,first_name varchar(50) not null,second_name varchar(50) not null,third_name varchar(50))ENGINE=InnoDB;
create table category_activity (category_activity_id int primary key auto_increment,category_name varchar(50) not null unique )ENGINE=InnoDB;
create table subcategory_activity (subcategory_activity_id int primary key auto_increment,category_activity_id int,subcategory_name varchar(50) not null unique )ENGINE=InnoDB;
create table timesheet (
                        timesheet_id bigint(20) primary key auto_increment,
                        activity_status varchar(20),
                        transactor_id bigint(20),
                        mode_activity_id int,
                        staff_id int,
                        category_activity_id int,
                        subcategory_activity_id int,
                        time_taken double,
                        activity_name varchar(255) not null,
                        activity_date date not null, 
                        submission_date datetime not null,
                        unit_of_time varchar(15) not null
                        )ENGINE=InnoDB;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',49,Now(),'6.0','');

ALTER TABLE store ADD COLUMN shift_mode INT(1) NOT NULL DEFAULT 0 AFTER store_code;
ALTER TABLE transaction_shift ADD COLUMN transaction_type_id INT(11) NULL AFTER shift_id;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',54,Now(),'6.0','');

ALTER TABLE transaction_cr_dr_note ADD COLUMN mode_code INT(1) NOT NULL DEFAULT 0 AFTER spent_points_amount;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',58,Now(),'6.0','');

ALTER TABLE subscription ADD COLUMN commission_amount DOUBLE NOT NULL DEFAULT 0;
ALTER TABLE subscription_log ADD COLUMN commission_amount DOUBLE NOT NULL DEFAULT 0;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',63,Now(),'6.0','');

ALTER TABLE subscription ADD converted_by varchar(50) null, ADD referred_by varchar(50) null;
ALTER TABLE subscription_log ADD converted_by varchar(50) null, ADD referred_by varchar(50) null;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',68,Now(),'6.0','');

create table project (project_id int primary key auto_increment,project_name varchar(25) not null unique  );
ALTER TABLE timesheet ADD COLUMN project_id INT(11) NULL;
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',73,Now(),'6.0','');

create table pay_shift (pay_shift_id bigint(20) not null auto_increment, pay_id bigint(20) not null, shift_id int(11) not null, pay_type_id int(11) null, primary key (pay_shift_id));
INSERT INTO upgrade_control(script_name,line_no,upgrade_date,version_no,upgrade_detail) 
VALUES('scrpt_db_upgrade_17',77,Now(),'6.0','');