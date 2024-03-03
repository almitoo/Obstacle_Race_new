package com.example.obstacle_race.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.obstacle_race.R;
import com.example.obstacle_race.object.MyDB;
import com.example.obstacle_race.object.MySPv;
import com.example.obstacle_race.object.Player;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import interfaces.CallBackList;

public class ListFragment  extends Fragment {

    private MaterialButton[] topTen;
    private CallBackList callBackList;



    public void setActivity(AppCompatActivity activity){

    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_fragment, container, false);
        
        findViews(view);
        showTopTenPlayer();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void showTopTenPlayer() {
        String js = MySPv.getInstance().getString("MY_DB", "");
        MyDB myDB = new Gson().fromJson(js,MyDB.class);
        ArrayList<Player> players = myDB.getPlayers();
        if(players.size()<10){
            for(int i = 0; i< players.size(); i++){
                topTen[i].setVisibility(View.VISIBLE);
                topTen[i].setText(i+1+". "+ players.get(i).getPlayerName()+"\n  Score: "+ players.get(i).getScore());
            }
        }else{
            for(int i=0;i<10;i++){
                topTen[i].setVisibility(View.VISIBLE);
                topTen[i].setText(i+1+". "+ players.get(i).getPlayerName()+"\n  Score: "+ players.get(i).getScore());
            }
        }
    }

    public void setCallBackList(CallBackList callBackList){
        this.callBackList = callBackList;
    }


    private void findViews(View view) {
        topTen = new MaterialButton[]{
                view.findViewById(R.id.list_BTN_winner1),
                view.findViewById(R.id.list_BTN_winner2),
                view.findViewById(R.id.list_BTN_winner3),
                view.findViewById(R.id.list_BTN_winner4),
                view.findViewById(R.id.list_BTN_winner5),
                view.findViewById(R.id.list_BTN_winner6),
                view.findViewById(R.id.list_BTN_winner7),
                view.findViewById(R.id.list_BTN_winner8),
                view.findViewById(R.id.list_BTN_winner9),
                view.findViewById(R.id.list_BTN_winner10),
        };

    }



}
