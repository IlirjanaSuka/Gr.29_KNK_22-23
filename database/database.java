package com.example.loginstyle;

import java.sql.Connection;
import java.sql.DriverManager;


public class database {

    public static Connection connectDb(){

        try{

            Class.forName("com.mysql.jdbc.Driver");

            return DriverManager.getConnection("jdbc:mysql://localhost/knk", "root", "Lana.1234");
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

}
