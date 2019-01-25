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

import static android.database.DatabaseUtils.dumpCursorToString;
import static java.time.Instant.MAX;

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

        // Database with trophies, instantiate them immediately
        db.execSQL("create table trophies (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, " +
                "description TEXT, timestamp DATETIME, drawableId INTEGER, achieved INTEGER)");

        String trophySessionName = "Sober session";
        String trophySessionDescription = "You've had a session without drinking alcohol!";
        int trophySessionInt = 0;
        String trophyWeekName = "Sober week";
        String trophyWeekDescription = "You've had a week without drinking alcohol!";
        int trophyWeekInt = 0;
        String trophyMonthName = "Sober month";
        String trophyMonthDescription = "You've had a month without drinking alcohol!";
        int trophyMonthInt = 0;
        String trophyYearName = "Sober year";
        String trophyYearDescription = "Wow! You've made it a full year without drinking alcohol!";
        int trophyYearInt = 0;
        String trophyOneSessionName = "Just one";
        String trophyOneSessionDescription = "You've had a session with just one alcoholic drink!";
        int trophyOneSessionInt = 0;
        String threeDaysName = "Three days";
        String threeDaysDescription = "You've gone three days without drinking alcohol!";
        int threeDaysInt = 0;

        int drawableSessionID = context.getResources().getIdentifier("sessionprize",
                "drawable", context.getPackageName());
        int drawableWeekID = context.getResources().getIdentifier("weekprize",
                "drawable", context.getPackageName());
        int drawableMonthID = context.getResources().getIdentifier("monthprize",
                "drawable", context.getPackageName());
        int drawableYearID = context.getResources().getIdentifier("yearprize",
                "drawable", context.getPackageName());
        int drawableOneID = context.getResources().getIdentifier("onepersession",
                "drawable", context.getPackageName());
        int drawableThreeID = context.getResources().getIdentifier("threedaysprice",
                "drawable", context.getPackageName());

        // Use content values to put trophy data in the database
        ContentValues contentValuesSession = new ContentValues();
        ContentValues contentValuesWeek = new ContentValues();
        ContentValues contentValuesMonth = new ContentValues();
        ContentValues contentValuesYear = new ContentValues();
        ContentValues contentValuesOne = new ContentValues();
        ContentValues contentValuesThree = new ContentValues();

        contentValuesSession.put("name", trophySessionName);
        contentValuesSession.put("description", trophySessionDescription);
        contentValuesSession.put("achieved", trophySessionInt);
        contentValuesSession.put("drawableId", drawableSessionID);

        db.insert("trophies", null, contentValuesSession);

        contentValuesWeek.put("name", trophyWeekName);
        contentValuesWeek.put("description", trophyWeekDescription);
        contentValuesWeek.put("achieved", trophyWeekInt);
        contentValuesWeek.put("drawableId", drawableWeekID);

        db.insert("trophies", null, contentValuesWeek);

        contentValuesMonth.put("name", trophyMonthName);
        contentValuesMonth.put("description", trophyMonthDescription);
        contentValuesMonth.put("achieved", trophyMonthInt);
        contentValuesMonth.put("drawableId", drawableMonthID);

        db.insert("trophies", null, contentValuesMonth);

        contentValuesYear.put("name", trophyYearName);
        contentValuesYear.put("description", trophyYearDescription);
        contentValuesYear.put("achieved", trophyYearInt);
        contentValuesYear.put("drawableId", drawableYearID);

        db.insert("trophies", null, contentValuesYear);

        contentValuesOne.put("name", trophyOneSessionName);
        contentValuesOne.put("description", trophyOneSessionDescription);
        contentValuesOne.put("achieved", trophyOneSessionInt);
        contentValuesOne.put("drawableId", drawableOneID);

        db.insertOrThrow("trophies", null, contentValuesOne);

        contentValuesThree.put("name", threeDaysName);
        contentValuesThree.put("description", threeDaysDescription);
        contentValuesThree.put("achieved", threeDaysInt);
        contentValuesThree.put("drawableId", drawableThreeID);

        db.insert("trophies", null, contentValuesThree);
    }

    // Use Cursor to get (specific) access to table
    public Cursor selectAll() {

        // Open up connection with the database
        SQLiteDatabase writabledb = instance.getWritableDatabase();

        // Create cursor variable, select everything and place it in cursor
        Cursor cursor = writabledb.rawQuery("SELECT * FROM drinks", null);

        return cursor;
    }

    // Use Cursor to get to trophy data
    public Cursor selectAllTrophies() {

        // Open up connection with the database
        SQLiteDatabase writabledb = instance.getWritableDatabase();

        // Create cursor variable, select everything and place it in cursor
        Cursor cursor = writabledb.rawQuery("SELECT * FROM trophies", null);

        return cursor;
    }

    // Method that sets trophy as achieved
    public void trophyAchieved(String trophyName){

        // Open up connection with the database
        SQLiteDatabase writabledb = instance.getWritableDatabase();

//        Cursor cursor = writabledb.rawQuery("UPDATE trophies SET achieved = 1 WHERE name == '"+trophyName+"'", null);
//
//        cursor.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put("achieved", 1);

        writabledb.update("trophies", contentValues, "name= ?", new String[]{trophyName});
    }

    // Method that sets Just One trophy back
    public void justoneUndone(String JustOne){

        // Open up connection with the database
        SQLiteDatabase writabledb = instance.getWritableDatabase();

        Cursor cursor = writabledb.rawQuery("UPDATE trophies SET achieved = 0 WHERE name == '"+JustOne+"'", null);

        cursor.close();
    }

    public Cursor checkTrophies(String trophyName) {

        // Open up connection with the database
        SQLiteDatabase writabledb = instance.getWritableDatabase();

        Cursor cursor = writabledb.rawQuery("SELECT achieved FROM trophies WHERE " +
                "name == '"+trophyName+"' AND achieved == 1", null);

        return cursor;
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

    // Use cursor to find out if nothing was drunk in session
    public Cursor selectsoberSession(String starttime, String endtime){

        // Open up connection with database
        SQLiteDatabase soberdb = instance.getWritableDatabase();

        Cursor cursor = soberdb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                "'" + starttime + "' AND '" + endtime + "'", null);

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
        Cursor weekCursor = weekdb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                "'" + lastWeek + "' AND '"+ now + "'",null);

        return weekCursor;
    }

    // Method that selects drinks of past 6 days
    public Cursor selectSixDays() {

        // Open up connection with the database
        SQLiteDatabase weekdb = instance.getWritableDatabase();

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
        Cursor weekCursor = weekdb.rawQuery("SELECT * FROM drinks WHERE timestamp BETWEEN " +
                "'" + daysSix + "' AND '" + now + "'", null);

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

    // Select all drinks of specified kind in a session
    public Cursor selectsessionKind(String starttime, String endtime, Boolean check, String kind) {

        // Open up connection with the database
        SQLiteDatabase kinddb = instance.getReadableDatabase();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String stringNow = simpleDateFormat.format(new java.util.Date());

        // Select all beers drunk within current session
        if(check == true) {
            Cursor cursor = kinddb.rawQuery("SELECT * FROM drinks WHERE kind == '"+ kind
                    + "' AND timestamp BETWEEN '"+starttime+"' AND '"+stringNow+"' ",
                    null);

            return cursor;
        }

        // Select all beers drunk within last session
        else {
            Cursor cursor = kinddb.rawQuery("SELECT * FROM drinks WHERE kind == '"+ kind +"' " +
                    "AND timestamp BETWEEN '"+starttime+"' AND '"+endtime+"' ", null);
            return cursor;
        }
    }

    // Select all drinks of specified kind in a week
    public Cursor selectkindWeek(String kind) {

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

        // Get cursor to select week period and kind of drink
        Cursor weekCursor = weekdb.rawQuery("SELECT * FROM drinks WHERE kind == '"+kind+"' " +
                "AND timestamp BETWEEN '" + lastWeek + "' AND '" + now + "'", null);

        return weekCursor;
    }

    // Select all drinks of specified kind in a month
    public Cursor selectkindMonth(String kind) {

        // Open up connection with the database
        SQLiteDatabase monthdb = instance.getWritableDatabase();

        Cursor monthCursor = monthdb.rawQuery("SELECT * FROM drinks WHERE kind == '"+kind+"' "+
                " AND timestamp >= date('now', 'start of month', '-1 month')", null);

        return monthCursor;
    }

    // Select all drinks of specified kind in a year
    public Cursor selectkindYear(String kind) {

        // Open up connection with the database
        SQLiteDatabase monthdb = instance.getWritableDatabase();

        Cursor cursor = monthdb.rawQuery("SELECT * FROM drinks WHERE kind == '"+ kind +"' " +
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