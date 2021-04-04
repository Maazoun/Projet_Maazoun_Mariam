package com.ensta.librarymanager.model;

public class livre {
    private int id ;
    private String titre;
    private String auteur;
    private String isbn;

    public livre(){}
    public livre(int id,String titre,String auteur,String isbn)
    { this.auteur=auteur;
        this.id=id;
        this.isbn=isbn;
        this.titre=titre;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuteur() {
        return auteur;
    }

    @Override
    public String toString() {
        return "livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
