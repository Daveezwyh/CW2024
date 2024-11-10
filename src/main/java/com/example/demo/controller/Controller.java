package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;
import javafx.fxml.FXMLLoader;

public class Controller implements Observer {

	public static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
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
		} catch (IOException e) {
			e.printStackTrace();
			showError("Failed to load main menu: " + e.getMessage());
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

	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			showError("Error: " + e.getMessage());
		}
	}

	public Stage getStage() {
		return stage;
	}

	public void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
