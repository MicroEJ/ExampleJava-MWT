/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute.selector;

/**
 * An attribute value selector selects by checking the value of an attribute.
 * <p>
 * Equivalent to <code>[attribute=value]</code> selector in CSS. Its specificity is (0,0,1,0).
 *
 * @see com.microej.example.mwt.attribute.AttributeHolder#getAttribute(String)
 */
public class AttributeValueSelector extends AbstractAttributeSelector {

	private final String value;

	/**
	 * Creates an attribute value selector.
	 *
	 * @param attribute
	 *            the attribute to check.
	 * @param value
	 *            the value to check.
	 */
	public AttributeValueSelector(String attribute, String value) {
		super(attribute);
		this.value = value;
	}

	@Override
	public boolean attributeFits(String value) {
		return value.equals(this.value);
	}
}
