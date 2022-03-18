package fr.projet_ap;

import java.io.IOException;

import javafx.event.ActionEvent;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    private Button Soumettre;

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

            String TarifUR = "SELECT re_Prix_Repas FROM region INNER JOIN visiteur ON re_nom = fk_re WHERE vi_matricule = '"
                    + Common.login + "';";

            ResultSet resultR = statement.executeQuery(TarifUR);
            resultR.next();
            String montantR = resultR.getString(1);
            muRepas.setText(montantR + " €");

            String TarifUK = "SELECT ve_Prix_km FROM vehicule INNER JOIN visiteur ON ve_Puissance = fk_ve WHERE vi_matricule = '"
                    + Common.login + "';";

            ResultSet resultK = statement.executeQuery(TarifUK);
            resultK.next();
            String montantK = resultK.getString(1);
            muKilometrage.setText(montantK + " €");

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
            ResultSet resultN = statement.executeQuery(TarifUN);
            resultN.next();

            String montantN = resultN.getString(1);
            double MontantUN = Double.parseDouble(montantN);

            String nNuitee = nbrNuitee.getText();
            int nbrNuitee = Integer.parseInt(nNuitee);

            double totalNuitees = nbrNuitee * MontantUN;
            String Nuitee = String.valueOf(totalNuitees);
            ttNuitee.setText(Nuitee + " €");
            // InsertN();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    void calculerTTR(KeyEvent event) {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String TarifUR = "SELECT re_Prix_Repas FROM region INNER JOIN visiteur ON re_nom = fk_re WHERE vi_matricule = '"
                    + Common.login + "';";

            Statement statement = conn.createStatement();
            ResultSet resultR = statement.executeQuery(TarifUR);
            resultR.next();

            String montantR = resultR.getString(1);
            double MontantUR = Double.parseDouble(montantR);

            String nRepas = nbrRepas.getText();
            int nbrRepas = Integer.parseInt(nRepas);

            double totalRepas = nbrRepas * MontantUR;
            String Repas = String.valueOf(totalRepas);
            ttRepas.setText(Repas + " €");
            // InsertR();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void calculerTTK(KeyEvent event) {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String TarifUK = "SELECT ve_Prix_km FROM vehicule INNER JOIN visiteur ON ve_Puissance = fk_ve WHERE vi_matricule = '"
                    + Common.login + "';";

            Statement statement = conn.createStatement();
            ResultSet resultK = statement.executeQuery(TarifUK);
            resultK.next();

            String montantK = resultK.getString(1);
            double MontantUK = Double.parseDouble(montantK);

            String nKilo = nbrKilo.getText();
            int nbrKilos = Integer.parseInt(nKilo);

            double totalKilos = nbrKilos * MontantUK;
            String Kilos = String.valueOf(totalKilos);
            ttKilometrage.setText(Kilos + " €");
            // InsertK();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void Inserts(ActionEvent event) throws ClassNotFoundException {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            // insert NbrNuitees
            Map<String, String> dicoFR = new HashMap<String, String>();
            dicoFR.put("janvier", "JANVIER");
            dicoFR.put("fevrier", "FEVRIER");
            dicoFR.put("mars", "MARS");
            dicoFR.put("avril", "AVRIL");
            dicoFR.put("mai", "MAI");
            dicoFR.put("juin", "JUIN");
            dicoFR.put("juillet", "JUILLET");
            dicoFR.put("aout", "AOUT");
            dicoFR.put("septembre", "SEPTEMBRE");
            dicoFR.put("octobre", "OCTOBRE");
            dicoFR.put("novembre", "NOVEMBRE");
            dicoFR.put("decembre", "DECEMBRE");

            LocalDate currentdate = LocalDate.now();
            Month currentMonth = currentdate.getMonth();
            String leMois = currentMonth.getDisplayName(TextStyle.FULL, Locale.FRANCE);
            String mois = dicoFR.get(leMois);

            Statement statement = conn.createStatement();
            String SqlFiche = "INSERT INTO fiche (fi_id, fi_mois, fk_vi) VALUES (uuid(), '" + mois + "', '"
                    + Common.login + "');";
            statement.executeUpdate(SqlFiche);

            // Recuperation de l'ID
            String TellUUID = "Select fi_id FROM fiche WHERE fi_mois = '" + leMois + "' AND fk_vi = '" + Common.login
                    + "' ;";
            ResultSet UUID = statement.executeQuery(TellUUID);

            String nNuitee = nbrNuitee.getText();
            String mNuitee = muNuitee.getText();
            String SqlFFN = "INSERT INTO frais_forfaitaire` (`ff_nom`, `ff_quantite`, `ff_montant_unitaire`, `fk_fiche_ff`) VALUES ('Nuitée', '"
                    + nNuitee + "', '" + mNuitee + "', '" + UUID + "');";
            statement.executeUpdate(SqlFFN);

            // insert NbrRepas
            String nRepas = nbrRepas.getText();
            String mRepas = muRepas.getText();
            String SqlFFR = "INSERT INTO frais_forfaitaire` (ff_nom, ff_quantite, ff_montant_unitaire, fk_fiche_ff) VALUES ('Repas', '"
                    + nRepas + "', '" + mRepas + "', '" + UUID + "');";
            statement.executeUpdate(SqlFFR);

            // insert NbrKilometrage
            String nKilo = nbrKilo.getText();
            String mKilo = muKilometrage.getText();
            String SqlFFK = "INSERT INTO frais_forfaitaire` (ff_nom, ff_quantite, ff_montant_unitaire, fk_fiche_ff) VALUES ('Kilometrage', '"
                    + nKilo + "', '" + mKilo + "', '" + UUID + "');";
            statement.executeUpdate(SqlFFK);

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