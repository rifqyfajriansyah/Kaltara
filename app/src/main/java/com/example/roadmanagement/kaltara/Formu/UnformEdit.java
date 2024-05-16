package com.example.roadmanagement.kaltara.Formu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.helper.Session;

public class UnformEdit extends AppCompatActivity{

    Session session;
    FrameLayout frameLayout;

    int index;
    String tipe;
    String sumber;
    String posisi;

    TextView noprov;
    TextView noruas;
    TextView noseg;

    CardView buttonReset;

    DbBahu dbBahu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unform_edit);

        frameLayout = findViewById(R.id.unformedit);
        noprov = findViewById(R.id.detailprovinsi);
        noruas = findViewById(R.id.detailruas);
        noseg = findViewById(R.id.detailsegment);
        buttonReset = findViewById(R.id.resetDataSegment);

        buttonReset.setVisibility(View.GONE);

        session = new Session(this);
        //session.saveSPString(Session.FORM_INFO, "1");

        index = Integer.valueOf(getIntent().getExtras().get("id").toString());
        tipe = getIntent().getExtras().get("tipe").toString();
        sumber = getIntent().getExtras().get("dari").toString();
        posisi = getIntent().getExtras().get("posisi").toString();
        noprov.setText(session.getKodeprov());
        noruas.setText(session.getNoruas());
        noseg.setText(String.valueOf(index));



        switch (tipe) {

            case "Bahu":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.unformedit, DetailBahu.newInstance(index, sumber, posisi)).commit();
                break;

            case "Lahan":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.unformedit, DetailLahan.newInstance(index, sumber, posisi)).commit();
                break;

            case "Saluran":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.unformedit, DetailSaluran.newInstance(index, sumber,posisi)).commit();
                break;

            case "Lane":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.unformedit, DetailLane.newInstance(index, sumber, posisi)).commit();
                break;

            case "Median":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.unformedit, DetailMedian.newInstance(index, sumber)).commit();
                break;
        }



    }




}
