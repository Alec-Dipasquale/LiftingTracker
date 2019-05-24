package com.example.squale.liftingtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.squale.liftingtracker.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserStatistics extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private TextView tvYourStatistics;
    private TextView tvLogOutLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_statistics);

        firebaseAuth = FirebaseAuth.getInstance();

        //final EditText etName = (EditText) findViewById(R.id.etName);
        final TextView tvFirstNum = (TextView) findViewById(R.id.tvFirstNum);
        final TextView tvSecondNum = (TextView) findViewById(R.id.tvSecondNum);
        final TextView tvThirdNum = (TextView) findViewById(R.id.tvThirdNum);
        final TextView tvFirstPR = (TextView) findViewById(R.id.tvFirstPR);
        final TextView tvSecondPR = (TextView) findViewById(R.id.tvSecondPR);
        final TextView tvThirdPR = (TextView) findViewById(R.id.tvThirdPR);
        tvYourStatistics = (TextView) findViewById(R.id.tvYourStatistics);
        final Button bCalculator = (Button) findViewById(R.id.bCalculator);
        //final Button bPR = (Button) findViewById(R.id.bPR);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.bSettings);

        //final Button btnAllPRs = (Button) findViewById(R.id.btnAllPRs);


        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        tvYourStatistics.setText("Welcome, " + currentUser.getEmail());

        bCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calculatorIntent = new Intent(UserStatistics.this, Calculator.class);
                UserStatistics.this.startActivity(calculatorIntent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });


    }

    private void openSettings(){
        Intent settingsPopIntent = new Intent(UserStatistics.this, AppOverlay.class);
        UserStatistics.this.startActivity(settingsPopIntent);
    }
}
