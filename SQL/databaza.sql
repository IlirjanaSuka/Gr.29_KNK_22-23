create database knk;
use knk;

create table login(
username nvarchar(30)not null,
email nvarchar(30) not null,
id nvarchar(30) primary key not null,
password nvarchar(50)not null
);
