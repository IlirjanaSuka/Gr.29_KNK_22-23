package com.example.demo16.controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

import com.example.demo16.database.database;
import com.example.demo16.repository.courseData;
import com.example.demo16.repository.examData;
import com.example.demo16.repository.getData;
import com.example.demo16.repository.studentData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class dashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button addStudents_btn;

    @FXML
    private Button SemesterPayments_btn;

    @FXML
    private Button ExamPayment_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEnrolled;

    @FXML
    private Label home_totalFemale;

    @FXML
    private Label home_totalMale;

    @FXML
    private BarChart<?, ?> home_totalEnrolledChart;

    @FXML
    private AreaChart<?, ?> home_totalFemaleChart;

    @FXML
    private LineChart<?, ?> home_totalMaleChart;

    @FXML
    private AnchorPane addStudents_form;

    @FXML
    private TextField addStudents_search;

    @FXML
    private TableView<studentData> addStudents_tableView;

    @FXML
    private TableColumn<studentData, String> addStudents_col_studentNum;

    @FXML
    private TableColumn<studentData, String> addStudents_col_year;

    @FXML
    private TableColumn<studentData, String> addStudents_col_course;

    @FXML
    private TableColumn<studentData, String> addStudents_col_firstName;

    @FXML
    private TableColumn<studentData, String> addStudents_col_lastName;

    @FXML
    private TableColumn<studentData, String> addStudents_col_gender;

    @FXML
    private TableColumn<studentData, String> addStudents_col_birth;

    @FXML
    private TableColumn<studentData, String> addStudents_col_status;

    @FXML
    private TextField addStudents_studentNum;

    @FXML
    private ComboBox<?> addStudents_year;

    @FXML
    private ComboBox<?> addStudents_course;

    @FXML
    private TextField addStudents_firstName;

    @FXML
    private TextField addStudents_lastName;

    @FXML
    private DatePicker addStudents_birth;

    @FXML
    private ComboBox<?> addStudents_status;

    @FXML
    private ComboBox<?> addStudents_gender;

    @FXML
    private ImageView addStudents_imageView;

    @FXML
    private Button addStudents_insertBtn;

    @FXML
    private Button addStudents_addBtn;

    @FXML
    private Button addStudents_updateBtn;

    @FXML
    private Button addStudents_deleteBtn;

    @FXML
    private Button addStudents_clearBtn;

    @FXML
    private AnchorPane SemesterPayments_form;

    @FXML
    private TextField SemesterPayments_student;

    @FXML
    private TextField SemesterPayments_semester;

    @FXML
    private TextField SemesterPayments_price;

    @FXML
    private Button SemesterPayments_addBtn;

    @FXML
    private Button SemesterPayments_updateBtn;

    @FXML
    private Button SemesterPayments_clearBtn;

    @FXML
    private Button SemesterPayments_deleteBtn;

    @FXML
    private TableView<courseData> SemesterPayments_tableView;

    @FXML
    private TableColumn<courseData, String> SemesterPayments_col_student;

    @FXML
    private TableColumn<courseData, String> SemesterPayments_col_semester;

    @FXML
    private TableColumn<courseData, String> SemesterPayments_col_price;


    @FXML
    private AnchorPane ExamPayment_form;

    @FXML
    private TextField ExamPayment_studentNum;

    @FXML
    private TextField ExamPayment_data;

    @FXML
    private TextField ExamPayment_course1;

    @FXML
    private TextField ExamPayment_price;

    @FXML
    private TextField ExamPayment_sem;

    @FXML
    private Button ExamPayment_addBtn;

    @FXML
    private Button ExamPayment_clearBtn;

    @FXML
    private TableView<examData> ExamPayment_tableView;

    @FXML
    private TableColumn<examData, String> ExamPayment_col_studentNum;

    @FXML
    private TableColumn<examData, String> ExamPayment_col_course1;

    @FXML
    private TableColumn<examData, String> ExamPayment_col_Sem;

    @FXML
    private TableColumn<examData, String> ExamPayment_col_data;

    @FXML
    private TableColumn<examData, String> ExamPayment_col_price;

    @FXML
    private TextField ExamPayment_search;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;   public void homeDisplayTotalEnrolledStudents() {
        String sql = "SELECT COUNT(id) FROM student";

        connect = database.connectDb();

        int countEnrolled = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countEnrolled = result.getInt("COUNT(id)");
            }

            home_totalEnrolled.setText(String.valueOf(countEnrolled));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayFemaleEnrolled() {


        String sql = "SELECT COUNT(id) FROM student WHERE gender = 'Female' and status = 'Enrolled'";

        connect = database.connectDb();

        try {
            int countFemale = 0;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countFemale = result.getInt("COUNT(id)");
            }

            home_totalFemale.setText(String.valueOf(countFemale));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void homeDisplayMaleEnrolled() {

        String sql = "SELECT COUNT(id) FROM student WHERE gender = 'Male' and status = 'Enrolled'";

        connect = database.connectDb();

        try {
            int countMale = 0;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countMale = result.getInt("COUNT(id)");
            }
            home_totalMale.setText(String.valueOf(countMale));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void homeDisplayTotalEnrolledChart() {
        home_totalEnrolledChart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM student WHERE status = 'Enrolled' GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";

        connect = database.connectDb();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            home_totalEnrolledChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void homeDisplayFemaleEnrolledChart() {

        home_totalFemaleChart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM student WHERE status = 'Enrolled' and gender = 'Female' GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";

        connect = database.connectDb();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            home_totalFemaleChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayEnrolledMaleChart() {

        home_totalMaleChart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM student WHERE status = 'Enrolled' and gender = 'Male' GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";

        connect = database.connectDb();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            home_totalMaleChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void addStudentsAdd() {
        String insertData = "INSERT INTO student (studentNum, year, course, firstName, lastName, gender, birth, status, image, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect = database.connectDb();

        try {
            Alert alert;

            if (addStudents_studentNum.getText().isEmpty() || addStudents_year.getSelectionModel().getSelectedItem() == null
                    || addStudents_course.getSelectionModel().getSelectedItem() == null || addStudents_firstName.getText().isEmpty()
                    || addStudents_lastName.getText().isEmpty() || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null || addStudents_status.getSelectionModel().getSelectedItem() == null
                    || getData.path == null || getData.path.isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                // CHECK IF THE STUDENTNUMBER IS ALREADY EXIST
                String checkData = "SELECT studentNum FROM student WHERE studentNum = ?";
                PreparedStatement checkStatement = connect.prepareStatement(checkData);
                checkStatement.setString(1, addStudents_studentNum.getText());
                ResultSet result = checkStatement.executeQuery();

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Student #" + addStudents_studentNum.getText() + " already exists!");
                    alert.showAndWait();
                } else {
                    PreparedStatement prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, addStudents_studentNum.getText());
                    prepare.setString(2, (String) addStudents_year.getSelectionModel().getSelectedItem());
                    prepare.setString(3, (String) addStudents_course.getSelectionModel().getSelectedItem());
                    prepare.setString(4, addStudents_firstName.getText());
                    prepare.setString(5, addStudents_lastName.getText());
                    prepare.setString(6, (String) addStudents_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(7, String.valueOf(addStudents_birth.getValue()));
                    prepare.setString(8, (String) addStudents_status.getSelectionModel().getSelectedItem());
                    prepare.setString(9, getData.path);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(10, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    addStudentsShowListData();
                    addStudentsClear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addStudentsUpdate() {

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        String updateData = "UPDATE student SET "
                + "year = '" + addStudents_year.getSelectionModel().getSelectedItem()
                + "', course = '" + addStudents_course.getSelectionModel().getSelectedItem()
                + "', firstName = '" + addStudents_firstName.getText()
                + "', lastName = '" + addStudents_lastName.getText()
                + "', gender = '" + addStudents_gender.getSelectionModel().getSelectedItem()
                + "', birth = '" + addStudents_birth.getValue()
                + "', status = '" + addStudents_status.getSelectionModel().getSelectedItem()
                + "', image = '" + uri + "' WHERE studentNum = '"
                + addStudents_studentNum.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            if (addStudents_studentNum.getText().isEmpty()
                    || addStudents_year.getSelectionModel().getSelectedItem() == null
                    || addStudents_course.getSelectionModel().getSelectedItem() == null
                    || addStudents_firstName.getText().isEmpty()
                    || addStudents_lastName.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null
                    || addStudents_status.getSelectionModel().getSelectedItem() == null
                    || getData.path == null || getData.path == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Student #" + addStudents_studentNum.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    addStudentsShowListData();
                    // TO CLEAR THE FIELDS
                    addStudentsClear();

                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void addStudentsDelete() {
        String deleteData = "DELETE FROM student WHERE studentNum = ?";
        String deleteGradeData = "DELETE FROM student_grade WHERE studentNum = ?";

        connect = database.connectDb();

        try {
            Alert alert;
            if (addStudents_studentNum.getText().isEmpty() || addStudents_year.getSelectionModel().getSelectedItem() == null
                    || addStudents_course.getSelectionModel().getSelectedItem() == null || addStudents_firstName.getText().isEmpty()
                    || addStudents_lastName.getText().isEmpty() || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null || addStudents_status.getSelectionModel().getSelectedItem() == null
                    || getData.path == null || getData.path.isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Student #" + addStudents_studentNum.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    PreparedStatement deleteStatement = connect.prepareStatement(deleteData);
                    deleteStatement.setString(1, addStudents_studentNum.getText());
                    deleteStatement.executeUpdate();

                    PreparedStatement deleteGradeStatement = connect.prepareStatement(deleteGradeData);
                    deleteGradeStatement.setString(1, addStudents_studentNum.getText());
                    deleteGradeStatement.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    addStudentsShowListData();
                    addStudentsClear();
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addStudentsClear() {
        addStudents_studentNum.setText("");
        addStudents_studentNum.setText("");
        addStudents_year.getSelectionModel().clearSelection();
        addStudents_course.getSelectionModel().clearSelection();
        addStudents_firstName.setText("");
        addStudents_lastName.setText("");
        addStudents_gender.getSelectionModel().clearSelection();
        addStudents_birth.setValue(null);
        addStudents_status.getSelectionModel().clearSelection();
        addStudents_imageView.setImage(null);

        getData.path = "";
    }

    public void addStudentsInsertImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            image = new Image(file.toURI().toString(), 120, 149, false, true);
            addStudents_imageView.setImage(image);

            getData.path = file.getAbsolutePath();

        }
    }

      @FXML
    void addStudentsSearch() {
        String searchText = addStudents_search.getText().toLowerCase();

        FilteredList<studentData> filteredData = new FilteredList<>(addStudents_tableView.getItems(), p ->
                p.getFirstName().toLowerCase().contains(searchText) ||
                        p.getLastName().toLowerCase().contains(searchText) ||
                        p.getStudentNum().toLowerCase().contains(searchText) ||
                        p.getCourse().toLowerCase().contains(searchText) ||
                        p.getYear().toLowerCase().contains(searchText) ||
                        p.getGender().toLowerCase().contains(searchText) ||
                        p.getStatus().toLowerCase().contains(searchText));

        SortedList<studentData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(addStudents_tableView.comparatorProperty());
        addStudents_tableView.setItems(sortedData);
    }


    private String[] yearList = {"1", "2", "3", "4", "5", "6"};

    public void addStudentsYearList() {

        List<String> yearL = new ArrayList<>();

        for (String data : yearList) {
            yearL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(yearL);
        addStudents_year.setItems(ObList);

    }
    public void addStudentsCourseList() {

        String listCourse = "SELECT * FROM course";

        connect = database.connectDb();

        try {

            ObservableList listC = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(listCourse);
            result = prepare.executeQuery();

            while (result.next()) {
                listC.add(result.getString("course"));
            }
            addStudents_course.setItems(listC);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private String[] genderList = {"Male", "Female", "Others"};

    public void addStudentsGenderList() {
        List<String> genderL = new ArrayList<>();

        for (String data : genderList) {
            genderL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(genderL);
        addStudents_gender.setItems(ObList);
    }

    private String[] statusList = {"Enrolled", "Not Enrolled", "Inactive"};

    public void addStudentsStatusList() {
        List<String> statusL = new ArrayList<>();

        for (String data : statusList) {
            statusL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(statusL);
        addStudents_status.setItems(ObList);
    }

    public ObservableList<studentData> addStudentsListData() {

        ObservableList<studentData> listStudents = FXCollections.observableArrayList();

        String sql = "SELECT * FROM student";

        connect = database.connectDb();

        try {
            studentData studentD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                studentD = new studentData(result.getString("studentNum"),
                        result.getString("year"),
                        result.getString("course"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("birth"),
                        result.getString("status"),
                        result.getString("image"));

                listStudents.add(studentD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStudents;
    }

    private ObservableList<studentData> addStudentsListD;

    public void addStudentsShowListData() {
        addStudentsListD = addStudentsListData();

        addStudents_col_studentNum.setCellValueFactory(new PropertyValueFactory<>("studentNum"));
        addStudents_col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        addStudents_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        addStudents_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addStudents_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addStudents_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addStudents_col_birth.setCellValueFactory(new PropertyValueFactory<>("birth"));
        addStudents_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        addStudents_tableView.setItems(addStudentsListD);

    }

    public void addStudentsSelect() {

        studentData studentD = addStudents_tableView.getSelectionModel().getSelectedItem();
        int num = addStudents_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addStudents_studentNum.setText(String.valueOf(studentD.getStudentNum()));
        addStudents_firstName.setText(studentD.getFirstName());
        addStudents_lastName.setText(studentD.getLastName());
        addStudents_birth.setValue(LocalDate.parse(String.valueOf(studentD.getBirth())));

        String uri = "file:" + studentD.getImage();

        image = new Image(uri, 120, 149, false, true);
        addStudents_imageView.setImage(image);

        getData.path = studentD.getImage();

    }

    public void SemesterPaymentsAdd(){

        String insertData = "INSERT INTO semester (student,semester,price) VALUES(?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;

            if (SemesterPayments_student.getText().isEmpty()
                    || SemesterPayments_semester.getText().isEmpty()
                    || SemesterPayments_price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                String checkData = "SELECT student FROM semester WHERE student = '"
                        + SemesterPayments_student.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Student: " + SemesterPayments_student.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, SemesterPayments_student.getText());
                    prepare.setString(2, SemesterPayments_semester.getText());
                    prepare.setString(3, SemesterPayments_price.getText());

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();


                    availableCourseShowListData();
                    SemesterPaymentsClear();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SemesterPaymentsUpdate() {
        String updateData = "UPDATE semester SET semester = ?, price = ? WHERE student = ?";

        connect = database.connectDb();

        try {
            Alert alert;

            if (SemesterPayments_student.getText().isEmpty()
                    || SemesterPayments_semester.getText().isEmpty()
                    || SemesterPayments_price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Course: " + SemesterPayments_student.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    PreparedStatement updateStatement = connect.prepareStatement(updateData);
                    updateStatement.setString(1, SemesterPayments_semester.getText());
                    updateStatement.setString(2, SemesterPayments_price.getText());
                    updateStatement.setString(3, SemesterPayments_student.getText());
                    updateStatement.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    availableCourseShowListData();
                    SemesterPaymentsClear();
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SemesterPaymentsDelete() {
        String studentId = SemesterPayments_student.getText();
        String deleteStudentData = "DELETE FROM semester WHERE student = ?";
        String deletePaymentData = "DELETE FROM semester WHERE price = ?";
        String deleteSemesterData = "DELETE FROM semester WHERE semester = ?";
        connect = database.connectDb();

        try {
            Alert alert;

            if (studentId.isEmpty()
                    || SemesterPayments_semester.getText().isEmpty()
                    || SemesterPayments_price.getText().isEmpty()) {
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

                    availableCourseShowListData();
                    SemesterPaymentsClear();
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SemesterPaymentsClear() {
        SemesterPayments_student.setText("");
        SemesterPayments_semester.setText("");
        SemesterPayments_price.setText("");
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

    public void availableCourseShowListData() {
        availableCourseList = availableCourseListData();

        SemesterPayments_col_student.setCellValueFactory(new PropertyValueFactory<>("student"));
        SemesterPayments_col_semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        SemesterPayments_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        SemesterPayments_tableView.setItems(availableCourseList);

    }

    public void SemesterPaymentsSelect() {
        courseData courseD = SemesterPayments_tableView.getSelectionModel().getSelectedItem();

        if (courseD == null) {
            return;
        }

        SemesterPayments_student.setText(courseD.getStudent());
        SemesterPayments_semester.setText(courseD.getSemester());
        SemesterPayments_price.setText(courseD.getPrice());

    }


    public void ExamPaymentAdd() {
        String insertData = "INSERT INTO student_payments (studentNum, sem, data, course, price) VALUES (?, ?, ?, ?, ?)";

        connect = database.connectDb();

        try {
            Alert alert;

            if (ExamPayment_studentNum.getText().isEmpty()
                    || ExamPayment_sem.getText().isEmpty()
                    || ExamPayment_course1.getText().isEmpty()
                    || ExamPayment_data.getText().isEmpty()
                    || ExamPayment_price.getText().isEmpty()) {
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
                    insertStatement.setString(5, ExamPayment_price.getText());

                    insertStatement.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    ExamPaymentShowListData();
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
        ExamPayment_price.setText("");
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

    public void ExamPaymentShowListData() {
        ExamPaymentList = ExamPaymentListData();

        ExamPayment_col_studentNum.setCellValueFactory(new PropertyValueFactory<>("studentNum"));
        ExamPayment_col_data.setCellValueFactory(new PropertyValueFactory<>("data"));
        ExamPayment_col_course1.setCellValueFactory(new PropertyValueFactory<>("course1"));
        ExamPayment_col_Sem.setCellValueFactory(new PropertyValueFactory<>("sem"));
        ExamPayment_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ExamPayment_tableView.setItems(ExamPaymentList);
    }

    public void ExamPaymentSelect() {
        examData examD = ExamPayment_tableView.getSelectionModel().getSelectedItem();

        if (examD == null) {
            return;
        }

        ExamPayment_studentNum.setText(examD.getStudentNum());
        ExamPayment_data.setText(examD.getData());
        ExamPayment_course1.setText(examD.getCourse1());
        ExamPayment_sem.setText(examD.getSem());
        ExamPayment_price.setText(examD.getPrice());
    }

     @FXML
    void ExamPaymentSearch() {
    String searchText = ExamPayment_search.getText().toLowerCase();

    FilteredList<examData> filteredData = new FilteredList<>(ExamPayment_tableView.getItems(), p ->
            p.getCourse1().toLowerCase().contains(searchText) ||
                    p.getData().toLowerCase().contains(searchText) ||
                    p.getStudentNum().toLowerCase().contains(searchText) ||
                    p.getSem().toLowerCase().contains(searchText) ||
                    p.getPrice().toLowerCase().contains(searchText));

    SortedList<examData> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(ExamPayment_tableView.comparatorProperty());
    ExamPayment_tableView.setItems(sortedData);
}

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
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayUsername(){
        username.setText(getData.username);
    }

    public void defaultNav(){
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addStudents_form.setVisible(false);
            SemesterPayments_form.setVisible(false);
            ExamPayment_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudents_btn.setStyle("-fx-background-color:transparent");
            SemesterPayments_btn.setStyle("-fx-background-color:transparent");
            ExamPayment_btn.setStyle("-fx-background-color:transparent");

            homeDisplayTotalEnrolledStudents();
            homeDisplayMaleEnrolled();
            homeDisplayFemaleEnrolled();
            homeDisplayEnrolledMaleChart();
            homeDisplayFemaleEnrolledChart();
            homeDisplayTotalEnrolledChart();

        } else if (event.getSource() == addStudents_btn) {
            home_form.setVisible(false);
            addStudents_form.setVisible(true);
            SemesterPayments_form.setVisible(false);
            ExamPayment_form.setVisible(false);

            addStudents_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            SemesterPayments_btn.setStyle("-fx-background-color:transparent");
            ExamPayment_btn.setStyle("-fx-background-color:transparent");


            addStudentsShowListData();
            addStudentsYearList();
            addStudentsGenderList();
            addStudentsStatusList();
            addStudentsCourseList();
            addStudentsSearch();

        } else if (event.getSource() == SemesterPayments_btn) {
            home_form.setVisible(false);
            addStudents_form.setVisible(false);
            SemesterPayments_form.setVisible(true);
            ExamPayment_form.setVisible(false);

            SemesterPayments_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudents_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            ExamPayment_btn.setStyle("-fx-background-color:transparent");

            availableCourseShowListData();

        } else if (event.getSource() == ExamPayment_btn) {
            home_form.setVisible(false);
            addStudents_form.setVisible(false);
            SemesterPayments_form.setVisible(false);
            ExamPayment_form.setVisible(true);

            ExamPayment_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addStudents_btn.setStyle("-fx-background-color:transparent");
            SemesterPayments_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");

            ExamPaymentShowListData();
            ExamPaymentSearch();

        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

   @Override
public void initialize(URL location, ResourceBundle resources) {

    addStudents_search.textProperty().addListener((observable, oldValue, newValue) -> addStudentsSearch());
    ExamPayment_search.textProperty().addListener((observable, oldValue, newValue) -> ExamPaymentSearch());
    displayUsername();
    defaultNav();

    homeDisplayTotalEnrolledStudents();
    homeDisplayMaleEnrolled();
    homeDisplayFemaleEnrolled();
    homeDisplayEnrolledMaleChart();
    homeDisplayFemaleEnrolledChart();
    homeDisplayTotalEnrolledChart();

    addStudentsShowListData();
    addStudentsYearList();
    addStudentsGenderList();
    addStudentsStatusList();
    addStudentsCourseList();

    availableCourseShowListData();

    ExamPaymentShowListData();

}
