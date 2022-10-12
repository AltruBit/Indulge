package com.example.indulge;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class ExploreFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        int i;

        for (i = 0; i <= 5; i++)
        {
            ImageView imageView = new ImageView(rootView.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 30, 0);
            params.gravity = Gravity.CENTER;
            imageView.setLayoutParams(params);
            imageView.setBackgroundColor(Color.rgb(0, 150, 136));
            imageView.setImageResource(R.drawable.explore_foreground);
            ((ViewGroup) rootView.findViewById(R.id.exploreLinLayout)).addView(imageView);
        }

        // Inflate the layout for this fragment
        return rootView;
    }
}