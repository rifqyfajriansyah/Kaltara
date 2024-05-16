package com.example.roadmanagement.kaltara.Tabel;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.AsyntaskResponse;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.adapter.TableLaneAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class LaneFragment extends Fragment implements ClassModel.Passdata, AdapterView.OnItemSelectedListener {
    // Store instance variables
    private String title;
    private int page;
    DbLane dbLane;
    ArrayList<DataLane> dataLanesl1 = new ArrayList<>();
    ArrayList<DataLane> dataLanesl2 = new ArrayList<>();
    ArrayList<DataLane> dataLanesl3 = new ArrayList<>();
    ArrayList<DataLane> dataLanesl4 = new ArrayList<>();
    ArrayList<DataLane> dataLanesl5 = new ArrayList<>();
    ArrayList<DataLane> dataLanesl6 = new ArrayList<>();
    ArrayList<DataLane> dataLanesl7 = new ArrayList<>();
    ArrayList<DataLane> dataLanesl8 = new ArrayList<>();
    ArrayList<DataLane> dataLanesl9 = new ArrayList<>();
    ArrayList<DataLane> dataLanesl10 = new ArrayList<>();

    ArrayList<DataLane> dataLanesr1 = new ArrayList<>();
    ArrayList<DataLane> dataLanesr2 = new ArrayList<>();
    ArrayList<DataLane> dataLanesr3 = new ArrayList<>();
    ArrayList<DataLane> dataLanesr4 = new ArrayList<>();
    ArrayList<DataLane> dataLanesr5 = new ArrayList<>();
    ArrayList<DataLane> dataLanesr6 = new ArrayList<>();
    ArrayList<DataLane> dataLanesr7 = new ArrayList<>();
    ArrayList<DataLane> dataLanesr8 = new ArrayList<>();
    ArrayList<DataLane> dataLanesr9 = new ArrayList<>();
    ArrayList<DataLane> dataLanesr10 = new ArrayList<>();

    ArrayList<SingleSegment> km = new ArrayList<>();
    AsyntaskResponse response;
    TableLaneAdapter adapter;
    RecyclerView recyclerView;
    TextView textView;
    Spinner spinner;
    DbRuas dbRuas;
    DbSpinner dbSpinner;
    Session session;
    List<String> list = new ArrayList<>();

    CardView titleR1;
    CardView titleR2;
    CardView titleR3;
    CardView titleR4;
    CardView titleR5;
    CardView titleR6;
    CardView titleR7;
    CardView titleR8;
    CardView titleR9;
    CardView titleR10;

    CardView titleL1;
    CardView titleL2;
    CardView titleL3;
    CardView titleL4;
    CardView titleL5;
    CardView titleL6;
    CardView titleL7;
    CardView titleL8;
    CardView titleL9;
    CardView titleL10;

    int maxkiri;
    int maxkanan;

    String ruas;
    LinearLayout lFragment;

    TextView tl1, tl2, tl3, tl4, tl5, tl6, tl7, tl8, tl9, tl10,
            tr1, tr2, tr3, tr4, tr5, tr6, tr7, tr8, tr9, tr10;


    // newInstance constructor for creating fragment with arguments
    public static LaneFragment newInstance(int page, String title) {
        LaneFragment fragmentFirst = new LaneFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClassModel.getInstance().setListener(this);
        dbLane = new DbLane(getContext());
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentlane, container, false);
        recyclerView = view.findViewById(R.id.tabellanerecycle);
        spinner = view.findViewById(R.id.spinnertlane);
        lFragment = view.findViewById(R.id.ruasFragmentLane);

        titleL1 = view.findViewById(R.id.titlel1);
        titleL2 = view.findViewById(R.id.titlel2);
        titleL3 = view.findViewById(R.id.titlel3);
        titleL4 = view.findViewById(R.id.titlel4);
        titleL5 = view.findViewById(R.id.titlel5);
        titleL6 = view.findViewById(R.id.titlel6);
        titleL7 = view.findViewById(R.id.titlel7);
        titleL8 = view.findViewById(R.id.titlel8);
        titleL9 = view.findViewById(R.id.titlel9);
        titleL10 = view.findViewById(R.id.titlel10);

        titleR1 = view.findViewById(R.id.titler1);
        titleR2 = view.findViewById(R.id.titler2);
        titleR3 = view.findViewById(R.id.titler3);
        titleR4 = view.findViewById(R.id.titler4);
        titleR5 = view.findViewById(R.id.titler5);
        titleR6 = view.findViewById(R.id.titler6);
        titleR7 = view.findViewById(R.id.titler7);
        titleR8 = view.findViewById(R.id.titler8);
        titleR9 = view.findViewById(R.id.titler9);
        titleR10 = view.findViewById(R.id.titler10);

        tl1 = view.findViewById(R.id.tableLaneL1);
        tl2 = view.findViewById(R.id.tableLaneL2);
        tl3 = view.findViewById(R.id.tableLaneL3);
        tl4 = view.findViewById(R.id.tableLaneL4);
        tl5 = view.findViewById(R.id.tableLaneL5);
        tl6 = view.findViewById(R.id.tableLaneL6);
        tl7 = view.findViewById(R.id.tableLaneL7);
        tl8 = view.findViewById(R.id.tableLaneL8);
        tl9 = view.findViewById(R.id.tableLaneL9);
        tl10 = view.findViewById(R.id.tableLaneL10);

        tr1 = view.findViewById(R.id.tableLaneR1);
        tr2 = view.findViewById(R.id.tableLaneR2);
        tr3 = view.findViewById(R.id.tableLaneR3);
        tr4 = view.findViewById(R.id.tableLaneR4);
        tr5 = view.findViewById(R.id.tableLaneR5);
        tr6 = view.findViewById(R.id.tableLaneR6);
        tr7 = view.findViewById(R.id.tableLaneR7);
        tr8 = view.findViewById(R.id.tableLaneR8);
        tr9 = view.findViewById(R.id.tableLaneR9);
        tr10 = view.findViewById(R.id.tableLaneR10);


        session = new Session(getContext());
        dbRuas = new DbRuas(getContext());
        dbSpinner = new DbSpinner(getContext());

        if(session.getTipesurvey().equals("Opposite")){
            maxkiri = dbLane.getMaxLajur(session.getKodeprov(), session.getNoruas(), "kiri");
            maxkanan = dbLane.getMaxLajur(session.getKodeprov(), session.getNoruas(), "kanan");

            setTitle("kiri", tl1, tl2, tl3, tl4, tl5, tl6, tl7, tl8, tl9, tl10);
            setTitle("kanan", tr1, tr2, tr3, tr4, tr5, tr6, tr7, tr8, tr9, tr10);

        }else{
            maxkiri = dbLane.getMaxLajur(session.getKodeprov(), session.getNoruas(), "kanan");
            maxkanan = dbLane.getMaxLajur(session.getKodeprov(), session.getNoruas(), "kiri");

            setTitle("kanan", tl1, tl2, tl3, tl4, tl5, tl6, tl7, tl8, tl9, tl10);
            setTitle("kiri", tr1, tr2, tr3, tr4, tr5, tr6, tr7, tr8, tr9, tr10);
        }

        cekTitle(maxkiri, titleL1, titleL2, titleL3, titleL4, titleL5, titleL6, titleL7, titleL8, titleL9, titleL10);
        cekTitle(maxkanan, titleR1, titleR2, titleR3, titleR4, titleR5, titleR6, titleR7, titleR8, titleR9, titleR10);

        ruas = session.getNoruas();

        if(session.getSurvey()==1){
            lFragment.setVisibility(View.GONE);
            setRecycle(ruas);
        }else{
            lFragment.setVisibility(View.VISIBLE);
            initRuas();

        }

        return view;
    }

    public void initRuas(){

        list = dbRuas.getRuasDownload(String.valueOf(session.getKodeprov()));
        String[] spinnerArray = new String[list.size()];
        list.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(LaneFragment.this);

        spinner.setSelection(setValue());
    }

    private void setRecycle(String ruas){

        km.clear();
        dataLanesl1.clear();
        dataLanesl2.clear();
        dataLanesl3.clear();
        dataLanesl4.clear();
        dataLanesl5.clear();
        dataLanesl6.clear();
        dataLanesl7.clear();
        dataLanesl8.clear();
        dataLanesl9.clear();
        dataLanesl10.clear();

        dataLanesr1.clear();
        dataLanesr2.clear();
        dataLanesr3.clear();
        dataLanesr4.clear();
        dataLanesr5.clear();
        dataLanesr6.clear();
        dataLanesr7.clear();
        dataLanesr8.clear();
        dataLanesr9.clear();
        dataLanesr10.clear();

        km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);

        dataLanesl1 = dbLane.getListLane(session.getKodeprov(), ruas, "kiri", "L1");
        dataLanesl2 = dbLane.getListLane(session.getKodeprov(), ruas, "kiri", "L2");
        dataLanesl3 = dbLane.getListLane(session.getKodeprov(), ruas, "kiri", "L3");
        dataLanesl4 = dbLane.getListLane(session.getKodeprov(), ruas, "kiri", "L4");
        dataLanesl5 = dbLane.getListLane(session.getKodeprov(), ruas, "kiri", "L5");
        dataLanesl6 = dbLane.getListLane(session.getKodeprov(), ruas, "kiri", "L6");
        dataLanesl7 = dbLane.getListLane(session.getKodeprov(), ruas, "kiri", "L7");
        dataLanesl8 = dbLane.getListLane(session.getKodeprov(), ruas, "kiri", "L8");
        dataLanesl9 = dbLane.getListLane(session.getKodeprov(), ruas, "kiri", "L9");
        dataLanesl10 = dbLane.getListLane(session.getKodeprov(), ruas, "kiri", "L10");

        dataLanesr1 = dbLane.getListLane(session.getKodeprov(), ruas, "kanan", "R1");
        dataLanesr2 = dbLane.getListLane(session.getKodeprov(), ruas, "kanan", "R2");
        dataLanesr3 = dbLane.getListLane(session.getKodeprov(), ruas, "kanan", "R3");
        dataLanesr4 = dbLane.getListLane(session.getKodeprov(), ruas, "kanan", "R4");
        dataLanesr5 = dbLane.getListLane(session.getKodeprov(), ruas, "kanan", "R5");
        dataLanesr6 = dbLane.getListLane(session.getKodeprov(), ruas, "kanan", "R6");
        dataLanesr7 = dbLane.getListLane(session.getKodeprov(), ruas, "kanan", "R7");
        dataLanesr8 = dbLane.getListLane(session.getKodeprov(), ruas, "kanan", "R8");
        dataLanesr9 = dbLane.getListLane(session.getKodeprov(), ruas, "kanan", "R9");
        dataLanesr10 = dbLane.getListLane(session.getKodeprov(), ruas, "kanan", "R10");

        if(session.getTipesurvey().equals("Opposite")){

            maxkiri = dbLane.getMaxLajur(session.getKodeprov(), ruas, "kiri");
            maxkanan = dbLane.getMaxLajur(session.getKodeprov(), ruas, "kanan");
            adapter = new TableLaneAdapter(dataLanesl1, dataLanesl2, dataLanesl3, dataLanesl4, dataLanesl5, dataLanesl6, dataLanesl7, dataLanesl8, dataLanesl9, dataLanesl10,
                    dataLanesr1, dataLanesr2, dataLanesr3, dataLanesr4, dataLanesr5, dataLanesr6, dataLanesr7, dataLanesr8, dataLanesr9, dataLanesr10,
                    km, getContext(), maxkiri, maxkanan);

        }else{
            maxkiri = dbLane.getMaxLajur(session.getKodeprov(), ruas, "kanan");
            maxkanan = dbLane.getMaxLajur(session.getKodeprov(), ruas, "kiri");
            adapter = new TableLaneAdapter(dataLanesr1, dataLanesr2, dataLanesr3, dataLanesr4, dataLanesr5, dataLanesr6, dataLanesr7, dataLanesr8, dataLanesr9, dataLanesr10,
                    dataLanesl1, dataLanesl2, dataLanesl3, dataLanesl4, dataLanesl5, dataLanesl6, dataLanesl7, dataLanesl8, dataLanesl9, dataLanesl10,
                    km, getContext(), maxkiri, maxkanan);
        }

        /*maxkiri = dbLane.getMaxLajur(session.getKodeprov(), ruas, "kiri");
        maxkanan = dbLane.getMaxLajur(session.getKodeprov(), ruas, "kanan");
        adapter = new TableLaneAdapter(dataLanesl1, dataLanesl2, dataLanesl3, dataLanesl4, dataLanesl5, dataLanesl6, dataLanesl7, dataLanesl8, dataLanesl9, dataLanesl10,
                dataLanesr1, dataLanesr2, dataLanesr3, dataLanesr4, dataLanesr5, dataLanesr6, dataLanesr7, dataLanesr8, dataLanesr9, dataLanesr10,
                km, getContext(), maxkiri, maxkanan);*/

        cekTitle(maxkiri, titleL1, titleL2, titleL3, titleL4, titleL5, titleL6, titleL7, titleL8, titleL9, titleL10);
        cekTitle(maxkanan, titleR1, titleR2, titleR3, titleR4, titleR5, titleR6, titleR7, titleR8, titleR9, titleR10);


        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (dataLanesl1!=null || dataLanesr1!=null){
            recyclerView.scrollToPosition(session.getFokus());
        }

    }


    @Override
    public void stateChanged() {

        // Toast.makeText(getContext(), ClassModel.getInstance().getAngka(), Toast.LENGTH_SHORT).show();
        // dataLahan.clear();
        //  dataLahan = dbLahan.getAllLahan("35", ClassModel.getInstance().getAngka());
        // adapter = new TableLahanAdapter(dataLahan, getContext());
        // adapter.notifyDataSetChanged();
        //recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ruas = list.get(position);
        setRecycle(ruas);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int setValue(){
        int index = 0;

        for(int i = 0 ; i<list.size();i++){
            if(list.get(i).equals(session.getNoruas())){
                index = i;
            }
        }

        return index;
    }

    private void setTitle(String posisi, TextView t1, TextView t2, TextView t3, TextView t4, TextView t5,
                          TextView t6, TextView t7, TextView t8, TextView t9, TextView t10){

        String text;
        if(posisi.equals("kanan")){
            text = "R";
        }else{
            text = "L";
        }

        t1.setText(text+"1");
        t2.setText(text+"2");
        t3.setText(text+"3");
        t4.setText(text+"4");
        t5.setText(text+"5");
        t6.setText(text+"6");
        t7.setText(text+"7");
        t8.setText(text+"8");
        t9.setText(text+"9");
        t10.setText(text+"10");

    }


    private void cekTitle(int jumlah, CardView c1, CardView c2, CardView c3, CardView c4, CardView c5, CardView c6, CardView c7, CardView c8, CardView c9, CardView c10){

        switch (jumlah){
            case 0 :
                c1.setVisibility(View.GONE);
                c2.setVisibility(View.GONE);
                c3.setVisibility(View.GONE);
                c4.setVisibility(View.GONE);
                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;


            case 1 :
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.GONE);
                c3.setVisibility(View.GONE);
                c4.setVisibility(View.GONE);
                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;


            case 2 :
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.GONE);
                c4.setVisibility(View.GONE);
                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;


            case 3 :
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);
                c4.setVisibility(View.GONE);
                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;


            case 4 :
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);
                c4.setVisibility(View.VISIBLE);
                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;

            case 5 :
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);
                c4.setVisibility(View.VISIBLE);
                c5.setVisibility(View.VISIBLE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;

            case 6 :
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);
                c4.setVisibility(View.VISIBLE);
                c5.setVisibility(View.VISIBLE);
                c6.setVisibility(View.VISIBLE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;

            case 7 :
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);
                c4.setVisibility(View.VISIBLE);
                c5.setVisibility(View.VISIBLE);
                c6.setVisibility(View.VISIBLE);
                c7.setVisibility(View.VISIBLE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;

            case 8 :
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);
                c4.setVisibility(View.VISIBLE);
                c5.setVisibility(View.VISIBLE);
                c6.setVisibility(View.VISIBLE);
                c7.setVisibility(View.VISIBLE);
                c8.setVisibility(View.VISIBLE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;

            case 9 :
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);
                c4.setVisibility(View.VISIBLE);
                c5.setVisibility(View.VISIBLE);
                c6.setVisibility(View.VISIBLE);
                c7.setVisibility(View.VISIBLE);
                c8.setVisibility(View.VISIBLE);
                c9.setVisibility(View.VISIBLE);
                c10.setVisibility(View.GONE);

                break;

            case 10 :
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);
                c4.setVisibility(View.VISIBLE);
                c5.setVisibility(View.VISIBLE);
                c6.setVisibility(View.VISIBLE);
                c7.setVisibility(View.VISIBLE);
                c8.setVisibility(View.VISIBLE);
                c9.setVisibility(View.VISIBLE);
                c10.setVisibility(View.VISIBLE);

                break;


            default:

        }

    }
}

