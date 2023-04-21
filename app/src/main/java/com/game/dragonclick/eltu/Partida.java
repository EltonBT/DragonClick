package com.game.dragonclick.eltu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Scanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.dragonclick.eltu.DTO.PlayerScore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Partida extends AppCompatActivity {
    Scanner leia = new Scanner(System.in);
    CountDownTimer timer;
    MediaPlayer mediaPlayer;
    public int i = 0;
    public int tempoInicial = 10;
    boolean temporizadorIniciado = false;
    boolean contadorBloqueado = false;

    private DatabaseReference mDatabase;

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

                showDialog();
            }
        }.start();
        }
        temporizadorIniciado=true;
        i++;
        contadorTextView.setText("Contador:" + i);
    }

    public void showDialog() {
        LinearLayout lnPlayer = (LinearLayout) View.inflate(this, R.layout.inflate_player_dialog, null);
        EditText etPlayerName = (EditText) lnPlayer.findViewById(R.id.editTextPlayerName);

        AlertDialog.Builder builder = new AlertDialog.Builder(Partida.this);
        builder.setMessage("Adicione seu nome")
                .setPositiveButton("Confirmar", (dialog, id) -> {
                    String namePlayer = etPlayerName.getText().toString().isEmpty() ?
                            "Não identificado" : etPlayerName.getText().toString();
                    int score = i;
                    Double media = ((double) i / tempoInicial);

                    int tempo = tempoInicial;

                    saveToDB(new PlayerScore(namePlayer, score, media, tempo));
                })
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    // User cancelled the dialog
                })
                .setView(lnPlayer);
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void saveToDB(PlayerScore playerScore){
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("players").child("1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("GAME_LOG", "Error getting data", task.getException());

                    mDatabase.child("players").child("1").setValue(playerScore);
                }
                else {
                    try{
                        Log.d("GAME_LOG", String.valueOf(task.getResult().getValue()));

                        PlayerScore savedPlayerScore = task.getResult().getValue(PlayerScore.class);
                        assert savedPlayerScore != null;
                        Log.i("GAME_LOG", savedPlayerScore.name);

                        if(playerScore.media > savedPlayerScore.media){
                            mDatabase.child("players").child("1").setValue(playerScore);
                        }
                    }catch (Exception e){
                        mDatabase.child("players").child("1").setValue(playerScore);
                    }

                }
            }
        });
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