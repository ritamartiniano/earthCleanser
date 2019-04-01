package com.example.ritamartiniano.earthcleanser.Model;


import android.graphics.Bitmap;

public class Action {

    private String title, description;
    private int points;
    private Bitmap icon;
    public Action(String title, String description, int points) {
        this.title = title;
        this.description = description;
        this.points = points;
    }
    public Action(){}
    public String getTitle()
    {
        return title;
    }
    public String getDescription()
    {
        return description;
    }
    public int getPoints()
    {
        return points;
    }
    public void setIcon(Bitmap image){
      icon = image;
    }
    public Bitmap getIcon()
    {
        return icon;
    }
}
