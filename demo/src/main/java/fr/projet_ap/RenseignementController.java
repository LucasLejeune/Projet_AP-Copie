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
import java.time.Month;
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
    private Label Enregistre;
    @FXML
    private Label muNuitee;
    @FXML
    private Label muRepas;
    @FXML
    private Button BackButton;
    @FXML
    private Button Soumettre;
    @FXML
    private Button ValiderAF;
    @FXML
    private TextField dateaf1;
    @FXML
    private TextField libelleaf1;
    @FXML
    private TextField montantaf1;
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
            muNuitee.setText(montantN);

            String TarifUR = "SELECT re_Prix_Repas FROM region INNER JOIN visiteur ON re_nom = fk_re WHERE vi_matricule = '"
                    + Common.login + "';";

            ResultSet resultR = statement.executeQuery(TarifUR);
            resultR.next();
            String montantR = resultR.getString(1);
            muRepas.setText(montantR);

            String TarifUK = "SELECT ve_Prix_km FROM vehicule INNER JOIN visiteur ON ve_Puissance = fk_ve WHERE vi_matricule = '"
                    + Common.login + "';";

            ResultSet resultK = statement.executeQuery(TarifUK);
            resultK.next();
            String montantK = resultK.getString(1);
            muKilometrage.setText(montantK);

            // insert fiche
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

            String SQL = "SELECT fi_id FROM fiche INNER JOIN visiteur ON vi_matricule = fk_vi where fi_mois = '" + mois
                    + "' AND vi_matricule = "
                    + Common.login + ";";
            ResultSet Sql = statement.executeQuery(SQL);

            if (!Sql.next()) {
                // Recuperation de l'ID
                String TellID = "SELECT fi_id FROM fiche order by fi_id DESC LIMIT 1;";
                ResultSet ID = statement.executeQuery(TellID);
                ID.next();
                String StrID = ID.getString(1);
                int fi_id = Integer.parseInt(StrID);
                fi_id += 1;

                String SqlFiche = "INSERT INTO fiche (fi_id, fi_mois, fi_signature, fk_vi) VALUES (" + fi_id + ", '"
                        + mois
                        + "', 0, " + Common.login + ");";
                statement.executeUpdate(SqlFiche);
            }

            // select de fi_id
            String TellID = "SELECT fi_id FROM fiche INNER JOIN visiteur ON fk_vi = vi_matricule WHERE vi_matricule  = "
                    + Common.login + " order by fi_id DESC LIMIT 1;";
            ResultSet ID = statement.executeQuery(TellID);
            ID.next();
            String fi_id = ID.getString(1);

            // afficher les nuitees et leurs totaux
            String SQLN = "SELECT ff_id FROM fiche INNER JOIN visiteur ON vi_matricule = fk_vi Inner Join frais_forfaitaires ON fi_id = fk_fiche_ff where ff_nom = 'Nuitee' AND vi_matricule = "
                    + Common.login + ";";
            ResultSet SqlN = statement.executeQuery(SQLN);
            if (SqlN.next()) {
                String databaseN = "SELECT ff_quantite FROM frais_forfaitaires Inner Join fiche ON fi_id = fk_fiche_ff INNER JOIN visiteur ON vi_matricule = fk_vi where ff_nom = 'Nuitee' AND vi_matricule = '"
                        + Common.login + "';";

                ResultSet quantite_nuitee = statement.executeQuery(databaseN);
                quantite_nuitee.next();
                String str_quantite_nuitee = quantite_nuitee.getString(1);
                nbrNuitee.setText(str_quantite_nuitee);

                String tarifUnitaireNuitee = "SELECT re_Prix_nuitee FROM region INNER JOIN visiteur ON re_nom = fk_re WHERE vi_matricule = '"
                        + Common.login + "';";

                ResultSet resultNuitee = statement.executeQuery(tarifUnitaireNuitee);
                resultNuitee.next();
                String str_Unitiare_Nuitee = resultNuitee.getString(1);

                double MontantUN = Double.parseDouble(str_Unitiare_Nuitee);
                double QuantiteN = Double.parseDouble(str_quantite_nuitee);

                double totalNuitees = MontantUN * QuantiteN;
                String Nuitees = String.valueOf(totalNuitees);
                ttNuitee.setText(Nuitees);

            }

            // afficher les repas et leurs totaux
            String SQLR = "SELECT ff_id FROM fiche INNER JOIN visiteur ON vi_matricule = fk_vi Inner Join frais_forfaitaires ON fi_id = fk_fiche_ff where ff_nom = 'Repas' AND vi_matricule = "
                    + Common.login + ";";
            ResultSet SqlR = statement.executeQuery(SQLR);
            if (SqlR.next()) {
                String databaseR = "SELECT ff_quantite FROM frais_forfaitaires Inner Join fiche ON fi_id = fk_fiche_ff INNER JOIN visiteur ON vi_matricule = fk_vi where ff_nom = 'Repas' AND vi_matricule = '"
                        + Common.login + "';";
                ResultSet quantite_repas = statement.executeQuery(databaseR);
                quantite_repas.next();
                String str_quantite_repas = quantite_repas.getString(1);
                nbrRepas.setText(str_quantite_repas);

                String tarif_Unuitaire_Repas = "SELECT re_Prix_Repas FROM region INNER JOIN visiteur ON re_nom = fk_re WHERE vi_matricule = '"
                        + Common.login + "';";

                ResultSet resultRepas = statement.executeQuery(tarif_Unuitaire_Repas);
                resultRepas.next();
                String str_Unitaire_Nuitee = resultRepas.getString(1);

                double MontantUR = Double.parseDouble(str_Unitaire_Nuitee);
                double QuantiteR = Double.parseDouble(str_quantite_repas);

                double totalRepas = QuantiteR * MontantUR;
                String Repas = String.valueOf(totalRepas);
                ttRepas.setText(Repas);

            }

            // afficher les kilometrage et leurs totaux
            String SQLK = "SELECT ff_id FROM fiche INNER JOIN visiteur ON vi_matricule = fk_vi Inner Join frais_forfaitaires ON fi_id = fk_fiche_ff where ff_nom = 'Kilometrage' AND vi_matricule = "
                    + Common.login + ";";
            ResultSet SqlK = statement.executeQuery(SQLK);
            if (SqlK.next()) {
                String databaseK = "SELECT ff_quantite FROM frais_forfaitaires Inner Join fiche ON fi_id = fk_fiche_ff INNER JOIN visiteur ON vi_matricule = fk_vi where ff_nom = 'Kilometrage' AND vi_matricule = '"
                        + Common.login + "';";
                ResultSet quantite_kilometrage = statement.executeQuery(databaseK);
                quantite_kilometrage.next();
                String str_quantite_kilometrage = quantite_kilometrage.getString(1);
                nbrKilo.setText(str_quantite_kilometrage);

                String tarif_Unitaire_Kilometrage = "SELECT ve_Prix_km FROM vehicule INNER JOIN visiteur ON ve_Puissance = fk_ve WHERE vi_matricule = '"
                        + Common.login + "';";
                ResultSet resultKilometrage = statement.executeQuery(tarif_Unitaire_Kilometrage);
                resultKilometrage.next();
                String str_Unitaire_Nuitee = resultKilometrage.getString(1);

                double MontantUK = Double.parseDouble(str_Unitaire_Nuitee);
                double QuantiteK = Double.parseDouble(str_quantite_kilometrage);

                double totalKilos = QuantiteK * MontantUK;
                String Kilos = String.valueOf(totalKilos);
                ttKilometrage.setText(Kilos);
            }

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
            ttNuitee.setText(Nuitee);

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
            ttRepas.setText(Repas);

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
            ttKilometrage.setText(Kilos);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    void ValiderAF(ActionEvent event) {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            Statement statement = conn.createStatement();

            // select de fi_id
            String TellID = "SELECT fi_id FROM fiche INNER JOIN visiteur ON fk_vi = vi_matricule WHERE vi_matricule = "
                    + Common.login + " order by fi_id DESC LIMIT 1;";
            ResultSet ID = statement.executeQuery(TellID);
            ID.next();
            String fi_id = ID.getString(1);

            // insert AF1
            String date1 = dateaf1.getText();
            String libelle1 = libelleaf1.getText();
            String montant1 = montantaf1.getText();
            String SqlAF1 = "INSERT INTO autres_frais (af_date, af_libelle, af_montant, fk_fiche_af) VALUES ("
                    + date1 + ", " + libelle1 + ", " + montant1 + ", " + fi_id + ");";
            statement.executeUpdate(SqlAF1);

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

            Statement statement = conn.createStatement();
            // select de fi_id
            String TellID = "SELECT fi_id FROM fiche INNER JOIN visiteur ON fk_vi = vi_matricule WHERE vi_matricule  = "
                    + Common.login + " order by fi_id DESC LIMIT 1;";
            ResultSet ID = statement.executeQuery(TellID);
            ID.next();
            String fi_id = ID.getString(1);

            String SQLN = "SELECT ff_id FROM fiche INNER JOIN visiteur ON vi_matricule = fk_vi Inner Join frais_forfaitaires ON fi_id = fk_fiche_ff where ff_nom = 'Nuitee' AND vi_matricule = "
                    + Common.login + ";";
            ResultSet SqlN = statement.executeQuery(SQLN);
            if (!SqlN.next()) {
                // Insert NbrNuitee
                String nNuitee = nbrNuitee.getText();
                String mNuitee = muNuitee.getText();
                String SqlFFN = "INSERT INTO frais_forfaitaires (ff_nom, ff_quantite, ff_montant_unitaire, fk_fiche_ff) VALUES ('Nuitee', "
                        + nNuitee + ", " + mNuitee + ", " + fi_id + ");";
                statement.executeUpdate(SqlFFN);
                String TellFFID1 = "SELECT ff_id FROM frais_forfaitaires order by ff_id DESC LIMIT 1;";
                ResultSet FFID1 = statement.executeQuery(TellFFID1);
                FFID1.next();
                String StrFFID1 = FFID1.getString(1);
            } else {
                String nNuitee = nbrNuitee.getText();
                String SqlFFN = "UPDATE frais_forfaitaires SET ff_quantite = " + nNuitee
                        + " WHERE ff_nom = 'Nuitee' AND fk_fiche_ff = " + fi_id + ";";
                statement.executeUpdate(SqlFFN);
            }

            String SQLR = "SELECT ff_id FROM fiche INNER JOIN visiteur ON vi_matricule = fk_vi Inner Join frais_forfaitaires ON fi_id = fk_fiche_ff where ff_nom = 'Repas' AND vi_matricule = "
                    + Common.login + ";";
            ResultSet SqlR = statement.executeQuery(SQLR);
            if (!SqlR.next()) {
                // insert NbrRepas
                String nRepas = nbrRepas.getText();
                String mRepas = muRepas.getText();
                String SqlFFR = "INSERT INTO frais_forfaitaires (ff_nom, ff_quantite, ff_montant_unitaire, fk_fiche_ff) VALUES ('Repas', "
                        + nRepas + ", " + mRepas + ", " + fi_id + " );";
                statement.executeUpdate(SqlFFR);
                String TellFFID2 = "SELECT ff_id FROM frais_forfaitaires order by ff_id DESC LIMIT 1;";
                ResultSet FFID2 = statement.executeQuery(TellFFID2);
                FFID2.next();
                String StrFFID2 = FFID2.getString(1);
            } else {
                String nRepas = nbrRepas.getText();
                String SqlFFR = "UPDATE frais_forfaitaires SET ff_quantite = " + nRepas
                        + " WHERE ff_nom = 'Repas' AND fk_fiche_ff = " + fi_id + ";";
                statement.executeUpdate(SqlFFR);
            }

            String SQLK = "SELECT ff_id FROM fiche INNER JOIN visiteur ON vi_matricule = fk_vi Inner Join frais_forfaitaires ON fi_id = fk_fiche_ff where ff_nom = 'Kilometrage' AND vi_matricule = "
                    + Common.login + ";";
            ResultSet SqlK = statement.executeQuery(SQLK);
            if (!SqlK.next()) {
                // insert NbrKilometrage
                String nKilo = nbrKilo.getText();
                String mKilo = muKilometrage.getText();
                String SqlFFK = "INSERT INTO frais_forfaitaires (ff_nom, ff_quantite, ff_montant_unitaire, fk_fiche_ff) VALUES ('Kilometrage', "
                        + nKilo + ", " + mKilo + ", " + fi_id + " );";
                statement.executeUpdate(SqlFFK);
                String TellFFID3 = "SELECT ff_id FROM frais_forfaitaires order by ff_id DESC LIMIT 1;";
                ResultSet FFID3 = statement.executeQuery(TellFFID3);
                FFID3.next();
                String StrFFID3 = FFID3.getString(1);
            } else {
                String nKilo = nbrKilo.getText();
                String SqlFFK = "UPDATE frais_forfaitaires SET ff_quantite = " + nKilo
                        + " WHERE ff_nom = 'Kilometrage' AND fk_fiche_ff = " + fi_id + ";";
                statement.executeUpdate(SqlFFK);
            }

            Enregistre.setText("Fiche de renseignement: Enregistrée");

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