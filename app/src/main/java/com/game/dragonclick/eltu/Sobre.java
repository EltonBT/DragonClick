package com.game.dragonclick.eltu;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Sobre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        ImageButton bt_back=findViewById(R.id.bt_back);
        bt_back.setOnClickListener(v -> finish());
    }
}