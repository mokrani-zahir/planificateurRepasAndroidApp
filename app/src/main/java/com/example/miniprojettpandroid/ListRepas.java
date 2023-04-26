package com.example.miniprojettpandroid;

public class ListRepas {
    public int idRepas;
    public String name;
    public String heur;
    ListRepas(String name, String heur){
        this.name = name;
        this.heur = heur;
    }

    ListRepas(String name, String heur,int idRepas){
        this.name = name;
        this.heur = heur;
        this.idRepas = idRepas;
    }
}
