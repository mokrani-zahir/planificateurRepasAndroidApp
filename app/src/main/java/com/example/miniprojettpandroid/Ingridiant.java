package com.example.miniprojettpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Iterator;

public class Ingridiant extends AppCompatActivity {

    TextView titleRepas,heurRepas,grandTitreActivity;
    LinearLayout editRepas,btnAdd,listJour,selected;
    ArrayList<ListIngridiant> Items;
    CustomAdapter myadpter;
    ListView listPlat;
    Bundle extras;
    int idPlat;
    Bass b = new Bass(Ingridiant.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plat);

        titleRepas = (TextView) findViewById(R.id.titre_repas_edit);
        heurRepas = (TextView) findViewById(R.id.heur_reap_edit);

        grandTitreActivity = (TextView) findViewById(R.id.grandTitreActivity);
        grandTitreActivity.setText("List des ingrediant");

        btnAdd = (LinearLayout) findViewById(R.id.btnAdd);
        editRepas = (LinearLayout) findViewById(R.id.edit_repas);

        listJour = (LinearLayout) findViewById(R.id.list_jour);


        extras = getIntent().getExtras();

        titleRepas.setText(extras.getString("title"));
        heurRepas.setText(extras.getString("duree"));
        idPlat = Integer.parseInt(extras.getString("id"));

        selected = (LinearLayout) listJour.getChildAt(Integer.parseInt(extras.getString("jour")));
        selected.setBackgroundResource(R.drawable.button_background_seconde);

        //configuration de listview
        Items = new ArrayList<ListIngridiant>();
        listPlat = (ListView) findViewById(R.id.list_repas);

        myadpter = new CustomAdapter(Items);
        listPlat.setAdapter(myadpter);


