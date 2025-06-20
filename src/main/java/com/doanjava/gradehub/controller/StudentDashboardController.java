package com.doanjava.gradehub.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentDashboardController {
    @FXML
    private StackPane mainContentPane;
    @FXML
    private VBox sidebar;

    @FXML
    private HBox dashboardMenu;
    @FXML
    private HBox resultMenu;
    @FXML
    private HBox semesterAvgMenu;
    @FXML
    private HBox cumulativeAvgMenu;
    @FXML
    private HBox changePasswordMenu;
    @FXML
    private HBox logoutMenu;
    @FXML
    private HBox settingMenu;
    @FXML
    private HBox notificationMenu;

    @FXML
    private Button changePasswordBtn;
    @FXML
    private Button logoutBtn;

    private List<HBox> menuItems;
    private String role = "student"; // default

    @FXML
    private void initialize() {
        // Collect menu items for easy style management
        menuItems = Arrays.asList(dashboardMenu, resultMenu, semesterAvgMenu, cumulativeAvgMenu, notificationMenu, settingMenu, changePasswordMenu);
        // Load dashboard by default
        setSelectedMenu(dashboardMenu);
        loadContent("/view/" + role + "/dashboard-content-view.fxml");
    }

    @FXML
    private void handleDashboardMenu(MouseEvent event) {
        setSelectedMenu(dashboardMenu);
        loadContent("/view/" + role + "/dashboard-content-view.fxml");
    }

    @FXML
    private void handleResultMenu(MouseEvent event) {
        setSelectedMenu(resultMenu);
        loadContent("/view/" + role + "/result-content-view.fxml");
    }

    @FXML
    private void handleSemesterAvgMenu(MouseEvent event) {
        setSelectedMenu(semesterAvgMenu);
        loadContent("/view/" + role + "/semester-avg-content-view.fxml");
    }

    @FXML
    private void handleCumulativeAvgMenu(MouseEvent event) {
        setSelectedMenu(cumulativeAvgMenu);
        loadContent("/view/" + role + "/cumulative-avg-content-view.fxml");
    }

    @FXML
    private void handleNotificationMenu(MouseEvent event) {
        setSelectedMenu(notificationMenu);
        loadContent("/view/notification-content-view.fxml");
    }

    @FXML
    private void handleChangePasswordMenu(MouseEvent event) {
        setSelectedMenu(changePasswordMenu);
        loadContent("/view/change-password-content-view.fxml");
    }

    @FXML
    private void handleLogoutMenu(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login-view.fxml"));
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);
            Stage stage = (Stage) sidebar.getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("Đăng nhập");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettingMenu(MouseEvent event) {
        setSelectedMenu(settingMenu);
        loadContent("/view/setting-content-view.fxml");
    }

    private void setSelectedMenu(HBox selected) {
        for (HBox menu : menuItems) {
            menu.getStyleClass().remove("selected");
        }
        if (!selected.getStyleClass().contains("selected")) {
            selected.getStyleClass().add("selected");
        }
    }

    private void loadContent(String fxmlPath) {
        try {
            Node content = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainContentPane.getChildren().setAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRole(String role) { this.role = role; }
}