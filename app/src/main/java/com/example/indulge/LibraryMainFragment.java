package com.example.indulge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LibraryMainFragment extends Fragment {
    private static final String TAG = "LibraryMainFragment";

    LibraryPlaceAdapter placeAdapter;
    RecyclerView libraryRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "OnCreateView");

        FloatingActionButton addDishBtn;

        View rootView   = inflater.inflate(R.layout.fragment_library_main, container, false);
        String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        User user = new User(userID);

        libraryRV   = rootView.findViewById(R.id.rvLibrary);
        addDishBtn  = rootView.findViewById(R.id.btnOpenPopup);

        //Bottom Right Button (Add Dish)
        addDishBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SearchPlace.class);
            startActivity(intent);
        });

        // Load Places on Fragment
        placeAdapter = new LibraryPlaceAdapter(this.getActivity(), user.getUserPlaceList(), user.getUserMealList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        libraryRV.setLayoutManager(layoutManager);
        libraryRV.setAdapter(placeAdapter);

        return rootView;
    }
}