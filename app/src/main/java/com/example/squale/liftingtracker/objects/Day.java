package com.example.squale.liftingtracker.objects;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Day {

    private ArrayList<Exercise> arrExercise = new ArrayList<>();
    private static final String TAG = "Day";
    private long Id;
    private String date;
    private LinearLayout llDay;
    private Button button;
    private Context context;


    public Day(){
    }

    public Day(String date, Button button, LinearLayout llDay, Context context){
        this.date = date;
        this.llDay = llDay;
        this.button = button;
        this.context = context;
        button.setOnClickListener(new addExerciseClick());
    }

    class addExerciseClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Exercise exercise = new Exercise(arrExercise.size() + 1, llDay, context, view);
            arrExercise.add(exercise);
        }
    }

    public String getWorkoutInfoString(){
        return getDate()+ "\n" + getExercisesInfoString();
    }



    public String getExercisesInfoString(){
        Exercise object = arrExercise.get(0);
        StringBuilder sb = new StringBuilder(object.getExerciseInfoString());
        for(int i = 1; i<arrExercise.size(); i++){
            object = arrExercise.get(i);
            sb.append("\n").append(object.getExerciseInfoString());
        }
        return sb.toString();
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
}
