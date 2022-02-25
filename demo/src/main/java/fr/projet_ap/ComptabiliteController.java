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

                String SqlQN = "SELECT Quantite FROM frais_forfaitaires JOIN visiteur ON vi_matricule = fk_matricule WHERE Matricule = '"
                        + ident + "' AND ff_nom = nuitee;";

                String SqlQR = "SELECT Quantite FROM frais_forfaitaires JOIN visiteur ON vi_matricule = fk_matricule WHERE Matricule = '"
                        + ident + "' AND ff_nom = repas;";

                String SqlQK = "SELECT Quantite FROM frais_forfaitaires JOIN visiteur ON vi_matricule = fk_matricule WHERE Matricule = '"
                        + ident + "' AND ff_nom = kilometre;";

                Statement stmt = conn.createStatement();
                ResultSet resultatQN = stmt.executeQuery(SqlQN);
                ResultSet resultatQR = stmt.executeQuery(SqlQR);
                ResultSet resultatQK = stmt.executeQuery(SqlQK);

                resultatQN.next();
                String QNuitee = resultatQN.getString(1);
                String QRepas = resultatQR.getString(1);
                String QKilometrage = resultatQK.getString(1);

                QuantiteNuitee.setText(QNuitee);
                QuantiteRepas.setText(QRepas);
                QuantiteKilometrage.setText(QKilometrage);

                float QnuiteeInt = Float.parseFloat(QNuitee) * 80;
                String TotalNuit = String.valueOf(QnuiteeInt);

                float QRepasInt = Float.parseFloat(QRepas) * 29;
                String TotalRepasstr = String.valueOf(QRepasInt);

                float QKmInt = Float.parseFloat(QKilometrage) * (80 / 100);
                String TotalKm = String.valueOf(QKmInt);

                TotalNuitee.setText(TotalNuit);
                TotalRepas.setText(TotalRepasstr);
                TotalKilometrage.setText(TotalKm);
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
