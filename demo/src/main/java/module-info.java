module fr.projet_ap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens fr.projet_ap to javafx.fxml;
    exports fr.projet_ap;
}
