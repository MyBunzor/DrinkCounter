package com.example.wvand.drinksdrunk;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TrophyAdapter extends ArrayAdapter<Trophy> {

    ArrayList<Trophy> trophies;
    DrinkDatabase db;


    public TrophyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Trophy> objects) {
        super(context, resource, objects);
        this.trophies = objects;
    }

    // Method that fills each griditem with a trophy
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }

        // Find the views in the grid_item xml to fill them
        ImageView trophyImage = convertView.findViewById(R.id.trophyImage);
        TextView trophyName = convertView.findViewById(R.id.trophyName);

        // Getting data from the trophies list
        int drawable = trophies.get(position).getDrawableId();
        String name = trophies.get(position).getName();
        boolean achieved = trophies.get(position).getAchieved();

        // Set the views with that data
        trophyImage.setImageResource(drawable);
        trophyName.setText(name);

        // Set images to black and white if trophies aren't achieved
        if(achieved == false) {

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            trophyImage.setColorFilter(filter);
        }

        return convertView;
    }
}
