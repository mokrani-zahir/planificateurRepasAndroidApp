package com.example.miniprojettpandroid;

public class Ingrediants {

        private int idIngrediants;
        private String nom;
        private String unity ;
    private int idPlat ;

    public int getIdIngrediants() {
        return idIngrediants;
    }

    public void setIdIngrediants(int idIngrediants) {
        this.idIngrediants = idIngrediants;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity ;

        public Ingrediants(String nom, String unity, int quantity,int idPlat) {

            this.nom = nom;
            this.unity = unity;
            this.quantity = quantity;
            this.idPlat = idPlat;
        }

    public Ingrediants(int idIngrediants,String nom, String unity, int quantity) {
        this.idIngrediants = idIngrediants;
        this.nom = nom;
        this.unity = unity;
        this.quantity = quantity;
    }


    public int getIdPlat() {
        return idPlat;
    }
}
