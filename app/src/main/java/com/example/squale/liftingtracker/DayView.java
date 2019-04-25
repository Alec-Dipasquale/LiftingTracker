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


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import static android.view.Gravity.*;

public class DayView extends AppCompatActivity{

    //Local Variables

    private int count = 0;

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
    private int addExerciseCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_day_view);


        Button bAddExercise = (Button) findViewById(R.id.btnAddSet);
        Button bFinish = (Button) findViewById(R.id.btnFinish);
        Button bTestLoad = (Button) findViewById(R.id.btnTestLoad);
        AppOverlay appOverlay = new AppOverlay();

        bFinish.setOnClickListener(new finishWorkoutSession());
        bTestLoad.setOnClickListener(new testLoad());
        bAddExercise.setOnClickListener(new addExerciseClick());

        appOverlay.optionsAction(DayView.this);

        createCalendar();

    }

    private void finishWorkOutSessionClicked(View view) {
        count++;
        FileOutputStream fileOutputStream = null;
        String data = "test data" + count;
        try{
            fileOutputStream = openFileOutput("src\\main\\res\\userFiles\\" + day + "-" + month + "-" + year + ".txt", MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            for(int i =0; i<horizontalSetsLayoutArrayList.size(); i++){
                LinearLayout tempLinearLayout = horizontalSetsLayoutArrayList.get(i);
                if(tempLinearLayout.getId() - horizontalSetsLayoutID == 0){
                    //tempLinearLayout.addView();
                }
            }
            Toast.makeText(DayView.this, "File added " + day + "-" + month + "-" + year + ".txt", Toast.LENGTH_LONG).show();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
                if(fileOutputStream!=null){
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    private void testLoadClicked(View view){
        FileInputStream fis = null;

        try{
            fis = openFileInput("src\\main\\res\\userFiles\\" + day + "-" + month + "-" + year + ".txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while((text = br.readLine())!= null){
                sb.append(text).append("\n");
            }

            Toast.makeText(DayView.this, sb.toString(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e ) {
            e.printStackTrace();
        } finally{
            if (fis != null){
                try{
                    fis.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
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

    class finishWorkoutSession implements View.OnClickListener{
        @Override
        public void onClick(View view){finishWorkOutSessionClicked(view);}
    }

    class testLoad implements View.OnClickListener{
        @Override
        public void onClick(View view){testLoadClicked(view);}
    }

    class addExerciseClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            addExerciseCount++;
            LinearLayout linAddExercise = (LinearLayout) findViewById(R.id.linAddExercise);
            AddExercise addExercise = new AddExercise(addExerciseCount, linAddExercise,DayView.this, view);
        }
    }



    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }
}

