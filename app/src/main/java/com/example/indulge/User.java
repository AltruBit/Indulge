package com.example.indulge;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private static final String TAG = "User Class";

    private DatabaseReference userDR;
    private ArrayList<Place> userPlaceList;
    private ArrayList<Meal> userMealList;
    public String fullName, email;

    public User(String userID) {
        userDR          = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        userPlaceList   = new ArrayList<>();
        userMealList    = new ArrayList<>();

        FireBaseDataConnect();
    }

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void FireBaseDataConnect() {
        //Get Firebase User Place Data and Place Details
        userDR.addValueEventListener(new ValueEventListener() {

            //User DataSnapShop
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(TAG, "User DataSnapShot Called");

                for (DataSnapshot dsUserPlaces : snapshot.child("User Places").getChildren()) {
                    String placeID = dsUserPlaces.getKey();

                    if (placeID == null) return;

                    Place place = new Place(placeID);

                    userPlaceList.add(place);

                    //Meals DataSnapshot
                    for (DataSnapshot dsMeal : dsUserPlaces.child("Meals").getChildren()) {
                        String mealKey = dsMeal.getKey();
                        String mealComment = null;
                        int ratingValue;

                        // TODO - Needs to allow null
                        if (dsMeal.child("Comment").getValue() != null)
                            //noinspection ConstantConditions
                            mealComment = dsMeal.child("Comment").getValue().toString();

                        ratingValue = Integer.parseInt(Objects.requireNonNull(dsMeal.child("Rating").getValue()).toString());

                        Meal libraryMealData = new Meal(placeID, mealKey);
                        libraryMealData.setRating(ratingValue);
                        libraryMealData.setComment(mealComment);

                        userMealList.add(libraryMealData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public ArrayList<Place> getUserPlaceList() {
        return userPlaceList;
    }

    public ArrayList<Meal> getUserMealList() {
        return userMealList;
    }
}