package com.example.roadmanagement.kaltara.FormTab.Helper;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class TabImage{

    public static boolean checkPermission(String url){
        try{
            FileInputStream is = new FileInputStream(url);
            return true;

        }catch (IOException e) {
            return false;
        }
    }

    public static String getImageLatlong(String url) throws IOException {

        ExifInterface exifInterface = new ExifInterface(url);
        float[] latLong = new float[2];
        boolean hasLatLong = exifInterface.getLatLong(latLong);
        if (hasLatLong) {
            return latLong[0]+"km"+latLong[1];
        }else{
            return "0km0";
        }

    }

    public static String getTempoName(String namaFile, File Directory, int urutan){
        String urut = String.valueOf(urutan);
        return String.valueOf(Directory)+File.separator+"temp_"+namaFile+urut+".jpg";

    }

    public static File createImageFromBitmap(Context context, String fileNama, Uri uri){

        InputStream is = null;
        try {
            File fileFoto = new File(fileNama);
            fileFoto.createNewFile();

            is = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            OutputStream os = new FileOutputStream(fileFoto);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                os = Files.newOutputStream(fileFoto.toPath());
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            return fileFoto;

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static Bitmap bitmapFactory(File file, int value) throws IOException {

        Bitmap bitmap;
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

}
