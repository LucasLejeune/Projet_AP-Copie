package fr.projet_ap;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void initialize() {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String SqlNom = "SELECT vi_nom, vi_prenom FROM visiteur Where vi_matricule = '" + Common.login + "';";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SqlNom);

            result.next();
            String nom = result.getString(1);
            String prenom = result.getString(2);
            idConnexion.setText(nom + "-" + prenom);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}