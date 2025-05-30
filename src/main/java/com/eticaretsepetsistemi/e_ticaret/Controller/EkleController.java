package com.eticaretsepetsistemi.e_ticaret.Controller;

import com.eticaretsepetsistemi.e_ticaret.Services.SQL_Service;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EkleController {

    @FXML
    private TextField urunAdiField;

    @FXML
    private TextField fiyatField;

    @FXML
    private TextField stokField;

    @FXML
    private TextArea aciklamaField;

    @FXML
    private ComboBox<String> kategoriComboBox;

    @FXML
    private Button ekleBtn;

    @FXML
    private Button temizleBtn;
    SQL_Service db;

    // Sayfa ilk yüklendiğinde çalışır
    @FXML
    public void initialize() {
        db = new SQL_Service();
        kategoriComboBox.getItems().addAll("Elektronik", "Giyim", "Kitap", "Kozmetik", "Ev & Yaşam");

        ekleBtn.setOnAction(e -> urunEkle());
        temizleBtn.setOnAction(e -> formuTemizle());
    }

    private void urunEkle() {
        String ad = urunAdiField.getText();
        String fiyatStr = fiyatField.getText();
        String stokStr = stokField.getText();
        String aciklama = aciklamaField.getText();

        if (ad.isEmpty() || fiyatStr.isEmpty() || stokStr.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Lütfen tüm zorunlu alanları doldurunuz.");
            return;
        }

        try {
            double fiyat = Double.parseDouble(fiyatStr);
            int stok = Integer.parseInt(stokStr);

            // SQL sorgusu (kategori kaldırıldı)
            String fiyatStrFormatted = String.format("%.2f", fiyat).replace(',', '.');

            String sql = String.format(
                    "INSERT INTO Urunler (UrunAdi, Aciklama, Fiyat, Stok, Kategori) VALUES ('%s', '%s', %s, %d, '%s')",
                    ad.replace("'", "''"),
                    aciklama.replace("'", "''"),
                    fiyatStrFormatted,
                    stok,
                    kategoriComboBox.getValue().replace("'", "''")
            );


            System.out.println(sql);


            boolean basarili = db.insert(sql);

            if (basarili) {
                showAlert(Alert.AlertType.INFORMATION, "Ürün başarıyla eklendi!");
                formuTemizle();
            } else {
                showAlert(Alert.AlertType.ERROR, "Ürün eklenirken hata oluştu.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Fiyat ve Stok değerleri geçerli sayılar olmalıdır.");
        }
    }

    private void formuTemizle() {
        urunAdiField.clear();
        fiyatField.clear();
        stokField.clear();
        aciklamaField.clear();
        kategoriComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Bilgi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
