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

public class FormDetailBahu extends AppCompatActivity {

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
        setContentView(R.layout.activity_form_detail_bahu);
        session = new Session(this);
        //spinner = findViewById(R.id.spinnerfragment);
        dbRuas = new DbRuas(this);

        //initRuas();
        vpPager = (ViewPager) findViewById(R.id.pagerformbahu);
        adapterViewPager = new FormDetailBahu.MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);



    }



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
                    return FragmentBahuDetail.newInstance(0, "");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return FragmentBahuKolektif.newInstance(1, "", null);

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
