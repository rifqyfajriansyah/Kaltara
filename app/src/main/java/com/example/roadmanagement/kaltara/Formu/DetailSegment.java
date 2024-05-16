package com.example.roadmanagement.kaltara.Formu;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DetailSegment extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}/*implements  AdapterView.OnItemSelectedListener {
    Context context;
    Session session;
    DataSegmen dataSegmen;
    DbSegmen dbSegmen;
    int jumlahlane =0;
    int statl1 = 0;
    int statl2 = 0;
    int statl3 = 0;
    int statl4 = 0;
    int statr1 = 0;
    int statr2 = 0;
    int statr3 = 0;
    int statr4 = 0;
    int status = 0;
    ImageView gl1;
    ImageView gl2;
    ImageView gl3;
    ImageView gl4;
    ImageView gr1;
    ImageView gr2;
    ImageView gr3;
    ImageView gr4;
    RelativeLayout kotakl1;
    RelativeLayout kotakl2;
    RelativeLayout kotakl3;
    RelativeLayout kotakl4;
    RelativeLayout kotakr1;
    RelativeLayout kotakr2;
    RelativeLayout kotakr3;
    RelativeLayout kotakr4;
    TextView jumlahlaner;
    Button save;

    TextView dsegment;
    TextView druas;
    TextView dprovinsi;

    Spinner vertikal;
    Spinner horizontal;
    Spinner tipejalan;

    List<String> vertikals;
    List<String> horizontals;
    List<String> tipejalans;

    Float lebarpvmt;


    SpinnerHelper spinnerhelpertipejalan;

    DbTemporari dbtemporari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_segment);
        context = DetailSegment.this;
        gl1 = findViewById(R.id.detaillanel1);
        gl2 = findViewById(R.id.detaillanel2);
        gl3 = findViewById(R.id.detaillanel3);
        gl4 = findViewById(R.id.detaillanel4);
        gr1 = findViewById(R.id.detaillaner1);
        gr2 = findViewById(R.id.detaillaner2);
        gr3 = findViewById(R.id.detaillaner3);
        gr4 = findViewById(R.id.detaillaner4);
        kotakl1 = findViewById(R.id.kotakl1);
        kotakl2 =findViewById(R.id.kotakl2);
        kotakl3 = findViewById(R.id.kotakl3);
        kotakl4 = findViewById(R.id.kotakl4);
        kotakr1 = findViewById(R.id.kotakr1);
        kotakr2 =findViewById(R.id.kotakr2);
        kotakr3 = findViewById(R.id.kotakr3);
        kotakr4 = findViewById(R.id.kotakr4);
        jumlahlaner =findViewById(R.id.jumlahlane);
        save = findViewById(R.id.detailsegmentbutton);
        jumlahlaner.setText("0");

        vertikal = findViewById(R.id.spinneralvertikal);
        horizontal = findViewById(R.id.spinneralhorizontal);
        tipejalan = findViewById(R.id.spinnertipejalan);

       // gl1.setVisibility(View.GONE);
      //  gr1.setVisibility(View.GONE);

        dbSegmen = new DbSegmen(this);
        session = new Session(this);
        dbtemporari = new DbTemporari(this);
        dataSegmen = dbSegmen.getSegmen(session.getKodeprov(), session.getNoruas(), session.getNosegment());
        if (dataSegmen!=null){
            statl1 = dataSegmen.getSegmentl1();
            statl2 = dataSegmen.getSegmentl2();
            statl3 = dataSegmen.getSegmentl3();
            statl4 = dataSegmen.getSegmentl4();
            statr1 = dataSegmen.getSegmentr1();
            statr2 = dataSegmen.getSegmentr2();
            statr3 = dataSegmen.getSegmentr3();
            statr4 = dataSegmen.getSegmentr4();
            int kiri = statl1+statl2+statl3+statl4;
            int kanan = statr1+statr2+statr3+statr4;
            jumlahlane = dataSegmen.getJumlahsegment();
            jumlahlaner.setText(String.valueOf(jumlahlane));
            setSegment(kiri, kotakl1, kotakl2, kotakl3, kotakl4, gl1, gl2, gl3, gl4);
            setSegment(kanan, kotakr1, kotakr2, kotakr3, kotakr4, gr1, gr2, gr3, gr4);
            status = 1;
        }else{
            status = 0;
           dataSegmen =new DataSegmen(0,0,null,0,0,0,0,0,0,0,0,0,0,"","","",0);
            setSegment(0, kotakl1, kotakl2, kotakl3, kotakl4, gl1, gl2, gl3, gl4);
            setSegment(0, kotakr1, kotakr2, kotakr3, kotakr4, gr1, gr2, gr3, gr4);
        }
        leftClick();
        rightClick();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekPerubahan(dataSegmen.getSegmentl1(), statl1, "L1");
                cekPerubahan(dataSegmen.getSegmentl2(), statl2, "L2");
                cekPerubahan(dataSegmen.getSegmentl3(), statl3, "L3");
                cekPerubahan(dataSegmen.getSegmentl4(), statl4, "L4");
                cekPerubahan(dataSegmen.getSegmentr1(), statr1, "R1");
                cekPerubahan(dataSegmen.getSegmentr2(), statr2, "R2");
                cekPerubahan(dataSegmen.getSegmentr3(), statr3, "R3");
                cekPerubahan(dataSegmen.getSegmentr4(), statr4, "R4");
                dataSegmen.setNoprov(session.getKodeprov());
                dataSegmen.setNoruas(session.getNoruas());
                dataSegmen.setNosegment(session.getNosegment());
                dataSegmen.setSegmentl1(statl1);
                dataSegmen.setSegmentl2(statl2);
                dataSegmen.setSegmentl3(statl3);
                dataSegmen.setSegmentl4(statl4);
                dataSegmen.setSegmentr1(statr1);
                dataSegmen.setSegmentr2(statr2);
                dataSegmen.setSegmentr3(statr3);
                dataSegmen.setSegmentr4(statr4);
                dataSegmen.setJumlahsegment(jumlahlane);
                dataSegmen.setLebarpvmt(lebarpvmt);
                if(status==1){
                    dbSegmen.updateSegmen(dataSegmen);
                }else{
                    dbSegmen.insertSegmen(dataSegmen);
                }

                setValueTemporari("Segment", "Update", "");
                Intent i = new Intent(DetailSegment.this, FormUtama.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        dprovinsi = findViewById(R.id.detailprovinsi);
        druas = findViewById(R.id.detailruas);
        dsegment = findViewById(R.id.detailsegment);


        dsegment.setText(String.valueOf(session.getNosegment()));
        druas.setText(session.getNoruas());
        dprovinsi.setText(String.valueOf(session.getKodeprov()));

        initvertikal();
        initHorizontal();
        initTipejalan();

        horizontal.setSelection(getIndex(horizontals, dataSegmen.getHorizontal()));
        vertikal.setSelection(getIndex(vertikals, dataSegmen.getVertikal()));
        tipejalan.setSelection(getIndex(tipejalans, dataSegmen.getTipejalan()));

    }

    public void leftClick(){
        gl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statl1==0) {
                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.kliktambah));
                    gl1.setImageResource(R.drawable.icon_silang);
                    //gl1.setVisibility(View.GONE);
                    gl2.setVisibility(View.VISIBLE);
                    gl2.setImageResource(R.drawable.icon_tambah);
                    statl1=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakl1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl1.setImageResource(R.drawable.icon_tambah);
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
                    gl3.setImageResource(R.drawable.icon_tambah);
                    statl2=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakl2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl1.setVisibility(View.VISIBLE);
                    gl1.setImageResource(R.drawable.icon_silang);
                    gl2.setImageResource(R.drawable.icon_tambah);
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
                    gl4.setImageResource(R.drawable.icon_tambah);
                    statl3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakl3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gl2.setVisibility(View.VISIBLE);
                    gl2.setImageResource(R.drawable.icon_silang);
                    gl3.setImageResource(R.drawable.icon_tambah);
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
                    gl4.setImageResource(R.drawable.icon_tambah);
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
                    gr2.setImageResource(R.drawable.icon_tambah);
                    statr1=1;
                    jumlahlane = jumlahlane +1;
                }else{
                    kotakr1.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr1.setImageResource(R.drawable.icon_tambah);
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
                    gr3.setImageResource(R.drawable.icon_tambah);
                    statr2=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr2.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr1.setVisibility(View.VISIBLE);
                    gr1.setImageResource(R.drawable.icon_silang);
                    gr2.setImageResource(R.drawable.icon_tambah);
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
                    gr4.setImageResource(R.drawable.icon_tambah);
                    statr3=1;
                    jumlahlane = jumlahlane+1;
                }else{
                    kotakr3.setBackground(ContextCompat.getDrawable(context, R.drawable.klikhapus));
                    gr2.setVisibility(View.VISIBLE);
                    gr2.setImageResource(R.drawable.icon_silang);
                    gr3.setImageResource(R.drawable.icon_tambah);
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
                    gr4.setImageResource(R.drawable.icon_tambah);
                    statr4=0;
                    jumlahlane = jumlahlane-1;
                }
                jumlahlaner.setText(String.valueOf(jumlahlane));
            }
        });

    }

  public void setSegment(int jumlah, RelativeLayout k1, RelativeLayout k2, RelativeLayout k3, RelativeLayout k4, ImageView g1, ImageView g2, ImageView g3, ImageView g4){
        switch (jumlah){
            case 0:{
                g1.setImageResource(R.drawable.icon_tambah);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
            }
            break;
            case 1:{
                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g1.setImageResource(R.drawable.icon_silang);
                //g1.setVisibility(View.GONE);
                g2.setVisibility(View.VISIBLE);
                g2.setImageResource(R.drawable.icon_tambah);
                g3.setVisibility(View.GONE);
                g4.setVisibility(View.GONE);
            }
            break;
            case 2 :{
                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g1.setVisibility(View.GONE);
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g2.setImageResource(R.drawable.icon_silang);
                g3.setVisibility(View.VISIBLE);
                g3.setImageResource(R.drawable.icon_tambah);
                g4.setVisibility(View.GONE);
            }
            break;
            case 3:{
                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g3.setImageResource(R.drawable.icon_silang);
                g4.setVisibility(View.VISIBLE);
                g4.setImageResource(R.drawable.icon_tambah);
            }
            break;
            case 4:{
                k1.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k2.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                k3.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g1.setVisibility(View.GONE);
                g2.setVisibility(View.GONE);
                g3.setVisibility(View.GONE);
                k4.setBackground(ContextCompat.getDrawable(context,R.drawable.kliktambah));
                g4.setImageResource(R.drawable.icon_silang);
            }
        }
  }

    public void setValueTemporari(String tipe, String letak, String urut){
        DataTemporari dataTemporarip = new DataTemporari(0, session.getKodeprov(), session.getNoruas(), session.getNosegment(), tipe, letak, urut, "0");

        dbtemporari.postTemporari(dataTemporarip);
    }

    private int getIndex(List<String> dataSpinners, String myString){

        int index = 0;

        for (int i=0;i<dataSpinners.size();i++){
            if (dataSpinners.get(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    public void initvertikal(){
        vertikals = new ArrayList<>();
        vertikals.add("--Pilih--");
        String text[] = getResources().getStringArray(R.array.al_vertikal);
        for (int i = 0; i < text.length; i++) {
            vertikals.add(text[i]);
        }
        String[] spinnerArray = new String[vertikals.size()];
        vertikals.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        vertikal.setAdapter(spinnerArrayAdapter);
        vertikal.setOnItemSelectedListener(this);

    }

    public void initHorizontal(){
        horizontals = new ArrayList<>();
        horizontals.add("--Pilih--");
        String text[] = getResources().getStringArray(R.array.al_horizontal);
        for (int i = 0; i < text.length; i++) {
            horizontals.add(text[i]);
        }
        String[] spinnerArray = new String[horizontals.size()];
        horizontals.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        horizontal.setAdapter(spinnerArrayAdapter);
        horizontal.setOnItemSelectedListener(this);

    }


    public void initTipejalan(){
        tipejalans = new ArrayList<>();
        String text[] = getResources().getStringArray(R.array.tipe_lane);
        for (int i = 0; i < text.length; i++) {
            tipejalans.add(text[i]);
        }
        String[] spinnerArray = new String[tipejalans.size()];
        tipejalans.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        tipejalan.setAdapter(spinnerArrayAdapter);
        tipejalan.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.spinneralvertikal) {
            if(vertikals.get(position).equals("--Pilih--")){
                dataSegmen.setVertikal(null);
            }else {
                dataSegmen.setVertikal(vertikals.get(position));
            }
        }else if (parent.getId() == R.id.spinneralhorizontal){
            if(horizontals.get(position).equals("--Pilih--")) {
                dataSegmen.setHorizontal(null);
            }else {
                dataSegmen.setHorizontal(horizontals.get(position));
            }
        }else if(parent.getId() == R.id.spinnertipejalan){
            if(tipejalans.get(position).equals("--Pilih--")) {
                dataSegmen.setTipejalan(null);
            }else {
                dataSegmen.setTipejalan(tipejalans.get(position));
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void cekPerubahan(int a, int b, String lajur){

        DataTemporari dataTemporari = new DataTemporari(0, session.getKodeprov(), session.getNoruas(), session.getNosegment(), "Segment", "", lajur, "0");

        if(a!=b){

            if(a==0 && b==1){
                //Toast.makeText(context, "Tambah "+lajur, Toast.LENGTH_SHORT).show();
                dataTemporari.setPosisi("Tambah");
            }else if(a==1 && b==0){
                //Toast.makeText(context, "Hapus "+lajur, Toast.LENGTH_SHORT).show();
                dataTemporari.setPosisi("Hapus");
            }

            dbtemporari.postTemporari(dataTemporari);

        }


    }
}*/
