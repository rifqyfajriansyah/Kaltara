package com.example.roadmanagement.kaltara.lokasi;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.adapter.DashboardAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    CardView logo;
    Intent i;

    RecyclerView recyclerView;
    DashboardAdapter dashboardAdapter;
    ArrayList<DataTemporari> arrayList = new ArrayList<>();
    DbTemporari dbTemporari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //logo = findViewById(R.id.pilihlokasi);
        recyclerView = findViewById(R.id.dashboardrecycle);
        dbTemporari = new DbTemporari(this);
        arrayList = dbTemporari.getAllTemporari("0");
        dashboardAdapter = new DashboardAdapter(arrayList, Dashboard.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        Toast.makeText(this, arrayList.get(3).getTipe(), Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(dashboardAdapter);



        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Dashboard.this, PilihLokasi.class);
                startActivity(i);
            }
        });

    }
}
