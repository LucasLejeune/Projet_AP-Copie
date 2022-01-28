package fr.projet_ap;

import java.io.IOException;

import javafx.fxml.FXML;

public class VisiteurController {

    @FXML
    private void switchToClient() throws IOException {
        App.setRoot("ClientV");
    }

    @FXML
    private void switchToAcceuil() throws IOException {
        App.setRoot("Acceuil");
    }
}