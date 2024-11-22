package com.example.demo.misc;

import com.example.demo.level.LevelView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class KillCounter {

    private final Text killCounterText;
    private int currentKills;
    private final int killsToAdvance;

    public KillCounter(double x, double y, int killsToAdvance) {
        this.currentKills = 0;
        this.killsToAdvance = killsToAdvance;
        this.killCounterText = new Text(x, y, formatKillCounterText());
        this.killCounterText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        this.killCounterText.setFill(Color.WHITE);
        this.killCounterText.setStroke(Color.BLACK);
        this.killCounterText.setStrokeWidth(1);
    }

    public Text getKillCounterText() {
        return killCounterText;
    }

    public void incrementKillCount() {
        if (currentKills < killsToAdvance) {
            currentKills++;
            updateKillCounterText();
        }
    }

    public void resetKillCount() {
        currentKills = 0;
        updateKillCounterText();
    }

    public KillCounter setCurrentKills(int currentKills) {
        this.currentKills = currentKills;
        return this;
    }

    public void updateKillCounterText() {
        killCounterText.setText(formatKillCounterText());
    }

    private String formatKillCounterText() {
        return "Kills: " + currentKills + " / " + killsToAdvance;
    }
}
