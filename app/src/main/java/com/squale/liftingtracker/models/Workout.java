package com.squale.liftingtracker.models;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squale.liftingtracker.dao.WorkoutDAO;

import java.util.ArrayList;

public class Workout {


    private static final String TAG = "Workout";
    private long Id;
    private String date;


    public Workout() {
    }

    public Workout(long Id, String mDate) {
        this.Id = Id;
        this.date = mDate;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String mDate) {
        this.date = mDate;
    }

    public long getId() {
        return Id;
    }

    public void setId(long mId) {
        this.Id = mId;
    }

}
