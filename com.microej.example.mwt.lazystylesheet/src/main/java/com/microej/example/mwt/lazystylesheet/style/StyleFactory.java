/*
 * Copyright 2019-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.lazystylesheet.style;

import ej.mwt.style.EditableStyle;

/**
 * A style factory is associated with a rule in the cascading lazy stylesheet.
 *
 * @see CascadingLazyStylesheet#setSelectorStyle(ej.mwt.stylesheet.selector.Selector, StyleFactory)
 */
public interface StyleFactory {

	/**
	 * Applies some properties to an editable style.
	 * 
	 * @param style
	 *            the style to apply on.
	 */
	void applyOn(EditableStyle style);

}
