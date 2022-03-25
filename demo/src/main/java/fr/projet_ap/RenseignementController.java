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
    private TextField dateaf2;
    @FXML
    private TextField dateaf3;
    @FXML
    private TextField libelleaf1;
    @FXML
    private TextField libelleaf2;
    @FXML
    private TextField libelleaf3;
    @FXML
    private TextField montantaf1;
    @FXML
    private TextField montantaf2;
    @FXML
    private TextField montantaf3;
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
            Sql.next();

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
                        + "', 1, " + Common.login + ");";
                statement.executeUpdate(SqlFiche);
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
            ttNuitee.setText(Nuitee + " €");

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

            // insert AF2
            String date2 = dateaf2.getText();
            String libelle2 = libelleaf2.getText();
            String montant2 = montantaf2.getText();
            String SqlAF2 = "INSERT INTO autres_frais (af_date, af_libelle, af_montant, fk_fiche_af) VALUES ("
                    + date2 + ", " + libelle2 + ", " + montant2 + ", " + fi_id + ");";
            statement.executeUpdate(SqlAF2);

            // insert AF3
            String date3 = dateaf3.getText();
            String libelle3 = libelleaf3.getText();
            String montant3 = montantaf3.getText();
            String SqlAF3 = "INSERT INTO autres_frais (af_date, af_libelle, af_montant, fk_fiche_af) VALUES ("
                    + date3 + ", " + libelle3 + ", " + montant3 + ", " + fi_id + ");";
            statement.executeUpdate(SqlAF3);

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
            String TellID = "SELECT fi_id FROM fiche INNER JOIN visiteur ON fk_vi = vi_matricule WHERE vi_matricule = "
                    + Common.login + " order by fi_id DESC LIMIT 1;";
            ResultSet ID = statement.executeQuery(TellID);
            ID.next();
            String fi_id = ID.getString(1);

            String SQLN = "SELECT ff_id FROM frais_fortaires INNER JOIN visiteur ON vi_matricule = fk_vi where ff_nom = 'Nuitee' AND vi_matricule = "
                    + Common.login + ";";
            ResultSet SqlN = statement.executeQuery(SQLN);
            SqlN.next();

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
                // Insert a_ff
                String SqlAFF1 = "INSERT INTO a_ff (fk_id, fk_fi) VALUES (" + StrFFID1 + ", " + fi_id + ");";
                statement.executeUpdate(SqlAFF1);
            } else {
                String nNuitee = nbrNuitee.getText();
                String SqlFFN = "UPDATE frais_forfaitaires SET ff_quantite = " + nNuitee
                        + " WHERE ff_nom = 'Nuitee' AND fi_id = " + fi_id + ";"
                        + nNuitee + ", AND fi_id = " + fi_id + ");";
                statement.executeUpdate(SqlFFN);
            }

            String SQLR = "SELECT ff_id FROM frais_fortaires INNER JOIN visiteur ON vi_matricule = fk_vi where ff_nom = 'Repas' AND vi_matricule = "
                    + Common.login + ";";
            ResultSet SqlR = statement.executeQuery(SQLR);
            SqlR.next();
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
                // Insert a_ff
                String SqlAFF2 = "INSERT INTO a_ff (fk_id, fk_fi) VALUES (" + StrFFID2 + ", " + fi_id + ");";
                statement.executeUpdate(SqlAFF2);
            } else {
                String nRepas = nbrRepas.getText();
                String SqlFFR = "UPDATE frais_forfaitaires SET ff_quantite = " + nRepas
                        + " WHERE ff_nom = 'Repas' AND fi_id = " + fi_id + ";";
                statement.executeUpdate(SqlFFR);
            }

            String SQLK = "SELECT ff_id FROM frais_fortaires INNER JOIN visiteur ON vi_matricule = fk_vi where ff_nom = 'Repas' AND vi_matricule = "
                    + Common.login + ";";
            ResultSet SqlK = statement.executeQuery(SQLK);
            SqlK.next();
            if (!SqlR.next()) {
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
                // Insert a_ff
                String SqlAFF3 = "INSERT INTO a_ff (fk_id, fk_fi) VALUES (" + StrFFID3 + ", " + fi_id + ");";
                statement.executeUpdate(SqlAFF3);
            } else {
                String nKilo = nbrRepas.getText();
                String SqlFFK = "UPDATE frais_forfaitaires SET ff_quantite = " + nKilo
                        + " WHERE ff_nom = 'Kilometrage' AND fi_id = " + fi_id + ";";
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