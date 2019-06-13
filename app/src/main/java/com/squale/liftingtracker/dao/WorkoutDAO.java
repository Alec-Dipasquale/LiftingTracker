package com.squale.liftingtracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.squale.liftingtracker.models.Workout;
import com.squale.liftingtracker.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDAO {

    public static final String TAG = "WorkoutDAO";


    //Database fields
    private SQLiteDatabase database;
    private DatabaseHelperWorkout databaseHelperWorkout;
    private Context context;
    private String[] allColumns = {
            DatabaseHelperWorkout.COL_WORKOUT_ID, DatabaseHelperWorkout.COL_WORKOUT_DATE};

    public WorkoutDAO(Context context) {
        this.context = context;
        databaseHelperWorkout = new DatabaseHelperWorkout(context);
    }

    public void open() throws SQLException {
        this.database = this.databaseHelperWorkout.getWritableDatabase();
    }

    public void close() {
        databaseHelperWorkout.close();
    }

    public long createWorkout(Workout workout){

        long id = -1;
        DatabaseHelperWorkout databaseHelperWorkout = DatabaseHelperWorkout.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelperWorkout.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperWorkout.COL_WORKOUT_DATE, workout.getDate());

        try {
            id = sqLiteDatabase.insertOrThrow(DatabaseHelperWorkout.TABLE_WORKOUT, null, contentValues);
        } catch (SQLiteException e){
            Log.e(TAG, "Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public void deleteWorkout(Workout workout) {
        long id = workout.getId();
        // delete all exercises of this workout
        ExerciseDAO exerciseDAO = new ExerciseDAO(context);
        List<Exercise> listExercises = exerciseDAO.getExercisesOfWorkout(id);
        if (listExercises != null && !listExercises.isEmpty()) {
            for (Exercise e : listExercises) {
                exerciseDAO.deleteExercise(e);
            }
        }

        System.out.println("the deleted workout has the id: " + id);
        database.delete(DatabaseHelperWorkout.TABLE_WORKOUT, DatabaseHelperWorkout.COL_WORKOUT_ID
                + " = " + id, null);
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> listWorkouts = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_WORKOUT, allColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Workout workout = cursorToWorkout(cursor);
                listWorkouts.add(workout);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listWorkouts;
    }

    public Workout getWorkoutById(long id) {
        DatabaseHelperWorkout databaseHelperWorkout = DatabaseHelperWorkout.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelperWorkout.getReadableDatabase();

        Workout workout = null;

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(DatabaseHelperWorkout.TABLE_WORKOUT, null,
                    DatabaseHelperWorkout.COL_WORKOUT_ID + " = ? ", new String[] {String.valueOf(id)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                String date = cursor.getString(cursor.getColumnIndex(DatabaseHelperWorkout.COL_WORKOUT_DATE));

                workout = new Workout(id, date);
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return workout;

//        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_WORKOUT, allColumns,
//                DatabaseHelperWorkout.COL_WORKOUT_ID + " = ?",
//                new String[]{String.valueOf(id)}, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursorToWorkout(cursor);
    }


    public long getItemID(String date) {
        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_WORKOUT, allColumns,
                DatabaseHelperWorkout.COL_WORKOUT_DATE + " = ?",
                new String[]{String.valueOf(date)}, null, null, null);
        long id = cursor.getColumnIndex(DatabaseHelperWorkout.COL_WORKOUT_ID);
        cursor.close();
        return id;
    }

    protected Workout cursorToWorkout(Cursor cursor) {
        Workout workout = new Workout();
        workout.setId(cursor.getLong(0));
        workout.setDate(cursor.getString(1));
        return workout;
    }

}
