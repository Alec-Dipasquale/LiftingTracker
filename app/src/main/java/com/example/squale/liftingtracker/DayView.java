package com.example.squale.liftingtracker;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.kd.dynamic.calendar.generator.ImageGenerator;


import java.util.Calendar;
import java.util.Vector;

import static android.view.Gravity.*;

public class DayView extends AppCompatActivity{

    private TextView mDateEditText;
    private Calendar mCurrentDate;
    private Bitmap mGeneratedDateIcon;
    private ImageGenerator mImageGenerator;
    private ImageView mDisplayGeneratedImage;

    private Vector horizontalSetsLayoutIDs = new Vector();
    private Vector btnNewSetIDs = new Vector();
    private final int horizontalSetsLayoutID = 5000;
    private final int btnNewSetID = 6000;
    private final int singleSetLayoutID=7000;
    private int currentButtonId;
    private LinearLayout linAddSet;
    private LinearLayout linAddExercise;
    private LinearLayout exerciseLayout;
    private LinearLayout horizontalSetsLayout;
    private LinearLayout exerciseTitleBarLayout;
    private LinearLayout setsButtonLayout;
    private LinearLayout singleSetLayout;
    private ImageButton btnNewSet;

    private int day;
    private int month;
    private int year;
    private String stringDate;
    private int countSets = 0;
    private int countExercises = 0;

    private static final String TAG = "Testing";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        Button bAddExercise = (Button) findViewById(R.id.btnAddSet);
        AppOverlay appOverlay = new AppOverlay();

        bAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExercise();
            }
        });

        appOverlay.optionsAction(DayView.this);

        createCalendar();

    }

    private void addExercise(){
        countExercises++;


        linAddExercise = (LinearLayout) findViewById(R.id.linAddExercise);

        exerciseLayout = new LinearLayout(this);
        exerciseLayout.setOrientation(LinearLayout.VERTICAL);
        exerciseLayout.setGravity(View.FOCUS_LEFT);
        exerciseLayout.setPadding(5,5,5,5);


        exerciseTitleBarLayout = new LinearLayout(this);
        exerciseTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        exerciseTitleBarLayout.setOrientation(LinearLayout.HORIZONTAL);
        exerciseTitleBarLayout.setGravity(FILL);
        exerciseTitleBarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        setsButtonLayout = new LinearLayout(this);
        setsButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
        setsButtonLayout.setGravity(END);
        setsButtonLayout.setBackgroundColor(Color.WHITE);

        horizontalSetsLayout = new LinearLayout(this);
        horizontalSetsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        horizontalSetsLayout.setOrientation(LinearLayout.VERTICAL);
        horizontalSetsLayout.setGravity(FILL);
        horizontalSetsLayout.setBackgroundColor(Color.WHITE);
        horizontalSetsLayout.setId(horizontalSetsLayoutID +countExercises);
        horizontalSetsLayoutIDs.add(horizontalSetsLayoutID +countExercises);


        final TextView tvExercise = new TextView(this);
        tvExercise.setText("(Exercise)");
        tvExercise.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tvExercise.setTextColor(Color.BLACK);
        tvExercise.setPadding(10,5,5,5);

        final TextView etExerciseCount = new TextView(this);
        etExerciseCount.setText(String.format("%d", countExercises));
        etExerciseCount.setTextColor(Color.BLACK);
        etExerciseCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        etExerciseCount.setPadding(5,5, 10,5);



        btnNewSet = new ImageButton(this);
        btnNewSet.setId(btnNewSetID +countExercises);
        btnNewSetIDs.add(btnNewSetID +countExercises);
        btnNewSet.setImageResource(R.drawable.plus);
        btnNewSet.setLayoutParams(new LinearLayout.LayoutParams(400, 200));


        exerciseLayout.addView(exerciseTitleBarLayout);
        exerciseLayout.addView(horizontalSetsLayout);
        setsButtonLayout.addView(btnNewSet);
        exerciseLayout.addView(setsButtonLayout);

        exerciseTitleBarLayout.addView(etExerciseCount);
        exerciseTitleBarLayout.addView(tvExercise);

        btnNewSet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currentButtonId = view.getId();
                addSet();
            }
        });

        linAddExercise.addView(exerciseLayout);

    }

    private void addSet(){

        countSets++;
        singleSetLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        singleSetLayout.setOrientation(LinearLayout.HORIZONTAL);
        singleSetLayout.setGravity(FILL_HORIZONTAL);
        singleSetLayout.setLayoutParams(layoutParams);
        singleSetLayout.setId(singleSetLayoutID+countSets);



        TextView tvSetsCount = new TextView(this); // this refers to the activity or the context.
        EditText etReps = new EditText(this);
        EditText etWeight  = new EditText(this);


        // set attributes as need
        etWeight.setLayoutParams(layoutParams);
        etWeight.setHint("Weight");
        etWeight.setPadding(50,20,50,20);

        etReps.setLayoutParams(layoutParams);
        etReps.setHint("Reps");
        etReps.setPadding(50,20,50,20);

        tvSetsCount.setText(String.format("%d", countSets));
        tvSetsCount.setTextColor(Color.BLACK);
        tvSetsCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tvSetsCount.setPadding(5,0, 20,0);

        singleSetLayout.addView(tvSetsCount);
        singleSetLayout.addView(etWeight);
        singleSetLayout.addView(etReps);

        horizontalSetsLayout.addView(singleSetLayout);

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
}

