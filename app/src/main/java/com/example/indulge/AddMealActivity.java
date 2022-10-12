package com.example.indulge;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class AddMealActivity extends AppCompatActivity {
    private static final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS, Place.Field.ADDRESS, Place.Field.RATING);

    DatabaseReference userPlacesDR;
    DatabaseReference placesDR;
    PlacesClient placesClient;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        // Layout
        EditText RestaurantName_et = findViewById(R.id.etRestaurantName);
        EditText DishName_et = findViewById(R.id.etDishName);
        EditText DishComment_et = findViewById(R.id.etDishComment);
        RatingBar dishRating_rb = findViewById(R.id.rbMealRating);

        // FireBase
        placesDR = FirebaseDatabase.getInstance().getReference("Places");
        userPlacesDR = FirebaseDatabase.getInstance().getReference("Users");

        //noinspection ConstantCon ditions
        userPlacesDR = userPlacesDR.child((FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("User Places");

        placesClient = Places.createClient(getApplicationContext());

        // Intent
        intent = getIntent();
        String intentPlaceName = intent.getStringExtra("PlaceName");
        String intentPlaceID = intent.getStringExtra("PlaceID");

        RestaurantName_et.setText(intentPlaceName);

        //noinspection Convert2Lambda
        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rDish = DishName_et.getText().toString().trim();
                String dComment = DishComment_et.getText().toString().trim();
                int numOfStars = (int) dishRating_rb.getRating();

                if (rDish.length() == 0) {
                    return;
                }

                FetchPlaceRequest request = FetchPlaceRequest.newInstance(intentPlaceID, placeFields);

                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    Place place = response.getPlace();
                    AddressComponents addressComponents = place.getAddressComponents();
                    String city;
                    String state;

                    placesDR.child(intentPlaceID).child("Info").child("Place Name").setValue(place.getName());
                    placesDR.child(intentPlaceID).child("Info").child("Overall Rating").setValue(place.getRating());
                    placesDR.child(intentPlaceID).child("Meals").child(rDish).setValue("");

                    userPlacesDR.child(intentPlaceID).child("Place Name").setValue(place.getName());

                    for (int i = 0; i < addressComponents.asList().size(); i++) {
                        AddressComponent addressComponents1 = addressComponents.asList().get(i);

                        if (addressComponents1.getTypes().get(0).equals("locality")
                                && addressComponents1.getTypes().get(1).equals("political")) {
                            city = addressComponents.asList().get(i).getName();
                            placesDR.child(intentPlaceID).child("Info").child("City").setValue(city);

                        }

                        if (addressComponents1.getTypes().get(0).equals("administrative_area_level_1")
                                && addressComponents1.getTypes().get(1).equals("political")) {
                            state = addressComponents.asList().get(i).getShortName();
                            placesDR.child(intentPlaceID).child("Info").child("State").setValue(state);

                        }
                    }

                    //locality, political
                    //administrative_area_level_1, political
                    Log.i(TAG, "Place found: " + place.getName());


                    //noinspection Convert2Lambda
                    userPlacesDR.child(intentPlaceID).child("Meals")
                            .child(rDish).child("Rating")
                            .setValue(numOfStars)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.i(TAG, "Restaurant and Meal added: " + intentPlaceName + ", " + rDish);
                                }
                            });

                    if (dComment.length() > 0) {

                        //noinspection Convert2Lambda
                        userPlacesDR.child(intentPlaceID).child("Meals")
                                .child(rDish).child("Comment")
                                .setValue(dComment)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.i(TAG, "Comment added: " + dComment);
                                    }
                                });
                    }

                    Toast endToast = Toast.makeText(AddMealActivity.this, "Done", Toast.LENGTH_SHORT);
                    endToast.show();

                    intent = new Intent(AddMealActivity.this, LibraryMainFragment.class);
                    startActivity(intent);

                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        final ApiException apiException = (ApiException) exception;
                        final int statusCode = apiException.getStatusCode();

                        // TODO: Handle error with given status code.
                        Log.e(TAG, "Place not found: " + statusCode);

                    }
                });
            }
        });
    }
}
