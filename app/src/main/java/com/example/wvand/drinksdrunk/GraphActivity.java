package com.example.wvand.drinksdrunk;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GraphActivity extends AppCompatActivity {

    private DrinkDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Get intent with sessioninfo
        Intent getsessionData = getIntent();
        String startsession = (String) getsessionData.getSerializableExtra("sessionstart");
        String endsession = (String) getsessionData.getSerializableExtra("sessionend");

        // Get the database to get data
        db = DrinkDatabase.getInstance(getApplicationContext());

        // Use cursor and database to get amount of beers
        Cursor cursor = db.selectBeer();
        int count = cursor.getCount();
        System.out.println("count: "+ count);
    }
}
