package com.example.miniprojettpandroid;

public class Platt {
    private int id;
    private String nom;
    private int dure;
    private int idRep;

    public Platt(String nom, int dure, int idRepas) {
        this.nom = nom;
        this.dure = dure;
        this.idRep = idRepas;
    }

    public Platt(int id, String nom, int dure) {
        this.id = id;
        this.nom = nom;
        this.dure = dure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDure() {
        return dure;
    }

    public void setDure(int dure) {
        this.dure = dure;
    }

    public int getIdRep() {
        return idRep;
    }

    public void setIdRep(int idRep) {
        this.idRep = idRep;
    }
}
