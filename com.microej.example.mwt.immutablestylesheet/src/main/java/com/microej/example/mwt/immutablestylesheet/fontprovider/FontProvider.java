/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.immutablestylesheet.fontprovider;

import ej.microui.display.Font;

/**
 * Provides a font for widgets.
 */
public interface FontProvider {

	/**
	 * Returns the font provided by this font provider.
	 *
	 * @return the font provided by this font provider.
	 */
	Font getFont();
}
