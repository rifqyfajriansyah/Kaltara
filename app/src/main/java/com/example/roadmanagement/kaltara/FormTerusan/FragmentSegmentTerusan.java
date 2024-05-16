package com.example.roadmanagement.kaltara.FormTerusan;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.helper.HelperList;
import com.example.roadmanagement.kaltara.helper.PermissionHelper;
import com.example.roadmanagement.kaltara.helper.PermissionImage;
import com.example.roadmanagement.kaltara.helper.Session;

public class FragmentSegmentTerusan extends Fragment implements AdapterView.OnItemSelectedListener{

    //Widget
    Spinner spVertical, spHorizontal, spTipeJalan;
    EditText edGrade, edLebarPvmt;
    ImageView imL1, imL2, imL3, imL4, imL5, imL6, imL7, imL8, imL9, imL10,
            imR1, imR2, imR3, imR4, imR5, imR6, imR7, imR8, imR9, imR10;

    TextView txJumlahLane;

    CardView bSave;

    RelativeLayout kotakL1, kotakL2, kotakL3, kotakL4, kotakL5, kotakL6, kotakL7, kotakL8, kotakL9, kotakL10,
            kotakR1, kotakR2, kotakR3, kotakR4, kotakR5, kotakR6, kotakR7, kotakR8, kotakR9, kotakR10;

    LinearLayout row5, row6, row7, row8, row9, row10;

    int statL1, statL2, statL3, statL4, statL5, statL6, statL7, statL8, statL9, statL10,
            statR1, statR2, statR3, statR4, statR5, statR6, statR7, statR8, statR9, statR10, jumlahkiri, jumlahkanan, jumlahlane;


    //Variable
    String [] arrVertical, arrHorizontal, arrTipeJalan;

    //Object
    Context context;
    PermissionHelper permissionHelper;
    PermissionImage permissionImage;
    HelperList helperList;
    Session session;
    HelperFormTerusan helperFormTerusan;

    DataSegmen dataSegmen;
    DbSegmen dbSegmen;

    String asal;


    public static FragmentSegmentTerusan newInstance(String posisi) {
        FragmentSegmentTerusan fragment = new FragmentSegmentTerusan();
        Bundle args = new Bundle();
        args.putString("posisi", posisi);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.form_terusan_segment, container, false);

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();

        /*if(session.getUserTipe()==99){

            spVertical.setEnabled(false);
            spHorizontal.setEnabled(false);
            spTipeJalan.setEnabled(false);
            edGrade.setEnabled(false);
            edLebarPvmt.setEnabled(false);
            bSave.setEnabled(false);

            setTipe(View.INVISIBLE, false, imL1, imL2, imL3, imL4, imL5, imL6, imL7, imL8, imL9, imL10);
            setTipe(View.INVISIBLE, false, imR1, imR2, imR3, imR4, imR5, imR6, imR7, imR8, imR9, imR10);

            bSave.setCardBackgroundColor(Color.parseColor("#d9d9d9"));

        }else{

            spVertical.setEnabled(true);
            spHorizontal.setEnabled(true);
            spTipeJalan.setEnabled(true);
            edGrade.setEnabled(true);
            edLebarPvmt.setEnabled(true);
            bSave.setEnabled(true);

            //setTipe(View.VISIBLE, true, imL1, imL2, imL3, imL4, imL5, imL6, imL7, imL8, imL9, imL10);
            //setTipe(View.VISIBLE, true, imR1, imR2, imR3, imR4, imR5, imR6, imR7, imR8, imR9, imR10);

            bSave.setCardBackgroundColor(Color.parseColor("#4159BE"));

        }*/

        spVertical.setEnabled(false);
        spHorizontal.setEnabled(false);
        spTipeJalan.setEnabled(false);
        edGrade.setEnabled(false);
        edLebarPvmt.setEnabled(false);
        bSave.setVisibility(View.GONE);

        setTipe(View.INVISIBLE, false, imL1, imL2, imL3, imL4, imL5, imL6, imL7, imL8, imL9, imL10);
        setTipe(View.INVISIBLE, false, imR1, imR2, imR3, imR4, imR5, imR6, imR7, imR8, imR9, imR10);



        session.saveSPInt(Session.POSISITABEL, helperFormTerusan.getPosisiTabel("Segment"));

