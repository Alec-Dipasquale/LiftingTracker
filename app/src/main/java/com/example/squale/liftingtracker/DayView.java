package com.example.squale.liftingtracker;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.text.MessagePattern;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.kd.dynamic.calendar.generator.ImageGenerator;


import java.util.ArrayList;
import java.util.Calendar;

import static android.view.Gravity.*;

public class DayView extends AppCompatActivity{

    //Local Variables
    private TextView mDateEditText;
    private Calendar mCurrentDate;
    private Bitmap mGeneratedDateIcon;
    private ImageGenerator mImageGenerator;
    private ImageView mDisplayGeneratedImage;

    private ArrayList<LinearLayout> horizontalSetsLayoutArrayList = new ArrayList<LinearLayout>();
    private ArrayList<Integer> setsCountArrayList = new ArrayList<Integer>();
    private ArrayList<ImageButton> btnNewSetArrayList = new ArrayList<ImageButton>();

    private static final String TAG = "DayView";

    private final int horizontalSetsLayoutID = 20000;
    private final int btnNewSetID = 10000;

    private int day;
    private int month;
    private int year;
    private String stringDate;
    private int countExercises = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        Button bAddExercise = (Button) findViewById(R.id.btnAddSet);
        AppOverlay appOverlay = new AppOverlay();

        bAddExercise.setOnClickListener(new addExerciseClick());

        appOverlay.optionsAction(DayView.this);

