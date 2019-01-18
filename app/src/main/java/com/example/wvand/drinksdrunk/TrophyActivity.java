package com.example.wvand.drinksdrunk;

import android.content.Intent;
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

        // Creating list of trophies
        ArrayList<Trophy> trophies = new ArrayList<>();

        // Adding drawable id for trophies
        int drawableSessionID = this.getResources().getIdentifier("sessionprize",
                "drawable", this.getPackageName());
        int drawableWeekID = this.getResources().getIdentifier("weekprize",
                "drawable", this.getPackageName());
        int drawableMonthID = this.getResources().getIdentifier("monthprize",
                "drawable", this.getPackageName());
        int drawableYearID = this.getResources().getIdentifier("yearprize",
                "drawable", this.getPackageName());

        // Instantiate trophy objects
        Trophy sessionPrize = new Trophy("Sober session", "You've had a session " +
                "without drinking alcohol!", drawableSessionID, false);
        Trophy weekPrize = new Trophy("Sober week", "You've had a week without " +
                "drinking alcohol!", drawableWeekID, false);
        Trophy monthPrize = new Trophy("Sober month", "You've had a month without" +
                "drinking alcohol!", drawableMonthID, false);
        Trophy yearPrize = new Trophy("Sober year", " Wow! You've made it a full " +
                "year without drinking alcohol!", drawableYearID, false);



        // Adding objects to trophies list
        trophies.add(sessionPrize);
        trophies.add(weekPrize);
        trophies.add(monthPrize);
        trophies.add(yearPrize);

        // Creating trophy adapter
        TrophyAdapter adapter = new TrophyAdapter(this, R.layout.grid_item, trophies);

        // Set adapter on TrophyActivity
        GridView trophyGrid = findViewById(R.id.trophyGrid);
        trophyGrid.setAdapter(adapter);

        // Fill time variables, use them to check what was drunk in session
        Intent fromPlus = getIntent();
        sessionStart = (String) fromPlus.getSerializableExtra("sessionstart");
        sessionEnd = (String) fromPlus.getSerializableExtra("sessionend");
        check = (Boolean) fromPlus.getSerializableExtra("switch");

        // Get database to check data
        db = DrinkDatabase.getInstance(getApplicationContext());

        // Use cursor to find out what was drunk in session, only if session is completed
        if (sessionEnd != null) {
            Cursor cursor = db.selectsoberSession(sessionStart, sessionEnd);

            // If no drinks were drunk, set session trophy as achieved
            if (cursor.getCount() == 0) {
                System.out.println("Set it visible!");
                sessionPrize.setAchieved(true);

            }
        }
    }
}
