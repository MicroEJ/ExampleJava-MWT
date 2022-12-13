/*
 * Copyright 2015-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute.selector;

/**
 * An attribute value prefix selector selects by checking that the value of an attribute equals a prefix or starts with
 * this prefix.
 * <p>
 * Checks that the attribute value is exactly the given value or starts with the given value immediately followed by "-"
 * (U+002D).
 * <p>
 * Equivalent to <code>[attribute|=value]</code> selector in CSS. Its specificity is (0,0,1,0).
 *
 * @see com.microej.example.mwt.attribute.AttributeHolder#getAttribute(String)
 */
public class AttributeValuePrefixSelector extends AbstractAttributeSelector {

	private static final char SEPARATOR = '-';

	private final String value;

	/**
	 * Creates an attribute value prefix selector.
	 *
	 * @param attribute
	 *            the attribute to check.
	 * @param value
	 *            the value to check.
	 */
	public AttributeValuePrefixSelector(String attribute, String value) {
		super(attribute);
		this.value = value;
	}

	@Override
	public boolean attributeFits(String value) {
		return value.equals(this.value) || value.startsWith(this.value + SEPARATOR);
	}
}
