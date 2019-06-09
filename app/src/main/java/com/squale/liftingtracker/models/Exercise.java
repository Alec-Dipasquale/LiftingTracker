package com.squale.liftingtracker.models;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squale.liftingtracker.R;
import com.squale.liftingtracker.dao.ExerciseDAO;
import com.squale.liftingtracker.dao.WorkoutDAO;

import java.io.Serializable;
import java.util.ArrayList;

import static android.view.Gravity.END;
import static android.view.Gravity.FILL;

public class Exercise implements Serializable {

    public static final String TAG = "Exercise";

    private long id;
    private String name;
    private Workout workout;

    public Exercise() {
    }

    public Exercise(long id, String name, Workout workout) {
        this.id = id;
        this.name = name;
        this.workout = workout;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String exerciseName) {
        this.name = exerciseName;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

}
