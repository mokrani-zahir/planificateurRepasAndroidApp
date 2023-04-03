package com.example.miniprojettpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Calendar;

public class Plat extends AppCompatActivity {

    TextView titleRepas,heurRepas;
    LinearLayout editRepas,btnAdd,listJour,selected;
    ArrayList<ListPlat> Items;
    CustomAdapter myadpter;
    ListView listPlat;
    Bundle extras;
    int numeroJour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plat);

        titleRepas = (TextView) findViewById(R.id.titre_repas_edit);
        heurRepas = (TextView) findViewById(R.id.heur_reap_edit);
        btnAdd = (LinearLayout) findViewById(R.id.btnAdd);
        editRepas = (LinearLayout) findViewById(R.id.edit_repas);

        listJour = (LinearLayout) findViewById(R.id.list_jour);


        extras = getIntent().getExtras();

        titleRepas.setText(extras.getString("title"));
        heurRepas.setText(extras.getString("heur"));
        numeroJour = Integer.parseInt(extras.getString("jour"));
        selected = (LinearLayout) listJour.getChildAt(numeroJour);
        selected.setBackgroundResource(R.drawable.button_background_primary);

        //configuration de listview
        Items = new ArrayList<ListPlat>();
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
                String[] heur = extras.getString("heur").split(":");
                openDialog(
                        extras.getString("title"),
                        Integer.parseInt(heur[0]),
                        Integer.parseInt(heur[1])
                );
            }
        });

    }

    private void openDialog(){
        int heurUtilisateur = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutUtilisateur = Calendar.getInstance().get(Calendar.MINUTE);
        Dialog dialog = new Dialog(Plat.this,R.style.Dialog_Custom);
        dialog.setContentView(R.layout.dialog_repas);

        EditText nomRepas = (EditText) dialog.findViewById(R.id.edittext_add_repa);
        NumberPicker heur = (NumberPicker)dialog.findViewById(R.id.edit_repas_heur);
        NumberPicker minut = (NumberPicker)dialog.findViewById(R.id.edit_repas_min);
        NumberPicker amOrpm = (NumberPicker)dialog.findViewById(R.id.edit_repas_ampm);
        LinearLayout save = (LinearLayout)dialog.findViewById(R.id.save_add_repa);
        LinearLayout close = (LinearLayout)dialog.findViewById(R.id.close_add_repa);
        LinearLayout linear_edite_text = (LinearLayout)dialog.findViewById(R.id.linear_edite_text);



        amOrpm.setMaxValue(0);
        amOrpm.setMinValue(0);
        String[] textValues = {"Min"};
        amOrpm.setDisplayedValues(textValues);

        heur.setValue(heurUtilisateur);
        minut.setValue(minutUtilisateur);

        dialog.show();

        close.setOnClickListener(v1 -> {
            dialog.dismiss();
        });

        save.setOnClickListener(v1 -> {
            if(nomRepas.getText().toString().trim().isEmpty()){
                linear_edite_text.setBackgroundResource(R.drawable.edit_background_red);
                return;
            }
            Items.add(new ListPlat(nomRepas.getText().toString(),heur.getValue()+":"+minut.getValue()));
            myadpter.notifyDataSetChanged();
            dialog.dismiss();
        });
    }

    //donne cette method en a busoi seulement ID de element puis enva recuper dant la base de donnée
    //Donc apre il sura private void openDialog(int identifiant)

    private void openDialog(String nom,int heurs,int minuts){
        Dialog dialog = new Dialog(Plat.this,R.style.Dialog_Custom);
        dialog.setContentView(R.layout.dialog_repas);

        EditText nomRepas = (EditText) dialog.findViewById(R.id.edittext_add_repa);
        NumberPicker heur = (NumberPicker)dialog.findViewById(R.id.edit_repas_heur);
        NumberPicker minut = (NumberPicker)dialog.findViewById(R.id.edit_repas_min);
        NumberPicker amOrpm = (NumberPicker)dialog.findViewById(R.id.edit_repas_ampm);
        LinearLayout save = (LinearLayout)dialog.findViewById(R.id.save_add_repa);
        LinearLayout close = (LinearLayout)dialog.findViewById(R.id.close_add_repa);
        LinearLayout linear_edite_text = (LinearLayout)dialog.findViewById(R.id.linear_edite_text);
        TextView titreDialog = (TextView)dialog.findViewById(R.id.textView_repas);
        titreDialog.setText("Modifier le repas");
        nomRepas.setText(nom);

        amOrpm.setMaxValue(1);
        amOrpm.setMinValue(0);
        String[] textValues = {"AM", "PM"};
        amOrpm.setDisplayedValues(textValues);

        heur.setValue(heurs);
        minut.setValue(minuts);

        dialog.show();

        close.setOnClickListener(v1 -> {
            dialog.dismiss();
        });

        save.setOnClickListener(v1 -> {
            Toast.makeText(Plat.this,"La modification est bien fait",3000);
            dialog.dismiss();
        });
    }

    //Pour charger dant la base de donnée direct les list dant listView
    //cet Method doit etre modifier et adapte pour la base de donnée
    // enva fair un information fausse
    public void setListView(){
        Items.add(new ListPlat("Lait au chocalate","5 min"));
        Items.add(new ListPlat("Croissant","30 min"));
        Items.add(new ListPlat("Confiture à la frais","50 min"));
        myadpter.notifyDataSetChanged();
    }

    class CustomAdapter extends BaseAdapter {

        ArrayList<ListPlat> Items = new ArrayList<ListPlat>();
        CustomAdapter(ArrayList<ListPlat> Items){
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
                    Intent activityPlat = new Intent(Plat.this,Ingridiant.class);
                    activityPlat.putExtra("title",title.getText().toString());
                    activityPlat.putExtra("duree",duree.getText().toString());
                    activityPlat.putExtra("jour",String.valueOf(numeroJour));
                    startActivity(activityPlat);
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Items.remove(position);
                    myadpter.notifyDataSetChanged();
                    Toast.makeText(Plat.this,"Element est bien suppermier",3000).show();
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Items.remove(position);
                    myadpter.notifyDataSetChanged();
                    Toast.makeText(Plat.this,"Element est bien suppermier",3000).show();
                }
            });

            title.setText(Items.get(position).name);
            duree.setText(Items.get(position).duree);
            myadpter.notifyDataSetChanged();

            return view;
        }
    }
}