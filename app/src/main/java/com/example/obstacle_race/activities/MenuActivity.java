package com.example.obstacle_race.activities;

import android.content.Intent;
import android.os.Bundle;
//import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.obstacle_race.GameManager;
import com.example.obstacle_race.R;
import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {
    AppCompatImageView menu_IMG_background;
    private MaterialButton menu_BTN_Sensor_game;
    private MaterialButton menu_BTN_fast_buttons_game;
    private MaterialButton menu_BTN_slow_buttons_game;
    private MaterialButton menu_BTN_records_list;


    private GameManager game_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        game_manager = new GameManager();

        findViews();
        InitBtnClick();
         initViews();


    }
    private void InitBtnClick() {
        menu_BTN_fast_buttons_game.setOnClickListener(view -> startGame("fast"));
        menu_BTN_slow_buttons_game.setOnClickListener(view -> startGame("slow"));
        menu_BTN_Sensor_game.setOnClickListener(view -> startGame("sensors"));
        menu_BTN_records_list.setOnClickListener(view -> showRecordsActivity());

    }

    private void showRecordsActivity() {
        Intent intent = new Intent(this, TopTenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void startGame(String gameMode) {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("gameMode", gameMode);
        intent.putExtra("Bundle", bundle);
        startActivity(intent);
        finish();
    }



    private void initViews() {
        Glide.with(this)
                .load(R.drawable.ic_road_background)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(menu_IMG_background);
    }


    private void findViews() {
        menu_IMG_background = findViewById(R.id.menu_IMG_background);
        menu_BTN_Sensor_game = findViewById(R.id.menu_BTN_sensor_game);
        menu_BTN_fast_buttons_game = findViewById(R.id.menu_BTN_fast_buttons_game);
        menu_BTN_slow_buttons_game = findViewById(R.id.menu_BTN_slow_buttons_game);
        menu_BTN_records_list = findViewById(R.id.menu_BTN_Records_list);
    }

}



