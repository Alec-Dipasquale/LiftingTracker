package com.squale.liftingtracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.squale.liftingtracker.models.Exercise;
import com.squale.liftingtracker.models.Set;

import java.util.ArrayList;
import java.util.List;

public class SetDAO {
    public static final String TAG = "setDAO";

    private Context context;

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelperWorkout databaseHelper;
    private String[] mAllColumns = {DatabaseHelperWorkout.COL_SET_ID,
            DatabaseHelperWorkout.COL_SET_WEIGHT, DatabaseHelperWorkout.COL_SET_REPS,
            DatabaseHelperWorkout.COL_SET_EXERCISE_ID};

    public SetDAO(Context context) {
        databaseHelper = new DatabaseHelperWorkout(context);
        this.context = context;
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public long createSet(Set set) {

        long id = -1;
        DatabaseHelperWorkout databaseHelperWorkout = DatabaseHelperWorkout.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelperWorkout.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperWorkout.COL_SET_WEIGHT, set.getWeight());
        contentValues.put(DatabaseHelperWorkout.COL_SET_REPS, set.getReps());
        contentValues.put(DatabaseHelperWorkout.COL_SET_EXERCISE_ID, set.getExercise().getId());

        try {
            id = sqLiteDatabase.insertOrThrow(DatabaseHelperWorkout.TABLE_SET,  null, contentValues);
        } catch (SQLiteException e){
            Log.e(TAG, "Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public void deleteSet(Set set) {
        long id = set.getId();
        System.out.println("the deleted employee has the id: " + id);
        database.delete(DatabaseHelperWorkout.TABLE_SET, DatabaseHelperWorkout.COL_SET_ID
                + " = " + id, null);
    }

    public List<Set> getAllSets() {
        List<Set> listSets = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_SET, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Set set = cursorToSet(cursor);
            listSets.add(set);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listSets;
    }

    public List<Set> getSetsOfExercise(long exerciseId) {
        List<Set> listSet = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_SET, mAllColumns,
                DatabaseHelperWorkout.COL_SET_ID + " = ?",
                new String[]{String.valueOf(exerciseId)}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Set set = cursorToSet(cursor);
            listSet.add(set);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listSet;
    }

    private Set cursorToSet(Cursor cursor) {
        Set set = new Set();
        set.setId(cursor.getLong(0));
        set.setWeight(cursor.getString(1));
        set.setReps(cursor.getString(2));

        // get The company by id
        long exerciseId = cursor.getLong(3);
        ExerciseDAO dao = new ExerciseDAO(context);
        Exercise exercise = dao.getExerciseById(exerciseId);
        if (exercise != null)
            set.setExercise(exercise);

        return set;
    }
}
