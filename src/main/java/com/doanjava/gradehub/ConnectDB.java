package com.doanjava.gradehub;
import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/gradehubdb";
    private static final String USER = "root";
    private static final String PASSWORD = "tr@m2004";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("Kết nối thành công!");
            } else {
                System.out.println("Kết nối thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
