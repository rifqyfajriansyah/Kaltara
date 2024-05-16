package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.roadmanagement.kaltara.Interface.DataRuas;
import com.example.roadmanagement.kaltara.Interface.UpdateTable;

import java.util.ArrayList;
import java.util.List;

public class DbTable {

    private Context context;
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbTable(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertDataTable (UpdateTable updateTable) {

        open();

        ContentValues values = new ContentValues();

        values.put(UpdateTable.COLUMN_NORUAS,updateTable.getNoruas());
        values.put(UpdateTable.COLUMN_WAKTU,updateTable.getWaktu());

        database.insert(UpdateTable.TABLE_NAME, null, values);
        database.close();
        close();

    }


    public ArrayList<UpdateTable> getTableAll() {
        ArrayList<UpdateTable> notes = new ArrayList<>();
        String tabel;
        tabel = UpdateTable.TABLE_NAME;
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(tabel,
                new String[]{UpdateTable.COLUMN_NORUAS, UpdateTable.COLUMN_WAKTU}, null,
                null, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                UpdateTable updateTable = new UpdateTable(
                        cursor.getString(cursor.getColumnIndex(UpdateTable.COLUMN_NORUAS)),
                        cursor.getString(cursor.getColumnIndex(UpdateTable.COLUMN_WAKTU))
                );

                notes.add(updateTable);
            } while (cursor.moveToNext());
        }


        cursor.close();
        database.close();
        close();

        return notes;
    }





    public void UpdateTableku(UpdateTable updateTable) {
        open();
        ContentValues values = new ContentValues();
        values.put(UpdateTable.COLUMN_WAKTU, updateTable.getWaktu());
        String where = UpdateTable.COLUMN_NORUAS+" = ? ";
        database.update(UpdateTable.TABLE_NAME, values, where,
                new String[]{updateTable.getNoruas()});
        database.close();
        close();
    }



    public UpdateTable getTable(String noruas){
        String tabel;
        UpdateTable updateTable = null;
        String isi = null;
        tabel = UpdateTable.TABLE_NAME;
        database = dbHelper.getWritableDatabase();
        String where = UpdateTable.COLUMN_NORUAS+" = ? ";
        Cursor cursor = database.query(tabel,
                new String[]{UpdateTable.COLUMN_NORUAS, UpdateTable.COLUMN_WAKTU},
                where,
                new String[]{noruas}, null, null, null, null);


        if (cursor.moveToFirst()) {

            updateTable = new UpdateTable(
                    cursor.getString(cursor.getColumnIndex(UpdateTable.COLUMN_NORUAS)),
                    cursor.getString(cursor.getColumnIndex(UpdateTable.COLUMN_WAKTU))
            );

        }

        cursor.close();

        database.close();
        close();

        return updateTable;
    }





    public void setTable(String ruas, String waktu){
        UpdateTable updateTable = getTable(ruas);

        if(updateTable!=null){
            updateTable.setWaktu(waktu);
            UpdateTableku(updateTable);
        }else{
            UpdateTable updateTable1= new UpdateTable(ruas, waktu);

            insertDataTable(updateTable1);
        }
    }

    public void clear() {

        String selectQuery = "delete FROM tableupdate ";
        database = dbHelper.getWritableDatabase();
        database.execSQL(selectQuery);
        database.close();
        close();

    }

}
