package fr.projet_ap;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FormCController {

    @FXML
    private TextField Email;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Prenom;

    @FXML
    private TextField id;

    @FXML
    private PasswordField mdp;

    @FXML
    private TextField Date_naissance;

    /**
     * @throws IOException
     */
    @FXML
    private void switchToForm() throws IOException {
        App.setRoot("FormC");
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
    private void inscription_C() throws IOException {
        String dbURL = "jdbc:mysql://127.0.0.1:3306/projet_AP";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "INSERT INTO users (nom, prenom, date_naissance, email, identifiant, Mot_de_passe) VALUES (?, ?, ?, ?, ?, ?)";

            String nom = Nom.getText();
            String prenom = Prenom.getText();
            String date_naissance = Date_naissance.getText();
            String email = Email.getText();
            String Identifiant = id.getText();
            String Mot_de_passe = mdp.getText();

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, date_naissance);
            statement.setString(4, email);
            statement.setString(5, Identifiant);
            statement.setString(6, Mot_de_passe);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        App.setRoot("Comptabilite");
    }

}
