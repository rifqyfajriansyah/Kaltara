package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataRuas;

import java.util.ArrayList;
import java.util.List;

public class DbRuas {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbRuas(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertRuas (DataRuas dataRuas) {

        open();

        ContentValues values = new ContentValues();

        values.put(DataRuas.COLUMN_NOPROV,dataRuas.getNoprov());
        values.put(DataRuas.COLUMN_RUAS,dataRuas.getNoruas());
        values.put(DataRuas.SINKRON_ID,dataRuas.getSinkronid());
        values.put(DataRuas.SINKRON_DETAIL,dataRuas.getSinkronDetail());
        values.put(DataRuas.NAMA_RUAS,dataRuas.getNamaruas());
        values.put(DataRuas.FLAG_RESET,dataRuas.getFlagreset());

        database.insert(DataRuas.TABLE_NAME, null, values);
        database.close();
        close();

    }


    public List<String> getRuas(String noprov) {
        List<String> notes = new ArrayList<>();
        notes.add("--Pilih ruas--");
        String tabel;
        tabel = DataRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataRuas.COLUMN_NOPROV+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{DataRuas.COLUMN_RUAS},
                where,
                new String[]{noprov}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                String ruasku = cursor.getString(cursor.getColumnIndex(DataRuas.COLUMN_RUAS));
                notes.add(ruasku);
            } while (cursor.moveToNext());
        }


        cursor.close();
        database.close();
        close();

