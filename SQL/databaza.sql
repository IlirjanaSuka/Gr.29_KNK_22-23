CREATE DATABASE knk;
USE knk;

CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO admin (username, password, role) VALUES ('admin', '123', 'admin');


CREATE TABLE student (
  id INT AUTO_INCREMENT PRIMARY KEY,
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
  INDEX idx_studentNum (studentNum),
  INDEX idx_student_year (year),
  INDEX idx_firstName (firstName),
  INDEX idx_lastName (lastName)
);

CREATE TABLE user (
  id INT AUTO_INCREMENT PRIMARY KEY,
  studentNum VARCHAR(20) NOT NULL,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  password NVARCHAR(30) NOT NULL,
  cpassword NVARCHAR(30) NOT NULL,
  FOREIGN KEY (studentNum) REFERENCES student(studentNum),
  FOREIGN KEY (firstName) REFERENCES student(firstName),
  FOREIGN KEY (lastName) REFERENCES student(lastName)
);
ALTER TABLE `user` MODIFY COLUMN `password` NVARCHAR(30);
select*from user;

CREATE TABLE student_grade (
  id INT AUTO_INCREMENT PRIMARY KEY,
  studentNum VARCHAR(20) NOT NULL,
  year VARCHAR(20) NOT NULL,
  course VARCHAR(50) NOT NULL,
  first_sem INT NOT NULL,
  second_sem INT NOT NULL,
  final INT NOT NULL,
  FOREIGN KEY (studentNum) REFERENCES student(studentNum)
);


CREATE TABLE course (
  id INT AUTO_INCREMENT PRIMARY KEY,
  course VARCHAR(50) NOT NULL,
  INDEX idx_course (course)
);

INSERT INTO course (course) VALUES
  ('FIEK'),
  ('FIM'),
  ('FNA');

drop table semester;
CREATE TABLE semester (
  id INT AUTO_INCREMENT PRIMARY KEY,
  studentNum VARCHAR(20) NOT NULL,
  semester VARCHAR(20) NOT NULL,
  price VARCHAR(20) NOT NULL,
  FOREIGN KEY (studentNum) REFERENCES student(studentNum),
  FOREIGN KEY (semester) REFERENCES student(year)
);

drop table student_payments;
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
