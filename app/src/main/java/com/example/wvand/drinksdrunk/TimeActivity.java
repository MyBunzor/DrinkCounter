package com.example.wvand.drinksdrunk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
    }

    // Method that selects specific data from table
    public void sessionPeriod(View view) {

        // TO DO: pass specific data with intent
        Intent session = new Intent(TimeActivity.this, GraphActivity.class);
        startActivity(session);
    }

    // Method that selects last week from table
    public void weekPeriod(View view) {

        // TO DO: pass specific data with intent
        Intent week = new Intent(TimeActivity.this, GraphActivity.class);
        startActivity(week);
    }

    // Method that selects last month from table
    public void monthPeriod(View view) {

        // TO DO: pass specific data with intent
        Intent month = new Intent(TimeActivity.this, GraphActivity.class);
        startActivity(month);
    }

    // Method that selects last year from table
    public void yearPeriod(View view) {

        // TO DO: pass specific data with intent
        Intent year = new Intent(TimeActivity.this, GraphActivity.class);
        startActivity(year);
    }
}
