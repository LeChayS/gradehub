package com.doanjava.gradehub.controller.student;

import com.doanjava.gradehub.model.CurrentSubject;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DashboardContentController {
    @FXML private TableView<CurrentSubject> currentSubjectsTable;
    @FXML private TableColumn<CurrentSubject, String> colSubName;
    @FXML private TableColumn<CurrentSubject, String> colSubCode;
    @FXML private TableColumn<CurrentSubject, String> colSubClass;
    @FXML private TableColumn<CurrentSubject, String> colTeacher;

    @FXML
    public void initialize() {
        // Initialization logic can be added here if needed
        colSubName.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getSubjectName()));
        colSubCode.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getSubjectCode()));
        colSubClass.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getSubjectClass()));
        colTeacher.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTeacherName()));
    }
}