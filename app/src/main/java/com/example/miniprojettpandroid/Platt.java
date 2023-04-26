package com.example.miniprojettpandroid;

public class Platt {
    private int id;
    private String nom;
    private String dure;
    private int idRep;

    public Platt(String nom, String dure, int idRepas) {
        this.nom = nom;
        this.dure = dure;
        this.idRep = idRepas;
    }

    public Platt(int id, String nom, String dure) {
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

    public String getDure() {
        return dure;
    }

    public void setDure(String dure) {
        this.dure = dure;
    }

    public int getIdRep() {
        return idRep;
    }

    public void setIdRep(int idRep) {
        this.idRep = idRep;
    }
}