        return notes;
    }


    public String getSinkronid(String noprov, String noruas) {
        String tabel;
        String isi = null;
        tabel = DataRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.COLUMN_RUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataRuas.SINKRON_ID},
                where,
                new String[]{noprov, noruas}, null, null, null, null);


        if (cursor.moveToFirst()) {

                isi = cursor.getString(cursor.getColumnIndex(DataRuas.SINKRON_ID));

        }

        cursor.close();

        database.close();
        close();

        return isi;
    }

    public String getSinkronDetail(String noprov, String noruas) {
        String tabel;
        String isi = null;
        tabel = DataRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.COLUMN_RUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataRuas.SINKRON_DETAIL},
                where,
                new String[]{noprov, noruas}, null, null, null, null);


        if (cursor.moveToFirst()) {

            isi = cursor.getString(cursor.getColumnIndex(DataRuas.SINKRON_DETAIL));

        }

        cursor.close();

        database.close();
        close();

        return isi;
    }

    public String getNamaRuas(String noprov, String noruas) {
        String tabel;
        String isi = null;
        tabel = DataRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.COLUMN_RUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataRuas.NAMA_RUAS},
                where,
                new String[]{noprov, noruas}, null, null, null, null);


        if (cursor.moveToFirst()) {

            isi = cursor.getString(cursor.getColumnIndex(DataRuas.NAMA_RUAS));

        }

        cursor.close();

        database.close();
        close();

        return isi;
    }


    public int getFlag(String noprov, String noruas) {
        String tabel;
        int isi = 0;
        tabel = DataRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.COLUMN_RUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataRuas.FLAG_RESET},
                where,
                new String[]{noprov, noruas}, null, null, null, null);


        if (cursor.moveToFirst()) {

            isi = cursor.getInt(cursor.getColumnIndex(DataRuas.FLAG_RESET));

        }

        cursor.close();

        database.close();
        close();

        return isi;
    }

    public void updateFlag(String noprov, String noruas, int flag) {
        open();
        ContentValues values = new ContentValues();
        values.put(DataRuas.FLAG_RESET, flag);
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.COLUMN_RUAS+" = ? ";
        database.update(DataRuas.TABLE_NAME, values, where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas)});
        database.close();
        close();
    }

    public List<String> getRuasDownload(String noprov) {
        List<String> notes = new ArrayList<>();
        String tabel;
        String isi = null;
        tabel = DataRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.SINKRON_ID+" != ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataRuas.COLUMN_RUAS},
                where,
                new String[]{noprov, "0"}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                String ruasku = cursor.getString(cursor.getColumnIndex(DataRuas.COLUMN_RUAS));
                notes.add(ruasku);
            } while (cursor.moveToNext());
        }

        cursor.close();

        database.close();
        close();

        return notes;
    }

    public List<String> getRuasUndownload(String noprov) {
        List<String> notes = new ArrayList<>();
        String tabel;
        String isi = null;
        tabel = DataRuas.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.SINKRON_ID+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataRuas.COLUMN_RUAS},
                where,
                new String[]{noprov, "0"}, null, null, DataRuas.COLUMN_RUAS, null);


        if (cursor.moveToFirst()) {
            do {
                String ruasku = cursor.getString(cursor.getColumnIndex(DataRuas.COLUMN_RUAS));
                notes.add(ruasku);
            } while (cursor.moveToNext());
        }

        cursor.close();

        database.close();
        close();

        return notes;
    }

    public int getIndexDownload(String noprov) {
        String selectQuery = "SELECT count(noprov) as id FROM dataruas where noprov =  '"+noprov+"' AND sinkron = 0 ";
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("id"));

        cursor.close();
        database.close();
        close();

        return maxid;

    }



    public DataRuas getRuasdata(String noprov, String noruas){
        String tabel;
        DataRuas dataRuas = null;
        String isi = null;
        tabel = DataRuas.TABLE_NAME;
        database = dbHelper.getWritableDatabase();
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.COLUMN_RUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataRuas.COLUMN_NOPROV, DataRuas.COLUMN_RUAS, DataRuas.SINKRON_ID, DataRuas.SINKRON_DETAIL, DataRuas.NAMA_RUAS, DataRuas.FLAG_RESET},
                where,
                new String[]{noprov, noruas}, null, null, null, null);


        if (cursor.moveToFirst()) {

            dataRuas = new DataRuas(
                    cursor.getString(cursor.getColumnIndex(DataRuas.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataRuas.COLUMN_RUAS)),
                    cursor.getString(cursor.getColumnIndex(DataRuas.SINKRON_ID)),
                    cursor.getString(cursor.getColumnIndex(DataRuas.SINKRON_DETAIL)),
                    cursor.getString(cursor.getColumnIndex(DataRuas.NAMA_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataRuas.FLAG_RESET))
            );

        }

        cursor.close();

        database.close();
        close();

        return dataRuas;
    }


    public void updateRuas(DataRuas dataRuas) {
        open();
        ContentValues values = new ContentValues();
        values.put(DataRuas.SINKRON_ID, dataRuas.getSinkronid());
        values.put(DataRuas.SINKRON_DETAIL, dataRuas.getSinkronDetail());
        values.put(DataRuas.NAMA_RUAS, dataRuas.getNamaruas());
        values.put(DataRuas.FLAG_RESET, dataRuas.getFlagreset());
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.COLUMN_RUAS+" = ? ";
        database.update(DataRuas.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataRuas.getNoprov()), String.valueOf(dataRuas.getNoruas())});
        database.close();
        close();
    }


    public void updateSinkronId(String noprov, String ruas, String sinkronId) {
        open();
        ContentValues values = new ContentValues();
        values.put(DataRuas.SINKRON_ID, sinkronId);
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.COLUMN_RUAS+" = ? ";
        database.update(DataRuas.TABLE_NAME, values, where,
                new String[]{noprov, ruas});
        database.close();
        close();
    }

    public void updateSinkronDetail(String noprov, String ruas, String sinkronDetail) {
        open();
        ContentValues values = new ContentValues();
        values.put(DataRuas.SINKRON_DETAIL, sinkronDetail);
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.COLUMN_RUAS+" = ? ";
        database.update(DataRuas.TABLE_NAME, values, where,
                new String[]{noprov, ruas});
        database.close();
        close();
    }

    public void updateFLagReset(String noprov, String ruas, int flag) {
        open();
        ContentValues values = new ContentValues();
        values.put(DataRuas.FLAG_RESET, flag);
        String where = DataRuas.COLUMN_NOPROV+" = ? AND "+DataRuas.COLUMN_RUAS+" = ? ";
        database.update(DataRuas.TABLE_NAME, values, where,
                new String[]{noprov, ruas});
        database.close();
        close();
    }

    public void setRuasku(DataRuas dataRuas){

        DataRuas dataRuasTemporari = getRuasdata(dataRuas.getNoprov(), dataRuas.getNoruas());

        if(dataRuasTemporari!=null){
            updateRuas(dataRuas);
        }else{
            insertRuas(dataRuas);
        }
    }


    public void clear() {

        String selectQuery = "delete FROM dataruas ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }


}
