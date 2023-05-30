package com.example.demo16.repository;
public class courseData {

    private String student;
    private String semester;
    private String price;

    public courseData(String student, String semester, String price){
        this.student=student;
        this.semester=semester;
        this.price=price;
    }
    public String getStudent(){
        return student;
    }
    public  String getSemester(){
        return semester;
    }
    public  String getPrice(){
        return price;
    }
}