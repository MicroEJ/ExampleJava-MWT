/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.immutablestylesheet.fontprovider;

import ej.microui.display.Font;

/**
 * Provides the default font of the system.
 */
public class DefaultFontProvider implements FontProvider {

	/**
	 * DefaultFontProvider singleton to avoid creating several ones.
	 */
	public static final DefaultFontProvider DEFAULT_FONT_PROVIDER = new DefaultFontProvider();
	// equals() and hashCode() from Object are sufficient as long as there is only one instance.

	private DefaultFontProvider() {
	}

	@Override
	public Font getFont() {
		return Font.getDefaultFont();
	}
}
