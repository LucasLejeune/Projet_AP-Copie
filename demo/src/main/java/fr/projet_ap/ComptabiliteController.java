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

            while (result.next()) {

                String ident = result.getString(1);

                String SqlQN = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires JOIN a_ff ON ff_id = fk_id JOIN fiche ON fk_fi = fi_id WHERE fk_vi = '"
                        + ident + "' AND ff_nom = 'Nuitee' AND fi_mois = 'MAI';";

                String SqlQR = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires JOIN a_ff ON ff_id = fk_id JOIN fiche ON fk_fi = fi_id WHERE fk_vi = '"
                        + ident + "' AND ff_nom = 'Repas' AND fi_mois = 'MAI';";

                String SqlQK = "SELECT ff_quantite, ff_montant_unitaire FROM frais_forfaitaires JOIN a_ff ON ff_id = fk_id JOIN fiche ON fk_fi = fi_id WHERE fk_vi = '"
                        + ident + "' AND ff_nom = 'Kilometrage' AND fi_mois = 'MAI';";

                String QNuitee = "";
                String MUNuitee = "";
                String QRepas = "";
                String MURepas = "";
                String QKilometrage = "";
                String MUkilometrage = "";

                Statement stmt = conn.createStatement();
                ResultSet resultatQN = stmt.executeQuery(SqlQN);

                while (resultatQN.next()) {
                    QNuitee = resultatQN.getString(1);
                    MUNuitee = resultatQN.getString(2);
                }

                ResultSet resultatQR = stmt.executeQuery(SqlQR);
                while (resultatQR.next()) {
                    QRepas = resultatQR.getString(1);
                    MURepas = resultatQR.getString(2);
                }

                ResultSet resultatQK = stmt.executeQuery(SqlQK);
                while (resultatQK.next()) {
                    QKilometrage = resultatQK.getString(1);
                    MUkilometrage = resultatQK.getString(2);
                }

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

                Float Tkilometrage = QKilometrageINT * MuKilometrageINT; // Float.parseFloat(QKilometrage)
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
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @throws IOException
     */
    @FXML
    private void validationNuit() throws IOException {
        String dbURL = "jdbc:mysql://localhost:3306/projet_AP";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "SELECT validee from fiche";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int validee = result.getInt(1);
                if (validee == 0) {
                    // changer BDD pour avoir validation pour chaque enregistrement

                }
            }
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
