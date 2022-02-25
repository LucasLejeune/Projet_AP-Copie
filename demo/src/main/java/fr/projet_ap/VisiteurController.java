package fr.projet_ap;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VisiteurController {

    @FXML
    private TextField id;

    @FXML
    private TextField mdp;

    @FXML
    private Label pasBon;

    /**
     * @throws IOException
     */
    @FXML
    private void switchToClient() throws IOException {
        String dbURL = "jdbc:mysql://localhost:3306/projet_AP";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "SELECT Matricule FROM users WHERE Matricule = '" + id.getText() + "' AND Mot_de_passe = '"
                    + mdp.getText() + "'; ";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                String ident = result.getString(1);

                if (id.getText().equals(ident)) {
                    App.setRoot("ClientV");
                }

            } else if (id.getText().equals("admin") && mdp.getText().equals("admin")) {
                App.setRoot("ClientV");

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
}