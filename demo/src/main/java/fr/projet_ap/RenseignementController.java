package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;

public class RenseignementController {

    @FXML
    private Label muKilometrage;

    @FXML
    private Label idConnexion;

    @FXML
    private Label muNuitee;

    @FXML
    private Label muRepas;

    @FXML
    private Button BackButton;

    @FXML
    private TextField nbrKilo;

    @FXML
    private TextField nbrNuitee;

    @FXML
    private TextField nbrRepas;

    @FXML
    private Label ttNuitee;

    @FXML
    private Label ttRepas;

    @FXML
    private Label ttKilometrage;

    @FXML
    void calculerTTN(KeyEvent event) {
        String nNuitee = nbrNuitee.getText();
        int nbrNuitee = Integer.parseInt(nNuitee);
        int totalNuitees = nbrNuitee * 80;
        String Nuitee = String.valueOf(totalNuitees);
        ttNuitee.setText(Nuitee + " €");
    }

    @FXML
    void calculerTTR(KeyEvent event) {
        String nRepas = nbrRepas.getText();
        int nbrRepas = Integer.parseInt(nRepas);
        int totalRepas = nbrRepas * 29;
        String Repas = String.valueOf(totalRepas);
        ttRepas.setText(Repas + " €");
    }

    @FXML
    void calculerTTK(KeyEvent event) {
        String nKilo = nbrKilo.getText();
        int nbrKilos = Integer.parseInt(nKilo);
        int totalKilos = nbrKilos * 100;
        String Kilos = String.valueOf(totalKilos);
        ttKilometrage.setText(Kilos + " €");
    }

    /**
     * @throws IOException
     */
    @FXML
    private void GoBack() throws IOException {
        App.setRoot("ClientV");
    }

}