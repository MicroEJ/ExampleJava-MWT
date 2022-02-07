/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.immutablestylesheet.fontprovider;

import ej.annotation.Nullable;
import ej.microui.display.Font;

/**
 * Provides a specific font identified by its resource path.
 * <p>
 * The font will only be loaded on demand.
 */
public class ResourceFontProvider implements FontProvider {

	private final String path;

	/**
	 * Creates a resource font provider with the resource path of the font to provide.
	 *
	 * @param path
	 *            the resource path of the font to provide.
	 */
	public ResourceFontProvider(String path) {
		this.path = path;
	}

	@Override
	public Font getFont() {
		return Font.getFont(this.path);
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj instanceof ResourceFontProvider) {
			ResourceFontProvider fontProvider = (ResourceFontProvider) obj;
			return this.path.equals(fontProvider.path);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.path.hashCode();
	}
}
