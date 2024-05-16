package com.example.roadmanagement.kaltara.UpdateForm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Formu.FormUtama;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Tabel.LihatTabel;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.helper.SpinnerHelper;
import com.example.roadmanagement.kaltara.sinkronForm.SinkronUtama;

import java.util.ArrayList;
import java.util.List;

public class FragmentSegmentDetail extends Fragment implements  AdapterView.OnItemSelectedListener{


    Context context;
    Session session;
    DataSegmen dataSegmen;
    DbSegmen dbSegmen;
    int jumlahlane =0;
    int statl1 = 0;
    int statl2 = 0;
    int statl3 = 0;
    int statl4 = 0;
    int statr1 = 0;
    int statr2 = 0;
    int statr3 = 0;
    int statr4 = 0;

    int statl5 = 0;
    int statl6 = 0;
    int statl7 = 0;
    int statl8 = 0;
    int statl9 = 0;
    int statl10 = 0;
    int statr5 = 0;
    int statr6 = 0;
    int statr7 = 0;
    int statr8 = 0;
    int statr9 = 0;
    int statr10 = 0;

    int status = 0;

    int jumlahlanekiri = 0;
    int jumlahlanekanan = 0;

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


    EditText lebarpvmt;
    EditText grade;
    TextView jumlahlaner;
    Button save;

    TextView dsegment;
    TextView druas;
    TextView dprovinsi;

    Spinner vertikal;
    Spinner horizontal;
    Spinner tipejalan;

    List<String> vertikals;
    List<String> horizontals;
    List<String> tipejalans;


    SpinnerHelper spinnerhelpertipejalan;

    DbTemporari dbtemporari;

    int page;
    String title;
    View view;

    DbLane dbLane;

    DataSegmen dataTemporariSegmen;

    CardView buttonReset;

    LinearLayout row5;
    LinearLayout row6;
    LinearLayout row7;
    LinearLayout row8;
    LinearLayout row9;
    LinearLayout row10;

    ImageView gl5;
    ImageView gl6;
    ImageView gl7;
    ImageView gl8;
    ImageView gl9;
    ImageView gl10;
    ImageView gr5;
    ImageView gr6;
    ImageView gr7;
    ImageView gr8;
    ImageView gr9;
    ImageView gr10;

    RelativeLayout kotakl5;
    RelativeLayout kotakl6;
    RelativeLayout kotakl7;
    RelativeLayout kotakl8;
    RelativeLayout kotakl9;
    RelativeLayout kotakl10;
    RelativeLayout kotakr5;
    RelativeLayout kotakr6;
    RelativeLayout kotakr7;
    RelativeLayout kotakr8;
    RelativeLayout kotakr9;
    RelativeLayout kotakr10;


    public static FragmentSegmentDetail newInstance(int page, String title) {
        FragmentSegmentDetail fragmentFirst = new FragmentSegmentDetail();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_detail_segment, container, false);

        context = getActivity();

        gl1 = view.findViewById(R.id.detaillanel1);
        gl2 = view.findViewById(R. id.detaillanel2);
        gl3 = view.findViewById(R.id.detaillanel3);
        gl4 = view.findViewById(R.id.detaillanel4);
        gr1 = view.findViewById(R.id.detaillaner1);
        gr2 = view.findViewById(R.id.detaillaner2);
        gr3 = view.findViewById(R.id.detaillaner3);
        gr4 = view.findViewById(R.id.detaillaner4);

        kotakl1 = view.findViewById(R.id.kotakl1);
        kotakl2 = view.findViewById(R.id.kotakl2);
        kotakl3 = view.findViewById(R.id.kotakl3);
        kotakl4 = view.findViewById(R.id.kotakl4);
        kotakr1 = view.findViewById(R.id.kotakr1);
        kotakr2 = view.findViewById(R.id.kotakr2);
        kotakr3 = view.findViewById(R.id.kotakr3);
        kotakr4 = view.findViewById(R.id.kotakr4);

        //tambahan 4 lane
        gl5 = view.findViewById(R.id.detaillanel5);
        gl6 = view.findViewById(R.id.detaillanel6);
        gl7 = view.findViewById(R.id.detaillanel7);
        gl8 = view.findViewById(R.id.detaillanel8);
        gl9 = view.findViewById(R.id.detaillanel9);
        gl10 = view.findViewById(R.id.detaillanel10);
        gr5 = view.findViewById(R.id.detaillaner5);
        gr6 = view.findViewById(R.id.detaillaner6);
        gr7 = view.findViewById(R.id.detaillaner7);
        gr8 = view.findViewById(R.id.detaillaner8);
        gr9 = view.findViewById(R.id.detaillaner9);
        gr10 = view.findViewById(R.id.detaillaner10);

