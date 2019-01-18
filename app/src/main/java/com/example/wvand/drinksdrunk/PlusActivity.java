package com.example.wvand.drinksdrunk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PlusActivity extends AppCompatActivity {

    DrinkDatabase db;
    String StoredStart, StoredEnd;
    Boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        // Retrieve database
        db = DrinkDatabase.getInstance(getApplicationContext());

        // Get switch button, so a session can be made; place listener on it
        Switch switchSession = findViewById(R.id.switchSession);
        switchSession.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Initialise editor to save timestamps later on
                SharedPreferences.Editor editor = getSharedPreferences("time", MODE_PRIVATE).edit();

                // When switch is in on Position, a session is created
                if (isChecked == true) {
                    check = isChecked;

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                    String sessionstart = simpleDateFormat.format(new java.util.Date());

                    // Use shared preferences' editor to keep time window for session
                    editor.putString("sessionstart", sessionstart);
                    editor.apply();

                    // Let user know the session started
                    Context context = getApplicationContext();
                    CharSequence text = "Session started!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                // If not, the session is (if it was there) ended
                else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                    String sessionend = simpleDateFormat.format(new java.util.Date());

                    editor.putString("sessionend", sessionend);
                    editor.apply();

                    // Let user know the session ended
                    Context context = getApplicationContext();
                    CharSequence text = "Session ended.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }

    // Method that's connected to the data-button
    public void toTime(View view) {
        Intent chooseTime = new Intent(PlusActivity.this, TimeActivity.class);

        // Retrieve start of session, with help of editor under key 'time'
        SharedPreferences startTime = getSharedPreferences("time", MODE_PRIVATE);
        StoredStart = startTime.getString("sessionstart", "0");

        // Retrieve end of session, with help of editor under key 'time'
        SharedPreferences endTime = getSharedPreferences("time", MODE_PRIVATE);
        StoredEnd = endTime.getString("sessionend", "0");

        // Put time variables in intent
        chooseTime.putExtra("sessionstart", StoredStart);
        chooseTime.putExtra("sessionend", StoredEnd);
        chooseTime.putExtra("switch", check);

        startActivity(chooseTime);
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
        Intent Trophy = new Intent(PlusActivity.this, TrophyActivity.class);
        Trophy.putExtra("sessionstart", StoredStart);
        Trophy.putExtra("sessionend", StoredEnd);
        Trophy.putExtra("switch", check);
        startActivity(Trophy);
    }

    // Method that's connected to the plus-buttons and adds a drink to the database
    public void addDrink(View view) {

        // Let user know a drink was added
        Context context = getApplicationContext();
        CharSequence text = "Drink added!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        // Find out which drink was plussed with view
        Button clicked = (Button) view;
        String olddrink = (String) clicked.getText();
        String kind = olddrink.replace("+ ", "");

        // Record moment of adding drink
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = simpleDateFormat.format(new java.util.Date());
        System.out.println("format: "+ format);

        // Create drink object with data above, then insert it in database
        Drink drink = new Drink(kind, format);

        db = DrinkDatabase.getInstance(getApplicationContext());
        db.insert(drink);

        // When user inputted a drink, disable buttons for 5 sec
        final Button beer = findViewById(R.id.beer);
        final Button wine = findViewById(R.id.wine);
        final Button mixed = findViewById(R.id.mixed);
        final Button liquor = findViewById(R.id.liquor);
        final Button craftbeer = findViewById(R.id.craftbeer);
        beer.setEnabled(false);
        wine.setEnabled(false);
        mixed.setEnabled(false);
        liquor.setEnabled(false);
        craftbeer.setEnabled(false);

        beer.postDelayed(new Runnable() {
            public void run() {
                beer.setEnabled(true);
            }
        }, 1*5*1000);

        wine.postDelayed(new Runnable() {
            public void run() {
                wine.setEnabled(true);
            }
        }, 1*5*1000);

        mixed.postDelayed(new Runnable() {
            public void run() {
                mixed.setEnabled(true);
            }
        }, 1*5*1000);

        liquor.postDelayed(new Runnable() {
            public void run() {
                liquor.setEnabled(true);
            }
        }, 1*5*1000);

        craftbeer.postDelayed(new Runnable() {
            public void run() {
                craftbeer.setEnabled(true);
            }
        }, 1*5*1000);
    }

    // Method that deletes last drink added
    public void undoDrink(View view) {

        // Call delete method from DrinkDatabase to undo last input
        db = DrinkDatabase.getInstance(getApplicationContext());
        db.delete();

        // Let user know that the last input is deleted
        Context context = getApplicationContext();
        CharSequence text = "Last drink removed!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    // Method that directs user to history activity
    public void toHistory(View view) {

        Intent seeHistory = new Intent(PlusActivity.this, HistoryActivity.class);
        startActivity(seeHistory);
    }
}
