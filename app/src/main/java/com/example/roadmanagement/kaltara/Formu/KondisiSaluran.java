package com.example.roadmanagement.kaltara.Formu;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class KondisiSaluran extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}/*implements AdapterView.OnItemSelectedListener {

    TextView judulkondisi;
    EditText panjangRusak;
    String letak;
    Context context;
    Session session;

    Spinner kerusakansaluran;
    ArrayList<DataSpinner> listsaluran = new ArrayList<>();
    SpinnerHelper spinnerHelper;

    RecyclerView recyclerView;
    ArrayList<File> listgambar = new ArrayList<>();
    ArrayList<File> listicon = new ArrayList<>();
    ImageAdapter imageAdapter;
    ImageView buttonGambar;
    String gambarl;
    String gambaricon;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    File iconFoto;

    String namafile = "Kondisi_Saluran";
    ImageHelper imageHelper;

    int cek;
    DbSaluran dbHelper;
    Button save;
    DataSaluran dataSaluran;

    TextView tipesaluran;
    TextView dalamsaluran;
    TextView lebarsaluran;

    String posisi;

    DbTemporari dbtemporari;

    TextView staku;
    TextView kmku;
    TextView dsegment;
    TextView druas;
    TextView dprovinsi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kondisi_saluran);
        judulkondisi = findViewById(R.id.kondisisaluranjudul);
        kerusakansaluran = findViewById(R.id.kondisisalurankerusakan);
        panjangRusak = findViewById(R.id.kondisisaluranpanjang);
        recyclerView = findViewById(R.id.kondisisaluranrecycle);
        buttonGambar = findViewById(R.id.kondisisalurantambahfoto);
        save = findViewById(R.id.kondisisaluranbutton);
        dalamsaluran = findViewById(R.id.kondisisalurandalam);
        lebarsaluran = findViewById(R.id.kondisisaluranlebar);
        tipesaluran = findViewById(R.id.kondisisalurantipe);

        context = KondisiSaluran.this;
        session = new Session(this);
        imageHelper = new ImageHelper(context);
        dbHelper = new DbSaluran(this);

        dbtemporari = new DbTemporari(this);



        letak = getIntent().getExtras().get("judul").toString();
        judulkondisi.setText("Kondisi Saluran "+ letak);


        if(letak.equals("Left")){
            posisi = "kiri";
        }else{
            posisi = "kanan";
        }


        initDataSaluran();

        dataSaluran = dbHelper.getSaluran(session.getKodeprov(), session.getNoruas(), session.getNosegment(), posisi);
        //Toast.makeText(context, dataSaluran.getKerusakanSaluran(), Toast.LENGTH_SHORT).show();
        if(null != dataSaluran){
            setValueSaluran(dataSaluran);
            cek = 0;
        }else{
         finish();
        }

        imageAdapter = new ImageAdapter(listgambar, context, new SendId() {
            @Override
            public void hapusGambar(int id) {
                listgambar.remove(id);
                listicon.remove(id);
                gambarl = null;
                gambaricon=null;
                for(int i = 0; i<listgambar.size();i++){
                    if(gambarl==null){
                        gambarl = listgambar.get(i).getPath();
                        gambaricon = listicon.get(i).getPath();
                    }else{
                        gambarl = gambarl+","+listgambar.get(i).getPath();
                        gambaricon = gambaricon+","+listicon.get(i).getPath();
                    }
                }
                dataSaluran.setGambarKr(gambarl);
                dataSaluran.setGambarKricon(gambaricon);
                if(listgambar.size()==5){
                    buttonGambar.setVisibility(View.GONE);
                }else {
                    buttonGambar.setVisibility(View.VISIBLE);
                }
                imageAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);

        buttonGambar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo", "Choose from Library",
                        "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(KondisiSaluran.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {
                            requestStoragePermission(true);
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

                setEdittext();
                Intent i = new Intent(KondisiSaluran.this, DetailSaluran.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("judul", letak);
                startActivity(i);
            }
        });


        dprovinsi = findViewById(R.id.detailprovinsi);
        druas = findViewById(R.id.detailruas);
        dsegment = findViewById(R.id.detailsegment);
        staku = findViewById(R.id.kondisisaluransta);
        kmku = findViewById(R.id.kondisisalurankm);
        staku.setText(session.getSTA());
        kmku.setText(session.getKM());
        dsegment.setText(String.valueOf(session.getNosegment()));
        druas.setText(session.getNoruas());
        dprovinsi.setText(String.valueOf(session.getKodeprov()));
    }

    public void initDataSaluran(){
        String text[] = getResources().getStringArray(R.array.tipe_lahan);
        String value[] = getResources().getStringArray(R.array.tipe_lahan);
        listsaluran.clear();
        for (int i = 0; i < text.length; i++) {
            listsaluran.add(new DataSpinner(value[i], text[i]));
        }

        spinnerHelper = new SpinnerHelper(KondisiSaluran.this, R.layout.singlespinner, R.id.kondisisalurankerusakan, listsaluran);
        spinnerHelper.setDropDownViewResource(R.layout.singlespinner);
        kerusakansaluran.setAdapter(spinnerHelper);
        kerusakansaluran.setOnItemSelectedListener(KondisiSaluran.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(listsaluran.get(position).getTextview().equals("--Pilih--")){
            dataSaluran.setKerusakanSaluran("-");
        }else {
            dataSaluran.setKerusakanSaluran(listsaluran.get(position).getTextview());
        }
        dataSaluran.setKerusakanSaluranvalue(listsaluran.get(position).getValue());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //String a = setNamathumbnail(namafile,listgambar.size());
            String a = imageHelper.setNamathumbnail(namafile, listgambar.size());
            String nama = imageHelper.setNamaFile(namafile,listgambar.size());
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {

                    //mPhotoFile = imageHelper.saveBitmapToFile(mPhotoFile,a);
                    mPhotoFile = imageHelper.saveFromCamera(mPhotoFile);
                    iconFoto = imageHelper.saveIcon(mPhotoFile, a);
                    Toast.makeText(context,a, Toast.LENGTH_SHORT ).show();
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
                   // mPhotoFile = imageHelper.getGallerytoFile(mPhotoFile,nama,a);
                    mPhotoFile = imageHelper.saveFromGaleri(mPhotoFile, nama);
                    iconFoto = imageHelper.saveIcon(mPhotoFile, a);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listgambar.add(mPhotoFile);
                listicon.add(iconFoto);
                imageAdapter.notifyDataSetChanged();
            }

            gambarl = null;
            gambaricon = null;
            if(listgambar!=null) {
                for(int i = 0; i<listgambar.size();i++){
                    if(gambarl==null){
                        gambarl = listgambar.get(i).getPath();
                        gambaricon = listicon.get(i).getPath();
                    }else{
                        gambarl = gambarl+","+listgambar.get(i).getPath();
                        gambaricon = gambaricon+","+listicon.get(i).getPath();
                    }
                }
                dataSaluran.setGambarKr(gambarl);
                dataSaluran.setGambarKricon(gambaricon);
                if (listgambar.size() == 5) {
                    buttonGambar.setVisibility(View.GONE);
                } else {
                    buttonGambar.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void requestStoragePermission(final boolean isCamera) {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
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
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.show();

    }

    private File createImageFile() throws IOException {
        String detail = getIntent().getExtras().get("judul").toString()+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
        String mFileName = "Kondisi_Saluran_" + detail + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+"Kondisi_Saluran");
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
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

    public void setValueSaluran(DataSaluran dataSaluran){
        dalamsaluran.setText(String.valueOf(dataSaluran.getDalamSaluran())+" m");
        lebarsaluran.setText(String.valueOf(dataSaluran.getLebarSaluran())+" m");
        tipesaluran.setText(dataSaluran.getTipeSaluran());
        panjangRusak.setText(String.valueOf(dataSaluran.getPanjangKr()));
        kerusakansaluran.setSelection(getIndex(listsaluran, dataSaluran.getKerusakanSaluranvalue()));
        getImage(dataSaluran.getGambarKr());
        getIcon(dataSaluran.getGambarKricon());
    }

    private int getIndex(ArrayList<DataSpinner> dataSpinners, String myString){

        int index = 0;

        for (int i=0;i<dataSpinners.size();i++){
            if (dataSpinners.get(i).getValue().equals(myString)){
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

    public void setEdittext(){
        if(panjangRusak.getText().toString().equals("")){
            dataSaluran.setPanjangKr(0);
        }else {
            dataSaluran.setPanjangKr(Integer.parseInt(panjangRusak.getText().toString()));
        }

            dbHelper.updateSaluran(dataSaluran);

        setValueTemporari("Saluran", letak, "");

    }

    public void setValueTemporari(String tipe, String letak, String urut){
        DataTemporari dataTemporarip = new DataTemporari(0, session.getKodeprov(), session.getNoruas(), session.getNosegment(), tipe, letak, urut, "0");
        DataTemporari dataTemporari = dbtemporari.getTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), "Saluran", letak, "");
        if(dataTemporari!=null){
            dbtemporari.updateTemporari(dataTemporarip);
        }else{
            dbtemporari.insertTemporari(dataTemporarip);
        }
    }


}*/
