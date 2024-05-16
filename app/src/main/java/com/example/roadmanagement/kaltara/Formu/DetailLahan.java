package com.example.roadmanagement.kaltara.Formu;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.BuildConfig;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataSpinner;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.api.Apiku;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.HelperList;
import com.example.roadmanagement.kaltara.helper.ImageHelper;
import com.example.roadmanagement.kaltara.helper.PermissionHelper;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.helper.SpinnerHelper;
import com.example.roadmanagement.kaltara.sinkronForm.SinkronUtama;
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

import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class DetailLahan extends Fragment implements  AdapterView.OnItemSelectedListener{

    private String params;
    private int index;
    private static final String TAG = DetailMedian.class.getSimpleName();
    /*private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;*/
    String lokasi;
    List<String> listlokasi = new ArrayList<>();

    TextView judul;
    Spinner tipeLahan;
    Spinner tagunLahan;


    ArrayList<DataSpinner> listlahan;
    ArrayList<DataSpinner> listtagun;
    SpinnerHelper spinnerHelper;
    SpinnerHelper spinnerHelper2;
    RecyclerView recyclerView;
    ArrayList<File> listgambar = new ArrayList<>();
    ArrayList<File> listicon = new ArrayList<>();
    ImageAdapter imageAdapter;
    private ImageView buttonGambar;
    EditText tinggilahan;
    DbLahan dbLahan;
    DbTemporari dbtemporari;
    Button save;
    String gambarl;
    String gambaricon;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    File iconFoto;
    Session session;
    // FileCompressor mCompressor;
    ImageHelper imageHelper;
    DataLahan dataLahan;
    String letak;
    Retrofit retrofit;
    Apiku apiku;

    FungsiAPI fungsiAPI;

    String namafile = "Lahan";

    TextView titleUnform;

    LocationManager locationManager;

    String posisi;
    String posisiData;

    /*private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    //private long FASTEST_INTERVAL = 2000; /* 2 sec */


    ProgressDialog dialog;

    DataLahan dataTemporariLahan;

    HelperList helperList;




    // newInstance constructor for creating fragment with arguments
    public static DetailLahan newInstance(int index, String params, String posisi) {
        DetailLahan fragmentFirst = new DetailLahan();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putString("parameter", params);
        args.putString("posisi", posisi);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt("index", 0);
        params = getArguments().getString("parameter");
        posisi = getArguments().getString("posisi");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.unform_lahan, container, false);
        tagunLahan = view.findViewById(R.id.unformlahantataguna);
        tipeLahan = view.findViewById(R.id.unformlahantipe);
        buttonGambar = view.findViewById(R.id.unformlahanfoto);
        save = view.findViewById(R.id.unformlahanbutton);
        tinggilahan = view.findViewById(R.id.unformlahantinggi);
        recyclerView = view.findViewById(R.id.unformlahanrecycle);
        titleUnform = view.findViewById(R.id.unformlahanjudul);

        titleUnform.setText("Lahan "+posisi);

        imageHelper = new ImageHelper(getActivity());
        session = new Session(getActivity());

        apiku = new Apiku(getActivity());
        retrofit = apiku.initRetrofit();
        fungsiAPI =  new FungsiAPI(getActivity());

        dbLahan = new DbLahan(getActivity());
        dbtemporari= new DbTemporari(getActivity());

        listlahan = new ArrayList<>();
        listtagun = new ArrayList<>();

        initDataLahan();
        initDataTagun();

        helperList = new HelperList();

        posisiData = posisi;



        if(params.equals("Sinkron")||params.equals("Recycle")){
            dataLahan = dbLahan.getLahanUn(session.getKodeprov(), session.getNoruas(), index);
            dataTemporariLahan = dbLahan.getLahanUn(session.getKodeprov(), session.getNoruas(), index);
            setValueLahan(dataLahan);

        }else{
            dataLahan = new DataLahan(session.getKodeprov(), session.getNoruas(), index, index, posisiData, null, null,  0, 0, null, null, null );
            dataTemporariLahan = new DataLahan(session.getKodeprov(), session.getNoruas(), index, index, posisiData, null, null,  0, 0, null, null, null );
        }

       // Toast.makeText(getContext(),  posisiData, Toast.LENGTH_SHORT).show();





       // mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        imageAdapter = new ImageAdapter(listgambar, getActivity(), new SendId() {
            @Override
            public void hapusGambar(int id) {

                listgambar.remove(id);
                listicon.remove(id);
                listlokasi.remove(id);

                if(listgambar.size()==5){
                    buttonGambar.setVisibility(View.GONE);
                }else {
                    buttonGambar.setVisibility(View.VISIBLE);
                }
                imageAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);

        buttonGambar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo", "Choose from Library",
                        "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {



                            /*if (!checkPermissions()) {
                                requestPermissions();
                            } else if(checkLocation()){
                                requestStoragePermission(true);
                            }*/


                        } else if (items[item].equals("Choose from Library")) {
                            requestStoragePermission(false);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String pss;
                float lahan;


                if(tinggilahan.getText().toString().equals(null)||tinggilahan.getText().toString().equals("")){
                    lahan= Float.parseFloat("0");
                }else{
                    lahan=Float.parseFloat(tinggilahan.getText().toString());
                }

                dataLahan.setTinggiLahan(lahan);
                 //Toast.makeText( getContext(), String.valueOf(dataLahan.getTinggiLahan()), Toast.LENGTH_SHORT).show();

                dataLahan.setGambarLahan(helperList.getNamaImage(listgambar));
                dataLahan.setIcongambar(helperList.getNamaImage(listicon));
                dataLahan.setLokasilahan(helperList.getLokasi(listlokasi));

                if(dataTemporariLahan!=dataLahan) {
                    dbLahan.setLahanUn(dataLahan);
                    setValueTemporari("Lahan", posisi);
                }

                Intent i;
                if(params.equals("Sinkron")){
                    i = new Intent(getActivity(), SinkronUtama.class);
                }else{
                    String indexPosisi;
                    if(posisi.equals("kiri")){
                        indexPosisi = "1";
                    }else{
                        indexPosisi ="2";
                    }

                    i = new Intent(getActivity(), UnForm.class);
                    i.putExtra("tipe", "1");
                    i.putExtra("posisi", indexPosisi);
                }
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);



            }
        });

        /*mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        mGoogleApiClient.connect();*/

        return view;
    }


    public void initDataLahan(){
        String text[] = getResources().getStringArray(R.array.tipe_lahan);
        String value[] = getResources().getStringArray(R.array.tipe_lahan);
        listlahan.clear();
        for (int i = 0; i < text.length; i++) {
            listlahan.add(new DataSpinner(value[i], text[i]));
        }

        spinnerHelper = new SpinnerHelper(getActivity(), R.layout.singlespinner, R.id.dlahantipe, listlahan);
        spinnerHelper.setDropDownViewResource(R.layout.singlespinner);
        tipeLahan.setAdapter(spinnerHelper);
        tipeLahan.setOnItemSelectedListener(this);
    }

    public void initDataTagun(){
        String text2[]=getResources().getStringArray(R.array.tatagunaLahan);
        String value2[]=getResources().getStringArray(R.array.valuetagunlahan);
        for (int i=0; i<text2.length; i++){
            listtagun.add(new DataSpinner(value2[i], text2[i]));
        }
        spinnerHelper2 = new SpinnerHelper(getActivity(), R.layout.singlespinner, R.id.dlahantguna, listtagun);
        spinnerHelper2.setDropDownViewResource(R.layout.singlespinner);
        tagunLahan.setAdapter(spinnerHelper2);
        tagunLahan.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.unformlahantipe) {
            if(listlahan.get(position).getTextview().equals("--Pilih--")){
                dataLahan.setTipeLahan(null);
            }else {
                dataLahan.setTipeLahan(listlahan.get(position).getTextview());
            }
        }else if (parent.getId() == R.id.unformlahantataguna){
            if(listtagun.get(position).getTextview().equals("--Pilih--")) {
                dataLahan.setTatagunaLahan(null);
            }else {
                dataLahan.setTatagunaLahan(listtagun.get(position).getTextview());
            }
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
            String a = imageHelper.setNamathumbnailUn(namafile, index, posisi, listgambar.size());
            String nama = imageHelper.setNamaFileUn(namafile,index,posisi, listgambar.size());
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {

                    mPhotoFile = imageHelper.saveFromCamera(mPhotoFile);
                    // mPhotoFile.renameTo(new File(mPhotoFile.getParent(), nama));
                    //mPhotoFile = imageHelper.saveBitmapToFile(mPhotoFile, nama);
                    //Toast.makeText(context, mPhotoFile.getName(), Toast.LENGTH_SHORT).show();
                    iconFoto = imageHelper.saveIcon(mPhotoFile, a);

                    // Toast.makeText(context,a, Toast.LENGTH_SHORT ).show();
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
                    iconFoto = imageHelper.saveIcon(mPhotoFile, a);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listgambar.add(mPhotoFile);
                listicon.add(iconFoto);
                imageAdapter.notifyDataSetChanged();
            }
            //getLastLocation();
            /*lokasi = null;
            gambarl = null;
            if(listgambar!=null) {
                for (int i = 0; i < listgambar.size(); i++) {
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
                            lokasi = lokasi + "," + listlokasi.get(i);
                        }else{
                            lokasi = lokasi+","+"0km0";
                        }
                    }
                }
                dataLahan.setGambarLahan(gambarl);
                dataLahan.setIcongambar(gambaricon);
                dataLahan.setLokasilahan(lokasi);
                if (listgambar.size() == 5) {
                    buttonGambar.setVisibility(View.GONE);
                } else {
                    buttonGambar.setVisibility(View.VISIBLE);
                }
            }*/

            if (listgambar.size() == 5) {
                buttonGambar.setVisibility(View.GONE);
            } else {
                buttonGambar.setVisibility(View.VISIBLE);
            }
        }
    }


    public void setValueLahan(DataLahan dataLahan){
        tinggilahan.setText(String.valueOf(dataLahan.getTinggiLahan()));
        tipeLahan.setSelection(getIndex(listlahan, dataLahan.getTipeLahan()));
        tagunLahan.setSelection(getIndex(listtagun, dataLahan.getTatagunaLahan()));
        setLokasi(dataLahan.getLokasilahan());
        getImage(dataLahan.getGambarLahan());
        getIcon(dataLahan.getIcongambar());
    }

    private int getIndex(ArrayList<DataSpinner> dataSpinners, String myString){

        int index = 0;

        for (int i=0;i<dataSpinners.size();i++){
            if (dataSpinners.get(i).getTextview().equals(myString)){
                index = i;
            }
        }
        return index;
    }

    private void getImage(String string){

        if(string!=null) {
            String[] gambarku = string.split(",");
            for (int i = 0; i < gambarku.length; i++) {
                File fileku = new File(gambarku[i]);
                listgambar.add(fileku);
                if (gambarl == null) {
                    gambarl = gambarku[i];
                } else {
                    gambarl = gambarl + "," + gambarku[i];
                }
            }
        }
    }

    private void getIcon(String string){
        if(string!=null) {
            String[] gambarku = string.split(",");
            for (int i = 0; i < gambarku.length; i++) {
                File fileku = new File(gambarku[i]);
                listicon.add(fileku);
                if (gambaricon == null) {
                    gambaricon = gambarku[i];
                } else {
                    gambaricon = gambaricon + "," + gambarku[i];
                }
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
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String detail = posisi+"_Und_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment())+"_"+timeStamp;
        String mFileName = "Lahan_" + detail;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+"Lahan"+File.separator+posisi.substring(0,1));
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        //File mFile = new File(storageDir, mFileName);
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

    public void setValueTemporari(String tipe, String letak){
        String tempofoto;
        if(dataLahan.getGambarLahan()==null){
            tempofoto="0";
        }else{
            tempofoto="1";
        }

        DataTemporari dataTemporarip = new DataTemporari(session.getKodeprov(), session.getNoruas(), index, index, tipe, letak, "", "Unidentified", index, index, tempofoto, "0");
        dbtemporari.postTemporari(dataTemporarip);
    }

    private void setLokasi(String string){
        if(string!=null) {
            String[] lokasiku = string.split(",");
            for (int i = 0; i < lokasiku.length; i++) {
                listlokasi.add(lokasiku[i]);
                if (lokasi == null) {
                    lokasi = lokasiku[i];
                } else {
                    lokasi = lokasi + "," + lokasiku[i];
                }
            }
        }
    }


    /*@Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
            checkLocation();
        }
    }*/

    //@SuppressWarnings("MissingPermission")
    /*private void getLastLocation() {
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
                            //showSnackbar("tidak ada koneksi");

                        }
                    }
                });

    }


    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
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
    }*/


}