package com.example.demo16.database;

import java.sql.*;

public class database {


    public static Connection connectDb() {

        try{

            Class.forName("com.mysql.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/knk", "root", "Lana.1234");
            return connect;
        }catch(Exception e)
        {e.printStackTrace();}
        return null;
    }

}