package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataMedian;

import java.util.ArrayList;

public class DbMinlet {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbMinlet(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    private void insertMinlet (DataInletMedian dataMinlet) {

        open();

        ContentValues values = new ContentValues();
        values.put(DataInletMedian.NOPROV,dataMinlet.getNoprov());
        values.put(DataInletMedian.NORUAS,dataMinlet.getNoruas());
        values.put(DataInletMedian.NOSEG,dataMinlet.getNosegment());
        values.put(DataInletMedian.SUBSEG,dataMinlet.getSubsegment());
        values.put(DataInletMedian.POSISI, dataMinlet.getPosisi());
        values.put(DataInletMedian.KEBERADAAN, dataMinlet.getKeberadaan());
        values.put(DataInletMedian.JENISPENAMPANG,dataMinlet.getJenisPenampang());
        values.put(DataInletMedian.TINGGI,dataMinlet.getTinggi());
        values.put(DataInletMedian.PANJANG, dataMinlet.getPanjang());
        values.put(DataInletMedian.LEBAR,dataMinlet.getLebar());
        values.put(DataInletMedian.TINGGISEDIMEN,dataMinlet.getTinggiSedimen());
        values.put(DataInletMedian.JENISKONSTRUKSI,dataMinlet.getJenisKonstruksi());
        values.put(DataInletMedian.KONDISISALURAN,dataMinlet.getKondisiSaluran());
        values.put(DataInletMedian.COLUMN_GAMBAR,dataMinlet.getGambar());
        values.put(DataInletMedian.COLUMN_GAMBAR_ICON,dataMinlet.getIcon());
        values.put(DataInletMedian.COLUMN_LOKASI,dataMinlet.getLokasi());


        database.insert(DataInletMedian.TABLE_NAME, null, values);

        database.close();
        close();


    }

    public DataInletMedian getMinlet(String noprov, String noruas, int nosegment, int subsegment, String posisi) {
        String tabel;
        database = dbHelper.getReadableDatabase();
        DataInletMedian dataMinlet = null;

        tabel = DataInletMedian.TABLE_NAME;


        String where = DataInletMedian.NOPROV+" = ? AND "+DataInletMedian.NORUAS+" = ? AND "+DataInletMedian.NOSEG+" = ? AND "+DataInletMedian.SUBSEG+" = ? AND "+DataInletMedian.POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataInletMedian.NOPROV, DataInletMedian.NORUAS, DataInletMedian.NOSEG,  DataInletMedian.SUBSEG, DataInletMedian.POSISI, DataInletMedian.KEBERADAAN, DataInletMedian.JENISPENAMPANG,
                        DataInletMedian.TINGGI, DataInletMedian.PANJANG, DataInletMedian.LEBAR, DataInletMedian.TINGGISEDIMEN, DataInletMedian.JENISKONSTRUKSI, DataInletMedian.KONDISISALURAN,
                        DataInletMedian.COLUMN_GAMBAR, DataInletMedian.COLUMN_GAMBAR_ICON, DataInletMedian.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment), posisi}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataMinlet = new DataInletMedian(
                    cursor.getString(cursor.getColumnIndex(DataInletMedian.NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataInletMedian.NORUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataInletMedian.NOSEG)),
                    cursor.getInt(cursor.getColumnIndex(DataInletMedian.SUBSEG)),
                    cursor.getString(cursor.getColumnIndex(DataInletMedian.POSISI)),
                    cursor.getString(cursor.getColumnIndex(DataInletMedian.KEBERADAAN)),
                    cursor.getString(cursor.getColumnIndex(DataInletMedian.JENISPENAMPANG)),
                    cursor.getFloat(cursor.getColumnIndex(DataInletMedian.TINGGI)),
                    cursor.getFloat(cursor.getColumnIndex(DataInletMedian.PANJANG)),
                    cursor.getFloat(cursor.getColumnIndex(DataInletMedian.LEBAR)),
                    cursor.getFloat(cursor.getColumnIndex(DataInletMedian.TINGGISEDIMEN)),
                    cursor.getString(cursor.getColumnIndex(DataInletMedian.JENISKONSTRUKSI)),
                    cursor.getString(cursor.getColumnIndex(DataInletMedian.KONDISISALURAN)),
                    cursor.getString(cursor.getColumnIndex(DataInletMedian.COLUMN_GAMBAR)),
                    cursor.getString(cursor.getColumnIndex(DataInletMedian.COLUMN_GAMBAR_ICON)),
                    cursor.getString(cursor.getColumnIndex(DataInletMedian.COLUMN_LOKASI)));

