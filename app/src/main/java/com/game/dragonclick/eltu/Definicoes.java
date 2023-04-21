package com.game.dragonclick.eltu;

import static com.game.dragonclick.eltu.R.id.bt_back;
import static com.game.dragonclick.eltu.R.id.tempoDeclarado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.Scanner;

import android.widget.Switch;
import android.widget.TextView;

public class Definicoes extends AppCompatActivity {
    public MediaPlayer mediaPlayer;
    EditText tempoDeclarado;
    TextView musica,tempo_total;
    Switch switchMsc;

    AudioPlay audioPlay = new AudioPlay();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definicoes);

        /*mediaPlayer = MediaPlayer.create(this, R.raw.backsound);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.start();*/

        tempoDeclarado = findViewById(R.id.tempoDeclarado);
        musica=findViewById(R.id.textView);
        tempo_total=findViewById(R.id.textView5);
        switchMsc=findViewById(R.id.switchMsc);

        tempoDeclarado.setText(String.valueOf(getTimeConfig()));

        switchMsc.setChecked(getMusicConfig());

        switchMsc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMusicConfig(isChecked);
                if(isChecked){
                    audioPlay.play(getApplicationContext());
                }else{
                    audioPlay.stop();
                }
            }
        });

        tempoDeclarado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTimeConfig(String.valueOf(s));
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ImageButton bt_back = findViewById(R.id.bt_back);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTimeConfig(String time) {
        time = time.isEmpty() ? "0" : time;
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Const.TIME_PREFS, Integer.parseInt(time));
        editor.apply();
    }

    public int getTimeConfig(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPref.getInt(Const.TIME_PREFS, 10);
    }

    public void setMusicConfig(boolean configMusic) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Const.MUSIC_PREFS, configMusic);
        editor.apply();
    }

    public boolean getMusicConfig(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(Const.MUSIC_PREFS, true);
    }
}
