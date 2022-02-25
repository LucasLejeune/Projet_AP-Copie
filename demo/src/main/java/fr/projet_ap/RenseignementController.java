package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
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
    void calculerTTK(MouseDragEvent event) {

    }

    @FXML
    void calculerTTN(MouseDragEvent event) {

    }

    @FXML
    void calculerTTR(DragEvent event) {
        String nRepas = nbrRepas.getText();
        int nbrRepas = Integer.parseInt(nRepas);
        int totalRepas = nbrRepas * 50;
        String Repas = String.valueOf(totalRepas);
        ttRepas.setText(Repas);
    }

    /**
     * @throws IOException
     */
    @FXML
    private void GoBack() throws IOException {
        App.setRoot("ClientV");
    }

}