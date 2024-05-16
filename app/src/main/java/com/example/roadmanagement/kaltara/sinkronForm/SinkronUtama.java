package com.example.roadmanagement.kaltara.sinkronForm;

import android.content.Context;
import android.content.Intent;
import androidx.constraintlayout.solver.widgets.Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.Task.TaskSinkron;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.HelperList;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.helper.SpinnerWarna;

import java.util.ArrayList;

import static android.view.View.GONE;

public class SinkronUtama extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tRuas, tJudul;
    Spinner sTipe, sPosisi, sWarna;
    ImageView imBack;

    String tipeku, posisiku, urutku, fotoku, statusku;

    String[] lTipe, lPosisi, lWarna;

    RecyclerView recyclerView;

    ArrayList<DataTemporari> listRecycle= new ArrayList<>();
    ArrayList<DataTemporari> listNotif= new ArrayList<>();
    ArrayList<DataTemporari> listSinkron= new ArrayList<>();
    ArrayList<DataTemporari> listSegment= new ArrayList<>();

    DbTemporari dbTemporari;

    SinkronAdapter sinkronAdapter;

    Session session;

    TextView tnotif;
    CardView boxnotif;

    String parameter;

    Context context;
    HelperList helperList;

    FloatingActionButton sinkronButton;
    FungsiAPI fungsiAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinkron_utama);

        tRuas = findViewById(R.id.sinkronProvinsi);

        sTipe = findViewById(R.id.sinkronDetail);
        sPosisi = findViewById(R.id.sinkronPosisi);
        sWarna = findViewById(R.id.sinkronWarna);

        tnotif = findViewById(R.id.sinkronnotif);
        boxnotif = findViewById(R.id.boxnotifsinkron);

        sinkronButton = findViewById(R.id.sinkronButton);
        recyclerView  = findViewById(R.id.recyclesinkron);
        imBack = findViewById(R.id.sinkronBack);

        context = SinkronUtama.this;

        dbTemporari = new DbTemporari(this);
        session = new Session(this);
        fungsiAPI = new FungsiAPI(context);

        helperList = new HelperList();

        tRuas.setText(session.getNoruas());


        /*
        if(getIntent().hasExtra("tipe")){
            stipe.setSelection(Integer.valueOf(getIntent().getExtras().get("tipe").toString()));
        }else{
            stipe.setSelection(0);
        }
        */

        initDataTipe();
        initWarna();




        sinkronButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(tipeku.equals("Lane")){

                        listSegment = dbTemporari.getListTemporari(session.getKodeprov(), session.getNoruas(), "Segment", null,  null, "1", "0", "1");
                        if(listSegment.size()==0){
                            fungsiAPI.cekSinkronUrut(session.getKodeprov(), session.getNoruas(), new SendId() {
                                @Override
                                public void hapusGambar(int id) {

                                    TaskSinkron taskSinkron = new TaskSinkron(context, listSinkron, id);
                                    taskSinkron.execute("bisa", "bisa", "bisa");

                                }
                            });
                        }else{

                            Toast.makeText(context, "Silahkan pilih Segment terlebih dahulu", Toast.LENGTH_SHORT).show();

                        }


                    }else {
                        fungsiAPI.cekSinkronUrut(session.getKodeprov(), session.getNoruas(), new SendId() {
                            @Override
                            public void hapusGambar(int id) {

                                TaskSinkron taskSinkron = new TaskSinkron(context, listSinkron, id);
                                taskSinkron.execute("bisa", "bisa", "bisa");

                            }
                        });
                    }


                }
            });

        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Menuutama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });


    }


    private void initData(final String tipe, final String posisi, final String foto, final String status){

        String tipeku = null;
        String posisiku = null;
        String urutku = null;

        if(!tipe.equals("--Pilih--")){
            tipeku = tipe;
        }

        if(posisi!=null) {
            if (posisi.substring(0, 1).equals("k")) {
                posisiku = posisi;
            } else if (posisi.substring(0, 1).equals("L")) {
                posisiku = "kiri";
                urutku = posisi;
            } else if (posisi.substring(0, 1).equals("R")) {
                posisiku = "kanan";
                urutku = posisi;
            }
        }

       listNotif.clear();
       listRecycle.clear();
       listSinkron.clear();

       listRecycle = dbTemporari.getListTemporari(session.getKodeprov(), session.getNoruas(), tipeku, posisiku, urutku, foto, status, "0");
       listNotif = dbTemporari.getListTemporari(session.getKodeprov(), session.getNoruas(), tipeku, posisiku, urutku, "0", "0", "0");

       listSinkron = dbTemporari.getListTemporari(session.getKodeprov(), session.getNoruas(), tipeku, posisiku, urutku, "1", status, "1");


        if(listNotif.size()==0){
            boxnotif.setVisibility(GONE);
        }else {
            boxnotif.setVisibility(View.VISIBLE);
            tnotif.setText("Terdapat "+String.valueOf(listNotif.size())+" perubahan yang tidak terdapat foto");
        }

        sinkronAdapter = new SinkronAdapter(listRecycle, this, new SendId() {
            @Override
            public void hapusGambar(int id) {

                initData(tipe, posisi, foto, status);

            }
        });

        if(listSinkron.size()>0){
            sinkronButton.setVisibility(View.VISIBLE);
            setButtonSinkron(status);
        }else{
            sinkronButton.setVisibility(View.GONE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(sinkronAdapter);
        sinkronAdapter.notifyDataSetChanged();

    }


    public void initDataTipe(){

        lTipe = helperList.getListSpinner(context, R.array.tipeSurvey);
        sTipe.setAdapter(helperList.getSpinner(context, R.array.tipeSurvey));
        sTipe.setOnItemSelectedListener(this);

         if(getIntent().hasExtra("tipe")){
            String value = String.valueOf(getIntent().getExtras().get("tipe").toString());
            sTipe.setSelection(getIndexArray(lTipe, value));
         }
    }

    public void initDataPosisi(String tipe){

        if(tipe.equals("Lane")){
            lPosisi = helperList.getListSpinner(context, R.array.posisiLane);
            sPosisi.setAdapter(helperList.getSpinner(context, R.array.posisiLane));
        }else if(tipe.equals("--Pilih--")||tipe.equals("Median")||tipe.equals("Segment")){
            lPosisi = helperList.getListSpinner(context, R.array.posisiMedian);
            sPosisi.setAdapter(helperList.getSpinner(context, R.array.posisiMedian));
        }else{
            lPosisi = helperList.getListSpinner(context, R.array.posisiTipe);
            sPosisi.setAdapter(helperList.getSpinner(context, R.array.posisiTipe));
        }


        sPosisi.setOnItemSelectedListener(this);

        if(getIntent().hasExtra("posisi")){
            String value = String.valueOf(getIntent().getExtras().get("posisi").toString());
            sPosisi.setSelection(getIndexArray(lPosisi, value));
        }

    }

    private void initWarna(){

        lWarna = helperList.getListSpinner(context, R.array.warna);

        SpinnerWarna spinnerWarna = helperList.getSpinnerWarna(context, R.array.warna);
        sWarna.setAdapter(spinnerWarna);
        sWarna.setOnItemSelectedListener(this);

        if(getIntent().hasExtra("warna")){
            String value = String.valueOf(getIntent().getExtras().get("warna").toString());
            sWarna.setSelection(getIndexArray(lWarna, value));
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId()==R.id.sinkronDetail){
            tipeku = lTipe[position];
            initDataPosisi(tipeku);
        }else if(parent.getId()==R.id.sinkronPosisi){
            posisiku = lPosisi[position];
        }else if(parent.getId()==R.id.sinkronWarna){
            getTipeWarna(lWarna[position]);
        }

        initData(tipeku, posisiku, fotoku, statusku);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getTipeWarna(String value){

        fotoku = null;
        statusku = null;

        switch (value){

            case "merah" :
                fotoku = "0";
                statusku = "0";
            break;

            case "hijau" :
                fotoku = "1";
                statusku = "0";
                break;

            case "biru" :
                fotoku = "1";
                statusku = "1";
                break;
        }
    }

    private void setButtonSinkron(String status){

        if(status!=null){
            if(status.equals("0")){
                sinkronButton.setVisibility(View.GONE);
            }
        }

    }

    private int getIndexArray(String[] list, String value){

        int sReturn =0;

        for(int i =0; i<list.length; i++){
            if (list[i].equals(value)){
                sReturn = i ;
            }
        }

        return sReturn;

    }
}
