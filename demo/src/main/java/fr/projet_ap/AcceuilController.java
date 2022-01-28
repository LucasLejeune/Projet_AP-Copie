package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;

public class AcceuilController {

    @FXML
    private void switchToVisiteur() throws IOException {
        App.setRoot("Visiteur");
    }

    @FXML
    private void switchToComptabilite() throws IOException {
        App.setRoot("Comptabilite");
    }
}
