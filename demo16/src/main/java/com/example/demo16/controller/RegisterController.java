package com.example.demo16.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/knk";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "liburna12K$";
    private static final String ENCRYPTION_ALGORITHM = "AES";

    @FXML
    private Button close;

    @FXML
    private PasswordField cpassword;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField studentNum;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    void register(ActionEvent event) {
        String studentNumber = studentNum.getText();
        String first = firstName.getText();
        String last = lastName.getText();
        String pass = password.getText();
        String confirmPass = cpassword.getText();

        if (studentNumber.isEmpty() || first.isEmpty() || last.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            System.out.println("Please fill in all the required fields.");
            return;
        }

        if (!pass.equals(confirmPass)) {
            System.out.println("Passwords do not match.");
            return;
        }

        try {
            // Hash the password using SHA-256 for added security
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedPasswordBytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKey = new SecretKeySpec(hashedPasswordBytes, ENCRYPTION_ALGORITHM);

            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedPasswordBytes = cipher.doFinal(pass.getBytes(StandardCharsets.UTF_8));
            String encryptedPassword = Base64.getEncoder().encodeToString(encryptedPasswordBytes);

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String sql = "INSERT INTO user (studentNum, firstName, lastName, password, cpassword) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, studentNumber);
                statement.setString(2, first);
                statement.setString(3, last);
                statement.setString(4, encryptedPassword);
                statement.setString(5, confirmPass);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User registered successfully!");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                    // Close the current registration window
                    Stage currentStage = (Stage) registerBtn.getScene().getWindow();
                    currentStage.close();
                } else {
                    System.out.println("Registration failed.");
                }
            } catch (SQLException e) {
                System.out.println("Error executing SQL query: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println("Error during encryption: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
