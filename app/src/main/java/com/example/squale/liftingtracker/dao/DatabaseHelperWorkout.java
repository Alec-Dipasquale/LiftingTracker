package com.example.squale.liftingtracker.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperWorkout extends SQLiteOpenHelper {

    public static final String TAG = "DataBaseHelperWorkout";


    //columns of the workout table
    public static final String TABLE_WORKOUT = "workout";
    public static final String COL_WORKOUT_ID = "_id";        //PK
    public static final String COL_WORKOUT_DATE = "date";

    //columns of the exercise table
    public static final String TABLE_EXERCISE = "exercises";
    public static final String COL_EXERCISE_ID = COL_WORKOUT_ID;
    public static final String COL_EXERCISE_NAME = "exercise_name";
    public static final String COL_EXERCISE_WORKOUT_ID = "workout_id";

    //columns of the sets table
    public static final String TABLE_SET = "set_table";
    public static final String COL_SET_ID = COL_EXERCISE_ID;
    public static final String COL_SET_WEIGHT = "weight";
    public static final String COL_SET_REPS = "repetitions";
    public static final String COL_SET_EXERCISE_ID = "exercise_id";

    private static final String DATABASE_NAME = "workout.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_SETS = "CREATE TABLE " + TABLE_SET
            + "(" + COL_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_SET_WEIGHT + " TEXT NOT NULL, "
            + COL_SET_REPS + " TEXT NOT NULL, "
            + COL_SET_EXERCISE_ID + " INTEGER NOT NULL "
            + ");";

    private static final String SQL_CREATE_TABLE_EXERCISES = "CREATE TABLE " + TABLE_EXERCISE
            + "(" +  COL_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_EXERCISE_NAME + " TEXT NOT NULL, "
            + COL_EXERCISE_WORKOUT_ID + " INTEGER NOT NULL "
            + ");";

    private static final String SQL_CREATE_TABLE_WORKOUT = "CREATE TABLE " + TABLE_WORKOUT
            + "(" + COL_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_WORKOUT_DATE + " TEXT NOT NULL"
            + ");";

    public DatabaseHelperWorkout(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(SQL_CREATE_TABLE_WORKOUT);
        database.execSQL(SQL_CREATE_TABLE_EXERCISES);
        database.execSQL(SQL_CREATE_TABLE_SETS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion +" to "+ newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SET);

        onCreate(database);
    }

//
//    public DatabaseHelperWorkout(Context context){
//        super(context, TABLE_NAME, null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COL2 + " TEXT)";
//        db.execSQL(createTable);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//    }
//
//    public boolean addData(String item){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL2, item);
//
//        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
//
//        long result = db.insert(TABLE_NAME, null, contentValues);
//        if(result == -1){
//            return false;
//        }
//        else{
//            return true;
//        }
//    }
//
//
//
//    /**
//     * Returns all the data from database
//     * @return
//     */
//    public Cursor getData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT * FROM " + TABLE_NAME;
//        Cursor data = db.rawQuery(query, null);
//        return data;
//    }
//
//    /**
//     * Returns only the ID that matches the name passed in
//     * @param date
//     * @return
//     */
//    public Cursor getItemID(String date){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
//                " WHERE " + COL2 + " = '" + date + "'";
//        Cursor data = db.rawQuery(query, null);
//        return data;
//    }
//
//    /**
//     * Updates the name field
//     * @param newDate
//     * @param id
//     * @param oldDate
//     */
//    public void updateName(String newDate, int id, String oldDate){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
//                " = '" + newDate + "' WHERE " + COL1 + " = '" + id + "'" +
//                " AND " + COL2 + " = '" + oldDate + "'";
//        Log.d(TAG, "updateName: query: " + query);
//        Log.d(TAG, "updateName: Setting name to " + newDate);
//        db.execSQL(query);
//    }
//
//    /**
//     * Delete from database
//     * @param id
//     * @param date
//     */
//    public void deleteName(int id, String date){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL1 + " = '" + id + "'" +
//                " AND " + COL2 + " = '" + date + "'";
//        Log.d(TAG, "deleteName: query: " + query);
//        Log.d(TAG, "deleteName: Deleting " + date + " from database.");
//        db.execSQL(query);
//    }

}