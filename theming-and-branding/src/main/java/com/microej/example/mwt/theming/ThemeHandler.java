/*
 *  Java
 *
 *  Copyright 2023 MicroEJ Corp. All rights reserved.
 *  Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.theming;

import com.microej.example.mwt.theming.stylesheet.FlatStyle;
import com.microej.example.mwt.theming.stylesheet.StyleTheme;
import com.microej.example.mwt.theming.theme.NormalTheme;
import com.microej.example.mwt.theming.theme.Theme;

import ej.mwt.stylesheet.Stylesheet;

/**
 * Theme handler.
 */
public class ThemeHandler {

	/**
	 * Single instance.
	 */
	public static final ThemeHandler INSTANCE = new ThemeHandler();

	private Theme currentColorTheme;
	private StyleTheme currentStyleTheme;

	private ThemeHandler() {
		this.currentColorTheme = new NormalTheme();
		this.currentStyleTheme = new FlatStyle();
	}

	/**
	 * Get's the current color name.
	 *
	 * @return current color name.
	 */
	public String getCurrentColorName() {
		return this.currentColorTheme.getName();
	}

	/**
	 * Get's the current theme name.
	 *
	 * @return current theme name.
	 */
	public String getCurrentThemeName() {
		return this.currentStyleTheme.getName();
	}

	/**
	 * Get's the current stylesheet.
	 *
	 * @return current stylesheet.
	 */
	public Stylesheet getStylesheet() {
		return this.currentStyleTheme.extendStylesheet(this.currentColorTheme);
	}

	/**
	 * Set's the color theme.
	 *
	 * @param colorTheme
	 *            the color theme.
	 */
	public void setColorTheme(Theme colorTheme) {
		this.currentColorTheme = colorTheme;
	}

	/**
	 * Set's the style theme.
	 *
	 * @param styleTheme
	 *            the style theme.
	 */
	public void setStyleTheme(StyleTheme styleTheme) {
		this.currentStyleTheme = styleTheme;
	}
}
