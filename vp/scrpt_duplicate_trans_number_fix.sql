CREATE TABLE duplicate_trans_nos_trans AS 
	select transaction_number,count(transaction_number) as cnt from transaction group by 
	transaction_number having count(transaction_number)>1;

CREATE TABLE duplicate_trans_nos_prod AS 
	select transaction_number,count(transaction_number) as cnt from trans_production group by 
	transaction_number having count(transaction_number)>1;

update transaction SET transaction_number=concat(transaction_number,'_',transaction_id) 
where transaction_id>0 and transaction_number IN(select transaction_number from duplicate_trans_nos_trans);

update transaction_hist SET transaction_number=concat(transaction_number,'_',transaction_id) 
where transaction_hist_id>0 and transaction_number IN(select transaction_number from duplicate_trans_nos_trans);

update trans_production SET transaction_number=concat(transaction_number,'_',transaction_id) 
where transaction_id>0 and transaction_number IN(select transaction_number from duplicate_trans_nos_prod);


update pay_trans SET transaction_number=concat(transaction_number,'_',transaction_id) 
where pay_trans_id>0 and transaction_type_id!=70 and transaction_number IN(select transaction_number from duplicate_trans_nos_trans);

update pay_trans SET transaction_number=concat(transaction_number,'_',transaction_id) 
where pay_trans_id>0 and transaction_type_id=70 and transaction_number IN(select transaction_number from duplicate_trans_nos_prod);

update pay_trans_hist SET transaction_number=concat(transaction_number,'_',transaction_id) 
where pay_trans_hist_id>0 and transaction_type_id!=70 and transaction_number IN(select transaction_number from duplicate_trans_nos_trans);

update pay_trans_hist SET transaction_number=concat(transaction_number,'_',transaction_id) 
where pay_trans_hist_id>0 and transaction_type_id=70 and transaction_number IN(select transaction_number from duplicate_trans_nos_prod);

-- checking
select transaction_number,count(transaction_number) as cnt from transaction group by transaction_number having count(transaction_number)>1;
select transaction_number,count(transaction_number) as cnt from trans_production group by transaction_number having count(transaction_number)>1;

-- if all is ok
truncate table duplicate_trans_nos_trans;
truncate table duplicate_trans_nos_prod;