create table transaction_item_excise(
                        transaction_item_excise_id bigint(20) primary key auto_increment,
                        transaction_item_id bigint(20),
                        rate_name varchar(250),
                        rate_perc double,
                        rate_value double,
                        excise_tax double,
                        currency_code_tax varchar(50),
                        unit_code_tax varchar(50)
                        )ENGINE=InnoDB;

create table transaction_tax(
                        transaction_tax_id bigint(20) primary key auto_increment,
                        transaction_id bigint(20),
                        tax_category varchar(50),
                        tax_rate_name varchar(50),
                        tax_rate double,
                        taxable_amount double,
                        tax_amount double
                        )ENGINE=InnoDB;

CREATE TABLE transaction_package (
  transaction_package_id bigint(20) NOT NULL AUTO_INCREMENT,
  transaction_number varchar(50) NOT NULL,
  transaction_date date NOT NULL,
  store_id int(11) NOT NULL,
  store2_id int(11) DEFAULT NULL,
  transactor_id bigint(20) DEFAULT NULL,
  transaction_type_id int(11) NOT NULL,
  transaction_reason_id int(11) NOT NULL,
  sub_total double NOT NULL,
  total_trade_discount double NOT NULL,
  total_tax double NOT NULL,
  cash_discount double DEFAULT NULL,
  grand_total double NOT NULL,
  transaction_ref varchar(100) DEFAULT NULL,
  transaction_comment varchar(255) DEFAULT NULL,
  add_user_detail_id int(11) NOT NULL,
  add_date datetime NOT NULL,
  edit_user_detail_id int(11) DEFAULT NULL,
  edit_date datetime DEFAULT NULL,
  transaction_user_detail_id int(11) DEFAULT NULL,
  currency_code varchar(10) DEFAULT NULL,
  location_id bigint(20) DEFAULT NULL,
  status_code varchar(20) DEFAULT NULL,
  status_date datetime DEFAULT NULL,
  PRIMARY KEY (transaction_id),
  KEY idp_trans_number (transaction_number)
) ENGINE=InnoDB;

create table transaction_package_tax(
                        transaction_package_tax_id bigint(20) primary key auto_increment,
                        transaction_package_id bigint(20),
                        tax_category varchar(50),
                        tax_rate_name varchar(50),
                        tax_rate double,
                        taxable_amount double,
                        tax_amount double
                        )ENGINE=InnoDB;

CREATE TABLE transaction_package_item (
  transaction_package_item_id bigint(20) NOT NULL AUTO_INCREMENT,
  transaction_package_id bigint(20) NOT NULL,
  item_id bigint(20) DEFAULT NULL,
  unit_id int,
  batchno varchar(100) DEFAULT NULL,
  code_specific varchar(250) DEFAULT NULL,
  desc_specific varchar(250) DEFAULT NULL,
  desc_more varchar(250) DEFAULT NULL,
  item_qty double NOT NULL,
  base_unit_qty double NOT NULL,
  unit_price double DEFAULT NULL,
  unit_trade_discount double DEFAULT NULL,
  unit_vat double DEFAULT NULL,
  excise_rate_name varchar(250),
  excise_rate_perc double,
  excise_rate_value double,
  excise_tax double,
  excise_currency_code_tax varchar(50),
  excise_unit_code_tax varchar(50),
  amount double DEFAULT NULL,
  vat_rated varchar(50) DEFAULT NULL,
  vat_perc double DEFAULT NULL,
  narration varchar(100) DEFAULT NULL,
  PRIMARY KEY (transaction_package_item)
) ENGINE=InnoDB1;
