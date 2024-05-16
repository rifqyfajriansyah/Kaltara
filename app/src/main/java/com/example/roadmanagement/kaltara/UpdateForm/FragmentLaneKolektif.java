package com.example.roadmanagement.kaltara.UpdateForm;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.BuildConfig;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Formu.DetailMedian;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.HelperList;
import com.example.roadmanagement.kaltara.helper.ImageHelper;
import com.example.roadmanagement.kaltara.helper.Session;

import com.example.roadmanagement.kaltara.kolektif.KolektifTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragmentLaneKolektif extends Fragment implements  AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    // Store instance variables
    String ruas;
    String asal;
    private String title;
    private int page;
    TextView tnoprov;
    TextView tnoruas;

    Spinner klposisi;
    Spinner klawal;
    Spinner klakhir;
    Spinner klsc;

    EditText tlebar;
    //EditText tlebarpvmt;
    Button button;

    Session session;
    DbLane dbLane;
    DbTemporari dbTemporari;
    DbRuas dbRuas;
    DbSpinner dbSpinner;

    Context context;

    List<String> lposisi;
    List<String> lawal;
    List<String> lakhir;
    List<String> lsc;


    String sawal;
    String sakhir;
    String sposisi;
    String ssc;
    String slebar;
    //String spvmt;


    String letak;
    View view;

    ImageAdapter imageAdapter;
    RecyclerView recyclerView;
    ImageView fotoButton;

    String gambarl;
    String gambaricon;
    String lokasi;

    ArrayList<File> listgambar = new ArrayList<>();
    ArrayList<File> listicon = new ArrayList<>();
    List<String> listlokasi = new ArrayList<>();

    File mPhotoFile;
    File iconFoto;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;

    ImageHelper imageHelper;


    LocationManager locationManager;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private static final String TAG = DetailMedian.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

    HelperList helperList;

    String halaman;

    // newInstance constructor for creating fragment with arguments
    public static FragmentLaneKolektif newInstance(int page, String title, String halaman) {
        FragmentLaneKolektif fragmentFirst = new FragmentLaneKolektif();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putString("someHalaman", halaman);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        halaman = getArguments().getString("someHalaman");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_kolektif_laneku, container, false);
        tnoprov = view.findViewById(R.id.toolbakprovinsi);
        tnoruas = view.findViewById(R.id.toolbakruas);
        klposisi = view.findViewById(R.id.posisikolektiflane);
        klawal = view.findViewById(R.id.awalkolektiflane);
        klakhir = view.findViewById(R.id.akhirkolektiflane);
        klsc = view.findViewById(R.id.sckolektiflane);
        tlebar = view.findViewById(R.id.lebarkolektiflane);
        button = view.findViewById(R.id.buttonkolektiflane);
        context = getActivity();

        lposisi = new ArrayList<>();
        lawal = new ArrayList<>();
        lakhir = new ArrayList<>();
        lsc = new ArrayList<>();


        helperList = new HelperList();


        session = new Session(getActivity());
        dbLane = new DbLane(getActivity());
        dbTemporari = new DbTemporari(getActivity());
        dbRuas = new DbRuas(getActivity());
        dbSpinner = new DbSpinner(context);

        sawal = String.valueOf(session.getNosegment());


        Toolbar toolbar = view.findViewById(R.id.toolbaksearch);
        TextView textView = view.findViewById(R.id.judulkolektiflane);
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

        imageHelper = new ImageHelper(context);
        recyclerView = view.findViewById(R.id.lanekolektifrecycle);
        fotoButton = view.findViewById(R.id.tambahfotolanekolektif);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());




        imageAdapter = new ImageAdapter(listgambar, context, new SendId() {
            @Override
            public void hapusGambar(int id) {

                File filegambar = listgambar.get(id);
                File fileicon = listicon.get(id);

                filegambar.delete();
                fileicon.delete();

                listgambar.remove(id);
                listicon.remove(id);
                listlokasi.remove(id);

                if(listgambar.size()==5){
                    fotoButton.setVisibility(View.GONE);
                }else {
                    fotoButton.setVisibility(View.VISIBLE);
                }
                imageAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

        prepareData();
        setValue();


        //mulai
        fotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!sposisi.equals("--Pilih--")   && !sawal.equals(sakhir)) {

                    letak=sposisi;

                    final CharSequence[] items = {"Take Photo", "Choose from Library",
                            "Cancel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Take Photo")) {

                                if (!checkPermissions()) {
                                    requestPermissions();
                                } else if (checkLocation()) {
                                    requestStoragePermission(true);
                                }

                            } else if (items[item].equals("Choose from Library")) {
                                requestStoragePermission(false);
                            } else if (items[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }else{
                    Toast.makeText(context, "Silahkan mengisi parameter terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                slebar = tlebar.getText().toString();
               // spvmt = tlebarpvmt.getText().toString();

                if(!sposisi.equals("--Pilih--") && !ssc.equals("--Pilih--")  && !tlebar.getText().toString().equals("") && !tlebar.getText().toString().equals("0") && !sawal.equals(sakhir)){

                    String lajur;
                    if(sposisi.substring(0,1).equals("L")){
                        lajur ="kiri";
                    }else{
                        lajur="kanan";
                    }

                    gambarl = helperList.getNamaImage(listgambar);
                    gambaricon = helperList.getNamaImage(listicon);
                    lokasi = helperList.getLokasi(listlokasi);


                    KolektifTask kolektifTask = new KolektifTask(context, session.getKodeprov(), ruas, lajur, sposisi, Integer.valueOf(sawal), Integer.valueOf(sakhir), "Lane", ssc, slebar, null, null, asal, gambarl, gambaricon, lokasi, halaman);
                    kolektifTask.execute("bisa", "bisa", "bisa");
                }else{
                    Toast.makeText(context, "Data tidak komplit", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }


    public void prepareData(){

        initDataLane();
        initPosisi();
        initRuasakhir();
        initRuasawal();

    }


    public void initPosisi(){
        lposisi.add("--Pilih Posisi--");
        lposisi.add("L1");
        lposisi.add("L2");
        lposisi.add("L3");
        lposisi.add("L4");
        lposisi.add("L5");
        lposisi.add("L6");
        lposisi.add("L7");
        lposisi.add("L8");
        lposisi.add("L9");
        lposisi.add("L10");

        lposisi.add("R1");
        lposisi.add("R2");
        lposisi.add("R3");
        lposisi.add("R4");
        lposisi.add("R5");
        lposisi.add("R6");
        lposisi.add("R7");
        lposisi.add("R8");
        lposisi.add("R9");
        lposisi.add("R10");

        String[] spinnerArray = new String[lposisi.size()];
        lposisi.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        klposisi.setAdapter(spinnerArrayAdapter);
        klposisi.setOnItemSelectedListener(this);

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


    public void initDataLane(){
        String text[] = getResources().getStringArray(R.array.tipe_permukaan);
        for (int i = 0; i < text.length; i++) {
            lsc.add(text[i]);
        }
        String[] spinnerArray = new String[lsc.size()];
        lsc.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        klsc.setAdapter(spinnerArrayAdapter);
        klsc.setOnItemSelectedListener(this);

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

    private void getImage(String string){
        if(string!=null) {
            String[] gambarku = string.split(",");
            for (int i = 0; i < gambarku.length; i++) {
                File fileku = new File(gambarku[i]);
                listgambar.add(fileku);
            }
        }
    }
    private void getIcon(String string){
        if(string!=null) {
            String[] gambarku = string.split(",");
            for (int i = 0; i < gambarku.length; i++) {
                File fileku = new File(gambarku[i]);
                listicon.add(fileku);
            }
        }

    }

    private void getLokasi(String string){
        if(string!=null) {
            String[] lokasiku = string.split(",");
            for (int i = 0; i < lokasiku.length; i++) {
                listlokasi.add(lokasiku[i]);
            }
        }
    }

    private String getPosition(String dataposisi){

        String position = dataposisi.substring(0,1);
        if(position.equals("L")){
            position = "kiri";
        }else{
            position = "kanan";
        }

        return  position;

    }


    public void setValue(){

        klposisi.setSelection(lposisi.indexOf(session.getPosisiLajur()));
        klawal.setSelection(session.getNosegment()-1);
        klakhir.setSelection(session.getNosegment()-1);


        if(session.getPosisiLajur()!=null){
            /*DataLane dataLane = dbLane.getLane(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getPosisiLajur(), getPosition(session.getPosisiLajur()));

            if(dataLane!=null) {
                klsc.setSelection(getIndex(lsc, dataLane.getSc1()));
                tlebar.setText(String.valueOf(dataLane.getLebarLane()));
                getImage(dataLane.getGambarLane());
                getIcon(dataLane.getGambarLaneicon());
                getLokasi(dataLane.getLokasiLane());
            }else{
                klsc.setSelection(0);
                tlebar.setText("0");
            }*/

        }else{

            klsc.setSelection(0);
            tlebar.setText("0");

        }



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

    public void setValueOnClick(String posisiku, String segmentku){

        //Toast.makeText(context, getPosition(posisiku), Toast.LENGTH_SHORT).show();

        if(posisiku.equals("--Pilih Posisi--")){

            klsc.setSelection(0);
            tlebar.setText("0");

            listgambar.clear();
            listicon.clear();
            listlokasi.clear();

            imageAdapter.notifyDataSetChanged();

        }else{

            listgambar.clear();
            listicon.clear();
            listlokasi.clear();

            /*DataLane dataLane = dbLane.getLane(session.getKodeprov(), session.getNoruas(), Integer.valueOf(segmentku), posisiku, getPosition(posisiku));
            if(dataLane!=null) {
                klsc.setSelection(lsc.indexOf(dataLane.getSc1()));
                tlebar.setText(String.valueOf(dataLane.getLebarLane()));

                getImage(dataLane.getGambarLane());
                getIcon(dataLane.getGambarLaneicon());
                getLokasi(dataLane.getLokasiLane());
            }else{
                klsc.setSelection(0);
                tlebar.setText("0");
            }*/

            imageAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.posisikolektiflane){
            sposisi = lposisi.get(position);
            setValueOnClick(sposisi, sawal);
        }else if(parent.getId() == R.id.awalkolektiflane){
            sawal = lawal.get(position);
            setValueOnClick(sposisi, sawal);
        }else if(parent.getId() == R.id.akhirkolektiflane){
            sakhir = lakhir.get(position);
        }else if(parent.getId() == R.id.sckolektiflane){
            ssc = lsc.get(position);

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    private void dispatchGalleryIntent() {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //String a = setNamathumbnail(namafile,listgambar.size());
            String a = imageHelper.setNamathumbnailkolektif(ruas,"Lane", listgambar.size(), sawal, sakhir, letak);
            String nama = imageHelper.setNamaFilekolektif(ruas, "Lane",listgambar.size(), sawal, sakhir, letak);

            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {

                    //mPhotoFile = imageHelper.saveBitmapToFile(mPhotoFile,a);
                    mPhotoFile = imageHelper.saveFromCamera(mPhotoFile);
                    iconFoto = imageHelper.saveIcon(mPhotoFile,a);
                    //  Toast.makeText(context, mPhotoFile.getPath(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context,a, Toast.LENGTH_SHORT ).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                listgambar.add(mPhotoFile);
                listicon.add(iconFoto);
                imageAdapter.notifyDataSetChanged();
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();

                try {
                    //mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    mPhotoFile = new File(getRealPathFromUri(selectedImage));
                    //mPhotoFile = imageHelper.getGallerytoFile(mPhotoFile,nama,a);
                    mPhotoFile = imageHelper.saveFromGaleri(mPhotoFile, nama);
                    iconFoto = imageHelper.saveIcon(mPhotoFile,a);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                listgambar.add(mPhotoFile);
                listicon.add(iconFoto);
                imageAdapter.notifyDataSetChanged();
            }
            getLastLocation();
            /*lokasi = null;
            gambarl = null;
            gambaricon =null;
            if(listgambar!=null) {
                for(int i = 0; i<listgambar.size();i++){
                    if(gambarl==null){
                        gambarl = listgambar.get(i).getPath();
                        gambaricon = listicon.get(i).getPath();
                        if(listlokasi.size()!=0) {
                            lokasi = listlokasi.get(i);
                        }else{
                            lokasi = "0km0";
                        }
                    }else{
                        gambarl = gambarl+","+listgambar.get(i).getPath();
                        gambaricon = gambaricon+","+listicon.get(i).getPath();

                        if(listlokasi.size()!=0) {
                            lokasi = lokasi +","+ listlokasi.get(i);
                        }else{
                            lokasi = lokasi +","+ "0km0";
                        }

                    }
                }

                Toast.makeText(context, gambarl +"\n"+gambaricon +"\n"+lokasi +"\n", Toast.LENGTH_SHORT).show();

                if (listgambar.size() == 5) {
                    fotoButton.setVisibility(View.GONE);
                } else {
                    fotoButton.setVisibility(View.VISIBLE);
                }

                // Toast.makeText(context, gambarl, Toast.LENGTH_SHORT).show();
            }*/
            if (listgambar.size() == 5) {
                fotoButton.setVisibility(View.GONE);
            } else {
                fotoButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private void requestStoragePermission(final boolean isCamera) {
        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                //.withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
       /* builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());*/
        builder.show();

    }

    private File createImageFile() throws IOException {
        //String posisil = getActivity().getIntent().getExtras().get("judul").toString();
        String posisiku = letak;
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String detail = letak+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(sawal)+"-"+String.valueOf(sakhir)+"_"+timeStamp;
        String mFileName = "Lane_" + detail;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+"Lane"+File.separator+posisiku);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            checkLocation();
            getLastLocation();
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        final String[] latlong = {null};

        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            // Toast.makeText(PilihLokasi.this, String.valueOf(mLastLocation.getLatitude()),Toast.LENGTH_SHORT).show();
                            String latitude = String.valueOf(mLastLocation.getLatitude());
                            String longtitude = String.valueOf( mLastLocation.getLongitude());
                            latlong[0] = latitude+"km"+longtitude;
                            listlokasi.add(latlong[0]);

                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));

                        }
                    }
                });

    }


    private void showSnackbar(final String text) {
        View container = view.findViewById(R.id.map);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(view.findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
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
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @Override
    public void onConnected(Bundle bundle) {


        // startLocationUpdates();

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        Location mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(getActivity(), "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {
        mGoogleApiClient.disconnect();
    }
    

}
