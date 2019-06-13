package com.squale.liftingtracker.models;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

import static android.view.Gravity.FILL_HORIZONTAL;

public class Set implements Serializable {
    public static final String TAG = "Set";

    private long id;
    private String weight;
    private String reps;
    private Exercise exercise;
    private int count = 0;

    public Set() {
    }

    public Set(long id, String weight, String reps, Exercise exercise) {
        this.id = id;
        this.weight = weight;
        this.reps = reps;
        this.exercise = exercise;
    }

    public String getCount() {
        return "s" + this.count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWeight() {
        return this.weight;

    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getReps() {
        return this.reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }


}
