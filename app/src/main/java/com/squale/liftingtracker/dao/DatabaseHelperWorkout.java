package com.squale.liftingtracker.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperWorkout extends SQLiteOpenHelper {

    public static final String TAG = "DataBaseHelperWorkout";

    private static DatabaseHelperWorkout databaseHelperWorkout;


    //columns of the workout table
    public static final String TABLE_WORKOUT = "workout";
    public static final String COL_WORKOUT_ID = "_id";        //PK
    public static final String COL_WORKOUT_DATE = "date";

    //columns of the exercise table
    public static final String TABLE_EXERCISE = "exercises";
    public static final String COL_EXERCISE_ID = "_id";
    public static final String COL_EXERCISE_NAME = "exercise_name";
    public static final String COL_EXERCISE_WORKOUT_ID = "workout_id";

    //columns of the sets table
    public static final String TABLE_SET = "set_table";
    public static final String COL_SET_ID = "_id";
    public static final String COL_SET_WEIGHT = "weight";
    public static final String COL_SET_REPS = "repetitions";
    public static final String COL_SET_EXERCISE_ID = "exercise_id";

    private static final String DATABASE_NAME = "workout-db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_SETS = "CREATE TABLE " + TABLE_SET
            + "(" + COL_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_SET_WEIGHT + " TEXT NOT NULL, "
            + COL_SET_REPS + " TEXT NOT NULL, "
            + COL_SET_EXERCISE_ID + " INTEGER NOT NULL "
            + ");";

    private static final String SQL_CREATE_TABLE_EXERCISES = "CREATE TABLE " + TABLE_EXERCISE
            + "(" + COL_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_EXERCISE_NAME + " TEXT NOT NULL, "
            + COL_EXERCISE_WORKOUT_ID + " INTEGER NOT NULL "
            + ");";

    private static final String SQL_CREATE_TABLE_WORKOUT = "CREATE TABLE " + TABLE_WORKOUT
            + "(" + COL_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_WORKOUT_DATE + " TEXT NOT NULL"
            + ");";

    public DatabaseHelperWorkout(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DatabaseHelperWorkout");
    }

    public static DatabaseHelperWorkout getInstance(Context context) {
        if(databaseHelperWorkout==null){
            synchronized (DatabaseHelperWorkout.class) {
                if(databaseHelperWorkout==null)
                    databaseHelperWorkout = new DatabaseHelperWorkout(context);
            }
        }
        return databaseHelperWorkout;
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(SQL_CREATE_TABLE_WORKOUT);
        database.execSQL(SQL_CREATE_TABLE_EXERCISES);
        database.execSQL(SQL_CREATE_TABLE_SETS);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SET);

        onCreate(database);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }
}