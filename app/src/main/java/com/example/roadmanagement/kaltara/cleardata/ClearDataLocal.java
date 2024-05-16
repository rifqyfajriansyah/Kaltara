package com.example.roadmanagement.kaltara.cleardata;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataRuas;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class ClearDataLocal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView provinsi;
    TextView judul;

    Spinner ruas;
    List<String> lruas = new ArrayList<>();
    ArrayList<DataTemporari> dataTemporaris = new ArrayList();
    CardView button;

    Session session;

    DbTemporari dbTemporari;

    String sruas;

    DbRuas dbRuas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_data_local);

        session = new Session(this);

        provinsi = findViewById(R.id.judulprovinsi);
        judul = findViewById(R.id.judultoolbar);
        ruas = findViewById(R.id.clearruas);
        button = findViewById(R.id.cleardata);

        provinsi.setText(session.getKodeprov());
        judul.setText("Clear Data");


        dbRuas = new DbRuas(this);
        dbTemporari = new DbTemporari(this);

        initRuas();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sruas.equals("--Pilih ruas--")){
                    dataTemporaris = dbTemporari.getListTemporari(session.getKodeprov(), sruas, null, null, null, "1",  "0", "1");
                    if(dataTemporaris.size()==0){
                        //dbTemporari.clear();
                        dbRuas.updateSinkronId(session.getKodeprov(), sruas, "0");
                        dbRuas.updateSinkronDetail(session.getKodeprov(), sruas, "0");
                        startActivity(new Intent(ClearDataLocal.this, Menuutama.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }else{
                        Toast.makeText(ClearDataLocal.this, "Silahkan sinkron perubahan pada ruas terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ClearDataLocal.this, "Silahkan pilih ruas", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void initRuas() {
        lruas = dbRuas.getRuasDownload(String.valueOf(session.getKodeprov()));
        String[] spinnerArray = new String[lruas.size()];
        lruas.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (ClearDataLocal.this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        ruas.setAdapter(spinnerArrayAdapter);
        ruas.setOnItemSelectedListener(ClearDataLocal.this);


    }

    public String getkodeprov(int a){
        String prov = null;

        if(a<10){
            prov="0"+String.valueOf(a);
        }else{
            prov = String.valueOf(a);
        }

        return prov;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sruas = lruas.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
