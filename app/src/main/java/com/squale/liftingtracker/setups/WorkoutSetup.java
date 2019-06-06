package com.squale.liftingtracker.setups;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squale.liftingtracker.dao.WorkoutDAO;
import com.squale.liftingtracker.models.Exercise;
import com.squale.liftingtracker.models.Workout;

import java.util.ArrayList;


public class WorkoutSetup {

    private static final String TAG = "WorkoutSetup";

    private ArrayList<ExerciseSetup> exerciseSetupArrayList = new ArrayList<>();
    private LinearLayout mWorkoutLayout;
    private Button mBtnEditWorkout;
    private Context mContext;

    public void make(LinearLayout workoutLayout, Button btnFinish, Context context){
        this.mWorkoutLayout = workoutLayout;
        this.mContext = context;

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExerciseSetup exerciseSetup = new ExerciseSetup(
                        exerciseSetupArrayList.size() + 1, mWorkoutLayout, mContext);
                exerciseSetupArrayList.add(exerciseSetup);
            }
        });
    }

    public void sendCurrentToDatabase(String date) {
        WorkoutDAO workoutDAO = new WorkoutDAO(mContext);
        Log.d(TAG, "workout added for date: " + date);
        workoutDAO.createWorkout(date);
        long workoutId = workoutDAO.getItemID(date);
        Workout workout = new Workout(workoutId, date);
        Log.d(TAG, "workout id updated to be " + workoutId);
        for (int i = 0; i < exerciseSetupArrayList.size(); i++) {
            ExerciseSetup exerciseSetup = exerciseSetupArrayList.get(i);
            exerciseSetup.sendCurrentToDatabase(workout);
        }
        Log.d(TAG, "date field is empty???");
    }

    public void makeNonEditable(final LinearLayout llWorkout, final Button bFinish,
                                final LinearLayout llAddExercise, final Button bAddExercise) {
        this.mBtnEditWorkout = new Button(mContext);
        mBtnEditWorkout.setText("Edit Workout");

        for (int i = exerciseSetupArrayList.size() - 1; i >= 0; i--) {
            ExerciseSetup exerciseSetup = exerciseSetupArrayList.get(i);
            exerciseSetup.makeNonEditable();
            if (exerciseSetup.getCount() == 0 && TextUtils.isEmpty(exerciseSetup.getName())) {
                exerciseSetupArrayList.remove(exerciseSetup);
            }
        }

        if (exerciseSetupArrayList.isEmpty())
            Toast.makeText(mContext, "Need at least one Exercise!", Toast.LENGTH_LONG).show();
        else {
            llWorkout.removeView(bFinish);
            llAddExercise.removeView(bAddExercise);
            llAddExercise.addView(mBtnEditWorkout);
            mBtnEditWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (ExerciseSetup exerciseSetup : exerciseSetupArrayList) {
                        exerciseSetup.makeEditable();
                    }
                    llWorkout.addView(bFinish);
                    llAddExercise.addView(bAddExercise);
                    llAddExercise.removeView(mBtnEditWorkout);
                }
            });
        }
    }

    public void resetSetup(){
        ExerciseSetup exerciseSetup;
        for(int i = exerciseSetupArrayList.size() - 1; i>=0; i--){
            exerciseSetup = exerciseSetupArrayList.get(i);
            exerciseSetup.delete();
        }
    }
}
