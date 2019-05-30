package com.squale.liftingtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by FBI on 8/6/2017.
 */

public class Pop extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);


        final TextView[] etWeight = new TextView[]{
                (TextView) findViewById(R.id.etWeight1),
                (TextView) findViewById(R.id.etWeight2),
                (TextView) findViewById(R.id.etWeight3),
                (TextView) findViewById(R.id.etWeight4),
                (TextView) findViewById(R.id.etWeight5),
                (TextView) findViewById(R.id.etWeight6),
                (TextView) findViewById(R.id.etWeight7),
                (TextView) findViewById(R.id.etWeight8),
                (TextView) findViewById(R.id.etWeight9),
                (TextView) findViewById(R.id.etWeight10),
                (TextView) findViewById(R.id.etWeight11),
                (TextView) findViewById(R.id.etWeight12)};


        Intent intent = getIntent();
        String reps = intent.getStringExtra("reps");
        String weight = intent.getStringExtra("weight");
        int oneRM = 0;

        switch (Integer.parseInt(reps)) {
            case 1:
                oneRM = (int) (Integer.parseInt(weight));
                break;
            case 2:
                oneRM = (int) (Integer.parseInt(weight) / .95);
                break;
            case 3:
                oneRM = (int) (Integer.parseInt(weight) / .93);
                break;
            case 4:
                oneRM = (int) (Integer.parseInt(weight) / .9);
                break;
            case 5:
                oneRM = (int) (Integer.parseInt(weight) / .87);
                break;
            case 6:
                oneRM = (int) (Integer.parseInt(weight) / .85);
                break;
            case 7:
                oneRM = (int) (Integer.parseInt(weight) / .83);
                break;
            case 8:
                oneRM = (int) (Integer.parseInt(weight) / .80);
                break;
            case 9:
                oneRM = (int) (Integer.parseInt(weight) / .77);
                break;
            case 10:
                oneRM = (int) (Integer.parseInt(weight) / .75);
                break;
            case 11:
                oneRM = (int) (Integer.parseInt(weight) / .73);
                break;
            case 12:
                oneRM = (int) (Integer.parseInt(weight) / .7);
                break;


        }

        etWeight[0].setText(Integer.toString(oneRM));
        etWeight[1].setText(Integer.toString((int) (oneRM * .95)));
        etWeight[2].setText(Integer.toString((int) (oneRM * .93)));
        etWeight[3].setText(Integer.toString((int) (oneRM * .9)));
        etWeight[4].setText(Integer.toString((int) (oneRM * .87)));
        etWeight[5].setText(Integer.toString((int) (oneRM * .85)));
        etWeight[6].setText(Integer.toString((int) (oneRM * .83)));
        etWeight[7].setText(Integer.toString((int) (oneRM * .8)));
        etWeight[8].setText(Integer.toString((int) (oneRM * .77)));
        etWeight[9].setText(Integer.toString((int) (oneRM * .75)));
        etWeight[10].setText(Integer.toString((int) (oneRM * .73)));
        etWeight[11].setText(Integer.toString((int) (oneRM * .7)));


    }
}
