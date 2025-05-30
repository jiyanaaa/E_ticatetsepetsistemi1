package com.eticaretsepetsistemi.e_ticaret.Services;

import java.sql.*;

public class SQL_Service {


            // kendi şifreni yaz

    private static final String DB_URL = "jdbc:sqlserver://LAPTOP-0Q2FGCL9;databaseName=E_ticaretSepetSistemi;integratedSecurity=True;encrypt=True;trustServerCertificate=True";

    private Connection conn;

    public SQL_Service() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Bağlantı başarılı!");
        } catch (SQLException e) {
            System.err.println("Bağlantı başarısız: " + e.getMessage());
        }
    }

    // INSERT işlemi
    public boolean insert(String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("INSERT hatası: " + e.getMessage());
            return false;
        }
    }

    // UPDATE işlemi
    public boolean update(String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("UPDATE hatası: " + e.getMessage());
            return false;
        }
    }

    // DELETE işlemi
    public boolean delete(String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("DELETE hatası: " + e.getMessage());
            return false;
        }
    }

    // SELECT (veri okuma işlemi için örnek)
    public ResultSet select(String sql) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("SELECT hatası: " + e.getMessage());
            return null;
        }
    }

    // Bağlantıyı kapatma
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Bağlantı kapatıldı.");
            }
        } catch (SQLException e) {
            System.out.println("Bağlantı kapatma hatası: " + e.getMessage());
        }
    }
}
