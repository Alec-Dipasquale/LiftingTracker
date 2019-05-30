package com.squale.liftingtracker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.squale.liftingtracker.R;


/**
 * Created by FBI on 8/13/2017.
 */

public class AllPRsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_prs_activity);
        final TextView tvPRs = findViewById(R.id.tvPRs);


//        myDb = new DatabaseHelper(this);
//
//        Cursor res = myDb.getAllData();
//        if(res.getCount()!=0) {
//            res.moveToLast();
//            StringBuilder strBuilder = new StringBuilder();
//            strBuilder.append("Bench :" + res.getString(0) + "\n")
//                    .append("Deadlift :" + res.getString(1) + "\n")
//                    .append("Squats :" + res.getString(2) + "\n")
//                    .append("Date :" + res.getString(3) + "\n\n");
//            while (res.moveToPrevious()) {
//                strBuilder.append("Bench :" + res.getString(0) + "\n")
//                        .append("Deadlift :" + res.getString(1) + "\n")
//                        .append("Squats :" + res.getString(2) + "\n")
//                        .append("Date :" + res.getString(3) + "\n\n");
//            }
//
//            // Show all data
//            tvPRs.setText(strBuilder.toString());
//        }
//        tvPRs.setMovementMethod(new ScrollingMovementMethod());


    }
}
