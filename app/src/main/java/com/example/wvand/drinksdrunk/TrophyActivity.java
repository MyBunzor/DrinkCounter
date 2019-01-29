package com.example.wvand.drinksdrunk;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.icu.util.DateInterval;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.joda.time.DateTime;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import static android.database.DatabaseUtils.dumpCursorToString;
import static com.example.wvand.drinksdrunk.AppNotification.CHANNEL_1_ID;
import static com.example.wvand.drinksdrunk.PlusActivity.launchLong;

public class TrophyActivity extends AppCompatActivity {

    final static long THREEDAYS = 259200000;
    final static long WEEKDURATION = 604800000;
    final static long MONTHDURATION = (WEEKDURATION * 4) + (1000 * 60 * 60 * 24 * 2);
    final static long YEARDURATION = (MONTHDURATION * 12) + (1000 * 60 * 60 * 24 * 4);

    String sessionStart, sessionEnd;
    Boolean check;
    DrinkDatabase db;
    private NotificationManagerCompat notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy);

        // Get database to check data and create trophies
        db = DrinkDatabase.getInstance(getApplicationContext());

        // Fill instantiated notification manager to send notifications
        notificationManager = NotificationManagerCompat.from(this);

        // Check what trophies are achieved already
        Cursor controlSession = db.checkTrophies("Sober session");
        Cursor controlWeek = db.checkTrophies("Sober week");
        Cursor controlMonth = db.checkTrophies("Sober month");
        Cursor controlJustOne = db.checkTrophies("Just one");
        Cursor controlYear = db.checkTrophies("Sober year");
        Cursor controlDays = db.checkTrophies("Three days");

        // Fill time variables, use them to check what was drunk in time periods
        Intent fromPlus = getIntent();
        sessionStart = (String) fromPlus.getSerializableExtra("sessionstart");
        sessionEnd = (String) fromPlus.getSerializableExtra("sessionend");
        check = (Boolean) fromPlus.getSerializableExtra("switch");

        // Use first cursor to find out what was drunk in session, only if session is completed
        if (sessionEnd.length() > 1) {

            System.out.println("Session end isn't null");

            Cursor cursor = db.selectsoberSession(sessionStart, sessionEnd);

            // If no drinks were drunk and trophy wasn't achieved yet, set trophy as achieved
            if (cursor.getCount() == 0 && controlSession.getCount() == 0) {
                db.trophyAchieved("Sober session");

                // Notify user of won trophy
                Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.notificationglass)
                        .setContentTitle("Well done!")
                        .setContentText("You've won a trophy. Click to find out which!")
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();
                notificationManager.notify(1, notification);
            }

            // If just one drink was drunk and trophy wasn't achieved yet, set trophy achieved
            else if (cursor.getCount() == 1 && controlJustOne.getCount() == 0) {
                db.trophyAchieved("Just one");
            }
        }

        // If databae is emtpy, undo Just One trophy
        Cursor allDrinks = db.selectAll();
        System.out.println("CURSOR: " +DatabaseUtils.dumpCursorToString(allDrinks));
        if (allDrinks.getCount() == 0) {
            System.out.println("Testdeleting just one");
            db.justoneUndone();
        }

        // Get time in millis right now
        Calendar cal = Calendar.getInstance();
        long actual = cal.getTimeInMillis();

        // If app is launched longer than three days ago, find out how much was drunk in three days
        if (actual - launchLong > THREEDAYS) {

            // Use cursor to find out what was drunk in past three days
            Cursor threedaysCursor = db.selectThreeDays();

            // If nothing was drunk, set days trophy as achieved (if not already achieved)
            if (threedaysCursor.getCount() == 0 && controlDays.getCount() == 0) {
                db.trophyAchieved("Three days");
            }
        }

        // If app is launched longer than week ago, find out how much was drunk in week
        if (actual - launchLong > WEEKDURATION){

            // Use cursor to find out what was drunk in past week
            Cursor weekCursor = db.selectWeek();

            // If nothing was drunk, set week trophy as achieved (if not already achieved)
            if (weekCursor.getCount() == 0 && controlWeek.getCount() == 0) {
                db.trophyAchieved("Sober week");
            }
        }

        // If app is launched longer than a month ago, find out how much was drunk in month
        if ((actual - launchLong) > MONTHDURATION) {

            System.out.println("MONTH!");

            // Use cursor to find out what was drunk in last month
            Cursor monthCursor = db.selectMonth();

            if (monthCursor.getCount() == 0 && controlMonth.getCount() == 0) {
                db.trophyAchieved("Sober month");
            }
        }

        // If app is launched longer than a year ago, find out how much was drunk in year
        if ((actual - launchLong) > YEARDURATION) {

            // Use cursor to find out what was drunk in last year
            Cursor yearCursor = db.selectYear();

            if (yearCursor.getCount() == 0 && controlYear.getCount() == 0) {
                db.trophyAchieved("Sober year");
            }
        }

        // Creating cursor and trophy adapter
        Cursor updatedCursor = db.selectAllTrophies();
        System.out.println("TROPHIES: " +DatabaseUtils.dumpCursorToString(updatedCursor));
        TrophyAdapter adapter = new TrophyAdapter(this, R.layout.grid_item, updatedCursor);

        // Set adapter on TrophyActivity
        GridView trophyGrid = findViewById(R.id.trophyGrid);
        trophyGrid.setAdapter(adapter);
        db.close();
    }
}