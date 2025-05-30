package com.eticaretsepetsistemi.e_ticaret.Controller;

import com.eticaretsepetsistemi.e_ticaret.HelloApplication;
import com.eticaretsepetsistemi.e_ticaret.Model.Sepet;
import com.eticaretsepetsistemi.e_ticaret.Model.Urunler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.io.IOException;

public class HelloController {
    @FXML private TableView<Urunler> urunTablosu;
    @FXML private ListView<Sepet> sepetListesi;
    @FXML private Label toplamUrunLabel;
    @FXML private Label araToplamLabel;
    @FXML private Label kdvLabel;
    @FXML private Label genelToplamLabel;

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