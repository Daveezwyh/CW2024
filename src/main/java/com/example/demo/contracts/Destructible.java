package com.example.demo.contracts;

/**
 * Represents an entity that can be destroyed or removed.
 * This interface defines the basic operations for handling destruction and removal.
 */
public interface Destructible {

	/**
	 * Applies damage to the entity.
	 * The specific behavior of how damage is handled is implemented by the concrete class.
	 */
	void takeDamage();

	/**
	 * Destroys the entity.
	 * This method should handle the complete destruction process, such as updating state and triggering effects.
	 */
	void destroy();

	/**
	 * Removes the entity from the game or scene.
	 * This method should handle cleanup, deallocation, or any other necessary removal logic.
	 */
	void remove();
}