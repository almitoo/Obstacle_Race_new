package com.example.obstacle_race;

import android.app.Application;

import com.example.obstacle_race.object.MySPv;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MySPv.initHelper(this);
    }
}
