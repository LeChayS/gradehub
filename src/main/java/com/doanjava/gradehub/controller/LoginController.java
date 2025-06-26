package com.doanjava.gradehub.controller;

import com.doanjava.gradehub.dto.LoginRequest;
import com.doanjava.gradehub.dto.LoginResponse;
import com.doanjava.gradehub.entity.NguoiDung;
import com.doanjava.gradehub.service.HttpClientService;
import javafx.application.Platform;
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
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showError("Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        // Disable login button and show loading
        errorLabel.setVisible(false);

        // Create login request
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Create and configure login service
        HttpClientService.LoginService loginService = new HttpClientService.LoginService();
        loginService.setLoginRequest(loginRequest);

        // Handle success
        loginService.setOnSucceeded(event -> {
            try {
                LoginResponse loginResponse = loginService.getValue().getData();
                // Lưu token vào HttpClientService
                if (loginResponse != null && loginResponse.token() != null) {
                    HttpClientService.setJwtToken(loginResponse.token());
                    System.out.println("Token set: " + loginResponse.token());
                    System.out.println("HttpClientService.getJwtToken(): " + HttpClientService.getJwtToken());
                } else {
                    System.out.println("LoginResponse hoặc token null!");
                }
                navigateToDashboard(loginResponse);
            } catch (Exception e) {
                showError("Lỗi khi xử lý phản hồi từ server.");
                e.printStackTrace();
            }
        });

        // Handle failure
        loginService.setOnFailed(event -> {
            Throwable exception = loginService.getException();
            if (exception != null) {
                showError(exception.getMessage());
            } else {
                showError("Lỗi kết nối đến server.");
            }
        });

        // Start the service
        loginService.start();
    }

    private void navigateToDashboard(LoginResponse loginResponse) {
        Platform.runLater(() -> {
            try {
                String fxmlPath = null;
                String role = loginResponse.vaiTro().name();

                switch (loginResponse.vaiTro()) {
                    case quan_tri:
                        fxmlPath = "/view/admin/dashboard-view.fxml";
                        break;
                    case giang_vien:
                        fxmlPath = "/view/teacher/dashboard-view.fxml";
                        break;
                    case sinh_vien:
                        fxmlPath = "/view/student/dashboard-view.fxml";
                        break;
                }

                if (fxmlPath != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

                    // Set controller based on role
                    if (role.equals("quan_tri")) {
                        loader.setControllerFactory(param -> new com.doanjava.gradehub.controller.AdminDashboardController());
                    } else if (role.equals("giang_vien")) {
                        loader.setControllerFactory(param -> new com.doanjava.gradehub.controller.TeacherDashboardController());
                    }
                    // student uses default controller

                    Parent dashboardRoot = loader.load();
                    Scene dashboardScene = new Scene(dashboardRoot);
                    Stage stage = (Stage) emailField.getScene().getWindow();
                    stage.setScene(dashboardScene);
                    stage.setTitle("Dashboard - " + loginResponse.hoTen());
                } else {
                    showError("Vai trò không được hỗ trợ.");
                }
            } catch (Exception e) {
                showError("Lỗi khi chuyển sang dashboard.");
                e.printStackTrace();
            }
        });
    }

    private void showError(String message) {
        Platform.runLater(() -> {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        });
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

        emailField.setOnAction(event -> handleLogin());
        passwordField.setOnAction(event -> handleLogin());
    }
}
