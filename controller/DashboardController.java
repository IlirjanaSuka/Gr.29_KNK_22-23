package com.example.demo16;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Kjo metodë thirret automatikisht pas ngarkimit të skedarit FXML
        // Këtu mund të inicializoni cilësitë e kontrollerit për faqen e dashboard-it
        // P.sh., mund të shfaqni një mesazh mirëseardhëse për përdoruesin
        welcomeLabel.setText("Welcome, " + getData.username + "!");
    }

    // Metoda të tjera të cilat realizojnë funksionalitetin e faqes së dashboard-it
    // Mund të shtoni metoda të tjera në përputhje me nevojat tuaja

}
