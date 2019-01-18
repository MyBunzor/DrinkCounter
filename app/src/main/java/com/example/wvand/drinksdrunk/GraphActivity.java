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
import com.anychart.core.ui.Title;
import com.anychart.core.ui.table.Column;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private DrinkDatabase db;

    String beer = "beer";
    String wine = "wine";
    String mixed = "mixed";
    String liquor = "liquor";
    String craftbeer = "craftbeer";

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
                Cursor kindbeerCursor = db.selectsessionKind(startsession, endsession, check, beer);
                Cursor kindwineCursor = db.selectsessionKind(startsession, endsession, check, wine);
                Cursor kindmixedCursor = db.selectsessionKind(startsession, endsession, check, mixed);
                Cursor kindliquorCursor = db.selectsessionKind(startsession, endsession, check, liquor);
                Cursor kindcraftbeerCursor = db.selectsessionKind(startsession, endsession, check, craftbeer);

                // Get count from cursors
                int beercount = kindbeerCursor.getCount();
                int winecount = kindwineCursor.getCount();
                int mixcount = kindmixedCursor.getCount();
                int liquorcount = kindliquorCursor.getCount();
                int craftcount = kindcraftbeerCursor.getCount();

                System.out.println("COUNT: "+ beercount + "_" + winecount + "_" +  mixcount + "_" +
                        liquorcount + "_" + craftcount);

                // Create column chart and list to put data in
                Cartesian column = AnyChart.column();
                List<DataEntry> data1 = new ArrayList<>();

                // Put amount of drinks drunk per kind in list
                data1.add(new ValueDataEntry("beer", beercount));
                data1.add(new ValueDataEntry("wine", winecount));
                data1.add(new ValueDataEntry("mixed", mixcount));
                data1.add(new ValueDataEntry("liquor", liquorcount));
                data1.add(new ValueDataEntry("craftbeer", craftcount));

                // Fetch datalist to the column
                column.data(data1);

                // Set column on the any chart view
                AnyChartView anyChartView = findViewById(R.id.any_chart_view);
                anyChartView.setChart(column);

                // Set title
                Title yTitle = column.title();
                yTitle.enabled(true);
                yTitle.text("Session consumption");

                // Set y-axis interval on 1 (input can't be a float)
                column.yScale().ticks().interval(1);

                break;

            case "week":

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks past week
                Cursor weekCursor = db.selectWeek();

                int weekcount = weekCursor.getCount();
                System.out.println("COUNT: "+ weekcount);

                Cursor weekbeerCursor = db.selectkindWeek(beer);
                Cursor weekwineCursor = db.selectkindWeek(wine);
                Cursor weekmixedCursor = db.selectkindWeek(mixed);
                Cursor weekliquorCursor = db.selectkindWeek(liquor);
                Cursor weekcraftbeerCursor = db.selectkindWeek(craftbeer);

                // Get count from cursors
                int beerweekCount = weekbeerCursor.getCount();
                int wineweekCount = weekwineCursor.getCount();
                int mixweekCount = weekmixedCursor.getCount();
                int liquorweekCount = weekliquorCursor.getCount();
                int craftweekCount = weekcraftbeerCursor.getCount();

                // Create column chart and list to put data in
                Cartesian columnWeek = AnyChart.column();
                List<DataEntry> dataWeek = new ArrayList<>();

                // Put amount of drinks drunk per kind in list
                dataWeek.add(new ValueDataEntry("beer", beerweekCount));
                dataWeek.add(new ValueDataEntry("wine", wineweekCount));
                dataWeek.add(new ValueDataEntry("mixed", mixweekCount));
                dataWeek.add(new ValueDataEntry("liquor", liquorweekCount));
                dataWeek.add(new ValueDataEntry("craftbeer", craftweekCount));

                // Fetch datalist to the column
                columnWeek.data(dataWeek);

                // Set column on the any chart view
                AnyChartView weekChart = findViewById(R.id.any_chart_view);
                weekChart.setChart(columnWeek);

                // Set title
                Title weekTitle = columnWeek.title();
                weekTitle.enabled(true);
                weekTitle.text("Weekly consumption");

                // Set y-axis interval on 1 (input can't be a float)
                columnWeek.yScale().ticks().interval(1);

                break;

            case "month":

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks past month

                Cursor monthCursor = db.selectMonth();

                int monthcount = monthCursor.getCount();
                System.out.println("MonthCOUNT: " + monthcount);

                Cursor monthbeerCursor = db.selectkindMonth(beer);
                Cursor monthwineCursor = db.selectkindMonth(wine);
                Cursor monthmixedCursor =db.selectkindMonth(mixed);
                Cursor monthliquorCursor =  db.selectkindMonth(liquor);
                Cursor monthcraftCursor = db.selectkindMonth(craftbeer);

                // Get count from cursors
                int beermonthCount = monthbeerCursor.getCount();
                int winemonthCount = monthwineCursor.getCount();
                int mixedmonthCount = monthmixedCursor.getCount();
                int liquormonthCount = monthliquorCursor.getCount();
                int craftmonthCount = monthcraftCursor.getCount();

                // Create column chart and list to put data in
                Cartesian columnMonth = AnyChart.column();
                List<DataEntry> dataMonth = new ArrayList<>();

                // Put amount of drinks drunk per kind in list
                dataMonth.add(new ValueDataEntry("beer", beermonthCount));
                dataMonth.add(new ValueDataEntry("wine", winemonthCount));
                dataMonth.add(new ValueDataEntry("mixed", mixedmonthCount));
                dataMonth.add(new ValueDataEntry("liquor", liquormonthCount));
                dataMonth.add(new ValueDataEntry("craftbeer", craftmonthCount));

                // Fetch datalist to the column
                columnMonth.data(dataMonth);

                // Set column on the any chart view
                AnyChartView monthChart = findViewById(R.id.any_chart_view);
                monthChart.setChart(columnMonth);

                // Set title
                Title monthTitle = columnMonth.title();
                monthTitle.enabled(true);
                monthTitle.text("Monthly consumption");

                // Set y-axis interval on 1 (input can't be a float)
                columnMonth.yScale().ticks().interval(1);

                break;

            case "year":

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks past month
                Cursor yearCursor = db.selectYear();

                int yearcount = yearCursor.getCount();
                System.out.println("YearCOUNT: " + yearcount);

                Cursor yearbeerCursor = db.selectkindYear(beer);
                Cursor yearwineCursor = db.selectkindYear(wine);
                Cursor yearmixedCursor = db.selectkindYear(mixed);
                Cursor yearliquorCursor = db.selectkindYear(liquor);
                Cursor yearcraftCursor = db.selectkindYear(craftbeer);

                // Get count from cursors
                int beeryearCount = yearbeerCursor.getCount();
                int wineyearCount = yearwineCursor.getCount();
                int mixedyearCount = yearmixedCursor.getCount();
                int liquoryearCount = yearliquorCursor.getCount();
                int craftyearCount = yearcraftCursor.getCount();

                // Create column chart and list to put data in
                Cartesian columnYear = AnyChart.column();
                List<DataEntry> dataYear = new ArrayList<>();

                // Put amount of drinks drunk per kind in list
                dataYear.add(new ValueDataEntry("beer", beeryearCount));
                dataYear.add(new ValueDataEntry("wine", wineyearCount));
                dataYear.add(new ValueDataEntry("mixed", mixedyearCount));
                dataYear.add(new ValueDataEntry("liquor", liquoryearCount));
                dataYear.add(new ValueDataEntry("craftbeer", craftyearCount));

                // Fetch datalist to the column
                columnYear.data(dataYear);

                // Set column on the any chart view
                AnyChartView yearChart = findViewById(R.id.any_chart_view);
                yearChart.setChart(columnYear);

                // Set title
                Title yearTitle = columnYear.title();
                yearTitle.enabled(true);
                yearTitle.text("Yearly consumption");

                // Set y-axis interval on 1 (input can't be a float)
                columnYear.yScale().ticks().interval(2);

                break;
        }
    }
}