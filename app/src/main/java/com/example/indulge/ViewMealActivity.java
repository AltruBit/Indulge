package com.example.indulge;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ViewMealActivity extends AppCompatActivity {

    DatabaseReference placesDR;
    DatabaseReference userPlacesDR;

    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_view_meal_data);

        // Layout
        TextView place_tv = findViewById(R.id.tvPlace);
        TextView meal_tv = findViewById(R.id.tvMeal);
        TextView mealDescription_tv = findViewById(R.id.tvMealDescription);

        //Firebase
        placesDR = FirebaseDatabase.getInstance().getReference("Places");

        userPlacesDR = FirebaseDatabase.getInstance().getReference("Users");
        userPlacesDR = userPlacesDR.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                .getUid())
                .child("User Places");

        // Intent
        intent = getIntent();
        String intentPlaceID = intent.getStringExtra("placeID");
        String intentMeal = intent.getStringExtra("meal");

        placesDR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String placeName;
                String mealName = "";
                String mealDescription = "";

                for(DataSnapshot placeDS : dataSnapshot.getChildren()){
                    //user = singleSnapshot.getValue(User.class);
                    if (intentPlaceID.equals(placeDS.getKey())) {
                        placeName = placeDS.child("Place Name").getValue().toString();

                        for(DataSnapshot mealDS : placeDS.child("Meals").getChildren()){
                            if (intentMeal.equals(mealDS.getKey())) {
                                mealName  = mealDS .getKey();

                                if (mealDS.child("Description").getValue() != null)
                                    mealDescription = mealDS.child("Description").getValue().toString();
                            }
                        }

                        place_tv.setText(placeName);
                        meal_tv.setText(mealName);
                        mealDescription_tv.setText(mealDescription);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

        //place_tv.setText(placeName);
        //meal_tv.setText(intentMeal);
        //mealDescription_tv.setText(mealDescription);
    }
}