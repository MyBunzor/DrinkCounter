package com.example.wvand.drinksdrunk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static java.time.Instant.MAX;

public class DrinkDatabase extends SQLiteOpenHelper {

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
            instance = new DrinkDatabase(context, "instance", 1);
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

    // Use cursor to get beer data
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

    public Cursor selectWeek(){

        // Open up connection with the database
        SQLiteDatabase weekdb = instance.getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String stringNow = simpleDateFormat.format(new java.util.Date());


        System.out.println("DATE: " + stringNow);

        // Get cursor to select week period
        Cursor weekCursor = weekdb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN" +
                " datetime('now', '-6days') AND datetime('now', 'localtime')", null);

        return weekCursor;
    }

    public Cursor selectMonth() {

        // Open up connection with the database
        SQLiteDatabase monthdb = instance.getWritableDatabase();

        Cursor monthCursor = monthdb.rawQuery("SELECT * FROM drinks WHERE strftime('%m', timestamp) = '01'", null);

        return monthCursor;
    }

    public Cursor selectYear() {

        // Open up connection with the database
        SQLiteDatabase monthdb = instance.getWritableDatabase();

        Cursor cursor = monthdb.rawQuery("SELECT * FROM drinks", null);

        return cursor;
    }

    // onUpgrade enables dropping or recreating the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "drinks");
        onCreate(db);
    }
}
