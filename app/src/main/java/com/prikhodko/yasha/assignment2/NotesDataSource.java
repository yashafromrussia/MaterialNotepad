package com.prikhodko.yasha.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yasha on 14-11-07.
 */
public class NotesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_CONTENTS,
            MySQLiteHelper.COLUMN_TIME,
            MySQLiteHelper.COLUMN_DATE
    };

    public NotesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Note createNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CONTENTS, noteText);

        DateFormat date = new SimpleDateFormat("MMM dd, yyyy");
        DateFormat time = new SimpleDateFormat("HH:mm");

        values.put(MySQLiteHelper.COLUMN_DATE, date.format(new Date()));
        values.put(MySQLiteHelper.COLUMN_TIME, time.format(new Date()));

        long insertId = database.insert(MySQLiteHelper.TABLE_NOTES, null, values);
        return getNote(insertId);
    }

    public Note getNote(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTES, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }

    public Note updateNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CONTENTS, note.getText());
        values.put(MySQLiteHelper.COLUMN_DATE, note.getDate());
        values.put(MySQLiteHelper.COLUMN_TIME, note.getTime());

        database.update(MySQLiteHelper.TABLE_NOTES, values, MySQLiteHelper.COLUMN_ID + "=" + note.getId(), null);
        return note;
    }

    public void deleteNote(Note note) {
        long id = note.getId();
        System.out.println("Note deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_NOTES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return notes;
    }

    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setText(cursor.getString(1));
        note.setTime(cursor.getString(2));
        note.setDate(cursor.getString(3));
        return note;
    }

}
