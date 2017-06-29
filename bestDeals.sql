# show databases;

use bestdealsdatabase;

drop table if exists Resistration;
create table Registration
(
username varchar(40),
password varchar(40), 
repassword varchar(40), 
usertype varchar (40)
);

drop table if exists CustomerOrders;
create table CustomerOrders
(
OrderId integer,
userName varchar(40),
orderName varchar(40),
orderPrice double,
userAddress varchar(40),
creditCardNo varchar(40),
primary key(orderId, userName,orderName)
);