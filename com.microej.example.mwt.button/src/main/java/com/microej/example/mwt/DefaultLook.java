/**
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mwt;

import ej.microui.display.Colors;
import ej.microui.display.Font;

/**
 * A simple look when the renderer can retrieve the colors for display.
 */
public class DefaultLook  {

	public static final int GET_FOREGROUND_COLOR_DEFAULT = 0;
	public static final int GET_BACKGROUND_COLOR_DEFAULT = 1;

	public static int getProperty(int resource) {
		switch (resource) {
		case GET_FOREGROUND_COLOR_DEFAULT:
			return Colors.WHITE;
		case GET_BACKGROUND_COLOR_DEFAULT:
			return Colors.BLACK;
		default:
			// We should never fall into the default case
			throw new RuntimeException("Unknow properties");
			// FIXME only for development, remove for production
		}
	}

	public static Font[] getFonts() {
		return Font.getAllFonts();
	}

}
