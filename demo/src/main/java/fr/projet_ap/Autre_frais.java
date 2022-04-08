package fr.projet_ap;

public class Autre_frais {
    private String Af_Date;
    private String Af_libelle;
    private double Af_montant;
    private Boolean Af_Validation = false;

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

    public Boolean getAf_Validation() {
        return Af_Validation;
    }

    public void setAf_Validation(Boolean af_Validation) {
        Af_Validation = af_Validation;
    }

    public Autre_frais(String date, String libelle, double montant) {
        this.Af_Date = date;
        this.Af_libelle = libelle;
        this.Af_montant = montant;
    }

}
