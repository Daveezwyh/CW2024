package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the Game
 * This class serves as the entry point of the application and initializes the JavaFX application.
 */
public class Main extends Application {

	// Constant screen dimensions for the game window
	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	/**
	 * Starts the JavaFX application by setting up the primary stage, initializing the game controller,
	 * and launching the game.
	 *
	 * @param stage the primary stage for the JavaFX application
	 * @throws ClassNotFoundException    if the controller class cannot be found
	 * @throws NoSuchMethodException     if a required method is missing
	 * @throws SecurityException         if access to a method or class is restricted
	 * @throws InstantiationException    if the controller class cannot be instantiated
	 * @throws IllegalAccessException    if the current context does not have access to the controller class
	 * @throws IllegalArgumentException  if an illegal argument is passed during initialization
	 * @throws InvocationTargetException if an error occurs during the method invocation
	 */
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		myController = new Controller(stage);
		myController.launchGame();
	}

	/**
	 * The main method serves as the entry point of the application.
	 * It launches the JavaFX application.
	 *
	 * @param args command-line arguments passed to the application (not used)
	 */
	public static void main(String[] args) {
		launch();
	}
}