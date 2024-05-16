package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.GetSemuaImage.DataListImage;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataMedian;

import java.util.ArrayList;

public class DbMedian {
    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbMedian(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertMedian (DataMedian dataMedian) {
        open();

        ContentValues values = new ContentValues();
        values.put(DataMedian.COLUMN_NOPROV,dataMedian.getNoprov());
        values.put(DataMedian.COLUMN_RUAS,dataMedian.getNoruas());
        values.put(DataMedian.COLUMN_SEGMENT,dataMedian.getNosegment());
        values.put(DataMedian.COLUMN_SUB_SEGMENT,dataMedian.getSubsegment());
        values.put(DataMedian.COLUMN_TIPE, dataMedian.getTipeMedian());
        values.put(DataMedian.COLUMN_LEBAR,dataMedian.getLebarMedian());
        values.put(DataMedian.COLUMN_GAMBAR,dataMedian.getGambarMedian());
        values.put(DataMedian.COLUMN_GAMBARICON,dataMedian.getGambarMedianicon());
        values.put(DataMedian.COLUMN_LOKASI,dataMedian.getLokasiMedian());

        database.insert(dataMedian.TABLE_NAME, null, values);
        database.close();
        close();

    }

    public DataMedian getMedian(String noprov, String noruas, int nosegment, int subsegment) {
        String tabel;
        database = dbHelper.getReadableDatabase();
        DataMedian dataMedian = null;

            tabel = DataMedian.TABLE_NAME;


        String where = DataMedian.COLUMN_NOPROV+" = ? AND "+DataMedian.COLUMN_RUAS+" = ? AND "+DataMedian.COLUMN_SEGMENT+" = ? AND "+DataMedian.COLUMN_SUB_SEGMENT+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataMedian.COLUMN_ID, DataMedian.COLUMN_NOPROV, DataMedian.COLUMN_RUAS, DataMedian.COLUMN_SEGMENT, DataMedian.COLUMN_SUB_SEGMENT, DataMedian.COLUMN_TIPE, DataMedian.COLUMN_LEBAR, DataMedian.COLUMN_GAMBAR, DataMedian.COLUMN_GAMBARICON, DataMedian.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment)}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataMedian = new DataMedian(
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataMedian.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataMedian.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_TIPE)),
                    cursor.getFloat(cursor.getColumnIndex(DataMedian.COLUMN_LEBAR)),
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_GAMBARICON)),
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();
        database.close();
        close();

        return dataMedian;
    }

    public void updateMedian(DataMedian dataMedian) {
        open();
        String tabel;

            tabel = DataMedian.TABLE_NAME;

        ContentValues values = new ContentValues();
        values.put(DataMedian.COLUMN_NOPROV,dataMedian.getNoprov());
        values.put(DataMedian.COLUMN_RUAS,dataMedian.getNoruas());
        values.put(DataMedian.COLUMN_SEGMENT,dataMedian.getNosegment());
        values.put(DataMedian.COLUMN_SUB_SEGMENT,dataMedian.getSubsegment());
        values.put(DataMedian.COLUMN_TIPE, dataMedian.getTipeMedian());
        values.put(DataMedian.COLUMN_LEBAR,dataMedian.getLebarMedian());
        values.put(DataMedian.COLUMN_GAMBAR,dataMedian.getGambarMedian());
        values.put(DataMedian.COLUMN_GAMBARICON,dataMedian.getGambarMedianicon());
        values.put(DataMedian.COLUMN_LOKASI,dataMedian.getLokasiMedian());
        String where = DataMedian.COLUMN_NOPROV+" = ? AND "+DataMedian.COLUMN_RUAS+" = ? AND "+DataMedian.COLUMN_SEGMENT+" = ? AND "+DataMedian.COLUMN_SUB_SEGMENT+" = ?";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataMedian.getNoprov()), String.valueOf(dataMedian.getNoruas()), String.valueOf(dataMedian.getNosegment()), String.valueOf(dataMedian.getSubsegment())});
        database.close();
        close();
    }


    public void setMedian(DataMedian dataMedian){
        DataMedian dataMedianTemporari =  getMedian(dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getNosegment(), dataMedian.getSubsegment());


        if(dataMedianTemporari!=null){

            updateMedian(dataMedian);
        }else{
            insertMedian(dataMedian);
        }

    }

    public void resetMedian(String provinsi, String ruas, int segment, int subsegment){
        DataMedian dataMedian =  getMedian(provinsi, ruas, segment, subsegment);
        dataMedian.setLokasiMedian(null);
        dataMedian.setGambarMedian(null);
        dataMedian.setGambarMedianicon(null);
        updateMedian(dataMedian);
    }



    public ArrayList<DataMedian> getListMedian(String noprov, String noruas) {
        ArrayList<DataMedian> notes = new ArrayList<>();
        String tabel;
        tabel = DataMedian.TABLE_NAME;
       database = dbHelper.getReadableDatabase();
        String where = DataMedian.COLUMN_NOPROV+" = ? AND "+DataMedian.COLUMN_RUAS+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataMedian.COLUMN_NOPROV, DataMedian.COLUMN_RUAS, DataMedian.COLUMN_SEGMENT, DataMedian.COLUMN_SUB_SEGMENT, DataMedian.COLUMN_TIPE, DataMedian.COLUMN_LEBAR, DataMedian.COLUMN_GAMBAR, DataMedian.COLUMN_GAMBARICON, DataMedian.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas)}, null, null, DataMedian.COLUMN_SEGMENT+", "+DataMedian.COLUMN_SUB_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                DataMedian dataMedian = new DataMedian(
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataMedian.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataMedian.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_TIPE)),
                        cursor.getFloat(cursor.getColumnIndex(DataMedian.COLUMN_LEBAR)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_LOKASI)));

                notes.add(dataMedian);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }

    public void tutup() {
        dbHelper.close();
    }




    public void deleteMedian(String noprov, String noruas, float subsegment) {

        String selectQuery = "delete FROM datamedian where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+subsegment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }


    public void saveMedianNormal(DataMedian dataMedian, int segmentAwal, int subSegmentAwal, int segmentAkhir, int subSegmentAkhir, String tipeSurvey){

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataMedian.COLUMN_TIPE, dataMedian.getTipeMedian());
        values.put(DataMedian.COLUMN_LEBAR,dataMedian.getLebarMedian());
        values.put(DataMedian.COLUMN_GAMBAR,dataMedian.getGambarMedian());
        values.put(DataMedian.COLUMN_GAMBARICON,dataMedian.getGambarMedianicon());
        values.put(DataMedian.COLUMN_LOKASI,dataMedian.getLokasiMedian());

        String where;

        if(tipeSurvey.equals("Normal")){
            where = DataMedian.COLUMN_NOPROV+" = ? AND "+DataMedian.COLUMN_RUAS+" = ? AND ( ( "
                    +DataMedian.COLUMN_SEGMENT+" = ? AND "+DataMedian.COLUMN_SEGMENT+ " != ? AND "+DataMedian.COLUMN_SUB_SEGMENT+" >= ? ) OR ( "
                    +DataMedian.COLUMN_SEGMENT+" > ? AND "+DataMedian.COLUMN_SEGMENT+" < ? ) OR ("
                    +DataMedian.COLUMN_SEGMENT+" = ? AND "+DataMedian.COLUMN_SEGMENT+ " != ? AND "+DataMedian.COLUMN_SUB_SEGMENT+"<= ? ) OR ( "
                    +DataMedian.COLUMN_SEGMENT+" = ? AND "+DataMedian.COLUMN_SEGMENT+" = ? AND "+DataMedian.COLUMN_SUB_SEGMENT+" >= ? AND "+DataMedian.COLUMN_SUB_SEGMENT+" <=  ? ))";

        }else{

            where = DataMedian.COLUMN_NOPROV+" = ? AND "+DataMedian.COLUMN_RUAS+" = ? AND ( ( "
                    +DataMedian.COLUMN_SEGMENT+" = ? AND "+DataMedian.COLUMN_SEGMENT+ " != ? AND "+DataMedian.COLUMN_SUB_SEGMENT+" <= ? ) OR ( "
                    +DataMedian.COLUMN_SEGMENT+" < ? AND "+DataMedian.COLUMN_SEGMENT+" > ? ) OR ("
                    +DataMedian.COLUMN_SEGMENT+" = ? AND "+DataMedian.COLUMN_SEGMENT+ " != ? AND "+DataMedian.COLUMN_SUB_SEGMENT+">= ? ) OR ( "
                    +DataMedian.COLUMN_SEGMENT+" = ? AND "+DataMedian.COLUMN_SEGMENT+" = ? AND "+DataMedian.COLUMN_SUB_SEGMENT+" <= ? AND "+DataMedian.COLUMN_SUB_SEGMENT+" >=  ? ))";


        }


        database.update(DataMedian.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataMedian.getNoprov()), String.valueOf(dataMedian.getNoruas()),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir),
                        String.valueOf(segmentAkhir), String.valueOf(segmentAwal),String.valueOf(subSegmentAkhir),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal), String.valueOf(subSegmentAkhir)
                });

        database.close();
        close();


    }

    //BATAS MEDIAN Tanpa Segment



    public DataMedian getMedianUn(String noprov, String noruas, int segment) {
        String tabel;
        database = dbHelper.getReadableDatabase();
        DataMedian dataMedian = null;

        tabel = DataMedian.TABLE_NAME;


        String where = DataMedian.COLUMN_NOPROV+" = ? AND "+DataMedian.COLUMN_RUAS+" = ? AND "+DataMedian.COLUMN_SEGMENT+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataMedian.COLUMN_ID, DataMedian.COLUMN_NOPROV, DataMedian.COLUMN_RUAS, DataMedian.COLUMN_SEGMENT, DataMedian.COLUMN_TIPE, DataMedian.COLUMN_LEBAR, DataMedian.COLUMN_GAMBAR, DataMedian.COLUMN_GAMBARICON, DataMedian.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(segment)}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataMedian = new DataMedian(
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataMedian.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataMedian.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_TIPE)),
                    cursor.getFloat(cursor.getColumnIndex(DataMedian.COLUMN_LEBAR)),
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_GAMBARICON)),
                    cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();
        database.close();
        close();

        return dataMedian;
    }

    public void updateMedianUn(DataMedian dataMedian) {

        open();
        String tabel;

        tabel = DataMedian.TABLE_NAME;

        ContentValues values = new ContentValues();
        values.put(DataMedian.COLUMN_NOPROV,dataMedian.getNoprov());
        values.put(DataMedian.COLUMN_RUAS,dataMedian.getNoruas());
        values.put(DataMedian.COLUMN_SEGMENT,dataMedian.getNosegment());
        values.put(DataMedian.COLUMN_SUB_SEGMENT,dataMedian.getSubsegment());
        values.put(DataMedian.COLUMN_TIPE, dataMedian.getTipeMedian());
        values.put(DataMedian.COLUMN_LEBAR,dataMedian.getLebarMedian());
        values.put(DataMedian.COLUMN_GAMBAR,dataMedian.getGambarMedian());
        values.put(DataMedian.COLUMN_GAMBARICON,dataMedian.getGambarMedianicon());
        values.put(DataMedian.COLUMN_LOKASI,dataMedian.getLokasiMedian());

        String where = DataMedian.COLUMN_NOPROV+" = ? AND "+DataMedian.COLUMN_RUAS+" = ? AND "+DataMedian.COLUMN_SEGMENT+" = ?";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataMedian.getNoprov()), String.valueOf(dataMedian.getNoruas()), String.valueOf(dataMedian.getNosegment())});
        database.close();
        close();
    }


    public void setMedianUn( DataMedian dataMedian){
        DataMedian dataMedianUn =  getMedianUn(dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getNosegment());


        if(dataMedianUn!=null){
            updateMedianUn(dataMedian);
        }else{
           insertMedian(dataMedian);
        }

    }


    public int getIndexMedian(String noprov, String noruas) {

        String selectQuery = "SELECT max(nosegment) as id FROM datamedian where noprov ='"+noprov+"' AND noruas ='"+noruas+"'";
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("id"));

        cursor.close();
        database.close();
        close();

        return maxid+1;

    }


    /*

     public void setMedianKolektif(String provinsi, String ruas, int segment, String tipe, float lebar, String foto, String icon, String lokasi){
        DataMedian dataMedian =  getMedian(provinsi, ruas, segment);


        if(dataMedian!=null){
            // Toast.makeText(context, "update lahan", Toast.LENGTH_SHORT).show();

            //dataMedian.setTipeMedian(tipe);
            dataMedian.setLebarMedian(lebar);
            dataMedian.setGambarMedian(foto);
            dataMedian.setGambarMedianicon(icon);
            dataMedian.setLokasiMedian(lokasi);

            updateMedian(dataMedian);
        }else{
            //  Toast.makeText(context, "insert lahan", Toast.LENGTH_SHORT).show();

            dataMedian = new DataMedian(0, provinsi, ruas, segment,  tipe,  lebar, null, null, null, null, 0,null, null,null);
            insertMedian(dataMedian);

        }

    }


    public void setMedianKolektifFoto(String provinsi, String ruas, int segment, String tipe, float lebar){
        DataMedian dataMedian =  getMedian(provinsi, ruas, segment);


        if(dataMedian!=null){
            // Toast.makeText(context, "update lahan", Toast.LENGTH_SHORT).show();

            //dataMedian.setTipeMedian(tipe);
            dataMedian.setLebarMedian(lebar);

            updateMedian(dataMedian);
        }else{
            //  Toast.makeText(context, "insert lahan", Toast.LENGTH_SHORT).show();

            dataMedian = new DataMedian(0, provinsi, ruas, segment,  tipe,  lebar, null, null, null, null, 0,null, null,null);
            insertMedian(dataMedian);

        }

    }

    public ArrayList<DataListImage> getImageMedian(String noprov, String noruas, String posisi) {
        ArrayList<DataListImage> notes = new ArrayList<>();
        String tabel;
        tabel = DataMedian.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataMedian.COLUMN_NOPROV+" = ? AND "+DataMedian.COLUMN_RUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataMedian.COLUMN_ID, DataMedian.COLUMN_NOPROV, DataMedian.COLUMN_RUAS, DataMedian.COLUMN_SEGMENT, DataMedian.COLUMN_GAMBAR , DataMedian.COLUMN_GAMBARICON, DataMedian.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas)}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataListImage datamedian = new DataListImage(
                        cursor.getInt(cursor.getColumnIndex(DataMedian.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_LOKASI)));
                notes.add(datamedian);
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
        tabel = DataMedian.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataMedian.COLUMN_NOPROV+" = ? AND "+DataMedian.COLUMN_RUAS+" = ? AND "+DataMedian.COLUMN_SEGMENT+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataMedian.COLUMN_NOPROV, DataMedian.COLUMN_RUAS, DataMedian.COLUMN_SEGMENT, DataMedian.COLUMN_GAMBAR , DataMedian.COLUMN_GAMBARICON, DataMedian.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(noseg)}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                note = new DataListImage(cursor.getInt(cursor.getColumnIndex(DataMedian.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataMedian.COLUMN_LOKASI)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return note;
    }

    public void updateImageMedian(String noprov, String noruas, int segment, String posisi, String image, String iconimage, String lokasi) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataMedian.COLUMN_GAMBAR,image);
        values.put(DataMedian.COLUMN_GAMBARICON,iconimage);
        values.put(DataMedian.COLUMN_LOKASI,lokasi);
        String where = DataMedian.COLUMN_NOPROV+" = ? AND "+DataMedian.COLUMN_RUAS+" = ? AND "+DataMedian.COLUMN_SEGMENT+" = ? ";
        database.update(DataMedian.TABLE_NAME, values, where,
                new String[]{noprov, noruas, String.valueOf(segment)});

        database.close();
        close();
    }

     */

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM datamedian where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM datamedian ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

}
