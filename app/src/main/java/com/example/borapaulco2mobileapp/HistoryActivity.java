package com.example.borapaulco2mobileapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.borapaulco2mobileapp.domain.DataTableDetails;

public class HistoryActivity extends AppCompatActivity {
    private String mailContact;
    private TableLayout mTableLayout;
    private Button mBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mTableLayout = findViewById(R.id.mainTable);
        mBackButton = findViewById(R.id.backToSettings);

        mailContact = getIntent().getStringExtra("emailAddress");

        Intent intent = getIntent();
        TableRow tableRow = new TableRow(this);
        setHeader("id", "Contact", "Threshold", "Date", tableRow);

        int listSize = intent.getIntExtra("listSize", 0);
        for (int i = 0; i < listSize; i++) {
            DataTableDetails dataTableDetails = intent.getParcelableExtra(String.valueOf(i));
            TableRow tableRowForData = new TableRow(this);
            setHeader(
                    String.valueOf(i),
                    dataTableDetails.getmContact(),
                    dataTableDetails.getmThreshold(),
                    dataTableDetails.getmDate(),
                    tableRowForData);
        }

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }

    private void openMainActivity() {
        Intent intent = new Intent(this, SenzorValueActivity.class);
        intent.putExtra("emailAddress",mailContact);
        startActivity(intent);
        finish();
    }


    private void setHeader(String id, String contact, String threshold, String date, TableRow mTablewRow) {

        TextView idTextView = new TextView(this);
        idTextView.setText(id);
        idTextView.setTextColor(Color.WHITE);
        idTextView.setPadding(10, 10, 10, 10);
        idTextView.setTextSize(14);
        idTextView.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView contactTextView = new TextView(this);
        contactTextView.setText(contact);
        contactTextView.setTextColor(Color.WHITE);
        contactTextView.setPadding(10, 10, 10, 10);
        contactTextView.setTextSize(14);
        contactTextView.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView dateTextView = new TextView(this);
        dateTextView.setText(date);
        dateTextView.setTextColor(Color.WHITE);
        dateTextView.setPadding(10, 10, 10, 10);
        dateTextView.setTextSize(14);
        dateTextView.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView thresholdTextView = new TextView(this);
        thresholdTextView.setText(threshold);
        thresholdTextView.setTextColor(Color.WHITE);
        thresholdTextView.setPadding(10, 10, 10, 10);
        thresholdTextView.setTextSize(14);
        thresholdTextView.setGravity(Gravity.CENTER_HORIZONTAL);

        mTablewRow.addView(idTextView);
        mTablewRow.addView(contactTextView);
        mTablewRow.addView(thresholdTextView);
        mTablewRow.addView(dateTextView);

        mTableLayout.addView(mTablewRow);
    }
}