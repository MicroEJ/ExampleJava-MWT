/*
 * Copyright 2015-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute.selector;

import com.microej.example.mwt.attribute.AttributeHolder;

import ej.mwt.Widget;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.SelectorHelper;

/**
 * An attribute set selector selects by checking if an attribute is set and if its value fits a specific pattern.
 *
 * @see AttributeHolder#getAttribute(String)
 * @see SelectorHelper
 */
public abstract class AbstractAttributeSelector implements Selector {

	private final String attribute;

	/**
	 * Creates an abstract attribute selector.
	 *
	 * @param attribute
	 *            the attribute to check.
	 */
	public AbstractAttributeSelector(String attribute) {
		this.attribute = attribute;
	}

	@Override
	public boolean appliesToWidget(Widget widget) {
		if (!(widget instanceof AttributeHolder)) {
			return false;
		}

		AttributeHolder attributeHolder = (AttributeHolder) widget;
		String value = attributeHolder.getAttribute(this.attribute);
		if (value == null) {
			return false;
		}

		return attributeFits(value);
	}

	@Override
	public int getSpecificity() {
		return SelectorHelper.getSpecificity(0, 0, 1, 0);
	}

	/**
	 * Checks whether or not the given attribute value fits this selector.
	 *
	 * @param value
	 *            the attribute value to test.
	 * @return <code>true</code> if the given attribute value fits this selector, <code>false</code> otherwise.
	 */
	protected abstract boolean attributeFits(String value);
}
