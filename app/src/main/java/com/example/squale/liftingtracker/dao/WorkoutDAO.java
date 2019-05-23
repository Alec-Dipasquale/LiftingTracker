package com.example.squale.liftingtracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.squale.liftingtracker.objects.Workout;
import com.example.squale.liftingtracker.objects.Exercise;

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

    public WorkoutDAO(Context context){
        this.context = context;
        databaseHelperWorkout = new DatabaseHelperWorkout(context);
        //open database
        try{
            open();
        } catch (SQLException e){
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        database = databaseHelperWorkout.getWritableDatabase();
    }

    public void close(){
        databaseHelperWorkout.close();
    }

    public Workout createWorkout(String date){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperWorkout.COL_WORKOUT_DATE, date);
        long insertId =
                database.insert(DatabaseHelperWorkout.TABLE_WORKOUT, null, values);
        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_WORKOUT,  allColumns,
                DatabaseHelperWorkout.COL_WORKOUT_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Workout newWorkout = cursorToWorkout(cursor);
        cursor.close();
        return newWorkout;
    }

    public void deletWorkout(Workout workout){
        long id = workout.getId();
        // delete all exercises of this workout
        ExerciseDAO exerciseDAO = new ExerciseDAO(context);
        List<Exercise> listExercises = exerciseDAO.getExercisesOfWorkout(id);
        if(listExercises != null && !listExercises.isEmpty()){
            for(Exercise e : listExercises) {
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
        Cursor cursor = database.query(DatabaseHelperWorkout.TABLE_WORKOUT, allColumns,
                DatabaseHelperWorkout.COL_WORKOUT_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToWorkout(cursor);
    }

    protected Workout cursorToWorkout(Cursor cursor) {
        Workout workout = new Workout();
        workout.setId(cursor.getLong(0));
        workout.setDate(cursor.getString(1));
        return workout;
    }

}
