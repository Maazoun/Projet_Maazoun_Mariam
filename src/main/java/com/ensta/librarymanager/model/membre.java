package com.ensta.librarymanager.model;

public class membre {
    private int id ;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private abonnement abonnement;

    public membre(){}
    public membre(int id)
    {this.id=id; }

    public membre(int id,String nom,String prenom,String telephone,String email,String adresse,abonnement abonnement)
    {this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.telephone=telephone;
        this.email=email;
        this.adresse=adresse;
        this.abonnement=abonnement;}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "membre{"+
                "id="+id +
                 " ,nom= " + nom + " ,prenom= " + prenom + " ,adresse= " + adresse + " , email= " + email
                + " ,telephone= " + telephone +", abonnement=" + abonnement +
                '}';
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAbonnement(abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public abonnement getAbonnement() {
        return abonnement;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}
