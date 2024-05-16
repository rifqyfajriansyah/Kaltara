package com.example.roadmanagement.kaltara.Formu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Handler;

import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataDrainase;
import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataOutlet;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.adapter.SubSegmentAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbGorong;
import com.example.roadmanagement.kaltara.databaseHelper.DbInlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbLereng;
import com.example.roadmanagement.kaltara.databaseHelper.DbMinlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbOutlet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.FormTerusan.MainFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SegmentClick;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.Tabel.LihatTabel;
import com.example.roadmanagement.kaltara.adapter.SegmentAdapter;
import com.example.roadmanagement.kaltara.api.Apiku;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.DialogHelper;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.PermissionHelper;
import com.example.roadmanagement.kaltara.helper.RepeaterOnclick;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.lokasi.PilihLokasi;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class FormUtama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_utama);
    }

}


   /* ImageView endSurvey;

    LinearLayout segmentjalan;
    LinearLayout lahanleft;
    LinearLayout lahanright;
    LinearLayout bahuleft;
    LinearLayout bahuright;
    LinearLayout saluranleft;
    LinearLayout saluranright;

    LinearLayoutManager horizontalku;


    RelativeLayout lane1;
    RelativeLayout lane2;
    RelativeLayout lane3;
    RelativeLayout lane4;
    RelativeLayout lane5;
    RelativeLayout lane6;
    RelativeLayout lane7;
    RelativeLayout lane8;
    RelativeLayout lane9;
    RelativeLayout lane10;


    CardView median;

    LinearLayout l1;
    LinearLayout l2;
    LinearLayout l3;
    LinearLayout l4;
    LinearLayout l5;
    LinearLayout l6;
    LinearLayout l7;
    LinearLayout l8;
    LinearLayout l9;
    LinearLayout l10;

    LinearLayout r1;
    LinearLayout r2;
    LinearLayout r3;
    LinearLayout r4;
    LinearLayout r5;
    LinearLayout r6;
    LinearLayout r7;
    LinearLayout r8;
    LinearLayout r9;
    LinearLayout r10;

    Intent i;
    String judul;
    Context context;
    Retrofit retrofit;
    Apiku api;
    Session session;

    //TextView kmawal;
    TextView staawal;
    TextView noSegment;
    TextView jmlSegment;
     //DbDownloadRuas dbDownloadRuas;

    ArrayList<SingleSegment> listsegment = new ArrayList<>();
    ArrayList<SingleSegment> listSub = new ArrayList<>();

    SegmentAdapter segmentAdapter;
    SubSegmentAdapter subSegmentAdapter;

    private RecyclerView recyclerView, recSub;

    FloatingActionButton buttonleft,buttonright;


    DataSegmen dataSegment;
    DbSegmen dbSegmen;
    DbLahan dbLahan;
    DbRuas dbRuas;
    DbSaluran dbSaluran;
    DbBahu dbBahu;
    DbMedian dbMedian;
    DbLane dbLane;

    DbInlet dbInlet;
    DbMinlet dbMinlet;
    DbOutlet dbOutlet;
    DbGorong dbGorong;
    DbLereng dbLereng;

    TextView tipelahanl;
    TextView usedlahanl;
    TextView tinggilahanl;
 //   TextView resikolahanl;
    TextView tipelahanr;
    TextView usedlahanr;
    TextView tinggilahanr;
  //  TextView resikolahanr;

    TextView tipesaluranl;
    TextView lebarsaluranl;
    TextView dalamsaluranl;
 //   TextView kondisaluranl;
    TextView tipesaluranr;
    TextView lebarsaluranr;
    TextView dalamsaluranr;
 //   TextView kondisaluranr;

    TextView tipebahul;
    TextView lebarbahul;
 //   TextView kondisibahul;
    TextView tipebahur;
    TextView lebarbahur;

    TextView surfacel1;
    TextView lebarl1;
    TextView surfacel2;
    TextView lebarl2;
    TextView surfacel3;
    TextView lebarl3;
    TextView surfacel4;
    TextView lebarl4;
    TextView surfacel5;
    TextView lebarl5;
    TextView surfacel6;
    TextView lebarl6;
    TextView surfacel7;
    TextView lebarl7;
    TextView surfacel8;
    TextView lebarl8;
    TextView surfacel9;
    TextView lebarl9;
    TextView surfacel10;
    TextView lebarl10;

    TextView surfacer1;
    TextView lebarr1;
    TextView surfacer2;
    TextView lebarr2;
    TextView surfacer3;
    TextView lebarr3;
    TextView surfacer4;
    TextView lebarr4;
    TextView surfacer5;
    TextView lebarr5;
    TextView surfacer6;
    TextView lebarr6;
    TextView surfacer7;
    TextView lebarr7;
    TextView surfacer8;
    TextView lebarr8;
    TextView surfacer9;
    TextView lebarr9;
    TextView surfacer10;
    TextView lebarr10;

    TextView judulProvinsi, judulRuas, judulJenis;

    List<String> listruas = new ArrayList<>();

    DbSpinner dbSpinner;

    DbTemporari dbTemporari;
    List<DataTemporari> listtempor = new ArrayList();

    FloatingActionButton lihatTabel, lihatPeta;

    HelperTextValue helperTextValue;

    DialogHelper dialogHelper;

    PermissionHelper permissionHelper;


    CardView cdMedian, cdInlet, cdOutlet, cdGorong;
    CardView cdInletKiri, cdInletKanan, cdOutletkiri, cdOutletKanan, cdGorongKiri, cdGorongKanan;

    TextView txMedianLebar, txInKirPenampang, txInKirTinggi, txInKirPanjang, txInKirLebar, txOutKirPenampang, txOutKirKonstruksi, txGorKirPenampang, txGorKirKonstruksi, txOutKirDiameter, txOutKirLebar, txOutKirTinggi, txInKirKonstruksi, txGorKirDiameter, txGorKirLebar, txGorKirTinggi,
            txInKanPenampang, txInKanTinggi, txInKanPanjang, txInKanLebar, txOutKanPenampang, txOutKanKonstruksi, txGorKanPenampang, txGorKanKonstruksi, txOutKanDiameter, txOutKanLebar, txOutKanTinggi, txGorKanDiameter, txGorKanLebar, txGorKanTinggi, txInKanKonstruksi;

    CardView cdInKirPenampang, cdInKirKonstruksi, cdInKirTinggi, cdInKirPanjang, cdInKirLebar, cdOutKirPenampang, cdOutKirKonstruksi, cdGorKirPenampang, cdGorKirKonstruksi, cdOutKirDiameter, cdOutKirLebar, cdOutKirTinggi, cdGorKirDiameter, cdGorKirLebar, cdGorKirTinggi, cdSalKirPenampang, cdSalKanPenampang,
            cdInKanPenampang, cdInKanKonstruksi, cdInKanTinggi, cdInKanPanjang, cdInKanLebar, cdOutKanPenampang, cdOutKanKonstruksi, cdGorKanPenampang, cdGorKanKonstruksi, cdOutKanDiameter, cdOutKanLebar, cdOutKanTinggi, cdGorKanDiameter, cdGorKanLebar, cdGorKanTinggi;
    int maxSegment, maxSub;

    TextView txLahanL, txSaluranL, txBahuL, txInletL, txOutletL, txGorongL, txL1, txL2, txL3, txL4, txL5, txL6, txL7, txL8, txL9, txL10,
            txLahanR, txSaluranR, txBahuR, txInletR, txOutletR, txGorongR, txR1, txR2, txR3, txR4, txR5, txR6, txR7, txR8, txR9, txR10;

    TextView txSalKirPenampang, txSalKirAir, txSalKirSedimen, txSalKirFungsi, txSalKanPenampang, txSalKanAir, txSalKanSedimen, txSalKanFungsi;
    TextView txBahKirDerajat, txBahKirPersen, txBahKirSesuai, txBahKanDerajat, txBahKanPersen, txBahKanSesuai;

    CardView cdLahan, cdSalKond, cdBahKond, cdInKond, cdOutKond, cdGorKond, cdLane1Kond, cdLane2Kond, cdLane3Kond, cdLane4Kond,
            cdLane5Kond, cdLane6Kond, cdLane7Kond, cdLane8Kond, cdLane9Kond, cdLane10Kond;

    CardView cd1Kond, cd2Kond, cd3Kond, cd4Kond, cd5Kond, cd6Kond, cd7Kond, cd8Kond, cd9Kond, cd10Kond;

    CardView cdInKondKir, cdInKondKan, cdOutKondKir, cdOutKondKan, cdGorKondKir, cdGorKondKan;
    LinearLayout linL1Kond, linL2Kond, linL3Kond, linL4Kond, linL5Kond, linL6Kond, linL7Kond, linL8Kond, linL9Kond, linL10Kond,
    linR1Kond, linR2Kond, linR3Kond, linR4Kond, linR5Kond, linR6Kond, linR7Kond, linR8Kond, linR9Kond, linR10Kond;

    String surveyArah;
    int surveyUser;

    ImageView imgSalKir, imgBahKir, imgInKir, imgOutKir, imgGorKir, imgL1, imgL2, imgL3, imgL4, imgL5, imgL6, imgL7, imgL8, imgL9, imgL10,
            imgSalKan, imgBahKan, imgInKan, imgOutKan, imgGorKan, imgR1, imgR2, imgR3, imgR4, imgR5, imgR6, imgR7, imgR8, imgR9, imgR10;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_utama);

        context = FormUtama.this;

        segmentjalan = findViewById(R.id.segmenjalan);
        lahanleft = findViewById(R.id.lahanleft);
        lahanright = findViewById(R.id.lahanright);
        bahuleft = findViewById(R.id.bahuleft);
        bahuright =findViewById(R.id.bahuright);
        median = findViewById(R.id.formCdMedian);
        saluranleft = findViewById(R.id.saluranleft);
        saluranright =findViewById(R.id.saluranright);

        l1 = findViewById(R.id.lane1l);
        l2 = findViewById(R.id.lane2l);
        l3 = findViewById(R.id.lane3l);
        l4 = findViewById(R.id.lane4l);
        l5 = findViewById(R.id.lane5l);
        l6 = findViewById(R.id.lane6l);
        l7 = findViewById(R.id.lane7l);
        l8 = findViewById(R.id.lane8l);
        l9 = findViewById(R.id.lane9l);
        l10 = findViewById(R.id.lane10l);

        r1 =findViewById(R.id.lane1r);
        r2 = findViewById(R.id.lane2r);
        r3 = findViewById(R.id.lane3r);
        r4 = findViewById(R.id.lane4r);
        r5 =findViewById(R.id.lane5r);
        r6 = findViewById(R.id.lane6r);
        r7 = findViewById(R.id.lane7r);
        r8 = findViewById(R.id.lane8r);
        r9 =findViewById(R.id.lane9r);
        r10 = findViewById(R.id.lane10r);

        lane1 = findViewById(R.id.lane1);
        lane2 = findViewById(R.id.lane2);
        lane3 = findViewById(R.id.lane3);
        lane4 = findViewById(R.id.lane4);
        lane5 = findViewById(R.id.lane5);
        lane6 = findViewById(R.id.lane6);
        lane7 = findViewById(R.id.lane7);
        lane8 = findViewById(R.id.lane8);
        lane9 = findViewById(R.id.lane9);
        lane10 = findViewById(R.id.lane10);

        endSurvey = findViewById(R.id.surveyEnd);

        helperTextValue = new HelperTextValue();

        recyclerView = findViewById(R.id.recycleformutama);
        recSub = findViewById(R.id.recycleSubsegment);

        horizontalku = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        dialogHelper = new DialogHelper(context);

        buttonleft = findViewById(R.id.buttonleft);
        buttonright = findViewById(R.id.buttonright);

        cdMedian = findViewById(R.id.formCdMedian);
        cdInlet = findViewById(R.id.formCdInlet);
        cdOutlet = findViewById(R.id.formCdOutlet);
        cdGorong = findViewById(R.id.formCdGorong);

        cdInletKiri = findViewById(R.id.formCdInletKiri);
        cdInletKanan = findViewById(R.id.formCdInletKanan);

        cdOutletkiri = findViewById(R.id.formCdOutletKiri);
        cdOutletKanan = findViewById(R.id.formCdOutletKanan);

        cdGorongKiri = findViewById(R.id.formCdGorongKiri);
        cdGorongKanan = findViewById(R.id.formCdGorongKanan);


        txMedianLebar = findViewById(R.id.formTxMedianLebar);
        txSalKirPenampang = findViewById(R.id.formTxSalKirPenampangVal);
        txSalKanPenampang  = findViewById(R.id.formTxSalKanPenampangVal);

        txInKirPenampang = findViewById(R.id.formTxInKirPenampang);
        txInKirTinggi = findViewById(R.id.formTxInKirTinggi);
        txInKirPanjang = findViewById(R.id.formTxInKirPanjang);
        txInKirLebar = findViewById(R.id.formTxInKirLebar);
        txInKirKonstruksi = findViewById(R.id.formTxInKirKonstruksi);

        txOutKirPenampang = findViewById(R.id.formTxOutKirPenampang);
        txOutKirKonstruksi = findViewById(R.id.formTxOutKirKonstruksi);
        txOutKirDiameter = findViewById(R.id.formTxOutKirDiameter);
        txOutKirLebar = findViewById(R.id.formTxOutKirLebar);
        txOutKirTinggi = findViewById(R.id.formTxOutKirTinggi);

        txGorKirPenampang = findViewById(R.id.formTxGorKirPenampang);
        txGorKirKonstruksi = findViewById(R.id.formTxGorKirKonstruksi);
        txGorKirDiameter = findViewById(R.id.formTxGorKirDiameter);
        txGorKirLebar = findViewById(R.id.formTxGorKirLebar);
        txGorKirTinggi = findViewById(R.id.formTxGorKirTinggi);

        txInKanPenampang = findViewById(R.id.formTxInKanPenampang);
        txInKanTinggi = findViewById(R.id.formTxInKanTinggi);
        txInKanPanjang = findViewById(R.id.formTxInKanPanjang);
        txInKanLebar = findViewById(R.id.formTxInKanLebar);
        txInKanKonstruksi = findViewById(R.id.formTxInKanKonstruksi);

        txOutKanPenampang = findViewById(R.id.formTxOutKanPenampang);
        txOutKanKonstruksi = findViewById(R.id.formTxOutKanKonstruksi);
        txOutKanDiameter = findViewById(R.id.formTxOutKanDiameter);
        txOutKanLebar = findViewById(R.id.formTxOutKanLebar);
        txOutKanTinggi = findViewById(R.id.formTxOutKanTinggi);

        txGorKanPenampang = findViewById(R.id.formTxGorKanPenampang);
        txGorKanKonstruksi = findViewById(R.id.formTxGorKanKonstruksi);
        txGorKanDiameter = findViewById(R.id.formTxGorKanDiameter);
        txGorKanLebar = findViewById(R.id.formTxGorKanLebar);
        txGorKanTinggi = findViewById(R.id.formTxGorKanTinggi);

        cdInKirPenampang = findViewById(R.id.formCdInKirPenampang);
        cdInKirTinggi = findViewById(R.id.formCdInKirTinggi);
        cdInKirPanjang = findViewById(R.id.formCdInKirPanjang);
        cdInKirLebar = findViewById(R.id.formCdInKirLebar);
        cdInKirKonstruksi = findViewById(R.id.formCdInKirKonstruksi);

        cdOutKirPenampang = findViewById(R.id.formCdOutKirPenampang);
        cdOutKirKonstruksi = findViewById(R.id.formCdOutKirKonstruksi);
        cdOutKirDiameter = findViewById(R.id.formCdOutKirDiameter);
        cdOutKirLebar = findViewById(R.id.formCdOutKirLebar);
        cdOutKirTinggi = findViewById(R.id.formCdOutKirTinggi);

        cdGorKirPenampang = findViewById(R.id.formCdGorKirPenampang);
        cdGorKirKonstruksi = findViewById(R.id.formCdGorKirKonstruksi);
        cdGorKirDiameter = findViewById(R.id.formCdGorKirDiameter);
        cdGorKirLebar = findViewById(R.id.formCdGorKirLebar);
        cdGorKirTinggi = findViewById(R.id.formCdGorKirTinggi);

        cdInKanPenampang = findViewById(R.id.formCdInKanPenampang);
        cdInKanTinggi = findViewById(R.id.formCdInKanTinggi);
        cdInKanPanjang = findViewById(R.id.formCdInKanPanjang);
        cdInKanLebar = findViewById(R.id.formCdInKanLebar);
        cdInKanKonstruksi = findViewById(R.id.formCdInKanKonstruksi);

        cdOutKanPenampang = findViewById(R.id.formCdOutKanPenampang);
        cdOutKanKonstruksi = findViewById(R.id.formCdOutKanKonstruksi);
        cdOutKanDiameter = findViewById(R.id.formCdOutKanDiameter);
        cdOutKanLebar = findViewById(R.id.formCdOutKanLebar);
        cdOutKanTinggi = findViewById(R.id.formCdOutKanTinggi);

        cdGorKanPenampang = findViewById(R.id.formCdGorKanPenampang);
        cdGorKanKonstruksi = findViewById(R.id.formCdGorKanKonstruksi);
        cdGorKanDiameter = findViewById(R.id.formCdGorKanDiameter);
        cdGorKanLebar = findViewById(R.id.formCdGorKanLebar);
        cdGorKanTinggi = findViewById(R.id.formCdGorKanTinggi);

        cdSalKirPenampang = findViewById(R.id.formCdSalKirPenampang);
        cdSalKanPenampang = findViewById(R.id.formCdSalKanPenampang);

        permissionHelper = new PermissionHelper(context);

        //kolomsegmen
        //kmawal = findViewById(R.id.km);
        staawal = findViewById(R.id.sta);
        jmlSegment = findViewById(R.id.jmlsegment);
        noSegment = findViewById(R.id.nosegmen);

        //kolomlahan
        tipelahanl = findViewById(R.id.tipelahanl);
        tinggilahanl = findViewById(R.id.tinggilahanl);
        usedlahanl = findViewById(R.id.uselahanl);
      //  resikolahanl = findViewById(R.id.resikolahanl);
        tipelahanr = findViewById(R.id.tipelahanr);
        tinggilahanr = findViewById(R.id.tinggilahanr);
        usedlahanr = findViewById(R.id.uselahanr);
    //    resikolahanr = findViewById(R.id.resikolahanr);

        //kolomsaluran
        tipesaluranl = findViewById(R.id.tipesaluranl);
        lebarsaluranl = findViewById(R.id.lebarsaluranl);
        dalamsaluranl = findViewById(R.id.dalamsaluranl);
   //     kondisaluranl = findViewById(R.id.kondisisaluranl);
        tipesaluranr = findViewById(R.id.tipesaluranr);
        lebarsaluranr = findViewById(R.id.lebarsaluranr);
        dalamsaluranr = findViewById(R.id.dalamsaluranr);
        tipebahul = findViewById(R.id.tipebahul);
        lebarbahul = findViewById(R.id.lebarbahul);
        tipebahur = findViewById(R.id.tipebahur);
        lebarbahur = findViewById(R.id.lebarbahur);

        surfacel1 = findViewById(R.id.surfaceL1);
        surfacel2 = findViewById(R.id.surfaceL2);
        surfacel3 = findViewById(R.id.surfaceL3);
        surfacel4 = findViewById(R.id.surfaceL4);
        surfacel5 = findViewById(R.id.surfaceL5);
        surfacel6 = findViewById(R.id.surfaceL6);
        surfacel7 = findViewById(R.id.surfaceL7);
        surfacel8 = findViewById(R.id.surfaceL8);
        surfacel9 = findViewById(R.id.surfaceL9);
        surfacel10 = findViewById(R.id.surfaceL10);

        lebarl1= findViewById(R.id.lebarlaneL1);
        lebarl2= findViewById(R.id.lebarlaneL2);
        lebarl3= findViewById(R.id.lebarlaneL3);
        lebarl4= findViewById(R.id.lebarlaneL4);
        lebarl5= findViewById(R.id.lebarlaneL5);
        lebarl6= findViewById(R.id.lebarlaneL6);
        lebarl7= findViewById(R.id.lebarlaneL7);
        lebarl8= findViewById(R.id.lebarlaneL8);
        lebarl9= findViewById(R.id.lebarlaneL9);
        lebarl10= findViewById(R.id.lebarlaneL10);

        surfacer1 = findViewById(R.id.surfaceR1);
        surfacer2 = findViewById(R.id.surfaceR2);
        surfacer3 = findViewById(R.id.surfaceR3);
        surfacer4 = findViewById(R.id.surfaceR4);
        surfacer5 = findViewById(R.id.surfaceR5);
        surfacer6 = findViewById(R.id.surfaceR6);
        surfacer7 = findViewById(R.id.surfaceR7);
        surfacer8 = findViewById(R.id.surfaceR8);
        surfacer9 = findViewById(R.id.surfaceR9);
        surfacer10 = findViewById(R.id.surfaceR10);

        lebarr1= findViewById(R.id.lebarlaneR1);
        lebarr2= findViewById(R.id.lebarlaneR2);
        lebarr3= findViewById(R.id.lebarlaneR3);
        lebarr4= findViewById(R.id.lebarlaneR4);
        lebarr5= findViewById(R.id.lebarlaneR5);
        lebarr6= findViewById(R.id.lebarlaneR6);
        lebarr7= findViewById(R.id.lebarlaneR7);
        lebarr8= findViewById(R.id.lebarlaneR8);
        lebarr9= findViewById(R.id.lebarlaneR9);
        lebarr10= findViewById(R.id.lebarlaneR10);


        cd1Kond = findViewById(R.id.formCdLaneKondisi1);
        cd2Kond = findViewById(R.id.formCdLaneKondisi2);
        cd3Kond = findViewById(R.id.formCdLaneKondisi3);
        cd4Kond = findViewById(R.id.formCdLaneKondisi4);
        cd5Kond = findViewById(R.id.formCdLaneKondisi5);
        cd6Kond = findViewById(R.id.formCdLaneKondisi6);
        cd7Kond = findViewById(R.id.formCdLaneKondisi7);
        cd8Kond = findViewById(R.id.formCdLaneKondisi8);
        cd9Kond = findViewById(R.id.formCdLaneKondisi9);
        cd10Kond = findViewById(R.id.formCdLaneKondisi10);

        linL1Kond = findViewById(R.id.formLinLaneKondL1);
        linL2Kond = findViewById(R.id.formLinLaneKondL2);
        linL3Kond = findViewById(R.id.formLinLaneKondL3);
        linL4Kond = findViewById(R.id.formLinLaneKondL4);
        linL5Kond = findViewById(R.id.formLinLaneKondL5);
        linL6Kond = findViewById(R.id.formLinLaneKondL6);
        linL7Kond = findViewById(R.id.formLinLaneKondL7);
        linL8Kond = findViewById(R.id.formLinLaneKondL8);
        linL9Kond = findViewById(R.id.formLinLaneKondL9);
        linL10Kond = findViewById(R.id.formLinLaneKondL10);

        linR1Kond = findViewById(R.id.formLinLaneKondR1);
        linR2Kond = findViewById(R.id.formLinLaneKondR2);
        linR3Kond = findViewById(R.id.formLinLaneKondR3);
        linR4Kond = findViewById(R.id.formLinLaneKondR4);
        linR5Kond = findViewById(R.id.formLinLaneKondR5);
        linR6Kond = findViewById(R.id.formLinLaneKondR6);
        linR7Kond = findViewById(R.id.formLinLaneKondR7);
        linR8Kond = findViewById(R.id.formLinLaneKondR8);
        linR9Kond = findViewById(R.id.formLinLaneKondR9);
        linR10Kond = findViewById(R.id.formLinLaneKondR10);

        imgL1 = findViewById(R.id.formImgLaneKondL1);
        imgL2 = findViewById(R.id.formImgLaneKondL2);
        imgL3 = findViewById(R.id.formImgLaneKondL3);
        imgL4 = findViewById(R.id.formImgLaneKondL4);
        imgL5 = findViewById(R.id.formImgLaneKondL5);
        imgL6 = findViewById(R.id.formImgLaneKondL6);
        imgL7 = findViewById(R.id.formImgLaneKondL7);
        imgL8 = findViewById(R.id.formImgLaneKondL8);
        imgL9 = findViewById(R.id.formImgLaneKondL9);
        imgL10 = findViewById(R.id.formImgLaneKondL10);

        imgR1 = findViewById(R.id.formImgLaneKondR1);
        imgR2 = findViewById(R.id.formImgLaneKondR2);
        imgR3 = findViewById(R.id.formImgLaneKondR3);
        imgR4 = findViewById(R.id.formImgLaneKondR4);
        imgR5 = findViewById(R.id.formImgLaneKondR5);
        imgR6 = findViewById(R.id.formImgLaneKondR6);
        imgR7 = findViewById(R.id.formImgLaneKondR7);
        imgR8 = findViewById(R.id.formImgLaneKondR8);
        imgR9 = findViewById(R.id.formImgLaneKondR9);
        imgR10 = findViewById(R.id.formImgLaneKondR10);


        judulProvinsi = findViewById(R.id.judulprovinsi);
        judulRuas = findViewById(R.id.judulruas);
        judulJenis = findViewById(R.id.juduljenis);

        lihatTabel = findViewById(R.id.lihatTabel);
        lihatPeta = findViewById(R.id.lihatPeta);




        lihatTabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FormUtama.this, LihatTabel.class);
                i.putExtra("posisi", session.getPosisiTable());
                startActivity(i);
            }
        });

        lihatPeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, PilihLokasi.class);

                if(permissionHelper.viewLocation()){
                    startActivity(i);
                }

            }
        });




        dbSpinner = new DbSpinner(this);
        dbSegmen = new DbSegmen(this);
        dbLahan = new DbLahan(this);
        dbSaluran = new DbSaluran(this);
        dbBahu = new DbBahu(this);
        dbMedian = new DbMedian(this);
        dbLane = new DbLane(this);
        dbRuas = new DbRuas(this);

        dbInlet = new DbInlet(context);
        dbMinlet = new DbMinlet(context);
        dbOutlet = new DbOutlet(context);
        dbLereng = new DbLereng(context);
        dbGorong = new DbGorong(context);

        session = new Session(this);
        api = new Apiku(this);
        retrofit = api.initRetrofit();

        surveyArah = session.getTipesurvey();
        surveyUser = session.getUserTipe();

        if(session.getTipesurvey().equals("Opposite")){
            horizontalku.setReverseLayout(true);
        }else{
            horizontalku.setReverseLayout(false);
        }



        maxSegment = dbSpinner.getMaksSegment(session.getKodeprov(), session.getNoruas());

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

        //surveyPosisi();

        setTextKotak();

        if(session.getUserTipe() == 99){

            uLet.setVisibility(View.VISIBLE);
            uMedKan.setVisibility(View.VISIBLE);
            uMedKir.setVisibility(View.VISIBLE);
            uGor.setVisibility(View.VISIBLE);
            uLer.setVisibility(View.VISIBLE);

        }else{

            uLet.setVisibility(View.GONE);
            uMedKan.setVisibility(View.GONE);
            uMedKir.setVisibility(View.GONE);
            uGor.setVisibility(View.GONE);
            uLer.setVisibility(View.GONE);

            uLet.setVisibility(View.VISIBLE);
            uMedKan.setVisibility(View.VISIBLE);
            uMedKir.setVisibility(View.VISIBLE);
            uGor.setVisibility(View.VISIBLE);
            uLer.setVisibility(View.VISIBLE);

        }



        listsegment = dbSpinner.listSegment(String.valueOf(session.getKodeprov()), session.getNoruas());

        int ambil = session.getNosegment()-1;

        judulProvinsi.setText(String.valueOf(session.getKodeprov()));
        judulRuas.setText(String.valueOf(session.getNoruas()));
        //judulJenis.setText(help);
        helperTextValue.setTextString(surveyArah, judulJenis);

        SingleSegment singleSegment = dbSpinner.getSpinner(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment());

        String sta = singleSegment.getStaawal()+" - "+singleSegment.getStaakhir();

        setValue(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment(), sta);
        setListSegment();
        setListSubsegment(session.getKodeprov(), session.getNoruas(), session.getNosegment());

        pindahHalaman();

        scrolling();


        endSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogHelper.dialogEnd();

            }
        });

    }

    public void pindahHalaman(){

        String posisiKiri, posisiKanan, lajurKiri, lajurKanan;

        if(surveyArah.equals("Opposite")){

            posisiKiri = "kanan";
            posisiKanan = "kiri";

            lajurKiri = "R";
            lajurKanan = "L";

        }else{

            posisiKiri = "kiri";
            posisiKanan = "kanan";
            lajurKiri = "L";
            lajurKanan = "R";
        }

        cdInletKiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Inlet ditrotoar");
                i.putExtra("posisi", posisiKiri);
                i.putExtra("from", "Survey");
                startActivity(i);

            }
        });

        cdInletKanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Inlet ditrotoar");
                i.putExtra("posisi", posisiKanan);
                i.putExtra("from", "Survey");
                startActivity(i);

            }
        });



        cdOutletkiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Outlet");
                i.putExtra("posisi", posisiKiri);
                i.putExtra("from", "Survey");
                startActivity(i);

            }
        });

        cdOutletKanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Outlet");
                i.putExtra("posisi", posisiKanan);
                i.putExtra("from", "Survey");
                startActivity(i);

            }
        });

        cdGorongKiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Gorong-gorong");
                i.putExtra("posisi", posisiKiri);
                i.putExtra("from", "Survey");
                startActivity(i);

            }
        });

        cdGorongKanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Gorong-gorong");
                i.putExtra("posisi", posisiKanan);
                i.putExtra("from", "Survey");
                startActivity(i);

            }
        });


        segmentjalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Segment");
                i.putExtra("posisi", "");
                i.putExtra("from", "Survey");
                startActivity(i);
            }
        });

        lahanleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Lahan");
                i.putExtra("posisi", posisiKiri);
                i.putExtra("from", "Survey");

                startActivity(i);
            }
        });

        lahanright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Lahan");
                i.putExtra("posisi", posisiKanan);
                i.putExtra("from", "Survey");

                startActivity(i);
            }
        });

        bahuleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Bahu");
                i.putExtra("posisi", posisiKiri);
                i.putExtra("from", "Survey");
                startActivity(i);
            }
        });

        bahuright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Bahu");
                i.putExtra("posisi", posisiKanan);
                i.putExtra("from", "Survey");
                startActivity(i);
            }
        });

        median.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Median");
                i.putExtra("posisi", "");
                i.putExtra("from", "Survey");

                startActivity(i);
            }
        });

        saluranleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Saluran");
                i.putExtra("posisi", posisiKiri);
                i.putExtra("from", "Survey");

                startActivity(i);


            }
        });

        saluranright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = new Intent(context, MainFormTerusan.class);
                i.putExtra("tipe", "Saluran");
                i.putExtra("posisi", posisiKanan);
                i.putExtra("from", "Survey");

                startActivity(i);
            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKiri+"1");
                startActivity(i);
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKiri+"2");
                startActivity(i);
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKiri+"3");
                startActivity(i);
            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKiri+"4");
                startActivity(i);
            }
        });

        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKiri+"5");
                startActivity(i);
            }
        });

        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKiri+"6");
                startActivity(i);
            }
        });

        l7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKiri+"7");
                startActivity(i);
            }
        });

        l8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKiri+"8");
                startActivity(i);
            }
        });

        l9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKiri+"9");
                startActivity(i);
            }
        });

        l10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKiri+"10");
                startActivity(i);
            }
        });



        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = clickBoxLane(lajurKanan+"1");
                startActivity(i);
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = clickBoxLane(lajurKanan+"2");
                startActivity(i);
            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKanan+"3");
                startActivity(i);
            }
        });

        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKanan+"4");
                startActivity(i);
            }
        });

        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKanan+"5");
                startActivity(i);
            }
        });

        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKanan+"6");
                startActivity(i);
            }
        });

        r7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKanan+"7");
                startActivity(i);
            }
        });

        r8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKanan+"8");
                startActivity(i);
            }
        });

        r9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKanan+"9");
                startActivity(i);
            }
        });

        r10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = clickBoxLane(lajurKanan+"10");
                startActivity(i);
            }
        });


    }


    public void setValue(String noprov, String noruas, int segment, int subsegment, String sta){

        noSegment.setText("SEGMENT - "+segment+"."+subsegment);
        staawal.setText(sta);
        setSegment(noprov, noruas, segment, subsegment);
        setIsi(noprov, noruas, segment, subsegment);


    }

    public void setSegment(String noprov, String noruas, int segment, int subsegment){
        dataSegment = dbSegmen.getSegmen(noprov, noruas, segment, subsegment);
        if(null!=dataSegment){
            jmlSegment.setText(String.valueOf(dataSegment.getJumlahsegment()));
            setLane(dataSegment, noprov, noruas, segment, subsegment);
        }else{
            jmlSegment.setText("0");
            r1.setVisibility(View.GONE);
            r2.setVisibility(View.GONE);
            r3.setVisibility(View.GONE);
            r4.setVisibility(View.GONE);
            r5.setVisibility(View.GONE);
            r6.setVisibility(View.GONE);
            r7.setVisibility(View.GONE);
            r8.setVisibility(View.GONE);
            r9.setVisibility(View.GONE);
            r10.setVisibility(View.GONE);

            l1.setVisibility(View.GONE);
            l2.setVisibility(View.GONE);
            l3.setVisibility(View.GONE);
            l4.setVisibility(View.GONE);
            l5.setVisibility(View.GONE);
            l6.setVisibility(View.GONE);
            l7.setVisibility(View.GONE);
            l8.setVisibility(View.GONE);
            l9.setVisibility(View.GONE);
            l10.setVisibility(View.GONE);

            lane1.setVisibility(View.GONE);
            lane2.setVisibility(View.GONE);
            lane3.setVisibility(View.GONE);
            lane4.setVisibility(View.GONE);
            lane5.setVisibility(View.GONE);
            lane6.setVisibility(View.GONE);
            lane7.setVisibility(View.GONE);
            lane8.setVisibility(View.GONE);
            lane9.setVisibility(View.GONE);
            lane10.setVisibility(View.GONE);
        }
    }

    public void setLane(DataSegmen dataSegmen, String noprov, String noruas, int segment, int subsegment){

        String posisiKiri ="kiri";
        String posisiKanan ="kanan";
        String lajurKiri ="L";
        String lajurKanan = "R";

        int statL1  = dataSegmen.getSegmentl1();
        int statL2 = dataSegmen.getSegmentl2();
        int statL3 = dataSegmen.getSegmentl3();
        int statL4 = dataSegmen.getSegmentl4();
        int statL5 = dataSegmen.getSegmentl5();
        int statL6 = dataSegmen.getSegmentl6();
        int statL7 = dataSegmen.getSegmentl7();
        int statL8 = dataSegmen.getSegmentl8();
        int statL9 = dataSegmen.getSegmentl9();
        int statL10 = dataSegmen.getSegmentl10();

        int statR1  = dataSegmen.getSegmentr1();
        int statR2 = dataSegmen.getSegmentr2();
        int statR3 = dataSegmen.getSegmentr3();
        int statR4 = dataSegmen.getSegmentr4();
        int statR5 = dataSegmen.getSegmentr5();
        int statR6 = dataSegmen.getSegmentr6();
        int statR7 = dataSegmen.getSegmentr7();
        int statR8 = dataSegmen.getSegmentr8();
        int statR9 = dataSegmen.getSegmentr9();
        int statR10 = dataSegmen.getSegmentr10();

        if(surveyArah.equals("Opposite")){
            posisiKiri = "kanan";
            posisiKanan = "kiri";
            lajurKiri = "R";
            lajurKanan = "L";

            statL1  = dataSegmen.getSegmentr1();
            statL2 = dataSegmen.getSegmentr2();
            statL3 = dataSegmen.getSegmentr3();
            statL4 = dataSegmen.getSegmentr4();
            statL5 = dataSegmen.getSegmentr5();
            statL6 = dataSegmen.getSegmentr6();
            statL7 = dataSegmen.getSegmentr7();
            statL8 = dataSegmen.getSegmentr8();
            statL9 = dataSegmen.getSegmentr9();
            statL10 = dataSegmen.getSegmentr10();

            statR1  = dataSegmen.getSegmentl1();
            statR2 = dataSegmen.getSegmentl2();
            statR3 = dataSegmen.getSegmentl3();
            statR4 = dataSegmen.getSegmentl4();
            statR5 = dataSegmen.getSegmentl5();
            statR6 = dataSegmen.getSegmentl6();
            statR7 = dataSegmen.getSegmentl7();
            statR8 = dataSegmen.getSegmentl8();
            statR9 = dataSegmen.getSegmentl9();
            statR10 = dataSegmen.getSegmentl10();
        }


        DataLane dataLanel1 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKiri, lajurKiri+"1");
        DataLane dataLanel2 = dbLane.getLane(noprov, noruas, segment, subsegment,  posisiKiri, lajurKiri+"2");
        DataLane dataLanel3 = dbLane.getLane(noprov, noruas, segment, subsegment,  posisiKiri, lajurKiri+"3");
        DataLane dataLanel4 = dbLane.getLane(noprov, noruas, segment, subsegment,  posisiKiri, lajurKiri+"4");
        DataLane dataLanel5 = dbLane.getLane(noprov, noruas, segment, subsegment,  posisiKiri, lajurKiri+"5");
        DataLane dataLanel6 = dbLane.getLane(noprov, noruas, segment, subsegment,  posisiKiri, lajurKiri+"6");
        DataLane dataLanel7 = dbLane.getLane(noprov, noruas, segment, subsegment,  posisiKiri, lajurKiri+"7");
        DataLane dataLanel8 = dbLane.getLane(noprov, noruas, segment, subsegment,  posisiKiri, lajurKiri+"8");
        DataLane dataLanel9 = dbLane.getLane(noprov, noruas, segment, subsegment,  posisiKiri, lajurKiri+"9");
        DataLane dataLanel10 = dbLane.getLane(noprov, noruas, segment, subsegment,  posisiKiri, lajurKiri+"10");

        DataLane dataLaner1 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKanan,  lajurKanan+"1");
        DataLane dataLaner2 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKanan, lajurKanan+"2");
        DataLane dataLaner3 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKanan, lajurKanan+"3");
        DataLane dataLaner4 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKanan, lajurKanan+"4");
        DataLane dataLaner5 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKanan, lajurKanan+"5");
        DataLane dataLaner6 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKanan, lajurKanan+"6");
        DataLane dataLaner7 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKanan, lajurKanan+"7");
        DataLane dataLaner8 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKanan, lajurKanan+"8");
        DataLane dataLaner9 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKanan, lajurKanan+"9");
        DataLane dataLaner10 = dbLane.getLane(noprov, noruas, segment, subsegment, posisiKanan, lajurKanan+"10");



        r1.setVisibility(View.GONE);
        r2.setVisibility(View.GONE);
        r3.setVisibility(View.GONE);
        r4.setVisibility(View.GONE);
        r5.setVisibility(View.GONE);
        r6.setVisibility(View.GONE);
        r7.setVisibility(View.GONE);
        r8.setVisibility(View.GONE);
        r9.setVisibility(View.GONE);
        r10.setVisibility(View.GONE);

        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
        l3.setVisibility(View.GONE);
        l4.setVisibility(View.GONE);
        l5.setVisibility(View.GONE);
        l6.setVisibility(View.GONE);
        l7.setVisibility(View.GONE);
        l8.setVisibility(View.GONE);
        l9.setVisibility(View.GONE);
        l10.setVisibility(View.GONE);

        lane1.setVisibility(View.GONE);
        lane2.setVisibility(View.GONE);
        lane3.setVisibility(View.GONE);
        lane4.setVisibility(View.GONE);
        lane5.setVisibility(View.GONE);
        lane6.setVisibility(View.GONE);
        lane7.setVisibility(View.GONE);
        lane8.setVisibility(View.GONE);
        lane9.setVisibility(View.GONE);
        lane10.setVisibility(View.GONE);

        cd1Kond.setVisibility(View.GONE);
        cd2Kond.setVisibility(View.GONE);
        cd3Kond.setVisibility(View.GONE);
        cd4Kond.setVisibility(View.GONE);
        cd5Kond.setVisibility(View.GONE);
        cd6Kond.setVisibility(View.GONE);
        cd7Kond.setVisibility(View.GONE);
        cd8Kond.setVisibility(View.GONE);
        cd9Kond.setVisibility(View.GONE);
        cd10Kond.setVisibility(View.GONE);




        setBoxLane(dataLanel1, statL1, lane1, l1, surfacel1, lebarl1, cd1Kond, linL1Kond, imgL1);
        setBoxLane(dataLanel2, statL2, lane2, l2, surfacel2, lebarl2, cd2Kond, linL2Kond, imgL2);
        setBoxLane(dataLanel3, statL3, lane3, l3, surfacel3, lebarl3, cd3Kond, linL3Kond, imgL3);
        setBoxLane(dataLanel4, statL4, lane4, l4, surfacel4, lebarl4, cd4Kond, linL4Kond, imgL4);
        setBoxLane(dataLanel5, statL5, lane5, l5, surfacel5, lebarl5, cd5Kond, linL5Kond, imgL5);
        setBoxLane(dataLanel6, statL6, lane6, l6, surfacel6, lebarl6, cd6Kond, linL6Kond, imgL6);
        setBoxLane(dataLanel7, statL7, lane7, l7, surfacel7, lebarl7, cd7Kond, linL7Kond, imgL7);
        setBoxLane(dataLanel8, statL8, lane8, l8, surfacel8, lebarl8, cd8Kond, linL8Kond, imgL8);
        setBoxLane(dataLanel9, statL9, lane9, l9, surfacel9, lebarl9, cd9Kond, linL9Kond, imgL9);
        setBoxLane(dataLanel10, statL10, lane10, l10, surfacel10, lebarl10, cd10Kond, linL10Kond, imgL10);

        setBoxLane(dataLaner1, statR1, lane1, r1, surfacer1, lebarr1, cd1Kond, linR1Kond, imgR1);
        setBoxLane(dataLaner2, statR2, lane2, r2, surfacer2, lebarr2, cd2Kond, linR2Kond, imgR2);
        setBoxLane(dataLaner3, statR3, lane3, r3, surfacer3, lebarr3, cd3Kond, linR3Kond, imgR3);
        setBoxLane(dataLaner4, statR4, lane4, r4, surfacer4, lebarr4, cd4Kond, linR4Kond, imgR4);
        setBoxLane(dataLaner5, statR5, lane5, r5, surfacer5, lebarr5, cd5Kond, linR5Kond, imgR5);
        setBoxLane(dataLaner6, statR6, lane6, r6, surfacer6, lebarr6, cd6Kond, linR6Kond, imgR6);
        setBoxLane(dataLaner7, statR7, lane7, r7, surfacer7, lebarr7, cd7Kond, linR7Kond, imgR7);
        setBoxLane(dataLaner8, statR8, lane8, r8, surfacer8, lebarr8, cd8Kond, linR8Kond, imgR8);
        setBoxLane(dataLaner9, statR9, lane9, r9, surfacer9, lebarr9, cd9Kond, linR9Kond, imgR9);
        setBoxLane(dataLaner10, statR10, lane10, r10, surfacer10, lebarr10, cd10Kond, linR10Kond, imgR10);


    }

    public void setIsi(String noprov, String noruas, int segment, int subsegment){

        String posisiKiri, posisiKanan;

        if(surveyArah.equals("Opposite")){
            posisiKiri = "kanan";
            posisiKanan = "kiri";
        }else{
            posisiKiri="kiri";
            posisiKanan="kanan";
        }


        DataLahan dataLahankiri = dbLahan.getLahan(noprov, noruas, segment, subsegment, posisiKiri);
        DataLahan dataLahankanan = dbLahan.getLahan(noprov, noruas, segment, subsegment, posisiKanan);

        if(dataLahankiri!=null) {
            helperTextValue.setTextString(dataLahankiri.getTipeLahan(), tipelahanl);
            helperTextValue.setTextString(helperTextValue.tataGuna(dataLahankiri.getTatagunaLahan()), usedlahanl);
            helperTextValue.setTextString(dataLahankiri.getTinggiLahan(), tinggilahanl);
        }

        if(dataLahankanan!=null) {
            helperTextValue.setTextString(dataLahankanan.getTipeLahan(), tipelahanr);
            helperTextValue.setTextString(helperTextValue.tataGuna(dataLahankanan.getTatagunaLahan()), usedlahanr);
            helperTextValue.setTextString(dataLahankanan.getTinggiLahan(), tinggilahanr);
        }


        DataSaluran dataSalurankiri =  dbSaluran.getSaluran(noprov, noruas, segment, subsegment, posisiKiri);
        DataSaluran dataSalurankanan =  dbSaluran.getSaluran(noprov, noruas, segment, subsegment, posisiKanan);

        if(dataSalurankiri != null){

            helperTextValue.setTextString(helperTextValue.tipeSaluran(dataSalurankiri.getTipeSaluran()), tipesaluranl);
            helperTextValue.setTextString(dataSalurankiri.getLebarSaluran(), lebarsaluranl);
            helperTextValue.setTextString(dataSalurankiri.getDalamSaluran(), dalamsaluranl);
            helperTextValue.setTextString(dataSalurankiri.getJenisPenampang(), txSalKirPenampang);
        }

        if(dataSalurankanan != null){

            helperTextValue.setTextString(helperTextValue.tipeSaluran(dataSalurankanan.getTipeSaluran()), tipesaluranr);
            helperTextValue.setTextString(dataSalurankanan.getLebarSaluran(), lebarsaluranr);
            helperTextValue.setTextString(dataSalurankanan.getDalamSaluran(), dalamsaluranr);
            helperTextValue.setTextString(dataSalurankanan.getJenisPenampang(), txSalKanPenampang);
        }


        DataBahu dataBahukiri = dbBahu.getBahu(noprov, noruas, segment, subsegment, posisiKiri);
        DataBahu dataBahukanan = dbBahu.getBahu(noprov, noruas, segment, subsegment, posisiKanan);

        if(dataBahukiri != null){
            helperTextValue.setTextString(helperTextValue.tipeBahu(dataBahukiri.getTipeBahu()), tipebahul);
            helperTextValue.setTextString(dataBahukiri.getLebarBahu(), lebarbahul);
        }

        if(dataBahukanan != null){
            helperTextValue.setTextString(helperTextValue.tipeBahu(dataBahukanan.getTipeBahu()), tipebahur);
            helperTextValue.setTextString(dataBahukanan.getLebarBahu(), lebarbahur);
        }

        DataMedian dataMedian = dbMedian.getMedian(noprov, noruas, segment, subsegment);
        if(dataMedian != null){

            helperTextValue.setTextString(dataMedian.getLebarMedian(), txMedianLebar);

        }


        DataInletTrotoar dataInletKiri = dbInlet.getInlet(noprov, noruas, segment, subsegment, posisiKiri);
        DataInletTrotoar dataInletKanan = dbInlet.getInlet(noprov, noruas, segment, subsegment, posisiKanan);

        setVisibleValue(cdInKirTinggi, txInKirTinggi, View.GONE);
        setVisibleValue(cdInKirLebar, txInKirLebar, View.GONE);
        setVisibleValue(cdInKirPanjang, txInKirPanjang, View.GONE);

        helperTextValue.setTextString("", txInKirPenampang);
        helperTextValue.setTextString("", txInKirKonstruksi);


        if(dataInletKiri!=null){

             if(dataInletKiri.getJenisPenampang()!=null){

                helperTextValue.setTextString(dataInletKiri.getJenisPenampang(), txInKirPenampang);
                helperTextValue.setTextString(dataInletKiri.getJenisKonstruksi(), txInKirKonstruksi);
                helperTextValue.setTextString(dataInletKiri.getPanjang(), txInKirPanjang);
                helperTextValue.setTextString(dataInletKiri.getLebar(), txInKirLebar);
                helperTextValue.setTextString(dataInletKiri.getTinggi(), txInKirTinggi);

                switch (dataInletKiri.getJenisPenampang()){
                    case "Curb" : setVisibleValue(cdInKirTinggi, txInKirTinggi, View.VISIBLE);
                                  setVisibleValue(cdInKirPanjang, txInKirPanjang, View.VISIBLE);

                        break;

                    case "Gutter" : setVisibleValue(cdInKirLebar, txInKirLebar, View.VISIBLE);
                                    setVisibleValue(cdInKirPanjang, txInKirPanjang, View.VISIBLE);

                        break;

                    case "Combination" : setVisibleValue(cdInKirTinggi, txInKirTinggi, View.VISIBLE);
                        setVisibleValue(cdInKirPanjang, txInKirPanjang, View.VISIBLE);
                        setVisibleValue(cdInKirLebar, txInKirLebar, View.VISIBLE);

                        break;

                    default:
                }

            }
        }

        setVisibleValue(cdInKanTinggi, txInKanTinggi, View.GONE);
        setVisibleValue(cdInKanLebar, txInKanLebar, View.GONE);
        setVisibleValue(cdInKanPanjang, txInKanPanjang, View.GONE);

        helperTextValue.setTextString("", txInKanPenampang);
        helperTextValue.setTextString("", txInKanKonstruksi);

        if(dataInletKanan!=null){
            if(dataInletKanan.getJenisPenampang()!=null){

                helperTextValue.setTextString(dataInletKanan.getJenisPenampang(), txInKanPenampang);
                helperTextValue.setTextString(dataInletKanan.getJenisKonstruksi(), txInKanKonstruksi);
                helperTextValue.setTextString(dataInletKanan.getTinggi(), txInKanTinggi);
                helperTextValue.setTextString(dataInletKanan.getPanjang(), txInKanPanjang);
                helperTextValue.setTextString(dataInletKanan.getLebar(), txInKanLebar);

                switch (dataInletKanan.getJenisPenampang()){
                    case "Curb" : setVisibleValue(cdInKanTinggi, txInKanTinggi, View.VISIBLE);
                        setVisibleValue(cdInKanPanjang, txInKanPanjang, View.VISIBLE);

                        break;

                    case "Gutter" : setVisibleValue(cdInKanLebar, txInKanLebar, View.VISIBLE);
                        setVisibleValue(cdInKanPanjang, txInKanPanjang, View.VISIBLE);

                        break;

                    case "Combination" : setVisibleValue(cdInKanTinggi, txInKanTinggi, View.VISIBLE);
                        setVisibleValue(cdInKanPanjang, txInKanPanjang, View.VISIBLE);
                        setVisibleValue(cdInKanLebar, txInKanLebar, View.VISIBLE);

                        break;

                    default:
                }

            }
        }

        DataOutlet dataOutletKiri = dbOutlet.getOutlet(noprov, noruas, segment, subsegment, posisiKiri);
        DataOutlet dataOutletKanan = dbOutlet.getOutlet(noprov, noruas, segment, subsegment, posisiKanan);

        setVisibleValue(cdOutKirDiameter, txOutKirDiameter, View.GONE);
        setVisibleValue(cdOutKirLebar, txOutKirLebar, View.GONE);
        setVisibleValue(cdOutKirTinggi, txOutKirTinggi, View.GONE);

        helperTextValue.setTextString("", txOutKirPenampang);
        helperTextValue.setTextString("", txOutKirKonstruksi);

        if(dataOutletKiri!=null){
            if(dataOutletKiri.getJenisPenampang()!=null){

                helperTextValue.setTextString(dataOutletKiri.getJenisPenampang(), txOutKirPenampang);
                helperTextValue.setTextString(dataOutletKiri.getJenisKonstruksi(), txOutKirKonstruksi);
                helperTextValue.setTextString(dataOutletKiri.getDiameter(), txOutKirDiameter);
                helperTextValue.setTextString(dataOutletKiri.getTinggi(), txOutKirTinggi);
                helperTextValue.setTextString(dataOutletKiri.getLebar(), txOutKirLebar);



                if(dataOutletKiri.getJenisPenampang().equals("Lingkaran")){
                    setVisibleValue(cdOutKirDiameter, txOutKirDiameter, View.VISIBLE);
                }else if(dataOutletKiri.getJenisPenampang().equals("Segiempat")){
                    setVisibleValue(cdOutKirTinggi, txOutKirTinggi, View.VISIBLE);
                    setVisibleValue(cdOutKirLebar, txOutKirLebar, View.VISIBLE);
                }
            }


        }

        setVisibleValue(cdOutKanDiameter, txOutKanDiameter, View.GONE);
        setVisibleValue(cdOutKanLebar, txOutKanLebar, View.GONE);
        setVisibleValue(cdOutKanTinggi, txOutKanTinggi, View.GONE);

        helperTextValue.setTextString("", txOutKanPenampang);
        helperTextValue.setTextString("", txOutKanKonstruksi);

        if(dataOutletKanan!=null){
           if(dataOutletKanan.getJenisPenampang()!=null){

                helperTextValue.setTextString(dataOutletKanan.getJenisPenampang(), txOutKanPenampang);
                helperTextValue.setTextString(dataOutletKanan.getJenisKonstruksi(), txOutKanKonstruksi);
                helperTextValue.setTextString(dataOutletKanan.getDiameter(), txOutKanDiameter);
                helperTextValue.setTextString(dataOutletKanan.getTinggi(), txOutKanTinggi);
                helperTextValue.setTextString(dataOutletKanan.getLebar(), txOutKanLebar);



                if(dataOutletKanan.getJenisPenampang().equals("Lingkaran")){
                    setVisibleValue(cdOutKanDiameter, txOutKanDiameter, View.VISIBLE);
                }else if(dataOutletKanan.getJenisPenampang().equals("Segiempat")){
                    setVisibleValue(cdOutKanTinggi, txOutKanTinggi, View.VISIBLE);
                    setVisibleValue(cdOutKanLebar, txOutKanLebar, View.VISIBLE);
                }
            }


        }

        DataCrossDrain dataGorongKiri = dbGorong.getGorong(noprov, noruas, segment, subsegment, posisiKiri);
        DataCrossDrain dataGorongKanan = dbGorong.getGorong(noprov, noruas, segment, subsegment, posisiKanan);

        setVisibleValue(cdGorKirDiameter, txGorKirDiameter, View.GONE);
        setVisibleValue(cdGorKirLebar, txGorKirLebar, View.GONE);
        setVisibleValue(cdGorKirTinggi, txGorKirTinggi, View.GONE);

        helperTextValue.setTextString("", txGorKirPenampang);
        helperTextValue.setTextString("", txGorKirKonstruksi);

        if(dataGorongKiri!=null){
             if(dataGorongKiri.getJenisPenampang()!=null){

                helperTextValue.setTextString(dataGorongKiri.getJenisPenampang(), txGorKirPenampang);
                helperTextValue.setTextString(dataGorongKiri.getJenisKonstruksi(), txGorKirKonstruksi);
                helperTextValue.setTextString(dataGorongKiri.getDiameter(), txGorKirDiameter);
                helperTextValue.setTextString(dataGorongKiri.getTinggi(), txGorKirTinggi);
                helperTextValue.setTextString(dataGorongKiri.getLebar(), txGorKirLebar);



                if(dataGorongKiri.getJenisPenampang().equals("Lingkaran")){
                    setVisibleValue(cdGorKirDiameter, txGorKirDiameter, View.VISIBLE);
                }else if(dataGorongKiri.getJenisPenampang().equals("Segiempat")){
                    setVisibleValue(cdGorKirTinggi, txGorKirTinggi, View.VISIBLE);
                    setVisibleValue(cdGorKirLebar, txGorKirLebar, View.VISIBLE);
                }
            }


        }

        setVisibleValue(cdGorKanDiameter, txGorKanDiameter, View.GONE);
        setVisibleValue(cdGorKanLebar, txGorKanLebar, View.GONE);
        setVisibleValue(cdGorKanTinggi, txGorKanTinggi, View.GONE);

        helperTextValue.setTextString("", txGorKanPenampang);
        helperTextValue.setTextString("", txGorKanKonstruksi);

        if(dataGorongKanan!=null){
            if(dataGorongKanan.getJenisPenampang()!=null){

                helperTextValue.setTextString(dataGorongKanan.getJenisPenampang(), txGorKanPenampang);
                helperTextValue.setTextString(dataGorongKanan.getJenisKonstruksi(), txGorKanKonstruksi);
                helperTextValue.setTextString(dataGorongKanan.getDiameter(), txGorKanDiameter);
                helperTextValue.setTextString(dataGorongKanan.getTinggi(), txGorKanTinggi);
                helperTextValue.setTextString(dataGorongKanan.getLebar(), txGorKanLebar);



                if(dataGorongKanan.getJenisPenampang().equals("Lingkaran")){
                    setVisibleValue(cdGorKanDiameter, txGorKanDiameter, View.VISIBLE);
                }else if(dataGorongKanan.getJenisPenampang().equals("Segiempat")){
                    setVisibleValue(cdGorKanTinggi, txGorKanTinggi, View.VISIBLE);
                    setVisibleValue(cdGorKanLebar, txGorKanLebar, View.VISIBLE);
                }
            }


        }



    }


    public void setListSegment() {
        listsegment = dbSpinner.listSegment(String.valueOf(session.getKodeprov()), session.getNoruas());

        segmentAdapter = new SegmentAdapter(listsegment, context, new SegmentClick() {
            @Override
            public void Klik(String provinsi, String ruas, int segment, int subsegment, String km, String sta) {

                setValue(provinsi, ruas, segment, subsegment, sta);

                setListSubsegment(provinsi, ruas, segment);

            }
        });

        recyclerView.setLayoutManager(horizontalku);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(segmentAdapter);

        recyclerView.scrollToPosition(session.getNosegment()-2);
        recyclerView.setScrollY(3);
        segmentAdapter.notifyDataSetChanged();


    }

    private void setVisibleValue(CardView cardView, TextView textView, Integer value){

        cardView.setVisibility(value);
        textView.setVisibility(value);

    }

    public void setListSubsegment(String provinsi, String ruas, int segment) {

        listSub.clear();
        listSub = dbSpinner.listSubSegment(provinsi, ruas, segment);

        if(listSub.size()>1){

            session.saveSPInt(Session.FOKUS, (segment-1)*10);

            recSub.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            if(surveyArah.equals("Opposite")){
                linearLayoutManager.setReverseLayout(true);
            }else{
                linearLayoutManager.setReverseLayout(false);
            }
            subSegmentAdapter = new SubSegmentAdapter(listSub, context, new SegmentClick() {
                @Override
                public void Klik(String provinsi, String ruas, int segment, int subsegment, String km, String sta) {
                    setValue(provinsi, ruas, segment, subsegment, sta);
                }
            });

            recSub.setLayoutManager(linearLayoutManager);
            recSub.setHasFixedSize(true);
            recSub.scrollToPosition(session.getSubsegment()-1);
            recSub.setAdapter(subSegmentAdapter);
            subSegmentAdapter.notifyDataSetChanged();


        }else{

            session.saveSPInt(Session.FOKUS, segment-1);

            session.saveSPInt(Session.SP_SUBSEGMENT, 0);
            recSub.setVisibility(View.GONE);

        }

    }


    public void scrolling(){

        int visible, gone, lScrol, lMax, rScrol, rMax;
        if(surveyArah.equals("Opposite")){
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

    private void setBoxLane(DataLane dataLane, int statBox, RelativeLayout rowLane, LinearLayout boxLane, TextView textSurface, TextView texLebar, CardView rowKond, LinearLayout linKond, ImageView img){

        linKond.setVisibility(View.GONE);
        img.setVisibility(View.GONE);

        if(statBox==1){
            rowLane.setVisibility(View.VISIBLE);
            boxLane.setVisibility(View.VISIBLE);
            if(surveyUser==99){
                rowKond.setVisibility(View.VISIBLE);
                linKond.setVisibility(View.VISIBLE);
            }
            if(dataLane!=null){
                textSurface.setText(helperTextValue.tipeLane(dataLane.getSc1()));
                helperTextValue.setTextString(dataLane.getLebarLane(), texLebar);
            }
        }

    }

    private Intent clickBoxLane(String lajur){

        Intent i = new Intent(context, MainFormTerusan.class);
        i.putExtra("tipe", "Lane");
        i.putExtra("posisi", lajur);
        i.putExtra("from", "Survey");

        return i;
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


    private void setTextKotak(){

        String survey = session.getTipesurvey();

        txLahanL = findViewById(R.id.formTxLahKir);
        txSaluranL = findViewById(R.id.formTxSalKir);
        txBahuL = findViewById(R.id.formTxBahKir);
        txInletL = findViewById(R.id.formTxInKir);
        txOutletL = findViewById(R.id.formTxOutKir);
        txGorongL = findViewById(R.id.formTxGorKir);

        txLahanR = findViewById(R.id.formTxLahKan);
        txSaluranR = findViewById(R.id.formTxSalKan);
        txBahuR = findViewById(R.id.formTxBahKan);
        txInletR = findViewById(R.id.formTxInKan);
        txOutletR = findViewById(R.id.formTxOutKan);
        txGorongR = findViewById(R.id.formTxGorKan);

        setTextValue(txLahanL, txLahanR, survey);
        setTextValue(txBahuL, txBahuR, survey);
        setTextValue(txSaluranL, txSaluranR, survey);
        setTextValue(txInletL, txInletR, survey);
        setTextValue(txOutletL, txOutletR, survey);
        setTextValue(txGorongL, txGorongR, survey);

        txL1 = findViewById(R.id.formTxL1);
        txL2 = findViewById(R.id.formTxL2);
        txL3 = findViewById(R.id.formTxL3);
        txL4 = findViewById(R.id.formTxL4);
        txL5 = findViewById(R.id.formTxL5);
        txL6 = findViewById(R.id.formTxL6);
        txL7 = findViewById(R.id.formTxL7);
        txL8 = findViewById(R.id.formTxL8);
        txL9 = findViewById(R.id.formTxL9);
        txL10 = findViewById(R.id.formTxL10);
        txR1 = findViewById(R.id.formTxR1);
        txR2 = findViewById(R.id.formTxR2);
        txR3 = findViewById(R.id.formTxR3);
        txR4 = findViewById(R.id.formTxR4);
        txR5 = findViewById(R.id.formTxR5);
        txR6 = findViewById(R.id.formTxR6);
        txR7 = findViewById(R.id.formTxR7);
        txR8 = findViewById(R.id.formTxR8);
        txR9 = findViewById(R.id.formTxR9);
        txR10 = findViewById(R.id.formTxR10);

        setTextValue(txL1, txR1, survey,1);
        setTextValue(txL2, txR2, survey,2);
        setTextValue(txL3, txR3, survey,3);
        setTextValue(txL4, txR4, survey,4);
        setTextValue(txL5, txR5, survey, 5);
        setTextValue(txL6, txR6, survey,6);
        setTextValue(txL7, txR7, survey,7);
        setTextValue(txL8, txR8, survey,8);
        setTextValue(txL9, txR9, survey,9);
        setTextValue(txL10, txR10, survey,10);

    }

    private void setTextValue(TextView txL, TextView txR, String survey){

        if(survey.equals("Opposite")){
            txL.setText("R");
            txR.setText("L");
        }else{
            txL.setText("L");
            txR.setText("R");
        }

    }

    private void setTextValue(TextView txL, TextView txR, String survey, int lajur){

        if(survey.equals("Opposite")){
            txL.setText("R"+lajur);
            txR.setText("L"+lajur);
        }else{
            txL.setText("L"+lajur);
            txR.setText("R"+lajur);
        }


    }


}*/
