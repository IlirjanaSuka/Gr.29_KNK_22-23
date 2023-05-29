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

 public void ExamPaymentSearch() {
 FilteredList<examData> filter = new FilteredList<>(ExamPaymentList, e -> true);

 ExamPayment_search.textProperty().addListener((Observable, oldValue, newValue) -> {
 filter.setPredicate(predicateExamData -> {
 if (newValue == null || newValue.isEmpty()) {
 return true;
 }
 String searchKey = newValue.toLowerCase();
 return predicateExamData.getStudentNum().toLowerCase().contains(searchKey)
 || predicateExamData.getData().toLowerCase().contains(searchKey)
 || predicateExamData.getCourse1().toLowerCase().contains(searchKey)
 || predicateExamData.getSem().toLowerCase().contains(searchKey)
 || predicateExamData.getPrice().toLowerCase().contains(searchKey);
 });
 });

 SortedList<examData> sortList = new SortedList<>(filter);
 sortList.comparatorProperty().bind(ExamPayment_tableView.comparatorProperty());
 ExamPayment_tableView.setItems(sortList);
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

}
