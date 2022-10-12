package com.example.indulge;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserData implements ValueEventListener {
    private static final String TAG = "UserData";
    private static final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);

    private PlacesClient placesClient;
    private DatabaseReference userDR;

    private final ArrayList<Place> placeList;
    private final ArrayList<Meal> mealList;
//    private ArrayList<ValueEventListener> listeners;

    public UserData( Context context) {
//        this.listeners  = new ArrayList<>(listeners);
        placeList       = new ArrayList<>();
        mealList        = new ArrayList<>();
        placesClient    = Places.createClient(context);

        userDR = FirebaseDatabase.getInstance().getReference("Users");
    }

    public ArrayList<Place> getPlaces() {
        return placeList;
    }

    public ArrayList<Meal> getMeals() {
        return mealList;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        Log.i(TAG, "DataSnapShot Called");

        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            for (DataSnapshot dsPlace : dataSnapshot.child("User Places").getChildren())
            {
                String placeID = dsPlace.getKey();

                // TODO : Change assert to if null
                assert placeID != null;
                FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeID, placeFields);

                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    Place place = response.getPlace();
                    placeList.add(place);

                    Log.i(TAG, "Place found: " + place.getName());
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        final ApiException apiException = (ApiException) exception;
                        final int statusCode = apiException.getStatusCode();
                        // TODO: Handle error with given status code.
                        Log.e(TAG, "Place not found: " + statusCode);

                    }
                });

                for (DataSnapshot dsMeal : dsPlace.child("Meals").getChildren()) {
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

                    mealList.add(libraryMealData);
                }
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
