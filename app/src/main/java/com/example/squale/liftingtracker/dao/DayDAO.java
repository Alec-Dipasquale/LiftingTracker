package com.example.squale.liftingtracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.squale.liftingtracker.objects.Day;
import com.example.squale.liftingtracker.objects.Exercise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DayDAO {

    public static final String TAG = "DayDAO";


    //Database fields
    private SQLiteDatabase database;
    private DatabaseHelperDay databaseHelperDay;
    private Context context;
    private String[] allColumns = {
            DatabaseHelperDay.COL_DAY_ID, DatabaseHelperDay.COL_DAY_DATE};

    public DayDAO(Context context){
        this.context = context;
        databaseHelperDay = new DatabaseHelperDay(context);
        //open database
        try{
            open();
        } catch (SQLException e){
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        database = databaseHelperDay.getWritableDatabase();
    }

    public void close(){
        databaseHelperDay.close();
    }

    public Day createDay(String date){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperDay.COL_DAY_DATE, date);
        long insertId =
                database.insert(DatabaseHelperDay.TABLE_DAY, null, values);
        Cursor cursor = database.query(DatabaseHelperDay.TABLE_DAY,  allColumns,
                DatabaseHelperDay.COL_DAY_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Day newDay = cursorToDay(cursor);
        cursor.close();
        return newDay;
    }

    public void deleteDay(Day day){
        long id = day.getId();
        // delete all exercises of this day
        ExerciseDAO exerciseDAO = new ExerciseDAO(context);
        List<Exercise> listExercises = exerciseDAO.getExercisesOfDay(id);
        if(listExercises != null && !listExercises.isEmpty()){
            for(Exercise e : listExercises) {
                exerciseDAO.deleteExercise(e);
            }
        }

        System.out.println("the deleted day has the id: " + id);
        database.delete(DatabaseHelperDay.TABLE_DAY, DatabaseHelperDay.COL_DAY_ID
                + " = " + id, null);
    }

    public List<Day> getAllDays() {
        List<Day> listDays = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelperDay.TABLE_DAY, allColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Day day = cursorToDay(cursor);
                listDays.add(day);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listDays;
    }

    public Day getDayById(long id) {
        Cursor cursor = database.query(DatabaseHelperDay.TABLE_DAY, allColumns,
                DatabaseHelperDay.COL_DAY_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToDay(cursor);
    }

    protected Day cursorToDay(Cursor cursor) {
        Day day = new Day();
        day.setId(cursor.getLong(0));
        day.setDate(cursor.getString(1));
        return day;
    }

}
