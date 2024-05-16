package com.example.roadmanagement.kaltara.FormTab;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.FormTab.Helper.AnimationTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.DialogTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.IntTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.SaveLogic;
import com.example.roadmanagement.kaltara.FormTerusan.FragmentSegmentTerusan;
import com.example.roadmanagement.kaltara.FormTerusan.HelperFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.helper.HelperList;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.PermissionHelper;
import com.example.roadmanagement.kaltara.helper.PermissionImage;
import com.example.roadmanagement.kaltara.helper.Session;

public class TabSegment extends Fragment implements AdapterView.OnItemSelectedListener{

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

    private TextView txLane, txTipe, txHor, txVer;
    private SaveLogic saveLogic;
    ImageView iconEdit, iconDone, iconCancel, iconExpand;
    LinearLayoutCompat formEdit, formShow;
    private String valProv, valRuas;
    private int valSeg, valSubseg;

    private boolean flag_show = true;
    private ViewGroup parentView;


    public static TabSegment newInstance(String noprov, String noruas, int noseg, int subseg) {
        TabSegment fragment = new TabSegment();
        Bundle args = new Bundle();
        args.putString("provinsi", noprov);
        args.putString("ruas", noruas);
        args.putInt("segment", noseg);
        args.putInt("subseg", subseg);
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

        View view = inflater.inflate(R.layout.tab_fragment_segment, container, false);
        parentView = container;

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();

        AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);

        if(session.getUserTipe()==99){
            iconEdit.setVisibility(View.GONE);
        }else{
            iconEdit.setVisibility(View.VISIBLE);
        }

        edLebarPvmt.setEnabled(false);

        leftClick();
        rightClick();

        iconEdit.setOnClickListener(view1 -> {
            AnimationTab.setFormEdit(formEdit,  true, iconEdit, iconDone, iconCancel, iconExpand, parentView);
        });

        iconDone.setOnClickListener(view1 -> {

            DialogTab.setDialogSave(context, position -> {


                if(position==1){

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


                    flag_show = true;
                    AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);
                    AnimationTab.setFormShow(formShow,  flag_show, iconExpand, parentView);
                    AnimationTab.hideKeyboardFrom(context, view);


                    saveLogic.saveSegment(dataSegmen);
                    initValue();
                    Toast.makeText(context, "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show();



                }

            });


        });

