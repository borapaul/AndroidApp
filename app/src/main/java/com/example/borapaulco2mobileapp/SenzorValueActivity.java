package com.example.borapaulco2mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SenzorValueActivity extends AppCompatActivity {

    private TextView mSensorValueTextView;
    private Button mRefreshButton;
    private Button mEmailSettingsButton;
    private HalfGauge mHalfGauge;
    private Button mChartButton;
    private String sensorValue = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChartButton = findViewById(R.id.chartButton);
        mSensorValueTextView = (TextView) findViewById(R.id.sensorValue);
        mRefreshButton = findViewById(R.id.refreshButton);
        mEmailSettingsButton = findViewById(R.id.emailSettings);
        mHalfGauge = findViewById(R.id.halfGauge);

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

        String url = "https://thingspeak.com/channels/2118120/fields/1.json?api_key=DGHTAT9MKCKX4140&results=2";
        getRequest(url);

        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://thingspeak.com/channels/2118120/fields/1.json?api_key=DGHTAT9MKCKX4140&results=2";
                getRequest(url);
            }
        });

        mEmailSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMailSettingsActivity();
            }
        });

        mChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChartAcitvity();
            }
        });
    }

    private void openChartAcitvity(){
        Intent intent = new Intent(this, ChartOfDataActivity.class);
        intent.putExtra("sensorValue", sensorValue);
        startActivity(intent);
    }

    private void openMailSettingsActivity(){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    private void getRequest(String url){
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
                            System.out.println("Exception at parsing json");;
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