        leftClick();
        rightClick();


        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataSegmen.setGrade(Float.parseFloat(edGrade.getText().toString()));
                dataSegmen.setLebarpvmt(Float.parseFloat(edLebarPvmt.getText().toString()));
                dataSegmen.setJumlahsegment(jumlahlane);

                dataSegmen.setSegmentl1(statL1);
                dataSegmen.setSegmentl2(statL2);
                dataSegmen.setSegmentl3(statL3);
                dataSegmen.setSegmentl4(statL4);
                dataSegmen.setSegmentl5(statL5);
                dataSegmen.setSegmentl6(statL6);
                dataSegmen.setSegmentl7(statL7);
                dataSegmen.setSegmentl8(statL8);
                dataSegmen.setSegmentl9(statL9);
                dataSegmen.setSegmentl10(statL10);

                dataSegmen.setSegmentr1(statR1);
                dataSegmen.setSegmentr2(statR2);
                dataSegmen.setSegmentr3(statR3);
                dataSegmen.setSegmentr4(statR4);
                dataSegmen.setSegmentr5(statR5);
                dataSegmen.setSegmentr6(statR6);
                dataSegmen.setSegmentr7(statR7);
                dataSegmen.setSegmentr8(statR8);
                dataSegmen.setSegmentr9(statR9);
                dataSegmen.setSegmentr10(statR10);


                if(session.getSurvey()==1){
                    helperFormTerusan.saveSegment(dataSegmen, asal);
                }else{
                    helperFormTerusan.saveSegmenDetail(dataSegmen, asal);
                }

