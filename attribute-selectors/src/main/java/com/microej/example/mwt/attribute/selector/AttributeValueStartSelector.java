/*
 * Copyright 2015-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mwt.attribute.selector;

/**
 * An attribute value start selector selects by checking that the value of an attribute starts with a specific string.
 * <p>
 * Equivalent to <code>[attribute^=value]</code> selector in CSS. Its specificity is (0,0,1,0).
 *
 * @see com.microej.example.mwt.attribute.AttributeHolder#getAttribute(String)
 */
public class AttributeValueStartSelector extends AbstractAttributeSelector {

	private final String value;

	/**
	 * Creates an attribute value start selector.
	 *
	 * @param attribute
	 *            the attribute to check.
	 * @param value
	 *            the value to check.
	 */
	public AttributeValueStartSelector(String attribute, String value) {
		super(attribute);
		this.value = value;
	}

	@Override
	public boolean attributeFits(String value) {
		return value.startsWith(this.value);
	}
}
