package com.example.miniprojettpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
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

public class Ingridiant extends AppCompatActivity {

    TextView titleRepas,heurRepas;
    LinearLayout editRepas,btnAdd,listJour,selected;
    ArrayList<ListIngridiant> Items;
    CustomAdapter myadpter;
    ListView listPlat;
    Bundle extras;

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
        heurRepas.setText(extras.getString("duree"));
        selected = (LinearLayout) listJour.getChildAt(Integer.parseInt(extras.getString("jour")));
        selected.setBackgroundResource(R.drawable.button_background_primary);

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

            }
        });

    }

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

            Items.add(new ListIngridiant(nomIngredient.getText().toString(),"60",museur.getSelectedItem().toString()));
            myadpter.notifyDataSetChanged();
            dialog.dismiss();
        });

        close.setOnClickListener(v1 -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    //donne cette method en a busoi seulement ID de element puis enva recuper dant la base de donnée
    //Donc apre il sura private void openDialog(int identifiant)

    private void openDialog(String nom,int heurs,int minuts){
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
            Toast.makeText(Ingridiant.this,"La modification est bien fait",3000);
            dialog.dismiss();
        });
    }

    //Pour charger dant la base de donnée direct les list dant listView
    //cet Method doit etre modifier et adapte pour la base de donnée
    // enva fair un information fausse
    public void setListView(){
        Items.add(new ListIngridiant("Farin","500","g"));
        Items.add(new ListIngridiant("Levure séche","10","g"));
        Items.add(new ListIngridiant("Surce","60","g"));
        Items.add(new ListIngridiant("Sel","10","g"));
        Items.add(new ListIngridiant("Lait","200","ml"));
        Items.add(new ListIngridiant("Oeufs","2",""));
        Items.add(new ListIngridiant("Beurre","200","g"));
        myadpter.notifyDataSetChanged();
    }

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
                    //Intent activityPlat = new Intent(MainActivity.this,Plat.class);
                    //activityPlat.putExtra("title",title.getText().toString());
                    //activityPlat.putExtra("heur",heur.getText().toString());
                    //activityPlat.putExtra("jour",String.valueOf(numeroJour));
                    //startActivity(activityPlat);
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Items.remove(position);
                    myadpter.notifyDataSetChanged();
                    Toast.makeText(Ingridiant.this,"Element est bien suppermier",3000).show();
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Items.remove(position);
                    myadpter.notifyDataSetChanged();
                    Toast.makeText(Ingridiant.this,"Element est bien suppermier",3000).show();
                }
            });

            title.setText(Items.get(position).name);
            duree.setText(Items.get(position).quantity);
            myadpter.notifyDataSetChanged();

            return view;
        }
    }
}