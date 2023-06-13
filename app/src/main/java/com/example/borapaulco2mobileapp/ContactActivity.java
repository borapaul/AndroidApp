package com.example.borapaulco2mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    private static final String EMAIL = "coappnotification@gmail.com";

    private String mailContact;

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

        mailContact = getIntent().getStringExtra("emailAddress");

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(EMAIL, mEmaiSubjectText.getText().toString(), mEmaiMessageText.getText().toString());
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }

    private void openMainActivity(){
        Intent intent = new Intent(this, SenzorValueActivity.class);
        intent.putExtra("emailAddress",mailContact);
        startActivity(intent);
    }

    private void sendEmail(String email, String subject, String message){
        try {
            JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, message);
            javaMailAPI.execute();
        }catch (Exception e){
            Toast.makeText(ContactActivity.this,"The email wasn't delivered", Toast.LENGTH_LONG);
        }
    }
}