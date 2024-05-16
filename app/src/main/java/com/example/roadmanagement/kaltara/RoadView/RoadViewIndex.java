package com.example.roadmanagement.kaltara.RoadView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.rifqy.kaltara.R;

public class RoadViewIndex {}
/*extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = DetailMedian.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    Session session;
    String ruasku;


    Handler handler = new Handler();


    Context mContex;


    MenuItem lihatTabel;
    MenuItem downloadDokumen;







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_view_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        session = new Session(this);

        //dbDownloadRuas = new DbDownloadRuas(Menuutama.this);
        mContex = Menuutama.this;


        spinner = findViewById(R.id.spinnermenuutama);

        unknownSegment = findViewById(R.id.unknownsegment);

        //terbalik
        recyclePerubahan = findViewById(R.id.updatedrecycle);
        recycleUpdate = findViewById(R.id.dashboardrecycle);

        dbTemporari = new DbTemporari(this);
        noprovku = findViewById(R.id.dbprovinsi);

        lahanhitam = findViewById(R.id.lahanh);
        lahanbiru = findViewById(R.id.lahanb);
        lahanmerah = findViewById(R.id.lahanm);
        bahuhitam = findViewById(R.id.bahuh);
        bahubiru = findViewById(R.id.bahub);
        bahumerah = findViewById(R.id.bahum);
        saluranhitam = findViewById(R.id.saluranh);
        saluranbiru = findViewById(R.id.saluranb);
        saluranmerah = findViewById(R.id.saluranm);
        lanehitam = findViewById(R.id.laneh);
        lanebiru = findViewById(R.id.laneb);
        lanemerah = findViewById(R.id.lanem);
        medianhitam = findViewById(R.id.medianh);
        medianbiru = findViewById(R.id.medianb);
        medianmerah = findViewById(R.id.medianm);
        segmenthitam = findViewById(R.id.segmenth);
        segmentbiru = findViewById(R.id.segmentb);

        askbutton = findViewById(R.id.buttonask);
        cardlahan = findViewById(R.id.menulahan);
        cardsaluran = findViewById(R.id.menusaluran);
        cardbahu = findViewById(R.id.menubahu);
        cardmedian = findViewById(R.id.menumedian);
        cardlane = findViewById(R.id.menulane);
        cardsegment = findViewById(R.id.menusegment);


        cardInlet = findViewById(R.id.menuInlet);
        cardMinlet = findViewById(R.id.menuMinlet);
        cardOutlet = findViewById(R.id.menuOutlet);
        cardGorong = findViewById(R.id.menuGorong);
        cardLereng = findViewById(R.id.menuLereng);

        inletM = findViewById(R.id.inletm);
        inletH = findViewById(R.id.inleth);
        inletB  = findViewById(R.id.inletb);

        minletM = findViewById(R.id.minletm);
        minletH = findViewById(R.id.minleth);
        minletB  = findViewById(R.id.minletb);

        outletM = findViewById(R.id.outletM);
        outletH = findViewById(R.id.outletH);
        outletB  = findViewById(R.id.outletB);

        gorongM = findViewById(R.id.gorongM);
        gorongH = findViewById(R.id.gorongH);
        gorongB  = findViewById(R.id.gorongB);

        lerengM = findViewById(R.id.lerengm);
        lerengH = findViewById(R.id.lerengh);
        lerengB  = findViewById(R.id.lerengb);

        Menu menu = navigationView.getMenu();
        lihatTabel = menu.findItem(R.id.tableku);
        downloadDokumen = menu.findItem(R.id.dokumenku);


        sinkron = findViewById(R.id.sinkron);
        sinkronulang = findViewById(R.id.sinkronulang);

        textEmptyPerubahan = findViewById(R.id.kosongsudah);
        textEmptyUpdate = findViewById(R.id.kosongbelum);

        offlinemode = findViewById(R.id.offlinedoc);


        permissionHelper = new PermissionHelper(Menuutama.this);

        dbLahan = new DbLahan(Menuutama.this);
        dbSaluran = new DbSaluran(this);
        dbBahu = new DbBahu(this);
        dbLane = new DbLane(this);
        dbMedian = new DbMedian(this);
        dbSegmen = new DbSegmen(this);

        setRecycle(recyclePerubahan, textEmptyPerubahan, "0");
        setRecycle(recycleUpdate, textEmptyUpdate, "1");


        fungsiAPI = new FungsiAPI(Menuutama.this);
        dbSpinner = new DbSpinner(this);
        dbRuas = new DbRuas(this);

        noprovku.setText(session.getKodeprov());
        //session.saveSPString(Session.SP_NORUAS, null);
        session.saveSPInt(Session.SP_NOSEGMENT, 1);
        session.saveSPInt(Session.SP_SUBSEGMENT, 0);
        session.saveSPInt(Session.FOKUS, 0);
        session.saveSPString(Session.FORM_INFO, "0");

        //session download awal
        if(session.DataRuasDownload()==0) {

            fungsiAPI.saveRuas(String.valueOf(session.getKodeprov()), new Downloadruas() {
                @Override
                public void DownloadAll(final ArrayList<DataRuas> dataRuas) {
                    DownloadRuas downloadTask = new DownloadRuas(Menuutama.this, dataRuas);
                    downloadTask.execute("bisa", "bisa", "bisa");

                }
            });

        }else if(dbRuas.getIndexDownload(session.getKodeprov())==0){
            session.saveSPInt(Session.DOWNLOADALL, 1);
        }else if(session.DownloadAll()==0){
            Pilihdownnloadku();
        }


        initRuas();


        offlinemode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (spinnerku.equals("--Pilih ruas--")) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Silahkan Pilih Ruas Terlebih dahulu", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    permissionHelper.checkAllPermission();
                }


            }
        });


        unknownSegment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkLocation()) {

                    if (spinnerku.equals("--Pilih ruas--")) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "Silahkan Pilih Ruas Terlebih dahulu", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {


                        i = new Intent(Menuutama.this, UnForm.class);
                        startActivity(i);

                    }
                }

            }
        });*/



      /* lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(checkLocation()) {

                    if (spinnerku.equals("--Pilih ruas--")) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "Silahkan Pilih Ruas Terlebih dahulu", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                            i = new Intent(Menuutama.this, PilihLokasi.class);

                            //i = new Intent(Menuutama.this, ListImage.class);
                        } else {
                            i = new Intent(Menuutama.this, FormUtama.class);
                        }
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            }
        });*/


        /*sinkron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(mContex);

                TextView textView = new TextView(mContex);
                textView.setText("Update Ruas "+ruasku);
                textView.setPadding(30, 30, 20, 30);
                textView.setTextSize(16F);
                textView.setBackgroundColor(R.color.colorPrimaryDark);
                textView.setTextColor(Color.WHITE);

                LayoutInflater inflater = ((Activity)mContex).getLayoutInflater();
                View dialogview = inflater.inflate(R.layout.dialoglist, null);
                RecyclerView recyclerViews = dialogview.findViewById(R.id.dialogrecycle);
                UpdateTableDetailAdapter updateAdapter = new UpdateTableDetailAdapter(ruasku, "0", mContex);

                recyclerViews.setLayoutManager(new LinearLayoutManager(mContex, LinearLayout.VERTICAL, false));
                recyclerViews.setHasFixedSize(true);
                recyclerViews.setAdapter(updateAdapter);

                builder.setView(dialogview);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.setCustomTitle(textView);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);

                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                    }
                });

                dialog.show();

            }
        });



        sinkronulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContex);

                TextView textView = new TextView(mContex);
                textView.setText("Update Ulang Ruas "+ruasku);
                textView.setPadding(30, 30, 20, 30);
                textView.setTextSize(16F);
                textView.setBackgroundColor(R.color.colorPrimaryDark);
                textView.setTextColor(Color.WHITE);
                //builder.setMessage("Silahkan pilih bagian untuk disinkron : ");


                LayoutInflater inflater = ((Activity)mContex).getLayoutInflater();
                View dialogview = inflater.inflate(R.layout.dialoglist, null);
                RecyclerView recyclerViews = dialogview.findViewById(R.id.dialogrecycle);
                UpdateTableDetailAdapter updateAdapter = new UpdateTableDetailAdapter(ruasku, "1", mContex);

                recyclerViews.setLayoutManager(new LinearLayoutManager(mContex, LinearLayout.VERTICAL, false));
                recyclerViews.setHasFixedSize(true);
                recyclerViews.setAdapter(updateAdapter);

                builder.setView(dialogview);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.setCustomTitle(textView);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);

                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                        //  dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.RED);
                    }
                });

                dialog.show();

            }
        });


        askbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Menuutama.this);
                //builder.setMessage("Silahkan pilih ruas yang telah ter-sinkron ");

                TextView textView = new TextView(Menuutama.this);
                textView.setText("Keterangan");
                textView.setPadding(20, 20, 20, 20);
                textView.setTextSize(14F);
                textView.setTextColor(Color.BLACK);
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

                LayoutInflater inflater = getLayoutInflater();
                View dialogview = inflater.inflate(R.layout.dialog_informasi, null);
                builder.setView(dialogview);
                final AlertDialog dialog = builder.create();

                dialog.setCustomTitle(textView);
                dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimaryDark);
                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                    }
                });

                dialog.show();


            }
        });

        //text(session.getNoruas());


        cardlahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Lahan");
                startActivity(i);
            }
        });

        cardbahu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Bahu");
                startActivity(i);
            }
        });

        cardsaluran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Saluran");
                startActivity(i);
            }
        });

        cardmedian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Median");
                startActivity(i);
            }
        });

        cardlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Lane");
                startActivity(i);
            }
        });

        cardsegment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Segment");
                startActivity(i);
            }
        });

        cardInlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Inlet ditrotoar");
                startActivity(i);
            }
        });

        cardMinlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Inlet dimedian");
                startActivity(i);
            }
        });

        cardOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Outlet");
                startActivity(i);
            }
        });

        cardGorong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Gorong-gorong");
                startActivity(i);
            }
        });

        cardLereng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Menuutama.this, SinkronUtama.class);
                i.putExtra("tipe", "Saluran Lereng");
                startActivity(i);
            }
        });

    }


    private void setTextTambahan(String ruas, String tipe, TextView textView1, TextView textView2, TextView textView3){

        String jumlah1 = dbTemporari.getJumlahtempo(ruas, tipe, null, "0");
        String jumlah2 = dbTemporari.getJumlahtempo(ruas, tipe, null, "1");
        String jumlah3 = dbTemporari.getJumlahtempo(ruas, tipe, "0", "0");

        setTextCek(jumlah1, textView1);
        setTextCek(jumlah2, textView2);
        setTextCek(jumlah3, textView3);
    }

    private void setTextCek(String value, TextView textView){
        if(value.equals("0")){
            textView.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.VISIBLE);
            textView.setText(value);
        }
    }


    public void setText(String ruas){

        String jumlahlahanhitam = dbTemporari.getJumlahtempo(ruas, "Lahan", null, "0");
        String jumlahlahanbiru = dbTemporari.getJumlahtempo(ruas, "Lahan", null, "1");
        String jumlahlahanmerah = dbTemporari.getJumlahtempo(ruas, "Lahan", "0", "0");

        if(jumlahlahanhitam.equals("0")){
            lahanhitam.setVisibility(View.GONE);
        }else{
            lahanhitam.setVisibility(View.VISIBLE);
            lahanhitam.setText(jumlahlahanhitam);
        }

        if(jumlahlahanbiru.equals("0")){
            lahanbiru.setVisibility(View.GONE);
        }else{
            lahanbiru.setVisibility(View.VISIBLE);
            lahanbiru.setText(jumlahlahanbiru);
        }

        if(jumlahlahanmerah.equals("0")){
            lahanmerah.setVisibility(View.GONE);
        }else{
            lahanmerah.setVisibility(View.VISIBLE);
            lahanmerah.setText(jumlahlahanmerah);
        }

        String jumlahsaluranhitam = dbTemporari.getJumlahtempo(ruas, "Saluran", null, "0");
        String jumlahsaluranbiru = dbTemporari.getJumlahtempo(ruas, "Saluran", null, "1");
        String jumlahsaluranmerah = dbTemporari.getJumlahtempo(ruas, "Saluran", "0", "0");

        if(jumlahsaluranhitam.equals("0")){
            saluranhitam.setVisibility(View.GONE);
        }else{
            saluranhitam.setVisibility(View.VISIBLE);
            saluranhitam.setText(jumlahsaluranhitam);
        }

        if(jumlahsaluranbiru.equals("0")){
            saluranbiru.setVisibility(View.GONE);
        }else{
            saluranbiru.setVisibility(View.VISIBLE);
            saluranbiru.setText(jumlahsaluranbiru);
        }

        if(jumlahsaluranmerah.equals("0")){
            saluranmerah.setVisibility(View.GONE);
        }else{
            saluranmerah.setVisibility(View.VISIBLE);
            saluranmerah.setText(jumlahsaluranmerah);
        }

        String jumlahbahuhitam = dbTemporari.getJumlahtempo(ruas, "Bahu", null, "0");
        String jumlahbahubiru = dbTemporari.getJumlahtempo(ruas, "Bahu", null, "1");
        String jumlahbahumerah = dbTemporari.getJumlahtempo(ruas, "Bahu", "0", "0");

        if(jumlahbahuhitam.equals("0")){
            bahuhitam.setVisibility(View.GONE);
        }else{
            bahuhitam.setVisibility(View.VISIBLE);
            bahuhitam.setText(jumlahbahuhitam);
        }

        if(jumlahbahubiru.equals("0")){
            bahubiru.setVisibility(View.GONE);
        }else{
            bahubiru.setVisibility(View.VISIBLE);
            bahubiru.setText(jumlahbahubiru);
        }

        if(jumlahbahumerah.equals("0")){
            bahumerah.setVisibility(View.GONE);
        }else{
            bahumerah.setVisibility(View.VISIBLE);
            bahumerah.setText(jumlahbahumerah);
        }

        String jumlahmedianhitam = dbTemporari.getJumlahtempo(ruas, "Median", null, "0");
        String jumlahmedianbiru = dbTemporari.getJumlahtempo(ruas, "Median", null, "1");
        String jumlahmedianmerah = dbTemporari.getJumlahtempo(ruas, "Median", "0", "0");

        if(jumlahmedianhitam.equals("0")){
            medianhitam.setVisibility(View.GONE);
        }else{
            medianhitam.setVisibility(View.VISIBLE);
            medianhitam.setText(jumlahmedianhitam);
        }

        if(jumlahmedianbiru.equals("0")){
            medianbiru.setVisibility(View.GONE);
        }else{
            medianbiru.setVisibility(View.VISIBLE);
            medianbiru.setText(jumlahmedianbiru);
        }

        if(jumlahmedianmerah.equals("0")){
            medianmerah.setVisibility(View.GONE);
        }else{
            medianmerah.setVisibility(View.VISIBLE);
            medianmerah.setText(jumlahmedianmerah);
        }

        String jumlahlanehitam = dbTemporari.getJumlahtempo(ruas, "Lane", null, "0");
        String jumlahlanebiru = dbTemporari.getJumlahtempo(ruas, "Lane", null, "1");
        String jumlahlanemerah = dbTemporari.getJumlahtempo(ruas, "Lane", "0", "0");

        if(jumlahlanehitam.equals("0")){
            lanehitam.setVisibility(View.GONE);
        }else{
            lanehitam.setVisibility(View.VISIBLE);
            lanehitam.setText(jumlahlanehitam);
        }

        if(jumlahlanebiru.equals("0")){
            lanebiru.setVisibility(View.GONE);
        }else{
            lanebiru.setVisibility(View.VISIBLE);
            lanebiru.setText(jumlahlanebiru);
        }

        if(jumlahlanemerah.equals("0")){
            lanemerah.setVisibility(View.GONE);
        }else{
            lanemerah.setVisibility(View.VISIBLE);
            lanemerah.setText(jumlahlanemerah);
        }

        String jumlahsegmenthitam = dbTemporari.getJumlahtempo(ruas, "Segment", null, "0");
        String jumlahsegmentbiru = dbTemporari.getJumlahtempo(ruas, "Segment", null, "1");


        if(jumlahsegmenthitam.equals("0")){
            segmenthitam.setVisibility(View.GONE);
        }else{
            segmenthitam.setVisibility(View.VISIBLE);
            segmenthitam.setText(jumlahsegmenthitam);
        }

        if(jumlahsegmentbiru.equals("0")){
            segmentbiru.setVisibility(View.GONE);
        }else{
            segmentbiru.setVisibility(View.VISIBLE);
            segmentbiru.setText(jumlahsegmentbiru);
        }

        setTextTambahan(ruas, "Inlet ditrotoar", inletH, inletB, inletM);
        setTextTambahan(ruas, "Inlet dimedian", minletH, minletB, minletM);
        setTextTambahan(ruas, "Outlet", outletH, outletB, outletM);
        setTextTambahan(ruas, "Gorong-gorong", gorongH, gorongB, gorongM);
        setTextTambahan(ruas, "Saluran Lereng", lerengH, lerengB, lerengM);

    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logoutku) {
            session.saveSPBoolean(Session.LOGIN, false);
            session.saveSPString(Session.SP_KODEPROV, null);
            session.saveSPString(Session.SP_NORUAS, null);
            session.saveSPInt(Session.DOWNLOADATARUAS, 0);
            session.saveSPInt(Session.DOWNLOADALL, 0);
            session.saveSPInt(Session.FOKUS, 0);
            session.saveSPInt(Session.SP_SUBSEGMENT, 0);
            startActivity(new Intent(Menuutama.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }else if(id == R.id.dokumenku){
            startActivity(new Intent(Menuutama.this, DokumenActivity.class));
            // finish();
        }else if(id==R.id.cleardata){
            startActivity(new Intent(Menuutama.this, ClearDataLocal.class));
        }else if(id==R.id.tableku){
            if(checkLocation()) {
                i = new Intent(Menuutama.this, LihatTabel.class);
                i.putExtra("posisi", "0");
                startActivity(i);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initRuas(){
        listruas = dbRuas.getRuas(String.valueOf(session.getKodeprov()));
        String[] spinnerArray = new String[listruas.size()];
        listruas.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(Menuutama.this);

        if(session.getNoruas()!=null){
            for(int i=0; i<listruas.size();i++){
                if(session.getNoruas().equals(listruas.get(i))){
                    spinner.setSelection(i);
                    //Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }
            }
        }

        // Toast.makeText(this, String.valueOf(listruas.get(0)), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        session.saveSPString(Session.SP_NORUAS, listruas.get(position));
        spinnerku = listruas.get(position);
        ruasku = listruas.get(position);

        if(!spinnerku.equals("--Pilih ruas--")) {



            setText(listruas.get(position));
            final String sinkronid = dbRuas.getSinkronid(String.valueOf(session.getKodeprov()), spinnerku);
            //Toast.makeText(mContex, sinkronid, Toast.LENGTH_SHORT).show();
            //cek status sudah terdownload atau belum
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Loading ...");
            dialog.setCancelable(false);
            dialog.show();

            if(sinkronid.equals("0")) {
                fungsiAPI.DownloadNew(String.valueOf(session.getKodeprov()), spinnerku, 1, dialog, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        Context context = Menuutama.this;
                        Intent j = new Intent(context, Menuutama.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        ((Activity)context).finish();
                        context.startActivity(j);
                    }
                });

            }else{

                List<String> segmentku = dbSpinner.getSegment(session.getKodeprov(), listruas.get(position));
                if(segmentku.size()==0){
                    //lokasi.setVisibility(View.GONE);
                    offlinemode.setVisibility(View.GONE);
                    unknownSegment.setVisibility(View.VISIBLE);

                    downloadDokumen.setVisible(false);
                    lihatTabel.setVisible(false);

                }else{
                    //lokasi.setVisibility(View.VISIBLE);
                    offlinemode.setVisibility(View.VISIBLE);
                    unknownSegment.setVisibility(View.GONE);

                    downloadDokumen.setVisible(true);
                    lihatTabel.setVisible(true);
                }

                fungsiAPI.cekSinkronid(session.getKodeprov(), spinnerku, sinkronid, dialog, new Sinkrondata() {
                    @Override
                    public void data(ArrayList<CekSinkron> cekSinkrons, String id) {

                        if(cekSinkrons.size()>0) {

                            TaskUpdate taskDownload = new TaskUpdate(Menuutama.this, cekSinkrons, id);
                            taskDownload.execute("oke", "0", "oke");

                        }else{
                            dbRuas.updateSinkronId(session.getKodeprov(), session.getNoruas(), id);
                        }

                    }
                });

            }*/

            /*else{

                List<String> segmentku = dbSpinner.getSegment(session.getKodeprov(), listruas.get(position));

                if(segmentku.size()==0){
                    //lokasi.setVisibility(View.GONE);
                    offlinemode.setVisibility(View.GONE);
                    unknownSegment.setVisibility(View.VISIBLE);

                    downloadDokumen.setVisible(false);
                    lihatTabel.setVisible(false);

                }else{
                    //lokasi.setVisibility(View.VISIBLE);
                    offlinemode.setVisibility(View.VISIBLE);
                    unknownSegment.setVisibility(View.GONE);

                    downloadDokumen.setVisible(true);
                    lihatTabel.setVisible(true);
                }

                final List<String> finalSegmentku = segmentku;

                fungsiAPI.cekSinkronid(session.getKodeprov(), spinnerku, sinkronid, dialog, new Sinkrondata() {
                    @Override
                    public void data(ArrayList<CekSinkron> cekSinkrons) {

                        if(cekSinkrons.size()>0) {

                            TaskUpdate taskDownload = new TaskUpdate(Menuutama.this, cekSinkrons);
                            taskDownload.execute("oke", "0", "oke");

                        }else{

                            dialog.show();
                            fungsiAPI.cekJumlahSegment(session.getKodeprov(), spinnerku, finalSegmentku.size(), dialog, new InterfaceCekJumlahSegment() {
                                @Override
                                public void cekSegment(String status, int segment, ArrayList<DetailDownload> detailDownloads) {

                                   //Toast.makeText(Menuutama.this, String.valueOf(finalSegmentku.size())+"-"+status, Toast.LENGTH_SHORT).show();

                                    if(status.equals("Delete")){

                                        dbSpinner.deleteSpinner(session.getKodeprov(), spinnerku, segment);
                                        dbBahu.deleteBahu(session.getKodeprov(), spinnerku, segment);
                                        dbLahan.deleteLahan(session.getKodeprov(), spinnerku, segment);
                                        dbLane.deleteLane(session.getKodeprov(), spinnerku, segment);
                                        dbMedian.deleteMedian(session.getKodeprov(), spinnerku, segment);
                                        dbSaluran.deleteSaluran(session.getKodeprov(), spinnerku, segment);
                                        dbSegmen.deleteSegment(session.getKodeprov(), spinnerku, segment);

                                        Intent i = new Intent(Menuutama.this, Menuutama.class);
                                        startActivity(i);



                                    }else if (status.equals("Add")){

                                        JumlahSegmentTask jumlahSegmentTask = new JumlahSegmentTask(Menuutama.this, detailDownloads, spinnerku);
                                        jumlahSegmentTask.execute("","","");

                                    }


                                }
                            });
                        }

                    }
                });




            }*/

       /* }else{
            // lokasi.setVisibility(View.GONE);
            offlinemode.setVisibility(View.GONE);
            unknownSegment.setVisibility(View.GONE);
            downloadDokumen.setVisible(false);
            lihatTabel.setVisible(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initRuas();

        setRecycle(recyclePerubahan, textEmptyPerubahan, "1");
        setRecycle(recycleUpdate, textEmptyUpdate, "0");

    }

    public String gettime(){
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        return timeStamp;
    }

    public String getkodeprov(int a){
        String prov = null;

        if(a<10){
            prov="0"+String.valueOf(a);
        }else{
            prov = String.valueOf(a);
        }

        return prov;
    }


    public void Pilihdownnloadku(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Menuutama.this);
        builder.setMessage("Silahkan memilih pilihan Download");
        builder.setPositiveButton("Download All Data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    DownloadAllBaru downloadAllBaru = new DownloadAllBaru(Menuutama.this, session.getKodeprov());
                    downloadAllBaru.execute("bisa","bisa","bisa");

                }else{
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Tidak ada koneksi internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        }).setNegativeButton("Download per ruas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
            }
        });

        dialog.show();
    }



    private boolean checkLocation() {

        boolean i;

        if(!isLocationEnabled()) {
            showAlert();
            i=false;
        }else if(!isLocationHasAcces()){
            requestPermissions();
            i=false;
        }else{
            i = true;
        }

        return i;
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    private boolean isLocationHasAcces(){
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            startLocationPermissionRequest();
        }
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {

        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }


    private void setRecycle(RecyclerView recyclerView, TextView textView, String status){

        List<String> list = dbTemporari.getRuastempo(status);

        if(list.size()>0){
            textView.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.VISIBLE);
        }

        RuasDasboardAdapter ruasDasboardAdapter = new RuasDasboardAdapter(list, mContex, status);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContex, LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(ruasDasboardAdapter);


    }


}*/