package com.example.demo.misc;

public class GameScore {
    private int score;
    private int lastIncrement;

    public GameScore(int initialScore){
        this.score = initialScore;
    }
    public int getScore() {
        return score;
    }
    public void resetScore() {
        this.score = 0;
    }

    public void increaseScoreBy(int increment){
        this.lastIncrement = increment;
        this.score += increment;
    }

    public int getLastIncrement(){
        return lastIncrement;
    }
}
