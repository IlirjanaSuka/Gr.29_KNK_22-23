package com.example.demo16.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.example.demo16.database.database;
import com.example.demo16.repository.courseData;
import com.example.demo16.repository.examData;
import com.example.demo16.repository.getData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static com.example.demo16.controller.dashboardController.*;


public class dashboard1Controller implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button SemesterPayments_btn;

    @FXML
    private Button ExamPayment_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane SemesterPayments_form;

    @FXML
    private TextField SemesterPayments_student;

    @FXML
    private TextField SemesterPayments_semester;



    @FXML
    private Button SemesterPayments_addBtn;

    @FXML
    private ComboBox<Integer> SemesterPayments_price;

    @FXML
    private Button SemesterPayments_clearBtn;

    @FXML
    private Button SemesterPayments_deleteBtn;

    @FXML
    private AnchorPane ExamPayment_form;

    @FXML
    private TextField ExamPayment_studentNum;

    @FXML
    private TextField ExamPayment_data;

    @FXML
    private TextField ExamPayment_course1;

    @FXML
    private ComboBox<Integer> ExamPayment_price;

    @FXML
    private TextField ExamPayment_sem;

    @FXML
    private Button ExamPayment_addBtn;

    @FXML
    private Button ExamPayment_clearBtn;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    private final Integer[] semesterPrices = {25};
    private final Integer examPrice = 1;

    public void SemesterPaymentsAdd() {
        String insertData = "INSERT INTO semester (studentNum,semester,price) VALUES(?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;

            if (SemesterPayments_student.getText().isEmpty()
                    || SemesterPayments_semester.getText().isEmpty()
                    || SemesterPayments_price.getSelectionModel().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                String checkData = "SELECT studentNum FROM semester WHERE studentNum = '"
                        + SemesterPayments_student.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Student: " + SemesterPayments_student.getText() + " already exists!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, SemesterPayments_student.getText());
                    prepare.setString(2, SemesterPayments_semester.getText());
                    prepare.setString(3, String.valueOf(SemesterPayments_price.getValue())); // Retrieve selected price

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Payment is done!");
                    alert.showAndWait();


                    SemesterPaymentsClear();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SemesterPaymentsDelete() {
        String studentId = SemesterPayments_student.getText();
        String deleteStudentData = "DELETE FROM semester WHERE studentNum = ?";
        String deletePaymentData = "DELETE FROM semester WHERE price = ?";
        String deleteSemesterData = "DELETE FROM semester WHERE semester = ?";
        connect = database.connectDb();

        try {
            Alert alert;

            if (studentId.isEmpty()
                    || SemesterPayments_semester.getText().isEmpty()
                    || SemesterPayments_price.getSelectionModel().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE student: " + studentId + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    PreparedStatement deleteStudentStatement = connect.prepareStatement(deleteStudentData);
                    deleteStudentStatement.setString(1, studentId);
                    deleteStudentStatement.executeUpdate();

                    PreparedStatement deletePaymentStatement = connect.prepareStatement(deletePaymentData);
                    deletePaymentStatement.setString(1, studentId);
                    deletePaymentStatement.executeUpdate();

                    PreparedStatement deleteSemesterStatement = connect.prepareStatement(deleteSemesterData);
                    deleteSemesterStatement.setString(1, SemesterPayments_semester.getText());
                    deleteSemesterStatement.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    SemesterPaymentsClear();
                } else {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SemesterPaymentsClear() {
        SemesterPayments_student.setText("");
        SemesterPayments_semester.setText("");
        SemesterPayments_price.getSelectionModel();
    }

    public ObservableList<courseData> availableCourseListData() {

        ObservableList<courseData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM semester";

        connect = database.connectDb();

        try {
            courseData courseD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                courseD = new courseData(result.getString("student"),
                        result.getString("semester"),
                        result.getString("price"));

                listData.add(courseD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<courseData> availableCourseList;

    @FXML
    private TableView<?> SemesterPayments_tableView;






    @FXML
    public void ExamPaymentAdd() {
        String insertData = "INSERT INTO student_payments (studentNum, sem, data, course, price) VALUES (?, ?, ?, ?, ?)";

        try {
            connect = database.connectDb(); // Initialize the connect variable with the database connection

            Alert alert;

            if (ExamPayment_studentNum.getText().isEmpty()
                    || ExamPayment_sem.getText().isEmpty()
                    || ExamPayment_course1.getText().isEmpty()
                    || ExamPayment_data.getText().isEmpty()
                    || ExamPayment_price.getSelectionModel().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                String checkData = "SELECT * FROM student_payments WHERE studentNum = ?";

                PreparedStatement checkStatement = connect.prepareStatement(checkData);
                checkStatement.setString(1, ExamPayment_studentNum.getText());
                ResultSet checkResult = checkStatement.executeQuery();

                if (checkResult.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Student: " + ExamPayment_studentNum.getText() + " already exists!");
                    alert.showAndWait();
                } else {
                    PreparedStatement insertStatement = connect.prepareStatement(insertData);
                    insertStatement.setString(1, ExamPayment_studentNum.getText());
                    insertStatement.setString(2, ExamPayment_sem.getText());
                    insertStatement.setString(3, ExamPayment_data.getText());
                    insertStatement.setString(4, ExamPayment_course1.getText());
                    insertStatement.setString(5, ExamPayment_price.getSelectionModel().getSelectedItem().toString());

                    insertStatement.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    ExamPaymentClear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ExamPaymentClear() {
        ExamPayment_studentNum.setText("");
        ExamPayment_data.setText("");
        ExamPayment_course1.setText("");
        ExamPayment_sem.setText("");
        ExamPayment_price.getSelectionModel();
    }

    public ObservableList<examData> ExamPaymentListData() {
        ObservableList<examData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM student_payments";

        connect = database.connectDb();

        try {
            examData examD;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                examD = new examData(result.getString("studentNum"),
                        result.getString("data"),
                        result.getString("course"),
                        result.getString("sem"),
                        result.getString("price"));

                listData.add(examD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<examData> ExamPaymentList;



    private double x = 0;
    private double y = 0;

    public void logout() {

        try {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {


                logout.getScene().getWindow().hide();


                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayUsername() {
        username.setText(getData.username);
    }


    public void switchForm(ActionEvent event) {
        if (event.getSource() == SemesterPayments_btn) {

            SemesterPayments_form.setVisible(true);
            ExamPayment_form.setVisible(false);

            SemesterPayments_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            ExamPayment_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == ExamPayment_btn) {
            SemesterPayments_form.setVisible(false);
            ExamPayment_form.setVisible(true);

            ExamPayment_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            SemesterPayments_btn.setStyle("-fx-background-color:transparent");


        }
    }

    @FXML
    void handleLoginKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            logout();
            ExamPaymentAdd();
            SemesterPaymentsAdd();

        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }


    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        SemesterPayments_price.getItems().addAll(semesterPrices);
        ExamPayment_price.getItems().add(examPrice);
    }

}
