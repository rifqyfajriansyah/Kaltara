package com.example.roadmanagement.kaltara.kolektif;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.databaseHelper.DbDownloadRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class KolektifMenuutama extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<String> list = new ArrayList<>();
    DbRuas dbRuas;
    Spinner spinner;

    Session session;

    CardView buttonlahan;
    CardView buttonsaluran;
    CardView buttonbahu;
    CardView buttonmedian;
    CardView buttonlane;
    CardView buttonsegment;

    TextView noprov;

    String sruas;
    Intent i;

    Context context;

    DbDownloadRuas dbDownloadRuas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kolektif_menuutama);

        session = new Session(this);
        dbRuas = new DbRuas(KolektifMenuutama.this);

        context =  KolektifMenuutama.this;
        dbDownloadRuas = new DbDownloadRuas(this);

        //list = dbRuas.getRuas(String.valueOf(session.getKodeprov()));

        spinner = findViewById(R.id.spinnerkolektif);
        buttonlahan = findViewById(R.id.buttonlahan);
        buttonsaluran = findViewById(R.id.buttonsaluran);
        buttonbahu = findViewById(R.id.buttonbahu);
        buttonmedian = findViewById(R.id.buttonmedian);
        buttonlane = findViewById(R.id.buttonlane);
        buttonsegment = findViewById(R.id.buttonsegment);

        noprov = findViewById(R.id.judulprovinsi);

        noprov.setText(String.valueOf(session.getKodeprov()));

        initRuas();


    }

    public void initRuas(){
        list = dbRuas.getRuasDownload(String.valueOf(session.getKodeprov()));
        String[] spinnerArray = new String[list.size()];
        list.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (KolektifMenuutama.this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(KolektifMenuutama.this);

        if(session.getNoruas()!=null){
            for(int i=0; i<list.size();i++){
                if(session.getNoruas().equals(list.get(i))){
                    spinner.setSelection(i);
                    //Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            sruas = list.get(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
