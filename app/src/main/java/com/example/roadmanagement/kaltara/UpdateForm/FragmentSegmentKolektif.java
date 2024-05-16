package com.example.roadmanagement.kaltara.UpdateForm;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.kolektif.KolektifSegmentTask;

import java.util.ArrayList;
import java.util.List;

public class FragmentSegmentKolektif extends Fragment implements  AdapterView.OnItemSelectedListener{

    String ruas;
    String asal;

    Session session;

    TextView tnoprov;
    TextView tnoruas;

    Spinner klawal;
    Spinner klakhir;
    Spinner klvertikal;
    Spinner klhorizontal;
    Spinner tipejalan;

    Button button;

    List<String> ltipejalan;
    List<String> lawal;
    List<String> lakhir;
    List<String> lvertikal;
    List<String> lhorizontal;

    String sawal;
    String sakhir;
    String svertikal;
    String shorizontal;
    String stipejalan;

    EditText kolomlebarpvmt;
    EditText kolomgrade;

    Float lebarpvmt;
    float grade;

    DbSpinner dbSpinner;

    int jumlahlane =0;
    int statl1 = 0;
    int statl2 = 0;
    int statl3 = 0;
    int statl4 = 0;
    int statl5 = 0;
    int statl6 = 0;
    int statl7 = 0;
    int statl8 = 0;
    int statl9 = 0;
    int statl10 = 0;

    int statr1 = 0;
    int statr2 = 0;
    int statr3 = 0;
    int statr4 = 0;
    int statr5 = 0;
    int statr6 = 0;
    int statr7 = 0;
    int statr8 = 0;
    int statr9 = 0;
    int statr10 = 0;

    int status = 0;

    ImageView gl1;
    ImageView gl2;
    ImageView gl3;
    ImageView gl4;
    ImageView gl5;
    ImageView gl6;
    ImageView gl7;
    ImageView gl8;
    ImageView gl9;
    ImageView gl10;

    ImageView gr1;
    ImageView gr2;
    ImageView gr3;
    ImageView gr4;
    ImageView gr5;
    ImageView gr6;
    ImageView gr7;
    ImageView gr8;
    ImageView gr9;
    ImageView gr10;

    RelativeLayout kotakl1;
    RelativeLayout kotakl2;
    RelativeLayout kotakl3;
    RelativeLayout kotakl4;
    RelativeLayout kotakl5;
    RelativeLayout kotakl6;
    RelativeLayout kotakl7;
    RelativeLayout kotakl8;
    RelativeLayout kotakl9;
    RelativeLayout kotakl10;

    RelativeLayout kotakr1;
    RelativeLayout kotakr2;
    RelativeLayout kotakr3;
    RelativeLayout kotakr4;
    RelativeLayout kotakr5;
    RelativeLayout kotakr6;
    RelativeLayout kotakr7;
    RelativeLayout kotakr8;
    RelativeLayout kotakr9;
    RelativeLayout kotakr10;

    TextView jumlahlaner;

    Button save;
    Context context;
    View view;

    int page;
    String title;

    DbSegmen dbSegmen;


    public static FragmentSegmentKolektif newInstance(int page, String title) {
        FragmentSegmentKolektif fragmentFirst = new FragmentSegmentKolektif();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_kolektif_segmentku, container, false);

        tnoprov = view.findViewById(R.id.toolbakprovinsi);
        tnoruas = view.findViewById(R.id.toolbakruas);

        klawal = view.findViewById(R.id.awalkolektifsegmentkolektif);
        klakhir = view.findViewById(R.id.akhirkolektifsegmentkolektif);
        klvertikal = view.findViewById(R.id.spinneralvertikalkolektif);
        klhorizontal = view.findViewById(R.id.spinneralhorizontalkolektif);
        tipejalan = view.findViewById(R.id.spinnertipejalankolektif);

        kolomlebarpvmt = view.findViewById(R.id.lebarpvmtkolektif);
        kolomgrade = view.findViewById(R.id.gradekolektif);

        gl1 = view.findViewById(R.id.detaillanel1kolektif);
        gl2 = view.findViewById(R.id.detaillanel2kolektif);
        gl3 = view.findViewById(R.id.detaillanel3kolektif);
        gl4 = view.findViewById(R.id.detaillanel4kolektif);
        gl5 = view.findViewById(R.id.detaillanel5kolektif);
        gl6 = view.findViewById(R.id.detaillanel6kolektif);
        gl7 = view.findViewById(R.id.detaillanel7kolektif);
        gl8 = view.findViewById(R.id.detaillanel8kolektif);
        gl9 = view.findViewById(R.id.detaillanel9kolektif);
        gl10 = view.findViewById(R.id.detaillanel10kolektif);

