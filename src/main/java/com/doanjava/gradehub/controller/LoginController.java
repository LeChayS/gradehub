package com.doanjava.gradehub.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Kiểm tra đăng nhập mẫu
        if ("admin@example.com".equals(email) && "123456".equals(password)) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Sai email hoặc mật khẩu.");
        }
    }
}

