package com.example.squale.liftingtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonalRecordSettings extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText etFirstPR, etSecondPR, etThirdPR;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_record_settings);
            myDb = new DatabaseHelper(this);

        etFirstPR = (EditText) findViewById(R.id.etFirstPRNum);
        etSecondPR = (EditText) findViewById(R.id.etSecondPRNum);
        etThirdPR = (EditText) findViewById(R.id.etThirdPRNum);
        btnSave = (Button) findViewById(R.id.bSave);

        AddData();

    }

    public void AddData(){
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(etFirstPR.getText().toString(),
                        etSecondPR.getText().toString(),
                        etThirdPR.getText().toString());
                if(isInserted == true)
                    Toast.makeText(PersonalRecordSettings.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(PersonalRecordSettings.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                startActivity(new Intent(PersonalRecordSettings.this, UserStatistics.class));

            }
        });
    }


}
