package com.example.wvand.drinksdrunk;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.ui.LabelsFactory;
import com.anychart.core.ui.table.Column;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private DrinkDatabase db;

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

                // Get different kind of drinks drunk
                Cursor kindCursor = db.selectsessionKind(startsession, endsession, check);
                int beercount = kindCursor.getCount();
                System.out.println("BEERCOUNT: " + beercount);

                // Create column chart and list to put data in
                Cartesian column = AnyChart.column();
                List<DataEntry> data1 = new ArrayList<>();

                // Put amount of drinks drunk per kind in list
                data1.add(new ValueDataEntry("beer", beercount));

                // Fetch datalist to the column
                column.data(data1);

                // Set column on the any chart view
                AnyChartView anyChartView = findViewById(R.id.any_chart_view);
                anyChartView.setChart(column);

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