        kotakl5 = view.findViewById(R.id.kotakl5);
        kotakl6 = view.findViewById(R.id.kotakl6);
        kotakl7 = view.findViewById(R.id.kotakl7);
        kotakl8 = view.findViewById(R.id.kotakl8);
        kotakl9 = view.findViewById(R.id.kotakl9);
        kotakl10 = view.findViewById(R.id.kotakl10);
        kotakr5 = view.findViewById(R.id.kotakr5);
        kotakr6 = view.findViewById(R.id.kotakr6);
        kotakr7 = view.findViewById(R.id.kotakr7);
        kotakr8 = view.findViewById(R.id.kotakr8);
        kotakr9 = view.findViewById(R.id.kotakr9);
        kotakr10 = view.findViewById(R.id.kotakr10);

        row5 = view.findViewById(R.id.row5);
        row6 = view.findViewById(R.id.row6);
        row7 = view.findViewById(R.id.row7);
        row8 = view.findViewById(R.id.row8);
        row9 = view.findViewById(R.id.row9);
        row10 = view.findViewById(R.id.row10);

        lebarpvmt = view.findViewById(R.id.lebarpvmt);
        grade = view.findViewById(R.id.grade);
        jumlahlaner = view.findViewById(R.id.jumlahlane);
        save = view.findViewById(R.id.detailsegmentbutton);
        jumlahlaner.setText("0");
        dbLane = new DbLane(context);

        vertikal = view.findViewById(R.id.spinneralvertikal);
        horizontal = view.findViewById(R.id.spinneralhorizontal);
        tipejalan = view.findViewById(R.id.spinnertipejalan);

        buttonReset = view.findViewById(R.id.resetDataSegment);

        buttonReset.setVisibility(View.GONE);

        // gl1.setVisibility(View.GONE);
        //  gr1.setVisibility(View.GONE);

        dbSegmen = new DbSegmen(getActivity());
        session = new Session(getActivity());
        dbtemporari = new DbTemporari(getActivity());
        //dataSegmen = dbSegmen.getSegmen(session.getKodeprov(), session.getNoruas(), session.getNosegment());
        //dataTemporariSegmen = dbSegmen.getSegmen(session.getKodeprov(), session.getNoruas(), session.getNosegment());
        if (dataSegmen!=null){

            statl1 = dataSegmen.getSegmentl1();
            statl2 = dataSegmen.getSegmentl2();
            statl3 = dataSegmen.getSegmentl3();
            statl4 = dataSegmen.getSegmentl4();
            statl5 = dataSegmen.getSegmentl5();
            statl6 = dataSegmen.getSegmentl6();
            statl7 = dataSegmen.getSegmentl7();
            statl8 = dataSegmen.getSegmentl8();
            statl9 = dataSegmen.getSegmentl9();
            statl10 = dataSegmen.getSegmentl10();

            statr1 = dataSegmen.getSegmentr1();
            statr2 = dataSegmen.getSegmentr2();
            statr3 = dataSegmen.getSegmentr3();
            statr4 = dataSegmen.getSegmentr4();
            statr5 = dataSegmen.getSegmentr5();
            statr6 = dataSegmen.getSegmentr6();
            statr7 = dataSegmen.getSegmentr7();
            statr8 = dataSegmen.getSegmentr8();
            statr9 = dataSegmen.getSegmentr9();
            statr10 = dataSegmen.getSegmentr10();

            jumlahlanekiri = statl1+statl2+statl3+statl4+statl5+statl6+statl7+statl8+statl9+statl10;
            jumlahlanekanan = statr1+statr2+statr3+statr4+statr5+statr6+statr7+statr8+statr9+statr10;

            lebarpvmt.setText(String.valueOf(dataSegmen.getLebarpvmt()));
            grade.setText(String.valueOf(dataSegmen.getGrade()));
            jumlahlane = dataSegmen.getJumlahsegment();
            jumlahlaner.setText(String.valueOf(jumlahlane));

            setSegment(jumlahlanekiri, kotakl1, kotakl2, kotakl3, kotakl4, kotakl5, kotakl6, kotakl7, kotakl8, kotakl9, kotakl10,
                    gl1, gl2, gl3, gl4, gl5, gl6, gl7, gl8, gl9, gl10);
            setSegment(jumlahlanekanan, kotakr1, kotakr2, kotakr3, kotakr4, kotakr5, kotakr6, kotakr7, kotakr8, kotakr9, kotakr10,
                    gr1, gr2, gr3, gr4, gr5, gr6, gr7, gr8, gr9, gr10 );

            setRow(jumlahlanekiri, jumlahlanekanan, row5, row6, row7, row8, row9, row10);

            status = 1;
        }else{
            status = 0;
            //dataSegmen =new DataSegmen(0,null,null,0,0,0,0,0,0,0,0,0, 0,0,
              //      0,0,0,0,0,0,0,0,0,0,0,"","","",0,0);
            setSegment(0, kotakl1, kotakl2, kotakl3, kotakl4, kotakl5, kotakl6, kotakl7, kotakl8, kotakl9, kotakl10,
                    gl1, gl2, gl3, gl4, gl5, gl6, gl7, gl8, gl9, gl10);
            setSegment(0, kotakr1, kotakr2, kotakr3, kotakr4, kotakr5, kotakr6, kotakr7, kotakr8, kotakr9, kotakr10,
                    gr1, gr2, gr3, gr4, gr5, gr6, gr7, gr8, gr9, gr10 );

            setRow(0, 0, row5, row6, row7, row8, row9, row10);
        }