            // close the db connection

        }
        cursor.close();

        database.close();
        close();

        return dataMinlet;
    }

    public void updateMinlet(DataInletMedian dataMinlet) {
        open();
        String tabel;
        tabel = DataInletMedian.TABLE_NAME;

        ContentValues values = new ContentValues();

        values.put(DataInletMedian.KEBERADAAN, dataMinlet.getKeberadaan());
        values.put(DataInletMedian.JENISPENAMPANG,dataMinlet.getJenisPenampang());
        values.put(DataInletMedian.TINGGI,dataMinlet.getTinggi());
        values.put(DataInletMedian.PANJANG, dataMinlet.getPanjang());
        values.put(DataInletMedian.LEBAR,dataMinlet.getLebar());
        values.put(DataInletMedian.TINGGISEDIMEN,dataMinlet.getTinggiSedimen());
        values.put(DataInletMedian.JENISKONSTRUKSI,dataMinlet.getJenisKonstruksi());
        values.put(DataInletMedian.KONDISISALURAN,dataMinlet.getKondisiSaluran());
        values.put(DataInletMedian.COLUMN_GAMBAR,dataMinlet.getGambar());
        values.put(DataInletMedian.COLUMN_GAMBAR_ICON,dataMinlet.getIcon());
        values.put(DataInletMedian.COLUMN_LOKASI,dataMinlet.getLokasi());

        String where = DataInletMedian.NOPROV+" = ? AND "+DataInletMedian.NORUAS+" = ? AND "+DataInletMedian.NOSEG+" = ? AND "+DataInletMedian.SUBSEG+" = ? AND "+DataInletMedian.POSISI+" = ? ";
        database.update(tabel, values, where,
                new String[]{String.valueOf(dataMinlet.getNoprov()), String.valueOf(dataMinlet.getNoruas()), String.valueOf(dataMinlet.getNosegment()), String.valueOf(dataMinlet.getSubsegment()), dataMinlet.getPosisi()});
        database.close();
        close();
    }

    public ArrayList<DataInletMedian> getListMinlet(String noprov, String noruas, String posisi) {
        ArrayList<DataInletMedian> notes = new ArrayList<>();
        String tabel;
        tabel = DataInletMedian.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataInletMedian.NOPROV+" = ? AND "+DataInletMedian.NORUAS+" = ? AND "+DataInletMedian.POSISI+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataInletMedian.NOPROV, DataInletMedian.NORUAS, DataInletMedian.NOSEG,  DataInletMedian.SUBSEG, DataInletMedian.POSISI, DataInletMedian.KEBERADAAN, DataInletMedian.JENISPENAMPANG,
                        DataInletMedian.TINGGI, DataInletMedian.PANJANG, DataInletMedian.LEBAR, DataInletMedian.TINGGISEDIMEN, DataInletMedian.JENISKONSTRUKSI, DataInletMedian.KONDISISALURAN,
                        DataInletMedian.COLUMN_GAMBAR, DataInletMedian.COLUMN_GAMBAR_ICON, DataInletMedian.COLUMN_LOKASI},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), posisi}, null, null, DataInletMedian.NOSEG+", "+DataInletMedian.SUBSEG, null);

        if (cursor.moveToFirst()) {
            do {
                DataInletMedian dataMinlet = new DataInletMedian(
                        cursor.getString(cursor.getColumnIndex(DataInletMedian.NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataInletMedian.NORUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataInletMedian.NOSEG)),
                        cursor.getInt(cursor.getColumnIndex(DataInletMedian.SUBSEG)),
                        cursor.getString(cursor.getColumnIndex(DataInletMedian.POSISI)),
                        cursor.getString(cursor.getColumnIndex(DataInletMedian.KEBERADAAN)),
                        cursor.getString(cursor.getColumnIndex(DataInletMedian.JENISPENAMPANG)),
                        cursor.getFloat(cursor.getColumnIndex(DataInletMedian.TINGGI)),
                        cursor.getFloat(cursor.getColumnIndex(DataInletMedian.PANJANG)),
                        cursor.getFloat(cursor.getColumnIndex(DataInletMedian.LEBAR)),
                        cursor.getFloat(cursor.getColumnIndex(DataInletMedian.TINGGISEDIMEN)),
                        cursor.getString(cursor.getColumnIndex(DataInletMedian.JENISKONSTRUKSI)),
                        cursor.getString(cursor.getColumnIndex(DataInletMedian.KONDISISALURAN)),
                        cursor.getString(cursor.getColumnIndex(DataInletMedian.COLUMN_GAMBAR)),
                        cursor.getString(cursor.getColumnIndex(DataInletMedian.COLUMN_GAMBAR_ICON)),
                        cursor.getString(cursor.getColumnIndex(DataInletMedian.COLUMN_LOKASI)));

                notes.add(dataMinlet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }


    public void setMinlet(DataInletMedian dataMinlet){
        DataInletMedian dataMinletTempo =  getMinlet(dataMinlet.getNoprov(), dataMinlet.getNoruas(), dataMinlet.getNosegment(), dataMinlet.getSubsegment(), dataMinlet.getPosisi());
        if(dataMinletTempo!=null){
            updateMinlet(dataMinlet);
        }else{
            insertMinlet(dataMinlet);
        }

    }

    public void deleteMinlet (String noprov, String noruas, float segment) {

        String selectQuery = "delete FROM dataInletMedian where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+segment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM dataInletMedian where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM dataInletMedian ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void saveMinletNormal(DataInletMedian dataMinlet, int segmentAwal, int subSegmentAwal, int segmentAkhir, int subSegmentAkhir, String tipeSurvey){

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataInletMedian.LEBAR,dataMinlet.getLebar());
        values.put(DataInletMedian.COLUMN_GAMBAR,dataMinlet.getGambar());
        values.put(DataInletMedian.COLUMN_GAMBAR_ICON,dataMinlet.getIcon());
        values.put(DataInletMedian.COLUMN_LOKASI,dataMinlet.getLokasi());

        String where;

        if(tipeSurvey.equals("Normal")){
            where = DataInletMedian.NOPROV+" = ? AND "+DataInletMedian.NORUAS+" = ? AND "+DataInletMedian.POSISI+" = ? AND ( ( "
                    +DataInletMedian.NOSEG+" = ? AND "+DataInletMedian.NOSEG+ " != ? AND "+DataInletMedian.SUBSEG+" >= ? ) OR ( "
                    +DataInletMedian.NOSEG+" > ? AND "+DataInletMedian.NOSEG+" < ? ) OR ("
                    +DataInletMedian.NOSEG+" = ? AND "+DataInletMedian.NOSEG+ " != ? AND "+DataInletMedian.SUBSEG+"<= ? ) OR ( "
                    +DataInletMedian.NOSEG+" = ? AND "+DataInletMedian.NOSEG+" = ? AND "+DataInletMedian.SUBSEG+" >= ? AND "+DataInletMedian.SUBSEG+" <=  ? ))";

        }else{

            where = DataInletMedian.NOPROV+" = ? AND "+DataInletMedian.NORUAS+" = ? AND "+DataInletMedian.POSISI+" = ? AND ( ( "
                    +DataInletMedian.NOSEG+" = ? AND "+DataInletMedian.NOSEG+ " != ? AND "+DataInletMedian.SUBSEG+" <= ? ) OR ( "
                    +DataInletMedian.NOSEG+" < ? AND "+DataInletMedian.NOSEG+" > ? ) OR ("
                    +DataInletMedian.NOSEG+" = ? AND "+DataInletMedian.NOSEG+ " != ? AND "+DataInletMedian.SUBSEG+">= ? ) OR ( "
                    +DataInletMedian.NOSEG+" = ? AND "+DataInletMedian.NOSEG+" = ? AND "+DataInletMedian.SUBSEG+" <= ? AND "+DataInletMedian.SUBSEG+" >=  ? ))";


        }


        database.update(DataInletMedian.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataMinlet.getNoprov()), String.valueOf(dataMinlet.getNoruas()), dataMinlet.getPosisi(),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir),
                        String.valueOf(segmentAkhir), String.valueOf(segmentAwal),String.valueOf(subSegmentAkhir),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal), String.valueOf(subSegmentAkhir)
                });

        database.close();
        close();


    }

}
