package com.example.roadmanagement.kaltara.kolektif;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.UpdateForm.FragmentBahuKolektif;
import com.example.roadmanagement.kaltara.UpdateForm.FragmentLahanKolektif;
import com.example.roadmanagement.kaltara.UpdateForm.FragmentLaneKolektif;
import com.example.roadmanagement.kaltara.UpdateForm.FragmentMedianKolektif;
import com.example.roadmanagement.kaltara.UpdateForm.FragmentSaluranKolektif;
import com.example.roadmanagement.kaltara.UpdateForm.FragmentSegmentKolektif;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class MenuKolektif extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnertipe;
    Spinner spinnerruas;
    Session session;
    DbRuas dbRuas;
    List<String> ltipe = new ArrayList<>();
    List<String> lruas = new ArrayList<>();

    String ruas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_kolektif);
        spinnertipe = (Spinner) findViewById(R.id.kolektifspjenis);
        spinnerruas = (Spinner) findViewById(R.id.kolektifspruas);
        session = new Session(this);
        dbRuas = new DbRuas(this);

        session.saveSPString(Session.POSISI, null);
        session.saveSPString(Session.POSISILAJUR, null);
        session.saveSPInt(Session.SP_NOSEGMENT, 1);

        initDataruas();


    }

    public void initDataTipe(){
        String text[] = {"--Tipe--", "Lahan","Bahu","Saluran","Median","Lane","Segment"};
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
        spinnertipe.setAdapter(spinnerArrayAdapter);
        spinnertipe.setOnItemSelectedListener(this);

    }

    public void initDataruas(){

        lruas = dbRuas.getRuasDownload(String.valueOf(session.getKodeprov()));
        String[] spinnerArray = new String[lruas.size()];
        lruas.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinnerruas.setAdapter(spinnerArrayAdapter);
        spinnerruas.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if(parent.getId()==R.id.kolektifspjenis){


            switch (position) {
                case 0: getSupportFragmentManager().beginTransaction()
                        .replace(R.id.halamankolektif, Fragmentnormal.newInstance()).commit();
                break;
                case 1:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.halamankolektif, FragmentLahanKolektif.newInstance(3, ruas, "halaman")).commit();
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.halamankolektif, FragmentBahuKolektif.newInstance(3, ruas, "halaman")).commit();
                    break;
                case 3:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.halamankolektif, FragmentSaluranKolektif.newInstance(3, ruas, "halaman")).commit();
                    break;

                case 4:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.halamankolektif, FragmentMedianKolektif.newInstance(3, ruas, "halaman")).commit();
                    break;
                case 5:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.halamankolektif, FragmentLaneKolektif.newInstance(3, ruas, "halaman")).commit();
                    break;
                case 6:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.halamankolektif, FragmentSegmentKolektif.newInstance(3, ruas)).commit();
                    break;
            }

        }else if(parent.getId()==R.id.kolektifspruas){

            if(position!=0) {
                ltipe.clear();
                initDataTipe();
                ruas = lruas.get(position);
                spinnertipe.setSelection(0);
            }else{
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.halamankolektif, Fragmentnormal.newInstance()).commit();
            }


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
