package com.example.squale.liftingtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperAddExercise extends SQLiteOpenHelper {
    private static final String  TAG = "DatabaseHelperAddExercise";

    private static final String TABLE_NAME = "exercise_table";
    private static final String COL1 = "exercise_ID";
    private static final String COL2 = "day_ID";
    private static final String COL3 = "exercise_name";


    public DatabaseHelperAddExercise(Context context){
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
