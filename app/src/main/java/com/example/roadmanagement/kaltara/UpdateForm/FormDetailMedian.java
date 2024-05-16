package com.example.roadmanagement.kaltara.UpdateForm;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.AsyntaskResponse;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.helper.Session;

public class FormDetailMedian extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;

    DbRuas dbRuas;
    //ClassModel classModel;

    Session session;
    //List<String> listruas = new ArrayList();
    String ruasl="kosong";
    AsyntaskResponse response;
    ViewPager vpPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_detail_median);
        session = new Session(this);
        //spinner = findViewById(R.id.spinnerfragment);
        dbRuas = new DbRuas(this);

        //initRuas();
        vpPager = (ViewPager) findViewById(R.id.pagerformmedian);
        adapterViewPager = new FormDetailMedian.MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);



    }

   /* public void initRuas(){
        listruas = dbRuas.getRuas(String.valueOf(session.getKodeprov()));
        String[] spinnerArray = new String[listruas.size()];
        listruas.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(LihatTabel.this);

    }*/
/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String ruasr = listruas.get(position);
        ClassModel.getInstance().changeState(ruasr);
        //ruasl = listruas.get(position);
        //vpPager.getAdapter().notifyDataSetChanged();
    }*/



    public  class MyPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 2;

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
                    return FragmentMedianDetail.newInstance(0, "");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return FragmentMedianKolektif.newInstance(1, "", null);

                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Single";
                case 1:
                    return "Kolektif";
                default:
                    return null;
            }

        }

    }
}
