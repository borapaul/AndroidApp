package com.example.borapaulco2mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.borapaulco2mobileapp.domain.DataTableDetails;

public class Settings extends AppCompatActivity {
    private final static String TEST_MAIL_SUBJECT = "Test Email";

    private static final String TEST_MAIL_MESSAGE = "This is a test e-mail from CO mobile application. Don't worry, you are safe!";

    private Button backButton;
    private Button mSendTestEmailsButton;
    private Button mSetMailsDetails;
    private Button mSetThreshold;

    private Button mContactButton;

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
        mSetThreshold = findViewById(R.id.mailHistory);
        mContactButton = findViewById(R.id.contactButton);

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
                openHistoryActivity();
            }
        });

        mContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactActivity();
            }
        });
    }

    private void openContactActivity() {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("emailAddress",mailContact);
        startActivity(intent);
        finish();
    }

    private void openEmailSettings() {
        Intent intent = new Intent(this, EmailSettings.class);
        intent.putExtra("emailAddress",mailContact);
        startActivity(intent);
        finish();
    }

    private void openHistoryActivity() {
        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
        Intent activityIntent = getIntent();
        intent.putExtra("emailAddress",mailContact);
        intent.putExtra("listSize", listSize);
        for (int i = 0; i < listSize; i++) {
            DataTableDetails dataTableDetailsFromIntent = activityIntent.getParcelableExtra(String.valueOf(i));
            intent.putExtra(String.valueOf(i), dataTableDetailsFromIntent);
        }
        startActivity(intent);
        finish();
    }

    private void sendEmail() {
        try {
            //Send Mail
            JavaMailAPI javaMailAPI = new JavaMailAPI(this, mailContact, TEST_MAIL_SUBJECT, TEST_MAIL_MESSAGE);
            javaMailAPI.execute();
        } catch (Exception e) {
            Toast.makeText(this, "The email wasn't delivered", Toast.LENGTH_LONG);
        }
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, SenzorValueActivity.class);
        intent.putExtra("emailAddress",mailContact);
        startActivity(intent);
        finish();
    }

}