        leftClick();
        rightClick();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cekPerubahan(dataSegmen.getSegmentl1(), statl1, "kiri", "L1");
                cekPerubahan(dataSegmen.getSegmentl2(), statl2, "kiri", "L2");
                cekPerubahan(dataSegmen.getSegmentl3(), statl3, "kiri", "L3");
                cekPerubahan(dataSegmen.getSegmentl4(), statl4, "kiri", "L4");
                cekPerubahan(dataSegmen.getSegmentl5(), statl5, "kiri", "L5");
                cekPerubahan(dataSegmen.getSegmentl6(), statl6, "kiri", "L6");
                cekPerubahan(dataSegmen.getSegmentl7(), statl7, "kiri", "L7");
                cekPerubahan(dataSegmen.getSegmentl8(), statl8, "kiri", "L8");
                cekPerubahan(dataSegmen.getSegmentl9(), statl9, "kiri", "L9");
                cekPerubahan(dataSegmen.getSegmentl10(), statl10, "kiri", "L10");

                cekPerubahan(dataSegmen.getSegmentr1(), statr1, "kanan", "R1");
                cekPerubahan(dataSegmen.getSegmentr2(), statr2, "kanan","R2");
                cekPerubahan(dataSegmen.getSegmentr3(), statr3, "kanan","R3");
                cekPerubahan(dataSegmen.getSegmentr4(), statr4, "kanan","R4");
                cekPerubahan(dataSegmen.getSegmentr5(), statr5, "kanan", "R5");
                cekPerubahan(dataSegmen.getSegmentr6(), statr6, "kanan","R6");
                cekPerubahan(dataSegmen.getSegmentr7(), statr7, "kanan","R7");
                cekPerubahan(dataSegmen.getSegmentr8(), statr8, "kanan","R8");
                cekPerubahan(dataSegmen.getSegmentr9(), statr9, "kanan", "R9");
                cekPerubahan(dataSegmen.getSegmentr10(), statr10, "kanan","R10");


                dataSegmen.setNoprov(session.getKodeprov());
                dataSegmen.setNoruas(session.getNoruas());
                dataSegmen.setNosegment(session.getNosegment());

                dataSegmen.setSegmentl1(statl1);
                dataSegmen.setSegmentl2(statl2);
                dataSegmen.setSegmentl3(statl3);
                dataSegmen.setSegmentl4(statl4);
                dataSegmen.setSegmentl5(statl5);
                dataSegmen.setSegmentl6(statl6);
                dataSegmen.setSegmentl7(statl7);
                dataSegmen.setSegmentl8(statl8);
                dataSegmen.setSegmentl9(statl9);
                dataSegmen.setSegmentl10(statl10);

