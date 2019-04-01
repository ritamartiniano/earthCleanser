package com.example.ritamartiniano.earthcleanser.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ritamartiniano.earthcleanser.R;

public class Energy extends Action {

    public Bitmap icon;
    public Energy(String title, String description, int points) {
        super(title, description, points);
        icon = BitmapFactory.decodeResource(null,R.drawable.ic_energy);
    }
}
