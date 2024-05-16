package com.example.roadmanagement.kaltara.databaseHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


import com.example.roadmanagement.kaltara.Interface.DataSpLuar;

import java.util.ArrayList;

public class DbSpl {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;


    public DbSpl(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertData(DataSpLuar dataSpLuar) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataSpLuar.COLUMN_NOPROV,dataSpLuar.getNoprov());
        values.put(DataSpLuar.COLUMN_RUAS,dataSpLuar.getNoruas());
        values.put(DataSpLuar.COLUMN_SEGMENT,dataSpLuar.getNosegment());
        values.put(DataSpLuar.COLUMN_SUB_SEGMENT,dataSpLuar.getSubsegment());
        values.put(DataSpLuar.COLUMN_POSISI, dataSpLuar.getPosisi());
        values.put(DataSpLuar.COLUMN_LEBAR, dataSpLuar.getLebar());
        values.put(DataSpLuar.COLUMN_GAMBAR,dataSpLuar.getGambar());
        values.put(DataSpLuar.COLUMN_GAMBAR_ICON,dataSpLuar.getIcon());
        values.put(DataSpLuar.COLUMN_LOKASI,dataSpLuar.getLokasi());

        database.insert(DataSpLuar.TABLE_NAME , null, values);
        database.close();
        close();

    }

    @SuppressLint("Range")
    public DataSpLuar getData(String noprov, String noruas, int nosegment, int subSegment, String posisi) {
        open();
        DataSpLuar dataSpLuar = null;

        String where = DataSpLuar.COLUMN_NOPROV+" = ? AND "+DataSpLuar.COLUMN_RUAS+" = ? AND "+DataSpLuar.COLUMN_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_SUB_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(DataSpLuar.TABLE_NAME,
                new String[]{DataSpLuar.COLUMN_ID, DataSpLuar.COLUMN_NOPROV, DataSpLuar.COLUMN_RUAS, DataSpLuar.COLUMN_SEGMENT, DataSpLuar.COLUMN_SUB_SEGMENT, DataSpLuar.COLUMN_POSISI,
                        DataSpLuar.COLUMN_LEBAR, DataSpLuar.COLUMN_GAMBAR , DataSpLuar.COLUMN_GAMBAR_ICON, DataSpLuar.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subSegment), posisi}, null, null, null, null);

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            dataSpLuar = new DataSpLuar(
                    cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataSpLuar.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataSpLuar.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_POSISI)),
                    cursor.getFloat(cursor.getColumnIndex(DataSpLuar.COLUMN_LEBAR)),
                    cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_LOKASI)));
        }
        cursor.close();
        database.close();
        close();


        return dataSpLuar;
    }

    public void updateData(DataSpLuar dataSpLuar) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataSpLuar.COLUMN_LEBAR, dataSpLuar.getLebar());
        values.put(DataSpLuar.COLUMN_GAMBAR,dataSpLuar.getGambar());
        values.put(DataSpLuar.COLUMN_GAMBAR_ICON,dataSpLuar.getIcon());
        values.put(DataSpLuar.COLUMN_LOKASI,dataSpLuar.getLokasi());
        String where = DataSpLuar.COLUMN_NOPROV+" = ? AND "+DataSpLuar.COLUMN_RUAS+" = ? AND "+DataSpLuar.COLUMN_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_SUB_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_POSISI+" = ? ";
        database.update(DataSpLuar.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataSpLuar.getNoprov()), String.valueOf(dataSpLuar.getNoruas()), String.valueOf(dataSpLuar.getNosegment()), String.valueOf(dataSpLuar.getSubsegment()), dataSpLuar.getPosisi()});

        database.close();
        close();
    }


    public ArrayList<DataSpLuar> getList(String noprov, String noruas, String posisi) {
        ArrayList<DataSpLuar> notes = new ArrayList<>();
        String tabel;
        tabel = DataSpLuar.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataSpLuar.COLUMN_NOPROV+" = ? AND "+DataSpLuar.COLUMN_RUAS+" = ? AND "+DataSpLuar.COLUMN_POSISI+" = ? ";
        String[] parameter = new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi};


        Cursor cursor = database.query(tabel,
                new String[]{DataSpLuar.COLUMN_ID, DataSpLuar.COLUMN_NOPROV, DataSpLuar.COLUMN_RUAS, DataSpLuar.COLUMN_SEGMENT, DataSpLuar.COLUMN_SUB_SEGMENT, DataSpLuar.COLUMN_POSISI,
                        DataSpLuar.COLUMN_LEBAR, DataSpLuar.COLUMN_GAMBAR , DataSpLuar.COLUMN_GAMBAR_ICON, DataSpLuar.COLUMN_LOKASI},
                where,
                parameter, null, null, DataSpLuar.COLUMN_SEGMENT+", "+DataSpLuar.COLUMN_SUB_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") DataSpLuar dataSpLuar = new DataSpLuar(
                        cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataSpLuar.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataSpLuar.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_POSISI)),
                        cursor.getFloat(cursor.getColumnIndex(DataSpLuar.COLUMN_LEBAR)),
                        cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataSpLuar.COLUMN_LOKASI)));
                notes.add(dataSpLuar);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }



    public void postData(DataSpLuar dataSpLuar){

        DataSpLuar dataTempo =  getData(dataSpLuar.getNoprov(), dataSpLuar.getNoruas(), dataSpLuar.getNosegment(), dataSpLuar.getSubsegment(), dataSpLuar.getPosisi());

        if(dataTempo!=null){
            updateData(dataSpLuar);
        }else{
            insertData(dataSpLuar);
        }

    }


    public void saveNormal(DataSpLuar dataSpLuar, int segmentAwal, int subsegmentAwal, int segmentAkhir, int subsegmentAkhir, String tipeSurvey){

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataSpLuar.COLUMN_LEBAR, dataSpLuar.getLebar());
        values.put(DataSpLuar.COLUMN_GAMBAR,dataSpLuar.getGambar());
        values.put(DataSpLuar.COLUMN_GAMBAR_ICON,dataSpLuar.getIcon());
        values.put(DataSpLuar.COLUMN_LOKASI,dataSpLuar.getLokasi());

        String where;

        if(tipeSurvey.equals("Normal")){
            where = DataSpLuar.COLUMN_NOPROV+" = ? AND "+DataSpLuar.COLUMN_RUAS+" = ? AND "+DataSpLuar.COLUMN_POSISI+" = ? AND ( ( "
                    +DataSpLuar.COLUMN_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_SEGMENT+ " != ? AND "+DataSpLuar.COLUMN_SUB_SEGMENT+" >= ? ) OR ( "
                    +DataSpLuar.COLUMN_SEGMENT+" > ? AND "+DataSpLuar.COLUMN_SEGMENT+" < ? ) OR ("
                    +DataSpLuar.COLUMN_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_SEGMENT+ " != ? AND "+DataSpLuar.COLUMN_SUB_SEGMENT+"<= ? ) OR ( "
                    +DataSpLuar.COLUMN_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_SUB_SEGMENT+" >= ? AND "+DataSpLuar.COLUMN_SUB_SEGMENT+" <=  ? ))";

        }else{

            where = DataSpLuar.COLUMN_NOPROV+" = ? AND "+DataSpLuar.COLUMN_RUAS+" = ? AND "+DataSpLuar.COLUMN_POSISI+" = ? AND ( ( "
                    +DataSpLuar.COLUMN_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_SEGMENT+ " != ? AND "+DataSpLuar.COLUMN_SUB_SEGMENT+" <= ? ) OR ( "
                    +DataSpLuar.COLUMN_SEGMENT+" < ? AND "+DataSpLuar.COLUMN_SEGMENT+" > ? ) OR ("
                    +DataSpLuar.COLUMN_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_SEGMENT+ " != ? AND "+DataSpLuar.COLUMN_SUB_SEGMENT+">= ? ) OR ( "
                    +DataSpLuar.COLUMN_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_SEGMENT+" = ? AND "+DataSpLuar.COLUMN_SUB_SEGMENT+" <= ? AND "+DataSpLuar.COLUMN_SUB_SEGMENT+" >=  ? ))";


        }

        database.update(DataSpLuar.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataSpLuar.getNoprov()), String.valueOf(dataSpLuar.getNoruas()), dataSpLuar.getPosisi(),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subsegmentAwal),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir),
                        String.valueOf(segmentAkhir), String.valueOf(segmentAwal),String.valueOf(subsegmentAkhir),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subsegmentAwal), String.valueOf(subsegmentAkhir)
                });

        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM dataspluar ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

}
