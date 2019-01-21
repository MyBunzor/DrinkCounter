package com.example.wvand.drinksdrunk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

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

        // Creating trophy adapter
        TrophyAdapter adapter = new TrophyAdapter(this, R.layout.grid_item, cursorTrophy);

        // Set adapter on TrophyActivity
        GridView trophyGrid = findViewById(R.id.trophyGrid);
        trophyGrid.setAdapter(adapter);

        // Fill time variables, use them to check what was drunk in session
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
        }

        // Use cursor to find out what was drunk in past week
        Cursor weekCursor = db.selectWeek();

        // If nothing was drunk, set week trophy as achieved
        if (weekCursor.getCount() == 0) {

            System.out.println("First");

            db.trophyAchieved("Sober week");
        }

        // Use cursor to find out what was drunk in last month
        Cursor monthCursor = db.selectMonth();

        if (monthCursor.getCount() == 0) {
            db.trophyAchieved("Sober month");
        }

        // Use cursor to find out what was drunk in last year
        Cursor yearCursor = db.selectYear();

        if (yearCursor.getCount() == 0) {
            db.trophyAchieved("Sober year");
        }
    }
}
