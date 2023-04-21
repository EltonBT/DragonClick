package com.game.dragonclick.eltu.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PlayerScore {
    public String name;
    public Integer score;
    public Double media;
    public Integer time;

    public PlayerScore() {
    }

    public PlayerScore(String name, Integer score, Double media, Integer time) {
        this.name = name;
        this.score = score;
        this.media = media;
        this.time = time;
    }
}
