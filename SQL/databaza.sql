create database knk;
use knk;

create table admin(
id int primary key,
username nvarchar(100),
password nvarchar(100)
);

insert into admin(id, username, password) value(1, 'admin1', '123');
