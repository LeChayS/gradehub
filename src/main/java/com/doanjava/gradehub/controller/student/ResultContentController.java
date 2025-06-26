package com.doanjava.gradehub.controller.student;

import com.doanjava.gradehub.model.StudentGradeModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ResultContentController {
    
    @FXML
    private TableView<StudentGradeModel> gradeTable;
    @FXML
    private TableColumn<StudentGradeModel, String> colCode;
    @FXML
    private TableColumn<StudentGradeModel, String> colName;
    @FXML
    private TableColumn<StudentGradeModel, Integer> colCredits;
    @FXML
    private TableColumn<StudentGradeModel, Double> colMidterm;
    @FXML
    private TableColumn<StudentGradeModel, Double> colFinal;
    @FXML
    private TableColumn<StudentGradeModel, Double> colTotal;
    @FXML
    final private ObservableList<StudentGradeModel> rawGradeList = FXCollections.observableArrayList(); 

    @FXML
    private Label labelCredits;
    @FXML
    private Label labelGPA;
    @FXML
    private ComboBox<String> comboBoxYear;
    @FXML
    private ComboBox<String> comboBoxSemester;

    @FXML
    public void initialize() {
        colCode.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSubjectCode()));
        colName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSubjectName()));
        colCredits.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCredits()).asObject());
        colMidterm.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getMidterm()).asObject());
        colFinal.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getFinalExam()).asObject());
        colTotal.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getTotal()).asObject());

        // Dữ liệu tĩnh

        // Dữ liệu sinh viên
      rawGradeList.addAll(
        new StudentGradeModel("IT101", "Nhập môn CNTT", 3, 7.5, 8.0, "2022-2023", "1"),
        new StudentGradeModel("MK101", "Nhập môn Marketing", 3, 6.5, 7.0, "2022-2023", "1"),
        new StudentGradeModel("EC101", "Nhập môn TMĐT", 3, 7.0, 7.5, "2022-2023", "1"),
        new StudentGradeModel("IT102", "Kỹ thuật lập trình", 4, 6.0, 7.5, "2022-2023", "2"),
        new StudentGradeModel("MA101", "Toán rời rạc", 3, 8.0, 8.0, "2022-2023", "2"),
        new StudentGradeModel("EN101", "Tiếng Anh 1", 2, 6.0, 6.5, "2022-2023", "3"),
        new StudentGradeModel("CS201", "Cấu trúc dữ liệu", 3, 7.0, 7.5, "2023-2024", "1"),
        new StudentGradeModel("DB201", "Cơ sở dữ liệu", 3, 7.5, 8.5, "2023-2024", "1"),
        new StudentGradeModel("IT202", "Lập trình hướng đối tượng", 4, 6.5, 7.5, "2023-2024", "1"),
        new StudentGradeModel("WE201", "Thiết kế Web", 3, 8.0, 8.5, "2023-2024", "2"),
        new StudentGradeModel("MA201", "Xác suất thống kê", 3, 6.5, 7.0, "2023-2024", "2"),
        new StudentGradeModel("EN102", "Tiếng Anh 2", 2, 7.0, 7.0, "2023-2024", "3"));

        // Dữ liệu combox box
        ObservableList<String> listOfYear = FXCollections.observableArrayList("2022-2023", "2023-2024", "2024-2025", "2025-2026");
        ObservableList<String> listOfSemester = FXCollections.observableArrayList("1", "2", "3");
        
        // Set dữ liệu
        comboBoxYear.setItems(listOfYear);
        comboBoxSemester.setItems(listOfSemester);

        // Tính tổng tín chỉ & GPA
        int totalCredits = rawGradeList.stream().mapToInt(StudentGradeModel::getCredits).sum();
        double gpa = rawGradeList.stream().mapToDouble(g -> g.getTotal() * g.getCredits()).sum() / totalCredits;

        labelCredits.setText("Tín chỉ: " + totalCredits);
        labelGPA.setText(String.format("GPA: %.2f", gpa));

        // Trigger
        comboBoxYear.setOnAction(this::comboBoxChanged);
        comboBoxSemester.setOnAction(this::comboBoxChanged);
    }

    public void comboBoxChanged(ActionEvent actionEvent){
        String selectedYear = comboBoxYear.getValue();
        String selectedSemester = comboBoxSemester.getValue();

        if(selectedYear == null || selectedSemester == null) return;

        ObservableList<StudentGradeModel> filteredList = rawGradeList.filtered(g -> g.getAcademicYear().equals(selectedYear) 
        && g.getSemester().equals(selectedSemester));

        gradeTable.setItems(filteredList);
    }
}
