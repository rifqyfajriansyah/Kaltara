package com.example.roadmanagement.kaltara.Dokumen;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.BuildConfig;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SendId;

import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.helper.Session;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DokumenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Session session;
    Spinner spinner;
    TextView tnoprov, judul;
    CardView bdokumensingle, bdokumenall;

    DbRuas dbRuas;
    String ruasnya, noprov, tipeLogin, segment, subseg, posisi;

    List<String> listSegment = new ArrayList<>();
    List<String> listSubseg = new ArrayList<>();
    List<String> listPosisi = new ArrayList<>();
    List<String> list = new ArrayList<>();

    Spinner spSegment, spSubseg, spPosisi;
    DbSpinner dbSpinner;
    CardView bDownload, bRuasDownload;

    private ProgressDialog pDialog;
    private static final int progress_bar_type = 0;

    String url_dokumen = "https://www.road-dcm.com/apiandrobaru/dokumenku/rniDownload.php?noprov=";
    String uri = "https://www.road-dcm.com/apiandrobaru/dokumenku/";

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokumen);

        spinner = findViewById(R.id.downloadruas);
        bdokumensingle = findViewById(R.id.downloaddokumensingle);
        bdokumenall = findViewById(R.id.downloaddokumenall);
        tnoprov = findViewById(R.id.judulprovinsi);
        judul = findViewById(R.id.judultoolbar);

        spSegment = findViewById(R.id.downloadsegment);
        spSubseg = findViewById(R.id.downloadsubseg);
        spPosisi = findViewById(R.id.downloadposisi);
        bDownload = findViewById(R.id.downloaddokumenDrainase);
        bRuasDownload = findViewById(R.id.downloadRuasDrainase);

        context = DokumenActivity.this;

        judul.setText("Download Dokumen");
        session = new Session(this);

        if(session.getUserTipe()==0){
            tipeLogin = "Android";
        }else{
            tipeLogin = "Drainase";

        }
        dbRuas = new DbRuas(this);

        dbSpinner = new DbSpinner(context);

        tnoprov.setText(session.getKodeprov());


        noprov = session.getKodeprov();

        getApplicationContext().getExternalFilesDir("RoadManagement");

        /*File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "RoadManagement");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        */

        setDirectory("");

        initRuas();


        bdokumensingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ruasnya.equals("--Pilih ruas--")){
                    if(!checkPermissions()){
                        requestStoragePermission();
                    }else {

                        String url = uri+"rniDownload.php?noprov="+noprov+"&noruas="+ruasnya+"&jenis="+tipeLogin;
                        String pathFile = "Dokumen/"+noprov+"/"+noprov+"."+ruasnya+".xlsx";

                        String folderku = "Dokumen/"+noprov;
                        setDirectory(folderku);

                        new DownloadFile(pathFile).execute(url);

                        //String url_dokumen_single = url_dokumen + noprov + "&noruas=" + ruasnya+"&jenis="+tipeLogin;
                        //new DownloadSingleDokumen().execute(url_dokumen_single);
                    }
                }
            }
        });




        bdokumenall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(!checkPermissions()){
                    requestStoragePermission();
                }else {
                    DownloadAll(1);
                }

            }
        });

        bDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context, noprov+"-"+ruasnya+"-"+segment+"."+subseg+"-"+posisi, Toast.LENGTH_SHORT).show();
                //String url = "https://estitools.com/apiandrobaru/dokumenku/rniDrainSingle.php?noprov="+noprov+"&noruas="+ruasnya+"&subseg="+segment+"."+subseg+"&posisi="+posisi;

                String url = "https://www.road-dcm.com/apiandrobaru/dokumenku/rniDrainSingle.php?noprov="+noprov+"&noruas="+ruasnya+"&noseg="+segment+"&posisi="+posisi;

                if(!subseg.equals("--Pilih--")){
                    if(!checkPermissions()){
                        requestStoragePermission();
                    }else {
                        new DownloadDrainase().execute(url);
                    }
                }


            }
        });

        bRuasDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://www.road-dcm.com/apiandrobaru/dokumenku/rniDrainRuas.php?noprov="+noprov+"&noruas="+ruasnya+"&posisi="+posisi;

                if(!posisi.equals("--Pilih--")){
                    if(!checkPermissions()){
                        requestStoragePermission();
                    }else {
                        new DownloadRuasDrainase().execute(url);
                    }
                }

            }
        });

    }

    public void initRuas(){

        list = dbRuas.getRuas(String.valueOf(session.getKodeprov()));
        String[] spinnerArray = new String[list.size()];
        list.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (DokumenActivity.this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(DokumenActivity.this);

    }

    public void initSegment(String ruas) {

        listSegment.clear();
        if (!ruas.equals("--Pilih ruas--")){
            listSegment = dbSpinner.getSegment(session.getKodeprov(), ruas);
            if(listSegment.size()==0){
                listSegment.add("--Pilih--");
            }
        }else{
            listSegment.add("--Pilih--");
        }

        String[] spinnerArray = new String[listSegment.size()];
        listSegment.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (DokumenActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spSegment.setAdapter(spinnerArrayAdapter);
        spSegment.setOnItemSelectedListener(DokumenActivity.this);

    }

    public void initSubseg(String segment){


        listSubseg.clear();

        if (!segment.equals("--Pilih--")){
            listSubseg = dbSpinner.getSubSegment(session.getKodeprov(), ruasnya, segment);
            if(listSubseg.size()==0){
                listSubseg.add("0");
            }
        }else{
            listSubseg.add("--Pilih--");
        }

        String[] spinnerArray = new String[listSubseg.size()];
        listSubseg.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (DokumenActivity.this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spSubseg.setAdapter(spinnerArrayAdapter);
        spSubseg.setOnItemSelectedListener(DokumenActivity.this);

    }

    public void initPosisi(String ruas){

        listPosisi.clear();


        if (!ruas.equals("--Pilih ruas--")){

            listPosisi.add("kiri");
            listPosisi.add("kanan");

        }else{

            listPosisi.add("--Pilih--");
        }

        String[] spinnerArray = new String[listPosisi.size()];
        listPosisi.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (DokumenActivity.this, android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spPosisi.setAdapter(spinnerArrayAdapter);
        spPosisi.setOnItemSelectedListener(DokumenActivity.this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId()==R.id.downloadruas) {
            ruasnya = list.get(position);
            initSegment(ruasnya);
            initPosisi(ruasnya);
        }else if(parent.getId()==R.id.downloadsegment){
            segment = listSegment.get(position);
            initSubseg(segment);
        }else if(parent.getId()==R.id.downloadsubseg){
            subseg = listSubseg.get(position);
        }else if(parent.getId()==R.id.downloadposisi){
            posisi = listPosisi.get(position);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    protected Dialog onCreateDialog(int id){
        switch (id){
            case progress_bar_type:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please Wait");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    public void DownloadAll(int i){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Download All Data");
        dialog.setMessage(list.get(i));
        dialog.show();
        String file_urlaneall;

        file_urlaneall = url_dokumen + noprov + "&noruas=" + list.get(i);

        DownloadAllDokumen downloadAllDokumen = new DownloadAllDokumen(list.get(i), i, new SendId() {
            @Override
            public void hapusGambar(int id) {
                if(id!=list.size()-1){
                    dialog.dismiss();
                    DownloadAll(id+1);
                }else{
                    dialog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(getWindow().getDecorView().getRootView(), "Download Berhasil", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Open", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    Uri uri = Uri.parse("/sdcard//Dokumen/RoadManagement/Dokumen/"+noprov); // a directory
                                    intent.setDataAndType(uri, "*/*");
                                    startActivity(Intent.createChooser(intent, "Open folder"));
                                }
                            });

                    snackbar.show();
                }
            }
        });

        downloadAllDokumen.execute(file_urlaneall);
    }




//Download Single Android
    class DownloadSingleDokumen extends AsyncTask<String, String, String> {


    public DownloadSingleDokumen(){


    }

        protected  void onPreExecute(){
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int lenghOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"Dokumen/RoadManagement"+File.separator+"Dokumen"+File.separator+noprov);
                directory.mkdirs();

               // OutputStream output = new FileOutputStream("/sdcard//RoadManagement/Dokumen/"+noprov+"/"+noprov+"."+ruasnya+".xlsx");
                OutputStream output = new FileOutputStream("/sdcard//Dokumen/RoadManagement/Dokumen/"+noprov+"/"+noprov+"."+ruasnya+".xlsx");

                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1){
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e){
                Log.e("Error : ", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            //pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        protected void onPostExecute(String file_url){
            dismissDialog(progress_bar_type);

            Snackbar snackbar = Snackbar
                    .make(getWindow().getDecorView().getRootView(), "Download Berhasil", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Open File", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            //Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+ File.separator+"RoadManagement"+File.separator+"Dokumen"+File.separator+noprov+File.separator+noprov+"."+ruasnya+".xlsx"); // a directory
                            File coba = new File(Environment.getExternalStorageDirectory()+ File.separator+"Dokumen/RoadManagement"+File.separator+"Dokumen"+File.separator+noprov+File.separator+noprov+"."+ruasnya+".xlsx");
                            //Uri uri = Uri.fromFile(coba);
                            Uri uri = FileProvider.getUriForFile(DokumenActivity.this, BuildConfig.APPLICATION_ID+".provider", coba);
                            intent.setDataAndType(uri, "application/vnd.ms-excel");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);



                            //Log.d("COBA", String.valueOf(uri));
                            startActivity(Intent.createChooser(intent, "Open file"));


                        }
                    });

            snackbar.show();
        }
    }


    class DownloadAllDokumen extends AsyncTask<String, String, String> {


        String ruaspilih;
        int index;
        SendId sendId;

        public DownloadAllDokumen(String ruaspilih, int index, SendId id){
            this.ruaspilih = ruaspilih;
            this.index = index;
            this.sendId = id;

        }

        protected  void onPreExecute(){
            super.onPreExecute();
           // showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int lenghOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);


                File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"Dokumen/RoadManagement"+File.separator+"Dokumen"+File.separator+noprov);
                directory.mkdirs();

                OutputStream output = new FileOutputStream("/sdcard//Dokumen/RoadManagement/Dokumen/"+noprov+"/"+noprov+"."+ruaspilih+".xlsx");

                //OutputStream output = new FileOutputStream("/sdcard//Download/"+noprov+"."+ruaspilih+".xlsx");
                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1){
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e){
                Log.e("Error : ", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            //pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        protected void onPostExecute(String file_url){
           // dismissDialog(progress_bar_type);

            sendId.hapusGambar(index);
        }
    }



    class DownloadDrainase extends AsyncTask<String, String, String> {


        public DownloadDrainase(){

        }

        protected  void onPreExecute(){
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int lenghOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"Dokumen/RoadManagement"+File.separator+"Dokumen Drainase"+File.separator+noprov+File.separator+ruasnya+File.separator+posisi);
                directory.mkdirs();

                OutputStream output = new FileOutputStream("/sdcard//Dokumen/RoadManagement/Dokumen Drainase/"+noprov+"/"+ruasnya+"/"+posisi+"/"+noprov+"-"+ruasnya+"-"+segment+"."+subseg+"-"+posisi+".xlsx");

                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1){
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e){
                Log.e("Error : ", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            //pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        protected void onPostExecute(String file_url){
            dismissDialog(progress_bar_type);

            Snackbar snackbar = Snackbar
                    .make(getWindow().getDecorView().getRootView(), "Download Berhasil", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Open File", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            //Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+ File.separator+"RoadManagement"+File.separator+"Dokumen"+File.separator+noprov+File.separator+noprov+"."+ruasnya+".xlsx"); // a directory
                            File coba = new File(Environment.getExternalStorageDirectory()+ File.separator+"Dokumen/RoadManagement"+File.separator+"Dokumen Drainase"+File.separator+noprov+File.separator+ruasnya+ File.separator+posisi+File.separator+
                                    noprov+"-"+ruasnya+"-"+segment+"."+subseg+"-"+posisi+".xlsx");
                            //Uri uri = Uri.fromFile(coba);
                            Uri uri = FileProvider.getUriForFile(DokumenActivity.this, BuildConfig.APPLICATION_ID+".provider", coba);
                            intent.setDataAndType(uri, "application/vnd.ms-excel");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);



                            //Log.d("COBA", String.valueOf(uri));
                            startActivity(Intent.createChooser(intent, "Open file"));


                        }
                    });

            snackbar.show();
        }
    }

    class DownloadRuasDrainase extends AsyncTask<String, String, String> {


        public DownloadRuasDrainase(){

        }

        protected  void onPreExecute(){
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int lenghOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"Dokumen/RoadManagement"+File.separator+"Dokumen Drainase"+File.separator+noprov);
                directory.mkdirs();

                OutputStream output = new FileOutputStream("/sdcard//Dokumen/RoadManagement/Dokumen Drainase/"+noprov+"/"+ruasnya+"/Drainase-"+noprov+"-"+ruasnya+"-"+posisi+".xlsx");

                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1){
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e){
                Log.e("Error : ", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            //pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        protected void onPostExecute(String file_url){
            dismissDialog(progress_bar_type);

            Snackbar snackbar = Snackbar
                    .make(getWindow().getDecorView().getRootView(), "Download Berhasil", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Open File", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            //Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+ File.separator+"RoadManagement"+File.separator+"Dokumen"+File.separator+noprov+File.separator+noprov+"."+ruasnya+".xlsx"); // a directory
                            File coba = new File(Environment.getExternalStorageDirectory()+ File.separator+"Dokumen/RoadManagement"+File.separator+"Dokumen Drainase"+File.separator+noprov+File.separator+ruasnya+File.separator+
                                   "Drainase-"+ noprov+"-"+ruasnya+"-"+posisi+".xlsx");
                            //Uri uri = Uri.fromFile(coba);
                            Uri uri = FileProvider.getUriForFile(DokumenActivity.this, BuildConfig.APPLICATION_ID+".provider", coba);
                            intent.setDataAndType(uri, "application/vnd.ms-excel");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);



                            //Log.d("COBA", String.valueOf(uri));
                            startActivity(Intent.createChooser(intent, "Open file"));


                        }
                    });

            snackbar.show();
        }
    }




    private void requestStoragePermission() {

        DexterBuilder.MultiPermissionListener dexter;

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            dexter = Dexter.withActivity((Activity) context).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
            //Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            dexter.withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            // check if all permissions are granted
                            if (report.areAllPermissionsGranted()) {

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
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.show();

    }

    private boolean checkPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return true;
        }else{
            int permissionState = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return permissionState == PackageManager.PERMISSION_GRANTED;

        }
    }


    class DownloadFile extends AsyncTask<String, String, String> {

        String pathFile;

        public DownloadFile(String pathFile){
            this.pathFile = pathFile;
        }

        protected  void onPreExecute(){
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {

            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int lenghOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);


                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_DOCUMENTS+File.separator+"RoadManagement"+File.separator+pathFile);

                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1){
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e){
                Log.e("Error : ", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        protected void onPostExecute(String file_url){
            dismissDialog(progress_bar_type);

            Snackbar snackbar = Snackbar
                    .make(getWindow().getDecorView().getRootView(), "Download Berhasil", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Open File", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent intent = new Intent(Intent.ACTION_VIEW);
                             File coba = new File(Environment.getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_DOCUMENTS+File.separator+"RoadManagement"+File.separator+pathFile);
                            Uri uri = FileProvider.getUriForFile(DokumenActivity.this, BuildConfig.APPLICATION_ID+".provider", coba);
                            intent.setDataAndType(uri, "application/vnd.ms-excel");
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(Intent.createChooser(intent, "Open file"));

                        }
                    });

            snackbar.show();
        }
    }

    private void setDirectory(String pathDokumen){

        File directory = new File(Environment.getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_DOCUMENTS+File.separator+"RoadManagement"+File.separator+pathDokumen);

        if (!directory.exists()) {
            directory.mkdirs();
        }

    }


}
