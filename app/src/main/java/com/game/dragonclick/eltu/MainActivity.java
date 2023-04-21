package com.game.dragonclick.eltu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.dragonclick.eltu.DTO.PlayerScore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    ImageButton bt_start, bt_definition, bt_about, bt_end;

    private DatabaseReference mDatabase;
    AudioPlay audioPlay = new AudioPlay();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        });

        if(getMusicConfig()){
            audioPlay.play(getApplicationContext());
        }

        loadBestPlayer();
    }

    @Override
    protected void onDestroy () {
        audioPlay.stop();

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        loadBestPlayer();

        super.onResume();
    }

    public void loadBestPlayer(){
        TextView textViewBestPlayer = findViewById(R.id.textViewBestPlayerName);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        try{
            mDatabase.child("players").child("1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("GAME_LOG", "Error getting data", task.getException());
                    }
                    else {
                        Log.d("GAME_LOG", String.valueOf(task.getResult().getValue()));

                        PlayerScore savedPlayerScore = task.getResult().getValue(PlayerScore.class);

                        if(savedPlayerScore != null){
                            textViewBestPlayer.setText(String.format("%s - %s", savedPlayerScore.name, savedPlayerScore.media));
                        }
                    }
                }
            });
        }
        catch (Exception ignored){}
    }

    public boolean getMusicConfig(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                Const.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(Const.MUSIC_PREFS, true);
    }
}