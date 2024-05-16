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

import com.example.roadmanagement.kaltara.Interface.AsyntaskResponse;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.adapter.TableBahuAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class BahuFragment extends Fragment implements ClassModel.Passdata, AdapterView.OnItemSelectedListener {
    // Store instance variables
    private String title;
    private int page;
    DbBahu dbBahu;
    ArrayList<DataBahu> dataBahus = new ArrayList<>();
    ArrayList<DataBahu> dataBahuk = new ArrayList<>();
    ArrayList<SingleSegment> km= new ArrayList<>();
    AsyntaskResponse response;
    TableBahuAdapter adapter;
    RecyclerView recyclerView;
    TextView textView;
    Spinner spinner;
    DbRuas dbRuas;
    Session session;
    List<String> list = new ArrayList<>();
    DbSpinner dbSpinner;

    String ruas;
    LinearLayout lFragment;

    TextView tx1, tx2;

    // newInstance constructor for creating fragment with arguments
    public static BahuFragment newInstance(int page, String title) {
        BahuFragment fragmentFirst = new BahuFragment();
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
        dbBahu = new DbBahu(getContext());
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentbahu, container, false);
        recyclerView = view.findViewById(R.id.tabelbahurecycle);
        spinner = view.findViewById(R.id.spinnertbahu);
        lFragment = view.findViewById(R.id.ruasFragmentBahu);





        session = new Session(getContext());
        dbRuas = new DbRuas(getContext());
        dbSpinner = new DbSpinner(getContext());

        tx1 = view.findViewById(R.id.tableBahuTx1);
        tx2 = view.findViewById(R.id.tableBahuTx2);

        if(session.getTipesurvey().equals("Opposite")){
            tx1.setText("Opposite (R)");
            tx2.setText("Normal (L)");
        }else{
            tx1.setText("Normal (L)");
            tx2.setText("Opposite (R)");
        }

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

    private void setRecycle(String ruas){

        dataBahus.clear();

        if(session.getTipesurvey().equals("Opposite")){
            dataBahus = dbBahu.getListBahu(String.valueOf(session.getKodeprov()), ruas, "kiri");
            dataBahuk = dbBahu.getListBahu(String.valueOf(session.getKodeprov()), ruas, "kanan");
        }else{

            dataBahus = dbBahu.getListBahu(String.valueOf(session.getKodeprov()), ruas, "kanan");
            dataBahuk = dbBahu.getListBahu(String.valueOf(session.getKodeprov()), ruas, "kiri");

        }

        km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);

        adapter = new TableBahuAdapter(dataBahus, dataBahuk, km, getContext());
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
        spinner.setOnItemSelectedListener(BahuFragment.this);

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
}
