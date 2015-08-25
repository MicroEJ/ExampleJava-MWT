/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt.widget;

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

}
