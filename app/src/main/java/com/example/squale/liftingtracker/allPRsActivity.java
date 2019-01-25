package com.example.squale.liftingtracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Created by FBI on 8/13/2017.
 */

public class allPRsActivity extends AppCompatActivity {
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_prs_activity);
        final TextView tvPRs = (TextView) findViewById(R.id.tvPRs);
        myDb = new DatabaseHelper(this);



        Cursor res = myDb.getAllData();
        if(res.getCount()!=0) {
            res.moveToLast();
            StringBuffer buffer = new StringBuffer();
            buffer.append("Bench :" + res.getString(0) + "\n");
            buffer.append("Deadlift :" + res.getString(1) + "\n");
            buffer.append("Squats :" + res.getString(2) + "\n");
            buffer.append("Date :" + res.getString(3) + "\n\n");
            while (res.moveToPrevious()) {
                buffer.append("Bench :" + res.getString(0) + "\n");
                buffer.append("Deadlift :" + res.getString(1) + "\n");
                buffer.append("Squats :" + res.getString(2) + "\n");
                buffer.append("Date :" + res.getString(3) + "\n\n");
            }

            // Show all data
            tvPRs.setText(buffer.toString());
        }
        tvPRs.setMovementMethod(new ScrollingMovementMethod());



    }
}
