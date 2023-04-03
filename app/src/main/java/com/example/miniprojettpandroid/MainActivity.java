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


public class MainActivity extends AppCompatActivity {

    LinearLayout btnDim, btnLun, btnMar, btnMer, btnJed, btnVen, btnSam, btnAdd, listJour,selected;
    String TAG = "app";
    ListView listRepas;
    ArrayList<ListRepas> Items;
    MyCustomAdapter myadpter;
    int numeroJour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDim = (LinearLayout) findViewById(R.id.btnDim);
        btnLun = (LinearLayout) findViewById(R.id.btnLun);
        btnMar = (LinearLayout) findViewById(R.id.btnMar);
        btnMer = (LinearLayout) findViewById(R.id.btnMer);
        btnJed = (LinearLayout) findViewById(R.id.btnJed);
        btnVen = (LinearLayout) findViewById(R.id.btnVen);
        btnSam = (LinearLayout) findViewById(R.id.btnSam);
        btnAdd = (LinearLayout) findViewById(R.id.btnAdd);
        numeroJour = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) -1;

        //pour selection le jour et changer sont background pour affichier au utlisateur
        listJour = (LinearLayout) findViewById(R.id.list_jour);
        selected = (LinearLayout) listJour.getChildAt(numeroJour);
        selected.setBackgroundResource(R.drawable.button_background_primary);

        //configuration de listview
        Items = new ArrayList<ListRepas>();
        listRepas = (ListView) findViewById(R.id.list_repas);

        myadpter = new MyCustomAdapter(Items);
        listRepas.setAdapter(myadpter);

        //charger les repas dant la base de donnée et affichier dant listView
        setListView();


        btnDim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectionJour(btnDim);
                Log.i(TAG,"Clicke");
            }
        });

        btnLun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectionJour(btnLun);
                btnLun.setBackgroundResource(R.drawable.button_background_primary);
                Log.i(TAG,"Clicke");
            }
        });

        btnMar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectionJour(btnMar);
                Log.i(TAG,"Clicke");
            }
        });

        btnMer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectionJour(btnMer);
                Log.i(TAG,"Clicke");
            }
        });

        btnJed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectionJour(btnJed);
                Log.i(TAG,"Clicke");
            }
        });

        btnVen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectionJour(btnVen);
                Log.i(TAG,"Clicke");
            }
        });

        btnSam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSelectionJour(btnSam);
                Log.i(TAG,"Clicke");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void changeSelectionJour(LinearLayout element){
        int count = listJour.getChildCount();
        for (int i = 0; i < count; i++) {
            LinearLayout linearLaouyJour = (LinearLayout) listJour.getChildAt(i);
            linearLaouyJour.setBackgroundResource(R.drawable.surface_label_background);
            // faire quelque chose avec la vue ici
        }
        element.setBackgroundResource(R.drawable.button_background_primary);
        numeroJour = listJour.indexOfChild(element);
    }

    private void openDialog(){
        int heurUtilisateur = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutUtilisateur = Calendar.getInstance().get(Calendar.MINUTE);
        Dialog dialog = new Dialog(MainActivity.this,R.style.Dialog_Custom);
        dialog.setContentView(R.layout.dialog_repas);

        EditText nomRepas = (EditText) dialog.findViewById(R.id.edittext_add_repa);
        NumberPicker heur = (NumberPicker)dialog.findViewById(R.id.edit_repas_heur);
        NumberPicker minut = (NumberPicker)dialog.findViewById(R.id.edit_repas_min);
        NumberPicker amOrpm = (NumberPicker)dialog.findViewById(R.id.edit_repas_ampm);
        LinearLayout save = (LinearLayout)dialog.findViewById(R.id.save_add_repa);
        LinearLayout close = (LinearLayout)dialog.findViewById(R.id.close_add_repa);
        LinearLayout linear_edite_text = (LinearLayout)dialog.findViewById(R.id.linear_edite_text);



        amOrpm.setMaxValue(1);
        amOrpm.setMinValue(0);
        String[] textValues = {"AM", "PM"};
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
            Items.add(new ListRepas(nomRepas.getText().toString(),heur.getValue()+":"+minut.getValue()));
            myadpter.notifyDataSetChanged();
            dialog.dismiss();
        });
    }

    //Pour charger dant la base de donnée direct les list dant listView
    //cet Method doit etre modifier et adapte pour la base de donnée
    // enva fair un information fausse
    public void setListView(){
        Items.add(new ListRepas("Matin","07:00"));
        Items.add(new ListRepas("Midi","12:00"));
        Items.add(new ListRepas("Collation","16:00"));
        Items.add(new ListRepas("Soir","19:00"));
        myadpter.notifyDataSetChanged();
    }

    class MyCustomAdapter extends BaseAdapter{

        ArrayList<ListRepas> Items = new ArrayList<ListRepas>();
        MyCustomAdapter(ArrayList<ListRepas> Items){
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
            TextView heur = (TextView) view.findViewById(R.id.label_time_item);
            LinearLayout btnEdit = (LinearLayout) view.findViewById(R.id.edit_item);
            LinearLayout btnRemove = (LinearLayout) view.findViewById(R.id.remove_item);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activityPlat = new Intent(MainActivity.this,Plat.class);
                    activityPlat.putExtra("title",title.getText().toString());
                    activityPlat.putExtra("heur",heur.getText().toString());
                    activityPlat.putExtra("jour",String.valueOf(numeroJour));
                    startActivity(activityPlat);
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Items.remove(position);
                    myadpter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,"Element est bien suppermier",3000).show();
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Items.remove(position);
                    myadpter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,"Element est bien suppermier",3000).show();
                }
            });

            title.setText(Items.get(position).name);
            heur.setText(Items.get(position).heur);
            myadpter.notifyDataSetChanged();

            return view;
        }
    }
}