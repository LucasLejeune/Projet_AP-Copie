package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;

public class ClientCController {

    @FXML
    private void switchToValidation() throws IOException {
        App.setRoot("Validation");
    }

    @FXML
    private void switchToSuivi() throws IOException {
        App.setRoot("Suivi");
    }

    @FXML
    private void switchToAcceuil() throws IOException {
        App.setRoot("Acceuil");
    }
}