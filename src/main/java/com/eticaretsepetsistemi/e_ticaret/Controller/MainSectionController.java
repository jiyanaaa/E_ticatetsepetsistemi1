package com.eticaretsepetsistemi.e_ticaret.Controller;

import com.eticaretsepetsistemi.e_ticaret.HelloApplication;
import com.eticaretsepetsistemi.e_ticaret.Services.SQL_Service;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import com.eticaretsepetsistemi.e_ticaret.Model.Urunler;
import javafx.stage.Stage;

public class MainSectionController {

    @FXML
    private ListView<Urunler> urunListView;

    @FXML
    private TextField searchField;

    // Veritabanı bağlantısı için gerekli bilgiler
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eticaret_db";
    private static final String DB_USER = "kullanici_adi";
    private static final String DB_PASS = "sifre";

    @FXML
    public void initialize() {
        // Ürün listesini yükle
        urunleriVeritabanindanYukle();

        // ListView için özel hücre tasarımı
        urunListView.setCellFactory(param -> new ListCell<Urunler>() {
            @Override
            protected void updateItem(Urunler urun, boolean empty) {
                super.updateItem(urun, empty);

                if (empty || urun == null) {
                    setGraphic(null);
                } else {
                    // Özel ürün görünümü oluştur
                    VBox urunKutusu = new VBox(10);
                    urunKutusu.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5;");

                    // Ürün resmi (gerçek uygulamada veritabanından URL alınacak)



                    // Ürün bilgileri
                    Label adLabel = new Label(urun.getUrunAdi());
                    adLabel.setStyle("-fx-font-weight: bold;");

                    Label fiyatLabel = new Label(String.format("₺%.2f", urun.getFiyat()));
                    fiyatLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");

                    Label stokLabel = new Label("Stok: " + urun.getStok());
                    stokLabel.setStyle("-fx-text-fill: #666;");

                    Button ekleButonu = new Button("Sepete Ekle");
                    ekleButonu.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
                    ekleButonu.setOnAction(event -> {
                        // Sepete ekleme işlemi
                        System.out.println(urun.getUrunAdi() + " sepete eklendi!");
                    });

                    urunKutusu.getChildren().addAll(adLabel, fiyatLabel, stokLabel, ekleButonu);
                    setGraphic(urunKutusu);
                }
            }
        });
    }

    private void urunleriVeritabanindanYukle() {
        SQL_Service dbService = new SQL_Service();
        try {
            // SQLService üzerinden verileri çek
            ResultSet resultSet = dbService.select("SELECT TOP 5 * FROM Urunler ORDER BY UrunID");

            ObservableList<Urunler> urunler = FXCollections.observableArrayList();

            while (resultSet.next()) {
                Urunler urun = new Urunler(
                        resultSet.getInt("urunID"),
                        resultSet.getString("urunAdi"),
                        resultSet.getString("aciklama"),
                        resultSet.getDouble("fiyat"),
                        resultSet.getInt("stok"),
                        resultSet.getString("kategori")
                );
                urunler.add(urun);
            }

            urunListView.setItems(urunler);

            // ResultSet'i kapat (eğer SQLService kapatmıyorsa)
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Veritabanı hatası: " + e.getMessage());

            // Hata durumunda kullanıcıya bilgi ver
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Veritabanı Hatası");
            alert.setHeaderText("Ürünler yüklenirken bir hata oluştu");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSearch() {
        String arananKelime = searchField.getText().trim().toLowerCase();

        if (arananKelime.isEmpty()) {
            urunleriVeritabanindanYukle();
        } else {
            // Arama işlevselliği buraya eklenecek
            System.out.println("Aranan kelime: " + arananKelime);
        }
    }
    @FXML
    private void giris() throws IOException {
        Stage stage = (Stage) searchField.getScene().getWindow();
        stage.close();
        HelloApplication.setRoot("log_in_view");
    }
}