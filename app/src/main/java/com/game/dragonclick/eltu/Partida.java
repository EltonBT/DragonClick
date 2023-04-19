package com.game.dragonclick.eltu;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Scanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.game.dragonclick.eltu.*;
import com.game.dragonclick.eltu.R.id.*;

public class Partida extends AppCompatActivity {
    Scanner leia = new Scanner(System.in);
    CountDownTimer timer;
    MediaPlayer mediaPlayer;
    int i = 0;
    public int tempoInicial = 10;
    boolean temporizadorIniciado = false;
    boolean contadorBloqueado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        tempoInicial = getTimeConfig();

        ImageButton bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void addClick(View view){
        TextView temporizadorTextView = findViewById(R.id.temporizadorTextView);
        TextView contadorTextView = findViewById(R.id.contadorTextView);
        if(contadorBloqueado) return;
        if(!temporizadorIniciado){
        timer = new CountDownTimer(tempoInicial*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = (int) (millisUntilFinished / 1000);
                temporizadorTextView.setText(String.format("%02d:%02d",secondsLeft/60,secondsLeft%60));
            }
            @Override
            public void onFinish() {
                temporizadorTextView.setText("00:00");
                contadorBloqueado = true;
            }
        }.start();
        }
        temporizadorIniciado=true;
        i++;
        contadorTextView.setText("Contador:" + i);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            temporizadorIniciado=false;
        }
    }
    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public int getTimeConfig(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPref.getInt(Const.TIME_PREFS, 10);
    }
}