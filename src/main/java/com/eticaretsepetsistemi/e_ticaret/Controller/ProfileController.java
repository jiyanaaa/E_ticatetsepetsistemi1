package com.eticaretsepetsistemi.e_ticaret.Controller;

import com.eticaretsepetsistemi.e_ticaret.Model.CurrentUser;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProfileController {
    @FXML
    private TextField adField;
    @FXML private TextField soyadField;
    @FXML private TextField epostaField;
    @FXML private PasswordField sifreField;
    @FXML private TextField rolField;

    public void initialize() {
        // Örnek: Kullanıcı bilgilerini doldur
        adField.setText(CurrentUser.getUser().getAd());
        soyadField.setText(CurrentUser.getUser().getSoyad());
        epostaField.setText(CurrentUser.getUser().getEposta());



        // Gerçek uygulamada SQL'den çek ve göster
    }

    @FXML
    private void handleGeriDon() {
        // Ana sayfaya geri dönüş kodları
        Stage stage = (Stage) adField.getScene().getWindow();
        stage.close();
    }

}
