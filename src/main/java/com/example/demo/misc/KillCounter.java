package com.example.demo.misc;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

/**
 * Represents a kill counter used to track and display the player's progress in terms of kills.
 * The counter shows the current number of kills and the target number of kills required to advance.
 */
public class KillCounter {

    private final Text killCounterText;
    private int currentKills;
    private final int killsToAdvance;

    /**
     * Constructs a {@link KillCounter} with the specified position and target kills to advance.
     *
     * @param x              the X-coordinate for the kill counter's position.
     * @param y              the Y-coordinate for the kill counter's position.
     * @param killsToAdvance the target number of kills required to advance.
     */
    public KillCounter(double x, double y, int killsToAdvance) {
        this.currentKills = 0;
        this.killsToAdvance = killsToAdvance;
        this.killCounterText = new Text(x, y, formatKillCounterText());
        this.killCounterText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        this.killCounterText.setFill(Color.WHITE);
        this.killCounterText.setStroke(Color.BLACK);
        this.killCounterText.setStrokeWidth(1);
    }

    /**
     * Returns the {@link Text} object representing the kill counter text.
     *
     * @return the kill counter text.
     */
    public Text getKillCounterText() {
        return killCounterText;
    }

    /**
     * Sets the current number of kills.
     *
     * @param currentKills the current number of kills.
     * @return the {@link KillCounter} instance for method chaining.
     */
    public KillCounter setCurrentKills(int currentKills) {
        this.currentKills = currentKills;
        return this;
    }

    /**
     * Updates the kill counter text to reflect the current state.
     */
    public void updateKillCounterText() {
        killCounterText.setText(formatKillCounterText());
    }

    /**
     * Formats the kill counter text based on the current kills and the kills to advance.
     *
     * @return the formatted kill counter text.
     */
    private String formatKillCounterText() {
        return "Kills: " + currentKills + " / " + killsToAdvance;
    }
}