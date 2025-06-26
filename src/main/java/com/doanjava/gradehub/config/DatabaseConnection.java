package com.doanjava.gradehub.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/gradehub";
        String user = "root";
        String password = "bephuc123";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối thành công!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");
            e.printStackTrace();
            return null;
        }
    }
}
