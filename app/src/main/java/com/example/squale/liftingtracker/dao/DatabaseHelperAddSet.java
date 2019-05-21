package com.example.squale.liftingtracker.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperAddSet extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelperAddExercise";


    private static final String TABLE_NAME = "set_table";
    private static final String COL1 = "set_ID";   //PK
    private static final String COL2 = "set_weight";
    private static final String COL3 = "set_reps";
    private static final String COL4 = "exercise_ID";


    public DatabaseHelperAddSet(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

