package com.example.squale.liftingtracker.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.squale.liftingtracker.objects.Workout;
import com.example.squale.liftingtracker.objects.Exercise;

public class ExerciseDAO {
    public static final String TAG = "ExerciseDAO";

    private Context context;

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelperWorkout databaseHelper;
    private String[] mAllColumns = { DatabaseHelperWorkout.COL_EXERCISE_ID,
            DatabaseHelperWorkout.COL_EXERCISE_NAME, DatabaseHelperWorkout.COL_EXERCISE_WORKOUT_ID };

    public ExerciseDAO(Context context) {
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

    public Exercise createExercise(String exerciseName, long workoutId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperWorkout.COL_EXERCISE_NAME, exerciseName);
        values.put(DatabaseHelperWorkout.COL_EXERCISE_WORKOUT_ID, workoutId);
        long insertId = database
                .insert(DatabaseHelperWorkout.TABLE_EXERCISE, null, values);
        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_EXERCISE, mAllColumns,
                DatabaseHelperWorkout.COL_EXERCISE_WORKOUT_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Exercise newExercise = cursorToExercise(cursor);
        cursor.close();
        return newExercise;
    }

    public void deleteExercise(Exercise exercise) {
        long id = exercise.getId();
        System.out.println("the deleted employee has the id: " + id);
        database.delete(DatabaseHelperWorkout.TABLE_EXERCISE, DatabaseHelperWorkout.COL_EXERCISE_ID
                + " = " + id, null);
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> listExercises = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_EXERCISE, mAllColumns,
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

    public List<Exercise> getExercisesOfWorkout(long workoutId) {
        List<Exercise> listExercise = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_EXERCISE, mAllColumns,
                DatabaseHelperWorkout.COL_WORKOUT_ID + " = ?",
                new String[] { String.valueOf(workoutId) }, null, null, null);

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
        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_WORKOUT, mAllColumns,
                DatabaseHelperWorkout.COL_WORKOUT_ID + " = ?",
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
        long workoutId = cursor.getLong(7);
        WorkoutDAO dao = new WorkoutDAO(context);
        Workout workout = dao.getWorkoutById(workoutId);
        if (workout != null)
            exercise.setWorkout(workout);

        return exercise;
    }
}