        //charger les repas dant la base de donnée et affichier dant listView
        setListView();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        editRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] heur = extras.getString("duree").split(":");
                openDialogPlat(
                        extras.getString("title").toString(),
                        Integer.parseInt(heur[0]),
                        Integer.parseInt(heur[1])
                );
            }
        });

    }

    /**
     * Affichier une dialog (pop up) pour ajouter un ingridiant
     * Utiliser la method putIngridiant()
     *
     */
    private void openDialog(){
        Dialog dialog = new Dialog(Ingridiant.this,R.style.Dialog_Custom);
        dialog.setContentView(R.layout.dialog_ingredient);

        Spinner museur = (Spinner) dialog.findViewById(R.id.spinner_mesur);
        LinearLayout close = (LinearLayout)dialog.findViewById(R.id.close_ingredient);
        LinearLayout save = (LinearLayout)dialog.findViewById(R.id.save_ingredient);

        LinearLayout linea_edit_ingredient = (LinearLayout) dialog.findViewById(R.id.linear_ingredient);
        EditText nomIngredient = (EditText) dialog.findViewById(R.id.editText_ingredient);

        LinearLayout linea_edit_quantity = (LinearLayout) dialog.findViewById(R.id.linear_quantity);
        EditText quantity = (EditText) dialog.findViewById(R.id.editText_quantity);


        //pour adapter le Spinner pour le desgin
        ArrayAdapter adapter = ArrayAdapter.createFromResource(Ingridiant.this,R.array.spinner_items,R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        museur.setAdapter(adapter);

        save.setOnClickListener(v1 -> {



            if(nomIngredient.getText().toString().trim().isEmpty()){
                linea_edit_ingredient.setBackgroundResource(R.drawable.edit_background_red);
                return;
            }

            if(quantity.getText().toString().trim().isEmpty()){
                linea_edit_quantity.setBackgroundResource(R.drawable.edit_background_red);
                return;
            }

            Log.i("salim","click save");

            idPlat = Integer.parseInt(extras.getString("id"));
            Log.i("salim","idPlat : "+idPlat);


            Ingrediants r = new Ingrediants(
                    nomIngredient.getText().toString(),
                    museur.getSelectedItem().toString(),
                    Integer.parseInt(quantity.getText().toString()),
                    idPlat);

            b.insetIngrediant(r);

            setListView();

            dialog.dismiss();
        });

        close.setOnClickListener(v1 -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    //donne cette method en a busoi seulement ID de element puis enva recuper dant la base de donnée
    //Donc apre il sura private void openDialog(int identifiant)

    /**
     * Affichier une dialog (pop up) pour modifier un ingridiant
     * Recuper les proprité de ingridiant dant la base de donnée et affichier dant EditText et Spinner
     * Utiliser la method setIngridiant()
     *
     * @version 0.1
     * @param idIngr identifiant ingredient (SELECT * FROM ingredient WHERE id='idIngr')
     */
    private void openDialog(int idIngr){
        Dialog dialog = new Dialog(Ingridiant.this,R.style.Dialog_Custom);
        dialog.setContentView(R.layout.dialog_ingredient);

        TextView title = (TextView) dialog.findViewById(R.id.title_ingredient_editAdd);
        Spinner museur = (Spinner) dialog.findViewById(R.id.spinner_mesur);
        LinearLayout close = (LinearLayout)dialog.findViewById(R.id.close_ingredient);
        LinearLayout save = (LinearLayout)dialog.findViewById(R.id.save_ingredient);

        LinearLayout linea_edit_ingredient = (LinearLayout) dialog.findViewById(R.id.linear_ingredient);
        EditText nomIngredient = (EditText) dialog.findViewById(R.id.editText_ingredient);

        LinearLayout linea_edit_quantity = (LinearLayout) dialog.findViewById(R.id.linear_quantity);
        EditText quantity = (EditText) dialog.findViewById(R.id.editText_quantity);


        //pour adapter le Spinner pour le desgin
        ArrayAdapter adapter = ArrayAdapter.createFromResource(Ingridiant.this,R.array.spinner_items,R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        museur.setAdapter(adapter);

        title.setText("Modifié un ingrédient");

        save.setOnClickListener(v1 -> {

            if(nomIngredient.getText().toString().trim().isEmpty()){
                linea_edit_ingredient.setBackgroundResource(R.drawable.edit_background_red);
                return;
            }

            if(quantity.getText().toString().trim().isEmpty()){
                linea_edit_quantity.setBackgroundResource(R.drawable.edit_background_red);
                return;
            }

            Ingrediants r = new Ingrediants(
                    idIngr,
                    nomIngredient.getText().toString(),
                    museur.getSelectedItem().toString(),
                    Integer.parseInt(quantity.getText().toString())
            );

            b.modifierIngrediant(r);

            setListView();



            dialog.dismiss();
        });

        close.setOnClickListener(v1 -> {
            dialog.dismiss();
        });

        dialog.show();
    }


    /**
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * @param
     */
    private void openDialogPlat(String nom,int heurs,int minuts){
        Dialog dialog = new Dialog(Ingridiant.this,R.style.Dialog_Custom);
        dialog.setContentView(R.layout.dialog_repas);

        EditText nomRepas = (EditText) dialog.findViewById(R.id.edittext_add_repa);
        NumberPicker heur = (NumberPicker)dialog.findViewById(R.id.edit_repas_heur);
        NumberPicker minut = (NumberPicker)dialog.findViewById(R.id.edit_repas_min);
        NumberPicker amOrpm = (NumberPicker)dialog.findViewById(R.id.edit_repas_ampm);
        LinearLayout save = (LinearLayout)dialog.findViewById(R.id.save_add_repa);
        LinearLayout close = (LinearLayout)dialog.findViewById(R.id.close_add_repa);
        LinearLayout linear_edite_text = (LinearLayout)dialog.findViewById(R.id.linear_edite_text);
        TextView titreDialog = (TextView)dialog.findViewById(R.id.textView_repas);
        titreDialog.setText("Modifier le plat");

        amOrpm.setMaxValue(0);
        amOrpm.setMinValue(0);
        String[] textValues = {"Min"};
        amOrpm.setDisplayedValues(textValues);

        int minutTotal = heur.getValue()*60+minut.getValue();

        dialog.show();

        close.setOnClickListener(v1 -> {
            dialog.dismiss();
        });

        save.setOnClickListener(v1 -> {
            Toast.makeText(Ingridiant.this,"La modification est bien fait",Toast.LENGTH_LONG);

            titleRepas.setText(nomRepas.getText().toString());

            Platt r = new Platt(
                    idPlat,
                    nomRepas.getText().toString(),
                    heur.getValue()+":"+minut.getValue()
            );
            b.modifierPlat(r);

            dialog.dismiss();
        });
    }
    //Pour charger dant la base de donnée direct les list dant listView pre ouvreteur de activity (page ou application)
    //cet Method doit etre modifier et adapte pour la base de donnée
    // enva fair un information fausse
    //setListView(int identiterPlat) puis recuper la list ingridiant et affichier sur listView

    /**
     * chercher ingridiant de plat dant bas de donnée et affichier dant la listView
     *
     * @version 0.1
     * @param id identifiant de plat (SELECT * FROM ingridiant WHERE idPlat='id')
     * @return void puis ecreer une boucle ajouter a la arry list puis affichier
     */
    public void setListView(){
        //Items.add(new ListIngridiant("Farin",500,"g"));
        Items.removeAll(Items);

        idPlat = Integer.parseInt(extras.getString("id"));
        Log.i("salimPlat", String.valueOf(idPlat));

        Cursor c = b.getALLIngrediant(idPlat);

        for (int i = 0; i < c.getCount(); i++) {

            // Position the cursor
            c.moveToNext();

            // Fetch your data values
            int ids = c.getInt(c.getColumnIndex("_idI"));
            String nom = c.getString(c.getColumnIndex("nom"));
            int qua = c.getInt(c.getColumnIndex("quantity"));
            String museur = c.getString(c.getColumnIndex("unity"));

            Items.add(new ListIngridiant(nom,qua,museur,ids));
        }

        Log.e("salim", String.valueOf(Items.size()));

        myadpter.notifyDataSetChanged();
    }


    /**
     * Ajouter ingridiant au plat dant la base de donnée
     *
     * @version 0.1
     * @param nom Nome de plat
     * @param quantity Quantité de ingridiant
     * @param unity Unité de mesure
     * @param idPlat identifiant de plat a associer (Clé etrangere)
     * @return void puis ecreer une boucle ajouter a la arry list puis affichier
     */
    public void putIngridiant(String nom,int quantity,String unity,int idPlat){
        //Items.add(new ListIngridiant(nom,quantity,unity));
        myadpter.notifyDataSetChanged();
        Toast.makeText(Ingridiant.this,"Ingrediant est bien ajouter",Toast.LENGTH_LONG);
    }

    /**
     * Modifier Ingridiant de plat dant la base de donnée
     *
     * @version 0.1
     * @param idIngr identifiant ingredient
     * @param nom Nouveau nom de l'ingredient
     * @param quantity Nouveau quantité  de l'ingrdiant
     * @param unity Nouveau unity
     */
    public void setIngridiant(int idIngr,String nom,int quantity,String unity){
        Toast.makeText(Ingridiant.this,"Ingrediant est bien modifier",Toast.LENGTH_LONG);
    }

    /**
     * Supprimer ingredient dant la base de donnée
     *
     * @version 0.1
     * @param idIngr identifiant ingredient
     */
    public void deletIngridiant(int idIngr){
        Toast.makeText(Ingridiant.this,"Ingrediant est bien supprimer",Toast.LENGTH_LONG);
    }

    /**
     * C'est une classe pour adapter comportement des items dant la listView
     *
     * @author Mokrani Zahir
     * @version 1.0
     */
    class CustomAdapter extends BaseAdapter {

        ArrayList<ListIngridiant> Items = new ArrayList<ListIngridiant>();
        CustomAdapter(ArrayList<ListIngridiant> Items){
            this.Items = Items;
        }

        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public Object getItem(int position) {
            return Items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_edit_remove,null);

            TextView title = (TextView) view.findViewById(R.id.tilte_item);
            TextView duree = (TextView) view.findViewById(R.id.label_time_item);
            LinearLayout btnEdit = (LinearLayout) view.findViewById(R.id.edit_item);
            LinearLayout btnRemove = (LinearLayout) view.findViewById(R.id.remove_item);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog(Items.get(position).idIngridiant);
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    b.supprimerIngrediant(Items.get(position).idIngridiant);

                    Items.remove(position);
                    myadpter.notifyDataSetChanged();
                    deletIngridiant(0);
                }
            });

            title.setText(Items.get(position).name);
            duree.setText(Items.get(position).quantity + " " + Items.get(position).museur);
            myadpter.notifyDataSetChanged();

            return view;
        }
    }
}