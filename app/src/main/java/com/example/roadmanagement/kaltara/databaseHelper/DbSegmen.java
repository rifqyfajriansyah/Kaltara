package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;

import java.util.ArrayList;
import java.util.List;

public class DbSegmen {
    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbSegmen(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertSegmen (DataSegmen dataSegmen) {
        open();

        ContentValues values = new ContentValues();
        values.put(DataSegmen.COLUMN_NOPROV,dataSegmen.getNoprov());
        values.put(DataSegmen.COLUMN_RUAS,dataSegmen.getNoruas());
        values.put(DataSegmen.COLUMN_SEGMENT,dataSegmen.getNosegment());
        values.put(DataSegmen.COLUMN_SUB_SEGMENT,dataSegmen.getSubsegment());
        values.put(DataSegmen.COLUMN_SEGMENL1,dataSegmen.getSegmentl1());
        values.put(DataSegmen.COLUMN_SEGMENL2,dataSegmen.getSegmentl2());
        values.put(DataSegmen.COLUMN_SEGMENL3,dataSegmen.getSegmentl3());
        values.put(DataSegmen.COLUMN_SEGMENL4,dataSegmen.getSegmentl4());
        values.put(DataSegmen.COLUMN_SEGMENL5,dataSegmen.getSegmentl5());
        values.put(DataSegmen.COLUMN_SEGMENL6,dataSegmen.getSegmentl6());
        values.put(DataSegmen.COLUMN_SEGMENL7,dataSegmen.getSegmentl7());
        values.put(DataSegmen.COLUMN_SEGMENL8,dataSegmen.getSegmentl8());
        values.put(DataSegmen.COLUMN_SEGMENL9,dataSegmen.getSegmentl9());
        values.put(DataSegmen.COLUMN_SEGMENL10,dataSegmen.getSegmentl10());

        values.put(DataSegmen.COLUMN_SEGMENR1,dataSegmen.getSegmentr1());
        values.put(DataSegmen.COLUMN_SEGMENR2,dataSegmen.getSegmentr2());
        values.put(DataSegmen.COLUMN_SEGMENR3,dataSegmen.getSegmentr3());
        values.put(DataSegmen.COLUMN_SEGMENR4,dataSegmen.getSegmentr4());
        values.put(DataSegmen.COLUMN_SEGMENR5,dataSegmen.getSegmentr5());
        values.put(DataSegmen.COLUMN_SEGMENR6,dataSegmen.getSegmentr6());
        values.put(DataSegmen.COLUMN_SEGMENR7,dataSegmen.getSegmentr7());
        values.put(DataSegmen.COLUMN_SEGMENR8,dataSegmen.getSegmentr8());
        values.put(DataSegmen.COLUMN_SEGMENR9,dataSegmen.getSegmentr9());
        values.put(DataSegmen.COLUMN_SEGMENR10,dataSegmen.getSegmentr10());

        values.put(DataSegmen.COLUMN_JUMLAHSEGMEN,dataSegmen.getJumlahsegment());
        values.put(DataSegmen.VERTIKAL,dataSegmen.getVertikal());
        values.put(DataSegmen.HORIZONTAL,dataSegmen.getHorizontal());
        values.put(DataSegmen.TIPEJALAN,dataSegmen.getTipejalan());
        values.put(DataSegmen.LEBARPVMT, dataSegmen.getLebarpvmt());
        values.put(DataSegmen.GRADE, dataSegmen.getGrade());
        database.insert(DataSegmen.TABLE_NAME, null, values);

        database.close();
        close();

    }

    public DataSegmen getSegmen(String noprov, String noruas, int nosegment, int subsegment) {

       database = dbHelper.getReadableDatabase();
        DataSegmen dataSegmen = null;
        String where = DataSegmen.COLUMN_NOPROV+" = ? AND "+DataSegmen.COLUMN_RUAS+" = ? AND "+DataSegmen.COLUMN_SEGMENT+" = ? AND "+DataSegmen.COLUMN_SUB_SEGMENT+" = ?";
        Cursor cursor = database.query(DataSegmen.TABLE_NAME,
                new String[]{DataSegmen.COLUMN_ID, DataSegmen.COLUMN_NOPROV, DataSegmen.COLUMN_RUAS, DataSegmen.COLUMN_SEGMENT, DataSegmen.COLUMN_SUB_SEGMENT,
                        DataSegmen.COLUMN_SEGMENL1, DataSegmen.COLUMN_SEGMENL2, DataSegmen.COLUMN_SEGMENL3, DataSegmen.COLUMN_SEGMENL4, DataSegmen.COLUMN_SEGMENL5, DataSegmen.COLUMN_SEGMENL6, DataSegmen.COLUMN_SEGMENL7, DataSegmen.COLUMN_SEGMENL8, DataSegmen.COLUMN_SEGMENL9, DataSegmen.COLUMN_SEGMENL10,
                        DataSegmen.COLUMN_SEGMENR1, DataSegmen.COLUMN_SEGMENR2, DataSegmen.COLUMN_SEGMENR3, DataSegmen.COLUMN_SEGMENR4, DataSegmen.COLUMN_SEGMENR5, DataSegmen.COLUMN_SEGMENR6, DataSegmen.COLUMN_SEGMENR7, DataSegmen.COLUMN_SEGMENR8, DataSegmen.COLUMN_SEGMENR9, DataSegmen.COLUMN_SEGMENR10,
                        DataSegmen.COLUMN_JUMLAHSEGMEN, DataSegmen.VERTIKAL, DataSegmen.HORIZONTAL, DataSegmen.TIPEJALAN, DataSegmen.LEBARPVMT, DataSegmen.GRADE
                },
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment)}, null, null, null, null);


        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataSegmen = new DataSegmen(
                    cursor.getString(cursor.getColumnIndex(DataSegmen.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataSegmen.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SUB_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL1)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL2)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL3)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL4)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL5)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL6)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL7)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL8)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL9)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL10)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR1)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR2)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR3)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR4)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR5)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR6)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR7)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR8)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR9)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR10)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_JUMLAHSEGMEN)),
                    cursor.getString(cursor.getColumnIndex(DataSegmen.VERTIKAL)),
                    cursor.getString(cursor.getColumnIndex(DataSegmen.HORIZONTAL)),
                    cursor.getString(cursor.getColumnIndex(DataSegmen.TIPEJALAN)),
                    cursor.getFloat(cursor.getColumnIndex(DataSegmen.LEBARPVMT)),
                    cursor.getFloat(cursor.getColumnIndex(DataSegmen.GRADE)));

            // close the db connection

        }
        cursor.close();
        database.close();
        close();

        return dataSegmen;
    }

    public void updateSegmen(DataSegmen dataSegmen) {
        open();

        ContentValues values = new ContentValues();
        values.put(DataSegmen.COLUMN_SEGMENL1,dataSegmen.getSegmentl1());
        values.put(DataSegmen.COLUMN_SEGMENL2,dataSegmen.getSegmentl2());
        values.put(DataSegmen.COLUMN_SEGMENL3,dataSegmen.getSegmentl3());
        values.put(DataSegmen.COLUMN_SEGMENL4,dataSegmen.getSegmentl4());
        values.put(DataSegmen.COLUMN_SEGMENL5,dataSegmen.getSegmentl5());
        values.put(DataSegmen.COLUMN_SEGMENL6,dataSegmen.getSegmentl6());
        values.put(DataSegmen.COLUMN_SEGMENL7,dataSegmen.getSegmentl7());
        values.put(DataSegmen.COLUMN_SEGMENL8,dataSegmen.getSegmentl8());
        values.put(DataSegmen.COLUMN_SEGMENL9,dataSegmen.getSegmentl9());
        values.put(DataSegmen.COLUMN_SEGMENL10,dataSegmen.getSegmentl10());

        values.put(DataSegmen.COLUMN_SEGMENR1,dataSegmen.getSegmentr1());
        values.put(DataSegmen.COLUMN_SEGMENR2,dataSegmen.getSegmentr2());
        values.put(DataSegmen.COLUMN_SEGMENR3,dataSegmen.getSegmentr3());
        values.put(DataSegmen.COLUMN_SEGMENR4,dataSegmen.getSegmentr4());
        values.put(DataSegmen.COLUMN_SEGMENR5,dataSegmen.getSegmentr5());
        values.put(DataSegmen.COLUMN_SEGMENR6,dataSegmen.getSegmentr6());
        values.put(DataSegmen.COLUMN_SEGMENR7,dataSegmen.getSegmentr7());
        values.put(DataSegmen.COLUMN_SEGMENR8,dataSegmen.getSegmentr8());
        values.put(DataSegmen.COLUMN_SEGMENR9,dataSegmen.getSegmentr9());
        values.put(DataSegmen.COLUMN_SEGMENR10,dataSegmen.getSegmentr10());

        values.put(DataSegmen.COLUMN_JUMLAHSEGMEN,dataSegmen.getJumlahsegment());
        values.put(DataSegmen.VERTIKAL,dataSegmen.getVertikal());
        values.put(DataSegmen.HORIZONTAL,dataSegmen.getHorizontal());
        values.put(DataSegmen.TIPEJALAN,dataSegmen.getTipejalan());
        values.put(DataSegmen.LEBARPVMT,dataSegmen.getLebarpvmt());
        values.put(DataSegmen.GRADE,dataSegmen.getGrade());
        String where = DataSegmen.COLUMN_NOPROV+" = ? AND "+DataSegmen.COLUMN_RUAS+" = ? AND "+DataSegmen.COLUMN_SEGMENT+" = ? AND "+DataSegmen.COLUMN_SUB_SEGMENT+" = ?";
        database.update(DataSegmen.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataSegmen.getNoprov()), String.valueOf(dataSegmen.getNoruas()), String.valueOf(dataSegmen.getNosegment()), String.valueOf(dataSegmen.getSubsegment())});
        database.close();
        close();
    }

    public void postSegment(DataSegmen dataSegmen){

        DataSegmen dataSegmentTempo = getSegmen(dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), dataSegmen.getSubsegment());

        if(dataSegmentTempo!=null){
            updateSegmen(dataSegmen);
        }else{
            insertSegmen(dataSegmen);
        }

    }

    public ArrayList<DataSegmen> getAllsegment(String noprov, String noruas) {
        ArrayList<DataSegmen> notes = new ArrayList<>();
        String tabel;
        tabel = DataSegmen.TABLE_NAME;
        database = dbHelper.getReadableDatabase();
        String where = DataSegmen.COLUMN_NOPROV+" = ? AND "+DataSegmen.COLUMN_RUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{DataSegmen.COLUMN_NOPROV, DataSegmen.COLUMN_RUAS, DataSegmen.COLUMN_SEGMENT, DataSegmen.COLUMN_SUB_SEGMENT,
                        DataSegmen.COLUMN_SEGMENL1, DataSegmen.COLUMN_SEGMENL2, DataSegmen.COLUMN_SEGMENL3, DataSegmen.COLUMN_SEGMENL4, DataSegmen.COLUMN_SEGMENL5, DataSegmen.COLUMN_SEGMENL6, DataSegmen.COLUMN_SEGMENL7, DataSegmen.COLUMN_SEGMENL8, DataSegmen.COLUMN_SEGMENL9, DataSegmen.COLUMN_SEGMENL10,
                        DataSegmen.COLUMN_SEGMENR1, DataSegmen.COLUMN_SEGMENR2, DataSegmen.COLUMN_SEGMENR3, DataSegmen.COLUMN_SEGMENR4, DataSegmen.COLUMN_SEGMENR5, DataSegmen.COLUMN_SEGMENR6, DataSegmen.COLUMN_SEGMENR7, DataSegmen.COLUMN_SEGMENR8, DataSegmen.COLUMN_SEGMENR9, DataSegmen.COLUMN_SEGMENR10,
                        DataSegmen.COLUMN_JUMLAHSEGMEN, DataSegmen.VERTIKAL, DataSegmen.HORIZONTAL, DataSegmen.TIPEJALAN, DataSegmen.LEBARPVMT, DataSegmen.GRADE},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas)}, null, null, DataSegmen.COLUMN_SEGMENT+", "+DataSegmen.COLUMN_SUB_SEGMENT, null);

        if (cursor.moveToFirst()) {
            do {
                DataSegmen dataSegmen = new DataSegmen(
                        cursor.getString(cursor.getColumnIndex(DataSegmen.COLUMN_NOPROV)),
                        cursor.getString(cursor.getColumnIndex(DataSegmen.COLUMN_RUAS)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SUB_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL1)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL2)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL3)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL4)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL5)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL6)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL7)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL8)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL9)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL10)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR1)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR2)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR3)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR4)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR5)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR6)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR7)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR8)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR9)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR10)),
                        cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_JUMLAHSEGMEN)),
                        cursor.getString(cursor.getColumnIndex(DataSegmen.VERTIKAL)),
                        cursor.getString(cursor.getColumnIndex(DataSegmen.HORIZONTAL)),
                        cursor.getString(cursor.getColumnIndex(DataSegmen.TIPEJALAN)),
                        cursor.getFloat(cursor.getColumnIndex(DataSegmen.LEBARPVMT)),
                        cursor.getFloat(cursor.getColumnIndex(DataSegmen.GRADE)));
                        notes.add(dataSegmen);


            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }

    public void tutup() {
        dbHelper.close();
    }

    public void deleteSegment(String noprov, String noruas, float subsegment) {

        String selectQuery = "delete FROM datasegmen where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+subsegment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void saveSegmentNormal(DataSegmen dataSegmen, int segmentAwal, int subSegmentAwal, int segmentAkhir, int subSegmentAkhir, String tipeSurvey){

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataSegmen.COLUMN_SEGMENL1,dataSegmen.getSegmentl1());
        values.put(DataSegmen.COLUMN_SEGMENL2,dataSegmen.getSegmentl2());
        values.put(DataSegmen.COLUMN_SEGMENL3,dataSegmen.getSegmentl3());
        values.put(DataSegmen.COLUMN_SEGMENL4,dataSegmen.getSegmentl4());
        values.put(DataSegmen.COLUMN_SEGMENL5,dataSegmen.getSegmentl5());
        values.put(DataSegmen.COLUMN_SEGMENL6,dataSegmen.getSegmentl6());
        values.put(DataSegmen.COLUMN_SEGMENL7,dataSegmen.getSegmentl7());
        values.put(DataSegmen.COLUMN_SEGMENL8,dataSegmen.getSegmentl8());
        values.put(DataSegmen.COLUMN_SEGMENL9,dataSegmen.getSegmentl9());
        values.put(DataSegmen.COLUMN_SEGMENL10,dataSegmen.getSegmentl10());

        values.put(DataSegmen.COLUMN_SEGMENR1,dataSegmen.getSegmentr1());
        values.put(DataSegmen.COLUMN_SEGMENR2,dataSegmen.getSegmentr2());
        values.put(DataSegmen.COLUMN_SEGMENR3,dataSegmen.getSegmentr3());
        values.put(DataSegmen.COLUMN_SEGMENR4,dataSegmen.getSegmentr4());
        values.put(DataSegmen.COLUMN_SEGMENR5,dataSegmen.getSegmentr5());
        values.put(DataSegmen.COLUMN_SEGMENR6,dataSegmen.getSegmentr6());
        values.put(DataSegmen.COLUMN_SEGMENR7,dataSegmen.getSegmentr7());
        values.put(DataSegmen.COLUMN_SEGMENR8,dataSegmen.getSegmentr8());
        values.put(DataSegmen.COLUMN_SEGMENR9,dataSegmen.getSegmentr9());
        values.put(DataSegmen.COLUMN_SEGMENR10,dataSegmen.getSegmentr10());

        values.put(DataSegmen.COLUMN_JUMLAHSEGMEN,dataSegmen.getJumlahsegment());
        values.put(DataSegmen.VERTIKAL,dataSegmen.getVertikal());
        values.put(DataSegmen.HORIZONTAL,dataSegmen.getHorizontal());
        values.put(DataSegmen.TIPEJALAN,dataSegmen.getTipejalan());
        values.put(DataSegmen.LEBARPVMT,dataSegmen.getLebarpvmt());
        values.put(DataSegmen.GRADE,dataSegmen.getGrade());

        String where;

        if(tipeSurvey.equals("Normal")){
            where = DataSegmen.COLUMN_NOPROV+" = ? AND "+DataSegmen.COLUMN_RUAS+" = ? AND ( ( "
                    +DataSegmen.COLUMN_SEGMENT+" = ? AND "+DataSegmen.COLUMN_SEGMENT+ " != ? AND "+DataSegmen.COLUMN_SUB_SEGMENT+" >= ? ) OR ( "
                    +DataSegmen.COLUMN_SEGMENT+" > ? AND "+DataSegmen.COLUMN_SEGMENT+" < ? ) OR ("
                    +DataSegmen.COLUMN_SEGMENT+" = ? AND "+DataSegmen.COLUMN_SEGMENT+ " != ? AND "+DataSegmen.COLUMN_SUB_SEGMENT+"<= ? ) OR ( "
                    +DataSegmen.COLUMN_SEGMENT+" = ? AND "+DataSegmen.COLUMN_SEGMENT+" = ? AND "+DataSegmen.COLUMN_SUB_SEGMENT+" >= ? AND "+DataSegmen.COLUMN_SUB_SEGMENT+" <=  ? ))";

        }else{

            where = DataSegmen.COLUMN_NOPROV+" = ? AND "+DataSegmen.COLUMN_RUAS+" = ? AND ( ( "
                    +DataSegmen.COLUMN_SEGMENT+" = ? AND "+DataSegmen.COLUMN_SEGMENT+ " != ? AND "+DataSegmen.COLUMN_SUB_SEGMENT+" <= ? ) OR ( "
                    +DataSegmen.COLUMN_SEGMENT+" < ? AND "+DataSegmen.COLUMN_SEGMENT+" > ? ) OR ("
                    +DataSegmen.COLUMN_SEGMENT+" = ? AND "+DataSegmen.COLUMN_SEGMENT+ " != ? AND "+DataSegmen.COLUMN_SUB_SEGMENT+">= ? ) OR ( "
                    +DataSegmen.COLUMN_SEGMENT+" = ? AND "+DataSegmen.COLUMN_SEGMENT+" = ? AND "+DataSegmen.COLUMN_SUB_SEGMENT+" <= ? AND "+DataSegmen.COLUMN_SUB_SEGMENT+" >=  ? ))";


        }

        database.update(DataSegmen.TABLE_NAME, values, where,
                new String[]{String.valueOf(dataSegmen.getNoprov()), String.valueOf(dataSegmen.getNoruas()),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir),
                        String.valueOf(segmentAkhir), String.valueOf(segmentAwal),String.valueOf(subSegmentAkhir),
                        String.valueOf(segmentAwal), String.valueOf(segmentAkhir), String.valueOf(subSegmentAwal), String.valueOf(subSegmentAkhir)
                });

        database.close();
        close();


    }

    public DataSegmen getMaksSegment(String noprov, String noruas) {

        DataSegmen dataSegmen = null;
        String selectQuery = "SELECT * FROM datasegmen where noprov ='"+noprov+"' AND noruas ='"+noruas+"' order by nosegment desc , subsegment desc limit 1";
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            // prepare note object
            dataSegmen = new DataSegmen(
                    cursor.getString(cursor.getColumnIndex(DataSegmen.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(DataSegmen.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SUB_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL1)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL2)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL3)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL4)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL5)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL6)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL7)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL8)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL9)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENL10)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR1)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR2)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR3)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR4)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR5)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR6)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR7)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR8)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR9)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_SEGMENR10)),
                    cursor.getInt(cursor.getColumnIndex(DataSegmen.COLUMN_JUMLAHSEGMEN)),
                    cursor.getString(cursor.getColumnIndex(DataSegmen.VERTIKAL)),
                    cursor.getString(cursor.getColumnIndex(DataSegmen.HORIZONTAL)),
                    cursor.getString(cursor.getColumnIndex(DataSegmen.TIPEJALAN)),
                    cursor.getFloat(cursor.getColumnIndex(DataSegmen.LEBARPVMT)),
                    cursor.getFloat(cursor.getColumnIndex(DataSegmen.GRADE)));

            // close the db connection

        }

        cursor.close();
        database.close();
        close();

        return dataSegmen;

    }

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM datasegmen where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( nosegment="+noseg+" AND subsegment>"+subseg+ " ) OR nosegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM datasegmen ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

}
