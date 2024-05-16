package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.GetSemuaImage.DataListImage;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;

import java.util.ArrayList;

public class DbSaluran {
    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbSaluran(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertSaluran (DataSaluran dataSaluran) {

        open();

        ContentValues values = new ContentValues();
        values.put(DataSaluran.COLUMN_NOPROV,dataSaluran.getNoprov());
        values.put(DataSaluran.COLUMN_RUAS,dataSaluran.getNoruas());
        values.put(DataSaluran.COLUMN_SEGMENT,dataSaluran.getNosegment());
        values.put(DataSaluran.COLUMN_SUB_SEGMENT,dataSaluran.getSubsegment());
        values.put(DataSaluran.COLUMN_POSISI, dataSaluran.getPosisi());
        values.put(DataSaluran.COLUMN_TIPE, dataSaluran.getTipeSaluran());
        values.put(DataSaluran.COLUMN_PERMUKAAN_SAMPING,dataSaluran.getPermukaanSamping());
        values.put(DataSaluran.COLUMN_JENIS_PENAMPANG,dataSaluran.getJenisPenampang());
        values.put(DataSaluran.COLUMN_LEBAR, dataSaluran.getLebarSaluran());
        values.put(DataSaluran.COLUMN_DALAM,dataSaluran.getDalamSaluran());
        values.put(DataSaluran.COLUMN_TINGGI_AIR,dataSaluran.getTinggiAir());
        values.put(DataSaluran.COLUMN_TINGGI_SEDIMEN,dataSaluran.getTinggiSedimen());
        values.put(DataSaluran.COLUMN_JENIS_KONSTRUKSI,dataSaluran.getJenisKonstruksi());
        values.put(DataSaluran.COLUMN_KONDISI_SALURAN,dataSaluran.getKondisiSaluran());
        values.put(DataSaluran.COLUMN_GAMBAR,dataSaluran.getGambarSaluran());
        values.put(DataSaluran.COLUMN_GAMBAR_ICON,dataSaluran.getGambarSaluranicon());
        values.put(DataSaluran.COLUMN_LOKASI,dataSaluran.getLokasiSaluran());



        database.insert(DataSaluran.TABLE_NAME, null, values);

        database.close();
        close();


    }

    public DataSaluran getSaluran(String noprov, String noruas, int nosegment, int subsegment, String posisi) {
        String tabel;
        database = dbHelper.getReadableDatabase();
        DataSaluran dataSaluran = null;

        tabel = DataSaluran.TABLE_NAME;


        String where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_SUB_SEGMENT+" = ? AND "+DataSaluran.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataSaluran.COLUMN_NOPROV, DataSaluran.COLUMN_RUAS, DataSaluran.COLUMN_SEGMENT,  DataSaluran.COLUMN_SUB_SEGMENT, DataSaluran.COLUMN_POSISI, DataSaluran.COLUMN_TIPE, DataSaluran.COLUMN_PERMUKAAN_SAMPING, DataSaluran.COLUMN_JENIS_PENAMPANG,
                        DataSaluran.COLUMN_LEBAR, DataSaluran.COLUMN_DALAM, DataSaluran.COLUMN_TINGGI_AIR, DataSaluran.COLUMN_TINGGI_SEDIMEN, DataSaluran.COLUMN_JENIS_KONSTRUKSI, DataSaluran.COLUMN_KONDISI_SALURAN,
                        DataSaluran.COLUMN_GAMBAR, DataSaluran.COLUMN_GAMBAR_ICON, DataSaluran.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment), posisi}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataSaluran = new DataSaluran(
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_TIPE)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_PERMUKAAN_SAMPING)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_JENIS_PENAMPANG)),
                    cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_LEBAR)),
                    cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_DALAM)),
                    cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_TINGGI_AIR)),
                    cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_TINGGI_SEDIMEN)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_JENIS_KONSTRUKSI)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_KONDISI_SALURAN)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();

        database.close();
        close();

        return dataSaluran;
    }

    public void updateSaluran(DataSaluran dataSaluran) {
        open();
        String tabel;
        tabel = DataSaluran.TABLE_NAME;

        ContentValues values = new ContentValues();
        values.put(DataSaluran.COLUMN_TIPE, dataSaluran.getTipeSaluran());
        values.put(DataSaluran.COLUMN_PERMUKAAN_SAMPING,dataSaluran.getPermukaanSamping());
        values.put(DataSaluran.COLUMN_JENIS_PENAMPANG,dataSaluran.getJenisPenampang());
        values.put(DataSaluran.COLUMN_LEBAR, dataSaluran.getLebarSaluran());
        values.put(DataSaluran.COLUMN_DALAM,dataSaluran.getDalamSaluran());
        values.put(DataSaluran.COLUMN_TINGGI_AIR,dataSaluran.getTinggiAir());
        values.put(DataSaluran.COLUMN_TINGGI_SEDIMEN,dataSaluran.getTinggiSedimen());
        values.put(DataSaluran.COLUMN_JENIS_KONSTRUKSI,dataSaluran.getJenisKonstruksi());
        values.put(DataSaluran.COLUMN_KONDISI_SALURAN,dataSaluran.getKondisiSaluran());
        values.put(DataSaluran.COLUMN_GAMBAR,dataSaluran.getGambarSaluran());
        values.put(DataSaluran.COLUMN_GAMBAR_ICON,dataSaluran.getGambarSaluranicon());
        values.put(DataSaluran.COLUMN_LOKASI,dataSaluran.getLokasiSaluran());

        String where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_SUB_SEGMENT+" = ? AND "+DataSaluran.COLUMN_POSISI+" = ? ";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataSaluran.getNoprov()), String.valueOf(dataSaluran.getNoruas()), String.valueOf(dataSaluran.getNosegment()), String.valueOf(dataSaluran.getSubsegment()), dataSaluran.getPosisi()});
        database.close();
        close();
    }

    public void resetSaluran(String provinsi, String ruas, int segment, int subsegment, String posisi){
        DataSaluran dataSaluran =  getSaluran(provinsi, ruas, segment, subsegment, posisi);
        dataSaluran.setLokasiSaluran(null);
        dataSaluran.setGambarSaluran(null);
        dataSaluran.setGambarSaluranicon(null);
        updateSaluran(dataSaluran);
    }

    public ArrayList<DataSaluran> getListSaluran(String noprov, String noruas, String posisi) {
        ArrayList<DataSaluran> notes = new ArrayList<>();
        String tabel;
        tabel = DataSaluran.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataSaluran.COLUMN_NOPROV, DataSaluran.COLUMN_RUAS, DataSaluran.COLUMN_SEGMENT,  DataSaluran.COLUMN_SUB_SEGMENT, DataSaluran.COLUMN_POSISI, DataSaluran.COLUMN_TIPE, DataSaluran.COLUMN_PERMUKAAN_SAMPING, DataSaluran.COLUMN_JENIS_PENAMPANG,
                        DataSaluran.COLUMN_LEBAR, DataSaluran.COLUMN_DALAM, DataSaluran.COLUMN_TINGGI_AIR, DataSaluran.COLUMN_TINGGI_SEDIMEN, DataSaluran.COLUMN_JENIS_KONSTRUKSI, DataSaluran.COLUMN_KONDISI_SALURAN,
                        DataSaluran.COLUMN_GAMBAR, DataSaluran.COLUMN_GAMBAR_ICON, DataSaluran.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi}, null, null, DataSaluran.COLUMN_SEGMENT+", "+DataSaluran.COLUMN_SUB_SEGMENT, null);

        if (cursor.moveToFirst()) {
            do {
                DataSaluran dataSaluran = new DataSaluran(
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_TIPE)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_PERMUKAAN_SAMPING)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_JENIS_PENAMPANG)),
                        cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_LEBAR)),
                        cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_DALAM)),
                        cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_TINGGI_AIR)),
                        cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_TINGGI_SEDIMEN)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_JENIS_KONSTRUKSI)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_KONDISI_SALURAN)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_LOKASI)));
                notes.add(dataSaluran);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }




    public void setSaluran(DataSaluran dataSaluran){
        DataSaluran dataSaluranTempo =  getSaluran(dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getNosegment(), dataSaluran.getSubsegment(), dataSaluran.getPosisi());
        if(dataSaluranTempo!=null){
            updateSaluran(dataSaluran);
        }else{
            insertSaluran(dataSaluran);
        }

    }




    public void tutup() {
        dbHelper.close();
    }




    public void deleteSaluran (String noprov, String noruas, float segment) {

        String selectQuery = "delete FROM datasaluran where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+segment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }


    public void saveSaluranNormal(DataSaluran dataSaluran, int segmentAwal, int subSegmentAwal, int segmentAkhir, int subSegmentAkhir, String tipeSurvey){

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataSaluran.COLUMN_TIPE, dataSaluran.getTipeSaluran());
        values.put(DataSaluran.COLUMN_PERMUKAAN_SAMPING,dataSaluran.getPermukaanSamping());
        values.put(DataSaluran.COLUMN_JENIS_PENAMPANG,dataSaluran.getJenisPenampang());
        values.put(DataSaluran.COLUMN_LEBAR, dataSaluran.getLebarSaluran());
        values.put(DataSaluran.COLUMN_DALAM,dataSaluran.getDalamSaluran());
        values.put(DataSaluran.COLUMN_TINGGI_AIR,dataSaluran.getTinggiAir());
        values.put(DataSaluran.COLUMN_TINGGI_SEDIMEN,dataSaluran.getTinggiSedimen());
        values.put(DataSaluran.COLUMN_JENIS_KONSTRUKSI,dataSaluran.getJenisKonstruksi());
        values.put(DataSaluran.COLUMN_KONDISI_SALURAN,dataSaluran.getKondisiSaluran());
        values.put(DataSaluran.COLUMN_GAMBAR,dataSaluran.getGambarSaluran());
        values.put(DataSaluran.COLUMN_GAMBAR_ICON,dataSaluran.getGambarSaluranicon());
        values.put(DataSaluran.COLUMN_LOKASI,dataSaluran.getLokasiSaluran());

        String where;

        if(tipeSurvey.equals("Normal")){
            where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_POSISI+" = ? AND ( ( "
                    +DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_SEGMENT+ " != ? AND "+DataSaluran.COLUMN_SUB_SEGMENT+" >= ? ) OR ( "
                    +DataSaluran.COLUMN_SEGMENT+" > ? AND "+DataSaluran.COLUMN_SEGMENT+" < ? ) OR ("
                    +DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_SEGMENT+ " != ? AND "+DataSaluran.COLUMN_SUB_SEGMENT+"<= ? ) OR ( "
                    +DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_SUB_SEGMENT+" >= ? AND "+DataSaluran.COLUMN_SUB_SEGMENT+" <=  ? ))";

        }else{

            where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_POSISI+" = ? AND ( ( "
                    +DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_SEGMENT+ " != ? AND "+DataSaluran.COLUMN_SUB_SEGMENT+" <= ? ) OR ( "
                    +DataSaluran.COLUMN_SEGMENT+" < ? AND "+DataSaluran.COLUMN_SEGMENT+" > ? ) OR ("
                    +DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_SEGMENT+ " != ? AND "+DataSaluran.COLUMN_SUB_SEGMENT+">= ? ) OR ( "
                    +DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_SUB_SEGMENT+" <= ? AND "+DataSaluran.COLUMN_SUB_SEGMENT+" >=  ? ))";


        }
        database.update(DataSaluran.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataSaluran.getNoprov()), String.valueOf(dataSaluran.getNoruas()), dataSaluran.getPosisi(),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir),
                        String.valueOf(segmentAkhir), String.valueOf(segmentAwal),String.valueOf(subSegmentAkhir),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal), String.valueOf(subSegmentAkhir)
                });

        database.close();
        close();


    }


    // BATAS tanpa segment

    public DataSaluran getSaluranUn(String noprov, String noruas, int segment) {
        String tabel;
        database = dbHelper.getReadableDatabase();
        DataSaluran dataSaluran = null;

        tabel = DataSaluran.TABLE_NAME;


        String where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_SEGMENT+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataSaluran.COLUMN_NOPROV, DataSaluran.COLUMN_RUAS, DataSaluran.COLUMN_SEGMENT,  DataSaluran.COLUMN_SUB_SEGMENT, DataSaluran.COLUMN_POSISI, DataSaluran.COLUMN_TIPE, DataSaluran.COLUMN_PERMUKAAN_SAMPING, DataSaluran.COLUMN_JENIS_PENAMPANG,
                        DataSaluran.COLUMN_LEBAR, DataSaluran.COLUMN_DALAM, DataSaluran.COLUMN_TINGGI_AIR, DataSaluran.COLUMN_TINGGI_SEDIMEN, DataSaluran.COLUMN_JENIS_KONSTRUKSI, DataSaluran.COLUMN_KONDISI_SALURAN,
                        DataSaluran.COLUMN_GAMBAR, DataSaluran.COLUMN_GAMBAR_ICON, DataSaluran.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(segment)}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataSaluran = new DataSaluran(
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_TIPE)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_PERMUKAAN_SAMPING)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_JENIS_PENAMPANG)),
                    cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_LEBAR)),
                    cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_DALAM)),
                    cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_TINGGI_AIR)),
                    cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_TINGGI_SEDIMEN)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_JENIS_KONSTRUKSI)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_KONDISI_SALURAN)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();

        database.close();
        close();

        return dataSaluran;
    }

    public void updateSaluranUn(DataSaluran dataSaluran) {
        open();
        String tabel;
        tabel = DataSaluran.TABLE_NAME;

        ContentValues values = new ContentValues();
        values.put(DataSaluran.COLUMN_TIPE, dataSaluran.getTipeSaluran());
        values.put(DataSaluran.COLUMN_PERMUKAAN_SAMPING,dataSaluran.getPermukaanSamping());
        values.put(DataSaluran.COLUMN_JENIS_PENAMPANG,dataSaluran.getJenisPenampang());
        values.put(DataSaluran.COLUMN_LEBAR, dataSaluran.getLebarSaluran());
        values.put(DataSaluran.COLUMN_DALAM,dataSaluran.getDalamSaluran());
        values.put(DataSaluran.COLUMN_TINGGI_AIR,dataSaluran.getTinggiAir());
        values.put(DataSaluran.COLUMN_TINGGI_SEDIMEN,dataSaluran.getTinggiSedimen());
        values.put(DataSaluran.COLUMN_JENIS_KONSTRUKSI,dataSaluran.getJenisKonstruksi());
        values.put(DataSaluran.COLUMN_KONDISI_SALURAN,dataSaluran.getKondisiSaluran());
        values.put(DataSaluran.COLUMN_GAMBAR,dataSaluran.getGambarSaluran());
        values.put(DataSaluran.COLUMN_GAMBAR_ICON,dataSaluran.getGambarSaluranicon());
        values.put(DataSaluran.COLUMN_LOKASI,dataSaluran.getLokasiSaluran());

        String where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_SEGMENT+" = ? ";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataSaluran.getNoprov()), String.valueOf(dataSaluran.getNoruas()), String.valueOf(dataSaluran.getNosegment())});
        database.close();
        close();
    }

    public void setSaluranUn(DataSaluran dataSaluran){
        DataSaluran dataSaluranUn =  getSaluranUn(dataSaluran.getNoprov(), dataSaluran.getNoruas(),dataSaluran.getNosegment());
        if(dataSaluranUn !=null){
            updateSaluranUn(dataSaluran);
        }else{
            insertSaluran(dataSaluran);
        }

    }

    public int getIndexSaluran(String noprov, String noruas) {

        String selectQuery = "SELECT max(nosegment) as id FROM datasaluran where noprov ='"+noprov+"' AND noruas ='"+noruas+"'";
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

    public ArrayList<DataSaluran> getSalurankiri(String noprov, String noruas) {
        ArrayList<DataSaluran> notes = new ArrayList<>();
        String tabel;
        tabel = DataSaluran.TABLE_NAME_KIRI;
        database = dbHelper.getReadableDatabase();
        String where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataSaluran.COLUMN_ID, DataSaluran.COLUMN_NOPROV, DataSaluran.COLUMN_RUAS, DataSaluran.COLUMN_SEGMENT, DataSaluran.COLUMN_POSISI, DataSaluran.COLUMN_TIPE, DataSaluran.COLUMN_LEBAR, DataSaluran.COLUMN_DALAM, DataSaluran.COLUMN_GAMBAR, DataSaluran.COLUMN_GAMBAR_ICON, DataSaluran.COLUMN_LOKASI, DataSaluran.COLUMN_KERUSAKAN, DataSaluran.COLUMN_PANJANGKR, DataSaluran.COLUMN_GAMBARKR, DataSaluran.COLUMN_GAMBARKRICON, DataSaluran.COLUMN_LOKASIKR},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), "kiri"}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                DataSaluran dataSaluran = new DataSaluran(
                        cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_TIPE)),
                        cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_LEBAR)),
                        cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_DALAM)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_LOKASI)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_KERUSAKAN)),
                        cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_PANJANGKR)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBARKR)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBARKRICON)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_LOKASIKR)));
                notes.add(dataSaluran);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }

    public ArrayList<DataSaluran> getSalurankanan(String noprov, String noruas) {
        ArrayList<DataSaluran> notes = new ArrayList<>();
        String tabel;
        tabel = DataSaluran.TABLE_NAME_KIRI;
        database = dbHelper.getReadableDatabase();
        String where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataSaluran.COLUMN_ID, DataSaluran.COLUMN_NOPROV, DataSaluran.COLUMN_RUAS, DataSaluran.COLUMN_SEGMENT, DataSaluran.COLUMN_POSISI, DataSaluran.COLUMN_TIPE, DataSaluran.COLUMN_LEBAR, DataSaluran.COLUMN_DALAM, DataSaluran.COLUMN_GAMBAR, DataSaluran.COLUMN_GAMBAR_ICON, DataSaluran.COLUMN_LOKASI, DataSaluran.COLUMN_KERUSAKAN, DataSaluran.COLUMN_PANJANGKR, DataSaluran.COLUMN_GAMBARKR, DataSaluran.COLUMN_GAMBARKRICON, DataSaluran.COLUMN_LOKASIKR},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), "kanan"}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                DataSaluran dataSaluran = new DataSaluran(
                        cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_TIPE)),
                        cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_LEBAR)),
                        cursor.getFloat(cursor.getColumnIndex(DataSaluran.COLUMN_DALAM)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_LOKASI)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_KERUSAKAN)),
                        cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_PANJANGKR)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBARKR)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBARKRICON)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_LOKASIKR)));
                notes.add(dataSaluran);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }

     public ArrayList<DataListImage> getImageSaluran(String noprov, String noruas, String posisi) {
        ArrayList<DataListImage> notes = new ArrayList<>();
        String tabel;
        tabel = DataSaluran.TABLE_NAME_KIRI;
        database = dbHelper.getReadableDatabase();
        String where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataSaluran.COLUMN_ID, DataSaluran.COLUMN_NOPROV, DataSaluran.COLUMN_RUAS, DataSaluran.COLUMN_SEGMENT, DataSaluran.COLUMN_POSISI, DataSaluran.COLUMN_GAMBAR , DataSaluran.COLUMN_GAMBAR_ICON, DataSaluran.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                DataListImage datasaluran = new DataListImage(
                        cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_LOKASI)));
                notes.add(datasaluran);
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
        tabel = DataSaluran.TABLE_NAME_KIRI;
        database = dbHelper.getReadableDatabase();
        String where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataSaluran.COLUMN_NOPROV, DataSaluran.COLUMN_RUAS, DataSaluran.COLUMN_SEGMENT, DataSaluran.COLUMN_GAMBAR , DataSaluran.COLUMN_GAMBAR_ICON, DataSaluran.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(noseg), posisi}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                note = new DataListImage(cursor.getInt(cursor.getColumnIndex(DataSaluran.COLUMN_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataSaluran.COLUMN_LOKASI)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return note;
    }

    public void updateImageSaluran(String noprov, String noruas, int segment, String posisi, String image, String iconimage, String lokasi) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataSaluran.COLUMN_GAMBAR,image);
        values.put(DataSaluran.COLUMN_GAMBAR_ICON,iconimage);
        values.put(DataSaluran.COLUMN_LOKASI,lokasi);
        String where = DataSaluran.COLUMN_NOPROV+" = ? AND "+DataSaluran.COLUMN_RUAS+" = ? AND "+DataSaluran.COLUMN_SEGMENT+" = ? AND "+DataSaluran.COLUMN_POSISI+" = ? ";
        database.update(DataSaluran.TABLE_NAME_KIRI, values, where,
                new String[]{noprov, noruas, String.valueOf(segment), posisi});

        database.close();
        close();
    }

    public void setSaluranKolektif(String provinsi, String ruas, int segment, String posisi, String tipe, float panjang, float dalam, String foto, String icon, String lokasi){
        DataSaluran dataSaluran =  getSaluran(provinsi, ruas, segment, posisi);
        if(dataSaluran!=null){
            dataSaluran.setTipeSaluran(tipe);
            dataSaluran.setDalamSaluran(panjang);
            dataSaluran.setLebarSaluran(dalam);
            dataSaluran.setGambarSaluran(foto);
            dataSaluran.setGambarSaluranicon(icon);
            dataSaluran.setLokasiSaluran(lokasi);

            updateSaluran(dataSaluran);
        }else{
            dataSaluran = new DataSaluran(0, provinsi, ruas, segment, posisi, tipe, panjang, dalam,null, null, null, null, 0, null, null, null);
            insertSaluran(dataSaluran);
        }

    }

    public void setSaluranKolektifFoto(String provinsi, String ruas, int segment, String posisi, String tipe, float panjang, float dalam){
        DataSaluran dataSaluran =  getSaluran(provinsi, ruas, segment, posisi);
        if(dataSaluran!=null){
            dataSaluran.setTipeSaluran(tipe);
            dataSaluran.setDalamSaluran(panjang);
            dataSaluran.setLebarSaluran(dalam);


            updateSaluran(dataSaluran);
        }else{
            dataSaluran = new DataSaluran(0, provinsi, ruas, segment, posisi, tipe, panjang, dalam,null, null, null, null, 0, null, null, null);
            insertSaluran(dataSaluran);
        }

    }


     */

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM datasaluran where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }


    public void clear() {

        String selectQuery = "delete FROM datasaluran ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

}
