package com.example.borapaulco2mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.borapaulco2mobileapp.utils.DBUtils;

public class LogIn extends AppCompatActivity {
    private static final String DEFAULT_EMAIL_MESSAGE = "The threshold was reached, you must check the room.";
    private static final String DEFAULT_EMAIL_SUBJECT = "CO level RISING";

    private static final int LOG_IN_ACTIVITY_CODE = 1;

    private Button mLoginButton;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mRegisterButton;
    private DBUtils db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mRegisterButton = findViewById(R.id.openRegisterAcitivty);
        mLoginButton = findViewById(R.id.logInButton);
        mEmailEditText = findViewById(R.id.loginEmail);
        mPasswordEditText = findViewById(R.id.logInPassword);
        db = new DBUtils(this);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                if(email.equals("") || password.equals("")){
                    Toast.makeText(LogIn.this, "Please fill all the fields",Toast.LENGTH_SHORT).show();
                }else
                    if (db.loginCheck(email,password)){
                        Intent intent = new Intent(getApplicationContext(), SenzorValueActivity.class);
                        intent.putExtra("emailAddress", mEmailEditText.getText().toString());
                        intent.putExtra("emailMessage",DEFAULT_EMAIL_MESSAGE);
                        intent.putExtra("emailSubject",DEFAULT_EMAIL_SUBJECT);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LogIn.this, "The credentials does not match",Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
    public void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}