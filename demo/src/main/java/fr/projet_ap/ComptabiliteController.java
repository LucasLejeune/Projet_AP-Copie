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

                String SqlQN = "SELECT vi_nom FROM visiteur WHERE vi_matricule = '" // JOIN visiteur ON vi_matricule =
                        // fk_matricule
                        + ident + "';";

                String SqlQR = "SELECT vi_nom FROM visiteur WHERE vi_matricule = '" // JOIN visiteur ON vi_matricule =
                        // fk_matricule
                        + ident + "';";

                String SqlQK = "SELECT vi_nom FROM visiteur WHERE vi_matricule = '" // JOIN visiteur ON vi_matricule =
                        // fk_matricule
                        + ident + "';";

                Statement stmt = conn.createStatement();
                ResultSet resultatQN = stmt.executeQuery(SqlQN);
                resultatQN.next();
                String QNuitee = resultatQN.getString(1);

                ResultSet resultatQR = stmt.executeQuery(SqlQR);
                resultatQR.next();
                String QRepas = resultatQR.getString(1);

                ResultSet resultatQK = stmt.executeQuery(SqlQK);
                resultatQK.next();
                String QKilometrage = resultatQK.getString(1);

                QuantiteNuitee.setText(QNuitee);
                QuantiteRepas.setText(QRepas);
                QuantiteKilometrage.setText(QKilometrage);

                float QnuiteeInt = 4 * 80; // Float.parseFloat(QNuitee)
                String TotalNuit = String.valueOf(QnuiteeInt);

                float QRepasInt = 4 * 29; // Float.parseFloat(QRepas)
                String TotalRepasstr = String.valueOf(QRepasInt);

                float QKmInt = 4 * (80 / 100); // Float.parseFloat(QKilometrage)
                String TotalKm = String.valueOf(QKmInt);

                TotalNuitee.setText(TotalNuit);
                TotalRepas.setText(TotalRepasstr);
                TotalKilometrage.setText(TotalKm);

                String SqlNom = "SELECT vi_nom, vi_prenom FROM visiteur Where vi_matricule = '" + ident
                        + "';";

                ResultSet resultat = statement.executeQuery(SqlNom);

                resultat.next();
                String nom = resultat.getString(1);
                String prenom = resultat.getString(2);
                Nom_prenom.setText(nom + " " + prenom);
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
