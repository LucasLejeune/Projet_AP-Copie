package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;

public class ClientVController {

    /**
     * @throws IOException
     */
    @FXML
    private void switchToConsultation() throws IOException {
        App.setRoot("Consultation");
    }

    /**
     * @throws IOException
     */
    @FXML
    private void switchToRenseignement() throws IOException {
        App.setRoot("Renseignement");
    }

    /**
     * @throws IOException
     */
    @FXML
    private void switchToAcceuil() throws IOException {
        App.setRoot("Acceuil");
    }
}