                dataSegmen.setSegmentr1(statr1);
                dataSegmen.setSegmentr2(statr2);
                dataSegmen.setSegmentr3(statr3);
                dataSegmen.setSegmentr4(statr4);
                dataSegmen.setSegmentr5(statr5);
                dataSegmen.setSegmentr6(statr6);
                dataSegmen.setSegmentr7(statr7);
                dataSegmen.setSegmentr8(statr8);
                dataSegmen.setSegmentr9(statr9);
                dataSegmen.setSegmentr10(statr10);

                if(lebarpvmt.getText().toString().equals(null)){
                    dataSegmen.setLebarpvmt(0);
                }else{
                    dataSegmen.setLebarpvmt(Float.parseFloat(lebarpvmt.getText().toString()));
                }

                if(grade.getText().toString().equals("")){
                    dataSegmen.setGrade(0);
                }else{
                    dataSegmen.setGrade(Float.parseFloat(grade.getText().toString()));
                }

                dataSegmen.setJumlahsegment(jumlahlane);
                if(status==1){
                    dbSegmen.updateSegmen(dataSegmen);
                }else{
                    dbSegmen.insertSegmen(dataSegmen);
                }

                if(dataTemporariSegmen!=dataSegmen) {
                    setValueTemporari("Segment", "Update", "");
                }
                Intent i;

                if(getActivity().getIntent().getExtras().get("dari").toString().equals("form")) {
                    i = new Intent(getActivity(), FormUtama.class);
                }else if(getActivity().getIntent().getExtras().get("dari").toString().equals("Sinkron")) {
                    i = new Intent(getActivity(), SinkronUtama.class);
                }else{
                    i = new Intent(getActivity(), LihatTabel.class);
                }
                i.putExtra("posisi", "0");

                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        dprovinsi = view.findViewById(R.id.detailprovinsi);
        druas = view.findViewById(R.id.detailruas);
        dsegment = view.findViewById(R.id.detailsegment);


        dsegment.setText(String.valueOf(session.getNosegment()));
        druas.setText(session.getNoruas());
        dprovinsi.setText(String.valueOf(session.getKodeprov()));

        initvertikal();
        initHorizontal();
        initTipejalan();

        horizontal.setSelection(getIndex(horizontals, dataSegmen.getHorizontal()));
        vertikal.setSelection(getIndex(vertikals, dataSegmen.getVertikal()));
        tipejalan.setSelection(getIndex(tipejalans, dataSegmen.getTipejalan()));

