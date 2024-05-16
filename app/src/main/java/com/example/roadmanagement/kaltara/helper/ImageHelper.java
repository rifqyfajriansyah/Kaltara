package com.example.roadmanagement.kaltara.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class ImageHelper {
    Session session;
    Context context;
    Intent intent;
    public ImageHelper(Context context){
        this.context = context;
        session = new Session(context);
        intent = new Intent(String.valueOf(context));
    }

    public File saveBitmapToFile(File file, String string) throws IOException {
        Bitmap rotatedBitmap = null;
        rotatedBitmap = bitmapFactory(file, rotatedBitmap, 75);
        //file.createNewFile();
       // File file1 = new File(string);

        file.createNewFile();
      //  file.renameTo(new File(string));
        //File file1 =new File(file.getParent()+"/"+string);
        FileOutputStream outputStream = new FileOutputStream(file);
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);
        outputStream.close();


        //file.renameTo(file1);

        return file;

    }

    public File saveFromCamera(File file) throws IOException {
        Bitmap rotatedBitmap = null;
        rotatedBitmap = bitmapFactory(file, rotatedBitmap, 75);
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);
        outputStream.close();
        //saveThumbnail(file,string);

        return file;

    }

    public File saveFromGaleri(File file, String namafile) throws IOException {
        Bitmap rotatedBitmap = null;
        OutputStream out;

        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());

        int randomNum = ThreadLocalRandom.current().nextInt(0, 999 + 1);
        String random = String.valueOf(randomNum);
        File filebaru = new File(namafile);
        filebaru.createNewFile();
        out = new FileOutputStream(filebaru);

        rotatedBitmap = bitmapFactory(file, rotatedBitmap, 75);
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , out);
        out.close();

        //saveThumbnail(file,string);

        return filebaru;

    }

    public File getGallerytoFile(File file, String namafile, String string) throws IOException {
        Bitmap rotatedBitmap = null;
        OutputStream out;
        File filebaru = new File(namafile);
        filebaru.createNewFile();
        out = new FileOutputStream(filebaru);
        rotatedBitmap = bitmapFactory(file, rotatedBitmap, 75);
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , out);
        out.close();

        saveThumbnail(file,string);

        return file;

    }

    public File saveIcon(File file, String string) throws IOException {
        Bitmap rotatedBitmap = null;
        OutputStream out;
        File thumbnail = new File(string);
        thumbnail.createNewFile();
        out = new FileOutputStream(thumbnail);
        rotatedBitmap = bitmapFactory(file, rotatedBitmap, 30);
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , out);
        out.close();
        return thumbnail;
    }

    public void saveThumbnail(File file, String string) throws IOException {
        Bitmap rotatedBitmap = null;
        OutputStream out;
        File thumbnail = new File(string);
        thumbnail.createNewFile();
        out = new FileOutputStream(thumbnail);
        rotatedBitmap = bitmapFactory(file, rotatedBitmap, 30);
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , out);
        out.close();
    }

    public Bitmap bitmapFactory(File file, Bitmap bitmap, int value) throws IOException {
        Matrix matrix = new Matrix();
        ExifInterface exif = new ExifInterface(file.getAbsolutePath());
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        try {

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 4;
            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            final int REQUIRED_SIZE = value;

            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }


            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;


            inputStream = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            //Toast.makeText(context, String.valueOf(orientation)+","+String.valueOf(bitmap.getWidth()), Toast.LENGTH_SHORT).show();
            if(orientation==3){
                matrix.postRotate(180);
            }else if (orientation==6){
                matrix.postRotate(90);
            }else{
                matrix.postRotate(360);
            }

            inputStream.close();
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);



            return rotatedBitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public String setNamathumbnail(String string, int i){


        File directory;
        String detail;


        if(string.equals("Median")){
            detail = String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+string);
        }else{

            String posisiku;
            String namaposisi;

            if(((Activity) context).getIntent().hasExtra("judul")) {
                namaposisi = ((Activity) context).getIntent().getExtras().get("judul").toString();
                if (((Activity) context).getIntent().getExtras().get("judul").toString().equals("Left")) {
                    posisiku = "L";
                } else if (((Activity) context).getIntent().getExtras().get("judul").toString().equals("Right")) {
                    posisiku = "R";
                } else {
                    posisiku = ((Activity) context).getIntent().getExtras().get("judul").toString();
                }
            }else{
                posisiku = "Unidentified";
                namaposisi = "Und";
            }
            detail =  namaposisi+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+string+File.separator+posisiku);


        }


        String root = String.valueOf(directory);
        String mFileName = root+File.separator+string+"_" + detail + "_thumbnail"+String.valueOf(i)+".jpg";
        return mFileName;
    }

    public String setNamaFile(String string, int i){

        File directory;
        String detail;

        if(string.equals("Median")){
            detail = String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+string);
        }else{

            String posisiku;
            String namaposisi;

            if(((Activity) context).getIntent().hasExtra("judul")) {
                namaposisi = ((Activity) context).getIntent().getExtras().get("judul").toString();
                if (((Activity) context).getIntent().getExtras().get("judul").toString().equals("Left")) {
                    posisiku = "L";
                } else if (((Activity) context).getIntent().getExtras().get("judul").toString().equals("Right")) {
                    posisiku = "R";
                } else {
                    posisiku = ((Activity) context).getIntent().getExtras().get("judul").toString();
                }
            }else{
                posisiku = "Unidentified";
                namaposisi = "Und";
            }

            detail =  namaposisi+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+string+File.separator+posisiku);


        }



        String root = String.valueOf(directory);
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String mFileName = root+File.separator+string+"_"+detail +"_"+String.valueOf(i)+timeStamp+".jpg";
        return mFileName;
    }


    public String setNamaFileListImage(String detailNama, int i){
        String[] a = detailNama.split("_");

        String directory;
        String detail;


        if(a[0].equals("Median")){
            detail = String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
            directory = String.valueOf(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+a[0]));
        }else{

            String posisiku;
            if(a[1].equals("Left")){
                posisiku = "L";
            }else if(a[1].equals("Right")){
                posisiku="R";
            }else{
                posisiku = a[1];
            }
            detail =  a[1]+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
            directory = String.valueOf(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+a[0]+File.separator+posisiku));
        }

        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String mFile = directory+ File.separator+a[0]+"_"+detail+"_"+String.valueOf(i)+timeStamp+".jpg";
        return mFile;
    }


    public String setNamaThumbnailList(String detailNama, int i){
        String[] a = detailNama.split("_");


        String directory;
        String detail;


        if(a[0].equals("Median")){
            detail = String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
            directory = String.valueOf(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+a[0]));
        }else{

            String posisiku;
            if(a[1].equals("Left")){
                posisiku = "L";
            }else if(a[1].equals("Right")){
                posisiku="R";
            }else{
                posisiku = a[1];
            }
            detail =  a[1]+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
            directory = String.valueOf(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+a[0]+File.separator+posisiku));
        }


        String mFile = directory+ File.separator+a[0]+"_"+detail+"_thumbnail"+String.valueOf(i)+".jpg";
        return mFile;
    }

    public String setNamathumbnailkolektif(String ruas, String string, int i, String sawal, String sakhir, String letak){


        File directory;
        String detail;


        if(string.equals("Median")){
            detail = String.valueOf(session.getKodeprov())+"."+String.valueOf(ruas)+"."+String.valueOf(sawal)+"-"+String.valueOf(sakhir);
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+ruas+File.separator+string);
        }else{

            String posisiku;
            if(letak.equals("Left")){
                posisiku = "L";
            }else if(letak.equals("Right")){
                posisiku="R";
            }else{
                posisiku = letak;
            }
            detail =  string+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(ruas)+"."+String.valueOf(sawal)+"-"+String.valueOf(sakhir);
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+ruas+File.separator+string+File.separator+posisiku);


        }


        String root = String.valueOf(directory);
        String mFileName = root+File.separator+string+"_" + detail + "_thumbnail"+String.valueOf(i)+".jpg";
        return mFileName;
    }

    public String setNamaFilekolektif(String ruas, String string, int i, String sawal, String sakhir, String letak){

        File directory;
        String detail;

        if(string.equals("Median")){
            detail = String.valueOf(session.getKodeprov())+"."+String.valueOf(ruas)+"."+String.valueOf(sawal)+"-"+String.valueOf(sakhir);
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+ruas+File.separator+string);
        }else{

            String posisiku;
            if(letak.equals("Left")){
                posisiku = "L";
            }else if(letak.equals("Right")){
                posisiku="R";
            }else{
                posisiku = letak;
            }
            detail =  string+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(ruas)+"."+String.valueOf(sawal)+"-"+String.valueOf(sakhir);
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+ruas+File.separator+string+File.separator+posisiku);


        }



        String root = String.valueOf(directory);
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String mFileName = root+File.separator+string+"_"+detail +"_"+String.valueOf(i)+timeStamp+".jpg";
        return mFileName;
    }


    public String setNamathumbnailUn(String string, int segment, String posisi, int i){


        File directory;
        String detail;


        if(string.equals("Median")){
            detail = "Und"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(segment);
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+string);
        }else{

            String posisiku;
            String namaposisi;

            if(string.equals("Lane")){
                posisiku = posisi;
            }else{
                posisiku = posisi.substring(0,1);
            }

            namaposisi = "Und";
            detail =  posisi+"_"+namaposisi+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(segment);
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+string+File.separator+posisiku);


        }


        String root = String.valueOf(directory);
        String mFileName = root+File.separator+string+"_" + detail + "_thumbnail"+String.valueOf(i)+".jpg";
        return mFileName;
    }

    public String setNamaFileUn(String string, int segment, String posisi, int i){

        File directory;
        String detail;

        if(string.equals("Median")){
            detail = "Und_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(segment);
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+string);
        }else{

            String posisiku;
            if(string.equals("Lane")){
                posisiku = posisi;
            }else{
                posisiku = posisi.substring(0,1);
            }
            String namaposisi;

            namaposisi = "Und";


            detail =  posisi+"_"+namaposisi+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(segment);
            directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+string+File.separator+posisiku);


        }



        String root = String.valueOf(directory);
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String mFileName = root+File.separator+string+"_"+detail +"_"+String.valueOf(i)+timeStamp+".jpg";
        return mFileName;
    }



    //Foto Terusan


}


/*for(int i = 1; i<=5; i++){

    for(int j = 1 ; j<=i; j++){

        cout<<1<<" ";

        }

        cout<<endl;
}*/
