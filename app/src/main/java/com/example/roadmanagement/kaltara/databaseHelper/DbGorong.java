package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;

import java.util.ArrayList;

public class DbGorong {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbGorong(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    private void insertGorong (DataCrossDrain dataCrossDrain) {

        open();

        ContentValues values = new ContentValues();
        values.put(DataCrossDrain.NOPROV,dataCrossDrain.getNoprov());
        values.put(DataCrossDrain.NORUAS,dataCrossDrain.getNoruas());
        values.put(DataCrossDrain.NOSEG,dataCrossDrain.getNosegment());
        values.put(DataCrossDrain.SUBSEG,dataCrossDrain.getSubsegment());
        values.put(DataCrossDrain.POSISI, dataCrossDrain.getPosisi());
        values.put(DataCrossDrain.KEBERADAAN, dataCrossDrain.getKeberadaan());
        values.put(DataCrossDrain.JENISPENAMPANG,dataCrossDrain.getJenisPenampang());
        values.put(DataCrossDrain.DIAMETER,dataCrossDrain.getDiameter());
        values.put(DataCrossDrain.LEBAR, dataCrossDrain.getLebar());
        values.put(DataCrossDrain.TINGGI,dataCrossDrain.getTinggi());
        values.put(DataCrossDrain.JENISKONSTRUKSI,dataCrossDrain.getJenisKonstruksi());
        values.put(DataCrossDrain.KONDISISALURAN,dataCrossDrain.getKondisiSaluran());
        values.put(DataCrossDrain.COLUMN_GAMBAR,dataCrossDrain.getGambar());
        values.put(DataCrossDrain.COLUMN_GAMBAR_ICON,dataCrossDrain.getIcon());
        values.put(DataCrossDrain.COLUMN_LOKASI,dataCrossDrain.getLokasi());


        database.insert(DataCrossDrain.TABLE_NAME, null, values);

        database.close();
        close();


    }

    public DataCrossDrain getGorong(String noprov, String noruas, int nosegment, int subsegment, String posisi) {
        String tabel;
        database = dbHelper.getReadableDatabase();
        DataCrossDrain dataCrossdrain = null;

        tabel = DataCrossDrain.TABLE_NAME;


        String where = DataCrossDrain.NOPROV+" = ? AND "+DataCrossDrain.NORUAS+" = ? AND "+DataCrossDrain.NOSEG+" = ? AND "+DataCrossDrain.SUBSEG+" = ? AND "+DataCrossDrain.POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataCrossDrain.NOPROV, DataCrossDrain.NORUAS, DataCrossDrain.NOSEG,  DataCrossDrain.SUBSEG, DataCrossDrain.POSISI, DataCrossDrain.KEBERADAAN, DataCrossDrain.JENISPENAMPANG, DataCrossDrain.DIAMETER,
                        DataCrossDrain.LEBAR, DataCrossDrain.TINGGI, DataCrossDrain.JENISKONSTRUKSI, DataCrossDrain.KONDISISALURAN,
                        DataCrossDrain.COLUMN_GAMBAR, DataCrossDrain.COLUMN_GAMBAR_ICON, DataCrossDrain.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment), posisi}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataCrossdrain = new DataCrossDrain(
                    cursor.getString(cursor.getColumnIndex(DataCrossDrain.NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataCrossDrain.NORUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataCrossDrain.NOSEG)),
                    cursor.getInt(cursor.getColumnIndex(DataCrossDrain.SUBSEG)),
                    cursor.getString(cursor.getColumnIndex(DataCrossDrain.POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataCrossDrain.KEBERADAAN)),
                    cursor.getString(cursor.getColumnIndex(DataCrossDrain.JENISPENAMPANG)),
                    cursor.getFloat(cursor.getColumnIndex(DataCrossDrain.DIAMETER)),
                    cursor.getFloat(cursor.getColumnIndex(DataCrossDrain.LEBAR)),
                    cursor.getFloat(cursor.getColumnIndex(DataCrossDrain.TINGGI)),
                    cursor.getString(cursor.getColumnIndex(DataCrossDrain.JENISKONSTRUKSI)),
                    cursor.getString(cursor.getColumnIndex(DataCrossDrain.KONDISISALURAN)),
                    cursor.getString(cursor.getColumnIndex(DataCrossDrain.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataCrossDrain.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataCrossDrain.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();

        database.close();
        close();

        return dataCrossdrain;
    }

    public void updateGorong(DataCrossDrain dataCrossDrain) {
        open();
        String tabel;
        tabel = DataCrossDrain.TABLE_NAME;

        ContentValues values = new ContentValues();

        values.put(DataCrossDrain.KEBERADAAN, dataCrossDrain.getKeberadaan());
        values.put(DataCrossDrain.JENISPENAMPANG,dataCrossDrain.getJenisPenampang());
        values.put(DataCrossDrain.DIAMETER,dataCrossDrain.getDiameter());
        values.put(DataCrossDrain.LEBAR, dataCrossDrain.getLebar());
        values.put(DataCrossDrain.TINGGI,dataCrossDrain.getTinggi());
        values.put(DataCrossDrain.JENISKONSTRUKSI,dataCrossDrain.getJenisKonstruksi());
        values.put(DataCrossDrain.KONDISISALURAN,dataCrossDrain.getKondisiSaluran());
        values.put(DataCrossDrain.COLUMN_GAMBAR,dataCrossDrain.getGambar());
        values.put(DataCrossDrain.COLUMN_GAMBAR_ICON,dataCrossDrain.getIcon());
        values.put(DataCrossDrain.COLUMN_LOKASI,dataCrossDrain.getLokasi());

        String where = DataCrossDrain.NOPROV+" = ? AND "+DataCrossDrain.NORUAS+" = ? AND "+DataCrossDrain.NOSEG+" = ? AND "+DataCrossDrain.SUBSEG+" = ? AND "+DataCrossDrain.POSISI+" = ? ";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataCrossDrain.getNoprov()), String.valueOf(dataCrossDrain.getNoruas()), String.valueOf(dataCrossDrain.getNosegment()), String.valueOf(dataCrossDrain.getSubsegment()), dataCrossDrain.getPosisi()});
        database.close();
        close();
    }

    public ArrayList<DataCrossDrain> getListGorong(String noprov, String noruas, String posisi) {
        ArrayList<DataCrossDrain> notes = new ArrayList<>();
        String tabel;
        tabel = DataCrossDrain.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataCrossDrain.NOPROV+" = ? AND "+DataCrossDrain.NORUAS+" = ? AND "+DataCrossDrain.POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataCrossDrain.NOPROV, DataCrossDrain.NORUAS, DataCrossDrain.NOSEG,  DataCrossDrain.SUBSEG, DataCrossDrain.POSISI, DataCrossDrain.KEBERADAAN, DataCrossDrain.JENISPENAMPANG, DataCrossDrain.DIAMETER,
                        DataCrossDrain.LEBAR, DataCrossDrain.TINGGI, DataCrossDrain.JENISKONSTRUKSI, DataCrossDrain.KONDISISALURAN,
                        DataCrossDrain.COLUMN_GAMBAR, DataCrossDrain.COLUMN_GAMBAR_ICON, DataCrossDrain.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi}, null, null, DataCrossDrain.NOSEG+", "+DataCrossDrain.SUBSEG, null);

        if (cursor.moveToFirst()) {
            do {
                DataCrossDrain dataCrossdrain = new DataCrossDrain(
                        cursor.getString(cursor.getColumnIndex(DataCrossDrain.NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataCrossDrain.NORUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataCrossDrain.NOSEG)),
                        cursor.getInt(cursor.getColumnIndex(DataCrossDrain.SUBSEG)),
                        cursor.getString(cursor.getColumnIndex(DataCrossDrain.POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataCrossDrain.KEBERADAAN)),
                        cursor.getString(cursor.getColumnIndex(DataCrossDrain.JENISPENAMPANG)),
                        cursor.getFloat(cursor.getColumnIndex(DataCrossDrain.DIAMETER)),
                        cursor.getFloat(cursor.getColumnIndex(DataCrossDrain.LEBAR)),
                        cursor.getFloat(cursor.getColumnIndex(DataCrossDrain.TINGGI)),
                        cursor.getString(cursor.getColumnIndex(DataCrossDrain.JENISKONSTRUKSI)),
                        cursor.getString(cursor.getColumnIndex(DataCrossDrain.KONDISISALURAN)),
                        cursor.getString(cursor.getColumnIndex(DataCrossDrain.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataCrossDrain.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataCrossDrain.COLUMN_LOKASI)));
                notes.add(dataCrossdrain);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }




    public void setSaluran(DataCrossDrain dataCrossdrain){
        DataCrossDrain dataCrossdrainTempo =  getGorong(dataCrossdrain.getNoprov(), dataCrossdrain.getNoruas(), dataCrossdrain.getNosegment(), dataCrossdrain.getSubsegment(), dataCrossdrain.getPosisi());
        if(dataCrossdrainTempo!=null){
            updateGorong(dataCrossdrain);
        }else{
            insertGorong(dataCrossdrain);
        }

    }

    public void deleteGorong (String noprov, String noruas, float segment) {

        String selectQuery = "delete FROM dataCrossdrain where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+segment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM dataCrossdrain where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM dataCrossdrain ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

}
