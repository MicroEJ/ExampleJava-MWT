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
import ej.mwt.Widget;

/**
 * A label is a widget that simply displays a text.
 * 
 */
public class Label extends Widget {

	private String text;

	/**
	 * Construct a new Label.
	 * 
	 * @param text
	 *            the text to display
	 */
	public Label(String text) {
		super();
		this.setText(text);
	}

	/**
	 * Get the text of this label.
	 * 
	 * @return the label text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Change the text of this label and ask for a graphical update (repaint).
	 * 
	 * @param text
	 *            the new text to display
	 */
	public void setText(String text) {
		this.text = text;
		repaint();
	}

	@Override
	public void render(GraphicsContext g) {

		// Background
		g.setColor(DefaultLook.getProperty(DefaultLook.GET_BACKGROUND_COLOR_DEFAULT));
		int width = getWidth();
		int height = getHeight();
		g.fillRect(0, 0, width, height);

		// Text
		String text = getText();
		g.setColor(DefaultLook.getProperty(DefaultLook.GET_FOREGROUND_COLOR_DEFAULT));
		g.drawString(text, width / 2, height / 2, GraphicsContext.HCENTER
				| GraphicsContext.VCENTER);

	}

	@Override
	public void validate(int widthHint, int heightHint) {
		setSize(widthHint, heightHint);
	}
}
