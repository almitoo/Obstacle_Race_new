package com.example.obstacle_race.object;

import android.content.ContextWrapper;
import android.media.MediaPlayer;

public class Sound {

        private MediaPlayer mp;

        public Sound() {
            MediaPlayer mp = new MediaPlayer();
        }

        public void setMpAndPlay(ContextWrapper cw, int sample) {
            this.mp = MediaPlayer.create(cw,sample);
            this.mp.start();
        }
    }

