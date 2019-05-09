package com.example.squale.liftingtracker;

import android.util.Log;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int stuff;
    int notstuff;
    String stringStuff;
    private ArrayList<Integer> arrExercise = new ArrayList<Integer>();
    private static final String TAG = "Student";


    Student(int firstNum, int secondNum, String string){
        this.stuff = firstNum;
        this.notstuff = secondNum;
        this.stringStuff = string;
    }

    public void finishWorkout(View view){

        FileOutputStream outStream = null;
        try {
            FileOutputStream fos = new FileOutputStream("t.tmp");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            fos.close();
            Log.d(TAG, "File Made");

        } catch (java.io.NotSerializableException e){
            Log.d(TAG, "Not Serializable");
        }
            catch (FileNotFoundException e) {
            Log.d(TAG, "File not found");
        } catch (IOException e) {
            Log.d(TAG, "Error initializing stream");
        }
//
//            try {
//                String filepath = "src\\main\\res\\userFiles\\obj";
//                FileOutputStream fileOut = new FileOutputStream(filepath);
//                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//                objectOut.close();
//                Log.d("Day", "finishWorkout file made successfully");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.d("Day", "finishWorkout Failed");
//            }
        }

}
