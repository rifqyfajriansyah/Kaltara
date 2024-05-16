package com.example.roadmanagement.kaltara.helper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.example.rifqy.kaltara.BuildConfig;
import com.example.roadmanagement.kaltara.CameraLoc.CameraPreview;
import com.example.roadmanagement.kaltara.CameraLoc.Cameraku;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PermissionImage {

    private Context context;

    public PermissionImage(Context context){

        this.context =context;

    }

    public Intent getFotoCamera(String nama, File folder) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(nama, folder);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra("Fileku", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            }
        }
        return takePictureIntent;
    }

    public Intent getFotoLokasi(Context context, String fullNama, String lokasi){

        Intent i = new Intent(context, Cameraku.class);
        i.putExtra("namafile", fullNama);
        i.putExtra("kmlokasi", lokasi);

        return i;
    }

    public Intent getFotoGaleri() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        return pickPhoto;
        //((Activity)context).startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    private File createImageFile(String namaFile, File Directory) throws IOException {

        File mFile = File.createTempFile(namaFile, ".jpg", Directory);
        return mFile;
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
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

    public String getNamaFile(String tipe, String provinsi, String ruas, String segment, String subsegment, String posisi){

        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String namaFile;

        String kodePosisi;


        if(tipe.equals("Median")){

            namaFile = tipe+"_"+provinsi+"."+ruas+"."+segment+"."+subsegment+"_"+timeStamp;

        }else {

            if (posisi.equals("kiri")) {
                kodePosisi = "Left";
            } else if (posisi.equals("kanan")) {
                kodePosisi = "Right";
            } else {
                kodePosisi = posisi;
            }


            namaFile = tipe + "_" + kodePosisi + "_" + provinsi + "." + ruas + "."+segment+"."+ subsegment + "_" + timeStamp;
        }

        return namaFile;

    }

    /*public File getFolderFile(String tipe, String provinsi, String ruas, String posisi){

        String kodePosisi;
        File storageDir;


        if(tipe.equals("Median")){
            storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+provinsi+File.separator+ruas+File.separator+tipe);
        }else {

            if (posisi.equals("kiri")) {
                kodePosisi = "L";
            } else if (posisi.equals("kanan")) {
                kodePosisi = "R";
            } else {
                kodePosisi = posisi;
            }

            storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+provinsi+File.separator+ruas+File.separator+tipe+File.separator+kodePosisi);
        }

        return storageDir;

    }*/

    public File getFolderFile(String tipe, String provinsi, String ruas, String posisi){

        String kodePosisi;
        //File storageDir;

        String storageDir;


        if(tipe.equals("Median")){
            storageDir = Environment.getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_DOCUMENTS+File.separator+"RoadManagement"+File.separator+"Gambar"+File.separator
                    +provinsi+File.separator+ruas+File.separator+tipe;
        }else {

            if (posisi.equals("kiri")) {
                kodePosisi = "L";
            } else if (posisi.equals("kanan")) {
                kodePosisi = "R";
            } else {
                kodePosisi = posisi;
            }

            storageDir = Environment.getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_DOCUMENTS+File.separator+"RoadManagement"+File.separator+"Gambar"+File.separator
                    +provinsi+File.separator+ruas+File.separator+tipe+File.separator+kodePosisi;
            //storageDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+provinsi+File.separator+ruas+File.separator+tipe+File.separator+kodePosisi);
        }

        File directory = new File(storageDir);
        directory.mkdirs();

        return directory;

    }

    public String getFullNameImage(String namaFile, File Directory, int urutan){
        String urut = String.valueOf(urutan);
        return String.valueOf(Directory)+File.separator+namaFile+urut+".jpg";

    }

    public String getThumbnailName(String namaFile, File Directory, int urutan){

        String urut = String.valueOf(urutan);

        return String.valueOf(Directory)+File.separator+namaFile+"_thumbnail"+urut+".jpg";

    }

}
