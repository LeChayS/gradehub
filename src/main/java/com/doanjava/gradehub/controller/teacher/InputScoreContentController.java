package com.doanjava.gradehub.controller.teacher;

import com.doanjava.gradehub.util.TeacherDashboardFakeData;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

public class InputScoreContentController {
    @FXML private ComboBox<String> yearCombo;
    @FXML private ComboBox<String> semesterCombo;
    @FXML private ComboBox<String> subjectCombo;
    @FXML private ComboBox<String> classCombo;
    @FXML private TextField lessonBeginField;
    @FXML private TextField lessonEndField;
    @FXML private DatePicker datePicker;
    @FXML private Button filterButton;

    @FXML private TableView<TeacherDashboardFakeData.StudentScoreRow> studentScoreTable;
    @FXML private TableColumn<TeacherDashboardFakeData.StudentScoreRow, String> studentIdCol;
    @FXML private TableColumn<TeacherDashboardFakeData.StudentScoreRow, String> studentNameCol;
    @FXML private TableColumn<TeacherDashboardFakeData.StudentScoreRow, Double> attendanceScoreCol;
    @FXML private TableColumn<TeacherDashboardFakeData.StudentScoreRow, Double> midtermScoreCol;
    @FXML private TableColumn<TeacherDashboardFakeData.StudentScoreRow, Double> finalScoreCol;
    @FXML private Button saveButton;

    @FXML
    public void initialize() {
        yearCombo.setItems(TeacherDashboardFakeData.getYears());
        semesterCombo.setItems(TeacherDashboardFakeData.getSemesters());

        yearCombo.setOnAction(e -> updateSubjects());
        semesterCombo.setOnAction(e -> updateSubjects());
        subjectCombo.setOnAction(e -> updateClasses());

        // Set default year to 2024-2025 if available
        String defaultYear = "2024-2025";
        if (TeacherDashboardFakeData.getYears().contains(defaultYear)) {
            yearCombo.getSelectionModel().select(TeacherDashboardFakeData.getYears().indexOf(defaultYear));
        } else {
            yearCombo.getSelectionModel().selectFirst();
        }
        semesterCombo.getSelectionModel().selectFirst();
        updateSubjects();

        classCombo.setOnAction(e -> {/* Optionally update lessons or other fields */});

        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        attendanceScoreCol.setCellValueFactory(new PropertyValueFactory<>("attendanceScore"));
        midtermScoreCol.setCellValueFactory(new PropertyValueFactory<>("midtermScore"));
        finalScoreCol.setCellValueFactory(new PropertyValueFactory<>("finalScore"));

        attendanceScoreCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        midtermScoreCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        finalScoreCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        studentScoreTable.setEditable(true);

        attendanceScoreCol.setOnEditCommit(event -> {
            TeacherDashboardFakeData.StudentScoreRow row = event.getRowValue();
            row.setAttendanceScore(event.getNewValue());
        });
        midtermScoreCol.setOnEditCommit(event -> {
            TeacherDashboardFakeData.StudentScoreRow row = event.getRowValue();
            row.setMidtermScore(event.getNewValue());
        });
        finalScoreCol.setOnEditCommit(event -> {
            TeacherDashboardFakeData.StudentScoreRow row = event.getRowValue();
            row.setFinalScore(event.getNewValue());
        });

        studentScoreTable.getItems().clear();
        saveButton.setOnAction(e -> saveScores());
        filterButton.setOnAction(e -> filterScores());

        lessonBeginField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*") || newVal.length() > 2) {
                lessonBeginField.setText(oldVal);
            }
        });
        lessonEndField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*") || newVal.length() > 2) {
                lessonEndField.setText(oldVal);
            }
        });
    }

    private void updateSubjects() {
        String year = yearCombo.getValue();
        subjectCombo.setItems(TeacherDashboardFakeData.getSubjects(year));
        subjectCombo.getSelectionModel().selectFirst();
        updateClasses();
    }

    private void updateClasses() {
        String year = yearCombo.getValue();
        String semester = semesterCombo.getValue();
        String subjectDisplay = subjectCombo.getValue();
        String subjectCode = TeacherDashboardFakeData.extractSubjectCode(subjectDisplay);
        classCombo.setItems(TeacherDashboardFakeData.getClasses(year, subjectCode, semester));
        classCombo.getSelectionModel().selectFirst();
    }

    private void saveScores() {
        for (TeacherDashboardFakeData.StudentScoreRow row : studentScoreTable.getItems()) {
            System.out.println(row.getStudentId() + " - " + row.getStudentName() + ": " +
                row.getAttendanceScore() + ", " + row.getMidtermScore() + ", " + row.getFinalScore());
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Lưu điểm thành công!", ButtonType.OK);
        alert.showAndWait();
    }

    private void filterScores() {
        if (yearCombo.getValue() == null || semesterCombo.getValue() == null || subjectCombo.getValue() == null ||
            classCombo.getValue() == null) {
            showError("Vui lòng chọn đầy đủ các bộ lọc!");
            return;
        }
        // Default values for lesson fields and date
        String beginText = lessonBeginField.getText();
        String endText = lessonEndField.getText();
        int begin = 1, end = 12;
        if (!beginText.isEmpty()) {
            try { begin = Integer.parseInt(beginText); } catch (NumberFormatException e) { showError("Tiết học phải là số từ 1 đến 12."); return; }
        }
        if (!endText.isEmpty()) {
            try { end = Integer.parseInt(endText); } catch (NumberFormatException e) { showError("Tiết học phải là số từ 1 đến 12."); return; }
        }
        if (begin < 1 || begin > 12 || end < 1 || end > 12 || begin > end) {
            showError("Tiết học phải từ 1 đến 12 và bắt đầu <= kết thúc.");
            return;
        }
        // Default date to today if not selected
        if (datePicker.getValue() == null) {
            datePicker.setValue(java.time.LocalDate.now());
        }
        studentScoreTable.setItems(TeacherDashboardFakeData.getStudentScores());
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}