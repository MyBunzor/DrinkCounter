package com.example.wvand.drinksdrunk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.time.Instant.MAX;

public class DrinkDatabase extends SQLiteOpenHelper {

    final private static String TAG = "DrinkDatabase";

    private DrinkDatabase(Context context, String name, int version) {
        super(context, name, null, version);
    }

    // Unique instance of the class DrinkDatabase: only 1 needed
    private static DrinkDatabase instance;

    // Check if a database was already made: if so, return it, otherwise, create one
    public static DrinkDatabase getInstance(Context context) {
        if(instance == null) {
            instance = new DrinkDatabase(context, "instance", 1);
            return instance;
        }
        else {
            return instance;
        }
    }

    // Creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Columns represent each field in Drink model class
        db.execSQL("create table drinks (_id INTEGER PRIMARY KEY AUTOINCREMENT, kind TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");

    }

    // Insert method is called when one of the drinks is plussed
    public void insert(Drink drink){

        // Get access to the database
        SQLiteDatabase writabledb = instance.getWritableDatabase();

        // Use ContentValues class to insert values in the table
        ContentValues contentValues = new ContentValues();

        // Get attributes of newly inputted drink object
        String kind = drink.getKind();
        String timestamp = drink.getTimestamp();
        System.out.println(kind + timestamp);

        // Add the above extracted values to ContentValues variable, which is placed in database
        contentValues.put("kind", kind);
        contentValues.put("timestamp", timestamp);
        writabledb.insert("drinks", null, contentValues);
    }

    // Method to delete last inputted drink
    public void delete() {

        // Get access to database to remove last input
        SQLiteDatabase deletabledb = instance.getWritableDatabase();

        // Remove the input with the highest number listed in column _id
        deletabledb.execSQL("DELETE FROM drinks WHERE _id IN (SELECT MAX(_id) FROM drinks LIMIT 1)");
    }

    // Use Cursor to get (specific) access to table
    public Cursor selectAll() {

        // Open up connection with the database
        SQLiteDatabase writabledb = instance.getWritableDatabase();

        // Create cursor variable, select everything and place it in cursor
        Cursor cursor = writabledb.rawQuery("SELECT * FROM drinks", null);

        return cursor;
    }

    // Use cursor to get drinks in a session
    public Cursor selectSession(String starttime, String endtime, Boolean check) {

        // Open up connection with the database
        SQLiteDatabase beerdb = instance.getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String stringNow = simpleDateFormat.format(new java.util.Date());

        // Select all consumptions upward of starttime if session is live
        if(check == true) {
            System.out.println("Start: " + starttime + "Now: " + stringNow);
            Cursor cursor = beerdb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                    "'"+starttime+"' AND '"+stringNow+"'", null);
            return cursor;
        }

        // Otherwise, select all consumptions in session
        else {

            Cursor cursor = beerdb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                    "'" + starttime + "' AND '" + endtime + "'", null);
            return cursor;
        }
    }

    // Method that selects drinks of past 7 days
    public Cursor selectWeek(){

        // Open up connection with the database
        SQLiteDatabase weekdb = instance.getWritableDatabase();

        // Get today's date..
        Calendar cal = Calendar.getInstance();
        long actual = cal.getTimeInMillis();
        Date datenow = new Date(actual);

        // ..and date from 7 days ago
        cal.add(Calendar.DAY_OF_MONTH, -7);
        long week = cal.getTimeInMillis();
        Date lastweek = new Date(week);

        // Format to SimpleDateFormat to work with SQLite
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String now = simpleDateFormat.format(datenow);
        String lastWeek = simpleDateFormat.format(lastweek);

        // Get cursor to select week period
        Cursor weekCursor = weekdb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                "'" + lastWeek + "' AND '"+ now + "'",null);

        return weekCursor;
    }

    // Method that selects drinks from last month
    public Cursor selectMonth() {

        // Open up connection with the database
        SQLiteDatabase monthdb = instance.getWritableDatabase();

        Cursor monthCursor = monthdb.rawQuery("SELECT * FROM drinks WHERE timestamp >= " +
                "date('now', 'start of month', '-1 month')", null);

        return monthCursor;
    }

    // Method that selects drinks from this year
    public Cursor selectYear() {

        // Open up connection with the database
        SQLiteDatabase monthdb = instance.getWritableDatabase();

        Cursor cursor = monthdb.rawQuery("SELECT * FROM drinks WHERE timestamp <= " +
                "date('now', 'start of year')", null);

        return cursor;
    }

    // Test method
    public Cursor selectsessionKind(String starttime, String endtime, Boolean check) {

        // Open up connection with the database
        SQLiteDatabase kinddb = instance.getReadableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String stringNow = simpleDateFormat.format(new java.util.Date());

        // Select all beers drunk within current session
        if(check == true) {
            Cursor cursor = kinddb.rawQuery("SELECT * FROM drinks WHERE kind == 'beer' AND " +
                    "timestamp BETWEEN '"+starttime+"' AND '"+stringNow+"' ", null);

            return cursor;
        }

        // Select all beers drunk within last session
        else {
            Cursor cursor = kinddb.rawQuery("SELECT * FROM drinks WHERE kind == 'beer' AND " +
                    "timestamp BETWEEN '"+starttime+"' AND '"+endtime+"' ", null);
            return cursor;
        }
    }

    // onUpgrade enables dropping or recreating the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "drinks");
        onCreate(db);
    }
}
