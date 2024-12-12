/**
 * Singleton class representing a game loop that manages periodic updates.
 * <p>
 * The {@code GameLoop} class uses a {@link Timeline} from JavaFX to handle updates
 * at a fixed interval. It supports operations such as starting, stopping, pausing,
 * and resuming the game loop.
 * </p>
 */
package com.example.demo.singleton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameLoop {

    /**
     * The single instance of the {@code GameLoop}.
     */
    private static GameLoop instance;

    /**
     * The JavaFX {@link Timeline} used to schedule periodic updates.
     */
    private Timeline timeline;

    /**
     * A flag indicating whether the game loop is currently paused.
     */
    private boolean paused = false;

    /**
     * Private constructor to enforce the singleton pattern.
     * Initializes the {@link Timeline} and sets it to loop indefinitely.
     */
    private GameLoop() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE); // Loop indefinitely
    }

    /**
     * Retrieves the singleton instance of {@code GameLoop}.
     * <p>
     * If the instance does not exist, it will be created.
     * </p>
     *
     * @return the singleton instance of {@code GameLoop}
     */
    public static synchronized GameLoop getInstance() {
        if (instance == null) {
            instance = new GameLoop();
        }
        return instance;
    }

    /**
     * Initializes the game loop with a specified frame duration and update task.
     * <p>
     * Clears any existing {@link KeyFrame}s and adds a new one with the specified duration
     * and task.
     * </p>
     *
     * @param frameDuration the duration of each frame
     * @param runnableTask    the task to run on each frame update
     */
    public void initialize(Duration frameDuration, Runnable runnableTask) {
        timeline.getKeyFrames().clear(); // Clear any existing frames
        KeyFrame keyFrame = new KeyFrame(frameDuration, e -> runnableTask.run());
        timeline.getKeyFrames().add(keyFrame);
    }

    /**
     * Starts the game loop.
     * <p>
     * Sets the paused state to {@code false} and plays the {@link Timeline}.
     * </p>
     */
    public void start() {
        paused = false;
        timeline.play();
    }

    /**
     * Stops the game loop.
     * <p>
     * Sets the paused state to {@code false} and stops the {@link Timeline}.
     * </p>
     */
    public void stop() {
        paused = false;
        timeline.stop();
    }

    /**
     * Pauses the game loop.
     * <p>
     * Sets the paused state to {@code true} and pauses the {@link Timeline}.
     * </p>
     */
    public void pause() {
        paused = true;
        timeline.pause();
    }

    /**
     * Resumes the game loop if it is currently paused.
     * <p>
     * Sets the paused state to {@code false} and resumes playing the {@link Timeline}.
     * </p>
     */
    public void resume() {
        if (paused) {
            paused = false;
            timeline.play();
        }
    }

    /**
     * Checks whether the game loop is currently paused.
     *
     * @return {@code true} if the game loop is paused, {@code false} otherwise
     */
    public boolean isPaused() {
        return paused;
    }

    public boolean isRunning() {
        return timeline.getStatus() == Animation.Status.RUNNING;
    }
}