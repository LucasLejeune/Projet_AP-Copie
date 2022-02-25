package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;

public class ConsultationController {

    /**
     * @throws IOException
     */
    @FXML
    private void GoBack() throws IOException {
        App.setRoot("ClientV");
    }

    /**
     * @throws IOException
     */
    @FXML
    private void Modifier() throws IOException {
        App.setRoot("Renseignement");
    }

}
