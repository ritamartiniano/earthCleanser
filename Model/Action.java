package com.example.ritamartiniano.earthcleanser.Model;

public class Action {

    public String title, description,points;

    public Action(String title, String description, String points) {
        this.title = title;
        this.description = description;
        this.points = points;

    }
    public String getTitle()
    {
        return title;
    }
    public String getDescription()
    {
        return description;
    }
    public String getPoints()
    {
        return points;
    }
}
