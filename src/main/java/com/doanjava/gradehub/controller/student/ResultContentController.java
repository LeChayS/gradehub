package com.doanjava.gradehub.controller.student;

import com.doanjava.gradehub.model.StudentGradeModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private Label labelCredits;
    @FXML
    private Label labelGPA;

    @FXML
    public void initialize() {
        colCode.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSubjectCode()));
        colName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSubjectName()));
        colCredits.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCredits()).asObject());
        colMidterm.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getMidterm()).asObject());
        colFinal.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getFinalExam()).asObject());
        colTotal.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getTotal()).asObject());

        // Dữ liệu tĩnh
        ObservableList<StudentGradeModel> data = FXCollections.observableArrayList(
                new StudentGradeModel("IT101", "Nhập môn CNTT", 3, 7.5, 8.0),
                new StudentGradeModel("IT102", "Kỹ thuật lập trình", 4, 6.0, 7.5),
                new StudentGradeModel("IT201", "Cấu trúc dữ liệu", 3, 8.0, 8.5));

        gradeTable.setItems(data);

        // Tính tổng tín chỉ & GPA
        int totalCredits = data.stream().mapToInt(StudentGradeModel::getCredits).sum();
        double gpa = data.stream().mapToDouble(g -> g.getTotal() * g.getCredits()).sum() / totalCredits;

        labelCredits.setText("Tín chỉ: " + totalCredits);
        labelGPA.setText(String.format("GPA: %.2f", gpa));
    }
}