        iconCancel.setOnClickListener(view1 -> {

            DialogTab.setDialogAlert(context, "Apakah anda ingin membatalkan perubahan ?", new IntTab() {
                @Override
                public void sendPosition(int position) {
                    if(position==1){
                        flag_show = true;
                        AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);
                        AnimationTab.setFormShow(formShow,  flag_show, iconExpand, parentView);
                        AnimationTab.hideKeyboardFrom(context, view);
                    }
                }
            });

        });

        iconExpand.setOnClickListener(view1 -> {
            flag_show = !flag_show;
            AnimationTab.setFormShow(formShow,  flag_show, iconExpand, parentView);

        });

        edGrade.setOnFocusChangeListener((view1, b) -> {
            AnimationTab.setFormShow(formShow, getParamShow(b), iconExpand, parentView);
        });

        edLebarPvmt.setOnFocusChangeListener((view1, b) -> {
            AnimationTab.setFormShow(formShow, getParamShow(b), iconExpand, parentView);
        });

        return view;
    }

    private boolean getParamShow(boolean param){

        if(param){
            return false;
        }else{
            return flag_show;
        }

    }

    private void initWidget(View view){

        imL1 = view.findViewById(R.id.tabSegmentDetailL1);
        imL2 = view.findViewById(R.id.tabSegmentDetailL2);
        imL3 = view.findViewById(R.id.tabSegmentDetailL3);
        imL4 = view.findViewById(R.id.tabSegmentDetailL4);
        imL5 = view.findViewById(R.id.tabSegmentDetailL5);
        imL6 = view.findViewById(R.id.tabSegmentDetailL6);
        imL7 = view.findViewById(R.id.tabSegmentDetailL7);
        imL8 = view.findViewById(R.id.tabSegmentDetailL8);
        imL9 = view.findViewById(R.id.tabSegmentDetailL9);
        imL10 = view.findViewById(R.id.tabSegmentDetailL10);

        imR1 = view.findViewById(R.id.tabSegmentDetailR1);
        imR2 = view.findViewById(R.id.tabSegmentDetailR2);
        imR3 = view.findViewById(R.id.tabSegmentDetailR3);
        imR4 = view.findViewById(R.id.tabSegmentDetailR4);
        imR5 = view.findViewById(R.id.tabSegmentDetailR5);
        imR6 = view.findViewById(R.id.tabSegmentDetailR6);
        imR7 = view.findViewById(R.id.tabSegmentDetailR7);
        imR8 = view.findViewById(R.id.tabSegmentDetailR8);
        imR9 = view.findViewById(R.id.tabSegmentDetailR9);
        imR10 = view.findViewById(R.id.tabSegmentDetailR10);

        kotakL1 = view.findViewById(R.id.tabSegmentKotakL1);
        kotakL2 = view.findViewById(R.id.tabSegmentKotakL2);
        kotakL3 = view.findViewById(R.id.tabSegmentKotakL3);
        kotakL4 = view.findViewById(R.id.tabSegmentKotakL4);
        kotakL5 = view.findViewById(R.id.tabSegmentKotakL5);
        kotakL6 = view.findViewById(R.id.tabSegmentKotakL6);
        kotakL7 = view.findViewById(R.id.tabSegmentKotakL7);
        kotakL8 = view.findViewById(R.id.tabSegmentKotakL8);
        kotakL9 = view.findViewById(R.id.tabSegmentKotakL9);
        kotakL10 = view.findViewById(R.id.tabSegmentKotakL10);

        kotakR1 = view.findViewById(R.id.tabSegmentKotakR1);
        kotakR2 = view.findViewById(R.id.tabSegmentKotakR2);
        kotakR3 = view.findViewById(R.id.tabSegmentKotakR3);
        kotakR4 = view.findViewById(R.id.tabSegmentKotakR4);
        kotakR5 = view.findViewById(R.id.tabSegmentKotakR5);
        kotakR6 = view.findViewById(R.id.tabSegmentKotakR6);
        kotakR7 = view.findViewById(R.id.tabSegmentKotakR7);
        kotakR8 = view.findViewById(R.id.tabSegmentKotakR8);
        kotakR9 = view.findViewById(R.id.tabSegmentKotakR9);
        kotakR10 = view.findViewById(R.id.tabSegmentKotakR10);

        row5 = view.findViewById(R.id.tabSegmentRow5);
        row6 = view.findViewById(R.id.tabSegmentRow6);
        row7 = view.findViewById(R.id.tabSegmentRow7);
        row8 = view.findViewById(R.id.tabSegmentRow8);
        row9 = view.findViewById(R.id.tabSegmentRow9);
        row10 = view.findViewById(R.id.tabSegmentRow10);

        spVertical = view.findViewById(R.id.tabSegmentSpVertical);
        spHorizontal = view.findViewById(R.id.tabSegmentSpHorizon);
        spTipeJalan = view.findViewById(R.id.tabSegmentSpTipeJalan);
        edGrade= view.findViewById(R.id.tabSegmentEdGrade);
        edLebarPvmt= view.findViewById(R.id.tabSegmentEdLebar);
        txJumlahLane = view.findViewById(R.id.tabSegmentTxJumlahLane);

        formEdit = view.findViewById(R.id.tabSegmentFormEdit);
        formShow = view.findViewById(R.id.tabSegmentFormShow);

        txLane = view.findViewById(R.id.tabSegmentTxLane);
        txTipe = view.findViewById(R.id.tabSegmentTxTipeJalan);
        txHor = view.findViewById(R.id.tabSegmentTxHorizon);
        txVer = view.findViewById(R.id.tabSegmentTxVertical);


        iconEdit = view.findViewById(R.id.tabToolEdit);
        iconCancel = view.findViewById(R.id.tabToolClose);
        iconDone= view.findViewById(R.id.tabToolSave);
        iconExpand = view.findViewById(R.id.tabToolExpand);

        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);
        saveLogic = new SaveLogic(context);

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

        HelperTextValue helperTextValue = new HelperTextValue();
        helperTextValue.setTextString(helperTextValue.tipeHorizontal(dataSegmen.getHorizontal()), txHor);
        helperTextValue.setTextString(helperTextValue.tipeVertikal(dataSegmen.getVertikal()), txVer);
        helperTextValue.setTextString(dataSegmen.getTipejalan(), txTipe);
        txLane.setText(String.valueOf(dataSegmen.getJumlahsegment()));

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

    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == spTipeJalan.getId()) {
            if(arrTipeJalan[position].equals("--Pilih--")){
                dataSegmen.setTipejalan(null);
            }else {
                dataSegmen.setTipejalan(arrTipeJalan[position]);
            }
        }else if (parent.getId() == spVertical.getId()){
            if(arrVertical[position].equals("--Pilih--")) {
                dataSegmen.setVertikal(null);
            }else {
                dataSegmen.setVertikal(arrVertical[position]);
            }
        }else if(parent.getId() == spHorizontal.getId()){
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