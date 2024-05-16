package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.GetSemuaImage.DataListImage;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;

import java.util.ArrayList;

public class DbLahan  {
    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;


    public DbLahan(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertLahan (DataLahan dataLahan) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataLahan.COLUMN_NOPROV,dataLahan.getNoprov());
        values.put(DataLahan.COLUMN_RUAS,dataLahan.getNoruas());
        values.put(DataLahan.COLUMN_SEGMENT,dataLahan.getNosegment());
        values.put(DataLahan.COLUMN_SUB_SEGMENT,dataLahan.getSubsegment());
        values.put(DataLahan.COLUMN_POSISI, dataLahan.getPosisi());
        values.put(DataLahan.COLUMN_TIPE, dataLahan.getTipeLahan());
        values.put(DataLahan.COLUMN_TAGUN, dataLahan.getTatagunaLahan());
        values.put(DataLahan.COLUMN_KEMIRINGANLAHAN,dataLahan.getKemiringan());
        values.put(DataLahan.COLUMN_TINGGILAHAN,dataLahan.getTinggiLahan());
        values.put(DataLahan.COLUMN_GAMBAR,dataLahan.getGambarLahan());
        values.put(DataLahan.COLUMN_GAMBAR_ICON,dataLahan.getIcongambar());
        values.put(DataLahan.COLUMN_LOKASI,dataLahan.getLokasilahan());

