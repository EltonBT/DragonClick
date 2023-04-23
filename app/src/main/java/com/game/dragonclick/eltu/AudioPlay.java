package com.game.dragonclick.eltu;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class AudioPlay {
    public static MediaPlayer mediaPlayer;
    public static boolean isPlaying;

    public void play(Context context){
        mediaPlayer = MediaPlayer.create(context, R.raw.backsound);
        new SoundPool(4, AudioManager.STREAM_MUSIC, 100);

        if(!mediaPlayer.isPlaying()){
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public void stop(){
        isPlaying = false;
        mediaPlayer.stop();
    }

}
