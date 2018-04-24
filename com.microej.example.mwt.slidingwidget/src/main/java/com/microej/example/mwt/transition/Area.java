/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt.transition;

/**
 * An area.
 */
public class Area {

	private final int x;
	private final int y;
	private final int width;
	private final int height;

	/**
	 * Creates an area.
	 * 
	 * @param x
	 *            the abscissa of the area.
	 * @param y
	 *            the the ordinate of the area.
	 * @param width
	 *            the width of the area.
	 * @param height
	 *            the height of the area.
	 */
	public Area(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Gets the abscissa.
	 * 
	 * @return the abscissa.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Gets the ordinate.
	 * 
	 * @return the ordinate.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Gets the height.
	 * 
	 * @return the height.
	 */
	public int getHeight() {
		return this.height;
	}
}
