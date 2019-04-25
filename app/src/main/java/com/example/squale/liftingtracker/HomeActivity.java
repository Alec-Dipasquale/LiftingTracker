package com.example.squale.liftingtracker;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppOverlay appOverlay = new AppOverlay();

        firebaseAuth = FirebaseAuth.getInstance();

        Button btnUserStatistics = (Button) findViewById(R.id.bUserStatistics);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       // ImageButton btnSettings = (ImageButton) findViewById(R.id.bSettings);
        TextView tvUser = (TextView) findViewById(R.id.tvUser);


        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));

        }

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        tvUser.setText("Welcome, " + currentUser.getEmail());



        //On Click Listeners
        btnUserStatistics.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startUserStatistics();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startDayView();
                    }
                });
        appOverlay.optionsAction(HomeActivity.this);
//        btnSettings.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        openSettings();
//                    }
//                });

    }

        // On Click Actions
        private void startDayView(){
            Intent dayViewIntent = new Intent(HomeActivity.this, DayView.class);
            HomeActivity.this.startActivity(dayViewIntent);
        }
        private void startUserStatistics(){

            Intent userStatisticsIntent = new Intent(HomeActivity.this, UserStatistics.class);
            HomeActivity.this.startActivity(userStatisticsIntent);
        }
        private void openSettings(){
                    Intent settingsPopIntent = new Intent(HomeActivity.this, AppOverlay.class);
                    HomeActivity.this.startActivity(settingsPopIntent);
        }
}
