/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.widget;

/**
 * ButtonActionHandler defines the interface for objects responsible to handle
 * press and release action of a {@link Button}. An instance of
 * {@link ButtonActionHandler} should focus on the action of the button, not
 * necessarily on its graphical aspect (such as asking for a repaint).
 * 
 */
public interface ButtonActionHandler {

	/**
	 * Define the action to do when the button is pressed.
	 * 
	 * @param button
	 *            the button that is being pressed
	 */
	void onPressed(Button button);

	/**
	 * Define the action to do when the button is released.
	 * 
	 * @param button
	 *            the button that is being released
	 */
	void onReleased(Button button);

}
