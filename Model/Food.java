package com.example.ritamartiniano.earthcleanser.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ritamartiniano.earthcleanser.R;

public class Food extends Action {
    public Bitmap icon;
    public Food(String title, String description, int points) {

        super(title, description, points);
        icon = BitmapFactory.decodeResource(null, R.drawable.ic_food);
    }

    @Override
    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
