create database knk;
use knk;


create table admin(
username nvarchar(100),
password nvarchar(100)
);

insert into admin( username, password) value('admin1', '123'),('admin2', '1234');



CREATE TABLE student (
  id INT PRIMARY KEY AUTO_INCREMENT,
  studentNum VARCHAR(20) NOT NULL,
  year VARCHAR(20) NOT NULL,
  course VARCHAR(50) NOT NULL,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  gender VARCHAR(10) NOT NULL,
  birth DATE NOT NULL,
  status VARCHAR(20) NOT NULL,
  image VARCHAR(100) NOT NULL,
  date DATE NOT NULL,
  INDEX idx_studentNum (studentNum) 
);
CREATE INDEX idx_student_year ON student (year);

CREATE TABLE student_grade (
  id INT PRIMARY KEY AUTO_INCREMENT,
  studentNum VARCHAR(20) NOT NULL,
  year VARCHAR(20) NOT NULL,
  course VARCHAR(50) NOT NULL,
  first_sem INT NOT NULL,
  second_sem INT NOT NULL,
  final INT NOT NULL,
  FOREIGN KEY (studentNum) REFERENCES student(studentNum)
);





CREATE TABLE course (
  id INT PRIMARY KEY AUTO_INCREMENT,
  course VARCHAR(50) NOT NULL,
  INDEX idx_course (course) 
);

INSERT INTO course (course) VALUES
 ('FIEK'),('FIM'),('FNA');

 CREATE TABLE semester (
  id INT PRIMARY KEY AUTO_INCREMENT,
  student VARCHAR(20) NOT NULL,
  semester VARCHAR(20) NOT NULL,
  price VARCHAR(20) NOT NULL,
  foreign key(student) references student(studentNum),
foreign key(semester) references student(year) 
);


CREATE TABLE student_payments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  studentNum VARCHAR(50) NOT NULL,
  data VARCHAR(50) NOT NULL,
  course VARCHAR(50) NOT NULL,
  sem VARCHAR(20) NOT NULL,
  price VARCHAR(50) NOT NULL,
  FOREIGN KEY (studentNum) REFERENCES student(studentNum),
  FOREIGN KEY (sem) REFERENCES semester(semester)
);
