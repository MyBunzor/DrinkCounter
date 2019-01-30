package com.example.wvand.drinksdrunk;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.ui.Title;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private DrinkDatabase db;

    String beer = "beer", wine = "wine", mixed = "mixed", liquor = "liquor", craftbeer =
            "craftbeer", cocktail = "cocktail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Get intent and find out what data user clicked on
        Intent getTimePeriod = getIntent();
        String timePeriod = (String) getTimePeriod.getSerializableExtra("timePeriod");

        // Use switch statement to determine what data should be shown
        switch(timePeriod) {
            case "session":

                // Get intent with session info
                Intent getSessionData = getIntent();
                String startSession = (String) getSessionData.getSerializableExtra("sessionstart");
                String endsession = (String) getSessionData.getSerializableExtra("sessionend");
                Boolean check = (Boolean) getSessionData.getSerializableExtra("switch");

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks in session, pass timestamps along
                Cursor sessionCursor = db.selectSession(startSession, endsession, check);

                // Get different kind of drinks drunk
                Cursor kindBeerCursor = db.selectsessionKind(startSession, endsession, check, beer);
                Cursor kindWineCursor = db.selectsessionKind(startSession, endsession, check, wine);
                Cursor kindMixedCursor = db.selectsessionKind(startSession, endsession, check, mixed);
                Cursor kindLiquorCursor = db.selectsessionKind(startSession, endsession, check, liquor);
                Cursor kindCraftBeerCursor = db.selectsessionKind(startSession, endsession, check, craftbeer);
                Cursor kindCocktailCursor = db.selectsessionKind(startSession, endsession, check, cocktail);

                // Get count from cursors
                int beerCount = kindBeerCursor.getCount();
                int wineCount = kindWineCursor.getCount();
                int mixCount = kindMixedCursor.getCount();
                int liquorCount = kindLiquorCursor.getCount();
                int craftCount = kindCraftBeerCursor.getCount();
                int cocktailCount = kindCocktailCursor.getCount();

                // Create column chart and list to put data in
                Cartesian column = AnyChart.column();
                List<DataEntry> data1 = new ArrayList<>();

                // Put amount of drinks drunk per kind in list
                data1.add(new ValueDataEntry("cocktail", cocktailCount));
                data1.add(new ValueDataEntry("beer", beerCount));
                data1.add(new ValueDataEntry("wine", wineCount));
                data1.add(new ValueDataEntry("mixed", mixCount));
                data1.add(new ValueDataEntry("liquor", liquorCount));
                data1.add(new ValueDataEntry("craftbeer", craftCount));

                // Fetch datalist to the column
                column.data(data1);

                // Set column on the any chart view
                AnyChartView anyChartView = findViewById(R.id.any_chart_view);
                anyChartView.setChart(column);

                // Set title
                Title yTitle = column.title();
                yTitle.enabled(true);
                yTitle.text("Session consumption");

                // Set y-axis interval on 1 (input can't be a float), make background transparent
                column.yScale().ticks().interval(1);
                column.background().fill("#B2EBF2");

                break;

            case "week":

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks past week
                Cursor weekCocktailCursor = db.selectkindWeek(cocktail);
                Cursor weekBeerCursor = db.selectkindWeek(beer);
                Cursor weekWineCursor = db.selectkindWeek(wine);
                Cursor weekMixedCursor = db.selectkindWeek(mixed);
                Cursor weekLiquorCursor = db.selectkindWeek(liquor);
                Cursor weekCraftBeerCursor = db.selectkindWeek(craftbeer);

                // Get count from cursors
                int beerWeekCount = weekBeerCursor.getCount();
                int wineWeekCount = weekWineCursor.getCount();
                int mixWeekCount = weekMixedCursor.getCount();
                int liquorWeekCount = weekLiquorCursor.getCount();
                int craftWeekCount = weekCraftBeerCursor.getCount();
                int cocktailWeekCount = weekCocktailCursor.getCount();

                // Create column chart and list to put data in
                Cartesian columnWeek = AnyChart.column();
                List<DataEntry> dataWeek = new ArrayList<>();

                // Put amount of drinks drunk per kind in list
                dataWeek.add(new ValueDataEntry("cocktail", cocktailWeekCount));
                dataWeek.add(new ValueDataEntry("beer", beerWeekCount));
                dataWeek.add(new ValueDataEntry("wine", wineWeekCount));
                dataWeek.add(new ValueDataEntry("mixed", mixWeekCount));
                dataWeek.add(new ValueDataEntry("liquor", liquorWeekCount));
                dataWeek.add(new ValueDataEntry("craftbeer", craftWeekCount));

                // Fetch datalist to the column
                columnWeek.data(dataWeek);

                // Set column on the any chart view
                AnyChartView weekChart = findViewById(R.id.any_chart_view);
                weekChart.setChart(columnWeek);

                // Set title
                Title weekTitle = columnWeek.title();
                weekTitle.enabled(true);
                weekTitle.text("Weekly consumption");

                // Set y-axis interval on 1 (input can't be a float), set background transparent
                columnWeek.yScale().ticks().interval(1);
                columnWeek.background().fill("#B2EBF2");

                break;

            case "month":

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks past month
                Cursor monthBeerCursor = db.selectkindMonth(beer);
                Cursor monthWineCursor = db.selectkindMonth(wine);
                Cursor monthMixedCursor =db.selectkindMonth(mixed);
                Cursor monthLiquorCursor =  db.selectkindMonth(liquor);
                Cursor monthCraftCursor = db.selectkindMonth(craftbeer);
                Cursor monthCocktailCursor = db.selectkindMonth(cocktail);

                // Get count from cursors
                int beerMonthCount = monthBeerCursor.getCount();
                int wineMonthCount = monthWineCursor.getCount();
                int mixedMonthCount = monthMixedCursor.getCount();
                int liquorMonthCount = monthLiquorCursor.getCount();
                int craftMonthCount = monthCraftCursor.getCount();
                int cocktailMonthCount = monthCocktailCursor.getCount();

                // Create column chart and list to put data in
                Cartesian columnMonth = AnyChart.column();
                List<DataEntry> dataMonth = new ArrayList<>();

                // Put amount of drinks drunk per kind in list
                dataMonth.add(new ValueDataEntry("cocktail", cocktailMonthCount));
                dataMonth.add(new ValueDataEntry("beer", beerMonthCount));
                dataMonth.add(new ValueDataEntry("wine", wineMonthCount));
                dataMonth.add(new ValueDataEntry("mixed", mixedMonthCount));
                dataMonth.add(new ValueDataEntry("liquor", liquorMonthCount));
                dataMonth.add(new ValueDataEntry("craftbeer", craftMonthCount));

                // Fetch datalist to the column
                columnMonth.data(dataMonth);

                // Set column on the any chart view
                AnyChartView monthChart = findViewById(R.id.any_chart_view);
                monthChart.setChart(columnMonth);

                // Set title
                Title monthTitle = columnMonth.title();
                monthTitle.enabled(true);
                monthTitle.text("Monthly consumption");

                Cursor monthCount = db.selectMonth();
                int monthInterval = (int) Math.ceil(monthCount.getCount() / 4);

                // Set y-axis interval on 1 (input can't be a float) and make background transparent
                columnMonth.yScale().ticks().interval(monthInterval);
                columnMonth.background().fill("#B2EBF2");

                break;

            case "year":

                // Get the database to get to data
                db = DrinkDatabase.getInstance(getApplicationContext());

                // Use cursor and database to get amount of drinks past month
                Cursor yearBeerCursor = db.selectkindYear(beer);
                Cursor yearWineCursor = db.selectkindYear(wine);
                Cursor yearMixedCursor = db.selectkindYear(mixed);
                Cursor yearLiquorCursor = db.selectkindYear(liquor);
                Cursor yearCraftCursor = db.selectkindYear(craftbeer);
                Cursor yearCocktailCursor = db.selectkindYear(cocktail);

                // Get count from cursors
                int beerYearCount = yearBeerCursor.getCount();
                int wineYearCount = yearWineCursor.getCount();
                int mixedYearCount = yearMixedCursor.getCount();
                int liquorYearCount = yearLiquorCursor.getCount();
                int craftYearCount = yearCraftCursor.getCount();
                int cocktailYearCount = yearCocktailCursor.getCount();

                // Create column chart and list to put data in
                Cartesian columnYear = AnyChart.column();
                List<DataEntry> dataYear = new ArrayList<>();

                // Put amount of drinks drunk per kind in list
                dataYear.add(new ValueDataEntry("Cocktail", cocktailYearCount));
                dataYear.add(new ValueDataEntry("beer", beerYearCount));
                dataYear.add(new ValueDataEntry("wine", wineYearCount));
                dataYear.add(new ValueDataEntry("mixed", mixedYearCount));
                dataYear.add(new ValueDataEntry("liquor", liquorYearCount));
                dataYear.add(new ValueDataEntry("craftbeer", craftYearCount));

                // Fetch datalist to the column
                columnYear.data(dataYear);

                // Set column on the any chart view
                AnyChartView yearChart = findViewById(R.id.any_chart_view);
                yearChart.setChart(columnYear);

                // Set title
                Title yearTitle = columnYear.title();
                yearTitle.enabled(true);
                yearTitle.text("Yearly consumption");

                // Set y-axis on a dynamic interval, background transparent
                Cursor yearCount = db.selectYear();
                int yearInterval = (int) Math.ceil(yearCount.getCount() / 10);
                columnYear.yScale().ticks().interval(yearInterval);
                columnYear.background().fill("#B2EBF2");

                break;
        }
    }
}