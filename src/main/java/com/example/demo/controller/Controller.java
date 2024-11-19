package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.example.demo.level.LevelParent;
import com.example.demo.level.LevelNotification;

public class Controller implements Observer {
	private final Stage stage;
	private LevelParent currentLevel;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		showMainMenu();
		stage.show();
	}

	private void showMainMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/views/MainMenu.fxml"));
			Scene menuScene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());

			MainMenuController menuController = loader.getController();
			menuController.setMainController(this);

			stage.setScene(menuScene);
		} catch (Exception e) {
			showError(e);
		}
	}

	public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
			if(currentLevel != null){
				currentLevel.deleteObserver(this);
			}

			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			currentLevel.addObserver(this);

			Scene scene = currentLevel.initializeScene();

			stage.setScene(null);
			stage.setScene(scene);
			currentLevel.startGame();
	}
	private void handleLoseGame() {
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof LevelNotification notification) {
			try {
				switch (notification.getNextAction()) {
					case NEXT_LEVEL -> {
						String levelName = notification.getLevelName();
						goToLevel(levelName);
					}
					case WIN_GAME -> {}
					case LOSE_GAME -> handleLoseGame();
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
			((LevelParent) currentLevel).stopGame();
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
