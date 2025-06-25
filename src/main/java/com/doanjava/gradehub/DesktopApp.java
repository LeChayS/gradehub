package com.doanjava.gradehub;

import java.io.IOException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DesktopApp extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(com.doanjava.gradehub.MySpringBootApplication.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //view đăng nhập
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login-view.fxml"));

        //view giáo viên
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teacher/dashboard-view.fxml"));

        //view sinh viên
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student/dashboard-view.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        // stage.setMaximized(true);
        stage.setTitle("GradeHub - Đăng nhập");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        context.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}