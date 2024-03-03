package com.example.obstacle_race.object;

import android.content.Context;
import android.content.SharedPreferences;

public class MySPv {
    private static final String DB_FILE = "DB_FILE";

    private static MySPv instance;
    private static SharedPreferences preferences;

    private MySPv(Context context){
        preferences = context.getSharedPreferences(DB_FILE,Context.MODE_PRIVATE);
    }

    public static MySPv initHelper(Context context) {
        if (instance == null) {
            instance = new MySPv(context);
        }
        return instance;
    }


    public static MySPv getInstance() {
        return instance;
    }


    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();

    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }
}
