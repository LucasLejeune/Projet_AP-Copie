package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;

public class ClientVController {

    @FXML
    private void switchToConsultation() throws IOException {
        App.setRoot("Consultation");
    }

    @FXML
    private void switchToRenseignement() throws IOException {
        App.setRoot("Renseignement");
    }
    
    @FXML
    private void switchToAcceuil() throws IOException {
        App.setRoot("Acceuil");
    }
}