package com.example.demo.actor;

import com.example.demo.contracts.Destructible;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {
	private boolean isDestroyed;
	private boolean shouldRemove;
	protected Rectangle boundingBox;
	private final boolean isBoundingBoxVisible = true;
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos)
	{
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;

		boundingBox = new Rectangle(getFitWidth(), getFitHeight());
		boundingBox.setStroke(Color.RED);
		boundingBox.setFill(Color.TRANSPARENT);
		boundingBox.setX(initialXPos);
		boundingBox.setY(initialYPos);
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

	public abstract void takeDamage();
	public abstract void repairDamage();

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
	@Override
	public void remove(){
		setShouldRemove(true);
	}
	public boolean getShouldRemove(){
		return shouldRemove;
	}
	public void setShouldRemove(boolean shouldRemove){
		this.shouldRemove = shouldRemove;
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
	public long getCreatedTimeStamp(){ return 0; }
}
