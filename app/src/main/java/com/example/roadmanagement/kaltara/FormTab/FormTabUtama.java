package com.example.roadmanagement.kaltara.FormTab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.FormTab.Helper.AnimationTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.DialogTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.FormTabAtributAdapter;
import com.example.roadmanagement.kaltara.FormTab.Helper.FormTabLaneAdapter;
import com.example.roadmanagement.kaltara.FormTab.Helper.IntTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.ObAtribut;
import com.example.roadmanagement.kaltara.FormTab.Helper.StringTab;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.SegmentClick;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.Tabel.LihatTabel;
import com.example.roadmanagement.kaltara.adapter.SegmentAdapter;
import com.example.roadmanagement.kaltara.adapter.SubSegmentAdapter;
import com.example.roadmanagement.kaltara.api.Apiku;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.DialogHelper;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.PermissionHelper;
import com.example.roadmanagement.kaltara.helper.RepeaterOnclick;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.lokasi.PilihLokasi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class FormTabUtama extends AppCompatActivity {
    private Context context;

    private RecyclerView recyclerView, recSub, recAtribut, recLaneAtribut;
    private CardView cdAtrKiri, cdAtrKanan;
    private TextView txAtKiri, txAtKanan, judulProvinsi, judulRuas, judulJenis, staawal, noSegment;
    private ImageView endSurvey;
    private LinearLayoutManager horizontalku;
    private FloatingActionButton lihatTabel, lihatPeta, buttonleft,buttonright;
    private FrameLayout frameLayout;


    private Retrofit retrofit;
    private Apiku api;
    private Session session;
    private HelperTextValue helperTextValue;
    private DialogHelper dialogHelper;
    private PermissionHelper permissionHelper;


    private SegmentAdapter segmentAdapter;
    private SubSegmentAdapter subSegmentAdapter;
    private FormTabAtributAdapter atributAdapter;

    private FormTabLaneAdapter laneAdapter;

    private DbSegmen dbSegmen;
    private DbSpinner dbSpinner;

    private ArrayList<ObAtribut> arrayAtribut = new ArrayList<>();
    private ArrayList<SingleSegment> listsegment = new ArrayList<>();
    private ArrayList<SingleSegment> listSub = new ArrayList<>();
    private String valProv, valRuas, valPosisi, valArah, valLajur;
    private int valSegment, valSubSegment, valAtribut, valUser, maxSegment;

    private FragmentTransaction fragmentTransaction;

    String valText1, valText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tab_utama);

        context = FormTabUtama.this;
        declareWidget();
        setHelperWidget(context);
        getValue(context);

        judulProvinsi.setText(valProv);
        judulRuas.setText(valRuas);
        helperTextValue.setTextString(valArah, judulJenis);

        SingleSegment singleSegment = dbSpinner.getSpinner(valProv, valRuas, valSegment, valSubSegment);
        setFormAtribut(0, valProv, valRuas, valSegment, valSubSegment, valPosisi);
        setValue(valSegment, valSubSegment, singleSegment.getStaawal()+" - "+singleSegment.getStaakhir());
        setListSegment();
        setListSubsegment(valProv, valRuas, valSegment);
        setRecAtribut(valPosisi);


        lihatTabel.setOnClickListener(v -> {

            if(session.getFlagForm()) {

                DialogTab.setDialogAlert(context, "Data perubahan belum tersimpan, tetap lanjut ?", position -> {
                    if (position == 1) {
                        Intent i = new Intent(FormTabUtama.this, LihatTabel.class);
                        i.putExtra("posisi", session.getPosisiTable());
                        startActivity(i);
                    }
                });

            }else{

                Intent i = new Intent(FormTabUtama.this, LihatTabel.class);
                i.putExtra("posisi", session.getPosisiTable());
                startActivity(i);

            }

        });

        lihatPeta.setOnClickListener(v -> {

            if(session.getFlagForm()) {

                DialogTab.setDialogAlert(context, "Data perubahan belum tersimpan, tetap lanjut ?", position -> {
                    if (position == 1) {
                        Intent i = new Intent(context, PilihLokasi.class);

                        if (permissionHelper.viewLocation()) {
                            startActivity(i);
                        }
                    }
                });
            }else{

                Intent i = new Intent(context, PilihLokasi.class);

                if (permissionHelper.viewLocation()) {
                    startActivity(i);
                }

            }



        });


        if(valArah.equals("Opposite")){
            horizontalku.setReverseLayout(true);
        }else{
            horizontalku.setReverseLayout(false);
        }

        if(valPosisi != null){
            setButtonPosisi(valPosisi);
        }

        if(session.getTipesurvey()==null||session.getSurvey()==0) {
            session.saveSPInt(Session.SURVEY, 0 );
            session.saveSPString(Session.TIPESURVEY, null);
            session.saveSPInt(Session.SP_NOSEGMENT, 1);
            session.saveSPInt(Session.SP_SUBSEGMENT, 0 );
            session.saveSPInt(Session.FOKUS, 0);
            Intent intent = new Intent(context, Menuutama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
            ((Activity) context).finish();
        }


        int ambil = session.getNosegment()-1;

        //session.saveSPInt(Session.TIPEUSER, 99);


        scrolling();

        cdAtrKiri.setOnClickListener(view -> {

            if(session.getFlagForm()){
                DialogTab.setDialogAlert(context, "Data perubahan belum tersimpan, tetap lanjut ?", position -> {
                    if (position == 1) {
                        setButtonPosisi(valText1);
                        session.saveSPString(Session.POSISI, valText1);
                    }
                });
            }else{
                setButtonPosisi(valText1);
                session.saveSPString(Session.POSISI, valText1);
            }




        });

        cdAtrKanan.setOnClickListener(view -> {
            if(session.getFlagForm()) {
                DialogTab.setDialogAlert(context, "Data perubahan belum tersimpan, tetap lanjut ?", position -> {
                    if (position == 1) {
                        setButtonPosisi(valText2);
                        session.saveSPString(Session.POSISI, valText2);
                    }
                });
            }else{
                setButtonPosisi(valText2);
                session.saveSPString(Session.POSISI, valText2);
            }
        });


        endSurvey.setOnClickListener(v -> {

            if(session.getFlagForm()) {

                DialogTab.setDialogAlert(context, "Data perubahan belum tersimpan, tetap lanjut ?", position -> {
                    if (position == 1) {
                        dialogHelper.dialogEnd();
                    }
                });
            }else{
                dialogHelper.dialogEnd();
            }



        });

    }

    private void declareWidget(){

        endSurvey = findViewById(R.id.surveyEnd);

        recyclerView = findViewById(R.id.recycleformutama);
        recSub = findViewById(R.id.recycleSubsegment);
        buttonleft = findViewById(R.id.buttonleft);
        buttonright = findViewById(R.id.buttonright);

        staawal = findViewById(R.id.sta);
        noSegment = findViewById(R.id.nosegmen);

        judulProvinsi = findViewById(R.id.judulprovinsi);
        judulRuas = findViewById(R.id.judulruas);
        judulJenis = findViewById(R.id.juduljenis);

        lihatTabel = findViewById(R.id.lihatTabel);
        lihatPeta = findViewById(R.id.lihatPeta);

        cdAtrKanan = findViewById(R.id.formUtCdKanan);
        cdAtrKiri = findViewById(R.id.formUtCdKiri);
        txAtKiri = findViewById(R.id.formUtTextKiri);
        txAtKanan = findViewById(R.id.formUtTextKanan);

        recAtribut = findViewById(R.id.formUtRecAtr);

        recLaneAtribut = findViewById(R.id.formUtRecLane);

        frameLayout = findViewById(R.id.formUtFrameLayout);


    }

    private void getValue(Context context){

        session = new Session(context);

        valProv = session.getKodeprov();
        valRuas = session.getNoruas();
        valSegment = session.getNosegment();
        valSubSegment = session.getSubsegment();
        valUser = session.getUserTipe();
        valArah = session.getTipesurvey();

        //session.saveSPString(Session.TIPESURVEY, "Normal");

        session.saveSPBoolean(Session.FLAG_FORM, false);

        if(session.getFlagTab()){
            session.saveSPBoolean(Session.FLAG_TAB, false);
        }else{
            session.saveSPString(Session.POSISI, null);
            session.saveSPInt(Session.TAB_ATRIBUT, 0);
            session.saveSPInt(Session.TAB_ATRIBUT_LANE, 0);
        }

        if(session.getTipesurvey().equals("Opposite")){
            valText1 = "kanan";
            valText2 = "kiri";

            txAtKiri.setText("R(Kanan)");
            txAtKanan.setText("L(Kiri)");
        }else{
            valText1 = "kiri";
            valText2 = "kanan";

            txAtKanan.setText("R(Kanan)");
            txAtKiri.setText("L(Kiri)");
        }


        valPosisi = session.getPosisi();
        valAtribut = session.getTabAtribut();


        maxSegment = dbSpinner.getMaksSegment(valProv, valRuas);
        listsegment = dbSpinner.listSegment(valProv, valRuas);

    }

    private void setHelperWidget(Context context){

        helperTextValue = new HelperTextValue();
        horizontalku = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        dialogHelper = new DialogHelper(context);
        permissionHelper = new PermissionHelper(context);
        dbSpinner = new DbSpinner(context);
        dbSegmen = new DbSegmen(context);

        api = new Apiku(this);
        retrofit = api.initRetrofit();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

    }


    private void setFormAtribut(int position, String tProv, String tRuas, int tSeg, int tSubseg, String tPosisi){

        //session.saveSPBoolean(Session.FLAG_FORM, false);

        recLaneAtribut.setVisibility(position==5?View.VISIBLE : View.GONE);

        Fragment frAdd = null;

        AnimationTab.hideKeyboardFrom(FormTabUtama.this);

        session.saveSPInt(Session.POSISITABEL, position);

        switch (position){
            case 0 :
            {
                frAdd = TabSegment.newInstance(tProv, tRuas, tSeg, tSubseg);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();
            }
                break;
            case 1 : {
                frAdd = TabMedian.newInstance(tProv, tRuas, tSeg, tSubseg);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();
            }
            break;
            case 2 : {
                frAdd = TabLahan.newInstance(tProv, tRuas, tSeg, tSubseg, tPosisi);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();
            }
            break;

            case 3:{

                frAdd = TabSaluran.newInstance(tProv, tRuas, tSeg, tSubseg, tPosisi);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();

            }
            break;

            case 4:{

                frAdd = TabBahu.newInstance(tProv, tRuas, tSeg, tSubseg, tPosisi);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();

            }
            break;

            case 5:{


                Fragment fragment = getSupportFragmentManager().findFragmentByTag("Show_form");
                if(fragment!=null){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.remove(fragment).commit();
                }


                setRecAtributLane(tProv, tRuas, tSeg, tSubseg, tPosisi);

            }
            break;

            case 6:{

                frAdd = TabPerkerasan.newInstance(tProv, tRuas, tSeg, tSubseg, tPosisi);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();

            }
            break;

            case 7:{

                frAdd = TabKerasDalam.newInstance(tProv, tRuas, tSeg, tSubseg, tPosisi);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();

            }
            break;

            case 8:{

                frAdd = TabKerasLuar.newInstance(tProv, tRuas, tSeg, tSubseg, tPosisi);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();

            }
            break;

            case 9:{

                frAdd = TabInlet.newInstance(tProv, tRuas, tSeg, tSubseg, tPosisi);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();

            }
            break;

            case 10:{

                frAdd = TabOutlet.newInstance(tProv, tRuas, tSeg, tSubseg, tPosisi);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();

            }
            break;

            case 11:{

                frAdd = TabGorong.newInstance(tProv, tRuas, tSeg, tSubseg, tPosisi);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form").commit();

            }
            break;

        }



    }



    private void setRecAtributLane(String noprov, String noruas, int noseg, int subseg, String posisi){

        session.saveSPBoolean(Session.FLAG_FORM, false);

        ArrayList arrayList = getListLane(noprov, noruas, noseg, subseg, posisi);
        valLajur = getLajur(arrayList, posisi);

        if(valLajur!=null){
            //Toast.makeText(context, valLajur, Toast.LENGTH_SHORT).show();
            setLaneAtribut(noprov, noruas, noseg, subseg, posisi, valLajur);
        }

        laneAdapter = new FormTabLaneAdapter(arrayList, context, lajur -> setLaneAtribut(noprov, noruas, noseg, subseg, posisi, lajur));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recLaneAtribut.setLayoutManager(linearLayoutManager);
        recLaneAtribut.setHasFixedSize(true);
        recLaneAtribut.setAdapter(laneAdapter);

        laneAdapter.notifyDataSetChanged();

    }

    private void setLaneAtribut(String noprov, String noruas, int noseg, int subseg, String posisi, String lajur){
        Fragment frAdd = TabLane.newInstance(noprov, noruas, noseg, subseg, posisi, lajur);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.formUtFrameLayout, frAdd, "Show_form_lane").commit();
    }

    private String getLajur(ArrayList<String> arrayList, String posisi){

        String posisiku;
        String lajurku;

        if(posisi.equals("kiri")){
            posisiku = "L";
        }else{
            posisiku = "R";
        }

        if(arrayList.size()>0) {

            if (session.getTabAtributLane() < arrayList.size()) {
                lajurku = posisiku + String.valueOf(session.getTabAtributLane() + 1);
            } else {
                session.saveSPInt(Session.TAB_ATRIBUT_LANE, arrayList.size() - 1);
                if (session.getTabAtributLane() > 0) {
                    lajurku = posisiku + String.valueOf(session.getTabAtributLane() + 1);
                } else {
                    lajurku = posisiku + "1";
                }
            }
        }else{
            lajurku = null;
        }

        return lajurku;

    }

    private ArrayList<String> getListLane(String noprov, String noruas, int seg, int subseg, String posisi){

        ArrayList<String> listku = new ArrayList<>();
        DataSegmen dataSegmen = dbSegmen.getSegmen(noprov, noruas, seg, subseg);

        if(posisi.equals("kiri")){

            if(getParamLane(dataSegmen.getSegmentl1()))listku.add("L1");
            if(getParamLane(dataSegmen.getSegmentl2()))listku.add("L2");
            if(getParamLane(dataSegmen.getSegmentl3()))listku.add("L3");
            if(getParamLane(dataSegmen.getSegmentl4()))listku.add("L4");
            if(getParamLane(dataSegmen.getSegmentl5()))listku.add("L5");
            if(getParamLane(dataSegmen.getSegmentl6()))listku.add("L6");
            if(getParamLane(dataSegmen.getSegmentl7()))listku.add("L7");
            if(getParamLane(dataSegmen.getSegmentl8()))listku.add("L8");
            if(getParamLane(dataSegmen.getSegmentl9()))listku.add("L9");
            if(getParamLane(dataSegmen.getSegmentl10()))listku.add("L10");

        }else{

            if(getParamLane(dataSegmen.getSegmentr1()))listku.add("R1");
            if(getParamLane(dataSegmen.getSegmentr2()))listku.add("R2");
            if(getParamLane(dataSegmen.getSegmentr3()))listku.add("R3");
            if(getParamLane(dataSegmen.getSegmentr4()))listku.add("R4");
            if(getParamLane(dataSegmen.getSegmentr5()))listku.add("R5");
            if(getParamLane(dataSegmen.getSegmentr6()))listku.add("R6");
            if(getParamLane(dataSegmen.getSegmentr7()))listku.add("R7");
            if(getParamLane(dataSegmen.getSegmentr8()))listku.add("R8");
            if(getParamLane(dataSegmen.getSegmentr9()))listku.add("R9");
            if(getParamLane(dataSegmen.getSegmentr10()))listku.add("R10");

        }

        return listku;



    }

    private boolean getParamLane(int param){

        if(param==1){
            return true;
        }else{
            return false;
        }
    }



    public void setValue(int segment, int subsegment, String sta){

        noSegment.setText("SEGMENT - "+segment+"."+subsegment);
        staawal.setText(sta);

    }

    private void createArray(String paramPosisi){

        arrayAtribut.clear();

        arrayAtribut.add(new ObAtribut("Segment", "#2a2a2a"));
        arrayAtribut.add(new ObAtribut("Median", "#2a2a2a"));

        if(paramPosisi!=null){
            /*arrayAtribut.add(new ObAtribut("Lahan", "#0B700F"));
            arrayAtribut.add(new ObAtribut("Saluran", "#3F51B5"));
            arrayAtribut.add(new ObAtribut("Bahu", "#676666"));
            arrayAtribut.add(new ObAtribut("Inlet", "#BC1D53"));
            arrayAtribut.add(new ObAtribut("Outlet", "#816719"));
            arrayAtribut.add(new ObAtribut("Gorong", "#0B700F"));
            arrayAtribut.add(new ObAtribut("Lane", "#2a2a2a"));*/

            arrayAtribut.add(new ObAtribut("Lahan", "#2a2a2a"));
            arrayAtribut.add(new ObAtribut("Saluran", "#2a2a2a"));
            arrayAtribut.add(new ObAtribut("Bahu", "#2a2a2a"));
            arrayAtribut.add(new ObAtribut("Lane", "#2a2a2a"));
            arrayAtribut.add(new ObAtribut("Perkerasan", "#2a2a2a"));
            arrayAtribut.add(new ObAtribut("SPD", "#2a2a2a"));
            arrayAtribut.add(new ObAtribut("SPL", "#2a2a2a"));
            arrayAtribut.add(new ObAtribut("Inlet", "#2a2a2a"));
            arrayAtribut.add(new ObAtribut("Outlet", "#2a2a2a"));
            arrayAtribut.add(new ObAtribut("Gorong", "#2a2a2a"));

        }



    }

    private void setRecAtribut(String paramPosisi){

        createArray(paramPosisi);

        atributAdapter = new FormTabAtributAdapter(arrayAtribut, context, new IntTab() {
            @Override
            public void sendPosition(int position) {

                valAtribut = position;
                setFormAtribut(valAtribut, valProv, valRuas, valSegment, valSubSegment, valPosisi);

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recAtribut.setLayoutManager(linearLayoutManager);
        recAtribut.setHasFixedSize(true);
        recAtribut.setAdapter(atributAdapter);

        atributAdapter.notifyDataSetChanged();

    }


    public void setListSegment() {

        listsegment = dbSpinner.listSegment(String.valueOf(valProv), valRuas);
        segmentAdapter = new SegmentAdapter(listsegment, context, new SegmentClick() {
            @Override
            public void Klik(String provinsi, String ruas, int segment, int subsegment, String km, String sta) {

                setValue(segment, subsegment, sta);
                setListSubsegment(provinsi, ruas, segment);

                valSegment = segment;
                valSubSegment = subsegment;

                setFormAtribut(valAtribut, valProv, valRuas, valSegment, valSubSegment, valPosisi);


            }
        });

        recyclerView.setLayoutManager(horizontalku);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(segmentAdapter);

        recyclerView.scrollToPosition(valSegment-2);
        recyclerView.setScrollY(3);
        segmentAdapter.notifyDataSetChanged();


    }

    public void setListSubsegment(String provinsi, String ruas, int segment) {

        listSub.clear();
        listSub = dbSpinner.listSubSegment(provinsi, ruas, segment);

        if(listSub.size()>1){

            session.saveSPInt(Session.FOKUS, (segment-1)*10);

            recSub.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            if(valArah.equals("Opposite")){
                linearLayoutManager.setReverseLayout(true);
            }else{
                linearLayoutManager.setReverseLayout(false);
            }
            subSegmentAdapter = new SubSegmentAdapter(listSub, context, new SegmentClick() {
                @Override
                public void Klik(String provinsi, String ruas, int segment, int subsegment, String km, String sta) {
                    setValue(segment, subsegment, sta);
                    valSubSegment = subsegment;
                    setFormAtribut(valAtribut, valProv, valRuas, valSegment, valSubSegment, valPosisi);
                }
            });

            recSub.setLayoutManager(linearLayoutManager);
            recSub.setHasFixedSize(true);
            recSub.scrollToPosition(valSubSegment-1);
            recSub.setAdapter(subSegmentAdapter);
            subSegmentAdapter.notifyDataSetChanged();


        }else{

            session.saveSPInt(Session.FOKUS, segment-1);
            session.saveSPInt(Session.SP_SUBSEGMENT, 0);
            recSub.setVisibility(View.GONE);

        }

    }

    private void setButtonPosisi(String posisi){

        if(posisi.equals("kiri")){
            setColorPosisi(txAtKiri, cdAtrKiri, txAtKanan, cdAtrKanan);
        }else{
            setColorPosisi(txAtKanan, cdAtrKanan, txAtKiri, cdAtrKiri);
        }

        valPosisi = posisi;

        setRecAtribut(posisi);
        setFormAtribut(valAtribut, valProv, valRuas, valSegment, valSubSegment, valPosisi);

    }

    private void setColorPosisi(TextView txCl, CardView cdCl, TextView txUncl, CardView cdUncl){

        if(session.getTipesurvey().equals("Opposite")){
            txUncl.setTextColor(Color.parseColor("#FFFFFF"));
            cdUncl.setCardBackgroundColor(Color.parseColor("#58968B"));

            txCl.setTextColor(Color.parseColor("#000000"));
            cdCl.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }else {
            txCl.setTextColor(Color.parseColor("#FFFFFF"));
            cdCl.setCardBackgroundColor(Color.parseColor("#58968B"));

            txUncl.setTextColor(Color.parseColor("#000000"));
            cdUncl.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }



    }


    public void scrolling(){

        int visible, gone, lScrol, lMax, rScrol, rMax;
        if(valArah.equals("Opposite")){
            visible = View.GONE;
            gone = View.VISIBLE;

            lScrol = horizontalku.findLastVisibleItemPosition() + 3;
            lMax = listsegment.size()-1;

            rScrol = horizontalku.findFirstVisibleItemPosition() - 3;
            rMax = 0;

        }else{
            visible = View.VISIBLE;
            gone = View.GONE;

            lScrol = horizontalku.findFirstVisibleItemPosition() - 3;
            lMax = 0;

            rScrol = horizontalku.findLastVisibleItemPosition() + 3;
            rMax = listsegment.size()-1;

        }

        buttonleft.setOnTouchListener(new RepeaterOnclick(400, 400, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horizontalku.findFirstVisibleItemPosition() > 2) {
                    buttonleft.setVisibility(visible);
                    recyclerView.smoothScrollToPosition(horizontalku.findFirstVisibleItemPosition() - 3);
                } else {
                    buttonleft.setVisibility(gone);
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        }));

        buttonright.setOnTouchListener(new RepeaterOnclick(400, 400, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horizontalku.findFirstVisibleItemPosition() < listsegment.size()-6) {
                    buttonright.setVisibility(visible);
                    recyclerView.smoothScrollToPosition(horizontalku.findLastVisibleItemPosition() + 3);
                } else {
                    buttonright.setVisibility(gone);
                    recyclerView.smoothScrollToPosition(listsegment.size()-1);
                }
            }
        }));


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (horizontalku.findFirstVisibleItemPosition() > 0) {
                    buttonleft.setVisibility(visible);
                }else{
                    buttonleft.setVisibility(gone);
                }

                if(horizontalku.findLastVisibleItemPosition()<listsegment.size()-1){
                    buttonright.setVisibility(visible);
                }else{
                    buttonright.setVisibility(gone);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        permissionHelper.viewLocation();
        super.onResume();


    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}