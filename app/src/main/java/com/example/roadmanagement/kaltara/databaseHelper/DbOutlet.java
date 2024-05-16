package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.Interface.DataOutlet;

import java.util.ArrayList;

public class DbOutlet {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbOutlet(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    private void insertOutlet (DataOutlet dataCrossDrain) {

        open();

        ContentValues values = new ContentValues();
        values.put(DataOutlet.NOPROV,dataCrossDrain.getNoprov());
        values.put(DataOutlet.NORUAS,dataCrossDrain.getNoruas());
        values.put(DataOutlet.NOSEG,dataCrossDrain.getNosegment());
        values.put(DataOutlet.SUBSEG,dataCrossDrain.getSubsegment());
        values.put(DataOutlet.POSISI, dataCrossDrain.getPosisi());
        values.put(DataOutlet.KEBERADAAN, dataCrossDrain.getKeberadaan());
        values.put(DataOutlet.JENISPENAMPANG,dataCrossDrain.getJenisPenampang());
        values.put(DataOutlet.DIAMETER,dataCrossDrain.getDiameter());
        values.put(DataOutlet.LEBAR, dataCrossDrain.getLebar());
        values.put(DataOutlet.TINGGI,dataCrossDrain.getTinggi());
        values.put(DataOutlet.JENISKONSTRUKSI,dataCrossDrain.getJenisKonstruksi());
        values.put(DataOutlet.KONDISISALURAN,dataCrossDrain.getKondisiSaluran());
        values.put(DataOutlet.COLUMN_GAMBAR,dataCrossDrain.getGambar());
        values.put(DataOutlet.COLUMN_GAMBAR_ICON,dataCrossDrain.getIcon());
        values.put(DataOutlet.COLUMN_LOKASI,dataCrossDrain.getLokasi());


        database.insert(DataOutlet.TABLE_NAME, null, values);

        database.close();
        close();


    }

    public DataOutlet getOutlet(String noprov, String noruas, int nosegment, int subsegment, String posisi) {
        String tabel;
        database = dbHelper.getReadableDatabase();
        DataOutlet dataOutlet = null;

        tabel = DataOutlet.TABLE_NAME;


        String where = DataOutlet.NOPROV+" = ? AND "+DataOutlet.NORUAS+" = ? AND "+DataOutlet.NOSEG+" = ? AND "+DataOutlet.SUBSEG+" = ? AND "+DataOutlet.POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataOutlet.NOPROV, DataOutlet.NORUAS, DataOutlet.NOSEG,  DataOutlet.SUBSEG, DataOutlet.POSISI, DataOutlet.KEBERADAAN, DataOutlet.JENISPENAMPANG, DataOutlet.DIAMETER,
                        DataOutlet.LEBAR, DataOutlet.TINGGI, DataOutlet.JENISKONSTRUKSI, DataOutlet.KONDISISALURAN,
                        DataOutlet.COLUMN_GAMBAR, DataOutlet.COLUMN_GAMBAR_ICON, DataOutlet.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment), posisi}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataOutlet = new DataOutlet(
                    cursor.getString(cursor.getColumnIndex(DataOutlet.NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataOutlet.NORUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataOutlet.NOSEG)),
                    cursor.getInt(cursor.getColumnIndex(DataOutlet.SUBSEG)),
                    cursor.getString(cursor.getColumnIndex(DataOutlet.POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataOutlet.KEBERADAAN)),
                    cursor.getString(cursor.getColumnIndex(DataOutlet.JENISPENAMPANG)),
                    cursor.getFloat(cursor.getColumnIndex(DataOutlet.DIAMETER)),
                    cursor.getFloat(cursor.getColumnIndex(DataOutlet.LEBAR)),
                    cursor.getFloat(cursor.getColumnIndex(DataOutlet.TINGGI)),
                    cursor.getString(cursor.getColumnIndex(DataOutlet.JENISKONSTRUKSI)),
                    cursor.getString(cursor.getColumnIndex(DataOutlet.KONDISISALURAN)),
                    cursor.getString(cursor.getColumnIndex(DataOutlet.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataOutlet.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataOutlet.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();

        database.close();
        close();

        return dataOutlet;
    }

    public void updateOutlet(DataOutlet dataCrossDrain) {
        open();
        String tabel;
        tabel = DataOutlet.TABLE_NAME;

        ContentValues values = new ContentValues();

        values.put(DataOutlet.KEBERADAAN, dataCrossDrain.getKeberadaan());
        values.put(DataOutlet.JENISPENAMPANG,dataCrossDrain.getJenisPenampang());
        values.put(DataOutlet.DIAMETER,dataCrossDrain.getDiameter());
        values.put(DataOutlet.LEBAR, dataCrossDrain.getLebar());
        values.put(DataOutlet.TINGGI,dataCrossDrain.getTinggi());
        values.put(DataOutlet.JENISKONSTRUKSI,dataCrossDrain.getJenisKonstruksi());
        values.put(DataOutlet.KONDISISALURAN,dataCrossDrain.getKondisiSaluran());
        values.put(DataOutlet.COLUMN_GAMBAR,dataCrossDrain.getGambar());
        values.put(DataOutlet.COLUMN_GAMBAR_ICON,dataCrossDrain.getIcon());
        values.put(DataOutlet.COLUMN_LOKASI,dataCrossDrain.getLokasi());

        String where = DataOutlet.NOPROV+" = ? AND "+DataOutlet.NORUAS+" = ? AND "+DataOutlet.NOSEG+" = ? AND "+DataOutlet.SUBSEG+" = ? AND "+DataOutlet.POSISI+" = ? ";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataCrossDrain.getNoprov()), String.valueOf(dataCrossDrain.getNoruas()), String.valueOf(dataCrossDrain.getNosegment()), String.valueOf(dataCrossDrain.getSubsegment()), dataCrossDrain.getPosisi()});
        database.close();
        close();
    }

    public ArrayList<DataOutlet> getListOutlet(String noprov, String noruas, String posisi) {
        ArrayList<DataOutlet> notes = new ArrayList<>();
        String tabel;
        tabel = DataOutlet.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataOutlet.NOPROV+" = ? AND "+DataOutlet.NORUAS+" = ? AND "+DataOutlet.POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataOutlet.NOPROV, DataOutlet.NORUAS, DataOutlet.NOSEG,  DataOutlet.SUBSEG, DataOutlet.POSISI, DataOutlet.KEBERADAAN, DataOutlet.JENISPENAMPANG, DataOutlet.DIAMETER,
                        DataOutlet.LEBAR, DataOutlet.TINGGI, DataOutlet.JENISKONSTRUKSI, DataOutlet.KONDISISALURAN,
                        DataOutlet.COLUMN_GAMBAR, DataOutlet.COLUMN_GAMBAR_ICON, DataOutlet.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi}, null, null, DataOutlet.NOSEG+", "+DataOutlet.SUBSEG, null);

        if (cursor.moveToFirst()) {
            do {
                DataOutlet dataOutlet = new DataOutlet(
                        cursor.getString(cursor.getColumnIndex(DataOutlet.NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataOutlet.NORUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataOutlet.NOSEG)),
                        cursor.getInt(cursor.getColumnIndex(DataOutlet.SUBSEG)),
                        cursor.getString(cursor.getColumnIndex(DataOutlet.POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataOutlet.KEBERADAAN)),
                        cursor.getString(cursor.getColumnIndex(DataOutlet.JENISPENAMPANG)),
                        cursor.getFloat(cursor.getColumnIndex(DataOutlet.DIAMETER)),
                        cursor.getFloat(cursor.getColumnIndex(DataOutlet.LEBAR)),
                        cursor.getFloat(cursor.getColumnIndex(DataOutlet.TINGGI)),
                        cursor.getString(cursor.getColumnIndex(DataOutlet.JENISKONSTRUKSI)),
                        cursor.getString(cursor.getColumnIndex(DataOutlet.KONDISISALURAN)),
                        cursor.getString(cursor.getColumnIndex(DataOutlet.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataOutlet.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataOutlet.COLUMN_LOKASI)));
                notes.add(dataOutlet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }




    public void setSaluran(DataOutlet dataOutlet){
        DataOutlet dataOutletTempo =  getOutlet(dataOutlet.getNoprov(), dataOutlet.getNoruas(), dataOutlet.getNosegment(), dataOutlet.getSubsegment(), dataOutlet.getPosisi());
        if(dataOutletTempo!=null){
            updateOutlet(dataOutlet);
        }else{
            insertOutlet(dataOutlet);
        }

    }

    public void deleteOutlet (String noprov, String noruas, float segment) {

        String selectQuery = "delete FROM dataOutlet where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+segment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM dataOutlet where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM dataOutlet ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

}
