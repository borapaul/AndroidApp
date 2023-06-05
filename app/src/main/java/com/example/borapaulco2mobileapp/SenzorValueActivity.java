package com.example.borapaulco2mobileapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.example.borapaulco2mobileapp.domain.DataTableDetails;
import com.example.borapaulco2mobileapp.domain.EmailDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class SenzorValueActivity extends AppCompatActivity {

    private static final String URL = "https://thingspeak.com/channels/2118120/fields/1.json?api_key=DGHTAT9MKCKX4140&results=2";

    private TextView mSensorValueTextView;
    private Button mRefreshButton;
    private Button mEmailSettingsButton;
    private HalfGauge mHalfGauge;
    private Button mChartButton;
    private EditText mThresholdValueText;
    private Switch mIsSendingEmails;
    private String sensorValue = "0";

    private static final int DEFAULT_THRESHOLD = 35;

    private static final boolean IS_SENDING_EMAILS = true;
    private int threshold = DEFAULT_THRESHOLD;
    private boolean isSendingEmails = IS_SENDING_EMAILS;
    private List<DataTableDetails> dataTableDetailsList = new ArrayList<>();
    private EmailDto emailDto = new EmailDto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();


        mChartButton = findViewById(R.id.chartButton);
        mSensorValueTextView = (TextView) findViewById(R.id.sensorValue);
        mRefreshButton = findViewById(R.id.refreshButton);
        mEmailSettingsButton = findViewById(R.id.emailSettings);
        mHalfGauge = findViewById(R.id.halfGauge);
        mIsSendingEmails = findViewById(R.id.switchEmailSending);
        mThresholdValueText = findViewById(R.id.thresholdValueText);

        gaugeSettings();
        getRequest(URL);

        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequest(URL);
            }
        });

        mEmailSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMailSettingsActivity(getIntent().getStringExtra("emailAddress"));
            }
        });

        mChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChartAcitvity();
            }
        });

        emailDto.setEmail(intent.getStringExtra("emailAddress"));
        emailDto.setMessage(intent.getStringExtra("emailMessage"));
        emailDto.setSubject(intent.getStringExtra("emailSubject"));
        mThresholdValueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                threshold = Integer.parseInt(mThresholdValueText.getText().toString());
            }
        });
        mIsSendingEmails.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isSendingEmails = false;
                }else {
                    isSendingEmails = true;
                }
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getRequest(URL);
                checkifThresholdIsReached(threshold, isSendingEmails);
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }

    private void gaugeSettings() {
        Range firstPart = new Range();
        firstPart.setColor(Color.parseColor("#a3c6ff"));
        firstPart.setFrom(0);
        firstPart.setTo(65);

        Range secondPart = new Range();
        secondPart.setColor(Color.parseColor("#297aff"));
        secondPart.setFrom(65);
        secondPart.setTo(135);

        Range thirdPart = new Range();
        thirdPart.setColor(Color.parseColor("#000552"));
        thirdPart.setFrom(135);
        thirdPart.setTo(200);

        mHalfGauge.addRange(firstPart);
        mHalfGauge.addRange(secondPart);
        mHalfGauge.addRange(thirdPart);
        mHalfGauge.setNeedleColor(Color.WHITE);
        mHalfGauge.setValueColor(Color.WHITE);
        mHalfGauge.setMinValue(0);
        mHalfGauge.setMaxValue(200);
    }

    private void checkifThresholdIsReached(int threshold, boolean isSendingEmails) {

        if (checkIfSendEmails(threshold, isSendingEmails)) {
            sendMail(emailDto.getEmail(), emailDto.getSubject(), emailDto.getMessage());
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            DataTableDetails dataTableDetails = new DataTableDetails();
            dataTableDetails.setmThreshold(String.valueOf(threshold));
            dataTableDetails.setmContact(emailDto.getEmail());
            dataTableDetails.setmDate(dateFormat.format(System.currentTimeMillis()));
            dataTableDetailsList.add(dataTableDetails);
        }
    }

    private boolean checkIfSendEmails(int threshold, boolean isSendingEmails) {
        return (Integer.parseInt(sensorValue) > threshold && isSendingEmails);
    }

    private void sendMail(String mailContact, String mailSubject, String mailMessage) {
            JavaMailAPI javaMailAPI = new JavaMailAPI(this, mailContact, mailSubject, mailMessage);
            javaMailAPI.execute();

    }

    private void openChartAcitvity() {
        Intent intent = new Intent(this, ChartOfDataActivity.class);
        intent.putExtra("sensorValue", sensorValue);
        startActivity(intent);
    }

    private void openMailSettingsActivity(String mailContact) {
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("emailAddress", mailContact);
        intent.putExtra("listSize", dataTableDetailsList.size());
        for (int i = 0; i < dataTableDetailsList.size(); i++) {
            intent.putExtra(String.valueOf(i), dataTableDetailsList.get(i));
        }
        startActivity(intent);
        finish();
    }

    private void getRequest(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject arrayOfData = new JSONObject(response);
                            JSONArray allSensorValues = arrayOfData.getJSONArray("feeds");
                            String lastSensorValueJson = allSensorValues.getString(1);
                            JSONObject sensorValueObject = new JSONObject(lastSensorValueJson);
                            sensorValue = sensorValueObject.getString("field1");
                            System.out.println(sensorValue);
                            mSensorValueTextView.setText(sensorValue);
                            mHalfGauge.setValue(Double.parseDouble(sensorValue));
                        } catch (JSONException e) {
                            System.out.println("Exception at parsing json");
                            ;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        });
        queue.add(stringRequest);
    }


}