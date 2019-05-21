package com.example.squale.liftingtracker.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.squale.liftingtracker.objects.Day;
import com.example.squale.liftingtracker.objects.Exercise;

public class ExerciseDAO {
    public static final String TAG = "ExerciseDAO";

    private Context context;

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelperDay databaseHelper;
    private String[] mAllColumns = { DatabaseHelperDay.COL_EXERCISE_ID,
            DatabaseHelperDay.COL_EXERCISE_NAME, DatabaseHelperDay.COL_EXERCISE_DAY_ID };

    public ExerciseDAO(Context context) {
        databaseHelper = new DatabaseHelperDay(context);
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

    public Exercise createExercise(String exerciseName, long dayId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperDay.COL_EXERCISE_NAME, exerciseName);
        values.put(DatabaseHelperDay.COL_EXERCISE_DAY_ID, dayId);
        long insertId = database
                .insert(DatabaseHelperDay.TABLE_EXERCISE, null, values);
        Cursor cursor = database.query(DatabaseHelperDay.TABLE_EXERCISE, mAllColumns,
                DatabaseHelperDay.COL_EXERCISE_DAY_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Exercise newExercise = cursorToExercise(cursor);
        cursor.close();
        return newExercise;
    }

    public void deleteExercise(Exercise exercise) {
        long id = exercise.getId();
        System.out.println("the deleted employee has the id: " + id);
        database.delete(DatabaseHelperDay.TABLE_EXERCISE, DatabaseHelperDay.COL_EXERCISE_ID
                + " = " + id, null);
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> listExercises = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelperDay.TABLE_EXERCISE, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = cursorToExercise(cursor);
            listExercises.add(exercise);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listExercises;
    }

    public List<Exercise> getExercisesOfDay(long dayId) {
        List<Exercise> listExercise = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelperDay.TABLE_EXERCISE, mAllColumns,
                DatabaseHelperDay.COL_DAY_ID + " = ?",
                new String[] { String.valueOf(dayId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = cursorToExercise(cursor);
            listExercise.add(exercise);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listExercise;
    }

    public Exercise getExerciseById(long id) {
        Cursor cursor = database.query(DatabaseHelperDay.TABLE_DAY, mAllColumns,
                DatabaseHelperDay.COL_DAY_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToExercise(cursor);
    }

    private Exercise cursorToExercise(Cursor cursor) {
        Exercise exercise = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setName(cursor.getString(1));

        // get The company by id
        long dayId = cursor.getLong(7);
        DayDAO dao = new DayDAO(context);
        Day day = dao.getDayById(dayId);
        if (day != null)
            exercise.setDay(day);

        return exercise;
    }
}
