package com.example.wvand.drinksdrunk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import static android.database.DatabaseUtils.dumpCursorToString;
import static com.example.wvand.drinksdrunk.PlusActivity.launchLong;

public class TrophyActivity extends AppCompatActivity {

    String sessionStart, sessionEnd;
    Boolean check;
    DrinkDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy);

        // Get database to check data and create trophies
        db = DrinkDatabase.getInstance(getApplicationContext());

        // Creating trophies
        Cursor cursorTrophy = db.selectAllTrophies();

        Log.d("Cursor", dumpCursorToString(cursorTrophy));

        // Fill time variables, use them to check what was drunk in time periods
        Intent fromPlus = getIntent();
        sessionStart = (String) fromPlus.getSerializableExtra("sessionstart");
        sessionEnd = (String) fromPlus.getSerializableExtra("sessionend");
        check = (Boolean) fromPlus.getSerializableExtra("switch");

        // Use cursor to find out what was drunk in session, only if session is completed
        if (sessionEnd != null) {
            Cursor cursor = db.selectsoberSession(sessionStart, sessionEnd);

            // If no drinks were drunk, set session trophy as achieved in database
            if (cursor.getCount() == 0) {

                db.trophyAchieved("Sober session");
            }

            // If just one drink was drunk, set Just One trophy as achieved
            else if (cursor.getCount() == 1) {

                System.out.println("Did you get here?");

                db.trophyAchieved("Just one");
            }
        }

        // Get time in millis right now
        Calendar cal = Calendar.getInstance();
        long actual = cal.getTimeInMillis();

        // Create long with amount of milliseconds in week
        long weekMillis = 1000 * 60 * 60 * 24 * 7;

        // If app is launched longer than week ago, find out how much was drunk in week
        if ((actual - launchLong) > weekMillis) {

            System.out.println("Got there!");
            // Use cursor to find out what was drunk in past week
            Cursor weekCursor = db.selectWeek();

            // If nothing was drunk, set week trophy as achieved
            if (weekCursor.getCount() == 0) {

                System.out.println("Weekcursor!");

                db.trophyAchieved("Sober week");
            }
        }

        // Create long with amount of milliseconds in month
        long monthMillis = weekMillis * 4;

        if ((actual - launchLong) > monthMillis) {

            // Use cursor to find out what was drunk in last month
            Cursor monthCursor = db.selectMonth();

            if (monthCursor.getCount() == 0) {
                db.trophyAchieved("Sober month");
            }
        }

        // Create long with amount of milliseconds in year
        long yearMillis = monthMillis * 52;

        if ((actual - launchLong) > yearMillis) {

            // Use cursor to find out what was drunk in last year
            Cursor yearCursor = db.selectYear();

            if (yearCursor.getCount() == 0) {
                db.trophyAchieved("Sober year");
            }
        }

        // Creating trophy adapter
        TrophyAdapter adapter = new TrophyAdapter(this, R.layout.grid_item, cursorTrophy);

        // Set adapter on TrophyActivity
        GridView trophyGrid = findViewById(R.id.trophyGrid);
        trophyGrid.setAdapter(adapter);
    }
}
