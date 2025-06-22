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

public class AdminDashboardController {
    @FXML
    private StackPane mainContentPane;
    @FXML
    private VBox sidebar;

    @FXML
    private HBox dashboardMenu;
    @FXML
    private HBox changePasswordMenu;
    @FXML
    private HBox logoutMenu;
    @FXML
    private HBox settingMenu;
    @FXML
    private HBox notificationMenu;
    @FXML
    private HBox accountMenu;
    @FXML
    private HBox subjectMenu;
    @FXML
    private HBox scoreMenu;
    @FXML
    private HBox configMenu;

    @FXML
    private Button changePasswordBtn;
    @FXML
    private Button logoutBtn;

    private List<HBox> menuItems;

    @FXML
    private void initialize() {
        menuItems = Arrays.asList(dashboardMenu, accountMenu, subjectMenu, scoreMenu, configMenu, notificationMenu, settingMenu, changePasswordMenu);
        setSelectedMenu(dashboardMenu);
        loadContent("/view/admin/dashboard-content-view.fxml");
    }

    @FXML
    private void handleAccountMenu(MouseEvent event) {
        setSelectedMenu(accountMenu);
        loadContent("/view/admin/account-content-view.fxml");
    }

    @FXML
    private void handleSubjectMenu(MouseEvent event) {
        setSelectedMenu(subjectMenu);
        loadContent("/view/admin/subject-content-view.fxml");
    }

    @FXML
    private void handleScoreMenu(MouseEvent event) {
        setSelectedMenu(scoreMenu);
        loadContent("/view/admin/score-content-view.fxml");
    }

    @FXML
    private void handleConfigMenu(MouseEvent event) {
        setSelectedMenu(configMenu);
        loadContent("/view/admin/config-content-view.fxml");
    }

    @FXML
    private void handleDashboardMenu(MouseEvent event) {
        setSelectedMenu(dashboardMenu);
        loadContent("/view/admin/dashboard-content-view.fxml");
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
}