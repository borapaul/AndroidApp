package com.example.borapaulco2mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {
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
                        Intent intent = new Intent(LogIn.this, SenzorValueActivity.class);
                        startActivity(intent);
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