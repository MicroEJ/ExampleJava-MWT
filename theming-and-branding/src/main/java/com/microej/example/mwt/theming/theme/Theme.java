/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.theming.theme;

import ej.microui.display.Font;

/**
 * Theme used to set properties in a StyleTheme (font for instance).
 */
public interface Theme {
	/**
	 * Get's the theme name.
	 *
	 * @return the theme name.
	 */
	String getName();

	/**
	 * Get's the default font for the theme.
	 *
	 * @return path to the font
	 */
	Font getFont();

	/**
	 * Returns true if normal theme otherwise false.
	 *
	 * @return true if normal theme
	 */
	boolean isCondensed();

}