        return view;
    }


    public void leftClick(){

        gl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl1==0) {
                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl2.setVisibility(View.VISIBLE);

                    gl1.setImageResource(R.drawable.icon_silang);
                    gl2.setImageResource(R.drawable.icon_add_lane);

                    statl1=1;
                    jumlahlane = jumlahlane+1;
                }else{

                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl2.setVisibility(View.GONE);

                    gl1.setImageResource(R.drawable.icon_add_lane);

                    statl1=0;
                    jumlahlane = jumlahlane-1;
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
                    gl3.setVisibility(View.VISIBLE);

                    gl2.setImageResource(R.drawable.icon_silang);
                    gl3.setImageResource(R.drawable.icon_add_lane);

                    statl2=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakl2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl1.setVisibility(View.VISIBLE);
                    gl3.setVisibility(View.GONE);

                    gl1.setImageResource(R.drawable.icon_silang);
                    gl2.setImageResource(R.drawable.icon_add_lane);

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
                    gl4.setVisibility(View.VISIBLE);

                    gl3.setImageResource(R.drawable.icon_silang);
                    gl4.setImageResource(R.drawable.icon_add_lane);

                    statl3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakl3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl2.setVisibility(View.VISIBLE);
                    gl4.setVisibility(View.GONE);

                    gl2.setImageResource(R.drawable.icon_silang);
                    gl3.setImageResource(R.drawable.icon_add_lane);

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
                    gl5.setVisibility(View.VISIBLE);

                    gl4.setImageResource(R.drawable.icon_silang);
                    gl5.setImageResource(R.drawable.icon_add_lane);

                    row5.setVisibility(View.VISIBLE);

                    statl4=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl3.setVisibility(View.VISIBLE);
                    gl5.setVisibility(View.GONE);

                    gl3.setImageResource(R.drawable.icon_silang);
                    gl4.setImageResource(R.drawable.icon_add_lane);

                    if(statr4==0) {
                        row5.setVisibility(View.GONE);
                    }

                    statl4=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl5==0) {
                    kotakl5.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl4.setVisibility(View.GONE);
                    gl6.setVisibility(View.VISIBLE);

                    gl5.setImageResource(R.drawable.icon_silang);
                    gl6.setImageResource(R.drawable.icon_add_lane);

                    row6.setVisibility(View.VISIBLE);

                    statl5=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl5.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl4.setVisibility(View.VISIBLE);
                    gl6.setVisibility(View.GONE);

                    gl4.setImageResource(R.drawable.icon_silang);
                    gl5.setImageResource(R.drawable.icon_add_lane);

                    if(statr5==0) {
                        row6.setVisibility(View.GONE);
                    }

                    statl5=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl6==0) {
                    kotakl6.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl5.setVisibility(View.GONE);
                    gl7.setVisibility(View.VISIBLE);

                    gl6.setImageResource(R.drawable.icon_silang);
                    gl7.setImageResource(R.drawable.icon_add_lane);

                    row7.setVisibility(View.VISIBLE);

                    statl6=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl6.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl5.setVisibility(View.VISIBLE);
                    gl7.setVisibility(View.GONE);

                    gl5.setImageResource(R.drawable.icon_silang);
                    gl6.setImageResource(R.drawable.icon_add_lane);

                    if(statr6==0) {
                        row7.setVisibility(View.GONE);
                    }

                    statl6=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl7==0) {
                    kotakl7.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl6.setVisibility(View.GONE);
                    gl8.setVisibility(View.VISIBLE);

                    gl7.setImageResource(R.drawable.icon_silang);
                    gl8.setImageResource(R.drawable.icon_add_lane);

                    row8.setVisibility(View.VISIBLE);

                    statl7=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl7.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl6.setVisibility(View.VISIBLE);
                    gl8.setVisibility(View.GONE);

                    gl6.setImageResource(R.drawable.icon_silang);
                    gl7.setImageResource(R.drawable.icon_add_lane);

                    if(statr7==0) {
                        row8.setVisibility(View.GONE);
                    }

                    statl7=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl8==0) {
                    kotakl8.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl7.setVisibility(View.GONE);
                    gl9.setVisibility(View.VISIBLE);

                    gl8.setImageResource(R.drawable.icon_silang);
                    gl9.setImageResource(R.drawable.icon_add_lane);

                    row9.setVisibility(View.VISIBLE);


                    statl8=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl8.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl7.setVisibility(View.VISIBLE);
                    gl9.setVisibility(View.GONE);

                    gl7.setImageResource(R.drawable.icon_silang);
                    gl8.setImageResource(R.drawable.icon_add_lane);

                    if(statr8==0) {
                        row9.setVisibility(View.GONE);
                    }


                    statl8=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl9==0) {
                    kotakl9.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl8.setVisibility(View.GONE);
                    gl10.setVisibility(View.VISIBLE);

                    gl9.setImageResource(R.drawable.icon_silang);
                    gl10.setImageResource(R.drawable.icon_add_lane);

                    row10.setVisibility(View.VISIBLE);


                    statl9=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl9.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl8.setVisibility(View.VISIBLE);
                    gl10.setVisibility(View.GONE);

                    gl8.setImageResource(R.drawable.icon_silang);
                    gl9.setImageResource(R.drawable.icon_add_lane);

                    if(statr9==0) {
                        row10.setVisibility(View.GONE);
                    }


                    statl9=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl10==0) {
                    kotakl10.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl9.setVisibility(View.GONE);

                    gl10.setImageResource(R.drawable.icon_silang);


                    statl10=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl10.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl9.setVisibility(View.VISIBLE);

                    gl9.setImageResource(R.drawable.icon_silang);
                    gl10.setImageResource(R.drawable.icon_add_lane);


                    statl10=0;
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

                    gr2.setVisibility(View.VISIBLE);

                    gr1.setImageResource(R.drawable.icon_silang);
                    gr2.setImageResource(R.drawable.icon_add_lane);

                    statr1=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakr1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr2.setVisibility(View.GONE);

                    gr1.setImageResource(R.drawable.icon_add_lane);

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
                    gr3.setVisibility(View.VISIBLE);

                    gr2.setImageResource(R.drawable.icon_silang);
                    gr3.setImageResource(R.drawable.icon_add_lane);

                    statr2=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr1.setVisibility(View.VISIBLE);
                    gr3.setVisibility(View.GONE);

                    gr1.setImageResource(R.drawable.icon_silang);
                    gr2.setImageResource(R.drawable.icon_add_lane);

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
                    gr4.setVisibility(View.VISIBLE);

                    gr3.setImageResource(R.drawable.icon_silang);
                    gr4.setImageResource(R.drawable.icon_add_lane);

                    statr3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr2.setVisibility(View.VISIBLE);
                    gr4.setVisibility(View.GONE);

                    gr2.setImageResource(R.drawable.icon_silang);
                    gr3.setImageResource(R.drawable.icon_add_lane);

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
                    gr5.setVisibility(View.VISIBLE);

                    gr4.setImageResource(R.drawable.icon_silang);
                    gr5.setImageResource(R.drawable.icon_add_lane);

                    row5.setVisibility(View.VISIBLE);

                    statr4=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr3.setVisibility(View.VISIBLE);
                    gr5.setVisibility(View.GONE);

                    gr3.setImageResource(R.drawable.icon_silang);
                    gr4.setImageResource(R.drawable.icon_add_lane);

                    if(statl4==0) {
                        row5.setVisibility(View.GONE);
                    }

                    statr4=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr5==0) {
                    kotakr5.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr4.setVisibility(View.GONE);
                    gr6.setVisibility(View.VISIBLE);

                    gr5.setImageResource(R.drawable.icon_silang);
                    gr6.setImageResource(R.drawable.icon_add_lane);

                    row6.setVisibility(View.VISIBLE);

                    statr5=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr5.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr4.setVisibility(View.VISIBLE);
                    gr6.setVisibility(View.GONE);

                    gr4.setImageResource(R.drawable.icon_silang);
                    gr5.setImageResource(R.drawable.icon_add_lane);

                    if(statl5==0) {
                        row6.setVisibility(View.GONE);
                    }

                    statr5=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr6==0) {
                    kotakr6.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr5.setVisibility(View.GONE);
                    gr7.setVisibility(View.VISIBLE);

                    gr6.setImageResource(R.drawable.icon_silang);
                    gr7.setImageResource(R.drawable.icon_add_lane);

                    row7.setVisibility(View.VISIBLE);

                    statr6=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr6.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr5.setVisibility(View.VISIBLE);
                    gr7.setVisibility(View.GONE);

                    gr5.setImageResource(R.drawable.icon_silang);
                    gr6.setImageResource(R.drawable.icon_add_lane);

                    if(statl6==0) {
                        row7.setVisibility(View.GONE);
                    }

                    statr6=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr7==0) {
                    kotakr7.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr6.setVisibility(View.GONE);
                    gr8.setVisibility(View.VISIBLE);

                    gr7.setImageResource(R.drawable.icon_silang);
                    gr8.setImageResource(R.drawable.icon_add_lane);

                    row8.setVisibility(View.VISIBLE);

                    statr7=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr7.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr6.setVisibility(View.VISIBLE);
                    gr8.setVisibility(View.GONE);

                    gr6.setImageResource(R.drawable.icon_silang);
                    gr7.setImageResource(R.drawable.icon_add_lane);

                    if(statl7==0) {
                        row8.setVisibility(View.GONE);
                    }

                    statr7=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr8==0) {
                    kotakr8.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr7.setVisibility(View.GONE);
                    gr9.setVisibility(View.VISIBLE);

                    gr8.setImageResource(R.drawable.icon_silang);
                    gr9.setImageResource(R.drawable.icon_add_lane);

                    row9.setVisibility(View.VISIBLE);


                    statr8=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr8.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr7.setVisibility(View.VISIBLE);
                    gr9.setVisibility(View.GONE);

                    gr7.setImageResource(R.drawable.icon_silang);
                    gr8.setImageResource(R.drawable.icon_add_lane);

                    if(statl8==0) {
                        row9.setVisibility(View.GONE);
                    }


                    statr8=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });


        gr9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr9==0) {
                    kotakr9.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr8.setVisibility(View.GONE);
                    gr10.setVisibility(View.VISIBLE);

                    gr9.setImageResource(R.drawable.icon_silang);
                    gr10.setImageResource(R.drawable.icon_add_lane);

                    row10.setVisibility(View.VISIBLE);


                    statr9=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr9.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr8.setVisibility(View.VISIBLE);
                    gr10.setVisibility(View.GONE);

                    gr8.setImageResource(R.drawable.icon_silang);
                    gr9.setImageResource(R.drawable.icon_add_lane);

                    if(statl9==0) {
                        row10.setVisibility(View.GONE);
                    }


                    statr9=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr10==0) {
                    kotakr10.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr9.setVisibility(View.GONE);

                    gr10.setImageResource(R.drawable.icon_silang);


                    statr10=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr10.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr9.setVisibility(View.VISIBLE);

                    gr9.setImageResource(R.drawable.icon_silang);
                    gr10.setImageResource(R.drawable.icon_add_lane);


                    statr10=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });


        /*gr4.setOnClickListener(new View.OnClickListener() {
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
                    gr4.setImageResource(R.drawable.icon_add_lane);
                    statr4=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });*/

    }

    private void setRow(int jumlahkiri, int jumlahkanan, LinearLayout baris5, LinearLayout baris6, LinearLayout baris7, LinearLayout baris8, LinearLayout baris9, LinearLayout baris10){

        int maxJumlah;

        if(jumlahkiri>jumlahkanan){
            maxJumlah = jumlahkiri;
        }else{
            maxJumlah = jumlahkanan;
        }


        switch (maxJumlah){

            case 4 : {
                baris5.setVisibility(View.VISIBLE);
                baris6.setVisibility(View.GONE);
                baris7.setVisibility(View.GONE);
                baris8.setVisibility(View.GONE);
                baris9.setVisibility(View.GONE);
                baris10.setVisibility(View.GONE);
            }
            break;

            case 5 : {
                baris5.setVisibility(View.VISIBLE);
                baris6.setVisibility(View.VISIBLE);
                baris7.setVisibility(View.GONE);
                baris8.setVisibility(View.GONE);
                baris9.setVisibility(View.GONE);
                baris10.setVisibility(View.GONE);
            }
            break;

            case 6 : {
                baris5.setVisibility(View.VISIBLE);
                baris6.setVisibility(View.VISIBLE);
                baris7.setVisibility(View.VISIBLE);
                baris8.setVisibility(View.GONE);
                baris9.setVisibility(View.GONE);
                baris10.setVisibility(View.GONE);
            }
            break;

            case 7 : {
                baris5.setVisibility(View.VISIBLE);
                baris6.setVisibility(View.VISIBLE);
                baris7.setVisibility(View.VISIBLE);
                baris8.setVisibility(View.VISIBLE);
                baris9.setVisibility(View.GONE);
                baris10.setVisibility(View.GONE);
            }
            break;

            case 8 : {
                baris5.setVisibility(View.VISIBLE);
                baris6.setVisibility(View.VISIBLE);
                baris7.setVisibility(View.VISIBLE);
                baris8.setVisibility(View.VISIBLE);
                baris9.setVisibility(View.VISIBLE);
                baris10.setVisibility(View.GONE);
            }
            break;

            case 9  :
            case 10 : {
                baris5.setVisibility(View.VISIBLE);
                baris6.setVisibility(View.VISIBLE);
                baris7.setVisibility(View.VISIBLE);
                baris8.setVisibility(View.VISIBLE);
                baris9.setVisibility(View.VISIBLE);
                baris10.setVisibility(View.VISIBLE);
            }
            break;

            default:{
                baris5.setVisibility(View.GONE);
                baris6.setVisibility(View.GONE);
                baris7.setVisibility(View.GONE);
                baris8.setVisibility(View.GONE);
                baris9.setVisibility(View.GONE);
                baris10.setVisibility(View.GONE);
            }


        }
    }

    public void setSegment(int jumlah,
                           RelativeLayout k1, RelativeLayout k2, RelativeLayout k3, RelativeLayout k4, RelativeLayout k5, RelativeLayout k6, RelativeLayout k7, RelativeLayout k8, RelativeLayout k9, RelativeLayout k10,
                           ImageView g1, ImageView g2, ImageView g3, ImageView g4, ImageView g5, ImageView g6, ImageView g7, ImageView g8 , ImageView g9, ImageView g10){
        switch (jumlah){
            case 0:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.VISIBLE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_add_lane);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

            }
            break;
            case 1:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.VISIBLE);
                g2.setVisibility(View.VISIBLE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_add_lane);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);
            }
            break;
            case 2 :{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.VISIBLE);
                g3.setVisibility(View.VISIBLE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_add_lane);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);
            }
            break;
            case 3:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.VISIBLE);
                g4.setVisibility(View.VISIBLE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_add_lane);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

            }
            break;
            case 4:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.VISIBLE);
                g5.setVisibility(View.VISIBLE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_add_lane);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);
            }
            break;

            case 5:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.VISIBLE);
                g6.setVisibility(View.VISIBLE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_add_lane);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);
            }
            break;

            case 6:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.VISIBLE);
                g7.setVisibility(View.VISIBLE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_add_lane);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);
            }
            break;

            case 7:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.VISIBLE);
                g8.setVisibility(View.VISIBLE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_add_lane);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);
            }
            break;

            case 8:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.VISIBLE);
                g9.setVisibility(View.VISIBLE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_add_lane);
                g10.setImageResource(R.drawable.icon_silang);
            }
            break;

            case 9:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.VISIBLE);
                g10.setVisibility(View.VISIBLE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_add_lane);
            }
            break;

            case 10:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.VISIBLE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);
            }
            break;


        }
    }

    public void setValueTemporari(String tipe, String letak, String urut){
        //DataTemporari dataTemporarip = new DataTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), tipe, letak, urut,null, 0, "1","0");
        //dbtemporari.postTemporari(dataTemporarip);
    }

    private int getIndex(List<String> dataSpinners, String myString){

        int index = 0;

        for (int i=0;i<dataSpinners.size();i++){
            if (dataSpinners.get(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    public void initvertikal(){
        vertikals = new ArrayList<>();
        vertikals.add("--Pilih--");
        String text[] = getResources().getStringArray(R.array.al_vertikal);
        for (int i = 0; i < text.length; i++) {
            vertikals.add(text[i]);
        }
        String[] spinnerArray = new String[vertikals.size()];
        vertikals.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        vertikal.setAdapter(spinnerArrayAdapter);
        vertikal.setOnItemSelectedListener(this);

    }

    public void initHorizontal(){
        horizontals = new ArrayList<>();
        horizontals.add("--Pilih--");
        String text[] = getResources().getStringArray(R.array.al_horizontal);
        for (int i = 0; i < text.length; i++) {
            horizontals.add(text[i]);
        }
        String[] spinnerArray = new String[horizontals.size()];
        horizontals.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        horizontal.setAdapter(spinnerArrayAdapter);
        horizontal.setOnItemSelectedListener(this);

    }


    public void initTipejalan(){
        tipejalans = new ArrayList<>();
        String text[] = getResources().getStringArray(R.array.tipe_lane);
        for (int i = 0; i < text.length; i++) {
            tipejalans.add(text[i]);
        }
        String[] spinnerArray = new String[tipejalans.size()];
        tipejalans.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        tipejalan.setAdapter(spinnerArrayAdapter);
        tipejalan.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.spinneralvertikal) {
            if(vertikals.get(position).equals("--Pilih--")){
                dataSegmen.setVertikal(null);
            }else {
                dataSegmen.setVertikal(vertikals.get(position));
            }
        }else if (parent.getId() == R.id.spinneralhorizontal){
            if(horizontals.get(position).equals("--Pilih--")) {
                dataSegmen.setHorizontal(null);
            }else {
                dataSegmen.setHorizontal(horizontals.get(position));
            }
        }else if(parent.getId() == R.id.spinnertipejalan){
            if(tipejalans.get(position).equals("--Pilih--")) {
                dataSegmen.setTipejalan(null);
            }else {
                dataSegmen.setTipejalan(tipejalans.get(position));
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void cekPerubahan(int a, int b, String posisi, String lajur){

        /*DataTemporari dataTemporari = new DataTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), "Segment", "", lajur, null, 0,"1","0");
        String letak;
        if(posisi.equals("kiri")){
            letak = "Left";
        }else{
            letak = "Right";
        }
        if(a!=b){

            if(a==0 && b==1){

                dataTemporari.setPosisi("Tambah");
                //dbLane.tambahLane(session.getKodeprov(), session.getNoruas(), session.getNosegment(), posisi, lajur);
                dbtemporari.postTemporari(new DataTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), "Lane", letak, lajur,null, 0, "0", "0"));
                dbtemporari.hapusTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), "Segment", "Hapus", lajur);
                //dbtemporari
            }else if(a==1 && b==0){

                dataTemporari.setPosisi("Hapus");
                //dbLane.hapusLane(session.getKodeprov(), session.getNoruas(), session.getNosegment(), posisi, lajur);
                dbtemporari.hapusTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), "Lane", letak, lajur);
                dbtemporari.hapusTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), "Segment", "Tambah", lajur);
            }

            dbtemporari.postTemporari(dataTemporari);

        }*/


    }

}
