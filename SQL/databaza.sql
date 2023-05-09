create database KNK;
use KNK;


CREATE TABLE Student
(
    studentID VARCHAR(255) not null,
    studentName VARCHAR(255) not null,
    studentSurname VARCHAR(255) not null,
    gender CHAR(1) not null,
    phoneNumber VARCHAR(255) not null,
    email VARCHAR(255) not null unique,
    password VARCHAR(255) not null,
    PRIMARY KEY(studentID)
);

CREATE TABLE Administrator
(
    administratorID VARCHAR(20),
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY(administratorID)
    );


    create table Exames(
    exameID VARCHAR(20) not null,
    class VARCHAR(255),
    price double,
    times int,
    PRIMARY KEY(exameID)
    );

    create table semester(
     semNum VARCHAR(20) not null,
    classes VARCHAR(255),
    price double,
    year int
    );


INSERT INTO Administrator VALUES
    ('1','admin','admin','admin1');


