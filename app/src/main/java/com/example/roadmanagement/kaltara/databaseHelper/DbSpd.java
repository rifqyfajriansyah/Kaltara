package com.example.roadmanagement.kaltara.databaseHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.Interface.DataSpDalam;

import java.util.ArrayList;

public class DbSpd {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;


    public DbSpd(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertData(DataSpDalam dataSpDalam) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataSpDalam.COLUMN_NOPROV,dataSpDalam.getNoprov());
        values.put(DataSpDalam.COLUMN_RUAS,dataSpDalam.getNoruas());
        values.put(DataSpDalam.COLUMN_SEGMENT,dataSpDalam.getNosegment());
        values.put(DataSpDalam.COLUMN_SUB_SEGMENT,dataSpDalam.getSubsegment());
        values.put(DataSpDalam.COLUMN_POSISI, dataSpDalam.getPosisi());
        values.put(DataSpDalam.COLUMN_LEBAR, dataSpDalam.getLebar());
        values.put(DataSpDalam.COLUMN_GAMBAR,dataSpDalam.getGambar());
        values.put(DataSpDalam.COLUMN_GAMBAR_ICON,dataSpDalam.getIcon());
        values.put(DataSpDalam.COLUMN_LOKASI,dataSpDalam.getLokasi());

