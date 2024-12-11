package com.example.demo.actors.shared;

/**
 * Defines the contract for destructible game objects.
 * Classes implementing this interface should provide behavior for taking damage
 * and being destroyed.
 */
public interface Destructible {

	/**
	 * Applies damage to the object.
	 */
	void takeDamage();

	/**
	 * Destroys the object, marking it as no longer active or usable.
	 */
	void destroy();

}