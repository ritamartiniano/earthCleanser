package com.example.ritamartiniano.earthcleanser.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ritamartiniano.earthcleanser.R;

public class Transportation extends Action{
    Bitmap icon;
    public Transportation(String title, String description, int points) {
        super(title, description, points);
        icon = BitmapFactory.decodeResource(null,R.drawable.ic_transportation);
    }

    public Bitmap getIcon()
    {
        return icon;
    }
}
