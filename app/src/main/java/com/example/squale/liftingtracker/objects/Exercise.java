package com.example.squale.liftingtracker.objects;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.squale.liftingtracker.R;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

import static android.view.Gravity.END;
import static android.view.Gravity.FILL;

public class Exercise implements Serializable {

    public static final String TAG = "Exercise";

    private long id;
    private String name;
    private Workout workout;

    private LinearLayout llAddExercise;
    private LinearLayout llTitleBar;
    private EditText etExerciseName;
    private LinearLayout llSetButton;
    private ImageButton btnAddSet;
    private LinearLayout llSetsList;
    private TextView tvExerciseCount;
    private Context context;
    private LinearLayout linearLayoutAddedTo;
    private ArrayList<Set>  arrSet = new ArrayList<>();



    public Exercise(){}

    public Exercise(long id, String name, Workout workout) {
        this.id = id;
        this.name = name;
        this.workout = workout;
    }

    public void setUp(int count , LinearLayout linearLayoutAddedTo, Context context, View view) {
        this.context = context;
        this.llAddExercise = new LinearLayout(context);
        this.llTitleBar = new LinearLayout(context);
        this.llSetButton = new LinearLayout(context);
        this.llSetsList = new LinearLayout(context);
        this.etExerciseName = new EditText(context);
        this.tvExerciseCount = new TextView(context);
        this.btnAddSet = new ImageButton(context);
        this.linearLayoutAddedTo = linearLayoutAddedTo;


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


        tvExerciseCount.setText(String.format("%d", count));
        tvExerciseCount.setTextColor(Color.BLACK);
        tvExerciseCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tvExerciseCount.setPadding(5, 5, 10, 5);
        tvExerciseCount.setId(View.generateViewId());


        btnAddSet.setImageResource(R.drawable.plus);
        btnAddSet.setMaxHeight(30);
        btnAddSet.setLayoutParams(new LinearLayout.LayoutParams(400, 200));
        btnAddSet.setId(View.generateViewId());

        Set set = new Set();
        set.setUp(arrSet.size()+1, llSetsList,context,btnAddSet);
        arrSet.add(set);

        llAddExercise.addView(llTitleBar);
        llAddExercise.addView(llSetsList);
        llSetButton.addView(btnAddSet);
        llAddExercise.addView(llSetButton);

        llTitleBar.addView(tvExerciseCount);
        llTitleBar.addView(etExerciseName);

        btnAddSet.setOnClickListener(new addSetClick());


        linearLayoutAddedTo.addView(llAddExercise);
    }


    public void makeNonEditable(){
        for(Set set : arrSet){
            set.makeNonEditable();
            if(TextUtils.isEmpty(set.getReps())){
                arrSet.remove(set);
            }
        }
        if(arrSet.isEmpty() && TextUtils.isEmpty(etExerciseName.getText().toString())){
            linearLayoutAddedTo.removeView(llAddExercise);
        }
        etExerciseName.setEnabled(false);
        llSetButton.removeView(btnAddSet);
    }

    public void makeEditable() {
        for(Set set : arrSet){
            set.makeEditable();
        }
        etExerciseName.setEnabled(true);
        llSetButton.addView(btnAddSet);
    }

    class addSetClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Set set = new Set();
            set.setUp(arrSet.size()+1 , llSetsList,context,btnAddSet);
            arrSet.add(set);

        }
    }

    public String getExerciseInfoString(){
        return getExerciseCount() + "\t" + getExercise() + '\n' + getSetsInfoString();
    }

    private String getExerciseCount() {
        return "e" + tvExerciseCount.getText().toString();
    }

    public String getSetsInfoString(){
        Set object = this.arrSet.get(0);
        StringBuilder sb = new StringBuilder(object.getSetInfoString());
        for(int i = 1; i<this.arrSet.size(); i++){
            object = this.arrSet.get(i);
            sb.append("\t").append(object.getSetInfoString());
        }
        return sb.toString();
    }

    public String getExercise(){
        return this.etExerciseName.getText().toString();
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

    public int getSetsCount(){
        return arrSet.size();
    }
}
