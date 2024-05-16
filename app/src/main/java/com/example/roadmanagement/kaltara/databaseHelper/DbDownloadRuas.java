package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.Interface.DataDownload;
import com.example.roadmanagement.kaltara.Interface.DataDownloadRuas;
import com.example.roadmanagement.kaltara.Interface.DataRuas;

import java.util.ArrayList;
import java.util.List;

public class DbDownloadRuas {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbDownloadRuas(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertaRuas (DataDownloadRuas dataDownload) {

        open();

        ContentValues values = new ContentValues();

        values.put(DataDownloadRuas.COLUMN_NOPROV,dataDownload.getNoprov());
        values.put(DataDownloadRuas.COLUMN_RUAS,dataDownload.getNoruas());
        values.put(DataDownloadRuas.CEKRUAS,dataDownload.getCekdownload());
        database.insert(DataDownloadRuas.TABLE_NAME, null, values);

        database.close();
        close();

    }



    public String getDownload(String noprov, String noruas) {
        String tabel;
        String isi = null;
        tabel = DataDownloadRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataDownloadRuas.COLUMN_NOPROV+" = ? AND "+DataDownloadRuas.COLUMN_RUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataDownloadRuas.CEKRUAS},
                where,
                new String[]{noprov, noruas}, null, null, null, null);


        if (cursor.moveToFirst()) {

            isi = cursor.getString(cursor.getColumnIndex(DataDownloadRuas.CEKRUAS));

        }

        cursor.close();
        database.close();
        close();

        return isi;
    }


    public DataDownloadRuas getRuasdata(String noprov, String noruas){
        String tabel;
        DataDownloadRuas dataDownloadRuas = null;
        String isi = null;
        tabel = DataDownloadRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataDownloadRuas.COLUMN_NOPROV+" = ? AND "+DataDownloadRuas.COLUMN_RUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataDownloadRuas.COLUMN_NOPROV, DataDownloadRuas.COLUMN_RUAS, DataDownloadRuas.CEKRUAS},
                where,
                new String[]{noprov, noruas}, null, null, null, null);


        if (cursor.moveToFirst()) {

            dataDownloadRuas = new DataDownloadRuas(
                    cursor.getString(cursor.getColumnIndex(DataDownloadRuas.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataDownloadRuas.COLUMN_RUAS)),
                    cursor.getString(cursor.getColumnIndex(DataDownloadRuas.CEKRUAS))
            );

        }
        cursor.close();
        database.close();
        close();

        return dataDownloadRuas;
    }


    public void updateRuas(DataDownloadRuas dataDownloadRuas) {
        open();
        ContentValues values = new ContentValues();
        values.put(DataDownloadRuas.CEKRUAS, dataDownloadRuas.getCekdownload());
        String where = DataDownloadRuas.COLUMN_NOPROV+" = ? AND "+DataDownloadRuas.COLUMN_RUAS+" = ? ";
        database.update(DataDownloadRuas.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataDownloadRuas.getNoprov()), String.valueOf(dataDownloadRuas.getNoruas())});
        database.close();
        close();
    }





    public void setRuas(String noprov, String ruas, String cek){
        DataDownloadRuas dataDownloadRuas = getRuasdata(noprov, ruas);
        // Toast.makeText(context, noprov, Toast.LENGTH_SHORT).show();
        dataDownloadRuas.setCekdownload(cek);
        if(dataDownloadRuas!=null){
            updateRuas(dataDownloadRuas);
        }
    }


    public List<String> getRuas(String noprov) {
        List<String> notes = new ArrayList<>();
        notes.add("--Pilih ruas--");
        String tabel;
        tabel = DataDownloadRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataDownloadRuas.COLUMN_NOPROV+" = ? AND "+DataDownloadRuas.CEKRUAS+" =?";
        Cursor cursor = database.query(tabel,
                new String[]{DataDownloadRuas.COLUMN_RUAS},
                where,
                new String[]{noprov, "1"}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                String ruasku = cursor.getString(cursor.getColumnIndex(DataDownloadRuas.COLUMN_RUAS));
                notes.add(ruasku);
            } while (cursor.moveToNext());
        }


        cursor.close();
        database.close();
        close();

        return notes;
    }

    public Integer getRuasDownload(String noprov){
        Integer a = 0;

        List<String> notes = new ArrayList<>();
        String tabel;
        tabel = DataDownloadRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataDownloadRuas.COLUMN_NOPROV+" = ? AND "+DataDownloadRuas.CEKRUAS+" =?";
        Cursor cursor = database.query(tabel,
                new String[]{DataDownloadRuas.COLUMN_RUAS},
                where,
                new String[]{noprov, "1"}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                String ruasku = cursor.getString(cursor.getColumnIndex(DataDownloadRuas.COLUMN_RUAS));
                notes.add(ruasku);
            } while (cursor.moveToNext());
        }


        cursor.close();
        database.close();
        close();

        if(notes.size()>0){
            a = 1;
        }

        return a;


    }

    public int getIndexDownload(String noprov) {
        String selectQuery = "SELECT count(noprov) as id FROM downloadruas where noprov =  '"+noprov+"' AND cekruas = 0 ";
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("id"));

        cursor.close();
        database.close();
        close();

        return maxid;

    }

    public void setRuas(DataDownloadRuas dataDownloadRuas){
        DataDownloadRuas dataDownloadRuas1 = getRuasdata(dataDownloadRuas.getNoprov(), dataDownloadRuas.getNoruas());
        if(dataDownloadRuas1==null){
            insertaRuas(dataDownloadRuas);
        }else{
            updateRuas(dataDownloadRuas);
        }
    }

    public void tutup() {
        dbHelper.close();
    }

}
