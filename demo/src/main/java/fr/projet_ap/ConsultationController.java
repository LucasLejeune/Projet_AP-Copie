package fr.projet_ap;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {

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
    private ComboBox combobox;
    @FXML
    private Label fichevide;
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
    private TableView<Autre_frais> AF;
    @FXML
    private TableColumn<Autre_frais, Date> Af_Date;
    @FXML
    private TableColumn<Autre_frais, String> Af_libelle;
    @FXML
    private TableColumn<Autre_frais, Double> Af_montant;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";
        ObservableList<String> list = FXCollections.observableArrayList("Janvier", "Fevrier", "Mars", "Avril", "Mai",
                "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre");
        combobox.setItems(list);

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
    void SelectionnerMois(ActionEvent event) {
        String dbURL = "jdbc:mysql://localhost:3306/projet_ap";
        String username = "root";
        String password = "";
        String s = combobox.getSelectionModel().getSelectedItem().toString();
        String month = s;

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            // Recuperation de l'ID de la fiche
            String sqlSelectFiche = "Select fi_id from fiche where fi_mois = '" + month
                    + "' and fk_vi = '"
                    + Common.login + "';";
            Statement statement = conn.createStatement();
            ResultSet resultfiid = statement.executeQuery(sqlSelectFiche);

            if (!resultfiid.next()) {
                fichevide.setText("Fiche du mois selectionné vide");
                qunu.setText("");
                prnu.setText("");
                qure.setText("");
                prre.setText("");
                quki.setText("");
                prki.setText("");
                ttNuitee.setText("");
                ttRepas.setText("");
                ttKilometrage.setText("");
                ObservableList<Autre_frais> list = FXCollections.observableArrayList();
                AF.setItems(list);

            } else {
                fichevide.setText("");
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

                // Autres frais
                int i = 1;

                ObservableList<Autre_frais> list = FXCollections.observableArrayList();

                String SqlAf = "SELECT af_date, af_libelle, af_montant FROM autres_frais JOIN fiche ON fk_fiche_af = fi_id WHERE fk_vi = '"
                        + Common.login + "' AND af_Est_Validee = '0' AND fi_mois = '" + month + "';";
                ResultSet resultAF = statement.executeQuery(SqlAf);

                while (resultAF.next()) {
                    if (i != 1) {
                        i += 3;
                    }
                    String date = resultAF.getString(i);
                    String libelle = resultAF.getString(i + 1);
                    double montant = Double.parseDouble(resultAF.getString(i + 2));
                    list.add(new Autre_frais(date, libelle, montant));

                }

                Af_Date.setCellValueFactory(new PropertyValueFactory<Autre_frais, Date>("Af_Date"));
                Af_libelle.setCellValueFactory(new PropertyValueFactory<Autre_frais, String>("Af_libelle"));
                Af_montant.setCellValueFactory(new PropertyValueFactory<Autre_frais, Double>("Af_montant"));

                AF.setItems(list);
            }

        } catch (

        SQLException ex) {
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
