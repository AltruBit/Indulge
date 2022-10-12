package com.example.indulge;

import android.annotation.SuppressLint;

@SuppressLint("ParcelCreator")
public class Meal extends Place {
    private final String mealName;
    private String mealComment;
    private double mealRating;
    private boolean visibility;

    public Meal(String placeID, String mealName)
    {
        super(placeID);
        this.mealName = mealName;
        this.visibility = false;
    }

    public void setComment(String mealComment)
    {
        this.mealComment = mealComment;
    }

    public void setRating(int mealRating)
    {
        this.mealRating = mealRating;
    }

    public void setVisibility(boolean visibility)
    {
        this.visibility = visibility;
    }

    public String getName()
    {
        return mealName;
    }

    public String getComment()
    {
        return mealComment;
    }

    public Double getPlaceRating()
    {
        return mealRating;
    }

    public boolean getVisibility()
    {
        return visibility;
    }
}