package fr.projet_ap;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultationController {

    @FXML
    private Button BackButton;
    @FXML
    private Button generer;
    @FXML
    private Label idConnexion;
    @FXML
    private Label matricule;
    @FXML
    private Label ttNuitee;
    @FXML
    private Label ttRepas;
    @FXML
    private Label ttKilometrage;
    @FXML
    private TextField mois;
    @FXML
    private Label prki;
    @FXML
    private Label prnu;
    @FXML
    private Label prre;
    @FXML
    private Label quki;
    @FXML
    private Label qunu;
    @FXML
    private Label qure;

    @FXML
    public void initialize() {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            // recuperation Nom et Matricule
            String SqlNom = "SELECT vi_nom, vi_prenom FROM visiteur Where vi_matricule = '" + Common.login + "';";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SqlNom);

            result.next();
            String nom = result.getString(1);
            String prenom = result.getString(2);
            idConnexion.setText(nom + "-" + prenom);
            matricule.setText(Common.login);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void Generer(ActionEvent event) {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            // Recuperation de l'ID de la fiche
            String strmois = mois.getText();
            String sqlSelectFiche = "Select fi_id from fiche where fi_mois = '" + strmois + "' and fk_vi = '"
                    + Common.login + "';";
            Statement statement = conn.createStatement();
            ResultSet resultfiid = statement.executeQuery(sqlSelectFiche);
            resultfiid.next();
            String fi_id = resultfiid.getString(1);

            // frais forfaitaire
            String SQLN = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires INNER JOIN fiche ON fk_fiche_ff = fi_id where ff_nom = 'Nuitee' AND fk_vi = "
                    + Common.login + " and fk_fiche_ff = '" + fi_id + "';";
            ResultSet resultnuitees = statement.executeQuery(SQLN);
            resultnuitees.next();
            String quantite_nuits = resultnuitees.getString(1);
            String prix_nuits = resultnuitees.getString(2);
            qunu.setText(quantite_nuits);
            prnu.setText(prix_nuits);
            double dokunu = Double.parseDouble(quantite_nuits);
            double doprnu = Double.parseDouble(prix_nuits);
            double MontantN = dokunu * doprnu;
            String MontantTN = String.valueOf(MontantN);
            ttNuitee.setText(MontantTN);

            String SQLR = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires INNER JOIN fiche ON fk_fiche_ff = fi_id where ff_nom = 'Repas' AND fk_vi = "
                    + Common.login + " and fk_fiche_ff = '" + fi_id + "';";
            ResultSet resultrepas = statement.executeQuery(SQLR);
            resultrepas.next();
            String quantite_repas = resultrepas.getString(1);
            String prix_repas = resultrepas.getString(2);
            qure.setText(quantite_repas);
            prre.setText(prix_repas);
            double doqure = Double.parseDouble(quantite_repas);
            double doprre = Double.parseDouble(prix_nuits);
            double MontantR = doqure * doprre;
            String MontantTR = String.valueOf(MontantR);
            ttRepas.setText(MontantTR);

            String SQLK = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires INNER JOIN fiche ON fk_fiche_ff = fi_id where ff_nom = 'Kilometrage' AND fk_vi = "
                    + Common.login + " and fk_fiche_ff = '" + fi_id + "';";
            ResultSet resultkilo = statement.executeQuery(SQLK);
            resultkilo.next();
            String quantite_kilo = resultkilo.getString(1);
            String prix_kilo = resultkilo.getString(2);
            quki.setText(quantite_kilo);
            prki.setText(prix_kilo);
            double doquki = Double.parseDouble(quantite_kilo);
            double doprki = Double.parseDouble(prix_kilo);
            double MontantK = doprki * doquki;
            String MontantTK = String.valueOf(MontantK);
            ttKilometrage.setText(MontantTK);

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
