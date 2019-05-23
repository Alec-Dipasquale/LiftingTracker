package com.example.squale.liftingtracker.objects;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

import static android.view.Gravity.FILL_HORIZONTAL;

public class Set implements Serializable {
    public static final String TAG = "Set";

    private long id;
    private String weight;
    private String reps;
    private Exercise exercise;

    private LinearLayout llSetAddedTo;
    private LinearLayout llAddSet;
    private TextView tvSetsCount;
    private  EditText etReps;
    private  EditText etWeight;
    private int count = 0;

    public Set(){}

    public Set(long id, String weight, String reps, Exercise exercise) {
        this.id = id;
        this.weight = weight;
        this.reps = reps;
        this.exercise = exercise;
    }

    public void setUp(int count, LinearLayout llAddSet, Context context, View view){
        this.llAddSet = llAddSet;
        llSetAddedTo = new LinearLayout(context);
        this.count = count;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        llSetAddedTo.setOrientation(LinearLayout.HORIZONTAL);
        llSetAddedTo.setGravity(FILL_HORIZONTAL);
        llSetAddedTo.setLayoutParams(layoutParams);

        this.tvSetsCount = new TextView(context); // this refers to the activity or the context.
        this.etReps = new EditText(context);
        this.etWeight  = new EditText(context);

        // set attributes as need
        etWeight.setLayoutParams(layoutParams);
        etWeight.setHint("Weight");
        etWeight.setPadding(50,20,50,20);

        etReps.setLayoutParams(layoutParams);
        etReps.setHint("Reps");
        etReps.setPadding(50,20,50,20);

        tvSetsCount.setText(String.format("%d", count));
        tvSetsCount.setTextColor(Color.BLACK);
        tvSetsCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tvSetsCount.setPadding(5,0, 20,0);

        llSetAddedTo.addView(tvSetsCount);
        llSetAddedTo.addView(etWeight);
        llSetAddedTo.addView(etReps);

        this.llAddSet.addView(llSetAddedTo);
    }

    public void makeNonEditable() {
        this.setRepsFromEditText();
        this.setWeightFromEditText();
        if(TextUtils.isEmpty(etReps.getText().toString()) || TextUtils.isEmpty(etWeight.getText().toString())){
            deleteSet();
        }
        etWeight.setEnabled(false);
        etReps.setEnabled(false);

    }

    public void deleteSet(){
        this.llAddSet.removeView(llSetAddedTo);
    }

    public void makeEditable(){
        etWeight.setEnabled(true);
        etReps.setEnabled(true);
    }

    public String getSetInfoString(){
        return getSetCount() + "\t" + getWeight() + "\t" + getReps();
    }

    public String getSetCount(){
        return  "s" + this.count;
    }

    public String getWeight(){
        return this.etWeight.getText().toString();

    }

    public String getReps(){
        return this.etReps.getText().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setWeightFromEditText(){
        this.weight = this.etWeight.toString();
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setRepsFromEditText(){
        this.reps = this.etReps.toString();
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
