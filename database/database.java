package com.example.demo16;

import java.sql.*;

public class database {


    public static Connection connectDb() {

        try{

            Class.forName("com.mysql.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/knk", "root", "");
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

}
