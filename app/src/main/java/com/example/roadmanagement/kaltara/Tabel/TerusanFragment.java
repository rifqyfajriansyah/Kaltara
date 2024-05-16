package com.example.roadmanagement.kaltara.Tabel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.AsyntaskResponse;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataDrainase;
import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataOutlet;
import com.example.roadmanagement.kaltara.Interface.DataSpDalam;
import com.example.roadmanagement.kaltara.Interface.DataSpLuar;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.adapter.TableGorongAdapter;
import com.example.roadmanagement.kaltara.adapter.TableInletAdapter;
import com.example.roadmanagement.kaltara.adapter.TableLerengAdapter;
import com.example.roadmanagement.kaltara.adapter.TableMinletAdapter;
import com.example.roadmanagement.kaltara.adapter.TableOutletAdapter;
import com.example.roadmanagement.kaltara.adapter.TableSpdAdapter;
import com.example.roadmanagement.kaltara.adapter.TableSplAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbGorong;
import com.example.roadmanagement.kaltara.databaseHelper.DbInlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbLereng;
import com.example.roadmanagement.kaltara.databaseHelper.DbMinlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbOutlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpd;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpl;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class TerusanFragment extends Fragment implements ClassModel.Passdata, AdapterView.OnItemSelectedListener {
    // Store instance variables
    private String title;
    private int page;

    DbRuas dbRuas;

    ArrayList<SingleSegment> km= new ArrayList<>();
    List<String> list = new ArrayList<>();

    RecyclerView recyclerView;
    Spinner spinner;
    Session session;

    DbSpinner dbSpinner;
    DbInlet dbInlet;
    DbMinlet dbMinlet;
    DbOutlet dbOutlet;
    DbLereng dbLereng;
    DbGorong dbGorong;
    DbSpd dbSpd;
    DbSpl dbSpl;

    String ruas;
    LinearLayout lFragment;

    String tipe;

    TextView tx1, tx2;

    // newInstance constructor for creating fragment with arguments
    public static TerusanFragment newInstance(String title) {
        TerusanFragment fragmentFirst = new TerusanFragment();
        Bundle args = new Bundle();
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClassModel.getInstance().setListener(this);
        title = getArguments().getString("someTitle");
        tipe = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentambahan, container, false);

        recyclerView = view.findViewById(R.id.tabelTambahanrecycle);
        spinner = view.findViewById(R.id.spinnertTambahan);
        lFragment = view.findViewById(R.id.ruasFragmentTambahan);


        session = new Session(getContext());
        dbRuas = new DbRuas(getContext());
        dbSpinner = new DbSpinner(getContext());

        dbInlet = new DbInlet(getContext());
        dbMinlet = new DbMinlet(getContext());
        dbOutlet = new DbOutlet(getContext());
        dbLereng = new DbLereng(getContext());
        dbGorong = new DbGorong(getContext());

        dbSpd = new DbSpd(getContext());
        dbSpl = new DbSpl(getContext());

        tx1 = view.findViewById(R.id.tableTambahanTx1);
        tx2 = view.findViewById(R.id.tableTambahanTx2);

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



    public void initRuas(){

        list = dbRuas.getRuasDownload(String.valueOf(session.getKodeprov()));
        String[] spinnerArray = new String[list.size()];
        list.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(TerusanFragment.this);

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

    private void setRecycle(String ruas){

        switch (tipe){

            /*case "Inlet ditrotoar" : setRecycleInlet(ruas);
            break;

            case "Inlet dimedian" : setRecycleMinlet(ruas);
                break;

            case "Outlet" : setRecycleOutlet(ruas);
                break;

            case "Gorong-gorong" : setRecycleGorong(ruas);
                break;

            case "Saluran Lereng" : setRecycleLereng(ruas);
                break;*/

            case "Perkerasan" : setRecyclePerkerasan(ruas);
                break;

            case "SPD" : setRecycleSPD(ruas);
                break;

            case "SPL" : setRecycleSPL(ruas);
                break;

            case "Inlet" : setRecycleInlet(ruas);
                break;

            case "Outlet" : setRecycleOutlet(ruas);
                break;

            case "Gorong" : setRecycleGorong(ruas);
                break;


        }

        if (km!=null){
            recyclerView.scrollToPosition(session.getFokus());
        }

    }


    private void setRecycleInlet(String ruas){

        km.clear();

        ArrayList<DataInletTrotoar> listInletKiri ;
        ArrayList<DataInletTrotoar> listInletKanan ;

        if(session.getTipesurvey().equals("Opposite")){
            listInletKiri = dbInlet.getListInlet(session.getKodeprov(), ruas, "kiri");
            listInletKanan = dbInlet.getListInlet(session.getKodeprov(), ruas, "kanan");
        }else{

            listInletKiri = dbInlet.getListInlet(session.getKodeprov(), ruas, "kanan");
            listInletKanan = dbInlet.getListInlet(session.getKodeprov(), ruas, "kiri");

        }

        km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);

        TableInletAdapter adapter = new TableInletAdapter(listInletKiri, listInletKanan, km, getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);



    }

    private void setRecyclePerkerasan(String ruas){

        km.clear();

        ArrayList<DataInletMedian> listPerkerasanKiri ;
        ArrayList<DataInletMedian> ListPerkerasanKanan ;

        if(session.getTipesurvey().equals("Opposite")){
            listPerkerasanKiri = dbMinlet.getListMinlet(session.getKodeprov(), ruas, "kiri");
            ListPerkerasanKanan = dbMinlet.getListMinlet(session.getKodeprov(), ruas, "kanan");
        }else{

            listPerkerasanKiri = dbMinlet.getListMinlet(session.getKodeprov(), ruas, "kanan");
            ListPerkerasanKanan = dbMinlet.getListMinlet(session.getKodeprov(), ruas, "kiri");

        }

        km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);

        TableMinletAdapter adapter = new TableMinletAdapter(listPerkerasanKiri, ListPerkerasanKanan, km, getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }

    private void setRecycleSPD(String ruas){

        km.clear();

        ArrayList<DataSpDalam> listSPDKiri ;
        ArrayList<DataSpDalam> listSPDKanan ;

        if(session.getTipesurvey().equals("Opposite")){
            listSPDKiri = dbSpd.getList(session.getKodeprov(), ruas, "kiri");
            listSPDKanan = dbSpd.getList(session.getKodeprov(), ruas, "kanan");
        }else{

            listSPDKiri = dbSpd.getList(session.getKodeprov(), ruas, "kanan");
            listSPDKanan = dbSpd.getList(session.getKodeprov(), ruas, "kiri");

        }

        km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);

        TableSpdAdapter adapter = new TableSpdAdapter(listSPDKiri, listSPDKanan, km, getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }

    private void setRecycleSPL(String ruas){

        km.clear();

        ArrayList<DataSpLuar> listSPLKiri ;
        ArrayList<DataSpLuar> listSPLKanan ;

        if(session.getTipesurvey().equals("Opposite")){
            listSPLKiri = dbSpl.getList(session.getKodeprov(), ruas, "kiri");
            listSPLKanan = dbSpl.getList(session.getKodeprov(), ruas, "kanan");
        }else{

            listSPLKiri = dbSpl.getList(session.getKodeprov(), ruas, "kanan");
            listSPLKanan = dbSpl.getList(session.getKodeprov(), ruas, "kiri");

        }

        km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);

        TableSplAdapter adapter = new TableSplAdapter(listSPLKiri, listSPLKanan, km, getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }

    private void setRecycleOutlet(String ruas){

        km.clear();

        ArrayList<DataOutlet> listOutletKiri;
        ArrayList<DataOutlet> listOutletKanan;

        if(session.getTipesurvey().equals("Opposite")){
            listOutletKiri = dbOutlet.getListOutlet(session.getKodeprov(), ruas, "kiri");
            listOutletKanan = dbOutlet.getListOutlet(session.getKodeprov(), ruas, "kanan");
        }else{
            listOutletKiri = dbOutlet.getListOutlet(session.getKodeprov(), ruas, "kanan");
            listOutletKanan = dbOutlet.getListOutlet(session.getKodeprov(), ruas, "kiri");
        }

        km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);

        TableOutletAdapter adapter = new TableOutletAdapter(listOutletKiri, listOutletKanan, km, getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }

    private void setRecycleGorong(String ruas){

        km.clear();

        ArrayList<DataCrossDrain> listGorongKiri;
        ArrayList<DataCrossDrain> listGorongKanan;

        if(session.getTipesurvey().equals("Opposite")){
            listGorongKiri = dbGorong.getListGorong(session.getKodeprov(), ruas, "kiri");
            listGorongKanan = dbGorong.getListGorong(session.getKodeprov(), ruas, "kanan");
        }else{
            listGorongKiri = dbGorong.getListGorong(session.getKodeprov(), ruas, "kanan");
            listGorongKanan = dbGorong.getListGorong(session.getKodeprov(), ruas, "kiri");
        }

        km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);

        TableGorongAdapter adapter = new TableGorongAdapter(listGorongKiri, listGorongKanan, km, getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }

    private void setRecycleLereng(String ruas){

        km.clear();

        ArrayList<DataDrainase> listLerengKiri = dbLereng.getListLereng(session.getKodeprov(), ruas, "kiri");
        ArrayList<DataDrainase> listLerengKanan = dbLereng.getListLereng(session.getKodeprov(), ruas, "kanan");


        km = dbSpinner.listSegmentFull(String.valueOf(session.getKodeprov()), ruas);

        TableLerengAdapter adapter = new TableLerengAdapter(listLerengKiri, listLerengKanan, km, getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }

}