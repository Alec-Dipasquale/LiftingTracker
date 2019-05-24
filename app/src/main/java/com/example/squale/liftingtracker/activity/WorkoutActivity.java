package com.example.squale.liftingtracker.activity;

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
import android.widget.Toast;

import com.example.squale.liftingtracker.AppOverlay;
import com.example.squale.liftingtracker.objects.Workout;
import com.example.squale.liftingtracker.R;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class WorkoutActivity extends AppCompatActivity{

    //Local Variables

    public static final String TAG = "WorkoutActivity";

    private int count = 0;

    private TextView mDateEditText;
    private Calendar mCurrentDate;
    private Bitmap mGeneratedDateIcon;
    private ImageGenerator mImageGenerator;
    private ImageView mDisplayGeneratedImage;
    private ArrayList<LinearLayout> horizontalSetsLayoutArrayList = new ArrayList<>();
    private final int horizontalSetsLayoutID = 20000;

    private int day;
    private int month;
    private int year;
    private String stringDate;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_day_view);
        final Button bAddExercise = findViewById(R.id.btnAddSet);
        LinearLayout llExercise = findViewById(R.id.linAddExercise);
        final LinearLayout llAddExercise = findViewById(R.id.lin_btn_holder_add_exercise);
        final LinearLayout llWorkout = findViewById(R.id.linWorkout);
        final Workout workout = new Workout();
        workout.setUp(stringDate, bAddExercise, llExercise, WorkoutActivity.this);


        final Button bFinish = findViewById(R.id.btnFinish);
        //Button bTestLoad = findViewById(R.id.btnTestLoad);
        AppOverlay appOverlay = new AppOverlay();

        bFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "finish clicked!");
                workout.makeNonEditable(llWorkout, bFinish, llAddExercise, bAddExercise);
                workout.sendCurrentToDatabase();

//
//                try {
//                    FileOutputStream fOut = openFileOutput(file,MODE_PRIVATE);
//                    fOut.write(full.getWorkoutInfoString().getBytes());
//
//                    fOut.close();
//                    File filePath = new File(getFilesDir(),file);
//                    /*if (filePath.exists()){
//                        filePath.delete();
//                    }
//                    filePath.createNewFile();*/
//                    Toast.makeText(getBaseContext(), "File Saved at " + filePath +"Contents " +full.toString(), Toast.LENGTH_LONG).show();
//                }
//                catch (Exception e){
//                    Log.d(TAG, "Error initializing stream");
//                }
            }
        });

        appOverlay.optionsAction(WorkoutActivity.this);

        createCalendar();

    }

    public void finishWorkout(Workout full){

//        String file = "newFile.tmp";
//        FileOutputStream fOut;
//        try {
//            fOut = openFileOutput(file,MODE_PRIVATE);
//            ObjectOutputStream objectOutStream = new ObjectOutputStream(fOut);
//            objectOutStream.writeObject(full);
//            fOut.close();
//            //objectOutStream.close();
//            File filePath = new File(getFilesDir(),file);
//            Toast.makeText(getBaseContext(), "File Saved at " +filePath +"Contents Some bullshit", Toast.LENGTH_LONG).show();
//
//
//        }  catch (IOException e) {
//            Log.d(TAG, "Error initializing stream");
//        }

//        try {
//            String filepath = "src\\main\\res\\userFiles\\obj";
//            FileOutputStream fileOut = new FileOutputStream(filepath);
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//            objectOut.writeObject(day);
//            objectOut.close();
//            Log.d(TAG, "finishWorkout file made successfully");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d(TAG, "finishWorkout Failed");
//        }
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
            Toast.makeText(WorkoutActivity.this, "File added " + day + "-" + month + "-" + year + ".txt", Toast.LENGTH_LONG).show();
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

            Toast.makeText(WorkoutActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
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
        mDateEditText =findViewById(R.id.tvDateEntered);
        mDisplayGeneratedImage = findViewById(R.id.imageGen);

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

                DatePickerDialog mDatePicker = new DatePickerDialog(WorkoutActivity.this, new DatePickerDialog.OnDateSetListener() {
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

//    class finishClick implements View.OnClickListener{
//        @Override
//        public void onClick(View view){
//            //full.finishWorkout(view);
//            finishWorkout(full);
//        }
//    }





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

