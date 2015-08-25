/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt.widget;

import ej.microui.Event;
import ej.microui.io.Pointer;
import ej.mwt.Widget;

/**
 * A button has a text and a delegate object that will perform actions upon
 * pressed and released. A button has a {@link #isPressed()} method so that
 * button renderer can render the button differently whether it is pressed or
 * not.
 * 
 */
public class Button extends Widget {

	private final ButtonActionHandler handler;
	private final String text;
	private boolean isPressed;

	/**
	 * Construct a new button.
	 * 
	 * @param text
	 *            the text of the button
	 * @param handler
	 *            the handler which is called upon press and release
	 */
	public Button(String text, ButtonActionHandler handler) {
		this.text = text;
		this.handler = handler;
		setPressed(false);
	}

	@Override
	public boolean handleEvent(int event) {
		// When the button is pressed, change the model
		if (Event.getType(event) == Event.POINTER) {
			if (Pointer.isReleased(event)) {
				setPressed(false);
				handler.onReleased(this);
				return true;
			} else if (Pointer.isPressed(event)) {
				setPressed(true);
				handler.onPressed(this);
				return true;
			}
		}

		// Other types of events are not handled
		return super.handleEvent(event);
	}

	/**
	 * Get the text shown by this button.
	 * 
	 * @return the text of the button
	 */
	public String getText() {
		return text;
	}

	/**
	 * Get the current state if this button.
	 * 
	 * @return 'true' is the button is currently pressed
	 */
	public boolean isPressed() {
		return isPressed;
	}

	/**
	 * Change the state of this button and ask for a graphical update.
	 * 
	 * @param isPressed
	 *            new state ('true' is currently pressed)
	 */
	private void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
		repaint();
	}

}
