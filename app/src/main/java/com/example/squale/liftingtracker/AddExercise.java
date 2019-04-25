package com.example.squale.liftingtracker;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.Gravity.END;
import static android.view.Gravity.FILL;

class AddExercise {
    private LinearLayout linearLayoutAddedTo;
    private LinearLayout llAddExercise;
    private LinearLayout llTitleBar;
    private EditText etExerciseName;
    private String exercise;
    private LinearLayout llSetButton;
    private ImageButton btnAddSet;
    private LinearLayout llSetsList;
    private TextView tvExerciseCount;
    private Context context;
    private View view;
    private int count = 0;
    private int setsCount = 0;

    AddExercise(int count, LinearLayout linearLayoutAddedTo, Context context, View view) {
        this.context = context;
        this.count = count;
        this.llAddExercise = new LinearLayout(context);
        this.llTitleBar = new LinearLayout(context);
        this.linearLayoutAddedTo = linearLayoutAddedTo;
        this.llSetButton = new LinearLayout(context);
        this.llSetsList = new LinearLayout(context);
        this.etExerciseName = new EditText(context);
        this.tvExerciseCount = new TextView(context);
        this.btnAddSet = new ImageButton(context);


        llAddExercise.setOrientation(LinearLayout.VERTICAL);
        llAddExercise.setGravity(View.FOCUS_LEFT);
        llAddExercise.setPadding(5, 25, 5, 25);
        llAddExercise.setElevation(500* view.getContext().getResources().getDisplayMetrics().density);
        llAddExercise.setId(View.generateViewId());


        llTitleBar.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llTitleBar.setOrientation(LinearLayout.HORIZONTAL);
        llTitleBar.setGravity(FILL);
        llTitleBar.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorPrimary));
        llTitleBar.setId(View.generateViewId());


        llSetButton.setOrientation(LinearLayout.HORIZONTAL);
        llSetButton.setGravity(END);
        llSetButton.setBackgroundColor(Color.WHITE);
        llSetButton.setId(View.generateViewId());


        llSetsList.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        llSetsList.setOrientation(LinearLayout.VERTICAL);
        llSetsList.setGravity(FILL);
        llSetsList.setBackgroundColor(Color.WHITE);
        llSetsList.setId(View.generateViewId());


        etExerciseName.setHint("Exercise Name");
        etExerciseName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        etExerciseName.setTextColor(Color.BLACK);
        etExerciseName.setPadding(10, 5, 5, 5);
        etExerciseName.setId(View.generateViewId());


        tvExerciseCount.setText(String.format("%d", this.count));
        tvExerciseCount.setTextColor(Color.BLACK);
        tvExerciseCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tvExerciseCount.setPadding(5, 5, 10, 5);
        tvExerciseCount.setId(View.generateViewId());


        btnAddSet.setImageResource(R.drawable.plus);
        btnAddSet.setMaxHeight(30);
        btnAddSet.setLayoutParams(new LinearLayout.LayoutParams(400, 200));
        btnAddSet.setId(View.generateViewId());

        setsCount++;
        AddSet addSet = new AddSet(setsCount, llSetsList,context,btnAddSet);

        llAddExercise.addView(llTitleBar);
        llAddExercise.addView(llSetsList);
        llSetButton.addView(btnAddSet);
        llAddExercise.addView(llSetButton);

        llTitleBar.addView(tvExerciseCount);
        llTitleBar.addView(etExerciseName);

        btnAddSet.setOnClickListener(new addSetClick());


        linearLayoutAddedTo.addView(llAddExercise);
    }

    class addSetClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            setsCount++;
            AddSet addSet = new AddSet(setsCount, llSetsList,context,btnAddSet);
        }
    }

    public void setCount(int count){
        this.count = count;
    }

    public int getCount(){
        return this.count;
    }

}