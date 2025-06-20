package com.doanjava.gradehub.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Check login credentials for each role
        String fxmlPath = null;
        String role = null;
        if ("admin@gmail.com".equals(email) && "123456".equals(password)) {
            fxmlPath = "/view/admin/dashboard-view.fxml";
            role = "admin";
        } else if ("teacher@gmail.com".equals(email) && "123456".equals(password)) {
            fxmlPath = "/view/teacher/dashboard-view.fxml";
            role = "teacher";
        } else if ("student@gmail.com".equals(email) && "123456".equals(password)) {
            fxmlPath = "/view/student/dashboard-view.fxml";
            role = "student";
        }

        if (fxmlPath != null) {
            try {
                final String finalRole = role;
                final String finalFxmlPath = fxmlPath;
                FXMLLoader loader = new FXMLLoader(getClass().getResource(finalFxmlPath));
                if ("admin".equals(finalRole)) {
                    loader.setControllerFactory(param -> new com.doanjava.gradehub.controller.AdminDashboardController());
                } else if ("teacher".equals(finalRole)) {
                    loader.setControllerFactory(param -> new com.doanjava.gradehub.controller.TeacherDashboardController());
                } // student uses default
                Parent dashboardRoot = loader.load();
                Scene dashboardScene = new Scene(dashboardRoot);
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(dashboardScene);
                stage.setTitle("Dashboard");
            } catch (Exception e) {
                errorLabel.setText("Lỗi khi chuyển sang dashboard.");
                errorLabel.setVisible(true);
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Sai email hoặc mật khẩu.");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void initialize() {
        // Hide error when user starts typing
        emailField.textProperty().addListener((obs, oldVal, newVal) -> {
            errorLabel.setVisible(false);
            errorLabel.setText("");
        });
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            errorLabel.setVisible(false);
            errorLabel.setText("");
        });
    }
}

