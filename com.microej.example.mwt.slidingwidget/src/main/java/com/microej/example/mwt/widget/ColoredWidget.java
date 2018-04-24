/**
 * Java
 *
 * Copyright 2014-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.widget;

import com.microej.example.mwt.util.ColorsHelper;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.mwt.Widget;

/**
 * A widget formed with a numeric value and a color.
 */
public class ColoredWidget extends Widget {
	private static final int PADDING = 10;

	private static final Font BIG_FONT = Font.getFont(Font.LATIN, 50, Font.STYLE_BOLD);

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

	@Override
	public void render(GraphicsContext g) {
		int width = getWidth();
		int height = getHeight();
		// draw border
		g.setColor(getOtherColor());
		g.fillRect(0, 0, width, height);
		// fill background
		g.setColor(getColor());
		g.fillRect(PADDING, PADDING, width - PADDING * 2, height - PADDING * 2);
		// draw centered value
		g.setFont(BIG_FONT);
		g.setColor(getOtherColor());
		String value = String.valueOf(getValue());
		g.drawString(value, width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		
	}

}
