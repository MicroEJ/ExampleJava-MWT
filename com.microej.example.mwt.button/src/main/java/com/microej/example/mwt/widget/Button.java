/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.widget;

import com.microej.example.mwt.DefaultLook;

import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
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

	@Override
	public void render(GraphicsContext g) {
		// Background
		int foreground = DefaultLook.getProperty(DefaultLook.GET_FOREGROUND_COLOR_DEFAULT);
		int background = DefaultLook.getProperty(DefaultLook.GET_BACKGROUND_COLOR_DEFAULT);
		if (isPressed()) {
			// Invert colors
			g.setColor(foreground);
		} else {
			g.setColor(background);
		}
		int width = getWidth();
		int height = getHeight();
		g.fillRect(0, 0, width, height);

		// Border
		g.setColor(foreground);
		g.drawRect(0, 0, width - 1, height - 1);

		// Text
		if (isPressed()) {
			// Invert colors
			g.setColor(background);
		} else {
			g.setColor(foreground);
		}
		String text = getText();
		g.drawString(text, width / 2, height / 2, GraphicsContext.HCENTER
						| GraphicsContext.VCENTER);
		
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		// Nothing to do.
	}

}
