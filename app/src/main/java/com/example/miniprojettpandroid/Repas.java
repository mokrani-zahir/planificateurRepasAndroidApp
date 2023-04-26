package com.example.miniprojettpandroid;

public class Repas {
    private int id;
    private String nom;
    private String heur;
    private int numjour;

    public Repas(String nom, String heur, int numjour) {
        this.nom = nom;
        this.heur = heur;
        this.numjour = numjour;
    }

    public Repas(int id, String nom, String heur, int numjour) {
        this.id = id;
        this.nom = nom;
        this.heur = heur;
        this.numjour = numjour;
    }

    public Repas(int id,String nom, String heur) {
        this.id = id;
        this.heur = heur;
        this.nom = nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setHeur(String heur) {
        this.heur = heur;
    }

    public void setNumjour(int numjour) {
        this.numjour = numjour;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getHeur() {
        return heur;
    }

    public int getNumjour() {
        return numjour;
    }
}
