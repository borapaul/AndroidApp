package com.example.borapaulco2mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;

public class EmailSettings extends AppCompatActivity {

    private Button mBackToSensorActivity;

    private EditText mEmailAddressValue;
    private EditText mEmailSubjectValue;
    private EditText mEmailMessageValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_settings);
        mBackToSensorActivity = findViewById(R.id.backToSensorValueActivity);
        mEmailAddressValue = findViewById(R.id.emailAddressValue);
        mEmailSubjectValue = findViewById(R.id.customSubjectValue);
        mEmailMessageValue = findViewById(R.id.customEmailMessageValue);


        mBackToSensorActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSensorActivity();
            }
        });
    }

    private void openSensorActivity(){
        Intent intent = new Intent(this,SenzorValueActivity.class);
        intent.putExtra("emailAddress", mEmailAddressValue.getText().toString());
        intent.putExtra("emailMessage", mEmailMessageValue.getText().toString());
        intent.putExtra("emailSubject", mEmailSubjectValue.getText().toString());
        startActivity(intent);
        finish();
    }
}