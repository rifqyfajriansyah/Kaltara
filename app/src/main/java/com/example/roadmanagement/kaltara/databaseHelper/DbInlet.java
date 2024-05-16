package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;

import java.util.ArrayList;

public class DbInlet {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbInlet(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    private void insertInlet (DataInletTrotoar dataInlet) {

        open();

        ContentValues values = new ContentValues();
        values.put(DataInletTrotoar.NOPROV,dataInlet.getNoprov());
        values.put(DataInletTrotoar.NORUAS,dataInlet.getNoruas());
        values.put(DataInletTrotoar.NOSEG,dataInlet.getNosegment());
        values.put(DataInletTrotoar.SUBSEG,dataInlet.getSubsegment());
        values.put(DataInletTrotoar.POSISI, dataInlet.getPosisi());
        values.put(DataInletTrotoar.KEBERADAAN, dataInlet.getKeberadaan());
        values.put(DataInletTrotoar.JENISPENAMPANG,dataInlet.getJenisPenampang());
        values.put(DataInletTrotoar.TINGGI,dataInlet.getTinggi());
        values.put(DataInletTrotoar.PANJANG, dataInlet.getPanjang());
        values.put(DataInletTrotoar.LEBAR,dataInlet.getLebar());
        values.put(DataInletTrotoar.TINGGISEDIMEN,dataInlet.getTinggiSedimen());
        values.put(DataInletTrotoar.JENISKONSTRUKSI,dataInlet.getJenisKonstruksi());
        values.put(DataInletTrotoar.KONDISISALURAN,dataInlet.getKondisiSaluran());
        values.put(DataInletTrotoar.COLUMN_GAMBAR,dataInlet.getGambar());
        values.put(DataInletTrotoar.COLUMN_GAMBAR_ICON,dataInlet.getIcon());
        values.put(DataInletTrotoar.COLUMN_LOKASI,dataInlet.getLokasi());


        database.insert(DataInletTrotoar.TABLE_NAME, null, values);

        database.close();
        close();


    }

    public DataInletTrotoar getInlet(String noprov, String noruas, int nosegment, int subsegment, String posisi) {
        String tabel;
        database = dbHelper.getReadableDatabase();
        DataInletTrotoar dataInlet = null;

        tabel = DataInletTrotoar.TABLE_NAME;


        String where = DataInletTrotoar.NOPROV+" = ? AND "+DataInletTrotoar.NORUAS+" = ? AND "+DataInletTrotoar.NOSEG+" = ? AND "+DataInletTrotoar.SUBSEG+" = ? AND "+DataInletTrotoar.POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataInletTrotoar.NOPROV, DataInletTrotoar.NORUAS, DataInletTrotoar.NOSEG,  DataInletTrotoar.SUBSEG, DataInletTrotoar.POSISI, DataInletTrotoar.KEBERADAAN, DataInletTrotoar.JENISPENAMPANG,
                        DataInletTrotoar.TINGGI, DataInletTrotoar.PANJANG, DataInletTrotoar.LEBAR, DataInletTrotoar.TINGGISEDIMEN, DataInletTrotoar.JENISKONSTRUKSI, DataInletTrotoar.KONDISISALURAN,
                        DataInletTrotoar.COLUMN_GAMBAR, DataInletTrotoar.COLUMN_GAMBAR_ICON, DataInletTrotoar.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment), posisi}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataInlet = new DataInletTrotoar(
                    cursor.getString(cursor.getColumnIndex(DataInletTrotoar.NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataInletTrotoar.NORUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataInletTrotoar.NOSEG)),
                    cursor.getInt(cursor.getColumnIndex(DataInletTrotoar.SUBSEG)),
                    cursor.getString(cursor.getColumnIndex(DataInletTrotoar.POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataInletTrotoar.KEBERADAAN)),
                    cursor.getString(cursor.getColumnIndex(DataInletTrotoar.JENISPENAMPANG)),
                    cursor.getFloat(cursor.getColumnIndex(DataInletTrotoar.TINGGI)),
                    cursor.getFloat(cursor.getColumnIndex(DataInletTrotoar.PANJANG)),
                    cursor.getFloat(cursor.getColumnIndex(DataInletTrotoar.LEBAR)),
                    cursor.getFloat(cursor.getColumnIndex(DataInletTrotoar.TINGGISEDIMEN)),
                    cursor.getString(cursor.getColumnIndex(DataInletTrotoar.JENISKONSTRUKSI)),
                    cursor.getString(cursor.getColumnIndex(DataInletTrotoar.KONDISISALURAN)),
                    cursor.getString(cursor.getColumnIndex(DataInletTrotoar.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataInletTrotoar.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataInletTrotoar.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();

        database.close();
        close();

        return dataInlet;
    }

    public void updateInlet(DataInletTrotoar dataInlet) {
        open();
        String tabel;
        tabel = DataInletTrotoar.TABLE_NAME;

        ContentValues values = new ContentValues();

        values.put(DataInletTrotoar.KEBERADAAN, dataInlet.getKeberadaan());
        values.put(DataInletTrotoar.JENISPENAMPANG,dataInlet.getJenisPenampang());
        values.put(DataInletTrotoar.TINGGI,dataInlet.getTinggi());
        values.put(DataInletTrotoar.PANJANG, dataInlet.getPanjang());
        values.put(DataInletTrotoar.LEBAR,dataInlet.getLebar());
        values.put(DataInletTrotoar.TINGGISEDIMEN,dataInlet.getTinggiSedimen());
        values.put(DataInletTrotoar.JENISKONSTRUKSI,dataInlet.getJenisKonstruksi());
        values.put(DataInletTrotoar.KONDISISALURAN,dataInlet.getKondisiSaluran());
        values.put(DataInletTrotoar.COLUMN_GAMBAR,dataInlet.getGambar());
        values.put(DataInletTrotoar.COLUMN_GAMBAR_ICON,dataInlet.getIcon());
        values.put(DataInletTrotoar.COLUMN_LOKASI,dataInlet.getLokasi());

        String where = DataInletTrotoar.NOPROV+" = ? AND "+DataInletTrotoar.NORUAS+" = ? AND "+DataInletTrotoar.NOSEG+" = ? AND "+DataInletTrotoar.SUBSEG+" = ? AND "+DataInletTrotoar.POSISI+" = ? ";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataInlet.getNoprov()), String.valueOf(dataInlet.getNoruas()), String.valueOf(dataInlet.getNosegment()), String.valueOf(dataInlet.getSubsegment()), dataInlet.getPosisi()});
        database.close();
        close();
    }

    public ArrayList<DataInletTrotoar> getListInlet(String noprov, String noruas, String posisi) {
        ArrayList<DataInletTrotoar> notes = new ArrayList<>();
        String tabel;
        tabel = DataInletTrotoar.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataInletTrotoar.NOPROV+" = ? AND "+DataInletTrotoar.NORUAS+" = ? AND "+DataInletTrotoar.POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataInletTrotoar.NOPROV, DataInletTrotoar.NORUAS, DataInletTrotoar.NOSEG,  DataInletTrotoar.SUBSEG, DataInletTrotoar.POSISI, DataInletTrotoar.KEBERADAAN, DataInletTrotoar.JENISPENAMPANG,
                        DataInletTrotoar.TINGGI, DataInletTrotoar.PANJANG, DataInletTrotoar.LEBAR, DataInletTrotoar.TINGGISEDIMEN, DataInletTrotoar.JENISKONSTRUKSI, DataInletTrotoar.KONDISISALURAN,
                        DataInletTrotoar.COLUMN_GAMBAR, DataInletTrotoar.COLUMN_GAMBAR_ICON, DataInletTrotoar.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi}, null, null, DataInletTrotoar.NOSEG+", "+DataInletTrotoar.SUBSEG, null);

        if (cursor.moveToFirst()) {
            do {
                DataInletTrotoar dataInlet = new DataInletTrotoar(
                        cursor.getString(cursor.getColumnIndex(DataInletTrotoar.NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataInletTrotoar.NORUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataInletTrotoar.NOSEG)),
                        cursor.getInt(cursor.getColumnIndex(DataInletTrotoar.SUBSEG)),
                        cursor.getString(cursor.getColumnIndex(DataInletTrotoar.POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataInletTrotoar.KEBERADAAN)),
                        cursor.getString(cursor.getColumnIndex(DataInletTrotoar.JENISPENAMPANG)),
                        cursor.getFloat(cursor.getColumnIndex(DataInletTrotoar.TINGGI)),
                        cursor.getFloat(cursor.getColumnIndex(DataInletTrotoar.PANJANG)),
                        cursor.getFloat(cursor.getColumnIndex(DataInletTrotoar.LEBAR)),
                        cursor.getFloat(cursor.getColumnIndex(DataInletTrotoar.TINGGISEDIMEN)),
                        cursor.getString(cursor.getColumnIndex(DataInletTrotoar.JENISKONSTRUKSI)),
                        cursor.getString(cursor.getColumnIndex(DataInletTrotoar.KONDISISALURAN)),
                        cursor.getString(cursor.getColumnIndex(DataInletTrotoar.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataInletTrotoar.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataInletTrotoar.COLUMN_LOKASI)));

                notes.add(dataInlet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }


    public void setInlet(DataInletTrotoar dataInlet){
        DataInletTrotoar dataInletTempo =  getInlet(dataInlet.getNoprov(), dataInlet.getNoruas(), dataInlet.getNosegment(), dataInlet.getSubsegment(), dataInlet.getPosisi());
        if(dataInletTempo!=null){
            updateInlet(dataInlet);
        }else{
            insertInlet(dataInlet);
        }

    }

    public void deleteGorong (String noprov, String noruas, float segment) {

        String selectQuery = "delete FROM dataInletTrotoar where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+segment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM dataInletTrotoar where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM dataInletTrotoar ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

}
