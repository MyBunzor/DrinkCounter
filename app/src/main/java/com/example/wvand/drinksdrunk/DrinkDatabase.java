package com.example.wvand.drinksdrunk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DrinkDatabase extends SQLiteOpenHelper {

    Context context;

    private DrinkDatabase(Context context, String name, int version) {
        super(context, name, null, version);

        this.context = context;
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
        db.execSQL("create table drinks (_id INTEGER PRIMARY KEY AUTOINCREMENT, kind TEXT, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");

        // Database with trophies, instantiate them immediately afterwards
        db.execSQL("create table trophies (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, " +
                "achieved INTEGER)");

        // Create names and achieved data for trohpies
        String trophySessionName = "Sober session";
        int trophySessionInt = 0;
        String trophyWeekName = "Sober week";
        int trophyWeekInt = 0;
        String trophyMonthName = "Sober month";
        int trophyMonthInt = 0;
        String trophyYearName = "Sober year";
        int trophyYearInt = 0;
        String trophyOneSessionName = "Just one";
        int trophyOneSessionInt = 0;
        String threeDaysName = "Three days";
        int threeDaysInt = 0;

        // Use content values to put trophy data in the database
        ContentValues contentValuesSession = new ContentValues();
        ContentValues contentValuesWeek = new ContentValues();
        ContentValues contentValuesMonth = new ContentValues();
        ContentValues contentValuesYear = new ContentValues();
        ContentValues contentValuesOne = new ContentValues();
        ContentValues contentValuesThree = new ContentValues();

        contentValuesSession.put("name", trophySessionName);
        contentValuesSession.put("achieved", trophySessionInt);
        db.insert("trophies", null, contentValuesSession);

        contentValuesWeek.put("name", trophyWeekName);
        contentValuesWeek.put("achieved", trophyWeekInt);
        db.insert("trophies", null, contentValuesWeek);

        contentValuesMonth.put("name", trophyMonthName);
        contentValuesMonth.put("achieved", trophyMonthInt);
        db.insert("trophies", null, contentValuesMonth);

        contentValuesYear.put("name", trophyYearName);
        contentValuesYear.put("achieved", trophyYearInt);
        db.insert("trophies", null, contentValuesYear);

        contentValuesOne.put("name", trophyOneSessionName);
        contentValuesOne.put("achieved", trophyOneSessionInt);
        db.insertOrThrow("trophies", null, contentValuesOne);

        contentValuesThree.put("name", threeDaysName);
        contentValuesThree.put("achieved", threeDaysInt);
        db.insert("trophies", null, contentValuesThree);
    }

    // Use Cursor to get (specific) access to table
    public Cursor selectAll() {

        // Open up connection with the database
        SQLiteDatabase writableDb = instance.getWritableDatabase();

        // Create cursor variable, select everything and place it in cursor
        Cursor cursor = writableDb.rawQuery("SELECT * FROM drinks", null);

        return cursor;
    }

    // Use Cursor to get to trophy data
    public Cursor selectAllTrophies() {

        // Open up connection with the database
        SQLiteDatabase writableDb = instance.getWritableDatabase();

        // Create cursor variable, select everything and place it in cursor
        Cursor cursor = writableDb.rawQuery("SELECT * FROM trophies", null);

        return cursor;
    }

    // Method that sets trophy as achieved
    public void trophyAchieved(String trophyName){

        // Open up connection with the database
        SQLiteDatabase writableDb = instance.getWritableDatabase();

        // Use ContentValues to alter the database
        ContentValues contentValues = new ContentValues();
        contentValues.put("achieved", 1);

        writableDb.update("trophies", contentValues, "name= ?", new String[]{trophyName});
    }

    // Method that sets trophy back to unachieved
    public void trophyUndone(String trophyName){

        // Open up connection with the database
        SQLiteDatabase writableDb = instance.getWritableDatabase();

        // Use ContentValues to alter the database
        ContentValues contentValues = new ContentValues();
        contentValues.put("achieved", 0);

        writableDb.update("trophies", contentValues, "name= ?", new String[]{trophyName});
    }

    // Method that checks if trophy is already achieved
    public Cursor checkTrophies(String trophyName) {

        // Open up connection with the database
        SQLiteDatabase writableDb = instance.getWritableDatabase();

        Cursor cursor = writableDb.rawQuery("SELECT achieved FROM trophies WHERE " +
                "name == '"+trophyName+"' AND achieved == 1", null);

        return cursor;
    }

    // Insert method is called when one of the drinks is plussed
    public void insert(Drink drink){

        // Get access to the database
        SQLiteDatabase writableDb = instance.getWritableDatabase();

        // Use ContentValues class to insert values in the table
        ContentValues contentValues = new ContentValues();

        // Get attributes of newly inputted drink object
        String kind = drink.getKind();
        String timestamp = drink.getTimestamp();

        // Add the above extracted values to ContentValues variable, which is placed in database
        contentValues.put("kind", kind);
        contentValues.put("timestamp", timestamp);
        writableDb.insert("drinks", null, contentValues);
    }

    // Method to delete last inputted drink
    public void delete() {

        // Get access to database to remove last input
        SQLiteDatabase deletableDb = instance.getWritableDatabase();

        // Remove the input with the highest number listed in column _id
        deletableDb.execSQL("DELETE FROM drinks WHERE _id IN (SELECT MAX(_id) FROM drinks LIMIT 1)");
    }

    // Use cursor to find out if nothing was drunk in session
    public Cursor selectsoberSession(String starttime, String endtime){

        // Open up connection with database
        SQLiteDatabase soberDb = instance.getWritableDatabase();

        Cursor cursor = soberDb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                "'" + starttime + "' AND '" + endtime + "'", null);

        return cursor;
    }

    // Use cursor to get drinks in a session
    public Cursor selectSession(String starttime, String endtime, Boolean check) {

        // Open up connection with the database
        SQLiteDatabase beerDb = instance.getWritableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String stringNow = simpleDateFormat.format(new java.util.Date());

        // Select all consumptions upward of starttime if session is live
        if(check == true) {
            Cursor cursor = beerDb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                    "'"+starttime+"' AND '"+stringNow+"'", null);
            return cursor;
        }

        // Otherwise, select all consumptions in session
        else {

            Cursor cursor = beerDb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                    "'" + starttime + "' AND '" + endtime + "'", null);
            return cursor;
        }
    }

    // Method that selects drinks of past 7 days
    public Cursor selectWeek(){

        // Open up connection with the database
        SQLiteDatabase weekDb = instance.getWritableDatabase();

        // Get today's date..
        Calendar cal = Calendar.getInstance();
        long actual = cal.getTimeInMillis();
        Date dateNow = new Date(actual);

        // ..and date from 7 days ago
        cal.add(Calendar.DAY_OF_MONTH, -7);
        long week = cal.getTimeInMillis();
        Date lastweek = new Date(week);

        // Format to SimpleDateFormat to work with SQLite
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String now = simpleDateFormat.format(dateNow);
        String lastWeek = simpleDateFormat.format(lastweek);

        // Get cursor to select week period
        Cursor weekCursor = weekDb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                "'" + lastWeek + "' AND '"+ now + "'",null);

        return weekCursor;
    }

    public Cursor selectFourHours() {

        // Open up connection with the database
        SQLiteDatabase weekDb = instance.getWritableDatabase();

        // Get today's date..
        Calendar cal = Calendar.getInstance();
        long actual = cal.getTimeInMillis();
        Date datenow = new Date(actual);

        cal.add(Calendar.HOUR_OF_DAY, -4);
        long hoursFour = cal.getTimeInMillis();
        Date fourHours = new Date(hoursFour);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String dateNow = simpleDateFormat.format(datenow);
        String quatroHours = simpleDateFormat.format(fourHours);

        // Get cursor to select week period
        Cursor cursor = weekDb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                "'" + quatroHours + "' AND '" + dateNow + "'", null);

        return cursor;
    }

    public Cursor selectThreeDays() {

        // Open up connection with the database
        SQLiteDatabase weekDb = instance.getWritableDatabase();

        // Get today's date..
        Calendar cal = Calendar.getInstance();
        long actual = cal.getTimeInMillis();
        Date datenow = new Date(actual);

        // ..and date from 3 days ago
        cal.add(Calendar.DAY_OF_MONTH, -3);
        long three = cal.getTimeInMillis();
        Date daysThree = new Date(three);

        // Format to SimpleDateFormat to work with SQLite
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String now = simpleDateFormat.format(datenow);
        String threeDays = simpleDateFormat.format(daysThree);

        // Get cursor to select week period
        Cursor weekCursor = weekDb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                "'" + threeDays + "' AND '" + now + "'", null);

        return weekCursor;
    }

    // Method that selects drinks of past 6 days
    public Cursor selectSixDays() {

        // Open up connection with the database
        SQLiteDatabase weekDb = instance.getWritableDatabase();

        // Get today's date..
        Calendar cal = Calendar.getInstance();
        long actual = cal.getTimeInMillis();
        Date datenow = new Date(actual);

        // ..and date from 7 days ago
        cal.add(Calendar.DAY_OF_MONTH, -6);
        long week = cal.getTimeInMillis();
        Date sixDays = new Date(week);

        // Format to SimpleDateFormat to work with SQLite
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String now = simpleDateFormat.format(datenow);
        String daysSix = simpleDateFormat.format(sixDays);

        // Get cursor to select week period
        Cursor weekCursor = weekDb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                "'" + daysSix + "' AND '" + now + "'", null);

        return weekCursor;
    }

    // Method that selects drinks from last month
    public Cursor selectMonth() {

        // Open up connection with the database
        SQLiteDatabase monthDb = instance.getWritableDatabase();

        Cursor monthCursor = monthDb.rawQuery("SELECT * FROM drinks WHERE timestamp >= " +
                "date('start of month', '-1 month') AND timestamp < " +
                "date('start of month')", null);

        return monthCursor;
    }

    // Method that selects drinks from this year
    public Cursor selectYear() {

        // Open up connection with the database
        SQLiteDatabase monthDb = instance.getWritableDatabase();

        Cursor cursor = monthDb.rawQuery("SELECT * FROM drinks WHERE timestamp <= " +
                "date('now', 'start of year')", null);

        return cursor;
    }

    // Select all drinks of specified kind in a session
    public Cursor selectsessionKind(String starttime, String endtime, Boolean check, String kind) {

        // Open up connection with the database
        SQLiteDatabase kindDb = instance.getReadableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String stringNow = simpleDateFormat.format(new java.util.Date());

        // Select all beers drunk within current session
        if(check == true) {
            Cursor cursor = kindDb.rawQuery("SELECT * FROM drinks WHERE kind == '"+ kind
                    + "' AND timestamp BETWEEN '"+starttime+"' AND '"+stringNow+"' ",
                    null);

            return cursor;
        }

        // Select all beers drunk within last session
        else {
            Cursor cursor = kindDb.rawQuery("SELECT * FROM drinks WHERE kind == '"+ kind +"' " +
                    "AND timestamp BETWEEN '"+starttime+"' AND '"+endtime+"' ", null);
            return cursor;
        }
    }

    // Select all drinks of specified kind in a week
    public Cursor selectkindWeek(String kind) {

        // Open up connection with the database
        SQLiteDatabase weekDb = instance.getWritableDatabase();

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

        // Get cursor to select week period and kind of drink
        Cursor weekCursor = weekDb.rawQuery("SELECT * FROM drinks WHERE kind == '"+kind+"' " +
                "AND timestamp BETWEEN '" + lastWeek + "' AND '" + now + "'", null);

        return weekCursor;
    }

    // Select all drinks of specified kind in a month
    public Cursor selectkindMonth(String kind) {

        // Open up connection with the database
        SQLiteDatabase monthDb = instance.getWritableDatabase();

        Cursor monthCursor = monthDb.rawQuery("SELECT * FROM drinks WHERE kind == '"+kind+"' "+
                " AND timestamp >= date('now', 'start of month', '-1 month')", null);

        return monthCursor;
    }

    // Select all drinks of specified kind in a year
    public Cursor selectkindYear(String kind) {

        // Open up connection with the database
        SQLiteDatabase monthDb = instance.getWritableDatabase();

        Cursor cursor = monthDb.rawQuery("SELECT * FROM drinks WHERE kind == '"+ kind +"' " +
                "AND timestamp <= date('now', 'start of year')", null);

        return cursor;
    }

    // onUpgrade enables dropping or recreating the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "drinks");
        onCreate(db);
    }
}