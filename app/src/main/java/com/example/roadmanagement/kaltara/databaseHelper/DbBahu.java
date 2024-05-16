package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.GetSemuaImage.DataListImage;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataBahu;

import java.util.ArrayList;

public class DbBahu {
    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbBahu(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {

         database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertBahu (DataBahu dataBahu) {
        open();

        ContentValues values = new ContentValues();
        values.put(DataBahu.COLUMN_NOPROV,dataBahu.getNoprov());
        values.put(DataBahu.COLUMN_RUAS,dataBahu.getNoruas());
        values.put(DataBahu.COLUMN_SEGMENT,dataBahu.getNosegment());
        values.put(DataBahu.COLUMN_SUB_SEGMENT,dataBahu.getSubSegment());
        values.put(DataBahu.COLUMN_POSISI, dataBahu.getPosisi());
        values.put(DataBahu.COLUMN_TIPE, dataBahu.getTipeBahu());
        values.put(DataBahu.COLUMN_LEBAR,dataBahu.getLebarBahu());
        values.put(DataBahu.COLUMN_MATERIAL_BAHU,dataBahu.getMaterialBahu());
        values.put(DataBahu.COLUMN_KEMIRINGAN_DERAJAT,dataBahu.getKemiringanDerajat());
        values.put(DataBahu.COLUMN_KEMIRINGAN_PERSEN,dataBahu.getKemiringanPersen());
        values.put(DataBahu.COLUMN_KEMIRINGAN_ARAH,dataBahu.getKemiringanArah());
        values.put(DataBahu.COLUMN_KEMIRINGAN_KONDISI,dataBahu.getKemiringanKondisi());
        values.put(DataBahu.COLUMN_TIPE_INNER, dataBahu.getTipeBahuInner());
        values.put(DataBahu.COLUMN_LEBAR_INNER,dataBahu.getLebarBahuInner());
        values.put(DataBahu.COLUMN_MATERIAL_INNER,dataBahu.getMaterialInner());
        values.put(DataBahu.COLUMN_GAMBAR,dataBahu.getGambarBahu());
        values.put(DataBahu.COLUMN_GAMBARICON,dataBahu.getGambarBahuicon());
        values.put(DataBahu.COLUMN_LOKASI,dataBahu.getLokasiBahu());

        database.insert(DataBahu.TABLE_NAME, null, values);

        database.close();
        close();

    }

    public DataBahu getBahu(String noprov, String noruas, int nosegment, int subsegment, String posisi) {

        open();
        DataBahu dataBahu = null;

        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_SUB_SEGMENT+" = ? AND "+ DataBahu.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(DataBahu.TABLE_NAME,
                new String[]{DataBahu.COLUMN_NOPROV, DataBahu.COLUMN_RUAS, DataBahu.COLUMN_SEGMENT, DataBahu.COLUMN_SUB_SEGMENT, DataBahu.COLUMN_POSISI,
                        DataBahu.COLUMN_TIPE, DataBahu.COLUMN_LEBAR, DataBahu.COLUMN_MATERIAL_BAHU, DataBahu.COLUMN_KEMIRINGAN_DERAJAT, DataBahu.COLUMN_KEMIRINGAN_PERSEN, DataBahu.COLUMN_KEMIRINGAN_ARAH, DataBahu.COLUMN_KEMIRINGAN_KONDISI,
                        DataBahu.COLUMN_TIPE_INNER,  DataBahu.COLUMN_LEBAR_INNER ,  DataBahu.COLUMN_MATERIAL_INNER, DataBahu.COLUMN_GAMBAR, DataBahu.COLUMN_GAMBARICON, DataBahu.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment), posisi}, null, null, null, null);


        if (cursor.moveToFirst()) {

            dataBahu = new DataBahu(
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE)),
                    cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_MATERIAL_BAHU)),
                    cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_DERAJAT)),
                    cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_PERSEN)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_ARAH)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_KONDISI)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE_INNER)),
                    cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR_INNER)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_MATERIAL_INNER)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARICON)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();

        database.close();
        close();

        return dataBahu;
    }




    public void updateBahu(DataBahu dataBahu) {
        open();

        ContentValues values = new ContentValues();
        values.put(DataBahu.COLUMN_TIPE, dataBahu.getTipeBahu());
        values.put(DataBahu.COLUMN_LEBAR,dataBahu.getLebarBahu());
        values.put(DataBahu.COLUMN_MATERIAL_BAHU,dataBahu.getMaterialBahu());
        values.put(DataBahu.COLUMN_KEMIRINGAN_DERAJAT,dataBahu.getKemiringanDerajat());
        values.put(DataBahu.COLUMN_KEMIRINGAN_PERSEN,dataBahu.getKemiringanPersen());
        values.put(DataBahu.COLUMN_KEMIRINGAN_ARAH,dataBahu.getKemiringanArah());
        values.put(DataBahu.COLUMN_KEMIRINGAN_KONDISI,dataBahu.getKemiringanKondisi());
        values.put(DataBahu.COLUMN_TIPE_INNER, dataBahu.getTipeBahuInner());
        values.put(DataBahu.COLUMN_LEBAR_INNER,dataBahu.getLebarBahuInner());
        values.put(DataBahu.COLUMN_MATERIAL_INNER,dataBahu.getMaterialInner());
        values.put(DataBahu.COLUMN_GAMBAR,dataBahu.getGambarBahu());
        values.put(DataBahu.COLUMN_GAMBARICON,dataBahu.getGambarBahuicon());
        values.put(DataBahu.COLUMN_LOKASI,dataBahu.getLokasiBahu());
        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_SUB_SEGMENT+" = ? AND "+ DataBahu.COLUMN_POSISI+" = ? ";
        database.update(DataBahu.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataBahu.getNoprov()), String.valueOf(dataBahu.getNoruas()), String.valueOf(dataBahu.getNosegment()), String.valueOf(dataBahu.getSubSegment()), dataBahu.getPosisi() });

        database.close();
        close();
    }

    public ArrayList<DataBahu> getListBahu(String noprov, String noruas, String posisi) {
        ArrayList<DataBahu> notes = new ArrayList<>();
        String tabel = DataBahu.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ?";
        String[] parameter = new String[]{String.valueOf(noprov), String.valueOf(noruas)};

        if(posisi!=null){

            where = where+"AND "+DataBahu.COLUMN_POSISI+" = ? ";
            parameter = add_element(parameter.length, parameter, posisi);

        }

        Cursor cursor = database.query(tabel,
                new String[]{DataBahu.COLUMN_ID, DataBahu.COLUMN_NOPROV, DataBahu.COLUMN_RUAS, DataBahu.COLUMN_SEGMENT, DataBahu.COLUMN_SUB_SEGMENT, DataBahu.COLUMN_POSISI,
                        DataBahu.COLUMN_TIPE, DataBahu.COLUMN_LEBAR, DataBahu.COLUMN_MATERIAL_BAHU, DataBahu.COLUMN_KEMIRINGAN_DERAJAT, DataBahu.COLUMN_KEMIRINGAN_PERSEN, DataBahu.COLUMN_KEMIRINGAN_ARAH, DataBahu.COLUMN_KEMIRINGAN_KONDISI,
                        DataBahu.COLUMN_TIPE_INNER,  DataBahu.COLUMN_LEBAR_INNER ,  DataBahu.COLUMN_MATERIAL_INNER, DataBahu.COLUMN_GAMBAR, DataBahu.COLUMN_GAMBARICON, DataBahu.COLUMN_LOKASI},
                where, parameter, null, null, DataBahu.COLUMN_SEGMENT+", "+DataBahu.COLUMN_SUB_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                DataBahu dataBahu = new DataBahu(
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_MATERIAL_BAHU)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_DERAJAT)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_PERSEN)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_ARAH)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_KONDISI)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE_INNER)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR_INNER)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_MATERIAL_INNER)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_LOKASI)));
                notes.add(dataBahu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }

    public ArrayList<DataBahu> getListBahuSegment(String noprov, String noruas, String posisi, int segment) {
        ArrayList<DataBahu> notes = new ArrayList<>();
        String tabel = DataBahu.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_POSISI+" = ? AND "+DataBahu.COLUMN_SEGMENT+" = ?";
        String[] parameter = new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi, String.valueOf(segment)};


        Cursor cursor = database.query(tabel,
                new String[]{DataBahu.COLUMN_NOPROV, DataBahu.COLUMN_RUAS, DataBahu.COLUMN_SEGMENT, DataBahu.COLUMN_SUB_SEGMENT, DataBahu.COLUMN_POSISI,
                        DataBahu.COLUMN_TIPE, DataBahu.COLUMN_LEBAR, DataBahu.COLUMN_MATERIAL_BAHU, DataBahu.COLUMN_KEMIRINGAN_DERAJAT, DataBahu.COLUMN_KEMIRINGAN_PERSEN, DataBahu.COLUMN_KEMIRINGAN_ARAH, DataBahu.COLUMN_KEMIRINGAN_KONDISI,
                        DataBahu.COLUMN_TIPE_INNER,  DataBahu.COLUMN_LEBAR_INNER ,  DataBahu.COLUMN_MATERIAL_INNER, DataBahu.COLUMN_GAMBAR, DataBahu.COLUMN_GAMBARICON, DataBahu.COLUMN_LOKASI},
                where, parameter, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataBahu dataBahu = new DataBahu(
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_MATERIAL_BAHU)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_DERAJAT)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_PERSEN)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_ARAH)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_KONDISI)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE_INNER)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR_INNER)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_MATERIAL_INNER)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_LOKASI)));
                notes.add(dataBahu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }

    public void setBahu(DataBahu dataBahu){

        DataBahu dataBahuTemporari =  getBahu(dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getNosegment(), dataBahu.getSubSegment(), dataBahu.getPosisi());

        if(dataBahuTemporari!=null){

            updateBahu(dataBahu);

        }else{

            insertBahu(dataBahu);

        }

    }


    public void resetBahu(String provinsi, String ruas, int segment, int subsegment, String posisi){
        DataBahu dataBahu =  getBahu(provinsi, ruas, segment, subsegment, posisi);
        dataBahu.setLokasiBahu(null);
        dataBahu.setGambarBahu(null);
        dataBahu.setGambarBahuicon(null);
        updateBahu(dataBahu);
    }




    public void tutup() {
        dbHelper.close();
    }



    public void deleteBahu(String noprov, String noruas, float subsegment) {

        String selectQuery = "delete FROM databahu where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+subsegment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void saveBahuNormal(DataBahu dataBahu, int segmentAwal, int subSegmentAwal, int segmentAkhir, int subSegmentAkhir, String tipeSurvey){

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBahu.COLUMN_TIPE, dataBahu.getTipeBahu());
        values.put(DataBahu.COLUMN_LEBAR,dataBahu.getLebarBahu());
        values.put(DataBahu.COLUMN_MATERIAL_BAHU,dataBahu.getMaterialBahu());
        values.put(DataBahu.COLUMN_KEMIRINGAN_DERAJAT,dataBahu.getKemiringanDerajat());
        values.put(DataBahu.COLUMN_KEMIRINGAN_PERSEN,dataBahu.getKemiringanPersen());
        values.put(DataBahu.COLUMN_KEMIRINGAN_ARAH,dataBahu.getKemiringanArah());
        values.put(DataBahu.COLUMN_KEMIRINGAN_KONDISI,dataBahu.getKemiringanKondisi());
        values.put(DataBahu.COLUMN_TIPE_INNER, dataBahu.getTipeBahuInner());
        values.put(DataBahu.COLUMN_LEBAR_INNER,dataBahu.getLebarBahuInner());
        values.put(DataBahu.COLUMN_MATERIAL_INNER,dataBahu.getMaterialInner());
        values.put(DataBahu.COLUMN_GAMBAR,dataBahu.getGambarBahu());
        values.put(DataBahu.COLUMN_GAMBARICON,dataBahu.getGambarBahuicon());
        values.put(DataBahu.COLUMN_LOKASI,dataBahu.getLokasiBahu());

        String where;

        if(tipeSurvey.equals("Normal")){
            where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_POSISI+" = ? AND ( ( "
                    +DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_SEGMENT+ " != ? AND "+DataBahu.COLUMN_SUB_SEGMENT+" >= ? ) OR ( "
                    +DataBahu.COLUMN_SEGMENT+" > ? AND "+DataBahu.COLUMN_SEGMENT+" < ? ) OR ("
                    +DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_SEGMENT+ " != ? AND "+DataBahu.COLUMN_SUB_SEGMENT+"<= ? ) OR ( "
                    +DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_SUB_SEGMENT+" >= ? AND "+DataBahu.COLUMN_SUB_SEGMENT+" <=  ? ))";

        }else{

            where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_POSISI+" = ? AND ( ( "
                    +DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_SEGMENT+ " != ? AND "+DataBahu.COLUMN_SUB_SEGMENT+" <= ? ) OR ( "
                    +DataBahu.COLUMN_SEGMENT+" < ? AND "+DataBahu.COLUMN_SEGMENT+" > ? ) OR ("
                    +DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_SEGMENT+ " != ? AND "+DataBahu.COLUMN_SUB_SEGMENT+">= ? ) OR ( "
                    +DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_SUB_SEGMENT+" <= ? AND "+DataBahu.COLUMN_SUB_SEGMENT+" >=  ? ))";


        }

        database.update(DataBahu.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataBahu.getNoprov()), String.valueOf(dataBahu.getNoruas()), dataBahu.getPosisi(),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir),
                        String.valueOf(segmentAkhir), String.valueOf(segmentAwal),String.valueOf(subSegmentAkhir),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal), String.valueOf(subSegmentAkhir)
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


    //--BATAS TANPA SEGMENT --

    public void updateBahuUn(DataBahu dataBahu) {
        open();

        ContentValues values = new ContentValues();
        values.put(DataBahu.COLUMN_TIPE, dataBahu.getTipeBahu());
        values.put(DataBahu.COLUMN_LEBAR,dataBahu.getLebarBahu());
        values.put(DataBahu.COLUMN_MATERIAL_BAHU,dataBahu.getMaterialBahu());
        values.put(DataBahu.COLUMN_KEMIRINGAN_DERAJAT,dataBahu.getKemiringanDerajat());
        values.put(DataBahu.COLUMN_KEMIRINGAN_PERSEN,dataBahu.getKemiringanPersen());
        values.put(DataBahu.COLUMN_KEMIRINGAN_ARAH,dataBahu.getKemiringanArah());
        values.put(DataBahu.COLUMN_KEMIRINGAN_KONDISI,dataBahu.getKemiringanKondisi());
        values.put(DataBahu.COLUMN_TIPE_INNER, dataBahu.getTipeBahuInner());
        values.put(DataBahu.COLUMN_LEBAR_INNER,dataBahu.getLebarBahuInner());
        values.put(DataBahu.COLUMN_MATERIAL_INNER,dataBahu.getMaterialInner());
        values.put(DataBahu.COLUMN_GAMBAR,dataBahu.getGambarBahu());
        values.put(DataBahu.COLUMN_GAMBARICON,dataBahu.getGambarBahuicon());
        values.put(DataBahu.COLUMN_LOKASI,dataBahu.getLokasiBahu());
        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_SEGMENT+" = ? ";
        database.update(DataBahu.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataBahu.getNoprov()), String.valueOf(dataBahu.getNoruas()), String.valueOf(dataBahu.getNosegment())});

        database.close();
        close();
    }


    public DataBahu getBahuUn(String noprov, String noruas, int segment) {
        //database = dbHelper.getReadableDatabase();
        open();
        DataBahu dataBahu = null;

        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_SEGMENT+" = ? ";
        Cursor cursor = database.query(DataBahu.TABLE_NAME,
                new String[]{DataBahu.COLUMN_ID, DataBahu.COLUMN_NOPROV, DataBahu.COLUMN_RUAS, DataBahu.COLUMN_SEGMENT, DataBahu.COLUMN_SUB_SEGMENT, DataBahu.COLUMN_POSISI,
                        DataBahu.COLUMN_TIPE, DataBahu.COLUMN_LEBAR, DataBahu.COLUMN_MATERIAL_BAHU, DataBahu.COLUMN_KEMIRINGAN_DERAJAT, DataBahu.COLUMN_KEMIRINGAN_PERSEN, DataBahu.COLUMN_KEMIRINGAN_ARAH, DataBahu.COLUMN_KEMIRINGAN_KONDISI,
                        DataBahu.COLUMN_TIPE_INNER,  DataBahu.COLUMN_LEBAR_INNER ,  DataBahu.COLUMN_MATERIAL_INNER, DataBahu.COLUMN_GAMBAR, DataBahu.COLUMN_GAMBARICON, DataBahu.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(segment)}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataBahu = new DataBahu(
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE)),
                    cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_MATERIAL_BAHU)),
                    cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_DERAJAT)),
                    cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_PERSEN)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_ARAH)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_KEMIRINGAN_KONDISI)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE_INNER)),
                    cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR_INNER)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_MATERIAL_INNER)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARICON)),
                    cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();

        database.close();
        close();

        return dataBahu;
    }

    public void setBahuUn(DataBahu dataBahu){
        DataBahu dataBahuKu =  getBahuUn(dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getNosegment());
        if(dataBahuKu!=null){
            updateBahuUn(dataBahu);
        }else{
            insertBahu(dataBahu);
        }

    }





    public int getIndexBahuUn(String noprov, String noruas) {

        String selectQuery = "SELECT max(nosegment) as id FROM databahu where noprov ='"+noprov+"' AND noruas ='"+noruas+"'";
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

        String selectQuery = "delete FROM databahu where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM databahu ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }


    //gakepake amanin
    /*

    public ArrayList<DataBahu> getBahukiri(String noprov, String noruas) {
        ArrayList<DataBahu> notes = new ArrayList<>();
        String tabel;
        tabel = DataBahu.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_POSISI+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataBahu.COLUMN_ID, DataBahu.COLUMN_NOPROV, DataBahu.COLUMN_RUAS, DataBahu.COLUMN_SEGMENT, DataBahu.COLUMN_POSISI, DataBahu.COLUMN_TIPE,  DataBahu.COLUMN_LEBAR, DataBahu.COLUMN_TIPE_INNER,  DataBahu.COLUMN_LEBAR_INNER, DataBahu.COLUMN_GAMBAR, DataBahu.COLUMN_GAMBARICON, DataBahu.COLUMN_LOKASI, DataBahu.COLUMN_KERUSAKAN,  DataBahu.COLUMN_PANJANGKR, DataBahu.COLUMN_DALAMKR, DataBahu.COLUMN_GAMBARKR, DataBahu.COLUMN_GAMBARKRICON, DataBahu.COLUMN_LOKASIKR},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), "kiri"}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataBahu dataBahu = new DataBahu(
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE_INNER)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR_INNER)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_LOKASI)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_KERUSAKAN)),
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_PANJANGKR)),
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_DALAMKR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARKR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARKRICON)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_LOKASIKR)));
                notes.add(dataBahu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }

    public ArrayList<DataBahu> getBahukanan(String noprov, String noruas) {
        ArrayList<DataBahu> notes = new ArrayList<>();
        String tabel;
        tabel = DataBahu.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_POSISI+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataBahu.COLUMN_ID, DataBahu.COLUMN_NOPROV, DataBahu.COLUMN_RUAS, DataBahu.COLUMN_SEGMENT, DataBahu.COLUMN_POSISI, DataBahu.COLUMN_TIPE,  DataBahu.COLUMN_LEBAR, DataBahu.COLUMN_TIPE_INNER,  DataBahu.COLUMN_LEBAR_INNER, DataBahu.COLUMN_GAMBAR, DataBahu.COLUMN_GAMBARICON, DataBahu.COLUMN_LOKASI, DataBahu.COLUMN_KERUSAKAN,  DataBahu.COLUMN_PANJANGKR, DataBahu.COLUMN_DALAMKR, DataBahu.COLUMN_GAMBARKR, DataBahu.COLUMN_GAMBARKRICON, DataBahu.COLUMN_LOKASIKR},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), "kanan"}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataBahu dataBahu = new DataBahu(
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_TIPE_INNER)),
                        cursor.getFloat(cursor.getColumnIndex(DataBahu.COLUMN_LEBAR_INNER)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_LOKASI)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_KERUSAKAN)),
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_PANJANGKR)),
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_DALAMKR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARKR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARKRICON)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_LOKASIKR)));
                notes.add(dataBahu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }

    public void setBahuKolektif(String provinsi, String ruas, int segment, String posisi, String tipe, float lebar, String tipeinner, float lebarinner, String foto, String icon, String lokasi){
        DataBahu dataBahu =  getBahu(provinsi, ruas, segment, posisi);
        if(dataBahu!=null){
            dataBahu.setTipeBahu(tipe);
            dataBahu.setTipeBahuInner(tipeinner);
            dataBahu.setLebarBahu(lebar);
            dataBahu.setLebarBahuInner(lebarinner);
            dataBahu.setGambarBahu(foto);
            dataBahu.setGambarBahuicon(icon);
            dataBahu.setLokasiBahu(lokasi);

            updateBahu(dataBahu);
        }else{
            dataBahu = new DataBahu(0, provinsi, ruas, segment, posisi, tipe, lebar, tipeinner, lebarinner, null,null, null, null,0, 0, null, null, null);
            insertBahu(dataBahu);
        }

    }

    public void setBahuKolektifFoto(String provinsi, String ruas, int segment, String posisi, String tipe, float lebar, String tipeinner, float lebarinner){
        DataBahu dataBahu =  getBahu(provinsi, ruas, segment, posisi);
        if(dataBahu!=null){
            dataBahu.setTipeBahu(tipe);
            dataBahu.setTipeBahuInner(tipeinner);
            dataBahu.setLebarBahu(lebar);
            dataBahu.setLebarBahuInner(lebarinner);

            updateBahu(dataBahu);
        }else{
            dataBahu = new DataBahu(0, provinsi, ruas, segment, posisi, tipe, lebar, tipeinner, lebarinner, null,null, null, null,0, 0, null, null, null);
            insertBahu(dataBahu);
        }

    }

    public ArrayList<DataListImage> getImageBahu(String noprov, String noruas, String posisi) {
        ArrayList<DataListImage> notes = new ArrayList<>();
        String tabel;
        tabel = DataBahu.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataBahu.COLUMN_ID, DataBahu.COLUMN_NOPROV, DataBahu.COLUMN_RUAS, DataBahu.COLUMN_SEGMENT, DataBahu.COLUMN_POSISI, DataBahu.COLUMN_GAMBAR , DataBahu.COLUMN_GAMBARICON, DataBahu.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataListImage databahu = new DataListImage(
                        cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_LOKASI)));
                notes.add(databahu);
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
        tabel = DataBahu.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataBahu.COLUMN_NOPROV, DataBahu.COLUMN_RUAS, DataBahu.COLUMN_SEGMENT, DataBahu.COLUMN_GAMBAR , DataBahu.COLUMN_GAMBARICON, DataBahu.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(noseg), posisi}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                note = new DataListImage(cursor.getInt(cursor.getColumnIndex(DataBahu.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataBahu.COLUMN_LOKASI)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return note;
    }

    public void updateImageBahu(String noprov, String noruas, int segment, String posisi, String image, String iconimage, String lokasi) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBahu.COLUMN_GAMBAR,image);
        values.put(DataBahu.COLUMN_GAMBARICON,iconimage);
        values.put(DataBahu.COLUMN_LOKASI,lokasi);
        String where = DataBahu.COLUMN_NOPROV+" = ? AND "+DataBahu.COLUMN_RUAS+" = ? AND "+DataBahu.COLUMN_SEGMENT+" = ? AND "+DataBahu.COLUMN_POSISI+" = ? ";
        database.update(DataBahu.TABLE_NAME, values, where,
                new String[]{noprov, noruas, String.valueOf(segment), posisi});

        database.close();
        close();
    }

     */


}
