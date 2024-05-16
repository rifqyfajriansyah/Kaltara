package com.example.roadmanagement.kaltara.Formu;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class UnForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Session session;
    TextView textRuas;
    TextView textProvinsi;
    TextView textView;
    Spinner spinTipe;
    Spinner spinposisi;

    CardView addUnform;

    List<String> ltipe = new ArrayList<>();
    List<String> lposisi = new ArrayList<>();

    int index;

    DbBahu dbBahu;
    DbLahan dbLahan;
    DbSaluran  dbSaluran;
    DbMedian dbMedian;
    DbLane dbLane;
    DbTemporari dbTemporari;

    String tipe = null;
    String posisi = null;

    UnFormAdapter unFormAdapter;
    RecyclerView unformRecycle;

    ArrayList<DataTemporari> listUnform = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_form);
        textProvinsi = findViewById(R.id.unformprovinsi);
        textRuas = findViewById(R.id.unformruas);
        textView = findViewById(R.id.textunform);
        spinTipe = findViewById(R.id.unformtipe);
        spinposisi = findViewById(R.id.unformposisi);
        unformRecycle = findViewById(R.id.unformrecycle);
        addUnform = findViewById(R.id.unformtambah);

        session = new Session(this);
        textProvinsi.setText(session.getKodeprov());
        textRuas.setText(session.getNoruas());

        dbBahu = new DbBahu(this);
        dbLahan = new DbLahan(this);
        dbSaluran = new DbSaluran(this);
        dbMedian = new DbMedian(this);
        dbLane = new DbLane(this);
        dbTemporari = new DbTemporari(this);

        initDataTipe();



        addUnform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i;

                switch (tipe){
                    case "Lahan" : index = dbLahan.getIndexLahanUn(session.getKodeprov(), session.getNoruas());
                        i  = new Intent(UnForm.this, UnformEdit.class);
                        break;

                    case "Saluran" : index = dbSaluran.getIndexSaluran(session.getKodeprov(), session.getNoruas());
                        i  = new Intent(UnForm.this, UnformEdit.class);
                        break;

                    case "Bahu" : index = dbBahu.getIndexBahuUn(session.getKodeprov(), session.getNoruas());
                        i  = new Intent(UnForm.this, UnformEdit.class);
                        break;

                    case "Median" : index = dbMedian.getIndexMedian(session.getKodeprov(), session.getNoruas());
                        i  = new Intent(UnForm.this, UnformEdit.class);
                    posisi = "";
                        break;

                    case "Lane" : index = dbLane.getIndexLaneUn(session.getKodeprov(), session.getNoruas());
                        i  = new Intent(UnForm.this, FormLaneUtama.class);
                        break;

                        default: i  = new Intent(UnForm.this, UnformEdit.class);
                }
                i.putExtra("tipe", tipe);
                i.putExtra("posisi", posisi);
                i.putExtra("id", index);
                i.putExtra("dari", "Unform");
                //Toast.makeText(UnForm.this, String.valueOf(index), Toast.LENGTH_LONG).show();
                startActivity(i);
                //Toast.makeText(UnForm.this, String.valueOf(index), Toast.LENGTH_SHORT).show();
            }
        });


        if(getIntent().hasExtra("tipe")){
            spinTipe.setSelection(Integer.valueOf(getIntent().getExtras().get("tipe").toString()));
            if(getIntent().hasExtra("posisi")){
                spinposisi.setSelection(Integer.valueOf(getIntent().getExtras().get("posisi").toString()));
            }
        }

    }

    private void initData(String tipeku, String posisiku){

        listUnform.clear();

        listUnform = dbTemporari.getSinkronUnform(session.getNoruas(),tipeku, posisiku);


        unFormAdapter = new UnFormAdapter(listUnform, this);

        unformRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        unformRecycle.setHasFixedSize(true);
        unformRecycle.setAdapter(unFormAdapter);


        unFormAdapter.notifyDataSetChanged();

    }

    public void initDataTipe(){
        String text[] = {"--Tipe--", "Lahan","Bahu","Saluran","Lane","Median"};
        for (int i = 0; i < text.length; i++) {
            ltipe.add(text[i]);
        }
        String[] spinnerArray = new String[ltipe.size()];
        ltipe.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinTipe.setAdapter(spinnerArrayAdapter);
        spinTipe.setOnItemSelectedListener(this);

    }

    public void initDataPosisi(String tipeku){
        lposisi.clear();
        String[] text;
        if(tipeku.equals("--Tipe--")||tipeku.equals("Median")) {
            text = new String[]{"--Posisi--"};

        }else{
            if (tipeku.equals("Lane")) {
                text = new String[]{"--Posisi--", "L1", "L2", "L3", "L4", "R1", "R2", "R3", "R4"};
            } else {
                text = new String[]{"--Posisi--", "kiri", "kanan"};
            }
        }
        for (int i = 0; i < text.length; i++) {
            lposisi.add(text[i]);
        }
        String[] spinnerArray = new String[lposisi.size()];
        lposisi.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinposisi.setAdapter(spinnerArrayAdapter);
        spinposisi.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cekParams();
        if(parent.getId()==R.id.unformtipe){
            tipe = ltipe.get(position);
            initDataPosisi(tipe);

            if(ltipe.get(position).equals("Median") || ltipe.get(position).equals("Lane")){
                addUnform.setVisibility(View.VISIBLE);
                spinposisi.setSelection(0);
                initData(tipe, null);
            }else if(!ltipe.get(position).equals("--Tipe--")){
                initData(ltipe.get(position), null);
                spinposisi.setSelection(0);

            }else{
                addUnform.setVisibility(View.GONE);
                tipe = null;
            }

        }else if(parent.getId()==R.id.unformposisi){

            if(position!=0){
                addUnform.setVisibility(View.VISIBLE);
                posisi = lposisi.get(position);
                initData(tipe, posisi);
            }else{
                posisi =null;
                if(tipe!=null){
                    if(tipe.equals("Median") || tipe.equals("Lane")){
                    addUnform.setVisibility(View.VISIBLE);
                    }else {
                        addUnform.setVisibility(View.GONE);
                    }
                }else{
                    addUnform.setVisibility(View.GONE);
                }
            }



        }





    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void cekParams(){
        if(tipe!=null){
            if(listUnform.size()>0){
                unformRecycle.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            }else{
                unformRecycle.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.setText("Tidak ada data");
            }
        }else{
            unformRecycle.setVisibility(View.GONE);
            addUnform.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Silahkan lengkapi data terlebih dahulu");
        }
    }

}
