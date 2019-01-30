package com.example.wvand.drinksdrunk;

import android.content.Context;
import android.database.Cursor;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class TrophyAdapter extends ResourceCursorAdapter {

    public TrophyAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Retrieving views to set them
        ImageView trophyImage = view.findViewById(R.id.trophyImage);
        TextView trophyName = view.findViewById(R.id.trophyName);

        // Get trophy name, image and boolean (to set black or white in image) from cursor
        String nameTrophy = cursor.getString(cursor.getColumnIndex("name"));
        int achieved = cursor.getInt(cursor.getColumnIndex("achieved"));

        // Set the views with retrieved data
        trophyName.setText(nameTrophy);

        // Set images to black and white if trophies aren't achieved
        if(achieved == 0) {

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            trophyImage.setColorFilter(filter);
        }

        switch (nameTrophy) {
            case "Sober session":

                trophyImage.setImageResource(R.drawable.sessionprize);
                break;

            case "Sober week":

                trophyImage.setImageResource(R.drawable.weekprize);
                break;

            case "Sober month":

                trophyImage.setImageResource(R.drawable.monthprize);
                break;

            case "Sober year":

                trophyImage.setImageResource(R.drawable.yearprize);
                break;

            case "Just one":

                trophyImage.setImageResource(R.drawable.onepersession);
                break;

            case "Three days":

                trophyImage.setImageResource(R.drawable.threedaysprice);
                break;
        }
    }
}
