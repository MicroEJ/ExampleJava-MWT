/**
 * Java
 *
 * Copyright 2013-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.util;

/**
 * Color utilities.
 */
public class ColorsHelper {

	private static final int MAX_COLOR_VALUE = 0xff;

	private static final int LIGHT_COLOR_LIMIT = 0xb0;

	private static final double BLUE_LIGHT_FACTOR = 0.114;

	private static final double GREEN_LIGHT_FACTOR = 0.587;

	private static final double RED_LIGHT_FACTOR = 0.299;

	/**
	 * The red shift in RGB value.
	 */
	private static final int RED_SHIFT = 16;

	/**
	 * The green shift in RGB value.
	 */
	private static final int GREEN_SHIFT = 8;

	/**
	 * The blue shift in RGB value.
	 */
	private static final int BLUE_SHIFT = 0;

	/**
	 * The red mask in RGB value.
	 */
	private static final int RED_MASK = MAX_COLOR_VALUE << RED_SHIFT;

	/**
	 * The green mask in RGB value.
	 */
	private static final int GREEN_MASK = MAX_COLOR_VALUE << GREEN_SHIFT;

	/**
	 * The blue mask in RGB value.
	 */
	private static final int BLUE_MASK = MAX_COLOR_VALUE << BLUE_SHIFT;

	/**
	 * The light factor.
	 */
	private static final int LIGHT_FACTOR = 0x10;

	// Prevents initialization.
	private ColorsHelper() {
	}

	/**
	 * Gets a darken color.
	 * 
	 * @param color
	 *            the color to darken.
	 * @param factor
	 *            the darken factor.
	 * @return the darken color.
	 */
	public static int darkenColor(int color, int factor) {
		int red = getRed(color);
		int green = getGreen(color);
		int blue = getBlue(color);

		factor = Math.abs(factor) * LIGHT_FACTOR;

		red = Math.max(0x0, red - factor);
		green = Math.max(0x0, green - factor);
		blue = Math.max(0x0, blue - factor);

		return getColor(red, green, blue);
	}

	/**
	 * Gets a lighten color.
	 * 
	 * @param color
	 *            the color to lighten.
	 * @param factor
	 *            the lighten factor.
	 * @return the lighten color.
	 */
	public static int lightenColor(int color, int factor) {
		int red = getRed(color);
		int green = getGreen(color);
		int blue = getBlue(color);

		factor = Math.abs(factor) * LIGHT_FACTOR;

		red = Math.min(MAX_COLOR_VALUE, red + factor);
		green = Math.min(MAX_COLOR_VALUE, green + factor);
		blue = Math.min(MAX_COLOR_VALUE, blue + factor);

		return getColor(red, green, blue);
	}

	/**
	 * Gets whether a color is light or dark.
	 * 
	 * @param color
	 *            the color to test.
	 * @return {@code true} if the color is a light one, {@code false} otherwise.
	 */
	public static boolean isLightColor(int color) {
		int red = getRed(color);
		int green = getGreen(color);
		int blue = getBlue(color);
		int light = (int) (red * RED_LIGHT_FACTOR + green * GREEN_LIGHT_FACTOR + blue * BLUE_LIGHT_FACTOR);
		return light > LIGHT_COLOR_LIMIT;
	}

	/**
	 * Gets a color red component.
	 * 
	 * @param color
	 *            the color.
	 * @return the red component.
	 */
	public static int getRed(int color) {
		return (color & RED_MASK) >>> RED_SHIFT;
	}

	/**
	 * Gets a color green component.
	 * 
	 * @param color
	 *            the color.
	 * @return the green component.
	 */
	public static int getGreen(int color) {
		return (color & GREEN_MASK) >>> GREEN_SHIFT;
	}

	/**
	 * Gets a color blue component.
	 * 
	 * @param color
	 *            the color.
	 * @return the blue component.
	 */
	public static int getBlue(int color) {
		return (color & BLUE_MASK) >>> BLUE_SHIFT;
	}

	/**
	 * Gets the color from red, green and blue components.
	 * 
	 * @param red
	 *            the red component.
	 * @param green
	 *            the green component.
	 * @param blue
	 *            the blue component.
	 * @return the color.
	 */
	public static int getColor(int red, int green, int blue) {
		return (red << RED_SHIFT) & RED_MASK | (green << GREEN_SHIFT) & GREEN_MASK | (blue << BLUE_SHIFT) & BLUE_MASK;
	}

}
