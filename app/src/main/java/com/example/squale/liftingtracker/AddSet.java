package com.example.squale.liftingtracker;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.Gravity.FILL_HORIZONTAL;

public class AddSet {
    private LinearLayout llSetAddedTo;
    private LinearLayout llAddSet;
    private TextView tvSetsCount;
    private  EditText etReps;
    private  EditText etWeight;
    private int count = 0;


    AddSet(int count, LinearLayout llAddSet, Context context, View view){
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



}
