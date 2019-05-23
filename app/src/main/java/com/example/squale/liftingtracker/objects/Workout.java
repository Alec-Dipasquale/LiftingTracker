package com.example.squale.liftingtracker.objects;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Workout {

    private ArrayList<Exercise> arrExercise = new ArrayList<>();
    private static final String TAG = "Workout";
    private long Id;
    private String date;
    private LinearLayout llWorkout;
    private Button button;
    private Context context;


    public Workout(){
    }

    public Workout(long id, String date) {
        Id = id;
        this.date = date;
    }

    public void setUp(String date, Button button, LinearLayout llWorkout, Context context){
        this.date = date;
        this.llWorkout = llWorkout;
        this.button = button;
        this.context = context;
        button.setOnClickListener(new addExerciseClick());
    }

    public void makeNonEditable(final LinearLayout llWorkout, final Button bFinish, final LinearLayout llAddExercise, final Button bAddExercise){
        final Button btnEditWorkout = new Button(context);
        btnEditWorkout.setText("Edit Workout");

        for(Exercise exercise : arrExercise){
            exercise.makeNonEditable();
            if(exercise.getSetsCount() == 0 && TextUtils.isEmpty(exercise.getName())){
                arrExercise.remove(exercise);
            }
        }
        llWorkout.removeView(bFinish);
        llAddExercise.removeView(bAddExercise);
        llWorkout.addView(btnEditWorkout);
        btnEditWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Exercise exercise : arrExercise){
                    exercise.makeEditable();
                }
                llWorkout.addView(bFinish);
                llAddExercise.addView(bAddExercise);
                llWorkout.removeView(btnEditWorkout);
            }
        });
    }

    class addExerciseClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Exercise exercise = new Exercise();
            exercise.setUp(arrExercise.size() + 1, llWorkout, context, view);
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
