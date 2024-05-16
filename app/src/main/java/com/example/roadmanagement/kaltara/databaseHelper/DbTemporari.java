package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DbTemporari {
    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;


    public DbTemporari(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);

    }

    private void open() throws SQLiteException {
            database = dbHelper.getWritableDatabase();

    }

    private void close() {
        dbHelper.close();
    }

    public void insertTemporari (DataTemporari dataTemporari) {
        open();

        ContentValues values = new ContentValues();
        values.put(DataTemporari.COLUMN_NOPROV,dataTemporari.getNoprov());
        values.put(DataTemporari.COLUMN_RUAS,dataTemporari.getNoruas());
        values.put(DataTemporari.COLUMN_SEGMENT,dataTemporari.getNosegment());
        values.put(DataTemporari.COLUMN_SUB_SEGMENT,dataTemporari.getSubsegment());
        values.put(DataTemporari.COLUMN_TIPE,dataTemporari.getTipe());
        values.put(DataTemporari.COLUMN_POSISI,dataTemporari.getPosisi());
        values.put(DataTemporari.COLUMN_JENIS,dataTemporari.getJenis());
        values.put(DataTemporari.COLUMN_SEGMENTAKHIR,dataTemporari.getSegmentakhir());
        values.put(DataTemporari.COLUMN_SUB_SEGMENTAKHIR,dataTemporari.getSubsegmentakhir());
        values.put(DataTemporari.COLUMN_URUT,dataTemporari.getUrut());
        values.put(DataTemporari.COLUMN_FOTO,dataTemporari.getFoto());
        values.put(DataTemporari.COLUMN_STATUS,dataTemporari.getStatus());


        database.insert(dataTemporari.TABLE_NAME, null, values);
        database.close();
        close();


    }

    public DataTemporari getTemporari(String noprov, String noruas, int segment, int subsegment, String tipe, String posisi, String urut) {
        String tabel;
        database = dbHelper.getReadableDatabase();
        DataTemporari dataTemporari = null;

        tabel = DataTemporari.TABLE_NAME;


        String where = DataTemporari.COLUMN_NOPROV+" = ? AND "+DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_SEGMENT+" = ? AND "+DataTemporari.COLUMN_SUB_SEGMENT+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_POSISI+" = ? AND "+DataTemporari.COLUMN_URUT+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_NOPROV, DataTemporari.COLUMN_RUAS, DataTemporari.COLUMN_SEGMENT, DataTemporari.COLUMN_SUB_SEGMENT, DataTemporari.COLUMN_TIPE, DataTemporari.COLUMN_POSISI, DataTemporari.COLUMN_URUT, DataTemporari.COLUMN_JENIS, DataTemporari.COLUMN_SEGMENTAKHIR, DataTemporari.COLUMN_SUB_SEGMENTAKHIR, DataTemporari.COLUMN_FOTO, DataTemporari.COLUMN_STATUS},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(segment), String.valueOf(subsegment), tipe, posisi, urut}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataTemporari = new DataTemporari(
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_TIPE)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_URUT)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_JENIS)),
                    cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENTAKHIR)),
                    cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENTAKHIR)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_FOTO)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_STATUS)));

            // close the db connection

        }
        cursor.close();
        database.close();
        close();


        return dataTemporari;
    }

    public void updateTemporari(DataTemporari dataTemporari) {
        open();

        String tabel;

        tabel = DataTemporari.TABLE_NAME;

        ContentValues values = new ContentValues();
        values.put(DataTemporari.COLUMN_TIPE,dataTemporari.getTipe());
        values.put(DataTemporari.COLUMN_POSISI,dataTemporari.getPosisi());
        values.put(DataTemporari.COLUMN_URUT,dataTemporari.getUrut());
        values.put(DataTemporari.COLUMN_JENIS,dataTemporari.getJenis());
        values.put(DataTemporari.COLUMN_SEGMENTAKHIR,dataTemporari.getSegmentakhir());
        values.put(DataTemporari.COLUMN_SUB_SEGMENTAKHIR,dataTemporari.getSubsegmentakhir());
        values.put(DataTemporari.COLUMN_FOTO,dataTemporari.getFoto());
        values.put(DataTemporari.COLUMN_STATUS,dataTemporari.getStatus());
        String where = DataTemporari.COLUMN_NOPROV+" = ? AND "+DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_SEGMENT+" = ? AND "+DataTemporari.COLUMN_SUB_SEGMENT+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_POSISI+" = ? AND "+DataTemporari.COLUMN_URUT+" = ?";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataTemporari.getNoprov()), String.valueOf(dataTemporari.getNoruas()), String.valueOf(dataTemporari.getNosegment()), String.valueOf(dataTemporari.getSubsegment()), dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut()});

        database.close();
        close();
    }

    public ArrayList<DataTemporari> getAllTemporari(String status) {
        ArrayList<DataTemporari> notes = new ArrayList<>();
        String tabel;
        tabel = DataTemporari.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataTemporari.COLUMN_STATUS+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_NOPROV, DataTemporari.COLUMN_RUAS, DataTemporari.COLUMN_SEGMENT, DataTemporari.COLUMN_SUB_SEGMENT, DataTemporari.COLUMN_TIPE, DataTemporari.COLUMN_POSISI, DataTemporari.COLUMN_URUT,
                        DataTemporari.COLUMN_JENIS, DataTemporari.COLUMN_SEGMENTAKHIR, DataTemporari.COLUMN_SUB_SEGMENTAKHIR,  DataTemporari.COLUMN_FOTO, DataTemporari.COLUMN_STATUS},
                where,
                new String[]{status}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataTemporari dataTemporari = new DataTemporari(
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_TIPE)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_URUT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_JENIS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENTAKHIR)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENTAKHIR)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_FOTO)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_STATUS)));
                notes.add(dataTemporari);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();


        return notes;
    }



    public ArrayList<DataTemporari> getSinkronUnform(String ruas, String tipe, String posisi) {
        ArrayList<DataTemporari> notes = new ArrayList<>();
        String tabel;
        String[] parameter;
        String where;
        String urut = "Unidentified";
        String order;

        if(tipe.equals("Median")){
            where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_JENIS+" = ?";
            parameter = new String[]{ruas, tipe, urut};
            order = DataTemporari.COLUMN_SEGMENT;
        }else if(posisi != null){
            where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_POSISI+" = ? AND "+DataTemporari.COLUMN_JENIS+" = ?";
            parameter = new String[]{ruas, tipe, posisi, urut};
            order = DataTemporari.COLUMN_SEGMENT;
        }else{
            where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_JENIS+" = ?";
            parameter = new String[]{ruas, tipe, urut};
            order = DataTemporari.COLUMN_SEGMENT;
        }

        tabel = DataTemporari.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_NOPROV, DataTemporari.COLUMN_RUAS, DataTemporari.COLUMN_SEGMENT, DataTemporari.COLUMN_SUB_SEGMENT, DataTemporari.COLUMN_TIPE, DataTemporari.COLUMN_POSISI, DataTemporari.COLUMN_URUT,
                        DataTemporari.COLUMN_JENIS, DataTemporari.COLUMN_SEGMENTAKHIR, DataTemporari.COLUMN_SUB_SEGMENTAKHIR, DataTemporari.COLUMN_FOTO, DataTemporari.COLUMN_STATUS},
                where, parameter, null, null, order, null);


        if (cursor.moveToFirst()) {
            do {
                DataTemporari dataTemporari = new DataTemporari(
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_TIPE)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_URUT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_JENIS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENTAKHIR)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENTAKHIR)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_FOTO)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_STATUS)));

                notes.add(dataTemporari);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();


        return notes;
    }


    public List<String> getRuastempo(String status) {
        List<String> notes = new ArrayList<>();
        String tabel;
        tabel = DataTemporari.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataTemporari.COLUMN_STATUS+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_NOPROV, DataTemporari.COLUMN_RUAS, DataTemporari.COLUMN_SEGMENT, DataTemporari.COLUMN_SUB_SEGMENT, DataTemporari.COLUMN_TIPE, DataTemporari.COLUMN_POSISI, DataTemporari.COLUMN_URUT, DataTemporari.COLUMN_JENIS,
                        DataTemporari.COLUMN_SEGMENTAKHIR, DataTemporari.COLUMN_SUB_SEGMENTAKHIR, DataTemporari.COLUMN_FOTO, DataTemporari.COLUMN_STATUS},
                where,
                new String[]{status}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                String a = cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_RUAS));
                if(!notes.contains(a)) {
                    notes.add(a);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();


        return notes;
    }

    public String getJumlahtempo(String ruas, String tipe, String foto, String status) {
        List<String> notes = new ArrayList<>();
        String tabel;
        tabel = DataTemporari.TABLE_NAME;
        database = dbHelper.getReadableDatabase();

        String where;
        String[] parameter;

        if(foto == null){
            where = DataTemporari.COLUMN_RUAS + " = ? AND " + DataTemporari.COLUMN_TIPE + " = ? AND " + DataTemporari.COLUMN_STATUS + " = ? ";
            parameter = new String[]{ruas, tipe, status};
        }else {

            where = DataTemporari.COLUMN_RUAS + " = ? AND " + DataTemporari.COLUMN_TIPE + " = ? AND " + DataTemporari.COLUMN_FOTO + " = ? AND " + DataTemporari.COLUMN_STATUS + " = ? ";
            parameter = new String[]{ruas, tipe, foto, status};
        }
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_NOPROV, DataTemporari.COLUMN_RUAS, DataTemporari.COLUMN_SEGMENT, DataTemporari.COLUMN_TIPE, DataTemporari.COLUMN_POSISI, DataTemporari.COLUMN_URUT,  DataTemporari.COLUMN_JENIS, DataTemporari.COLUMN_SEGMENTAKHIR, DataTemporari.COLUMN_FOTO, DataTemporari.COLUMN_STATUS},
                where,
                parameter, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                notes.add("a");
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();


        return String.valueOf(notes.size());
    }




    public void postTemporari(DataTemporari dataTemporari){
        DataTemporari dataTemporari2 = getTemporari(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut());

        if(dataTemporari2!=null){
            updateTemporari(dataTemporari);
        }else{
            insertTemporari(dataTemporari);
        }

    }


    public void postTemporariUn(DataTemporari dataTemporari){

        DataTemporari dataTemporari2 = getTemporari(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getNosegment(), dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut());

        if(dataTemporari2==null){
            insertTemporari(dataTemporari);
        }else{
            updateTemporari(dataTemporari);
        }

    }



    public void hapusTemporari(String provinsi, String ruas, int segment, int subSegment, String tipe, String posisi, String lajur){

        DataTemporari dataTemporari = getTemporari(provinsi, ruas, segment, subSegment, tipe, posisi, lajur);
        if(dataTemporari!=null) {
            String tabel = DataTemporari.TABLE_NAME;
            database = dbHelper.getWritableDatabase();
            String where = DataTemporari.COLUMN_NOPROV + " = ? AND " + DataTemporari.COLUMN_RUAS + " = ? AND "+ DataTemporari.COLUMN_SEGMENT + " = ? AND " + DataTemporari.COLUMN_SUB_SEGMENT + " = ? AND " + DataTemporari.COLUMN_TIPE + " = ? AND " +DataTemporari.COLUMN_POSISI + " = ? AND " + DataTemporari.COLUMN_URUT + " = ?";
            database.delete(tabel, where, new String[]{String.valueOf(provinsi), ruas, String.valueOf(segment), String.valueOf(subSegment), tipe,  posisi, lajur});
            database.close();
        }
    }


    public void tutup() {
        dbHelper.close();
    }



    public void saveTemporariTerusan(String tipe, String provinsi, String ruas, String posisi, String urut, int segmentAwal, int subSegmentAwal, int segmentAkhir, int subSegmentAkhir, String jenis, String foto){

        DataTemporari dTempo = new DataTemporari(provinsi, ruas, segmentAwal, subSegmentAwal, tipe, posisi, urut, jenis, segmentAkhir, subSegmentAkhir, foto, "0");
        DataTemporari dReturn = selectTemporariTerusan(dTempo);


        if(dReturn!=null){
            updateTemporariTerusan(dTempo);
        }else{
            insertTemporari(dTempo);
        }

    }

    private DataTemporari selectTemporariTerusan(DataTemporari dataTemporari){

        String tabel;
        database = dbHelper.getReadableDatabase();
        DataTemporari dataTemporariSelect = null;

        tabel = DataTemporari.TABLE_NAME;


        String where = DataTemporari.COLUMN_NOPROV+" = ? AND "+DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_SEGMENT+" = ? AND "+DataTemporari.COLUMN_SUB_SEGMENT+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_POSISI+" = ? AND "+DataTemporari.COLUMN_URUT+" = ? AND "+DataTemporari.COLUMN_JENIS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_NOPROV, DataTemporari.COLUMN_RUAS, DataTemporari.COLUMN_SEGMENT, DataTemporari.COLUMN_SUB_SEGMENT, DataTemporari.COLUMN_TIPE, DataTemporari.COLUMN_POSISI, DataTemporari.COLUMN_URUT,
                        DataTemporari.COLUMN_JENIS, DataTemporari.COLUMN_SEGMENTAKHIR, DataTemporari.COLUMN_SUB_SEGMENTAKHIR, DataTemporari.COLUMN_FOTO, DataTemporari.COLUMN_STATUS},
                where,
                new String[]{dataTemporari.getNoprov(), dataTemporari.getNoruas(), String.valueOf(dataTemporari.getNosegment()), String.valueOf(dataTemporari.getSubsegment()), dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut(), dataTemporari.getJenis()}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataTemporariSelect = new DataTemporari(
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_TIPE)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_URUT)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_JENIS)),
                    cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENTAKHIR)),
                    cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENTAKHIR)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_FOTO)),
                    cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_STATUS)));

            // close the db connection

        }
        cursor.close();
        database.close();
        close();


        return dataTemporariSelect;

    }

    private void updateTemporariTerusan(DataTemporari dataTemporari) {

        open();
        String tabel;
        tabel = DataTemporari.TABLE_NAME;

        ContentValues values = new ContentValues();
        values.put(DataTemporari.COLUMN_TIPE,dataTemporari.getTipe());
        values.put(DataTemporari.COLUMN_POSISI,dataTemporari.getPosisi());
        values.put(DataTemporari.COLUMN_URUT,dataTemporari.getUrut());
        values.put(DataTemporari.COLUMN_SEGMENTAKHIR,dataTemporari.getSegmentakhir());
        values.put(DataTemporari.COLUMN_SUB_SEGMENTAKHIR,dataTemporari.getSubsegmentakhir());
        values.put(DataTemporari.COLUMN_FOTO,dataTemporari.getFoto());
        values.put(DataTemporari.COLUMN_STATUS,dataTemporari.getStatus());

        String where = DataTemporari.COLUMN_NOPROV+" = ? AND "+DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_SEGMENT+" = ? AND "+DataTemporari.COLUMN_SUB_SEGMENT+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_POSISI+" = ? AND "+DataTemporari.COLUMN_URUT+" = ? AND "+DataTemporari.COLUMN_JENIS+" = ? ";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataTemporari.getNoprov()), String.valueOf(dataTemporari.getNoruas()), String.valueOf(dataTemporari.getNosegment()), String.valueOf(dataTemporari.getSubsegment()), dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut(), dataTemporari.getJenis()});

        database.close();
        close();
    }

    public void deleteAllTemporari(String noprov, String noruas) {

        String selectQuery = "delete FROM datasementara where noprov ='"+noprov+"' AND noruas ='"+noruas+"'";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void updateTemporariDetail(String tipeSurvey, String provinsi, String ruas) {

        open();
        String tabel;
        tabel = DataTemporari.TABLE_NAME;

        ContentValues values = new ContentValues();
        values.put(DataTemporari.COLUMN_JENIS, tipeSurvey);

        String where = DataTemporari.COLUMN_NOPROV+" = ? AND "+DataTemporari.COLUMN_RUAS+" = ? ";
        database.update(tabel, values, where,
                new String[]{provinsi, ruas});

        database.close();
        close();

    }

    public void deleteTemporariPosisi(String noprov, String noruas, String tipe, String posisi) {

        String selectQuery = "delete FROM datasementara where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND tipe ='"+tipe+"' AND posisi ='"+posisi+"'";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void deleteTemporariJenis(String noprov, String noruas, String jenis) {

        String selectQuery = "delete FROM datasementara where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND jenis ='"+jenis+"'";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void deleteDetailSegment(String noprov, String noruas, float subsegment) {

        String selectQuery = "delete FROM datasementara where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment ="+String.valueOf(subsegment)+" AND (posisi ='Tambah' or posisi = 'Hapus') ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public String cekSurveyTemporari(String noprov, String noruas) {

        String survey;

        String selectQuery = "SELECT  jenis, count(nosegment) as id FROM datasementara where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND (jenis = 'Normal' or jenis = 'Opposite') group by jenis";

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor!=null && cursor.moveToFirst()){
            survey = cursor.getString(cursor.getColumnIndex("jenis"));
        }else{
            survey = "Detail";
        }



        cursor.close();
        database.close();
        close();


        return survey;
    }

    public String cekSurveyTemporariTipe(String noprov, String noruas, String tipe, String posisi, String urut) {

        String survey;

        String selectQuery = "SELECT  jenis, count(nosegment) as id FROM datasementara where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND tipe ='"+tipe+"' AND posisi ='"+posisi+"' AND urut  ='"+urut+"' AND (jenis = 'Normal' or jenis = 'Opposite') group by jenis";

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor!=null && cursor.moveToFirst()){
            survey = cursor.getString(cursor.getColumnIndex("jenis"));
        }else{
            survey = "Detail";
        }



        cursor.close();
        database.close();
        close();


        return survey;
    }

    public ArrayList<Integer> getSegmentTerusan(String noprov, String noruas, String tipe, String posisi, String urut, String jenis){

        String tabel;
        ArrayList<Integer> listReturn = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        tabel = DataTemporari.TABLE_NAME;


        String where = DataTemporari.COLUMN_NOPROV+" = ? AND "+DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_POSISI+" = ? AND "+DataTemporari.COLUMN_URUT+" = ? AND "+DataTemporari.COLUMN_JENIS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_SUB_SEGMENT},
                where,
                new String[]{noprov, noruas, tipe, posisi, urut, jenis}, null, null, null, null);


        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                listReturn.add(cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENT)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        if(jenis.equals("Normal")) {
            Collections.sort(listReturn);
        }else{
            Collections.sort(listReturn, Collections.<Integer>reverseOrder());
        }


        return listReturn;

    }



    public ArrayList<DataTemporari> getListTemporari(String noprov, String ruas, String tipe, String posisi, String urut, String foto, String status, String sinkron) {
        ArrayList<DataTemporari> notes = new ArrayList<>();
        String tabel;
        String[] parameter;
        String where;
        String order;

        where = DataTemporari.COLUMN_NOPROV+" = ? AND "+DataTemporari.COLUMN_RUAS+" = ? ";
        parameter = new String[]{noprov, ruas};

        if(tipe!=null){
            where = where+"AND "+DataTemporari.COLUMN_TIPE+" = ? ";
            parameter = add_element(parameter.length, parameter, tipe);
        }

        if(posisi!=null){

            where = where+"AND "+DataTemporari.COLUMN_POSISI+" = ? ";
            parameter = add_element(parameter.length, parameter, posisi);

        }

        if(urut!=null){

            where = where+"AND "+DataTemporari.COLUMN_URUT+" = ? ";
            parameter = add_element(parameter.length, parameter, urut);

        }

        if(foto!=null){

            where = where+"AND "+DataTemporari.COLUMN_FOTO+" = ? ";
            parameter = add_element(parameter.length, parameter, foto);

        }

        if(status!=null){

            where = where+"AND "+DataTemporari.COLUMN_STATUS+" = ? ";
            parameter = add_element(parameter.length, parameter, status);

        }

        if(sinkron.equals("1")){
            where = where+"AND "+DataTemporari.COLUMN_JENIS+"  != ? ";
            parameter = add_element(parameter.length, parameter, "Detail");
            order = DataTemporari.COLUMN_TIPE+" DESC";
        }else if(sinkron.equals("Normal")) {

            order = DataTemporari.COLUMN_SEGMENT+", "+DataTemporari.COLUMN_SUB_SEGMENT;

        }else if(sinkron.equals("Opposite")){

            order = DataTemporari.COLUMN_SEGMENT+" DESC, "+DataTemporari.COLUMN_SUB_SEGMENT+" DESC";

        }else{
            order = DataTemporari.COLUMN_TIPE+", "+DataTemporari.COLUMN_SEGMENT;
        }


        tabel = DataTemporari.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_NOPROV, DataTemporari.COLUMN_RUAS, DataTemporari.COLUMN_SEGMENT, DataTemporari.COLUMN_SUB_SEGMENT, DataTemporari.COLUMN_TIPE, DataTemporari.COLUMN_POSISI, DataTemporari.COLUMN_URUT,
                        DataTemporari.COLUMN_JENIS, DataTemporari.COLUMN_SEGMENTAKHIR, DataTemporari.COLUMN_SUB_SEGMENTAKHIR, DataTemporari.COLUMN_FOTO, DataTemporari.COLUMN_STATUS},
                where, parameter, null, null, order, null);


        if (cursor.moveToFirst()) {
            do {
                DataTemporari dataTemporari = new DataTemporari(
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_TIPE)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_URUT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_JENIS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENTAKHIR)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENTAKHIR)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_FOTO)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_STATUS)));
                notes.add(dataTemporari);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();


        return notes;
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

    public void clear() {

        String selectQuery = "delete FROM datasementara ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    /*

    public List<String> getRuasUpdated(String noprov, String noruas) {

        List<String> list = new ArrayList<>();

        String selectQuery = "SELECT no_ruas FROM datasementara where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND status = '1' group by no_ruas order by no_ruas";

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_RUAS)));
            } while (cursor.moveToNext());
        }



        cursor.close();
        database.close();
        close();


        return list;
    }

    public ArrayList<DataTemporari> getSinkrotempo(String ruas, String tipe, String posisi, String urut, String status, String param) {
        ArrayList<DataTemporari> notes = new ArrayList<>();
        String tabel;
        String[] parameter;
        String where;
        String order;

        if(tipe != null){
            if(tipe == "Median"){
                where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_STATUS+" = ?";
                parameter = new String[]{ruas, tipe, status};
                order = DataTemporari.COLUMN_SEGMENT;
            }else if(tipe == "Lane"){
                if(urut==null){
                    where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_STATUS+" = ?";
                    parameter = new String[]{ruas, tipe, status};
                    order = DataTemporari.COLUMN_SEGMENT+", "+DataTemporari.COLUMN_URUT;
                }else{
                    where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_POSISI+" = ? AND "+DataTemporari.COLUMN_URUT+" = ? AND "+DataTemporari.COLUMN_STATUS+" = ?";
                    if(urut.equals("L1")||urut.equals("L2")||urut.equals("L3")||urut.equals("L4")){
                        posisi = "Left";
                    }else{
                        posisi = "Right";
                    }
                    parameter = new String[]{ruas, tipe, posisi, urut, status};
                    order = DataTemporari.COLUMN_SEGMENT;
                }
            }else{
                if(posisi==null){
                    where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_STATUS+" = ?";
                    parameter = new String[]{ruas, tipe, status};
                    order = DataTemporari.COLUMN_SEGMENT+", "+DataTemporari.COLUMN_POSISI;
                }else{
                    where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ? AND "+DataTemporari.COLUMN_POSISI+" = ? AND "+DataTemporari.COLUMN_STATUS+" = ?";
                    parameter = new String[]{ruas,tipe, posisi, status};
                    order = DataTemporari.COLUMN_SEGMENT;
                }
            }
        }else{
            where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_STATUS+" = ?";
            parameter = new String[]{ruas,status};
            order =DataTemporari.COLUMN_TIPE+", "+DataTemporari.COLUMN_SEGMENT+", "+DataTemporari.COLUMN_POSISI+", "+DataTemporari.COLUMN_URUT;
        }

        if (param!=null){

            //String[] coba = new String[parameter];

            //Toast.makeText(context, String.valueOf(parameter.length)+ ", "+String.valueOf(parameter[0]), Toast.LENGTH_SHORT).show();


            String[] coba = new String[parameter.length+1];
            for(int i=0; i<=parameter.length;i++){
                if(i == parameter.length){
                    coba[i] = param;
                }else {
                    coba[i] = parameter[i];
                }
            }
            parameter = coba;
            where = where+" AND "+DataTemporari.COLUMN_FOTO+" = ?";

        }

        tabel = DataTemporari.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_NOPROV, DataTemporari.COLUMN_RUAS, DataTemporari.COLUMN_SEGMENT, DataTemporari.COLUMN_TIPE, DataTemporari.COLUMN_POSISI, DataTemporari.COLUMN_URUT, DataTemporari.COLUMN_JENIS, DataTemporari.COLUMN_SEGMENTAKHIR, DataTemporari.COLUMN_FOTO, DataTemporari.COLUMN_STATUS},
                where, parameter, null, null, order, null);


        if (cursor.moveToFirst()) {
            do {
                DataTemporari dataTemporari = new DataTemporari(
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_TIPE)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_URUT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_JENIS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENTAKHIR)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_FOTO)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_STATUS)));
                notes.add(dataTemporari);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();


        return notes;
    }

    public void updateRuastemporari(ArrayList<DataTemporari> dataTemporaris) {
        open();

        String tabel;

        tabel = DataTemporari.TABLE_NAME;

        ContentValues values = new ContentValues();

        for(int i=0; i<dataTemporaris.size();i++) {
            values.put(DataTemporari.COLUMN_TIPE, dataTemporaris.get(i).getTipe());
            values.put(DataTemporari.COLUMN_POSISI, dataTemporaris.get(i).getPosisi());
            values.put(DataTemporari.COLUMN_URUT, dataTemporaris.get(i).getUrut());
            values.put(DataTemporari.COLUMN_JENIS, dataTemporaris.get(i).getJenis());
            values.put(DataTemporari.COLUMN_SEGMENTAKHIR, dataTemporaris.get(i).getSegmentakhir());
            values.put(DataTemporari.COLUMN_FOTO, dataTemporaris.get(i).getFoto());
            values.put(DataTemporari.COLUMN_STATUS, dataTemporaris.get(i).getStatus());
            String where = DataTemporari.COLUMN_NOPROV + " = ? AND " + DataTemporari.COLUMN_RUAS + " = ? AND " + DataTemporari.COLUMN_SEGMENT + " = ? AND " + DataTemporari.COLUMN_TIPE + " = ? AND " + DataTemporari.COLUMN_POSISI + " = ? AND " + DataTemporari.COLUMN_URUT + " = ?";
            database.update(tabel, values, where,
                    new String[]{String.valueOf(dataTemporaris.get(i).getNoprov()), String.valueOf(dataTemporaris.get(i).getNoruas()), String.valueOf(dataTemporaris.get(i).getNosegment()), dataTemporaris.get(i).getTipe(), dataTemporaris.get(i).getPosisi(), dataTemporaris.get(i).getUrut()});
        }
        database.close();
        close();
    }

    public ArrayList<DataTemporari> getRuasTemporari(String noruas, String status) {
        ArrayList<DataTemporari> notes = new ArrayList<>();
        String tabel;
        tabel = DataTemporari.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_STATUS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_NOPROV, DataTemporari.COLUMN_RUAS, DataTemporari.COLUMN_SEGMENT, DataTemporari.COLUMN_TIPE, DataTemporari.COLUMN_POSISI, DataTemporari.COLUMN_URUT, DataTemporari.COLUMN_JENIS, DataTemporari.COLUMN_SEGMENTAKHIR, DataTemporari.COLUMN_FOTO, DataTemporari.COLUMN_STATUS},
                where,
                new String[]{noruas, status}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataTemporari dataTemporari = new DataTemporari(
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_TIPE)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_URUT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_JENIS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SEGMENTAKHIR)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_FOTO)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_STATUS)));
                notes.add(dataTemporari);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();


        return notes;
    }

    public void updateKolektif(String noprov, String ruas, int awal, int akhir, String tipe, String posisi, String lajur, String foto, SendId id) {
        database = dbHelper.getWritableDatabase();

        for(int i = awal; i<=akhir; i++){
           DataTemporari dataTemporari = new DataTemporari(noprov, ruas, i, tipe, posisi, lajur,null, 0, foto, "0");
           postTemporari(dataTemporari);

           if(i==akhir){
              // Toast.makeText(context, "Data Berhasil di ubah", Toast.LENGTH_SHORT).show();
               id.hapusGambar(i);
           }
        }

        database.close();
        close();
    }

     public ArrayList<DataTemporari> getTipeTemporari(String noruas, String detail, String status) {
        ArrayList<DataTemporari> notes = new ArrayList<>();
        String tabel;
        tabel = DataTemporari.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataTemporari.COLUMN_RUAS+" = ? AND "+DataTemporari.COLUMN_STATUS+" = ? AND "+DataTemporari.COLUMN_TIPE+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataTemporari.COLUMN_NOPROV, DataTemporari.COLUMN_RUAS, DataTemporari.COLUMN_SUB_SEGMENT, DataTemporari.COLUMN_TIPE, DataTemporari.COLUMN_POSISI, DataTemporari.COLUMN_URUT, DataTemporari.COLUMN_JENIS, DataTemporari.COLUMN_SUB_SEGMENTAKHIR,  DataTemporari.COLUMN_FOTO,  DataTemporari.COLUMN_STATUS},
                where,
                new String[]{noruas, status, detail}, null, null, DataTemporari.COLUMN_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                DataTemporari dataTemporari = new DataTemporari(
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_TIPE)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_URUT)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_JENIS)),
                        cursor.getInt(cursor.getColumnIndex(DataTemporari.COLUMN_SUB_SEGMENTAKHIR)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_FOTO)),
                        cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_STATUS)));
                notes.add(dataTemporari);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();


        return notes;
    }



     */


}
