package com.example.roadmanagement.kaltara.Formu;

import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

public class FormLaneUtama extends AppCompatActivity {

    ImageView gl1;
    ImageView gl2;
    ImageView gl3;
    ImageView gl4;
    ImageView gr1;
    ImageView gr2;
    ImageView gr3;
    ImageView gr4;
    RelativeLayout kotakl1;
    RelativeLayout kotakl2;
    RelativeLayout kotakl3;
    RelativeLayout kotakl4;
    RelativeLayout kotakr1;
    RelativeLayout kotakr2;
    RelativeLayout kotakr3;
    RelativeLayout kotakr4;

    int statl1;
    int statl2;
    int statl3;
    int statl4;
    int statr1;
    int statr2;
    int statr3;
    int statr4;

    LinearLayout bl1;
    LinearLayout bl2;
    LinearLayout bl3;
    LinearLayout bl4;
    LinearLayout br1;
    LinearLayout br2;
    LinearLayout br3;
    LinearLayout br4;

    TextView tnoprov;
    TextView tnoruas;
    TextView tnoseg;
    Session session;

    int segment;

    Context context;

    TextView tl1;
    TextView tl2;
    TextView tl3;
    TextView tl4;
    TextView tr1;
    TextView tr2;
    TextView tr3;
    TextView tr4;

    DbLane dbLane;

    DbTemporari dbTemporari;

