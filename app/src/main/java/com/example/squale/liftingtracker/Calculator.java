package com.example.squale.liftingtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Calculator extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_activity);

        final EditText etRepetitions = (EditText) findViewById(R.id.etRepetitions);
        final EditText etWeight = (EditText) findViewById(R.id.etWeight);
        final Button bCalculate = (Button) findViewById(R.id.bCalculate);

        bCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reps = etRepetitions.getText().toString();
                String weight = etWeight.getText().toString();
                Intent calcIntent = new Intent(Calculator.this, Pop.class);
                calcIntent.putExtra("reps", reps);
                calcIntent.putExtra("weight", weight);
                Calculator.this.startActivity(calcIntent);



            }
        });
    }


}

