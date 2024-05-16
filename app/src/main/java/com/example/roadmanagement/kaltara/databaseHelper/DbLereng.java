package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.Interface.DataDrainase;

import java.util.ArrayList;

public class DbLereng {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbLereng(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertLereng (DataDrainase dataDrainase) {

        open();

        ContentValues values = new ContentValues();
        values.put(DataDrainase.NOPROV,dataDrainase.getNoprov());
        values.put(DataDrainase.NORUAS,dataDrainase.getNoruas());
        values.put(DataDrainase.NOSEG,dataDrainase.getNosegment());
        values.put(DataDrainase.SUBSEG,dataDrainase.getSubsegment());
        values.put(DataDrainase.POSISI, dataDrainase.getPosisi());
        values.put(DataDrainase.KEBERADAAN, dataDrainase.getKeberadaan());
        values.put(DataDrainase.JENISPENAMPANG,dataDrainase.getJenisPenampang());
        values.put(DataDrainase.LEBAR, dataDrainase.getLebar());
        values.put(DataDrainase.TINGGI,dataDrainase.getTinggi());
        values.put(DataDrainase.TINGGISEDIMEN,dataDrainase.getTinggiSedimen());
        values.put(DataDrainase.KONDISISALURAN,dataDrainase.getKondisiSaluran());
        values.put(DataDrainase.COLUMN_GAMBAR,dataDrainase.getGambar());
        values.put(DataDrainase.COLUMN_GAMBAR_ICON,dataDrainase.getIcon());
        values.put(DataDrainase.COLUMN_LOKASI,dataDrainase.getLokasi());



        database.insert(DataDrainase.TABLE_NAME, null, values);

        database.close();
        close();


    }

    public DataDrainase getLereng(String noprov, String noruas, int nosegment, int subsegment, String posisi) {
        String tabel;
        database = dbHelper.getReadableDatabase();
        DataDrainase dataDrainase = null;

        tabel = DataDrainase.TABLE_NAME;


        String where = DataDrainase.NOPROV+" = ? AND "+DataDrainase.NORUAS+" = ? AND "+DataDrainase.NOSEG+" = ? AND "+DataDrainase.SUBSEG+" = ? AND "+DataDrainase.POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataDrainase.NOPROV, DataDrainase.NORUAS, DataDrainase.NOSEG,  DataDrainase.SUBSEG, DataDrainase.POSISI, DataDrainase.KEBERADAAN, DataDrainase.JENISPENAMPANG,
                        DataDrainase.LEBAR, DataDrainase.TINGGI, DataDrainase.TINGGISEDIMEN, DataDrainase.KONDISISALURAN,
                        DataDrainase.COLUMN_GAMBAR, DataDrainase.COLUMN_GAMBAR_ICON, DataDrainase.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment), posisi}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataDrainase = new DataDrainase(
                    cursor.getString(cursor.getColumnIndex(DataDrainase.NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataDrainase.NORUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataDrainase.NOSEG)),
                    cursor.getInt(cursor.getColumnIndex(DataDrainase.SUBSEG)),
                    cursor.getString(cursor.getColumnIndex(DataDrainase.POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataDrainase.KEBERADAAN)),
                    cursor.getString(cursor.getColumnIndex(DataDrainase.JENISPENAMPANG)),
                    cursor.getFloat(cursor.getColumnIndex(DataDrainase.LEBAR)),
                    cursor.getFloat(cursor.getColumnIndex(DataDrainase.TINGGI)),
                    cursor.getFloat(cursor.getColumnIndex(DataDrainase.TINGGISEDIMEN)),
                    cursor.getString(cursor.getColumnIndex(DataDrainase.KONDISISALURAN)),
                    cursor.getString(cursor.getColumnIndex(DataDrainase.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataDrainase.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataDrainase.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();

        database.close();
        close();

        return dataDrainase;
    }

    public void updateLereng(DataDrainase dataDrainase) {
        open();
        String tabel;
        tabel = DataDrainase.TABLE_NAME;

        ContentValues values = new ContentValues();
        values.put(DataDrainase.KEBERADAAN, dataDrainase.getKeberadaan());
        values.put(DataDrainase.JENISPENAMPANG,dataDrainase.getJenisPenampang());
        values.put(DataDrainase.LEBAR, dataDrainase.getLebar());
        values.put(DataDrainase.TINGGI,dataDrainase.getTinggi());
        values.put(DataDrainase.TINGGISEDIMEN,dataDrainase.getTinggiSedimen());
        values.put(DataDrainase.KONDISISALURAN,dataDrainase.getKondisiSaluran());
        values.put(DataDrainase.COLUMN_GAMBAR,dataDrainase.getGambar());
        values.put(DataDrainase.COLUMN_GAMBAR_ICON,dataDrainase.getIcon());
        values.put(DataDrainase.COLUMN_LOKASI,dataDrainase.getLokasi());

        String where = DataDrainase.NOPROV+" = ? AND "+DataDrainase.NORUAS+" = ? AND "+DataDrainase.NOSEG+" = ? AND "+DataDrainase.SUBSEG+" = ? AND "+DataDrainase.POSISI+" = ? ";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataDrainase.getNoprov()), String.valueOf(dataDrainase.getNoruas()), String.valueOf(dataDrainase.getNosegment()), String.valueOf(dataDrainase.getSubsegment()), dataDrainase.getPosisi()});
        database.close();
        close();
    }


    public ArrayList<DataDrainase> getListLereng(String noprov, String noruas, String posisi) {
        ArrayList<DataDrainase> notes = new ArrayList<>();
        String tabel;
        tabel = DataDrainase.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataDrainase.NOPROV+" = ? AND "+DataDrainase.NORUAS+" = ? AND "+DataDrainase.POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataDrainase.NOPROV, DataDrainase.NORUAS, DataDrainase.NOSEG,  DataDrainase.SUBSEG, DataDrainase.POSISI, DataDrainase.KEBERADAAN, DataDrainase.JENISPENAMPANG,
                        DataDrainase.LEBAR, DataDrainase.TINGGI, DataDrainase.TINGGISEDIMEN, DataDrainase.JENISKONSTRUKSI, DataDrainase.KONDISISALURAN,
                        DataDrainase.COLUMN_GAMBAR, DataDrainase.COLUMN_GAMBAR_ICON, DataDrainase.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi}, null, null, DataDrainase.NOSEG+", "+DataDrainase.SUBSEG, null);

        if (cursor.moveToFirst()) {
            do {
                DataDrainase dataDrainase = new DataDrainase(
                        cursor.getString(cursor.getColumnIndex(DataDrainase.NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataDrainase.NORUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataDrainase.NOSEG)),
                        cursor.getInt(cursor.getColumnIndex(DataDrainase.SUBSEG)),
                        cursor.getString(cursor.getColumnIndex(DataDrainase.POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataDrainase.KEBERADAAN)),
                        cursor.getString(cursor.getColumnIndex(DataDrainase.JENISPENAMPANG)),
                        cursor.getFloat(cursor.getColumnIndex(DataDrainase.LEBAR)),
                        cursor.getFloat(cursor.getColumnIndex(DataDrainase.TINGGI)),
                        cursor.getFloat(cursor.getColumnIndex(DataDrainase.TINGGISEDIMEN)),
                        cursor.getString(cursor.getColumnIndex(DataDrainase.KONDISISALURAN)),
                        cursor.getString(cursor.getColumnIndex(DataDrainase.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataDrainase.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataDrainase.COLUMN_LOKASI)));
                notes.add(dataDrainase);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }




    public void setLereng(DataDrainase dataDrainase){
        DataDrainase dataDrainaseTempo =  getLereng(dataDrainase.getNoprov(), dataDrainase.getNoruas(), dataDrainase.getNosegment(), dataDrainase.getSubsegment(), dataDrainase.getPosisi());
        if(dataDrainaseTempo!=null){
            updateLereng(dataDrainase);
        }else{
            insertLereng(dataDrainase);
        }

    }




    public void tutup() {
        dbHelper.close();
    }




    public void deleteLereng (String noprov, String noruas, float segment) {

        String selectQuery = "delete FROM dataDrainase where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+segment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM dataDrainase where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM dataDrainase ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

}
