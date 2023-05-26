package com.example.borapaulco2mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.mail.search.IntegerComparisonTerm;

public class RegisterActivity extends AppCompatActivity {

    private Button mRegisterButton;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private DBUtils db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mRegisterButton = findViewById(R.id.registerActivityButton);
        mEmailEditText = findViewById(R.id.emailAddresValue);
        mPasswordEditText = findViewById(R.id.registerPassword);
        mConfirmPasswordEditText = findViewById(R.id.registerConfirmPassword);
        db = new DBUtils(this);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String confirmPassword = mConfirmPasswordEditText.getText().toString();
                if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"You must fill all the fields", Toast.LENGTH_SHORT);
                }
                else {
                    if(password.equals(confirmPassword)){
                        if (db.checkIfUserExists(email)){
                            Toast.makeText(RegisterActivity.this, "The user already exists, please sing in", Toast.LENGTH_SHORT);
                        }
                        else {
                            if(db.insertDataIntoDb(email,password)){
                                Toast.makeText(RegisterActivity.this,"Register succesfully",Toast.LENGTH_SHORT);
                                Intent intent = new Intent(RegisterActivity.this, LogIn.class);
                                startActivity(intent);
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,"The passwords does not match", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }
}