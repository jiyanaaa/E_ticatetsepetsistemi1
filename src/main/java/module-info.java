module com.eticaretsepetsistemi.e_ticaret {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.eticaretsepetsistemi.e_ticaret to javafx.fxml;
    exports com.eticaretsepetsistemi.e_ticaret;
    exports com.eticaretsepetsistemi.e_ticaret.Controller;
    opens com.eticaretsepetsistemi.e_ticaret.Controller to javafx.fxml;
    exports com.eticaretsepetsistemi.e_ticaret.Model;
    opens com.eticaretsepetsistemi.e_ticaret.Model to javafx.fxml;
}