create table efris_invoice_detail (
	efris_invoice_detail_id bigint primary key auto_increment,
	id varchar(32) not null,
	invoiceNo varchar(30) not null,
    oriInvoiceId varchar(32),
    oriInvoiceNo varchar(30),
    -- issuedDate datetime not null,
    issuedDate varchar(100),
    -- businessName varchar(256) not null,
    buyerTin varchar(20),
    buyerLegalName varchar(256),
    buyerNinBrn varchar(100),
    currency varchar(10) not null,
    grossAmount varchar(256) not null,
    taxAmount varchar(256) not null,
    dataSource varchar(3) not null,
    isInvalid varchar(1) not null,
    isRefund varchar(1),
    invoiceType varchar(1) not null,
    invoiceKind varchar(1) not null,
    invoiceIndustryCode varchar(3),
    branchName varchar(500),
    deviceNo varchar(50) not null,
    -- uploadingTime datetime not null,
    uploadingTime varchar(100) not null,
    referenceNo varchar(50),
    operator varchar(100),
    userName varchar(500),
    process_flag int,
    add_date datetime not null,
    process_date datetime
)ENGINE=InnoDB;

create table efris_good_detail (
	efris_good_detail_id bigint primary key auto_increment,
    invoiceNo varchar(30) not null,
    referenceNo varchar(50),
    item varchar(200) not null,
    itemCode varchar(50) not null,
    qty varchar(100) not null,
    unitOfMeasure varchar(3) not null,
    unitPrice varchar(100) not null,
    total varchar(100) not null,
    taxRate varchar(100) not null,
    tax varchar(100) not null,
    discountTotal varchar(100) not null,
    discountTaxRate varchar(100) not null,
    orderNumber varchar(100) not null,
    discountFlag varchar(1) not null,
    deemedFlag varchar(1) not null,
    exciseFlag varchar(1) not null,
    categoryId varchar(18) not null,
    categoryName varchar(1024) not null,
    goodsCategoryId varchar(18) not null,
    goodsCategoryName varchar(200) not null,
    exciseRate varchar(21) not null,
    exciseRule varchar(1) not null,
    exciseTax varchar(100) not null,
    pack varchar(100) not null,
    stick varchar(100) not null,
    exciseUnit varchar(3) not null,
    exciseCurrency varchar(10) not null,
    exciseRateName varchar(500) not null,
    process_flag int,
    add_date datetime not null,
    process_date datetime
)ENGINE=InnoDB;

ALTER TABLE efris_invoice_detail ADD COLUMN process_desc varchar(500);
ALTER TABLE efris_good_detail ADD COLUMN process_desc varchar(500);
ALTER TABLE efris_invoice_detail MODIFY id varchar(32) not null unique;

-- commodity category
create table efris_goods_commodity (
	efris_goods_commodity_id bigint primary key auto_increment,
    commodityCategoryCode varchar(18) not null default '',
    parentCode varchar(18) not null default '',
    commodityCategoryName varchar(200) not null default '',
    commodityCategoryLevel varchar(1) not null default '',
    rate varchar(4) not null default '',
    isLeafNode varchar(3) not null default '',
    serviceMark varchar(3) not null default '',
    isZeroRate varchar(3) not null default '',
    zeroRateStartDate varchar(100) not null default '',
    zeroRateEndDate varchar(100) not null default '',
    isExempt varchar(3) not null default '',
    exemptRateStartDate varchar(100) not null default '',
    exemptRateEndDate varchar(100) not null default '',
    enableStatusCode varchar(1) not null default '',
    exclusion varchar(1) not null default '',
    add_date datetime not null
)ENGINE=InnoDB;

-- Excise duty
create table efris_excise_duty_list (
	efris_excise_duty_list_id bigint primary key auto_increment,
	id varchar(20) not null,
	exciseDutyCode varchar(20) not null,
    goodService varchar(500),
    parentCode varchar(20),
    rateText varchar(50) not null,
    isLeafNode varchar(1),
    effectiveDate varchar(100),
    unit varchar(3),
    currency varchar(100) not null,
    rate_perc varchar(500),
    rate_value varchar(500),
    add_date datetime not null
)ENGINE=InnoDB;

create table item_excise_duty_map (
	item_excise_duty_map_id bigint primary key auto_increment,
	item_id bigint(20) not null,
	efris_excise_duty_list_id bigint not null
)ENGINE=InnoDB;

create table download_status (
	download_status_id int primary key auto_increment,
    download_name varchar(50) not null unique,
	download_status int not null,
	download_status_msg varchar(50) not null,
	total_amount int not null,
	total_downloaded int not null,
    add_date datetime not null
)ENGINE=InnoDB;
INSERT INTO download_status (download_name, download_status, download_status_msg, total_amount, total_downloaded, add_date) 
VALUES ('GOODS COMMODITY', '3', 'NEW', '0','0',Now());