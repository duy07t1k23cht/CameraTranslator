package com.example.cameratranslator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Created by Duy M. Nguyen on 6/3/2020.
 */
public class OldAppDatabase extends SQLiteOpenHelper {

    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "app.db";

    public OldAppDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create table history
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS history (" +
                "imagePath TEXT NOT NULL," +
                "date LONG NOT NULL," +
                "boundingPoly TEXT NOT NULL)");

        // Create table flashcard
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS flashcard (" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "image BLOB," +
                "word TEXT NOT NULL," +
                "status INTEGER NOT NULL," +
                "audio TEXT)");

        // Create table flashcard set
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS fc_set (" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "name TEXT NOT NULL," +
                "date LONG NOT NULL)");

        // Create table set details
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS set_details (" +
                "flashID INTEGER," +
                "setID INTEGER," +
                "PRIMARY KEY (flashID, setID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
