package com.example.roadmanagement.kaltara.databaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataDownloadRuas;
import com.example.roadmanagement.kaltara.Interface.DataDrainase;
import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataOutlet;
import com.example.roadmanagement.kaltara.Interface.DataRuas;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.DataSpDalam;
import com.example.roadmanagement.kaltara.Interface.DataSpLuar;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.Interface.UpdateTable;

public class DbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "kaltara.db";
    private static final int DATABASE_VERSION = 2;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DataLahan.CREATE_TABLE);
        db.execSQL(DataSegmen.CREATE_TABLE);
        db.execSQL(DataSaluran.CREATE_TABLE);
        db.execSQL(DataBahu.CREATE_TABLE);
        db.execSQL(DataLane.CREATE_TABLE_LEFT);
        db.execSQL(DataMedian.CREATE_TABLE);
        db.execSQL(DataTemporari.CREATE_TABLE);
        db.execSQL(SingleSegment.CREATE_TABLE);
        db.execSQL(DataRuas.CREATE_TABLE);
        db.execSQL(DataDownloadRuas.CREATE_TABLE);
        db.execSQL(UpdateTable.CREATE_TABLE);

        db.execSQL(DataCrossDrain.CREATE_TABLE);
        db.execSQL(DataInletMedian.CREATE_TABLE);
        db.execSQL(DataInletTrotoar.CREATE_TABLE);
        db.execSQL(DataDrainase.CREATE_TABLE);
        db.execSQL(DataOutlet.CREATE_TABLE);

        db.execSQL(DataSpDalam.CREATE_TABLE);
        db.execSQL(DataSpLuar.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + DataLahan.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataSegmen.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataSaluran.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBahu.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataLane.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataMedian.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataTemporari.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SingleSegment.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataRuas.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataDownloadRuas.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UpdateTable.TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS " + DataCrossDrain.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataInletMedian.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataInletTrotoar.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataDrainase.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataOutlet.TABLE_NAME);

        onCreate(db);*/
        db.execSQL("DROP TABLE IF EXISTS " + DataSpLuar.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataSpDalam.TABLE_NAME);

        db.execSQL(DataSpDalam.CREATE_TABLE);
        db.execSQL(DataSpLuar.CREATE_TABLE);

    }




/*
    public List<ContactsContract.CommonDataKinds.Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }*/
}
