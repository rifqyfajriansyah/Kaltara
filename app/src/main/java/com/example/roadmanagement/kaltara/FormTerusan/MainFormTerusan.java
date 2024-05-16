package com.example.roadmanagement.kaltara.FormTerusan;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Formu.FormUtama;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.Tabel.LihatTabel;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.sinkronForm.SinkronUtama;

public class MainFormTerusan extends AppCompatActivity {

    Context context;
    TextView tProvinsi, tRuas, tSegment, tSubSeg, tJudul;
            //tKmAwal, tKmAkhir, tStaAwal, tStaAkhir, tJudul;
    ImageView imBack;
    Session session;
    DbSpinner dbSpinner;
    SingleSegment singleSegment;

    String tipe, posisi, asal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form_terusan);

        // Deklarasi

        context = MainFormTerusan.this;
        session = new Session(context);

        tProvinsi = findViewById(R.id.formProvinsi);
        tRuas = findViewById(R.id.formRuas);
        tSegment = findViewById(R.id.formSegment);
        //tSubSeg = findViewById(R.id.formSubsegment);
        //tKmAwal = findViewById(R.id.formKmAwal);
        //tKmAkhir = findViewById(R.id.formKmakhir);
        //tStaAwal  =findViewById(R.id.formStaAwal);
        //tStaAkhir = findViewById(R.id.formStaAkhir);
        tJudul = findViewById(R.id.formTitle);
        imBack = findViewById(R.id.formBack);
        dbSpinner = new DbSpinner(context);
        //singleSegment = dbSpinner.getSpinner(session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), session.getSubsegment());

        setTextAsset();

    }

    private void setTextAsset(){

        if(getIntent().hasExtra("from")){
            asal = getIntent().getExtras().get("from").toString();
        }

        tipe = getIntent().getExtras().get("tipe").toString();

        if(getIntent().hasExtra("posisi")){
            posisi = getIntent().getExtras().get("posisi").toString();
            tJudul.setText(tipe+" "+posisi);
        }else{
            tJudul.setText(tipe);
        }

        //set value asset diawal
        tProvinsi.setText(session.getKodeprov());
        tRuas.setText(session.getNoruas());
        tSegment.setText(session.getNosegment()+"."+session.getSubsegment());
      //  tKmAwal.setText(singleSegment.getKmawal());
      //  tKmAwal.setText(singleSegment.getKmakhir());
      //  tStaAwal.setText(singleSegment.getStaawal());
      //  tStaAkhir.setText(singleSegment.getStaakhir());

        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i ;

                if(session.getSurvey()==1){
                    i = new Intent(context, FormUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }else if(asal.equals("Tabel")){
                    i = new Intent(context, LihatTabel.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("posisi", session.getPosisiTable());
                }else{
                    i = new Intent(context, SinkronUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("tipe", tipe);
                }
                startActivity(i);
                finish();
            }
        });



        switch (tipe) {

            case "Segment":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentSegmentTerusan.newInstance(posisi)).commit();
                break;


            case "Lane":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentLaneTerusan.newInstance(posisi)).commit();
                break;

            case "Median":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentMedianTerusan.newInstance(posisi)).commit();
                break;

            case "Bahu":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentBahuTerusan.newInstance(posisi)).commit();
                break;

            case "Lahan":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentLahanTerusan.newInstance(posisi)).commit();
                break;

            case "Saluran":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentSaluranTerusan.newInstance(posisi)).commit();
                break;

            case "Inlet ditrotoar":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentInlet.newInstance(posisi)).commit();
                break;

            case "Perkerasan":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentPerkerasan.newInstance(posisi, "Perkerasan")).commit();
                break;

            case "SPD":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentPerkerasan.newInstance(posisi, "SPD")).commit();
                break;

            case "SPL":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentPerkerasan.newInstance(posisi, "SPL")).commit();
                break;

            case "Outlet":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentOutlet.newInstance(posisi)).commit();
                break;

            case "Gorong-gorong":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentGorong.newInstance(posisi)).commit();
                break;

            case "Saluran Lereng":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.formFragment, FragmentLereng.newInstance(posisi)).commit();
                break;

        }

    }
}
