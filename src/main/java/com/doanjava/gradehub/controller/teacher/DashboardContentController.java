package com.doanjava.gradehub.controller.teacher;

import com.doanjava.gradehub.util.TeacherDashboardFakeData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.doanjava.gradehub.util.TeacherDashboardFakeData.SubjectRow;
import com.doanjava.gradehub.util.TeacherDashboardFakeData.GradeManagementRow;

public class DashboardContentController {
    @FXML private Label totalClassesLabel;
    @FXML private Label totalSubjectsLabel;
    @FXML private Label totalStudentsLabel;

    @FXML private TableView<SubjectRow> subjectsTable;
    @FXML private TableColumn<SubjectRow, String> subjectNameCol;
    @FXML private TableColumn<SubjectRow, String> subjectCodeCol;
    @FXML private TableColumn<SubjectRow, String> semesterCol;
    @FXML private TableColumn<SubjectRow, String> classCol;
    @FXML private TableColumn<SubjectRow, Void> enterGradeCol;
    @FXML private TableColumn<SubjectRow, Void> viewClassCol;

    @FXML private TableView<GradeManagementRow> gradeManagementTable;
    @FXML private TableColumn<GradeManagementRow, String> gmSubjectCol;
    @FXML private TableColumn<GradeManagementRow, String> gmClassCol;
    @FXML private TableColumn<GradeManagementRow, String> gmSemesterCol;
    @FXML private TableColumn<GradeManagementRow, String> gmStatusCol;
    @FXML private TableColumn<GradeManagementRow, Void> gmEnterGradeCol;

    @FXML
    public void initialize() {
        TeacherDashboardFakeData.populateOverview(totalClassesLabel, totalSubjectsLabel, totalStudentsLabel);
        subjectNameCol.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        subjectCodeCol.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        classCol.setCellValueFactory(new PropertyValueFactory<>("className"));
        gmSubjectCol.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        gmClassCol.setCellValueFactory(new PropertyValueFactory<>("className"));
        gmSemesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        gmStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Populate tables with default (first year and semester) data
        String defaultYear = TeacherDashboardFakeData.getYears().isEmpty() ? null : TeacherDashboardFakeData.getYears().get(0);
        String defaultSemester = TeacherDashboardFakeData.getSemesters().isEmpty() ? null : TeacherDashboardFakeData.getSemesters().get(0);
        TeacherDashboardFakeData.populateSubjectsTable(subjectsTable, defaultYear, defaultSemester);
        TeacherDashboardFakeData.populateGradeManagementTable(gradeManagementTable, defaultYear, defaultSemester);
    }
}