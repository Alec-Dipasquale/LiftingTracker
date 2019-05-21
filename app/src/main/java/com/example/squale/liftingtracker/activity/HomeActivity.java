package com.example.squale.liftingtracker.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.squale.liftingtracker.AppOverlay;
import com.example.squale.liftingtracker.LoginActivity;
import com.example.squale.liftingtracker.R;
import com.example.squale.liftingtracker.UserStatistics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppOverlay appOverlay = new AppOverlay();

        firebaseAuth = FirebaseAuth.getInstance();

        Button btnUserStatistics = findViewById(R.id.bUserStatistics);
        FloatingActionButton fab = findViewById(R.id.fab);
       // ImageButton btnSettings = findViewById(R.id.bSettings);
        TextView tvUser = findViewById(R.id.tvUser);


        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));

        }else{
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            tvUser.setText("Welcome, " + currentUser.getEmail());
        }




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
            Intent dayViewIntent = new Intent(HomeActivity.this, DayActivity.class);
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
