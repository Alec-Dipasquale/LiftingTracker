package com.example.squale.liftingtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class AppOverlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_overlay);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5),(int)(height*.4));

        Button btnLogOutLink = (Button) findViewById(R.id.bLogOut);
        Button btnSettings = (Button) findViewById(R.id.bSettings);

        btnLogOutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutAction();
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsAction();
            }
        });

    }
    private void signOutAction(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(AppOverlay.this, LoginActivity.class));
    }
    private void settingsAction(){
        Toast toast = Toast.makeText(getApplicationContext(),
                "Doesn't do shit yet dumby", Toast.LENGTH_LONG);
        toast.show();
    }
    public void optionsAction(final Context activity){
        View rootView = ((Activity)activity).getWindow().getDecorView().findViewById(
                android.R.id.content);
        ImageButton btnOptions =  (ImageButton) rootView.findViewById(R.id.bSettings);
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsPopIntent = new Intent(activity, AppOverlay.class);
                activity.startActivity(settingsPopIntent);
            }
        });


    }
}
