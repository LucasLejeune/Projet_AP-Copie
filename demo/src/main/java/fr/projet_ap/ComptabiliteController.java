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
import java.time.Month;
import java.time.format.TextStyle;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ComptabiliteController {

    @FXML
    private TextField id;

    @FXML
    private PasswordField mdp;

    @FXML
    private Label pasBon;

    @FXML
    private TextField Matricule;

    @FXML
    private TextField nom;

    @FXML
    private Label QuantiteKilometrage;

    @FXML
    private Label QuantiteNuitee;

    @FXML
    private Label QuantiteRepas;

    @FXML
    private Label TotalKilometrage;

    @FXML
    private Label TotalNuitee;

    @FXML
    private Label TotalRepas;

    @FXML
    private Label Nom_prenom;

    @FXML
    private Label MontantUkilometrage;

    @FXML
    private Label MontantUnuitee;

    @FXML
    private Label MontantUrepas;

    @FXML
    private Label Mois;

    @FXML
    private Label AutreDate1;

    @FXML
    private Label AutreDate2;

    @FXML
    private Label AutreDate3;

    @FXML
    private Label AutreLibelle1;

    @FXML
    private Label AutreLibelle2;

    @FXML
    private Label AutreLibelle3;

    @FXML
    private Label AutreMontant1;

    @FXML
    private Label AutreMontant2;

    @FXML
    private Label AutreMontant3;

    /**
     * @throws IOException
     */
    @FXML
    private void switchToClient() throws IOException {
        String dbURL = "jdbc:mysql://localhost:3306/projet_AP";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "SELECT vi_matricule FROM visiteur WHERE vi_matricule = '" + id.getText()
                    + "' AND vi_mdp = '" + mdp.getText() + "'; ";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                String ident = result.getString(1);

                if (id.getText().equals(ident)) {
                    Common.login = ident;
                    App.setRoot("ClientC");
                }

            } else if (id.getText().equals("admin") && mdp.getText().equals("admin")) {
                App.setRoot("ClientC");

            } else {
                pasBon.setText("Identifiant ou mot de passe invalide");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * @throws IOException
     */
    @FXML
    private void switchToAcceuil() throws IOException {
        App.setRoot("Acceuil");
    }

    @FXML
    private void RetourClient() throws IOException {
        App.setRoot("ClientC");
    }

    /**
     * @throws IOException
     */
    @FXML
    private void Generer_ff() throws IOException {
        String dbURL = "jdbc:mysql://localhost:3306/projet_AP";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "SELECT vi_matricule FROM visiteur WHERE vi_matricule ='" + Matricule.getText() + "';";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();

            Map<String, String> dicoMois = new HashMap<String, String>();
            dicoMois.put("janvier", "1");
            dicoMois.put("fevrier", "2");
            dicoMois.put("mars", "3");
            dicoMois.put("avril", "4");
            dicoMois.put("mai", "5");
            dicoMois.put("juin", "6");
            dicoMois.put("juillet", "7");
            dicoMois.put("aout", "8");
            dicoMois.put("septembre", "9");
            dicoMois.put("octobre", "10");
            dicoMois.put("novembre", "11");
            dicoMois.put("decembre", "12");

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
            int moisP = Integer.parseInt(dicoMois.get(leMois)) - 1;
            String mois = "";
            for (Map.Entry<String, String> entry : dicoMois.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (Integer.parseInt(value) == moisP) {
                    mois = dicoFR.get(key);
                }
            }

            Mois.setText(mois);

            String ident = result.getString(1);

            String SqlQN = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires JOIN a_ff ON ff_id = fk_id JOIN fiche ON fk_fi = fi_id WHERE fk_vi = '"
                    + ident + "' AND ff_nom = 'Nuitee' AND fi_mois = '" + mois + "';";

            String SqlQR = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires JOIN a_ff ON ff_id = fk_id JOIN fiche ON fk_fi = fi_id WHERE fk_vi = '"
                    + ident + "' AND ff_nom = 'Repas' AND fi_mois = '" + mois + "';";

            String SqlQK = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires JOIN a_ff ON ff_id = fk_id JOIN fiche ON fk_fi = fi_id WHERE fk_vi = '"
                    + ident + "' AND ff_nom = 'Kilometrage' AND fi_mois = '" + mois + "';";

            String QNuitee = "";
            String MUNuitee = "";
            String QRepas = "";
            String MURepas = "";
            String QKilometrage = "";
            String MUkilometrage = "";

            Statement stmt = conn.createStatement();
            ResultSet resultatQN = stmt.executeQuery(SqlQN);

            resultatQN.next();
            QNuitee = resultatQN.getString(1);
            MUNuitee = resultatQN.getString(2);

            ResultSet resultatQR = stmt.executeQuery(SqlQR);
            resultatQR.next();
            QRepas = resultatQR.getString(1);
            MURepas = resultatQR.getString(2);

            ResultSet resultatQK = stmt.executeQuery(SqlQK);
            resultatQK.next();
            QKilometrage = resultatQK.getString(1);
            MUkilometrage = resultatQK.getString(2);

            QuantiteNuitee.setText(QNuitee);
            QuantiteRepas.setText(QRepas);
            QuantiteKilometrage.setText(QKilometrage);
            MontantUnuitee.setText(MUNuitee);
            MontantUrepas.setText(MURepas);
            MontantUkilometrage.setText(MUkilometrage);

            Float QNuiteeINT = Float.parseFloat(QNuitee);
            Float QRepasINT = Float.parseFloat(QRepas);
            Float QKilometrageINT = Float.parseFloat(QKilometrage);
            Float MuNuiteeINT = Float.parseFloat(MUNuitee);
            Float MuRepasINT = Float.parseFloat(MURepas);
            Float MuKilometrageINT = Float.parseFloat(MUkilometrage);

            Float Tnuitee = QNuiteeINT * MuNuiteeINT;
            String TotalNuit = String.valueOf(Tnuitee);

            Float Trepas = QRepasINT * MuRepasINT;
            String TotalRepasstr = String.valueOf(Trepas);

            Float Tkilometrage = QKilometrageINT * MuKilometrageINT;
            String TotalKm = String.valueOf(Tkilometrage);

            TotalNuitee.setText(TotalNuit);
            TotalRepas.setText(TotalRepasstr);
            TotalKilometrage.setText(TotalKm);

            String SqlNom = "SELECT vi_nom, vi_prenom FROM visiteur Where vi_matricule = '" + ident
                    + "';";

            ResultSet resultat = statement.executeQuery(SqlNom);

            resultat.next();
            String nom = resultat.getString(1);
            String prenom = resultat.getString(2);
            Nom_prenom.setText(nom + "-" + prenom);

            String SqlAf = "SELECT af_date, af_libelle, af_montant FROM autres_frais JOIN fiche ON fk_fiche_af = fi_id WHERE fk_vi = '"
                    + ident + "' AND af_Est_Validee = '0' AND fi_mois = '" + mois + "';";
            ResultSet resultAF = statement.executeQuery(SqlAf);

            if (resultAF.next()) {
                String ad1 = resultAF.getString(1);
                String al1 = resultAF.getString(2);
                String am1 = resultAF.getString(3);
                AutreDate1.setText(ad1);
                AutreLibelle1.setText(al1);
                AutreMontant1.setText(am1);
            }

            if (resultAF.next()) {
                String ad2 = resultAF.getString(1);
                String al2 = resultAF.getString(2);
                String am2 = resultAF.getString(3);
                AutreDate2.setText(ad2);
                AutreLibelle2.setText(al2);
                AutreMontant2.setText(am2);

            }

            if (resultAF.next()) {
                String ad3 = resultAF.getString(1);
                String al3 = resultAF.getString(2);
                String am3 = resultAF.getString(3);
                AutreDate3.setText(ad3);
                AutreLibelle3.setText(al3);
                AutreMontant3.setText(am3);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
