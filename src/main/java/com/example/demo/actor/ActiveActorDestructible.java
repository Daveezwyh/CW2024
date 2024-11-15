package com.example.demo.actor;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {
	private boolean isDestroyed;
	protected Rectangle boundingBox;
	private final boolean isBoundingBoxVisible = true;
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos)
	{
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;

		boundingBox = new Rectangle(getFitWidth(), getFitHeight());
		boundingBox.setStroke(Color.RED);
		boundingBox.setFill(Color.TRANSPARENT);
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

	@Override
	public abstract void takeDamage();

	@Override
	public void destroy() {
		setDestroyed(true);
	}

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
	protected void updateBoundingBox() {
		boundingBox.setX(getLayoutX() + getTranslateX());
		boundingBox.setY(getLayoutY() + getTranslateY());
		boundingBox.setWidth(getFitWidth());
		boundingBox.setHeight(getFitHeight());
	}

	public boolean isBoundingBoxVisible(){
		return this.isBoundingBoxVisible;
	}
}
