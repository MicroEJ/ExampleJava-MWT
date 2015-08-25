/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt.widget;

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
