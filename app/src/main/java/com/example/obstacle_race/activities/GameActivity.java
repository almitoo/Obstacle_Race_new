package com.example.obstacle_race.activities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.obstacle_race.GameManager;
import com.example.obstacle_race.R;
import com.example.obstacle_race.object.MyDB;
import com.example.obstacle_race.object.Sound;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    AppCompatImageView game_IMG_background;
    private FloatingActionButton game_FAB_left;
    private FloatingActionButton game_FAB_right;
    private ShapeableImageView[] game_IMG_hearts;
    private ShapeableImageView[][] panelGame;
    private ShapeableImageView[] game_IMG_cars;
    private MaterialTextView main_LBL_score;

    private enum Element {
        empty(0), stone(R.drawable.ic_stone), coin(R.drawable.ic_coin);
        public final int imageId;

        private Element(int id) {
            this.imageId = id;
        }
    }
    private Element[][] elementsMatrix = new Element[4][5];

    //Sound
    private Sound sound;

    private String gameMode;
    private Random r;

    private int carLocation = 2;
    private int score =0;

    //Sensor
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    //Game manager
    private GameManager gameManager;

    private final MyDB myDB = MyDB.getInstance();

    //Timer
    Timer timer = new Timer();


    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_game);

        r = new Random();
        sound = new Sound();
        gameManager = new GameManager();

        if (getIntent().getBundleExtra("Bundle") != null) {
            this.bundle = getIntent().getBundleExtra("Bundle");
            gameMode = bundle.getString("gameMode");
        } else {
            this.bundle = new Bundle();
        }

        findViews();
       initBackground();

        if (gameMode.equals("fast") || gameMode.equals("slow")) {
            game_FAB_left.setVisibility(View.VISIBLE);
            game_FAB_right.setVisibility(View.VISIBLE);
            initButton();
        } else {
            game_FAB_left.setVisibility(View.INVISIBLE);
            game_FAB_right.setVisibility(View.INVISIBLE);
            initSensor();
        }

        refreshUI();

    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        initEventListener();
    }

    private void initEventListener(){
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                movePlayer(x);
            }

            private void movePlayer(float x) {
                if(x<-4){
                    game_IMG_cars[carLocation].setVisibility(View.INVISIBLE);
                    carLocation =4;
                    game_IMG_cars[carLocation].setVisibility(View.VISIBLE);
                }else if(-4<x && x<-3.5){

                }
                else if(-3.5<x && x<-1.5){
                    game_IMG_cars[carLocation].setVisibility(View.INVISIBLE);
                    carLocation =3;
                    game_IMG_cars[carLocation].setVisibility(View.VISIBLE);
                }else if(x<-1.5 && x<-1){

                } else if(-1<x && x<1){
                    game_IMG_cars[carLocation].setVisibility(View.INVISIBLE);
                    carLocation =2;
                    game_IMG_cars[carLocation].setVisibility(View.VISIBLE);
                }else if(x<1 && x<1.5){

                }else if(1.5<x && x<3.5){
                    game_IMG_cars[carLocation].setVisibility(View.INVISIBLE);
                    carLocation =1;
                    game_IMG_cars[carLocation].setVisibility(View.VISIBLE);
                }else if(x<3.5 && x<4){

                }else if(4<x){
                    game_IMG_cars[carLocation].setVisibility(View.INVISIBLE);
                    carLocation =0;
                    game_IMG_cars[carLocation].setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

    }

    private void initButton() {
        game_FAB_left.setOnClickListener(view -> {
            clicked(true);
        });
        game_FAB_right.setOnClickListener(view -> {
            clicked(false);
        });
    }

    private void refreshUI() {
        if(gameMode.equals("fast") || gameMode.equals("sensor")){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                           tick();
                        checkCrash();
                        updateHeartsUi();
                        moveTheElements();
                    }
                });
            }
        }, 2000, 1000);
        }
       else {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tick();
                           checkCrash();
                           updateHeartsUi();
                            moveTheElements();
                       }
                    });
               }
            }, 3000, 3000);
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gameMode.equals("sensor")){
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gameMode.equals("sensor")) {
            sensorManager.unregisterListener(sensorEventListener,sensor);
        }
    }
    private void tick(){
        ++score;
        main_LBL_score.setText(" " + score);
    }

    private void checkCrash() {
        if (elementsMatrix[3][carLocation] == Element.stone) {
            if (panelGame[3][carLocation].getVisibility() == View.VISIBLE) {
                Toast.makeText(this,"BOOM",Toast.LENGTH_SHORT).show();
                sound.setMpAndPlay((ContextWrapper) getApplicationContext(),R.raw.sound_crash);
                gameManager.reduceLives();
            }
        }else if(elementsMatrix[3][carLocation] == Element.coin){
            if (panelGame[3][carLocation].getVisibility() == View.VISIBLE) {
                Toast.makeText(this,"+10",Toast.LENGTH_SHORT).show();
                sound.setMpAndPlay((ContextWrapper) getApplicationContext(), R.raw.sound_coin_crash);
                score = score +10;
                main_LBL_score.setText(" " + score);
            }
        }
    }

    private void moveTheElements() {
        for (int j = 0; j < 5; j++) {
            for (int i = 3; i > 0; i--) {
                panelGame[i][j].setImageResource(elementsMatrix[i - 1][j].imageId);
                panelGame[i][j].setVisibility(panelGame[i - 1][j].getVisibility());
                elementsMatrix[i][j] = elementsMatrix[i - 1][j];
            }
        }
        for (int i = 0; i < 5; i++) {
            panelGame[0][i].setVisibility(View.INVISIBLE);
            elementsMatrix[0][i] = Element.empty;
        }
        int columnIndex = r.nextInt(5);
        int elementIndex = r.nextInt(Element.values().length);
        Element chosenElement = Element.values()[elementIndex];
        panelGame[0][columnIndex].setImageResource(chosenElement.imageId);
        panelGame[0][columnIndex].setVisibility(View.VISIBLE);
        elementsMatrix[0][columnIndex] = chosenElement;

    }

    private void clicked(boolean isLeft) {
        game_IMG_cars[carLocation].setVisibility(View.INVISIBLE);
        if (isLeft && carLocation > 0) {
            carLocation--;

        } else if (!isLeft && carLocation < 4) {
            carLocation++;
        }
        game_IMG_cars[carLocation].setVisibility(View.VISIBLE);
    }

        private void initBackground() {
        Glide
                .with(this)
                .load(R.drawable.img_road)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(game_IMG_background);
    }

    private void findViews() {
        game_IMG_background = findViewById(R.id.game_IMG_background);
        game_FAB_left = findViewById(R.id.game_FAB_left);
        game_FAB_right = findViewById(R.id.game_FAB_right);
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3),
        };

        main_LBL_score = findViewById(R.id.main_LBL_score);
        panelGame = new ShapeableImageView[][]{
                {findViewById(R.id.game_IMG_0), findViewById(R.id.game_IMG_1), findViewById(R.id.game_IMG_2), findViewById(R.id.game_IMG_12), findViewById(R.id.game_IMG_17)},
                {findViewById(R.id.game_IMG_3), findViewById(R.id.game_IMG_4), findViewById(R.id.game_IMG_5), findViewById(R.id.game_IMG_13), findViewById(R.id.game_IMG_18)},
                {findViewById(R.id.game_IMG_6), findViewById(R.id.game_IMG_7), findViewById(R.id.game_IMG_8), findViewById(R.id.game_IMG_14), findViewById(R.id.game_IMG_19)},
                {findViewById(R.id.game_IMG_9), findViewById(R.id.game_IMG_10), findViewById(R.id.game_IMG_11), findViewById(R.id.game_IMG_15), findViewById(R.id.game_IMG_20)},
        };

        elementsMatrix = new Element[][]{
                {Element.empty, Element.empty, Element.empty, Element.empty, Element.empty},
                {Element.empty, Element.empty, Element.empty, Element.empty, Element.empty},
                {Element.empty, Element.empty, Element.empty, Element.empty, Element.empty},
                {Element.empty, Element.empty, Element.empty, Element.empty, Element.empty}
        };
        for (ShapeableImageView[] row : panelGame) {
            for (ShapeableImageView panel : row) {
                panel.setVisibility(View.INVISIBLE);
            }
        }
        game_IMG_cars = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_car1),
                findViewById(R.id.game_IMG_car2),
                findViewById(R.id.game_IMG_car3),
                findViewById(R.id.game_IMG_car4),
                findViewById(R.id.game_IMG_car5),

        };
        game_IMG_cars[0].setVisibility(View.INVISIBLE);
        game_IMG_cars[1].setVisibility(View.INVISIBLE);
        game_IMG_cars[3].setVisibility(View.INVISIBLE);
        game_IMG_cars[4].setVisibility(View.INVISIBLE);

    }

    private void updateHeartsUi() {
        if (gameManager.getLives() < game_IMG_hearts.length) {
            game_IMG_hearts[gameManager.getLives()].setVisibility(View.INVISIBLE);
            if (gameManager.getLives() == 0) {
                showGameOverActivity();
            }
        }
    }

    private void showGameOverActivity() {
        Intent intent = new Intent(this, GameOverActivity.class);
        bundle.putInt("score", score);
        intent.putExtra("Bundle", bundle);
        startActivity(intent);
        finish();
    }
}




    

