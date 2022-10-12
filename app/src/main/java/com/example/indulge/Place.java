package com.example.indulge;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

@SuppressLint("ParcelCreator")
public class Place {
    private static final String TAG = "Place Class";

    private final DatabaseReference placeDR;
    private final String placeID;
    private String placeName;
    private String placeCity;
    private String placeState;
    private Double placeRating;


    public Place(String placeID) {
        placeDR = FirebaseDatabase.getInstance().getReference("Places").child(placeID);
        this.placeID = placeID;

        FireBaseDataConnect();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void FireBaseDataConnect() {
        placeDR.addValueEventListener(new ValueEventListener() {
            //Place DataSnapSot
            @Override
            public void onDataChange(@NonNull DataSnapshot dsPlaceInfo) {
                Log.i(TAG, "Place DataSnapShot Called");

                dsPlaceInfo = dsPlaceInfo.child(placeID).child("Info");

                placeName   = Objects.requireNonNull(dsPlaceInfo.child("Place Name").getValue()).toString();
                placeCity   = Objects.requireNonNull(dsPlaceInfo.child("City").getValue()).toString();
                placeState  = Objects.requireNonNull(dsPlaceInfo.child("State").getValue()).toString();
                placeRating = (Double) dsPlaceInfo.child("Overall Rating").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setName(String name) {
        placeName = name;
    }

    public void setCity(String city) {
        placeCity = city;
    }

    public void setState(String state) {
        placeState = state;
    }

    public void setAddress(String city, String state) {
        setCity(city);
        setState(state);
    }

    public void setPlaceRating(Double placeRating) {
        this.placeRating = placeRating;
    }

    public String getId() {
        return placeID;
    }

    public String getName() {
        return placeName;
    }

    public String getCity() {
        return placeCity;
    }

    public String getState() {
        return placeState;
    }

    public Double getPlaceRating() { return placeRating; }

    public String getAddressShortName() {
        return getCity() + ", " + getState();
    }
}