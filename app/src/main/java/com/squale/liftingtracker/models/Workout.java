package com.squale.liftingtracker.models;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squale.liftingtracker.dao.WorkoutDAO;

import java.util.ArrayList;

public class Workout {

    private ArrayList<Exercise> arrExercise = new ArrayList<>();
    private static final String TAG = "Workout";
    private long Id;
    private String date;
    private LinearLayout mWorkoutLayout;
    private Context context;
    private Button btnEditWorkout;


    public Workout() {
    }

    public Workout(long Id, String mDate) {
        this.Id = Id;
        this.date = mDate;
    }

    public void setUp(String date, Button button, LinearLayout workoutLayout, Context context) {
        this.date = date;
        this.mWorkoutLayout = workoutLayout;
        this.context = context;
        button.setOnClickListener(new addExerciseClick());
    }

    public void sendCurrentToDatabase() {
        WorkoutDAO workoutDAO = new WorkoutDAO(context);
        if (!TextUtils.isEmpty(this.date)) {
            Log.d(TAG, "workout added for date: " + this.date);
            workoutDAO.createWorkout(this.date);
            this.Id = workoutDAO.getItemID(this.date);
            Log.d(TAG, "workout id updated to be " + this.Id);
            for (int i = 0; i < arrExercise.size(); i++) {
                Exercise exercise = arrExercise.get(i);
                exercise.sendCurrentToDatabase();
            }
        } else {
            Log.d(TAG, "date field is empty???");
        }
    }

    public void makeNonEditable(final LinearLayout llWorkout, final Button bFinish,
                                final LinearLayout llAddExercise, final Button bAddExercise) {
        this.btnEditWorkout = new Button(context);
        btnEditWorkout.setText("Edit Workout");

        for (int i = arrExercise.size() - 1; i >= 0; i--) {
            Exercise exercise = arrExercise.get(i);
            exercise.makeNonEditable();
            if (exercise.getSetsCount() == 0 && TextUtils.isEmpty(exercise.getName())) {
                arrExercise.remove(exercise);
            }
        }

        if (arrExercise.isEmpty()) {
            Toast.makeText(context, "Need at least one Exercise!", Toast.LENGTH_LONG).show();
        } else {
            llWorkout.removeView(bFinish);
            llAddExercise.removeView(bAddExercise);
            llAddExercise.addView(btnEditWorkout);
            btnEditWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (Exercise exercise : arrExercise) {
                        exercise.makeEditable();
                    }
                    llWorkout.addView(bFinish);
                    llAddExercise.addView(bAddExercise);
                    llAddExercise.removeView(btnEditWorkout);
                }
            });
        }
    }

    class addExerciseClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Exercise exercise = new Exercise();
            exercise.setUp(arrExercise.size() + 1, mWorkoutLayout, context, view);
            arrExercise.add(exercise);
        }
    }

    public String getWorkoutInfoString() {
        return getDate() + "\n" + getExercisesInfoString();
    }

    public String getExercisesInfoString() {
        Exercise object = arrExercise.get(0);
        StringBuilder sb = new StringBuilder(object.getExerciseInfoString());
        for (int i = 1; i < arrExercise.size(); i++) {
            object = arrExercise.get(i);
            sb.append("\n").append(object.getExerciseInfoString());
        }
        return sb.toString();
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String mDate) {
        this.date = mDate;
    }

    public long getId() {
        return Id;
    }

    public void setId(long mId) {
        this.Id = mId;
    }

    public Button getBtnEditWorkout() {
        return btnEditWorkout;
    }
}
