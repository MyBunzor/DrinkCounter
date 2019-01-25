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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class InputActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    Button button;
    TextView text;
    int day, month, year, hour, minute;
    int day_x, month_x, year_x, hour_x, minute_x;
    String userDateTime;
    DrinkDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Use button active methods below that let user pick time and date
        button = findViewById(R.id.buttonTime);
    }

    // Method that fires a datepicker
    public void timeMethod(View view) {

        Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(InputActivity.this, InputActivity.this, year, month, day);
        datePickerDialog.show();

    }

    // Method that fires a timepicker
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        year_x = year;
        month_x = month;
        day_x = dayOfMonth;

        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(InputActivity.this, InputActivity.this,
                hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        hour_x = hourOfDay;
        minute_x = minute;

        // Adjust month to right format
        month_x = month_x + 1;

        //if (hour_x == 10 ||11||12|| 13|| 14|| 15|| 16|| 17|| 18|| 19||20||21| 22|| 23)
        boolean isSingleDigit = (hour_x <= 9);

        if (isSingleDigit == true) {

            userDateTime = day_x + "-0" + month_x + "-" + year_x + "-0" + hour_x + "-" + minute_x + "-00";
        }

        else {

            // Fetch string from user
            userDateTime = day_x + "-0" + month_x + "-" + year_x + "-" + hour_x + "-" + minute_x + "-00";
        }
    }

    public void addDrink(View view) {

        // Find out which drink was plussed with view
        Button clicked = (Button) view;
        String olddrink = (String) clicked.getText();
        String kind = olddrink.replace("+ ", "");

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

        }
    }
}
