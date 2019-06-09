package com.squale.liftingtracker.activity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squale.liftingtracker.AppOverlay;
import com.squale.liftingtracker.R;
import com.squale.liftingtracker.dao.WorkoutDAO;
import com.squale.liftingtracker.models.Workout;
import com.kd.dynamic.calendar.generator.ImageGenerator;
import com.squale.liftingtracker.setups.WorkoutSetup;

import java.util.ArrayList;
import java.util.Calendar;

public class WorkoutActivity extends AppCompatActivity {

    //Local Variables

    public static final String TAG = "WorkoutActivity";


    private TextView mDateEditText;
    private Calendar mCurrentDate;
    private Bitmap mGeneratedDateIcon;
    private ImageView mDisplayGeneratedImage;
    private ImageGenerator mImageGenerator;

    private int day;
    private int month;
    private int year;
    private String stringDate;

    private WorkoutDAO mWorkoutDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_day_view);
        final Button bAddExercise = findViewById(R.id.btnAddSet);
        LinearLayout llExercise = findViewById(R.id.linAddExercise);
        final LinearLayout llAddExercise = findViewById(R.id.lin_btn_holder_add_exercise);
        final LinearLayout llWorkout = findViewById(R.id.linWorkout);
        final WorkoutSetup workoutSetup = new WorkoutSetup(llExercise, bAddExercise, this);

        final Button bFinish = findViewById(R.id.btnFinish);
        //Button bTestLoad = findViewById(R.id.btnTestLoad);
        AppOverlay appOverlay = new AppOverlay();

        bFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "finish clicked!");
                workoutSetup.makeNonEditable(llWorkout, bFinish, llAddExercise, bAddExercise);
                workoutSetup.sendCurrentToDatabase(stringDate);

            }
        });

        appOverlay.optionsAction(WorkoutActivity.this);
        createCalendar();


    }

    private void createCalendar() {
        // Create an object of ImageGenerator class in your activity
        // and pass the context as the parameter
        mImageGenerator = new ImageGenerator(this);
        mDateEditText = findViewById(R.id.tvDateEntered);
        mDisplayGeneratedImage = findViewById(R.id.imageGen);

        // Set the icon size to the generated in dip.
        mImageGenerator.setIconSize(50, 5);

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
        stringDate = day + "-" + month + "-" + year;
        mDateEditText.setText(stringDate);


        mImageGenerator.setStorageToSDCard(true);

        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentDate = Calendar.getInstance();
                year = mCurrentDate.get(Calendar.YEAR);
                month = mCurrentDate.get(Calendar.MONTH);
                day = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(WorkoutActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth++;
                        stringDate = selectedDay + "-" + selectedMonth + "-" + selectedYear;
                        mDateEditText.setText(stringDate);

                        mCurrentDate.set(selectedYear, selectedMonth, selectedDay);
//                        mGeneratedDateIcon = mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
//                        mDisplayGeneratedImage.setImageBitmap(mGeneratedDateIcon);
                    }
                }, year, month, day);
                mDatePicker.show();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }
}

