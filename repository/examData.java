package com.example.demo16;

public class examData {
    private String studentNum;
    private String data;
    private String course1;
    private String sem;
    private String price;

    public examData(String studentNum, String data, String course1, String sem, String price) {
        this.studentNum = studentNum;
        this.data = data;
        this.course1 = course1;
        this.sem = sem;
        this.price = price;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public String getData() {
        return data;
    }

    public String getCourse1() {
        return course1;
    }

    public String getSem() {
        return sem;
    }

    public String getPrice() {
        return price;
    }
}
