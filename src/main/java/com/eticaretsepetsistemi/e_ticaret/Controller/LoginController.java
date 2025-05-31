package com.eticaretsepetsistemi.e_ticaret.Controller;

import com.eticaretsepetsistemi.e_ticaret.HelloApplication;
import com.eticaretsepetsistemi.e_ticaret.Model.CurrentUser;
import com.eticaretsepetsistemi.e_ticaret.Model.Kullanici;
import com.eticaretsepetsistemi.e_ticaret.Services.SQL_Service;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField signInNameField;
    @FXML
    private TextField loginUsernameField;

    @FXML
    private TextField signInSurnameField;

    @FXML
    private TextField signInUsernameField;  // burada e-posta

    @FXML
    private PasswordField signInPasswordField;
    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private PasswordField signInPasswordRepeatField;
    @FXML
    private void kayitOl() {
        String ad = signInNameField.getText().trim();
        String soyad = signInSurnameField.getText().trim();
        String email = signInUsernameField.getText().trim();
        String sifre = signInPasswordField.getText();
        String sifreTekrar = signInPasswordRepeatField.getText();
        String role = "musteri";

        if(ad.isEmpty() || soyad.isEmpty() || email.isEmpty() || sifre.isEmpty() || sifreTekrar.isEmpty()) {
            System.out.println("Lütfen tüm alanları doldurun.");
            return;
        }
        if(!sifre.equals(sifreTekrar)) {
            System.out.println("Şifreler eşleşmiyor!");
            return;
        }

        // SQL sorgusu (basit, direkt string kullanımı - dikkat SQL Injection riski var!)
        String sql = String.format("INSERT INTO Kullanicilar (ad, soyad, Eposta, sifre, user_role) VALUES ('%s', '%s', '%s', '%s', '%s')",
                ad.replace("'", "''"),
                soyad.replace("'", "''"),
                email.replace("'", "''"),
                sifre.replace("'", "''"),
                role);
        SQL_Service db = new SQL_Service();
        boolean sonuc = db.insert(sql);
        if (sonuc) {
            System.out.println("Kayıt başarılı!");
            signInNameField.clear();
            signInSurnameField.clear();
            signInUsernameField.clear();
            signInPasswordField.clear();
            signInPasswordRepeatField.clear();
        } else {
            System.out.println("Kayıt başarısız!");
        }
    }private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void girisYap() {
        String email = loginUsernameField.getText().trim();
        String sifre = loginPasswordField.getText();

        if(email.isEmpty() || sifre.isEmpty()) {
            showAlert("Hata", "Lütfen e-posta ve şifre alanlarını doldurun.");
            return;
        }

        // SQL injection'a karşı korumalı sorgu
        String sql = String.format("SELECT * FROM Kullanicilar WHERE Ad='%s' AND sifre='%s'",
                email.replace("'", "''"),
                sifre.replace("'", "''"));

        SQL_Service db = new SQL_Service();
        ResultSet rs = db.select(sql);

        try {
            if(rs != null && rs.next()) {
                // Kullanıcı bilgilerini al ve CurrentUser'a kaydet
                Kullanici user = new Kullanici();
                user.setKullaniciID(rs.getInt("kullaniciID"));
                user.setAd(rs.getString("ad"));
                user.setSoyad(rs.getString("soyad"));
                user.setEposta(rs.getString("eposta"));
                // Şifre veritabanından okunuyor ama modelde saklanması güvenlik açığı olabilir

                CurrentUser.setUser(user);

                System.out.println("Giriş başarılı! Hoş geldiniz: " + CurrentUser.getFullName());
                HelloApplication.setRoot("hello-view");
            } else {
                showAlert("Hata", "E-posta veya şifre hatalı!");
            }
        } catch (SQLException e) {
            showAlert("Hata", "Giriş sırasında bir hata oluştu: " + e.getMessage());
        } catch (IOException e) {
            showAlert("Hata", "Sayfa yüklenirken bir hata oluştu.");
        }
    }




}
