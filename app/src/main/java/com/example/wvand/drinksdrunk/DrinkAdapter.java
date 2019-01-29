package com.example.wvand.drinksdrunk;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class DrinkAdapter extends ResourceCursorAdapter {

    public DrinkAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c);
    }

    // Use Cursor class to retrieve data from database and set text-views with it
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Get text-views to set them later
        TextView kind = view.findViewById(R.id.textKind);
        TextView time = view.findViewById(R.id.textTime);

        // Retrieve data: kind and timestamp, set text-views
        String beerKind = cursor.getString(cursor.getColumnIndex("kind"));
        kind.setText(beerKind);
        String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));
        time.setText(timestamp);
    }
}
