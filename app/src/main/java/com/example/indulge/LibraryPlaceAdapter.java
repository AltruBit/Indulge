package com.example.indulge;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


// Restaurant adapter extending from RecyclerView.Adapter
public class LibraryPlaceAdapter extends RecyclerView.Adapter<LibraryPlaceAdapter.ViewHolder> {
    private static final String TAG = "LibraryPlaceAdapter";

    private final ArrayList<Meal> mealList;
    private final ArrayList<Place> placeList;
    private final Activity activity;

    //Constructor
    public LibraryPlaceAdapter(Activity activity, ArrayList<Place> placeList, ArrayList<Meal> mealList) {
        Log.i(TAG, "Constructor Called");

        this.activity = activity;
        this.placeList = placeList;
        this.mealList = mealList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView meal_rv;
        public CardView place_cv;
        public TextView placeName_tv;
        public TextView placeAddress_tv;
        public TextView expandImage_tv;
        public TextView menuHeader_tv;
        public TextView placeRating_tv;

        // Constructor
        public ViewHolder(View itemView) {
            super(itemView);

            Log.i(TAG, "ViewHolder");

            place_cv        = itemView.findViewById(R.id.cvPlace);
            placeName_tv    = itemView.findViewById(R.id.tvPlaceName);
            menuHeader_tv   = itemView.findViewById(R.id.tvMenuHeader);
            expandImage_tv  = itemView.findViewById(R.id.tvExpandImage);
            placeAddress_tv = itemView.findViewById(R.id.tvPlaceAddress);
            placeRating_tv  = itemView.findViewById(R.id.tvPlaceRating);
            meal_rv         = itemView.findViewById(R.id.rvMeal);

            menuHeader_tv.setVisibility(View.GONE);

            place_cv.setOnClickListener(v -> {
                Meal childData = mealList.get(getAdapterPosition());
                childData.setVisibility(!childData.getVisibility());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        Log.i(TAG, "onCreateViewHolder");

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recyclerview_library_list, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder");

        // Get the data model based on position
        Place placeData = placeList.get(position);

        ArrayList<Meal> newDishArrayList = new ArrayList<>();

        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getId().equals(placeData.getId()))
                newDishArrayList.add(mealList.get(i));
        }

        Meal libraryMealData = mealList.get(position);
        boolean isVisible = libraryMealData.getVisibility();

        String placeName = placeData.getName();
        String placeAddress = placeData.getAddressShortName();
        String placeRating = placeData.getPlaceRating().toString();

        holder.placeName_tv.setText(placeName);
        holder.placeRating_tv.setText(placeRating);
        holder.placeAddress_tv.setText(placeAddress);

        LibraryMealAdapter adapterMember = new LibraryMealAdapter(activity, placeData.getId(), newDishArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.menuHeader_tv.setVisibility(isVisible ? View.VISIBLE : View.GONE);

        holder.meal_rv.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        holder.meal_rv.setLayoutManager(linearLayoutManager);
        holder.meal_rv.setAdapter(adapterMember);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return placeList.size();
    }
}