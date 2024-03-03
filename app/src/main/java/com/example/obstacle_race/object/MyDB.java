package com.example.obstacle_race.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MyDB {

    private ArrayList<Player> players;
    private static MyDB myDB;


    private MyDB() {
        players = new ArrayList<>();

    }

    public static MyDB getInstance() {
        if(myDB==null){
            myDB=new MyDB();
        }
        return myDB;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