    CardView saveLane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_lane_utama);

        gl1 = findViewById(R.id.lanedetaillanel1);
        gl2 = findViewById(R.id.lanedetaillanel2);
        gl3 = findViewById(R.id.lanedetaillanel3);
        gl4 = findViewById(R.id.lanedetaillanel4);
        gr1 = findViewById(R.id.lanedetaillaner1);
        gr2 = findViewById(R.id.lanedetaillaner2);
        gr3 = findViewById(R.id.lanedetaillaner3);
        gr4 = findViewById(R.id.lanedetaillaner4);
        kotakl1 = findViewById(R.id.lanekotakl1);
        kotakl2 = findViewById(R.id.lanekotakl2);
        kotakl3 = findViewById(R.id.lanekotakl3);
        kotakl4 = findViewById(R.id.lanekotakl4);
        kotakr1 = findViewById(R.id.lanekotakr1);
        kotakr2 = findViewById(R.id.lanekotakr2);
        kotakr3 = findViewById(R.id.lanekotakr3);
        kotakr4 = findViewById(R.id.lanekotakr4);
        bl1 = findViewById(R.id.boxl1);
        bl2 = findViewById(R.id.boxl2);
        bl3 = findViewById(R.id.boxl3);
        bl4 = findViewById(R.id.boxl4);
        br1 = findViewById(R.id.boxr1);
        br2 = findViewById(R.id.boxr2);
        br3 = findViewById(R.id.boxr3);
        br4 = findViewById(R.id.boxr4);
        tl1 = findViewById(R.id.textl1);
        tl2 = findViewById(R.id.textl2);
        tl3 = findViewById(R.id.textl3);
        tl4 = findViewById(R.id.textl4);
        tr1 = findViewById(R.id.textr1);
        tr2 = findViewById(R.id.textr2);
        tr3 = findViewById(R.id.textr3);
        tr4 = findViewById(R.id.textr4);

        saveLane = findViewById(R.id.buttonlaneutama);

        session = new Session(this);

        tnoprov = findViewById(R.id.detailprovinsi);
        tnoruas = findViewById(R.id.detailruas);
        tnoseg = findViewById(R.id.detailsegment);

        tnoprov.setText(session.getKodeprov());
        tnoruas.setText(session.getNoruas());

        segment = Integer.valueOf(getIntent().getExtras().get("id").toString());
        tnoseg.setText(String.valueOf(segment));

        dbLane = new DbLane(this);
        dbTemporari = new DbTemporari(this);

        context = FormLaneUtama.this;

        tl1.setText(String.valueOf(segment)+" - Lane L1");
        tl2.setText(String.valueOf(segment)+" - Lane L2");
        tl3.setText(String.valueOf(segment)+" - Lane L3");
        tl4.setText(String.valueOf(segment)+" - Lane L4");
        tr1.setText(String.valueOf(segment)+" - Lane R1");
        tr2.setText(String.valueOf(segment)+" - Lane R2");
        tr3.setText(String.valueOf(segment)+" - Lane R3");
        tr4.setText(String.valueOf(segment)+" - Lane R4");

        int jumlahkiri = dbLane.getJumlahPosisi(session.getKodeprov(), session.getNoruas(), segment, "L");
        int jumlahkanan = dbLane.getJumlahPosisi(session.getKodeprov(), session.getNoruas(), segment, "R");

        nilaiStatusKiri(jumlahkiri);
        nilaiStatusKanan(jumlahkanan);

        setSegment(jumlahkiri, kotakl1, kotakl2, kotakl3, kotakl4, gl1, gl2, gl3, gl4, bl1, bl2, bl3, bl4);
        setSegment(jumlahkanan, kotakr1, kotakr2, kotakr3, kotakr4, gr1, gr2, gr3, gr4, br1, br2, br3, br4);
        leftClick();
        rightClick();

        //Toast.makeText(this, String.valueOf(dbLane.getIndexLaneUn(session.getKodeprov(), session.getNoruas())), Toast.LENGTH_LONG).show();

        bl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLajur();
                Intent i = new Intent(FormLaneUtama.this, UnformEdit.class);
                i.putExtra("tipe", "Lane");
                i.putExtra("posisi", "L1");
                i.putExtra("id", String.valueOf(segment));
                i.putExtra("dari", "Utama");
                startActivity(i);
            }
        });

        bl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLajur();
                Intent i = new Intent(FormLaneUtama.this, UnformEdit.class);
                i.putExtra("tipe", "Lane");
                i.putExtra("posisi", "L2");
                i.putExtra("id", String.valueOf(segment));
                i.putExtra("dari", "Utama");
                startActivity(i);
            }
        });

        bl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLajur();
                Intent i = new Intent(FormLaneUtama.this, UnformEdit.class);
                i.putExtra("tipe", "Lane");
                i.putExtra("posisi", "L3");
                i.putExtra("id", String.valueOf(segment));
                i.putExtra("dari", "Utama");
                startActivity(i);
            }
        });

        bl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLajur();
                Intent i = new Intent(FormLaneUtama.this, UnformEdit.class);
                i.putExtra("tipe", "Lane");
                i.putExtra("posisi", "L4");
                i.putExtra("id", String.valueOf(segment));
                i.putExtra("dari", "Utama");
                startActivity(i);
            }
        });

        br1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLajur();
                Intent i = new Intent(FormLaneUtama.this, UnformEdit.class);
                i.putExtra("tipe", "Lane");
                i.putExtra("posisi", "R1");
                i.putExtra("id", String.valueOf(segment));
                i.putExtra("dari", "Utama");
                startActivity(i);
            }
        });

        br2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLajur();
                Intent i = new Intent(FormLaneUtama.this, UnformEdit.class);
                i.putExtra("tipe", "Lane");
                i.putExtra("posisi", "R2");
                i.putExtra("id", String.valueOf(segment));
                i.putExtra("dari", "Utama");
                startActivity(i);
            }
        });

        br3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLajur();
                Intent i = new Intent(FormLaneUtama.this, UnformEdit.class);
                i.putExtra("tipe", "Lane");
                i.putExtra("posisi", "R3");
                i.putExtra("id", String.valueOf(segment));
                i.putExtra("dari", "Utama");
                startActivity(i);
            }
        });

        br4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLajur();
                Intent i = new Intent(FormLaneUtama.this, UnformEdit.class);
                i.putExtra("tipe", "Lane");
                i.putExtra("posisi", "R4");
                i.putExtra("id", String.valueOf(segment));
                i.putExtra("dari", "Utama");
                startActivity(i);
            }
        });


        saveLane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLajur();

                Intent i = new Intent(FormLaneUtama.this, UnForm.class);
                i.putExtra("tipe", "4");
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


    }

    public void onClickLajur(){

        DataLane dataLaneL1 = new DataLane(session.getKodeprov(), session.getNoruas(), segment,  segment,"L1", null, 0, null, 0, 0,
                null, null, null, null, null);
        DataLane dataLaneL2 = new DataLane(session.getKodeprov(), session.getNoruas(), segment,  segment,"L2", null, 0, null, 0, 0,
                null, null, null, null, null);
        DataLane dataLaneL3 = new DataLane(session.getKodeprov(), session.getNoruas(), segment,  segment,"L3", null, 0, null, 0, 0,
                null, null, null, null, null);
        DataLane dataLaneL4 = new DataLane(session.getKodeprov(), session.getNoruas(), segment,  segment,"L4", null, 0, null, 0, 0,
                null, null, null, null, null);

        DataLane dataLaneR1 = new DataLane(session.getKodeprov(), session.getNoruas(), segment,  segment,"R1", null, 0, null, 0, 0,
                null, null, null, null, null);
        DataLane dataLaneR2 = new DataLane(session.getKodeprov(), session.getNoruas(), segment,  segment,"R2", null, 0, null, 0, 0,
                null, null, null, null, null);
        DataLane dataLaneR3 = new DataLane(session.getKodeprov(), session.getNoruas(), segment,  segment,"R3", null, 0, null, 0, 0,
                null, null, null, null, null);
        DataLane dataLaneR4 = new DataLane(session.getKodeprov(), session.getNoruas(), segment,  segment,"R4", null, 0, null, 0, 0,
                null, null, null, null, null);

        dbLane.setLaneUn(dataLaneL1, statl1);
        dbLane.setLaneUn(dataLaneL2, statl2);
        dbLane.setLaneUn(dataLaneL3, statl3);
        dbLane.setLaneUn(dataLaneL4, statl4);
        dbLane.setLaneUn(dataLaneR1, statr1);
        dbLane.setLaneUn(dataLaneR2, statr2);
        dbLane.setLaneUn(dataLaneR3, statr3);
        dbLane.setLaneUn(dataLaneR4, statr4);

        setTemporari(dataLaneL1, statl1);
        setTemporari(dataLaneL2, statl2);
        setTemporari(dataLaneL3, statl3);
        setTemporari(dataLaneL4, statl4);
        setTemporari(dataLaneR1, statr1);
        setTemporari(dataLaneR2, statr2);
        setTemporari(dataLaneR3, statr3);
        setTemporari(dataLaneR4, statr4);

    }

    public void setTemporari(DataLane dataLane, int params){

        DataTemporari dataTemporari = new DataTemporari(dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(),dataLane.getNosegment(),
                "Lane", dataLane.getPosisi(), "", "Unidentified", dataLane.getNosegment(),dataLane.getNosegment(),"0", "0");

        if(params==1){
            dbTemporari.postTemporariUn(dataTemporari);
        }else if(params==0){
            dbTemporari.hapusTemporari(dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(),  dataLane.getSubsegment(),"Lane", dataLane.getPosisi(), "");
        }

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
                    gl2.setImageResource(R.drawable.icon_add_lane);
                    statl1=1;

                    bl1.setVisibility(View.VISIBLE);
                }else{
                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl1.setImageResource(R.drawable.icon_add_lane);
                    gl2.setVisibility(View.GONE);
                    statl1=0;
                    bl1.setVisibility(View.GONE);
                }
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
                    gl3.setImageResource(R.drawable.icon_add_lane);
                    statl2=1;

                    bl2.setVisibility(View.VISIBLE);
                }else{
                    kotakl2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl1.setVisibility(View.VISIBLE);
                    gl1.setImageResource(R.drawable.icon_silang);
                    gl2.setImageResource(R.drawable.icon_add_lane);
                    gl3.setVisibility(View.GONE);
                    statl2=0;

                    bl2.setVisibility(View.GONE);
                }
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
                    gl4.setImageResource(R.drawable.icon_add_lane);
                    statl3=1;

                    bl3.setVisibility(View.VISIBLE);
                }else{
                    kotakl3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl2.setVisibility(View.VISIBLE);
                    gl2.setImageResource(R.drawable.icon_silang);
                    gl3.setImageResource(R.drawable.icon_add_lane);
                    gl4.setVisibility(View.GONE);
                    statl3=0;

                    bl3.setVisibility(View.GONE);
                }
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

                    bl4.setVisibility(View.VISIBLE);
                }else{
                    kotakl4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl3.setVisibility(View.VISIBLE);
                    gl3.setImageResource(R.drawable.icon_silang);
                    gl4.setImageResource(R.drawable.icon_add_lane);
                    statl4=0;

                    bl4.setVisibility(View.GONE);
                }
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
                    gr2.setImageResource(R.drawable.icon_add_lane);
                    statr1=1;

                    br1.setVisibility(View.VISIBLE);
                }else{
                    kotakr1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr1.setImageResource(R.drawable.icon_add_lane);
                    gr2.setVisibility(View.GONE);
                    statr1=0;

                    br1.setVisibility(View.GONE);
                }
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
                    gr3.setImageResource(R.drawable.icon_add_lane);
                    statr2=1;

                    br2.setVisibility(View.VISIBLE);
                }else{
                    kotakr2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr1.setVisibility(View.VISIBLE);
                    gr1.setImageResource(R.drawable.icon_silang);
                    gr2.setImageResource(R.drawable.icon_add_lane);
                    gr3.setVisibility(View.GONE);
                    statr2=0;

                    br2.setVisibility(View.GONE);
                }
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
                    gr4.setImageResource(R.drawable.icon_add_lane);
                    statr3=1;

                    br3.setVisibility(View.VISIBLE);
                }else{
                    kotakr3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr2.setVisibility(View.VISIBLE);
                    gr2.setImageResource(R.drawable.icon_silang);
                    gr3.setImageResource(R.drawable.icon_add_lane);
                    gr4.setVisibility(View.GONE);
                    statr3=0;

                    br3.setVisibility(View.GONE);
                }
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

                    br4.setVisibility(View.VISIBLE);
                }else{
                    kotakr4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr3.setVisibility(View.VISIBLE);
                    gr3.setImageResource(R.drawable.icon_silang);
                    gr4.setImageResource(R.drawable.icon_add_lane);
                    statr4=0;

                    br4.setVisibility(View.GONE);
                }
            }
        });

    }


    public void nilaiStatusKiri(int jumlah){
        switch (jumlah){
            case 0 :
                statl1 = 0;
                statl2 = 0;
                statl3 = 0;
                statl4 = 0;

                break;

            case 1 :
                statl1 = 1;
                statl2 = 0;
                statl3 = 0;
                statl4 = 0;

                break;

            case 2 :
                statl1 = 1;
                statl2 = 1;
                statl3 = 0;
                statl4 = 0;

                break;

            case 3 :
                statl1 = 1;
                statl2 = 1;
                statl3 = 1;
                statl4 = 0;

                break;

            case 4 :
                statl1 = 1;
                statl2 = 1;
                statl3 = 1;
                statl4 = 1;

                break;
        }
    }

    public void nilaiStatusKanan(int jumlah){
        switch (jumlah){
            case 0 :
                statr1 = 0;
                statr2 = 0;
                statr3 = 0;
                statr4 = 0;

                break;

            case 1 :
                statr1 = 1;
                statr2 = 0;
                statr3 = 0;
                statr4 = 0;

                break;

            case 2 :
                statr1 = 1;
                statr2 = 1;
                statr3 = 0;
                statr4 = 0;

                break;

            case 3 :
                statr1 = 1;
                statr2 = 1;
                statr3 = 1;
                statr4 = 0;

                break;

            case 4 :
                statr1 = 1;
                statr2 = 1;
                statr3 = 1;
                statr4 = 1;

                break;
        }
    }


    public void setSegment(int jumlah, RelativeLayout k1, RelativeLayout k2, RelativeLayout k3, RelativeLayout k4, ImageView g1, ImageView g2, ImageView g3, ImageView g4,
                           LinearLayout b1, LinearLayout b2, LinearLayout b3, LinearLayout b4){
        switch (jumlah){
            case 0:{
                g1.setImageResource(R.drawable.icon_add_lane);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);

                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b3.setVisibility(View.GONE);
                b4.setVisibility(View.GONE);
            }
            break;
            case 1:{
                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g1.setImageResource(R.drawable.icon_silang);
                //g1.setVisibility(View.GONE);
                g2.setVisibility(View.VISIBLE);
                g2.setImageResource(R.drawable.icon_add_lane);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);

                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.GONE);
                b3.setVisibility(View.GONE);
                b4.setVisibility(View.GONE);
            }
            break;
            case 2 :{
                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g1.setVisibility(View.GONE);
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g2.setImageResource(R.drawable.icon_silang);
                g3.setVisibility(View.VISIBLE);
                g3.setImageResource(R.drawable.icon_add_lane);
                g4.setVisibility(View.GONE);

                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.GONE);
                b4.setVisibility(View.GONE);
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
                g4.setImageResource(R.drawable.icon_add_lane);

                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);
                b4.setVisibility(View.GONE);
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

                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);
                b4.setVisibility(View.VISIBLE);
            }
        }
    }
}
