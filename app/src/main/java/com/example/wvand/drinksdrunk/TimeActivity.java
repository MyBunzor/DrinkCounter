package com.example.wvand.drinksdrunk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TimeActivity extends AppCompatActivity {

    // Initialise variable to ensure right data is shown in GraphActivity
    String timePeriod, StoredStart, StoredEnd;
    Boolean check;

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

    // Method that directs user to history activity
    public void toHistory(View view) {

        Intent seeHistory = new Intent(TimeActivity.this, HistoryActivity.class);
        startActivity(seeHistory);
    }

    // Method that selects last week from table
    public void weekPeriod(View view) {

        timePeriod = "week";

        // Guide user to data visualization of consumptions in a week
        Intent week = new Intent(TimeActivity.this, GraphActivity.class);
        week.putExtra("timePeriod", timePeriod);
        startActivity(week);
    }

    // Method that's connected to the trophies-button
    public void toTrophies(View view) {

        // Retrieve start of session, with help of editor under key 'time'
        SharedPreferences startTime = getSharedPreferences("time", MODE_PRIVATE);
        StoredStart = startTime.getString("sessionstart", "0");

        // Retrieve end of session, with help of editor under key 'time'
        SharedPreferences endTime = getSharedPreferences("time", MODE_PRIVATE);
        StoredEnd = endTime.getString("sessionend", "0");

        // Intent to activity with trophies
        Intent Trophy = new Intent(TimeActivity.this, TrophyActivity.class);
        Trophy.putExtra("sessionstart", StoredStart);
        Trophy.putExtra("sessionend", StoredEnd);
        Trophy.putExtra("switch", check);
        startActivity(Trophy);
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
