package com.example.wvand.drinksdrunk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TimeActivity extends AppCompatActivity {

    // Initialise variable to ensure right data is shown in GraphActivity
    String timePeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
    }

    // Method that selects specific data from table
    public void sessionPeriod(View view) {

        timePeriod = "session";

        // Get intent with the start and end data of session, as well as session check
        Intent getsessionData = getIntent();
        String sessionstart = (String) getsessionData.getSerializableExtra("sessionstart");
        String sessionend = (String) getsessionData.getSerializableExtra("sessionend");
        Boolean check = (Boolean) getsessionData.getSerializableExtra("switch");

        // Start intent and pass given data above with it
        Intent session = new Intent(TimeActivity.this, GraphActivity.class);
        session.putExtra("sessionstart", sessionstart);
        session.putExtra("sessionend", sessionend);
        session.putExtra("switch", check);
        session.putExtra("timePeriod", timePeriod);
        startActivity(session);
    }

    // Method that selects last week from table
    public void weekPeriod(View view) {

        timePeriod = "week";

        // Guide user to data visualization of consumptions in a week
        Intent week = new Intent(TimeActivity.this, GraphActivity.class);
        week.putExtra("timePeriod", timePeriod);
        startActivity(week);
    }

    // Method that selects last month from table
    public void monthPeriod(View view) {

        timePeriod = "month";

        // TO DO: pass specific data with intent
        Intent month = new Intent(TimeActivity.this, GraphActivity.class);
        month.putExtra("timePeriod", timePeriod);
        startActivity(month);
    }

    // Method that selects last year from table
    public void yearPeriod(View view) {

        timePeriod = "year";

        // TO DO: pass specific data with intent
        Intent year = new Intent(TimeActivity.this, GraphActivity.class);
        year.putExtra("timePeriod", timePeriod);
        startActivity(year);
    }
}
