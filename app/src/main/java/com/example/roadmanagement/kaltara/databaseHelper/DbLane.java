package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.FormTerusan.InterfaceLaneTask;
import com.example.roadmanagement.kaltara.GetSemuaImage.DataListImage;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataLane;

import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.SendId;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class DbLane {
    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbLane(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertLane (DataLane dataLane) {
        open();

        ContentValues values = new ContentValues();
        values.put(DataLane.COLUMN_NOPROV,dataLane.getNoprov());
        values.put(DataLane.COLUMN_RUAS,dataLane.getNoruas());
        values.put(DataLane.COLUMN_SEGMENT,dataLane.getNosegment());
        values.put(DataLane.COLUMN_SUB_SEGMENT,dataLane.getSubsegment());
        values.put(DataLane.COLUMN_POSISI,dataLane.getPosisi());
        values.put(DataLane.COLUMN_URUT,dataLane.getUrut());
        values.put(DataLane.COLUMN_LEBAR,dataLane.getLebarLane());
        values.put(DataLane.COLUMN_SC1, dataLane.getSc1());
        values.put(DataLane.COLUMN_KEMIRINGAN_DERAJAT, dataLane.getKemiringanDerajat());
        values.put(DataLane.COLUMN_KEMIRINGAN_PERSEN, dataLane.getKemiringanPersen());
        values.put(DataLane.COLUMN_KEMIRINGAN_ARAH, dataLane.getKemiringanArah());
        values.put(DataLane.COLUMN_KEMIRINGAN_KONDISI, dataLane.getKemiringanKondisi());
        values.put(DataLane.COLUMN_GAMBAR,dataLane.getGambarLane());
        values.put(DataLane.COLUMN_GAMBARICON,dataLane.getGambarLaneicon());
        values.put(DataLane.COLUMN_LOKASI,dataLane.getLokasiLane());

        database.insert(DataLane.TABLE_NAME, null, values);
        database.close();
        close();

    }

    public DataLane getLane(String noprov, String noruas, int nosegment, int subsegment, String posisi, String lajur) {
        database = dbHelper.getReadableDatabase();
        DataLane dataLane = null;


        String where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_SUB_SEGMENT+" = ? AND "+DataLane.COLUMN_POSISI+" = ? AND "+DataLane.COLUMN_URUT+" = ?";
        Cursor cursor = database.query(DataLane.TABLE_NAME,
                new String[]{DataLane.COLUMN_ID, DataLane.COLUMN_NOPROV, DataLane.COLUMN_RUAS, DataLane.COLUMN_SEGMENT, DataLane.COLUMN_SUB_SEGMENT, DataLane.COLUMN_POSISI, DataLane.COLUMN_URUT,
                        DataLane.COLUMN_LEBAR, DataLane.COLUMN_SC1, DataLane.COLUMN_KEMIRINGAN_DERAJAT, DataLane.COLUMN_KEMIRINGAN_PERSEN, DataLane.COLUMN_KEMIRINGAN_ARAH, DataLane.COLUMN_KEMIRINGAN_KONDISI,
                        DataLane.COLUMN_GAMBAR, DataLane.COLUMN_GAMBARICON, DataLane.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment), posisi, lajur}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {

            dataLane = new DataLane(
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataLane.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataLane.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_URUT)),
                    cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_LEBAR)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_SC1)),
                    cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_DERAJAT)),
                    cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_PERSEN)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_ARAH)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_KONDISI)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBARICON)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_LOKASI)));

        }
        cursor.close();
        database.close();
        close();

        return dataLane;
    }

    public void updateLane(DataLane dataLane) {
        open();

        ContentValues values = new ContentValues();
        values.put(DataLane.COLUMN_LEBAR,dataLane.getLebarLane());
        values.put(DataLane.COLUMN_SC1, dataLane.getSc1());
        values.put(DataLane.COLUMN_KEMIRINGAN_DERAJAT, dataLane.getKemiringanDerajat());
        values.put(DataLane.COLUMN_KEMIRINGAN_PERSEN, dataLane.getKemiringanPersen());
        values.put(DataLane.COLUMN_KEMIRINGAN_ARAH, dataLane.getKemiringanArah());
        values.put(DataLane.COLUMN_KEMIRINGAN_KONDISI, dataLane.getKemiringanKondisi());
        values.put(DataLane.COLUMN_GAMBAR,dataLane.getGambarLane());
        values.put(DataLane.COLUMN_GAMBARICON,dataLane.getGambarLaneicon());
        values.put(DataLane.COLUMN_LOKASI,dataLane.getLokasiLane());
        String where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_SUB_SEGMENT+" = ? AND "+DataLane.COLUMN_POSISI+" = ? AND "+DataLane.COLUMN_URUT+" = ?";
        database.update(DataLane.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataLane.getNoprov()), String.valueOf(dataLane.getNoruas()), String.valueOf(dataLane.getNosegment()), String.valueOf(dataLane.getSubsegment()), dataLane.getPosisi(), dataLane.getUrut()});
        database.close();
        close();
    }

    public void updateLaneTerusan(DataLane dataLane) {
        open();

        ContentValues values = new ContentValues();
        values.put(DataLane.COLUMN_LEBAR,dataLane.getLebarLane());
        values.put(DataLane.COLUMN_SC1, dataLane.getSc1());
        values.put(DataLane.COLUMN_KEMIRINGAN_DERAJAT, dataLane.getKemiringanDerajat());
        values.put(DataLane.COLUMN_KEMIRINGAN_PERSEN, dataLane.getKemiringanPersen());
        values.put(DataLane.COLUMN_KEMIRINGAN_ARAH, dataLane.getKemiringanArah());
        values.put(DataLane.COLUMN_KEMIRINGAN_KONDISI, dataLane.getKemiringanKondisi());
        values.put(DataLane.COLUMN_GAMBAR,dataLane.getGambarLane());
        values.put(DataLane.COLUMN_GAMBARICON,dataLane.getGambarLaneicon());
        values.put(DataLane.COLUMN_LOKASI,dataLane.getLokasiLane());
        String where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_SUB_SEGMENT+" = ? AND "+DataLane.COLUMN_POSISI+" = ? AND "+DataLane.COLUMN_URUT+" = ?";
        database.update(DataLane.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataLane.getNoprov()), String.valueOf(dataLane.getNoruas()), String.valueOf(dataLane.getSubsegment()), dataLane.getPosisi(), dataLane.getUrut()});
        database.close();
        close();
    }

    public void resetLane(String provinsi, String ruas, int segment, int subsegment, String posisi, String lajur){
        DataLane dataLane =  getLane(provinsi, ruas, segment, subsegment, posisi, lajur);

        if(dataLane!=null) {
            dataLane.setLokasiLane(null);
            dataLane.setGambarLane(null);
            dataLane.setGambarLaneicon(null);
            updateLane(dataLane);
        }
    }



    public ArrayList<DataLane> getListLane(String noprov, String noruas, String posisi, String lajur) {
        ArrayList<DataLane> notes = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        String where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_POSISI+" = ? AND "+DataLane.COLUMN_URUT+" = ?";
        Cursor cursor = database.query(DataLane.TABLE_NAME,
                new String[]{DataLane.COLUMN_NOPROV, DataLane.COLUMN_RUAS, DataLane.COLUMN_SEGMENT, DataLane.COLUMN_SUB_SEGMENT, DataLane.COLUMN_POSISI, DataLane.COLUMN_URUT,
                        DataLane.COLUMN_LEBAR, DataLane.COLUMN_SC1, DataLane.COLUMN_KEMIRINGAN_DERAJAT, DataLane.COLUMN_KEMIRINGAN_PERSEN, DataLane.COLUMN_KEMIRINGAN_ARAH, DataLane.COLUMN_KEMIRINGAN_KONDISI,
                        DataLane.COLUMN_GAMBAR, DataLane.COLUMN_GAMBARICON, DataLane.COLUMN_LOKASI},
                where,
                new String[]{noprov, noruas, posisi, lajur}, null, null, DataLane.COLUMN_SEGMENT+", "+DataLane.COLUMN_SUB_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                DataLane dataLane = new DataLane(
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataLane.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataLane.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_URUT)),
                        cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_LEBAR)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_SC1)),
                        cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_DERAJAT)),
                        cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_PERSEN)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_ARAH)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_KONDISI)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_LOKASI)));
                notes.add(dataLane);
            } while (cursor.moveToNext());
        }



        cursor.close();
        database.close();
        close();

        return notes;
    }

    public void setLane(DataLane dataLane){

        DataLane dataLaneTemporari =  getLane(dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getSubsegment(), dataLane.getPosisi(), dataLane.getUrut());

        if(dataLaneTemporari!=null){
            updateLane(dataLane);
        }else{
            insertLane(dataLane);
        }

    }


    public void tambahLane(String provinsi, String ruas, int segment, int subsegment, String posisi, String lajur){
        DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, posisi, lajur, 0, null, 0, 0, null, null, null, null, null);
        setLane(dataLane);
    }

    public void hapusLane(String provinsi, String ruas, int segment, int subsegment, String posisi, String lajur){

        DataLane dataLane = getLane(provinsi, ruas, segment, subsegment, posisi, lajur);

        if(dataLane!=null) {
            String tabel = DataLane.TABLE_NAME;
            database = dbHelper.getWritableDatabase();
            String where = DataLane.COLUMN_NOPROV + " = ? AND " + DataLane.COLUMN_RUAS + " = ? AND " + DataLane.COLUMN_SEGMENT + " = ? AND "+ DataLane.COLUMN_SUB_SEGMENT + " = ? AND " + DataLane.COLUMN_POSISI + " = ? AND " + DataLane.COLUMN_URUT + " = ?";
            database.delete(tabel, where, new String[]{String.valueOf(provinsi), ruas, String.valueOf(segment), String.valueOf(subsegment), posisi, lajur});
            database.close();
        }
    }

    public void deleteLane(String noprov, String noruas, float subsegment) {

        String selectQuery = "delete FROM datalane where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+subsegment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }


    public void tutup() {
        dbHelper.close();
    }





    public DataLane getLaneUn(String noprov, String noruas, int noseg, String posisi) {
        database = dbHelper.getReadableDatabase();
        DataLane dataLane = null;


        String where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_POSISI+" = ?";
         Cursor cursor = database.query(DataLane.TABLE_NAME,
                        new String[]{DataLane.COLUMN_ID, DataLane.COLUMN_NOPROV, DataLane.COLUMN_RUAS, DataLane.COLUMN_SEGMENT, DataLane.COLUMN_SUB_SEGMENT, DataLane.COLUMN_POSISI, DataLane.COLUMN_URUT,
                                DataLane.COLUMN_LEBAR, DataLane.COLUMN_SC1, DataLane.COLUMN_KEMIRINGAN_DERAJAT, DataLane.COLUMN_KEMIRINGAN_PERSEN, DataLane.COLUMN_KEMIRINGAN_ARAH, DataLane.COLUMN_KEMIRINGAN_KONDISI,
                                DataLane.COLUMN_GAMBAR, DataLane.COLUMN_GAMBARICON, DataLane.COLUMN_LOKASI},
                        where,
                        new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(noseg), posisi}, null, null, null, null);


         if (cursor.moveToFirst()) {

            dataLane = new DataLane(
                                cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_NOPROV)),
                                cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_RUAS)),
                                cursor.getInt(cursor.getColumnIndex(DataLane.COLUMN_SEGMENT)),
                                cursor.getInt(cursor.getColumnIndex(DataLane.COLUMN_SUB_SEGMENT)),
                                cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_POSISI)),
                                cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_URUT)),
                                cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_LEBAR)),
                                cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_SC1)),
                                cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_DERAJAT)),
                                cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_PERSEN)),
                                cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_ARAH)),
                                cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_KONDISI)),
                                cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBAR)),
                                cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBARICON)),
                                cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_LOKASI)));
        }
        cursor.close();
        database.close();
        close();

        return dataLane;
    }

    public void updateLaneUn(DataLane dataLane) {


        open();

        ContentValues values = new ContentValues();

        values.put(DataLane.COLUMN_LEBAR,dataLane.getLebarLane());
        values.put(DataLane.COLUMN_SC1, dataLane.getSc1());
        values.put(DataLane.COLUMN_KEMIRINGAN_DERAJAT, dataLane.getKemiringanDerajat());
        values.put(DataLane.COLUMN_KEMIRINGAN_PERSEN, dataLane.getKemiringanPersen());
        values.put(DataLane.COLUMN_KEMIRINGAN_ARAH, dataLane.getKemiringanArah());
        values.put(DataLane.COLUMN_KEMIRINGAN_KONDISI, dataLane.getKemiringanKondisi());
        values.put(DataLane.COLUMN_GAMBAR,dataLane.getGambarLane());
        values.put(DataLane.COLUMN_GAMBARICON,dataLane.getGambarLaneicon());
        values.put(DataLane.COLUMN_LOKASI,dataLane.getLokasiLane());

        String where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_POSISI+" = ?";
        database.update(DataLane.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataLane.getNoprov()), String.valueOf(dataLane.getNoruas()), String.valueOf(dataLane.getNosegment()),  dataLane.getPosisi()});
        database.close();
        close();
    }

    public void hapusLaneUnd(DataLane dataLane1){

        DataLane dataLane = getLaneUn(dataLane1.getNoprov(), dataLane1.getNoruas(), dataLane1.getNosegment(), dataLane1.getPosisi());

        if(dataLane!=null) {
            String tabel = DataLane.TABLE_NAME;
            database = dbHelper.getWritableDatabase();
            String where = DataLane.COLUMN_NOPROV + " = ? AND " + DataLane.COLUMN_RUAS + " = ? AND " + DataLane.COLUMN_SEGMENT + " = ? AND " + DataLane.COLUMN_POSISI + " = ?";
            database.delete(tabel, where, new String[]{dataLane.getNoprov(), dataLane.getNoruas(), String.valueOf(dataLane.getNosegment()), dataLane.getPosisi()});
            database.close();
        }
    }


    public void setLaneUn(DataLane dataLane, int aksi){
        DataLane dataLaneun =  getLaneUn(dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getPosisi());

        if(aksi==1) {
            if (dataLaneun == null) {
                insertLane(dataLane);
            }
        }else if(aksi==2){

           hapusLaneUnd(dataLane);

        } else if(aksi==3){
            if (dataLaneun != null) {
                updateLaneUn(dataLane);
            } else {
                insertLane(dataLane);
            }
        }

    }

    public int getIndexLaneUn(String noprov, String noruas) {

        String selectQuery = "SELECT max(nosegment) as nosegment FROM datalane where noprov ='"+noprov+"' AND noruas ='"+noruas+"'";
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("nosegment"));

        cursor.close();
        database.close();
        close();

        return maxid+1;
    }


    public int getJumlahPosisi(String noprov, String noruas, float subsegment, String posisi) {

        String selectQuery = "SELECT count(nosegment) as nosegment FROM datalane where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment="+subsegment+" AND POSISI LIKE '"+posisi+"%'";
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("subsegment"));

        cursor.close();
        database.close();
        close();

        return maxid;
    }

    public int getJumlahPosisiUnd(String noprov, String noruas, int segment, String posisi) {

        String selectQuery = "SELECT count(nosegment) as nosegment FROM datalane where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND nosegment="+segment+" AND POSISI LIKE '"+posisi+"%'";
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("nosegment"));

        cursor.close();
        database.close();
        close();

        return maxid;
    }

    public int getMaxLajur(String noprov, String noruas, String posisi) {

        int maxlajur = 0;
        String lajur = null;

        ArrayList<Integer> arrayLajur = new ArrayList<>();

        int index = 0;

        String selectQuery = "SELECT laneurut as lajur FROM datalane where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND posisi='"+posisi+"' GROUP BY laneurut ORDER BY laneurut ASC ";
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                //lajur = cursor.getString(cursor.getColumnIndex("lajur"));
                arrayLajur.add(Integer.valueOf(cursor.getString(cursor.getColumnIndex("lajur")).substring(1)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        if(arrayLajur.size()>0) {

          maxlajur   = Collections.max(arrayLajur);

        }


        return maxlajur;
    }


    //TERUSASN


    public DataLane getLaneTerusan(String noprov, String noruas, float subsegment, String posisi, String lajur) {

        database = dbHelper.getReadableDatabase();
        DataLane dataLane = null;


        String where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_SUB_SEGMENT+" = ? AND "+DataLane.COLUMN_POSISI+" = ? AND "+DataLane.COLUMN_URUT+" = ?";
        Cursor cursor = database.query(DataLane.TABLE_NAME,
                new String[]{DataLane.COLUMN_ID, DataLane.COLUMN_NOPROV, DataLane.COLUMN_RUAS, DataLane.COLUMN_SEGMENT, DataLane.COLUMN_SUB_SEGMENT, DataLane.COLUMN_POSISI, DataLane.COLUMN_URUT,
                        DataLane.COLUMN_LEBAR, DataLane.COLUMN_SC1, DataLane.COLUMN_KEMIRINGAN_DERAJAT, DataLane.COLUMN_KEMIRINGAN_PERSEN, DataLane.COLUMN_KEMIRINGAN_ARAH, DataLane.COLUMN_KEMIRINGAN_KONDISI,
                        DataLane.COLUMN_GAMBAR, DataLane.COLUMN_GAMBARICON, DataLane.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(subsegment), posisi, lajur}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataLane = new DataLane(
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataLane.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataLane.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_URUT)),
                    cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_LEBAR)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_SC1)),
                    cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_DERAJAT)),
                    cursor.getFloat(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_PERSEN)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_ARAH)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_KEMIRINGAN_KONDISI)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBARICON)),
                    cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_LOKASI)));

        }
        cursor.close();
        database.close();
        close();

        return dataLane;
    }

    public void saveLaneNormal(DataLane dataLane, int segmentAwal, int subSegmentAwal, int segmentAkhir, int subSegmentAkhir, String tipeSurvey){

        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataLane.COLUMN_LEBAR,dataLane.getLebarLane());
        values.put(DataLane.COLUMN_SC1, dataLane.getSc1());
        values.put(DataLane.COLUMN_KEMIRINGAN_DERAJAT, dataLane.getKemiringanDerajat());
        values.put(DataLane.COLUMN_KEMIRINGAN_PERSEN, dataLane.getKemiringanPersen());
        values.put(DataLane.COLUMN_KEMIRINGAN_ARAH, dataLane.getKemiringanArah());
        values.put(DataLane.COLUMN_KEMIRINGAN_KONDISI, dataLane.getKemiringanKondisi());
        values.put(DataLane.COLUMN_GAMBAR,dataLane.getGambarLane());
        values.put(DataLane.COLUMN_GAMBARICON,dataLane.getGambarLaneicon());
        values.put(DataLane.COLUMN_LOKASI,dataLane.getLokasiLane());

        String where;

        if(tipeSurvey.equals("Normal")){
            where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_POSISI+" = ? AND "+DataLane.COLUMN_URUT+" = ? AND ( ( "
                    +DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_SEGMENT+ " != ? AND "+DataLane.COLUMN_SUB_SEGMENT+" >= ? ) OR ( "
                    +DataLane.COLUMN_SEGMENT+" > ? AND "+DataLane.COLUMN_SEGMENT+" < ? ) OR ("
                    +DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_SEGMENT+ " != ? AND "+DataLane.COLUMN_SUB_SEGMENT+"<= ? ) OR ( "
                    +DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_SUB_SEGMENT+" >= ? AND "+DataLane.COLUMN_SUB_SEGMENT+" <=  ? ))";

        }else{

            where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_POSISI+" = ? AND "+DataLane.COLUMN_URUT+" = ? AND ( ( "
                    +DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_SEGMENT+ " != ? AND "+DataLane.COLUMN_SUB_SEGMENT+" <= ? ) OR ( "
                    +DataLane.COLUMN_SEGMENT+" < ? AND "+DataLane.COLUMN_SEGMENT+" > ? ) OR ("
                    +DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_SEGMENT+ " != ? AND "+DataLane.COLUMN_SUB_SEGMENT+">= ? ) OR ( "
                    +DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_SUB_SEGMENT+" <= ? AND "+DataLane.COLUMN_SUB_SEGMENT+" >=  ? ))";


        }

        database.update(DataLane.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataLane.getNoprov()), String.valueOf(dataLane.getNoruas()), dataLane.getPosisi(), dataLane.getUrut(),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir),
                        String.valueOf(segmentAkhir), String.valueOf(segmentAwal),String.valueOf(subSegmentAkhir),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal), String.valueOf(subSegmentAkhir)
        });

        database.close();
        close();


    }

    public void setLaneTerusan(DataLane dataLane, String tipeSurvey, int indexSegment, int indexSub, int segmentAwal, int subAwal, int segmentAkhir, int subAkhir, InterfaceLaneTask interfaceLaneTask){

        DataLane dataLaneNormal = getLane(dataLane.getNoprov(), dataLane.getNoruas(), indexSegment, indexSub, dataLane.getPosisi(), dataLane.getUrut());

        if(dataLaneNormal!=null){
            dataLane.setNosegment(indexSegment);
            dataLane.setSubsegment(indexSub);
            updateLane(dataLane);
            Log.d("UPDATELANE", "Update - "+indexSegment+"."+indexSub);


            if(segmentAwal == 0){
                segmentAwal = indexSegment;
                subAwal = indexSub;
            }

        }else{

            if(segmentAwal != 0){

                segmentAkhir = getMinSegment(tipeSurvey, indexSegment, indexSub);
                subAkhir = getMinSub(tipeSurvey, indexSub);


            }
        }

        interfaceLaneTask.sendData(segmentAwal, subAwal, segmentAkhir, subAkhir);



    }



    public int getMaksSegmentLajur(String tipeSurvey, String noprov, String noruas, String posisi, String lajur) {

        String selectQuery;

        if(tipeSurvey.equals("Normal")) {
            selectQuery = "SELECT max(nosegment) as segment FROM datalane where noprov ='" + noprov + "' AND noruas ='" + noruas + "' AND posisi='" + posisi + "' AND laneurut='" + lajur + "'";
        }else{
            selectQuery = "SELECT min(nosegment) as segment FROM datalane where noprov ='" + noprov + "' AND noruas ='" + noruas + "' AND posisi='" + posisi + "' AND laneurut='" + lajur + "'";
        }
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("segment"));

        cursor.close();
        database.close();
        close();

        return maxid;
    }

    public int getMaksSubsegLajur(String tipeSurvey, String noprov, String noruas, int noseg, String posisi, String lajur) {

        String selectQuery;

        if(tipeSurvey.equals("Normal")) {
            selectQuery = "SELECT max(subsegment) as subsegment FROM datalane where noprov ='" + noprov + "' AND noruas ='" + noruas + "' AND nosegment='" + noseg + "' AND posisi='" + posisi + "' AND laneurut='" + lajur + "' ";
        }else {
            selectQuery = "SELECT min(subsegment) as subsegment FROM datalane where noprov ='" + noprov + "' AND noruas ='" + noruas + "' AND nosegment='" + noseg + "' AND posisi='" + posisi + "' AND laneurut='" + lajur + "' ";
        }

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("subsegment"));

        cursor.close();
        database.close();
        close();

        return maxid;
    }

    private int getMinSegment(String jenis, int segment, int subsegment){

        if(jenis.equals("Normal")){

            if(subsegment>0){
                return segment;
            }else{
                return segment-1;
            }

        }else{

            if(subsegment == 9){
                return segment+1;
            }else{
                return segment;
            }

        }

    }

    private int getMinSub(String jenis, int subsegment){

        if(jenis.equals("Normal")){

            if(subsegment>0){
                return subsegment-1;
            }else{
                return 9;
            }

        }else{

            if(subsegment<9){
                return subsegment+1;
            }else{
                return 0;
            }

        }

    }

    /*

    public void setLaneKolektif(String provinsi, String ruas, int segment, String posisi, String letak, String sc, float panjang, String foto, String icon, String lokasi,  SendId id){
        DataLane dataLane =  getLane(provinsi, ruas, segment, letak, posisi);


        if(dataLane!=null){
            // Toast.makeText(context, "update lane", Toast.LENGTH_SHORT).show();
            dataLane.setSc1(sc);
            dataLane.setLebarLane(panjang);
            dataLane.setGambarLane(foto);
            dataLane.setGambarLaneicon(icon);
            dataLane.setLokasiLane(lokasi);
            updateLane(dataLane);

            id.hapusGambar(1);
        }else{
            id.hapusGambar(0);
        }

    }

    public void setLaneKolektifFoto(String provinsi, String ruas, int segment, String posisi, String letak, String sc, float panjang,  SendId id){
        DataLane dataLane =  getLane(provinsi, ruas, segment, letak, posisi);


        if(dataLane!=null){
            // Toast.makeText(context, "update lane", Toast.LENGTH_SHORT).show();
            dataLane.setSc1(sc);
            dataLane.setLebarLane(panjang);
            updateLane(dataLane);

            id.hapusGambar(1);
        }else{
            id.hapusGambar(0);
        }

    }

    public void setLaneku(DataLane dataLane){
        DataLane dataLane1 = getLane(dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getUrut(), dataLane.getPosisi());
        if(dataLane1==null){
            inserLane(dataLane);
        }else{
            updateLane(dataLane);
        }
    }

    public ArrayList<DataListImage> getImageLane(String noprov, String noruas, String posisi) {
        ArrayList<DataListImage> notes = new ArrayList<>();
        String tabel;
        tabel = DataLane.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_URUT+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataLane.COLUMN_ID, DataLane.COLUMN_NOPROV, DataLane.COLUMN_RUAS, DataLane.COLUMN_SEGMENT, DataLane.COLUMN_URUT,  DataLane.COLUMN_GAMBAR , DataLane.COLUMN_GAMBARICON, DataLane.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataListImage dataLane = new DataListImage(
                        cursor.getInt(cursor.getColumnIndex(DataLane.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_LOKASI)));
                notes.add(dataLane);
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
        tabel = DataLane.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_URUT+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataLane.COLUMN_NOPROV, DataLane.COLUMN_RUAS, DataLane.COLUMN_SEGMENT, DataLane.COLUMN_GAMBAR , DataLane.COLUMN_GAMBARICON, DataLane.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(noseg), posisi}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                note = new DataListImage(cursor.getInt(cursor.getColumnIndex(DataLane.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_GAMBARICON)),
                        cursor.getString(cursor.getColumnIndex(DataLane.COLUMN_LOKASI)));
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
        values.put(DataLane.COLUMN_GAMBAR,image);
        values.put(DataLane.COLUMN_GAMBARICON,iconimage);
        values.put(DataLane.COLUMN_LOKASI,lokasi);
        String where = DataLane.COLUMN_NOPROV+" = ? AND "+DataLane.COLUMN_RUAS+" = ? AND "+DataLane.COLUMN_SEGMENT+" = ? AND "+DataLane.COLUMN_URUT+" = ? ";
        database.update(DataLane.TABLE_NAME, values, where,
                new String[]{noprov, noruas, String.valueOf(segment), posisi});

        database.close();
        close();
    }


public void saveLaneTerusan(DataSegmen dataSegmen, String posisi, String lajur, int segmentAwal, int segmentAkhir, String tipeSurvey){


        if(tipeSurvey.equals("Normal")){

            for(int i=segmentAwal; i<=segmentAkhir; i++){

                DataLane dataLane = getLaneTerusan(dataSegmen.getNoprov(), dataSegmen.getNoruas(), i, posisi, lajur);
                setLaneku(dataLane);

            }


        }else{

            for(int i=segmentAwal; i>=segmentAkhir; i--){

                DataLane dataLane = getLaneTerusan(dataSegmen.getNoprov(), dataSegmen.getNoruas(), i, posisi, lajur);
                setLaneku(dataLane);

            }


        }

    }


     */

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM datalane where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM datalane ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }


}
