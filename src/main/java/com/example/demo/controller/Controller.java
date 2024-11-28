package com.example.demo.controller;

import com.example.demo.level.LevelNotification;
import com.example.demo.level.LevelParent;
import com.example.demo.util.GameScore;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * The Controller class manages the application's main flow, handling game levels, navigation,
 * and interactions between game components.
 */
public class Controller implements Observer {

	private final Stage stage;
	private LevelParent currentLevel;
	private Scene mainMenuScene;
	private Scene gameInfoScene;
	private Scene gameOverScene;
	private GameOverController gameOverController;
	private GameScore gameScore;

	/**
	 * Constructs a Controller with the specified stage.
	 *
	 * @param stage the main stage of the application.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
		this.gameScore = new GameScore(0);
	}

	/**
	 * Launches the game by displaying the main menu and making the primary stage visible.
	 *
	 * @throws ClassNotFoundException if a required class cannot be found during game initialization.
	 * @throws NoSuchMethodException if a required method or constructor is missing.
	 * @throws SecurityException if access to a method or constructor is denied.
	 * @throws InstantiationException if an instantiation attempt fails (e.g., the class is abstract).
	 * @throws IllegalAccessException if a method or constructor is inaccessible.
	 * @throws IllegalArgumentException if an illegal argument is provided during initialization.
	 * @throws InvocationTargetException if an exception occurs while invoking a method or constructor.
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		showMainMenu();
		stage.show();
	}

	/**
	 * Displays the main menu.
	 */
	public void showMainMenu() {
		if (mainMenuScene == null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/views/MainMenu.fxml"));
				Parent menuRoot = loader.load();
				mainMenuScene = new Scene(menuRoot, stage.getWidth(), stage.getHeight());
				mainMenuScene.getStylesheets().add(
						Objects.requireNonNull(getClass().getResource("/com/example/demo/styles/MainMenu.css")).toExternalForm()
				);

				MainMenuController menuController = loader.getController();
				menuController.setMainController(this);
			} catch (Exception e) {
				showError(e);
				return;
			}
		}
		stage.setScene(mainMenuScene);
	}

	/**
	 * Displays the game information screen.
	 */
	public void showGameInfo() {
		if (gameInfoScene == null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/views/GameInfo.fxml"));
				Parent menuRoot = loader.load();
				gameInfoScene = new Scene(menuRoot, stage.getWidth(), stage.getHeight());
				gameInfoScene.getStylesheets().add(
						Objects.requireNonNull(getClass().getResource("/com/example/demo/styles/GameInfo.css")).toExternalForm()
				);

				GameInfoController infoController = loader.getController();
				infoController.setMainController(this);
			} catch (Exception e) {
				showError(e);
				return;
			}
		}
		stage.setScene(gameInfoScene);
	}

	/**
	 * Navigates to a specified level by dynamically loading the class and initializing it.
	 *
	 * @param className the fully qualified name of the class representing the level.
	 * @throws ClassNotFoundException if the specified class cannot be located.
	 * @throws NoSuchMethodException if a matching constructor cannot be found for the level.
	 * @throws SecurityException if access to the constructor is denied.
	 * @throws InstantiationException if the level class cannot be instantiated (e.g., it's abstract).
	 * @throws IllegalAccessException if the constructor is inaccessible.
	 * @throws IllegalArgumentException if an illegal argument is passed to the constructor.
	 * @throws InvocationTargetException if the constructor invocation causes an exception.
	 */
	public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (currentLevel != null) {
			currentLevel.stopGame();
			cleanUp();
		}

		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		currentLevel.addObserver(this);
		currentLevel.addGameScore(gameScore);

		Scene scene = currentLevel.initializeScene();
		stage.setScene(scene);

		currentLevel.startGame();
	}

	/**
	 * Gets the main stage of the application.
	 *
	 * @return the stage.
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Handles errors by displaying an alert and stopping the game.
	 *
	 * @param exception the exception to handle.
	 */
	public void showError(Exception exception) {
		exception.printStackTrace();

		if (currentLevel != null && currentLevel instanceof LevelParent) {
			currentLevel.stopGame();
		}

		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("An exception occurred");
			alert.setContentText(exception.getMessage() != null ? exception.getMessage() : "No details available.");
			alert.showAndWait();
		});
	}

	/**
	 * Cleans up the current level and resets the scene.
	 */
	private void cleanUp() {
		currentLevel.deleteObserver(this);
		currentLevel = null;
		stage.setScene(null);
	}

	/**
	 * Handles the end-of-game scenario by showing the appropriate game-over screen.
	 *
	 * @param wlcase the win or lose case.
	 * @throws Exception if the game-over screen fails to load.
	 */
	private void handleWinOrLoseGame(LevelNotification.Action wlcase) throws Exception {
		if (gameOverScene == null || gameOverController == null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/views/GameOver.fxml"));
			Parent overlayRoot = loader.load();
			gameOverScene = new Scene(overlayRoot, stage.getWidth(), stage.getHeight());
			gameOverScene.getStylesheets().add(
					Objects.requireNonNull(getClass().getResource("/com/example/demo/styles/GameOver.css")).toExternalForm()
			);
			gameOverController = loader.getController();
			gameOverController.setMainController(this);
		}

		switch (wlcase) {
			case WIN_GAME -> gameOverController.setMode(GameOverController.GameOverCase.WIN);
			case LOSE_GAME -> gameOverController.setMode(GameOverController.GameOverCase.LOSE);
		}
		gameOverController.setGameScore(gameScore.getScore());
		gameScore.resetScore();
		stage.setScene(gameOverScene);
	}

	/**
	 * Handles updates received from observed objects.
	 *
	 * @param arg0 the observable object.
	 * @param arg1 the update argument.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof LevelNotification notification) {
			try {
				LevelNotification.Action levelNotificationAction = notification.nextAction();
				switch (levelNotificationAction) {
					case NEXT_LEVEL -> goToLevel(notification.levelName());
					case WIN_GAME, LOSE_GAME -> handleWinOrLoseGame(levelNotificationAction);
				}
			} catch (Exception e) {
				showError(e);
			}
		} else {
			showError(new Exception("Unexpected notification type received."));
		}
	}
}