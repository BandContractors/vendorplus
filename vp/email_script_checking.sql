select count(*) from contact_list;  -- 15857

select valid_email,count(valid_email) from 
(SELECT email1,email1 REGEXP '^[A-Za-z0-9._%\-+!#$&/=?^|~]+@[A-Za-z0-9.-]+[.][A-Za-z]+$' AS valid_email FROM contact_list) T
group by valid_email;

create table contact_list_check as 
SELECT 
email1,email1 REGEXP '^[A-Za-z0-9._%\-+!#$&/=?^|~]+@[A-Za-z0-9.-]+[.][A-Za-z]+$' AS valid1,
email2,email2 REGEXP '^[A-Za-z0-9._%\-+!#$&/=?^|~]+@[A-Za-z0-9.-]+[.][A-Za-z]+$' AS valid2  
FROM contact_list;

select * from contact_list where (email1 REGEXP '^[A-Za-z0-9._%\-+!#$&/=?^|~]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')=0;

select * from contact_list where email1 like '%.';

select count(*) from contact_list_check where valid1=0 or valid;

update contact_list set email1='' where contact_list_id>0 and email1 in(select cc.email1 from contact_list_check cc where cc.valid1=0);

delete from contact_list where contact_list_id>0 and (email1 REGEXP '^[A-Za-z0-9._%\-+!#$&/=?^|~]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')=0;

update contact_list set email2='' where contact_list_id>0 and length(email2)>0 and email2 in(select cc.email2 from contact_list_check cc where cc.valid2=0);

select * from contact_list where contact_list_id>0 and length(email2)>0 and email2 in(select cc.email2 from contact_list_check cc where cc.valid2=0);



SELECT * from contact_list where (email1 REGEXP '^[A-Za-z0-9._%\-+!#$&/=?^|~]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')=0 and length(email1)>0;

