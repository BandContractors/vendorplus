-- 1. check secure l;ocation to upload files from
SHOW VARIABLES LIKE "secure_file_priv";
-- 2. Upload the csv files to that location: C:/wamp64/tmp
-- 3. run the following commands
LOAD DATA INFILE 'C:/wamp64/tmp/unit_tax_list.csv' INTO TABLE `unit_tax_list` FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;
LOAD DATA INFILE 'C:/wamp64/tmp/item_unspsc.csv' INTO TABLE `item_unspsc` FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;
LOAD DATA INFILE 'C:/wamp64/tmp/iso_country_code.csv' INTO TABLE `iso_country_code` FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

