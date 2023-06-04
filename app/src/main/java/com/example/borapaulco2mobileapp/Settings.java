package com.example.borapaulco2mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {
    private final static String TEST_MAIL_SUBJECT = "Test Email";

    private static final String TEST_MAIL_MESSAGE = "This is a test e-mail from CO mobile application. Don't worry, you are safe!";

    private Button backButton;
    private Button mSendTestEmailsButton;
    private Button mSetMailsDetails;
    private Button mSetThreshold;

    private String mailContact;

    private DataTableDetails dataTableDetails;

    private int listSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        backButton = findViewById(R.id.backButton);

        mSendTestEmailsButton = findViewById(R.id.sendEmailTest);
        mSetMailsDetails = findViewById(R.id.addNewAddress);
        mSetThreshold = findViewById(R.id.setThresholdButton);

        Intent intent = getIntent();

        mailContact = intent.getStringExtra("emailAddress");

        listSize = intent.getIntExtra("listSize", 0);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        mSetMailsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmailSettings();
            }
        });

        mSendTestEmailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        mSetThreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTresholdActivity();
            }
        });
    }

    private void openEmailSettings(){
        Intent intent = new Intent(this,EmailSettings.class);
        startActivity(intent);
    }

    private void openTresholdActivity(){
        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
        Intent activityIntent = getIntent();
        intent.putExtra("listSize", listSize);
        for(int i = 0 ; i < listSize; i++){
            DataTableDetails dataTableDetailsFromIntent = activityIntent.getParcelableExtra(String.valueOf(i));
            intent.putExtra(String.valueOf(i),dataTableDetailsFromIntent);
        }
        startActivity(intent);
    }

    private void sendEmail(){
            //Send Mail
            JavaMailAPI javaMailAPI = new JavaMailAPI(this,mailContact,TEST_MAIL_SUBJECT,TEST_MAIL_MESSAGE);
            javaMailAPI.execute();
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, SenzorValueActivity.class);
        startActivity(intent);
    }

}