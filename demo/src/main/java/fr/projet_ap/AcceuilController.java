package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;

public class AcceuilController {

    /**
     * @throws IOException
     */
    @FXML
    private void switchToVisiteur() throws IOException {
        App.setRoot("Visiteur");
    }

    /**
     * @throws IOException
     */
    @FXML
    private void switchToComptabilite() throws IOException {
        App.setRoot("Comptabilite");
    }
}
