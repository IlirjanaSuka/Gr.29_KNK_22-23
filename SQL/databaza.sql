create database knk;
use knk;

create table admin(
username nvarchar(100),
password nvarchar(100)
);

insert into admin( username, password) value('admin1', '123'),('admin2', '1234');
select* from admin;


CREATE TABLE student (
  id INT AUTO_INCREMENT PRIMARY KEY,
  studentNum VARCHAR(10) NOT NULL,
  year VARCHAR(10) NOT NULL,
  course VARCHAR(50) NOT NULL,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  gender VARCHAR(10) NOT NULL,
  birth DATE NOT NULL,
  status VARCHAR(10) NOT NULL,
  image VARCHAR(100),
  date DATE NOT NULL
);
drop table student_grade;
CREATE TABLE student_grade (
  id INT AUTO_INCREMENT PRIMARY KEY,
  studentNum VARCHAR(10) NOT NULL,
  year VARCHAR(10) NOT NULL,
  course VARCHAR(50) NOT NULL,
   sem INT NOT NULL,
  final INT NOT NULL
);

CREATE TABLE course (
  id INT PRIMARY KEY AUTO_INCREMENT,
  course VARCHAR(50)
);
insert into course values
(1,'TIK'),
(2,'KNK'),
(3,'POO');
