package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.misc.GameScore;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.example.demo.level.LevelParent;
import com.example.demo.level.LevelNotification;

public class Controller implements Observer {
	private final Stage stage;
	private LevelParent currentLevel;
	private Scene mainMenuScene;
	private Scene gameOverScene;
	private GameOverController gameOverController;
	private GameScore gameScore;

	public Controller(Stage stage) {
		this.stage = stage;
		this.gameScore = new GameScore(0);
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		showMainMenu();
		stage.show();
	}

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

	public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
			if(currentLevel != null){
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
	private void cleanUp(){
		currentLevel.deleteObserver(this);
		currentLevel = null;
		stage.setScene(null);
	}
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
			case WIN_GAME -> {
				gameOverController.setMode(GameOverController.GameOverCase.WIN);
			}
			case LOSE_GAME -> {
				gameOverController.setMode(GameOverController.GameOverCase.LOSE);
			}
		}
		gameOverController.setGameScore(gameScore.getScore());

		gameScore.resetScore();
		stage.setScene(gameOverScene);
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof LevelNotification notification) {
			try {
				LevelNotification.Action levelNotificationAction = notification.nextAction();
				switch (levelNotificationAction) {
					case NEXT_LEVEL -> {
						String levelName = notification.levelName();
						goToLevel(levelName);
					}
					case WIN_GAME, LOSE_GAME -> handleWinOrLoseGame(levelNotificationAction);
                }
			} catch (Exception e) {
				showError(e);
			}
		} else {
			showError(new Exception("Unexpected notification type received."));
		}
	}

	public Stage getStage() {
		return stage;
	}

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
}
