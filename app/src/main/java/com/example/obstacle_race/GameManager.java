package com.example.obstacle_race;

public class GameManager {

    private int lives =3;
//    private int score =0;
    private int columns=5;
    private int rows=4;
    private String userName;

    public GameManager() {
    }

    public int getLives() {
        return lives;
    }

    public GameManager setLives(int lives) {
        this.lives = lives;
        return this;
    }

    public int getColumns() {
        return columns;
    }

    public GameManager setColumns(int columns) {
        this.columns = columns;
        return this;
    }

    public int getRows() {
        return rows;
    }

    public GameManager setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public GameManager setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public void reduceLives() {
       if( lives>0){
           setLives(lives-1);
       }
    }
}
