package com.example.obstacle_race.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.obstacle_race.R;
import com.example.obstacle_race.object.MyDB;
import com.example.obstacle_race.object.MySPv;
import com.example.obstacle_race.object.Player;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class GameOverActivity extends AppCompatActivity {
     View gameOver_IMG_background;
    private EditText gameOver_LBL_name;
    private MaterialButton gameOver_BTN_ok;

    private String playerName;
    private Bundle bundle;
    private int score;
    private MyDB myDB= MyDB.getInstance();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        if(getIntent().getBundleExtra("Bundle")!=null){
            this.bundle=getIntent().getBundleExtra("Bundle");
            score=bundle.getInt("score");
        }else{
            this.bundle = new Bundle();
        }

        findViews();
        initViews();
        insertPlayerToMyDB();


        gameOver_BTN_ok.setOnClickListener(view -> showRecordsActivity("player_name"));

    }

    private void insertPlayerToMyDB() {
        playerName = gameOver_LBL_name.getEditableText().toString();
        Player p1 = new Player().setPlayerName(playerName).setScore(score);
        myDB.addPlayer(p1);
        String json = new Gson().toJson(myDB);
        System.out.println(json);
        MySPv.getInstance().putString("MY_DB",json);
    }

    private void initViews() {
        Glide.with(this)
                .load((R.drawable.ic_game_over))
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into((ImageView) gameOver_IMG_background);
    }

    private void showRecordsActivity(String playerName) {
        Intent intent = new Intent(this, TopTenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("playerName", playerName);
        bundle.putInt("score",score);
        intent.putExtra("Bundle", bundle);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        gameOver_IMG_background = findViewById(R.id.gameOver_IMG_background);
        gameOver_LBL_name = findViewById(R.id.gameOver_LBL_name);
        gameOver_BTN_ok = findViewById(R.id.gamr_Over_BTN_ok);

    }

}