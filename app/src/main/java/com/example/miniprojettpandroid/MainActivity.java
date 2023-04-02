package com.example.miniprojettpandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    LinearLayout btnDim, btnLun, btnMar, btnMer, btnJed, btnVen, btnSam, btnAdd;
    String TAG = "app";
    ListView listRepas;
    ArrayList<ListRepas> Items;
    MyCustomAdapter myadpter;


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

        Items = new ArrayList<ListRepas>();
        listRepas = (ListView) findViewById(R.id.list_repas);

        myadpter = new MyCustomAdapter(Items);
        listRepas.setAdapter(myadpter);




        btnDim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Clicke");
            }
        });

        btnLun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Clicke");
            }
        });

        btnMar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Clicke");
            }
        });

        btnMer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Clicke");
            }
        });

        btnJed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Clicke");
            }
        });

        btnVen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Clicke");
            }
        });

        btnSam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Clicke");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this,R.style.Dialog_Custom);
                dialog.setContentView(R.layout.dialog_repas);
                EditText nomRepas = (EditText) dialog.findViewById(R.id.edittext_add_repa);
                NumberPicker heur = (NumberPicker)dialog.findViewById(R.id.edit_repas_heur);
                NumberPicker minut = (NumberPicker)dialog.findViewById(R.id.edit_repas_min);
                NumberPicker amOrpm = (NumberPicker)dialog.findViewById(R.id.edit_repas_ampm);
                LinearLayout save = (LinearLayout)dialog.findViewById(R.id.save_add_repa);
                LinearLayout close = (LinearLayout)dialog.findViewById(R.id.close_add_repa);

                amOrpm.setMaxValue(1);
                amOrpm.setMinValue(0);
                String[] textValues = {"AM", "PM"};
                amOrpm.setDisplayedValues(textValues);

                dialog.show();

                close.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });

                save.setOnClickListener(v1 -> {
                    Items.add(new ListRepas(nomRepas.getText().toString(),heur.getValue()+":"+minut.getValue()));
                    dialog.dismiss();
                });
            }
        });
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

            title.setText(Items.get(position).name);
            heur.setText(Items.get(position).heur);
            myadpter.notifyDataSetChanged();



            return view;
        }
    }
}