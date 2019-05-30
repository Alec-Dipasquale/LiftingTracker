package com.squale.liftingtracker.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squale.liftingtracker.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initiating Firebase Auth Object
        firebaseAuth = FirebaseAuth.getInstance();
        checkLoggedIn();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button bLogin = findViewById(R.id.bLogin);
        TextView registerLink = findViewById(R.id.tvRegisterHere);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void checkLoggedIn() {
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Need E-mail", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Need Password", Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(LoginActivity.this, "Login Successful.",
                                    Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user.isEmailVerified())
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            else


                                Toast.makeText(LoginActivity.this, "Verify Email", Toast.LENGTH_LONG).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Incorrect User or Password",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void goToRegister() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(registerIntent);
    }
}
