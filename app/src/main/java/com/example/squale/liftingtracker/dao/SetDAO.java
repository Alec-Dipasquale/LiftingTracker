package com.example.squale.liftingtracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.squale.liftingtracker.objects.Day;
import com.example.squale.liftingtracker.objects.Exercise;
import com.example.squale.liftingtracker.objects.Set;

import java.util.ArrayList;
import java.util.List;

public class SetDAO {
    public static final String TAG = "setDAO";

    private Context context;

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelperDay databaseHelper;
    private String[] mAllColumns = { DatabaseHelperDay.COL_SET_ID,
            DatabaseHelperDay.COL_SET_WEIGHT, DatabaseHelperDay.COL_SET_REPS,
            DatabaseHelperDay.COL_SET_EXERCISE_ID };

    public SetDAO(Context context) {
        databaseHelper = new DatabaseHelperDay(context);
        this.context = context;
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public Set createSet(String setWeight, String setReps, long exerciseId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperDay.COL_SET_WEIGHT, setWeight);
        values.put(DatabaseHelperDay.COL_SET_REPS, setReps);
        values.put(DatabaseHelperDay.COL_SET_EXERCISE_ID, exerciseId);
        long insertId = database
                .insert(DatabaseHelperDay.TABLE_SET, null, values);
        Cursor cursor = database.query(DatabaseHelperDay.TABLE_SET, mAllColumns,
                DatabaseHelperDay.COL_SET_EXERCISE_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Set newSet = cursorToSet(cursor);
        cursor.close();
        return newSet;
    }

    public void deleteset(Set set) {
        long id = set.getId();
        System.out.println("the deleted employee has the id: " + id);
        database.delete(DatabaseHelperDay.TABLE_SET, DatabaseHelperDay.COL_SET_ID
                + " = " + id, null);
    }

    public List<Set> getAllsets() {
        List<Set> listSets = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelperDay.TABLE_SET, mAllColumns,
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

    public List<Set> getsetsOfDay(long dayId) {
        List<Set> listSet = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelperDay.TABLE_SET, mAllColumns,
                DatabaseHelperDay.COL_DAY_ID + " = ?",
                new String[] { String.valueOf(dayId) }, null, null, null);

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
        long companyId = cursor.getLong(7);
        ExerciseDAO dao = new ExerciseDAO(context);
        Exercise exercise = dao.getExerciseById(companyId);
        if (exercise != null)
            set.setExercise(exercise);

        return set;
    }
}
