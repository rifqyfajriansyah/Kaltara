package com.example.roadmanagement.kaltara.kolektif;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class KolektifSegmentku extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Session session;

    TextView tnoprov;
    TextView tnoruas;

    Spinner klawal;
    Spinner klakhir;
    Spinner klvertikal;
    Spinner klhorizontal;
    Spinner tipejalan;

    Button button;

    List<String> ltipejalan;
    List<String> lawal;
    List<String> lakhir;
    List<String> lvertikal;
    List<String> lhorizontal;

    String sawal;
    String sakhir;
    String svertikal;
    String shorizontal;
    String stipejalan;

    DbSpinner dbSpinner;

    EditText kolomlebarpvmt;
    EditText kolomgrade;
    Float lebarpvmt;

    int jumlahlane =0;

    int statl1 = 0;
    int statl2 = 0;
    int statl3 = 0;
    int statl4 = 0;
    int statl5 = 0;
    int statl6 = 0;
    int statl7 = 0;
    int statl8 = 0;
    int statl9 = 0;
    int statl10 = 0;

    int statr1 = 0;
    int statr2 = 0;
    int statr3 = 0;
    int statr4 = 0;
    int statr5 = 0;
    int statr6 = 0;
    int statr7 = 0;
    int statr8 = 0;
    int statr9 = 0;
    int statr10 = 0;

    int status = 0;
    ImageView gl1;
    ImageView gl2;
    ImageView gl3;
    ImageView gl4;
    ImageView gl5;
    ImageView gl6;
    ImageView gl7;
    ImageView gl8;
    ImageView gl9;
    ImageView gl10;

    ImageView gr1;
    ImageView gr2;
    ImageView gr3;
    ImageView gr4;
    ImageView gr5;
    ImageView gr6;
    ImageView gr7;
    ImageView gr8;
    ImageView gr9;
    ImageView gr10;

    RelativeLayout kotakl1;
    RelativeLayout kotakl2;
    RelativeLayout kotakl3;
    RelativeLayout kotakl4;
    RelativeLayout kotakl5;
    RelativeLayout kotakl6;
    RelativeLayout kotakl7;
    RelativeLayout kotakl8;
    RelativeLayout kotakl9;
    RelativeLayout kotakl10;

    RelativeLayout kotakr1;
    RelativeLayout kotakr2;
    RelativeLayout kotakr3;
    RelativeLayout kotakr4;
    RelativeLayout kotakr5;
    RelativeLayout kotakr6;
    RelativeLayout kotakr7;
    RelativeLayout kotakr8;
    RelativeLayout kotakr9;
    RelativeLayout kotakr10;

    TextView jumlahlaner;

    Button save;
    Context context;

    float grade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kolektif_segmentku);

        tnoprov = findViewById(R.id.toolbakprovinsi);
        tnoruas = findViewById(R.id.toolbakruas);

        klawal = findViewById(R.id.awalkolektifsegmentkolektif);
        klakhir = findViewById(R.id.akhirkolektifsegmentkolektif);
        klvertikal = findViewById(R.id.spinneralvertikalkolektif);
        klhorizontal = findViewById(R.id.spinneralhorizontalkolektif);
        tipejalan = findViewById(R.id.spinnertipejalankolektif);
        kolomlebarpvmt = findViewById(R.id.lebarpvmtkolektif);
        kolomgrade = findViewById(R.id.gradekolektif);

        gl1 = findViewById(R.id.detaillanel1kolektif);
        gl2 = findViewById(R.id.detaillanel2kolektif);
        gl3 = findViewById(R.id.detaillanel3kolektif);
        gl4 = findViewById(R.id.detaillanel4kolektif);
        gl5 = findViewById(R.id.detaillanel5kolektif);
        gl6 = findViewById(R.id.detaillanel6kolektif);
        gl7 = findViewById(R.id.detaillanel7kolektif);
        gl8 = findViewById(R.id.detaillanel8kolektif);
        gl9 = findViewById(R.id.detaillanel9kolektif);
        gl10 = findViewById(R.id.detaillanel10kolektif);

        gr1 = findViewById(R.id.detaillaner1kolektif);
        gr2 = findViewById(R.id.detaillaner2kolektif);
        gr3 = findViewById(R.id.detaillaner3kolektif);
        gr4 = findViewById(R.id.detaillaner4kolektif);
        gr5 = findViewById(R.id.detaillaner5kolektif);
        gr6 = findViewById(R.id.detaillaner6kolektif);
        gr7 = findViewById(R.id.detaillaner7kolektif);
        gr8 = findViewById(R.id.detaillaner8kolektif);
        gr9 = findViewById(R.id.detaillaner9kolektif);
        gr10 = findViewById(R.id.detaillaner10kolektif);

        kotakl1 = findViewById(R.id.kotakl1kolektif);
        kotakl2 =findViewById(R.id.kotakl2kolektif);
        kotakl3 = findViewById(R.id.kotakl3kolektif);
        kotakl4 = findViewById(R.id.kotakl4kolektif);
        kotakl5 = findViewById(R.id.kotakl5kolektif);
        kotakl6 =findViewById(R.id.kotakl6kolektif);
        kotakl7 = findViewById(R.id.kotakl7kolektif);
        kotakl8 = findViewById(R.id.kotakl8kolektif);
        kotakl9 = findViewById(R.id.kotakl9kolektif);
        kotakl10 =findViewById(R.id.kotakl10kolektif);

        kotakr1 = findViewById(R.id.kotakr1kolektif);
        kotakr2 =findViewById(R.id.kotakr2kolektif);
        kotakr3 = findViewById(R.id.kotakr3kolektif);
        kotakr4 = findViewById(R.id.kotakr4kolektif);
        kotakr5 = findViewById(R.id.kotakr5kolektif);
        kotakr6 =findViewById(R.id.kotakr6kolektif);
        kotakr7 = findViewById(R.id.kotakr7kolektif);
        kotakr8 = findViewById(R.id.kotakr8kolektif);
        kotakr9 = findViewById(R.id.kotakr9kolektif);
        kotakr10 =findViewById(R.id.kotakr10kolektif);

        jumlahlaner =findViewById(R.id.jumlahlanekolektif);
        save = findViewById(R.id.kolektifsegmentbutton);

        context = this;

        ltipejalan = new ArrayList<>();
        lawal = new ArrayList<>();
        lakhir = new ArrayList<>();
        lvertikal = new ArrayList<>();
        lhorizontal = new ArrayList<>();

        session = new Session(this);
        dbSpinner = new DbSpinner(this);

        tnoprov.setText(session.getKodeprov());
        tnoruas.setText(session.getNoruas());


        initRuasawal();
        initRuasakhir();
        initVertikal();
        initHorizontal();
        initTipejalan();

        setSegment(0, kotakl1, kotakl2, kotakl3, kotakl4, gl1, gl2, gl3, gl4);
        setSegment(0, kotakr1, kotakr2, kotakr3, kotakr4, gr1, gr2, gr3, gr4);
        leftClick();
        rightClick();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kolomlebarpvmt.getText().toString().equals("")){
                    lebarpvmt=Float.valueOf(0);
                }else{
                    lebarpvmt= Float.valueOf(kolomlebarpvmt.getText().toString());
                }

                if(kolomgrade.getText().toString().equals("")){
                    grade=Float.valueOf(0);
                }else{
                    grade= Float.valueOf(kolomgrade.getText().toString());
                }
               // KolektifSegmentTask kolektifSegmentTask = new KolektifSegmentTask(context, session.getKodeprov(), session.getNoruas(), Integer.valueOf(sawal), Integer.valueOf(sakhir), svertikal, shorizontal, stipejalan, lebarpvmt, grade, statl1,statl2,statl3,statl4,statr1,statr2,statr3,statr4,"");
               // kolektifSegmentTask.execute("bisa", "bisa", "bisa");
            }
        });

    }

    public void initRuasawal(){
        lawal = dbSpinner.getSegment(String.valueOf(session.getKodeprov()), session.getNoruas());
        String[] spinnerArray = new String[lawal.size()];
        lawal.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        klawal.setAdapter(spinnerArrayAdapter);
        klawal.setOnItemSelectedListener(KolektifSegmentku.this);

    }

    public void initRuasakhir(){
        lakhir = dbSpinner.getSegment(String.valueOf(session.getKodeprov()), session.getNoruas());
        String[] spinnerArray = new String[lakhir.size()];
        lakhir.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        klakhir.setAdapter(spinnerArrayAdapter);
        klakhir.setOnItemSelectedListener(KolektifSegmentku.this);

    }


    public void initVertikal(){
        String text[] = getResources().getStringArray(R.array.al_vertikal);
        for (int i = 0; i < text.length; i++) {
            lvertikal.add(text[i]);
        }
        String[] spinnerArray = new String[lvertikal.size()];
        lvertikal.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        klvertikal.setAdapter(spinnerArrayAdapter);
        klvertikal.setOnItemSelectedListener(KolektifSegmentku.this);

    }

    public void initHorizontal(){
        String text[] = getResources().getStringArray(R.array.al_horizontal);
        for (int i = 0; i < text.length; i++) {
            lhorizontal.add(text[i]);
        }
        String[] spinnerArray = new String[lhorizontal.size()];
        lhorizontal.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        klhorizontal.setAdapter(spinnerArrayAdapter);
        klhorizontal.setOnItemSelectedListener(KolektifSegmentku.this);

    }

    public void initTipejalan(){
        String text[] = getResources().getStringArray(R.array.tipe_lane);
        for (int i = 0; i < text.length; i++) {
            ltipejalan.add(text[i]);
        }
        String[] spinnerArray = new String[ltipejalan.size()];
        ltipejalan.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        tipejalan.setAdapter(spinnerArrayAdapter);
        tipejalan.setOnItemSelectedListener(KolektifSegmentku.this);

    }

    public String getkodeprov(int a) {
        String prov;

        if (a < 10) {
            prov = "0" + String.valueOf(a);
        } else {
            prov = String.valueOf(a);
        }

        return prov;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.awalkolektifsegmentkolektif){
            sawal = lawal.get(position);
        }else if(parent.getId() == R.id.akhirkolektifsegmentkolektif){
            sakhir = lakhir.get(position);
        }else if(parent.getId() == R.id.spinneralvertikalkolektif){
            svertikal = lvertikal.get(position);
        }else if(parent.getId() == R.id.spinneralhorizontalkolektif){
            shorizontal = lhorizontal.get(position);
        }else if(parent.getId() == R.id.spinnertipejalankolektif){
            stipejalan = ltipejalan.get(position);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void leftClick(){
        gl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl1==0) {
                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gl1.setImageResource(R.drawable.icon_silang);
                    //gl1.setVisibility(View.GONE);
                    gl2.setVisibility(View.VISIBLE);
                    gl2.setImageResource(R.drawable.icon_tambah);
                    statl1=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl1.setImageResource(R.drawable.icon_tambah);
                    gl2.setVisibility(View.GONE);
                    statl1=0;
                    jumlahlane = jumlahlane -1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl2==0) {
                    kotakl2.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gl1.setVisibility(View.GONE);
                    gl2.setImageResource(R.drawable.icon_silang);
                    gl3.setVisibility(View.VISIBLE);
                    gl3.setImageResource(R.drawable.icon_tambah);
                    statl2=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakl2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl1.setVisibility(View.VISIBLE);
                    gl1.setImageResource(R.drawable.icon_silang);
                    gl2.setImageResource(R.drawable.icon_tambah);
                    gl3.setVisibility(View.GONE);
                    statl2=0;
                    jumlahlane = jumlahlane -1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl3==0) {
                    kotakl3.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gl2.setVisibility(View.GONE);
                    gl3.setImageResource(R.drawable.icon_silang);
                    gl4.setVisibility(View.VISIBLE);
                    gl4.setImageResource(R.drawable.icon_tambah);
                    statl3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakl3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl2.setVisibility(View.VISIBLE);
                    gl2.setImageResource(R.drawable.icon_silang);
                    gl3.setImageResource(R.drawable.icon_tambah);
                    gl4.setVisibility(View.GONE);
                    statl3=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl4==0) {
                    kotakl4.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gl3.setVisibility(View.GONE);
                    gl4.setImageResource(R.drawable.icon_silang);
                    statl4=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakl4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl3.setVisibility(View.VISIBLE);
                    gl3.setImageResource(R.drawable.icon_silang);
                    gl4.setImageResource(R.drawable.icon_tambah);
                    statl4=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

    }

    public void rightClick(){
        gr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr1==0) {
                    kotakr1.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gr1.setImageResource(R.drawable.icon_silang);
                    //gr1.setVisibility(View.GONE);
                    gr2.setVisibility(View.VISIBLE);
                    gr2.setImageResource(R.drawable.icon_tambah);
                    statr1=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakr1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr1.setImageResource(R.drawable.icon_tambah);
                    gr2.setVisibility(View.GONE);
                    statr1=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr2==0) {
                    kotakr2.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gr1.setVisibility(View.GONE);
                    gr2.setImageResource(R.drawable.icon_silang);
                    gr3.setVisibility(View.VISIBLE);
                    gr3.setImageResource(R.drawable.icon_tambah);
                    statr2=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr1.setVisibility(View.VISIBLE);
                    gr1.setImageResource(R.drawable.icon_silang);
                    gr2.setImageResource(R.drawable.icon_tambah);
                    gr3.setVisibility(View.GONE);
                    statr2=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr3==0) {
                    kotakr3.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gr2.setVisibility(View.GONE);
                    gr3.setImageResource(R.drawable.icon_silang);
                    gr4.setVisibility(View.VISIBLE);
                    gr4.setImageResource(R.drawable.icon_tambah);
                    statr3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr2.setVisibility(View.VISIBLE);
                    gr2.setImageResource(R.drawable.icon_silang);
                    gr3.setImageResource(R.drawable.icon_tambah);
                    gr4.setVisibility(View.GONE);
                    statr3=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr4==0) {
                    kotakr4.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gr3.setVisibility(View.GONE);
                    gr4.setImageResource(R.drawable.icon_silang);
                    statr4=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr3.setVisibility(View.VISIBLE);
                    gr3.setImageResource(R.drawable.icon_silang);
                    gr4.setImageResource(R.drawable.icon_tambah);
                    statr4=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

    }

    public void setSegment(int jumlah, RelativeLayout k1, RelativeLayout k2, RelativeLayout k3, RelativeLayout k4, ImageView g1, ImageView g2, ImageView g3, ImageView g4){
        switch (jumlah){
            case 0:{
                g1.setImageResource(R.drawable.icon_tambah);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
            }
            break;
            case 1:{
                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g1.setImageResource(R.drawable.icon_silang);
                //g1.setVisibility(View.GONE);
                g2.setVisibility(View.VISIBLE);
                g2.setImageResource(R.drawable.icon_tambah);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
            }
            break;
            case 2 :{
                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g1.setVisibility(View.GONE);
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g2.setImageResource(R.drawable.icon_silang);
                g3.setVisibility(View.VISIBLE);
                g3.setImageResource(R.drawable.icon_tambah);
                g4.setVisibility(View.GONE);
            }
            break;
            case 3:{
                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g3.setImageResource(R.drawable.icon_silang);
                g4.setVisibility(View.VISIBLE);
                g4.setImageResource(R.drawable.icon_tambah);
            }
            break;
            case 4:{
                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g4.setImageResource(R.drawable.icon_silang);
            }
        }
    }
}
