package com.example.demo.misc;

import com.example.demo.util.GameScore;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class GameScoreCounter {
    private Text gameScoreCounterText;
    private GameScore gameScore;
    public GameScoreCounter(double x, double y, int initGameScore) {
        this.gameScore = new GameScore(initGameScore);
        init(x, y);
    }

    public GameScoreCounter(double x, double y) {
        this.gameScore = new GameScore(0);
        init(x, y);
    }

    public void init(double x, double y){
        this.gameScoreCounterText = new Text(x, y, formatGameScoreCounterText());
        this.gameScoreCounterText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        this.gameScoreCounterText.setFill(Color.YELLOW);
        this.gameScoreCounterText.setStroke(Color.BLACK);
        this.gameScoreCounterText.setStrokeWidth(1);
    }

    public Text getGameScoreCounterText() {
        return gameScoreCounterText;
    }

    public void updateGameScoreCounterText(GameScore gameScore) {
        this.gameScore = gameScore;
        gameScoreCounterText.setText(formatGameScoreCounterText());
    }

    private String formatGameScoreCounterText(){
        return "Score: " + gameScore.getScore();
    }
}
