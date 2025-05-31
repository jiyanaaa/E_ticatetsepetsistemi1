package com.eticaretsepetsistemi.e_ticaret.Controller;

import com.eticaretsepetsistemi.e_ticaret.Model.CurrentUser;
import com.eticaretsepetsistemi.e_ticaret.Model.Kullanici;
import com.eticaretsepetsistemi.e_ticaret.Model.Sepet;
import com.eticaretsepetsistemi.e_ticaret.Model.Urunler;
import com.eticaretsepetsistemi.e_ticaret.Services.SQL_Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SepetController {

    @FXML private TableView<Sepet> sepetTableView;
    @FXML private Label araToplamLabel;
    @FXML private Label kdvLabel;
    @FXML private Label genelToplamLabel;
    @FXML private VBox bosSepetVBox;
    @FXML private Button siparisiTamamlaButton;
    @FXML private TableColumn<Sepet, String> urunAdiColumn;
    @FXML private TableColumn<Sepet, Double> fiyatColumn;
    @FXML private TableColumn<Sepet, Integer> miktarColumn;
    @FXML private TableColumn<Sepet, Double> toplamColumn;


    @FXML
    public void initialize() {
        // Kullanıcı giriş yapmış mı kontrolü
        if (!CurrentUser.isLoggedIn()) {
            showAlert("Hata", "Sepetinizi görüntülemek için giriş yapmalısınız!");
            return;
        }


        // Sepet verilerini yükle
        loadSepetData();
        urunAdiColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUrun().getUrunAdi())
        );

        fiyatColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getUrun().getFiyat()).asObject()
        );

        miktarColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getMiktar()).asObject()
        );

        toplamColumn.setCellValueFactory(cellData -> {
            double fiyat = cellData.getValue().getUrun().getFiyat();
            int miktar = cellData.getValue().getMiktar();
            double toplam = fiyat * miktar;
            return new javafx.beans.property.SimpleDoubleProperty(toplam).asObject();
        });
    }

    private void loadSepetData() {
        int kullaniciID = CurrentUser.getUserId();
        SQL_Service sqlService = new SQL_Service();

        ObservableList<Sepet> sepetList = FXCollections.observableArrayList();

        // SQL Server uyumlu sorgu
        String sql = "SELECT s.SepetID AS sepet_id, s.Miktar AS miktar, " +
                "u.UrunID AS urun_id, u.UrunAdi AS urun_adi, u.Fiyat AS urun_fiyat " +
                "FROM [E_ticaretSepetSistemi].[dbo].[Sepet] s " +
                "JOIN [E_ticaretSepetSistemi].[dbo].[Urunler] u ON s.UrunID = u.UrunID " +
                "WHERE s.KullaniciID = " + kullaniciID;
        System.out.println(sql);

        ResultSet rs = sqlService.select(sql);

        try {
            while (rs != null && rs.next()) {
                int sepetID = rs.getInt("sepet_id");
                int miktar = rs.getInt("miktar");

                // Ürün bilgileri
                int urunID = rs.getInt("urun_id");
                String urunAdi = rs.getString("urun_adi");
                double urunFiyat = rs.getDouble("urun_fiyat");

                // Urunler nesnesi oluştur
                Urunler urun = new Urunler();
                urun.setUrunID(urunID);
                urun.setAd(urunAdi);
                urun.setFiyat(urunFiyat);

                // Kullanici nesnesi
                Kullanici kullanici = new Kullanici();
                kullanici.setKullaniciID(kullaniciID);

                // Sepet nesnesi
                Sepet sepet = new Sepet();
                sepet.setSepetID(sepetID);
                sepet.setKullanici(kullanici);
                sepet.setUrun(urun);
                sepet.setMiktar(miktar);

                sepetList.add(sepet);
            }
        } catch (SQLException e) {
            System.out.println("Sepet verileri okunurken hata: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println("ResultSet kapatılırken hata: " + e.getMessage());
            }
        }

        // TableView'a veriyi aktar
        sepetTableView.setItems(sepetList);

        if (sepetList.isEmpty()) {
            bosSepetVBox.setVisible(true);
            sepetTableView.setVisible(false);
        } else {
            bosSepetVBox.setVisible(false);
            sepetTableView.setVisible(true);
            updateToplamlar(); // Fiyatları güncelle
        }
    }



    private void updateToplamlar() {
        SQL_Service sqlService = new SQL_Service();
        double araToplam = 0.0;

        for (Sepet item : sepetTableView.getItems()) {
            // Urun nesnesi üzerinden ürün ID'sini al
            Urunler urun = item.getUrun();
            int miktar = item.getMiktar();

            if (urun == null) continue; // urun null ise geç

            int urunId = urun.getUrunID(); // doğru şekilde ID alındı

            // SQL sorgusu: ürünün fiyatını al
            String sql = "SELECT fiyat FROM urunler WHERE UrunID = " + urunId;

            ResultSet rs = sqlService.select(sql);

            try {
                if (rs != null && rs.next()) {
                    double fiyat = rs.getDouble("fiyat");
                    araToplam += fiyat * miktar;
                }
            } catch (SQLException e) {
                System.out.println("Fiyat okuma hatası: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) rs.close(); // resultSet kapatılıyor
                } catch (SQLException e) {
                    System.out.println("ResultSet kapatma hatası: " + e.getMessage());
                }
            }
        }

        double kdv = araToplam * 0.18;
        double genelToplam = araToplam + kdv;

        araToplamLabel.setText(String.format("%.2f TL", araToplam));
        kdvLabel.setText(String.format("%.2f TL", kdv));
        genelToplamLabel.setText(String.format("%.2f TL", genelToplam));
    }



    @FXML
    private void handleArttir() {
        Sepet selected = sepetTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int yeniMiktar = selected.getMiktar() + 1;
            selected.setMiktar(yeniMiktar);
            sepetTableView.refresh();
            updateToplamlar();

            // Veritabanını güncelle
            SQL_Service sqlService = new SQL_Service();
            String updateSql = "UPDATE Sepet SET Miktar = " + yeniMiktar +
                    " WHERE SepetID = " + selected.getSepetID();

            sqlService.update(updateSql);
        }
    }


    @FXML
    private void handleAzalt() {
        Sepet selected = sepetTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int yeniMiktar = selected.getMiktar() - 1;
            selected.setMiktar(yeniMiktar);
            sepetTableView.refresh();
            updateToplamlar();

            // Veritabanını güncelle
            SQL_Service sqlService = new SQL_Service();
            String updateSql = "UPDATE Sepet SET Miktar = " + yeniMiktar +
                    " WHERE SepetID = " + selected.getSepetID();

            sqlService.update(updateSql);
        }
    }

    @FXML
    private void handleSil() {
        Sepet selected = sepetTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            sepetTableView.getItems().remove(selected);
            updateToplamlar();
            // Veritabanından sil
            if (sepetTableView.getItems().isEmpty()) {
                bosSepetVBox.setVisible(true);
                sepetTableView.setVisible(false);
            }
        }
    }

    @FXML
    private void handleSiparisiTamamla() {
        SQL_Service sqlService = new SQL_Service();
        if (sepetTableView.getItems().isEmpty()) {
            showAlert("Uyarı", "Sepetiniz boş!");
            return;
        }

        // Toplam tutarı hesapla
        double toplamTutar = 0;
        for (Sepet urun : sepetTableView.getItems()) {
            toplamTutar += urun.getUrun().getFiyat() * urun.getMiktar();
        }

        try {
            // Tarihi al
            String siparisTarihi = new java.sql.Timestamp(System.currentTimeMillis()).toString();

            // 1. Siparisler tablosuna ekle
            String siparisSQL = "INSERT INTO Siparisler (KullaniciID, SiparisTarihi, ToplamTutar) " +
                    "VALUES (" + CurrentUser.getUserId() + ", '" + siparisTarihi + "', " + toplamTutar + ")";
            sqlService.insert(siparisSQL);

            // 2. Eklenen siparişin ID'sini al (en son eklenen satır)
            String getLastIDSQL = "SELECT MAX(SiparisID) AS SonID FROM Siparisler";
            ResultSet rs = sqlService.select(getLastIDSQL);
            int siparisID = -1;
            if (rs.next()) {
                siparisID = rs.getInt("SonID");
            }

            // 3. Sipariş detaylarını ekle
            for (Sepet urun : sepetTableView.getItems()) {
                String detaySQL = "INSERT INTO SiparisDetaylari (SiparisID, UrunID, Miktar, Fiyat) " +
                        "VALUES (" + siparisID + ", " + urun.getUrun().getUrunID() + ", " + urun.getMiktar() + ", " + urun.getUrun().getFiyat() + ")";
                sqlService.insert(detaySQL);
            }
            String sepetSilSQL = "DELETE FROM Sepet WHERE KullaniciID = " + CurrentUser.getUserId();
            sqlService.delete(sepetSilSQL);

            // Sepeti temizle
            sepetTableView.getItems().clear();
            updateToplamlar();
            showAlert("Başarılı", "Sipariş başarıyla tamamlandı!");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Hata", "Sipariş sırasında hata oluştu!");
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}