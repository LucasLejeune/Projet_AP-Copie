package fr.projet_ap;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AffichageFiche {
    private int numero;
    private String mois;
    private String etat;
    private String numVisiteur;
    private BooleanProperty selected = new SimpleBooleanProperty(false);

    public AffichageFiche(int numero, String mois, String etat, String numVisiteur) {
        this.numero = numero;
        this.mois = mois;
        this.etat = etat;
        this.numVisiteur = numVisiteur;

    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public boolean isSelected() {
        return selected.get();
    }

    public String getNumVisiteur() {
        return numVisiteur;
    }

    public void setNumVisiteur(String numVisiteur) {
        this.numVisiteur = numVisiteur;
    }

    public String getNumero() {
        return String.valueOf(numero);
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
