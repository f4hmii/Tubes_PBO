package com.movr.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/pbo";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Ganti sesuai password MySQL kamu

    public static Connection getConnection() throws SQLException {
        //
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}