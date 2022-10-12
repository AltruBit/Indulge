package com.example.indulge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Dish Adapter extending from RecyclerView.Adapter
public class LibraryMealAdapter extends RecyclerView.Adapter<LibraryMealAdapter.ViewHolder> {
    private final Activity activity;
    private final ArrayList<Meal> mealArrayList;
    private final String placeID;

    //Constructor
    public LibraryMealAdapter(Activity activity, String placeID,  ArrayList<Meal> mealArrayList) {
        this.activity = activity;
        this.mealArrayList = mealArrayList;
        this.placeID = placeID;
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mealCard_cv;
        public RatingBar mealRating_rb;
        public TextView mealName_tv;
        public TextView mealComment_tv;

        //Constructor
        public ViewHolder(View itemView) {
            super(itemView);

            mealCard_cv = itemView.findViewById(R.id.cvMealCard);
            mealRating_rb = itemView.findViewById(R.id.rbMealRating);
            mealName_tv = itemView.findViewById(R.id.tvMealName);
            mealComment_tv = itemView.findViewById(R.id.tvMealComment);
        }
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.library_user_meal_list, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Meal UserMealData = mealArrayList.get(position);

        // Set item views
        holder.mealName_tv.setText(UserMealData.getName());
        holder.mealComment_tv.setText(UserMealData.getComment());
        holder.mealRating_rb.setRating(UserMealData.getPlaceRating().floatValue());

        holder.mealCard_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewMealActivity.class);

                intent.putExtra("placeID", placeID);
                intent.putExtra("meal", UserMealData.getName());

                activity.startActivity(intent);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mealArrayList.size();
    }
}