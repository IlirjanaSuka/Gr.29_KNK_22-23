
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.*;


public class HelloController {

    @FXML
    private Button button;

    @FXML
    private TextField email;

    @FXML
    private TextField id;

    @FXML
    private Label log;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label wlc;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/knk";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public void handleLoginButtonAction() {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username.getText());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {              
                String storedPassword = resultSet.getString("password");
                String enteredPassword = password.getText();

                if (storedPassword.equals(enteredPassword)) {                   
                    log.setText("Login successful");                  
                } else {
                    log.setText("Invalid password");
                }
            } else {
                log.setText("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
};
