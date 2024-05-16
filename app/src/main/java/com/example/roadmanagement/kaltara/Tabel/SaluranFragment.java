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
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.adapter.TableSaluranAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class SaluranFragment extends Fragment implements ClassModel.Passdata, AdapterView.OnItemSelectedListener {
    // Store instance variables
    private String title;
    private int page;
    DbSaluran dbSaluran;
    ArrayList<DataSaluran> dataSalurans = new ArrayList<>();
    ArrayList<DataSaluran> dataSalurank = new ArrayList<>();
    ArrayList<SingleSegment> km = new ArrayList<>();
    AsyntaskResponse response;
    TableSaluranAdapter adapter;
    RecyclerView recyclerView;
    TextView textView;
    Spinner spinner;
    DbRuas dbRuas;
    DbSpinner dbSpinner;
    Session session;
    List<String> list = new ArrayList<>();

    String ruas;
    LinearLayout lFragment;

    TextView tx1, tx2;

    public static SaluranFragment newInstance(int page, String title) {
        SaluranFragment fragmentFirst = new SaluranFragment();
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
        dbSaluran = new DbSaluran(getContext());
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentsaluran, container, false);
        recyclerView = view.findViewById(R.id.tabelsaluranrecycle);
        spinner = view.findViewById(R.id.spinnertsaluran);
        lFragment = view.findViewById(R.id.ruasFragmentSaluran);

        dbSpinner = new DbSpinner(getContext());
        session = new Session(getContext());
        dbRuas = new DbRuas(getContext());

        tx1 = view.findViewById(R.id.tableSaluranTx1);
        tx2 = view.findViewById(R.id.tableSaluranTx2);

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

        dataSalurans.clear();
        dataSalurans = dbSaluran.getListSaluran(String.valueOf(session.getKodeprov()), ruas, "kiri");
        dataSalurank = dbSaluran.getListSaluran(String.valueOf(session.getKodeprov()), ruas, "kanan");

        if(session.getTipesurvey().equals("Opposite")){
            dataSalurans = dbSaluran.getListSaluran(String.valueOf(session.getKodeprov()), ruas, "kiri");
            dataSalurank = dbSaluran.getListSaluran(String.valueOf(session.getKodeprov()), ruas, "kanan");
        }else{

            dataSalurans = dbSaluran.getListSaluran(String.valueOf(session.getKodeprov()), ruas, "kanan");
            dataSalurank = dbSaluran.getListSaluran(String.valueOf(session.getKodeprov()), ruas, "kiri");

        }

        km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);

        adapter = new TableSaluranAdapter(dataSalurans, dataSalurank, km, getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (dataSalurans!=null){
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
        spinner.setOnItemSelectedListener(SaluranFragment.this);

        spinner.setSelection(setValue());
    }


    @Override
    public void stateChanged() {

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

