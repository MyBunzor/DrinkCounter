package com.example.wvand.drinksdrunk;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.renderscript.ScriptGroup;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

// Source: https://www.youtube.com/watch?v=T3sb7VUpkeE
public class InputActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    EditText dateEdit;
    int day, month, year, day_x, month_x, year_x;
    String userDateTime;
    DrinkDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Use edit texts to let user know what time and date where chosen
        dateEdit = findViewById(R.id.dateEdit);

        // Set edit text default to current time, with right format
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;

        if (month < 10 && day < 10) {
            dateEdit.setText("0" + day + "-0" + month + "-" + year);
        }
        else if(month > 10 && day < 10) {
            dateEdit.setText("0" + day + "-" + month + "-" + year);
        }
        else if(month < 10 && day > 10) {
            dateEdit.setText(day + "-0" + month + "-" + year);
        }
    }

    // Disable the keyboard, source: https://www.youtube.com/watch?v=CW5Xekqfx3I
    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // Method that's connected to button and fires the date picker
    public void timeMethod(View view) {

        Calendar cal = Calendar.getInstance();

        // Set picker to real time
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(InputActivity.this,
                InputActivity.this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        year_x = year;
        month_x = month + 1;
        day_x = dayOfMonth;

        // Ensuring the right format for the database
        if (month_x < 10 && day_x < 10) {
            userDateTime = "0" + day_x + "-0" + month_x + "-" + year_x + "-13-37-37";
            dateEdit.setText("0" + day_x + "-0" + month_x + "-" + year_x);
        }
        else if(month_x > 10 && day_x < 10) {
            userDateTime = "0" + day_x + "-" + month_x + "-" + year_x + "-13-37-37";
            dateEdit.setText("0" + day_x + "-" + month_x + "-" + year_x);
        }
        else if(month_x < 10 && day_x > 10) {
            userDateTime = day_x + "-0" + month_x + "-" + year_x + "-13-37-37";
            dateEdit.setText(day_x + "-0" + month_x + "-" + year_x);
        }
        closeKeyboard();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    }

    public void addDrink(View view) {

        // Find out which drink was plussed with view
        ImageView clicked = (ImageView) view;
        String kind = (String) clicked.getContentDescription();

        // Create drink object with data above, then insert it in database
        if (userDateTime == null) {

            // Let user know to give date and time
            Context context = getApplicationContext();
            CharSequence text = "Please indicate time and date!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        else {

            // Instantiate the manually added drink and add to database
            Drink drink = new Drink(kind, userDateTime);
            db = DrinkDatabase.getInstance(getApplicationContext());
            db.insert(drink);

            // Let user know to give date and time
            Context context = getApplicationContext();
            CharSequence text = "Drink added!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
