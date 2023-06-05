package com.example.borapaulco2mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ContactActivity extends AppCompatActivity {

    private static final String EMAIL = "coappnotification@gmail.com";

    private Button mBackButton;

    private Button mSendButton;

    private EditText mEmaiSubjectText;

    private EditText mEmaiMessageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mBackButton = findViewById(R.id.backButton);
        mSendButton = findViewById(R.id.sendButton);
        mEmaiMessageText = findViewById(R.id.emailMessage);
        mEmaiSubjectText = findViewById(R.id.emailSubject);

        sendEmail(EMAIL, mEmaiSubjectText.getText().toString(), mEmaiMessageText.getText().toString());

    }

    private void sendEmail(String email, String subject, String message){
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email,subject,message);
        javaMailAPI.execute();
    }
}