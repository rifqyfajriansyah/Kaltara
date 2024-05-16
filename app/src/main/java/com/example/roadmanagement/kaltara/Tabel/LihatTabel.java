package com.example.roadmanagement.kaltara.Tabel;


import android.app.ProgressDialog;
import android.os.CountDownTimer;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;


public class LihatTabel extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FragmentPagerAdapter adapterViewPager;

    DbRuas dbRuas;
    List<String> ltipe = new ArrayList<>();
    List<String> lruas = new ArrayList<>();
    Session session;

    Spinner sTipe, sRuas;
    String ruasl, tipel;

    Fragment fragment;

    ProgressDialog progressDialog;

    //List<String> listruas = new ArrayList();
    //String ruasl="kosong";
     //AsyntaskResponse response;
     //ViewPager vpPager;
    //ClassModel classModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_tabel);
        session = new Session(this);
        dbRuas = new DbRuas(this);
        sTipe = (Spinner) findViewById(R.id.ltabeltipe);

        initDataTipe();

        ruasl = session.getNoruas();
        tipel = "Segment";

        fragment = new Fragment();

        if(getIntent().hasExtra("posisi")){

            int posisi = Integer.valueOf(getIntent().getExtras().get("posisi").toString());
            sTipe.setSelection(posisi);

        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);


    }

    public void initDataTipe(){
        String text[] = {"Segment", "Median", "Lahan", "Saluran", "Bahu", "Lane", "Perkerasan", "SPD", "SPL", "Inlet", "Outlet", "Gorong"};
        //String text[] = {"Segment", "Lahan","Saluran","Bahu","Median","Lane", "Inlet ditrotoar", "Inlet dimedian", "Outlet", "Gorong-gorong", "Saluran Lereng"};
        for (int i = 0; i < text.length; i++) {
            ltipe.add(text[i]);
        }
        String[] spinnerArray = new String[ltipe.size()];
        ltipe.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        sTipe.setAdapter(spinnerArrayAdapter);
        sTipe.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        progressDialog.show();
        new CountDownTimer(500,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }
        }.start();

        tipel = ltipe.get(position);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.halamantabel, getHalamanFragment(tipel, ruasl)).commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private Fragment getHalamanFragment(String tipeku, String ruasku){

        switch (tipeku) {

            case "Segment": fragment = SegmentFragment.newInstance(3, ruasku);
                break;

            case "Lahan": fragment = LahanFragment.newInstance(3, ruasku);
                break;

            case "Saluran": fragment = SaluranFragment.newInstance(3, ruasku);

                break;
            case "Bahu" : fragment = BahuFragment.newInstance(3, ruasku);

                break;

            case "Median" : fragment = MedianFragment.newInstance(3, ruasku);

                break;

            case "Lane": fragment = LaneFragment.newInstance(3, ruasku);

                break;

            default: fragment = TerusanFragment.newInstance(tipeku);

        }

        return fragment;
    }


   /* public  class MyPagerAdapter extends FragmentPagerAdapter {
        private  int NUM_ITEMS = 6;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }



        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return SegmentFragment.newInstance(0, ruasl);
                case 1: // Fragment # 0 - This will show FirstFragment
                    return LahanFragment.newInstance(1, ruasl);
                case 2: // Fragment # 0 - This will show FirstFragment different title
                    return SaluranFragment.newInstance(2, ruasl);
                case 3: // Fragment # 1 - This will show SecondFragment
                    return BahuFragment.newInstance(3, ruasl);

                case 4: // Fragment # 1 - This will show SecondFragment
                    return MedianFragment.newInstance(4, ruasl);

                case 5: // Fragment # 1 - This will show SecondFragment
                    return LaneFragment.newInstance(5, ruasl);

                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return "Segment";
                case 1 : return "Lahan";
                case 2 : return "Saluran";
                case 3 : return "Bahu";
                case 4 : return "Median";
                case 5 : return  "Lane";
                default: return null;
            }

        }

    }*/






}