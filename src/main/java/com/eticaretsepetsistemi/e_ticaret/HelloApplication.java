package com.eticaretsepetsistemi.e_ticaret;

import com.eticaretsepetsistemi.e_ticaret.Services.SQL_Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("log_in_view"));
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        // FXML yükleniyor
        Parent rootNode = loadFXML(fxml);

        // Yeni bir StackPane oluştur
        StackPane root = new StackPane(rootNode);

        // Yeni bir Stage oluştur (Açılan pencere)
        Stage newStage = new Stage();
        newStage.setTitle(fxml);
        newStage.setScene(new Scene(root)); // Yeni sahneyi oluştur ve ata

        // Stage'i ekrana ortala ve göster
        newStage.centerOnScreen();
        newStage.show();
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/eticaretsepetsistemi/e_ticaret/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        SQL_Service sql = new SQL_Service();
        launch();// // JavaFX uygulamasını başlatır. Bu, `start` metodunu çağırır.
    }
}