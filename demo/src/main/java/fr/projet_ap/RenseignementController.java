package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;

public class RenseignementController {

    @FXML
    private TextField mtntKilo;

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
    private void GoBack() throws IOException {
        App.setRoot("ClientV");
    }

    @FXML
    void calculerTTK(ActionEvent event) {

    }

    @FXML
    void calculerTTN(ActionEvent event) {

    }

    @FXML
    void calculerTTR(ActionEvent event) {

    }

}