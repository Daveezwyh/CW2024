package com.example.demo;

import javafx.scene.image.*;

public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		Image image = new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm());

		this.setImage(image);
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);

		double aspectRatio = image.getWidth() / image.getHeight();
		this.setFitWidth(aspectRatio * imageHeight);
	}

	public abstract void updatePosition();

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
