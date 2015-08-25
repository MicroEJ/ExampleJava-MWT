/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.example.mwt.widget;

import com.is2t.example.mwt.util.ColorsHelper;

import ej.mwt.Widget;

/**
 * A widget formed with a numeric value and a color.
 */
public class ColoredWidget extends Widget {

	private static final int LIGHT_FACTOR = 6;

	private final int value;
	private final int color;
	private final int otherColor;

	/**
	 * Creates a ColorWidget with the given value and the given color.
	 * 
	 * @param value
	 *            the value of the widget.
	 * @param color
	 *            the color of the widget.
	 */
	public ColoredWidget(int value, int color) {
		this.value = value;
		this.color = color;
		this.otherColor = ColorsHelper.isLightColor(color) ? ColorsHelper.darkenColor(color, LIGHT_FACTOR)
				: ColorsHelper.lightenColor(color, LIGHT_FACTOR);
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		// fill available space
		setPreferredSize(widthHint, heightHint);
	}

	/**
	 * Gets primary color.
	 * 
	 * @return the primary color.
	 */
	public int getColor() {
		return this.color;
	}

	/**
	 * Gets secondary color.
	 * 
	 * @return the secondary color.
	 */
	public int getOtherColor() {
		return this.otherColor;
	}

	/**
	 * Gets hold value.
	 * 
	 * @return the value.
	 */
	public int getValue() {
		return this.value;
	}

}
