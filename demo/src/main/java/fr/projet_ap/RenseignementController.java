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

            String TarifUN = "SELECT re_Prix_nuitee FROM region INNER JOIN visiteur ON re_nom = fk_re WHERE vi_matricule = '"
                    + Common.login + "';";

            ResultSet resultN = statement.executeQuery(TarifUN);
            resultN.next();
            String montantN = resultN.getString(1);
            muNuitee.setText(montantN + " €");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void calculerTTN(KeyEvent event) {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String TarifUN = "SELECT re_Prix_nuitee FROM region INNER JOIN visiteur ON re_nom = fk_re WHERE vi_matricule = '"
                    + Common.login + "';";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(TarifUN);
            result.next();
            String montantN = result.getString(1);

            String nNuitee = nbrNuitee.getText();
            int nbrNuitee = Integer.parseInt(nNuitee);
            int MontantUN = Integer.parseInt(TarifUN);
            int totalNuitees = nbrNuitee * MontantUN;
            String Nuitee = String.valueOf(totalNuitees);
            ttNuitee.setText(Nuitee + " €");
            // InsertN();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void InsertN() {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";
        String nNuitee = nbrNuitee.getText();

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String FraisNuitee = "SELECT re_Prix_nuitee FROM region INNER JOIN visiteur ON re_nom = fk_re WHERE vi_matricule = '"
                    + Common.login + "';";
            String SqlFiche = "INSERT INTO fiche ('fi_id', `fk_vi`) VALUES ('1', '" + Common.login
                    + "');";
            String SqlFFN = "INSERT INTO frais_forfaitaire` (`ff_nom`, `ff_quantite`, `ff_montant_unitaire`, `fk_fiche`) VALUES ('Nuitée', '"
                    + nNuitee + "', '" + FraisNuitee + "', '1');";

            Statement statement = conn.createStatement();
            statement.executeQuery(SqlFiche);
            statement.executeQuery(SqlFFN);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    void calculerTTR(KeyEvent event) {
        String nRepas = nbrRepas.getText();
        int nbrRepas = Integer.parseInt(nRepas);
        int totalRepas = nbrRepas * 29;
        String Repas = String.valueOf(totalRepas);
        ttRepas.setText(Repas + " €");
        InsertR();
    }

    public void InsertR() {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String SqlNom = "INSERT INTO `projet_ap`.`frais_forfaitaires` (`ff_nom`, `ff_quantite`, `ff_montant_unitaire`, `fk_fiche`) VALUES ('Repas', '6', '25', '1');";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SqlNom);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    void calculerTTK(KeyEvent event) {
        String nKilo = nbrKilo.getText();
        int nbrKilos = Integer.parseInt(nKilo);
        int totalKilos = nbrKilos * 100;
        String Kilos = String.valueOf(totalKilos);
        ttKilometrage.setText(Kilos + " €");
        InsertK();
    }

    public void InsertK() {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String SqlNom = "INSERT INTO `projet_ap`.`frais_forfaitaires` (`ff_nom`, `ff_quantite`, `ff_montant_unitaire`, `fk_fiche`) VALUES ('Kilometrage', '6', '25', '1');";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SqlNom);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * @throws IOException
     */
    @FXML
    private void GoBack() throws IOException {
        App.setRoot("ClientV");
    }

}