package fr.projet_ap;

import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;

public class SuiviPaiementController {

    @FXML
    private TableColumn<AffichageFiche, String> Etat;

    @FXML
    private TableColumn<AffichageFiche, String> Mois;

    @FXML
    private TableColumn<AffichageFiche, Integer> NumeroLigne;

    @FXML
    private TableColumn<AffichageFiche, String> NumeroVisiteur;

    @FXML
    private TableColumn<AffichageFiche, Boolean> Selection;

    @FXML
    private TableView<AffichageFiche> TVfiches;

    public void initialize() {

        String dbURL = "jdbc:mysql://localhost:3306/projet_AP";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            int i = 1;

            ObservableList<AffichageFiche> list = FXCollections.observableArrayList();

            String SqlAf = "SELECT fi_id, fi_mois, fi_remboursement, fk_vi FROM fiche ;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SqlAf);

            while (result.next()) {
                if (i != 1) {
                    i += 4;
                }
                int numero = Integer.parseInt(result.getString(i));
                String mois = result.getString(i + 1);
                String etat = result.getString(i + 2);
                String numVisiteur = result.getString(i + 3);
                list.add(new AffichageFiche(numero, mois, etat, numVisiteur));

            }

            NumeroLigne.setCellValueFactory(new PropertyValueFactory<AffichageFiche, Integer>("numero"));
            NumeroVisiteur.setCellValueFactory(new PropertyValueFactory<AffichageFiche, String>("numVisiteur"));
            Mois.setCellValueFactory(new PropertyValueFactory<AffichageFiche, String>("mois"));
            Etat.setCellValueFactory(new PropertyValueFactory<AffichageFiche, String>("etat"));
            Selection.setCellValueFactory(new PropertyValueFactory<AffichageFiche, Boolean>("selected"));
            Selection.setCellFactory(CheckBoxTableCell.forTableColumn(Selection));

            TVfiches.setItems(list);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void ouvrirFiche() throws IOException {
        for (AffichageFiche p : TVfiches.getItems()) {
            if (p.isSelected() == true) {
                Common.Matricule = Integer.parseInt(p.getNumVisiteur());
                Common.numeroFiche = Integer.parseInt(p.getNumero());
            } else {
                System.out.println("test");
            }
        }
        App.setRoot("ModifSuivi");
    }

    @FXML
    void back() throws IOException {
        App.setRoot("ClientC");
    }

}
