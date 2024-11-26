package com.example.demo.misc;

public class GameScore {
    private int score;
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
        this.score += increment;
    }
}
