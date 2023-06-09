package com.example.borapaulco2mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChartOfDataActivity extends AppCompatActivity {
    private static String url = "https://thingspeak.com/channels/2118120/fields/1.json?api_key=DGHTAT9MKCKX4140&results=2";
    private int counter = 0;
    private LineChart mLineChart;
    private Button mBackToMain;
    private ArrayList<Entry> dataArrayList = new ArrayList<>();
    private String sensorValue;
    private LineDataSet lineDataSet;

    private ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    private long delay = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());

        setContentView(R.layout.activity_chart_of_data);
        mLineChart = (LineChart) findViewById(R.id.senzorValueLineChart);
        mLineChart.setTouchEnabled(true);
        mLineChart.setPinchZoom(true);

        YAxis yAxisLeft = mLineChart.getAxisLeft();
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "";
            }
        });

        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axisBase) {
                return String.valueOf(value);
            }
        });

        mBackToMain = findViewById(R.id.backToMainActivity);
        mBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity(getIntent().getStringExtra("emailAddress"));
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getRequest(url);
                lineDataSet = new LineDataSet(dataArrayList, "Sensor Values");
                LineData lineData = new LineData(new LineDataSet(getIntent().getParcelableExtra(sensorValue), "Sensor Values"));
                lineData.addDataSet(lineDataSet);
                mLineChart.setData(lineData);
                mLineChart.invalidate();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    private void openMainActivity(String emailAddress) {
        Intent intent = new Intent(this, SenzorValueActivity.class);
        intent.putExtra("emailAddress",emailAddress);
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
                            Entry entry = new Entry(counter, Integer.parseInt(sensorValue));
                            dataArrayList.add(entry);
                            counter++;
                        } catch (JSONException e) {
                            System.out.println("Exception at parsing json");
                            ;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChartOfDataActivity.this, "Can't send reques to ThingSpeak", Toast.LENGTH_LONG);
            }
        });
        queue.add(stringRequest);
    }
}