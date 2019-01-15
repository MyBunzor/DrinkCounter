package com.example.wvand.drinksdrunk;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private DrinkDatabase db;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Get intent and find out what data user clicked on
        Intent getTimeperiod = getIntent();
        String timePeriod = (String) getTimeperiod.getSerializableExtra("timePeriod");

        // Use switch statement to determine what data should be shown
        switch(timePeriod) {
            case "session":

                // Get intent with sessioninfo
                Intent getsessionData = getIntent();
                String startsession = (String) getsessionData.getSerializableExtra("sessionstart");
                String endsession = (String) getsessionData.getSerializableExtra("sessionend");
                Boolean check = (Boolean) getsessionData.getSerializableExtra("switch");

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks in session, pass timestamps along
                Cursor sessionCursor = db.selectSession(startsession, endsession, check);

                int sessioncount = sessionCursor.getCount();
                System.out.println("COUNT:" + sessioncount);

                barChart = findViewById(R.id.bargraph);

                ArrayList<BarEntry> barEntries = new ArrayList<>();
                barEntries.add(new BarEntry(44f,0));
                barEntries.add(new BarEntry(88f,1));
                barEntries.add(new BarEntry(12f,2));
                BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");

                ArrayList<String> theDates = new ArrayList<>();
                theDates.add("April");
                theDates.add("May");
                theDates.add("June");


                BarData theData = new BarData((IBarDataSet) theDates,barDataSet);
                barChart.setData(theData);
                barChart.setTouchEnabled(true);

                break;

            case "week":

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks past week
                Cursor weekCursor = db.selectWeek();

                int weekcount = weekCursor.getCount();

                System.out.println("COUNT: "+ weekcount);

                break;

            case "month":

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks past month

                Cursor monthCursor = db.selectMonth();

                int monthcount = monthCursor.getCount();

                System.out.println("MonthCOUNT: " + monthcount);

                break;

            case "year":

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks past month

                Cursor yearCursor = db.selectYear();

                int yearcount = yearCursor.getCount();

                System.out.println("YearCOUNT: " + yearcount);

                break;
        }
    }
}