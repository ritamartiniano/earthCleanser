package com.example.ritamartiniano.earthcleanser.Model;

import android.graphics.Bitmap;
import android.provider.MediaStore;

public class MediaActions {
    public String title,description,sector;
    public String image;

    public MediaActions(String title,String description,String image)
    {
        this.title = title;
        this.description = description;
        this.image = image;
        this.sector = sector;
    }
    public MediaActions(){}

    public String getTitle(){

        return title;
    }

    public String getDescription()
    {
        return description;
    }
    public String getImage()
    {
        return image;
    }

    public String getSector()
    {
        return sector;
    }
}
