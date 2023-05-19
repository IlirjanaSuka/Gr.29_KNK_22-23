create database knk;
use knk;

create table admin(
username nvarchar(100),
password nvarchar(100)
);

insert into admin( username, password) value('admin1', '123'),('admin2', '1234');
select* from admin;