        database.insert(DataSpDalam.TABLE_NAME , null, values);
        database.close();
        close();

    }

    @SuppressLint("Range")
    public DataSpDalam getData(String noprov, String noruas, int nosegment, int subSegment, String posisi) {
        open();
        DataSpDalam dataSpDalam = null;

        String where = DataSpDalam.COLUMN_NOPROV+" = ? AND "+DataSpDalam.COLUMN_RUAS+" = ? AND "+DataSpDalam.COLUMN_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_SUB_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_POSISI+" = ? ";
        Cursor cursor = database.query(DataSpDalam.TABLE_NAME,
                new String[]{DataSpDalam.COLUMN_ID, DataSpDalam.COLUMN_NOPROV, DataSpDalam.COLUMN_RUAS, DataSpDalam.COLUMN_SEGMENT, DataSpDalam.COLUMN_SUB_SEGMENT, DataSpDalam.COLUMN_POSISI,
                        DataSpDalam.COLUMN_LEBAR, DataSpDalam.COLUMN_GAMBAR , DataSpDalam.COLUMN_GAMBAR_ICON, DataSpDalam.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subSegment), posisi}, null, null, null, null);

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            dataSpDalam = new DataSpDalam(
                    cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataSpDalam.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataSpDalam.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_POSISI)),
                    cursor.getFloat(cursor.getColumnIndex(DataSpDalam.COLUMN_LEBAR)),
                    cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_LOKASI)));
        }
        cursor.close();
        database.close();
        close();


        return dataSpDalam;
    }

    public void updateData(DataSpDalam dataSpDalam) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataSpDalam.COLUMN_LEBAR, dataSpDalam.getLebar());
        values.put(DataSpDalam.COLUMN_GAMBAR,dataSpDalam.getGambar());
        values.put(DataSpDalam.COLUMN_GAMBAR_ICON,dataSpDalam.getIcon());
        values.put(DataSpDalam.COLUMN_LOKASI,dataSpDalam.getLokasi());
        String where = DataSpDalam.COLUMN_NOPROV+" = ? AND "+DataSpDalam.COLUMN_RUAS+" = ? AND "+DataSpDalam.COLUMN_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_SUB_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_POSISI+" = ? ";
        database.update(DataSpDalam.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataSpDalam.getNoprov()), String.valueOf(dataSpDalam.getNoruas()), String.valueOf(dataSpDalam.getNosegment()), String.valueOf(dataSpDalam.getSubsegment()), dataSpDalam.getPosisi()});

        database.close();
        close();
    }


    public ArrayList<DataSpDalam> getList(String noprov, String noruas, String posisi) {
        ArrayList<DataSpDalam> notes = new ArrayList<>();
        String tabel;
        tabel = DataSpDalam.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataSpDalam.COLUMN_NOPROV+" = ? AND "+DataSpDalam.COLUMN_RUAS+" = ? AND "+DataSpDalam.COLUMN_POSISI+" = ? ";
        String[] parameter = new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi};


        Cursor cursor = database.query(tabel,
                new String[]{DataSpDalam.COLUMN_ID, DataSpDalam.COLUMN_NOPROV, DataSpDalam.COLUMN_RUAS, DataSpDalam.COLUMN_SEGMENT, DataSpDalam.COLUMN_SUB_SEGMENT, DataSpDalam.COLUMN_POSISI,
                        DataSpDalam.COLUMN_LEBAR, DataSpDalam.COLUMN_GAMBAR , DataSpDalam.COLUMN_GAMBAR_ICON, DataSpDalam.COLUMN_LOKASI},
                where,
                parameter, null, null, DataSpDalam.COLUMN_SEGMENT+", "+DataSpDalam.COLUMN_SUB_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") DataSpDalam dataSpDalam = new DataSpDalam(
                        cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataSpDalam.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataSpDalam.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_POSISI)),
                        cursor.getFloat(cursor.getColumnIndex(DataSpDalam.COLUMN_LEBAR)),
                        cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataSpDalam.COLUMN_LOKASI)));
                notes.add(dataSpDalam);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }



    public void postData(DataSpDalam dataSpDalam){

        DataSpDalam dataSpDalamTempo =  getData(dataSpDalam.getNoprov(), dataSpDalam.getNoruas(), dataSpDalam.getNosegment(), dataSpDalam.getSubsegment(), dataSpDalam.getPosisi());

        if(dataSpDalamTempo!=null){
            updateData(dataSpDalam);
        }else{
            insertData(dataSpDalam);
        }

    }


    public void saveNormal(DataSpDalam dataSpDalam, int segmentAwal, int subsegmentAwal, int segmentAkhir, int subsegmentAkhir, String tipeSurvey){

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataSpDalam.COLUMN_LEBAR, dataSpDalam.getLebar());
        values.put(DataSpDalam.COLUMN_GAMBAR,dataSpDalam.getGambar());
        values.put(DataSpDalam.COLUMN_GAMBAR_ICON,dataSpDalam.getIcon());
        values.put(DataSpDalam.COLUMN_LOKASI,dataSpDalam.getLokasi());

        String where;

        if(tipeSurvey.equals("Normal")){
            where = DataSpDalam.COLUMN_NOPROV+" = ? AND "+DataSpDalam.COLUMN_RUAS+" = ? AND "+DataSpDalam.COLUMN_POSISI+" = ? AND ( ( "
                    +DataSpDalam.COLUMN_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_SEGMENT+ " != ? AND "+DataSpDalam.COLUMN_SUB_SEGMENT+" >= ? ) OR ( "
                    +DataSpDalam.COLUMN_SEGMENT+" > ? AND "+DataSpDalam.COLUMN_SEGMENT+" < ? ) OR ("
                    +DataSpDalam.COLUMN_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_SEGMENT+ " != ? AND "+DataSpDalam.COLUMN_SUB_SEGMENT+"<= ? ) OR ( "
                    +DataSpDalam.COLUMN_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_SUB_SEGMENT+" >= ? AND "+DataSpDalam.COLUMN_SUB_SEGMENT+" <=  ? ))";

        }else{

            where = DataSpDalam.COLUMN_NOPROV+" = ? AND "+DataSpDalam.COLUMN_RUAS+" = ? AND "+DataSpDalam.COLUMN_POSISI+" = ? AND ( ( "
                    +DataSpDalam.COLUMN_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_SEGMENT+ " != ? AND "+DataSpDalam.COLUMN_SUB_SEGMENT+" <= ? ) OR ( "
                    +DataSpDalam.COLUMN_SEGMENT+" < ? AND "+DataSpDalam.COLUMN_SEGMENT+" > ? ) OR ("
                    +DataSpDalam.COLUMN_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_SEGMENT+ " != ? AND "+DataSpDalam.COLUMN_SUB_SEGMENT+">= ? ) OR ( "
                    +DataSpDalam.COLUMN_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_SEGMENT+" = ? AND "+DataSpDalam.COLUMN_SUB_SEGMENT+" <= ? AND "+DataSpDalam.COLUMN_SUB_SEGMENT+" >=  ? ))";


        }

        database.update(DataSpDalam.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataSpDalam.getNoprov()), String.valueOf(dataSpDalam.getNoruas()), dataSpDalam.getPosisi(),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subsegmentAwal),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir),
                        String.valueOf(segmentAkhir), String.valueOf(segmentAwal),String.valueOf(subsegmentAkhir),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subsegmentAwal), String.valueOf(subsegmentAkhir)
                });

        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM dataspdalam ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }


}