        gr1 = view.findViewById(R.id.detaillaner1kolektif);
        gr2 = view.findViewById(R.id.detaillaner2kolektif);
        gr3 = view.findViewById(R.id.detaillaner3kolektif);
        gr4 = view.findViewById(R.id.detaillaner4kolektif);
        gr5 = view.findViewById(R.id.detaillaner5kolektif);
        gr6 = view.findViewById(R.id.detaillaner6kolektif);
        gr7 = view.findViewById(R.id.detaillaner7kolektif);
        gr8 = view.findViewById(R.id.detaillaner8kolektif);
        gr9 = view.findViewById(R.id.detaillaner9kolektif);
        gr10 = view.findViewById(R.id.detaillaner10kolektif);

        kotakl1 = view.findViewById(R.id.kotakl1kolektif);
        kotakl2 = view.findViewById(R.id.kotakl2kolektif);
        kotakl3 = view.findViewById(R.id.kotakl3kolektif);
        kotakl4 = view.findViewById(R.id.kotakl4kolektif);
        kotakl5 = view.findViewById(R.id.kotakl5kolektif);
        kotakl6 = view.findViewById(R.id.kotakl6kolektif);
        kotakl7 = view.findViewById(R.id.kotakl7kolektif);
        kotakl8 = view.findViewById(R.id.kotakl8kolektif);
        kotakl9 = view.findViewById(R.id.kotakl9kolektif);
        kotakl10 = view.findViewById(R.id.kotakl10kolektif);

        kotakr1 = view.findViewById(R.id.kotakr1kolektif);
        kotakr2 = view.findViewById(R.id.kotakr2kolektif);
        kotakr3 = view.findViewById(R.id.kotakr3kolektif);
        kotakr4 = view.findViewById(R.id.kotakr4kolektif);
        kotakr5 = view.findViewById(R.id.kotakr5kolektif);
        kotakr6 = view.findViewById(R.id.kotakr6kolektif);
        kotakr7 = view.findViewById(R.id.kotakr7kolektif);
        kotakr8 = view.findViewById(R.id.kotakr8kolektif);
        kotakr9 = view.findViewById(R.id.kotakr9kolektif);
        kotakr10 = view.findViewById(R.id.kotakr10kolektif);

        jumlahlaner = view.findViewById(R.id.jumlahlanekolektif);
        save = view.findViewById(R.id.kolektifsegmentbutton);

        context = getActivity();

        ltipejalan = new ArrayList<>();
        lawal = new ArrayList<>();
        lakhir = new ArrayList<>();
        lvertikal = new ArrayList<>();
        lhorizontal = new ArrayList<>();

        session = new Session(getActivity());
        dbSpinner = new DbSpinner(getActivity());
        dbSegmen = new DbSegmen(getActivity());

        sawal = String.valueOf(session.getNosegment());

        Toolbar toolbar = view.findViewById(R.id.toolbaksearch);
        TextView textView = view.findViewById(R.id.judulkolektifsegment);
        if(page==3){
            toolbar.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            asal = "mainmenu";
            ruas = title;
        }else{
            asal = getActivity().getIntent().getExtras().get("dari").toString();
            ruas = session.getNoruas();
        }

        tnoprov.setText(session.getKodeprov());
        tnoruas.setText(session.getNoruas());


        initRuasawal();
        initRuasakhir();
        initVertikal();
        initHorizontal();
        initTipejalan();

        klawal.setSelection(session.getNosegment()-1);
        klakhir.setSelection(session.getNosegment()-1);

       // setSegment(0, kotakl1, kotakl2, kotakl3, kotakl4, gl1, gl2, gl3, gl4);
       // setSegment(0, kotakr1, kotakr2, kotakr3, kotakr4, gr1, gr2, gr3, gr4);

        setValue(session.getNosegment());

