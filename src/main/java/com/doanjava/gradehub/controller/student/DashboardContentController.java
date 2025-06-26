package com.doanjava.gradehub.controller.student;

import com.doanjava.gradehub.model.CurrentSubject;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class DashboardContentController {
    @FXML
    private TableView<CurrentSubject> currentSubjectsTable;
    @FXML
    public void initialize() {
        // Initialization logic can be added here if needed
    }
}