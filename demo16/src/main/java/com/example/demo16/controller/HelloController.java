package com.example.demo16.controller;

import com.example.demo16.database.database;
import com.example.demo16.repository.getData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerbtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;


 public void loginAdmin() {
        String adminSql = "SELECT * FROM admin WHERE username = ?";
        String userSql = "SELECT * FROM user WHERE studentNum = ? AND cpassword = ?";

        connect = database.connectDb();

        try {
            Alert alert;

            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(adminSql);
                prepare.setString(1, username.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    String storedPassword = result.getString("password");
                    if (storedPassword.equals(password.getText())) {
                        // Admin login
                        getData.username = username.getText();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully logged in!");
                        alert.showAndWait();

                        loginBtn.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        root.setOnMousePressed((MouseEvent event) -> {
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });

                        root.setOnMouseDragged((MouseEvent event) -> {
                            stage.setX(event.getScreenX() - x);
                            stage.setY(event.getScreenY() - y);
                        });

                        stage.initStyle(StageStyle.TRANSPARENT);

                        stage.setScene(scene);
                        stage.show();
                    } else {
                        // Incorrect password
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Incorrect password");
                        alert.showAndWait();
                    }
                } else {
                    prepare = connect.prepareStatement(userSql);
                    prepare.setString(1, username.getText());
                    prepare.setString(2, password.getText());

                    result = prepare.executeQuery();

                    if (result.next()) {
                        // Proceed to user dashboard
                        getData.username = username.getText();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully logged in!");
                        alert.showAndWait();

                        loginBtn.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("dashboard1.fxml"));

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid ID or password");
                        alert.showAndWait();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerUser(ActionEvent event) throws IOException {
        registerbtn.getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
