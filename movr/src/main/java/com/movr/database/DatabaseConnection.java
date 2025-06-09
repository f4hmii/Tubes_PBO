package com.movr.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // URL menunjuk ke server database (localhost), port (3306), dan nama database (praktikumpbo)
    private static final String URL = "jdbc:mysql://localhost:3306/pbo";
    
    // Username default untuk XAMPP biasanya "root"
    private static final String USER = "root";
    
    // Password default untuk XAMPP biasanya kosong, ganti jika Anda punya password
    private static final String PASSWORD = "";

    /**
     * Metode untuk mendapatkan koneksi ke database.
     * @return Objek Connection yang siap digunakan.
     * @throws SQLException jika koneksi gagal.
     */
    public static Connection getConnection() throws SQLException {
        //
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}