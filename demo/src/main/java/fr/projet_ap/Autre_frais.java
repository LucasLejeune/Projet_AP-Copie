package fr.projet_ap;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Autre_frais {
    private String Af_Date;
    private String Af_libelle;
    private double Af_montant;
    private BooleanProperty selected = new SimpleBooleanProperty(false);
    private int id;

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public String getAf_Date() {
        return Af_Date;
    }

    public void setAf_Date(String af_Date) {
        Af_Date = af_Date;
    }

    public String getAf_libelle() {
        return Af_libelle;
    }

    public void setAf_libelle(String af_libelle) {
        Af_libelle = af_libelle;
    }

    public double getAf_montant() {
        return Af_montant;
    }

    public void setAf_montant(double af_montant) {
        Af_montant = af_montant;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public boolean isSelected() {
        return selected.get();
    }

    public int GetId() {
        return id;
    }

    public Autre_frais(int id, String date, String libelle, double montant) {
        this.id = id;
        this.Af_Date = date;
        this.Af_libelle = libelle;
        this.Af_montant = montant;
    }

}
