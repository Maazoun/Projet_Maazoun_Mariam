package com.ensta.librarymanager.model;
import java.time.LocalDate;

public class emprunt {
    private int  id ;
    private membre membre;
    private livre livre;
    private static LocalDate dateEmprunt;
    private static LocalDate dateRetour;

    public emprunt(){}
    public emprunt(int id)
    {this.id=id;}
    public emprunt(int id,membre membre,livre livre,LocalDate dateEmprunt, LocalDate dateRetour)
    { this.id=id;
        this.membre=membre;
        this.livre=livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;}

    public emprunt(int id, int idMembre, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this(id);
        this.membre.setId(idMembre);
        this.livre.setId(idLivre);
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLivre(com.ensta.librarymanager.model.livre livre) {
        this.livre = livre;
    }

    public void setMembre(com.ensta.librarymanager.model.membre membre) {
        this.membre = membre;
    }

    public com.ensta.librarymanager.model.membre getMembre() {
        return membre;
    }

    public com.ensta.librarymanager.model.livre getLivre() {
        return livre;
    }

    @Override
    public String toString() {
        return "emprunt{" +
                "id=" + id +
                ", membre=" + membre +
                ", livre=" + livre +
                ",date emprunt="+dateEmprunt+
                ",date retour="+dateRetour+
                '}';
    }

    public static LocalDate getDateRetour() {
        return dateRetour;
    }

    public static void setDateRetour(LocalDate dateRetour) {
        emprunt.dateRetour = dateRetour;
    }

    public static void setDateEmprunt(LocalDate dateEmprunt) {
        emprunt.dateEmprunt = dateEmprunt;
    }

    public static LocalDate getDateEmprunt() {
        return dateEmprunt;
    }


}

