package com.example.roadmanagement.kaltara.Tabel;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.adapter.TableMedianAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class MedianFragment extends Fragment implements ClassModel.Passdata, AdapterView.OnItemSelectedListener {
    // Store instance variables
    private String title;
    private int page;
    DbMedian dbMedian;
    ArrayList<DataMedian> dataBahus = new ArrayList<>();
    AsyntaskResponse response;
    TableMedianAdapter adapter;
    RecyclerView recyclerView;
    TextView textView;
    Spinner spinner;
    DbRuas dbRuas;
    DbSpinner dbSpinner;
    Session session;
    List<String> list = new ArrayList<>();

    String ruas;
    LinearLayout lFragment;

    TextView tx;

    // newInstance constructor for creating fragment with arguments
    public static MedianFragment newInstance(int page, String title) {
        MedianFragment fragmentFirst = new MedianFragment();
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
        dbMedian = new DbMedian(getContext());
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentmedian, container, false);
        recyclerView = view.findViewById(R.id.tabelmedianrecycle);
        spinner = view.findViewById(R.id.spinnertmedian);
        lFragment = view.findViewById(R.id.ruasFragmentMedian);


        session = new Session(getContext());
        dbRuas = new DbRuas(getContext());
        dbSpinner = new DbSpinner(getContext());

        tx = view.findViewById(R.id.tableMedianTx);
        if(session.getTipesurvey().equals("Opposite")){
            tx.setText("R1-L1");
        }else{
            tx.setText("L1-R1");
        }

        ruas = session.getNoruas();

        if(session.getSurvey()==1){
            lFragment.setVisibility(View.GONE);
            setRecyle(ruas);
        }else{
            lFragment.setVisibility(View.VISIBLE);
            initRuas();
        }

        return view;
    }

    private void setRecyle(String ruas){

        dataBahus.clear();

        ArrayList<SingleSegment> km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);
        dataBahus = dbMedian.getListMedian(String.valueOf(session.getKodeprov()), ruas);
        adapter = new TableMedianAdapter(dataBahus, km, getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (dataBahus!=null){
            recyclerView.scrollToPosition(session.getFokus());
        }

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
        spinner.setOnItemSelectedListener(MedianFragment.this);

        spinner.setSelection(setValue());
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
        setRecyle(ruas);


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
}

