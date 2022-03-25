package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;

public class ClientCController {

    /**
     * @throws IOException
     */
    @FXML
    private void switchToValidation() throws IOException {
        App.setRoot("Validation");

    }

    /**
     * @throws IOException
     */
    @FXML
    private void switchToSuivi() throws IOException {
        App.setRoot("Suivi");
    }

    /**
     * @throws IOException
     */
    @FXML
    private void switchToAcceuil() throws IOException {
        App.setRoot("Acceuil");
    }
}