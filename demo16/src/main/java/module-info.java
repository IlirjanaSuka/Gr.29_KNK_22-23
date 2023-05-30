
module com.example.demo16 {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.demo16 to javafx.fxml;
    exports com.example.demo16;
    exports com.example.demo16.controller;
    opens com.example.demo16.controller to javafx.fxml;
    exports com.example.demo16.database;
    opens com.example.demo16.database to javafx.fxml;
    exports com.example.demo16.repository;
    opens com.example.demo16.repository to javafx.fxml;


}