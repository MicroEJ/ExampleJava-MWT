/*
 * Copyright 2019-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.immutablestylesheet.stylesheet;

import com.microej.example.mwt.immutablestylesheet.fontprovider.FontProvider;

import ej.mwt.style.background.Background;
import ej.mwt.style.dimension.Dimension;
import ej.mwt.style.outline.Outline;

/**
 * Holds immutable style data.
 */
public class ImmutableStyle {

	/* package */ final Dimension dimension;
	/* package */ final byte horizontalAlignment;
	/* package */ final byte verticalAlignment;
	/* package */ final Outline margin;
	/* package */ final Outline border;
	/* package */ final Outline padding;
	/* package */ final Background background;
	/* package */ final int color;
	/* package */ final FontProvider fontProvider;
	/* package */ final Object[] extraFields;
	/* package */ final short map;

	// Never used because instances are immutable but necessary to compile.
	private ImmutableStyle(ImmutableStyle style) {
		this.dimension = style.dimension;
		this.horizontalAlignment = style.horizontalAlignment;
		this.verticalAlignment = style.verticalAlignment;
		this.margin = style.margin;
		this.border = style.border;
		this.padding = style.padding;
		this.background = style.background;
		this.color = style.color;
		this.fontProvider = style.fontProvider;
		this.extraFields = style.extraFields;
		this.map = style.map;
	}
}
