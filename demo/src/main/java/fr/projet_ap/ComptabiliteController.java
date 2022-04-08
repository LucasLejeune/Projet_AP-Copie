package fr.projet_ap;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.Date;
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

    @FXML
    private Label idConnexion;

    @FXML
    private CheckBox CheckAf1;

    @FXML
    private CheckBox CheckAf2;

    @FXML
    private CheckBox CheckAf3;

    @FXML
    private TableView<Autre_frais> AF;

    @FXML
    private TableColumn<Autre_frais, Date> Af_Date;

    @FXML
    private TableColumn<Autre_frais, Boolean> Af_Validation;

    @FXML
    private TableColumn<Autre_frais, String> Af_libelle;

    @FXML
    private TableColumn<Autre_frais, Double> Af_montant;

    /**
     * @throws IOException
     */
    @FXML
    private void switchToClient() throws IOException {
        String dbURL = "jdbc:mysql://localhost:3306/projet_AP";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "SELECT co_id FROM comptable WHERE co_id = '" + id.getText()
                    + "' AND co_mdp = '" + mdp.getText() + "'; ";

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

            String SqlConn = "SELECT vi_nom, vi_prenom FROM visiteur Where vi_matricule = '" + Common.login + "';";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SqlConn);

            result.next();
            String nom = result.getString(1);
            String prenom = result.getString(2);
            idConnexion.setText(nom + "-" + prenom);

            String sql = "SELECT vi_matricule FROM visiteur WHERE vi_matricule ='" + Matricule.getText() + "';";

            ResultSet resultat = statement.executeQuery(sql);

            resultat.next();
            String ident = resultat.getString(1);

            String mois = GetMois();

            Mois.setText(mois);

            String SqlQN = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires JOIN fiche ON fk_fiche_ff = fi_id WHERE fk_vi = '"
                    + ident + "' AND ff_nom = 'Nuitee' AND fi_mois = '" + mois + "';";

            String SqlQR = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires JOIN fiche ON fk_fiche_ff = fi_id WHERE fk_vi = '"
                    + ident + "' AND ff_nom = 'Repas' AND fi_mois = '" + mois + "';";

            String SqlQK = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires JOIN fiche ON fk_fiche_ff = fi_id WHERE fk_vi = '"
                    + ident + "' AND ff_nom = 'Kilometrage' AND fi_mois = '" + mois + "';";

            String QNuitee = "";
            String MUNuitee = "";
            String QRepas = "";
            String MURepas = "";
            String QKilometrage = "";
            String MUkilometrage = "";

            ResultSet resultatQN = statement.executeQuery(SqlQN);

            resultatQN.next();
            QNuitee = resultatQN.getString(1);
            MUNuitee = resultatQN.getString(2);

            ResultSet resultatQR = statement.executeQuery(SqlQR);
            resultatQR.next();
            QRepas = resultatQR.getString(1);
            MURepas = resultatQR.getString(2);

            ResultSet resultatQK = statement.executeQuery(SqlQK);
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

            ResultSet resultatNom = statement.executeQuery(SqlNom);

            resultatNom.next();
            String nomV = resultatNom.getString(1);
            String prenomV = resultatNom.getString(2);
            Nom_prenom.setText(nomV + "-" + prenomV);

            String SqlAf = "SELECT af_date, af_libelle, af_montant FROM autres_frais JOIN fiche ON fk_fiche_af = fi_id WHERE fk_vi = '"
                    + ident + "' AND af_Est_Validee = '0' AND fi_mois = '" + mois + "';";
            ResultSet resultAF = statement.executeQuery(SqlAf);

            int i = 1;

            ObservableList<Autre_frais> list = FXCollections.observableArrayList();

            while (result.next()) {
                list.addAll(new Autre_frais(resultAF.getString(i), resultAF.getString(i + 1),
                        Double.parseDouble(resultAF.getString(i + 2))));
                i += 3;
            }

            Af_Date.setCellValueFactory(new PropertyValueFactory<Autre_frais, Date>("Af_Date"));
            Af_libelle.setCellValueFactory(new PropertyValueFactory<Autre_frais, String>("Af_libelle"));
            Af_montant.setCellValueFactory(new PropertyValueFactory<Autre_frais, Double>("Af_montant"));

            // Af_Validation.setCellValueFactory(new
            // PropertyValueFactory<Autre_frais,Boolean>("validation"));
            AF.setItems(list);

        } catch (

        SQLException ex) {
            ex.printStackTrace();
        }
    }

    private String GetMois() {
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
                return mois;
            }
        }
        return mois;

    }

    @FXML
    private void valider() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/projet_AP";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            String sql = "SELECT vi_matricule FROM visiteur WHERE vi_matricule ='" + Matricule.getText() + "';";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            result.next();
            String ident = result.getString(1);
            String ValiderFiche = "UPDATE fiche SET fi_Validation_Ct = 1 WHERE fk_vi = '" + ident + "' AND fi_mois = '"
                    + GetMois() + "';";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(ValiderFiche);
            LocalDate currentdate = LocalDate.now();
            String DateValid = "UPDATE fiche SET fi_Date_Validation = '" + currentdate + "' WHERE fk_vi = '" + ident
                    + "' AND fi_mois = '"
                    + GetMois() + "';";
            stmt.executeUpdate(DateValid);

        }

    }

}