        database.insert(DataLahan.TABLE_NAME , null, values);
        database.close();
        close();

    }

    public DataLahan getLahan(String noprov, String noruas, int nosegment, int subSegment, String posisi) {
       open();
        DataLahan dataLahan = null;

        String where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_SUB_SEGMENT+" = ? AND "+DataLahan.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(DataLahan.TABLE_NAME,
                new String[]{DataLahan.COLUMN_ID, DataLahan.COLUMN_NOPROV, DataLahan.COLUMN_RUAS, DataLahan.COLUMN_SEGMENT, DataLahan.COLUMN_SUB_SEGMENT, DataLahan.COLUMN_POSISI,
                        DataLahan.COLUMN_TIPE,  DataLahan.COLUMN_TAGUN, DataLahan.COLUMN_KEMIRINGANLAHAN, DataLahan.COLUMN_TINGGILAHAN, DataLahan.COLUMN_GAMBAR , DataLahan.COLUMN_GAMBAR_ICON, DataLahan.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subSegment), posisi}, null, null, null, null);

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            dataLahan = new DataLahan(
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_TIPE)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_TAGUN)),
                    cursor.getFloat(cursor.getColumnIndex(DataLahan.COLUMN_KEMIRINGANLAHAN)),
                    cursor.getFloat(cursor.getColumnIndex(DataLahan.COLUMN_TINGGILAHAN)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_LOKASI)));
        }
        cursor.close();
        database.close();
        close();


        return dataLahan;
    }

    public void updateLahan(DataLahan dataLahan) {
       database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataLahan.COLUMN_TIPE, dataLahan.getTipeLahan());
        values.put(DataLahan.COLUMN_TAGUN, dataLahan.getTatagunaLahan());
        values.put(DataLahan.COLUMN_KEMIRINGANLAHAN, dataLahan.getKemiringan());
        values.put(DataLahan.COLUMN_TINGGILAHAN,dataLahan.getTinggiLahan());
        values.put(DataLahan.COLUMN_GAMBAR,dataLahan.getGambarLahan());
        values.put(DataLahan.COLUMN_GAMBAR_ICON,dataLahan.getIcongambar());
        values.put(DataLahan.COLUMN_LOKASI,dataLahan.getLokasilahan());
        String where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_SUB_SEGMENT+" = ? AND "+DataLahan.COLUMN_POSISI+" = ? ";
        database.update(DataLahan.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataLahan.getNoprov()), String.valueOf(dataLahan.getNoruas()), String.valueOf(dataLahan.getNosegment()), String.valueOf(dataLahan.getSubsegment()), dataLahan.getPosisi()});

        database.close();
        close();
    }

    public void resetLahan(String provinsi, String ruas, int segment, int subSegment, String posisi){
        DataLahan dataLahan =  getLahan(provinsi, ruas, segment, subSegment, posisi);
        dataLahan.setLokasilahan(null);
        dataLahan.setGambarLahan(null);
        dataLahan.setIcongambar(null);
        updateLahan(dataLahan);
    }



    public ArrayList<DataLahan> getListLahan(String noprov, String noruas, String posisi) {
        ArrayList<DataLahan> notes = new ArrayList<>();
        String tabel;
        tabel = DataLahan.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ?";
        String[] parameter = new String[]{String.valueOf(noprov), String.valueOf(noruas)};

        if(posisi!=null){

            where = where+"AND "+DataLahan.COLUMN_POSISI+" = ? ";
            parameter = add_element(parameter.length, parameter, posisi);

        }

        Cursor cursor = database.query(tabel,
                new String[]{DataLahan.COLUMN_ID, DataLahan.COLUMN_NOPROV, DataLahan.COLUMN_RUAS, DataLahan.COLUMN_SEGMENT, DataLahan.COLUMN_SUB_SEGMENT, DataLahan.COLUMN_POSISI,
                        DataLahan.COLUMN_TIPE,  DataLahan.COLUMN_TAGUN, DataLahan.COLUMN_KEMIRINGANLAHAN, DataLahan.COLUMN_TINGGILAHAN, DataLahan.COLUMN_GAMBAR , DataLahan.COLUMN_GAMBAR_ICON, DataLahan.COLUMN_LOKASI},
                where,
                parameter, null, null, DataLahan.COLUMN_SEGMENT+", "+DataLahan.COLUMN_SUB_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                DataLahan dataLahan = new DataLahan(
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_TIPE)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_TAGUN)),
                        cursor.getFloat(cursor.getColumnIndex(DataLahan.COLUMN_KEMIRINGANLAHAN)),
                        cursor.getFloat(cursor.getColumnIndex(DataLahan.COLUMN_TINGGILAHAN)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_LOKASI)));
                notes.add(dataLahan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }



    public void setLahan(DataLahan dataLahan){

        DataLahan dataLahanTempo =  getLahan(dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getNosegment(), dataLahan.getSubsegment(), dataLahan.getPosisi());

      if(dataLahanTempo!=null){
            updateLahan(dataLahan);
        }else{
            insertLahan(dataLahan);
        }

    }



    public void tutup() {
        dbHelper.close();
    }

    public void deleteLahan(String noprov, String noruas, float subsegment) {

        String selectQuery = "delete FROM datalahan where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+subsegment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void saveLahanNormal(DataLahan dataLahan, int segmentAwal, int subsegmentAwal, int segmentAkhir, int subsegmentAkhir, String tipeSurvey){

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataLahan.COLUMN_TIPE, dataLahan.getTipeLahan());
        values.put(DataLahan.COLUMN_TAGUN, dataLahan.getTatagunaLahan());
        values.put(DataLahan.COLUMN_KEMIRINGANLAHAN, dataLahan.getKemiringan());
        values.put(DataLahan.COLUMN_TINGGILAHAN,dataLahan.getTinggiLahan());
        values.put(DataLahan.COLUMN_GAMBAR,dataLahan.getGambarLahan());
        values.put(DataLahan.COLUMN_GAMBAR_ICON,dataLahan.getIcongambar());
        values.put(DataLahan.COLUMN_LOKASI,dataLahan.getLokasilahan());

        String where;

        if(tipeSurvey.equals("Normal")){
            where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_POSISI+" = ? AND ( ( "
                    +DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_SEGMENT+ " != ? AND "+DataLahan.COLUMN_SUB_SEGMENT+" >= ? ) OR ( "
                    +DataLahan.COLUMN_SEGMENT+" > ? AND "+DataLahan.COLUMN_SEGMENT+" < ? ) OR ("
                    +DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_SEGMENT+ " != ? AND "+DataLahan.COLUMN_SUB_SEGMENT+"<= ? ) OR ( "
                    +DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_SUB_SEGMENT+" >= ? AND "+DataLahan.COLUMN_SUB_SEGMENT+" <=  ? ))";

        }else{

            where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_POSISI+" = ? AND ( ( "
                    +DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_SEGMENT+ " != ? AND "+DataLahan.COLUMN_SUB_SEGMENT+" <= ? ) OR ( "
                    +DataLahan.COLUMN_SEGMENT+" < ? AND "+DataLahan.COLUMN_SEGMENT+" > ? ) OR ("
                    +DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_SEGMENT+ " != ? AND "+DataLahan.COLUMN_SUB_SEGMENT+">= ? ) OR ( "
                    +DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_SUB_SEGMENT+" <= ? AND "+DataLahan.COLUMN_SUB_SEGMENT+" >=  ? ))";


        }

        database.update(DataLahan.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataLahan.getNoprov()), String.valueOf(dataLahan.getNoruas()), dataLahan.getPosisi(),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subsegmentAwal),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir),
                        String.valueOf(segmentAkhir), String.valueOf(segmentAwal),String.valueOf(subsegmentAkhir),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subsegmentAwal), String.valueOf(subsegmentAkhir)
                });

        database.close();
        close();


    }

    private  String[] add_element(int maks, String myarray[], String value)
    {

        String newArray[] = new String[maks + 1];
        //copy original array into new array
        for (int i = 0; i<maks; i++) {
            newArray[i] = myarray[i];
        }

        newArray[maks] = value;

        return newArray;

    }



    //BATAS TANPA SEGMENT

    public DataLahan getLahanUn(String noprov, String noruas, int segment) {
        open();
        //database = dbHelper.getReadableDatabase();
        DataLahan dataLahan = null;

        String where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_SEGMENT+" = ? ";
        Cursor cursor = database.query(DataLahan.TABLE_NAME,
                new String[]{DataLahan.COLUMN_ID, DataLahan.COLUMN_NOPROV, DataLahan.COLUMN_RUAS, DataLahan.COLUMN_SEGMENT, DataLahan.COLUMN_SUB_SEGMENT, DataLahan.COLUMN_POSISI, DataLahan.COLUMN_TIPE,  DataLahan.COLUMN_TAGUN, DataLahan.COLUMN_KEMIRINGANLAHAN, DataLahan.COLUMN_TINGGILAHAN, DataLahan.COLUMN_GAMBAR , DataLahan.COLUMN_GAMBAR_ICON, DataLahan.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(segment)}, null, null, null, null);

        //  int i =1;

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {

            dataLahan = new DataLahan(
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_TIPE)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_TAGUN)),
                    cursor.getFloat(cursor.getColumnIndex(DataLahan.COLUMN_KEMIRINGANLAHAN)),
                    cursor.getFloat(cursor.getColumnIndex(DataLahan.COLUMN_TINGGILAHAN)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_LOKASI)));

        }
        cursor.close();
        database.close();
        close();


        return dataLahan;
    }

    public void updateLahanUn(DataLahan dataLahan) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataLahan.COLUMN_TIPE, dataLahan.getTipeLahan());
        values.put(DataLahan.COLUMN_TAGUN, dataLahan.getTatagunaLahan());
        values.put(DataLahan.COLUMN_KEMIRINGANLAHAN, dataLahan.getKemiringan());
        values.put(DataLahan.COLUMN_TINGGILAHAN,dataLahan.getTinggiLahan());
        values.put(DataLahan.COLUMN_GAMBAR,dataLahan.getGambarLahan());
        values.put(DataLahan.COLUMN_GAMBAR_ICON,dataLahan.getIcongambar());
        values.put(DataLahan.COLUMN_LOKASI,dataLahan.getLokasilahan());
        String where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_SEGMENT+" = ? ";
        database.update(DataLahan.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataLahan.getNoprov()), String.valueOf(dataLahan.getNoruas()), String.valueOf(dataLahan.getNosegment())});

        database.close();
        close();
    }

    public void setLahanUn(DataLahan dataLahan){
        DataLahan dataLahanUn =  getLahanUn(dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getNosegment());

        if(dataLahanUn!=null){
            updateLahanUn(dataLahan);
        }else{
            insertLahan(dataLahan);
        }

    }

    public int getIndexLahanUn(String noprov, String noruas) {

        String selectQuery = "SELECT max(nosegment) as id FROM datalahan where noprov ='"+noprov+"' AND noruas ='"+noruas+"'";

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("id"));

        cursor.close();
        database.close();
        close();

        return maxid+1;
    }

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM datalahan where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM datalahan ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    /*

    public ArrayList<DataLahan> getLahankiri(String noprov, String noruas) {
        ArrayList<DataLahan> notes = new ArrayList<>();
        String tabel;
        tabel = DataLahan.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataLahan.COLUMN_ID, DataLahan.COLUMN_NOPROV, DataLahan.COLUMN_RUAS, DataLahan.COLUMN_SEGMENT, DataLahan.COLUMN_POSISI, DataLahan.COLUMN_TIPE,  DataLahan.COLUMN_TAGUN, DataLahan.COLUMN_KEMIRINGANLAHAN, DataLahan.COLUMN_TINGGILAHAN, DataLahan.COLUMN_GAMBAR , DataLahan.COLUMN_GAMBAR_ICON, DataLahan.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), "kiri"}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataLahan dataLahan = new DataLahan(
                        cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_TIPE)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_TAGUN)),
                        cursor.getFloat(cursor.getColumnIndex(DataLahan.COLUMN_KEMIRINGANLAHAN)),
                        cursor.getFloat(cursor.getColumnIndex(DataLahan.COLUMN_TINGGILAHAN)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_LOKASI)));
                notes.add(dataLahan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }


    public ArrayList<DataLahan> getLahankanan(String noprov, String noruas) {
        ArrayList<DataLahan> notes = new ArrayList<>();
        String tabel;
        tabel = DataLahan.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataLahan.COLUMN_ID, DataLahan.COLUMN_NOPROV, DataLahan.COLUMN_RUAS, DataLahan.COLUMN_SEGMENT, DataLahan.COLUMN_POSISI, DataLahan.COLUMN_TIPE,  DataLahan.COLUMN_TAGUN, DataLahan.COLUMN_KEMIRINGANLAHAN, DataLahan.COLUMN_TINGGILAHAN, DataLahan.COLUMN_GAMBAR , DataLahan.COLUMN_GAMBAR_ICON, DataLahan.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), "kanan"}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataLahan dataLahan = new DataLahan(
                        cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_TIPE)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_TAGUN)),
                        cursor.getFloat(cursor.getColumnIndex(DataLahan.COLUMN_KEMIRINGANLAHAN)),
                        cursor.getFloat(cursor.getColumnIndex(DataLahan.COLUMN_TINGGILAHAN)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_LOKASI)));
                notes.add(dataLahan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }


    public ArrayList<DataListImage> getImageLahan(String noprov, String noruas, String posisi) {
        ArrayList<DataListImage> notes = new ArrayList<>();
        String tabel;
        tabel = DataLahan.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataLahan.COLUMN_ID, DataLahan.COLUMN_NOPROV, DataLahan.COLUMN_RUAS, DataLahan.COLUMN_SEGMENT, DataLahan.COLUMN_POSISI, DataLahan.COLUMN_TIPE,  DataLahan.COLUMN_TAGUN, DataLahan.COLUMN_KEMIRINGANLAHAN, DataLahan.COLUMN_TINGGILAHAN, DataLahan.COLUMN_GAMBAR , DataLahan.COLUMN_GAMBAR_ICON, DataLahan.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataListImage dataLahan = new DataListImage(
                        cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_LOKASI)));
                notes.add(dataLahan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }


    public DataListImage getImageSingle(String noprov, String noruas, String noseg, String posisi) {
        DataListImage note = null;
        String tabel;
        tabel = DataLahan.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataLahan.COLUMN_NOPROV, DataLahan.COLUMN_RUAS, DataLahan.COLUMN_SEGMENT, DataLahan.COLUMN_GAMBAR , DataLahan.COLUMN_GAMBAR_ICON, DataLahan.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(noseg), posisi}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                       note = new DataListImage(cursor.getInt(cursor.getColumnIndex(DataLahan.COLUMN_SEGMENT)),
                       cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR)),
                       cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_GAMBAR_ICON)),
                       cursor.getString(cursor.getColumnIndex(DataLahan.COLUMN_LOKASI)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return note;
    }

    public void updateImageLahan(String noprov, String noruas, int segment, String posisi, String image, String iconimage, String lokasi) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataLahan.COLUMN_GAMBAR,image);
        values.put(DataLahan.COLUMN_GAMBAR_ICON,iconimage);
        values.put(DataLahan.COLUMN_LOKASI,lokasi);
        String where = DataLahan.COLUMN_NOPROV+" = ? AND "+DataLahan.COLUMN_RUAS+" = ? AND "+DataLahan.COLUMN_SEGMENT+" = ? AND "+DataLahan.COLUMN_POSISI+" = ? ";
        database.update(DataLahan.TABLE_NAME, values, where,
                new String[]{noprov, noruas, String.valueOf(segment), posisi});

        database.close();
        close();
    }

     public void setLahanKolektif(String provinsi, String ruas, int segment, String posisi, String tipe, String tagun, float kemiringanlahan,  float panjang, String foto, String icon, String lokasi){
        DataLahan dataLahan =  getLahan(provinsi, ruas, segment, posisi);


        if(dataLahan!=null){
            // Toast.makeText(context, "update lahan", Toast.LENGTH_SHORT).show();

            dataLahan.setTipeLahan(tipe);
            dataLahan.setTatagunaLahan(tagun);
            dataLahan.setTinggiLahan(panjang);
            dataLahan.setGambarLahan(foto);
            dataLahan.setIcongambar(icon);
            dataLahan.setLokasilahan(lokasi);
            updateLahan(dataLahan);
        }else{
            //  Toast.makeText(context, "insert lahan", Toast.LENGTH_SHORT).show();

            dataLahan = new DataLahan(0, provinsi, ruas, segment, posisi, tipe, tagun, 0,  panjang,null, null, null);
            insertLahan(dataLahan);

        }

    }


    public void setLahanKolektifFoto(String provinsi, String ruas, int segment, String posisi, String tipe, String tagun, float kemiringanlahan,  float panjang){
        DataLahan dataLahan =  getLahan(provinsi, ruas, segment, posisi);


        if(dataLahan!=null){
            // Toast.makeText(context, "update lahan", Toast.LENGTH_SHORT).show();

            dataLahan.setTipeLahan(tipe);
            dataLahan.setTatagunaLahan(tagun);
            dataLahan.setTinggiLahan(panjang);
            updateLahan(dataLahan);
        }else{
            //  Toast.makeText(context, "insert lahan", Toast.LENGTH_SHORT).show();

            dataLahan = new DataLahan(0, provinsi, ruas, segment, posisi, tipe, tagun, 0,  panjang,null, null, null);
            insertLahan(dataLahan);

        }

    }


    public void updateKolektif(String noprov, String ruas, int awal, int akhir, String posisi, String tipe, String tagun, float tinggi) {
        database = dbHelper.getWritableDatabase();

        for(int i = awal; i<=akhir; i++){
           DataLahan dataLahan = getLahan(noprov, ruas, i, posisi);
           dataLahan.setTipeLahan(tipe);
           dataLahan.setTatagunaLahan(tagun);
           dataLahan.setTinggiLahan(tinggi);

           updateLahan(dataLahan);
        }

        database.close();
        close();
    }
     */
}
