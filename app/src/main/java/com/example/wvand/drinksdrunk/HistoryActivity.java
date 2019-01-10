package com.example.wvand.drinksdrunk;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);

        // Get the listview to place the adapter on
        ListView drinkslist = findViewById(R.id.ListView);

        // Retrieve database
        DrinkDatabase db = DrinkDatabase.getInstance(getApplicationContext());

        // Get cursor with the data to put in the listview
        Cursor cursor = db.selectAll();

        // Set adapter to display data in the list-view
        DrinkAdapter adapter = new DrinkAdapter(this, R.layout.drink_row, cursor);
        drinkslist.setAdapter(adapter);
    }
}
