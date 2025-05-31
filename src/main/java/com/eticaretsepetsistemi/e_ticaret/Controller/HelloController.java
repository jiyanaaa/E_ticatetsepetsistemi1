package com.eticaretsepetsistemi.e_ticaret.Controller;

import com.eticaretsepetsistemi.e_ticaret.HelloApplication;
import com.eticaretsepetsistemi.e_ticaret.Model.CurrentUser;
import com.eticaretsepetsistemi.e_ticaret.Model.Kullanici;
import com.eticaretsepetsistemi.e_ticaret.Model.Sepet;
import com.eticaretsepetsistemi.e_ticaret.Model.Urunler;
import com.eticaretsepetsistemi.e_ticaret.Services.SQL_Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private VBox urunDetaylari;
    @FXML
    private Label seciliUrunAdi;
    @FXML
    private Label seciliUrunFiyat;
    @FXML
    private Label seciliUrunStok;
    @FXML
    private Label seciliUrunKategori;
    @FXML
    private TableView<Urunler> urunTablosu;

    @FXML
    private TableColumn<Urunler, String> urunAdiColumn;

    @FXML
    private TableColumn<Urunler, Double> fiyatColumn;

    @FXML
    private TableColumn<Urunler, Integer> stokColumn;

    @FXML
    private TableColumn<Urunler, String> kategoriColumn;

    private ObservableList<Urunler> urunListesi;

    @FXML
    public void initialize() {
        if(CurrentUser.isLoggedIn()) {
            Kullanici user = CurrentUser.getUser();
            System.out.println("Hoş Geldiniz, " + user.getAd() + " " + user.getSoyad());
            System.out.println(user.getEposta());
        } else {
            // Kullanıcı giriş yapmamışsa giriş sayfasına yönlendir
            try {
                HelloApplication.setRoot("login-view");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Sütunları Urunler modelinin alanlarına bağla
        urunAdiColumn.setCellValueFactory(new PropertyValueFactory<>("urunAdi"));
        fiyatColumn.setCellValueFactory(new PropertyValueFactory<>("fiyat"));
        stokColumn.setCellValueFactory(new PropertyValueFactory<>("stok"));
        kategoriColumn.setCellValueFactory(new PropertyValueFactory<>("kategori"));

        urunListesi = FXCollections.observableArrayList();
        urunTablosu.setItems(urunListesi);
        urunTablosu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                urunDetaylari.setVisible(true);
                seciliUrunAdi.setText(newSelection.getUrunAdi());
                seciliUrunFiyat.setText(String.format("%.2f TL", newSelection.getFiyat()));
                seciliUrunStok.setText(String.valueOf(newSelection.getStok()));
                seciliUrunKategori.setText(newSelection.getKategori());
            } else {
                urunDetaylari.setVisible(false);
            }
        });

        urunleriYukle();
    }
    @FXML
    private void Septegit() throws IOException {
        HelloApplication.setRoot("Sepet-view");
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void sepeteEkle() {
        if(!CurrentUser.isLoggedIn()) {
            showAlert("Hata", "Sepete ürün eklemek için giriş yapmalısınız!");
            return;
        }

        Urunler seciliUrun = urunTablosu.getSelectionModel().getSelectedItem();
        if(seciliUrun != null) {
            try {


                String sql = String.format(
                        "INSERT INTO Sepet (KullaniciID, UrunID, Miktar) VALUES (%d, %d, %d)",
                        CurrentUser.getUserId(),
                        seciliUrun.getUrunID(),
                        1
                );

                SQL_Service db = new SQL_Service();
                boolean success = db.insert(sql);

                if(success) {
                    showAlert("Başarılı", "Ürün sepete eklendi!");
                    //refreshSepet();
                } else {
                    showAlert("Hata", "Ürün sepete eklenemedi!");
                }
            } catch(NumberFormatException e) {
                showAlert("Hata", "Geçerli bir miktar giriniz!");
            }
        } else {
            showAlert("Uyarı", "Lütfen bir ürün seçiniz!");
        }
    }

    private void urunleriYukle() {
        SQL_Service sqlService = new SQL_Service();

        String sql = "SELECT UrunID, UrunAdi, Aciklama, Fiyat, Stok, Kategori FROM Urunler";

        try (ResultSet rs = sqlService.select(sql)) {
            urunListesi.clear();

            while (rs != null && rs.next()) {
                int id = rs.getInt("UrunID");
                String adi = rs.getString("UrunAdi");
                String aciklama = rs.getString("Aciklama");
                double fiyat = rs.getDouble("Fiyat");
                int stok = rs.getInt("Stok");
                String kategori = rs.getString("Kategori");

                Urunler urun = new Urunler(id, adi, aciklama, fiyat, stok, kategori);
                urunListesi.add(urun);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlService.closeConnection();
        }
    }

    @FXML
    private void Urun_ekle() throws IOException {
        HelloApplication.setRoot("ekle_view");


    }

    // Sepet.java işlemleri için metodlar


    @FXML
    private void sepetiOnayla() {
        // Sepet.java onaylama işlemi
    }

    @FXML
    private void sepetiTemizle() {
        // Sepeti boşaltma
    }
}