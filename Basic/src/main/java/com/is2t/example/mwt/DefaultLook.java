/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.example.mwt;

import ej.microui.Colors;
import ej.microui.io.DisplayFont;
import ej.mwt.rendering.Look;

/**
 * A simple look when the renderer can retrieve the colors for display.
 */
public class DefaultLook implements Look {

	@Override
	public int getProperty(int resource) {
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

	@Override
	public DisplayFont[] getFonts() {
		return DisplayFont.getAllFonts();
	}

}
