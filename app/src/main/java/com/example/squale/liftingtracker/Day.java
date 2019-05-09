package com.example.squale.liftingtracker;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Day {

    private ArrayList<AddExercise> arrExercise = new ArrayList<AddExercise>();
    private static final String TAG = "Day";
    private String date;
    private LinearLayout llDay;
    private Button button;
    private Context context;


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
            AddExercise addExercise = new AddExercise(arrExercise.size() + 1, llDay, context, view);
            arrExercise.add(addExercise);
        }
    }

    public String getWorkoutInfoString(){
        return getDate()+ "\n" + getExercisesInfoString();
    }



    public String getExercisesInfoString(){
        AddExercise object = arrExercise.get(0);
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



}
