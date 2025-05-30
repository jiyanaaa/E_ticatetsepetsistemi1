package com.eticaretsepetsistemi.e_ticaret.Controller;

import com.eticaretsepetsistemi.e_ticaret.HelloApplication;
import com.eticaretsepetsistemi.e_ticaret.Model.Sepet;
import com.eticaretsepetsistemi.e_ticaret.Model.Urunler;
import com.eticaretsepetsistemi.e_ticaret.Services.SQL_Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
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
        // Sütunları Urunler modelinin alanlarına bağla
        urunAdiColumn.setCellValueFactory(new PropertyValueFactory<>("urunAdi"));
        fiyatColumn.setCellValueFactory(new PropertyValueFactory<>("fiyat"));
        stokColumn.setCellValueFactory(new PropertyValueFactory<>("stok"));
        kategoriColumn.setCellValueFactory(new PropertyValueFactory<>("kategori"));

        urunListesi = FXCollections.observableArrayList();
        urunTablosu.setItems(urunListesi);

        urunleriYukle();
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
    private void sepeteEkle() {
        // Seçili ürünü sepete ekleme
    }

    @FXML
    private void sepetiOnayla() {
        // Sepet.java onaylama işlemi
    }

    @FXML
    private void sepetiTemizle() {
        // Sepeti boşaltma
    }
}