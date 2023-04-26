package com.example.miniprojettpandroid;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Bass extends SQLiteOpenHelper {



    public Bass(@Nullable Context context) {
        super(context, "gestionne", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Rep(_id INTEGER PRIMARY KEY,nom TEXT,  heur TEXT, numjour INTEGER)");
        db.execSQL("CREATE TABLE Plat(_idP INTEGER PRIMARY KEY,nom TEXT,  dure INTEGER , idRep INTEGER )");
        db.execSQL("CREATE TABLE ingrediant(_idI INTEGER PRIMARY KEY,nom TEXT,  unity TEXT, quantity INTEGER , idPl INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Rep");
        db.execSQL("DROP TABLE IF EXISTS Plat");
        db.execSQL("DROP TABLE IF EXISTS ingrediant");
        onCreate(db);
    }

    public Cursor getALLPlat(int numbre){
        SQLiteDatabase db=this.getReadableDatabase();

        Log.i("salimBdd","SELECT * FROM Plat WHERE idRep='"+numbre+"'");
        Cursor c =db.rawQuery("SELECT * FROM Plat WHERE idRep='"+numbre+"'",null);


        return c;
    }

    public Cursor getALLIngrediant(int numbre){
        SQLiteDatabase db=this.getReadableDatabase();

        Log.i("salimBdd","SELECT * FROM ingrediant WHERE idPl='"+numbre+"'");
        Cursor c =db.rawQuery("SELECT * FROM ingrediant WHERE idPl='"+numbre+"'",null);

        return c;
    }

    public Cursor getALLg(int numbre){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c =db.rawQuery("SELECT * FROM Rep WHERE numjour='"+numbre+"'",null);


        return c;
    }

    public void modifierPlat(Platt r){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put("nom",r.getNom());
        c.put("dure",r.getDure());
        db.update("plat",c,"_idP=?",new String[]{String.valueOf(r.getId())});
        db.close();

    }

    public void modifier(Repas r){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put("nom",r.getNom());
        c.put("heur",r.getHeur());
        db.update("Rep",c,"_id=?",new String[]{String.valueOf(r.getId())});
        db.close();

    }

    public void modifierIngrediant(Ingrediants r){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put("nom",r.getNom());
        c.put("unity",r.getUnity());
        c.put("quantity",r.getQuantity());

        db.update("ingrediant",c,"_idI=?",new String[]{String.valueOf(r.getIdIngrediants())});
        db.close();

    }


    public void supprimerRep(int id){
        SQLiteDatabase db= this.getWritableDatabase();

        db.delete("Rep","_id=?",new String[]{String.valueOf(id)});

        db.close();

    }

    public void supprimerPlat(int id){
        SQLiteDatabase db= this.getWritableDatabase();

        db.delete("Plat","_idP=?",new String[]{String.valueOf(id)});

        db.close();

    }

    public void supprimerIngrediant(int id){
        SQLiteDatabase db= this.getWritableDatabase();

        db.delete("ingrediant","_idI=?",new String[]{String.valueOf(id)});

        db.close();

    }

    public  void insetRep(Repas r){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c= new ContentValues();
        c.put("nom",r.getNom());
        c.put("heur",r.getHeur());
        c.put("numjour",r.getNumjour());
        db.insert("Rep",null,c);
        db.close();
    }

    public  void insetPlat(Platt r){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c= new ContentValues();
        c.put("nom",r.getNom());
        c.put("dure",r.getDure());
        c.put("idRep",r.getIdRep());
        db.insert("Plat",null,c);
        db.close();
    }

    public  void insetIngrediant(Ingrediants r){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c= new ContentValues();
        Log.e("salimBDDingr","bien save");
        c.put("nom",r.getNom());
        c.put("unity",r.getUnity());
        c.put("quantity",r.getQuantity());
        c.put("idPl",r.getIdPlat());
        db.insert("ingrediant",null,c);
        db.close();
    }

}