                /*if(session.getSurvey()==1){
                    helperFormTerusan.saveSegment(dataSegmen, asal);
                }else if(asal.equals("Tabel")){
                    helperFormTerusan.saveSegmentDetail(dataSegmen, asal);
                }else{
                    helperFormTerusan.saveSegmentSinkron(dataSegmen);
                }*/

            }
        });

        return view;
    }

    private void initWidget(View view){

        imL1 = view.findViewById(R.id.formSegmentDetailL1);
        imL2 = view.findViewById(R.id.formSegmentDetailL2);
        imL3 = view.findViewById(R.id.formSegmentDetailL3);
        imL4 = view.findViewById(R.id.formSegmentDetailL4);
        imL5 = view.findViewById(R.id.formSegmentDetailL5);
        imL6 = view.findViewById(R.id.formSegmentDetailL6);
        imL7 = view.findViewById(R.id.formSegmentDetailL7);
        imL8 = view.findViewById(R.id.formSegmentDetailL8);
        imL9 = view.findViewById(R.id.formSegmentDetailL9);
        imL10 = view.findViewById(R.id.formSegmentDetailL10);

        imR1 = view.findViewById(R.id.formSegmentDetailR1);
        imR2 = view.findViewById(R.id.formSegmentDetailR2);
        imR3 = view.findViewById(R.id.formSegmentDetailR3);
        imR4 = view.findViewById(R.id.formSegmentDetailR4);
        imR5 = view.findViewById(R.id.formSegmentDetailR5);
        imR6 = view.findViewById(R.id.formSegmentDetailR6);
        imR7 = view.findViewById(R.id.formSegmentDetailR7);
        imR8 = view.findViewById(R.id.formSegmentDetailR8);
        imR9 = view.findViewById(R.id.formSegmentDetailR9);
        imR10 = view.findViewById(R.id.formSegmentDetailR10);

        kotakL1 = view.findViewById(R.id.formSegmentKotakL1);
        kotakL2 = view.findViewById(R.id.formSegmentKotakL2);
        kotakL3 = view.findViewById(R.id.formSegmentKotakL3);
        kotakL4 = view.findViewById(R.id.formSegmentKotakL4);
        kotakL5 = view.findViewById(R.id.formSegmentKotakL5);
        kotakL6 = view.findViewById(R.id.formSegmentKotakL6);
        kotakL7 = view.findViewById(R.id.formSegmentKotakL7);
        kotakL8 = view.findViewById(R.id.formSegmentKotakL8);
        kotakL9 = view.findViewById(R.id.formSegmentKotakL9);
        kotakL10 = view.findViewById(R.id.formSegmentKotakL10);

        kotakR1 = view.findViewById(R.id.formSegmentKotakR1);
        kotakR2 = view.findViewById(R.id.formSegmentKotakR2);
        kotakR3 = view.findViewById(R.id.formSegmentKotakR3);
        kotakR4 = view.findViewById(R.id.formSegmentKotakR4);
        kotakR5 = view.findViewById(R.id.formSegmentKotakR5);
        kotakR6 = view.findViewById(R.id.formSegmentKotakR6);
        kotakR7 = view.findViewById(R.id.formSegmentKotakR7);
        kotakR8 = view.findViewById(R.id.formSegmentKotakR8);
        kotakR9 = view.findViewById(R.id.formSegmentKotakR9);
        kotakR10 = view.findViewById(R.id.formSegmentKotakR10);

        row5 = view.findViewById(R.id.formSegmentRow5);
        row6 = view.findViewById(R.id.formSegmentRow6);
        row7 = view.findViewById(R.id.formSegmentRow7);
        row8 = view.findViewById(R.id.formSegmentRow8);
        row9 = view.findViewById(R.id.formSegmentRow9);
        row10 = view.findViewById(R.id.formSegmentRow10);

        spVertical = view.findViewById(R.id.formSegmentVertical);
        spHorizontal = view.findViewById(R.id.formSegmentHorizontal);
        spTipeJalan = view.findViewById(R.id.formSegmentTipeJalan);
        edGrade= view.findViewById(R.id.formSegmentGrade);
        edLebarPvmt= view.findViewById(R.id.formSegmentPvmt);
        txJumlahLane = view.findViewById(R.id.formSegmentJumlahLane);

        bSave = view.findViewById(R.id.formSegmentSave);

        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);

        dbSegmen = new DbSegmen(context);


        helperList = new HelperList();
        helperFormTerusan = new HelperFormTerusan(context);


    }

    private void initObject(){


        spTipeJalan.setAdapter(helperList.getSpinner(context, R.array.tipe_lane));
        spVertical.setAdapter(helperList.getSpinner(context, R.array.al_vertikal));
        spHorizontal.setAdapter(helperList.getSpinner(context, R.array.al_horizontal));

        spTipeJalan.setOnItemSelectedListener(this);
        spVertical.setOnItemSelectedListener(this);
        spHorizontal.setOnItemSelectedListener(this);

    }



    private void initValue(){

        arrVertical = helperList.getListSpinner(context, R.array.al_vertikal);
        arrHorizontal = helperList.getListSpinner(context, R.array.al_horizontal);
        arrTipeJalan = helperList.getListSpinner(context, R.array.tipe_lane);

        dataSegmen = dbSegmen.getSegmen(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment());

        edLebarPvmt.setText(String.valueOf(dataSegmen.getLebarpvmt()));
        edGrade.setText(String.valueOf(dataSegmen.getGrade()));
        txJumlahLane.setText(String.valueOf(dataSegmen.getJumlahsegment()));

        spVertical.setSelection(helperList.getIndex(arrVertical, dataSegmen.getVertikal()));
        spHorizontal.setSelection(helperList.getIndex(arrHorizontal, dataSegmen.getHorizontal()));
        spTipeJalan.setSelection(helperList.getIndex(arrTipeJalan, dataSegmen.getTipejalan()));

        statL1 = dataSegmen.getSegmentl1();
        statL2 = dataSegmen.getSegmentl2();
        statL3 = dataSegmen.getSegmentl3();
        statL4 = dataSegmen.getSegmentl4();
        statL5 = dataSegmen.getSegmentl5();
        statL6 = dataSegmen.getSegmentl6();
        statL7 = dataSegmen.getSegmentl7();
        statL8 = dataSegmen.getSegmentl8();
        statL9 = dataSegmen.getSegmentl9();
        statL10 = dataSegmen.getSegmentl10();

        statR1 = dataSegmen.getSegmentr1();
        statR2 = dataSegmen.getSegmentr2();
        statR3 = dataSegmen.getSegmentr3();
        statR4 = dataSegmen.getSegmentr4();
        statR5 = dataSegmen.getSegmentr5();
        statR6 = dataSegmen.getSegmentr6();
        statR7 = dataSegmen.getSegmentr7();
        statR8 = dataSegmen.getSegmentr8();
        statR9 = dataSegmen.getSegmentr9();
        statR10 = dataSegmen.getSegmentr10();

        jumlahkiri = statL1+statL2+statL3+statL4+statL5+statL6+statL7+statL8+statL9+statL10;
        jumlahkanan = statR1+statR2+statR3+statR4+statR5+statR6+statR7+statR8+statR9+statR10;

        jumlahlane = dataSegmen.getJumlahsegment();

        setSegment(jumlahkiri, kotakL1, kotakL2, kotakL3, kotakL4, kotakL5, kotakL6, kotakL7, kotakL8, kotakL9, kotakL10,
                imL1, imL2, imL3, imL4, imL5, imL6, imL7, imL8, imL9, imL10);

        setSegment(jumlahkanan, kotakR1, kotakR2, kotakR3, kotakR4, kotakR5, kotakR6, kotakR7, kotakR8, kotakR9, kotakR10,
                imR1, imR2, imR3, imR4, imR5, imR6, imR7, imR8, imR9, imR10 );

        setRow(jumlahkiri, jumlahkanan, row5, row6, row7, row8, row9, row10);

        asal = getActivity().getIntent().getExtras().get("from").toString();

    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.formSegmentTipeJalan) {
            if(arrTipeJalan[position].equals("--Pilih--")){
                dataSegmen.setTipejalan(null);
            }else {
                dataSegmen.setTipejalan(arrTipeJalan[position]);
            }
        }else if (parent.getId() == R.id.formSegmentVertical){
            if(arrVertical[position].equals("--Pilih--")) {
                dataSegmen.setVertikal(null);
            }else {
                dataSegmen.setVertikal(arrVertical[position]);
            }
        }else if(parent.getId() == R.id.formSegmentHorizontal){
            if(arrHorizontal[position].equals("--Pilih--")) {
                dataSegmen.setHorizontal(null);
            }else {
                dataSegmen.setHorizontal(arrHorizontal[position]);
            }
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void leftClick(){

        imL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statL1==0) {
                    kotakL1.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imL2.setVisibility(View.VISIBLE);

                    imL1.setImageResource(R.drawable.icon_silang);
                    imL2.setImageResource(R.drawable.icon_add_lane);

                    statL1=1;
                    jumlahlane = jumlahlane+1;
                }else{

                    kotakL1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imL2.setVisibility(View.GONE);

                    imL1.setImageResource(R.drawable.icon_add_lane);

                    statL1=0;
                    jumlahlane = jumlahlane-1;
                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statL2==0) {
                    kotakL2.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imL1.setVisibility(View.GONE);
                    imL3.setVisibility(View.VISIBLE);

                    imL2.setImageResource(R.drawable.icon_silang);
                    imL3.setImageResource(R.drawable.icon_add_lane);

                    statL2=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakL2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imL1.setVisibility(View.VISIBLE);
                    imL3.setVisibility(View.GONE);

                    imL1.setImageResource(R.drawable.icon_silang);
                    imL2.setImageResource(R.drawable.icon_add_lane);

                    statL2=0;
                    jumlahlane = jumlahlane -1;
                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statL3==0) {
                    kotakL3.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imL2.setVisibility(View.GONE);
                    imL4.setVisibility(View.VISIBLE);

                    imL3.setImageResource(R.drawable.icon_silang);
                    imL4.setImageResource(R.drawable.icon_add_lane);

                    statL3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakL3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imL2.setVisibility(View.VISIBLE);
                    imL4.setVisibility(View.GONE);

                    imL2.setImageResource(R.drawable.icon_silang);
                    imL3.setImageResource(R.drawable.icon_add_lane);

                    statL3=0;
                    jumlahlane = jumlahlane-1;
                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statL4==0) {
                    kotakL4.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imL3.setVisibility(View.GONE);
                    imL5.setVisibility(View.VISIBLE);

                    imL4.setImageResource(R.drawable.icon_silang);
                    imL5.setImageResource(R.drawable.icon_add_lane);

                    row5.setVisibility(View.VISIBLE);

                    statL4=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakL4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imL3.setVisibility(View.VISIBLE);
                    imL5.setVisibility(View.GONE);

                    imL3.setImageResource(R.drawable.icon_silang);
                    imL4.setImageResource(R.drawable.icon_add_lane);

                    if(statR4==0) {
                        row5.setVisibility(View.GONE);
                    }

                    statL4=0;
                    jumlahlane = jumlahlane-1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imL5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statL5==0) {
                    kotakL5.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imL4.setVisibility(View.GONE);
                    imL6.setVisibility(View.VISIBLE);

                    imL5.setImageResource(R.drawable.icon_silang);
                    imL6.setImageResource(R.drawable.icon_add_lane);

                    row6.setVisibility(View.VISIBLE);

                    statL5=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakL5.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imL4.setVisibility(View.VISIBLE);
                    imL6.setVisibility(View.GONE);

                    imL4.setImageResource(R.drawable.icon_silang);
                    imL5.setImageResource(R.drawable.icon_add_lane);

                    if(statR5==0) {
                        row6.setVisibility(View.GONE);
                    }

                    statL5=0;
                    jumlahlane = jumlahlane-1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imL6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statL6==0) {
                    kotakL6.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imL5.setVisibility(View.GONE);
                    imL7.setVisibility(View.VISIBLE);

                    imL6.setImageResource(R.drawable.icon_silang);
                    imL7.setImageResource(R.drawable.icon_add_lane);

                    row7.setVisibility(View.VISIBLE);

                    statL6=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakL6.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imL5.setVisibility(View.VISIBLE);
                    imL7.setVisibility(View.GONE);

                    imL5.setImageResource(R.drawable.icon_silang);
                    imL6.setImageResource(R.drawable.icon_add_lane);

                    if(statR6==0) {
                        row7.setVisibility(View.GONE);
                    }

                    statL6=0;
                    jumlahlane = jumlahlane-1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imL7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statL7==0) {
                    kotakL7.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imL6.setVisibility(View.GONE);
                    imL8.setVisibility(View.VISIBLE);

                    imL7.setImageResource(R.drawable.icon_silang);
                    imL8.setImageResource(R.drawable.icon_add_lane);

                    row8.setVisibility(View.VISIBLE);

                    statL7=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakL7.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imL6.setVisibility(View.VISIBLE);
                    imL8.setVisibility(View.GONE);

                    imL6.setImageResource(R.drawable.icon_silang);
                    imL7.setImageResource(R.drawable.icon_add_lane);

                    if(statR7==0) {
                        row8.setVisibility(View.GONE);
                    }

                    statL7=0;
                    jumlahlane = jumlahlane-1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imL8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statL8==0) {
                    kotakL8.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imL7.setVisibility(View.GONE);
                    imL9.setVisibility(View.VISIBLE);

                    imL8.setImageResource(R.drawable.icon_silang);
                    imL9.setImageResource(R.drawable.icon_add_lane);

                    row9.setVisibility(View.VISIBLE);


                    statL8=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakL8.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imL7.setVisibility(View.VISIBLE);
                    imL9.setVisibility(View.GONE);

                    imL7.setImageResource(R.drawable.icon_silang);
                    imL8.setImageResource(R.drawable.icon_add_lane);

                    if(statR8==0) {
                        row9.setVisibility(View.GONE);
                    }


                    statL8=0;
                    jumlahlane = jumlahlane-1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imL9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statL9==0) {
                    kotakL9.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imL8.setVisibility(View.GONE);
                    imL10.setVisibility(View.VISIBLE);

                    imL9.setImageResource(R.drawable.icon_silang);
                    imL10.setImageResource(R.drawable.icon_add_lane);

                    row10.setVisibility(View.VISIBLE);


                    statL9=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakL9.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imL8.setVisibility(View.VISIBLE);
                    imL10.setVisibility(View.GONE);

                    imL8.setImageResource(R.drawable.icon_silang);
                    imL9.setImageResource(R.drawable.icon_add_lane);

                    if(statR9==0) {
                        row10.setVisibility(View.GONE);
                    }


                    statL9=0;
                    jumlahlane = jumlahlane-1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imL10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statL10==0) {
                    kotakL10.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imL9.setVisibility(View.GONE);

                    imL10.setImageResource(R.drawable.icon_silang);


                    statL10=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakL10.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imL9.setVisibility(View.VISIBLE);

                    imL9.setImageResource(R.drawable.icon_silang);
                    imL10.setImageResource(R.drawable.icon_add_lane);


                    statL10=0;
                    jumlahlane = jumlahlane-1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

    }

    public void rightClick() {
        imR1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statR1 == 0) {
                    kotakR1.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imR2.setVisibility(View.VISIBLE);

                    imR1.setImageResource(R.drawable.icon_silang);
                    imR2.setImageResource(R.drawable.icon_add_lane);

                    statR1 = 1;
                    jumlahlane = jumlahlane + 1;
                } else {
                    kotakR1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imR2.setVisibility(View.GONE);

                    imR1.setImageResource(R.drawable.icon_add_lane);

                    statR1 = 0;
                    jumlahlane = jumlahlane - 1;
                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imR2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statR2 == 0) {
                    kotakR2.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imR1.setVisibility(View.GONE);
                    imR3.setVisibility(View.VISIBLE);

                    imR2.setImageResource(R.drawable.icon_silang);
                    imR3.setImageResource(R.drawable.icon_add_lane);

                    statR2 = 1;
                    jumlahlane = jumlahlane + 1;
                } else {
                    kotakR2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imR1.setVisibility(View.VISIBLE);
                    imR3.setVisibility(View.GONE);

                    imR1.setImageResource(R.drawable.icon_silang);
                    imR2.setImageResource(R.drawable.icon_add_lane);

                    statR2 = 0;
                    jumlahlane = jumlahlane - 1;
                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imR3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statR3 == 0) {
                    kotakR3.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imR2.setVisibility(View.GONE);
                    imR4.setVisibility(View.VISIBLE);

                    imR3.setImageResource(R.drawable.icon_silang);
                    imR4.setImageResource(R.drawable.icon_add_lane);

                    statR3 = 1;
                    jumlahlane = jumlahlane + 1;
                } else {
                    kotakR3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imR2.setVisibility(View.VISIBLE);
                    imR4.setVisibility(View.GONE);

                    imR2.setImageResource(R.drawable.icon_silang);
                    imR3.setImageResource(R.drawable.icon_add_lane);

                    statR3 = 0;
                    jumlahlane = jumlahlane - 1;
                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imR4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statR4 == 0) {
                    kotakR4.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imR3.setVisibility(View.GONE);
                    imR5.setVisibility(View.VISIBLE);

                    imR4.setImageResource(R.drawable.icon_silang);
                    imR5.setImageResource(R.drawable.icon_add_lane);

                    row5.setVisibility(View.VISIBLE);

                    statR4 = 1;
                    jumlahlane = jumlahlane + 1;

                } else {
                    kotakR4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imR3.setVisibility(View.VISIBLE);
                    imR5.setVisibility(View.GONE);

                    imR3.setImageResource(R.drawable.icon_silang);
                    imR4.setImageResource(R.drawable.icon_add_lane);

                    if (statL4 == 0) {
                        row5.setVisibility(View.GONE);
                    }

                    statR4 = 0;
                    jumlahlane = jumlahlane - 1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imR5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statR5 == 0) {
                    kotakR5.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imR4.setVisibility(View.GONE);
                    imR6.setVisibility(View.VISIBLE);

                    imR5.setImageResource(R.drawable.icon_silang);
                    imR6.setImageResource(R.drawable.icon_add_lane);

                    row6.setVisibility(View.VISIBLE);

                    statR5 = 1;
                    jumlahlane = jumlahlane + 1;

                } else {
                    kotakR5.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imR4.setVisibility(View.VISIBLE);
                    imR6.setVisibility(View.GONE);

                    imR4.setImageResource(R.drawable.icon_silang);
                    imR5.setImageResource(R.drawable.icon_add_lane);

                    if (statL5 == 0) {
                        row6.setVisibility(View.GONE);
                    }

                    statR5 = 0;
                    jumlahlane = jumlahlane - 1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imR6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statR6 == 0) {
                    kotakR6.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imR5.setVisibility(View.GONE);
                    imR7.setVisibility(View.VISIBLE);

                    imR6.setImageResource(R.drawable.icon_silang);
                    imR7.setImageResource(R.drawable.icon_add_lane);

                    row7.setVisibility(View.VISIBLE);

                    statR6 = 1;
                    jumlahlane = jumlahlane + 1;

                } else {
                    kotakR6.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imR5.setVisibility(View.VISIBLE);
                    imR7.setVisibility(View.GONE);

                    imR5.setImageResource(R.drawable.icon_silang);
                    imR6.setImageResource(R.drawable.icon_add_lane);

                    if (statL6 == 0) {
                        row7.setVisibility(View.GONE);
                    }

                    statR6 = 0;
                    jumlahlane = jumlahlane - 1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imR7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statR7 == 0) {
                    kotakR7.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imR6.setVisibility(View.GONE);
                    imR8.setVisibility(View.VISIBLE);

                    imR7.setImageResource(R.drawable.icon_silang);
                    imR8.setImageResource(R.drawable.icon_add_lane);

                    row8.setVisibility(View.VISIBLE);

                    statR7 = 1;
                    jumlahlane = jumlahlane + 1;

                } else {
                    kotakR7.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imR6.setVisibility(View.VISIBLE);
                    imR8.setVisibility(View.GONE);

                    imR6.setImageResource(R.drawable.icon_silang);
                    imR7.setImageResource(R.drawable.icon_add_lane);

                    if (statL7 == 0) {
                        row8.setVisibility(View.GONE);
                    }

                    statR7 = 0;
                    jumlahlane = jumlahlane - 1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imR8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statR8 == 0) {
                    kotakR8.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imR7.setVisibility(View.GONE);
                    imR9.setVisibility(View.VISIBLE);

                    imR8.setImageResource(R.drawable.icon_silang);
                    imR9.setImageResource(R.drawable.icon_add_lane);

                    row9.setVisibility(View.VISIBLE);


                    statR8 = 1;
                    jumlahlane = jumlahlane + 1;

                } else {
                    kotakR8.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imR7.setVisibility(View.VISIBLE);
                    imR9.setVisibility(View.GONE);

                    imR7.setImageResource(R.drawable.icon_silang);
                    imR8.setImageResource(R.drawable.icon_add_lane);

                    if (statL8 == 0) {
                        row9.setVisibility(View.GONE);
                    }


                    statR8 = 0;
                    jumlahlane = jumlahlane - 1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });


        imR9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statR9 == 0) {
                    kotakR9.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imR8.setVisibility(View.GONE);
                    imR10.setVisibility(View.VISIBLE);

                    imR9.setImageResource(R.drawable.icon_silang);
                    imR10.setImageResource(R.drawable.icon_add_lane);

                    row10.setVisibility(View.VISIBLE);


                    statR9 = 1;
                    jumlahlane = jumlahlane + 1;

                } else {
                    kotakR9.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imR8.setVisibility(View.VISIBLE);
                    imR10.setVisibility(View.GONE);

                    imR8.setImageResource(R.drawable.icon_silang);
                    imR9.setImageResource(R.drawable.icon_add_lane);

                    if (statL9 == 0) {
                        row10.setVisibility(View.GONE);
                    }


                    statR9 = 0;
                    jumlahlane = jumlahlane - 1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

        imR10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statR10 == 0) {
                    kotakR10.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    imR9.setVisibility(View.GONE);

                    imR10.setImageResource(R.drawable.icon_silang);


                    statR10 = 1;
                    jumlahlane = jumlahlane + 1;

                } else {
                    kotakR10.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    imR9.setVisibility(View.VISIBLE);

                    imR9.setImageResource(R.drawable.icon_silang);
                    imR10.setImageResource(R.drawable.icon_add_lane);


                    statR10 = 0;
                    jumlahlane = jumlahlane - 1;

                }
                txJumlahLane.setText(String.valueOf(jumlahlane));
            }
        });

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

    private void setSegment(int jumlah,
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


    private void setTipe(int valueVisible, boolean valueEnable, ImageView g1, ImageView g2, ImageView g3, ImageView g4, ImageView g5, ImageView g6, ImageView g7, ImageView g8 , ImageView g9, ImageView g10){

        g1.setVisibility(valueVisible);
        g1.setEnabled(valueEnable);
        g2.setVisibility(valueVisible);
        g2.setEnabled(valueEnable);
        g3.setVisibility(valueVisible);
        g3.setEnabled(valueEnable);
        g4.setVisibility(valueVisible);
        g4.setEnabled(valueEnable);
        g5.setVisibility(valueVisible);
        g5.setEnabled(valueEnable);
        g6.setVisibility(valueVisible);
        g6.setEnabled(valueEnable);
        g7.setVisibility(valueVisible);
        g7.setEnabled(valueEnable);
        g8.setVisibility(valueVisible);
        g8.setEnabled(valueEnable);
        g9.setVisibility(valueVisible);
        g9.setEnabled(valueEnable);
        g10.setVisibility(valueVisible);
        g10.setEnabled(valueEnable);

    }
}