        createCalendar();

    }

    private void addExerciseClicked(View view){
        pickExercise();

        countExercises++;
        setsCountArrayList.add(0);


        LinearLayout linAddExercise = (LinearLayout) findViewById(R.id.linAddExercise);

        LinearLayout exerciseFullLayoutView = new LinearLayout(DayView.this);
        exerciseFullLayoutView.setOrientation(LinearLayout.VERTICAL);
        exerciseFullLayoutView.setGravity(View.FOCUS_LEFT);
        exerciseFullLayoutView.setPadding(5,25,5,25);
        exerciseFullLayoutView.setElevation(500* getApplicationContext().getResources().getDisplayMetrics().density);


        LinearLayout exerciseTitleBarLayout = new LinearLayout(DayView.this);
        exerciseTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        exerciseTitleBarLayout.setOrientation(LinearLayout.HORIZONTAL);
        exerciseTitleBarLayout.setGravity(FILL);
        exerciseTitleBarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        LinearLayout setsButtonLayout = new LinearLayout(DayView.this);
        setsButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
        setsButtonLayout.setGravity(END);
        setsButtonLayout.setBackgroundColor(Color.WHITE);


        LinearLayout horizontalSetsLayout = new LinearLayout(DayView.this);
        horizontalSetsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        horizontalSetsLayout.setOrientation(LinearLayout.VERTICAL);
        horizontalSetsLayout.setGravity(FILL);
        horizontalSetsLayout.setBackgroundColor(Color.WHITE);
        horizontalSetsLayout.setId(horizontalSetsLayoutID +countExercises);
        horizontalSetsLayoutArrayList.add(horizontalSetsLayout);


        final TextView tvExercise = new TextView(DayView.this);
        tvExercise.setText("(Exercise)");
        tvExercise.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tvExercise.setTextColor(Color.BLACK);
        tvExercise.setPadding(10,5,5,5);

        final TextView etExerciseCount = new TextView(DayView.this);
        etExerciseCount.setText(String.format("%d", countExercises));
        etExerciseCount.setTextColor(Color.BLACK);
        etExerciseCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        etExerciseCount.setPadding(5,5, 10,5);



        ImageButton btnNewSet = new ImageButton(DayView.this);
        btnNewSet.setId(btnNewSetID +countExercises);
        btnNewSet.setImageResource(R.drawable.plus);
        btnNewSet.setMaxHeight(30);
        btnNewSet.setLayoutParams(new LinearLayout.LayoutParams(400, 200));
        btnNewSetArrayList.add(btnNewSet);

        addSetClicked(btnNewSet);

        exerciseFullLayoutView.addView(exerciseTitleBarLayout);
        exerciseFullLayoutView.addView(horizontalSetsLayout);
        setsButtonLayout.addView(btnNewSet);
        exerciseFullLayoutView.addView(setsButtonLayout);

        exerciseTitleBarLayout.addView(etExerciseCount);
        exerciseTitleBarLayout.addView(tvExercise);

        for(int i = 0; i<btnNewSetArrayList.size(); i++) {
            ImageButton tempButton = btnNewSetArrayList.get(i);
            tempButton.setOnClickListener(new addSetClick());
        }
        linAddExercise.addView(exerciseFullLayoutView);

    }

    private void pickExercise(){
        
    }

    private void addSetClicked(View view){

        int id = view.getId() - btnNewSetID;
        int setCount = (setsCountArrayList.get(id-1)+1);

        setsCountArrayList.set(id-1, setCount);


        LinearLayout singleSetLayout = new LinearLayout(DayView.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        singleSetLayout.setOrientation(LinearLayout.HORIZONTAL);
        singleSetLayout.setGravity(FILL_HORIZONTAL);
        singleSetLayout.setLayoutParams(layoutParams);



        TextView tvSetsCount = new TextView(DayView.this); // this refers to the activity or the context.
        EditText etReps = new EditText(DayView.this);
        EditText etWeight  = new EditText(DayView.this);


        // set attributes as need
        etWeight.setLayoutParams(layoutParams);
        etWeight.setHint("Weight");
        etWeight.setPadding(50,20,50,20);

        etReps.setLayoutParams(layoutParams);
        etReps.setHint("Reps");
        etReps.setPadding(50,20,50,20);

        tvSetsCount.setText(String.format("%d", setsCountArrayList.get(id-1)));
        tvSetsCount.setTextColor(Color.BLACK);
        tvSetsCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tvSetsCount.setPadding(5,0, 20,0);

        singleSetLayout.addView(tvSetsCount);
        singleSetLayout.addView(etWeight);
        singleSetLayout.addView(etReps);

        for(int i =0; i<horizontalSetsLayoutArrayList.size(); i++){
            LinearLayout tempLinearLayout = horizontalSetsLayoutArrayList.get(i);
            if(tempLinearLayout.getId() - horizontalSetsLayoutID == id){
                tempLinearLayout.addView(singleSetLayout);
            }
        }

    }

    private void createCalendar(){
        // Create an object of ImageGenerator class in your activity
        // and pass the context as the parameter
        mImageGenerator = new ImageGenerator(this);
        mDateEditText = (TextView) findViewById(R.id.tvDateEntered);
        mDisplayGeneratedImage = (ImageView) findViewById(R.id.imageGen);

        // Set the icon size to the generated in dip.
        mImageGenerator.setIconSize(50,5);

        // Set the size of the date and month font in dip.
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);

        // Set the position of the date and month in dip.
        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(14);

        // Set the color of the font to be generated
        mImageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
        mImageGenerator.setMonthColor(Color.WHITE);

        //Set current Date
        mCurrentDate = Calendar.getInstance();
        year = mCurrentDate.get(Calendar.YEAR);
        month = mCurrentDate.get(Calendar.MONTH);
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month++;
        stringDate = day+"-"+month+"-"+year;
        mDateEditText.setText(stringDate);


        mImageGenerator.setStorageToSDCard(true);

        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentDate = Calendar.getInstance();
                year = mCurrentDate.get(Calendar.YEAR);
                month = mCurrentDate.get(Calendar.MONTH);
                day = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(DayView.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth++;
                        stringDate = selectedDay+"-"+selectedMonth+"-"+selectedYear;
                        mDateEditText.setText(stringDate);

                        mCurrentDate.set(selectedYear,selectedMonth,selectedDay);
                        mGeneratedDateIcon = mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
                        mDisplayGeneratedImage.setImageBitmap(mGeneratedDateIcon);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });
    }



    class addExerciseClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            addExerciseClicked(view);
        }
    }

    class addSetClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            addSetClicked(view);
        }
    }
}

