package com.example.obstacle_race.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.obstacle_race.R;
import com.example.obstacle_race.fragments.ListFragment;
import com.example.obstacle_race.fragments.MapFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class TopTenActivity extends AppCompatActivity {

    private MapFragment mapFragment;
    private ListFragment listFragment;


    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);

        if (getIntent().getBundleExtra("Bundle") != null) {
            this.bundle = getIntent().getBundleExtra("Bundle");
        } else {
            this.bundle = new Bundle();
        }


        listFragment = new ListFragment();
        mapFragment = new MapFragment();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.top_ten_frame_map, mapFragment)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.top_ten_frame_list, listFragment)
                .commit();



        listFragment.setActivity(this);
        listFragment.setCallBackList((lat, lon, playerName) -> mapFragment.locateOnMap(lat, lon));


    }

}


