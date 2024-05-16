package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;

import com.example.roadmanagement.kaltara.Interface.SingleSegment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Single;

public class DbSpinner {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbSpinner(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertSpinner (SingleSegment singleSegment) {



        open();

        ContentValues values = new ContentValues();

        values.put(SingleSegment.COLUMN_NOPROV,singleSegment.getNoprov());
        values.put(SingleSegment.COLUMN_RUAS,singleSegment.getNoruas());
        values.put(SingleSegment.COLUMN_SEGMENT,Integer.valueOf(singleSegment.getNoseg()));
        values.put(SingleSegment.COLUMN_SUB_SEGMENT,singleSegment.getSubSegment());
        values.put(SingleSegment.COLUMN_KMAWAL,singleSegment.getKmawal());
        values.put(SingleSegment.COLUMN_KMAKHIR,singleSegment.getKmakhir());
        values.put(SingleSegment.COLUMN_STAAWAL,singleSegment.getStaawal());
        values.put(SingleSegment.COLUMN_STAAKHIR,singleSegment.getStaakhir());
        database.insert(SingleSegment.TABLE_NAME, null, values);
        database.close();
        close();

    }


    public List<String> getRuas(String noprov) {
        List<String> notes = new ArrayList<>();
        String tabel;
        tabel = SingleSegment.TABLE_NAME;
        open();
        String where = SingleSegment.COLUMN_NOPROV+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_RUAS},
                where,
                new String[]{noprov}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                String ruasku = cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_RUAS));
                notes.add(ruasku);
                //Toast.makeText(context, String.valueOf(notes.size()), Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }
        database.close();
        close();

        return notes;
    }

    public List<String> getSegment(String noprov, String noruas) {
        List<String> notes = new ArrayList<>();
        String tabel;
        tabel = SingleSegment.TABLE_NAME;
        open();
        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SUB_SEGMENT+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_SEGMENT},
                where,
                new String[]{noprov, noruas, "0"}, null, null, SingleSegment.COLUMN_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                    String ruasku = String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SEGMENT)));
                    notes.add(ruasku);
               } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }

    public List<String> getSubSegment(String noprov, String noruas, String noseg) {
        List<String> notes = new ArrayList<>();
        String tabel;
        tabel = SingleSegment.TABLE_NAME;
        open();
        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SEGMENT+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_SUB_SEGMENT},
                where,
                new String[]{noprov, noruas, noseg}, null, null, SingleSegment.COLUMN_SUB_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                String ruasku = String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SUB_SEGMENT)));
                notes.add(ruasku);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        close();

        return notes;
    }

    public ArrayList<SingleSegment> listSegmentFull(String noprov, String noruas) {
        ArrayList<SingleSegment> notes = new ArrayList<>();
        String tabel;
        tabel = SingleSegment.TABLE_NAME;
        open();
        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_NOPROV, SingleSegment.COLUMN_RUAS, SingleSegment.COLUMN_SEGMENT, SingleSegment.COLUMN_SUB_SEGMENT, SingleSegment.COLUMN_KMAWAL, SingleSegment.COLUMN_KMAKHIR, SingleSegment.COLUMN_STAAWAL, SingleSegment.COLUMN_STAAKHIR},
                where,
                new String[]{noprov, noruas}, null, null, SingleSegment.COLUMN_SEGMENT+", "+SingleSegment.COLUMN_SUB_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                //Toast.makeText(context,  String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SEGMENT))), Toast.LENGTH_SHORT).show();
                SingleSegment dataSegment = new SingleSegment(
                        String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_NOPROV))),
                        String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_RUAS))),
                        cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAWAL)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAKHIR)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAWAL)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAKHIR)));
                notes.add(dataSegment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }


    public ArrayList<SingleSegment> listSegment(String noprov, String noruas) {
        ArrayList<SingleSegment> notes = new ArrayList<>();
        String tabel;
        tabel = SingleSegment.TABLE_NAME;
        open();
        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SUB_SEGMENT+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_NOPROV, SingleSegment.COLUMN_RUAS, SingleSegment.COLUMN_SEGMENT, SingleSegment.COLUMN_SUB_SEGMENT, SingleSegment.COLUMN_KMAWAL, SingleSegment.COLUMN_KMAKHIR, SingleSegment.COLUMN_STAAWAL, SingleSegment.COLUMN_STAAKHIR},
                where,
                new String[]{noprov, noruas, "0"}, SingleSegment.COLUMN_SEGMENT, null, SingleSegment.COLUMN_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                //Toast.makeText(context,  String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SEGMENT))), Toast.LENGTH_SHORT).show();
                SingleSegment dataSegment = new SingleSegment(
                        String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_NOPROV))),
                        String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_RUAS))),
                        cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAWAL)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAKHIR)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAWAL)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAKHIR)));
                notes.add(dataSegment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        /*if(tipeSurvey.equals("Opposite")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(notes, Comparator.comparing(SingleSegment::getNoseg));
                Collections.reverse(notes);
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(notes, Comparator.comparing(SingleSegment::getNoseg));
            }
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(notes, Comparator.comparing(SingleSegment::getNoseg));
        }

        return notes;
    }

    public ArrayList<SingleSegment> listSubSegment(String noprov, String noruas, int segment) {
        ArrayList<SingleSegment> notes = new ArrayList<>();
        String tabel;
        tabel = SingleSegment.TABLE_NAME;
        open();
        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SEGMENT+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_NOPROV, SingleSegment.COLUMN_RUAS, SingleSegment.COLUMN_SEGMENT, SingleSegment.COLUMN_SUB_SEGMENT, SingleSegment.COLUMN_KMAWAL, SingleSegment.COLUMN_KMAKHIR, SingleSegment.COLUMN_STAAWAL, SingleSegment.COLUMN_STAAKHIR},
                where,
                new String[]{noprov, noruas, String.valueOf(segment)}, null, null, SingleSegment.COLUMN_SUB_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                //Toast.makeText(context,  String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SEGMENT))), Toast.LENGTH_SHORT).show();
                SingleSegment dataSegment = new SingleSegment(
                        String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_NOPROV))),
                        String.valueOf(cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_RUAS))),
                        cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SEGMENT)),
                        cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SUB_SEGMENT)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAWAL)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAKHIR)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAWAL)),
                        cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAKHIR)));
                notes.add(dataSegment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        /*if(tipeSurvey.equals("Opposite")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(notes, Comparator.comparing(SingleSegment::getSubSegment));
                Collections.reverse(notes);
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(notes, Comparator.comparing(SingleSegment::getSubSegment));
            }
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(notes, Comparator.comparing(SingleSegment::getSubSegment));
        }

        return notes;
    }



    public void updateSpinner(SingleSegment singleSegment) {
        open();

        ContentValues values = new ContentValues();

        values.put(SingleSegment.COLUMN_KMAWAL,singleSegment.getKmawal());
        values.put(SingleSegment.COLUMN_KMAKHIR,singleSegment.getKmakhir());
        values.put(SingleSegment.COLUMN_STAAWAL,singleSegment.getStaawal());
        values.put(SingleSegment.COLUMN_STAAKHIR,singleSegment.getStaakhir());
        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SEGMENT+" = ? AND "+SingleSegment.COLUMN_SUB_SEGMENT+" = ? ";
        database.update(SingleSegment.TABLE_NAME, values, where,
                new String[]{String.valueOf(singleSegment.getNoprov()), String.valueOf(singleSegment.getNoruas()), String.valueOf(singleSegment.getNoseg()), String.valueOf(singleSegment.getSubSegment())});

        database.close();
        close();
    }


    public SingleSegment getSpinner(String noprov, String noruas, int nosegment, float subsegment) {
        open();
        SingleSegment singleSegment = null;


        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SEGMENT+" = ? AND "+SingleSegment.COLUMN_SUB_SEGMENT+" = ?";
        Cursor cursor = database.query(SingleSegment.TABLE_NAME,
                new String[]{SingleSegment.COLUMN_NOPROV, SingleSegment.COLUMN_RUAS, SingleSegment.COLUMN_SEGMENT, SingleSegment.COLUMN_SUB_SEGMENT, SingleSegment.COLUMN_KMAWAL, SingleSegment.COLUMN_KMAKHIR, SingleSegment.COLUMN_STAAWAL, SingleSegment.COLUMN_STAAKHIR},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(nosegment), String.valueOf(subsegment)}, null, null, null, null);

        //  int i =1;

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {

            singleSegment = new SingleSegment(
                    cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_NOPROV)),
                    cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_RUAS)),
                    cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SEGMENT)),
                    cursor.getInt(cursor.getColumnIndex(SingleSegment.COLUMN_SUB_SEGMENT)),
                    cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAWAL)),
                    cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAKHIR)),
                    cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAWAL)),
                    cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAKHIR)));

            // close the db connection

        }
        cursor.close();
        database.close();
        close();


        return singleSegment;
    }


    public void setSpinner(SingleSegment segment){
        SingleSegment singleSegment =  getSpinner(segment.getNoprov(), segment.getNoruas(), segment.getNoseg(), segment.getSubSegment());

        if(singleSegment!=null){
            updateSpinner(segment);
        }else{
            insertSpinner(segment);

        }

    }

    public void deleteSpinner(String noprov, String noruas, float subsegment) {

        String selectQuery = "delete FROM segmentku where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND subsegment>"+subsegment;
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public int getMaksSegment(String noprov, String noruas) {

        String selectQuery = "SELECT max(spinnersegment) as id FROM segmentku where noprov ='"+noprov+"' AND noruas ='"+noruas+"'";

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("id"));

        cursor.close();
        database.close();
        close();

        return maxid;
    }



    public int getMaksSegmentSub(String noprov, String noruas, int segment) {

        String selectQuery = "SELECT max(subsegment) as id FROM segmentku where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND spinnersegment="+segment;

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int maxid = cursor.getInt(cursor.getColumnIndex("id"));

        cursor.close();
        database.close();
        close();

        return maxid;
    }

    public float getMaksSegmentSub(String noprov, String noruas) {

        String selectQuery = "SELECT max(subsegment) as id FROM segmentku where noprov ='"+noprov+"' AND noruas ='"+noruas+"'";

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        float maxid = cursor.getFloat(cursor.getColumnIndex("id"));

        cursor.close();
        database.close();
        close();

        return maxid;
    }

    public int getSegment(String noprov, String noruas, float subsegment) {

        int segment = 0;

        open();

        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SUB_SEGMENT+" = ? ";
        Cursor cursor = database.query(SingleSegment.TABLE_NAME,
                new String[]{SingleSegment.COLUMN_SEGMENT},
                where,
                new String[]{String.valueOf(noprov), String.valueOf(noruas), String.valueOf(subsegment)}, null, null, null, null);

        //  int i =1;

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {

            segment =  Integer.valueOf(cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_SEGMENT)));


        }
        cursor.close();
        database.close();
        close();


        return segment;
    }

    public void tutup() {
        dbHelper.close();
    }

    public String getSta(String noprov, String noruas, int noseg, String kondisi) {

        int subseg;
        String staAwal = "";
        String staAkhir = "";

        if(kondisi.equals("awal")){
            subseg = 0;
        }else{
            subseg = getMaksSegmentSub(noprov, noruas, noseg);
        }

        String tabel = SingleSegment.TABLE_NAME;
        open();
        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SEGMENT+" = ? AND "+SingleSegment.COLUMN_SUB_SEGMENT+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_NOPROV, SingleSegment.COLUMN_RUAS, SingleSegment.COLUMN_SEGMENT, SingleSegment.COLUMN_SUB_SEGMENT, SingleSegment.COLUMN_KMAWAL, SingleSegment.COLUMN_KMAKHIR, SingleSegment.COLUMN_STAAWAL, SingleSegment.COLUMN_STAAKHIR},
                where,
                new String[]{noprov, noruas, String.valueOf(noseg),String.valueOf(subseg)}, SingleSegment.COLUMN_SEGMENT, null, SingleSegment.COLUMN_SEGMENT, null);


        if (cursor.moveToFirst()) {

               staAwal = cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAWAL));
               staAkhir = cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAKHIR));
        }
        cursor.close();
        database.close();
        close();

        if(kondisi.equals("awal")){
            return staAwal;
        }else{
            return staAkhir;
        }
    }

    public String getKm(String noprov, String noruas, int noseg, String kondisi) {

        int subseg;
        String staAwal = "";
        String staAkhir = "";

        if(kondisi.equals("awal")){
            subseg = 0;
        }else{
            subseg = getMaksSegmentSub(noprov, noruas, noseg);
        }

        String tabel = SingleSegment.TABLE_NAME;
        open();
        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SEGMENT+" = ? AND "+SingleSegment.COLUMN_SUB_SEGMENT+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_NOPROV, SingleSegment.COLUMN_RUAS, SingleSegment.COLUMN_SEGMENT, SingleSegment.COLUMN_SUB_SEGMENT, SingleSegment.COLUMN_KMAWAL, SingleSegment.COLUMN_KMAKHIR, SingleSegment.COLUMN_STAAWAL, SingleSegment.COLUMN_STAAKHIR},
                where,
                new String[]{noprov, noruas, String.valueOf(noseg), String.valueOf(subseg)}, SingleSegment.COLUMN_SEGMENT, null, SingleSegment.COLUMN_SEGMENT, null);


        if (cursor.moveToFirst()) {

            staAwal = cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAWAL));
            staAkhir = cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAKHIR));
        }
        cursor.close();
        database.close();
        close();

        if(kondisi.equals("awal")){
            return staAwal;
        }else{
            return staAkhir;
        }
    }

    public void deleteMaks(String noprov, String noruas, int noseg, int subseg) {

        String selectQuery = "delete FROM segmentku where noprov ='"+noprov+"' AND noruas ='"+noruas+"' AND ( ( spinnersegment="+noseg+" AND subsegment>"+subseg+ " ) OR spinnersegment> "+noseg+")";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

    public void clear() {

        String selectQuery = "delete FROM segmentku ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }


/*
    public ArrayList<String> getSegment(String noprov, String ruas) {
        ArrayList<String> notes = new ArrayList<>();
        String tabel;
        tabel = SingleSegment.TABLE_NAME;
        open();
        String where = SingleSegment.COLUMN_NOPROV+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_RUAS},
                where,
                new String[]{noprov}, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                String ruasku = cursor.getString(cursor.getColumnIndex(DataTemporari.COLUMN_TIPE));
                notes.add(ruasku);
            } while (cursor.moveToNext());
        }
        close();

        return notes;
    }

    public String getKm(String noprov, String noruas, String noseg){
        String kmku = null;
        String tabel = SingleSegment.TABLE_NAME;
        open();

       String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SEGMENT+" = ? ";
       Cursor cursor = database.query(tabel,
               new String[]{SingleSegment.COLUMN_KMAWAL, SingleSegment.COLUMN_KMAKHIR},
               where,
               new String[]{noprov, noruas, noseg}, null, null, null, null);


       if (cursor.moveToFirst()) {

           kmku = cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAWAL)) +" - "+cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAKHIR));


       }
       cursor.close();
       database.close();
       close();

        return kmku;
   }


    public List<String> getKMaja(String noprov, String noruas) {
        List<String> notes = new ArrayList<>();
        String tabel;
        tabel = SingleSegment.TABLE_NAME;
        open();
        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ?";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_KMAKHIR},
                where,
                new String[]{noprov, noruas}, null, null, SingleSegment.COLUMN_SEGMENT, null);


        if (cursor.moveToFirst()) {
            do {
                       notes.add( cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_KMAKHIR)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        close();

        return notes;
    }


    public String getSta(String noprov, String noruas, String noseg){
        String kmku = null;
        String tabel = SingleSegment.TABLE_NAME;
        open();

        String where = SingleSegment.COLUMN_NOPROV+" = ? AND "+SingleSegment.COLUMN_RUAS+" = ? AND "+SingleSegment.COLUMN_SEGMENT+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{SingleSegment.COLUMN_STAAWAL, SingleSegment.COLUMN_STAAKHIR},
                where,
                new String[]{noprov, noruas, noseg}, null, null, null, null);


        if (cursor.moveToFirst()) {

            kmku = cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAWAL)) +" - "+cursor.getString(cursor.getColumnIndex(SingleSegment.COLUMN_STAAKHIR));


        }
        cursor.close();
        database.close();
        close();

        return kmku;
    }
*/
}
