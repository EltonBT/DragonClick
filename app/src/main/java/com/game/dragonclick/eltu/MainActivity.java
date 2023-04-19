package com.game.dragonclick.eltu;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Scanner;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton bt_start, bt_definition, bt_about, bt_end;
    MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Msc_on();

        bt_start = findViewById(R.id.bt_start);
        bt_definition = findViewById(R.id.bt_definition);
        bt_about = findViewById(R.id.bt_about);
        bt_end = findViewById(R.id.bt_end);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Partida.class);
                startActivity(intent);
            }
        });
        bt_definition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Definicoes.class);
                startActivity(intent);
            }
        });
        bt_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Sobre.class);
                startActivity(intent);
            }
        });
        bt_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });Definicoes mscTest = new Definicoes();
        mscTest.msc_ON=true;
    }public void Msc_on() {
        mediaPlayer = MediaPlayer.create(this, R.raw.backsound);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.start();
    }
    @Override
    protected void onDestroy () {
        super.onDestroy();
        // Libera o MediaPlayer ao sair do aplicativo
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}