        leftClick();
        rightClick();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kolomlebarpvmt.getText().toString().equals("")){
                    lebarpvmt=Float.valueOf(0);
                }else{
                    lebarpvmt= Float.valueOf(kolomlebarpvmt.getText().toString());
                }

                if(kolomgrade.getText().toString().equals("")){
                    grade=Float.valueOf(0);
                }else{
                    grade= Float.valueOf(kolomgrade.getText().toString());
                }
                KolektifSegmentTask kolektifSegmentTask = new KolektifSegmentTask(context, session.getKodeprov(), ruas, Integer.valueOf(sawal), Integer.valueOf(sakhir), svertikal, shorizontal, stipejalan, lebarpvmt, grade,
                        statl1, statl2, statl3, statl4, statl5, statl6, statl7, statl8, statl9, statl10,
                        statr1, statr2, statr3, statr4, statr5, statr6, statr7, statr8, statr9, statr10, asal);
                kolektifSegmentTask.execute("bisa", "bisa", "bisa");
            }
        });

        return view;
    }

    private void setValue(int segmentku){

        /*DataSegmen dataSegmen = dbSegmen.getSegmen(session.getKodeprov(), session.getNoruas(), segmentku);
        statl1 = dataSegmen.getSegmentl1();
        statl2 = dataSegmen.getSegmentl2();
        statl3 = dataSegmen.getSegmentl3();
        statl4 = dataSegmen.getSegmentl4();
        statl5 = dataSegmen.getSegmentl5();
        statl6 = dataSegmen.getSegmentl6();
        statl7 = dataSegmen.getSegmentl7();
        statl8 = dataSegmen.getSegmentl8();
        statl9 = dataSegmen.getSegmentl9();
        statl10 = dataSegmen.getSegmentl10();

        statr1 = dataSegmen.getSegmentr1();
        statr2 = dataSegmen.getSegmentr2();
        statr3 = dataSegmen.getSegmentr3();
        statr4 = dataSegmen.getSegmentr4();
        statr5 = dataSegmen.getSegmentr5();
        statr6 = dataSegmen.getSegmentr6();
        statr7 = dataSegmen.getSegmentr7();
        statr8 = dataSegmen.getSegmentr8();
        statr9 = dataSegmen.getSegmentr9();
        statr10 = dataSegmen.getSegmentr10();

        int jumlahkiri = statl1+statl2+statl3+statl4+statl5+statl6+statl7+statl8+statl9+statl10;
        int jumlahkanan = statr1+statr2+statr3+statr4+statr5+statr6+statr7+statr8+statr9+statr10;

        jumlahlane = jumlahkanan+jumlahkiri;

        setSegment(jumlahkiri, kotakl1, kotakl2, kotakl3, kotakl4, kotakl5, kotakl6, kotakl7, kotakl8, kotakl9, kotakl10, gl1, gl2, gl3, gl4, gl5, gl6, gl7, gl8, gl9, gl10);
        setSegment(jumlahkanan, kotakr1, kotakr2, kotakr3, kotakr4, kotakr5, kotakr6, kotakr7, kotakr8, kotakr9, kotakr10, gr1, gr2, gr3, gr4, gr5, gr6, gr7, gr8, gr9, gr10);

        tipejalan.setSelection(ltipejalan.indexOf(dataSegmen.getTipejalan()));
        klhorizontal.setSelection(lhorizontal.indexOf(dataSegmen.getHorizontal()));
        klvertikal.setSelection(lvertikal.indexOf(dataSegmen.getVertikal()));
        kolomgrade.setText(String.valueOf(dataSegmen.getGrade()));
        kolomlebarpvmt.setText(String.valueOf(dataSegmen.getLebarpvmt()));

        jumlahlaner.setText(String.valueOf(dataSegmen.getJumlahsegment()));*/

    }


    public void initRuasawal(){
        lawal = dbSpinner.getSegment(String.valueOf(session.getKodeprov()), ruas);
        String[] spinnerArray = new String[lawal.size()];
        lawal.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        klawal.setAdapter(spinnerArrayAdapter);
        klawal.setOnItemSelectedListener(this);

    }

    public void initRuasakhir(){
        lakhir = dbSpinner.getSegment(String.valueOf(session.getKodeprov()), ruas);
        String[] spinnerArray = new String[lakhir.size()];
        lakhir.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        klakhir.setAdapter(spinnerArrayAdapter);
        klakhir.setOnItemSelectedListener(this);

    }


    public void initVertikal(){
        String text[] = getResources().getStringArray(R.array.al_vertikal);
        for (int i = 0; i < text.length; i++) {
            lvertikal.add(text[i]);
        }
        String[] spinnerArray = new String[lvertikal.size()];
        lvertikal.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        klvertikal.setAdapter(spinnerArrayAdapter);
        klvertikal.setOnItemSelectedListener(this);

    }

    public void initHorizontal(){
        String text[] = getResources().getStringArray(R.array.al_horizontal);
        for (int i = 0; i < text.length; i++) {
            lhorizontal.add(text[i]);
        }
        String[] spinnerArray = new String[lhorizontal.size()];
        lhorizontal.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        klhorizontal.setAdapter(spinnerArrayAdapter);
        klhorizontal.setOnItemSelectedListener(this);

    }

    public void initTipejalan(){
        String text[] = getResources().getStringArray(R.array.tipe_lane);
        for (int i = 0; i < text.length; i++) {
            ltipejalan.add(text[i]);
        }
        String[] spinnerArray = new String[ltipejalan.size()];
        ltipejalan.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        tipejalan.setAdapter(spinnerArrayAdapter);
        tipejalan.setOnItemSelectedListener(this);

    }

    public String getkodeprov(int a) {
        String prov;

        if (a < 10) {
            prov = "0" + String.valueOf(a);
        } else {
            prov = String.valueOf(a);
        }

        return prov;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.awalkolektifsegmentkolektif){
            sawal = lawal.get(position);
            setValue(Integer.valueOf(sawal));
        }else if(parent.getId() == R.id.akhirkolektifsegmentkolektif){
            sakhir = lakhir.get(position);
        }else if(parent.getId() == R.id.spinneralvertikalkolektif){
            svertikal = lvertikal.get(position);
        }else if(parent.getId() == R.id.spinneralhorizontalkolektif){
            shorizontal = lhorizontal.get(position);
        }else if(parent.getId() == R.id.spinnertipejalankolektif){
            stipejalan = ltipejalan.get(position);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void leftClick(){

        gl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl1==0) {
                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl2.setVisibility(View.VISIBLE);

                    gl1.setImageResource(R.drawable.icon_silang);
                    gl2.setImageResource(R.drawable.icon_add_lane);

                    statl1=1;
                    jumlahlane = jumlahlane+1;
                }else{

                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl2.setVisibility(View.GONE);

                    gl1.setImageResource(R.drawable.icon_add_lane);

                    statl1=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl2==0) {
                    kotakl2.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl1.setVisibility(View.GONE);
                    gl3.setVisibility(View.VISIBLE);

                    gl2.setImageResource(R.drawable.icon_silang);
                    gl3.setImageResource(R.drawable.icon_add_lane);

                    statl2=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakl2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl1.setVisibility(View.VISIBLE);
                    gl3.setVisibility(View.GONE);

                    gl1.setImageResource(R.drawable.icon_silang);
                    gl2.setImageResource(R.drawable.icon_add_lane);

                    statl2=0;
                    jumlahlane = jumlahlane -1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl3==0) {
                    kotakl3.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl2.setVisibility(View.GONE);
                    gl4.setVisibility(View.VISIBLE);

                    gl3.setImageResource(R.drawable.icon_silang);
                    gl4.setImageResource(R.drawable.icon_add_lane);

                    statl3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakl3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl2.setVisibility(View.VISIBLE);
                    gl4.setVisibility(View.GONE);

                    gl2.setImageResource(R.drawable.icon_silang);
                    gl3.setImageResource(R.drawable.icon_add_lane);

                    statl3=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl4==0) {
                    kotakl4.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl3.setVisibility(View.GONE);
                    gl5.setVisibility(View.VISIBLE);

                    gl4.setImageResource(R.drawable.icon_silang);
                    gl5.setImageResource(R.drawable.icon_add_lane);

                    statl4=1;
                    jumlahlane = jumlahlane+1;

                    kotakl5.setVisibility(View.VISIBLE);

                }else{
                    kotakl4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl3.setVisibility(View.VISIBLE);
                    gl5.setVisibility(View.GONE);

                    gl3.setImageResource(R.drawable.icon_silang);
                    gl4.setImageResource(R.drawable.icon_add_lane);


                    statl4=0;
                    jumlahlane = jumlahlane-1;
                    kotakl5.setVisibility(View.GONE);

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl5==0) {
                    kotakl5.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl4.setVisibility(View.GONE);
                    gl6.setVisibility(View.VISIBLE);

                    gl5.setImageResource(R.drawable.icon_silang);
                    gl6.setImageResource(R.drawable.icon_add_lane);


                    kotakl6.setVisibility(View.VISIBLE);


                    statl5=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl5.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl4.setVisibility(View.VISIBLE);
                    gl6.setVisibility(View.GONE);

                    gl4.setImageResource(R.drawable.icon_silang);
                    gl5.setImageResource(R.drawable.icon_add_lane);

                    kotakl6.setVisibility(View.GONE);

                    statl5=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl6==0) {
                    kotakl6.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl5.setVisibility(View.GONE);
                    gl7.setVisibility(View.VISIBLE);

                    gl6.setImageResource(R.drawable.icon_silang);
                    gl7.setImageResource(R.drawable.icon_add_lane);

                    kotakl7.setVisibility(View.VISIBLE);


                    statl6=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl6.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl5.setVisibility(View.VISIBLE);
                    gl7.setVisibility(View.GONE);

                    gl5.setImageResource(R.drawable.icon_silang);
                    gl6.setImageResource(R.drawable.icon_add_lane);


                    kotakl7.setVisibility(View.GONE);


                    statl6=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl7==0) {
                    kotakl7.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl6.setVisibility(View.GONE);
                    gl8.setVisibility(View.VISIBLE);

                    gl7.setImageResource(R.drawable.icon_silang);
                    gl8.setImageResource(R.drawable.icon_add_lane);

                    kotakl8.setVisibility(View.VISIBLE);

                    statl7=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl7.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl6.setVisibility(View.VISIBLE);
                    gl8.setVisibility(View.GONE);

                    gl6.setImageResource(R.drawable.icon_silang);
                    gl7.setImageResource(R.drawable.icon_add_lane);

                    kotakl8.setVisibility(View.GONE);

                    statl7=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl8==0) {
                    kotakl8.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl7.setVisibility(View.GONE);
                    gl9.setVisibility(View.VISIBLE);

                    gl8.setImageResource(R.drawable.icon_silang);
                    gl9.setImageResource(R.drawable.icon_add_lane);

                    kotakl9.setVisibility(View.VISIBLE);


                    statl8=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl8.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl7.setVisibility(View.VISIBLE);
                    gl9.setVisibility(View.GONE);

                    gl7.setImageResource(R.drawable.icon_silang);
                    gl8.setImageResource(R.drawable.icon_add_lane);

                    kotakl9.setVisibility(View.GONE);


                    statl8=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl9==0) {
                    kotakl9.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl8.setVisibility(View.GONE);
                    gl10.setVisibility(View.VISIBLE);

                    gl9.setImageResource(R.drawable.icon_silang);
                    gl10.setImageResource(R.drawable.icon_add_lane);


                    kotakl10.setVisibility(View.VISIBLE);



                    statl9=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl9.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl8.setVisibility(View.VISIBLE);
                    gl10.setVisibility(View.GONE);

                    gl8.setImageResource(R.drawable.icon_silang);
                    gl9.setImageResource(R.drawable.icon_add_lane);

                    kotakl10.setVisibility(View.GONE);



                    statl9=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl10==0) {
                    kotakl10.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gl9.setVisibility(View.GONE);

                    gl10.setImageResource(R.drawable.icon_silang);


                    statl10=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakl10.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gl9.setVisibility(View.VISIBLE);

                    gl9.setImageResource(R.drawable.icon_silang);
                    gl10.setImageResource(R.drawable.icon_add_lane);


                    statl10=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });



    }

    public void rightClick(){
        gr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr1==0) {
                    kotakr1.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr2.setVisibility(View.VISIBLE);

                    gr1.setImageResource(R.drawable.icon_silang);
                    gr2.setImageResource(R.drawable.icon_add_lane);

                    statr1=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakr1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr2.setVisibility(View.GONE);

                    gr1.setImageResource(R.drawable.icon_add_lane);

                    statr1=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr2==0) {
                    kotakr2.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr1.setVisibility(View.GONE);
                    gr3.setVisibility(View.VISIBLE);

                    gr2.setImageResource(R.drawable.icon_silang);
                    gr3.setImageResource(R.drawable.icon_add_lane);

                    statr2=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr1.setVisibility(View.VISIBLE);
                    gr3.setVisibility(View.GONE);

                    gr1.setImageResource(R.drawable.icon_silang);
                    gr2.setImageResource(R.drawable.icon_add_lane);

                    statr2=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr3==0) {
                    kotakr3.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr2.setVisibility(View.GONE);
                    gr4.setVisibility(View.VISIBLE);

                    gr3.setImageResource(R.drawable.icon_silang);
                    gr4.setImageResource(R.drawable.icon_add_lane);

                    statr3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr2.setVisibility(View.VISIBLE);
                    gr4.setVisibility(View.GONE);

                    gr2.setImageResource(R.drawable.icon_silang);
                    gr3.setImageResource(R.drawable.icon_add_lane);

                    statr3=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr4==0) {
                    kotakr4.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr3.setVisibility(View.GONE);
                    gr5.setVisibility(View.VISIBLE);

                    gr4.setImageResource(R.drawable.icon_silang);
                    gr5.setImageResource(R.drawable.icon_add_lane);

                    kotakr5.setVisibility(View.VISIBLE);

                    statr4=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr3.setVisibility(View.VISIBLE);
                    gr5.setVisibility(View.GONE);

                    gr3.setImageResource(R.drawable.icon_silang);
                    gr4.setImageResource(R.drawable.icon_add_lane);

                    kotakr5.setVisibility(View.GONE);


                    statr4=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr5==0) {
                    kotakr5.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr4.setVisibility(View.GONE);
                    gr6.setVisibility(View.VISIBLE);

                    gr5.setImageResource(R.drawable.icon_silang);
                    gr6.setImageResource(R.drawable.icon_add_lane);


                    kotakr6.setVisibility(View.VISIBLE);


                    statr5=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr5.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr4.setVisibility(View.VISIBLE);
                    gr6.setVisibility(View.GONE);

                    gr4.setImageResource(R.drawable.icon_silang);
                    gr5.setImageResource(R.drawable.icon_add_lane);

                    kotakr6.setVisibility(View.GONE);


                    statr5=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr6==0) {
                    kotakr6.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr5.setVisibility(View.GONE);
                    gr7.setVisibility(View.VISIBLE);

                    gr6.setImageResource(R.drawable.icon_silang);
                    gr7.setImageResource(R.drawable.icon_add_lane);

                    kotakr7.setVisibility(View.VISIBLE);


                    statr6=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr6.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr5.setVisibility(View.VISIBLE);
                    gr7.setVisibility(View.GONE);

                    gr5.setImageResource(R.drawable.icon_silang);
                    gr6.setImageResource(R.drawable.icon_add_lane);

                    kotakr7.setVisibility(View.GONE);


                    statr6=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr7==0) {
                    kotakr7.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr6.setVisibility(View.GONE);
                    gr8.setVisibility(View.VISIBLE);

                    gr7.setImageResource(R.drawable.icon_silang);
                    gr8.setImageResource(R.drawable.icon_add_lane);

                    kotakr8.setVisibility(View.VISIBLE);


                    statr7=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr7.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr6.setVisibility(View.VISIBLE);
                    gr8.setVisibility(View.GONE);

                    gr6.setImageResource(R.drawable.icon_silang);
                    gr7.setImageResource(R.drawable.icon_add_lane);

                    kotakr8.setVisibility(View.GONE);


                    statr7=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr8==0) {
                    kotakr8.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr7.setVisibility(View.GONE);
                    gr9.setVisibility(View.VISIBLE);

                    gr8.setImageResource(R.drawable.icon_silang);
                    gr9.setImageResource(R.drawable.icon_add_lane);

                    kotakr9.setVisibility(View.VISIBLE);

                    statr8=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr8.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr7.setVisibility(View.VISIBLE);
                    gr9.setVisibility(View.GONE);

                    gr7.setImageResource(R.drawable.icon_silang);
                    gr8.setImageResource(R.drawable.icon_add_lane);

                    kotakr9.setVisibility(View.GONE);

                    statr8=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });


        gr9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr9==0) {
                    kotakr9.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr8.setVisibility(View.GONE);
                    gr10.setVisibility(View.VISIBLE);

                    gr9.setImageResource(R.drawable.icon_silang);
                    gr10.setImageResource(R.drawable.icon_add_lane);

                    kotakr10.setVisibility(View.VISIBLE);

                    statr9=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr9.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr8.setVisibility(View.VISIBLE);
                    gr10.setVisibility(View.GONE);

                    gr8.setImageResource(R.drawable.icon_silang);
                    gr9.setImageResource(R.drawable.icon_add_lane);

                    kotakr10.setVisibility(View.GONE);

                    statr9=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr10==0) {
                    kotakr10.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr9.setVisibility(View.GONE);

                    gr10.setImageResource(R.drawable.icon_silang);


                    statr10=1;
                    jumlahlane = jumlahlane+1;

                }else{
                    kotakr10.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr9.setVisibility(View.VISIBLE);

                    gr9.setImageResource(R.drawable.icon_silang);
                    gr10.setImageResource(R.drawable.icon_add_lane);


                    statr10=0;
                    jumlahlane = jumlahlane-1;

                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });


        /*gr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr4==0) {
                    kotakr4.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));

                    gr3.setVisibility(View.GONE);

                    gr4.setImageResource(R.drawable.icon_silang);
                    statr4=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));

                    gr3.setVisibility(View.VISIBLE);

                    gr3.setImageResource(R.drawable.icon_silang);
                    gr4.setImageResource(R.drawable.icon_add_lane);
                    statr4=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });*/

    }

    /*public void leftClick(){
        gl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl1==0) {
                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gl1.setImageResource(R.drawable.icon_silang);
                    //gl1.setVisibility(View.GONE);
                    gl2.setVisibility(View.VISIBLE);
                    gl2.setImageResource(R.drawable.icon_add_lane);
                    statl1=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl1.setImageResource(R.drawable.icon_add_lane);
                    gl2.setVisibility(View.GONE);
                    statl1=0;
                    jumlahlane = jumlahlane -1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl2==0) {
                    kotakl2.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gl1.setVisibility(View.GONE);
                    gl2.setImageResource(R.drawable.icon_silang);
                    gl3.setVisibility(View.VISIBLE);
                    gl3.setImageResource(R.drawable.icon_add_lane);
                    statl2=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakl2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl1.setVisibility(View.VISIBLE);
                    gl1.setImageResource(R.drawable.icon_silang);
                    gl2.setImageResource(R.drawable.icon_add_lane);
                    gl3.setVisibility(View.GONE);
                    statl2=0;
                    jumlahlane = jumlahlane -1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl3==0) {
                    kotakl3.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gl2.setVisibility(View.GONE);
                    gl3.setImageResource(R.drawable.icon_silang);
                    gl4.setVisibility(View.VISIBLE);
                    gl4.setImageResource(R.drawable.icon_add_lane);
                    statl3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakl3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl2.setVisibility(View.VISIBLE);
                    gl2.setImageResource(R.drawable.icon_silang);
                    gl3.setImageResource(R.drawable.icon_add_lane);
                    gl4.setVisibility(View.GONE);
                    statl3=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl4==0) {
                    kotakl4.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gl3.setVisibility(View.GONE);
                    gl4.setImageResource(R.drawable.icon_silang);
                    statl4=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakl4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl3.setVisibility(View.VISIBLE);
                    gl3.setImageResource(R.drawable.icon_silang);
                    gl4.setImageResource(R.drawable.icon_add_lane);
                    statl4=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

    }

    public void rightClick(){
        gr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr1==0) {
                    kotakr1.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gr1.setImageResource(R.drawable.icon_silang);
                    //gr1.setVisibility(View.GONE);
                    gr2.setVisibility(View.VISIBLE);
                    gr2.setImageResource(R.drawable.icon_add_lane);
                    statr1=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakr1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr1.setImageResource(R.drawable.icon_add_lane);
                    gr2.setVisibility(View.GONE);
                    statr1=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr2==0) {
                    kotakr2.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gr1.setVisibility(View.GONE);
                    gr2.setImageResource(R.drawable.icon_silang);
                    gr3.setVisibility(View.VISIBLE);
                    gr3.setImageResource(R.drawable.icon_add_lane);
                    statr2=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr1.setVisibility(View.VISIBLE);
                    gr1.setImageResource(R.drawable.icon_silang);
                    gr2.setImageResource(R.drawable.icon_add_lane);
                    gr3.setVisibility(View.GONE);
                    statr2=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr3==0) {
                    kotakr3.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gr2.setVisibility(View.GONE);
                    gr3.setImageResource(R.drawable.icon_silang);
                    gr4.setVisibility(View.VISIBLE);
                    gr4.setImageResource(R.drawable.icon_add_lane);
                    statr3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr2.setVisibility(View.VISIBLE);
                    gr2.setImageResource(R.drawable.icon_silang);
                    gr3.setImageResource(R.drawable.icon_add_lane);
                    gr4.setVisibility(View.GONE);
                    statr3=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

        gr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statr4==0) {
                    kotakr4.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gr3.setVisibility(View.GONE);
                    gr4.setImageResource(R.drawable.icon_silang);
                    statr4=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr4.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr3.setVisibility(View.VISIBLE);
                    gr3.setImageResource(R.drawable.icon_silang);
                    gr4.setImageResource(R.drawable.icon_add_lane);
                    statr4=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

    }*/

    public void setSegment(int jumlah,
                           RelativeLayout k1, RelativeLayout k2, RelativeLayout k3, RelativeLayout k4, RelativeLayout k5, RelativeLayout k6, RelativeLayout k7, RelativeLayout k8, RelativeLayout k9, RelativeLayout k10,
                           ImageView g1, ImageView g2, ImageView g3, ImageView g4, ImageView g5, ImageView g6, ImageView g7, ImageView g8 , ImageView g9, ImageView g10){
        switch (jumlah){
            case 0:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.VISIBLE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_add_lane);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

                k5.setVisibility(View.GONE);
                k6.setVisibility(View.GONE);
                k7.setVisibility(View.GONE);
                k8.setVisibility(View.GONE);
                k9.setVisibility(View.GONE);
                k10.setVisibility(View.GONE);

            }
            break;
            case 1:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.VISIBLE);
                g2.setVisibility(View.VISIBLE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_add_lane);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

                k5.setVisibility(View.GONE);
                k6.setVisibility(View.GONE);
                k7.setVisibility(View.GONE);
                k8.setVisibility(View.GONE);
                k9.setVisibility(View.GONE);
                k10.setVisibility(View.GONE);
            }
            break;
            case 2 :{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.VISIBLE);
                g3.setVisibility(View.VISIBLE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_add_lane);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

                k5.setVisibility(View.GONE);
                k6.setVisibility(View.GONE);
                k7.setVisibility(View.GONE);
                k8.setVisibility(View.GONE);
                k9.setVisibility(View.GONE);
                k10.setVisibility(View.GONE);
            }
            break;
            case 3:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.VISIBLE);
                g4.setVisibility(View.VISIBLE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_add_lane);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

                k5.setVisibility(View.GONE);
                k6.setVisibility(View.GONE);
                k7.setVisibility(View.GONE);
                k8.setVisibility(View.GONE);
                k9.setVisibility(View.GONE);
                k10.setVisibility(View.GONE);

            }
            break;
            case 4:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.VISIBLE);
                g5.setVisibility(View.VISIBLE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_add_lane);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

                k5.setVisibility(View.VISIBLE);
                k6.setVisibility(View.GONE);
                k7.setVisibility(View.GONE);
                k8.setVisibility(View.GONE);
                k9.setVisibility(View.GONE);
                k10.setVisibility(View.GONE);
            }
            break;

            case 5:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.VISIBLE);
                g6.setVisibility(View.VISIBLE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_add_lane);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

                k5.setVisibility(View.VISIBLE);
                k6.setVisibility(View.VISIBLE);
                k7.setVisibility(View.GONE);
                k8.setVisibility(View.GONE);
                k9.setVisibility(View.GONE);
                k10.setVisibility(View.GONE);
            }
            break;

            case 6:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.VISIBLE);
                g7.setVisibility(View.VISIBLE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_add_lane);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

                k5.setVisibility(View.VISIBLE);
                k6.setVisibility(View.VISIBLE);
                k7.setVisibility(View.VISIBLE);
                k8.setVisibility(View.GONE);
                k9.setVisibility(View.GONE);
                k10.setVisibility(View.GONE);
            }
            break;

            case 7:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.VISIBLE);
                g8.setVisibility(View.VISIBLE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_add_lane);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

                k5.setVisibility(View.VISIBLE);
                k6.setVisibility(View.VISIBLE);
                k7.setVisibility(View.VISIBLE);
                k8.setVisibility(View.VISIBLE);
                k9.setVisibility(View.GONE);
                k10.setVisibility(View.GONE);
            }
            break;

            case 8:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.VISIBLE);
                g9.setVisibility(View.VISIBLE);
                g10.setVisibility(View.GONE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_add_lane);
                g10.setImageResource(R.drawable.icon_silang);

                k5.setVisibility(View.VISIBLE);
                k6.setVisibility(View.VISIBLE);
                k7.setVisibility(View.VISIBLE);
                k8.setVisibility(View.VISIBLE);
                k9.setVisibility(View.VISIBLE);
                k10.setVisibility(View.GONE);
            }
            break;

            case 9:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.klikhapus));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.VISIBLE);
                g10.setVisibility(View.VISIBLE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_add_lane);

                k5.setVisibility(View.VISIBLE);
                k6.setVisibility(View.VISIBLE);
                k7.setVisibility(View.VISIBLE);
                k8.setVisibility(View.VISIBLE);
                k9.setVisibility(View.VISIBLE);
                k10.setVisibility(View.VISIBLE);
            }
            break;

            case 10:{

                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k5.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k6.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k7.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k8.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k9.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k10.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));

                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
                g5.setVisibility(View.GONE);
                g6.setVisibility(View.GONE);
                g7.setVisibility(View.GONE);
                g8.setVisibility(View.GONE);
                g9.setVisibility(View.GONE);
                g10.setVisibility(View.VISIBLE);

                g1.setImageResource(R.drawable.icon_silang);
                g2.setImageResource(R.drawable.icon_silang);
                g3.setImageResource(R.drawable.icon_silang);
                g4.setImageResource(R.drawable.icon_silang);
                g5.setImageResource(R.drawable.icon_silang);
                g6.setImageResource(R.drawable.icon_silang);
                g7.setImageResource(R.drawable.icon_silang);
                g8.setImageResource(R.drawable.icon_silang);
                g9.setImageResource(R.drawable.icon_silang);
                g10.setImageResource(R.drawable.icon_silang);

                k5.setVisibility(View.VISIBLE);
                k6.setVisibility(View.VISIBLE);
                k7.setVisibility(View.VISIBLE);
                k8.setVisibility(View.VISIBLE);
                k9.setVisibility(View.VISIBLE);
                k10.setVisibility(View.VISIBLE);
            }
            break;


        }
    }
}

