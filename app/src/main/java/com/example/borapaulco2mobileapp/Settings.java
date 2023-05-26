package com.example.borapaulco2mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    private Button backButton;
    private Button sendTestEmailsButton;
    private Button mSetMailsDetails;
    private final static String testMailSubject = "Mere mailurili?";

    private final static String testMailContact = "alex_roncea@yahoo.com";

    private static final String testMailMessage = "asta-i mail de la licenta ba, il primisi?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sender);
        backButton = findViewById(R.id.backButton);

        sendTestEmailsButton = findViewById(R.id.sendEmailTest);
        mSetMailsDetails = findViewById(R.id.addNewAddress);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        sendTestEmailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        mSetMailsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmailSettings();
            }
        });
    }

    private void openEmailSettings(){
        Intent intent = new Intent(this,EmailSettings.class);
        startActivity(intent);
    }
    private void sendMail(){
        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,testMailContact,testMailSubject,testMailMessage);
        javaMailAPI.execute();
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, SenzorValueActivity.class);
        startActivity(intent);
    }

}